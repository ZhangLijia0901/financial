#### 文件信息表
DROP TABLE
IF EXISTS file_info;

CREATE TABLE file_info (
	id INT PRIMARY KEY auto_increment COMMENT '主键ID',
	token VARCHAR (64) COMMENT '文件token',
	path_name VARCHAR (512) COMMENT '文件路径及名称',
	file_name VARCHAR (64) COMMENT '文件原始名称',
	file_size INT COMMENT '文件大小',
	user_id INT COMMENT '文件归属用户',
	upload_time datetime COMMENT '文件上传时间'
)