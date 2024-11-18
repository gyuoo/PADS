import pandas as pd
import json
from model.statistics_updater import StatisticsUpdater

# {"batch_id":11232131,"csv_path":"test-input/input_sample_0.csv","image_path":""}
def main():
    # 새로 들어온 데이터를 로드
    with open('dataset/new_data.json', 'r', encoding='utf-8') as f:
        new_data = pd.DataFrame(json.load(f))

    # StatisticsUpdater 클래스 인스턴스 생성 및 업데이트 실행
    updater = StatisticsUpdater(stats_dir='dataset')
    updater.update_from_dataframe(new_data)

    # 빈 리스트로 초기화
    with open('dataset/new_data.json', 'w', encoding='utf-8') as f:
        json.dump([], f, ensure_ascii=False, indent=4)

if __name__ == "__main__":
    main()