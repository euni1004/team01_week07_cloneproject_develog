package com.week07.week07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week07Application {

	public static void main(String[] args) {
		SpringApplication.run(Week07Application.class, args);
	}

}
