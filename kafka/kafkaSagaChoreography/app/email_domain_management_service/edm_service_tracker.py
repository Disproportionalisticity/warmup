import json
from confluent_kafka import Consumer, KafkaError
from app.zzz_helpers.config import consumer_config_edm_tracker, AUTHENTICATE_DOMAIN_TOPIC
from app.email_domain_management_service.edm_service_tracker_util import process_event
from pymongo import MongoClient

mongo_client = MongoClient("localhost", port=27017)
domain_auth_db = mongo_client["domain-auth-db"]
collection_domain_db = domain_auth_db["domain-authentication"]

# ---- Consumer ----
consumer = Consumer(consumer_config_edm_tracker)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

print("EDM Tracker consumer listening started...")

try:
    while True:
        msg = consumer.poll(1.0)

        if msg is None:
            continue

        if msg.error():
            if msg.error().code() == KafkaError._PARTITION_EOF:
                continue
            print("MSG ERROR:", msg.error())
            continue

        event = json.loads(msg.value().decode("utf-8"))
        
        process_event(event, collection_domain_db)

        # print(f"Event received: {event}")
        
        consumer.commit(msg, asynchronous=False)
        
except KeyboardInterrupt:
    pass

finally:
    consumer.close()
