package juda.zhang.studio.avspider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Juda.Zhang on 2017/6/5.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        LOGGER.info("spider has been started successfully!");
    }
}
