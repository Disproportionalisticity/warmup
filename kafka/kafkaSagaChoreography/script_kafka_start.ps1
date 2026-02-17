docker-compose up -d

Start-Sleep -Seconds 5

docker exec -it kafka kafka-topics --create --topic domain-auth --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
docker exec -it kafka kafka-topics --create --topic notification-service --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
docker exec -it kafka kafka-topics --create --topic dead-letter-queue-service --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092