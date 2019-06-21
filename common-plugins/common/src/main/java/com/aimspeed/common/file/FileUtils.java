package com.aimspeed.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * 文件操作工具类
 * @author AimSpeed
 */
public abstract class FileUtils {


    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = 1024 * 1024 * 30;

    /**
     * xml格式
     */
    public static final String XML = "xml";

    /**
     * xls格式的excel
     */
    public static final String XLS = "xls";

    /**
     * xlsx格式的excel
     */
    public static final String XLSX = "xlsx";

    /**
     * zip
     */
    public static final String ZIP = "zip";

    /**
     * rar
     */
    public static final String RAR = "rar";

    /**
     * pdf
     */
    public static final String PDF = "pdf";
    /**
     * 属性文件后缀名
     */
    public static final String PROPS = ".properties";

    /**
     * 符号.
     */
    public static final String SYMBOL_DOT = ".";

    /**
     * 文件名称格式
     */
    public static final String FILENAME_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 主流图片格式
     */
    public static final String[] imageExtArr = {"png", "PNG", "gif", "GIF",
            "jpeg", "JPEG", "jpg", "JPG", "bmp", "BMP"};

    /**
     * word格式数组
     */
    public static final String[] wordExtArr = {"doc", "docx"};


    /**
     * 判断文件是否为Excel
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isExcel(String fileName) throws Exception {
        String extension = getExtension(fileName);
        if (XLS.equalsIgnoreCase(extension) || XLSX.equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否为xml
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isXMlFlie(String fileName) throws Exception {
        String extension = getExtension(fileName);
        if (XML.equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否为图片
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isPic(String fileName) throws Exception {
        String extension = getExtension(fileName);
        List<String> imageExtList = Arrays.asList(imageExtArr);
        if (imageExtList.contains(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否为ZIP
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isZipFile(String fileName) throws Exception {
        String extension = getExtension(fileName);
        if (ZIP.equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否为RAR
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isRarFile(String fileName) throws Exception {
        String extension = getExtension(fileName);
        if (RAR.equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否为PDF
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isPDFFile(String fileName) throws Exception {
        String extension = getExtension(fileName);
        if (PDF.equalsIgnoreCase(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否WORD
     * @author AimSpeed
     * @param fileName 文件名
     * @return boolean 布尔结果
     * @throws Exception boolean 
     */
    public static boolean isWORDFile(String fileName) throws Exception {
        String extension = getExtension(fileName);
        List<String> imageExtList = Arrays.asList(wordExtArr);
        if (imageExtList.contains(extension)) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件扩展名
     * @author AimSpeed
     * @param fileName 文件名
     * @return String 结果字符串
     * @throws Exception String 
     */
    public static String getExtension(String fileName) throws Exception {
        return fileName.lastIndexOf(SYMBOL_DOT) == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(SYMBOL_DOT) + 1);
    }

    /**
     * 获取去掉扩展名的文件名称
     * @author AimSpeed
     * @param fileName 文件名
     * @return String 结果字符串
     * @throws Exception String 
     */
    public static String getFileNameNotExtension(String fileName) throws Exception {
        return fileName.lastIndexOf(SYMBOL_DOT) == -1 ? "" : fileName
                .substring(0, fileName.lastIndexOf(SYMBOL_DOT));
    }

    /**
     * 文件重命名
     * @author AimSpeed
     * @param fileName 文件名
     * @return String 重命名后的文件名
     * @throws Exception String 
     */
    public static String rename(String fileName) throws Exception {
        String extension = getExtension(fileName);
        return UUID.randomUUID().toString() + SYMBOL_DOT + extension;
    }

    /**
     * 获取文件基本名（无后缀）
     * @author AimSpeed
     * @param fileName 文件名
     * @return String 文件基本名
     * @throws Exception String 
     */
    public static String getBaseName(String fileName) throws Exception {
        if (fileName.lastIndexOf(SYMBOL_DOT) == -1) {
            return fileName;
        } else {
            return fileName.substring(0, fileName.lastIndexOf(SYMBOL_DOT));
        }
    }

    /**
     * 转换文件类型，根据文件类型返回对应的ContentType
     * @author AimSpeed
     * @param fileName 文件名
     * @return String 文件对应的ContentType参数
     */
    public static String getContentType(String fileName) {

        String ext = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
        if (null == ext) {
            return "application/force-download";
        }

        if (ext.equals(".zip")) {
            return "application/zip";
        } else if (ext.equals(".xls") || ext.equals(".xlsx")) {
            return "application/x-excel";
        } else if (ext.equals(".doc") || ext.equals(".docx")) {
            return "application/msword";
        } else if (ext.equals(".pdf")) {
            return "application/pdf";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else if (ext.equals(".bmp")) {
            return "image/bmp";
        }
        return "application/force-download";
    }

    /**
     * 读取这个文件流信息，并且转换为字符串输出，默认编码UTF-8
     * @author AimSpeed
     * @param instream 读取的文件流信息
     * @return String 文件全部字符串信息
     * @throws UnsupportedEncodingException
     * @throws IOException String 
     */
    public static String stringOfReadStream(InputStream instream) throws UnsupportedEncodingException, IOException {
        return stringOfReadStream(instream, null);
    }

    /**
     * 读取这个文件流信息，并且转换为字符串输出
     * @author AimSpeed
     * @param instream 读取的文件流信息
     * @param coding 编码
     * @return String 文件全部字符串信息
     * @throws UnsupportedEncodingException
     * @throws IOException String 
     */
    public static String stringOfReadStream(InputStream instream, String coding) throws UnsupportedEncodingException, IOException {
        String teString = "";
        //默認UTF-8
        coding = null == coding || coding.length() < 0 ? "utf-8" : coding;

        int l;
        byte[] tmp = new byte[2048];
        while ((l = instream.read(tmp)) != -1) {
            teString += new String(tmp, 0, l, coding);
        }
        return teString;
    }
    
    /**
     * 根据文件路径获取文件
     * @author AimSpeed
     * @param filePath 文件路径
     * @return File 获取到的文件对象
     */
    public static File getFileByPath(String filePath) {
        return null == filePath ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     * @author AimSpeed
     * @param filePath 文件路径
     * @return boolean 是否存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 是否存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否是目录
     * @author AimSpeed
     * @param dirPath 目录路径
     * @return boolean 是否是目录
     */
    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 是否是目录
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否是文件
     * @author AimSpeed
     * @param filePath 文件路径
     * @return boolean  是否是文件
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 是否是文件
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     * @author AimSpeed
     * @param dirPath 文件夹路径
     * @return boolean 存在或创建成功，不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 存在或创建成功，不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     * @author AimSpeed
     * @param filePath 文件路径
     * @return boolean 存在或创建成功，不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 存在或创建成功，不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动目录
     * @author AimSpeed
     * @param srcDirPath 源目录路径
     * @param destDirPath 目标目录路径
     * @param isMove 是否移动，false则复制
     * @return boolean 是否复制或移动成功了
     */
    private static boolean copyOrMoveDir(String srcDirPath, String destDirPath, boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove);
    }

    /**
     * 复制或移动目录
     * @author AimSpeed
     * @param srcDir 源目录
     * @param destDir 目标目录
     * @param isMove 是否移动，false则复制
     * @return boolean 是否复制或移动成功了
     */
    private static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || destDir == null) {
            return false;
        }
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) {
            return false;
        }
        // 源文件不存在或者不是目录则返回false
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        // 目标目录不存在返回false
        if (!createOrExistsDir(destDir)) {
            return false;
        }
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // 如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                // 如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) {
                    return false;
                }
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 复制或移动文件
     * @author AimSpeed
     * @param srcFilePath 源文件路径
     * @param destFilePath 目标文件路径
     * @param isMove 是否移动，false则复制
     * @return boolean 是否复制或移动成功了
     */
    private static boolean copyOrMoveFile(String srcFilePath, String destFilePath, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);
    }

    /**
     * 复制或移动文件
     * @author AimSpeed
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @param isMove 是否移动，false则复制
     * @return boolean 是否复制或移动成功了
     */
    private static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) {
            return false;
        }
        // 源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        // 目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) {
            return false;
        }
        // 目标目录不存在返回false
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除目录
     * @author AimSpeed
     * @param dirPath 要删除的目录路径
     * @return boolean 是否操作成功
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     * @author AimSpeed
     * @param dir 要删除的目录文件对象
     * @return boolean 是否操作成功
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     * @author AimSpeed
     * @param srcFilePath 文件路径
     * @return boolean 是否操作成功
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     * @author AimSpeed
     * @param file 文件对象
     * @return boolean 是否操作成功
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除目录下的所有文件
     * @author AimSpeed
     * @param dirPath 目录路径
     * @return boolean 是否操作成功
     */
    public static boolean deleteFilesInDir(String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下的所有文件
     * @author AimSpeed
     * @param dir 目录路径对象
     * @return boolean 是否操作成功
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取目录下所有文件
     * @author AimSpeed
     * @param dirPath 目录路径
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     * @author AimSpeed
     * @param dir 目录
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (isRecursive) {
            return listFilesInDir(dir);
        }
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        Collections.addAll(list, dir.listFiles());
        return list;
    }

    /**
     * 获取目录下所有文件包括子目录
     * @author AimSpeed
     * @param dirPath 目录路径
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDir(String dirPath) {
        return listFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 获取目录下所有文件包括子目录
     * @author AimSpeed
     * @param dir 目录对象
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDir(File dir) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFilesInDir(file));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * @author AimSpeed
     * @param dirPath 目录路径
     * @param suffix 后缀名
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix, isRecursive);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件
     * @author AimSpeed
     * @param dir 目录
     * @param suffix 后缀名
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, suffix);
        }
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * @author AimSpeed
     * @param dirPath 目录路径
     * @param suffix 后缀名
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, String suffix) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), suffix);
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * @author AimSpeed
     * @param dir 目录路径
     * @param suffix 后缀名
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, suffix));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件
     * @author AimSpeed
     * @param dirPath 目录路径
     * @param filter 过滤器
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有符合filter的文件
     * @author AimSpeed
     * @param dir 目录路径对象
     * @param filter 过滤器
     * @param isRecursive 是否递归进子目录
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) {
            return listFilesInDirWithFilter(dir, filter);
        }
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     * @author AimSpeed
     * @param dirPath 目录路径
     * @param filter 过滤器
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(String dirPath, FilenameFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 获取目录下所有符合filter的文件包括子目录
     * @author AimSpeed
     * @param dir 目录对象
     * @param filter 过滤器
     * @return List<File> 结果文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
                if (file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter));
                }
            }
        }
        return list;
    }

    /**
     * 将输入流写入文件
     * @author AimSpeed
     * @param file 文件对象
     * @param is 输入流
     * @param append 是否追加在文件末
     * @return boolean 是否操作成功
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(is, os);
        }
    }
    
    /**
     * 将输入流写入文件
     * @author AimSpeed
     * @param file 文件对象
     * @param is 输入流
     * @param append 是否追加在文件末
     * @return boolean 是否操作成功
     */
    public static boolean writeFileFromIS(File file, InputStream is) {
        if (file == null || is == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(is, os);
        }
    }

    /**
     * 将字符串写入文件
     * @author AimSpeed
     * @param file 文件
     * @param content 写入内容
     * @param append 是否追加在文件末
     * @return boolean 是否操作成功
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIO(fileWriter);
        }
    }

    /**
     *  指定编码按行读取文件到List
     * @author AimSpeed
     * @param file 文件
     * @param charsetName 编码格式
     * @return List<String> 结果集
     */
    public static List<String> readFile2List(File file, String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 指定编码按行读取文件到List
     * @author AimSpeed
     * @param file  文件
     * @param st  需要读取的开始行数
     * @param end  需要读取的结束行数
     * @param charsetName 编码格式
     * @return List<String> 结果集
     */
    public static List<String> readFile2List(File file, int st, int end, String charsetName) {
        if (file == null) {
            return null;
        }
        if (st > end) {
            return null;
        }
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (null != charsetName) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) {
                    break;
                }
                if (st <= curLine && curLine <= end) {
                    list.add(line);
                }
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    /**
     * 指定编码按行读取文件到字符串中
     * @author AimSpeed
     * @param file 文件
     * @param charsetName 编码格式
     * @return String 结果集字符串
     */
    public static String readFile2String(File file, String charsetName) {
        if (file == null) {
            return null;
        }
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (null != charsetName) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            // 要去除最后的换行符
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    /**
     * 简单获取文件编码格式
     * @author AimSpeed
     * @param file 文件
     * @return String  文件编码
     */
    public static String getFileCharsetSimple(File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 获取文件行数
     * @author AimSpeed
     * @param file 文件
     * @return int  行数
     */
    public static int getFileLines(File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int readChars;
            while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (buffer[i] == '\n') {
                        ++count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        return count;
    }

    /**
     * 关闭IO
     * @author AimSpeed
     * @param closeables void 
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全路径中的最长目录
     * @author AimSpeed
     * @param filePath 文件路径
     * @return String  最长目录
     */
    public static String getDirName(String filePath) {
        if (null != filePath) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }


    /**
     * 获取全路径中的文件名
     * @author AimSpeed
     * @param filePath 文件路径
     * @return String 文件名
     */
    public static String getFileName(String filePath) {
        if (null != filePath) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取文件名后缀
     * @author AimSpeed
     * @param fileName 文件路径
     * @return String 文件拓展名
     */
    public static String getFileSuffixName(String fileName) {
    	if(fileName.contains(".")) {
    		String [] strings = fileName.split("\\.");
    		return strings[strings.length - 1];
    	}
    	return null;
    }
    
    /**
	 * 流转为字节
	 * @author AimSpeed
	 * @param inputStream
	 * @return byte[]  
	 */
	public static byte[] streamToByte(InputStream inputStream) {
		if(null == inputStream) {
			return null;
		}
		//转换为byte
    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[1024];  
        int rc = 0;  
        try {
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {  
			    swapStream.write(buff, 0, rc);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

}
