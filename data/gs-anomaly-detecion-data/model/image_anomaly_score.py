import pandas as pd
import httpx
import asyncio
from model.abstract_detector import AbstractDetector

class ImageAnomalyDetector(AbstractDetector):
    REQUIRED_COLUMN = ['prd_id']
    def __init__(self):
        super().__init__()

    async def fetch_image_status(self, client, prd_id):
        """
        개별 상품 ID에 대해 이미지 URL의 상태 코드를 확인하는 비동기 함수
        """
        image_url = f"http://image.gsshop.com/image/{prd_id[0:2]}/{prd_id[2:4]}/{prd_id}_L1.jpg"
        try:
            response = await client.get(image_url, timeout=5.0)
            if response.status_code == 404:
                return 100, "상품의 메인 이미지가 없습니다."
            else:
                return 0, "메인 이미지가 있는 정상상품입니다."
        except httpx.RequestError as e:
            return 100, f"Error: {e}"

    async def calculate_anomaly_async(self, data: pd.DataFrame) -> pd.DataFrame:
        """
        비동기 방식으로 이미지 이상치 점수를 계산.
        """
        async with httpx.AsyncClient() as client:
            tasks = [
                self.fetch_image_status(client, str(row['prd_id']))
                for _, row in data.iterrows()
            ]
            results = await asyncio.gather(*tasks)

        # 결과를 DataFrame에 추가
        data['C001_score'], data['C001_message'] = zip(*results)
        data['C000_score'] = data['C001_score']
        return data

    def calculate_anomaly(self, data: pd.DataFrame, **kwargs) -> pd.DataFrame:
        """
        메인 이미지가 등록되지 않은 경우 이상치 점수를 100으로 설정

        Returns:
        - DataFrame: 이미지 이상치 점수가 추가된 데이터프레임
        """
        self.validate_data(data)
        return asyncio.run(self.calculate_anomaly_async(data))