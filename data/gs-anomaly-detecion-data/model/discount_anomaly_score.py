import numpy as np

class DiscountAnomalyDetector:
    def __init__(self, data):
        self.data = data

    def get_discount_outlier_score(self):
        """
        할인 가격이 정가보다 높은 경우 이상치 점수를 계산.

        Returns:
        - DataFrame: 할인 이상치 점수가 추가된 데이터프레임
        """
        # 할인 가격이 정가보다 높은 경우 이상치 점수를 100으로 설정
        self.data['disc_outlier_score'] = (self.data['discprice'] > self.data['price']) * 100
        return self.data