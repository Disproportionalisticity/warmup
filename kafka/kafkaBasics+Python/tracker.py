import json

from confluent_kafka import Consumer, KafkaError


def on_error(err):
    print("KAFKA ERROR:", err)

consumer_config = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "order-tracker",
    "auto.offset.reset": "earliest",
    
    # # diagnostics
    # "error_cb": on_error,
    # "debug": "broker,topic,metadata,consumer,fetch,protocol",
}

consumer = Consumer(consumer_config)

consumer.subscribe(["orders"])

print("Consumer is subscribed to orders topic and is running...")

while True:
    msg = consumer.poll(1.0)
    if msg is None:
        continue
    if msg.error():
        if msg.error().code() == KafkaError._PARTITION_EOF:
            continue
        print("MSG ERROR:", msg.error())
        continue
    
    value = msg.value().decode("utf-8")
    order = json.loads(value)
    print(f"Received order: {order['quantity']} x {order['item']} from {order['user']}")