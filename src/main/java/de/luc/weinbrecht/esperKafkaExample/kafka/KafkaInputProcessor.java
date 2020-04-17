package de.luc.weinbrecht.esperKafkaExample.kafka;

import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esperio.kafka.EsperIOKafkaInputProcessor;
import com.espertech.esperio.kafka.EsperIOKafkaInputProcessorContext;
import de.luc.weinbrecht.esperKafkaExample.config.EsperConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaInputProcessor implements EsperIOKafkaInputProcessor {

    private static final Logger log = LoggerFactory.getLogger(KafkaInputProcessor.class);

    private EPRuntime runtime;

    @Override
    public void init(EsperIOKafkaInputProcessorContext context) {
        log.info("Initializing input processor");
        this.runtime = context.getRuntime();
    }

    @Override
    public void process(ConsumerRecords<Object, Object> records) {
        for (ConsumerRecord record : records) {

            if (record.value() != null) {
                log.info("Processing record with value {} ({})", record.value().toString(), record.toString());

                if (log.isDebugEnabled()) {
                    log.debug("Sending event {}", record.value().toString());
                }

                try {
                    log.info("Event Type Names: {}", EsperConfig.getConfiguration().getCommon().getEventTypeNames());
                    runtime.getEventService().sendEventBean(record.value().toString(), "String");
                } catch (Exception e) {
                    log.error("Could not send Event", e);
                }
            }
        }
    }

    public void close() {}
}

