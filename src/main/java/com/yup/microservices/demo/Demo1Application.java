package com.yup.microservices.demo;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo1Application {

	private static final Logger logger = LoggerFactory.getLogger(Demo1Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
		logger.info("Hola Spring Boot");
		logger.info("Hola Spring Boot");
	}
}
