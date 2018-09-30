package com.financial.wechat.service.handle;

import java.util.Map;

import com.financial.wechat.entity.BaseMessage;

public interface MessageHandle {

	boolean support(String type);

	BaseMessage handle(BaseMessage message);

	BaseMessage parseMessage(Map<String, String> params);
}
