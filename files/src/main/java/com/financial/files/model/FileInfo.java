package com.financial.files.model;

import java.util.Date;

import lombok.Data;

/**
 * 文件信息实体
 * 
 * @author: 张礼佳
 * @date: 2018年6月6日 下午3:05:42
 */
@Data
public class FileInfo {
	private Integer id; // 主键
	private String token; // 唯一标识
	private String pathName;// 文件路径
	private String fileName;// 文件名称
	private Long fileSize;// 文件大小
	private Integer userId;// 文件归属用户
	private Date uploadTime;// 文件上传时间
}
