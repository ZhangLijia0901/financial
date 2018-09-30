package com.financial.wechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMessage {
	/** 开发者微信 */
	protected String ToUserName;

	/** 发送方openid */
	protected String FromUserName;

	/** 创建时间 */
	protected long CreateTime;

	/** 内容类型 */
	protected String MsgType;
}
