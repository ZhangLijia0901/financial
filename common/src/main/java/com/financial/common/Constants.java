package com.financial.common;

/**
 * 
 * @Description: 常量
 * @author: 张礼佳
 * @date: 2018年5月3日 上午9:53:40
 */
public interface Constants {

	/** 数据状态 */
	interface STATUS {
		/** 启用 */
		String ENABLE = "00";
		/** 停用 */
		String DISABLED = "99";
	}

	/** 授权类型 */
	interface AUTH_TYPE {
		/** 手机 */
		String MOBILE = "10";
		/** 邮箱 */
		String EMAIL = "11";
		/** 微信 */
		String WECHAT = "20";
		/** QQ */
		String QQ = "21";
		/** 支付宝 */
		String ALIPAY = "22";
	}

}
