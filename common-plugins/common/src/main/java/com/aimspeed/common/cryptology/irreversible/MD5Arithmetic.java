package com.aimspeed.common.cryptology.irreversible;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.enums.CryptologyEnum;

import ch.qos.logback.access.pattern.StatusCodeConverter;

/**
 * MD5加密实现
 * @author AimSpeed
 */
public class MD5Arithmetic extends AbstractCryptology implements IrreversibleCryptology {
	
	/**
	 * 静态内部类的单例
	 * 外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化md5Arithmetic，所以不占内存。
	 * 当MD5Arithmetic第一次被加载时，并不需要去加载MD5ArithmeticSingleton，
	 * 只有当getInstance()方法第一次被调用时，才会去初始化md5Arithmetic，
	 * 第一次调用getInstance()方法会导致虚拟机加载MD5ArithmeticSingleton类，
	 * 这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
	 * @author AimSpeed
	 */
	private static class MD5ArithmeticSingleton {
		private static MD5Arithmetic md5Arithmetic = new MD5Arithmetic();
		
	}
	
	/**
	 * 获取到实例对象
	 * @author AimSpeed
	 * @return MD5Arithmetic
	 */
	public static MD5Arithmetic getInstance() {
		return MD5ArithmeticSingleton.md5Arithmetic;
	}
	
	/*
	 * 默认使用32位的加密
	 * @param encryptStr
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @overridden @see com.aimspeed.cryptology.irreversible.IrreversibleCryptology#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
		return encrypt32bit(encryptStr);
	}
	
	/**
	 * 使用MD5加密，但是MD5是128的散列算法，所以即便进行了16进制的转换，也只有32位，所以也只能获取中间的数值
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
    public String encrypt16bit(String encryptStr) throws NoSuchAlgorithmException{
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        return encrypt32bit(encryptStr).substring(12, 28);  
    }  
    
    /**
     * 使用MD5加密，但是MD5是128的散列算法，所以即便进行了16进制的转换，也只有32位
     * @author AimSpeed
     * @param encryptStr
     * @return String
     * @throws NoSuchAlgorithmException
     */
    public String encrypt32bit(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        //调用通用的方法获取加密后的位值
        byte [] bytes = getEncryptBit(CryptologyEnum.MD_5.getValue(),encryptStr);
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
    }  
    
    /**
     * 对文件全文加密生成32位MD5摘要码
     * @author AimSpeed
     * @param inputStream
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String encrypt16bit(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}  
    	byte [] bytes = getByteOfStream(inputStream,CryptologyEnum.MD_5.getValue());
        //return byteToHexString(b);
        inputStream.close();
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes).substring(12, 28);
    }
    
    /**
     * 对文件全文加密生成32位MD5摘要码
     * @author AimSpeed
     * @param inputStream
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String encrypt32bit(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}  
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
