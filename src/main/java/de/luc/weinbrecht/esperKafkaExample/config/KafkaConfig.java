package de.luc.weinbrecht.esperKafkaExample.config;

public class KafkaConfig {
    public final static String[] INPUT_TOPICS = {"input"};
    public final static String[] OUTPUT_TOPICS = {"wordLength"};
    public final static String BOOTSTRAP_ADDRESS = "localhost:9092";
}
