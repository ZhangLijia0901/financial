package com.financial.common.encrypt;

import java.util.Base64;

/**
 * 
 * @Description: 转换编码
 * @author: 张礼佳
 * @date: 2018年5月8日 下午1:30:50
 */
public class ConvertCode {
	private static final String HEX = "0123456789ABCDEF";
	private static final char[] HEX_ARRAY = HEX.toCharArray();

	/** 编码十六进制 */
	public static String hexEncode(byte[] bytes) {
		if (bytes == null)
			return null;
		StringBuilder sb = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(HEX_ARRAY[(bytes[i] & 0xf0) >> 4]).append(HEX_ARRAY[(bytes[i] & 0x0f)]);
		}
		return sb.toString();
	}

	/** 解码十六进制 */
	public static byte[] hexDecode(String data) {
		if (data == null)
			return null;

		byte[] bytes = new byte[data.length() / 2];
		for (int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) ((HEX.indexOf(data.charAt(2 * i)) << 4) + HEX.indexOf(data.charAt(2 * i + 1)) & 0xFF);

		return bytes;

	}

	/** 编码base64 */
	public static String base64Encode(byte[] bytes) {
		if (bytes == null)
			return null;
		return new String(Base64.getEncoder().encode(bytes));

	}

	/** 解码base64 */
	public static byte[] base64Decode(String data) {
		if (data == null)
			return null;
		return Base64.getDecoder().decode(data.getBytes());

	}
}
