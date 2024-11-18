import numpy as np
import pandas as pd
import os
import json

from model.abstract_detector import AbstractDetector


class PriceAnomalyDetector(AbstractDetector):

    REQUIRED_COLUMNS = ['prd_id', 'brand_name', 'class_name','supplier_code','cate2_nm', 'price']
    GROUP_COLUMNS = ['supplier_code', 'cate2_nm', 'brandclass_name']
    COEFFICIENT = 0.8

    def __init__(self, stats_dir='dataset'):
        """
        클래스 초기화 메서드.
        """
        super().__init__()
        self.stats = self.load_statistics('dataset')

    def load_statistics(self, stats_dir):
        """
        그룹별 모평균 및 모표준편차를 JSON 파일에서 로드.
        """
        stats = {}
        for col in self.GROUP_COLUMNS:
            file_path = os.path.join(stats_dir, f'{col}_statistics.json')
            with open(file_path, 'r', encoding='utf-8') as f:
                stats[col] = {item["name"]: {"mean": item["mean"], "std": item["std"] if item["std"] != 0 else 1e-6}
                              for item in json.load(f)}
        return stats

    def calculate_zscore(self, value, mean, std):
        """
        주어진 값에 대해 Z-score를 계산.
        """
        return (value - mean) / std

    def generate_message(self, row):
        """
        특정 행(row)에 대해 적절한 메시지를 생성.

        Parameters:
        - row: DataFrame의 행

        Returns:
        - str: 메시지
        """
        # 가격 이상치 점수가 높은 경우
        if row['D001_score'] > 75:
            return (f"이상치 점수가 높습니다. "
                    f"공급사 코드: {row['supplier_code']}, 카테고리: {row['cate2_nm']}, "
                    f"브랜드 및 클래스: {row['brandclass_name']}, 가격: {row['price']:.2f}원.")

        # 정상적인 경우
        return (f"가격 데이터는 정상으로 보입니다. "
                f"공급사 코드: {row['supplier_code']}, 카테고리: {row['cate2_nm']}, 가격: {row['price']:.2f}원.")

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs):
        """
        각 Z-score에 가중치를 적용하여 이상치 점수 계산.

        Returns:
        - DataFrame: 가중치 포함된 이상치 점수가 추가된 데이터프레임
        """
        self.validate_data(data)

        data['brandclass_name'] = data['brand_name'] + '_' + data['class_name']
        score_columns = []

        # 각 그룹별로 Z-score 계산
        for col in self.GROUP_COLUMNS:
            zscore_column = f'{col}_zscore'
            score_column = f'{col}_score'

            # 각 row에 대해 해당 그룹의 평균과 표준편차를 사용하여 Z-score를 계산
            data[zscore_column] = data.apply(
                lambda row: self.calculate_zscore(
                    row['price'],
                    self.stats[col].get(row[col], {"mean": 0, "std": 1})["mean"],
                    self.stats[col].get(row[col], {"mean": 0, "std": 1})["std"]
                ),
                axis=1
            )
            data[score_column] = 1 - np.exp(-self.COEFFICIENT * data[zscore_column].abs())
            score_columns.append(score_column)

        # 종합 이상치 점수 계산 (각 점수를 곱해서 이상치 점수를 계산), price_anomaly : D001
        data['D001_score'] = data[score_columns].apply(lambda row: np.prod(row), axis=1) * 100
        data['D001_message'] = data.apply(self.generate_message, axis=1)
        data['D000_score'] = data['D001_score']

        # 사용한 점수 및 Z-score 컬럼 삭제
        data = data.drop(columns=score_columns + [f'{col}_zscore' for col in self.GROUP_COLUMNS])
        return data
