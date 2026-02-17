import json

from confluent_kafka import Consumer, KafkaError, Producer, TopicPartition

from app.sendgrid_service.sendgrid_service_utils import (
    authenticate_compensate_domain_sendgrid,
    authenticate_domain_sendgrid,
    authenticate_domain_sendgrid_should_fail,
    validate_event_authenticate_compensate_domain_sendgrid,
    validate_event_authenticate_domain_sendgrid,
    validate_event_authenticate_domain_sendgrid_should_fail,
    validate_event_authenticate_compensate_domain_sendgrid_should_fail, 
    authenticate_compensate_domain_sendgrid_should_fail,
    validate_event_authenticate_domain_sendgrid_fail_transaction
)
from app.zzz_helpers.config import (
    AUTHENTICATE_DOMAIN_TOPIC,
    DEAD_LETTER_QUEUE_TOPIC,
    consumer_config_sendgrid,
    producer_config_sendgrid,
)
from app.zzz_helpers.general_utils import (
    commit_transaction,
    commit_transaction_ignore_event,
    publish,
)
# ---- Consumer ----
consumer = Consumer(consumer_config_sendgrid)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

# ---- Transactional Producer ----
producer = Producer(producer_config_sendgrid)
producer.init_transactions()

print("SendGridService (transactional) consumer listening started...")

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

        try:
            # Start transaction
            producer.begin_transaction()
            if validate_event_authenticate_domain_sendgrid(event):
                # Check if this is where the service fails
                if validate_event_authenticate_domain_sendgrid_should_fail(event):
                    event = authenticate_domain_sendgrid_should_fail(event)
                else:
                    # Authenticate domain
                    event = authenticate_domain_sendgrid(event)
                
            elif validate_event_authenticate_compensate_domain_sendgrid(event):
                # Check if this is where the compensation should fail
                if validate_event_authenticate_compensate_domain_sendgrid_should_fail(event):
                    # if we fail here- we need to send the event to Dead Letter queue
                    event = authenticate_compensate_domain_sendgrid_should_fail(event)
                    
                    key = event["sagaId"]
                    commit_transaction(producer, consumer, key, event, msg, DEAD_LETTER_QUEUE_TOPIC)
                    continue
                else: 
                    # Delete domain to compensate
                    authenticate_compensate_domain_sendgrid(event)
                
            else: 
                # Not interested in this event
                commit_transaction_ignore_event(producer, consumer, event, msg)
                continue

            # A special case in which we need to fail the transaction in between publishing and committing offset
            if validate_event_authenticate_domain_sendgrid_fail_transaction(event):
                encoded_event = json.dumps(event).encode("utf-8")

                # Produce next saga event
                key = event["sagaId"]
                
                publish(producer, key, encoded_event, AUTHENTICATE_DOMAIN_TOPIC)
                
                # Send consumer offset to transaction
                producer.send_offsets_to_transaction(
                    [TopicPartition(msg.topic(), msg.partition(), msg.offset() + 1)],
                    consumer.consumer_group_metadata()
                )

                # HERE is where we abort transaction to simulate that it failed in between. Thus, the publishing will not be shown
                producer.abort_transaction()
                print("Transaction aborted")
                
                # In order for Kafka to retry this message, we have to add this line
                # consumer.seek(
                #     TopicPartition(msg.topic(), msg.partition(), msg.offset())
                # )
                continue

            key = event["sagaId"]
            commit_transaction(producer, consumer, key, event, msg, AUTHENTICATE_DOMAIN_TOPIC)
        except Exception as e:
            print("Transaction failed:", e)
            producer.abort_transaction()


except KeyboardInterrupt:
    pass

finally:
    consumer.close()
