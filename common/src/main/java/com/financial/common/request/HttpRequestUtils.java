package com.financial.common.request;

import javax.servlet.http.HttpServletRequest;

import com.financial.common.Constants.AUTH_TYPE;;

/**
 * 
 * HttpRequest工具类
 * 
 * @author: 张礼佳
 * @date: 2018年5月28日 下午3:13:14
 */
public class HttpRequestUtils {

	/**** 获取用户原始IP ****/
	public static String getOriginalIp(HttpServletRequest request) {
		if (request.getHeader("x-natapp-ip") != null)
			return request.getHeader("x-natapp-ip");
		if (request.getHeader("x-real-ip") != null)
			return request.getHeader("x-real-ip");

		if (request.getHeader("x-forwarded-for") != null)
			return request.getHeader("x-forwarded-for").split(",")[0];

		return request.getRemoteAddr();

	}

	/** 获取用户浏览器类型 */
	public static UserAgent getUserAgent(HttpServletRequest request) {
		return UserAgent.getUserAgent(request.getHeader("user-agent").toLowerCase());
	}

	public enum UserAgent {
		ALI_PAY(AUTH_TYPE.ALIPAY, "alipay"), 
		WE_CHAT(AUTH_TYPE.WECHAT, "micromessenger"), 
		QQ(AUTH_TYPE.QQ, "qq"),
		UNKNOWN("", "");

		private String authType;
		private String regex;

		UserAgent(String authType, String regex) {
			this.authType = authType;
			this.regex = regex;
		}

		boolean match(String userAgent) {
			if (userAgent.indexOf(regex) != -1)
				return true;
			return false;
		}

		static UserAgent getUserAgent(String userAgent) {
			if (userAgent == null)
				return UNKNOWN;

			for (UserAgent ua : values()) {
				if (ua.match(userAgent))
					return ua;
			}
			return UNKNOWN;
		}

		public String getAuthType() {
			return authType;
		}
	}

}
