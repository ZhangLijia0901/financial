package com.financial.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.financial.user.model.UserAuth;

/**
 * 
 * @Description: 用户授权Mapper
 * @author: 张礼佳
 * @date: 2018年5月4日 下午2:01:33
 */
public interface UserAuthMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserAuth record);

	int insertSelective(UserAuth record);

	UserAuth selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserAuth record);

	int updateByPrimaryKey(UserAuth record);

	UserAuth selectByIdentifier(@Param("identifier") String identifier, @Param("credential") String credential);
}