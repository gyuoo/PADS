import os
import json
from PIL import Image
from torch.utils.data import Dataset

class PadsDataset(Dataset):
    """
        Pads 프로젝트에서 활용할 dataloader

        Args:
            img_dir (str): 이미지 파일들이 저장된 디렉터리 경로.
            text_dir (str): 텍스트 파일들이 저장된 디렉터리 경로 (JSON 파일 형식).
            tokenizer (AutoTokenizer): 활용할 토크나이저 (camiller/Korean_Llama_Tokenizer활용 권장)
            transform (callable, optional): 이미지에 적용할 변환 함수. 기본값은 None.
            max_length (int, optional): 텍스트 토큰화 시 최대 길이. 기본값은 128.
            data_type (str, optional): 사용할 데이터 유형. 'Both', 'Image', 'Text' 중 하나여야 합니다.
                - 'Both': 이미지와 텍스트 데이터를 모두 사용.
                - 'Image': 이미지 데이터만 사용.
                - 'Text': 텍스트 데이터만 사용.
                
        Raises:
            ValueError: data_type이 'Both', 'Image', 'Text' 중 하나가 아닐 경우 발생.
    """
    
    def __init__(self, img_dir, text_dir, tokenizer, transform=None, max_length=128, data_type="Both"):
        self.img_dir = img_dir
        self.text_dir = text_dir
        self.tokenizer = tokenizer
        self.transform = transform
        self.max_length = max_length
        self.data_ids = [os.path.splitext(f)[0] for f in os.listdir(text_dir) if f.endswith('.json')]
        
        # data_type 유효성 검사
        if data_type not in {"Both", "Image", "Text"}:
            raise ValueError(f"Invalid data_type '{data_type}'. Expected one of: 'Both', 'Image', 'Text'.")
        
        self.data_type = data_type

    def __len__(self):
        return len(self.data_ids)

    def __getitem__(self, idx):
        data_id = self.data_ids[idx]
        
        # 텍스트 로드
        with open(os.path.join(self.text_dir, f"{data_id}.json"), 'r') as f:
            text_data = json.load(f).get("text", "")
        text_tokens = self.tokenizer(text_data, padding="max_length", max_length=self.max_length, truncation=True, return_tensors="pt")

        # 이미지 로드 (이미지가 없을 경우 빈 이미지 생성)
        img_path = os.path.join(self.img_dir, f"{data_id}.jpg")
        if os.path.exists(img_path):
            image = Image.open(img_path).convert("RGB").resize((224, 224))
        else:
            image = Image.fromarray(np.zeros((224, 224, 3), dtype=np.uint8), "RGB")
        
        if self.transform:
            image = self.transform(image)
        
        # data_type에 따른 리턴 값 설정
        if self.data_type == "Both":
            return {
                "image": image,
                "text": text_tokens["input_ids"].squeeze(0)
            }
        elif self.data_type == "Image":
            return {
                "image": image
            }
        elif self.data_type == "Text":
            return {
                "text": text_tokens["input_ids"].squeeze(0)
            }