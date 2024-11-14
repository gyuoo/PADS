import os
import json
import numpy as np


class StatisticsUpdater:
    def __init__(self, stats_dir='dataset'):
        """
        클래스 초기화 메서드.

        Parameters:
        - stats_dir: str, 그룹별 통계가 저장된 디렉토리 경로
        """
        self.stats_dir = stats_dir
        self.stats_files = {
            'supplier_code': os.path.join(stats_dir, 'supplier_code_statistics.json'),
            'cate2_nm': os.path.join(stats_dir, 'cate2_nm_statistics.json'),
            'brandclass_name': os.path.join(stats_dir, 'brandclass_name_statistics.json')
        }
        self.stats_data = {col: self.load_statistics(file) for col, file in self.stats_files.items()}

    def load_statistics(self, file_path):
        """ JSON 파일에서 통계 데이터를 로드합니다. """
        if os.path.exists(file_path):
            with open(file_path, 'r', encoding='utf-8') as f:
                return json.load(f)
        else:
            return {}

    def update_statistics(self, column, new_data):
        """
        Welford 알고리즘을 사용하여 새로운 데이터로 통계를 업데이트.

        Parameters:
        - column: str, 업데이트할 컬럼 이름 ('supplier_code', 'cate2_nm', 'brandclass_name')
        - new_data: DataFrame, 새로운 데이터가 포함된 데이터프레임
        """
        # 증분 데이터 없을 경우 리턴
        if new_data.empty or column not in new_data.columns:
            return

        stats = self.stats_data[column]

        for value in new_data[column].unique():
            group_data = new_data[new_data[column] == value]['price']
            count = len(group_data)
            mean = group_data.mean()
            variance = group_data.var(ddof=0)

            # 기존 값이 있으면 Welford's Algorithm으로 업데이트
            if value in stats:
                old_count = stats[value]["count"]
                old_mean = stats[value]["mean"]
                old_std = stats[value]["std"]
                old_std = 0 if np.isnan(old_std) else old_std
                old_variance = old_std ** 2

                # 새 데이터와 기존 통계를 결합하여 새로운 통계 계산
                combined_count = old_count + count
                delta = mean - old_mean
                new_mean = old_mean + delta * count / combined_count
                new_variance = ((old_variance * (old_count - 1) + variance * (count - 1) +
                                 delta ** 2 * old_count * count / combined_count) / (combined_count - 1))
                new_std = np.sqrt(new_variance)

                # 업데이트된 값 저장
                stats[value] = {
                    "name": value,
                    "count": combined_count,
                    "mean": new_mean,
                    "std": 0 if np.isnan(new_std) else new_std
                }

            # 기존 값이 없으면 새로 추가
            else:
                stats[value] = {
                    "name": value,
                    "count": count,
                    "mean": mean,
                    "std": 0 if np.isnan(np.sqrt(variance)) else np.sqrt(variance)
                }

    def save_statistics(self):
        """
        업데이트된 통계를 JSON 파일로 저장.
        """
        for column, file_path in self.stats_files.items():
            with open(file_path, 'w', encoding='utf-8') as f:
                json.dump(list(self.stats_data[column].values()), f, ensure_ascii=False, indent=4)

    def update_from_dataframe(self, new_data):
        """
        주어진 데이터프레임을 사용하여 모든 컬럼의 통계를 업데이트합니다.

        Parameters:
        - new_data: DataFrame, 새로운 데이터가 포함된 데이터프레임
        """
        new_data['brandclass_name'] = new_data['brand_name'] + '_' + new_data['class_name']

        for column in self.stats_files.keys():
            self.update_statistics(column, new_data)

        self.save_statistics()