package me.k11i.springframework.logging.stackdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringAppExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAppExample.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(SpringAppExample.class);
        app.getBean(SpringAppExample.class).run();
    }

    void run() {
        LOGGER.info("foo {} baz", "bar");

        try {
            String s = null;
            s.toString();

        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
