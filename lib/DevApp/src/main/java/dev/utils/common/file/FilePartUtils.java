package dev.utils.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import dev.utils.JCLogUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 文件分片工具类
 * @author Ttt
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
    // 分片片段允许最小值 byte ( 1mb )
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
    public static String getPartName(final FilePartItem item, final String fileName) {
        return item != null ? item.getPartName(fileName) : null;
    }

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param assist    {@link FilePartAssist}
     * @param partIndex 分片索引
     * @return 分片文件名
     */
    public static String getPartName(final FilePartAssist assist, final int partIndex) {
        return assist != null ? assist.getPartName(partIndex) : null;
    }

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param fileName  原始文件名
     * @param partIndex 分片索引
     * @return 分片文件名
     */
    public static String getPartName(final String fileName, final int partIndex) {
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
    public static FilePartAssist getFilePartAssist(final String filePath,
                                                   final int partCount, final long minLength) {
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
    public static FilePartAssist getFilePartAssist(final File file,
                                                   final int partCount, final long minLength) {
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
    public static boolean isFilePart(final String filePath,
                                     final int partCount, final long minLength) {
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
    public static boolean isFilePart(final File file,
                                     final int partCount, final long minLength) {
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
    public static byte[] fileSplit(final String filePath, final long start, final long end) {
        return fileSplit(FileUtils.getFile(filePath), start, end);
    }

    /**
     * 文件拆分
     * @param file  文件
     * @param start 分片字节开始索引
     * @param end   分片字节结束索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(final File file, final long start, final long end) {
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
    public static byte[] fileSplit(final String filePath, final FilePartItem item) {
        return fileSplit(FileUtils.getFile(filePath), item);
    }

    /**
     * 文件拆分
     * @param file 文件
     * @param item {@link FilePartItem}
     * @return 指定位置数据
     */
    public static byte[] fileSplit(final File file, final FilePartItem item) {
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
    public static byte[] fileSplit(final String filePath,
                                   final FilePartAssist assist, final int partIndex) {
        return fileSplit(FileUtils.getFile(filePath), assist, partIndex);
    }

    /**
     * 文件拆分
     * @param file      文件
     * @param assist    {@link FilePartAssist}
     * @param partIndex 分片索引
     * @return 指定位置数据
     */
    public static byte[] fileSplit(final File file,
                                   final FilePartAssist assist, final int partIndex) {
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
     * @return 指定位置数据
     */
    public static boolean fileSplitSave(final String filePath, final long start, final long end,
                                        final String destFolderPath, final String partName) {
        return fileSplitSave(FileUtils.getFile(filePath), start, end, destFolderPath, partName);
    }

    /**
     * 文件拆分并存储
     * @param file           文件
     * @param start          分片字节开始索引
     * @param end            分片字节结束索引
     * @param destFolderPath 存储目标文件夹地址
     * @param partName       分片文件名
     * @return 指定位置数据
     */
    public static boolean fileSplitSave(final File file, final long start, final long end,
                                        final String destFolderPath, final String partName) {
        if (file != null && file.exists() && start >= 0 && end > start) {
            FileInputStream fis = null;
            FileChannel inputChannel = null;
            FileOutputStream fos = null;
            FileChannel outputChannel = null;
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
}