package com.example.demo.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.demo.domain.common.DemoNotFoundException;
import com.example.demo.domain.model.FileInfo;
import com.example.demo.domain.service.FileManager;

@Controller
public class DownloadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	FileManager fileManager;

	@GetMapping("download/{fileType}/{fileId}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable String fileType, @PathVariable String fileId) {
		
		FileInfo fileInfo = fileManager.findOne(fileId);
		if (fileInfo == null) {
			LOGGER.debug("not found : " + fileId);
			throw new DemoNotFoundException(fileId + " is not found.");
		}

		// header
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", fileInfo.getContentType());
		String urlEncododedFileName;
		try {
			urlEncododedFileName = URLEncoder.encode(fileInfo.getFileName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			urlEncododedFileName = fileInfo.getFileName();
		}
		responseHeaders.set("Content-Disposition", "attachment; filename=" + urlEncododedFileName);
		// body
		StreamingResponseBody responseBody = streamingResponseBody(fileInfo);
		// responseEntity
		ResponseEntity<StreamingResponseBody> responseEntity = new ResponseEntity<StreamingResponseBody>(responseBody,
				responseHeaders, HttpStatus.OK);
		return responseEntity;
	}

	private StreamingResponseBody streamingResponseBody(FileInfo fileInfo) {
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				LOGGER.debug("Start Async processing.");
				fileManager.loadFile(fileInfo, out);
				LOGGER.debug("End Async processing.");
			}
		};
	}
}
