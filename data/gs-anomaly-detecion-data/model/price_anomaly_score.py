import numpy as np
import pandas as pd

from abstract_detector import AbstractDetector
from scipy.stats import zscore


class PriceAnomalyDetector(AbstractDetector):

    REQUIRED_COLUMNS = ['prd_id', 'brand_name', 'class_name','supplier_code','cate2_nm', 'price']
    GROUP_COLUMNS = ['supplier_code', 'cate2_nm', 'brandclass_name']

    def __init__(self, z_threshold=0.8):
        """
        클래스 초기화 메서드.

        Parameters:
        - z_threshold: float, 이상치 판단 기준이 되는 Z-score 임계값
        """
        super().__init__()
        self.z_threshold = z_threshold

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs):
        """
        각 Z-score에 가중치를 적용하여 이상치 점수 계산.

        Returns:
        - DataFrame: 가중치 포함된 이상치 점수가 추가된 데이터프레임
        """
        self.validate_data(data)

        data['brandclass_name'] = data['brand_name'] + '_' + data['class_name']
        score_columns = []

        for col in self.GROUP_COLUMNS:
            zscore_column = f'{col}_zscore'
            score_column = f'{col}_score'
            data[zscore_column] = data.groupby(col)['price'].transform(lambda x: zscore(x, ddof=0))
            data[score_column] = 1- np.exp(-self.z_threshold*data[zscore_column].abs())
            score_columns.append(score_column)

        # 종합 이상치 점수 계산 (각 점수를 곱해서 이상치 점수를 계산)
        data['price_anomaly_score'] = data[score_columns].apply(lambda row: np.prod(row), axis=1) * 100

        data = data.drop(columns=score_columns + [f'{col}_zscore' for col in self.GROUP_COLUMNS])
        return data
