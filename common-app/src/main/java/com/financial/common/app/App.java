package com.financial.common.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { App.SCAN_PACKAGE }, scanBasePackageClasses = {})
//@ServletComponentScan(basePackages = {})
public class App {

	protected static final String SCAN_PACKAGE = "com.financial.common.config";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}