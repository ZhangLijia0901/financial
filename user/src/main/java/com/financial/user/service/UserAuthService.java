package com.financial.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.financial.user.mapper.UserAuthMapper;
import com.financial.user.model.UserAuth;

@Service("userAuthService")
public class UserAuthService{

	@Resource
	private UserAuthMapper userAuthMapper;

	public void addUserAuth(UserAuth userAuth) {
		userAuthMapper.insert(userAuth);
	}

	public void modifyUserAuth(UserAuth userAuth) {
		userAuthMapper.updateByPrimaryKeySelective(userAuth);
	}

	public UserAuth getByIdentifier(String identifier, String credential) {
		return null;
	}

}
