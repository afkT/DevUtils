package dev.utils.app.assist.record;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.app.AppUtils;
import dev.utils.app.DeviceUtils;
import dev.utils.app.ManifestUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.assist.record.RecordInsert;

/**
 * detail: App 日志记录插入信息
 * @author Ttt
 */
public class AppRecordInsert
        extends RecordInsert {

    // 是否每次都创建新的 FileInfo
    private final boolean mEveryCreate;

    public AppRecordInsert(boolean everyCreate) {
        super(null);
        this.mEveryCreate = everyCreate;
    }

    // =

    @Override
    public final RecordInsert setFileInfo(String fileInfo) {
        return this;
    }

    @Override
    public String getFileInfo() {
        if (mEveryCreate) return createFileInfo();
        if (StringUtils.isNotEmpty(mFileInfo)) return mFileInfo;
        mFileInfo = createFileInfo();
        return mFileInfo;
    }

    // ==========
    // = 配置信息 =
    // ==========

    // APP 版本名 ( 主要用于对用户显示版本信息 )
    private       String              APP_VERSION_NAME = "";
    // APP 版本号
    private       String              APP_VERSION_CODE = "";
    // 应用包名
    private       String              PACKAGE_NAME     = "";
    // 设备信息
    private       String              DEVICE_INFO_STR  = null;
    // 设备信息存储 Map
    private final Map<String, String> DEVICE_INFO_MAPS = new HashMap<>();

    private String createFileInfo() {
        // 如果版本信息为 null, 才进行处理
        if (TextUtils.isEmpty(APP_VERSION_NAME) || TextUtils.isEmpty(APP_VERSION_CODE)) {
            // 获取 APP 版本信息
            String[] versions = ManifestUtils.getAppVersion();
            // 防止为 null
            if (versions != null && versions.length == 2) {
                // 保存 APP 版本信息
                APP_VERSION_NAME = versions[0];
                APP_VERSION_CODE = versions[1];
            }
        }

        // 获取包名
        if (TextUtils.isEmpty(PACKAGE_NAME)) {
            PACKAGE_NAME = AppUtils.getPackageName();
        }

        int deviceInfoSize = DEVICE_INFO_MAPS.size();
        // 判断是否存在设备信息
        if (TextUtils.isEmpty(DEVICE_INFO_STR) || deviceInfoSize == 0) {
            if (deviceInfoSize == 0) {
                // 获取设备信息
                DeviceUtils.getDeviceInfo(DEVICE_INFO_MAPS);
            }
            if (TextUtils.isEmpty(DEVICE_INFO_STR)) {
                // 拼接设备信息
                DEVICE_INFO_STR = DeviceUtils.handlerDeviceInfo(
                        DEVICE_INFO_MAPS, "failed to get device information"
                );
            }
        }

        // ==========
        // = 拼接数据 =
        // ==========

        return DevFinal.SYMBOL.NEW_LINE_X2 +
                "[设备信息]" +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                "===========================" +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                DEVICE_INFO_STR +
                DevFinal.SYMBOL.NEW_LINE +
                "===========================" +
                DevFinal.SYMBOL.NEW_LINE_X4 +
                "[版本信息]" +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                "===========================" +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                "versionName: " + APP_VERSION_NAME +
                DevFinal.SYMBOL.NEW_LINE +
                "versionCode: " + APP_VERSION_CODE +
                DevFinal.SYMBOL.NEW_LINE +
                "package: " + PACKAGE_NAME +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                "===========================" +
                DevFinal.SYMBOL.NEW_LINE_X4 +
                "[日志内容]" +
                DevFinal.SYMBOL.NEW_LINE_X2 +
                "===========================";
    }
}