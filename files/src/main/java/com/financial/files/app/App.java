package com.financial.files.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {}, scanBasePackages = { App.SCAN_PACKAGE })
public class App {

	static final String SCAN_PACKAGE = "com.financial.files.config";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
