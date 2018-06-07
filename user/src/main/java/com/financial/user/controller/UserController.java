package com.financial.user.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.financial.common.bean.response.CommonResponse;
import com.financial.common.controller.BaseController;
import com.financial.common.request.HttpRequestUtils;
import com.financial.user.model.User;
import com.financial.user.model.UserAuth;
import com.financial.user.model.UserLogin;
import com.financial.user.service.UserLoginService;
import com.financial.user.service.UserService;

//@RestController
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserLoginService userLoginService;

	@PostMapping(value = { MAPPING_URL.LOGIN })
	public CommonResponse login(HttpServletRequest request, UserAuth userAuth) {

		if (userAuth != null) {
			UserLogin userLogin = new UserLogin();
			userLogin.setLoginIp(HttpRequestUtils.getOriginalIp(request));
			userAuth.setUserLogin(userLogin);
		}
		return userLoginService.userLogin(userAuth);
	}

	@GetMapping(value = { MAPPING_URL.REGISTER })
	@PostMapping(value = { MAPPING_URL.REGISTER })
	public CommonResponse register(HttpServletRequest request, HttpServletResponse response, User user) {
		if (user != null)
			user.setRegisterIp(HttpRequestUtils.getOriginalIp(request));
		return userService.register(user);
	}

	@GetMapping(value = { MAPPING_URL.TEST })
	public void test(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.write("<script> location.href='http://www.baidu.com'; </script>");
		out.flush();
		out.close();
	}

	@GetMapping(value = { MAPPING_URL.TEST1 })
	public void test1(HttpServletRequest req, HttpServletResponse resp, boolean isOnLine) throws IOException {
		File file = new File("E:/新建文本文档.txt");
		InputStream inputStream = new FileInputStream(file);
		OutputStream outputStream = resp.getOutputStream();
		byte[] bs = new byte[1024];
		resp.setContentLength((int) file.length());

		String fileName = file.getName();
		fileName = new String(fileName.getBytes(), "ISO-8859-1");
		if (isOnLine) {
			// 在线打开
			URL u = file.toURI().toURL();
			resp.setContentType(u.openConnection().getContentType());
			resp.setHeader("Content-Disposition", "inline; filename=" + fileName);

		} else {// 下载
//			resp.setContentType("application/x-msdownload");
//			resp.setContentType("bin");
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		}

		int len = 0;
		while ((len = inputStream.read(bs)) > 0)
			outputStream.write(bs, 0, len);
		inputStream.close();
		outputStream.close();

	}
}
