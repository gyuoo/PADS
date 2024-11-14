import torch
from transformers import AutoTokenizer, AutoModel
import torch.nn as nn

class MultiLabelBERT(nn.Module):
    def __init__(self, model_name, num_labels_cate1, num_labels_cate2, num_labels_cate3, num_labels_class):
        super(MultiLabelBERT, self).__init__()
        self.bert = AutoModel.from_pretrained(model_name)
        hidden_size = self.bert.config.hidden_size
        
        # 각 카테고리에 대한 분류 헤드 생성
        self.cate1_classifier = nn.Linear(hidden_size, num_labels_cate1)
        self.cate2_classifier = nn.Linear(hidden_size, num_labels_cate2)
        self.cate3_classifier = nn.Linear(hidden_size, num_labels_cate3)
        self.class_classifier = nn.Linear(hidden_size, num_labels_class)

        # 손실 함수 정의
        self.loss_fn = nn.CrossEntropyLoss()

    def forward(self, input_ids, attention_mask=None, cate1_labels=None, cate2_labels=None, cate3_labels=None, class_labels=None):
        outputs = self.bert(input_ids=input_ids, attention_mask=attention_mask)
        pooled_output = outputs.pooler_output  # [CLS] 토큰의 출력
        
        # 각 분류 헤드에서 예측 결과 생성
        cate1_logits = self.cate1_classifier(pooled_output)
        cate2_logits = self.cate2_classifier(pooled_output)
        cate3_logits = self.cate3_classifier(pooled_output)
        class_logits = self.class_classifier(pooled_output)

        # 라벨이 제공된 경우 로스 계산
        total_loss = None
        if cate1_labels is not None and cate2_labels is not None and cate3_labels is not None and class_labels is not None:
            loss_cate1 = self.loss_fn(cate1_logits, cate1_labels)
            loss_cate2 = self.loss_fn(cate2_logits, cate2_labels)
            loss_cate3 = self.loss_fn(cate3_logits, cate3_labels)
            loss_class = self.loss_fn(class_logits, class_labels)
            total_loss = loss_cate1 + loss_cate2 + loss_cate3 + loss_class

        return (total_loss, cate1_logits, cate2_logits, cate3_logits, class_logits) if total_loss is not None else (cate1_logits, cate2_logits, cate3_logits, class_logits)
