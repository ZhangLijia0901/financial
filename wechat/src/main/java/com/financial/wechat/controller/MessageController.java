package com.financial.wechat.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.financial.common.encrypt.OnewayEncrypt;
import com.financial.common.encrypt.OnewayEncrypt.EncryptType;
import com.financial.wechat.service.MessageService;

@Controller
@RequestMapping("wechat")
public class MessageController {

	@Value("${wechat.token}")
	private String wechatToken;

	@Autowired
	private MessageService messageService;

	@GetMapping("message")
	@ResponseBody
	public String message(String signature, String echostr, String timestamp, String nonce) {
		String[] str = { wechatToken, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];

		String digest = OnewayEncrypt.encryptToHex(bigStr, "UTF-8", EncryptType.SHA1).toLowerCase();
		if (signature.equals(digest))
			return echostr;

		return wechatToken;
	}

	@PostMapping("message")
	@ResponseBody
	public String message(HttpServletRequest request) {
		try {
			return messageService.message(request.getInputStream());
		} catch (IOException e) {
			return e.getMessage();
		}

	}
}
