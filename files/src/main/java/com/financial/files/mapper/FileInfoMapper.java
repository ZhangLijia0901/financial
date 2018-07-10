package com.financial.files.mapper;

import org.apache.ibatis.annotations.Param;

import com.financial.files.model.FileInfo;

/**
 * 文件信息mapper
 * 
 * @author: 张礼佳
 * @date: 2018年6月7日 上午9:22:00
 */
public interface FileInfoMapper {

	/** 新增文件信息 */
	int insert(FileInfo fileInfo);

	/**
	 * 修改文件信息
	 * 
	 * @required id与token必须存在一个
	 * @param fileInfo 文件信息
	 */
	int update(FileInfo fileInfo);

	/** 根据ID获取文件信息 */
	FileInfo getById(@Param("id") String id);

	/** 根据token获取文件信息 */
	FileInfo getByToken(@Param("token") String token);

	/** 根据path获取文件信息 */
	FileInfo getByPath(@Param("path") String path);

}
