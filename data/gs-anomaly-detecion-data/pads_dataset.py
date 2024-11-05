import os
import json
import numpy as np
from PIL import Image
from torch.utils.data import Dataset
import torch

class TextDataset(Dataset):
    """
        Pads 프로젝트에서 활용할 Text Classification을 위한 dataloader

        Args:
            data_dir (str): JSON 파일이 저장된 경로.
            tokenizer (AutoTokenizer): 활용할 토크나이저 (klur/bert-base활용 권장)
            max_length (int, optional): 텍스트 토큰화 시 최대 길이. 기본값은 128.
    """
    
    def __init__(self, data_dir, tokenizer, max_length=128, is_test=False):
        self.data_dir = data_dir
        self.tokenizer = tokenizer
        self.max_length = max_length

        self.data_ids = [os.path.splitext(f)[0] for f in os.listdir(data_dir) if f.endswith('.json')]

    def __len__(self):
        return len(self.data_ids)

    def __getitem__(self, idx):
        data_id = self.data_ids[idx]
        
        # 텍스트 로드
        with open(os.path.join(self.data_dir, f"{data_id}.json"), 'r') as f:
            data = json.load(f)

        text = data["text"]
        cate1_label = data["cate1_nm"]
        cate2_label = data["cate2_nm"]
        cate3_label = data["cate3_nm"]
        class_label = data["class_name"]
            
        inputs = self.tokenizer(text, padding="max_length", max_length=self.max_length, truncation=True, return_tensors="pt")

        item = {
            "input_ids": inputs["input_ids"].squeeze(0),
            "attention_mask": inputs["attention_mask"].squeeze(0),
            "cate1_label": torch.tensor(cate1_label, dtype=torch.long),
            "cate2_label": torch.tensor(cate2_label, dtype=torch.long),
            "cate3_label": torch.tensor(cate3_label, dtype=torch.long),
            "class_label": torch.tensor(class_label, dtype=torch.long)
        }

        return item