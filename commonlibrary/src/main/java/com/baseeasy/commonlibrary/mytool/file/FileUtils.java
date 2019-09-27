package com.baseeasy.commonlibrary.mytool.file;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.baseeasy.commonlibrary.mytool.AppUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 创建人：王志强
 * <p>
 * 创建时间： 2014年6月8日14:29:35
 */
public class FileUtils {

    public static final String SDPATH = Environment.getExternalStorageDirectory() + "/";
    public static final String [] FILE_TYPE_ALL={""};//所有类型
    public static final String [] FILE_TYPE_IMAGE={"jgp","jpg","png","gif","jpeg","bmp"};//图片类型
    public static final String [] FILE_TYPE_VIDEO={"mp4","3gp","wmv","asf","asx","rm","rmvb","avi","dat","mkv","flv","vob"};//视频类型
    public static final String  [] FILE_TYPE_DOCUMENT={"pdf","text","xls","doc","html","txt"};//文档类型
    public static final String  [] FILE_TYPE_COMPRESSION={"rar","zip","arj"};//压缩文件
    public static final String  [] FILE_TYPE_AUDIO={"wav","aif","au","mp3","ram","wma","amr","aac"};//音频文件




    /**
     * 在SD卡上创建文件
     */
    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + "/" + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     */
    /**
     * @param dirName
     * @return
     */
    public static File createSDDir(String dirName) {
        File dir = new File(SDPATH + "/" + dirName);
        if(!isDirectoryExist(dirName)){
             dir.mkdirs();
        }

        return dir;
    }

    /**
     * 判断SD卡上的文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + "/" + fileName);
        return file.exists();
    }

    /**
     * 判断SD卡上的目录是否存在
     *
     * @param dirName
     * @return
     */
    public static  boolean isDirectoryExist(String dirName){
        File file =new File(SDPATH + "/" + dirName);
        return  file .exists()  && file .isDirectory();
    }

    /**
     * 设置某个路径不被相册扫描
     *
     * @param dirName
     * @return
     * */
    public static  void createNoMedia(String dirName){
     if(!isFileExist(dirName+"/"+".nomedia")){
         try {
             creatSDFile(dirName+"/"+".nomedia");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     *
     * @param path     ：要写入SDCARD的目录
     * @param fileName ：要写入的文件名
     * @param input    ：要写入的数据
     * @return
     */
    public static File write2SDFromInput(String path, String fileName,
                                         InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int len = -1;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len); // 在这里使用另一个重载，防止流写入的问题.
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    //获取指定文件夹下的图片不包括二级目录推荐使用getAllFile（） 方法
    public static List<String> getImagePathFromSD(String folderPath) {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(folderPath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
        }

        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 获取指定文件夹下所有文件夹
     *
     * @param directoryPath     ：路径
     *
     *
     * @return
     */
    public static List<String> getAllDirectory(String directoryPath) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(getAllDirectory(file.getAbsolutePath()));
            } else {

            }
        }
        return list;
    }


    /**
     * 获取指定文件夹下所有文件夹或文件
     *
     * @param directoryPath     ：路径
     * @param isAddDirectory ：是否是否包含文件夹
     * @param type ：文件类型   所有文件类型传null
     * @return
     */
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory,String[] type) {
     return FileUtils.getAllFiles(directoryPath,isAddDirectory,type);
    }

    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory,String type) {
        String []mtype={type};
        return FileUtils.getAllFiles(directoryPath,isAddDirectory,mtype);
    }


    private static List<String> getAllFiles(String directoryPath, boolean isAddDirectory, String[] type){
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        if(null==files){
            List<String> stringList=new ArrayList<>();
            return stringList;

        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFiles(file.getAbsolutePath(), isAddDirectory,type));
            } else {
                if(null==type||type.length==0){
                    list.add(file.getAbsolutePath());
                }else {
                    if(FileUtils.checkIsFileType(file.getName(),type)){
                        list.add(file.getAbsolutePath());
                    }
                }
            }
        }

        return list;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */

    private static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 检查扩展名，是否是指定类型
     *
     * @param fName 文件名
     * @return
     */

    private static boolean checkIsFileType(String fName,String fileType) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals(fileType)) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 检查扩展名，是否是指定类型
     *
     * @param fName 文件名
     * @return
     */

    private static boolean checkIsFileType(String fName,String[] fileType) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        for (int i = 0; i <fileType.length ; i++) {
            if (FileEnd.equals(fileType[i])) {
                isImageFile = true;
                return isImageFile;
            } else {
                isImageFile = false;
            }
        }

        return isImageFile;
    }

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    // s删除指定文件或者文件夹
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return getFormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }


    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String getFormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * @param file_path 获取文件名后缀
     * @author Wang
     */
    public static String get_file_outname(String file_path) {

        File f = new File(file_path);
        String fileName = f.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return "." + prefix;

    }

    /**
     * @param file_path 获取文件名
     * @author Wang
     */
    public static String get_filename(String file_path) {

        File f = new File(file_path);
        String fileName = f.getName();

        return fileName;

    }



}
