#!/bin/bash
xterm -e kafka_2.12-2.2.0/bin/zookeeper-server-start.sh kafka_2.12-2.2.0/config/zookeeper.properties & 
(sleep 1; xterm -e kafka_2.12-2.2.0/bin/kafka-server-start.sh kafka_2.12-2.2.0/config/server.properties) & 
(sleep 5; echo List of topics; 
sleep 5; kafka_2.12-2.2.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092;
sleep 5; read -p "Enter number of new topic: "  n;
for ((i=0;i<n;i++))
do
   read -p "Enter new topic name: "  topic
   kafka_2.12-2.2.0/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $topic
   echo "Topic $topic created!"
done;
kafka_2.12-2.2.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092)

