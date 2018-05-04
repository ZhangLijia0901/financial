package com.financial.user.service;

import java.util.List;

import com.financial.user.model.UserLogin;

/**
 * 
 * @Description: 用户登录记录
 * @author: 张礼佳
 * @date: 2018年5月4日 下午2:16:37
 */
public interface UserLoginService {

	/** 提交用户登录记录 */
	void addUserLogin(UserLogin uesrLongin);

	/** 查询用户登录记录 */
	List<UserLogin> queryUserLogin(UserLogin uesrLogin);

}
