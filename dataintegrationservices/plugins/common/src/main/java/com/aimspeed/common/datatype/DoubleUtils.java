package com.aimspeed.common.datatype;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Double数据类型工具类 
 * @author AimSpeed
 */
public class DoubleUtils {
	
	private static final int DEF_DIV_SCALE = 10;
	
	/**
	 * 提供精确的加法运算。
	 * @author AimSpeed
	 * @param v1 相加值1
	 * @param v2 相加值2
	 * @return double 相加后的值
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的减法运算。
	 * @author AimSpeed
	 * @param v1 值1
	 * @param v2 值2
	 * @return double 相减后的值
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @author AimSpeed
	 * @param v1 值1
	 * @param v2 值2
	 * @return double 相乘后的值 
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @author AimSpeed
	 * @param v1 值1
	 * @param v2 值2
	 * @param scale 指 定精度
	 * @return double 结果
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。当v1或v2为空时返回0
	 * @author AimSpeed
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return double  两个参数的商
	 */
	public static double div(BigDecimal v1, BigDecimal v2, int scale) {
		if(v1==null||v2==null){
			return 0;
		}else{
			return div(v1.doubleValue(),v2.doubleValue(),scale);
		}
	}

	/**
	 * 提供精确的小数位四舍五入处理，四舍五入后的结果
	 * @author AimSpeed
	 * @param v 需要四舍五入处理的数据
	 * @param scale 
	 * @return double 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 转换为百分数
	 * @author AimSpeed
	 * @param d 需要转化的数据
	 * @param scale 小数点后保留几位
	 * @return String 转化后的结果
	 */
	public static String formatPercent(double d, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		NumberFormat nt = NumberFormat.getPercentInstance();
		nt.setMinimumFractionDigits(scale);
		return nt.format(d);
	}

	/**
	 * 转换为非科学计数法
	 * @author AimSpeed
	 * @param d 需要转换的值
	 * @return String  转换结果
	 */
	public static String format(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		// 转换为非科学计算
		double temp = bd.doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(temp);
	}

	/**
	 * 转换为非科学计数法，保留指定小数点
	 * @author AimSpeed
	 * @param d 需要转化的数据
	 * @param scale 小数点后保留几位
	 * @return String 转化后的结果
	 */
	public static String format(double d, int scale) {
		if (scale <= 2) {
			return format(d);
		}
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		// 转换为非科学计算
		double temp = bd.doubleValue();
		StringBuffer sb = new StringBuffer("0.");
		for (int i = 0; i < scale; i++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(temp);
	}

	/**
	 * 百分数字符串转换为数字
	 * @author AimSpeed
	 * @param format 需要转化的数据
	 * @return Double 转化后的结果
	 */
	public static Double percent2Number(String format) {
		if ("100.00%".equals(format)) {// TODO
			return 1d;
		}
		NumberFormat nt = NumberFormat.getPercentInstance();
		try {
			return (Double) nt.parse(format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
	}

}
