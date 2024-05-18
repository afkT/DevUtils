package afkt.project.feature.ui_effect.gpu.bean

import android.graphics.PointF
import dev.expand.engine.log.log_eTag
import jp.co.cyberagent.android.gpuimage.filter.GPUImage3x3ConvolutionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAddBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAlphaBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCGAColorspaceFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageChromaKeyBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBalanceFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBurnBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorDodgeBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDarkenBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDifferenceBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDilationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDirectionalSobelEdgeDetectionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDissolveBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDivideBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExclusionBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFalseColorFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGammaFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGlassSphereFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHalftoneFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHardLightBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageKuwaharaFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLaplacianFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLevelsFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLightenBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLinearBurnBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLookupFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLuminosityBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMonochromeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMultiplyBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageNonMaximumSuppressionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageNormalBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOpacityFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOverlayBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBDilationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageScreenBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSmoothToonFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSobelEdgeDetectionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSoftLightBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSourceOverBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSphereRefractionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSubtractBlendFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTransformFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTwoInputFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWeakPixelInclusionFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter
import java.util.LinkedList

/**
 * detail: 滤镜类型 Item
 * @author Ttt
 */
class FilterItem(
    // 滤镜名
    val filterName: String,
    // 滤镜类型
    val filterType: FilterType
) {
    /**
     * 滤镜类型枚举类
     */
    enum class FilterType {
        CONTRAST,
        GRAYSCALE,
        SHARPEN,
        SEPIA,
        SOBEL_EDGE_DETECTION,
        THREE_X_THREE_CONVOLUTION,
        FILTER_GROUP,
        EMBOSS,
        POSTERIZE,
        GAMMA,
        BRIGHTNESS,
        INVERT,
        HUE,
        PIXELATION,
        SATURATION,
        EXPOSURE,
        HIGHLIGHT_SHADOW,
        MONOCHROME,
        OPACITY,
        RGB,
        WHITE_BALANCE,
        VIGNETTE,
        TONE_CURVE,
        BLEND_COLOR_BURN,
        BLEND_COLOR_DODGE,
        BLEND_DARKEN,
        BLEND_DIFFERENCE,
        BLEND_DISSOLVE,
        BLEND_EXCLUSION,
        BLEND_SOURCE_OVER,
        BLEND_HARD_LIGHT,
        BLEND_LIGHTEN,
        BLEND_ADD,
        BLEND_DIVIDE,
        BLEND_MULTIPLY,
        BLEND_OVERLAY,
        BLEND_SCREEN,
        BLEND_ALPHA,
        BLEND_COLOR,
        BLEND_HUE,
        BLEND_SATURATION,
        BLEND_LUMINOSITY,
        BLEND_LINEAR_BURN,
        BLEND_SOFT_LIGHT,
        BLEND_SUBTRACT,
        BLEND_CHROMA_KEY,
        BLEND_NORMAL,
        LOOKUP_AMATORKA,
        GAUSSIAN_BLUR,
        CROSSHATCH,
        BOX_BLUR,
        CGA_COLORSPACE,
        DILATION,
        KUWAHARA,
        RGB_DILATION,
        SKETCH,
        TOON,
        SMOOTH_TOON,
        BULGE_DISTORTION,
        GLASS_SPHERE,
        HAZE,
        LAPLACIAN,
        NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION,
        SWIRL,
        WEAK_PIXEL_INCLUSION,
        FALSE_COLOR,
        COLOR_BALANCE,
        LEVELS_FILTER_MIN,
        BILATERAL_BLUR,
        HALFTONE,
        TRANSFORM2D
    }

    companion object {

        // 日志 TAG
        private val TAG = FilterItem::class.java.simpleName

        // 滤镜类型集合
        var FILTER_LISTS = mutableListOf<FilterItem>()

        /**
         * 创建 GPU Image Filter
         * @param type 滤镜类型
         * @return [GPUImageFilter]
         */
        @JvmStatic
        fun createFilterForType(type: FilterType?): GPUImageFilter? {
            return when (type) {
                FilterType.CONTRAST -> GPUImageContrastFilter(2.0F)
                FilterType.GAMMA -> GPUImageGammaFilter(2.0F)
                FilterType.INVERT -> GPUImageColorInvertFilter()
                FilterType.PIXELATION -> GPUImagePixelationFilter()
                FilterType.HUE -> GPUImageHueFilter(90.0F)
                FilterType.BRIGHTNESS -> GPUImageBrightnessFilter(1.5F)
                FilterType.GRAYSCALE -> GPUImageGrayscaleFilter()
                FilterType.SEPIA -> GPUImageSepiaToneFilter()
                FilterType.SHARPEN -> {
                    val sharpness = GPUImageSharpenFilter()
                    sharpness.setSharpness(2.0F)
                    sharpness
                }

                FilterType.SOBEL_EDGE_DETECTION -> GPUImageSobelEdgeDetectionFilter()
                FilterType.THREE_X_THREE_CONVOLUTION -> {
                    val convolution = GPUImage3x3ConvolutionFilter()
                    convolution.setConvolutionKernel(
                        floatArrayOf(
                            -1.0F, 0.0F, 1.0F,
                            -2.0F, 0.0F, 2.0F,
                            -1.0F, 0.0F, 1.0F
                        )
                    )
                    convolution
                }

                FilterType.EMBOSS -> GPUImageEmbossFilter()
                FilterType.POSTERIZE -> GPUImagePosterizeFilter()
                FilterType.FILTER_GROUP -> {
                    val filters = LinkedList<GPUImageFilter>()
                    filters.add(GPUImageContrastFilter())
                    filters.add(GPUImageDirectionalSobelEdgeDetectionFilter())
                    filters.add(GPUImageGrayscaleFilter())
                    GPUImageFilterGroup(filters)
                }

                FilterType.SATURATION -> GPUImageSaturationFilter(1.0F)
                FilterType.EXPOSURE -> GPUImageExposureFilter(0.0F)
                FilterType.HIGHLIGHT_SHADOW -> GPUImageHighlightShadowFilter(0.0F, 1.0F)
                FilterType.MONOCHROME -> GPUImageMonochromeFilter(
                    1.0F,
                    floatArrayOf(0.6F, 0.45F, 0.3F, 1.0F)
                )

                FilterType.OPACITY -> GPUImageOpacityFilter(1.0F)
                FilterType.RGB -> GPUImageRGBFilter(1.0F, 1.0F, 1.0F)
                FilterType.WHITE_BALANCE -> GPUImageWhiteBalanceFilter(5000.0F, 0.0F)
                FilterType.VIGNETTE -> {
                    val centerPoint = PointF()
                    centerPoint.x = 0.5F
                    centerPoint.y = 0.5F
                    GPUImageVignetteFilter(centerPoint, floatArrayOf(0.0F, 0.0F, 0.0F), 0.3F, 0.75F)
                }

                FilterType.TONE_CURVE -> GPUImageToneCurveFilter()
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

                FilterType.LOOKUP_AMATORKA -> GPUImageLookupFilter()
                FilterType.GAUSSIAN_BLUR -> GPUImageGaussianBlurFilter()
                FilterType.CROSSHATCH -> GPUImageCrosshatchFilter()
                FilterType.BOX_BLUR -> GPUImageBoxBlurFilter()
                FilterType.CGA_COLORSPACE -> GPUImageCGAColorspaceFilter()
                FilterType.DILATION -> GPUImageDilationFilter()
                FilterType.KUWAHARA -> GPUImageKuwaharaFilter()
                FilterType.RGB_DILATION -> GPUImageRGBDilationFilter()
                FilterType.SKETCH -> GPUImageSketchFilter()
                FilterType.TOON -> GPUImageToonFilter()
                FilterType.SMOOTH_TOON -> GPUImageSmoothToonFilter()
                FilterType.BULGE_DISTORTION -> GPUImageBulgeDistortionFilter()
                FilterType.GLASS_SPHERE -> GPUImageGlassSphereFilter()
                FilterType.HAZE -> GPUImageHazeFilter()
                FilterType.LAPLACIAN -> GPUImageLaplacianFilter()
                FilterType.NON_MAXIMUM_SUPPRESSION -> GPUImageNonMaximumSuppressionFilter()
                FilterType.SPHERE_REFRACTION -> GPUImageSphereRefractionFilter()
                FilterType.SWIRL -> GPUImageSwirlFilter()
                FilterType.WEAK_PIXEL_INCLUSION -> GPUImageWeakPixelInclusionFilter()
                FilterType.FALSE_COLOR -> GPUImageFalseColorFilter()
                FilterType.COLOR_BALANCE -> GPUImageColorBalanceFilter()
                FilterType.LEVELS_FILTER_MIN -> {
                    val levelsFilter = GPUImageLevelsFilter()
                    levelsFilter.setMin(0.0F, 3.0F, 1.0F)
                    levelsFilter
                }

                FilterType.HALFTONE -> GPUImageHalftoneFilter()
                FilterType.BILATERAL_BLUR -> GPUImageBilateralBlurFilter()
                FilterType.TRANSFORM2D -> GPUImageTransformFilter()
                else -> throw IllegalStateException("No filter of that type!")
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
                TAG.log_eTag(
                    throwable = e,
                    message = "createBlendFilter"
                )
            }
            return null
        }

        init {
            FILTER_LISTS.add(FilterItem("Contrast", FilterType.CONTRAST))
            FILTER_LISTS.add(FilterItem("Invert", FilterType.INVERT))
            FILTER_LISTS.add(FilterItem("Pixelation", FilterType.PIXELATION))
            FILTER_LISTS.add(FilterItem("Hue", FilterType.HUE))
            FILTER_LISTS.add(FilterItem("Gamma", FilterType.GAMMA))
            FILTER_LISTS.add(FilterItem("Brightness", FilterType.BRIGHTNESS))
            FILTER_LISTS.add(FilterItem("Sepia", FilterType.SEPIA))
            FILTER_LISTS.add(FilterItem("Grayscale", FilterType.GRAYSCALE))
            FILTER_LISTS.add(FilterItem("Sharpness", FilterType.SHARPEN))
            FILTER_LISTS.add(FilterItem("Sobel Edge Detection", FilterType.SOBEL_EDGE_DETECTION))
            FILTER_LISTS.add(FilterItem("3x3 Convolution", FilterType.THREE_X_THREE_CONVOLUTION))
            FILTER_LISTS.add(FilterItem("Emboss", FilterType.EMBOSS))
            FILTER_LISTS.add(FilterItem("Posterize", FilterType.POSTERIZE))
            FILTER_LISTS.add(FilterItem("Grouped filters", FilterType.FILTER_GROUP))
            FILTER_LISTS.add(FilterItem("Saturation", FilterType.SATURATION))
            FILTER_LISTS.add(FilterItem("Exposure", FilterType.EXPOSURE))
            FILTER_LISTS.add(FilterItem("Highlight Shadow", FilterType.HIGHLIGHT_SHADOW))
            FILTER_LISTS.add(FilterItem("Monochrome", FilterType.MONOCHROME))
            FILTER_LISTS.add(FilterItem("Opacity", FilterType.OPACITY))
            FILTER_LISTS.add(FilterItem("RGB", FilterType.RGB))
            FILTER_LISTS.add(FilterItem("White Balance", FilterType.WHITE_BALANCE))
            FILTER_LISTS.add(FilterItem("Vignette", FilterType.VIGNETTE))
            FILTER_LISTS.add(FilterItem("ToneCurve", FilterType.TONE_CURVE))
            FILTER_LISTS.add(FilterItem("Blend (Difference)", FilterType.BLEND_DIFFERENCE))
            FILTER_LISTS.add(FilterItem("Blend (Source Over)", FilterType.BLEND_SOURCE_OVER))
            FILTER_LISTS.add(FilterItem("Blend (Color Burn)", FilterType.BLEND_COLOR_BURN))
            FILTER_LISTS.add(FilterItem("Blend (Color Dodge)", FilterType.BLEND_COLOR_DODGE))
            FILTER_LISTS.add(FilterItem("Blend (Darken)", FilterType.BLEND_DARKEN))
            FILTER_LISTS.add(FilterItem("Blend (Dissolve)", FilterType.BLEND_DISSOLVE))
            FILTER_LISTS.add(FilterItem("Blend (Exclusion)", FilterType.BLEND_EXCLUSION))
            FILTER_LISTS.add(FilterItem("Blend (Hard Light)", FilterType.BLEND_HARD_LIGHT))
            FILTER_LISTS.add(FilterItem("Blend (Lighten)", FilterType.BLEND_LIGHTEN))
            FILTER_LISTS.add(FilterItem("Blend (Add)", FilterType.BLEND_ADD))
            FILTER_LISTS.add(FilterItem("Blend (Divide)", FilterType.BLEND_DIVIDE))
            FILTER_LISTS.add(FilterItem("Blend (Multiply)", FilterType.BLEND_MULTIPLY))
            FILTER_LISTS.add(FilterItem("Blend (Overlay)", FilterType.BLEND_OVERLAY))
            FILTER_LISTS.add(FilterItem("Blend (Screen)", FilterType.BLEND_SCREEN))
            FILTER_LISTS.add(FilterItem("Blend (Alpha)", FilterType.BLEND_ALPHA))
            FILTER_LISTS.add(FilterItem("Blend (Color)", FilterType.BLEND_COLOR))
            FILTER_LISTS.add(FilterItem("Blend (Hue)", FilterType.BLEND_HUE))
            FILTER_LISTS.add(FilterItem("Blend (Saturation)", FilterType.BLEND_SATURATION))
            FILTER_LISTS.add(FilterItem("Blend (Luminosity)", FilterType.BLEND_LUMINOSITY))
            FILTER_LISTS.add(FilterItem("Blend (Linear Burn)", FilterType.BLEND_LINEAR_BURN))
            FILTER_LISTS.add(FilterItem("Blend (Soft Light)", FilterType.BLEND_SOFT_LIGHT))
            FILTER_LISTS.add(FilterItem("Blend (Subtract)", FilterType.BLEND_SUBTRACT))
            FILTER_LISTS.add(FilterItem("Blend (Chroma Key)", FilterType.BLEND_CHROMA_KEY))
            FILTER_LISTS.add(FilterItem("Blend (Normal)", FilterType.BLEND_NORMAL))
            FILTER_LISTS.add(FilterItem("Lookup (Amatorka)", FilterType.LOOKUP_AMATORKA))
            FILTER_LISTS.add(FilterItem("Gaussian Blur", FilterType.GAUSSIAN_BLUR))
            FILTER_LISTS.add(FilterItem("Crosshatch", FilterType.CROSSHATCH))
            FILTER_LISTS.add(FilterItem("Box Blur", FilterType.BOX_BLUR))
            FILTER_LISTS.add(FilterItem("CGA Color Space", FilterType.CGA_COLORSPACE))
            FILTER_LISTS.add(FilterItem("Dilation", FilterType.DILATION))
            FILTER_LISTS.add(FilterItem("Kuwahara", FilterType.KUWAHARA))
            FILTER_LISTS.add(FilterItem("RGB Dilation", FilterType.RGB_DILATION))
            FILTER_LISTS.add(FilterItem("Sketch", FilterType.SKETCH))
            FILTER_LISTS.add(FilterItem("Toon", FilterType.TOON))
            FILTER_LISTS.add(FilterItem("Smooth Toon", FilterType.SMOOTH_TOON))
            FILTER_LISTS.add(FilterItem("Halftone", FilterType.HALFTONE))
            FILTER_LISTS.add(FilterItem("Bulge Distortion", FilterType.BULGE_DISTORTION))
            FILTER_LISTS.add(FilterItem("Glass Sphere", FilterType.GLASS_SPHERE))
            FILTER_LISTS.add(FilterItem("Haze", FilterType.HAZE))
            FILTER_LISTS.add(FilterItem("Laplacian", FilterType.LAPLACIAN))
            FILTER_LISTS.add(
                FilterItem(
                    "Non Maximum Suppression",
                    FilterType.NON_MAXIMUM_SUPPRESSION
                )
            )
            FILTER_LISTS.add(FilterItem("Sphere Refraction", FilterType.SPHERE_REFRACTION))
            FILTER_LISTS.add(FilterItem("Swirl", FilterType.SWIRL))
            FILTER_LISTS.add(FilterItem("Weak Pixel Inclusion", FilterType.WEAK_PIXEL_INCLUSION))
            FILTER_LISTS.add(FilterItem("False Color", FilterType.FALSE_COLOR))
            FILTER_LISTS.add(FilterItem("Color Balance", FilterType.COLOR_BALANCE))
            FILTER_LISTS.add(FilterItem("Levels Min (Mid Adjust)", FilterType.LEVELS_FILTER_MIN))
            FILTER_LISTS.add(FilterItem("Bilateral Blur", FilterType.BILATERAL_BLUR))
            FILTER_LISTS.add(FilterItem("Transform (2-D)", FilterType.TRANSFORM2D))
        }
    }
}