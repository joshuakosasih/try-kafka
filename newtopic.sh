read -p "Enter number of new topic: "  n
for ((i=0;i<n;i++))
do
   read -p "Enter new topic name: "  topic
   kafka_2.12-2.2.0/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $topic
   echo "Topic $topic created!"
done
kafka_2.12-2.2.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
