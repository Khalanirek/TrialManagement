package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.domain.upload.UploadResourceNotFoundException;
import com.service.UploadService;

@RestController
@RequestMapping("/files")
public class UploadController {

	@Autowired
	UploadService uploadService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST,headers = "content-type=multipart/form-data")
	public boolean handleFileUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
		try {
			return uploadService.saveMultipartFile(multipartFile);
		} catch (IllegalStateException | IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<Resource> handleFileDownload(@PathVariable String fileName){
		try {
			return uploadService.getMultipartFile(fileName);
		} catch (UploadResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST,headers = "content-type=multipart/form-data")
	public boolean handleFileUpdate(@RequestPart("multipartFile") MultipartFile multipartFile){
		try {
			return uploadService.updateMultipartFile(multipartFile);
		} catch (IllegalStateException | IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{file}")
	public boolean handleFileDelete(@PathVariable String fileName){
		try {
			return uploadService.deleteMultipartFile(fileName);
		} catch (IllegalStateException | IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
