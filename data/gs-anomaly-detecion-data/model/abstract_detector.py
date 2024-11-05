from abc import ABC, abstractmethod
import pandas as pd


class AbstractDetector(ABC):

    REQUIRED_COLUMNS = []

    def __init__(self):
        pass

    def validate_data(self, data):
        """
        필수 컬럼이 존재하는지 확인하고 없으면 예외 발생
        """
        if not isinstance(data, pd.DataFrame):
            raise ValueError("data parameter must be pd.DataFrame instance.")

        missing_columns = [col for col in self.REQUIRED_COLUMNS if col not in data.columns]
        if missing_columns:
            raise ValueError(f"필수 column 누락: {', '.join(missing_columns)}")

    @abstractmethod
    def calculate_anomaly(self, data: pd.DataFrame, **kwargs) -> pd.DataFrame:
        """
        DataFrame을 받아서 이상치 column이 추가된 DataFrame을 Return

        Returns:
        - DataFrame: 이상치 column이 추가된 데이터프레임
        """
        pass
