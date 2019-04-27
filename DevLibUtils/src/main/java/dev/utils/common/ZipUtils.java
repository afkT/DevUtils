package dev.utils.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import dev.utils.JCLogUtils;

/**
 * detail: 压缩相关工具类
 * @author Ttt
 */
public final class ZipUtils {

    private ZipUtils() {
    }

    // 日志 TAG
    private static final String TAG = ZipUtils.class.getSimpleName();
    // 缓存大小
    public static final int BUFFER_LEN = 8192;

    /**
     * 批量压缩文件
     * @param resFiles    待压缩文件路径集合
     * @param zipFilePath 压缩文件路径
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFiles(final Collection<String> resFiles, final String zipFilePath) throws Exception {
        return zipFiles(resFiles, zipFilePath, null);
    }

    /**
     * 批量压缩文件
     * @param resFilePaths 待压缩文件路径集合
     * @param zipFilePath  压缩文件路径
     * @param comment      压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFiles(final Collection<String> resFilePaths, final String zipFilePath, final String comment) throws Exception {
        if (resFilePaths == null || zipFilePath == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (String resFile : resFilePaths) {
                if (!zipFile(getFileByPath(resFile), "", zos, comment)) return false;
            }
            return true;
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * 批量压缩文件
     * @param resFiles 待压缩文件集合
     * @param zipFile  压缩文件
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFiles(final Collection<File> resFiles, final File zipFile) throws Exception {
        return zipFiles(resFiles, zipFile, null);
    }

    /**
     * 批量压缩文件
     * @param resFiles 待压缩文件集合
     * @param zipFile  压缩文件
     * @param comment  压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFiles(final Collection<File> resFiles, final File zipFile, final String comment) throws Exception {
        if (resFiles == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File resFile : resFiles) {
                if (!zipFile(resFile, "", zos, comment)) return false;
            }
            return true;
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * 压缩文件
     * @param resFilePath 待压缩文件路径
     * @param zipFilePath 压缩文件路径
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(final String resFilePath, final String zipFilePath) throws Exception {
        return zipFile(getFileByPath(resFilePath), getFileByPath(zipFilePath), null);
    }

    /**
     * 压缩文件
     * @param resFilePath 待压缩文件路径
     * @param zipFilePath 压缩文件路径
     * @param comment     压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(final String resFilePath, final String zipFilePath, final String comment) throws Exception {
        return zipFile(getFileByPath(resFilePath), getFileByPath(zipFilePath), comment);
    }

    /**
     * 压缩文件
     * @param resFile 待压缩文件
     * @param zipFile 压缩文件
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(final File resFile, final File zipFile) throws Exception {
        return zipFile(resFile, zipFile, null);
    }

    /**
     * 压缩文件
     * @param resFile 待压缩文件
     * @param zipFile 压缩文件
     * @param comment 压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(final File resFile, final File zipFile, final String comment) throws Exception {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, "", zos, comment);
        } finally {
            if (zos != null) {
                CloseUtils.closeIO(zos);
            }
        }
    }

    /**
     * 压缩文件
     * @param resFile  待压缩文件
     * @param rootPath 相对于压缩文件的路径
     * @param zos      压缩文件输出流
     * @param comment  压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    private static boolean zipFile(final File resFile, String rootPath, final ZipOutputStream zos, final String comment) throws Exception {
        rootPath = rootPath + (isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // 如果是空文件夹那么创建它，我把'/'换为File.separator测试就不成功，eggPain
            if (fileList == null || fileList.length == 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // 如果递归返回 false 则返回 false
                    if (!zipFile(file, rootPath, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(rootPath);
                entry.setComment(comment);
                zos.putNextEntry(entry);
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtils.closeIO(is);
            }
        }
        return true;
    }

    /**
     * 解压文件
     * @param zipFilePath 待解压文件路径
     * @param destDirPath 目标目录路径
     * @return 文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFile(final String zipFilePath, final String destDirPath) throws Exception {
        return unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    /**
     * 解压文件
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     * @return 文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFile(final File zipFile, final File destDir) throws Exception {
        return unzipFileByKeyword(zipFile, destDir, null);
    }

    /**
     * 解压带有关键字的文件
     * @param zipFilePath 待解压文件路径
     * @param destDirPath 目标目录路径
     * @param keyword     关键字
     * @return 带有关键字的文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFileByKeyword(final String zipFilePath, final String destDirPath, final String keyword) throws Exception {
        return unzipFileByKeyword(getFileByPath(zipFilePath), getFileByPath(destDirPath), keyword);
    }

    /**
     * 解压带有关键字的文件
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     * @param keyword 关键字
     * @return 带有关键字的文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFileByKeyword(final File zipFile, final File destDir, final String keyword) throws Exception {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<?> entries = zip.entries();
        if (isSpace(keyword)) {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains("../")) {
                    JCLogUtils.dTag(TAG, ("entryName: " + entryName + " is dangerous!"));
                    continue;
                }
                if (!unzipChildFile(destDir, files, zip, entry, entryName)) return files;
            }
        } else {
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains("../")) {
                    JCLogUtils.dTag(TAG, ("entryName: " + entryName + " is dangerous!"));
                    continue;
                }
                if (entryName.contains(keyword)) {
                    if (!unzipChildFile(destDir, files, zip, entry, entryName)) return files;
                }
            }
        }
        return files;
    }

    /**
     * 解压文件
     * @param destDir   目标目录
     * @param files     解压文件链表
     * @param zf        压缩文件条目
     * @param entry     压缩文件信息
     * @param entryName 文件名
     * @return {@code true} success, {@code false} fail
     * @throws Exception 异常时抛出
     */
    private static boolean unzipChildFile(final File destDir, final List<File> files, final ZipFile zf,
                                          final ZipEntry entry, final String entryName) throws Exception {
        File file = new File(destDir, entryName);
        files.add(file);
        if (entry.isDirectory()) {
            if (!createOrExistsDir(file)) return false;
        } else {
            if (!createOrExistsFile(file)) return false;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(zf.getInputStream(entry));
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                CloseUtils.closeIO(in, out);
            }
        }
        return true;
    }

    /**
     * 获取压缩文件中的文件路径链表
     * @param zipFilePath 压缩文件路径
     * @return 压缩文件中的文件路径链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getFilesPath(final String zipFilePath) throws Exception {
        return getFilesPath(getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的文件路径链表
     * @param zipFile 压缩文件
     * @return 压缩文件中的文件路径链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getFilesPath(final File zipFile) throws Exception {
        if (zipFile == null) return null;
        List<String> paths = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            String entryName = ((ZipEntry) entries.nextElement()).getName();
            if (entryName.contains("../")) {
                JCLogUtils.dTag(TAG, ("entryName: " + entryName + " is dangerous!"));
                paths.add(entryName);
            } else {
                paths.add(entryName);
            }
        }
        return paths;
    }

    /**
     * 获取压缩文件中的注释链表
     * @param zipFilePath 压缩文件路径
     * @return 压缩文件中的注释链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getComments(final String zipFilePath) throws Exception {
        return getComments(getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的注释链表
     * @param zipFile 压缩文件
     * @return 压缩文件中的注释链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getComments(final File zipFile) throws Exception {
        if (zipFile == null) return null;
        List<String> comments = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        return comments;
    }

    // =

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     * @param file 文件
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    private static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     * @param file 文件
     * @return {@code true} 存在或创建成功, {@code false} 不存在或创建失败
     */
    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回 true，是目录则返回 false
        if (file.exists()) return file.isFile();
        // 判断文件是否存在, 不存在则直接返回
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            // 存在, 则返回新的路径
            return file.createNewFile();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return {@link File}
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
