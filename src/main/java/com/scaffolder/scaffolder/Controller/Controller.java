package com.scaffolder.scaffolder.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaffolder.scaffolder.service.ScaffService;

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
	 * @param response
	 */
	@GetMapping(value = "/download/backend-tech/{backend}/frontend-tech/{frontend}", produces = "application/zip")
	public void download(@PathVariable("backend") String backend, @PathVariable("frontend") String frontend, HttpServletResponse response) {
		log.info("Inside the controller");
		try {
			ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
			
			List<Resource> backendFiles = service.getResources(backend);
			List<Resource> frontendFiles = service.getResources(frontend);
			List<Resource> filesList = new ArrayList<>();
			filesList.addAll(backendFiles);
			filesList.addAll(frontendFiles);
			
			// Traverse through the resource and make in single .zip file and download it
			for(Resource resource : filesList) {
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
