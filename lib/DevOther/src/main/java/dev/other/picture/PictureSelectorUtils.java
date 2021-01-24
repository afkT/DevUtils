package dev.other.picture;

import android.content.Intent;
import android.text.TextUtils;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 * <pre>
 *     功能配置文档
 *     @see <a href="https://github.com/LuckSiege/PictureSelector"/>
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *     <uses-permission android:name="android.permission.CAMERA" />
 * </pre>
 */
public final class PictureSelectorUtils {

    private PictureSelectorUtils() {
    }

    // 日志 TAG
    private static final String    TAG                   = PictureSelectorUtils.class.getSimpleName();
    // 全局请求跳转回传 code
    public static final  int       PIC_REQUEST_CODE      = PictureConfig.CHOOSE_REQUEST;
    // 全局相册配置
    public static final  PicConfig PIC_CONFIG            = new PicConfig();
    // 拍照保存地址
    private static       String    CAMERA_SAVE_PATH      = null;
    // 压缩图片保存地址
    private static       String    COMPRESS_SAVE_PATH    = null;
    // 图片大于多少才进行压缩 (kb)
    private static       int       MINIMUM_COMPRESS_SIZE = 2048;

    /**
     * 获取全局相册配置
     * @return {@link PicConfig}
     */
    public static PicConfig getPicConfig() {
        return PIC_CONFIG;
    }

    /**
     * 设置全局相册配置
     * @param picConfig 新的相册配置信息
     */
    public static void setPicConfig(final PicConfig picConfig) {
        PIC_CONFIG.set(picConfig);
    }

    /**
     * 获取拍照保存地址
     * @return 拍照保存地址
     */
    public static String getCameraSavePath() {
        return CAMERA_SAVE_PATH;
    }

    /**
     * 获取压缩图片保存地址
     * @return 压缩图片保存地址
     */
    public static String getCompressSavePath() {
        return COMPRESS_SAVE_PATH;
    }

    /**
     * 设置保存地址
     * @param cameraSavePath   拍照保存地址
     * @param compressSavePath 压缩图片保存地址
     */
    public static void setSavePath(
            final String cameraSavePath,
            final String compressSavePath
    ) {
        CAMERA_SAVE_PATH = cameraSavePath;
        COMPRESS_SAVE_PATH = compressSavePath;
    }

    /**
     * 获取图片大于多少才进行压缩 (kb)
     * @return 最小压缩大小
     */
    public static int getMinimumCompressSize() {
        return MINIMUM_COMPRESS_SIZE;
    }

    /**
     * 设置图片大于多少才进行压缩 (kb)
     * @param minimumCompressSize 最小压缩大小
     */
    public static void setMinimumCompressSize(final int minimumCompressSize) {
        MINIMUM_COMPRESS_SIZE = minimumCompressSize;
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 清空缓存
     * <pre>
     *     包括裁剪和压缩后的缓存, 要在上传成功后调用, 注意: 需要系统 SDCard 权限
     * </pre>
     * @param type {@link PictureMimeType#ofImage()} or {@link PictureMimeType#ofVideo()}
     */
    public static void deleteCacheDirFile(final int type) {
        try {
            PictureFileUtils.deleteCacheDirFile(DevUtils.getContext(), type);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile");
        }
    }

    /**
     * 获取选中的资源集合
     * <pre>
     *     图片、视频、音频选择结果回调
     *     List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
     *     <p></p>
     *     LocalMedia 里面返回三种 path
     *     1.media.getPath() 为原图 path
     *     2.media.getCutPath() 为裁剪后 path 需判断 media.isCut() 是否为 true  注意: 音视频除外
     *     3.media.getCompressPath() 为压缩后 path 需判断 media.isCompressed() 是否为 true  注意: 音视频除外
     *     如果裁剪并压缩了, 以取压缩路径为准, 因为是先裁剪后压缩的
     * </pre>
     * @param data {@link Intent}
     * @return {@link List<LocalMedia>}
     */
    public static List<LocalMedia> getLocalMedias(final Intent data) {
        return (data != null) ? PictureSelector.obtainMultipleResult(data) : null;
    }

    /**
     * 获取单独选中的资源
     * @param data {@link Intent}
     * @return {@link LocalMedia}
     */
    public static LocalMedia getSingleMedia(final Intent data) {
        List<LocalMedia> result = getLocalMedias(data);
        return (result.size() != 0) ? result.get(0) : null;
    }

    /**
     * 获取本地资源路径
     * @param data {@link Intent}
     * @return 本地资源路径
     */
    public static String getLocalMediaPath(final Intent data) {
        return getLocalMediaPath(getSingleMedia(data), false);
    }

    /**
     * 获取本地资源路径
     * @param localMedia {@link LocalMedia}
     * @return 本地资源路径
     */
    public static String getLocalMediaPath(final LocalMedia localMedia) {
        return getLocalMediaPath(localMedia, false);
    }

    /**
     * 获取本地资源路径
     * @param localMedia {@link LocalMedia}
     * @param original   是否使用原图地址
     * @return 本地资源路径
     */
    public static String getLocalMediaPath(
            final LocalMedia localMedia,
            final boolean original
    ) {
        if (localMedia != null) {
            if (original) return localMedia.getPath();
            // 判断资源类型
            String mimeType = localMedia.getMimeType();
            if (PictureMimeType.isHasImage(mimeType)) { // 图片
                if (localMedia.isCompressed()) { // 是否压缩图片
                    return localMedia.getCompressPath();
                } else if (localMedia.isCut()) { // 是否裁减图片
                    return localMedia.getCutPath();
                } else { // 获取原图
                    return localMedia.getPath();
                }
            } else {
                return localMedia.getPath();
            }
        }
        return null;
    }

    /**
     * 获取本地资源地址集合
     * @param data {@link Intent}
     * @return {@link List}
     */
    public static List<String> getLocalMediaPaths(final Intent data) {
        return getLocalMediaPaths(data, false);
    }

    /**
     * 获取本地资源地址集合
     * @param data     {@link Intent}
     * @param original 是否使用原图地址
     * @return {@link List}
     */
    public static List<String> getLocalMediaPaths(
            final Intent data,
            final boolean original
    ) {
        List<String>     lists  = new ArrayList<>();
        List<LocalMedia> result = getLocalMedias(data);
        if (result != null) {
            for (LocalMedia localMedia : result) {
                String path = getLocalMediaPath(localMedia, original);
                lists.add(path);
            }
        }
        return lists;
    }

    // =

    /**
     * 获取图片选择配置模型
     * <pre>
     *     // 结果回调 onActivityResult requestCode
     *     pictureSelectionModel.forResult(requestCode);
     * </pre>
     * @param pictureSelector {@link PictureSelector}
     * @param picConfig       {@link PicConfig}
     * @param isCamera        是否拍照
     * @return {@link PictureSelectionModel}
     */
    public static PictureSelectionModel getPictureSelectionModel(
            final PictureSelector pictureSelector,
            final PicConfig picConfig,
            final boolean isCamera
    ) {
        if (pictureSelector != null && picConfig != null) {
            // 图片选择配置模型
            PictureSelectionModel pictureSelectionModel;
            // 相册选择类型
            if (isCamera) {
                pictureSelectionModel = pictureSelector.openCamera(picConfig.mimeType);
            } else {
                pictureSelectionModel = pictureSelector.openGallery(picConfig.mimeType);
            }

            // 是否裁减
            boolean isCrop = picConfig.isCrop;
            // 是否圆形裁减
            boolean isCircleCrop = picConfig.isCircleCrop;

            // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            pictureSelectionModel.selectionMode(picConfig.selectionMode)
                    .imageEngine(GlideEngine.createGlideEngine())
                    .isPreviewImage(true) // 是否可预览图片 true or false
                    .isPreviewVideo(true) // 是否可以预览视频 true or false
                    .isEnablePreviewAudio(true) // 是否可播放音频 true or false
                    .isZoomAnim(true) // 图片列表点击 缩放效果 默认 true
                    .isPreviewEggs(true) // 预览图片时是否增强左右滑动图片体验 ( 图片滑动一半即可看到上一张是否选中 ) true or false
                    .imageSpanCount(picConfig.imageSpanCount)// 每行显示个数 int
                    .minSelectNum(picConfig.minSelectNum) // 最小选择数量 int
                    .maxSelectNum(picConfig.maxSelectNum) // 最大图片选择数量 int
                    .isCamera(picConfig.isCamera) // 是否显示拍照按钮 true or false
                    .isGif(picConfig.isGif) // 是否显示 Gif true or false
                    // = 压缩相关 =
                    .isCompress(picConfig.isCompress) // 是否压缩 true or false
                    .minimumCompressSize(picConfig.minimumCompressSize) // 小于 xxkb 的图片不压缩
                    .withAspectRatio(picConfig.withAspectRatio[0], picConfig.withAspectRatio[1]) // 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                    // = 裁减相关 =
                    // 判断是否显示圆形裁减
                    .circleDimmedLayer(isCircleCrop)
                    // = 裁减配置 =
                    .isEnableCrop(isCrop) // 是否裁剪 true or false
                    .freeStyleCropEnabled(isCrop) // 裁剪框是否可拖拽 true or false
                    .showCropFrame(!isCircleCrop && isCrop) // 是否显示裁剪矩形边框 圆形裁剪时建议设为 false
                    .showCropGrid(!isCircleCrop && isCrop) // 是否显示裁剪矩形网格 圆形裁剪时建议设为 false
                    .rotateEnabled(isCrop) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(isCrop); // 裁剪是否可放大缩小图片 true or false

            // 设置拍照保存地址
            if (!TextUtils.isEmpty(picConfig.cameraSavePath)) {
                pictureSelectionModel.setOutputCameraPath(picConfig.cameraSavePath);
            }
            // 设置压缩图片保存地址
            if (!TextUtils.isEmpty(picConfig.compressSavePath)) {
                pictureSelectionModel.compressSavePath(picConfig.compressSavePath);
            }
            // 判断是否存在选中资源
            if (picConfig.localMedia != null && picConfig.localMedia.size() != 0) {
                pictureSelectionModel.selectionMedia(picConfig.localMedia);
            }
            return pictureSelectionModel;
        }
        return null;
    }

//    /**
//     * 获取图片选择器对象
//     * @param activity    {@link Activity}
//     * @param fragment    {@link Fragment}
//     * @return {@link PictureSelector}
//     */
//    private static PictureSelector getPictureSelector(final Activity activity, final Fragment fragment) {
//        if (activity != null) {
//            return PictureSelector.create(activity);
//        } else if (fragment != null) {
//            return PictureSelector.create(fragment);
//        }
//        return null;
//    }

    // ===========
    // = 调用方法 =
    // ===========

    /**
     * 打开相册拍照
     * @param pictureSelector {@link PictureSelector}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(final PictureSelector pictureSelector) {
        return openCamera(pictureSelector, PIC_CONFIG);
    }

    /**
     * 打开相册拍照
     * @param pictureSelector {@link PictureSelector}
     * @param picConfig       {@link PicConfig}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(
            final PictureSelector pictureSelector,
            final PicConfig picConfig
    ) {
        PictureSelectionModel pictureSelectionModel = getPictureSelectionModel(pictureSelector, picConfig, true);
        if (pictureSelectionModel != null) {
            pictureSelectionModel.forResult(PIC_REQUEST_CODE);
            return true;
        }
        return false;
    }

    /**
     * 打开相册选择
     * @param pictureSelector {@link PictureSelector}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(final PictureSelector pictureSelector) {
        return openGallery(pictureSelector, PIC_CONFIG);
    }

    /**
     * 打开相册选择
     * @param pictureSelector {@link PictureSelector}
     * @param picConfig       {@link PicConfig}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(
            final PictureSelector pictureSelector,
            final PicConfig picConfig
    ) {
        PictureSelectionModel pictureSelectionModel = getPictureSelectionModel(pictureSelector, picConfig, false);
        if (pictureSelectionModel != null) {
            pictureSelectionModel.forResult(PIC_REQUEST_CODE);
            return true;
        }
        return false;
    }

    // ========
    // = 配置 =
    // ========

    /**
     * detail: 相册配置
     * @author Ttt
     * <pre>
     *     重要: PictureSelector.create() 调用此方法时
     *     在 activity 中传 activity.this, 在 fragment 中请传 fragment.this,
     *     影响回调到哪个地方的 onActivityResult()
     * </pre>
     */
    public static class PicConfig {

        // 相册选择类型
        private int              mimeType            = PictureMimeType.ofImage();
        // 相册选择模式
        private int              selectionMode       = PictureConfig.MULTIPLE;
        // 是否显示拍照
        private boolean          isCamera            = true;
        // 是否裁减
        private boolean          isCrop              = false;
        // 是否圆形裁减 true = 圆形, false = 矩形
        private boolean          isCircleCrop        = false;
        // 是否压缩
        private boolean          isCompress          = true;
        // 图片大于多少才进行压缩 (kb)
        private int              minimumCompressSize = MINIMUM_COMPRESS_SIZE;
        // 裁减比例
        private int[]            withAspectRatio     = new int[]{0, 0};
        // 是否显示 Gif
        private boolean          isGif               = false;
        // 每行显示个数
        private int              imageSpanCount      = 4;
        // 最小选择数量
        private int              minSelectNum        = 1;
        // 最大选择数量
        private int              maxSelectNum        = 9;
        // 已选择的本地资源
        private List<LocalMedia> localMedia;
        // 拍照保存地址
        private String           cameraSavePath      = CAMERA_SAVE_PATH;
        // 压缩图片保存地址
        private String           compressSavePath    = COMPRESS_SAVE_PATH;

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取相册选择类型
         * @return 相册选择类型
         */
        public int getMimeType() {
            return mimeType;
        }

        /**
         * 设置相册选择类型
         * <pre>
         *     全部 PictureMimeType.ofAll() = 0
         *     图片 ofImage() = 1
         *     视频 ofVideo() = 2
         *     音频 ofAudio() = 3
         * </pre>
         * @param mimeType 相册选择类型
         * @return {@link PicConfig}
         */
        public PicConfig setMimeType(final int mimeType) {
            // 超过最大、最小值都默认为全部类型
            if (mimeType > PictureMimeType.ofAudio() || mimeType < PictureMimeType.ofAll()) {
                this.mimeType = PictureMimeType.ofAll();
            } else {
                this.mimeType = mimeType;
            }
            return this;
        }

        /**
         * 获取相册选择模式
         * @return 相册选择模式
         */
        public int getSelectionMode() {
            return selectionMode;
        }

        /**
         * 设置相册选择模式
         * <pre>
         *     多选 PictureConfig.MULTIPLE
         *     单选 PictureConfig.SINGLE
         * </pre>
         * @param selectionMode 相册选择模式
         * @return {@link PicConfig}
         */
        public PicConfig setSelectionMode(final int selectionMode) {
            if (selectionMode >= PictureConfig.MULTIPLE) {
                this.selectionMode = PictureConfig.MULTIPLE;
            } else if (selectionMode <= PictureConfig.SINGLE) {
                this.selectionMode = PictureConfig.SINGLE;
            }
            return this;
        }

        /**
         * 是否显示拍照
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCamera() {
            return isCamera;
        }

        /**
         * 设置是否显示拍照
         * @param camera {@code true} yes, {@code false} no
         * @return {@link PicConfig}
         */
        public PicConfig setCamera(final boolean camera) {
            isCamera = camera;
            return this;
        }

        /**
         * 是否裁减
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCrop() {
            return isCrop;
        }

        /**
         * 设置是否裁减
         * @param crop {@code true} yes, {@code false} no
         * @return {@link PicConfig}
         */
        public PicConfig setCrop(final boolean crop) {
            isCrop = crop;
            return this;
        }

        /**
         * 是否圆形裁减
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCircleCrop() {
            return isCircleCrop;
        }

        /**
         * 设置是否圆形裁减
         * @param circleCrop {@code true} yes, {@code false} no
         * @return {@link PicConfig}
         */
        public PicConfig setCircleCrop(final boolean circleCrop) {
            isCircleCrop = circleCrop;
            return this;
        }

        /**
         * 是否压缩
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCompress() {
            return isCompress;
        }

        /**
         * 设置是否压缩
         * @param compress {@code true} yes, {@code false} no
         * @return {@link PicConfig}
         */
        public PicConfig setCompress(final boolean compress) {
            isCompress = compress;
            return this;
        }

        /**
         * 获取图片大于多少才进行压缩
         * @return 最小压缩大小
         */
        public int getMinimumCompressSize() {
            return minimumCompressSize;
        }

        /**
         * 设置图片大于多少才进行压缩
         * @param minimumCompressSize 最小压缩大小
         * @return {@link PicConfig}
         */
        public PicConfig setMinimumCompressSize(final int minimumCompressSize) {
            this.minimumCompressSize = minimumCompressSize;
            return this;
        }

        /**
         * 获取裁减比例
         * @return int[] 0 = 宽比例, 1 = 高比例
         */
        public int[] getWithAspectRatio() {
            return withAspectRatio;
        }

        /**
         * 设置裁减比例
         * @param x 宽比例
         * @param y 高比例
         * @return {@link PicConfig}
         */
        public PicConfig setWithAspectRatio(
                final int x,
                final int y
        ) {
            this.withAspectRatio[0] = x;
            this.withAspectRatio[1] = y;
            return this;
        }

        /**
         * 是否显示 Gif
         * @return {@code true} yes, {@code false} no
         */
        public boolean isGif() {
            return isGif;
        }

        /**
         * 设置是否显示 Gif
         * @param gif {@code true} yes, {@code false} no
         * @return {@link PicConfig}
         */
        public PicConfig setGif(final boolean gif) {
            isGif = gif;
            return this;
        }

        /**
         * 获取每行显示个数
         * @return 每行显示个数
         */
        public int getImageSpanCount() {
            return imageSpanCount;
        }

        /**
         * 设置每行显示个数
         * @param imageSpanCount 每行显示个数
         * @return {@link PicConfig}
         */
        public PicConfig setImageSpanCount(final int imageSpanCount) {
            this.imageSpanCount = Math.max(imageSpanCount, 1);
            return this;
        }

        /**
         * 获取最小选择数量
         * @return 最小选择数量
         */
        public int getMinSelectNum() {
            return minSelectNum;
        }

        /**
         * 设置最小选择数量
         * @param minSelectNum 最小选择数量
         * @return {@link PicConfig}
         */
        public PicConfig setMinSelectNum(final int minSelectNum) {
            this.minSelectNum = minSelectNum;
            return this;
        }

        /**
         * 获取最大选择数量
         * @return 最大选择数量
         */
        public int getMaxSelectNum() {
            return maxSelectNum;
        }

        /**
         * 设置最大选择数量
         * @param maxSelectNum 最大选择数量
         * @return {@link PicConfig}
         */
        public PicConfig setMaxSelectNum(final int maxSelectNum) {
            this.maxSelectNum = maxSelectNum;
            return this;
        }

        /**
         * 获取已选择的本地资源
         * @return 已选择的本地资源 {@link List<LocalMedia>}
         */
        public List<LocalMedia> getLocalMedia() {
            return localMedia;
        }

        /**
         * 设置已选择的本地资源
         * @param localMedia {@link List<LocalMedia>}
         * @return {@link PicConfig}
         */
        public PicConfig setLocalMedia(final List<LocalMedia> localMedia) {
            this.localMedia = localMedia;
            return this;
        }

        /**
         * 获取拍照保存地址
         * @return 拍照保存地址
         */
        public String getCameraSavePath() {
            return cameraSavePath;
        }

        /**
         * 设置拍照保存地址
         * @param cameraSavePath 拍照保存地址
         * @return {@link PicConfig}
         */
        public PicConfig setCameraSavePath(final String cameraSavePath) {
            this.cameraSavePath = cameraSavePath;
            return this;
        }

        /**
         * 获取压缩图片保存地址
         * @return 压缩图片保存地址
         */
        public String getCompressSavePath() {
            return compressSavePath;
        }

        /**
         * 设置压缩图片保存地址
         * @param compressSavePath 压缩图片保存地址
         * @return {@link PicConfig}
         */
        public PicConfig setCompressSavePath(final String compressSavePath) {
            this.compressSavePath = compressSavePath;
            return this;
        }

        // =

        /**
         * 克隆新的相册配置
         * @return {@link PicConfig}
         */
        public PicConfig clone() {
            PicConfig picConfig = new PicConfig();
            picConfig.mimeType = mimeType;
            picConfig.selectionMode = selectionMode;
            picConfig.isCamera = isCamera;
            picConfig.isCrop = isCrop;
            picConfig.isCircleCrop = isCircleCrop;
            picConfig.isCompress = isCompress;
            picConfig.minimumCompressSize = minimumCompressSize;
            picConfig.withAspectRatio = withAspectRatio;
            picConfig.isGif = isGif;
            picConfig.imageSpanCount = imageSpanCount;
            picConfig.minSelectNum = minSelectNum;
            picConfig.maxSelectNum = maxSelectNum;
            picConfig.localMedia = localMedia;
            picConfig.cameraSavePath = cameraSavePath;
            picConfig.compressSavePath = compressSavePath;
            return picConfig;
        }

        /**
         * 设置新的相册配置
         * @param picConfig 新的相册配置信息
         * @return {@link PicConfig}
         */
        public PicConfig set(final PicConfig picConfig) {
            if (picConfig != null) {
                mimeType = picConfig.mimeType;
                selectionMode = picConfig.selectionMode;
                isCamera = picConfig.isCamera;
                isCrop = picConfig.isCrop;
                isCircleCrop = picConfig.isCircleCrop;
                isCompress = picConfig.isCompress;
                minimumCompressSize = picConfig.minimumCompressSize;
                withAspectRatio = picConfig.withAspectRatio;
                isGif = picConfig.isGif;
                imageSpanCount = picConfig.imageSpanCount;
                minSelectNum = picConfig.minSelectNum;
                maxSelectNum = picConfig.maxSelectNum;
                localMedia = picConfig.localMedia;
                cameraSavePath = picConfig.cameraSavePath;
                compressSavePath = picConfig.compressSavePath;
            }
            return this;
        }
    }
}