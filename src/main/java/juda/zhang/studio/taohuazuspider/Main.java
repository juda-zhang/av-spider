package juda.zhang.studio.taohuazuspider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 晨辉 on 2017/6/5.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        LOGGER.info("taohuazu-spider has been started successfully!");
    }
}
