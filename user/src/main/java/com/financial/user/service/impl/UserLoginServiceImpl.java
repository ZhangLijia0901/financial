package com.financial.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.financial.common.bean.response.CommonResponse;
import com.financial.user.mapper.UserAuthMapper;
import com.financial.user.mapper.UserLoginMapper;
import com.financial.user.mapper.UserMapper;
import com.financial.user.model.User;
import com.financial.user.model.UserAuth;
import com.financial.user.model.UserLogin;
import com.financial.user.service.UserLoginService;

import lombok.extern.slf4j.Slf4j;

@Service("userLoginService")
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

	@Resource
	private UserAuthMapper userAuthMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserLoginMapper userLoginMapper;

	@Override
	public void addUserLogin(UserLogin uesrLongin) {
		userLoginMapper.insertSelective(uesrLongin);
	}

	@Override
	public List<UserLogin> queryUserLogin(UserLogin userLogin) {
		return userLoginMapper.selectList(userLogin);

	}

	@Override
	public CommonResponse userLogin(UserAuth userAuth) {
		log.debug("用户登录授权信息 : [{}]", JSON.toJSON(userAuth));
		// 根据唯一标识、授权凭证获取用户授权
		UserAuth ua = userAuthMapper.selectByIdentifier(userAuth.getIdentifier(), userAuth.getCredential());
		if (ua == null)
			return new CommonResponse("9999", "登录失败-用户授权不存在", null);

		// 根据授权用户获取用户信息
		User user = userMapper.selectByPrimaryKey(ua.getUserId()); // TODO 缺少用户权限查询
		if (user == null)
			return new CommonResponse("9999", "登录失败-用户不存在", null);

		// 添加用户登录记录
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(user.getId());// 用户ID
		userLogin.setUserAuthId(ua.getId());// 用户授权ID
		userLogin.setAuthType(ua.getAuthType());// 用户授权类型
		userLogin.setLoginIp(userAuth.getUserLogin().getLoginIp());// 用户登录IP
		userLogin.setLoginTime(new Date());// 用户登录时间
		userLoginMapper.insertSelective(userLogin);

		return new CommonResponse("0000", "SUCCESS", user);
	}

}
