package com.financial.wechat.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextMessage extends BaseMessage {

	public TextMessage() {
		this.MsgType = "text";
		this.CreateTime = System.currentTimeMillis() / 1000;
	}

	public TextMessage(BaseMessage message) {
		this();
		this.ToUserName = message.FromUserName;
		this.FromUserName = message.ToUserName;
	}

	/** 内容 */
	private String Content;
}
