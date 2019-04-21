import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class KafkaTwitterProducer {
    public static void main(String[] args) throws Exception {
        final LinkedBlockingQueue<Status> queue = new LinkedBlockingQueue<Status>(1000);

        if (args.length < 4) {
            System.out.println(
                    "Usage: KafkaTwitterProducer <twitter-consumer-key> <twitter-consumer-secret> <twitter-access-token> <twitter-access-token-secret> <topic-name> <twitter-search-keywords>");
            return;
        }

        String consumerKey = args[0].toString();
        String consumerSecret = args[1].toString();
        String accessToken = args[2].toString();
        String accessTokenSecret = args[3].toString();
        String topicName = args[4].toString();
        String[] arguments = args.clone();
        String[] keyWords = Arrays.copyOfRange(arguments, 5, arguments.length);

        // Set twitter oAuth tokens in the configuration
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);

        // Create twitterstream using the configuration
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                queue.offer(status);
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + "upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);

        // Filter keywords
        FilterQuery query = new FilterQuery().track(keyWords);
        twitterStream.filter(query);

        // Add Kafka producer config settings
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        int i = 0;
        int j = 0;

        // poll for new tweets in the queue. If new tweets are added, send them
        // to the topic
        while (true) {
            Status ret = queue.poll();

            if (ret == null) {
                Thread.sleep(100);
                // i++;
            } else {
                for (HashtagEntity hashtage : ret.getHashtagEntities()) {
                    System.out.println("Tweet:" + ret);
                    System.out.println("Hashtag: " + hashtage.getText());
                    // producer.send(new ProducerRecord<String, String>(
                    // topicName, Integer.toString(j++), hashtage.getText()));
                    producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(j++), ret.getText()));
                }
            }
        }
    }
}