package com.financial.files.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.financial.common.bean.response.CommonResponse;
import com.financial.common.exception.CommonException;
import com.financial.files.service.FileTreeSerice;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RootController {

	@Value("${files.path}")
	private String filePath;

	@Autowired
	private FileTreeSerice fileTreeSerice;

	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		CommonResponse commonResponse = fileTreeSerice.getFileListByRootPath(filePath, "");

		log.info(JSON.toJSONString(commonResponse));
		if (!CommonResponse.SUCCESS_CODE.equals(commonResponse.getRespCode()))
			throw new CommonException(commonResponse.getRespCode(), commonResponse.getRespMsg());
		
		model.addAttribute("data", commonResponse.getData());
		return "index_pc";
	}

}
