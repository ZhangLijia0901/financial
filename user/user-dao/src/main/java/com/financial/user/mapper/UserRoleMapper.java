package com.financial.user.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
	int deleteByPrimaryKey(@Param("userId") int userId, @Param("roleId") int roleId);

	int insert(@Param("userId") int userId, @Param("roleId") int roleId);

}