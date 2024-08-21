package com.Tancem.PIS;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.Tancem.PIS")
public class PisApplication {

	public static void main(String[] args) {

		SpringApplication.run(PisApplication.class, args);
	}

}
