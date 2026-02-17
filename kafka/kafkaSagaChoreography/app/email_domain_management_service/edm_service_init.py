import json
import uuid
from confluent_kafka import Producer
from app.zzz_helpers.general_utils import get_current_timestamp, publish
from app.zzz_helpers.config import producer_config, AUTHENTICATE_DOMAIN_TOPIC

def start_auth_flow(payloadKey):
        
    producer = Producer(producer_config)
    
    with open("app/possible_payloads.json") as f:
        data = json.load(f)
        
    authDomainRequest = data[payloadKey]
    
    authDomainRequest["sagaId"] = str(uuid.uuid4())
    authDomainRequest["timestamp"] = get_current_timestamp()

    authDomainRequestJson = json.dumps(authDomainRequest).encode("utf-8")

    key = authDomainRequest["sagaId"]

    publish(producer, key, authDomainRequestJson, AUTHENTICATE_DOMAIN_TOPIC)