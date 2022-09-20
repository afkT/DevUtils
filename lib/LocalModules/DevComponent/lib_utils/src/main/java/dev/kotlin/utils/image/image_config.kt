package dev.kotlin.utils.image

import dev.base.DevVariable
import dev.engine.image.ImageConfig
import dev.mvvm.utils.image.AppImageConfig
import dev.mvvm.utils.size.AppSize

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
//val ROUND_3 = ImageConfig.create(ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(3F)
//    )
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
 * 初始化 App ImageConfig 创建器
 * 正常应该在 startup 或 Application 等地方初始化
 */
fun initAppImageConfigCreator() {
    AppImageConfig.setCreator { key, param ->
        when (key) {
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
                        AppSize.dp2px(3F)
                    )
                }
            }
            IMAGE_ROUND_10 -> {
                ImageConfig.create(ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(10F)
                    )
                }
            }
            IMAGE_ROUND_CROP_10 -> {
                ImageConfig.create(ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(10F)
                    )
                    setScaleType(ImageConfig.SCALE_CENTER_CROP)
                }
            }
            IMAGE_ROUND_FIX_10 -> {
                ImageConfig.create(ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(10F)
                    )
                    setScaleType(ImageConfig.SCALE_FIT_CENTER)
                }
            }
            else -> {
                null
            }
        }
    }
}