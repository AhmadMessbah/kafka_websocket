import os
import json
from kafka import KafkaConsumer, KafkaProducer
from cryptography.fernet import Fernet

REQUEST_TOPIC = "check-requests"
RESPONSE_TOPIC = "check-responses"
KAFKA_BOOTSTRAP_SERVERS = os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'kafka:9092')
ENCRYPTION_KEY = os.getenv('ENCRYPTION_KEY', 'ThisIsASecretKey1234567890abcdef')

def encrypt(message):
    f = Fernet(ENCRYPTION_KEY.encode())
    return f.encrypt(message.encode()).decode()

def decrypt(encrypted_message):
    f = Fernet(ENCRYPTION_KEY.encode())
    return f.decrypt(encrypted_message.encode()).decode()

def produce_response(check_id, status, message):
    producer = KafkaProducer(bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS)
    response = {'checkId': check_id, 'status': status, 'message': message}
    encrypted_response = encrypt(json.dumps(response))
    producer.produce(RESPONSE_TOPIC, key=check_id.encode(), value=encrypted_response.encode())
    producer.flush()

def consume_requests():
    consumer = KafkaConsumer(
        REQUEST_TOPIC,
        bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS,
        auto_offset_reset='earliest',
        group_id='check-group'
    )
    for message in consumer:
        encrypted_request = message.value.decode()
        request = json.loads(decrypt(encrypted_request))
        check_id = request['checkId']
        amount = float(request['amount'])
        status = 'APPROVED' if amount <= 10000 else 'REJECTED'
        produce_response(check_id, status, 'Processed by bank')
        print(f"Processed check {check_id}: {status}")

if __name__ == "__main__":
    print(f"Listening on topic {REQUEST_TOPIC}...")
    consume_requests()