package com.aimspeed.common.cryptology.irreversible;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.enums.CryptologyEnum;

/**
 * SHA加密工具实现
 * @author AimSpeed
 * @Project AIM_SPEED_ARITHMETIC
 * @Package com.aimspeed.common.arithmetic.sha
 * @FileName SHAArithmeticUtils.java
 * @ClassName SHAArithmeticUtils
 * @date 2018年3月27日
 */
public class SHAArithmetic extends AbstractCryptology implements IrreversibleCryptology {
	
	/*
	 * 默认使用256进行加密
	 * @Title encrypt 
	 * @param encryptStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @date 2018年9月1日 下午11:54:21
	 * @overridden @see com.aimspeed.cryptology.irreversible.IrreversibleCryptology#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String encryptStr) throws NoSuchAlgorithmException {
		return encryptSHA256(encryptStr);
	}
	
	/**
	 * 使用SHA1进行加密
	 * @author AimSpeed
	 * @Title encryptSHA1 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:54:45
	 */
	public String encryptSHA1(String strText) throws NoSuchAlgorithmException {
        return encrypt(strText, CryptologyEnum.SHA_1.getValue());
    }

	/**
	 * 使用SHA224进行加密
	 * @author AimSpeed
	 * @Title encryptSHA224 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:54:52
	 */
    public String encryptSHA224(String strText) throws NoSuchAlgorithmException {
        return encrypt(strText, CryptologyEnum.SHA_224.getValue());
    }
    
	/**
	 * 使用SHA256进行加密
	 * @author AimSpeed
	 * @Title encryptSHA256 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:55:01
	 */
    public String encryptSHA256(String strText) throws NoSuchAlgorithmException {
        return encrypt(strText, CryptologyEnum.SHA_256.getValue());
    }

	/**
	 * 使用SHA384进行加密
	 * @author AimSpeed
	 * @Title encryptSHA384 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:55:09
	 */
    public String encryptSHA384(String strText) throws NoSuchAlgorithmException {
        return encrypt(strText, CryptologyEnum.SHA_384.getValue());
    }
    
	/**
	 * 使用SHA512进行加密
	 * @author AimSpeed
	 * @Title encryptSHA512 
	 * @param strText
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:55:16
	 */
    public String encryptSHA512(String strText) throws NoSuchAlgorithmException {
        return encrypt(strText, CryptologyEnum.SHA_512.getValue());
    }
    
	/**
	 * 使用SHA1对文件流进行加密
	 * @author AimSpeed
	 * @Title encryptSHA1 
	 * @param inputStream
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException String  
	 * @date 2018年9月1日 下午11:55:25
	 */
    public String encryptSHA1(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return encrypt(inputStream,CryptologyEnum.SHA_1.getValue());
    }
    
    /**
     * 使用SHA224对文件流进行加密
     * @author AimSpeed
     * @Title encryptSHA224 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:55:45
     */
    public String encryptSHA224(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return encrypt(inputStream,CryptologyEnum.SHA_224.getValue());
    }
    
    /**
     * 使用SHA256对文件流进行加密
     * @author AimSpeed
     * @Title encryptSHA256 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:55:55
     */
    public String encryptSHA256(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return encrypt(inputStream,CryptologyEnum.SHA_256.getValue());
    }
    
    /**
     * 使用SHA384对文件流进行加密
     * @author AimSpeed
     * @Title encryptSHA384 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:56:07
     */
    public String encryptSHA384(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return encrypt(inputStream,CryptologyEnum.SHA_384.getValue());
    }
    
    /**
     * 使用SHA512对文件流进行加密
     * @author AimSpeed
     * @Title encryptSHA512 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:56:15
     */
    public String encryptSHA512(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return encrypt(inputStream,CryptologyEnum.SHA_512.getValue());
    }

	/**
	 * 根据对应的算法对字符串进行加密
	 * @author AimSpeed
	 * @Title encrypt 
	 * @param encryptStr
	 * @param algorithmName
	 * @return
	 * @throws NoSuchAlgorithmException String 
	 * @date 2018年4月24日
	 */
	public String encrypt(String encryptStr, String algorithmName) throws NoSuchAlgorithmException {
		//调用通用的方法获取加密后的位值
        byte[] bytes = getEncryptBit(algorithmName,encryptStr);
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
	}

	/**
	 * 根据对应的算法对对流全文加密
	 * @author AimSpeed
	 * @Title encrypt 
	 * @param inputStream
	 * @param algorithmName
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws IOException  
	 * @date 2018年4月24日
	 */
	public String encrypt(InputStream inputStream, String algorithmName) throws NoSuchAlgorithmException, IOException {
		byte [] bytes = getByteOfStream(inputStream,algorithmName);
        //return byteToHexString(b);
        inputStream.close();
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
	}

	

}
