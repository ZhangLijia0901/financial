package com.financial.user.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class TestController {
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		this.restTemplate = new RestTemplate();
		return this.restTemplate;
	}

	@RequestMapping("/file")
	public String file() {
		return restTemplate.getForObject("http://127.0.0.1:9006/fileDir", String.class);
	}

	@RequestMapping("/fileHystrix")
	public String fileHystrix() {
		return restTemplate.getForObject("http://127.0.0.1:9006/fileDir/files", String.class);
		
//		return new FileHystrixCommand(restTemplate).execute();
	}

	@RequestMapping("/index")
	public Object index() {
//		restTemplate.getForObject("", String.class);
		return "SUCCESS";
	}

}
