import pandas as pd
from model.abstract_detector import AbstractDetector

class OverallAnomalyScore(AbstractDetector):
    REQUIRED_COLUMNS = ['prd_id', 'A000_score', 'B000_score', 'C000_score', 'D000_score', 'E000_score']
    POWER = 20

    def __init__(self):
        super().__init__()

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs) -> pd.DataFrame:
        """
        제곱 평균 방식의 이상치 종합 점수 계산 로직 추가

        Returns:
        - DataFrame: 종합 이상치 점수가 추가된 데이터프레임
        """
        self.validate_data(data)

        score_columns = ['A000_score', 'B000_score', 'C000_score', 'D000_score', 'E000_score']
        data['total_score'] = (data[score_columns] ** self.POWER).mean(axis=1) ** (1/self.POWER)
        return data
