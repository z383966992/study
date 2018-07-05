package utils;

import java.util.Arrays;
import java.util.Collection;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import clojure.main;

public class DesUtils {

	/**
	 * 加密
	 * 
	 * @param input
	 *            需加密的文本
	 * @param key
	 *            秘钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String input, String key) throws Exception {
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			throw new Exception("加密失败", e);
		}
		return new String(Base64.encodeBase64(crypted));
	}

	/**
	 * 解密
	 * 
	 * @param input
	 *            需解密的加密内容
	 * @param key
	 *            秘钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String input, String key) throws Exception {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			throw new Exception("解密失败", e);
		}
		return new String(output);
	}
	
	public static void main(String[] args) {
		Integer [] array = new Integer[]{4,2,6,8,3,1,6};
		java.util.Collections.sort(Arrays.asList(array));
	}
}