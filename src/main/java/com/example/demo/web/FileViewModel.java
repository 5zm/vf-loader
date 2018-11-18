package com.example.demo.web;

import java.io.Serializable;
import java.util.List;

import com.example.demo.domain.model.FileInfo;
import com.example.demo.domain.model.StoreConfig;

public class FileViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private StoreConfig storeConfig;
	
	private List<FileInfo> fileInfoList;
	
	public FileViewModel() {
		
	}

	public FileViewModel(StoreConfig storeConfig, List<FileInfo> fileInfoList) {
		super();
		this.storeConfig = storeConfig;
		this.fileInfoList = fileInfoList;
	}

	public StoreConfig getStoreConfig() {
		return storeConfig;
	}

	public void setStoreConfig(StoreConfig storeConfig) {
		this.storeConfig = storeConfig;
	}

	public List<FileInfo> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}
}
