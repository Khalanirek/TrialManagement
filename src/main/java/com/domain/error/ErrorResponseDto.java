package com.domain.error;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponseDto {
	 private HttpStatus status;
	 private String message;
	 private List<String> errors;

	 public ErrorResponseDto(HttpStatus status, String message, List<String> errors) {
	     this.status = status;
	     this.message = message;
	     this.errors = errors;
	 }

	 public ErrorResponseDto(HttpStatus status, String message, String error) {
	     this.status = status;
	     this.message = message;
	     errors = Arrays.asList(error);
	 }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
