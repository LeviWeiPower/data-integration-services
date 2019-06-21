package com.aimspeed.common.datatype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串类型工具类
 * @author AimSpeed
 */
public abstract class StringUtils {
	
	/**
	 * 剪切点，逗号
	 */
    public static final String DECOLLATOR = ",";
    
    /**
     * 引号
     */
    public static final String QUOTES = "'";
    
    public static final String STR2ISNUMERIC = "[-]?[0-9]+[.]?[0-9]*";
    public static final String STR2ISINTEGER = "[-]?[0-9]+";
    public static final String STR2REPLACEBLANK = "\\s*|\t|\r|\n";
    
    /**
     * 下划线转驼峰
     */
    private static Pattern lineToHumpPattern = Pattern.compile("_(\\w)");
    
    /**
     * 驼峰转下划线
     */
    private static Pattern humpToLinePattern = Pattern.compile("[A-Z]");
    
    /**
     * 字符串转化为unicode 
     * @author AimSpeed
     * @param gbString 需要转换的字符串
     * @return String 转换结果
     */
    public static String encodeUnicode(final String gbString) { 
        char[] utfBytes = gbString.toCharArray(); 
        String unicodeBytes = ""; 
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) { 
            String hexB = Integer.toHexString(utfBytes[byteIndex]); 
            if (hexB.length() <= 2) { 
                hexB = "00" + hexB; 
            } 
            //unicodeBytes = unicodeBytes + "\\u" + hexB; 
            unicodeBytes = unicodeBytes + hexB; 
        } 
        System.out.println(unicodeBytes); 
        return unicodeBytes; 
    } 
    
    /**
     * unicode转化汉字 
     * @author AimSpeed
     * @param utfStr 需要转换的字符串
     * @return String 转换结果 
     */
    public String decodeUnicode(String utfStr) { 
        final StringBuffer buffer = new StringBuffer(); 
        String charStr = ""; 
        String operStr = utfStr; 
        for(int i =0 ; i < utfStr.length() ;i=+4){ 
            charStr = operStr.substring(0, 4); 
            operStr = operStr.substring(4, operStr.length()); 
            char letter = (char) Integer.parseInt(charStr, 16); 
            buffer.append(new Character(letter).toString()); 
        } 
        return buffer.toString(); 
    }

    /**
     * 判断字符串是否为空
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @param isTrim 是否判断空字符
     * @return boolean false,true
     */
    public static boolean isEmpty(String str, boolean isTrim) {
        if (str == null) {
            return true;
        }
        if (isTrim) {
            str = str.trim();
        }
        if ("".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 空字符串转为null
     * @author AimSpeed
     * @param val 需要执行的字符串
     * @return String 执行结果
     */
    public static String EmptyToNull(String val) {
        if ("".equals(val.trim())) {
            return null;
        }
        return val;
    }

    /**
     * 判断是否数值
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return boolean 执行结果
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(STR2ISNUMERIC);
//		Pattern pattern = Pattern.compile("[-]?[0-9]+[.]?[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是整型
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return boolean 执行结果
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile(STR2ISINTEGER);
//		Pattern pattern = Pattern.compile("[-]?[0-9]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 首字母大写
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return String 执行结果
     */
    public static String upperCaseFirstChar(String str) {
        String newString;
        newString = str.substring(0, 1).toUpperCase()
                + str.substring(1);
        return newString;
    }

    /**
     * 首字母小写
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return String 执行结果
     */
    public static String lowerCaseFirstChar(String str) {
        String newString;
        newString = str.substring(0, 1).toLowerCase()
                + str.substring(1);
        return newString;
    }

    /**
     * sql字符串加上引号.如1, 2, 3返回'1','2','3'
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return String 执行结果
     */
    public static String plusQuotes(String str) {
        String[] arr = str.split(DECOLLATOR);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(arr[i])) {
                sb.append(QUOTES).append(arr[i]).append(QUOTES);
                if (i != arr.length - 1) {
                    sb.append(DECOLLATOR);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 提取字符串中的数字
     * @author AimSpeed
     * @param val 需要执行的字符串
     * @return String 执行结果 
     */
    public static String extractNumber(String val) {
        if (isEmpty(val, true)) {
            return "";
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(val);
        return m.replaceAll("").trim();
    }

    /**
     * 提取字符串中的非数字
     * @author AimSpeed
     * @param val 需要执行的字符串
     * @return String 执行结果 
     */
    public static String extractNoNum(String val) {
        if (isEmpty(val, true)) {
            return "";
        }
        return val.replaceAll("([1-9]+[0-9]*|0)(\\.[\\d]+)?", "");
    }

    /**
     * 提取小数点
     * @author AimSpeed
     * @param val 需要执行的字符串
     * @return String 执行结果 
     */
    public static String extractDecimal(String val) {
        char[] b = val.toCharArray();
        String result = "";
        for (int i = 0; i < b.length; i++) {
            if (("0123456789.").indexOf(b[i] + "") != -1) {
                result += b[i];
            }
        }
        return result;
    }

    /**
     *  获取两个字符的最大相似字符，如“rtyuio”,“1rty”最大相似字符是rty
     * @author AimSpeed
     * @param str1 需要执行的字符串1
     * @param str2 需要执行的字符串2
     * @return String 执行结果 
     */
    public static String getMaxLenStr(String str1, String str2) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str1) && org.apache.commons.lang3.StringUtils.isNotBlank(str2)) {
            String dest = null, tar = null;
            if (str1.length() < str2.length()) {
                tar = str1;
                dest = str2;
            } else {
                tar = str2;
                dest = str1;
            }
            int max = tar.length();
            for (int i = max; i > 0; i--) {
                for (int j = 0; j <= max - i; j++) {
                    String ago = tar.substring(j, i + j);
                    if (dest.contains(ago)) {
                        return ago;
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取两个字条串中的相同字符
     * @author AimSpeed
     * @param str1 需要执行的字符串
     * @param str2 需要执行的字符串
     * @return String[] 执行结果
     */
    public static String[] getIdentical(String str1, String str2) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str1) && org.apache.commons.lang3.StringUtils.isNotBlank(str2)) {
            List<String> list = new ArrayList<String>();
            String str = str1;
            if (str2.length() > str1.length()) {
                str = str2;
            }
            for (int i = 0; i < str.length(); i++) {
                String s = str.substring(i, i + 1);
                if (str1.contains(s) && str2.contains(s)) {
                    list.add(s);
                }
            }
            if (list.size() > 0) {
                String[] array = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
            }
        }
        return null;
    }

    /**
     * java去除字符串中的空格、回车、换行符、制表符
     * @author AimSpeed
     * @param str 需要执行的字符串
     * @return String 执行结果
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile(STR2REPLACEBLANK);
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 提供字符串数组到字符串的转变，转变后的字符串以sStr作为分割符
     * @author AimSpeed
     * @param tStrs 需要执行的字符串
     * @param sStr 分割符
     * @return String  执行结果
     */
    public static String StrsToStr(String[] tStrs, String sStr) {
        String reStr = "";
        int len = tStrs.length;
        if (len > 0) {
            if (tStrs[0] != null) {
                reStr = tStrs[0];
            }
        }
        for (int i = 1; i < len; i++) {
            if (tStrs[i] != null) {
                if (tStrs[i].length() > 0) {
                    reStr += sStr + tStrs[i];
                }
            }
        }
        return reStr;
    }

    /**
     * 判断字符是否为空
     * @author AimSpeed
     * @param cs
     * @return boolean 
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空
     * @author AimSpeed
     * @param cs
     * @return boolean 
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);

    }

    /**
     * 判断字符串是否为空字符串
     * @author AimSpeed
     * @param string
     * @return boolean 
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    /**
     * 判断数组是否为空
     * @author AimSpeed
     * @param array
     * @return boolean 
     */
    public static boolean isBlank(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断集合是否为空
     * @author AimSpeed
     * @param array
     * @return boolean 
     */
    public static boolean isBlank(Collection<? extends Object> array) {
        return array == null || array.size() == 0;
    }

    /**
     * 判断集合是否不为空
     * @author AimSpeed
     * @param array
     * @return boolean 
     */
    public static boolean isNotBlank(Collection<? extends Object> array) {
        return !isBlank(array);

    }

    /**
     * 判断所有集合是否都为空
     * @author AimSpeed
     * @param collections
     * @return boolean 
     */
    public static boolean isBlankAll(Collection<?>... collections) {
        for (Collection<?> c : collections) {
            if (!isBlank(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否都是空字符串
     * @author AimSpeed
     * @param strings
     * @return boolean 
     */
    public static boolean isBlankAll(String... strings) {
        for (String s : strings) {
            if (!isBlank(s)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断collections集合中是否至少有一个为空
     * @author AimSpeed
     * @param collections
     * @return
     */
    public static boolean isBlankAtLeastOne(Collection<?>... collections) {
        for (Collection<?> c : collections) {
            if (isBlank(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 字符数组是否有一个为空
     * @author AimSpeed
     * @param chars
     * @return boolean 
     */
    public static boolean isBlank(char[] chars) {
        // TODO Auto-generated method stub
        int strLen;
        if (chars == null || (strLen = chars.length) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(chars[i]) == false) {
                return false;
            }
        }
        return true;
    }

    
    /**
     * 下划线转驼峰
     * @author AimSpeed
     * @param str
     * @return String 
     */
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = lineToHumpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * 驼峰转下划线
     * @author AimSpeed
     * @param str
     * @return String 
     */
    public static String humpToLine(String str){
        Matcher matcher = humpToLinePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String lineToHump = lineToHump("fparentnoleader");
        System.out.println(lineToHump);//fParentNoLeader
        System.out.println(humpToLine("fParentNoLeader"));//fParentNoLeader
    }


}
