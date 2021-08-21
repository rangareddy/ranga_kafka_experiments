# Kafka Secure Producer and Consumer


## Keytab location

```sh
/etc/security/keytabs/kafka.service.keytab
```

## JAAS configration

```sh
/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf
```

## Creating a Kafka topic

```sh
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh \
--create \
--zookeeper node1.hadoop.com:2181,node2.hadoop.com \
--replication-factor 1 \
--partitions 2 \
--topic test
```

## Listing Kafka topics

```sh
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh --list --zookeeper node1.hadoop.com:2181,node2.hadoop.com
```

## Specify the path to the JAAS configuration file as one of your JVM parameters.

```sh
export KAFKA_CLIENT_KERBEROS_PARAMS="-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf"
```

## Run kinit, specifying the Kafka service keytab. For example:

```sh
kinit -k -t /etc/security/keytabs/kafka.service.keytab kafka/node1.hadoop.com@HADOOP.COM
```

## Kafka Producer - Produce messages

```sh
/usr/hdp/current/kafka-broker/bin/kafka-console-producer.sh \
  --broker-list node1.hadoop.com:6667,node2.hadoop.com:6667 \
  --topic test \
  --producer-property security.protocol=SASL_PLAINTEXT
```

## Kafka Consumer - Consumer messages

```sh
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh \
  --bootstrap-server node1.hadoop.com:6667,node2.hadoop.com:6667 \
  --topic test \
  --from-beginning \
  --consumer-property security.protocol=SASL_PLAINTEXT
```

## Java Producer API

```sh
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar \
-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf \
-Djava.security.krb5.conf=/etc/krb5.conf \
-Djavax.security.auth.useSubjectCredsOnly=false \
com.ranga.kafka.security.KafkaSecureProducerDemo node1.hadoop.com:6667,node2.hadoop.com:6667 test
```

## Java Consumer API

```sh
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar \
-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf \
-Djava.security.krb5.conf=/etc/krb5.conf \
-Djavax.security.auth.useSubjectCredsOnly=false \
com.ranga.kafka.security.KafkaSecureConsumerDemo node1.hadoop.com:6667,node2.hadoop.com:6667 test test_group_id
```
