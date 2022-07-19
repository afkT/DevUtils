package dev.kotlin.utils.image

import dev.base.DevVariable
import dev.engine.image.ImageConfig
import dev.kotlin.utils.size.GLOBAL_APP_AUTO_SIZE

// ================================
// = dev.engine.image.ImageConfig =
// ================================

// ============
// = 使用方式一 =
// ============

private val ROUND = ImageConfig.create().apply {
    setTransform(ImageConfig.TRANSFORM_ROUNDED_CORNERS)
    setScaleType(ImageConfig.SCALE_NONE)
}

//val DEFAULT_CROP = ImageConfig.create().apply {
//    setScaleType(ImageConfig.SCALE_CENTER_CROP)
//}
//
//val DEFAULT_FIX = ImageConfig.create().apply {
//    setScaleType(ImageConfig.SCALE_FIT_CENTER)
//}
//
//val ROUND_3 = ImageConfig.create(ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(3F)
//    )
//}
//
//val ROUND_10 = ImageConfig.create(ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(10F)
//    )
//}
//
//val ROUND_CROP_10 = ImageConfig.create(ROUND_10).apply {
//    setScaleType(ImageConfig.SCALE_CENTER_CROP)
//}
//
//val ROUND_FIX_10 = ImageConfig.create(ROUND_10).apply {
//    setScaleType(ImageConfig.SCALE_FIT_CENTER)
//}

// ============
// = 使用方式二 =
// ============

// ===========
// = Variable =
// ===========

// Image Engine ImageConfig 配置缓存
private val VAR_IMAGE_CONFIG = DevVariable<String, ImageConfig?>()

// IMAGE_KEY.toImageConfig() => ImageConfig
const val IMAGE_DEFAULT_CROP = "IMAGE_DEFAULT_CROP"
const val IMAGE_DEFAULT_FIX = "IMAGE_DEFAULT_FIX"
const val IMAGE_ROUND_3 = "IMAGE_ROUND_3"
const val IMAGE_ROUND_10 = "IMAGE_ROUND_10"
const val IMAGE_ROUND_CROP_10 = "IMAGE_ROUND_CROP_10"
const val IMAGE_ROUND_FIX_10 = "IMAGE_ROUND_FIX_10"

/**
 * 清空全部 ImageConfig 配置
 * 当适配值变动只需要 clear 即可
 */
fun clearImageConfigVAR() {
    VAR_IMAGE_CONFIG.clearVariables()
}

/**
 * 通过 Key 获取 ImageConfig
 * @return [ImageConfig]
 */
fun String.toImageConfig(): ImageConfig? {
    VAR_IMAGE_CONFIG.getVariableValue(this)?.let { config ->
        return config
    }
    val config = createImageConfig(this)
    config?.let { VAR_IMAGE_CONFIG.putVariable(this, it) }
    return config
}

/**
 * 通过 Key 创建 ImageConfig
 * @param key String
 * @return ImageConfig?
 * 新增 Key 需要进行添加判断
 */
internal fun createImageConfig(key: String): ImageConfig? {
    return when (key) {
        IMAGE_DEFAULT_CROP -> {
            ImageConfig.create().apply {
                setScaleType(ImageConfig.SCALE_CENTER_CROP)
            }
        }
        IMAGE_DEFAULT_FIX -> {
            ImageConfig.create().apply {
                setScaleType(ImageConfig.SCALE_FIT_CENTER)
            }
        }
        IMAGE_ROUND_3 -> {
            ImageConfig.create(ROUND).apply {
                setRoundedCornersRadius(
                    GLOBAL_APP_AUTO_SIZE.dp2px(3F)
                )
            }
        }
        IMAGE_ROUND_10 -> {
            ImageConfig.create(ROUND).apply {
                setRoundedCornersRadius(
                    GLOBAL_APP_AUTO_SIZE.dp2px(10F)
                )
            }
        }
        IMAGE_ROUND_CROP_10 -> {
            ImageConfig.create(ROUND).apply {
                setRoundedCornersRadius(
                    GLOBAL_APP_AUTO_SIZE.dp2px(10F)
                )
                setScaleType(ImageConfig.SCALE_CENTER_CROP)
            }
        }
        IMAGE_ROUND_FIX_10 -> {
            ImageConfig.create(ROUND).apply {
                setRoundedCornersRadius(
                    GLOBAL_APP_AUTO_SIZE.dp2px(10F)
                )
                setScaleType(ImageConfig.SCALE_FIT_CENTER)
            }
        }
        else -> {
            null
        }
    }
}