package com.financial.common.config;

//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ComponentScan(basePackages = { ScanPackage.PACKAGE_CONTROLLER, ScanPackage.PACKAGE_SERICE,
//		ScanPackage.PACKAGE_REMOTE })
public class ScanPackage {
	public final static String PACKAGE_CONTROLLER = "com.financial.user.controller";
	public final static String PACKAGE_SERICE = "com.financial.user.service";
	public final static String PACKAGE_REMOTE = "com.financial.user.remote";

}
