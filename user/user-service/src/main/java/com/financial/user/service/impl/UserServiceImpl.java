package com.financial.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.financial.user.mapper.UserMapper;
import com.financial.user.model.User;
import com.financial.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

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

}
