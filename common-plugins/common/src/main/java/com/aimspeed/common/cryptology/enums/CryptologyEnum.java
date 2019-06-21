package com.aimspeed.common.cryptology.enums;

/**
 * 加密的类型
 * @author AimSpeed
 */
public enum CryptologyEnum {
	
	/**
	 * SHA-1散列加密算法
	 */
	SHA_1("SHA-1"),
	
	/**
	 * SHA-224散列加密算法
	 */
	SHA_224("SHA-224"),
	
	/**
	 * SHA-256散列加密算法
	 */
	SHA_256("SHA-256"),
	
	/**
	 * SHA-384散列加密算法
	 */
	SHA_384("SHA-384"),
	
	/**
	 * SHA-512散列加密算法
	 */
	SHA_512("SHA-512"),
	
	/**
	 * MD5散列加密算法
	 */
	MD_5("MD5"),

	/**
	 * RSA非对称加密
	 */
	RSA("RSA"),
	
	/**
	 * 签名算法，MD5和RSA
	 */
	SIGNATURE_MD5_WITH_RSA("MD5withRSA"),
	
	/**
	 * RSA公钥算法
	 */
	RSA_PUBLIC_KEY("RSAPublicKey"),

	/**
	 * RSA私钥算法
	 */
	RSA_PRIVATE_KEY("RSAPrivatekey");
	
	private String value;
	
	private CryptologyEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
