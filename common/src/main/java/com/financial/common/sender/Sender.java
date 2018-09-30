package com.financial.common.sender;

import java.util.Map;

/**
 * 
 * @Description: 发送通知
 * @author: 张礼佳
 * @date: 2018年9月11日 上午10:32:20
 */
public interface Sender {
	String RECEIVE_ID = "ReceiveId";// 接收人
	String SENDER_TIME = "SenderTime";// 发送时间
	String SENDER_CONTEXT = "SenderContext";// 发送内容

	/** 发送通知 */
	boolean sender(Map<String, Object> param);
}
