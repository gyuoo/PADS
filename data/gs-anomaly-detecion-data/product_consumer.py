from dotenv import load_dotenv
from kafka import KafkaConsumer, KafkaProducer
import logging
import multiprocessing
import os
import json

from random import randint

# 환경변수 load
load_dotenv()

# Kafka Producer config
bootstrap_servers = os.getenv('KAFKA_BOOTSTRAP_SERVERS')
producer = KafkaProducer(
    bootstrap_servers=[bootstrap_servers],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')  # JSON 직렬화 설정
)

kafka_log_topic = os.getenv('PADS_LOG_TOPIC')

formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
file_handler = logging.FileHandler('product_consumer.log', encoding='utf-8')
file_handler.setLevel(logging.INFO)

logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

logger.addHandler(file_handler)


def send_json_message(topic, message, key=None):
    """Kafka 토픽에 JSON 메시지를 전송"""
    key = key.encode('utf-8') if key else None
    producer.send(topic, key=key, value=message)
    producer.flush()


def start_consumer(consumer_id):

    topic_name = os.getenv('PADS_PRODUCT_TOPIC')
    log_topic_name = os.getenv('PADS_LOG_TOPIC')
    bootstrap_servers = [os.getenv('KAFKA_BOOTSTRAP_SERVERS')]

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

        logger.info(f"Consumer {consumer_id} - 받은 메시지: {data}")
        # TO_DO : 데이터 처리 로직
        if 'prd_id' not in data:
            logger.error(f"Consumer {consumer_id} - KeyError: {data}")
            # TO_DO : 예외시 처리 로직
            
            continue

        # Log Topic에 전송
        json_message = {
            "prd_id": data["prd_id"],
            "report": {
                "A001": randint(0, 100),
                "A002": randint(0, 100),
                "A003": randint(0, 100),
                "A004": randint(0, 100)
            },
            "status": "received"
        }

        send_json_message(log_topic_name, json_message)
        logger.info(f"Consumer {consumer_id} - JSON 메시지 전송 완료: {json.dumps(json_message, ensure_ascii=False)}")


if __name__ == '__main__':
    consumer_processes = []

    available_cpu_cores = os.cpu_count()

    for i in range(3):
        if i < available_cpu_cores:
            process = multiprocessing.Process(target=start_consumer, args=(i+1,))
            consumer_processes.append(process)
            process.start()

    for process in consumer_processes:
        process.join()
