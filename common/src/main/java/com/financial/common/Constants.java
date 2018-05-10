package com.financial.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 常量
 * @author: 张礼佳
 * @date: 2018年5月3日 上午9:53:40
 */
public abstract class Constants {

	/** 授权类型 与 描述 */
	public static final Map<String, String> AUTH_TYPE_MESSAGE = new HashMap<>();
	public static final String CHARSET_NAME = "utf-8";
	public static final String PRIVATE_KEY = "privateKey";
	public static final String PUBLIC_KEY = "publicKey";

	static {
		AUTH_TYPE_MESSAGE.put(AUTH_TYPE.MOBILE, "手机号");
		AUTH_TYPE_MESSAGE.put(AUTH_TYPE.EMAIL, "邮箱号");
		AUTH_TYPE_MESSAGE.put(AUTH_TYPE.WECHAT, "微信");
		AUTH_TYPE_MESSAGE.put(AUTH_TYPE.QQ, "QQ");
		AUTH_TYPE_MESSAGE.put(AUTH_TYPE.ALIPAY, "支付宝");
	}

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
