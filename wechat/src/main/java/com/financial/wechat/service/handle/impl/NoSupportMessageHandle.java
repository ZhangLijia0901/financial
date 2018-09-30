package com.financial.wechat.service.handle.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.financial.wechat.entity.BaseMessage;
import com.financial.wechat.entity.TextMessage;
import com.financial.wechat.service.handle.MessageHandle;

@Service("noSupportMessageHandle")
public class NoSupportMessageHandle implements MessageHandle {

	@Override
	public boolean support(String type) {
		return true;
	}

	@Override
	public BaseMessage handle(BaseMessage message) {
		TextMessage textMessage = new TextMessage(message);
		textMessage.setContent("访问内容不支持");
		return textMessage;
	}

	@Override
	public BaseMessage parseMessage(Map<String, String> params) {

		BaseMessage message = new BaseMessage();
		message.setFromUserName(params.get("FromUserName"));
		message.setToUserName(params.get("ToUserName"));
		return message;
	}

}
