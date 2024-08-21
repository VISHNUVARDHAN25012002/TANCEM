package com.Tancem.PIS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class LogDirectoryInitializer {

    @Bean
    public CommandLineRunner createLogDirectory() {
        return args -> {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                boolean created = logDir.mkdirs();
                if (created) {
                    System.out.println("Logs directory created successfully.");
                } else {
                    System.err.println("Failed to create logs directory.");
                }
            }
        };
    }
}
