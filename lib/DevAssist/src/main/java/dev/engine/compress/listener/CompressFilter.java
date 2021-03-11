package dev.engine.compress.listener;

/**
 * detail: 压缩过滤接口
 * @author Ttt
 */
public interface CompressFilter {

    /**
     * 根据路径判断是否进行压缩
     * @param path 文件路径
     * @return {@code true} yes, {@code false} no
     */
    boolean apply(String path);
}