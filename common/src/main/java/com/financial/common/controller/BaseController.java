package com.financial.common.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseController {

	@Value("${server.port}")
	protected String port;
	@Value("${spring.application.name}")
	protected String appName;

	protected static interface MAPPING_URL {
		String FILE_TREE="fileTree";
		
		
		String LOGIN = "/login";
		String REGISTER = "/register";
		String ALL = "/**";
		String TOKEN = "/{token}";
		String DOWN = "/down";

		String TEST = "/test";
		String TEST1 = "/test1";

		String PROVIDER = "/provider";
		String CONSUMER = "/consumer";
	}

}
