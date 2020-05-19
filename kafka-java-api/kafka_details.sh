
# Creating a Kafka topic
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh \
  --create --zookeeper c1543-node2.coelab.cloudera.com:2181,c1543-node3.coelab.cloudera.com:2181,c1543-node4.coelab.cloudera.com:2181 \
  --replication-factor 1 \
  --partitions 2 \
  --topic test

# Listing Kafka topics
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh \
  --list --zookeeper c1543-node2.coelab.cloudera.com:2181,c1543-node3.coelab.cloudera.com:2181,c1543-node4.coelab.cloudera.com:2181

# Kafka Producer - Produce messages
/usr/hdp/current/kafka-broker/bin/kafka-console-producer.sh \
  --broker-list c1543-node2.coelab.cloudera.com:6667 \
  --topic test

# Kafka Consumer - Consumer messages
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh \
  --bootstrap-server c1543-node2.coelab.cloudera.com:6667 \
  --topic test \
  --from-beginning

# Java Producer API
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar com.ranga.kafka.KafkaProducerDemo c1543-node2.coelab.cloudera.com:6667 test

# Java Consumer API
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar com.ranga.kafka.KafkaConsumerDemo c1543-node2.coelab.cloudera.com:6667 test test_group_id