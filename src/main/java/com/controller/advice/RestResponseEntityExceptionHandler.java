package com.controller.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.domain.error.ErrorResponseDto;
import com.domain.error.ObjectNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	    ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	    return handleExceptionInternal(ex, errorResponseDto, headers, errorResponseDto.getStatus(), request);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	protected ResponseEntity<Object> handleResponseStatus(ObjectNotFoundException ex, WebRequest request){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getLocalizedMessage());
		ResponseEntity<Object> ob = new ResponseEntity<Object>(
	    	      errorResponseDto, new HttpHeaders(), errorResponseDto.getStatus());
		return ob;
	}
}
