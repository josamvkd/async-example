package com.josamtechie.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 👈 Enables async processing in the application
public class AsyncExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncExampleApplication.class, args);
	}

}
