import numpy as np
from scipy.stats import zscore


class PriceAnomalyDetector:
    def __init__(self, data):
        """
        클래스 초기화 메서드.

        Parameters:
        - data: DataFrame, 처리할 데이터셋
        - z_threshold: float, 이상치 판단 기준이 되는 Z-score 임계값
        """
        self.data = data
        # 브랜드와 클래스 이름을 결합하여 새로운 컬럼 생성
        self.data['brandclass_name'] = self.data['brand_name'] + '_' + self.data['class_name']

    def calculate_z_scores(self, group_column, column_name='price'):
        """
        주어진 그룹별로 Z-score를 계산하여 데이터프레임에 추가.

        Parameters:
        - group_column: str, 그룹화 기준이 되는 컬럼명
        - column_name: str, Z-score를 계산할 값이 들어있는 컬럼명 (기본값은 'price')

        Returns:
        - DataFrame: Z-score가 추가된 데이터프레임
        """
        zscore_column = f'{group_column}_zscore'  # Z-score 컬럼 이름 설정
        # 각 그룹별로 'price' 컬럼의 Z-score를 계산하여 새로운 컬럼에 추가
        self.data[zscore_column] = self.data.groupby(group_column)[column_name].transform(lambda x: zscore(x, ddof=0))
        return self.data

    def calculate_weighted_score(self):
        """
        각 Z-score에 가중치를 적용하여 이상치 점수 계산.

        Returns:
        - DataFrame: 가중치가 포함된 이상치 점수가 추가된 데이터프레임
        """
        # 각 Z-score 컬럼에 대한 가중치 점수 계산
        self.data['supplier_score'] = 1 - np.exp(-self.data['supplier_code_zscore'].abs())
        self.data['brandclass_score'] = 1 - np.exp(-self.data['brandclass_name_zscore'].abs())
        self.data['cate2_score'] = 1 - np.exp(-self.data['cate2_nm_zscore'].abs())

        # 종합 이상치 점수 계산 (각 점수를 곱해서 이상치 점수를 계산)
        self.data['outlier_score'] = (self.data['supplier_score'] *
                                      self.data['brandclass_score'] *
                                      self.data['cate2_score']) * 100
        return self.data

    def get_sorted_outliers(self):
        """
        Returns:
        - DataFrame: 가격에 대한 이상치 점수를 반환
        """
        # 이상치 탐지 및 가중치 점수 계산
        scored_outliers = self.calculate_weighted_score()

        # 결과로 반환할 컬럼만 선택
        return scored_outliers[['prd_id', 'supplier_code', 'cate2_nm', 'class_name', 'brand_name',
                                'supplier_code_zscore', 'brandclass_name_zscore', 'cate2_nm_zscore',
                                'overall_outlier', 'outlier_score', 'price']]