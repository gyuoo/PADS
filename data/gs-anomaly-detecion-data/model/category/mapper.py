import json
import os
import numpy as np
from sklearn.preprocessing import LabelEncoder


class Mapper:
    """
    매핑 파일을 통해 LabelEncoder를 관리하는 클래스입니다.
    """

    def __init__(self, json_path):
        self.label_encoder = LabelEncoder()
        self._load_json(json_path)
    
    def _load_json(self, json_path):
        """
        매핑 파일을 로드하여 LabelEncoder에 클래스 설정을 합니다.
        
        Parameters:
        - json_path (str): 매핑 파일 경로
        """
        with open(json_path, 'r', encoding='utf-8') as f:
            label_mapping = json.load(f)
        self.label_encoder.classes_ = np.array(label_mapping)

    def get_length(self):
        """
        레이블의 개수를 반환합니다.
        
        Returns:
        - int: 레이블 개수
        """
        return len(self.label_encoder.classes_)
        
    def get_label_encoder(self):
        """
        내부 LabelEncoder 객체를 반환합니다.
        
        Returns:
        - LabelEncoder: LabelEncoder 객체
        """
        return self.label_encoder