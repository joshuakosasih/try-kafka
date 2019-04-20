#!/bin/bash
xterm -e kafka_2.12-2.2.0/bin/zookeeper-server-start.sh kafka_2.12-2.2.0/config/zookeeper.properties & 
(sleep 1; xterm -e kafka_2.12-2.2.0/bin/kafka-server-start.sh kafka_2.12-2.2.0/config/server.properties) & 
(sleep 5; echo List of topics; 
sleep 5; kafka_2.12-2.2.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092)

