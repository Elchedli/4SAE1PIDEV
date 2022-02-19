package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VoyageAffairesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoyageAffairesApplication.class, args);
	}

}
