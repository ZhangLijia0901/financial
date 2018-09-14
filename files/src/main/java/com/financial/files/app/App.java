package com.financial.files.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.financial.common.Constants;

@SpringBootApplication(scanBasePackageClasses = {}, scanBasePackages = { App.SCAN_PACKAGE })
@ServletComponentScan(basePackages = { Constants.FILTER_PACKAGE })
public class App {

	static final String SCAN_PACKAGE = "com.financial.files.config";

	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
