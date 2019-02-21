package com.aimspeed.common.cryptology.irreversible;

import java.security.NoSuchAlgorithmException;

/**
 * 不可逆的加密接口
 * @author AimSpeed
 * @Project AIM_SPEED_CRYPTOLOGY 
 * @Package com.aimspeed.cryptology.irreversible
 * @FileName IrreversibleCryptology.java 
 * @ClassName IrreversibleCryptology 
 * @date 2018年9月1日 下午11:42:25 
 */
public interface IrreversibleCryptology {
	
	/**
	 * 加密
	 * @author AimSpeed
	 * @Title encrypt16bit
	 * @param encryptStr 需要加密的字符串
	 * @return String 加密后的16位数的字符串
	 * @throws NoSuchAlgorithmException String 
	 * @date 2018年3月15日 
	 */
    String encrypt(String encryptStr) throws NoSuchAlgorithmException;
	
}
