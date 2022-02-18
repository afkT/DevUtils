package afkt.project.utils

import dev.base.DevVariable
import dev.engine.image.ImageConfig
import dev.utils.app.SizeUtils

/**
 * detail: 项目工具类
 * @author Ttt
 */
object ProjectUtils {

    // ==================
    // = DevImageEngine =
    // ==================

    // GlideConfig 配置变量
    private val sConfigVariable = DevVariable<Float, ImageConfig?>()

    /**
     * 获取圆角 GlideConfig
     * @return 圆角 [ImageConfig]
     */
    val roundConfig3: ImageConfig
        get() = roundConfig(3F)

    /**
     * 获取圆角 GlideConfig
     * @return 圆角 [ImageConfig]
     */
    val roundConfig10: ImageConfig
        get() = roundConfig(10F)

    /**
     * 获取圆角 GlideConfig
     * @param roundDP 圆角 dp 值
     * @return [ImageConfig]
     */
    fun roundConfig(roundDP: Float): ImageConfig {
        sConfigVariable.getVariableValue(roundDP)?.let {
            return it
        }
        val config = ImageConfig.create().apply {
            setRoundedCornersRadius(SizeUtils.dp2px(roundDP))
            setTransform(ImageConfig.TRANSFORM_ROUNDED_CORNERS)
        }
        sConfigVariable.putVariable(roundDP, config)
        return config
    }
}