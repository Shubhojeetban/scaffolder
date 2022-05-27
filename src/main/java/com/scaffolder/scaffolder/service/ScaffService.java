package com.scaffolder.scaffolder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScaffService {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	private final String SPRING_ZIP = "classpath:static/Spring/starter-spring.zip";
	private final String REACT_ZIP = "classpath:static/React/starter-react.zip";
	private final String ANGULAR_ZIP = "classpath:static/Angular/starter-angular.zip";
	private final String NODE_ZIP = "classpath:static/Node/starter-node.zip";
	
	private final String SPRING_README = "classpath:static/Spring/SPRING_README.md";
	private final String REACT_README = "classpath:static/React/REACT_README.md";
	private final String ANGULAR_README = "classpath:static/Angular/ANGULAR_README.md";
	private final String NODE_README = "classpath:static/Node/NODE_README.md";
	
	public List<Resource> getResources(String tech) {
		log.info("Inside Service tech "+tech);
		String filename = tech.toUpperCase();
		
		List<Resource> fileList = new ArrayList<>();
		switch (filename) {
		case "SPRING": 
			fileList.add(resourceLoader.getResource(SPRING_README));
			fileList.add(resourceLoader.getResource(SPRING_ZIP));
			return fileList;
		case "REACT": 
			fileList.add(resourceLoader.getResource(REACT_README));
			fileList.add(resourceLoader.getResource(REACT_ZIP));
			return fileList;
		case "ANGULAR":
			fileList.add(resourceLoader.getResource(ANGULAR_README));
			fileList.add(resourceLoader.getResource(ANGULAR_ZIP));
			return fileList;
		case "NODE":
			fileList.add(resourceLoader.getResource(NODE_README));
			fileList.add(resourceLoader.getResource(NODE_ZIP));
			return fileList;
		default:
			throw new RuntimeException("File : "+filename+" not found");
		}
	}
}
