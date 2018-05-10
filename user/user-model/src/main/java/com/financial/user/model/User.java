package com.financial.user.model;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @Description: 用户信息
 * @author: 张礼佳
 * @date: 2018年5月3日 下午2:20:38
 */
@Data
public class User {

	private Integer id;// 主键
	private String nickName;// 昵称
	private String avatarUrl;// 头像
	private String name;// 姓名
	private String idcard;// 身份证
	private String sex;// 性别
	private Date birthday;// 生日
	private String nation;// 民族
	private String mobile;// 手机号
	private String email;// 邮箱
	private String address;// 地址
	private String status;// 状态
	private Date registerTime;// 注册时间
	private String registerIp;// 注册IP
	private Date loginTime;// 上次登录时间
	private String loginIp;// 删除登录IP

	private String password;// 密码

}