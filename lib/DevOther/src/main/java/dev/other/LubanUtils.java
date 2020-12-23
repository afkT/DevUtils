package dev.other;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.DevUtils;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.InputStreamProvider;
import top.zibin.luban.Luban;
import top.zibin.luban.OnRenameListener;

/**
 * detail: Luban 工具类
 * @author Ttt
 * <pre>
 *     Luban 鲁班图片压缩
 *     https://github.com/Curzibn/Luban
 * </pre>
 */
public final class LubanUtils {

    private LubanUtils() {
    }

    // 单位 KB 默认 100 kb 以下不压缩
    private static int     sIgnoreSize = 100;
    // 是否保留透明通道
    private static boolean sFocusAlpha = true;
    // 压缩图片存储路径
    private static String  sTargetDir  = null;

    /**
     * 设置全局默认配置
     * @param ignoreSize 压缩忽略大小 单位 KB
     * @param focusAlpha 是否保留透明通道
     * @param targetDir  压缩图片存储路径
     */
    public static void setConfig(
            final int ignoreSize,
            final boolean focusAlpha,
            final String targetDir
    ) {
        sIgnoreSize = ignoreSize;
        sFocusAlpha = focusAlpha;
        sTargetDir = targetDir;
    }

    // =

    /**
     * 获取全局 Context
     * @return {@link Context}
     */
    private static Context getContext() {
        return DevUtils.getContext();
    }

    // =======
    // = 接口 =
    // =======

    public interface OnCompressListener {

        /**
         * 压缩开始前调用
         * @param index 图片集合压缩索引
         */
        void onStart(int index);

        /**
         * 压缩成功后调用
         * @param file  压缩成功文件
         * @param index 图片集合压缩索引
         */
        void onSuccess(
                File file,
                int index
        );

        /**
         * 当压缩过程出现问题时调用
         * @param e     异常信息
         * @param index 图片集合压缩索引
         */
        void onError(
                Throwable e,
                int index
        );

        /**
         * 压缩完成
         * @param lists 压缩成功存储 List
         */
        void onComplete(List<File> lists);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            T data,
            OnCompressListener compressListener
    ) {
        return compress(data, sIgnoreSize, sFocusAlpha, compressListener);
    }

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param ignoreSize       压缩忽略大小 单位 KB
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            T data,
            int ignoreSize,
            OnCompressListener compressListener
    ) {
        return compress(data, ignoreSize, sFocusAlpha, compressListener);
    }

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param ignoreSize       压缩忽略大小 单位 KB
     * @param focusAlpha       是否保留透明通道
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            T data,
            int ignoreSize,
            boolean focusAlpha,
            OnCompressListener compressListener
    ) {
        List<T> lists = new ArrayList<>();
        lists.add(data);
        return compress(lists, ignoreSize, focusAlpha, sTargetDir, null, null, compressListener);
    }

    // =

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            OnCompressListener compressListener
    ) {
        return compress(lists, sIgnoreSize, sFocusAlpha, sTargetDir, null, null, compressListener);
    }

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param ignoreSize       压缩忽略大小 单位 KB
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            int ignoreSize,
            OnCompressListener compressListener
    ) {
        return compress(lists, ignoreSize, sFocusAlpha, sTargetDir, null, null, compressListener);
    }

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param ignoreSize       压缩忽略大小 单位 KB
     * @param focusAlpha       是否保留透明通道
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            int ignoreSize,
            boolean focusAlpha,
            OnCompressListener compressListener
    ) {
        return compress(lists, ignoreSize, focusAlpha, sTargetDir, null, null, compressListener);
    }

    /**
     * 最终压缩方法
     * @param lists            待压缩图片集合
     * @param ignoreSize       压缩忽略大小 单位 KB
     * @param focusAlpha       是否保留透明通道
     * @param targetDir        压缩图片存储路径
     * @param predicate        开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            int ignoreSize,
            boolean focusAlpha,
            String targetDir,
            CompressionPredicate predicate,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    ) {
        if (lists == null) return false;
        int           number  = 0;
        Luban.Builder builder = Luban.with(getContext());
        for (T src : lists) {
            if (src instanceof String) {
                builder.load((String) src);
                number++;
            } else if (src instanceof File) {
                builder.load((File) src);
                number++;
            } else if (src instanceof Uri) {
                builder.load((Uri) src);
                number++;
            } else if (src instanceof InputStreamProvider) {
                builder.load((InputStreamProvider) src);
                number++;
            }
        }
        if (number == 0) return false;
        int                count    = number;
        Map<Integer, File> fileMaps = new LinkedHashMap<>();
        // 配置信息
        builder.ignoreBy(ignoreSize)
                .setFocusAlpha(focusAlpha)
                .setTargetDir(targetDir)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        if (predicate != null) return predicate.apply(path);
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setRenameListener(renameListener)
                .setCompressListener(new top.zibin.luban.OnCompressListener() {
                    @Override
                    public void onStart() {
                        int size = fileMaps.size();
                        fileMaps.put(size, null);
                        if (compressListener != null) {
                            compressListener.onStart(size);
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file == null) {
                            onError(new NullPointerException("file is null"));
                            return;
                        }
                        if (!file.exists()) { // 文件不存在则触发异常回调
                            onError(new FileNotFoundException(file.getAbsolutePath()));
                            return;
                        }
                        int size  = fileMaps.size();
                        int index = (size - 1);
                        fileMaps.put(index, file);
                        if (compressListener != null) {
                            compressListener.onSuccess(file, index);
                        }
                        if (size >= count) {
                            if (compressListener != null) {
                                compressListener.onComplete(getLists());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        int size = fileMaps.size();
                        if (compressListener != null) {
                            compressListener.onError(e, size - 1);
                        }
                        if (size >= count) {
                            if (compressListener != null) {
                                compressListener.onComplete(getLists());
                            }
                        }
                    }

                    private List<File> getLists() {
                        List<File>     files    = new ArrayList<>();
                        Iterator<File> iterator = fileMaps.values().iterator();
                        while (iterator.hasNext()) {
                            File file = iterator.next();
                            if (file != null && file.exists()) {
                                files.add(file);
                            }
                        }
                        return files;
                    }
                }).launch();
        return true;
    }
}