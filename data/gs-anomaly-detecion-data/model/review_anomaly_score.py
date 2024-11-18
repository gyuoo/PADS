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

    def generate_message(row):
        """
        특정 행(row)에 대해 적절한 메시지를 생성.

        Parameters:
        - row: DataFrame의 행

        Returns:
        - str: 메시지
        """
        # 리뷰 개수가 적은 경우
        if row['review_count'] < 20:
            return "리뷰 개수가 적어 이상 여부를 판단하기 어렵습니다."

        # 리뷰 점수가 50점 이상인 경우
        if row['E001_score'] >= 50:
            return (f"리뷰가 좋지 못한 상품입니다. "
                    f"리뷰 개수: {row['review_count']}, 평균 리뷰 점수: {row['review_score']:.1f}점.")

        # 정상적인 경우
        return f"리뷰 개수: {row['review_count']}, 평균 리뷰 점수: {row['review_score']:.1f}점인 상품입니다."

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
        data['E001_message'] = data.apply(self.generate_message, axis=1)
        data['E000_score'] = data['E001_score']

        data = data.drop(columns=self.TO_REMOVE)
        return data