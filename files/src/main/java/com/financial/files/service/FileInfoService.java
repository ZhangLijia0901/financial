package com.financial.files.service;

import org.springframework.web.multipart.MultipartFile;

import com.financial.files.model.FileInfo;

/**
 * 文件信息 service
 * 
 * @author: 张礼佳
 * @date: 2018年6月7日 上午11:02:29
 */
public interface FileInfoService {

	/** 添加文件信息 */
	void addFileInfo(MultipartFile multipartFile, String pathName);

	/** 根据令牌获取文件信息 */
	FileInfo getFileInfoByToken(String token);

}
