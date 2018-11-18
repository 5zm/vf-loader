package com.example.demo.domain.model;

import java.io.Serializable;

public class StoreConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int order;
	
	private String fileType;
	
	private String fileTypeName;
	
	private String contentType;
	
	public StoreConfig() {
		
	}

	public StoreConfig(int order, String fileType, String fileTypeName, String contentType) {
		super();
		this.order = order;
		this.fileType = fileType;
		this.fileTypeName = fileTypeName;
		this.contentType = contentType;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
