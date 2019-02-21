package com.aimspeed.common.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 资源文件工具类
 * @author AimSpeed
 */
public abstract class PropertiesUtils {


	/**
	 * 获取属性配置中的int型配置，如果没有则返回默认值
	 * @author AimSpeed
	 * @param p properties文件属性对象集合
	 * @param key 属性的key
	 * @param defaultValue 默认值
	 * @return int 获取属性配置中的int型配置，如果没有则返回默认值 
	 */
    public static int getIntProperty(Properties p, String key, int defaultValue) {
        if (p == null) {
            return defaultValue;
        }
        String ret = p.getProperty(key);
        if (null != ret && ret.length() > 0) {
            return defaultValue;
        }
        return Integer.parseInt(ret);

    }

    /**
     * 保存properties到指定文件
     * @author AimSpeed
     * @param p 配置信息对象
     * @param fileName 文件名（含全路径，相对路径从WEB-INF/classes开始），保存可以是properties或xml文件
     * @param comment 备注信息 
     */
    public static void saveProperties(Properties p, String fileName, String comment) {
        saveProperties(p, fileName, comment, "UTF-8");
    }

    /**
     * 保存properties到指定Xml文件中
     * @author AimSpeed
     * @param p 配置信息
     * @param fileName 文件名（含全路径，相对路径从WEB-INF/classes开始），保存可以是properties或xml文件
     * @param encoding void 
     */
    public static void savePropertiesXml(Properties p, String fileName, String encoding) {
        saveProperties(p, fileName, null, encoding);
    }

    /**
     * 保存properties到指定文件中
     * @author AimSpeed
     * @param p 配置信息对象
     * @param fileName 文件名（含全路径，相对路径从WEB-INF/classes开始），保存可以是properties或xml文件，根据后缀判断
     * @param comment 备注信息
     * @param encoding void 
     */
    public static void saveProperties(Properties p, String fileName, String comment, String encoding) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            if (fileName.toLowerCase().endsWith(".xml")) {
                p.storeToXML(out, comment, encoding);
            } else {
                p.store(out, comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取配置文件中的内容（通过PropertyResourceBundle，更改时需重启服务）
     * @author AimSpeed
     * @param fileName 文件名（含全路径，相对路径从WEB-INF/classes开始），不用.properties结尾
     * @param key 属性的key
     * @return String 取属性配置中的String型配置，如果没有则返回默认值 
     */
    public static String getProperty(String fileName, String key) {
        String sRet = null;
        try {

            PropertyResourceBundle configBundle = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
            sRet = configBundle.getString(key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    /**
     * 获取配置文件中的内容（通过PropertyResourceBundle，更改时需重启服务）
     * @author AimSpeed
     * @param fileName 文件名（含全路径，相对路径从WEB-INF/classes开始），不用.properties结尾
     * @param key 属性的key
	 * @param defaultValue 默认值
     * @return String 取属性配置中的String型配置，如果没有则返回默认值 
     */
    public static String getProperty(String fileName, String key, String defaultValue) {
        String sRet = null;
        try {

            PropertyResourceBundle configBundle = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
            sRet = configBundle.getString(key);
            if (null != sRet && sRet.length() > 0) {
                return defaultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sRet;
    }

    /**
     * 获取配置文件中的整数配置内容（通过PropertyResourceBundle，更改时需重启服务）
     * @author AimSpeed
     * @param fileName  文件名（含全路径，相对路径从WEB-INF/classes开始），不用.properties结尾
     * @param key 属性的key
	 * @param defaultValue 默认值
	 * @return int 获取属性配置中的int型配置，如果没有则返回默认值 
     */
    public static int getIntProperty(String fileName, String key, int defaultValue) {
        try {
            PropertyResourceBundle configBundle = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
            String sRet = configBundle.getString(key);
            if (null != sRet && sRet.length() > 0) {
                return defaultValue;
            }
            return Integer.parseInt(sRet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


}
