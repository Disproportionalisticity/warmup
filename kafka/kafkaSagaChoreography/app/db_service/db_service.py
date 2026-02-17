import json
from confluent_kafka import Consumer, KafkaError, Producer
from app.zzz_helpers.config import consumer_config_db, producer_config_db, AUTHENTICATE_DOMAIN_TOPIC
from app.zzz_helpers.general_utils import commit_transaction, commit_transaction_ignore_event
from app.db_service.db_service_utils import validate_event_authenticate_domain_db, authenticate_domain_db, validate_event_authenticate_domain_db_should_fail, authenticate_domain_db_should_fail

current_service = "DB"

# ---- Consumer ----
consumer = Consumer(consumer_config_db)
consumer.subscribe([AUTHENTICATE_DOMAIN_TOPIC])

# ---- Transactional Producer ----
producer = Producer(producer_config_db)
producer.init_transactions()

print("DBService (transactional) consumer listening started...")

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

            if validate_event_authenticate_domain_db(event):
                if validate_event_authenticate_domain_db_should_fail(event):
                    event = authenticate_domain_db_should_fail(event)
                else:
                    # authenticate domain
                    event = authenticate_domain_db(event)
                
            else: 
                # not interested in this event
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
