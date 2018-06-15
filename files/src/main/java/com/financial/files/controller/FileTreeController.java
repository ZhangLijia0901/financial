package com.financial.files.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.financial.common.bean.response.CommonResponse;
import com.financial.common.controller.BaseController;
import com.financial.files.service.FileTreeSerice;

/**
 * 
 * *文件目录结构 Controller
 * 
 * @author: 张礼佳
 * @date: 2018年6月8日 上午11:04:11
 */
@Controller
@RequestMapping(value = { "fileTree" })
public class FileTreeController extends BaseController {

	@Value("${files.path}")
	private String filePath;

	@Autowired
	private FileTreeSerice fileTreeSerice;

	@GetMapping(MAPPING_URL.ALL)
	@ResponseBody
	public CommonResponse fileTree(HttpServletRequest request, HttpServletResponse response) {
		String currentPath = request.getRequestURI().replaceFirst("/fileTree", "");
		return fileTreeSerice.getFileListByRootPath(filePath, currentPath);

	}

}
