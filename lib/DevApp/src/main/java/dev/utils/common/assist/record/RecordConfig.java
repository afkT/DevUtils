package dev.utils.common.assist.record;

import java.io.File;
import java.util.Date;

import dev.utils.common.DateUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 日志记录配置信息
 * @author Ttt
 */
public final class RecordConfig {

    // 存储路径
    private final String       mStoragePath;
    // 文件名 ( 固定 )
    private final String       mFileName = "log_record.txt";
    // 文件夹名 ( 模块名 )
    private final String       mFolderName;
    // 文件记录间隔时间 如: HH
    private final TIME         mFileIntervalTime;
    // 是否处理记录
    private       boolean      mHandler;
    // 日志记录插入信息
    private       RecordInsert mRecordInsert;

    // =======
    // = 枚举 =
    // =======

    /**
     * detail: 文件记录间隔时间枚举
     * @author Ttt
     */
    public enum TIME {

        // DEFAULT ( 默认天, 在根目录下 )
        DEFAULT,

        // 小时
        HH,

        // 分钟
        MM,

        // 秒
        SS
    }

    // ==========
    // = 构造函数 =
    // ==========

    /**
     * 构造函数
     * @param storagePath      存储路径
     * @param folderName       文件夹名 ( 模块名 )
     * @param fileIntervalTime 文件记录间隔时间
     * @param handler          是否处理记录
     */
    private RecordConfig(
            final String storagePath,
            final String folderName,
            final TIME fileIntervalTime,
            final boolean handler
    ) {
        this.mStoragePath      = storagePath;
        this.mFolderName       = folderName;
        this.mFileIntervalTime = fileIntervalTime;
        this.mHandler          = handler;
    }

    // =

    /**
     * 获取配置信息
     * @param storagePath      存储路径
     * @param folderName       文件夹名 ( 模块名 )
     * @param fileIntervalTime 文件记录间隔时间
     * @return {@link RecordConfig}
     */
    public static RecordConfig get(
            final String storagePath,
            final String folderName,
            final TIME fileIntervalTime
    ) {
        return get(storagePath, folderName, fileIntervalTime, true);
    }

    /**
     * 获取配置信息
     * @param storagePath      存储路径
     * @param folderName       文件夹名 ( 模块名 )
     * @param fileIntervalTime 文件记录间隔时间
     * @param handler          是否处理记录
     * @return {@link RecordConfig}
     */
    public static RecordConfig get(
            final String storagePath,
            final String folderName,
            final TIME fileIntervalTime,
            final boolean handler
    ) {
        if (StringUtils.isEmpty(storagePath, folderName)) return null;
        return new RecordConfig(
                storagePath, folderName,
                (fileIntervalTime != null ? fileIntervalTime : TIME.DEFAULT), handler
        );
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取存储路径
     * @return 存储路径
     */
    public String getStoragePath() {
        return mStoragePath;
    }

    /**
     * 获取文件名 ( 固定 )
     * @return 文件名 ( 固定 )
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * 获取文件夹名 ( 模块名 )
     * @return 文件夹名 ( 模块名 )
     */
    public String getFolderName() {
        return mFolderName;
    }

    /**
     * 获取文件记录间隔时间
     * @return 文件记录间隔时间
     */
    public TIME getFileIntervalTime() {
        return mFileIntervalTime;
    }

    /**
     * 是否处理记录
     * @return {@code true} yes, {@code false} no
     */
    public boolean isHandler() {
        return mHandler;
    }

    /**
     * 设置是否处理记录
     * @param handler 是否处理记录
     * @return {@link RecordConfig}
     */
    public RecordConfig setHandler(final boolean handler) {
        this.mHandler = handler;
        return this;
    }

    /**
     * 获取日志记录插入信息
     * @return 日志记录插入信息
     */
    public RecordInsert getRecordInsert() {
        return mRecordInsert;
    }

    /**
     * 获取日志记录插入信息
     * @param defaultValue 默认值
     * @return 日志记录插入信息
     */
    public RecordInsert getRecordInsert(RecordInsert defaultValue) {
        if (mRecordInsert != null) return mRecordInsert;
        return defaultValue;
    }

    /**
     * 设置日志记录插入信息
     * @param recordInsert 日志记录插入信息
     * @return {@link RecordConfig}
     */
    public RecordConfig setRecordInsert(RecordInsert recordInsert) {
        this.mRecordInsert = recordInsert;
        return this;
    }

    // =

    /**
     * 获取文件地址
     * @return 文件地址
     */
    public String getFinalPath() {
        File file = FileUtils.getFile(mStoragePath, getIntervalTimeFolder());
        // 创建文件夹
        FileUtils.createFolder(file);
        return FileUtils.getAbsolutePath(file);
    }

    // =============
    // = 内部处理方法 =
    // =============

    /**
     * 获取时间间隔所属的文件夹
     * @return 时间间隔所属的文件夹
     */
    private String getIntervalTimeFolder() {
        // 文件夹
        String folder = String.format(
                "FileRecord/%s/%s/",
                DateUtils.getDateNow("yyyy_MM_dd"),
                mFolderName
        );
        // 进行判断
        switch (mFileIntervalTime) {
            case DEFAULT:
                return folder;
            case HH:
            case MM:
            case SS:
                Date date = new Date();
                // 小时格式 ( 24 )
                String hh_Format = DateUtils.timeAddZero(DateUtils.get24Hour(date));
                // 判断属于小时格式
                if (mFileIntervalTime == TIME.HH) {
                    // folder/HH_number/
                    return folder + "HH_" + hh_Format + File.separator;
                } else {
                    // 分钟格式
                    String mm_Format = DateUtils.timeAddZero(DateUtils.getMinute(date));
                    // 判断是否属于分钟
                    if (mFileIntervalTime == TIME.MM) {
                        // folder/HH_number/MM_number/
                        return folder + "HH_" + hh_Format + "/MM_" + mm_Format + File.separator;
                    } else { // 属于秒
                        // 秒格式
                        String ss_Format = DateUtils.timeAddZero(DateUtils.getSecond(date));
                        // folder/HH_number/MM_number/SS_number/
                        return folder + "HH_" + hh_Format + "/MM_" + mm_Format + "/SS_" + ss_Format + File.separator;
                    }
                }
        }
        return null;
    }
}