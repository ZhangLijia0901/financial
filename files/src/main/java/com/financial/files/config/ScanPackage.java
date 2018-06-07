package com.financial.files.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { ScanPackage.PACKAGE_CONTROLLER, ScanPackage.PACKAGE_SERICE })
public class ScanPackage {
	public final static String PACKAGE_CONTROLLER = "com.financial.files.controller";
	public final static String PACKAGE_SERICE = "com.financial.files.service";

}
