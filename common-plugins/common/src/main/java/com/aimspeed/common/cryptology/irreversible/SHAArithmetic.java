package com.aimspeed.common.cryptology.irreversible;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.enums.CryptologyEnum;

/**
 * SHA加密工具实现
 * @author AimSpeed
 */
public class SHAArithmetic extends AbstractCryptology implements IrreversibleCryptology {
	
	/**
	 * 静态内部类的单例
	 * 外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化md5Arithmetic，所以不占内存。
	 * 当MD5Arithmetic第一次被加载时，并不需要去加载MD5ArithmeticSingleton，
	 * 只有当getInstance()方法第一次被调用时，才会去初始化md5Arithmetic，
	 * 第一次调用getInstance()方法会导致虚拟机加载MD5ArithmeticSingleton类，
	 * 这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。
	 * @author AimSpeed
	 */
	private static class SHAArithmeticSingleton {
		private static SHAArithmetic shaArithmetic = new SHAArithmetic();
		
	}
	
	/**
	 * 获取到实例对象
	 * @author AimSpeed
	 * @return SHAArithmetic
	 */
	public static SHAArithmetic getInstance() {
		return SHAArithmeticSingleton.shaArithmetic;
	}
	
	/*
	 * 默认使用256进行加密
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
		return encryptSHA256(encryptStr);
	}
	
	/**
	 * 使用SHA1进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
	public String encryptSHA1(String encryptStr) throws NoSuchAlgorithmException {
        return encrypt(encryptStr, CryptologyEnum.SHA_1.getValue());
    }

	/**
	 * 使用SHA224进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
    public String encryptSHA224(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        return encrypt(encryptStr, CryptologyEnum.SHA_224.getValue());
    }
    
	/**
	 * 使用SHA256进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
    public String encryptSHA256(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        return encrypt(encryptStr, CryptologyEnum.SHA_256.getValue());
    }

	/**
	 * 使用SHA384进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
    public String encryptSHA384(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        return encrypt(encryptStr, CryptologyEnum.SHA_384.getValue());
    }
    
	/**
	 * 使用SHA512进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
    public String encryptSHA512(String encryptStr) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
        return encrypt(encryptStr, CryptologyEnum.SHA_512.getValue());
    }
    
	/**
	 * 使用SHA1对文件流进行加密
	 * @author AimSpeed
	 * @param inputStream
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
    public String encryptSHA1(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
        return encrypt(inputStream,CryptologyEnum.SHA_1.getValue());
    }
    
    /**
     * 使用SHA224对文件流进行加密
     * @author AimSpeed
     * @param inputStream
     * @return String  
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String encryptSHA224(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
        return encrypt(inputStream,CryptologyEnum.SHA_224.getValue());
    }
    
    /**
     * 使用SHA256对文件流进行加密
     * @author AimSpeed
     * @param inputStream
     * @return String  
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String encryptSHA256(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
        return encrypt(inputStream,CryptologyEnum.SHA_256.getValue());
    }
    
    /**
     * 使用SHA384对文件流进行加密
     * @author AimSpeed
     * @param inputStream
     * @return String  
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String encryptSHA384(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
        return encrypt(inputStream,CryptologyEnum.SHA_384.getValue());
    }
    
    /**
     * 使用SHA512对文件流进行加密
     * @author AimSpeed
     * @param inputStream
     * @return String  
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    public String encryptSHA512(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
        return encrypt(inputStream,CryptologyEnum.SHA_512.getValue());
    }

	/**
	 * 根据对应的算法对字符串进行加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @param algorithmName
	 * @return
	 * @throws NoSuchAlgorithmException String 
	 */
	public String encrypt(String encryptStr, String algorithmName) throws NoSuchAlgorithmException {
		if(null == encryptStr || "".equals(encryptStr.trim())) {
			return null;
		}
		//调用通用的方法获取加密后的位值
        byte[] bytes = getEncryptBit(algorithmName,encryptStr);
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
	}

	/**
	 * 根据对应的算法对对流全文加密
	 * @author AimSpeed
	 * @param inputStream
	 * @param algorithmName
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws IOException  
	 */
	public String encrypt(InputStream inputStream, String algorithmName) throws NoSuchAlgorithmException, IOException {
		if(null == inputStream) {
			return null;
		}
		byte [] bytes = getByteOfStream(inputStream,algorithmName);
        //return byteToHexString(b);
        inputStream.close();
        //调用父类的方法将byte[]转换为字符串
        return byteTo16Radix(bytes);
	}

	

}
