package com.example.demo.domain.common;

public class DemoSystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DemoSystemException() {
		super();
	}

	public DemoSystemException(String message) {
		super(message);
	}

	public DemoSystemException(Throwable cause) {
		super(cause);
	}

	public DemoSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public DemoSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
