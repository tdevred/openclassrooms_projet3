package com.tdevred.rentals;

import com.tdevred.rentals.configuration.StorageConfig;
import com.tdevred.rentals.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class RentalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			if(storageService.isInitialized()) {
				return;
			}

			storageService.deleteAll();
			storageService.init();
		};
	}
}
