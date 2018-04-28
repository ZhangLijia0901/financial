package com.financial.user.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = { App.SCAN_PACKAGE })
@EnableEurekaClient
public class App {

	protected static final String SCAN_PACKAGE = "com.financial.user.config";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

}
