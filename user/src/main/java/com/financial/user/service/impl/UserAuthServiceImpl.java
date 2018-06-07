package com.financial.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.financial.user.mapper.UserAuthMapper;
import com.financial.user.model.UserAuth;
import com.financial.user.service.UserAuthService;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

	@Resource
	private UserAuthMapper userAuthMapper;

	@Override
	public void addUserAuth(UserAuth userAuth) {
		userAuthMapper.insert(userAuth);
	}

	@Override
	public void modifyUserAuth(UserAuth userAuth) {
		userAuthMapper.updateByPrimaryKeySelective(userAuth);
	}

	@Override
	public UserAuth getByIdentifier(String identifier, String credential) {
		return null;
	}

}
