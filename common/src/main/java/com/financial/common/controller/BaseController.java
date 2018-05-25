package com.financial.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseController {

	@Value("${server.port}")
	protected String port;
	@Value("${spring.application.name}")
	protected String appName;

	protected interface MAPPING_URL {
		String LOGIN = "/login";
		String REGISTER = "/register";

		String TEST = "/test";
		String TEST1 = "/test1";

		String PROVIDER = "/provider";
		String CONSUMER = "/consumer";
	}

	/**** 获取用户原始IP ****/
	protected String getOriginalIp(HttpServletRequest request) {
		if (request.getHeader("x-natapp-ip") != null)
			return request.getHeader("x-natapp-ip");
		if (request.getHeader("x-real-ip") != null)
			return request.getHeader("x-real-ip");

		if (request.getHeader("x-forwarded-for") != null)
			return request.getHeader("x-forwarded-for").split(",")[0];

		return request.getRemoteAddr();

	}
}
