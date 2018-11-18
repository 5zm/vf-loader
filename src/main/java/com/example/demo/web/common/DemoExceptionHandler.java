package com.example.demo.web.common;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.domain.common.DemoNotFoundException;
import com.example.demo.domain.common.DemoSystemException;
import com.example.demo.domain.common.DemoValidationExeption;
import com.example.demo.domain.common.FileSizeOverException;

@RestControllerAdvice
public class DemoExceptionHandler {

	@ExceptionHandler(DemoNotFoundException.class)
	public ResponseEntity<ApiMessage> handleDemoNotFoundException(DemoNotFoundException e) {
		String message = "not found";
		List<String> errors = Arrays.asList(e.getMessage());
		return WebUtils.apiMessage(message, errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DemoValidationExeption.class)
	public ResponseEntity<ApiMessage> handleDemoValidationException(DemoValidationExeption e) {
		String message = "validation error";
		return WebUtils.apiMessage(message, e.getErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileSizeOverException.class)
	public ResponseEntity<ApiMessage> handleFileSizeOverException(FileSizeOverException e) {
		String message = "upload size error";
		List<String> errors = Arrays.asList(e.getMessage());
		return WebUtils.apiMessage(message, errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DemoSystemException.class)
	public ResponseEntity<ApiMessage> handleDemoSystemException(DemoSystemException e) {
		String message = "system error!";
		List<String> errors = Arrays.asList(e.getMessage());
		return WebUtils.apiMessage(message, errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
