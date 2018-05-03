package com.financial.user.model;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @Description: 用户权限
 * @author: 张礼佳
 * @date: 2018年5月3日 下午2:20:50
 */
@Data
public class UserAuth {

	private Integer id;// 主键
	private Integer userId;// 用户ID
	private String authType;// 授权类型
	private String authNick;// 授权昵称
	private String authAvatarUrl;// 授权头像
	private String identifier;// 唯一标识
	private String credential;// 授权凭证
	private Date authDate;// 授权时间

}