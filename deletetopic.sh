read -p "Enter number topic you want to delete: "  n
for ((i=0;i<n;i++))
do
   read -p "Enter topic name: "  topic
   kafka_2.12-2.2.0/bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic $topic
   echo "Topic $topic marked to be deleted!"
done
kafka_2.12-2.2.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092

