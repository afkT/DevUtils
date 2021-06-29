package dev.capture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.DevHttpCapture;
import dev.utils.LogPrintUtils;
import dev.utils.app.PathUtils;
import dev.utils.common.DevCommonUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.encrypt.MD5Utils;

class Utils {

    private Utils() {
    }

    // ========
    // = Gson =
    // ========

    private static final Gson GSON = createGson(false).create();

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGson(final boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder;
    }

    // ==========
    // = 转换方法 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(final Object object) {
        if (object == null) return null;
        try {
            return GSON.toJson(object);
        } catch (Exception e) {
            LogPrintUtils.eTag(DevHttpCapture.TAG, e, "toJson");
        }
        return null;
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(
            final String json,
            final Class<T> classOfT
    ) {
        if (json == null) return null;
        try {
            return GSON.fromJson(json, classOfT);
        } catch (Exception e) {
            LogPrintUtils.eTag(DevHttpCapture.TAG, e, "fromJson");
        }
        return null;
    }

    // =============
    // = 文件操作相关 =
    // =============

    // Http Capture Storage Path
    private static String sStoragePath;

    /**
     * 获取抓包存储路径
     * @return 抓包存储路径
     */
    public static String getStoragePath() {
        if (StringUtils.isEmpty(sStoragePath)) {
            sStoragePath = PathUtils.getInternal().getAppDataPath(
                    DevHttpCapture.TAG
            );
        }
        return sStoragePath;
    }

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名
     * @return 指定模块抓包存储路径
     */
    public static String getModulePath(final String moduleName) {
        return FileUtils.getAbsolutePath(
                FileUtils.getFile(getStoragePath(), moduleName)
        );
    }

    /**
     * 获取指定模块指定抓包存储文件
     * @param modulePath 模块路径
     * @param fileName   抓包存储文件名
     * @return 指定模块指定抓包存储文件
     */
    public static File getModuleHttpCaptureFile(
            final String modulePath,
            final String fileName
    ) {
        return FileUtils.getFile(modulePath, fileName);
    }

    /**
     * 获取指定模块指定抓包存储文件
     * @param captureFile 抓包存储文件
     * @return 指定模块指定抓包存储文件
     */
    public static File getModuleHttpCaptureFile(
            final CaptureFile captureFile
    ) {
        return getModuleHttpCaptureFile(
                Utils.getModulePath(captureFile.getModuleName()),
                captureFile.getFileName()
        );
    }

    // =

    /**
     * 获取唯一文件名
     * @param filePath  文件路径 {@link #getModulePath(String)}
     * @param isEncrypt 是否加密数据
     * @return 唯一文件名
     */
    private static String getUniqueFileName(
            final String filePath,
            final boolean isEncrypt
    ) {
        if (StringUtils.isEmpty(filePath)) return null;
        while (true) {
            String md5Value = MD5Utils.md5(DevCommonUtils.getRandomUUIDToString());
            String fileName;
            // 属于加密的文件名前加前缀
            if (isEncrypt) {
                fileName = "e_" + md5Value + ".json";
            } else {
                fileName = md5Value + ".json";
            }
            // 文件不存在才返回文件名
            if (!FileUtils.isFileExists(filePath, fileName)) {
                return fileName;
            }
        }
    }

    /**
     * 存储 Http 抓包数据
     * @param captureFile 抓包存储文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean saveHttpCaptureFile(final CaptureFile captureFile) {
        if (captureFile != null) {
            // 获取指定模块抓包存储路径
            String filePath = getModulePath(captureFile.getModuleName());
            // 创建文件夹
            FileUtils.createFolder(filePath);
            // 获取文件名
            String fileName = getUniqueFileName(filePath, captureFile.isEncrypt());
            if (fileName != null) {
                captureFile.setFileName(fileName);
                // 将对象转换为 JSON String
                String json = toJson(captureFile);
                // 存储处理
                return FileUtils.saveFile(
                        getModuleHttpCaptureFile(filePath, fileName),
                        StringUtils.getBytes(json)
                );
            }
        }
        return false;
    }

    /**
     * 通过 File 读取文件映射为抓包存储文件
     * @param file 抓包存储文件地址
     * @return 抓包存储文件
     */
    public static CaptureFile fromCaptureFile(final File file) {
        if (FileUtils.isFile(file)) {
            String json = FileUtils.readFile(file);
            return fromJson(json, CaptureFile.class);
        }
        return null;
    }

    /**
     * 获取指定模块所有抓包数据
     * @param moduleName 模块名
     * @param isEncrypt  是否加密数据
     * @return 指定模块所有抓包数据集合
     */
    public static List<CaptureFile> getModuleHttpCaptures(
            final String moduleName,
            final boolean isEncrypt
    ) {
        List<CaptureFile> lists = new ArrayList<>();
        // 获取指定模块抓包存储路径
        String filePath   = getModulePath(moduleName);
        File   moduleFile = FileUtils.getFile(filePath);
        if (FileUtils.isFileExists(moduleFile)) {
            File[] files = moduleFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (FileUtils.isFile(file)) {
                        // 获取文件名
                        String fileName = file.getName();
                        // 判断是否加密文件
                        boolean isEncryptFile = fileName.startsWith("e_");
                        if (isEncrypt) {
                            // 要求获取加密文件并且属于加密文件才处理
                            if (isEncryptFile) {
                                CaptureFile captureFile = fromCaptureFile(file);
                                if (captureFile != null) {
                                    lists.add(captureFile);
                                }
                            }
                        } else {
                            // 要求获取非加密文件并且属于非加密文件才处理
                            if (!isEncryptFile) {
                                CaptureFile captureFile = fromCaptureFile(file);
                                if (captureFile != null) {
                                    lists.add(captureFile);
                                }
                            }
                        }
                    }
                }
            }
        }
        return lists;
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    public static Map<String, List<CaptureFile>> getAllModule(
            final boolean isEncrypt
    ) {
        Map<String, List<CaptureFile>> maps = new LinkedHashMap<>();
        // 抓包存储路径
        String filePath = getStoragePath();
        File   rootFile = FileUtils.getFile(filePath);
        if (FileUtils.isFileExists(rootFile)) {
            File[] files = rootFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (FileUtils.isDirectory(file)) {
                        String moduleName = file.getName();
                        maps.put(
                                moduleName,
                                getModuleHttpCaptures(moduleName, isEncrypt)
                        );
                    }
                }
            }
        }
        return maps;
    }
}