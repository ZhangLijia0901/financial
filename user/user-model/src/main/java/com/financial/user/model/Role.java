package com.financial.user.model;

import lombok.Data;

/**
 * 
 * @Description: 角色信息
 * @author: 张礼佳
 * @date: 2018年5月3日 下午2:20:21
 */
@Data
public class Role {
	
    private Integer id;//主键
    private String name;//名称
    private String desc;//描述
    private String status;//状态
 
}