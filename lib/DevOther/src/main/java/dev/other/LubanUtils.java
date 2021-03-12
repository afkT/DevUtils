package dev.other;

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

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            T data,
            Config config,
            OnCompressListener compressListener
    ) {
        return compress(data, config, null, null, compressListener);
    }

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param predicate        开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            T data,
            Config config,
            CompressionPredicate predicate,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    ) {
        if (data == null || config == null || compressListener == null) return false;
        List<T> lists = new ArrayList<>();
        lists.add(data);
        return compress(lists, config, predicate, renameListener, compressListener);
    }

    // =

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            Config config,
            OnCompressListener compressListener
    ) {
        return compress(lists, config, null, null, compressListener);
    }

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param predicate        开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean compress(
            List<T> lists,
            Config config,
            CompressionPredicate predicate,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    ) {
        if (lists == null) return false;
        int           number  = 0;
        Luban.Builder builder = Luban.with(DevUtils.getContext());
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
        builder.ignoreBy(config.ignoreSize)
                .setFocusAlpha(config.focusAlpha)
                .setTargetDir(config.targetDir)
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
                            compressListener.onStart(size, count);
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
                            compressListener.onSuccess(file, index, count);
                        }
                        if (size >= count) {
                            if (compressListener != null) {
                                compressListener.onComplete(getLists(), fileMaps, count);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        int size = fileMaps.size();
                        if (compressListener != null) {
                            compressListener.onError(e, size - 1, count);
                        }
                        if (size >= count) {
                            if (compressListener != null) {
                                compressListener.onComplete(getLists(), fileMaps, count);
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

    // =======
    // = 配置 =
    // =======

    /**
     * detail: Image Compress Config
     * @author Ttt
     */
    public static class Config {

        // 单位 KB 默认 100 kb 以下不压缩
        public final int     ignoreSize;
        // 是否保留透明通道
        public final boolean focusAlpha;
        // 压缩图片存储路径
        public final String  targetDir;
        // 压缩失败、异常是否结束压缩
        private      boolean mFailFinish;

        public Config(int ignoreSize) {
            this(ignoreSize, true, null);
        }

        public Config(String targetDir) {
            this(100, true, targetDir);
        }

        public Config(
                int ignoreSize,
                String targetDir
        ) {
            this(ignoreSize, true, targetDir);
        }

        public Config(
                int ignoreSize,
                boolean focusAlpha,
                String targetDir
        ) {
            this.ignoreSize = ignoreSize;
            this.focusAlpha = focusAlpha;
            this.targetDir = targetDir;
        }

        // =

        public boolean isFailFinish() {
            return mFailFinish;
        }

        public Config setFailFinish(boolean failFinish) {
            this.mFailFinish = failFinish;
            return this;
        }
    }

    // =======
    // = 接口 =
    // =======

    /**
     * detail: 压缩回调接口
     * @author Ttt
     */
    public interface OnCompressListener {

        /**
         * 开始压缩前调用
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        void onStart(
                int index,
                int count
        );

        /**
         * 压缩成功后调用
         * @param file  压缩成功文件
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        void onSuccess(
                File file,
                int index,
                int count
        );

        /**
         * 当压缩过程出现问题时触发
         * @param error 异常信息
         * @param index 当前压缩索引
         * @param count 压缩总数
         */
        void onError(
                Throwable error,
                int index,
                int count
        );

        /**
         * 压缩完成 ( 压缩结束 )
         * @param lists 压缩成功存储 List
         * @param maps  每个索引对应压缩存储地址
         * @param count 压缩总数
         */
        void onComplete(
                List<File> lists,
                Map<Integer, File> maps,
                int count
        );
    }
}