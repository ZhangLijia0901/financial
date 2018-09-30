package com.financial.wechat.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financial.common.xml.XmlUtils;
import com.financial.wechat.entity.BaseMessage;
import com.financial.wechat.service.handle.MessageHandle;
import com.financial.wechat.service.handle.impl.NoSupportMessageHandle;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	private List<MessageHandle> messageHandles;

	public String message(InputStream inputStream) {
		Map<String, String> params = null;
		try {
			params = XmlUtils.parseXml(inputStream);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}

		MessageHandle handle = null;

		for (MessageHandle mh : messageHandles) {
			if (mh.support(params.get("MsgType")))
				handle = mh;
			if (!(handle instanceof NoSupportMessageHandle))
				break;
		}

		BaseMessage message = handle.parseMessage(params);

		message = handle.handle(message);

		return XmlUtils.messageToXml("xml", message);
	}
}
