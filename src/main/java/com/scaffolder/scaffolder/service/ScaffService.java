package com.scaffolder.scaffolder.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScaffService {
	private String SPRING = "D:\\DSGO\\starter-spring.zip";
	private String REACT = "D:\\DSGO\\Rec-spring.zip";
	
	@Autowired
	private ResourceLoader resourceLoader;
	
//	Path fileStoragePath;
//	
//	public ScaffService(@Value("${file.storage.location:temp}") String fileStoageLocation) {
//		this.fileStoragePath = Paths.get(fileStoageLocation).toAbsolutePath().normalize();
//	}
	
	private String SPRING_R = "classpath:static/Spring/starter-spring.zip";
	private String REACT_R = "classpath:static/React/Rec-spring.zip";
	public String getFilePath(String tech) {
		if(tech.toUpperCase().equals("SPRING")) {
			return SPRING;
		} else {
			return REACT;
		}
	}
	
	public Resource getResource(String tech) {
		log.info("Inside Service tech "+tech);
		if(tech.toUpperCase().equals("SPRING")) {
			log.info("SPring IF");
			return resourceLoader.getResource(SPRING_R);
		} else {
			return resourceLoader.getResource(REACT_R);
		}
	}
}
