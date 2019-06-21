package com.aimspeed.common.cryptology.reversible.symmetry;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import com.aimspeed.common.cryptology.AbstractCryptology;
import com.aimspeed.common.cryptology.reversible.SymmetryCryptology;

/**
 * 
 * @author AimSpeed
 */
public class Base64Arithmetic extends AbstractCryptology implements SymmetryCryptology {
	
	/*
	 * 加密
	 * @param content
	 * @param key
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @overridden @see com.aimspeed.cryptology.reversible.symmetry.SymmetryCryptology#encrypt(java.lang.String, java.lang.String)
	 */
	@Override
	public String encrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		if(null == content || "".equals(content.trim())) {
			return null;
		}
		return encrypt(content);
	}

	/*
	 * 解密
	 * @param content
	 * @param key
	 * @return String
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @overridden @see com.aimspeed.cryptology.reversible.symmetry.SymmetryCryptology#decrypt(java.lang.String, java.lang.String)
	 */
	@Override
	public String decrypt(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		if(null == content || "".equals(content.trim())) {
			return null;
		}
		return decrypt(content);
	}
	
	/**
	 * 加密
	 * @author AimSpeed
	 * @param encryptStr
	 * @return String  
	 * @throws NoSuchAlgorithmException
	 */
    public String encrypt(String encryptStr) throws NoSuchAlgorithmException{  
        return Base64.getEncoder().encodeToString(encryptStr.getBytes());
    }  
    
    /**
     * 解密
     * @author AimSpeed
     * @param decryptStr 
     * @return String 
     * @throws NoSuchAlgorithmException String  
     */
    public String decrypt(String decryptStr) throws NoSuchAlgorithmException {  
    	if(null == decryptStr || "".equals(decryptStr.trim())) {
			return null;
		}
        return byteToString(Base64.getDecoder().decode(decryptStr));
    }  
    
    /**
     * 加密图片
     * @author AimSpeed
     * @param inputStream
     * @param suffix
     * @return String  
     */
    public String encryptImage(InputStream inputStream,String suffix){ 
    	if(null == inputStream) {
			return null;
		}
    	suffix = null == suffix ? "jpg" : suffix;
        BufferedImage bufferedImage;      
        try {      
        	bufferedImage = ImageIO.read(inputStream);    
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();      
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);      
            byte[] bytes = byteArrayOutputStream.toByteArray();      
            return byteToString(Base64.getEncoder().encode(bytes));      
        } catch (IOException e) {      
        }   
        return null;
	}
	
    /**
     * 解码图片加密码
     * @author AimSpeed
     * @param decryptStr
     * @return BufferedImage  
     */
	public BufferedImage decryptImage(String decryptStr){
		if(null == decryptStr || "".equals(decryptStr.trim())) {
			return null;
		}      
        try {      
            byte[] bytes = Base64.getDecoder().decode(decryptStr);      
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream); 
            //写出
            /*File w2 = new File("C:\\Users\\Su\\Desktop\\ok." + subffix);//可以是jpg,png,gif格式      
            boolean fal = ImageIO.write(bufferedImage, subffix, w2);//不管输出什么格式图片，此处不需改动      
            System.out.println(fal);*/
            return bufferedImage;
        } catch (IOException e) {      
            e.printStackTrace();      
        }
       return null;      
    }
	
	
    
   
    
}
