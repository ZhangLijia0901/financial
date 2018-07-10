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

	public static String SUCCESS_CODE = "0000";
	public static String SUCCESS_MSG = "SUCCESS";

	public static String ERROR_CODE = "9999";

	private String respCode = SUCCESS_CODE;
	private String respMsg = SUCCESS_MSG;

	private Object data;

	public CommonResponse(Object data) {
		this.data = data;
	}

}
