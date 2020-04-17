# Esper Kafka Example

*I've searched for an Esper example. I haven't really found one, so I want to share this with you.*

This project provides an Esper example you could read and write from/to [Apache Kafka](https://kafka.apache.org/) using [EsperIO Kafka Adapter](http://esper.espertech.com/release-8.4.0/reference-esperio/html/adapter_kafka.html)

## Getting Started  
Run the following commands to simply test it. You could also open your IDE to start the Project (so you do not need the last 3 commands).

```shell script
$ # Download Kafka and unpack it
$ wget http://apache.lauf-forum.at/kafka/2.5.0/kafka_2.12-2.5.0.tgz && tar -xzf kafka_2.12-2.5.0.tgz
$ # Navigate into your kafka download folder to execute the following commands
$ cd kafka_2.12-2.5.0

$ # Start the Server  https://kafka.apache.org/quickstart#quickstart_startserver
$ bin/zookeeper-server-start.sh config/zookeeper.properties # Start Zookeper
$ bin/kafka-server-start.sh config/server.properties # and kafka

$ # Start the Esper example
$ mvn install
$ mvn clean compile assembly:single
$ java -jar target/esperKafkaExample-0.0.1-SNAPSHOT-jar-with-dependencies.jar

$ # Produce some input to be consumed from esper
$ bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic input

$ # Have a look at the output of esper
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic wordLength --from-beginning
```
