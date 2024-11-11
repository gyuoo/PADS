import pandas as pd
from model import DiscountAnomalyDetector, PriceAnomalyDetector, ReviewAnomalyDetector


class AnomalyDetector:
    def __init__(self):
        """
        AnomalyDetector 초기화 메서드. 각 하위 이상치 탐지 클래스를 리스트로 관리합니다.
        """
        self.detectors = [
            DiscountAnomalyDetector(),
            PriceAnomalyDetector(),
            ReviewAnomalyDetector()
        ]

    def process(self, data:pd.DataFrame):
        """
        모든 이상치 점수를 계산하고 결과를 반환합니다.

        Parameters:
        - data: DataFrame, 이상치 점수를 계산할 데이터

        Returns:
        - DataFrame: 모든 이상치 점수가 포함된 데이터프레임
        """
        columns = data.columns
        columns.drop('prd_id')

        # 각 Detector의 calculate_anomaly 호출
        for detector in self.detectors:
            data = detector.calculate_anomaly(data)

        data = data.drop(columns=columns)
        return data

