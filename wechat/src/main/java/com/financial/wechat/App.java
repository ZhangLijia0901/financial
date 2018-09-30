package com.financial.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.financial.common.xml.XmlUtils;
import com.financial.wechat.entity.TextMessage;

@SpringBootApplication
public class App {

	public static void main(String[] args) throws Exception {
		TextMessage message = new TextMessage();
		message.setContent("1");

		System.err.println(XmlUtils.messageToXml("xml", message));
		SpringApplication.run(App.class, args);
	}

}
