package com.financial.user.dao;

import com.financial.user.model.User;

public interface UserDao {

	int insert(User user);

	int update(User user);

	int delete(User User);
}
