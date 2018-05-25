package com.financial.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financial.common.Constants;
import com.financial.common.bean.response.CommonResponse;
import com.financial.common.controller.BaseController;
import com.financial.user.model.User;
import com.financial.user.model.UserAuth;
import com.financial.user.model.UserLogin;
import com.financial.user.service.UserLoginService;
import com.financial.user.service.UserService;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserLoginService userLoginService;

	@PostMapping(value = { MAPPING_URL.LOGIN })
	public CommonResponse login(HttpServletRequest request, UserAuth userAuth) {
		
		if (userAuth != null) {
			UserLogin userLogin = new UserLogin();
			userLogin.setLoginIp(getOriginalIp(request));
			userAuth.setUserLogin(userLogin);
		}
		return userLoginService.userLogin(userAuth);
	}

	@GetMapping(value = { MAPPING_URL.REGISTER })
	@PostMapping(value = { MAPPING_URL.REGISTER })
	public CommonResponse register(HttpServletRequest request, HttpServletResponse response, User user) {
		if (user != null)
			user.setRegisterIp(getOriginalIp(request));
		return userService.register(user);
	}

}
