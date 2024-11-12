from kafka import KafkaConsumer, KafkaProducer
import logging
import multiprocessing
import os
import json
import boto3
import pandas as pd
from io import StringIO
from pydantic import BaseModel, ValidationError
from model import AnomalyDetector


class DetectionReqeust(BaseModel):
    batch_id: int
    csv_path: str
    image_path: str


# Kafka Producer config
# bootstrap_servers = os.getenv('KAFKA_BOOTSTRAP_SERVERS')
producer = KafkaProducer(
    bootstrap_servers=['localhost:9092','localhost:9094','localhost:9096'],
    value_serializer=lambda v: json.dumps(v, ensure_ascii=False).encode('utf-8')  # JSON 직렬화 설정
)

kafka_log_topic = os.getenv('PADS_LOG_TOPIC')
kafka_ack_topic = os.getenv('PADS_ACK_TOPIC')

formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
file_handler = logging.FileHandler('product_consumer.log', encoding='utf-8')
file_handler.setLevel(logging.INFO)

logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

logger.addHandler(file_handler)

# S3 client 설정
s3_bucket = os.getenv('S3_BUCKET')
s3_key = os.getenv('CREDENTIALS_ACCESS_KEY')
s3_secret_key = os.getenv('CREDENTIALS_SECRET_KEY')
s3_region = os.getenv('S3_REGION')
s3_client = boto3.client(service_name='s3',
                         aws_access_key_id=s3_key,
                         aws_secret_access_key=s3_secret_key,
                         region_name=s3_region)

# Detector Import
anomaly_detector = AnomalyDetector()


def send_json_message(topic, message, producer, key=None):
    """Kafka 토픽에 JSON 메시지를 전송"""
    key = key.encode('utf-8') if key else None
    resp = producer.send(topic, key=key, value=message).get(timeout=10)
    logger.info(resp)


def process_csv_from_s3(request: DetectionReqeust):
    response = s3_client.get_object(Bucket=s3_bucket, Key=request.csv_path)
    csv_content = response['Body'].read().decode('utf-8')
    data = pd.read_csv(StringIO(csv_content))
    return anomaly_detector.process(data)


def data_to_log_mapping(row):
    row_data = row.drop('prd_id').to_dict()
    json_str = json.dumps(row_data, ensure_ascii=False)
    return json_str


def start_consumer(consumer_id):
    topic_name = os.getenv('PADS_PRODUCT_TOPIC')
    log_topic_name = os.getenv('PADS_LOG_TOPIC')
    bootstrap_servers = ['localhost:9092','localhost:9094','localhost:9096']

    consumer = KafkaConsumer(
        topic_name,
        bootstrap_servers=bootstrap_servers,
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='product_consumer'
    )

    logger.info(f"Consumer {consumer_id} 시작됨")

    for message in consumer:
        data = json.loads(message.value.decode('utf-8'))

        try:
            detection_request = DetectionReqeust(**data)
        except ValidationError as e:
            logger.error("Validation 실패 : ", e.json())
            continue
        except json.JSONDecodeError:
            logger.error("Json format 오류")
            continue

        ack_message = {
            "batch_id": detection_request.batch_id
        }
        print(ack_message)
        send_json_message(kafka_ack_topic, ack_message, producer)
        logger.info(f"Consumer {consumer_id} - 받은 메시지: {data}")

        resp_data = process_csv_from_s3(detection_request)

        for _, row in resp_data.iterrows():
            json_message = {
                "prd_id": row.get("prd_id"),
                "report": data_to_log_mapping(row),
                "status": "processed"
            }
            send_json_message(kafka_log_topic, json_message, producer)
            logger.debug(f"{kafka_log_topic} - {json_message}")
        logger.info("row 처리 완료")


if __name__ == '__main__':
    start_consumer(1)
