package com.eleazar.polling.pollerAvailableVehicles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static com.eleazar.polling.pollerAvailableVehicles.Poller.pollerMeep;

@SpringBootApplication
public class PollerAvailableVehiclesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollerAvailableVehiclesApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            pollerMeep();
        };
    }
}
