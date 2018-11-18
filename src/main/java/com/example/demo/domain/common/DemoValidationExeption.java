package com.example.demo.domain.common;

import java.util.List;

public class DemoValidationExeption extends DemoSystemException {

	private static final long serialVersionUID = 1L;
	
	List<String> errors;
	
	public DemoValidationExeption(List<String> errors) {
		super();
		this.errors = errors;
	}

	public DemoValidationExeption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DemoValidationExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DemoValidationExeption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DemoValidationExeption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DemoValidationExeption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public List<String> getErrors() {
		return errors;
	}

}
