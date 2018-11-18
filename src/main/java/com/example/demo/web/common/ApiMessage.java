package com.example.demo.web.common;

import java.io.Serializable;
import java.util.List;

public class ApiMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private List<String> messages;
	
	public ApiMessage() {
		
	}

	public ApiMessage(String message, List<String> messages) {
		super();
		this.message = message;
		this.messages = messages;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
