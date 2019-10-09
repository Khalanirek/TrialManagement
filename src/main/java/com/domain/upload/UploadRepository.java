package com.domain.upload;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class UploadRepository {

	public boolean saveFile(MultipartFile multipartFile, File file) throws IllegalStateException, IOException {
		multipartFile.transferTo(file);
		return true;
	}

	public Optional<Resource> getFile(File file){
		Resource fileSystemResource = null;
		if (file.exists()) {
			fileSystemResource = new FileSystemResource(file);
		}
		return Optional.ofNullable(fileSystemResource);
	}

	public boolean deleteFile(File file) {
		return file.delete();
	}
}
