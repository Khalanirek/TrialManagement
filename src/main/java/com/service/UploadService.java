package com.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.domain.upload.UploadRepository;
import com.domain.upload.UploadResourceNotFoundException;

@Service
public class UploadService {

	Environment env;
	UploadRepository uploadRepository;

	@Autowired
	public UploadService(Environment env, UploadRepository uploadRepository) {
		this.env = env;
		this.uploadRepository = uploadRepository;
	}

	public boolean saveMultipartFile(MultipartFile multipartFile) throws IllegalStateException, IOException {

		String destination = env.getProperty("CATALINA_BASE") + "\\uploadedFiles\\"  + multipartFile.getOriginalFilename();
	    File file = new File(destination);
	    int i =  1;
	    while(file.exists()) {
	    	int endIndex = destination.lastIndexOf(".");
	    	String newDestination = destination.substring(0, endIndex) + " (" + i + ")" + destination.substring(endIndex);
	    	file = new File(newDestination);
	    	i++;
	    }
	    return uploadRepository.saveFile(multipartFile, file);
	}

	public ResponseEntity<Resource> getMultipartFile(String fileName) throws UploadResourceNotFoundException {
		String destination = env.getProperty("CATALINA_BASE") + "\\uploadedFiles\\" + fileName;
		Optional<ResponseEntity<Resource>> file = uploadRepository.getFile(new File(destination));
		return file.orElseThrow(() -> new UploadResourceNotFoundException(fileName));
	}

	public boolean updateMultipartFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String destination = env.getProperty("CATALINA_BASE") + "\\uploadedFiles\\"  + multipartFile.getOriginalFilename();
	    File file = new File(destination);
	    return uploadRepository.saveFile(multipartFile, file);
	}

	public boolean deleteMultipartFile(String fileName) throws IllegalStateException, IOException {
		String destination = env.getProperty("CATALINA_BASE") + "\\uploadedFiles\\"  + fileName;
		File file = new File(destination);
		return uploadRepository.deleteFile(file);
	}
}
