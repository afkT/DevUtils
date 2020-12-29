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
    private static final int BUFFER_LEN = 8192;

    /**
     * 批量压缩文件
     * @param resFiles    待压缩文件路径集合
     * @param zipFilePath 压缩文件路径
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFiles(
            final Collection<String> resFiles,
            final String zipFilePath
    )
            throws Exception {
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
    public static boolean zipFiles(
            final Collection<String> resFilePaths,
            final String zipFilePath,
            final String comment
    )
            throws Exception {
        if (resFilePaths == null || zipFilePath == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (String resFile : resFilePaths) {
                if (!zipFile(FileUtils.getFileByPath(resFile), "", zos, comment)) return false;
            }
            return true;
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtils.closeIOQuietly(zos);
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
    public static boolean zipFiles(
            final Collection<File> resFiles,
            final File zipFile
    )
            throws Exception {
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
    public static boolean zipFiles(
            final Collection<File> resFiles,
            final File zipFile,
            final String comment
    )
            throws Exception {
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
                CloseUtils.closeIOQuietly(zos);
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
    public static boolean zipFile(
            final String resFilePath,
            final String zipFilePath
    )
            throws Exception {
        return zipFile(FileUtils.getFileByPath(resFilePath), FileUtils.getFileByPath(zipFilePath), null);
    }

    /**
     * 压缩文件
     * @param resFilePath 待压缩文件路径
     * @param zipFilePath 压缩文件路径
     * @param comment     压缩文件的注释
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(
            final String resFilePath,
            final String zipFilePath,
            final String comment
    )
            throws Exception {
        return zipFile(FileUtils.getFileByPath(resFilePath), FileUtils.getFileByPath(zipFilePath), comment);
    }

    /**
     * 压缩文件
     * @param resFile 待压缩文件
     * @param zipFile 压缩文件
     * @return {@code true} 压缩成功, {@code false} 压缩失败
     * @throws Exception 异常时抛出
     */
    public static boolean zipFile(
            final File resFile,
            final File zipFile
    )
            throws Exception {
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
    public static boolean zipFile(
            final File resFile,
            final File zipFile,
            final String comment
    )
            throws Exception {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, "", zos, comment);
        } finally {
            CloseUtils.closeIOQuietly(zos);
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
    private static boolean zipFile(
            final File resFile,
            final String rootPath,
            final ZipOutputStream zos,
            final String comment
    )
            throws Exception {
        // 处理后的文件路径
        String filePath = rootPath + (StringUtils.isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // 如果是空文件夹那么创建它
            if (fileList == null || fileList.length == 0) {
                ZipEntry entry = new ZipEntry(filePath + '/');
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // 如果递归返回 false 则返回 false
                    if (!zipFile(file, filePath, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(filePath);
                entry.setComment(comment);
                zos.putNextEntry(entry);
                byte[] buffer = new byte[BUFFER_LEN];
                int    len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtils.closeIOQuietly(is);
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
    public static List<File> unzipFile(
            final String zipFilePath,
            final String destDirPath
    )
            throws Exception {
        return unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    /**
     * 解压文件
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     * @return 文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFile(
            final File zipFile,
            final File destDir
    )
            throws Exception {
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
    public static List<File> unzipFileByKeyword(
            final String zipFilePath,
            final String destDirPath,
            final String keyword
    )
            throws Exception {
        return unzipFileByKeyword(FileUtils.getFileByPath(zipFilePath), FileUtils.getFileByPath(destDirPath), keyword);
    }

    /**
     * 解压带有关键字的文件
     * @param zipFile 待解压文件
     * @param destDir 目标目录
     * @param keyword 关键字
     * @return 带有关键字的文件链表
     * @throws Exception 异常时抛出
     */
    public static List<File> unzipFileByKeyword(
            final File zipFile,
            final File destDir,
            final String keyword
    )
            throws Exception {
        if (zipFile == null || destDir == null) return null;
        List<File>     files   = new ArrayList<>();
        ZipFile        zip     = new ZipFile(zipFile);
        Enumeration<?> entries = zip.entries();
        if (StringUtils.isSpace(keyword)) {
            while (entries.hasMoreElements()) {
                ZipEntry entry     = ((ZipEntry) entries.nextElement());
                String   entryName = entry.getName();
                if (entryName.contains("../")) {
                    JCLogUtils.dTag(TAG, "entryName: %s is dangerous!", entryName);
                    continue;
                }
                if (!unzipChildFile(destDir, files, zip, entry, entryName)) return files;
            }
        } else {
            while (entries.hasMoreElements()) {
                ZipEntry entry     = ((ZipEntry) entries.nextElement());
                String   entryName = entry.getName();
                if (entryName.contains("../")) {
                    JCLogUtils.dTag(TAG, "entryName: %s is dangerous!", entryName);
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
    private static boolean unzipChildFile(
            final File destDir,
            final List<File> files,
            final ZipFile zf,
            final ZipEntry entry,
            final String entryName
    )
            throws Exception {
        File file = new File(destDir, entryName);
        files.add(file);
        if (entry.isDirectory()) {
            return FileUtils.createOrExistsDir(file);
        } else {
            if (!FileUtils.createOrExistsFile(file)) return false;
            InputStream  is = null;
            OutputStream os = null;
            try {
                is = new BufferedInputStream(zf.getInputStream(entry));
                os = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[BUFFER_LEN];
                int    len;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            } finally {
                CloseUtils.closeIOQuietly(is, os);
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
    public static List<String> getFilesPath(final String zipFilePath)
            throws Exception {
        return getFilesPath(FileUtils.getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的文件路径链表
     * @param zipFile 压缩文件
     * @return 压缩文件中的文件路径链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getFilesPath(final File zipFile)
            throws Exception {
        if (zipFile == null) return null;
        List<String>   paths   = new ArrayList<>();
        Enumeration<?> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            String entryName = ((ZipEntry) entries.nextElement()).getName();
            if (entryName.contains("../")) {
                JCLogUtils.dTag(TAG, "entryName: %s is dangerous!", entryName);
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
    public static List<String> getComments(final String zipFilePath)
            throws Exception {
        return getComments(FileUtils.getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的注释链表
     * @param zipFile 压缩文件
     * @return 压缩文件中的注释链表
     * @throws Exception 异常时抛出
     */
    public static List<String> getComments(final File zipFile)
            throws Exception {
        if (zipFile == null) return null;
        List<String>   comments = new ArrayList<>();
        Enumeration<?> entries  = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        return comments;
    }
}