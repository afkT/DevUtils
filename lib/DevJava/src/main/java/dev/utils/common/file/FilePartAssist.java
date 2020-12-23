package dev.utils.common.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.utils.common.CollectionUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 文件分片辅助类
 * @author Ttt
 */
public final class FilePartAssist {

    // 文件路径
    private final File               file;
    // 文件分片信息集合
    private final List<FilePartItem> filePartItems = new ArrayList<>();

    /**
     * 构造函数
     * @param file          文件
     * @param filePartItems 文件分片信息集合
     */
    public FilePartAssist(
            final File file,
            final List<FilePartItem> filePartItems
    ) {
        this.file = file;
        if (filePartItems != null) {
            this.filePartItems.addAll(filePartItems);
        }
    }

    /**
     * 构造函数
     * @param filePath  文件路径
     * @param partCount 分片总数
     */
    public FilePartAssist(
            final String filePath,
            final int partCount
    ) {
        this(FileUtils.getFile(filePath), partCount);
    }

    /**
     * 构造函数
     * @param file      文件
     * @param partCount 分片总数
     */
    public FilePartAssist(
            final File file,
            final int partCount
    ) {
        this.file = file;
        if (file != null && file.exists() && partCount > 0) {
            // 原始文件总字节
            long fileByteLength = file.length();
            // 分片总字节
            long partByteLength = fileByteLength / partCount;
            // 余数 ( 全部加到最后一个分片 )
            long remainder = fileByteLength - partByteLength * partCount;
            // 当前分片字节累加总数
            long total = 0;
            if (partCount > 1) { // 如果分片大于 1 个, 则处理前面的数量
                for (int i = 0, len = partCount - 1; i < len; i++) {
                    FilePartItem item = new FilePartItem(
                            i, partCount, partByteLength, fileByteLength,
                            total, total + partByteLength
                    );
                    total += partByteLength;
                    filePartItems.add(item);
                }
            }
            // 最后一个分片片段
            FilePartItem item = new FilePartItem(
                    partCount - 1, partCount, partByteLength, fileByteLength,
                    total, total + partByteLength + remainder
            );
            filePartItems.add(item);
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取文件
     * @return {@link File}
     */
    public File getFile() {
        return file;
    }

    /**
     * 获取文件名
     * @return 文件名
     */
    public String getFileName() {
        return FileUtils.getFileName(file);
    }

    /**
     * 获取文件分片信息集合
     * @return {@link List<FilePartItem>}
     */
    public List<FilePartItem> getFilePartItems() {
        return filePartItems;
    }

    /**
     * 获取指定索引文件分片信息
     * @param partIndex 分片索引
     * @return {@link FilePartItem}
     */
    public FilePartItem getFilePartItem(final int partIndex) {
        return CollectionUtils.get(filePartItems, partIndex);
    }

    /**
     * 获取分片总数
     * @return 分片总数
     */
    public int getPartCount() {
        return filePartItems.size();
    }

    /**
     * 是否存在分片
     * @return {@code true} yes, {@code false} no
     */
    public boolean existsPart() {
        return getPartCount() != 0;
    }

    /**
     * 是否只有一个分片
     * @return {@code true} yes, {@code false} no
     */
    public boolean isOnlyOne() {
        return getPartCount() == 1;
    }

    /**
     * 获取分片文件名 ( 后缀索引拼接 )
     * @param partIndex 分片索引
     * @return 分片文件名
     */
    public String getPartName(final int partIndex) {
        return FilePartUtils.getPartName(getFileName(), partIndex);
    }
}