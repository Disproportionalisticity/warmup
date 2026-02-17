from typing import Final

# kafka topics
AUTHENTICATE_DOMAIN_TOPIC: Final[str] = "domain-auth"
NOTIFICATION_SERVICE_TOPIC: Final[str] = "notification-service"
DEAD_LETTER_QUEUE_TOPIC: Final[str] = "dead-letter-queue-service"

# producer config
producer_config = {
    "bootstrap.servers": "localhost:9092"
}

producer_config_sendgrid = {
    "bootstrap.servers": "localhost:9092",
    "transactional.id": "sendgrid-service-tx-1"
}

producer_config_route53 = {
    "bootstrap.servers": "localhost:9092",
    "transactional.id": "route53-service-tx-1"
}

producer_config_db = {
    "bootstrap.servers": "localhost:9092",
    "transactional.id": "db-service-tx-1"
}

producer_config_edm = {
    "bootstrap.servers": "localhost:9092",
    "transactional.id": "edm-service-tx-1"
}

# consumers config
def on_error(err):
    print("KAFKA ERROR:", err)
    
consumer_config_sendgrid = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "sendgrid_domain_authenticate_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}
    
consumer_config_route53 = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "route53_domain_authenticate_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}
    
consumer_config_db = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "db_domain_authenticate_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}
    
consumer_config_edm = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "edm_domain_authenticate_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}

consumer_config_edm_tracker= {
    "bootstrap.servers": "localhost:9092",
    "group.id": "edm_tracker_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}

consumer_config_nofitication = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "notification_service_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}

consumer_config_dead_letter_queue = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "dead_letter_queue_service_consumer_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,   # manual commit
    # "error_cb": on_error,
}

# consumer config for logging
consumer_config_topic_domain_auth = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "domain_auth_logger",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,
}

consumer_config_topic_notification_service = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "notification_service_logger",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,
}

consumer_config_topic_dead_letter_queue_service = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "dead_letter_queue_service_logger",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed",
    "enable.auto.commit": False,
}

# log files
LOG_FILE_NAMES: Final[dict[str, str]] = {
    AUTHENTICATE_DOMAIN_TOPIC: "app/zzz_logs/topic_domain_auth.log",
    NOTIFICATION_SERVICE_TOPIC: "app/zzz_logs/topic_notification_service.log",
    DEAD_LETTER_QUEUE_TOPIC: "app/zzz_logs/topic_dead_letter_queue.log"
}