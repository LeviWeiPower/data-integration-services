package com.speed.klutz.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;


/**
 * 资源文件工具类
 * @author AimSpeed
 */
public abstract class PropertiesUtils {
	
	/**
	 * 存储文件名和Properties对象
	 */
    private static Map<String,Properties> FILE_NAME_AND_PROPERTIES_MAP = new LinkedHashMap<String, Properties>();
    
    /**
     * 存放Properties文件数据
     */
    private static Map<String,String> PROPERTIES_DATAS_MAP = new LinkedHashMap<String, String>();

    /**
     * 文件输入流
     */
	private List<InputStream> propertiesInputStreams;
    
	/**
	 * 获取所有读取的资源文件的数据，对应数据的Key - Value
	 * @author AimSpeed
	 * @return Map<String,String> 
	 */
	public static Map<String,String> getPropertiesDatas() {
		return PROPERTIES_DATAS_MAP;
	}
	
	/**
	 * 获取所有读取的资源文件对象  文件名 - 资源文件对象 
	 * @author AimSpeed
	 * @return Map<String,Properties> 
	 */
	public static Map<String,Properties> getProperties() {
		return FILE_NAME_AND_PROPERTIES_MAP;
	}
	
	/**
	 * 加载资源文件流的数据 
	 * @author AimSpeed
	 * @param fileName
	 * @param inputStream
	 * @throws IOException void 
	 */
    public static void loadProperties(String fileName,InputStream inputStream) throws IOException{
        /*
         * 根据所有的文件路径获取到对应的Properties文件，
         * 并且生成对应的Properties对应存储到 
         *                      FILE_NAME_AND_FILE_PATH_MAP
         *                      FILE_NAME_AND_PROPERTIES_MAP
         * 
         */
        //获取文件名
        Properties properties = new Properties();
        properties.load(inputStream);
        
        FILE_NAME_AND_PROPERTIES_MAP.put(fileName, properties);//存储文件名和Properties对象
        inputStream.close();
        //调用readPropertiesDatas这个方法将所有的Properties文件数据存储到FILE_NAME_AND_PROPERTIES_MAP
        readPropertiesDatas();
    }
    
	/**
	 * 多个文件路径
	 * @author AimSpeed
	 * @param propertiesFilePaths
	 * @throws IOException void 
	 */
    public static void loadProperties(List<String> propertiesFilePaths) throws IOException {
        /*
         * 根据所有的文件路径获取到对应的Properties文件，
         * 并且生成对应的Properties对应存储到 
         *                      FILE_NAME_AND_FILE_PATH_MAP
         *                      FILE_NAME_AND_PROPERTIES_MAP
         * 
         */
        for (String propertiesFilePath : propertiesFilePaths) {
            InputStream inputStream = null;
            
            inputStream = new FileInputStream(new File(propertiesFilePath));
            String fileName = null;
            //获取文件名
            if(null != propertiesFilePath && propertiesFilePath.length() > 0){
            	fileName = propertiesFilePath.substring(propertiesFilePath.lastIndexOf("/")+1,propertiesFilePath.length()-1);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            
            FILE_NAME_AND_PROPERTIES_MAP.put(fileName, properties);//存储文件名和Properties对象
            inputStream.close();
        }
        
        //调用readPropertiesDatas这个方法将所有的Properties文件数据存储到FILE_NAME_AND_PROPERTIES_MAP
        readPropertiesDatas();
    }
	
	/**
	 * 加载资源文件
	 * @author AimSpeed
	 * @param inputStreamMaps
	 * @throws IOException void 
	 */
    public static void loadProperties(Map<String, InputStream> inputStreamMaps) throws IOException {
    	Integer count = 0;
    	
    	for (Entry<String, InputStream> entry : inputStreamMaps.entrySet()) {
    		InputStream inputStream = entry.getValue();
    		Properties properties = new Properties();
            properties.load(inputStream);
            FILE_NAME_AND_PROPERTIES_MAP.put(entry.getKey(), properties);//存储文件名和Properties对象
            count++;
            inputStream.close();
		}
    	//调用readPropertiesDatas这个方法将所有的Properties文件数据存储到FILE_NAME_AND_PROPERTIES_MAP
        readPropertiesDatas();
    }
    
	/**
	 * 将所有获取到的资源文件对象的数据存储到JVM对象中   
	 * @author AimSpeed
	 */
    public static void readPropertiesDatas(){
        int propertiesSize = FILE_NAME_AND_PROPERTIES_MAP.size();
        
        if(propertiesSize > 0){
            Properties propertiesObj = null;
            //获取到所有的Properties文件对应的对象
            Set<Map.Entry<String, Properties>> entrySet = FILE_NAME_AND_PROPERTIES_MAP.entrySet();
            for (Map.Entry<String, Properties> entry : entrySet) {
                 propertiesObj = entry.getValue();
                 if(null == propertiesObj){
                     continue;
                 }
                 
                 //读取所有的Properties文件，并且将这些文件的数据存储到PROPERTIES_DATAS_MAP
                 Iterator<Map.Entry<Object, Object>> it = propertiesObj.entrySet().iterator();
                 while (it.hasNext()) {
                     Map.Entry<Object, Object> entryProperties = it.next();
                     Object key = entryProperties.getKey();
                     Object value = entryProperties.getValue();
                     PROPERTIES_DATAS_MAP.put(key.toString(), value.toString());
                 }
            }
        }
        
    }
    
    
}
