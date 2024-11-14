import numpy as np
import pandas as pd
import torch
import torch.nn.functional as F
from abstract_detector import AbstractDetector

class CategoryAnomalyDetector(AbstractDetector):
    REQUIRED_COLUMNS = ['prd_id', 'view_name', 'cate1_nm', 'cate2_nm', 'cate3_nm', 'class_name']
    TOP_K = 7  # Top 7까지 가져오도록 설정
    BASE_SCORES = {4: 40, 5: 60, 6: 80, 7: 100}  # Top4부터 Top7까지의 기본 점수 설정

    def __init__(self, model_initializer):
        """
        CategoryAnomalyDetector 초기화 메서드.
        모델, 토크나이저, 라벨 인코더를 설정하며, 기본적으로 CPU 사용.
        """
        
        self.model = model_initializer.model
        self.tokenizer = model_initializer.tokenizer
        self.label_encoders = model_initializer.label_encoders
        self.device = 'cpu'  # CUDA 없이 CPU 사용

    def _calculate_anomaly_score(self, rank, percentage):
        """
        예측 순위와 예측치 퍼센트를 기반으로 점수를 반환합니다.

        Parameters:
        - rank : 예측된 순위 (1부터 시작)
        - percentage : 해당 순위의 예측치 퍼센트

        Returns:
        - score : 이상치 점수
        """
        # Top3까지는 이상치 점수 0
        if rank <= 3:
            return 0
        # Top4부터 Top7까지는 BASE_SCORES에 따른 점수 비율 적용
        base_score = self.BASE_SCORES.get(rank, 100)  # 기본 점수 설정, rank 4부터 7 외는 100
        adjusted_score = base_score * (1 - (percentage / 100))  # 예측치 퍼센트를 반영하여 조정
        return int(adjusted_score)

    def predict_topk(self, text):
        """
        text(view_name)에 대한 각 카테고리별 예측 순위와 예측치 퍼센트를 반환합니다.

        Parameters:
        - text : 예측을 수행할 입력 텍스트

        Returns:
        - topk_predictions : 카테고리별 예측 순위에 해당하는 텍스트 라벨 리스트
        - topk_percentages : 각 순위에 해당하는 예측치 퍼센트 리스트

        """
        # 입력 텍스트를 토큰화하여 CPU 텐서로 변환
        inputs = self.tokenizer(text, padding="max_length", max_length=128, truncation=True, return_tensors="pt")
        input_ids = inputs["input_ids"].to(self.device)
        attention_mask = inputs["attention_mask"].to(self.device)
        
        with torch.no_grad():
            logits = self.model(input_ids=input_ids, attention_mask=attention_mask)

        logits = [F.softmax(logit, dim=-1) for logit in logits]
        topk_predictions = {}
        topk_percentages = {}
        
        for i, column in enumerate(['cate1_nm', 'cate2_nm', 'cate3_nm', 'class_name']):
            topk_results = logits[i].topk(self.TOP_K, dim=-1)
            topk_indices = topk_results.indices.squeeze().tolist()
            topk_scores = topk_results.values.squeeze().tolist()
            
            topk_percentages[column] = [score * 100 for score in topk_scores]  # 0~1 스케일을 퍼센트로 변환
        
            # LabelEncoder에 리스트 전체를 한 번에 전달
            topk_predictions[column] = [
                (self.label_encoders[column].inverse_transform(np.array([idx]))[0]) for idx in topk_indices
            ]
            
        return topk_predictions, topk_percentages

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs):
        """
        카테고리별 이상치 점수와 추천 메시지를 추가한 데이터프레임을 반환.

        Parameters:
        - data : 평가할 데이터가 포함된 데이터프레임

        Returns:
        - data : 각 카테고리별 이상치 점수와 추천 메시지가 추가된 데이터프레임
        """
        self.validate_data(data)
        
        # A001_score, A002_score, A003_score, A004_score 이상치 점수 컬럼 및 A001_message 등 메시지 컬럼 초기화
        data['A001_score'] = 0
        data['A002_score'] = 0
        data['A003_score'] = 0
        data['A004_score'] = 0
        data['A001_message'] = ""
        data['A002_message'] = ""
        data['A003_message'] = ""
        data['A004_message'] = ""

        for idx, row in data.iterrows():
            predictions, percentages = self.predict_topk(row['view_name'])
            for i, column in enumerate(['cate1_nm', 'cate2_nm', 'cate3_nm', 'class_name']):
                # 이상치 점수 계산
                if row[column] in predictions[column]:
                    rank = predictions[column].index(row[column]) + 1
                    percentage = percentages[column][rank - 1]
                    score = self._calculate_anomaly_score(rank, percentage)
                else:
                    score = 100
                data.at[idx, f'A00{i+1}_score'] = score

                # Top 3 라벨링을 가져와서 추천 메시지 생성
                top3_labels = predictions[column][:3]  # Top 3 라벨 가져오기
                if column == 'cate3_nm' and '0' in top3_labels and len(predictions[column]) > 3:
                    top3_labels = [label if label != '0' else predictions[column][3] for label in top3_labels]
                
                recommendation = ", ".join(top3_labels)  # 라벨들만 나열
                message = f"추천 카테고리는 [{recommendation}] 입니다."
                data.at[idx, f'A00{i+1}_message'] = message

            # A000_score 계산: 사용자의 방식대로 가중치와 추가 점수 반영
            base_score = (
                data.at[idx, 'A001_score'] * 0.2 +
                data.at[idx, 'A002_score'] * 0.1 +
                data.at[idx, 'A003_score'] * 0.1 +
                data.at[idx, 'A004_score'] * 0.1
            )
            # 60점 이상인 점수 중 가장 높은 점수에 0.5를 곱해 추가 점수로 사용
            max_above_60 = max(
                [data.at[idx, 'A001_score'], data.at[idx, 'A002_score'], data.at[idx, 'A003_score'], data.at[idx, 'A004_score']],
                default=0
            )
            additional_score = max_above_60 * 0.4 if max_above_60 >= 60 else 0
            data.at[idx, 'A000_score'] = base_score + additional_score

        return data
