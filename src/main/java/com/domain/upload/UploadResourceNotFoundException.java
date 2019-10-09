package com.domain.upload;

import com.domain.error.ObjectNotFoundException;

public class UploadResourceNotFoundException extends ObjectNotFoundException {
	public UploadResourceNotFoundException(String path) {
		super(String.format("Upload resource %s not found", path));
	}
}
