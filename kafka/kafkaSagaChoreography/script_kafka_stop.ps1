docker exec kafka kafka-topics --list --bootstrap-server localhost:9092 |
ForEach-Object {
    Write-Host "Deleting topic: $_"
    docker exec kafka kafka-topics --delete --topic $_ --bootstrap-server localhost:9092
}

docker-compose down -v

python .\clear_logs.py