package dev.engine.compress.listener;

/**
 * detail: 修改压缩图片文件名接口
 * @author Ttt
 */
public interface OnRenameListener {

    /**
     * 压缩前调用该方法用于修改压缩后文件名
     * @param filePath 文件路径
     * @return 返回重命名后的字符串
     */
    String rename(String filePath);
}