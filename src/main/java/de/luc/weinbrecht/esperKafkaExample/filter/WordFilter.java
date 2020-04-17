package de.luc.weinbrecht.esperKafkaExample.filter;

import com.espertech.esper.common.client.EventBean;

public class WordFilter {
    public static int getLengthOfString(EventBean eventBean) {
        return eventBean.getUnderlying().toString().length();
    }

    public static String toLower(EventBean eventBean) {
        return eventBean.getUnderlying().toString().toLowerCase();
    }
}
