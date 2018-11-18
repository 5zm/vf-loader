package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.FileInfo;
import com.example.demo.domain.model.StoreConfig;
import com.example.demo.domain.service.FileManager;

@RestController
public class ManagementController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagementController.class);

	@Autowired
	FileManager fileManager;

	@GetMapping("files")
	public ResponseEntity<List<FileViewModel>> list() {
		return ResponseEntity.ok().body(findFileViewModel());
	}

	@DeleteMapping("{fileType}/{fileId}")
	public ResponseEntity<String> delete(@PathVariable String fileType, @PathVariable String fileId) {
		LOGGER.debug("delete fileType:{}, fileId:{}", fileType, fileId);
		fileManager.delete(fileId);
		return ResponseEntity.ok().body("success!");
	}

	private List<FileViewModel> findFileViewModel() {
		Map<String, StoreConfig> storeConfigMap = fileManager.storeConfigMap();
		List<FileInfo> fileInfoList = fileManager.findAll();
		List<FileViewModel> fileViewModelList = new ArrayList<FileViewModel>(storeConfigMap.size());
		for (StoreConfig storeConfig : storeConfigMap.values()) {
			String fileType = storeConfig.getFileType();
			List<FileInfo> list = new ArrayList<FileInfo>();
			fileInfoList.stream() //
					.filter(item -> fileType.equals(item.getFileType())) // fileType が等しいものを抽出
					.forEach(item -> list.add(item)); // 対象のみをリストに追加
			fileViewModelList.add(new FileViewModel(storeConfig, list));
		}
		return fileViewModelList;
	}
}
