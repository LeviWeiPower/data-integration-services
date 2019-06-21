package com.aimspeed.common.cryptology;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码学抽象类
 * @author AimSpeed
 */
public abstract class AbstractCryptology implements Cryptology {
	
	/**
	 * 获取到加密的字节码
	 * @author AimSpeed
	 * @param algorithmName 算法名称
	 * @param encryptStr 加密的字符串
	 * @return byte[] 加密后的字节码
	 * @throws NoSuchAlgorithmException 
	 */
	public byte[] getEncryptBit(String algorithmName,String encryptStr) throws NoSuchAlgorithmException {
		// 创建加密对象 并传入加密类型
        MessageDigest messageDigest = MessageDigest.getInstance(algorithmName);
        // 传入要加密的字符串
        messageDigest.update(encryptStr.getBytes());
        // 得到 byte 类型结果
        byte byteBuffer[] = messageDigest.digest();
        return byteBuffer;
    }

	/**
	 * 将字节转换为十六进制字符串
	 * @author AimSpeed
	 * @param bytes 需要转换的字节码
	 * @return String 转换后的字符串
	 */
    public String byteTo16Radix(byte[] bytes) {
      //密文转换为十六进制字符串
        StringBuilder su = new StringBuilder();  
        for(int offset = 0,bLen = bytes.length; offset < bLen; offset++){  
            String haxHex = Integer.toHexString(bytes[offset] & 0xFF);  
            if(haxHex.length() < 2){  
                su.append("0");  
            }  
            su.append(haxHex);  
        }  
        return su.toString(); 
    }
    
    /**
     * 将字节转换为字符串
     * @author AimSpeed
     * @param bytes
     * @return String 
     */
    public String byteToString(byte[] bytes) {
    	return new String(bytes); 
    }
    
    /**
     * 使用对应的加密算法获取到这个文件流的位数组
     * @author AimSpeed
     * @param inputStream 文件流
     * @param algorithmName 算法名 
     * @return byte[] 文件流的位数组
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public byte[] getByteOfStream(InputStream inputStream,String algorithmName) throws NoSuchAlgorithmException, IOException{
        MessageDigest md = MessageDigest.getInstance(algorithmName);
        byte[] buffer = new byte[2048];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, length);
        }
        byte[] bytes = md.digest();
        return bytes;
    }
	
    
    
}
