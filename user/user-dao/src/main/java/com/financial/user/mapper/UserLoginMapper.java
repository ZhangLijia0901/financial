package com.financial.user.mapper;

import java.util.List;

import com.financial.user.model.UserLogin;

public interface UserLoginMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserLogin record);

	int insertSelective(UserLogin record);

	UserLogin selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserLogin record);

	int updateByPrimaryKey(UserLogin record);

	List<UserLogin> selectList(UserLogin userLogin);
}