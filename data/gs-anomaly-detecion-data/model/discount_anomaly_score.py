import pandas as pd
from abstract_detector import AbstractDetector


class DiscountAnomalyDetector(AbstractDetector):

    REQUIRED_COLUMNS = ['prd_id','discprice', 'price']

    def __init__(self):
        super().__init__()

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs) -> pd.DataFrame:
        """
        할인 가격이 정가보다 높은 경우 이상치 점수를 계산.
        할인 가격이 정가보다 높은 경우 이상치 점수를 100으로 설정

        Returns:
        - DataFrame: 할인 이상치 점수가 추가된 데이터프레임
        """
        self.validate_data(data)

        data['discount_anomaly_score'] = (data['discprice'] > data['price']) * 100
        return data
