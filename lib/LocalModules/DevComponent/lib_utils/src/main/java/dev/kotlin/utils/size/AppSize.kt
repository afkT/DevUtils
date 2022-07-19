package dev.kotlin.utils.size

import android.content.Context
import androidx.annotation.DimenRes

/**
 * detail: App 适配值转换快捷类
 * @author Ttt
 * 内部使用 [AppAutoSize]
 */
object AppSize {

    // =============
    // = 对外公开方法 =
    // =============

    private val AUTO_SIZE = GLOBAL_APP_AUTO_SIZE

    val CONVERT: AppAutoSize.Convert by lazy {
        AUTO_SIZE.CONVERT
    }

    // ===============
    // = AppAutoSize =
    // ===============

    // =============
    // = 无 Context =
    // =============

    /**
     * dp 转 px
     * @param value 待转换值
     * @return 转换后的值
     */
    fun dp2px(value: Float): Int {
        return dp2px(null, value)
    }

    /**
     * dp 转 px ( float )
     * @param value 待转换值
     * @return 转换后的值
     */
    fun dp2pxf(value: Float): Float {
        return dp2pxf(null, value)
    }

    /**
     * px 转 dp
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2dp(value: Float): Int {
        return px2dp(null, value)
    }

    /**
     * px 转 dp ( float )
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2dpf(value: Float): Float {
        return px2dpf(null, value)
    }

    /**
     * sp 转 px
     * @param value 待转换值
     * @return 转换后的值
     */
    fun sp2px(value: Float): Int {
        return sp2px(null, value)
    }

    /**
     * sp 转 px ( float )
     * @param value 待转换值
     * @return 转换后的值
     */
    fun sp2pxf(value: Float): Float {
        return sp2pxf(null, value)
    }

    /**
     * px 转 sp
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2sp(value: Float): Int {
        return px2sp(null, value)
    }

    /**
     * px 转 sp ( float )
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2spf(value: Float): Float {
        return px2spf(null, value)
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    fun getDimension(@DimenRes id: Int): Float {
        return getDimension(null, id)
    }

    /**
     * 获取 Dimension
     * @param id resource identifier
     * @return Dimension
     */
    fun getDimensionInt(@DimenRes id: Int): Int {
        return getDimensionInt(null, id)
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    fun getDimension(resName: String?): Float {
        return getDimension(null, resName)
    }

    /**
     * 获取 Dimension
     * @param resName resource name
     * @return Dimension
     */
    fun getDimensionInt(resName: String?): Int {
        return getDimensionInt(null, resName)
    }

    // ===========
    // = Context =
    // ===========

    /**
     * dp 转 px
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun dp2px(
        context: Context?,
        value: Float
    ): Int {
        return dp2pxf(context, value).toInt()
    }

    /**
     * dp 转 px ( float )
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun dp2pxf(
        context: Context?,
        value: Float
    ): Float {
        return AUTO_SIZE.dp2pxf(context, value)
    }

    /**
     * px 转 dp
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2dp(
        context: Context?,
        value: Float
    ): Int {
        return px2dpf(context, value).toInt()
    }

    /**
     * px 转 dp ( float )
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2dpf(
        context: Context?,
        value: Float
    ): Float {
        return AUTO_SIZE.px2dpf(context, value)
    }

    /**
     * sp 转 px
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun sp2px(
        context: Context?,
        value: Float
    ): Int {
        return sp2pxf(context, value).toInt()
    }

    /**
     * sp 转 px ( float )
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun sp2pxf(
        context: Context?,
        value: Float
    ): Float {
        return AUTO_SIZE.sp2pxf(context, value)
    }

    /**
     * px 转 sp
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2sp(
        context: Context?,
        value: Float
    ): Int {
        return px2spf(context, value).toInt()
    }

    /**
     * px 转 sp ( float )
     * @param context Context
     * @param value 待转换值
     * @return 转换后的值
     */
    fun px2spf(
        context: Context?,
        value: Float
    ): Float {
        return AUTO_SIZE.px2spf(context, value)
    }

    /**
     * 获取 Dimension
     * @param context Context
     * @param id resource identifier
     * @return Dimension
     */
    fun getDimension(
        context: Context?,
        @DimenRes id: Int
    ): Float {
        return AUTO_SIZE.getDimension(context, id)
    }

    /**
     * 获取 Dimension
     * @param context Context
     * @param id resource identifier
     * @return Dimension
     */
    fun getDimensionInt(
        context: Context?,
        @DimenRes id: Int
    ): Int {
        return getDimension(context, id).toInt()
    }

    /**
     * 获取 Dimension
     * @param context Context
     * @param resName resource name
     * @return Dimension
     */
    fun getDimension(
        context: Context?,
        resName: String?
    ): Float {
        return AUTO_SIZE.getDimension(context, resName)
    }

    /**
     * 获取 Dimension
     * @param context Context
     * @param resName resource name
     * @return Dimension
     */
    fun getDimensionInt(
        context: Context?,
        resName: String?
    ): Int {
        return getDimension(context, resName).toInt()
    }
}