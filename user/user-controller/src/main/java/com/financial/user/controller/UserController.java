package com.financial.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financial.common.bean.response.CommonResponse;
import com.financial.common.controller.BaseController;
import com.financial.user.model.UserAuth;
import com.financial.user.service.UserLoginService;
import com.financial.user.service.UserService;
import com.netflix.client.http.HttpRequest;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

//	@Autowired
//	private UserService userService;

	@Autowired
	private UserLoginService userLoginService;

	@PostMapping(value = { MAPPING_URL.LOGIN })
	public CommonResponse login(HttpRequest request, UserAuth userAuth) {

		return userLoginService.userLogin(userAuth);
	}

}
