from confluent_kafka import Consumer

# --- Consumer configuration ---
consumer_config = {
    "bootstrap.servers": "localhost:9092",
    "group.id": "tx_monitor_group",
    "auto.offset.reset": "earliest",
    "isolation.level": "read_committed"  # only see committed transactions
}

consumer = Consumer(consumer_config)
consumer.subscribe(["__transaction_state"])

transactions = {}

try:
    while True:
        msg = consumer.poll(1.0)
        if msg is None:
            break
        if msg.error():
            continue

        tx_id = msg.key().decode() if msg.key() else "unknown"

        # Track topics affected (cannot decode value fully, approximate by counting messages)
        if tx_id not in transactions:
            transactions[tx_id] = {"count": 0}

        transactions[tx_id]["count"] += 1

finally:
    consumer.close()

# Print summary
print(f"{'Transaction ID':30} {'Committed Messages Count':25}")
print("="*60)
for tx_id, info in transactions.items():
    print(f"{tx_id:30} {info['count']:25}")
