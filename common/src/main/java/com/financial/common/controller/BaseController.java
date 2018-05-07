package com.financial.common.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseController {

	@Value("${server.port}")
	protected String port;
	@Value("${spring.application.name}")
	protected String appName;

	protected interface MAPPING_URL {
		String LOGIN = "/login";
		String TEST = "/test";
		String PROVIDER = "/provider";
		String CONSUMER = "/consumer";
	}
}
