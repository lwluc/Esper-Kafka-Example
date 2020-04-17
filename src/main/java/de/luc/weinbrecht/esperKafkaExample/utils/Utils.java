package de.luc.weinbrecht.esperKafkaExample.utils;

import com.espertech.esper.common.client.EventBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static void printBanner() {
        InputStream inputStream = Utils.class.getResourceAsStream("/banner.txt");

        // https://stackoverflow.com/a/5762502
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";

        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                if (line.contains("${Ansi.CYAN}")) {
                    System.out.println(ANSI_CYAN + line.replace("${Ansi.CYAN}", "") + ANSI_RESET);
                } else if (line.contains("${Ansi.RED}")) {
                    System.out.println(ANSI_RED + line.replace("${Ansi.RED}", "") + ANSI_RESET);
                } else {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            log.error("Could read Banner", e);
        }
        System.out.println("\n\n"); // Some margin bellow
    }

    public static String buildOutputString(EventBean event) {
        return event.get("word").toString() + " (" + event.get("len") + ")";
    }
}
