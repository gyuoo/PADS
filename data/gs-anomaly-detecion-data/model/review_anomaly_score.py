import numpy as np
from scipy.stats import zscore

class ReviewAnomalyDetector:
    def __init__(self, data):
        """
        클래스 초기화 메서드.

        Parameters:
        - data: DataFrame, 처리할 데이터셋
        - z_threshold: float, 이상치 판단 기준이 되는 Z-score 임계값
        """
        self.data = data

    def calculate_weighted_score(self):
        """
        리뷰 수와 리뷰 점수를 기반으로 종합 이상치 점수를 계산합니다.

        Parameters:
        - df: DataFrame, 데이터프레임

        Returns:
        - DataFrame: 종합 점수가 추가된 데이터프레임
        """
        # 리뷰 수 및 리뷰 점수 기반으로 가중치 점수 계산
        self.data['review_count_score'] = 1 - np.exp(-0.1 * self.data['review_count'].abs())
        self.data['convert_review_score'] = np.exp(-0.01 * self.data['review_score'].abs())

        # 종합 이상치 점수 계산 (각 점수를 곱해서 이상치 점수를 계산)
        self.data['review_anomaly_score'] = (self.data['convert_review_score'] * self.data['review_count_score']) * 100
        return self.data

    def get_review_anomaly_scores(self):
        """
        Returns:
        - DataFrame: 가격에 대한 이상치 점수를 반환
        """
        # 이상치 탐지 및 가중치 점수 계산
        scored_outliers = self.calculate_weighted_score()

        # 결과로 반환할 컬럼만 선택
        return scored_outliers[['prd_id', 'review_count', 'review_score', 'review_anomaly_score']]