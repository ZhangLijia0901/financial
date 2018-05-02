#### 用户基本信息
DROP TABLE
IF EXISTS USER;

CREATE TABLE USER (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	nick_name VARCHAR (32) COMMENT '昵称',
	avatar_url VARCHAR (128) COMMENT '头像url',
	NAME VARCHAR (32) COMMENT '用户名',
	idcard VARCHAR (18) COMMENT '身份证',
	sex CHAR (1) COMMENT '性别',
	birthday datetime COMMENT '出生日期',
	nation VARCHAR (8) COMMENT '民族',
	mobile CHAR (11) COMMENT '手机号',
	address VARCHAR (64) COMMENT '地址',
	STATUS CHAR (2) COMMENT '状态  00-正常 | 99-销户',
	register_time datetime COMMENT '注册时间',
	register_ip VARCHAR (18) COMMENT '注册机IP',
	login_time datetime COMMENT '上次登录时间',
	login_ip VARCHAR (18) COMMENT '上次登录IP'
);

#### 用户授权信息
DROP TABLE
IF EXISTS user_auth;

CREATE TABLE user_auth (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	user_id INT COMMENT '用户ID',
	auth_type VARCHAR (2) COMMENT '授权类型 00-手机 | 01-邮箱 | 10-微信 | 11-QQ | 12-支付宝',
	auth_nick VARCHAR (32) COMMENT '三方授权昵称',
	auth_avatar_url VARCHAR (128) COMMENT '三方授权头像url',
	identifier VARCHAR (64) COMMENT '唯一标识 授权类型对应的 手机号 | 邮箱号 | openId',
	credential VARCHAR (64) COMMENT '凭证 授权类型对应的密码 | token'
);

#### 用户登录记录
DROP TABLE
IF EXISTS user_login;

CREATE TABLE user_login (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	user_id INT COMMENT '用户ID',
	user_auth_id INT COMMENT '用户授权ID',
	auth_type VARCHAR (2) COMMENT '授权类型 00-手机 | 01-邮箱 | 10-微信 | 11-QQ | 12-支付宝',
	login_device_id VARCHAR (64) COMMENT '登录设备ID',
	login_time datetime COMMENT '登录时间',
	login_ip VARCHAR (18) COMMENT '登录IP'
);

#### 角色基本信息
DROP TABLE
IF EXISTS role;

CREATE TABLE role (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	NAME VARCHAR (32) COMMENT '角色名称',
	`desc` VARCHAR (64) COMMENT '角色描述'
);

INSERT INTO role (`NAME`, `DESC`)
VALUES
	('超级管理员', '--');

INSERT INTO role (`NAME`, `DESC`)
VALUES
	('管理员', '--');

INSERT INTO role (`NAME`, `DESC`)
VALUES
	('普通用户', '--');

INSERT INTO role (`NAME`, `DESC`)
VALUES
	('访客', '--');

#### 用户角色关联
DROP TABLE
IF EXISTS user_role;

CREATE TABLE user_role (
	user_id INT COMMENT '用户ID',
	role_id INT COMMENT '角色ID',
	STATUS CHAR (2) COMMENT '状态  00-可用  99-禁用',
	PRIMARY KEY (user_id, role_id)
);

#### 权限基本信息
DROP TABLE
IF EXISTS auth;

CREATE TABLE auth (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	NAME VARCHAR (32) COMMENT '权限名称',
	`desc` VARCHAR (64) COMMENT '权限描述'
);

#### 角色权限关联
DROP TABLE
IF EXISTS role_auth;

CREATE TABLE role_auth (
	role_id INT COMMENT '角色ID',
	auth_id INT COMMENT '权限ID',
	STATUS CHAR (2) COMMENT '状态  00-可用  99-禁用',
	PRIMARY KEY (auth_id, role_id)
);

