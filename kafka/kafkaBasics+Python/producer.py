import json
import uuid
from confluent_kafka import Producer

def delivery_report(err, msg):
    if err:
        print(f"Delivery failed: {err}")
    else:
        print(f"Delivery success: {msg.value().decode('utf-8')}")
        print(f"Delivery success: {msg.topic()}: partition {msg.partition()} at offset {msg.offset()}")
        
producer_config = {'bootstrap.servers': 'localhost:9092'}

producer = Producer(producer_config)

order = {
    "order_id": str(uuid.uuid4()),
    "user": "user1",
    "item": "chicken burger",
    "quantity": 2
}

value = json.dumps(order).encode("utf-8")

producer.produce(
    topic="orders", 
    value=value,
    callback=delivery_report
)
producer.flush()

'''
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092   # see list of topics
orders


docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --describe --topic orders   # information about a specific topic
Topic: orders   TopicId: DQhGxJniTMGh8PkDTEskJw PartitionCount: 1       ReplicationFactor: 1    Configs: 
        Topic: orders   Partition: 0    Leader: 1       Replicas: 1     Isr: 1  Elr:    LastKnownElr: 
        
        
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic orders --partition 0 --offset 0   # see all events
{"order_id": "e39e5ddd-ad65-4bae-816f-a095fd828b6a", "user": "user1", "item": "pepperoni pizza", "quantity": 3}
{"order_id": "99b1c903-f037-4d53-9536-0da40f6b78ed", "user": "user1", "item": "pepperoni pizza", "quantity": 3}
'''