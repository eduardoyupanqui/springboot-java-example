package com.yup.demo1.Domain;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.yup.demo1.Repository.CarRepository;

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
