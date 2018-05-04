package com.financial.user.mapper;

import org.apache.ibatis.annotations.Param;

public interface RoleAuthMapper {
	int deleteByPrimaryKey(@Param("roleId") int roleId, @Param("authId") int authId);

	int insert(@Param("roleId") int roleId, @Param("authId") int authId);

}