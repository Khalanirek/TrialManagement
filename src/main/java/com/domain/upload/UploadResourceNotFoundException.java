package com.domain.upload;

public class UploadResourceNotFoundException extends Exception {
	public UploadResourceNotFoundException(String path) {
		super(String.format("Upload resource %s not found", path));
	}
}
