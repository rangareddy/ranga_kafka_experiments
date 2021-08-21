
# Secure way
# ===================================================
# Keytab location = /etc/security/keytabs/kafka.service.keytab
# JAAS configration = /usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf

# Creating a Kafka topic
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh --create --zookeeper c3543-node2.coelab.cloudera.com:2181,c3543-node3.coelab.cloudera.com:2181,c3543-node4.coelab.cloudera.com:2181 --replication-factor 1 --partitions 2 --topic test

# Listing Kafka topics
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh --list --zookeeper c3543-node2.coelab.cloudera.com:2181,c3543-node3.coelab.cloudera.com:2181,c3543-node4.coelab.cloudera.com:2181

# swithc kafka user
su kafka

# Specify the path to the JAAS configuration file as one of your JVM parameters.
export KAFKA_CLIENT_KERBEROS_PARAMS="-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf"

#Run kinit, specifying the Kafka service keytab. For example:
kinit -k -t /etc/security/keytabs/kafka.service.keytab kafka/c3543-node2.coelab.cloudera.com@COELAB.CLOUDERA.COM

# Kafka Producer - Produce messages
/usr/hdp/current/kafka-broker/bin/kafka-console-producer.sh \
  --broker-list c3543-node2.coelab.cloudera.com:6667 \
  --topic test \
  --producer-property security.protocol=SASL_PLAINTEXT

# Kafka Consumer - Consumer messages
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh \
  --bootstrap-server c3543-node2.coelab.cloudera.com:6667 \
  --topic test \
  --from-beginning \
  --consumer-property security.protocol=SASL_PLAINTEXT

# Java Producer API
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar \
-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf \
-Djava.security.krb5.conf=/etc/krb5.conf \
-Djavax.security.auth.useSubjectCredsOnly=false \
com.ranga.kafka.security.KafkaSecureProducerDemo c3543-node2.coelab.cloudera.com:6667 test

# Java Consumer API
java -cp kafka-java-api-1.0.0-SNAPSHOT.jar \
-Djava.security.auth.login.config=/usr/hdp/current/kafka-broker/config/kafka_client_jaas.conf \
-Djava.security.krb5.conf=/etc/krb5.conf \
-Djavax.security.auth.useSubjectCredsOnly=false \
com.ranga.kafka.security.KafkaSecureConsumerDemo c3543-node2.coelab.cloudera.com:6667 test test_group_id
