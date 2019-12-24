package afkt.project.model.item;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dev.utils.app.logger.DevLogger;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLuminosityBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNormalBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSourceOverBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSubtractBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * detail: 滤镜类型 Item
 * @author Ttt
 */
public class FilterItem {

    // 日志 TAG
    private static final String TAG = FilterItem.class.getSimpleName();

    // 滤镜类型集合
    public static List<FilterItem> FILTER_LISTS = new ArrayList<>();

    // 滤镜名
    public final String filterName;
    // 滤镜类型
    public final FilterType filterType;

    public FilterItem(String filterName, FilterType filterType) {
        this.filterName = filterName;
        this.filterType = filterType;
    }

    /**
     * 滤镜类型枚举类
     */
    public enum FilterType {
        CONTRAST, GRAYSCALE, SHARPEN, SEPIA, SOBEL_EDGE_DETECTION, THREE_X_THREE_CONVOLUTION, FILTER_GROUP, EMBOSS,
        POSTERIZE, GAMMA, BRIGHTNESS, INVERT, HUE, PIXELATION, SATURATION, EXPOSURE, HIGHLIGHT_SHADOW, MONOCHROME,
        OPACITY, RGB, WHITE_BALANCE, VIGNETTE, TONE_CURVE, BLEND_COLOR_BURN, BLEND_COLOR_DODGE, BLEND_DARKEN,
        BLEND_DIFFERENCE, BLEND_DISSOLVE, BLEND_EXCLUSION, BLEND_SOURCE_OVER, BLEND_HARD_LIGHT, BLEND_LIGHTEN,
        BLEND_ADD, BLEND_DIVIDE, BLEND_MULTIPLY, BLEND_OVERLAY, BLEND_SCREEN, BLEND_ALPHA, BLEND_COLOR, BLEND_HUE,
        BLEND_SATURATION, BLEND_LUMINOSITY, BLEND_LINEAR_BURN, BLEND_SOFT_LIGHT, BLEND_SUBTRACT, BLEND_CHROMA_KEY,
        BLEND_NORMAL, LOOKUP_AMATORKA, GAUSSIAN_BLUR, CROSSHATCH, BOX_BLUR, CGA_COLORSPACE, DILATION, KUWAHARA,
        RGB_DILATION, SKETCH, TOON, SMOOTH_TOON, BULGE_DISTORTION, GLASS_SPHERE, HAZE, LAPLACIAN, NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION, SWIRL, WEAK_PIXEL_INCLUSION, FALSE_COLOR, COLOR_BALANCE, LEVELS_FILTER_MIN, BILATERAL_BLUR, HALFTONE, TRANSFORM2D
    }

    static {
        FILTER_LISTS.add(new FilterItem("Contrast", FilterType.CONTRAST));
        FILTER_LISTS.add(new FilterItem("Invert", FilterType.INVERT));
        FILTER_LISTS.add(new FilterItem("Pixelation", FilterType.PIXELATION));
        FILTER_LISTS.add(new FilterItem("Hue", FilterType.HUE));
        FILTER_LISTS.add(new FilterItem("Gamma", FilterType.GAMMA));
        FILTER_LISTS.add(new FilterItem("Brightness", FilterType.BRIGHTNESS));
        FILTER_LISTS.add(new FilterItem("Sepia", FilterType.SEPIA));
        FILTER_LISTS.add(new FilterItem("Grayscale", FilterType.GRAYSCALE));
        FILTER_LISTS.add(new FilterItem("Sharpness", FilterType.SHARPEN));
        FILTER_LISTS.add(new FilterItem("Sobel Edge Detection", FilterType.SOBEL_EDGE_DETECTION));
        FILTER_LISTS.add(new FilterItem("3x3 Convolution", FilterType.THREE_X_THREE_CONVOLUTION));
        FILTER_LISTS.add(new FilterItem("Emboss", FilterType.EMBOSS));
        FILTER_LISTS.add(new FilterItem("Posterize", FilterType.POSTERIZE));
        FILTER_LISTS.add(new FilterItem("Grouped filters", FilterType.FILTER_GROUP));
        FILTER_LISTS.add(new FilterItem("Saturation", FilterType.SATURATION));
        FILTER_LISTS.add(new FilterItem("Exposure", FilterType.EXPOSURE));
        FILTER_LISTS.add(new FilterItem("Highlight Shadow", FilterType.HIGHLIGHT_SHADOW));
        FILTER_LISTS.add(new FilterItem("Monochrome", FilterType.MONOCHROME));
        FILTER_LISTS.add(new FilterItem("Opacity", FilterType.OPACITY));
        FILTER_LISTS.add(new FilterItem("RGB", FilterType.RGB));
        FILTER_LISTS.add(new FilterItem("White Balance", FilterType.WHITE_BALANCE));
        FILTER_LISTS.add(new FilterItem("Vignette", FilterType.VIGNETTE));
        FILTER_LISTS.add(new FilterItem("ToneCurve", FilterType.TONE_CURVE));
        FILTER_LISTS.add(new FilterItem("Blend (Difference)", FilterType.BLEND_DIFFERENCE));
        FILTER_LISTS.add(new FilterItem("Blend (Source Over)", FilterType.BLEND_SOURCE_OVER));
        FILTER_LISTS.add(new FilterItem("Blend (Color Burn)", FilterType.BLEND_COLOR_BURN));
        FILTER_LISTS.add(new FilterItem("Blend (Color Dodge)", FilterType.BLEND_COLOR_DODGE));
        FILTER_LISTS.add(new FilterItem("Blend (Darken)", FilterType.BLEND_DARKEN));
        FILTER_LISTS.add(new FilterItem("Blend (Dissolve)", FilterType.BLEND_DISSOLVE));
        FILTER_LISTS.add(new FilterItem("Blend (Exclusion)", FilterType.BLEND_EXCLUSION));
        FILTER_LISTS.add(new FilterItem("Blend (Hard Light)", FilterType.BLEND_HARD_LIGHT));
        FILTER_LISTS.add(new FilterItem("Blend (Lighten)", FilterType.BLEND_LIGHTEN));
        FILTER_LISTS.add(new FilterItem("Blend (Add)", FilterType.BLEND_ADD));
        FILTER_LISTS.add(new FilterItem("Blend (Divide)", FilterType.BLEND_DIVIDE));
        FILTER_LISTS.add(new FilterItem("Blend (Multiply)", FilterType.BLEND_MULTIPLY));
        FILTER_LISTS.add(new FilterItem("Blend (Overlay)", FilterType.BLEND_OVERLAY));
        FILTER_LISTS.add(new FilterItem("Blend (Screen)", FilterType.BLEND_SCREEN));
        FILTER_LISTS.add(new FilterItem("Blend (Alpha)", FilterType.BLEND_ALPHA));
        FILTER_LISTS.add(new FilterItem("Blend (Color)", FilterType.BLEND_COLOR));
        FILTER_LISTS.add(new FilterItem("Blend (Hue)", FilterType.BLEND_HUE));
        FILTER_LISTS.add(new FilterItem("Blend (Saturation)", FilterType.BLEND_SATURATION));
        FILTER_LISTS.add(new FilterItem("Blend (Luminosity)", FilterType.BLEND_LUMINOSITY));
        FILTER_LISTS.add(new FilterItem("Blend (Linear Burn)", FilterType.BLEND_LINEAR_BURN));
        FILTER_LISTS.add(new FilterItem("Blend (Soft Light)", FilterType.BLEND_SOFT_LIGHT));
        FILTER_LISTS.add(new FilterItem("Blend (Subtract)", FilterType.BLEND_SUBTRACT));
        FILTER_LISTS.add(new FilterItem("Blend (Chroma Key)", FilterType.BLEND_CHROMA_KEY));
        FILTER_LISTS.add(new FilterItem("Blend (Normal)", FilterType.BLEND_NORMAL));
        FILTER_LISTS.add(new FilterItem("Lookup (Amatorka)", FilterType.LOOKUP_AMATORKA));
        FILTER_LISTS.add(new FilterItem("Gaussian Blur", FilterType.GAUSSIAN_BLUR));
        FILTER_LISTS.add(new FilterItem("Crosshatch", FilterType.CROSSHATCH));
        FILTER_LISTS.add(new FilterItem("Box Blur", FilterType.BOX_BLUR));
        FILTER_LISTS.add(new FilterItem("CGA Color Space", FilterType.CGA_COLORSPACE));
        FILTER_LISTS.add(new FilterItem("Dilation", FilterType.DILATION));
        FILTER_LISTS.add(new FilterItem("Kuwahara", FilterType.KUWAHARA));
        FILTER_LISTS.add(new FilterItem("RGB Dilation", FilterType.RGB_DILATION));
        FILTER_LISTS.add(new FilterItem("Sketch", FilterType.SKETCH));
        FILTER_LISTS.add(new FilterItem("Toon", FilterType.TOON));
        FILTER_LISTS.add(new FilterItem("Smooth Toon", FilterType.SMOOTH_TOON));
        FILTER_LISTS.add(new FilterItem("Halftone", FilterType.HALFTONE));
        FILTER_LISTS.add(new FilterItem("Bulge Distortion", FilterType.BULGE_DISTORTION));
        FILTER_LISTS.add(new FilterItem("Glass Sphere", FilterType.GLASS_SPHERE));
        FILTER_LISTS.add(new FilterItem("Haze", FilterType.HAZE));
        FILTER_LISTS.add(new FilterItem("Laplacian", FilterType.LAPLACIAN));
        FILTER_LISTS.add(new FilterItem("Non Maximum Suppression", FilterType.NON_MAXIMUM_SUPPRESSION));
        FILTER_LISTS.add(new FilterItem("Sphere Refraction", FilterType.SPHERE_REFRACTION));
        FILTER_LISTS.add(new FilterItem("Swirl", FilterType.SWIRL));
        FILTER_LISTS.add(new FilterItem("Weak Pixel Inclusion", FilterType.WEAK_PIXEL_INCLUSION));
        FILTER_LISTS.add(new FilterItem("False Color", FilterType.FALSE_COLOR));
        FILTER_LISTS.add(new FilterItem("Color Balance", FilterType.COLOR_BALANCE));
        FILTER_LISTS.add(new FilterItem("Levels Min (Mid Adjust)", FilterType.LEVELS_FILTER_MIN));
        FILTER_LISTS.add(new FilterItem("Bilateral Blur", FilterType.BILATERAL_BLUR));
        FILTER_LISTS.add(new FilterItem("Transform (2-D)", FilterType.TRANSFORM2D));
    }

    /**
     * 创建 GPU Image Filter
     * @param type 滤镜类型
     * @return {@link GPUImageFilter}
     */
    public static GPUImageFilter createFilterForType(final FilterType type) {
        switch (type) {
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();
                sharpness.setSharpness(2.0f);
                return sharpness;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImage3x3ConvolutionFilter convolution = new GPUImage3x3ConvolutionFilter();
                convolution.setConvolutionKernel(new float[]{
                        -1.0f, 0.0f, 1.0f,
                        -2.0f, 0.0f, 2.0f,
                        -1.0f, 0.0f, 1.0f
                });
                return convolution;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                List<GPUImageFilter> filters = new LinkedList<>();
                filters.add(new GPUImageContrastFilter());
                filters.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                filters.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(filters);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[]{0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                return new GPUImageToneCurveFilter();
            case BLEND_DIFFERENCE:
                return createBlendFilter(GPUImageDifferenceBlendFilter.class);
            case BLEND_SOURCE_OVER:
                return createBlendFilter(GPUImageSourceOverBlendFilter.class);
            case BLEND_COLOR_BURN:
                return createBlendFilter(GPUImageColorBurnBlendFilter.class);
            case BLEND_COLOR_DODGE:
                return createBlendFilter(GPUImageColorDodgeBlendFilter.class);
            case BLEND_DARKEN:
                return createBlendFilter(GPUImageDarkenBlendFilter.class);
            case BLEND_DISSOLVE:
                return createBlendFilter(GPUImageDissolveBlendFilter.class);
            case BLEND_EXCLUSION:
                return createBlendFilter(GPUImageExclusionBlendFilter.class);
            case BLEND_HARD_LIGHT:
                return createBlendFilter(GPUImageHardLightBlendFilter.class);
            case BLEND_LIGHTEN:
                return createBlendFilter(GPUImageLightenBlendFilter.class);
            case BLEND_ADD:
                return createBlendFilter(GPUImageAddBlendFilter.class);
            case BLEND_DIVIDE:
                return createBlendFilter(GPUImageDivideBlendFilter.class);
            case BLEND_MULTIPLY:
                return createBlendFilter(GPUImageMultiplyBlendFilter.class);
            case BLEND_OVERLAY:
                return createBlendFilter(GPUImageOverlayBlendFilter.class);
            case BLEND_SCREEN:
                return createBlendFilter(GPUImageScreenBlendFilter.class);
            case BLEND_ALPHA:
                return createBlendFilter(GPUImageAlphaBlendFilter.class);
            case BLEND_COLOR:
                return createBlendFilter(GPUImageColorBlendFilter.class);
            case BLEND_HUE:
                return createBlendFilter(GPUImageHueBlendFilter.class);
            case BLEND_SATURATION:
                return createBlendFilter(GPUImageSaturationBlendFilter.class);
            case BLEND_LUMINOSITY:
                return createBlendFilter(GPUImageLuminosityBlendFilter.class);
            case BLEND_LINEAR_BURN:
                return createBlendFilter(GPUImageLinearBurnBlendFilter.class);
            case BLEND_SOFT_LIGHT:
                return createBlendFilter(GPUImageSoftLightBlendFilter.class);
            case BLEND_SUBTRACT:
                return createBlendFilter(GPUImageSubtractBlendFilter.class);
            case BLEND_CHROMA_KEY:
                return createBlendFilter(GPUImageChromaKeyBlendFilter.class);
            case BLEND_NORMAL:
                return createBlendFilter(GPUImageNormalBlendFilter.class);
            case LOOKUP_AMATORKA:
                return new GPUImageLookupFilter();
            case GAUSSIAN_BLUR:
                return new GPUImageGaussianBlurFilter();
            case CROSSHATCH:
                return new GPUImageCrosshatchFilter();
            case BOX_BLUR:
                return new GPUImageBoxBlurFilter();
            case CGA_COLORSPACE:
                return new GPUImageCGAColorspaceFilter();
            case DILATION:
                return new GPUImageDilationFilter();
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case RGB_DILATION:
                return new GPUImageRGBDilationFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case TOON:
                return new GPUImageToonFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();
            case BULGE_DISTORTION:
                return new GPUImageBulgeDistortionFilter();
            case GLASS_SPHERE:
                return new GPUImageGlassSphereFilter();
            case HAZE:
                return new GPUImageHazeFilter();
            case LAPLACIAN:
                return new GPUImageLaplacianFilter();
            case NON_MAXIMUM_SUPPRESSION:
                return new GPUImageNonMaximumSuppressionFilter();
            case SPHERE_REFRACTION:
                return new GPUImageSphereRefractionFilter();
            case SWIRL:
                return new GPUImageSwirlFilter();
            case WEAK_PIXEL_INCLUSION:
                return new GPUImageWeakPixelInclusionFilter();
            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            case COLOR_BALANCE:
                return new GPUImageColorBalanceFilter();
            case LEVELS_FILTER_MIN:
                GPUImageLevelsFilter levelsFilter = new GPUImageLevelsFilter();
                levelsFilter.setMin(0.0f, 3.0f, 1.0f);
                return levelsFilter;
            case HALFTONE:
                return new GPUImageHalftoneFilter();
            case BILATERAL_BLUR:
                return new GPUImageBilateralFilter();
            case TRANSFORM2D:
                return new GPUImageTransformFilter();
            default:
                throw new IllegalStateException("No filter of that type!");
        }
    }

    /**
     * 创建内置混合滤镜效果
     * @param filterClass Class extends GPUImageTwoInputFilter
     * @return {@link GPUImageFilter}
     */
    private static GPUImageFilter createBlendFilter(Class<? extends GPUImageTwoInputFilter> filterClass) {
        try {
            GPUImageTwoInputFilter filter = filterClass.newInstance();
            return filter;
        } catch (Exception e) {
            DevLogger.eTag(TAG, e, "createBlendFilter");
        }
        return null;
    }
}
