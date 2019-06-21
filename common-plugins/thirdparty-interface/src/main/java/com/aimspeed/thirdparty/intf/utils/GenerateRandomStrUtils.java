package com.aimspeed.thirdparty.intf.utils;

import java.util.Random;

/** 
 * 随机生成字符工具类
 * @author AimSpeed
 */
public class GenerateRandomStrUtils {
	
	/*public static void main(String[] args) {
		String value = generateCodeOfRule("AS","","010210","bs");
		System.out.println(value);
	}*/

	
	/**
	 * 设置要生成的随机数数量
	 * @author AimSpeed
	 * @Title createRandomStr
	 * @param len 要生成的随机字符串长度
	 * @return String 结果字符串
	 */
	public static String createRandomStr(int len) {
		int random = createRandomInt();
		return createRandomStr(random, len);
	}
	
	/**
	 * 根据指定的值生成随机数，并指定长度
	 * @author AimSpeed
	 * @Title createRandomStr
	 * @param random 指定的值
	 * @param len 生成的长度
	 * @return String 结果字符串 
	 * @date 2018年3月15日
	 */
	public static String createRandomStr(int random, int len) {
		Random rd = new Random(random);
		final int maxNum = 62;
		StringBuffer sb = new StringBuffer();
		int rdGet;// 取得随机数
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };

		int count = 0;
		while (count < len) {
			rdGet = Math.abs(rd.nextInt(maxNum));// 生成的数最大为62-1
			if (rdGet >= 0 && rdGet < str.length) {
				sb.append(str[rdGet]);
				count++;
			}
		}
		return sb.toString();
	}

	/**
	 * 生成随机数，得到0.0到1.0之间的数字，并扩大100000倍
	 * @author AimSpeed
	 * @Title createRandomInt
	 * @return int 结果字符值
	 */
	public static int createRandomInt() {
		// 得到0.0到1.0之间的数字，并扩大100000倍
		double temp = Math.random() * 100000;
		// 如果数据等于100000，则减少1
		if (temp >= 100000) {
			temp = 99999;
		}
		int tempint = (int) Math.ceil(temp);
		return tempint;
	}
	
	/**
	 * 生成随机数字和字母
	 * @author AimSpeed
	 * @Title getStringRandom
	 * @param length 指定长度
	 * @return String 结果
	 */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
	
}
