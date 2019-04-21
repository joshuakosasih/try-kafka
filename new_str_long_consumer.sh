read -p "Enter broker port (default 9092): "  port
read -p "Enter consumer topic: " consumer
kafka_2.12-2.2.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:$port \
    --topic $consumer \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

