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
        
        
        String text = "XO4vZ1T1k2lhpxPHItmmGfhmgilZtL+ctlUn0lwbqeFl7IwG/s2euZqZgdXuVf2NuCvm9V7Fr6U5849skvAZX61JZSWD98e2zDhdIQbGJQdv/h7kqb/n/046BJQbPPVXT0i3S2Z3qScAMGLRqNp3uh8X6lyOPYsmFlPGCKTxcG4=";
        String password = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJpNzJq9vHV8lgo 6vo63YEnOjpkpJzcx5jKvfcLg53WG07CdKODnOo0RWX0CV1eUBBrD3k+FlWGSMCT 2y/zhKhwSuTjembO2dQPF5IX9PNSzQ2M3mMK6gPK6ye5ATkV12aELGkkUvrTgMh4 0L3rHCRpiRHLwyJJlx+fxMtoDiYLAgMBAAECgYBGNwxHExx+vSjMmstZi1Qz88EA K6wN4Tl+ZLq/Ru3Ij70IPN68I2LbHuW2rRxVrzAAhwQ/zVQNHE4TkghT2xOzJhJ9 NUwh1jhCSVA6ZZr7NZBVaXYt1SVP8b7UFB9mHaJSlBiRYjCtyIAGHfS/EfexJSoJ ohuevL8zIPvrJBaDEQJBAPxxYpUczXZtUJfjzS9kX2h9xSMwpv+nL5J2OpjIbNG5 iiBvTyBpJKjJSR6jyKR52V35kEzkcyrgYav7RQ3LyTUCQQCEP6K5F2ufMD/0JOYF gJE90W42fYXeWCLi15TAx2LQe7XGg7Qtt175H3x3A+bgdqdLJjrf1MSBS5YmZ67V 31o/AkEAl9Wyz7EFO2fAg7r2XLzoTbbn7aSDrVznVhZaZC6YlQQduih41SuoawS2 QAGO2q6XONi0HCuDwZQ36vM0s3mQyQJAETPp6ePHBx0SpKKMHVkdC4qLqKDpYgPa /eEHI5CMJQyCl8EYFf5NZ2CWIzEpvjcRCfB+JhGaQitf67z+/CkipwJALa1F1PiU CWbqY5qTFw2ZEfcD8cdi4fblevRN2qPdbDwkKJ2qXNnJALpKCFbAZ/GWXtwvFcB0 PHf4GzVd4UomBA==";
        
        rsaArithmetic.setPubliclicKey(password);
        byte[] decryptByPublicKey = rsaArithmetic.decryptByPublicKey(text.getBytes(), password);
        System.out.println(new String(decryptByPublicKey));
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
	 * 静态内部类的单例
	 * 外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化md5Arithmetic，所以不占内存。
	 * 当MD5Arithmetic第一次被加载时，并不需要去加载MD5ArithmeticSingleton，
	 * 只有当getInstance()方法第一次被调用时，才会去初始化md5Arithmetic，
	 * 第一次调用getInstance()方法会导致虚拟机加载MD5ArithmeticSingleton类，
	 * 这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
	 * @author AimSpeed
	 */
	private static class RsaArithmeticSingleton {
		private static RsaArithmetic rsaArithmetic = new RsaArithmetic();
		
	}
	
	/**
	 * 获取到实例对象
	 * @author AimSpeed
	 * @return RsaArithmetic
	 */
	public static RsaArithmetic getInstance() {
		return RsaArithmeticSingleton.rsaArithmetic;
	}
	
	/**
	 * 初始化密钥
	 * @author AimSpeed
	 * @return
	 * @throws Exception Map<String,Object>  
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
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     */
    public byte[] encryptByPrivateKey(String data, String key) throws Exception{
		if(null == data || "".equals(data.trim())) {
			return null;
		}
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
     * @param data
     * @param key
     * @return
     * @throws Exception byte[]  
     */
    public byte[] decryptByPublicKey( byte[] data, String key) throws Exception{
		if(null == data || data.length <= 0) {
			return null;
		}
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
     * @param data
     * @param key
     * @return byte[]  
     * @throws Exception 
     */
    public byte[] encryptByPrivateKey(byte[] data, String key) throws Exception{
		if(null == data || data.length <= 0) {
			return null;
		}
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
     * @param data
     * @param privateKey
     * @return String
     * @throws Exception
     */
    public String generateSignByPrivateKey(byte[] data, String privateKey) throws Exception {
		if(null == data || data.length <= 0) {
			return null;
		}
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
     * @param data
     * @param publicKey
     * @param sign
     * @return boolean
     * @throws Exception
     */
    public boolean pubKeyVerifyByPriKeySign(byte[] data, String publicKey, String sign) throws Exception{
		if(null == data || data.length <= 0) {
			return false;
		}
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
     * @param data
     * @param key
     * @return byte[]  
     * @throws Exception
     */
    public byte[] encryptByPublicKey( byte[] data, String key) throws Exception{
		if(null == data || data.length <= 0) {
			return null;
		}
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
     * @param data
     * @param key
     * @return byte[]  
     * @throws Exception
     */
    public byte[] decryptPrivateKey(byte[] data, String key) throws Exception{
		if(null == data || data.length <= 0) {
			return null;
		}
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

	public void setPubliclicKey(String publiclicKey) {
		this.publiclicKey = publiclicKey;
	}

	public void setPrivatelicKey(String privatelicKey) {
		this.privatelicKey = privatelicKey;
	}
    
    
   
	
}
