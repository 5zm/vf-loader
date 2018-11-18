package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.common.DemoValidationExeption;
import com.example.demo.domain.common.FileSizeOverException;
import com.example.demo.domain.model.FileInfo;
import com.example.demo.domain.service.FileManager;
import com.example.demo.web.common.StreamFile;
import com.example.demo.web.common.WebUtils;

@Controller
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	/**
	 * max upload file size (byte) <br>
	 * default 500MB
	 */
	@Value("${application.maxFileSize:524288000}")
	long maxFileSize;

	@Autowired
	FileManager fileManager;

	@PostMapping("upload/{fileType}")
	public ResponseEntity<FileInfo> upload(@Validated StreamFile streamFile, BindingResult result,
			@PathVariable String fileType) {

		if (result.hasErrors()) {
			LOGGER.debug("validated error = {}", result.getAllErrors());
			throw new DemoValidationExeption(WebUtils.parseValidationError(result));
		}

		if (maxFileSize < streamFile.getContentLength()) {
			throw new FileSizeOverException("file size is " + streamFile.getContentLength());
		}

		LOGGER.debug("streamFile={}", streamFile);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setContentLength(streamFile.getContentLength());
		fileInfo.setContentType(streamFile.getContentType());
		fileInfo.setFileName(streamFile.getFileName());
		fileInfo.setFileType(fileType);

		FileInfo registeredFileInfo = fileManager.save(fileInfo, streamFile.getInputStream());

		return new ResponseEntity<FileInfo>(registeredFileInfo, HttpStatus.CREATED);
	}

}
