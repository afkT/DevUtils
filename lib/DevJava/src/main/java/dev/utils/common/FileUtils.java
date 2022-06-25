package dev.utils.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;
import dev.utils.common.encrypt.MD5Utils;

/**
 * detail: 文件操作工具类
 * @author Ttt
 */
public final class FileUtils {

    private FileUtils() {
    }

    // 日志 TAG
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    public static File getFile(final String filePath) {
        return getFileByPath(filePath);
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    public static File getFile(
            final String filePath,
            final String fileName
    ) {
        return (filePath != null && fileName != null) ? new File(filePath, fileName) : null;
    }

    /**
     * 获取文件
     * @param parent   文件路径
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    public static File getFile(
            final File parent,
            final String fileName
    ) {
        return (parent != null && fileName != null) ? new File(parent, fileName) : null;
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    public static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * 获取路径, 并且进行创建目录
     * @param filePath 存储目录
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    public static File getFileCreateFolder(
            final String filePath,
            final String fileName
    ) {
        // 防止不存在目录文件, 自动创建
        createFolder(filePath);
        // 返回处理过后的 File
        return getFile(filePath, fileName);
    }

    /**
     * 获取路径, 并且进行创建目录
     * @param filePath 存储目录
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    public static String getFilePathCreateFolder(
            final String filePath,
            final String fileName
    ) {
        // 防止不存在目录文件, 自动创建
        createFolder(filePath);
        // 返回处理过后的 File
        File file = getFile(filePath, fileName);
        // 返回文件路径
        return getAbsolutePath(file);
    }

    /**
     * 判断某个文件夹是否创建, 未创建则创建 ( 纯路径无文件名 )
     * @param dirPath 文件夹路径 ( 无文件名字. 后缀 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolder(final String dirPath) {
        return createFolder(getFileByPath(dirPath));
    }

    /**
     * 判断某个文件夹是否创建, 未创建则创建 ( 纯路径无文件名 )
     * @param file 文件夹路径 ( 无文件名字. 后缀 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolder(final File file) {
        if (file != null) {
            try {
                // 当这个文件夹不存在的时候则创建文件夹
                if (!file.exists()) {
                    // 允许创建多级目录
                    return file.mkdirs();
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "createFolder");
            }
        }
        return false;
    }

    /**
     * 创建文件夹目录 ( 可以传入文件名 )
     * @param filePath 文件路径 + 文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolderByPath(final String filePath) {
        return createFolderByPath(getFileByPath(filePath));
    }

    /**
     * 创建文件夹目录 ( 可以传入文件名 )
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolderByPath(final File file) {
        // 创建文件夹 ( 如果失败才创建 )
        if (file != null) {
            if (file.exists()) {
                return true;
            } else if (!file.getParentFile().mkdirs()) {
                return createFolder(file.getParent());
            }
        }
        return false;
    }

    /**
     * 创建多个文件夹, 如果不存在则创建
     * @param filePaths 文件路径数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolderByPaths(final String... filePaths) {
        if (filePaths != null && filePaths.length != 0) {
            for (String filePath : filePaths) {
                createFolder(filePath);
            }
            return true;
        }
        return false;
    }

    /**
     * 创建多个文件夹, 如果不存在则创建
     * @param files 文件数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createFolderByPaths(final File... files) {
        if (files != null && files.length != 0) {
            for (File file : files) {
                createFolder(file);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 判断目录是否存在, 不存在则判断是否创建成功
     * @param dirPath 目录路径
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在, 不存在则判断是否创建成功
     * @param file 文件
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    public static boolean createOrExistsDir(final File file) {
        // 如果存在, 是目录则返回 true, 是文件则返回 false, 不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在, 不存在则判断是否创建成功
     * @param filePath 文件路径
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在, 不存在则判断是否创建成功
     * @param file 文件
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // 如果存在, 是文件则返回 true, 是目录则返回 false
        if (file.exists()) return file.isFile();
        // 判断文件是否存在, 不存在则直接返回
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            // 存在, 则返回新的路径
            return file.createNewFile();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "createOrExistsFile");
            return false;
        }
    }

    /**
     * 判断文件是否存在, 存在则在创建之前删除
     * @param filePath 文件路径
     * @return {@code true} 创建成功, {@code false} 创建失败
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在, 存在则在创建之前删除
     * @param file 文件
     * @return {@code true} 创建成功, {@code false} 创建失败
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回 false
        if (file.exists() && !file.delete()) return false;
        // 创建目录失败返回 false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "createFileByDeleteOldFile");
            return false;
        }
    }

    /**
     * 通过文件后缀创建时间戳文件名
     * @param extension 文件后缀 ( 有无 . 都行 )
     * @return 时间戳文件名 ( 包含后缀 )
     */
    public static String createTimestampFileName(final String extension) {
        // 临时后缀名
        String temp = StringUtils.clearSpace(extension);
        if (StringUtils.isNotEmpty(temp)) {
            temp = StringUtils.clearSEWiths(temp, ".");
            if (StringUtils.isNotEmpty(temp)) {
                return System.currentTimeMillis() + "." + temp;
            }
        }
        return null;
    }

    /**
     * 通过文件名创建时间戳文件名
     * @param fileName 文件名
     * @return 时间戳文件名 ( 包含后缀 )
     */
    public static String createTimestampFileNameByName(final String fileName) {
        return createTimestampFileName(FileUtils.getFileExtension(fileName));
    }

    /**
     * 通过文件创建时间戳文件名
     * @param file 文件
     * @return 时间戳文件名 ( 包含后缀 )
     */
    public static String createTimestampFileNameByFile(final File file) {
        return createTimestampFileName(FileUtils.getFileExtension(file));
    }

    /**
     * 通过文件路径创建时间戳文件名
     * @param filePath 文件路径
     * @return 时间戳文件名 ( 包含后缀 )
     */
    public static String createTimestampFileNameByPath(final String filePath) {
        return createTimestampFileName(FileUtils.getFileExtension(filePath));
    }

    // =

    /**
     * Path List 转 File List
     * @param paths Path List
     * @return File List
     */
    public static List<File> convertFiles(final List<String> paths) {
        return convertFiles(paths, true);
    }

    /**
     * Path List 转 File List
     * @param paths  Path List
     * @param ignore 是否忽略 null
     * @return File List
     */
    public static List<File> convertFiles(
            final List<String> paths,
            final boolean ignore
    ) {
        List<File> files = new ArrayList<>();
        if (paths != null && !paths.isEmpty()) {
            for (int i = 0, len = paths.size(); i < len; i++) {
                String path = paths.get(i);
                if (path == null) {
                    if (!ignore) files.add(null);
                    continue;
                }
                files.add(new File(path));
            }
        }
        return files;
    }

    /**
     * File List 转 Path List
     * @param files File List
     * @return Path List
     */
    public static List<String> convertPaths(final List<File> files) {
        return convertPaths(files, true);
    }

    /**
     * File List 转 Path List
     * @param files  File List
     * @param ignore 是否忽略 null
     * @return Path List
     */
    public static List<String> convertPaths(
            final List<File> files,
            final boolean ignore
    ) {
        List<String> paths = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (int i = 0, len = files.size(); i < len; i++) {
                File file = files.get(i);
                if (file == null) {
                    if (!ignore) paths.add(null);
                    continue;
                }
                paths.add(file.getAbsolutePath());
            }
        }
        return paths;
    }

    // =

    /**
     * 获取文件路径
     * @param file 文件
     * @return 文件路径
     */
    public static String getPath(final File file) {
        return file != null ? file.getPath() : null;
    }

    /**
     * 获取文件绝对路径
     * @param file 文件
     * @return 文件绝对路径
     */
    public static String getAbsolutePath(final File file) {
        return file != null ? file.getAbsolutePath() : null;
    }

    // =

    /**
     * 获取文件名
     * @param file 文件
     * @return 文件名
     */
    public static String getName(final File file) {
        return file != null ? file.getName() : null;
    }

    /**
     * 获取文件名
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getName(final String filePath) {
        return getName(filePath, "");
    }

    /**
     * 获取文件名
     * @param filePath   文件路径
     * @param defaultStr 默认字符串
     * @return 文件名, 如果文件路径为 null 时, 返回默认字符串
     */
    public static String getName(
            final String filePath,
            final String defaultStr
    ) {
        return StringUtils.isEmpty(filePath) ? defaultStr : new File(filePath).getName();
    }

    /**
     * 获取文件后缀名 ( 无 "." 单独后缀 )
     * @param file 文件
     * @return 文件后缀名 ( 无 "." 单独后缀 )
     */
    public static String getFileSuffix(final File file) {
        return getFileSuffix(getAbsolutePath(file));
    }

    /**
     * 获取文件后缀名 ( 无 "." 单独后缀 )
     * @param filePath 文件路径或文件名
     * @return 文件后缀名 ( 无 "." 单独后缀 )
     */
    public static String getFileSuffix(final String filePath) {
        // 获取最后的索引
        int lastIndexOf;
        // 判断是否存在
        if (filePath != null && (lastIndexOf = filePath.lastIndexOf('.')) != -1) {
            String result = filePath.substring(lastIndexOf);
            if (result.startsWith(".")) {
                return result.substring(1);
            }
            return result;
        }
        return null;
    }

    /**
     * 获取文件名 ( 无后缀 )
     * @param file 文件
     * @return 文件名 ( 无后缀 )
     */
    public static String getFileNotSuffix(final File file) {
        return getFileNotSuffix(getName(file));
    }

    /**
     * 获取文件名 ( 无后缀 )
     * @param filePath 文件路径
     * @return 文件名 ( 无后缀 )
     */
    public static String getFileNotSuffixToPath(final String filePath) {
        return getFileNotSuffix(getName(filePath));
    }

    /**
     * 获取文件名 ( 无后缀 )
     * @param fileName 文件名
     * @return 文件名 ( 无后缀 )
     */
    public static String getFileNotSuffix(final String fileName) {
        if (fileName != null) {
            if (fileName.lastIndexOf('.') != -1) {
                return fileName.substring(0, fileName.lastIndexOf('.'));
            } else {
                return fileName;
            }
        }
        return null;
    }

    /**
     * 获取路径中的不带扩展名的文件名
     * @param file 文件
     * @return 不带扩展名的文件名
     */
    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 获取路径中的不带扩展名的文件名
     * @param filePath 文件路径
     * @return 不带扩展名的文件名
     */
    public static String getFileNameNoExtension(final String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 获取路径中的文件扩展名
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    /**
     * 获取路径中的文件扩展名
     * @param filePath 文件路径
     * @return 文件扩展名
     */
    public static String getFileExtension(final String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    // =

    /**
     * 检查是否存在某个文件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 检查是否存在某个文件
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 检查是否存在某个文件
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileExists(
            final String filePath,
            final String fileName
    ) {
        return filePath != null && fileName != null && new File(filePath, fileName).exists();
    }

    /**
     * 判断是否文件
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否文件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 判断是否文件夹
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDirectory(final String filePath) {
        return isDirectory(getFileByPath(filePath));
    }

    /**
     * 判断是否文件夹
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDirectory(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断是否隐藏文件
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHidden(final String filePath) {
        return isHidden(getFileByPath(filePath));
    }

    /**
     * 判断是否隐藏文件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHidden(final File file) {
        return file != null && file.exists() && file.isHidden();
    }

    /**
     * 文件是否可读
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canRead(final String filePath) {
        return canRead(getFileByPath(filePath));
    }

    /**
     * 文件是否可读
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canRead(final File file) {
        return file != null && file.exists() && file.canRead();
    }

    /**
     * 文件是否可写
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canWrite(final String filePath) {
        return canWrite(getFileByPath(filePath));
    }

    /**
     * 文件是否可写
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canWrite(final File file) {
        return file != null && file.exists() && file.canWrite();
    }

    /**
     * 文件是否可读写
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canReadWrite(final String filePath) {
        return canReadWrite(getFileByPath(filePath));
    }

    /**
     * 文件是否可读写
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canReadWrite(final File file) {
        return file != null && file.exists() && file.canRead() && file.canWrite();
    }

    // =

    /**
     * 获取文件最后修改的毫秒时间戳
     * @param filePath 文件路径
     * @return 文件最后修改的毫秒时间戳
     */
    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * 获取文件最后修改的毫秒时间戳
     * @param file 文件
     * @return 文件最后修改的毫秒时间戳
     */
    public static long getFileLastModified(final File file) {
        if (file == null) return 0L;
        return file.lastModified();
    }

    /**
     * 获取文件编码格式
     * @param filePath 文件路径
     * @return 文件编码格式
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 获取文件编码格式
     * @param file 文件
     * @return 文件编码格式
     */
    public static String getFileCharsetSimple(final File file) {
        if (!isFileExists(file)) return null;
        int         pos = 0;
        InputStream is  = null;
        try {
            is  = new BufferedInputStream(new FileInputStream(file));
            pos = (is.read() << 8) + is.read();
        } catch (IOException e) {
            JCLogUtils.eTag(TAG, e, "getFileCharsetSimple");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        switch (pos) {
            case 0xefbb:
                return DevFinal.ENCODE.UTF_8;
            case 0xfffe:
                return DevFinal.ENCODE.UNICODE;
            case 0xfeff:
                return DevFinal.ENCODE.UTF_16BE;
            default:
                return DevFinal.ENCODE.GBK;
        }
    }

    /**
     * 获取文件行数
     * @param filePath 文件路径
     * @return 文件行数
     */
    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * 获取文件行数 ( 比 readLine 要快很多 )
     * @param file 文件
     * @return 文件行数
     */
    public static int getFileLines(final File file) {
        if (!isFileExists(file)) return 0;
        int         lineCount = 1;
        InputStream is        = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int    readChars;
            if (DevFinal.SYMBOL.NEW_LINE.endsWith("\n")) {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\n') ++lineCount;
                    }
                }
            } else {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\r') ++lineCount;
                    }
                }
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileLines");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return lineCount;
    }

    // =

    /**
     * 获取文件大小
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static String getFileSize(final String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小
     * @param file 文件
     * @return 文件大小
     */
    public static String getFileSize(final File file) {
        return formatByteMemorySize(getFileLength(file));
    }

    /**
     * 获取目录大小
     * @param dirPath 目录路径
     * @return 文件大小
     */
    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * 获取目录大小
     * @param dir 目录
     * @return 文件大小
     */
    public static String getDirSize(final File dir) {
        return formatByteMemorySize(getDirLength(dir));
    }

    /**
     * 获取文件大小
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static long getFileLength(final String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * 获取文件大小
     * @param file 文件
     * @return 文件大小
     */
    public static long getFileLength(final File file) {
        return file != null ? file.length() : 0L;
    }

    /**
     * 获取目录全部文件大小
     * @param dirPath 目录路径
     * @return 目录全部文件大小
     */
    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * 获取目录全部文件大小
     * @param dir 目录
     * @return 目录全部文件大小
     */
    public static long getDirLength(final File dir) {
        if (!isDirectory(dir)) return 0L;
        long   len   = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 获取文件大小 ( 网络资源 )
     * @param httpUri 文件网络链接
     * @return 文件大小
     */
    public static long getFileLengthNetwork(final String httpUri) {
        if (StringUtils.isEmpty(httpUri)) return 0L;
        // 判断是否网络资源
        boolean isHttpRes = httpUri.toLowerCase().startsWith("http:") || httpUri.toLowerCase().startsWith("https:");
        if (isHttpRes) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(httpUri).openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return conn.getContentLength();
                }
                return 0L;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getFileLengthNetwork");
            }
        }
        return 0L;
    }

    /**
     * 获取路径中的文件名
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(final File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    /**
     * 获取路径中的文件名
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(final String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取路径中的最长目录地址
     * @param file 文件
     * @return 最长目录地址
     */
    public static String getDirName(final File file) {
        if (file == null) return null;
        return getDirName(file.getPath());
    }

    /**
     * 获取全路径中的最长目录地址
     * @param filePath 文件路径
     * @return 最长目录地址
     */
    public static String getDirName(final String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    // =

    /**
     * 重命名文件 ( 同个目录下, 修改文件名 )
     * @param filePath    文件路径
     * @param newFileName 文件新名称
     * @return {@code true} yes, {@code false} no
     */
    public static boolean rename(
            final String filePath,
            final String newFileName
    ) {
        return rename(getFileByPath(filePath), newFileName);
    }

    /**
     * 重命名文件 ( 同个目录下, 修改文件名 )
     * @param file        文件
     * @param newFileName 文件新名称
     * @return {@code true} yes, {@code false} no
     */
    public static boolean rename(
            final File file,
            final String newFileName
    ) {
        if (StringUtils.isEmpty(newFileName)) return false;
        // 文件为空返回 false
        if (file == null) return false;
        // 文件不存在返回 false
        if (!file.exists()) return false;
        // 如果文件名没有改变返回 true
        if (newFileName.equals(file.getName())) return true;
        // 拼接新的文件路径
        File newFile = new File(file.getParent() + File.separator + newFileName);
        // 如果重命名的文件已存在返回 false
        return !newFile.exists() && file.renameTo(newFile);
    }

    // =============
    // = 文件大小处理 =
    // =============

    /**
     * 传入文件路径, 返回对应的文件大小
     * @param filePath 文件路径
     * @return 文件大小转换字符串
     */
    public static String formatFileSize(final String filePath) {
        File file = getFileByPath(filePath);
        return formatFileSize(file != null ? file.length() : 0);
    }

    /**
     * 传入文件路径, 返回对应的文件大小
     * @param file 文件
     * @return 文件大小转换字符串
     */
    public static String formatFileSize(final File file) {
        return formatFileSize(file != null ? file.length() : 0);
    }

    /**
     * 传入对应的文件大小, 返回转换后文件大小
     * @param fileSize 文件大小
     * @return 文件大小转换字符串
     */
    public static String formatFileSize(final double fileSize) {
        // 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String        fileSizeStr;
        if (fileSize <= 0) {
            fileSizeStr = "0B";
        } else if (fileSize < 1024) {
            fileSizeStr = df.format(fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeStr = df.format(fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeStr = df.format(fileSize / 1048576) + "MB";
        } else if (fileSize < 1099511627776D) {
            fileSizeStr = df.format(fileSize / 1073741824) + "GB";
        } else {
            fileSizeStr = df.format(fileSize / 1099511627776D) + "TB";
        }
        return fileSizeStr;
    }

    /**
     * 字节数转合适内存大小 保留 3 位小数
     * @param byteSize 字节数
     * @return 合适内存大小字符串
     */
    public static String formatByteMemorySize(final double byteSize) {
        return formatByteMemorySize(3, byteSize);
    }

    /**
     * 字节数转合适内存大小 保留 number 位小数
     * @param number   保留小数位数
     * @param byteSize 字节数
     * @return 合适内存大小字符串
     */
    public static String formatByteMemorySize(
            final int number,
            final double byteSize
    ) {
        if (byteSize < 0D) {
            return "0B";
        } else if (byteSize < 1024D) {
            return String.format("%." + number + "fB", byteSize);
        } else if (byteSize < 1048576D) {
            return String.format("%." + number + "fKB", byteSize / 1024D);
        } else if (byteSize < 1073741824D) {
            return String.format("%." + number + "fMB", byteSize / 1048576D);
        } else if (byteSize < 1099511627776D) {
            return String.format("%." + number + "fGB", byteSize / 1073741824D);
        } else {
            return String.format("%." + number + "fTB", byteSize / 1099511627776D);
        }
    }

    // ==========
    // = 文件操作 =
    // ==========

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFile(final String filePath) {
        return deleteFile(getFileByPath(filePath));
    }

    /**
     * 删除文件
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFile(final File file) {
        // 文件存在, 并且不是目录文件, 则直接删除
        if (file != null && file.exists() && !file.isDirectory()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除多个文件
     * @param filePaths 文件路径数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFiles(final String... filePaths) {
        if (filePaths != null && filePaths.length != 0) {
            for (String filePath : filePaths) {
                deleteFile(filePath);
            }
            return true;
        }
        return false;
    }

    /**
     * 删除多个文件
     * @param files 文件数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFiles(final File... files) {
        if (files != null && files.length != 0) {
            for (File file : files) {
                deleteFile(file);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 删除文件夹
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFolder(final String filePath) {
        return deleteFolder(getFileByPath(filePath));
    }

    /**
     * 删除文件夹
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteFolder(final File file) {
        if (file != null) {
            try {
                // 文件存在, 并且不是目录文件, 则直接删除
                if (file.exists()) {
                    if (file.isDirectory()) { // 属于文件目录
                        File[] files = file.listFiles();
                        if (files != null) {
                            for (File deleteFile : files) {
                                deleteFolder(deleteFile.getPath());
                            }
                        }
                        return file.delete();
                    } else { // 属于文件
                        return deleteFile(file);
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "deleteFolder");
            }
        }
        return false;
    }

    /**
     * 保存文件
     * @param filePath 文件路径
     * @param data     待存储数据
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveFile(
            final String filePath,
            final byte[] data
    ) {
        return saveFile(FileUtils.getFile(filePath), data);
    }

    /**
     * 保存文件
     * @param file 文件
     * @param data 待存储数据
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveFile(
            final File file,
            final byte[] data
    ) {
        if (file != null && data != null) {
            FileOutputStream     fos = null;
            BufferedOutputStream bos = null;
            try {
                // 防止文件夹没创建
                createFolder(getDirName(file));
                // 写入文件
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(data);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "saveFile");
            } finally {
                CloseUtils.closeIOQuietly(bos, fos);
            }
        }
        return false;
    }

    /**
     * 追加文件
     * @param filePath 文件路径
     * @param data     待追加数据
     * @return {@code true} success, {@code false} fail
     */
    public static boolean appendFile(
            final String filePath,
            final byte[] data
    ) {
        return appendFile(FileUtils.getFile(filePath), data);
    }

    /**
     * 追加文件
     * <pre>
     *     如果未创建文件, 则会创建并写入数据 ( 等同 {@link #saveFile} )
     *     如果已创建文件, 则在结尾追加数据
     * </pre>
     * @param file 文件
     * @param data 待追加数据
     * @return {@code true} success, {@code false} fail
     */
    public static boolean appendFile(
            final File file,
            final byte[] data
    ) {
        FileOutputStream     fos = null;
        BufferedOutputStream bos = null;
        try {
            // 防止文件夹没创建
            createFolder(getDirName(file));
            // 写入文件
            fos = new FileOutputStream(file, true);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "appendFile");
        } finally {
            CloseUtils.closeIOQuietly(bos, fos);
        }
        return false;
    }

    // =

    /**
     * 读取文件
     * @param filePath 文件路径
     * @return 文件内容 byte[]
     */
    public static byte[] readFileBytes(final String filePath) {
        return readFileBytes(getFileByPath(filePath));
    }

    /**
     * 读取文件
     * @param file 文件
     * @return 文件内容 byte[]
     */
    public static byte[] readFileBytes(final File file) {
        if (file != null && file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                int    length = fis.available();
                byte[] buffer = new byte[length];
                fis.read(buffer);
                return buffer;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "readFileBytes");
            } finally {
                CloseUtils.closeIOQuietly(fis);
            }
        }
        return null;
    }

    /**
     * 读取文件
     * @param inputStream {@link InputStream}
     * @return 文件内容 byte[]
     */
    public static byte[] readFileBytes(final InputStream inputStream) {
        if (inputStream != null) {
            try {
                int    length = inputStream.available();
                byte[] buffer = new byte[length];
                inputStream.read(buffer);
                return buffer;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "readFileBytes");
            } finally {
                CloseUtils.closeIOQuietly(inputStream);
            }
        }
        return null;
    }

    /**
     * 读取文件
     * @param filePath 文件路径
     * @return 文件内容字符串
     */
    public static String readFile(final String filePath) {
        return readFile(getFileByPath(filePath));
    }

    /**
     * 读取文件
     * @param file 文件
     * @return 文件内容字符串
     */
    public static String readFile(final File file) {
        if (file != null && file.exists()) {
            try {
                return readFile(new FileInputStream(file));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "readFile");
            }
        }
        return null;
    }

    /**
     * 读取文件
     * @param inputStream {@link InputStream} new FileInputStream(path)
     * @return 文件内容字符串
     */
    public static String readFile(final InputStream inputStream) {
        return readFile(inputStream, null);
    }

    /**
     * 读取文件
     * @param inputStream {@link InputStream} new FileInputStream(path)
     * @param encode      编码格式
     * @return 文件内容字符串
     */
    public static String readFile(
            final InputStream inputStream,
            final String encode
    ) {
        if (inputStream != null) {
            BufferedReader br = null;
            try {
                InputStreamReader isr;
                if (encode != null) {
                    isr = new InputStreamReader(inputStream, encode);
                } else {
                    isr = new InputStreamReader(inputStream);
                }
                br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String        line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "readFile");
            } finally {
                CloseUtils.closeIOQuietly(br);
            }
        }
        return null;
    }

    // =

    /**
     * 复制单个文件
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean copyFile(
            final InputStream inputStream,
            final String destFilePath,
            final boolean overlay
    ) {
        if (inputStream == null || destFilePath == null) {
            return false;
        }
        File destFile = new File(destFilePath);
        // 如果属于文件夹则跳过
        if (destFile.isDirectory()) {
            return false;
        }
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件, 无论目标文件是目录还是单个文件
                destFile.delete();
            } else { // 如果文件存在, 但是不覆盖, 则返回 false 表示失败
                return false;
            }
        } else {
            // 如果目标文件所在目录不存在, 则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败: 创建目标文件所在目录失败
                    return false;
                }
            }
        }
        // 复制文件
        int          len; // 读取的字节数
        InputStream  is = inputStream;
        OutputStream os = null;
        try {
            os = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "copyFile");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(os, is);
        }
    }

    /**
     * 复制单个文件
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean copyFile(
            final String srcFilePath,
            final String destFilePath,
            final boolean overlay
    ) {
        if (destFilePath == null) return false;
        if (!FileUtils.isFile(srcFilePath)) return false;
        return copyFile(
                FileIOUtils.getFileInputStream(srcFilePath),
                destFilePath, overlay
        );
    }

    /**
     * 复制文件夹
     * @param srcFolderPath  待复制的文件夹地址
     * @param destFolderPath 存储目标文件夹地址
     * @param overlay        如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean copyFolder(
            final String srcFolderPath,
            final String destFolderPath,
            final boolean overlay
    ) {
        return copyFolder(srcFolderPath, destFolderPath, srcFolderPath, overlay);
    }

    /**
     * 复制文件夹
     * @param srcFolderPath  待复制的文件夹地址
     * @param destFolderPath 存储目标文件夹地址
     * @param sourcePath     源文件地址 ( 用于保递归留记录 )
     * @param overlay        如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    private static boolean copyFolder(
            final String srcFolderPath,
            final String destFolderPath,
            final String sourcePath,
            final boolean overlay
    ) {
        if (srcFolderPath == null || destFolderPath == null || sourcePath == null) {
            return false;
        }
        File srcFile = new File(srcFolderPath);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isDirectory()) { // 不属于文件夹则跳过
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFolderPath);
        // 如果文件夹没创建, 则创建
        if (!destFile.exists()) {
            // 允许创建多级目录
            destFile.mkdirs();
        }
        // 判断是否属于文件夹 ( 不属于文件夹则跳过 )
        if (!destFile.isDirectory()) {
            return false;
        }
        // 判断是否存在
        if (srcFile.exists()) {
            // 获取文件路径
            File[] files = srcFile.listFiles();
            // 防止不存在文件
            if (files != null && files.length != 0) {
                // 进行遍历
                for (File file : files) {
                    // 文件存在才进行处理
                    if (file.exists()) {
                        // 属于文件夹
                        if (file.isDirectory()) {
                            copyFolder(file.getAbsolutePath(), destFolderPath, sourcePath, overlay);
                        } else { // 属于文件
                            // 复制的文件地址
                            String filePath = file.getAbsolutePath();
                            // 获取源文件地址并且进行判断
                            String dealSource = new File(sourcePath).getAbsolutePath();
                            // 属于最前才进行处理
                            if (filePath.indexOf(dealSource) == 0) {
                                // 获取处理后的地址
                                dealSource = filePath.substring(dealSource.length());
                                // 获取需要复制保存的地址
                                String savePath = new File(destFolderPath, dealSource).getAbsolutePath();
                                // 进行复制文件
                                boolean result = copyFile(filePath, savePath, overlay);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    // =

    /**
     * 移动 ( 剪切 ) 文件
     * @param srcFilePath  待移动的文件地址
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean moveFile(
            final String srcFilePath,
            final String destFilePath,
            final boolean overlay
    ) {
        // 复制文件
        if (copyFile(srcFilePath, destFilePath, overlay)) {
            // 删除文件
            return deleteFile(srcFilePath);
        }
        return false;
    }

    /**
     * 移动 ( 剪切 ) 文件夹
     * @param srcFilePath  待移动的文件夹地址
     * @param destFilePath 存储目标文件夹地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean moveFolder(
            final String srcFilePath,
            final String destFilePath,
            final boolean overlay
    ) {
        // 复制文件夹
        if (copyFolder(srcFilePath, destFilePath, overlay)) {
            // 删除文件夹
            return deleteFolder(srcFilePath);
        }
        return false;
    }

    // =

    /**
     * detail: 覆盖 / 替换事件
     * @author Ttt
     */
    public interface OnReplaceListener {

        /**
         * 是否覆盖 / 替换文件
         * @return {@code true} yes, {@code false} no
         */
        boolean onReplace();
    }

    /**
     * 复制或移动目录
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param listener    是否覆盖监听器
     * @param isMove      是否移动
     * @return {@code true} 复制或移动成功, {@code false} 复制或移动失败
     */
    public static boolean copyOrMoveDir(
            final String srcDirPath,
            final String destDirPath,
            final OnReplaceListener listener,
            final boolean isMove
    ) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

    /**
     * 复制或移动目录
     * @param srcDir   源目录
     * @param destDir  目标目录
     * @param listener 是否覆盖监听器
     * @param isMove   是否移动
     * @return {@code true} 复制或移动成功, {@code false} 复制或移动失败
     */
    public static boolean copyOrMoveDir(
            final File srcDir,
            final File destDir,
            final OnReplaceListener listener,
            final boolean isMove
    ) {
        if (srcDir == null || destDir == null || listener == null) return false;
        // 为防止以上这种情况出现出现误判, 需分别在后面加个路径分隔符
        String srcPath  = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        // 源文件不存在或者不是目录则返回 false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (listener.onReplace()) { // 需要覆盖则删除旧目录
                if (!deleteAllInDir(destDir)) { // 删除文件失败的话返回 false
                    return false;
                }
            } else { // 不需要覆盖直接返回即可 true
                return true;
            }
        }
        // 目标目录不存在返回 false
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        if (files == null) return false;
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // 如果操作失败返回 false
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
            } else if (file.isDirectory()) {
                // 如果操作失败返回 false
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 复制或移动文件
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param listener     是否覆盖监听器
     * @param isMove       是否移动
     * @return {@code true} 复制或移动成功, {@code false} 复制或移动失败
     */
    public static boolean copyOrMoveFile(
            final String srcFilePath,
            final String destFilePath,
            final OnReplaceListener listener,
            final boolean isMove
    ) {
        return copyOrMoveFile(
                getFileByPath(srcFilePath),
                getFileByPath(destFilePath),
                listener, isMove
        );
    }

    /**
     * 复制或移动文件
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param listener 是否覆盖监听器
     * @param isMove   是否移动
     * @return {@code true} 复制或移动成功, {@code false} 复制或移动失败
     */
    public static boolean copyOrMoveFile(
            final File srcFile,
            final File destFile,
            final OnReplaceListener listener,
            final boolean isMove
    ) {
        if (srcFile == null || destFile == null || listener == null) return false;
        // 如果源文件和目标文件相同则返回 false
        if (srcFile.equals(destFile)) return false;
        // 源文件不存在或者不是文件则返回 false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) { // 目标文件存在
            if (listener.onReplace()) { // 需要覆盖则删除旧文件
                if (!destFile.delete()) { // 删除文件失败的话返回 false
                    return false;
                }
            } else { // 不需要覆盖直接返回即可 true
                return true;
            }
        }
        // 目标目录不存在返回 false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return FileIOUtils.writeFileFromIS(
                    destFile, new FileInputStream(srcFile), false
            ) && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            JCLogUtils.eTag(TAG, e, "copyOrMoveFile");
            return false;
        }
    }

    /**
     * 复制目录
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param listener    是否覆盖监听器
     * @return {@code true} 复制成功, {@code false} 复制失败
     */
    public static boolean copyDir(
            final String srcDirPath,
            final String destDirPath,
            final OnReplaceListener listener
    ) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 复制目录
     * @param srcDir   源目录
     * @param destDir  目标目录
     * @param listener 是否覆盖监听器
     * @return {@code true} 复制成功, {@code false} 复制失败
     */
    public static boolean copyDir(
            final File srcDir,
            final File destDir,
            final OnReplaceListener listener
    ) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    /**
     * 复制文件
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param listener     是否覆盖监听器
     * @return {@code true} 复制成功, {@code false} 复制失败
     */
    public static boolean copyFile(
            final String srcFilePath,
            final String destFilePath,
            final OnReplaceListener listener
    ) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 复制文件
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param listener 是否覆盖监听器
     * @return {@code true} 复制成功, {@code false} 复制失败
     */
    public static boolean copyFile(
            final File srcFile,
            final File destFile,
            final OnReplaceListener listener
    ) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    /**
     * 移动目录
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param listener    是否覆盖监听器
     * @return {@code true} 移动成功, {@code false} 移动失败
     */
    public static boolean moveDir(
            final String srcDirPath,
            final String destDirPath,
            final OnReplaceListener listener
    ) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 移动目录
     * @param srcDir   源目录
     * @param destDir  目标目录
     * @param listener 是否覆盖监听器
     * @return {@code true} 移动成功, {@code false} 移动失败
     */
    public static boolean moveDir(
            final File srcDir,
            final File destDir,
            final OnReplaceListener listener
    ) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    /**
     * 移动文件
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param listener     是否覆盖监听器
     * @return {@code true} 移动成功, {@code false} 移动失败
     */
    public static boolean moveFile(
            final String srcFilePath,
            final String destFilePath,
            final OnReplaceListener listener
    ) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 移动文件
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param listener 是否覆盖监听器
     * @return {@code true} 移动成功, {@code false} 移动失败
     */
    public static boolean moveFile(
            final File srcFile,
            final File destFile,
            final OnReplaceListener listener
    ) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    /**
     * 删除目录
     * @param dirPath 目录路径
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除目录下所有文件
     * @param dirPath 目录路径
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下所有文件
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, pathname -> true);
    }

    /**
     * 删除目录下所有文件
     * @param dirPath 目录路径
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下所有文件
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, pathname -> pathname.isFile());
    }

    /**
     * 删除目录下所有过滤的文件
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteFilesInDirWithFilter(
            final String dirPath,
            final FileFilter filter
    ) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 删除目录下所有过滤的文件
     * @param dir    目录
     * @param filter 过滤器
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    public static boolean deleteFilesInDirWithFilter(
            final File dir,
            final FileFilter filter
    ) {
        if (filter == null) return false;
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取目录下所有文件 ( 不递归进子目录 )
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * 获取目录下所有文件 ( 不递归进子目录 )
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * 获取目录下所有文件
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(
            final String dirPath,
            final boolean isRecursive
    ) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(
            final File dir,
            final boolean isRecursive
    ) {
        return listFilesInDirWithFilter(dir, pathname -> true, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件 ( 不递归进子目录 )
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(
            final String dirPath,
            final FileFilter filter
    ) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * 获取目录下所有过滤的文件 ( 不递归进子目录 )
     * @param dir    目录
     * @param filter 过滤器
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(
            final File dir,
            final FileFilter filter
    ) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * 获取目录下所有过滤的文件
     * @param dirPath     目录路径
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(
            final String dirPath,
            final FileFilter filter,
            final boolean isRecursive
    ) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(
            final File dir,
            final FileFilter filter,
            final boolean isRecursive
    ) {
        if (!isDirectory(dir) || filter == null) return null;
        List<File> list  = new ArrayList<>();
        File[]     files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    List<File> fileLists = listFilesInDirWithFilter(file, filter, true);
                    if (fileLists != null) {
                        list.addAll(fileLists);
                    }
                }
            }
        }
        return list;
    }

    // =

    /**
     * detail: 文件列表
     * @author Ttt
     */
    public static class FileList {

        // 当前文件夹
        private final File           mFile;
        // 文件夹内子文件列表
        private final List<FileList> mSubFiles;

        /**
         * 构造函数
         * @param file 当前文件夹
         */
        public FileList(File file) {
            this(file, new ArrayList<>(0));
        }

        /**
         * 构造函数
         * @param file  当前文件夹
         * @param lists 文件夹内子文件列表
         */
        public FileList(
                File file,
                List<FileList> lists
        ) {
            this.mFile     = file;
            this.mSubFiles = lists;
        }

        // =

        /**
         * 获取当前文件夹
         * @return {@link File}
         */
        public File getFile() {
            return mFile;
        }

        /**
         * 获取文件夹内子文件列表
         * @return {@link List}
         */
        public List<FileList> getSubFiles() {
            return mSubFiles;
        }
    }

    // =

    /**
     * 获取目录下所有文件 ( 不递归进子目录 )
     * @param dirPath 目录路径
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirBean(final String dirPath) {
        return listFilesInDirBean(dirPath, false);
    }

    /**
     * 获取目录下所有文件 ( 不递归进子目录 )
     * @param dir 目录
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirBean(final File dir) {
        return listFilesInDirBean(dir, false);
    }

    /**
     * 获取目录下所有文件
     * @param dirPath     目录路径
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirBean(
            final String dirPath,
            final boolean isRecursive
    ) {
        return listFilesInDirBean(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirBean(
            final File dir,
            final boolean isRecursive
    ) {
        return listFilesInDirWithFilterBean(dir, pathname -> true, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件 ( 不递归进子目录 )
     * @param dirPath 目录路径
     * @param filter  过滤器
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirWithFilterBean(
            final String dirPath,
            final FileFilter filter
    ) {
        return listFilesInDirWithFilterBean(getFileByPath(dirPath), filter, false);
    }

    /**
     * 获取目录下所有过滤的文件 ( 不递归进子目录 )
     * @param dir    目录
     * @param filter 过滤器
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirWithFilterBean(
            final File dir,
            final FileFilter filter
    ) {
        return listFilesInDirWithFilterBean(dir, filter, false);
    }

    /**
     * 获取目录下所有过滤的文件
     * @param dirPath     目录路径
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirWithFilterBean(
            final String dirPath,
            final FileFilter filter,
            final boolean isRecursive
    ) {
        return listFilesInDirWithFilterBean(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<FileList> listFilesInDirWithFilterBean(
            final File dir,
            final FileFilter filter,
            final boolean isRecursive
    ) {
        if (!isDirectory(dir) || filter == null) return null;
        List<FileList> list  = new ArrayList<>();
        File[]         files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    FileList fileList;
                    if (isRecursive && file.isDirectory()) {
                        List<FileList> subs = listFilesInDirWithFilterBean(file, filter, true);
                        fileList = new FileList(file, subs);
                    } else {
                        fileList = new FileList(file);
                    }
                    list.add(fileList);
                }
            }
        }
        return list;
    }

    // =

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dirPath 目录路径
     * @return 文件目录列表
     */
    public static List<String> listOrEmpty(final String dirPath) {
        return listOrEmpty(getFile(dirPath));
    }

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dir 目录
     * @return 文件目录列表
     */
    public static List<String> listOrEmpty(final File dir) {
        if (isFileExists(dir)) {
            List<String> list = ArrayUtils.asList(dir.list());
            if (list != null) return list;
        }
        return new ArrayList<>();
    }

    // =

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dirPath 目录路径
     * @return 文件目录列表
     */
    public static List<File> listFilesOrEmpty(final String dirPath) {
        return listFilesOrEmpty(getFile(dirPath));
    }

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dir 目录
     * @return 文件目录列表
     */
    public static List<File> listFilesOrEmpty(final File dir) {
        if (isFileExists(dir)) {
            List<File> list = ArrayUtils.asList(dir.listFiles());
            if (list != null) return list;
        }
        return new ArrayList<>();
    }

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dirPath 目录路径
     * @param filter  文件过滤
     * @return 文件目录列表
     */
    public static List<File> listFilesOrEmpty(
            final String dirPath,
            final FilenameFilter filter
    ) {
        return listFilesOrEmpty(getFile(dirPath), filter);
    }

    /**
     * 获取文件夹下的文件目录列表 ( 非全部子目录 )
     * @param dir    目录
     * @param filter 文件过滤
     * @return 文件目录列表
     */
    public static List<File> listFilesOrEmpty(
            final File dir,
            final FilenameFilter filter
    ) {
        if (isFileExists(dir)) {
            List<File> list = ArrayUtils.asList(dir.listFiles(filter));
            if (list != null) return list;
        }
        return new ArrayList<>();
    }

    // =============
    // = 图片类型判断 =
    // =============

    // 图片格式
    private static final String[] IMAGE_FORMATS = {
            ".PNG", ".JPG", ".JPEG", ".BMP", ".GIF", ".WEBP"
    };

    /**
     * 根据文件名判断文件是否为图片
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(final File file) {
        return file != null && isImageFormats(file.getPath(), IMAGE_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(final String filePath) {
        return isImageFormats(filePath, IMAGE_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath    文件路径
     * @param fileFormats 文件格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImageFormats(
            final String filePath,
            final String[] fileFormats
    ) {
        return isFileFormats(filePath, fileFormats);
    }

    // =============
    // = 音频类型判断 =
    // =============

    // 音频格式
    private static final String[] AUDIO_FORMATS = {
            ".MP3", ".AAC", ".OGG", ".WMA", ".APE", ".FLAC", ".RA"
    };

    /**
     * 根据文件名判断文件是否为音频
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAudioFormats(final File file) {
        return file != null && isAudioFormats(file.getPath(), AUDIO_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为音频
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAudioFormats(final String filePath) {
        return isAudioFormats(filePath, AUDIO_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为音频
     * @param filePath    文件路径
     * @param fileFormats 文件格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAudioFormats(
            final String filePath,
            final String[] fileFormats
    ) {
        return isFileFormats(filePath, fileFormats);
    }

    // =============
    // = 视频类型判断 =
    // =============

    // 视频格式
    private static final String[] VIDEO_FORMATS = {
            ".MP4", ".AVI", ".MOV", ".ASF", ".MPG", ".MPEG", ".WMV", ".RM", ".RMVB", ".3GP", ".MKV"
    };

    /**
     * 根据文件名判断文件是否为视频
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVideoFormats(final File file) {
        return file != null && isVideoFormats(file.getPath(), VIDEO_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为视频
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVideoFormats(final String filePath) {
        return isVideoFormats(filePath, VIDEO_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为视频
     * @param filePath    文件路径
     * @param fileFormats 文件格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isVideoFormats(
            final String filePath,
            final String[] fileFormats
    ) {
        return isFileFormats(filePath, fileFormats);
    }

    // =

    /**
     * 根据文件名判断文件是否为指定格式
     * @param file        文件
     * @param fileFormats 文件格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileFormats(
            final File file,
            final String[] fileFormats
    ) {
        return file != null && isFileFormats(file.getPath(), fileFormats);
    }

    /**
     * 根据文件名判断文件是否为指定格式
     * @param filePath    文件路径
     * @param fileFormats 文件格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFileFormats(
            final String filePath,
            final String[] fileFormats
    ) {
        if (filePath == null || fileFormats == null || fileFormats.length == 0) return false;
        String path = filePath.toUpperCase();
        for (String format : fileFormats) {
            if (format != null) {
                if (path.endsWith(format.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    // ============
    // = MD5Utils =
    // ============

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值
     */
    public static byte[] getFileMD5(final String filePath) {
        return MD5Utils.getFileMD5(filePath);
    }

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final String filePath) {
        return MD5Utils.getFileMD5ToHexString(filePath);
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final File file) {
        return MD5Utils.getFileMD5ToHexString(file);
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值 byte[]
     */
    public static byte[] getFileMD5(final File file) {
        return MD5Utils.getFileMD5(file);
    }
}