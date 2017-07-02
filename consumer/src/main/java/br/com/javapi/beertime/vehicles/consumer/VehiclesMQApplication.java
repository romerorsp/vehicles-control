package br.com.javapi.beertime.vehicles.consumer;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

/**
 * @author Rômero Ricardo
 *
 */
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages="br.com.javapi.beertime.vehicles")
public class VehiclesMQApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiclesMQApplication.class);

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
        LOGGER.info("Vehicles MQ Consumer starting...");
        SpringApplication application = new SpringApplicationBuilder(VehiclesMQApplication.class).web(false).build();
        application.setAdditionalProfiles(args);
        application.run();
    }
}
