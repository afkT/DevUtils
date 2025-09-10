package dev.engine.core.barcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import dev.engine.barcode.IBarCodeEngine

/**
 * detail: BarCode ( Data、Params ) Item
 * @author Ttt
 */
open class BarCodeData private constructor(
    // 条码内容
    private val mContent: String?,
    // 条码宽度
    private val mWidth: Int,
    // 条码高度
    private val mHeight: Int
) : IBarCodeEngine.EngineItem {

    companion object {

        /**
         * 快捷创建 BarCode ( Data、Params ) Item
         * @param content 条码内容
         * @param size    条码大小
         * @return BarCode Item
         */
        operator fun get(
            content: String,
            size: Int
        ): BarCodeData {
            return BarCodeData(content, size, size)
        }

        /**
         * 快捷创建 BarCode ( Data、Params ) Item
         * @param content 条码内容
         * @param width   条码宽度
         * @param height  条码高度
         * @return BarCode Item
         */
        operator fun get(
            content: String,
            width: Int,
            height: Int
        ): BarCodeData {
            return BarCodeData(content, width, height)
        }
    }

    // 条码类型 ( 默认二维码 )
    private var mFormat = BarcodeFormat.QR_CODE

    // 条码前景色 ( 默认黑色方块 )
    private var mForegroundColor = Color.BLACK

    // 条码背景色 ( 默认白色背景 )
    private var mBackgroundColor = Color.WHITE

    // 条码嵌入 icon、logo
    private var mIcon: Bitmap? = null

    // icon 占比
    private var mIconScale = 4.0F

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取条码内容
     * @return 条码内容
     */
    fun getContent(): String? {
        return mContent
    }

    /**
     * 获取条码宽度
     * @return 条码宽度
     */
    fun getWidth(): Int {
        return mWidth
    }

    /**
     * 获取条码高度
     * @return 条码高度
     */
    fun getHeight(): Int {
        return mHeight
    }

    /**
     * 获取条码类型
     * @return 条码类型
     */
    fun getFormat(): BarcodeFormat {
        return mFormat
    }

    /**
     * 设置条码类型
     * @param format 条码类型
     * @return BarCode Item
     */
    fun setFormat(format: BarcodeFormat): BarCodeData {
        mFormat = format
        return this
    }

    /**
     * 获取条码前景色
     * @return 条码前景色
     */
    fun getForegroundColor(): Int {
        return mForegroundColor
    }

    /**
     * 设置条码前景色
     * @param foregroundColor 条码前景色
     * @return BarCode Item
     */
    fun setForegroundColor(foregroundColor: Int): BarCodeData {
        mForegroundColor = foregroundColor
        return this
    }

    /**
     * 获取条码背景色
     * @return 条码背景色
     */
    fun getBackgroundColor(): Int {
        return mBackgroundColor
    }

    /**
     * 设置条码背景色
     * @param backgroundColor 条码背景色
     * @return BarCode Item
     */
    fun setBackgroundColor(backgroundColor: Int): BarCodeData {
        mBackgroundColor = backgroundColor
        return this
    }

    /**
     * 设置条码前景、背景色
     * @param foregroundColor 条码前景色
     * @param backgroundColor 条码背景色
     * @return BarCode Item
     */
    fun setBackgroundColor(
        foregroundColor: Int,
        backgroundColor: Int
    ): BarCodeData {
        mForegroundColor = foregroundColor
        mBackgroundColor = backgroundColor
        return this
    }

    /**
     * 获取条码嵌入 icon、logo
     * @return 嵌入 icon、logo
     */
    fun getIcon(): Bitmap? {
        return mIcon
    }

    /**
     * 设置条码嵌入 icon、logo
     * @param icon icon、logov
     * @return BarCode Item
     */
    fun setIcon(icon: Bitmap?): BarCodeData {
        mIcon = icon
        return this
    }

    /**
     * 获取 icon 占比
     * @return icon 占比
     */
    fun getIconScale(): Float {
        return mIconScale
    }

    /**
     * 设置 icon 占比
     * @param iconScale icon 占比
     * @return BarCode Item
     */
    fun setIconScale(iconScale: Float): BarCodeData {
        mIconScale = iconScale
        return this
    }
}