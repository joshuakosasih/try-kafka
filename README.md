# try-kafka

I've created this repo in my attempt to try and understand bits of Apache Kafka.
I've also created several short scripts that I found to be useful in trying and testing the platform:
- run.sh (to quickstart kafka server with basic configuration)
- newtopic.sh (to add new topic(s) to the server)
- newproducer.sh (to create a new basic producer)
- newconsumer.sh (to create a new basic consumer)
- etc

### Kafka
Apache Kafka® is a distributed streaming platform. It is used for building real-time data pipelines and streaming apps. It is horizontally scalable, fault-tolerant, wicked fast, and runs in production in thousands of companies.

See [Kafka official website](https://kafka.apache.org/) for more information.

### Tutorial
1. Follow the [Kafka quickstart](https://kafka.apache.org/quickstart) to get the hang of it.
2. Continue with [Kafka Stream quickstart](https://kafka.apache.org/22/documentation/streams/quickstart) to learn about processing streams of data from Kafka in real-time
3. Create your own toy project. In this github, I created [my own Kafka project](https://github.com/joshuakosasih/try-kafka/tree/master/twitterkafka) that streams data from Twitter API and proccesses it.

### Twitter Kafka
