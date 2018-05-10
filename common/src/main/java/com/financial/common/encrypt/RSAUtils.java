package com.financial.common.encrypt;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.financial.common.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: rsa加密工具类
 * @author: 张礼佳
 * @date: 2018年5月10日 上午10:05:25
 */
@Slf4j
public class RSAUtils {

	private final static String RSA = "RSA";
	private final static String MD5WITH_RSA = "MD5withRSA";

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** 生成RSA公私钥对 */
	public static Map<String, String> initKey(int keysize) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
		keyPairGenerator.initialize(keysize);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		byte[] privateKey = keyPair.getPrivate().getEncoded();
		byte[] publicKey = keyPair.getPublic().getEncoded();
		Map<String, String> keyMap = new HashMap<>();
		keyMap.put(Constants.PRIVATE_KEY, ConvertCode.base64Encode(privateKey));
		keyMap.put(Constants.PUBLIC_KEY, ConvertCode.base64Encode(publicKey));
		return keyMap;
	}

	/** BASE64私钥转换为PrivateKey */
	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] encodedKey = ConvertCode.base64Decode(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return keyFactory.generatePrivate(keySpec);
	}

	/** BASE64公钥转换为PublicKey */
	private static PublicKey getPublicKey(String publicKey) throws Exception {
		byte[] encodedKey = ConvertCode.base64Decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return keyFactory.generatePublic(keySpec);
	}

	/** RSA 加密 */
	public static byte[] encrypt(byte[] decryptedData, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA);// RSA默认: RSA/ECB/PKCS1Padding
		cipher.init(Cipher.ENCRYPT_MODE, key);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = decryptedData.length;
		int currentLen = 0;
		byte[] cache;

		while (currentLen < len) {
			if (currentLen + MAX_ENCRYPT_BLOCK > len)
				cache = cipher.doFinal(decryptedData, currentLen, len - currentLen);
			else
				cache = cipher.doFinal(decryptedData, currentLen, MAX_ENCRYPT_BLOCK);

			out.write(cache, currentLen, cache.length);
			currentLen += MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/** RSA 解密 */
	public static byte[] decrypt(byte[] encryptedData, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA);// RSA默认: RSA/ECB/PKCS1Padding
		cipher.init(Cipher.DECRYPT_MODE, key);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int len = encryptedData.length;
		int currentLen = 0;
		byte[] cache;
		while (currentLen < len) {
			if (currentLen + MAX_ENCRYPT_BLOCK > len)
				cache = cipher.doFinal(encryptedData, currentLen, len - currentLen);
			else
				cache = cipher.doFinal(encryptedData, currentLen, MAX_DECRYPT_BLOCK);
			out.write(cache, 0, cache.length);
			currentLen += MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** 私钥加签 */
	public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance(MD5WITH_RSA);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/** 公钥加签 */
	public static boolean verify(byte[] data, PublicKey publicKey, byte[] sign) throws Exception {
		Signature signature = Signature.getInstance(MD5WITH_RSA);
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(sign);
	}

	/**
	 * 
	 * 公钥加密
	 * 
	 * @param context   加密前内容
	 * @param publicKey 加密公钥
	 * @param charset   内容编码
	 * @return: String 加密后内容
	 */
	public static String encryptByPublicKey(String context, String publicKey, String charset) {
		if (context == null || publicKey == null)
			return null;
		try {
			byte[] data = charset == null ? context.getBytes() : context.getBytes(charset);
			byte[] encrypt = encrypt(data, getPublicKey(publicKey));

			if (encrypt != null)
				return ConvertCode.base64Encode(encrypt);
		} catch (Exception e) {
			log.error("公钥加密异常: ", e);
		}
		return null;
	}

	/**
	 * 
	 * 私钥加密
	 * 
	 * @param context   加密前内容
	 * @param publicKey 加密私钥
	 * @param charset   内容编码
	 * @return: String 加密后内容
	 */
	public static String encryptByPrivateKey(String context, String privateKey, String charset) {
		if (context == null || privateKey == null)
			return null;
		try {
			byte[] data = charset == null ? context.getBytes() : context.getBytes(charset);
			byte[] encrypt = encrypt(data, getPrivateKey(privateKey));

			if (encrypt != null)
				return ConvertCode.base64Encode(encrypt);
		} catch (Exception e) {
			log.error("私钥加密异常: ", e);
		}
		return null;
	}

	/** 私钥加签 */
	public static String sign(String context, String privateKey, String charset) {
		if (context == null || privateKey == null)
			return null;

		try {
			byte[] data = charset == null ? context.getBytes() : context.getBytes(charset);
			byte[] sign = sign(data, getPrivateKey(privateKey));

			if (sign != null)
				return ConvertCode.base64Encode(sign);
		} catch (Exception e) {
			log.error("私钥加签异常: ", e);
		}
		return null;
	}

	/**
	 * 
	 * 公钥解密
	 * 
	 * @param context   加密内容
	 * @param publicKey 公钥
	 * @param charset   内容编码
	 * @return: String 解密后内容
	 */
	public static String decryptByPublicKey(String ciphertext, String publicKey, String charset) {
		if (ciphertext == null || publicKey == null)
			return null;
		try {
			byte[] data = ConvertCode.base64Decode(ciphertext);
			byte[] decrypt = decrypt(data, getPublicKey(publicKey));

			if (decrypt != null)
				if (charset != null)
					return new String(decrypt, charset);
				else
					return new String(decrypt);
		} catch (Exception e) {
			log.error("公钥解密异常: ", e);
		}
		return null;
	}

	/**
	 * 
	 * 私钥解密
	 * 
	 * @param context    加密内容
	 * @param privateKey 私钥
	 * @param charset    内容编码
	 * @return: String 解密后内容
	 */
	public static String decryptByPrivateKey(String ciphertext, String privateKey, String charset) {
		if (ciphertext == null || privateKey == null)
			return null;
		try {
			byte[] data = ConvertCode.base64Decode(ciphertext);
			byte[] decrypt = decrypt(data, getPrivateKey(privateKey));

			if (decrypt != null)
				if (charset != null)
					return new String(decrypt, charset);
				else
					return new String(decrypt);
		} catch (Exception e) {
			log.error("私钥解密异常: ", e);
		}
		return null;
	}

	/** 公钥验签 */
	public static boolean verify(String context, String publicKey, String charset, String sign) {
		if (context == null || publicKey == null || sign == null)
			return false;

		try {
			byte[] data = charset == null ? context.getBytes() : context.getBytes(charset);
			return verify(data, getPublicKey(publicKey), ConvertCode.base64Decode(sign));
		} catch (Exception e) {
			log.error("公钥验签异常: ", e);
		}
		return false;
	}
}
