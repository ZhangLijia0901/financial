package com.financial.files.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.financial.files.mapper.FileInfoMapper;
import com.financial.files.model.FileInfo;
import com.financial.files.service.FileInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileInfoServiceImpl implements FileInfoService {

	@Resource
	private FileInfoMapper fileInfoMapper;// 文件信息mapper

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addFileInfo(MultipartFile multipartFile, String pathName) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setToken(UUID.randomUUID().toString());// token
		fileInfo.setFileSize(multipartFile.getSize());// 文件大小
		fileInfo.setFileName(multipartFile.getOriginalFilename());// 原始名称
		fileInfo.setPathName(pathName);// 文件路径

		log.debug("新增文件信息 : [{}]", JSON.toJSON(fileInfo));
		fileInfoMapper.insert(fileInfo);
	}

	@Override
	public FileInfo getFileInfoByToken(String token) {
		if (token == null)
			return null;
		return fileInfoMapper.getByToken(token);
	}

}
