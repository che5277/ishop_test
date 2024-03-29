package tableshop.ilinkedtech.com.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import tableshop.ilinkedtech.com.consts.Const;

/*
 *  @项目名：  iShop
 *  @包名：    com.ilinkedtech.utils
 *  @文件名:   FileUtil
 *  @创建者:   Wilson
 *  @创建时间:  2017/7/7 16:45
 *  @描述：    TODO
 */
public class FileUtil {

    private static final String TAG        = "FileUtil";
    public static final String  SUFFIX_PDF = ".pdf";
    public static final String  SUFFIX_JPG = ".jpg";
    public static final String  SUFFIX_PCM= ".pcm";
    /**
     *
     * copy file
     *
     * @param src   源文件
     *            source file
     * @param dest  目标文件
     *            target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest)
            throws IOException
    {
        FileChannel inChannel  = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }


    /**
     * delete file删除文件
     *
     * @param file
     *            file
     * @return true 删除成功
     */
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    /**
     * @param file
     * @param newname TODO 修改文件名
     * @return
     */
    public static boolean renameFilenameTo(File file, String newname) {
        if (file != null && !TextUtils.isEmpty(newname)) {
            String parent = file.getParent();
            File newFile = new File(parent, newname);
            return file.renameTo(newFile);
        }
        return false;
    }


    public static boolean isEmpty(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        return isEmpty(file);
    }

    public static boolean isEmpty(File file) {
        if (file.exists() && file.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 保存录音文件的目录
     * @return
     */
    public static String getVoiceDir() {
        String dirPath = getExterStorageDirectory() +File.separator+ Const.DEBUG_TAG+File.separator+ "msc"+File.separator;
        File   dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirPath;
    }

    /**
     * 保存用户头像的目录
     * @return
     */
    public static String getPdfDir() {
        String dirPath = getExterStorageDirectory()+ Const.DEBUG_TAG +File.separator+ "pdf"+File.separator;
        File   dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirPath;
    }
    /**
     * 保存用户头像的目录
     * @return
     */
    public static String getImageDir() {
        String dirPath = getExterStorageDirectory()+Const.DEBUG_TAG +File.separator+ "img"+File.separator;
        File   dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirPath;
    }
    /**
     * 保存用户头像的目录
     * @return
     */
    public static String getImgFilePath(String filename) {
        return getImageDir()+filename+SUFFIX_JPG;
    }

    /**
     * 获取线索头像的地址
     * @param opportunityId
     * @return
     */
    public static String getLeadAvatarPath(String opportunityId) {
        if (StringUtils.isEmpty(opportunityId)){
            opportunityId=Const.DEBUG_TAG;
        }
        return getImageDir() + opportunityId + SUFFIX_JPG;
    }
    /**
     * 获取pdf文件的地址
     * @param filename
     * @return
     */
    public static String getPdfFilePath(String filename) {
        if (StringUtils.isEmpty(filename)){
            filename="noCachePdf";
        }
        return getPdfDir()+ filename + SUFFIX_PDF;
    }

    public static String getExterStorageDirectory() {
        if (isSDCardAvailable()) {
            return Environment.getExternalStorageDirectory()
                              .getAbsolutePath()+ File.separator;
        }
        return UIUtils.getContext().getFilesDir().getAbsolutePath()+ File.separator;
    }

    public static String getVoicePath(String msgId) {
        return getVoiceDir() + msgId + SUFFIX_PCM;
    }

    //    //获取SD卡剩余空间
    //    public long getSDFreeSize() {
    //        //取得SD卡文件路径
    //        File   path = Environment.getExternalStorageDirectory();
    //        StatFs sf   = new StatFs(path.getPath());
    //        //获取单个数据块的大小(Byte)
    //        long blockSize = sf.getBlockSize();
    //        //空闲的数据块的数量
    //        long freeBlocks = sf.getAvailableBlocks();
    //        //返回SD卡空闲大小
    //        //return freeBlocks * blockSize;  //单位Byte
    //        //return (freeBlocks * blockSize)/1024;   //单位KB
    //        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    //    }


    /**
     * 获取SD卡路径
     * @return
     */
    public static String getSDCardPath() {
        return getExterStorageDirectory();//File.separator文件路径分割符
    }

    /**
     *  判断SD卡是否可用
     * @return
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     *  获取SD卡剩余空间
     * @return
     */
    public static long getSDFreeSize() {
        if (isSDCardAvailable()) {
            StatFs statFs = new StatFs(getSDCardPath());

            long blockSize = statFs.getBlockSize();

            long freeBlocks = statFs.getAvailableBlocks();
            return freeBlocks * blockSize;
        }

        return 0;
    }

    /**
     *  获取SD卡的总容量
     * @return
     */
    public static long getSDAllSize() {
        if (isSDCardAvailable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory()
                                  .getAbsolutePath();
        }
        StatFs stat            = new StatFs(filePath);
        long   availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 拷贝文件，通过返回值判断是否拷贝成功
     * @param sourcePath
     *            源文件路径
     * @param targetPath
     *            目标文件路径
     * @return
     */
    public static boolean copyFile(String sourcePath, String targetPath) {
        boolean isOK = false;
        if (!TextUtils.isEmpty(sourcePath) && !TextUtils.isEmpty(targetPath)) {
            File sourcefile = new File(sourcePath);
            File targetFile = new File(targetPath);
            if (!sourcefile.exists()) {
                return false;
            }
            if (sourcefile.isDirectory()) {
                isOK = copyDir(sourcefile, targetFile);
            } else if (sourcefile.isFile()) {
                if (!targetFile.exists()) {
                    createFile(targetPath);
                }
                FileOutputStream outputStream = null;
                FileInputStream  inputStream  = null;
                try {
                    inputStream = new FileInputStream(sourcefile);
                    outputStream = new FileOutputStream(targetFile);
                    byte[] bs = new byte[1024];
                    int    len;
                    while ((len = inputStream.read(bs)) != -1) {
                        outputStream.write(bs, 0, len);
                    }
                    isOK = true;
                } catch (Exception e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                    isOK = false;
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            //                            Logger.i(TAG, e.getLocalizedMessage());
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            //                            Logger.i(TAG, e.getLocalizedMessage());
                        }
                    }
                }
            }

            return isOK;
        }
        return false;
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (!file.exists()) {
                return true;
            }
            try {
                if (file.isDirectory()){
                    deleteDir(file);
                    return true;
                }else {
                    return file.delete();
                }
            } catch (Exception e) {
                return false;
            }
            //            return true;
        }
        return true;
    }

    /**
     * 统计文件夹文件的大小
     */
    public static long getSize(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            // 如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
            if (!file.isFile()) {
                // 获取文件大小
                File[] fl = file.listFiles();
                long   ss = 0;
                for (File f : fl) { ss += getSize(f); }
                return ss;
            } else {
                long ss = (long) file.length();
                return ss; // 单位制bytes
            }
        } else {
            // System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0;
        }
    }

    /**
     * 把bytes转换成MB
     */
    public static String getTrafficStr(long total) {
        DecimalFormat format = new DecimalFormat("##0.0");
        if (total < 1024 * 1024) {
            return format.format(total / 1024f) + "KB";
        } else if (total < 1024 * 1024 * 1024) {
            return format.format(total / 1024f / 1024f) + "MB";
        } else if (total < 1024 * 1024 * 1024 * 1024) {
            return format.format(total / 1024f / 1024f / 1024f) + "GB";
        } else {
            return "统计错误";
        }
    }

    /**
     * 删除文件夹里面的所以文件
     */
    public static void deleteDir(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    deleteDir(files[i]);
                }
            }
        }
    }

    /**
     * 剪切文件，将文件拷贝到目标目录，再将源文件删除
     * @param sourcePath
     * @param targetPath
     */
    public static boolean cutFile(String sourcePath, String targetPath) {
        boolean isSuccessful = copyFile(sourcePath, targetPath);
        if (isSuccessful) {
            // 拷贝成功则删除源文件
            return deleteFile(sourcePath);
        }
        return false;
    }

    /**
     *  拷贝目录
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean copyDir(File sourceFile, File targetFile) {
        if (sourceFile == null || targetFile == null) {
            return false;
        }
        if (!sourceFile.exists()) {
            return false;
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 获取目录下所有文件和文件夹的列表
        File[] files = sourceFile.listFiles();
        if (files == null || files.length < 1) {
            return false;
        }
        File         file         = null;
        StringBuffer buffer       = new StringBuffer();
        boolean      isSuccessful = false;
        // 遍历目录下的所有文件文件夹，分别处理
        for (int i = 0; i < files.length; i++) {
            file = files[i];
            buffer.setLength(0);
            buffer.append(targetFile.getAbsolutePath())
                  .append(File.separator)
                  .append(file.getName());
            if (file.isFile()) {
                // 文件直接调用拷贝文件方法
                isSuccessful = copyFile(file.getAbsolutePath(), buffer.toString());
                if (!isSuccessful) {
                    return false;
                }
            } else if (file.isDirectory()) {
                // 目录再次调用拷贝目录方法
                copyDir(file, new File(buffer.toString()));
            }

        }
        return true;
    }

    /**
     * 剪切目录，先将目录拷贝完后再删除源目录
     * @param sourceDir
     * @param targetDir
     * @return
     */
    public static boolean cutDir(String sourceDir, String targetDir) {
        File    sourceFile       = new File(sourceDir);
        File    targetFile       = new File(targetDir);
        boolean isCopySuccessful = copyDir(sourceFile, targetFile);
        if (isCopySuccessful) {
            return deleteDir(sourceDir);
        }
        return false;
    }

    /**
     * 删除目录
     * @param dir
     * @return
     */
    public static boolean deleteDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return false;
        }
        File[]  files        = file.listFiles();
        boolean isSuccessful = false;
        if (files.length == 0) {
            file.delete();
            return true;
        }
        // 对所有列表中的路径进行判断是文件还是文件夹
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                isSuccessful = deleteDir(files[i].getAbsolutePath());
            } else if (files[i].isFile()) {
                isSuccessful = deleteFile(files[i].getAbsolutePath());
            }

            if (!isSuccessful) {
                // 如果有删除失败的情况直接跳出循环
                break;
            }
        }
        if (isSuccessful) {
            file.delete();
        }
        return isSuccessful;
    }

    /**
     *
     * 将流写入指定文件
     * @param inputStream
     * @param path
     * @return
     */
    public static boolean stream2File(InputStream inputStream, String path) {
        File             file             = new File(path);
        boolean          isSuccessful     = true;
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
                File file2 = file.getParentFile();
                file2.mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            byte[] bs     = new byte[1024];
            int    length = 0;
            while ((length = inputStream.read(bs)) != -1) {
                fileOutputStream.write(bs, 0, length);
            }
        } catch (Exception e) {
            isSuccessful = false;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return isSuccessful;
    }

    /**
     * 创建目录
     * @param path
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 修改文件读写权限
     * @param fileAbsPath
     * @param mode
     */
    public static void chmodFile(String fileAbsPath, String mode) {
        String cmd = "chmod " + mode + " " + fileAbsPath;
        try {
            Runtime.getRuntime()
                   .exec(cmd);
        } catch (Exception e) {
            //            Logger.i(TAG, e.getLocalizedMessage());
        }
    }

    /**
     *
     * 将object对象写入outFile文件
     * @param outFile
     * @param object
     * @param context
     */
    public static void writeObject2File(String outFile, Object object, Context context) {
        ObjectOutputStream out       = null;
        FileOutputStream   outStream = null;
        try {
            File dir = context.getDir("cache", Context.MODE_PRIVATE);
            outStream = new FileOutputStream(new File(dir, outFile));
            out = new ObjectOutputStream(new BufferedOutputStream(outStream));
            out.writeObject(object);
            out.flush();
        } catch (Exception e) {
            //            Logger.i(TAG, e.getLocalizedMessage());
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     *
     * 从outFile文件读取对象
     * @param filePath
     * @param context
     * @return
     */
    public static Object readObjectFromPath(String filePath, Context context) {
        Object            object      = null;
        ObjectInputStream in          = null;
        FileInputStream   inputStream = null;
        try {
            File dir = context.getDir("cache", Context.MODE_PRIVATE);
            File f   = new File(dir, filePath);
            if (f == null || !f.exists()) {
                return null;
            }
            inputStream = new FileInputStream(new File(dir, filePath));
            in = new ObjectInputStream(new BufferedInputStream(inputStream));
            object = in.readObject();
        } catch (Exception e) {
            //            Logger.i(TAG, e.getLocalizedMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                }
            }

        }
        return object;
    }

    /**
     * 读取指定路径下的文件内容
     * @param path
     * @return 文件内容
     */
    public static String readFile(String path) {
        BufferedReader br = null;
        try {
            File myFile = new File(path);
            br = new BufferedReader(new FileReader(myFile));
            StringBuffer sb   = new StringBuffer();
            String       line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            //            Logger.i(TAG, e.getLocalizedMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //                    Logger.i(TAG, e.getLocalizedMessage());
                }
            }
        }
        return null;
    }

    /**
     * 创建文件，并修改读写权限
     * @param filePath
     * @param mode
     * @return
     */
    public static File createFile(String filePath, String mode) {
        File desFile = null;
        try {
            String desDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
            File   dir    = new File(desDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            chmodFile(dir.getAbsolutePath(), mode);
            desFile = new File(filePath);
            if (!desFile.exists()) {
                desFile.createNewFile();
            }
            chmodFile(desFile.getAbsolutePath(), mode);
        } catch (Exception e) {
            //            Logger.i(TAG, e.getLocalizedMessage());
        }
        return desFile;
    }

    /**
     * 根据指定路径，创建父目录及文件
     * @param filePath
     * @return File 如果创建失败的话，返回null
     */
    public static File createFile(String filePath) {
        return createFile(filePath, "755");
    }

    /**
     * 获取系统存储路径
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory()
                          .getAbsolutePath();
    }

    /**
     * 获取外部存储路径
     * @return
     */
    public static String getExternalStorageDirectoryPath() {
        return Environment.getExternalStorageDirectory()
                          .getPath();
    }


}
