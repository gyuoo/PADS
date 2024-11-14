import os
import torch
from transformers import AutoTokenizer
from multi_label_bert import MultiLabelBERT
from mapper import Mapper  # Mapper 클래스를 import 합니다.


class ModelInitializer:
    """
    모델과 토크나이저, 그리고 각 카테고리의 레이블 인코더를 초기화하는 클래스입니다.
    """
    def __init__(self, model_name, model_path, mapping_path):
        self.model_name = model_name
        self.model_path = model_path
        self.mapping_path = mapping_path

        # 레이블 인코더 초기화
        self.mappers = self._initialize_label_encoders()
        self.label_encoders = {category: mapper.get_label_encoder() for category, mapper in self.mappers.items()}
        self.num_labels = self._get_num_labels()
        
        # 모델과 토크나이저 초기화
        self.model, self.tokenizer = self._initialize_model_and_tokenizer()

    def _initialize_label_encoders(self):
        """
        각 카테고리의 레이블 인코더를 초기화합니다.
        
        Returns:
        - dict: 각 카테고리별 초기화된 Mapper 객체 딕셔너리
        """
        mappers = {}
        for filename in os.listdir(self.mapping_path):
            if filename.endswith("_mapping.json"):
                column = filename.replace("_mapping.json", "")
                mappers[column] = Mapper(os.path.join(self.mapping_path, filename))

        return mappers

    def _get_num_labels(self):
        """
        각 카테고리의 레이블 수를 딕셔너리로 반환합니다.
        
        Returns:
        - dict: 각 카테고리별 레이블 수
        """
        return {category: encoder.get_length() for category, encoder in self.mappers.items()}

    def _initialize_model_and_tokenizer(self):
        """
        모델과 토크나이저를 초기화하고, 모델의 상태를 불러옵니다.

        Returns:
        - model (torch.nn.Module): 초기화된 모델
        - tokenizer (transformers.PreTrainedTokenizer): 초기화된 토크나이저
        """
        # 모델 초기화
        model = MultiLabelBERT(
            model_name=self.model_name, 
            num_labels_cate1=self.num_labels['cate1_nm'], 
            num_labels_cate2=self.num_labels['cate2_nm'], 
            num_labels_cate3=self.num_labels['cate3_nm'], 
            num_labels_class=self.num_labels['class_name']
        )
        
        # 모델 가중치 로드
        model.load_state_dict(torch.load(self.model_path, map_location=torch.device('cpu')))
        model.eval()

        # 토크나이저 초기화
        tokenizer = AutoTokenizer.from_pretrained(self.model_name)

        return model, tokenizer
