from kafka import KafkaConsumer, KafkaProducer
import logging
from typing import Optional
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
    image_path: Optional[str] = None

    
# 로거 설정
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
file_handler = logging.FileHandler('product_consumer.log', encoding='utf-8')
file_handler.setLevel(logging.INFO)

logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)
logger.addHandler(file_handler)

# 환경 변수 세팅
bootstrap_servers = os.getenv("KAFKA_BOOTSTRAP_SERVERS").split(',')
kafka_product_topic = os.getenv('PADS_PRODUCT_TOPIC')
kafka_log_topic = os.getenv('PADS_LOG_TOPIC')
kafka_ack_topic = os.getenv('PADS_ACK_TOPIC')
kafka_fail_topic = "pads_fail_topic"

if not bootstrap_servers or not kafka_product_topic or not kafka_log_topic or not kafka_ack_topic:
    raise EnvironmentError("환경 변수 Load 실패")

# Kafka Producer 세팅
try:
    producer = KafkaProducer(
        bootstrap_servers=bootstrap_servers,
        value_serializer=lambda v: json.dumps(v, ensure_ascii=False).encode('utf-8')
    )
except Exception as e:
    logger.error(f"Kafka Producer 초기화 실패: {str(e)}")
    raise

# S3 client 설정
s3_bucket = os.getenv('S3_BUCKET')
s3_key = os.getenv('CREDENTIALS_ACCESS_KEY')
s3_secret_key = os.getenv('CREDENTIALS_SECRET_KEY')
s3_region = os.getenv('S3_REGION')
try:
    s3_client = boto3.client(
        service_name='s3',
        aws_access_key_id=s3_key,
        aws_secret_access_key=s3_secret_key,
        region_name=s3_region
    )
except Exception as e:
    logger.error(f"S3 클라이언트 초기화 실패: {str(e)}")
    raise

# Detector Import
anomaly_detector = AnomalyDetector()


def send_json_message(topic, message, key=None, timeout=10):
    """Kafka 토픽에 JSON 메시지를 전송"""
    key = key.encode('utf-8') if key else None
    try:
        resp = producer.send(topic, key=key, value=message).get(timeout=timeout)
        logger.info(resp)
    except Exception as e:
        logger.error(f"Kafka 메시지 전송 실패: {str(e)}")


def process_csv_from_s3(request: DetectionReqeust):
    try:
        response = s3_client.get_object(Bucket=s3_bucket, Key=request.csv_path)
        csv_content = response['Body'].read().decode('utf-8')
        data = pd.read_csv(StringIO(csv_content))
    except s3_client.exceptions.NoSuchKey:
        logger.error(f"S3 파일 {request.csv_path}가 존재하지 않습니다.")
        return pd.DataFrame()
    except Exception as e:
        logger.error(f"S3 파일 읽기 오류: {str(e)}")
        return pd.DataFrame()
    return anomaly_detector.process(data)


def data_to_log_mapping(row):
    row_data = row.drop('prd_id').to_dict()
    json_str = json.dumps(row_data, ensure_ascii=False)
    return json_str


def start_consumer(consumer_id):

    consumer = KafkaConsumer(
        kafka_product_topic,
        bootstrap_servers=bootstrap_servers,
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='product_consumer'
    )

    logger.info(f"Consumer {consumer_id} 시작됨")

    for message in consumer:
        try:
            data = json.loads(message.value.decode('utf-8'))
        except json.JSONDecodeError:
            logger.error("JSON 형식 오류")
            continue

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
        send_json_message(kafka_ack_topic, ack_message)
        logger.info(f"Consumer {consumer_id} - 받은 메시지: {data}")

        try:
            resp_data = process_csv_from_s3(detection_request)
            if resp_data.empty:
                logger.info(f"데이터 없음: {detection_request.csv_path}")
                continue
        except KeyError as e:
            send_json_message(kafka_fail_topic, {
                "batch_id": detection_request.batch_id,
                "csv_path": detection_request.csv_path,
                "message": str(e)
            })
            logger.error(f"키 에러 발생: {str(e)}")
            continue
        except Exception as e:
            send_json_message(kafka_fail_topic, {
                "batch_id": detection_request.batch_id,
                "message": str(e)
            })
            logger.error(f"Unknown Exception: {str(e)}")
            continue

        for _, row in resp_data.iterrows():
            json_message = {
                "prd_id": row.get("prd_id"),
                "report": data_to_log_mapping(row),
                "status": "processed"
            }
            send_json_message(kafka_log_topic, json_message)
            logger.debug(f"{kafka_log_topic} - {json_message}")
        logger.info("row 처리 완료")


if __name__ == '__main__':
    start_consumer(1)
