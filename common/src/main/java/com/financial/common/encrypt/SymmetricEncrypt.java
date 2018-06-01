package com.financial.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.financial.common.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 对称加密
 * @author: 张礼佳
 * @date: 2018年5月8日 下午3:07:30
 */
@Slf4j
public final class SymmetricEncrypt {

	public static enum EncryptType {
		DES_ECB("DES", "DES/ECB/PKCS5Padding"), DES_CBC("DES", "DES/CBC/PKCS5Padding"),
		DES_CFB("DES", "DES/CFB/PKCS5Padding"), DES_OFB("DES", "DES/OFB/PKCS5Padding"),
		DES_CTR("DES", "DES/CTR/PKCS5Padding"),
		//
		DESEDE_ECB("DESede", "desede/ECB/PKCS5Padding"), DESEDE_CBC("DESede", "desede/CBC/PKCS5Padding"),
		DESEDE_CTR("DESede", "desede/CTR/PKCS5Padding"),
		//
		AES("AES", "AES");

		private EncryptType(String type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		private String type;
		private String desc;

	}

	/**
	 * 
	 * @Description: 对称加密
	 * @param context     加密前内容
	 * @param charset     编码
	 * @param keyStr      加密密钥
	 * @param keyCode     密钥类型: HEX/BASE64/DEFAULT
	 * @param ivStr       偏移量非ECB加密必须
	 * @param encryptType 加密方式
	 * @return: String 加密后BASE64
	 */
	public static String encrypt(String context, String charset, String keyStr, String keyCode, String ivStr,
			EncryptType encryptType) {
		if ("DES".equals(encryptType.type))
			return DESUtils.encrypt(context, charset, keyStr, keyCode, ivStr, encryptType);
		else if ("DESede".equals(encryptType.type))
			return DESedeUtils.encrypt(context, charset, keyStr, keyCode, ivStr, encryptType);
		else if ("AES".equals(encryptType.type))
			return AESUtiles.encrypt(context, charset, keyStr, "DEFAULT", ivStr, encryptType);
		return null;
	}

	/**
	 * DES密钥长度8的倍数<br>
	 * 
	 * @Description: 对称加密
	 * @param context     加密前内容
	 * @param keyStr      加密密钥
	 * @param encryptType 加密方式
	 * @return: String 加密后BASE64
	 */
	public static String encrypt(String context, String key, String iv, EncryptType encryptType) {
		return encrypt(context, Constants.CHARSET_NAME, key, "DEFAULT", iv, encryptType);
	}

	/**
	 * 
	 * @Description: 对称加密-解密
	 * @param context     加密内容
	 * @param charset     编码
	 * @param keyStr      密钥
	 * @param keyCode     密钥类型: HEX/BASE64/DEFAULT
	 * @param ivStr       偏移量非ECB加密必须
	 * @param encryptType 加密方式
	 * @return: String 解密后
	 */
	public static String decrypt(String context, String charset, String keyStr, String keyCode, String ivStr,
			EncryptType encryptType) {
		if ("DES".equals(encryptType.type))
			return DESUtils.decrypt(context, charset, keyStr, keyCode, ivStr, encryptType);
		else if ("DESede".equals(encryptType.type))
			return DESedeUtils.decrypt(context, charset, keyStr, keyCode, ivStr, encryptType);
		else if ("AES".equals(encryptType.type))
			return AESUtiles.decrypt(context, charset, keyStr, "DEFAULT", ivStr, encryptType);
		return null;
	}

	/**
	 * 
	 * @Description: 对称加密-解密
	 * @param context     加密内容
	 * @param keyStr      密钥
	 * @param encryptType 加密方式
	 * @return: String 解密后
	 */
	public static String decrypt(String context, String key, String iv, EncryptType encryptType) {
		return decrypt(context, Constants.CHARSET_NAME, key, "DEFAULT", iv, encryptType);
	}

	/**
	 * 
	 * @Description: DES加解密工具类
	 * @author: 张礼佳
	 * @date: 2018年5月9日 上午11:13:31
	 */
	private static class DESUtils {
		/** 加密 */
		private final static String encrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				return ConvertCode.base64Encode(encrypt(
						charset == null ? context.getBytes() : context.getBytes(charset), key, iv, encryptType));
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}

		/** 解密 */
		private final static String decrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				byte[] encrypt = decrypt(ConvertCode.base64Decode(context), key, iv, encryptType);
				if (encrypt == null)
					return null;
				return new String(encrypt, charset);
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}

		/** 加密 */
		private static byte[] encrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				DESKeySpec dks = new DESKeySpec(key);// 原始密钥
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptType.type);// 创建密钥工厂
				SecretKey secretKey = keyFactory.generateSecret(dks);// deskey转换secretkey
				Cipher cipher = Cipher.getInstance(encryptType.desc);// Cipher对象实际完成加密操作
				if (iv == null)
					cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());// 用密匙初始化Cipher对象
				else
					cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));// 用密匙初始化Cipher对象
				return cipher.doFinal(src);// 执行加密操作

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				log.error("无效KeySpec", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				log.error("iv异常", e);
			}
			return null;
		}

		/** 解密 */
		private static byte[] decrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				DESKeySpec dks = new DESKeySpec(key);// 原始密钥
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptType.type);// 创建密钥工厂
				SecretKey secretKey = keyFactory.generateSecret(dks);// deskey转换secretkey
				Cipher cipher = Cipher.getInstance(encryptType.desc);// Cipher对象实际完成加密操作
				if (iv == null)
					cipher.init(Cipher.DECRYPT_MODE, secretKey, new SecureRandom());// 用密匙初始化Cipher对象
				else
					cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));// 用密匙初始化Cipher对象
				return cipher.doFinal(src);// 执行加密操作

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				log.error("无效KeySpec", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				log.error("iv异常", e);
			}
			return null;
		}
	}

	/**
	 * 
	 * @Description: 3DES加密工具类
	 * @author: 张礼佳
	 * @date: 2018年5月9日 下午2:55:23
	 */
	private static class DESedeUtils {
		/** 加密 */
		private static byte[] encrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				// 添加一个安全提供者
//				Security.addProvider(new BouncyCastleProvider());
				// 获得密钥
				DESedeKeySpec desKey = new DESedeKeySpec(key);
				// 创建一个密钥工厂，然后用它把DESKeySpec转化
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptType.type);
				// 获得一个密钥
				SecretKey secretKey = keyFactory.generateSecret(desKey);

				// 获取密码实例
				Cipher cipher = Cipher.getInstance(encryptType.desc);

				// 初始化密码
				if (iv != null)
					cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
				else
					cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				// 执行加密
				return cipher.doFinal(src);

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				log.error("无效KeySpec", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				log.error("iv异常", e);
			}
			return null;
		}

		/** 解密 */
		private static byte[] decrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				// 添加一个安全提供者
//				Security.addProvider(new BouncyCastleProvider());
				// 获得密钥
				DESedeKeySpec desKey = new DESedeKeySpec(key);
				// 创建一个密钥工厂，然后用它把DESKeySpec转化
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptType.type);
				// 获得一个密钥
				SecretKey secretKey = keyFactory.generateSecret(desKey);

				// 获取密码实例
				Cipher cipher = Cipher.getInstance(encryptType.desc);

				// 初始化密码
				if (iv != null)
					cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
				else
					cipher.init(Cipher.DECRYPT_MODE, secretKey);
				// 执行加密
				return cipher.doFinal(src);

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				log.error("无效KeySpec", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				log.error("iv异常", e);
			}
			return null;
		}

		/** 加密 */
		private final static String encrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				return ConvertCode.base64Encode(encrypt(
						charset == null ? context.getBytes() : context.getBytes(charset), key, iv, encryptType));
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}

		/** 解密 */
		private final static String decrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				if (charset != null)
					return new String(decrypt(ConvertCode.base64Decode(context), key, iv, encryptType), charset);
				else
					return new String(decrypt(ConvertCode.base64Decode(context), key, iv, encryptType));
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}
	}

	/**
	 * 
	 * @Description: AES加密工具类
	 * @author: 张礼佳
	 * @date: 2018年5月9日 下午3:28:10
	 */
	private static class AESUtiles {

		/** 解密 */
		private final static String decrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				byte[] encrypt = decrypt(ConvertCode.base64Decode(context), key, iv, encryptType);
				if (encrypt == null)
					return null;
				return new String(encrypt, charset);
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}

		/** 加密 */
		private final static String encrypt(String context, String charset, String keyStr, String codeType,
				String ivStr, EncryptType encryptType) {
			if (keyStr == null || context == null)
				return null;
			byte[] key = parseKey(keyStr, codeType);
			byte[] iv = null;

			if (ivStr != null)
				iv = parseKey(ivStr, codeType);
			try {
				return ConvertCode.base64Encode(encrypt(
						charset == null ? context.getBytes() : context.getBytes(charset), key, iv, encryptType));
			} catch (UnsupportedEncodingException e) {
				log.error("指定编码错误 ", e);
				return null;
			}
		}

		/** 加密 */
		private static byte[] encrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance(encryptType.type);
				kgen.init(128, new SecureRandom(key));
				Cipher cipher = Cipher.getInstance(encryptType.desc); // Cipher对象实际完成加密操作
				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), encryptType.type)); // 用密匙初始化Cipher对象
				return cipher.doFinal(src); // 执行加密操作

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			}
			return null;
		}

		/** 解密 */
		private static byte[] decrypt(byte[] src, byte[] key, byte[] iv, EncryptType encryptType) {
			try {
				KeyGenerator kgen = KeyGenerator.getInstance(encryptType.type);
				kgen.init(128, new SecureRandom(key));
				Cipher cipher = Cipher.getInstance(encryptType.desc); // Cipher对象实际完成加密操作
				cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), encryptType.type)); // 用密匙初始化Cipher对象
				return cipher.doFinal(src); // 执行加密操作

			} catch (InvalidKeyException e) {
				e.printStackTrace();
				log.error("无效密钥", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				log.error("无效算法名称", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				log.error("无效字节", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				log.error("解析异常", e);
			}
			return null;
		}
	}

	/** 解析key */
	private static byte[] parseKey(String keyStr, String keyCode) {
		byte[] key;
		if (keyCode == null)
			keyCode = "DEFAULT";
		switch (keyCode) {
		case "BASE64":
			key = ConvertCode.base64Decode(keyStr);
			break;
		case "HEX":
			key = ConvertCode.hexDecode(keyStr);
			break;
		default:
			key = keyStr.getBytes();
			break;
		}
		return key;
	}

}
