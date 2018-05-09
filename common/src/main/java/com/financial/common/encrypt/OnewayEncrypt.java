package com.financial.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 单向加密
 * @author: 张礼佳
 * @date: 2018年5月7日 下午2:04:36
 */
@Slf4j
public final class OnewayEncrypt {

	public static enum EncryptType {
		MD5("md5"), SHA1("sha-1"), SHA256("sha-256"), SHA512("sha-512");
		private String desc;

		private EncryptType(String desc) {
			this.desc = desc;
		}

		public String desc() {
			return this.desc;
		}
	}

	public static byte[] encrypt(String data, String charset, EncryptType encryptType) {
		if (data == null || encryptType == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance(encryptType.desc());
			byte[] bs = charset == null ? data.getBytes() : data.getBytes(charset);
			return md.digest(bs);
		} catch (NoSuchAlgorithmException e) {
			log.error(encryptType + " 加密失败 ", e);
		} catch (UnsupportedEncodingException e) {
			log.error(encryptType + " 加密失败 ", e);
		}
		return null;
	}

	/**
	 * 
	 * @Description: 单向加密
	 * @param data      加密前数据
	 * @param charset   编码
	 * @param algorithm 加密方式
	 * @return: String 加密后数据
	 */
	public static String encryptToHex(String data, String charset, EncryptType encryptType) {
		return ConvertCode.hexEncode(encrypt(data, charset, encryptType));
	}

	public static String encryptToBase64(String data, String charset, EncryptType encryptType) {
		return ConvertCode.base64Encode(encrypt(data, charset, encryptType));
	}

//*****************************************************************************************************//
//	private volatile static Encrypt encrypt = new SHA512();
//
//	public static Encrypt getEncrypt() {
//		return encrypt;
//	}
	public static interface Encrypt {
		/** 对字节做MD5返回字节 */
		byte[] encrypt(byte[] data);

		/** 对字符串进行MD5返回 字符串 */
		String encryptToHex(String data, String charset);

		/** 对字符串进行MD5返回 BASE64 字符串 */
		String encryptToBase64(String data, String charset);

		/**
		 * 
		 * @Description: 执行解密
		 * @param data      加密字节
		 * @param algorithm 解密类型
		 * @throws NoSuchAlgorithmException
		 * @return: byte[]
		 */
		default byte[] encrypt(byte[] data, String algorithm) throws NoSuchAlgorithmException {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return md.digest(data);
		}
	}

	/** 单向加密 MD5实现 */
	public static class MD5 implements Encrypt {

		@Override
		public byte[] encrypt(byte[] data) {
			try {
				return encrypt(data, "MD5");
			} catch (NoSuchAlgorithmException e) {
				log.error("MD5 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToHex(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.hexEncode(encrypt(data.getBytes(charset)));
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
				return ConvertCode.base64Encode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("MD5 加密失败 ", e);
				return null;
			}
		}

	}

	/** 单向加密 sha1 */
	public static class SHA1 implements Encrypt {

		@Override
		public byte[] encrypt(byte[] data) {
			try {
				return encrypt(data, "SHA-1");
			} catch (NoSuchAlgorithmException e) {
				log.error("SHA1 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToHex(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.hexEncode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA1 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToBase64(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.base64Encode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA1 加密失败 ", e);
				return null;
			}
		}
	}

	/** 单向加密 sha256 */
	public static class SHA256 implements Encrypt {

		@Override
		public byte[] encrypt(byte[] data) {
			try {
				return encrypt(data, "SHA-256");
			} catch (NoSuchAlgorithmException e) {
				log.error("SHA256 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToHex(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.hexEncode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA256 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToBase64(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.base64Encode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA256 加密失败 ", e);
				return null;
			}
		}
	}

	/** 单向加密 sha512 */
	public static class SHA512 implements Encrypt {

		@Override
		public byte[] encrypt(byte[] data) {
			try {
				return encrypt(data, "SHA-512");
			} catch (NoSuchAlgorithmException e) {
				log.error("SHA512 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToHex(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.hexEncode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA512 加密失败 ", e);
				return null;
			}
		}

		@Override
		public String encryptToBase64(String data, String charset) {
			if (data == null)
				return null;
			try {
				return ConvertCode.base64Encode(encrypt(data.getBytes(charset)));
			} catch (UnsupportedEncodingException e) {
				log.error("SHA512 加密失败 ", e);
				return null;
			}
		}
	}

}
