package com.example.demo.web.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class WebUtils {

	public static List<String> parseValidationError(BindingResult result) {
		List<String> errors = new ArrayList<String>(result.getErrorCount());
		result.getAllErrors().stream().forEach(e -> errors.add(e.getDefaultMessage()));
		return errors;
	}

	public static ResponseEntity<ApiMessage> apiMessage(String message, List<String> messages, HttpStatus httpStatus) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=utf-8");
		responseHeaders.set("X-Content-Type-Options: nosniff", "");
		ApiMessage apiMessage = new ApiMessage(message, messages);
		return new ResponseEntity<ApiMessage>(apiMessage, responseHeaders, httpStatus);
	}
}
