package com.yup.microservices.demo.Domain;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.yup.microservices.demo.Repository.CarRepository;

public class Car {
	@Autowired
	private Owner owner;
	@Autowired
	private CarRepository repository;
	
	@Resource(name = "configFile")
	private File configFile;
	/*
	 * public Car(Owner owner) { // TODO Auto-generated constructor stub this.owner
	 * = owner;
	 * 
	 * }
	 */
	
}
