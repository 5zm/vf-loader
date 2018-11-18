package com.example.demo.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import com.example.demo.domain.common.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class FileInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String contentType;

	private long contentLength;

	private String fileName;

	@JsonIgnore
	private String fileType;

	private String fileId;

	@JsonIgnore
	private String filePath;

	@JsonSerialize(using = JsonDateSerializer.class)
	private Timestamp registeredDate;

	public FileInfo() {

	}

	public FileInfo(String contentType, long contentLength, String fileName, String fileType, String fileId,
			String filePath, Timestamp registeredDate) {
		super();
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileId = fileId;
		this.filePath = filePath;
		this.registeredDate = registeredDate;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Timestamp getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [contentType=");
		builder.append(contentType);
		builder.append(", contentLength=");
		builder.append(contentLength);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", fileId=");
		builder.append(fileId);
		builder.append(", filePath=");
		builder.append(filePath);
		builder.append(", registeredDate=");
		builder.append(registeredDate);
		builder.append("]");
		return builder.toString();
	}

}
