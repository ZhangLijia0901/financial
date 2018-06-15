package com.financial.files.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.financial.common.bean.response.CommonResponse;
import com.financial.files.service.FileTreeSerice;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileTreeSericeImpl implements FileTreeSerice {

	@Override
	public CommonResponse getFileListByRootPath(String path, String currentPath) {
		log.debug("开始获取文件列表 path:[{}], currentPath: [{}]", path, currentPath);
		if (currentPath == null || "".equals(currentPath))
			currentPath = "/";
		path = path + (currentPath.startsWith("/") ? currentPath.substring(1) : currentPath);

		List<File> files = getFiles(path);
		Map<String, Object> data = new HashMap<>(3);
		data.put("currentPath", currentPath);
		if (files != null) {
			data.put("directories", getDirectory(files));
			data.put("files", getFiles(files));
		}

		return new CommonResponse(data);
	}

	/** 获取路径下所有文件 */
	private List<File> getFiles(String path) {
		log.debug("获取文件列表的目录：[{}]", path);
		File file = new File(path);
		if (!file.exists() || !file.isDirectory())
			return null;
		File[] files = file.listFiles();

		return Arrays.asList(files);
	}

	/** 获取文件夹中的目录 */
	private List<Directory> getDirectory(List<File> files) {
		List<Directory> directories = new ArrayList<>(8);
		files.forEach((file) -> {
			if (!file.exists() || !file.isDirectory())
				return;
			Directory directory = new Directory();
			directory.setName(file.getName());
			directory.setLastUpdateTime(new Date(file.lastModified()));
			directories.add(directory);
		});

		return directories;
	}

	private List<FileInf> getFiles(List<File> files) {
		List<FileInf> fileInfs = new ArrayList<>(10);
		files.forEach((file) -> {
			if (!file.exists() || !file.isFile())
				return;
			FileInf fileInf = new FileInf();
			fileInf.setName(file.getName());
			fileInf.setLastUpdateTime(new Date(file.lastModified()));
			fileInf.setSize(file.length());
			fileInfs.add(fileInf);
		});
		return fileInfs;
	}

	@Data
	private static class FileInf {
		private String name;
		private Date lastUpdateTime;
		private Long size;

	}

	@Data
	private static class Directory {
		private String name;
		private Date lastUpdateTime;
	}

}
