package com.financial.files.service;

import com.financial.common.bean.response.CommonResponse;

/**
 * *文件结构
 * 
 * @author: 张礼佳
 * @date: 2018年6月8日 上午11:21:16
 */
public interface FileTreeSerice {

	/**
	 * *获取目录下的文件与文件夹
	 * 
	 * @param path        指定系统盘符位置
	 * @param currentPath 当前获取文件的目录
	 */
	CommonResponse getFileListByRootPath(String path, String currentPath);

}
