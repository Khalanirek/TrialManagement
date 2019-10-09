package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Boolean> handleFileUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(uploadService.saveMultipartFile(multipartFile));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> handleFileDownload(@PathVariable String fileName) throws UploadResourceNotFoundException{
			return ResponseEntity.ok()
	                .contentType(MediaType.MULTIPART_FORM_DATA)
	                .body(uploadService.getMultipartFile(fileName));
	}

	@RequestMapping(value = "/delete/{fileName:.+}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> handleFileDelete(@PathVariable String fileName){
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(uploadService.deleteMultipartFile(fileName));
	}
}
