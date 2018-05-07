package com.financial.common.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @Description: 公共响应字段
 * @author: 张礼佳
 * @date: 2018年5月7日 上午11:13:18
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommonResponse {

	private String respCode;
	private String respMsg;

	private Object data;
}
