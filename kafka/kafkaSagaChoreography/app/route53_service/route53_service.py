import json
from confluent_kafka import Consumer, KafkaError, Producer
from app.zzz_helpers.config import consumer_config_route53, producer_config_route53, AUTHENTICATE_DOMAIN_TOPIC
from app.zzz_helpers.general_utils import commit_transaction, commit_transaction_ignore_event
from app.route53_service.route53_service_utils import validate_event_authenticate_domain_route53, authenticate_domain_route53, validate_event_authenticate_compensate_domain_route53, authenticate_compensate_domain_route53, validate_event_authenticate_domain_route53_should_fail, authenticate_domain_route53_should_fail

current_service = "Route53"

# ---- Consumer ----
consumer = Consumer(consumer_config_route53)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

# ---- Transactional Producer ----
producer = Producer(producer_config_route53)
producer.init_transactions()

print("Route53Service (transactional) consumer listening started...")

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

            if validate_event_authenticate_domain_route53(event):
                
                # Check if this is where the service fails
                if validate_event_authenticate_domain_route53_should_fail(event):
                    event = authenticate_domain_route53_should_fail(event)
                else:
                    # Authenticate domain
                    event = authenticate_domain_route53(event)
                
            elif validate_event_authenticate_compensate_domain_route53(event):
                # Delete domain to compensate
                authenticate_compensate_domain_route53(event)
                
            else: 
                # Not interested in this event
                commit_transaction_ignore_event(producer, consumer, event, msg)
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
