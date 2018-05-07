package com.financial.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 单向加密
 * @author: 张礼佳
 * @date: 2018年5月7日 下午2:04:36
 */
@Slf4j
public final class OnewayEncrypt {

	private static final char[] hex = "0123456789abcdef".toCharArray();
	private volatile static Encrypt encrypt = new MD5();

	public static Encrypt getEncrypt() {
		return encrypt;
	}

	public static interface Encrypt {
		/** 对字节做MD5返回字节 */
		byte[] encrypt(byte[] data);

		/** 对字符串进行MD5返回 字符串 */
		String encryptHex(String data, String charset);

		/** 对字符串进行MD5返回 BASE64 字符串 */
		String encryptToBase64(String data, String charset);
	}

	/** 单向加密 MD5实现 */
	public static class MD5 implements Encrypt {

		@Override
		public byte[] encrypt(byte[] data) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				return md.digest(data);

			} catch (NoSuchAlgorithmException e) {
				log.error("MD5 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptHex(String data, String charset) {
			if (data == null)
				return null;
			try {
				return hex(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("MD5 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToBase64(String data, String charset) {
			if (data == null)
				return null;
			try {
				return base64(encrypt(data.getBytes(charset)), charset);
			} catch (UnsupportedEncodingException e) {
				log.error("MD5 加密失败 ", e);
				return null;
			}
		}
	}

	public static String hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(hex[(bytes[i] & 0xf0) >> 4]).append(hex[(bytes[i] & 0x0f)]);
		}
		return sb.toString();
	}

	public static String base64(byte[] bytes, String charset) throws UnsupportedEncodingException {
		return new String(Base64.getEncoder().encode(bytes), charset);
	}
}
