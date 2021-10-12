package com.yup.microservices.demo;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigFileResource {

	@Bean(name = "configFile")
	public File configFile() {
		File configFile = new File("configFile.xml");
		return configFile;
	}
	
}
