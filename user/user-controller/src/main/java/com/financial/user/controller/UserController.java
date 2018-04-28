package com.financial.user.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financial.user.model.User;

@RestController
@RequestMapping("user")
public class UserController {

	@GetMapping
	public User get() {
		return new User(1L, new Date());

	}
}
