package com.ranga.kafka;

/* rangareddy.avula created on 18/05/20 */

import com.ranga.kafka.security.KafkaSecureProducerDemo;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerDemo {

    private static Logger LOG = LoggerFactory.getLogger(KafkaProducerDemo.class);

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage:   KafkaProducerDemo <bootstrap_server> <kafka_topic>");
            System.err.println("Example: KafkaProducerDemo localhost:9092 test_topic");
            System.exit(0);
        }

        String bootstrapServer = args[0];
        String topicName = args[1];

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = null;
        try {
            kafkaProducer = new KafkaProducer<>(props);
            for (int i = 0; i < 100; i++) {
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, Integer.toString(i), "test message - " + i);
                kafkaProducer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                        System.out.println(String.format("Sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()));
                    }
                });
            }
            System.out.println("Message sent to topic <" + topicName + "> successfully");
            LOG.info("Message sent to topic <" + topicName + "> successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (kafkaProducer != null) {
                kafkaProducer.close();
                System.out.println("Kafka Producer closed successfully");
            }
        }
    }
}