package com.example.demo.web.common;

import java.io.InputStream;
import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class StreamFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
    private InputStream inputStream;

    @Size(max = 200)
    @NotEmpty
    private String contentType;

    @Min(1)
    private long contentLength;

    @Size(max = 200)
    @NotEmpty
    private String fileName;
    
    public StreamFile() {
    	
    }

	public StreamFile(InputStream inputStream, String contentType, long contentLength, String fileName) {
		super();
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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

	@Override
	public String toString() {
		return "StreamFile [contentType=" + contentType + ", contentLength=" + contentLength + ", fileName=" + fileName
				+ "]";
	}
    
}
