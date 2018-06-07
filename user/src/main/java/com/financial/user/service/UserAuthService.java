package com.financial.user.service;

import com.financial.user.model.UserAuth;

/**
 * 
 * @Description: 用户授权Service
 * @author: 张礼佳
 * @date: 2018年5月4日 下午1:37:39
 */
public interface UserAuthService {

	/** 添加用户授权信息 */
	void addUserAuth(UserAuth userAuth);

	/** 修改用户授权信息 */
	void modifyUserAuth(UserAuth userAuth);

	/** 根据唯一标识、授权码获取授权信息 */
	UserAuth getByIdentifier(String identifier, String credential);
}
