package com.aimspeed.common.cryptology.irreversible;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.enums.CryptologyEnum;

/**
 * MD5加密实现
 * @author AimSpeed
 * @Project AIM_SPEED_ARITHMETIC 
 * @Package com.aimspeed.arithmetic.md5 
 * @FileName MD5ArithmeticImpl.java 
 * @ClassName MD5ArithmeticImpl 
 * @date  2018年4月24日 
 */
public class MD5Arithmetic extends AbstractCryptology implements IrreversibleCryptology {
	
	/*
	 * 默认使用32位的加密
	 * @Title encrypt 
	 * @param encryptStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @date 2018年9月1日 下午11:52:29
	 * @overridden @see com.aimspeed.cryptology.irreversible.IrreversibleCryptology#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String encryptStr) throws NoSuchAlgorithmException {
		return encrypt32bit(encryptStr);
	}
	
	/**
	 * 使用MD5加密，但是MD5是128的散列算法，所以即便进行了16进制的转换，也只有32位，所以也只能获取中间的数值
	 * @author AimSpeed
	 * @Title encrypt16bit 
	 * @param encryptStr
	 * @return
	 * @throws NoSuchAlgorithmException String  
	 * @date 2018年9月1日 下午11:53:09
	 */
    public String encrypt16bit(String encryptStr) throws NoSuchAlgorithmException{  
        return encrypt32bit(encryptStr).substring(12, 28);  
    }  
    
    /**
     * 使用MD5加密，但是MD5是128的散列算法，所以即便进行了16进制的转换，也只有32位
     * @author AimSpeed
     * @Title encrypt32bit 
     * @param encryptStr
     * @return
     * @throws NoSuchAlgorithmException String  
     * @date 2018年9月1日 下午11:53:18
     */
    public String encrypt32bit(String encryptStr) throws NoSuchAlgorithmException {  
        //调用通用的方法获取加密后的位值
        byte [] bytes = getEncryptBit(CryptologyEnum.MD_5.getValue(),encryptStr);
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
    }  
    
    /**
     * 对文件全文加密生成32位MD5摘要码
     * @author AimSpeed
     * @Title encrypt16bit 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:53:26
     */
    public String encrypt16bit(InputStream inputStream) throws NoSuchAlgorithmException, IOException {  
    	byte [] bytes = getByteOfStream(inputStream,CryptologyEnum.MD_5.getValue());
        //return byteToHexString(b);
        inputStream.close();
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes).substring(12, 28);
    }
    
    /**
     * 对文件全文加密生成32位MD5摘要码
     * @author AimSpeed
     * @Title encrypt32bit 
     * @param inputStream
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException String  
     * @date 2018年9月1日 下午11:53:35
     */
    public String encrypt32bit(InputStream inputStream) throws NoSuchAlgorithmException, IOException {  
    	byte [] bytes = getByteOfStream(inputStream,CryptologyEnum.MD_5.getValue());
        //return byteToHexString(b);
        inputStream.close();
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
    }

    
   public static void main(String[] args) throws NoSuchAlgorithmException {
	   System.out.println("密码123456加密后：" + new MD5Arithmetic().encrypt32bit("wertyuio"));
   }
    
}
