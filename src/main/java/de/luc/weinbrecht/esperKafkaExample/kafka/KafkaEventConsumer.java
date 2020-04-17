package de.luc.weinbrecht.esperKafkaExample.kafka;

import com.espertech.esperio.kafka.EsperIOKafkaInputSubscriber;
import com.espertech.esperio.kafka.EsperIOKafkaInputSubscriberContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static de.luc.weinbrecht.esperKafkaExample.config.KafkaConfig.INPUT_TOPICS;

public class KafkaEventConsumer implements EsperIOKafkaInputSubscriber {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Override
    public void subscribe(EsperIOKafkaInputSubscriberContext context) {
        log.info("Subscribing to topics {}", INPUT_TOPICS.toString());
        context.getConsumer().subscribe(Arrays.asList(INPUT_TOPICS));
    }
}
