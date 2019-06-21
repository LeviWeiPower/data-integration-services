package com.aimspeed.common.cryptology.reversible.symmetry;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.reversible.SymmetryCryptology;

/**
 * Aes加密实现
 * @author AimSpeed
 */
public class AesArithmetic extends AbstractCryptology implements SymmetryCryptology {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		System.out.println(AesArithmetic.getInstance().encrypt("123456", "321"));
		System.out.println(AesArithmetic.getInstance().decrypt("XO4vZ1T1k2lhpxPHItmmGfhmgilZtL+ctlUn0lwbqeFl7IwG/s2euZqZgdXuVf2NuCvm9V7Fr6U5849skvAZX61JZSWD98e2zDhdIQbGJQdv/h7kqb/n/046BJQbPPVXT0i3S2Z3qScAMGLRqNp3uh8X6lyOPYsmFlPGCKTxcG4=", 
											"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJpNzJq9vHV8lgo 6vo63YEnOjpkpJzcx5jKvfcLg53WG07CdKODnOo0RWX0CV1eUBBrD3k+FlWGSMCT 2y/zhKhwSuTjembO2dQPF5IX9PNSzQ2M3mMK6gPK6ye5ATkV12aELGkkUvrTgMh4 0L3rHCRpiRHLwyJJlx+fxMtoDiYLAgMBAAECgYBGNwxHExx+vSjMmstZi1Qz88EA K6wN4Tl+ZLq/Ru3Ij70IPN68I2LbHuW2rRxVrzAAhwQ/zVQNHE4TkghT2xOzJhJ9 NUwh1jhCSVA6ZZr7NZBVaXYt1SVP8b7UFB9mHaJSlBiRYjCtyIAGHfS/EfexJSoJ ohuevL8zIPvrJBaDEQJBAPxxYpUczXZtUJfjzS9kX2h9xSMwpv+nL5J2OpjIbNG5 iiBvTyBpJKjJSR6jyKR52V35kEzkcyrgYav7RQ3LyTUCQQCEP6K5F2ufMD/0JOYF gJE90W42fYXeWCLi15TAx2LQe7XGg7Qtt175H3x3A+bgdqdLJjrf1MSBS5YmZ67V 31o/AkEAl9Wyz7EFO2fAg7r2XLzoTbbn7aSDrVznVhZaZC6YlQQduih41SuoawS2 QAGO2q6XONi0HCuDwZQ36vM0s3mQyQJAETPp6ePHBx0SpKKMHVkdC4qLqKDpYgPa /eEHI5CMJQyCl8EYFf5NZ2CWIzEpvjcRCfB+JhGaQitf67z+/CkipwJALa1F1PiU CWbqY5qTFw2ZEfcD8cdi4fblevRN2qPdbDwkKJ2qXNnJALpKCFbAZ/GWXtwvFcB0 PHf4GzVd4UomBA=="));
	}
	
	/**
	 * 静态内部类的单例
	 * 外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化md5Arithmetic，所以不占内存。
	 * 当MD5Arithmetic第一次被加载时，并不需要去加载MD5ArithmeticSingleton，
	 * 只有当getInstance()方法第一次被调用时，才会去初始化md5Arithmetic，
	 * 第一次调用getInstance()方法会导致虚拟机加载MD5ArithmeticSingleton类，
	 * 这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
	 * @author AimSpeed
	 */
	private static class AesArithmeticSingleton {
		private static AesArithmetic aesArithmetic = new AesArithmetic();
		
	}
	
	/**
	 * 获取到实例对象
	 * @author AimSpeed
	 * @return RsaArithmetic
	 */
	public static AesArithmetic getInstance() {
		return AesArithmeticSingleton.aesArithmetic;
	}
	
	/*
	 * 使用Ase进行加密
	 * @author AimSpeed
	 * @param content
	 * @param key
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @overridden @see com.aimspeed.arithmetic.aes.AesArithmetic#encrypt32bit(java.lang.String, java.lang.String)
	 */
	@Override
    public String encrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		if(null == content || "".equals(content.trim())) {
			return null;
		}
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] byteRresult = cipher.doFinal(byteContent);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteRresult.length; i++) {
            String hex = Integer.toHexString(byteRresult[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

	/*
	 * 使用Ase进行解密
	 * @author AimSpeed
	 * @param content
	 * @param key
	 * @return String
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @overridden @see com.aimspeed.arithmetic.aes.AesArithmetic#decrypt32bit(java.lang.String, java.lang.String)
	 */
	@Override
    public String decrypt(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		if(null == content || "".equals(content.trim())) {
			return null;
		}
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] result = cipher.doFinal(byteRresult);
        return new String(result);
    }
	
	
}
