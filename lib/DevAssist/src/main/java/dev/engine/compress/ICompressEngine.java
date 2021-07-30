package dev.engine.compress;

import java.util.List;

import dev.engine.compress.listener.CompressFilter;
import dev.engine.compress.listener.OnCompressListener;
import dev.engine.compress.listener.OnRenameListener;

/**
 * detail: Image Compress Engine 接口
 * @author Ttt
 */
public interface ICompressEngine<Config extends ICompressEngine.EngineConfig> {

    /**
     * detail: Image Compress Config
     * @author Ttt
     */
    class EngineConfig {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    boolean compress(
            Object data,
            Config config,
            OnCompressListener compressListener
    );

    /**
     * 压缩方法
     * @param data             待压缩图片
     * @param config           配置信息
     * @param filter           开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    boolean compress(
            Object data,
            Config config,
            CompressFilter filter,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    );

    // =

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    boolean compress(
            List<?> lists,
            Config config,
            OnCompressListener compressListener
    );

    /**
     * 压缩方法
     * @param lists            待压缩图片集合
     * @param config           配置信息
     * @param filter           开启压缩条件
     * @param renameListener   压缩前重命名接口
     * @param compressListener 压缩回调接口
     * @return {@code true} success, {@code false} fail
     */
    boolean compress(
            List<?> lists,
            Config config,
            CompressFilter filter,
            OnRenameListener renameListener,
            OnCompressListener compressListener
    );
}