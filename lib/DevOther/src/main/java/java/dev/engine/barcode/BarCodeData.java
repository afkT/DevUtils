package java.dev.engine.barcode;

import android.graphics.Bitmap;
import android.graphics.Color;

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
    // 条码类型 ( 默认二维码 )
    private       BarcodeFormat mFormat          = BarcodeFormat.QR_CODE;
    // 条码前景色 ( 默认黑色方块 )
    private       int           mForegroundColor = Color.BLACK;
    // 条码背景色 ( 默认白色背景 )
    private       int           mBackgroundColor = Color.WHITE;
    // 条码嵌入 icon、logo
    private       Bitmap        mIcon;

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
     * 获取条码内容
     * @return 条码内容
     */
    public String getContent() {
        return mContent;
    }

    /**
     * 获取条码宽度
     * @return 条码宽度
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * 获取条码高度
     * @return 条码高度
     */
    public int getHeight() {
        return mHeight;
    }

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

    /**
     * 获取条码前景色
     * @return 条码前景色
     */
    public int getForegroundColor() {
        return mForegroundColor;
    }

    /**
     * 设置条码前景色
     * @param foregroundColor 条码前景色
     * @return BarCode Item
     */
    public BarCodeData setForegroundColor(int foregroundColor) {
        this.mForegroundColor = foregroundColor;
        return this;
    }

    /**
     * 获取条码背景色
     * @return 条码背景色
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * 设置条码背景色
     * @param backgroundColor 条码背景色
     * @return BarCode Item
     */
    public BarCodeData setBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
        return this;
    }

    /**
     * 设置条码前景、背景色
     * @param foregroundColor 条码前景色
     * @param backgroundColor 条码背景色
     * @return BarCode Item
     */
    public BarCodeData setBackgroundColor(
            int foregroundColor,
            int backgroundColor
    ) {
        this.mForegroundColor = foregroundColor;
        this.mBackgroundColor = backgroundColor;
        return this;
    }

    /**
     * 获取条码嵌入 icon、logo
     * @return 嵌入 icon、logo
     */
    public Bitmap getIcon() {
        return mIcon;
    }

    /**
     * 设置条码嵌入 icon、logo
     * @param icon icon、logo
     * @return BarCode Item
     */
    public BarCodeData setIcon(Bitmap icon) {
        this.mIcon = icon;
        return this;
    }
}