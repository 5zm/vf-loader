package com.example.demo.domain.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.common.DemoSystemException;
import com.example.demo.domain.common.FileSizeOverException;
import com.example.demo.domain.model.FileInfo;
import com.example.demo.domain.model.StoreConfig;
import com.example.demo.domain.model.StoreConfigs;
import com.example.demo.domain.repository.FileInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FileManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

	/**
	 * define buffer size default 1MB
	 */
	@Value("${application.bufferSize:1048576}")
	int bufferSize;

	/**
	 * max upload file size (byte) <br>
	 * default 500MB
	 */
	@Value("${application.maxFileSize:524288000}")
	long maxFileSize;

	@Value("${application.saveBaseDirectory:./filedata}")
	private File saveBaseDirectory;

	@Value("${application.storeConfig:./config/storeConfigs.json}")
	private File storeConfigsFile;

	private StoreConfigs storeConfigs;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	FileInfoRepository fileInfoRepository;

	@PostConstruct
	public void init() {
		try {
			storeConfigs = objectMapper.readValue(storeConfigsFile, StoreConfigs.class);
		} catch (IOException e) {
			LOGGER.error("", e);
			throw new DemoSystemException("storeConfigs json parse error!", e);
		}
		if (!saveBaseDirectory.exists()) {
			if (!saveBaseDirectory.mkdir()) {
				throw new DemoSystemException("saveBaseDirectory is incccorect : " + saveBaseDirectory);
			}
		}
		for (StoreConfig storeConfig : storeConfigs.getStoreConfigs().values()) {
			File saveFileDir = new File(saveBaseDirectory, storeConfig.getFileType());
			if (!saveFileDir.exists()) {
				if (!saveFileDir.mkdir()) {
					throw new DemoSystemException("StoreConfigs.json is incccorect : " + saveFileDir);
				}
			}
		}

	}

	public Map<String, StoreConfig> storeConfigMap() {
		return Collections.unmodifiableMap(storeConfigs.getStoreConfigs());
	}

	@Transactional(readOnly = true)
	public FileInfo findOne(String fileId) {
		return fileInfoRepository.fineOne(fileId);
	}

	@Transactional(readOnly = true)
	public List<FileInfo> findAll() {
		return fileInfoRepository.findAll();
	}

	@Transactional
	public void delete(String fileId) {
		FileInfo fileInfo = fileInfoRepository.fineOne(fileId);
		if (fileInfo == null) {
			LOGGER.warn("file not exist in db : " + fileId);
		} else {
			File file = new File(saveBaseDirectory, fileInfo.getFilePath());
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
				LOGGER.warn("file not exist at system : " + fileId, e);
			} catch (IOException e) {
				LOGGER.warn("error file delete at system : " + fileId, e);
			}

			fileInfoRepository.deleteByKey(fileId);
		}

	}

	public void loadFile(FileInfo fileInfo, OutputStream out) throws IOException {
		File file = new File(saveBaseDirectory, fileInfo.getFilePath());
		try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
			byte[] buffer = new byte[bufferSize];
			long total = 0;
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				out.write(buffer, 0, len);
				out.flush();
				total = total + len;
				// LOGGER.debug("writed : " + total);
			}
		}
	}

	@Transactional
	public FileInfo save(FileInfo fileInfo, InputStream input) throws FileSizeOverException {

		// generate file id and anything
		Timestamp registeredDate = new Timestamp(new Date().getTime());
		String tempFileId = UUID.randomUUID().toString().replaceAll("-", "");
		String extention = parseExtention(fileInfo.getFileName());
		String fileId = null;
		File saveFile = null;
		try {
			File saveFileDir = new File(saveBaseDirectory, fileInfo.getFileType());
			saveFile = File.createTempFile(tempFileId + "_", extention, saveFileDir);
			fileId = saveFile.getName().replace(extention, "");
		} catch (IOException e) {
			LOGGER.error("save file error at fileId genarate", e);
			throw new DemoSystemException("save file error at fileId genarate", e);
		}

		// save file
		try {
			try (OutputStream output = new BufferedOutputStream(new FileOutputStream(saveFile))) {
				byte[] buffer = new byte[bufferSize];
				long total = 0;
				int len = 0;
				while ((len = input.read(buffer)) != -1) {
					output.write(buffer, 0, len);
					output.flush();
					total = total + len;
					// LOGGER.debug("writed : " + total);
					if (maxFileSize < total) {
						throw new FileSizeOverException("file size is " + total);
					}
				}
			}
		} catch (IOException e) {
			throw new DemoSystemException("save file error", e);
		}

		// save file info
		FileInfo registeredFileInfo = new FileInfo();
		registeredFileInfo.setFileId(fileId);
		registeredFileInfo.setRegisteredDate(registeredDate);
		registeredFileInfo.setContentLength(fileInfo.getContentLength());
		registeredFileInfo.setContentType(fileInfo.getContentType());
		registeredFileInfo.setFileName(fileInfo.getFileName());
		registeredFileInfo.setFileType(fileInfo.getFileType());
		registeredFileInfo.setFilePath("/" + fileInfo.getFileType() + "/" + saveFile.getName());

		LOGGER.debug("insert registeredFileInfo={}", registeredFileInfo);
		fileInfoRepository.insert(registeredFileInfo);

		return registeredFileInfo;
	}

	private String parseExtention(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}

}
