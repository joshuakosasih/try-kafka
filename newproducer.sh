read -p "Enter broker port (default 9092): "  port
read -p "Enter producer topic: " producer
kafka_2.12-2.2.0/bin/kafka-console-producer.sh --broker-list localhost:$port --topic $producer

