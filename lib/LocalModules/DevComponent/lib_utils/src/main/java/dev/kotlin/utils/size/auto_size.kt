package dev.kotlin.utils.size

import android.content.Context
import androidx.annotation.DimenRes
import dev.DevUtils
import dev.base.DevVariableExt
import dev.utils.app.ResourceUtils
import dev.utils.app.SizeUtils
import dev.utils.app.assist.ResourceAssist

// 全局 App 适配值转换快捷类
val GLOBAL_APP_AUTO_SIZE = AppAutoSize()

/**
 * detail: App 适配值转换快捷类
 * @author Ttt
 * 该类主要用于内部缓存适配值, 便于第二次直接从缓存中读取
 * 也可使用
 * SizeUtils.dp2px(context, value).toFloat()
 * AutoSizeUtils.dp2px(context, value).toFloat()
 */
class AppAutoSize {

    // =============
    // = 对外公开方法 =
    // =============

    val CONVERT = Convert()

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
        return CONVERT.innerConvert(
            context, Type.dp2px, value
        )
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
        return CONVERT.innerConvert(
            context, Type.px2dp, value
        )
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
        return CONVERT.innerConvert(
            context, Type.sp2px, value
        )
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
        return CONVERT.innerConvert(
            context, Type.px2sp, value
        )
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
        val value: Float = CONVERT.DIMEN_ID.getVariableValue(
            id, context
        )
        if (value == 0F) {
            CONVERT.DIMEN_ID.variable.removeVariable(id)
        }
        return value
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
        val value: Float = CONVERT.DIMEN_NAME.getVariableValue(
            resName, context
        )
        if (value == 0F) {
            CONVERT.DIMEN_NAME.variable.removeVariable(resName)
        }
        return value
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

    // =============
    // = 具体实现代码 =
    // =============

    // =============
    // = 内部转换存储 =
    // =============

    internal enum class Type {

        dp2px,

        px2dp,

        sp2px,

        px2sp
    }

    /**
     * detail: 转换适配值封装
     * @author Ttt
     */
    class Convert {

        // dp 转 px ( float )
        val DP_2_PX: DevVariableExt<Float, Float, Context?> by lazy {
            DevVariableExt<Float, Float, Context?> { key, context ->
                innerCreator(context, Type.dp2px, key)
            }
        }

        // px 转 dp ( float )
        val PX_2_DP: DevVariableExt<Float, Float, Context?> by lazy {
            DevVariableExt<Float, Float, Context?> { key, context ->
                innerCreator(context, Type.px2dp, key)
            }
        }

        // sp 转 px ( float )
        val SP_2_PX: DevVariableExt<Float, Float, Context?> by lazy {
            DevVariableExt<Float, Float, Context?> { key, context ->
                innerCreator(context, Type.sp2px, key)
            }
        }

        // px 转 sp ( float )
        val PX_2_SP: DevVariableExt<Float, Float, Context?> by lazy {
            DevVariableExt<Float, Float, Context?> { key, context ->
                innerCreator(context, Type.px2sp, key)
            }
        }

        // dimen id 值获取 ( float )
        val DIMEN_ID: DevVariableExt<Int, Float, Context?> by lazy {
            DevVariableExt<Int, Float, Context?> { key, context ->
                val ctx = context ?: DevUtils.getTopActivity()
                ResourceUtils.getDimension(
                    ResourceAssist.get(
                        DevUtils.getContext(ctx)
                    ), key
                )
            }
        }

        // dimen id Name 值获取 ( float )
        val DIMEN_NAME: DevVariableExt<String?, Float, Context?> by lazy {
            DevVariableExt<String?, Float, Context?> { key, context ->
                val ctx = context ?: DevUtils.getTopActivity()
                ResourceUtils.getDimension(
                    ResourceAssist.get(
                        DevUtils.getContext(ctx)
                    ), key
                )
            }
        }

        // ===============
        // = Type 转换处理 =
        // ===============

        /**
         * 适配值内部创建实现
         * @param context Context
         * @param type Type
         * @param key Float
         * @return Float
         */
        private fun innerCreator(
            context: Context?,
            type: Type,
            key: Float
        ): Float {
            val ctx = context ?: DevUtils.getTopActivity()
            return when (type) {
                Type.dp2px -> {
                    SizeUtils.dp2pxf(DevUtils.getContext(ctx), key)
                }
                Type.px2dp -> {
                    SizeUtils.px2dpf(DevUtils.getContext(ctx), key)
                }
                Type.sp2px -> {
                    SizeUtils.sp2pxf(DevUtils.getContext(ctx), key)
                }
                Type.px2sp -> {
                    SizeUtils.px2spf(DevUtils.getContext(ctx), key)
                }
            }
        }

        /**
         * 适配值内部转换
         * @param context Context?
         * @param type Type
         * @param key Float
         * @return Float
         * 该方法主要解决出现转换失败返回 0 的情况
         * 导致后续获取缓存值为 0 直接返回使用
         */
        internal fun innerConvert(
            context: Context?,
            type: Type,
            key: Float
        ): Float {
            when (type) {
                Type.dp2px -> {
                    val value: Float = DP_2_PX.getVariableValue(key, context)
                    if (value == 0F) {
                        DP_2_PX.variable.removeVariable(key)
                    }
                    return value
                }
                Type.px2dp -> {
                    val value: Float = PX_2_DP.getVariableValue(key, context)
                    if (value == 0F) {
                        PX_2_DP.variable.removeVariable(key)
                    }
                    return value
                }
                Type.sp2px -> {
                    val value: Float = SP_2_PX.getVariableValue(key, context)
                    if (value == 0F) {
                        SP_2_PX.variable.removeVariable(key)
                    }
                    return value
                }
                Type.px2sp -> {
                    val value: Float = PX_2_SP.getVariableValue(key, context)
                    if (value == 0F) {
                        PX_2_SP.variable.removeVariable(key)
                    }
                    return value
                }
            }
        }
    }
}