package com.scaffolder.scaffolder.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.scaffolder.scaffolder.service.ScaffService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class Controller {
	
	@Autowired
	private ScaffService service;
	private final String ZIP_FILE_NAME = "starter.zip";
	
	/**
	 * 
	 * @param backend
	 * @param frontend
	 */
	
	@GetMapping(value = "/download/backend-tech/{backend}/frontend-tech/{frontend}", produces = "application/zip")
	public void download(@PathVariable("backend") String backend, @PathVariable("frontend") String frontend, HttpServletResponse response) {
		log.info("Inside the controller");
//		String backendPath = service.getFilePath(backend);
//		String frontendPath = service.getFilePath(frontend);
		
//		String backendPath = "D:\\DSGO\\starter-spring.zip";
//		String frontendPath = "D:\\DSGO\\Rec-spring.zip";
		
		// List<String> paths = Arrays.asList(backendPath, frontendPath);
		List<String> paths = Arrays.asList(backend, frontend);
		try {
			ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
			for(String filename : paths) {
				// FileSystemResource resource = new FileSystemResource(filename);
				Resource resource = service.getResource(filename);
				log.info(resource.getFilename()+" For loop");
				ZipEntry zipEntry = new ZipEntry(resource.getFilename());
				zipEntry.setSize(resource.contentLength());
				
				zipOut.putNextEntry(zipEntry);
				StreamUtils.copy(resource.getInputStream(), zipOut);
				zipOut.closeEntry();
			}
			
			zipOut.finish();
			zipOut.close();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=starter.zip");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
}
