package afkt.project.model.engine

import afkt.project.app.AppContext
import dev.engine.core.image.ImageConfig
import dev.simple.mvvm.utils.image.AppImageConfig
import dev.simple.mvvm.utils.size.AppSize
import dev.simple.DevSimple

// =============================
// = IImageEngine.EngineConfig =
// =============================

// ================================
// = dev.engine.image.ImageConfig =
// ================================

// ============
// = 使用方式一 =
// ============

val IMAGE_ROUND = ImageConfig.create().apply {
    setTransform(ImageConfig.TRANSFORM_ROUNDED_CORNERS)
    setScaleType(ImageConfig.SCALE_NONE)
}

//val IMAGE_DEFAULT_CROP = ImageConfig.create().apply {
//    setScaleType(ImageConfig.SCALE_CENTER_CROP)
//}
//
//val IMAGE_ROUND_3 = ImageConfig.create(IMAGE_ROUND).apply {
//    setRoundedCornersRadius(
//        AppSize.dp2px(3F)
//    )
//}

// ============
// = 使用方式二 =
// ============

// IMAGE_KEY.toImageConfig() => ImageConfig
const val IMAGE_DEFAULT_CROP = "IMAGE_DEFAULT_CROP"
const val IMAGE_DEFAULT_FIX = "IMAGE_DEFAULT_FIX"
const val IMAGE_ROUND_3 = "IMAGE_ROUND_3"
const val IMAGE_ROUND_10 = "IMAGE_ROUND_10"
const val IMAGE_ROUND_CROP_10 = "IMAGE_ROUND_CROP_10"
const val IMAGE_ROUND_FIX_10 = "IMAGE_ROUND_FIX_10"

// ============
// = 初始化方法 =
// ============

/**
 * 初始化 [AppImageConfig] ImageConfig Creator
 */
fun AppContext.initAppImageConfigCreator() {
    DevSimple.setImageCreator { key, param ->
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
                ImageConfig.create(IMAGE_ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(3F)
                    )
                }
            }

            IMAGE_ROUND_10 -> {
                ImageConfig.create(IMAGE_ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(10F)
                    )
                }
            }

            IMAGE_ROUND_CROP_10 -> {
                ImageConfig.create(IMAGE_ROUND).apply {
                    setRoundedCornersRadius(
                        AppSize.dp2px(10F)
                    )
                    setScaleType(ImageConfig.SCALE_CENTER_CROP)
                }
            }

            IMAGE_ROUND_FIX_10 -> {
                ImageConfig.create(IMAGE_ROUND).apply {
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