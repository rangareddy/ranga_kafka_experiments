package com.ranga.kafka.security;/* rangareddy.avula created on 18/05/20 */

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class KafkaSecureConsumerDemo {

    private static Logger LOG = LoggerFactory.getLogger(KafkaSecureConsumerDemo.class);

    public static void main(String[] args) {

        if (args.length < 3) {
            System.err.println("Usage:   KafkaSecureConsumerDemo <bootstrap_server> <kafka_topic> <group_id>");
            System.out.println("Example: KafkaSecureConsumerDemo localhost:9092 test_topic test_group_id");
            LOG.error("Usage:   KafkaSecureConsumerDemo <bootstrap_server> <kafka_topic> <group_id>");
            LOG.info("Example: KafkaSecureConsumerDemo localhost:9092 test_topic test_group_id");
            System.exit(0);
        }

        String bootstrapServer = args[0];
        String topicName = args[1];
        String groupId = args[2];

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);

        // specify the protocol for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        List<String> topicList = Arrays.asList(topicName.split(","));
        Consumer<String, String> kafkaConsumer = null;

        try {
            kafkaConsumer = new KafkaConsumer<>(props);
            kafkaConsumer.subscribe(topicList);
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received Message topic =%s, partition =%s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
                kafkaConsumer.commitSync();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (kafkaConsumer != null) {
                kafkaConsumer.close();
            }
        }
    }
}
