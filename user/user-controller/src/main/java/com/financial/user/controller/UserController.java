package com.financial.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.client.http.HttpRequest;

@RestController
@RequestMapping("user")
public class UserController {

	@PostMapping("login")
	public void login(HttpRequest request) {
	}

}
