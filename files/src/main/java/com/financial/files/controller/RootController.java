package com.financial.files.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@Slf4j
public class RootController {

//	@Value("${files.path}")
//	private String filePath;

//	@Autowired
//	private FileTreeSerice fileTreeSerice;

	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
//		CommonResponse commonResponse = fileTreeSerice.getFileListByRootPath(filePath, "");

//		log.info(JSON.toJSONString(commonResponse));
//		if (!CommonResponse.SUCCESS_CODE.equals(commonResponse.getRespCode()))
//			throw new CommonException(commonResponse.getRespCode(), commonResponse.getRespMsg());

//		model.addAttribute("data", commonResponse.getData());
		return "index_pc";
	}

}
