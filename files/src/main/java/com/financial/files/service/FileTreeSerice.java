package com.financial.files.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
import com.financial.common.bean.response.CommonResponse;
import com.financial.files.model.FileInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileTreeSerice {

	@Autowired
	private FileInfoService fileInfoService;

	public CommonResponse createFolder(String path, String currentPath) {
		try {
			currentPath = URLDecoder.decode(currentPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("URL解码异常", e);
		}
		log.debug("开始创建目录 path:[{}], currentPath: [{}]", path, currentPath);
		if (currentPath == null || "".equals(currentPath))
			currentPath = "/";
		path = path + (currentPath.startsWith("/") ? currentPath : "/" + currentPath);

		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
			return new CommonResponse();
		}
		return new CommonResponse(CommonResponse.ERROR_CODE, "文件目录已经存在!");
	}

	public CommonResponse getFileListByRootPath(String path, String currentPath) {
		log.debug("开始获取文件列表 path:[{}], currentPath: [{}]", path, currentPath);
		if (currentPath == null || "".equals(currentPath))
			currentPath = "/";
		path = path + (currentPath.startsWith("/") ? currentPath : "/" + currentPath);

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
			FileInfo fileInfo = fileInfoService.getFileInfoByFilePath(file.getPath().replaceAll("\\\\", "/"));

			FileInf fileInf = new FileInf();
			fileInf.setName(fileInfo == null ? file.getName() : fileInfo.getFileName());
			fileInf.setLastUpdateTime(new Date(file.lastModified()));
			fileInf.setSize(parseSize(file.length()));

			fileInfs.add(fileInf);
		});
		return fileInfs;
	}

	static Map<Integer, String> SIZE = Map.of(0, "B", 1, "KB", 2, "MB", 3, "GB", 4, "TB");

	public String parseSize(long size) {
		int i = 0;
		double temp = size;
		int hex = 1 << 10;
		while (temp > 1000) {
			temp /= hex;
			i++;
		}
		return String.format("%.2f", temp) + SIZE.get(i);
	}

	@Data
	private static class FileInf {
		private String iocn;// 图标

		private String name;// 名称

		public void setName(String name) {
			this.name = name;
			this.iocn = getIOCN(name.substring(name.lastIndexOf(".") + 1).toUpperCase());
		}

		static String getIOCN(String name) {
			String iocn = "img/file_iocn/" + name + ".png";
			URL resource = FileInf.class.getClassLoader().getResource("static/" + iocn);
			if (resource != null)
				return iocn;
			else
				return "img/file_iocn/OHTER.png";
		}

		@JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
		private Date lastUpdateTime;// 最后修改时间

		private String size;

	}

	@Data
	private static class Directory {
		private String iocn = FileInf.getIOCN("FOLDER");// 图标

		private String name; // 名称

		@JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
		private Date lastUpdateTime;// 最后修改时间
	}

}
