import json
from confluent_kafka import Consumer, KafkaError, Producer
from app.zzz_helpers.config import consumer_config_edm, producer_config_edm, AUTHENTICATE_DOMAIN_TOPIC, NOTIFICATION_SERVICE_TOPIC
from app.zzz_helpers.general_utils import commit_transaction, commit_transaction_ignore_event
from app.email_domain_management_service.edm_service_utils import validate_event_authenticate_domain_success, authenticate_domain_success, validate_event_authenticate_domain_failed, authenticate_domain_failed


# ---- Consumer ----
consumer = Consumer(consumer_config_edm)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

# ---- Transactional Producer ----
producer = Producer(producer_config_edm)
producer.init_transactions()

print("EmailDomainManagementService (transactional) consumer listening started...")

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

            if validate_event_authenticate_domain_success(event):
                # authenticate domain
                event = authenticate_domain_success(event)
                
            elif validate_event_authenticate_domain_failed(event):
                # delete domain to compensate
                event = authenticate_domain_failed(event)
                
            else: 
                # not interested in this event
                commit_transaction_ignore_event(producer, consumer, event, msg)
                continue

            key = event["sagaId"]

            commit_transaction(producer, consumer, key, event, msg, NOTIFICATION_SERVICE_TOPIC)
            
        except Exception as e:
            print("Transaction failed:", e)
            producer.abort_transaction()


except KeyboardInterrupt:
    pass

finally:
    consumer.close()
