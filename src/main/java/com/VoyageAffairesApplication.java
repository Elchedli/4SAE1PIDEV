package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


//@Configuration
@SpringBootApplication
//@EnableScheduling
//@EnableAspectJAutoProxy
public class VoyageAffairesApplication {
	public static void main(String[] args) {
		SpringApplication.run(VoyageAffairesApplication.class, args);
	}

}
