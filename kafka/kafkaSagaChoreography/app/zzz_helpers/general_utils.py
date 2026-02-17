import json
from datetime import datetime, timezone
from confluent_kafka import TopicPartition

def get_current_timestamp():
    return datetime.now(timezone.utc).isoformat()

# publish logic
def delivery_report(err, msg):
    if err:
        print(f"Delivery failed: {err}")
    else:
        print(f"Delivery success: {msg.value().decode('utf-8')}")
        print(f"Delivery success: {msg.topic()}: partition {msg.partition()} at offset {msg.offset()}")
        
def publish(producer, key, value, topic, callback=delivery_report):
    producer.produce(
        topic=topic, 
        key=key,
        value=value,
        callback=callback
    )
    producer.flush()
    
def commit_transaction_ignore_event(producer, consumer, event, msg):
    print(f"Not interested: {event}")
                
    producer.send_offsets_to_transaction(
        [TopicPartition(msg.topic(), msg.partition(), msg.offset() + 1)],
        consumer.consumer_group_metadata()
    )

    producer.commit_transaction()

def commit_transaction(producer, consumer, key, event, msg, topic):
    encoded_event = json.dumps(event).encode("utf-8")

    # Produce next saga event
    publish(producer, key, encoded_event, topic)

    # Send consumer offset to transaction
    producer.send_offsets_to_transaction(
        [TopicPartition(msg.topic(), msg.partition(), msg.offset() + 1)],
        consumer.consumer_group_metadata()
    )

    # Commit transaction (atomic write + offset commit)
    producer.commit_transaction()