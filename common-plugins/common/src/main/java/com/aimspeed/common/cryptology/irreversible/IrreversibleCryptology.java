package com.aimspeed.common.cryptology.irreversible;

import java.security.NoSuchAlgorithmException;

/**
 * 不可逆的加密接口
 * @author AimSpeed
 */
public interface IrreversibleCryptology {
	
	/**
	 * 加密
	 * @author AimSpeed
	 * @param encryptStr 需要加密的字符串
	 * @return String 加密后的16位数的字符串
	 * @throws NoSuchAlgorithmException
	 */
    String encrypt(String encryptStr) throws NoSuchAlgorithmException;
	
}
