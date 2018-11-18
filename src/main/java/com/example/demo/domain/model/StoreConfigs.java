package com.example.demo.domain.model;

import java.io.Serializable;
import java.util.Map;

public class StoreConfigs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, StoreConfig> storeConfigs;

	public StoreConfigs() {
		
	}

	public Map<String, StoreConfig> getStoreConfigs() {
		return storeConfigs;
	}

	public void setStoreConfigs(Map<String, StoreConfig> storeConfigs) {
		this.storeConfigs = storeConfigs;
	}
	
}
