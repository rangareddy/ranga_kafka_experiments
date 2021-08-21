# Kafka Producer and Consumer

## Creating a Kafka topic

```sh
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh \
  --create --zookeeper node1.hadoop.com:2181,node2.hadoop.com \
  --replication-factor 1 \
  --partitions 2 \
  --topic test
```

## Listing Kafka topics

```sh
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh \
  --list --zookeeper node1.hadoop.com:2181,node2.hadoop.com
```

## Kafka Producer - Produce messages

```sh
/usr/hdp/current/kafka-broker/bin/kafka-console-producer.sh \
  --broker-list node1.hadoop.com:6667,node2.hadoop.com:6667 \
  --topic test
```

## Kafka Consumer - Consumer messages

```sh
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh \
  --bootstrap-server node1.hadoop.com:6667,node2.hadoop.com:6667 \
  --topic test \
  --from-beginning
```

## Java Producer API

```sh
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar com.ranga.kafka.KafkaProducerDemo node1.hadoop.com:6667,node2.hadoop.com:6667 test
```

## Java Consumer API

```sh
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar com.ranga.kafka.KafkaConsumerDemo node1.hadoop.com:6667,node2.hadoop.com:6667 test test_group_id
```
