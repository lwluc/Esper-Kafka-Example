package de.luc.weinbrecht.esperKafkaExample.config;

import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esperio.kafka.*;
import de.luc.weinbrecht.esperKafkaExample.kafka.KafkaEventConsumer;
import de.luc.weinbrecht.esperKafkaExample.kafka.KafkaEventProducer;
import de.luc.weinbrecht.esperKafkaExample.kafka.KafkaInputProcessor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;

import static de.luc.weinbrecht.esperKafkaExample.config.KafkaConfig.BOOTSTRAP_ADDRESS;

public class EsperConfig {

    private static final Logger log = LoggerFactory.getLogger(EsperConfig.class);

    public final static String RUNTIME_URI = "EventMatcher";
    public final static String DEPLOYMENT_ID = "eventMatcher";

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.configure("esper.cfg.xml");
        configuration.getCommon().addImport(KafkaOutputDefault.class);
        configuration.getCommon().addEventType(String.class);

        configuration.getRuntime().addPluginLoader("KafkaInput", EsperIOKafkaInputAdapterPlugin.class.getName(), getConsumerProps(), null);
         configuration.getRuntime().addPluginLoader("KafkaOutput", EsperIOKafkaOutputAdapterPlugin.class.getName(), getProducerProps(), null);

        log.info("Configuration is loaded ({})", configuration.toString());
        return configuration;
    }

    public static Properties getConsumerProps() {
        Properties props = new Properties();

        // Kafka Consumer Properties
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString().toLowerCase());

        // EsperIO Kafka Input Adapter Properties
        props.put(EsperIOKafkaConfig.INPUT_SUBSCRIBER_CONFIG, KafkaEventConsumer.class.getName());
        props.put(EsperIOKafkaConfig.INPUT_PROCESSOR_CONFIG, KafkaInputProcessor.class.getName());
        props.put(EsperIOKafkaConfig.INPUT_TIMESTAMPEXTRACTOR_CONFIG, EsperIOKafkaInputTimestampExtractorConsumerRecord.class.getName());

        return props;
    }

    public static Properties getProducerProps() {
        Properties props = new Properties();

        // Kafka Producer Properties
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // EsperIO Kafka Output Adapter Properties
        props.put(EsperIOKafkaConfig.OUTPUT_FLOWCONTROLLER_CONFIG, KafkaEventProducer.class.getName());

        return props;
    }
}
