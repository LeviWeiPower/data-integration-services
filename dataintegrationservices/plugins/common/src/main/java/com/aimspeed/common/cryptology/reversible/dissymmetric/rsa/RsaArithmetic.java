package com.aimspeed.common.cryptology.reversible.dissymmetric.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.enums.CryptologyEnum;
import com.aimspeed.common.cryptology.reversible.dissymmetric.DissymmetricArithmetic;

/**
 * 
 * @author AimSpeed
 * @Project AIM_SPEED_CRYPTOLOGY 
 * @Package com.aimspeed.cryptology.reversible.dissymmetric.rsa
 * @FileName RSACoder.java 
 * @ClassName RSACoder 
 * @date 2018年9月1日 下午11:59:57 
 */
public class RsaArithmetic extends AbstractCryptology implements DissymmetricArithmetic {
	public static void main(String[] args) throws Exception {
		RsaArithmetic rsaArithmetic = new RsaArithmetic();
		
		boolean initFlag = rsaArithmetic.initKey();
        
		System.out.println("初始化密钥：" + initFlag);
		
        System.out.println("公钥：" + rsaArithmetic.getPubliclicKey());
        System.out.println("私钥：" + rsaArithmetic.getPrivatelicKey());
    	
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();//每次的得到的字节数组是不一样的。
        //第二步 私钥加密
        byte[] encodedData = rsaArithmetic.encryptByPrivateKey(inputStr, rsaArithmetic.getPrivatelicKey());
        //私钥进行数据签名
        String privateKeySign = rsaArithmetic.generateSignByPrivateKey(encodedData, rsaArithmetic.getPrivatelicKey());
         
        //第三步 公钥验证数字签名
        boolean flag = rsaArithmetic.pubKeyVerifyByPriKeySign(encodedData, rsaArithmetic.getPubliclicKey(), privateKeySign);
        System.out.println("公钥验证数字签名:" + flag);
        
        //用公钥对数据解密
        byte[] decodedData = rsaArithmetic.decryptByPublicKey(encodedData, rsaArithmetic.getPubliclicKey());
         
        System.out.println("data:" + data + "加密数据：" + encodedData + "    解密数据：" + decodedData);
        System.out.println("加密前数据-：" + new String(data) + "     解密后数据： " + new String(decodedData));
         
        //第四步使用公钥加密数据
        encodedData = rsaArithmetic.encryptByPublicKey(decodedData, rsaArithmetic.getPubliclicKey());
         
        //第五步 使用私钥解密数据
        decodedData = rsaArithmetic.decryptPrivateKey(encodedData, rsaArithmetic.getPrivatelicKey());
         
         
        System.out.println("data:" + data + "加密数据：" + encodedData + "    解密数据：" + decodedData);
        System.out.println("加密前数据：" + inputStr + "     解密后数据： " + new String(decodedData));
	}
	
	/**
	 * 公钥
	 */
	private String publiclicKey;

	/**
	 * 私钥
	 */
	private String privatelicKey;
	
	/**
	 * 初始化密钥
	 * @author AimSpeed
	 * @Title initKey 
	 * @return
	 * @throws Exception Map<String,Object>  
	 * @date 2018年9月2日 下午5:10:10
	 */
    public boolean initKey() {
    	//初始化
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance(CryptologyEnum.RSA.getValue());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
        //公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        
        //私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        publiclicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        privatelicKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        return true;
    }
	
    /**
     * 使用私钥加密
     * @author AimSpeed
     * @Title encryptByPrivateKey 
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     * @date 2018年9月2日 下午5:32:00
     */
    public byte[] encryptByPrivateKey(String data, String key) throws Exception{
        //进行数据加密编码
    	byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        
        //生成私钥
        KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
        Key privateKey = keyFactory.generatePrivate(keySpec);
         
        //基于私钥加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data.getBytes());
    }
    
    /**
     * 使用公钥进行数据解密
     * @author AimSpeed
     * @Title decryptByPublicKey 
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     * @date 2018年9月2日 下午5:40:05
     */
    public byte[] decryptByPublicKey( byte[] data, String key) throws Exception{
    	//解密
    	byte[] keyBytes = Base64.getDecoder().decode(key);
        
    	//获取公钥
    	X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    	KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
    	Key pKey = keyFactory.generatePublic(keySpec);
        
    	//对数据解密
    	Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    	cipher.init(Cipher.DECRYPT_MODE, pKey);
    	return cipher.doFinal(data);
    }
    
    /**
     * 使用私钥加密
     * @author AimSpeed
     * @Title encryptByPrivateKey 
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     * @date 2018年9月2日 下午5:32:00
     */
    public byte[] encryptByPrivateKey(byte[] data, String key) throws Exception{
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
        Key privateKey = keyFactory.generatePrivate(keySpec);
         
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 使用私钥生成数字签名
     * @author AimSpeed
     * @Title generateSignByPrivateKey 
     * @param data
     * @param privateKey
     * @return
     * @throws Exception String  
     * @date 2018年9月2日 下午5:35:36
     */
    public String generateSignByPrivateKey(byte[] data, String privateKey) throws Exception {
        //解密私钥
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        
        //获取到加密的私钥Key工程
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
         
        //获取私钥对象
        PrivateKey pKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         
        //用私钥生成数字签名
        Signature signature = Signature.getInstance(CryptologyEnum.SIGNATURE_MD5_WITH_RSA.getValue());
        signature.initSign(pKey);
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
     
    /**
     * 公钥验证私钥签名信息
     * @author AimSpeed
     * @Title pubKeyVerifyByPriKeySign 
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception boolean  
     * @date 2018年9月2日 下午5:37:45
     */
    public boolean pubKeyVerifyByPriKeySign(byte[] data, String publicKey, String sign) throws Exception{
        //解密公钥
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);

        //获取到加密的私钥Key工程
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
         
        //获取公钥对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        
        //用公钥生成签名信息
        Signature signature = Signature.getInstance(CryptologyEnum.SIGNATURE_MD5_WITH_RSA.getValue());
        signature.initVerify(pubKey);
        signature.update(data);
        
        //验证签名是否正常
        return signature.verify(Base64.getDecoder().decode(sign));
    }
     
    /**
     * 使用公钥加密数据
     * @author AimSpeed
     * @Title encryptByPublicKey 
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     * @date 2018年9月2日 下午5:41:15
     */
    public byte[] encryptByPublicKey( byte[] data, String key) throws Exception{
    	//解密
        byte[] keyBytes = Base64.getDecoder().decode(key);

        //获取公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
        Key pKey = keyFactory.generatePublic(keySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pKey);
        return cipher.doFinal(data);
    }
    
    /**
     * 使用私钥解密数据
     * @author AimSpeed
     * @Title decryptPrivateKey 
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     * @date 2018年9月2日 下午5:41:58
     */
    public byte[] decryptPrivateKey(byte[] data, String key) throws Exception{
    	//解密
    	byte[] keyBytes = Base64.getDecoder().decode(key);
        
    	//取得私钥
    	PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    	KeyFactory factory = KeyFactory.getInstance(CryptologyEnum.RSA.getValue());
    	Key pKey = factory.generatePrivate(encodedKeySpec);
        
    	//对数据解密
    	Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
    	cipher.init(Cipher.DECRYPT_MODE, pKey);
    	return cipher.doFinal(data);
    }

	public String getPubliclicKey() {
		return publiclicKey;
	}

	public String getPrivatelicKey() {
		return privatelicKey;
	}
    
    
   
	
}
