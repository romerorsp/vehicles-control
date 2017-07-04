package br.com.javapi.beertime.vehicles.websocket;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author Rômero Ricardo
 *
 */
@EnableScheduling
@EnableWebSocket
@SpringBootApplication(scanBasePackages="br.com.javapi.beertime.vehicles")
public class WSocketApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(WSocketApplication.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            LOGGER.warn("No Spring profile configured, running with default configuration");
        } else {
            LOGGER.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

    public static void main(String[] args) {
        LOGGER.info("Vehicles websocket starting...");
        SpringApplication application = new SpringApplicationBuilder(WSocketApplication.class).web(true).build();
        application.setAdditionalProfiles(args);
        application.run();
    }
}
