package afkt.project.features.ui_effect.gpu

import android.graphics.PointF
import dev.engine.extensions.log.log_eTag
import jp.co.cyberagent.android.gpuimage.filter.*
import java.util.*

class GPUFilterItem(
    // 滤镜名称
    val filterName: String,
    // 滤镜类型
    val filterType: FilterType = FilterType.NONE,
    // ACV 滤镜文件
    val acvPath: String = ""
) {
    /**
     * 是否滤镜枚举类型
     */
    fun isFilterType() = filterType != FilterType.NONE

    companion object {

        // 日志 TAG
        val TAG = GPUFilterItem::class.java.simpleName

        // ACV 滤镜文件列表
        val ACV_LISTS = mutableListOf<GPUFilterItem>()

        // 滤镜枚举类型集合
        var FILTER_LISTS = mutableListOf<GPUFilterItem>()

        init {

            // ==================
            // = ACV 滤镜文件列表 =
            // ==================

            ACV_LISTS.add(GPUFilterItem("August", acvPath = "filter/August.acv"))
            ACV_LISTS.add(GPUFilterItem("Darker", acvPath = "filter/Darker.acv"))
            ACV_LISTS.add(GPUFilterItem("Dream", acvPath = "filter/Dream.acv"))
            ACV_LISTS.add(GPUFilterItem("Fornature", acvPath = "filter/Fornature.acv"))
            ACV_LISTS.add(GPUFilterItem("Greens", acvPath = "filter/Greens.acv"))
            ACV_LISTS.add(GPUFilterItem("Miami", acvPath = "filter/Miami.acv"))

            // =================
            // = 滤镜枚举类型集合 =
            // =================

            FilterType.entries.forEach {
                if (it != FilterType.NONE) {
                    FILTER_LISTS.add(GPUFilterItem(it.name, it))
                }
            }
        }
    }
}

/**
 * 滤镜类型枚举类
 */
enum class FilterType(
    typeName: String
) {
    NONE("NONE"),
    CONTRAST("Contrast"),
    GRAYSCALE("Grayscale"),
    SHARPEN("Sharpness"),
    SEPIA("Sepia"),
    SOBEL_EDGE_DETECTION("Sobel Edge Detection"),
    THREE_X_THREE_CONVOLUTION("3x3 Convolution"),
    FILTER_GROUP("Grouped filters"),
    EMBOSS("Emboss"),
    POSTERIZE("Posterize"),
    GAMMA("Gamma"),
    BRIGHTNESS("Brightness"),
    INVERT("Invert"),
    HUE("Hue"),
    PIXELATION("Pixelation"),
    SATURATION("Saturation"),
    EXPOSURE("Exposure"),
    HIGHLIGHT_SHADOW("Highlight Shadow"),
    MONOCHROME("Monochrome"),
    OPACITY("Opacity"),
    RGB("RGB"),
    WHITE_BALANCE("White Balance"),
    VIGNETTE("Vignette"),
    TONE_CURVE("ToneCurve"),
    BLEND_COLOR_BURN("Blend (Color Burn)"),
    BLEND_COLOR_DODGE("Blend (Color Dodge)"),
    BLEND_DARKEN("Blend (Darken)"),
    BLEND_DIFFERENCE("Blend (Difference)"),
    BLEND_DISSOLVE("Blend (Dissolve)"),
    BLEND_EXCLUSION("Blend (Exclusion)"),
    BLEND_SOURCE_OVER("Blend (Source Over)"),
    BLEND_HARD_LIGHT("Blend (Hard Light)"),
    BLEND_LIGHTEN("Blend (Lighten)"),
    BLEND_ADD("Blend (Add)"),
    BLEND_DIVIDE("Blend (Divide)"),
    BLEND_MULTIPLY("Blend (Multiply)"),
    BLEND_OVERLAY("Blend (Overlay)"),
    BLEND_SCREEN("Blend (Screen)"),
    BLEND_ALPHA("Blend (Alpha)"),
    BLEND_COLOR("Blend (Color)"),
    BLEND_HUE("Blend (Hue)"),
    BLEND_SATURATION("Blend (Saturation)"),
    BLEND_LUMINOSITY("Blend (Luminosity)"),
    BLEND_LINEAR_BURN("Blend (Linear Burn)"),
    BLEND_SOFT_LIGHT("Blend (Soft Light)"),
    BLEND_SUBTRACT("Blend (Subtract)"),
    BLEND_CHROMA_KEY("Blend (Chroma Key)"),
    BLEND_NORMAL("Blend (Normal)"),
    LOOKUP_AMATORKA("Lookup (Amatorka)"),
    GAUSSIAN_BLUR("Gaussian Blur"),
    CROSSHATCH("Crosshatch"),
    BOX_BLUR("Box Blur"),
    CGA_COLORSPACE("CGA Color Space"),
    DILATION("Dilation"),
    KUWAHARA("Kuwahara"),
    RGB_DILATION("RGB Dilation"),
    SKETCH("Sketch"),
    TOON("Toon"),
    SMOOTH_TOON("Smooth Toon"),
    BULGE_DISTORTION("Bulge Distortion"),
    GLASS_SPHERE("Glass Sphere"),
    HAZE("Haze"),
    LAPLACIAN("Laplacian"),
    NON_MAXIMUM_SUPPRESSION("Non Maximum Suppression"),
    SPHERE_REFRACTION("Sphere Refraction"),
    SWIRL("Swirl"),
    WEAK_PIXEL_INCLUSION("Weak Pixel Inclusion"),
    FALSE_COLOR("False Color"),
    COLOR_BALANCE("Color Balance"),
    LEVELS_FILTER_MIN("Levels Min (Mid Adjust)"),
    BILATERAL_BLUR("Bilateral Blur"),
    HALFTONE("Halftone"),
    TRANSFORM2D("Transform (2-D)"),
}

/**
 * 创建 GPU Image Filter
 * @return [GPUImageFilter]
 */
fun FilterType.createGPUImageFilter(): GPUImageFilter? {
    return when (this) {
        FilterType.TOON -> GPUImageToonFilter()
        FilterType.HAZE -> GPUImageHazeFilter()
        FilterType.SWIRL -> GPUImageSwirlFilter()
        FilterType.SKETCH -> GPUImageSketchFilter()
        FilterType.EMBOSS -> GPUImageEmbossFilter()
        FilterType.SEPIA -> GPUImageSepiaToneFilter()
        FilterType.BOX_BLUR -> GPUImageBoxBlurFilter()
        FilterType.DILATION -> GPUImageDilationFilter()
        FilterType.KUWAHARA -> GPUImageKuwaharaFilter()
        FilterType.HALFTONE -> GPUImageHalftoneFilter()
        FilterType.INVERT -> GPUImageColorInvertFilter()
        FilterType.POSTERIZE -> GPUImagePosterizeFilter()
        FilterType.GRAYSCALE -> GPUImageGrayscaleFilter()
        FilterType.LAPLACIAN -> GPUImageLaplacianFilter()
        FilterType.TONE_CURVE -> GPUImageToneCurveFilter()
        FilterType.CROSSHATCH -> GPUImageCrosshatchFilter()
        FilterType.PIXELATION -> GPUImagePixelationFilter()
        FilterType.TRANSFORM2D -> GPUImageTransformFilter()
        FilterType.FALSE_COLOR -> GPUImageFalseColorFilter()
        FilterType.SMOOTH_TOON -> GPUImageSmoothToonFilter()
        FilterType.LOOKUP_AMATORKA -> GPUImageLookupFilter()
        FilterType.RGB_DILATION -> GPUImageRGBDilationFilter()
        FilterType.GLASS_SPHERE -> GPUImageGlassSphereFilter()
        FilterType.GAUSSIAN_BLUR -> GPUImageGaussianBlurFilter()
        FilterType.COLOR_BALANCE -> GPUImageColorBalanceFilter()
        FilterType.CGA_COLORSPACE -> GPUImageCGAColorspaceFilter()
        FilterType.BILATERAL_BLUR -> GPUImageBilateralBlurFilter()
        FilterType.BULGE_DISTORTION -> GPUImageBulgeDistortionFilter()
        FilterType.SPHERE_REFRACTION -> GPUImageSphereRefractionFilter()
        FilterType.WEAK_PIXEL_INCLUSION -> GPUImageWeakPixelInclusionFilter()
        FilterType.SOBEL_EDGE_DETECTION -> GPUImageSobelEdgeDetectionFilter()
        FilterType.NON_MAXIMUM_SUPPRESSION -> GPUImageNonMaximumSuppressionFilter()

        FilterType.HUE -> GPUImageHueFilter(90.0F)
        FilterType.GAMMA -> GPUImageGammaFilter(2.0F)
        FilterType.OPACITY -> GPUImageOpacityFilter(1.0F)
        FilterType.CONTRAST -> GPUImageContrastFilter(2.0F)
        FilterType.EXPOSURE -> GPUImageExposureFilter(0.0F)
        FilterType.SATURATION -> GPUImageSaturationFilter(1.0F)
        FilterType.BRIGHTNESS -> GPUImageBrightnessFilter(1.5F)
        FilterType.RGB -> GPUImageRGBFilter(1.0F, 1.0F, 1.0F)
        FilterType.WHITE_BALANCE -> GPUImageWhiteBalanceFilter(5000.0F, 0.0F)
        FilterType.HIGHLIGHT_SHADOW -> GPUImageHighlightShadowFilter(0.0F, 1.0F)

        FilterType.MONOCHROME -> GPUImageMonochromeFilter(
            1.0F, floatArrayOf(0.6F, 0.45F, 0.3F, 1.0F)
        )

        FilterType.SHARPEN -> {
            GPUImageSharpenFilter().apply {
                setSharpness(2.0F)
            }
        }

        FilterType.LEVELS_FILTER_MIN -> {
            GPUImageLevelsFilter().apply {
                setMin(0.0F, 3.0F, 1.0F)
            }
        }

        FilterType.THREE_X_THREE_CONVOLUTION -> {
            GPUImage3x3ConvolutionFilter().apply {
                setConvolutionKernel(
                    floatArrayOf(
                        -1.0F, 0.0F, 1.0F,
                        -2.0F, 0.0F, 2.0F,
                        -1.0F, 0.0F, 1.0F
                    )
                )
            }
        }

        FilterType.VIGNETTE -> {
            val centerPoint = PointF()
            centerPoint.x = 0.5F
            centerPoint.y = 0.5F
            GPUImageVignetteFilter(
                centerPoint,
                floatArrayOf(0.0F, 0.0F, 0.0F),
                0.3F, 0.75F
            )
        }

        FilterType.FILTER_GROUP -> {
            val filters = LinkedList<GPUImageFilter>()
            filters.add(GPUImageContrastFilter())
            filters.add(GPUImageDirectionalSobelEdgeDetectionFilter())
            filters.add(GPUImageGrayscaleFilter())
            GPUImageFilterGroup(filters)
        }

        FilterType.BLEND_DIFFERENCE -> createBlendFilter(
            GPUImageDifferenceBlendFilter::class.java
        )

        FilterType.BLEND_SOURCE_OVER -> createBlendFilter(
            GPUImageSourceOverBlendFilter::class.java
        )

        FilterType.BLEND_COLOR_BURN -> createBlendFilter(
            GPUImageColorBurnBlendFilter::class.java
        )

        FilterType.BLEND_COLOR_DODGE -> createBlendFilter(
            GPUImageColorDodgeBlendFilter::class.java
        )

        FilterType.BLEND_DARKEN -> createBlendFilter(
            GPUImageDarkenBlendFilter::class.java
        )

        FilterType.BLEND_DISSOLVE -> createBlendFilter(
            GPUImageDissolveBlendFilter::class.java
        )

        FilterType.BLEND_EXCLUSION -> createBlendFilter(
            GPUImageExclusionBlendFilter::class.java
        )

        FilterType.BLEND_HARD_LIGHT -> createBlendFilter(
            GPUImageHardLightBlendFilter::class.java
        )

        FilterType.BLEND_LIGHTEN -> createBlendFilter(
            GPUImageLightenBlendFilter::class.java
        )

        FilterType.BLEND_ADD -> createBlendFilter(
            GPUImageAddBlendFilter::class.java
        )

        FilterType.BLEND_DIVIDE -> createBlendFilter(
            GPUImageDivideBlendFilter::class.java
        )

        FilterType.BLEND_MULTIPLY -> createBlendFilter(
            GPUImageMultiplyBlendFilter::class.java
        )

        FilterType.BLEND_OVERLAY -> createBlendFilter(
            GPUImageOverlayBlendFilter::class.java
        )

        FilterType.BLEND_SCREEN -> createBlendFilter(
            GPUImageScreenBlendFilter::class.java
        )

        FilterType.BLEND_ALPHA -> createBlendFilter(
            GPUImageAlphaBlendFilter::class.java
        )

        FilterType.BLEND_COLOR -> createBlendFilter(
            GPUImageColorBlendFilter::class.java
        )

        FilterType.BLEND_HUE -> createBlendFilter(
            GPUImageHueBlendFilter::class.java
        )

        FilterType.BLEND_SATURATION -> createBlendFilter(
            GPUImageSaturationBlendFilter::class.java
        )

        FilterType.BLEND_LUMINOSITY -> createBlendFilter(
            GPUImageLuminosityBlendFilter::class.java
        )

        FilterType.BLEND_LINEAR_BURN -> createBlendFilter(
            GPUImageLinearBurnBlendFilter::class.java
        )

        FilterType.BLEND_SOFT_LIGHT -> createBlendFilter(
            GPUImageSoftLightBlendFilter::class.java
        )

        FilterType.BLEND_SUBTRACT -> createBlendFilter(
            GPUImageSubtractBlendFilter::class.java
        )

        FilterType.BLEND_CHROMA_KEY -> createBlendFilter(
            GPUImageChromaKeyBlendFilter::class.java
        )

        FilterType.BLEND_NORMAL -> createBlendFilter(
            GPUImageNormalBlendFilter::class.java
        )

        else -> null
    }
}

/**
 * 创建内置混合滤镜效果
 * @param filterClass Class extends GPUImageTwoInputFilter
 * @return [GPUImageFilter]
 */
private fun createBlendFilter(filterClass: Class<out GPUImageTwoInputFilter>): GPUImageFilter? {
    try {
        return filterClass.newInstance()
    } catch (e: Exception) {
        GPUFilterItem.TAG.log_eTag(
            message = "createBlendFilter",
            throwable = e
        )
    }
    return null
}