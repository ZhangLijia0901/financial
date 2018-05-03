package com.financial.user.model;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @Description: 用户登录记录
 * @author: 张礼佳
 * @date: 2018年5月3日 下午2:21:03
 */
@Data
public class UserLogin {

	private Integer id;// 主键
	private Integer userId;// 用户ID
	private Integer userAuthId;// 用户授权ID
	private String authType;// 授权类型
	private String loginDeviceId;// 登录设备ID
	private Date loginTime;// 登录时间
	private String loginIp;// 登录IP

}