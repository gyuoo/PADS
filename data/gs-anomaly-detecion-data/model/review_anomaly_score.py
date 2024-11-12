import numpy as np
import pandas as pd
from model.abstract_detector import AbstractDetector


class ReviewAnomalyDetector(AbstractDetector):

    REQUIRED_COLUMNS = ['prd_id', 'review_count', 'review_score']
    TO_REMOVE = ['review_count_score','convert_review_score']
    REVIEW_COEFFICIENT = -0.1
    CONVERT_REVIEW_COEFFICIENT = -0.01

    def __init__(self):
        """
        클래스 초기화 메서드.

        Parameters:
        """
        super().__init__()

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs) -> pd.DataFrame:
        """
        가격에 대한 이상치 점수를 반환합니다.

        Returns:
        - DataFrame: 이상치 점수가 포함된 데이터프레임
        """
        self.validate_data(data)

        # 이상치 탐지 및 가중치 점수 계산
        # 리뷰 수 및 리뷰 점수 기반으로 가중치 점수 계산
        data['review_count_score'] = 1 - np.exp(self.REVIEW_COEFFICIENT * data['review_count'].abs())
        data['convert_review_score'] = np.exp(self.CONVERT_REVIEW_COEFFICIENT * data['review_score'].abs())

        # 종합 이상치 점수 계산 (각 점수를 곱해서 이상치 점수를 계산), reveiw_anomaly : E001
        data['E001_score'] = (data['convert_review_score'] * data['review_count_score']) * 100
        data['E001_message'] = "TBD"

        data = data.drop(columns=self.TO_REMOVE)
        return data