import json
from confluent_kafka import Consumer, KafkaError
from app.zzz_helpers.config import consumer_config_nofitication, NOTIFICATION_SERVICE_TOPIC

# ---- Consumer ----
consumer = Consumer(consumer_config_nofitication)
consumer.subscribe([NOTIFICATION_SERVICE_TOPIC])

print("NotificationService consumer listening started...")

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

        print(f"Notification request received: {event}")
        
        consumer.commit(msg, asynchronous=False)
        
except KeyboardInterrupt:
    pass

finally:
    consumer.close()
