package me.k11i.logback.stackdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackExample.class);

    public static void main(String[] args) {
        LOGGER.info("foo {} baz", "bar");

        try {
            String s = null;
            s.toString();

        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
