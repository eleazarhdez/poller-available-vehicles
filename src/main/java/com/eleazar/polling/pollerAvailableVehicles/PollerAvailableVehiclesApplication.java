package com.eleazar.polling.pollerAvailableVehicles;

import com.eleazar.polling.models.Coordinates;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;

import static com.eleazar.polling.pollerAvailableVehicles.Poller.pollerMeep;
import static com.eleazar.polling.utils.Utils.buildArgs;

@SpringBootApplication
public class PollerAvailableVehiclesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollerAvailableVehiclesApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            HashMap builtArguments = buildArgs(args);
            pollerMeep( (Coordinates) builtArguments.get("lowerLeftLatLon"),
                        (Coordinates) builtArguments.get("upperRightLatLon"),
                        (List<Integer>) builtArguments.get("companyIds"));
        };
    }

}
