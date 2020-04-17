# Esper Kafka Example

*I've searched for an Esper example. I haven't really found one, so I want to share this with you.*

This project provides an Esper example you could read and write from/to [Apache Kafka](https://kafka.apache.org/) using [EsperIO Kafka Adapter](http://esper.espertech.com/release-8.4.0/reference-esperio/html/adapter_kafka.html). You could also send EPL statements to an REST API, have a look at the [Swagger UI](http://localhost:8080/swagger-ui.html).

There is also another branch ([plain-esper](https://github.com/lwluc/Esper-Kafka-Example/tree/plain-esper)) you could check out, which is simply Esper with Kafka without Spring Boot and a REST API ...

## Getting Started  
Run the following commands to simply test it. You could also open your IDE to start the Project (so you do not need the commands to start this application).

```shell script
$ # Download Kafka and unpack it
$ wget http://apache.lauf-forum.at/kafka/2.5.0/kafka_2.12-2.5.0.tgz && tar -xzf kafka_2.12-2.5.0.tgz
$ # Navigate into your kafka download folder to execute the following commands
$ cd kafka_2.12-2.5.0

$ # Start the Server  https://kafka.apache.org/quickstart#quickstart_startserver
$ bin/zookeeper-server-start.sh config/zookeeper.properties # Start Zookeper
$ bin/kafka-server-start.sh config/server.properties # and kafka

$ # Start the Esper example applicaiton
$ mvn install
$ mvn spring-boot:run

$ # Produce some input to be consumed from esper
$ bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic input

$ # Have a look at the output of esper
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic wordLength --from-beginning
```

*Note:* I am not using Spring Annotations for the Esper code.