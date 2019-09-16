package com.domain.upload;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class UploadRepository {

	public boolean saveFile(MultipartFile multipartFile, File file) throws IllegalStateException, IOException {
		multipartFile.transferTo(file);
		return true;
	}

	public Optional<ResponseEntity<Resource>> getFile(File file){
		Resource fileSystemResource = new FileSystemResource(file);
			return Optional.of(ResponseEntity.ok()
	                .contentType(MediaType.MULTIPART_FORM_DATA)
	                .body(fileSystemResource));
	}

	public boolean deleteFile(File file) {
		return file.delete();
	}
}
