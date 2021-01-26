package dev.utils.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dev.utils.JCLogUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 文件分片工具类
 * @author Ttt
 * <pre>
 *     当下载大文件时, 如果网络不稳定或者程序异常退出, 会导致下载失败, 甚至重试多次仍无法完成下载
 *     可以使用断点续传下载:
 *     断点续传下载将需要下载的文件分成若干个分片分别下载, 所有分片都下载完成后, 将所有分片合并成完整的文件
 *     也可以用于断点续传上传 ( 分片续传 )
 *     <p></p>
 *     RandomAccessFile 简介与使用
 *     @see <a href="https://blog.csdn.net/qq_31615049/article/details/88562892"/>
 *     <p></p>
 *     可用 {@link FileUtils#getFileMD5(File)} 进行校验分片合并后与源文件 MD5 值是否一致
 * </pre>
 */
public final class FilePartUtils {

    private FilePartUtils() {
    }

    // 日志 TAG
    private static final String TAG         = FilePartUtils.class.getSimpleName();
    // 分片文件后缀
    public static final  String PART_SUFFIX = "_part_";
    // 分片数量
    public static final  int    PART_COUNT  = 10;
    // 分片片段允许最小值 byte ( 默认 1mb )
    public static final  long   MIN_LENGTH  = 1048576L;

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param item     {@link FilePartItem}
     * @param fileName 原始文件名
     * @return 分片文件名
     */
    public static String getPartName(
            final FilePartItem item,
            final String fileName
    ) {
        return item != null ? item.getPartName(fileName) : null;
    }

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param assist    {@link FilePartAssist}
     * @param partIndex 分片索引
     * @return 分片文件名
     */
    public static String getPartName(
            final FilePartAssist assist,
            final int partIndex
    ) {
        return assist != null ? assist.getPartName(partIndex) : null;
    }

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param fileName  原始文件名
     * @param partIndex 分片索引
     * @return 分片文件名
     */
    public static String getPartName(
            final String fileName,
            final int partIndex
    ) {
        return String.format("%s%s%s", fileName, PART_SUFFIX, partIndex);
    }

    // =

    /**
     * 获取文件分片辅助类
     * @param filePath 文件路径
     * @return {@link FilePartAssist}
     */
    public static FilePartAssist getFilePartAssist(final String filePath) {
        return getFilePartAssist(FileUtils.getFile(filePath), PART_COUNT, MIN_LENGTH);
    }

    /**
     * 获取文件分片辅助类
     * @param filePath  文件路径
     * @param partCount 分片总数
     * @param minLength 分片片段允许最小值 byte
     * @return {@link FilePartAssist}
     */
    public static FilePartAssist getFilePartAssist(
            final String filePath,
            final int partCount,
            final long minLength
    ) {
        return getFilePartAssist(FileUtils.getFile(filePath), partCount, minLength);
    }

    /**
     * 获取文件分片辅助类
     * @param file 文件
     * @return {@link FilePartAssist}
     */
    public static FilePartAssist getFilePartAssist(final File file) {
        return getFilePartAssist(file, PART_COUNT, MIN_LENGTH);
    }

    /**
     * 获取文件分片辅助类
     * @param file      文件
     * @param partCount 分片总数
     * @param minLength 分片片段允许最小值 byte
     * @return {@link FilePartAssist}
     */
    public static FilePartAssist getFilePartAssist(
            final File file,
            final int partCount,
            final long minLength
    ) {
        boolean filePart = isFilePart(file, partCount, minLength);
        return new FilePartAssist(file, filePart ? partCount : 1);
    }

    // =

    /**
     * 是否符合文件分片条件
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFilePart(final String filePath) {
        return isFilePart(FileUtils.getFile(filePath), PART_COUNT, MIN_LENGTH);
    }

    /**
     * 是否符合文件分片条件
     * @param filePath  文件路径
     * @param partCount 分片总数
     * @param minLength 分片片段允许最小值 byte
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFilePart(
            final String filePath,
            final int partCount,
            final long minLength
    ) {
        return isFilePart(FileUtils.getFile(filePath), partCount, minLength);
    }

    /**
     * 是否符合文件分片条件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFilePart(final File file) {
        return isFilePart(file, PART_COUNT, MIN_LENGTH);
    }

    /**
     * 是否符合文件分片条件
     * @param file      文件
     * @param partCount 分片总数
     * @param minLength 分片片段允许最小值 byte
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFilePart(
            final File file,
            final int partCount,
            final long minLength
    ) {
        // 原始文件总字节
        long fileByteLength = FileUtils.getFileLength(file);
        // 分片总字节
        long partByteLength = fileByteLength / partCount;
        return partByteLength >= minLength;
    }

    // ===========
    // = 文件拆分 =
    // ===========

    /**
     * 文件拆分
     * @param filePath 文件路径
     * @param start    分片字节开始索引
     * @param end      分片字节结束索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final String filePath,
            final long start,
            final long end
    ) {
        return fileSplit(FileUtils.getFile(filePath), start, end);
    }

    /**
     * 文件拆分
     * <pre>
     *     慎用, 防止内存溢出
     * </pre>
     * @param file  文件
     * @param start 分片字节开始索引
     * @param end   分片字节结束索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final File file,
            final long start,
            final long end
    ) {
        if (file != null && file.exists() && start >= 0 && end > start) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "r");
                if (end > raf.length()) return null;
                raf.seek(start);
                byte[] bytes = new byte[(int) (end - start)];
                raf.read(bytes);
                return bytes;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fileSplit");
            } finally {
                CloseUtils.closeIOQuietly(raf);
            }
        }
        return null;
    }

    /**
     * 文件拆分
     * @param filePath 文件路径
     * @param item     {@link FilePartItem}
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final String filePath,
            final FilePartItem item
    ) {
        return fileSplit(FileUtils.getFile(filePath), item);
    }

    /**
     * 文件拆分
     * @param file 文件
     * @param item {@link FilePartItem}
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final File file,
            final FilePartItem item
    ) {
        if (file == null || item == null) return null;
        return fileSplit(file, item.start, item.end);
    }

    /**
     * 文件拆分
     * @param filePath  文件路径
     * @param assist    {@link FilePartAssist}
     * @param partIndex 分片索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final String filePath,
            final FilePartAssist assist,
            final int partIndex
    ) {
        return fileSplit(FileUtils.getFile(filePath), assist, partIndex);
    }

    /**
     * 文件拆分
     * @param file      文件
     * @param assist    {@link FilePartAssist}
     * @param partIndex 分片索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(
            final File file,
            final FilePartAssist assist,
            final int partIndex
    ) {
        if (file == null || assist == null) return null;
        return fileSplit(file, assist.getFilePartItem(partIndex));
    }

    // =

    /**
     * 文件拆分并存储
     * @param filePath       文件路径
     * @param start          分片字节开始索引
     * @param end            分片字节结束索引
     * @param destFolderPath 存储目标文件夹地址
     * @param partName       分片文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final String filePath,
            final long start,
            final long end,
            final String destFolderPath,
            final String partName
    ) {
        return fileSplitSave(FileUtils.getFile(filePath), start, end, destFolderPath, partName);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param start          分片字节开始索引
     * @param end            分片字节结束索引
     * @param destFolderPath 存储目标文件夹地址
     * @param partName       分片文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final File file,
            final long start,
            final long end,
            final String destFolderPath,
            final String partName
    ) {
        if (file != null && file.exists() && start >= 0 && end > start) {
            FileInputStream  fis           = null;
            FileChannel      inputChannel  = null;
            FileOutputStream fos           = null;
            FileChannel      outputChannel = null;
            try {
                fis = new FileInputStream(file);
                if (end > file.length()) return false;
                inputChannel = fis.getChannel();
                fos = new FileOutputStream(new File(destFolderPath, partName));
                outputChannel = fos.getChannel();
                inputChannel.transferTo(start, (int) (end - start), outputChannel);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fileSplitSave");
            } finally {
                CloseUtils.closeIOQuietly(outputChannel, fos, inputChannel, fis);
            }
        }
        return false;
    }

    /**
     * 文件拆分并存储
     * @param filePath       文件路径
     * @param item           {@link FilePartItem}
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final String filePath,
            final FilePartItem item,
            final String destFolderPath
    ) {
        return fileSplitSave(FileUtils.getFile(filePath), item, destFolderPath);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param item           {@link FilePartItem}
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final File file,
            final FilePartItem item,
            final String destFolderPath
    ) {
        if (file == null || item == null) return false;
        return fileSplitSave(file, item.start, item.end, destFolderPath,
                item.getPartName(FileUtils.getFileName(file)));
    }

    /**
     * 文件拆分并存储
     * @param filePath       文件路径
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 存储目标文件夹地址
     * @param partIndex      分片索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final String filePath,
            final FilePartAssist assist,
            final String destFolderPath,
            final int partIndex
    ) {
        return fileSplitSave(FileUtils.getFile(filePath), assist, destFolderPath, partIndex);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 存储目标文件夹地址
     * @param partIndex      分片索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSave(
            final File file,
            final FilePartAssist assist,
            final String destFolderPath,
            final int partIndex
    ) {
        if (file == null || assist == null) return false;
        return fileSplitSave(file, assist.getFilePartItem(partIndex), destFolderPath);
    }

    // =

    /**
     * 文件拆分并存储
     * @param filePath       文件路径
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSaves(
            final String filePath,
            final String destFolderPath
    ) {
        return fileSplitSaves(FileUtils.getFile(filePath), destFolderPath);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSaves(
            final File file,
            final String destFolderPath
    ) {
        return fileSplitSaves(file, getFilePartAssist(file), destFolderPath);
    }

    /**
     * 文件拆分并存储
     * @param filePath       文件路径
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSaves(
            final String filePath,
            final FilePartAssist assist,
            final String destFolderPath
    ) {
        return fileSplitSaves(FileUtils.getFile(filePath), assist, destFolderPath);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 存储目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitSaves(
            final File file,
            final FilePartAssist assist,
            final String destFolderPath
    ) {
        if (file == null || assist == null) return false;
        if (!assist.existsPart()) return false;
        String fileName = FileUtils.getFileName(file);
        if (fileName == null) return false;
        for (int i = 0, len = assist.getPartCount(); i < len; i++) {
            FilePartItem item = assist.getFilePartItem(i);
            if (item != null) {
                boolean result = fileSplitSave(file, item.start, item.end, destFolderPath,
                        item.getPartName(fileName));
                if (!result) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    // ===========
    // = 分片删除 =
    // ===========

    /**
     * 删除拆分文件
     * @param filePath       文件路径
     * @param item           {@link FilePartItem}
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDelete(
            final String filePath,
            final FilePartItem item,
            final String destFolderPath
    ) {
        return fileSplitDelete(FileUtils.getFile(filePath), item, destFolderPath);
    }

    /**
     * 删除拆分文件
     * @param file           文件
     * @param item           {@link FilePartItem}
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDelete(
            final File file,
            final FilePartItem item,
            final String destFolderPath
    ) {
        if (file == null || item == null) return false;
        return FileUtils.deleteFile(
                FileUtils.getFile(destFolderPath, item.getPartName(FileUtils.getFileName(file)))
        );
    }

    /**
     * 删除拆分文件
     * @param filePath       文件路径
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 待删除目标文件夹地址
     * @param partIndex      分片索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDelete(
            final String filePath,
            final FilePartAssist assist,
            final String destFolderPath,
            final int partIndex
    ) {
        return fileSplitDelete(FileUtils.getFile(filePath), assist, destFolderPath, partIndex);
    }

    /**
     * 删除拆分文件
     * @param file           文件
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 待删除目标文件夹地址
     * @param partIndex      分片索引
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDelete(
            final File file,
            final FilePartAssist assist,
            final String destFolderPath,
            final int partIndex
    ) {
        if (file == null || assist == null) return false;
        return fileSplitDelete(file, assist.getFilePartItem(partIndex), destFolderPath);
    }

    // =

    /**
     * 删除拆分文件
     * @param filePath       文件路径
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDeletes(
            final String filePath,
            final String destFolderPath
    ) {
        return fileSplitDeletes(FileUtils.getFile(filePath), destFolderPath);
    }

    /**
     * 删除拆分文件
     * @param file           文件
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDeletes(
            final File file,
            final String destFolderPath
    ) {
        return fileSplitDeletes(file, getFilePartAssist(file), destFolderPath);
    }

    /**
     * 删除拆分文件
     * @param filePath       文件路径
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDeletes(
            final String filePath,
            final FilePartAssist assist,
            final String destFolderPath
    ) {
        return fileSplitDeletes(FileUtils.getFile(filePath), assist, destFolderPath);
    }

    /**
     * 删除拆分文件
     * @param file           文件
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 待删除目标文件夹地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitDeletes(
            final File file,
            final FilePartAssist assist,
            final String destFolderPath
    ) {
        if (file == null || assist == null) return false;
        if (!assist.existsPart()) return false;
        for (int i = 0, len = assist.getPartCount(); i < len; i++) {
            fileSplitDelete(file, assist.getFilePartItem(i), destFolderPath);
        }
        return true;
    }

    // ===========
    // = 分片合并 =
    // ===========

    /**
     * 分片合并
     * @param filePath 文件路径
     * @param paths    待合并文件 ( 按顺序 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMergePaths(
            final String filePath,
            final List<String> paths
    ) {
        return fileSplitMergePaths(FileUtils.getFile(filePath), paths);
    }

    /**
     * 分片合并
     * @param file  文件
     * @param paths 待合并文件 ( 按顺序 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMergePaths(
            final File file,
            final List<String> paths
    ) {
        if (file == null || paths == null) return false;
        return fileSplitMergeFiles(file, FileUtils.convertFiles(paths));
    }

    /**
     * 分片合并
     * @param filePath 文件路径
     * @param files    待合并文件 ( 按顺序 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMergeFiles(
            final String filePath,
            final List<File> files
    ) {
        return fileSplitMergeFiles(FileUtils.getFile(filePath), files);
    }

    /**
     * 分片合并
     * @param file  文件
     * @param files 待合并文件 ( 按顺序 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMergeFiles(
            final File file,
            final List<File> files
    ) {
        if (file == null || files == null) return false;
        if (files.isEmpty()) return false;
        FileUtils.deleteFile(file);
        RandomAccessFile raf    = null;
        RandomAccessFile reader = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            for (int i = 0, len = files.size(); i < len; i++) {
                reader = new RandomAccessFile(files.get(i), "r");
                byte[] buffer = new byte[1024];
                int    readLen; // 读取的字节数
                while ((readLen = reader.read(buffer)) != -1) {
                    raf.write(buffer, 0, readLen);
                }
                CloseUtils.closeIOQuietly(reader);
            }
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "fileSplitMergeFiles");
            FileUtils.deleteFile(file);
        } finally {
            CloseUtils.closeIOQuietly(reader, raf);
        }
        return false;
    }

    /**
     * 分片合并
     * @param filePath       文件路径
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 分片所在文件夹地址
     * @param fileName       原文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMerge(
            final String filePath,
            final FilePartAssist assist,
            final String destFolderPath,
            final String fileName
    ) {
        return fileSplitMerge(FileUtils.getFile(filePath), assist, destFolderPath, fileName);
    }

    /**
     * 分片合并
     * @param file           文件
     * @param assist         {@link FilePartAssist}
     * @param destFolderPath 分片所在文件夹地址
     * @param fileName       原文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean fileSplitMerge(
            final File file,
            final FilePartAssist assist,
            final String destFolderPath,
            final String fileName
    ) {
        if (file == null || assist == null || destFolderPath == null || fileName == null) {
            return false;
        }
        if (!assist.existsPart()) return false;
        List<File> files = new ArrayList<>();
        try {
            for (int i = 0, len = assist.getPartCount(); i < len; i++) {
                FilePartItem item     = assist.getFilePartItems().get(i);
                File         partFile = new File(destFolderPath, item.getPartName(fileName));
                files.add(partFile);
            }
            return fileSplitMergeFiles(file, files);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "fileSplitMerge");
        }
        return false;
    }
}