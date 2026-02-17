from confluent_kafka import Consumer
from app.zzz_helpers.config import consumer_config_topic_notification_service, NOTIFICATION_SERVICE_TOPIC, LOG_FILE_NAMES

consumer = Consumer(consumer_config_topic_notification_service)
consumer.subscribe([NOTIFICATION_SERVICE_TOPIC])

print("Listening in real time... Ctrl+C to stop.")

try:
    with open(LOG_FILE_NAMES[NOTIFICATION_SERVICE_TOPIC], "a", encoding="utf-8") as f:
        while True:
            msg = consumer.poll(1.0)

            if msg is None:
                continue
            if msg.error():
                print("Error:", msg.error())
                continue

            value = msg.value().decode("utf-8")
            f.write(value + "\n")
            f.flush()  # force immediate write to disk

            consumer.commit(message=msg)
            print("Logged:", value)

except KeyboardInterrupt:
    print("Stopped.")

finally:
    consumer.close()
