package com.financial.wechat.service.handle.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financial.wechat.entity.BaseMessage;
import com.financial.wechat.entity.TextMessage;
import com.financial.wechat.service.handle.MessageHandle;

@Service("textMessageHandle")
public class TextMessageHandle implements MessageHandle {

	private final String xiaodou = "http://api.douqq.com/?key=d1JMNz1rPTVwTVFSbGJDZXRKN01BL011dkJjQUFBPT0&msg=";//

//	private final String qyk = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";//青云客

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public boolean support(String type) {
		if ("text".equals(type))
			return true;
		return false;
	}

	@Override
	public BaseMessage handle(BaseMessage message) {
		TextMessage textMessage = (TextMessage) message;

		ResponseEntity<String> res = restTemplate.getForEntity(xiaodou + textMessage.getContent(), String.class);

		textMessage = new TextMessage(message);
		textMessage.setContent(res.getBody());
		return textMessage;
	}

	@Override
	public TextMessage parseMessage(Map<String, String> params) {

		TextMessage message = new TextMessage();
		message.setContent(params.get("Content"));
		message.setFromUserName(params.get("FromUserName"));
		message.setToUserName(params.get("ToUserName"));

		return message;
	}

}
