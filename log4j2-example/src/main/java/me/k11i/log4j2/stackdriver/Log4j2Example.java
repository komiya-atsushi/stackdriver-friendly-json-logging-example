package me.k11i.log4j2.stackdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Example {
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4j2Example.class);

    public static void main(String[] args) {
        LOGGER.info("foo {} baz", "bar");

        try {
            String s = null;
            s.toString();

        } catch (Exception e) {
            LOGGER.error("Exception {}", e);
        }
    }
}
