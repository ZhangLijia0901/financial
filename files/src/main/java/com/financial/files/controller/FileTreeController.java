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
import com.financial.common.controller.BaseController.MAPPING_URL;
import com.financial.files.service.FileTreeSerice;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * *文件目录结构 Controller
 * 
 * @author: 张礼佳
 * @date: 2018年6月8日 上午11:04:11
 */
@Controller
@RequestMapping(value = { MAPPING_URL.FILE_DIR })
@Slf4j
public class FileTreeController extends BaseController {

	@Value("${files.path}")
	private String filePath;

	@Autowired
	private FileTreeSerice fileTreeSerice;

	@GetMapping(MAPPING_URL.ALL)
	@ResponseBody
	public CommonResponse fileTree(HttpServletRequest request, HttpServletResponse response) {

		long startTime = System.currentTimeMillis();

		String currentPath = request.getRequestURI().replaceFirst(MAPPING_URL.FILE_DIR, "");
		CommonResponse commonResponse = fileTreeSerice.getFileListByRootPath(filePath, currentPath);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		log.info("耗时： [{} ms]", (endTime - startTime));
		return commonResponse;

	}
	
	@GetMapping("/files")
	@ResponseBody
	public String fi() {
		return new FileHystrixCommand(fileTreeSerice).execute();
	}

}
