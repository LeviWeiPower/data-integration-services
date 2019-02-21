package com.aimspeed.common.cryptology.reversible;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 对称加密接口
 * @author AimSpeed
 * @Project AIM_SPEED_ARITHMETIC 
 * @Package com.aimspeed.arithmetic 
 * @FileName Arithmetic.java 
 * @ClassName Arithmetic 
 * @date  2018年4月24日 
 */
public interface SymmetryCryptology {
	
	/**
     * 加密
     * @author AimSpeed
     * @Title encrypt32bit
     * @param content 加密内容
     * @param key     加密的密钥
     * @return String 加密后的内容
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException String 
     * @date 2018年3月15日
     */
    String encrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
    /**
     * 解密
     * @author AimSpeed
     * @Title decrypt32bit
     * @param content加密内容
     * @param key     加密的密钥
     * @return String 加密后的内容
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException String 
     * @date 2018年3月15日 
     */
    String decrypt(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException;

	
}
