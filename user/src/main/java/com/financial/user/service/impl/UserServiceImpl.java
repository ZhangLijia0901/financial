package com.financial.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.financial.common.Constants;
import com.financial.common.bean.response.CommonResponse;
import com.financial.user.mapper.UserAuthMapper;
import com.financial.user.mapper.UserMapper;
import com.financial.user.model.User;
import com.financial.user.model.UserAuth;
import com.financial.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserAuthMapper userAuthMapper;

	@Override
	public void addUser(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public void modifyUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);

	}

	@Override
	public void removeUser(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User byId(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> queryList(User user) {
		return userMapper.selectList(user);
	}

	@Override
	public CommonResponse register(User user) {
		if (user == null)
			return new CommonResponse("9999", "用户信息空", null);
		if (StringUtils.isEmpty(user.getMobile()) && StringUtils.isEmpty(user.getEmail())
				&& user.getUserAuths() == null)
			return new CommonResponse("9999", "手机号、邮箱号不可同空", null);
		if (StringUtils.isEmpty(user.getPassword()) && user.getUserAuths() == null)
			return new CommonResponse("9999", "登录凭证不可为空", null);

		user.setRegisterTime(new Date());// 注册时间
		user.setStatus(Constants.STATUS.ENABLE);// 状态

		// 添加用户信息
		addUser(user);
		log.debug("添加用户信息完成  userId:[{}]", user.getId());

		// 添加用户授权
		if (user.getUserAuths() != null && user.getUserAuths().size() > 0)
			user.getUserAuths().forEach(o -> {
				if (o == null)
					return;
				o.setUserId(user.getId());// 设置用户ID
				o.setAuthDate(new Date());// 授权时间
				userAuthMapper.insertSelective(o);
			});

		UserAuth userAuth;
		if (StringUtils.hasLength(user.getMobile())) {
			userAuth = new UserAuth();
			userAuth.setAuthAvatarUrl(user.getAvatarUrl());// 头像地址
			userAuth.setUserId(user.getId());// 设置用户ID
			userAuth.setAuthDate(new Date());// 授权时间
			userAuth.setAuthNick(user.getNickName());// 昵称
			userAuth.setAuthType(Constants.AUTH_TYPE.MOBILE);// 授权类型
			userAuth.setIdentifier(user.getMobile());// 唯一标识
			userAuth.setCredential(user.getPassword());// 授权凭证
			userAuthMapper.insertSelective(userAuth);// 添加授权
		}

		if (StringUtils.hasLength(user.getEmail())) {
			userAuth = new UserAuth();
			userAuth.setAuthAvatarUrl(user.getAvatarUrl());// 头像地址
			userAuth.setUserId(user.getId());// 设置用户ID
			userAuth.setAuthDate(new Date());// 授权时间
			userAuth.setAuthNick(user.getNickName());// 昵称
			userAuth.setAuthType(Constants.AUTH_TYPE.EMAIL);// 授权类型
			userAuth.setIdentifier(user.getEmail());// 唯一标识
			userAuth.setCredential(user.getPassword());// 授权凭证
			userAuthMapper.insertSelective(userAuth);// 添加授权
		}

		return new CommonResponse("0000", "SUCCESS", null);
	}

}
