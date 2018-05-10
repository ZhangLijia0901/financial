package com.financial.user.service;

import java.util.List;

import com.financial.common.bean.response.CommonResponse;
import com.financial.user.model.User;

/**
 * 
 * @Description: 用户Service
 * @author: 张礼佳
 * @date: 2018年5月3日 下午2:15:46
 */
public interface UserService {

	/** 添加用户 */
	void addUser(User user);

	/** 修改用户 */
	void modifyUser(User user);

	/** 删除用户 */
	void removeUser(int id);

	/** 按id查询 */
	User byId(int id);

	/** 查询用户列表 */
	List<User> queryList(User user);

	/** 注册用户 */
	CommonResponse register(User user);
}
