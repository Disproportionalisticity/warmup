from confluent_kafka import Consumer
from app.zzz_helpers.config import consumer_config_topic_domain_auth, AUTHENTICATE_DOMAIN_TOPIC, LOG_FILE_NAMES

consumer = Consumer(consumer_config_topic_domain_auth)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

print("Listening in real time... Ctrl+C to stop.")

try:
    with open(LOG_FILE_NAMES[AUTHENTICATE_DOMAIN_TOPIC], "a", encoding="utf-8") as f:
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
