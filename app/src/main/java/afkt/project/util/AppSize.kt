package afkt.project.util

import android.content.Context
import dev.DevUtils
import dev.base.DevVariableExt
import dev.utils.app.SizeUtils

/**
 * detail: App 适配快捷类
 * @author Ttt
 */
object AppSize {

    // dp 值转换处理
    private val sDPConvert: DevVariableExt<Float, Float, Context?> by lazy {
        DevVariableExt<Float, Float, Context?> { key, param ->
            var context = param
            if (param == null) {
                context = DevUtils.getTopActivity()
            }
            SizeUtils.dp2pxf(DevUtils.getContext(context), key)
//            AutoSizeUtils.dp2px(DevUtils.getContext(context), key).toFloat()
        }
    }

    // sp 值转换处理
    private val sSPConvert: DevVariableExt<Float, Float, Context?> by lazy {
        DevVariableExt<Float, Float, Context?> { key, param ->
            var context = param
            if (param == null) {
                context = DevUtils.getTopActivity()
            }
            SizeUtils.sp2pxf(DevUtils.getContext(context), key)
//            AutoSizeUtils.sp2px(DevUtils.getContext(context), key).toFloat()
        }
    }

    // =

    /**
     * dp 转 px
     * @param key Float
     * @return Float
     */
    fun dp2px(key: Float): Int {
        return sDPConvert.getVariableValue(key).toInt()
    }

    /**
     * dp 转 px ( float )
     * @param key Float
     * @return Float
     */
    fun dp2pxf(key: Float): Float {
        return sDPConvert.getVariableValue(key)
    }

    /**
     * sp 转 px
     * @param key Float
     * @return Float
     */
    fun sp2px(key: Float): Int {
        return sSPConvert.getVariableValue(key).toInt()
    }

    /**
     * sp 转 px ( float )
     * @param key Float
     * @return Float
     */
    fun sp2pxf(key: Float): Float {
        return sSPConvert.getVariableValue(key)
    }
}