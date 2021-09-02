package java.dev.engine.barcode;

import com.google.zxing.BarcodeFormat;

import dev.engine.barcode.IBarCodeEngine;

/**
 * detail: BarCode ( Data、Params ) Item
 * @author Ttt
 */
public class BarCodeData
        extends IBarCodeEngine.EngineItem {

    // 条码内容
    private final String        mContent;
    // 条码宽度
    private final int           mWidth;
    // 条码高度
    private final int           mHeight;
    // 条码类型
    private       BarcodeFormat mFormat;

    private BarCodeData(
            String content,
            int width,
            int height
    ) {
        this.mContent = content;
        this.mWidth   = width;
        this.mHeight  = height;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 快捷创建 BarCode ( Data、Params ) Item
     * @param content 条码内容
     * @param size    条码大小
     * @return BarCode Item
     */
    public static BarCodeData get(
            String content,
            int size
    ) {
        return new BarCodeData(content, size, size);
    }

    /**
     * 快捷创建 BarCode ( Data、Params ) Item
     * @param content 条码内容
     * @param width   条码宽度
     * @param height  条码高度
     * @return BarCode Item
     */
    public static BarCodeData get(
            String content,
            int width,
            int height
    ) {
        return new BarCodeData(content, width, height);
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取条码类型
     * @return 条码类型
     */
    public BarcodeFormat getFormat() {
        return mFormat;
    }

    /**
     * 设置条码类型
     * @param format 条码类型
     * @return BarCode Item
     */
    public BarCodeData setFormat(BarcodeFormat format) {
        this.mFormat = format;
        return this;
    }
}