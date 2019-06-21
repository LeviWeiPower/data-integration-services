package com.aimspeed.thirdparty.intf.utils.tencent.wechat;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信解密工具类
 * @author AimSpeed
 */
public abstract class WeChatDecodeUtils {
	
	/**
	 * 解密用户敏感数据获取用户信息 
	 * @author AimSpeed
	 * @Title getUserInfo 
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据 
	 * @param sessionKey 数据进行加密签名的密钥 
	 * @param iv 加密算法的初始向量 
	 * @return JSONObject 
	 */
	public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){ 
		// 被加密的数据 
	    byte[] dataByte = Base64.decode(encryptedData); 
	    // 加密秘钥 
	    byte[] keyByte = Base64.decode(sessionKey); 
	    // 偏移量 
	    byte[] ivByte = Base64.decode(iv); 
	    try { 
	    	
    		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要 
    		int base = 16; 
    		if (keyByte.length % base != 0) { 
	    		int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0); 
	    		byte[] temp = new byte[groups * base]; 
	    		Arrays.fill(temp, (byte) 0); 
	    		System.arraycopy(keyByte, 0, temp, 0, keyByte.length); 
	    		keyByte = temp; 
    		} 
    		// 初始化 
    		Security.addProvider(new BouncyCastleProvider()); 
    		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC"); 
    		SecretKeySpec spec = new SecretKeySpec(keyByte, "AES"); 
    		AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES"); 
    		parameters.init(new IvParameterSpec(ivByte)); 
    		cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化 
    		byte[] resultByte = cipher.doFinal(dataByte); 
    		if (null != resultByte && resultByte.length > 0) { 
    			String result = new String(resultByte, "UTF-8"); 
    			return JSON.parseObject(result); 
    		} 
	    } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException 
	    		| IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException 
	    		| InvalidParameterSpecException | NoSuchProviderException | NoSuchPaddingException e) { 
	    	e.printStackTrace();
	    }
	    return null; 
	  } 
	
	
	
}
