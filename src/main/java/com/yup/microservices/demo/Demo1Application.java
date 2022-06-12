package com.yup.microservices.demo;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Demo1Application {

	private static final Logger logger = LoggerFactory.getLogger(Demo1Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

	@Value("${prueba.config}")
	private String PruebaConfig;
	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		logger.info("Hola Spring Boot, valor de prueba.config es "+PruebaConfig);
	}
}
