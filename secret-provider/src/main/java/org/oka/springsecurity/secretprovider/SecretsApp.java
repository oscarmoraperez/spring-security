package org.oka.springsecurity.secretprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point of the whole secret-provider app.
 */
@SpringBootApplication
@EnableScheduling
public class SecretsApp {
    public static void main(String[] args) {
        SpringApplication.run(SecretsApp.class, args);
    }
}
