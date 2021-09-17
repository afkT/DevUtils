package java.dev.other;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.dev.engine.media.GlideEngine;
import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Android 平台下的图片选择器
 * @author Ttt
 * <pre>
 *     功能配置文档
 *     @see <a href="https://github.com/LuckSiege/PictureSelector"/>
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *     <uses-permission android:name="android.permission.CAMERA"/>
 *     <p></p>
 *     尽量不使用 isCompressed 压缩, 通过获取选中的路径后自行进行压缩
 *     防止需要适配 Android 11 ( R ) 进行转存文件需判断文件路径
 * </pre>
 */
public final class PictureSelectorUtils {

    private PictureSelectorUtils() {
    }

    // 日志 TAG
    private static final String      TAG              = PictureSelectorUtils.class.getSimpleName();
    // 全局请求跳转回传 code
    public static final  int         PIC_REQUEST_CODE = 159857;
    // 全局相册配置
    public static final  MediaConfig PIC_CONFIG       = new MediaConfig();

    /**
     * 获取全局相册配置
     * @return {@link MediaConfig}
     */
    public static MediaConfig getConfig() {
        return PIC_CONFIG;
    }

    /**
     * 设置全局相册配置
     * @param config 新的相册配置信息
     */
    public static void setConfig(final MediaConfig config) {
        PIC_CONFIG.set(config);
    }

    /**
     * 获取拍照存储地址
     * @return 拍照存储地址
     */
    public static String getCameraSavePath() {
        return PIC_CONFIG.getCameraSavePath();
    }

    /**
     * 获取压缩图片存储地址
     * @return 压缩图片存储地址
     */
    public static String getCompressSavePath() {
        return PIC_CONFIG.getCompressSavePath();
    }

    /**
     * 设置存储地址
     * @param cameraSavePath   拍照存储地址
     * @param compressSavePath 压缩图片存储地址
     */
    public static void setSavePath(
            final String cameraSavePath,
            final String compressSavePath
    ) {
        PIC_CONFIG.setCameraSavePath(cameraSavePath)
                .setCompressSavePath(compressSavePath);
    }

    /**
     * 获取图片大于多少才进行压缩 ( kb )
     * @return 最小压缩大小
     */
    public static int getMinimumCompressSize() {
        return PIC_CONFIG.getMinimumCompressSize();
    }

    /**
     * 设置图片大于多少才进行压缩 ( kb )
     * @param minimumCompressSize 最小压缩大小
     */
    public static void setMinimumCompressSize(final int minimumCompressSize) {
        PIC_CONFIG.setMinimumCompressSize(minimumCompressSize);
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 清空缓存
     * <pre>
     *     包括裁剪和压缩后的缓存, 要在上传成功后调用, 注意: 需要系统 SDCard 权限
     * </pre>
     * @param type {@link MediaConfig.MimeType#ofImage()} or {@link MediaConfig.MimeType#ofVideo()}
     */
    public static void deleteCacheDirFile(final int type) {
        try {
            PictureFileUtils.deleteCacheDirFile(DevUtils.getContext(), type);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile");
        }
    }

    /**
     * 清空全部缓存
     */
    public static void deleteAllCacheDirFile() {
        try {
            PictureFileUtils.deleteAllCacheDirFile(DevUtils.getContext());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteAllCacheDirFile");
        }
    }

    /**
     * 是否图片选择 ( onActivityResult )
     * @param requestCode 请求 code
     * @param resultCode  resultCode
     * @return {@code true} success, {@code false} fail
     */
    public static boolean isMediaSelectorResult(
            final int requestCode,
            final int resultCode
    ) {
        return requestCode == PIC_REQUEST_CODE && resultCode == Activity.RESULT_OK;
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
            if (StringUtils.isStartsWith(mimeType, DevFinal.IMAGE)) { // 图片
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
     * @param config          {@link MediaConfig}
     * @param isCamera        是否拍照
     * @return {@link PictureSelectionModel}
     */
    public static PictureSelectionModel getPictureSelectionModel(
            final PictureSelector pictureSelector,
            final MediaConfig config,
            final boolean isCamera
    ) {
        if (pictureSelector != null && config != null) {
            // 图片选择配置模型
            PictureSelectionModel pictureSelectionModel;
            // 相册选择类型
            if (isCamera) {
                pictureSelectionModel = pictureSelector.openCamera(config.getMimeType());
            } else {
                pictureSelectionModel = pictureSelector.openGallery(config.getMimeType());
            }

            // 是否裁减
            boolean isCrop = config.isCrop();
            // 是否圆形裁减
            boolean isCircleCrop = config.isCircleCrop();

            // 多选 or 单选 MediaConfig.MULTIPLE or MediaConfig.SINGLE
            pictureSelectionModel.selectionMode(config.getSelectionMode())
                    .imageEngine(GlideEngine.createGlideEngine())
                    .isPreviewImage(true) // 是否可预览图片 true or false
                    .isPreviewVideo(true) // 是否可以预览视频 true or false
                    .isEnablePreviewAudio(true) // 是否可播放音频 true or false
                    .isZoomAnim(true) // 图片列表点击 缩放效果 默认 true
                    .isPreviewEggs(true) // 预览图片时是否增强左右滑动图片体验 ( 图片滑动一半即可看到上一张是否选中 ) true or false
                    .imageSpanCount(config.getImageSpanCount())// 每行显示个数 int
                    .minSelectNum(config.getMinSelectNum()) // 最小选择数量 int
                    .maxSelectNum(config.getMaxSelectNum()) // 最大图片选择数量 int
                    .isCamera(config.isCamera()) // 是否显示拍照按钮 true or false
                    .isGif(config.isGif()) // 是否显示 Gif true or false
                    // = 压缩相关 =
                    .isCompress(config.isCompress()) // 是否压缩 true or false
                    .minimumCompressSize(config.getMinimumCompressSize()) // 小于 xxkb 的图片不压缩
                    .withAspectRatio(
                            config.getWithAspectRatio()[0],
                            config.getWithAspectRatio()[1]
                    ) // 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
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

            // 设置拍照存储地址
            if (!TextUtils.isEmpty(config.getCameraSavePath())) {
                pictureSelectionModel.setOutputCameraPath(config.getCameraSavePath());
            }
            // 设置压缩图片存储地址
            if (!TextUtils.isEmpty(config.getCompressSavePath())) {
                pictureSelectionModel.compressSavePath(config.getCompressSavePath());
            }
            // 判断是否存在选中资源
            if (config.getLocalMedia() != null && config.getLocalMedia().size() != 0) {
                pictureSelectionModel.selectionData(config.getLocalMedia());
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

    // ==========
    // = 调用方法 =
    // ==========

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
     * @param config          {@link MediaConfig}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openCamera(
            final PictureSelector pictureSelector,
            final MediaConfig config
    ) {
        PictureSelectionModel pictureSelectionModel = getPictureSelectionModel(pictureSelector, config, true);
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
     * @param config          {@link MediaConfig}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGallery(
            final PictureSelector pictureSelector,
            final MediaConfig config
    ) {
        PictureSelectionModel pictureSelectionModel = getPictureSelectionModel(pictureSelector, config, false);
        if (pictureSelectionModel != null) {
            pictureSelectionModel.forResult(PIC_REQUEST_CODE);
            return true;
        }
        return false;
    }

    // =======
    // = 配置 =
    // =======

    /**
     * detail: 相册配置
     * @author Ttt
     * <pre>
     *     重要: PictureSelector.create() 调用此方法时
     *     在 activity 中传 activity.this, 在 fragment 中请传 fragment.this,
     *     影响回调到哪个地方的 onActivityResult()
     * </pre>
     */
    public static class MediaConfig {

        // 相册选择类型
        private int              mMimeType            = MimeType.ofImage();
        // 相册选择模式
        private int              mSelectionMode       = MimeType.MULTIPLE;
        // 是否显示拍照
        private boolean          mIsCamera            = true;
        // 是否裁减
        private boolean          mIsCrop              = false;
        // 是否圆形裁减 true = 圆形, false = 矩形
        private boolean          mIsCircleCrop        = false;
        // 是否压缩
        private boolean          mIsCompress          = false;
        // 图片大于多少才进行压缩 ( kb )
        private int              mMinimumCompressSize = 2048;
        // 裁减比例
        private int[]            mWithAspectRatio     = new int[]{0, 0};
        // 是否显示 Gif
        private boolean          mIsGif               = false;
        // 每行显示个数
        private int              mImageSpanCount      = 4;
        // 最小选择数量
        private int              mMinSelectNum        = 1;
        // 最大选择数量
        private int              mMaxSelectNum        = 9;
        // 已选择的本地资源
        private List<LocalMedia> mLocalMedia;
        // 拍照存储地址
        private String           mCameraSavePath      = null;
        // 压缩图片存储地址
        private String           mCompressSavePath    = null;

        /**
         * detail: 选择模式
         * @author Ttt
         */
        public static class MimeType {

            public final static int SINGLE   = 1;
            public final static int MULTIPLE = 2;

            public final static int TYPE_ALL   = 0;
            public final static int TYPE_IMAGE = 1;
            public final static int TYPE_VIDEO = 2;
            public final static int TYPE_AUDIO = 3;

            public static int ofAll() {
                return TYPE_ALL;
            }

            public static int ofImage() {
                return TYPE_IMAGE;
            }

            public static int ofVideo() {
                return TYPE_VIDEO;
            }

            public static int ofAudio() {
                return TYPE_AUDIO;
            }
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取相册选择类型
         * @return 相册选择类型
         */
        public int getMimeType() {
            return mMimeType;
        }

        /**
         * 设置相册选择类型
         * <pre>
         *     全部 ofAll()   = 0
         *     图片 ofImage() = 1
         *     视频 ofVideo() = 2
         *     音频 ofAudio() = 3
         * </pre>
         * @param mimeType 相册选择类型
         * @return {@link MediaConfig}
         */
        public MediaConfig setMimeType(final int mimeType) {
            // 超过最大、最小值都默认为全部类型
            if (mimeType > MimeType.ofAudio() || mimeType < MimeType.ofAll()) {
                this.mMimeType = MimeType.ofAll();
            } else {
                this.mMimeType = mimeType;
            }
            return this;
        }

        /**
         * 获取相册选择模式
         * @return 相册选择模式
         */
        public int getSelectionMode() {
            return mSelectionMode;
        }

        /**
         * 设置相册选择模式
         * <pre>
         *     多选 MimeType.MULTIPLE
         *     单选 MimeType.SINGLE
         * </pre>
         * @param selectionMode 相册选择模式
         * @return {@link MediaConfig}
         */
        public MediaConfig setSelectionMode(final int selectionMode) {
            if (selectionMode >= MimeType.MULTIPLE) {
                this.mSelectionMode = MimeType.MULTIPLE;
            } else if (selectionMode <= MimeType.SINGLE) {
                this.mSelectionMode = MimeType.SINGLE;
            }
            return this;
        }

        /**
         * 是否显示拍照
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCamera() {
            return mIsCamera;
        }

        /**
         * 设置是否显示拍照
         * @param camera {@code true} yes, {@code false} no
         * @return {@link MediaConfig}
         */
        public MediaConfig setCamera(final boolean camera) {
            mIsCamera = camera;
            return this;
        }

        /**
         * 是否裁减
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCrop() {
            return mIsCrop;
        }

        /**
         * 设置是否裁减
         * @param crop {@code true} yes, {@code false} no
         * @return {@link MediaConfig}
         */
        public MediaConfig setCrop(final boolean crop) {
            mIsCrop = crop;
            return this;
        }

        /**
         * 是否圆形裁减
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCircleCrop() {
            return mIsCircleCrop;
        }

        /**
         * 设置是否圆形裁减
         * @param circleCrop {@code true} yes, {@code false} no
         * @return {@link MediaConfig}
         */
        public MediaConfig setCircleCrop(final boolean circleCrop) {
            mIsCircleCrop = circleCrop;
            return this;
        }

        /**
         * 是否压缩
         * @return {@code true} yes, {@code false} no
         */
        public boolean isCompress() {
            return mIsCompress;
        }

        /**
         * 设置是否压缩
         * @param compress {@code true} yes, {@code false} no
         * @return {@link MediaConfig}
         */
        public MediaConfig setCompress(final boolean compress) {
            mIsCompress = compress;
            return this;
        }

        /**
         * 获取图片大于多少才进行压缩
         * @return 最小压缩大小
         */
        public int getMinimumCompressSize() {
            return mMinimumCompressSize;
        }

        /**
         * 设置图片大于多少才进行压缩
         * @param minimumCompressSize 最小压缩大小
         * @return {@link MediaConfig}
         */
        public MediaConfig setMinimumCompressSize(final int minimumCompressSize) {
            this.mMinimumCompressSize = minimumCompressSize;
            return this;
        }

        /**
         * 获取裁减比例
         * @return int[] 0 = 宽比例, 1 = 高比例
         */
        public int[] getWithAspectRatio() {
            return mWithAspectRatio;
        }

        /**
         * 设置裁减比例
         * @param x 宽比例
         * @param y 高比例
         * @return {@link MediaConfig}
         */
        public MediaConfig setWithAspectRatio(
                final int x,
                final int y
        ) {
            this.mWithAspectRatio[0] = x;
            this.mWithAspectRatio[1] = y;
            return this;
        }

        /**
         * 是否显示 Gif
         * @return {@code true} yes, {@code false} no
         */
        public boolean isGif() {
            return mIsGif;
        }

        /**
         * 设置是否显示 Gif
         * @param gif {@code true} yes, {@code false} no
         * @return {@link MediaConfig}
         */
        public MediaConfig setGif(final boolean gif) {
            mIsGif = gif;
            return this;
        }

        /**
         * 获取每行显示个数
         * @return 每行显示个数
         */
        public int getImageSpanCount() {
            return mImageSpanCount;
        }

        /**
         * 设置每行显示个数
         * @param imageSpanCount 每行显示个数
         * @return {@link MediaConfig}
         */
        public MediaConfig setImageSpanCount(final int imageSpanCount) {
            this.mImageSpanCount = Math.max(imageSpanCount, 1);
            return this;
        }

        /**
         * 获取最小选择数量
         * @return 最小选择数量
         */
        public int getMinSelectNum() {
            return mMinSelectNum;
        }

        /**
         * 设置最小选择数量
         * @param minSelectNum 最小选择数量
         * @return {@link MediaConfig}
         */
        public MediaConfig setMinSelectNum(final int minSelectNum) {
            this.mMinSelectNum = minSelectNum;
            return this;
        }

        /**
         * 获取最大选择数量
         * @return 最大选择数量
         */
        public int getMaxSelectNum() {
            return mMaxSelectNum;
        }

        /**
         * 设置最大选择数量
         * @param maxSelectNum 最大选择数量
         * @return {@link MediaConfig}
         */
        public MediaConfig setMaxSelectNum(final int maxSelectNum) {
            this.mMaxSelectNum = maxSelectNum;
            return this;
        }

        /**
         * 获取已选择的本地资源
         * @return 已选择的本地资源 {@link List<LocalMedia>}
         */
        public List<LocalMedia> getLocalMedia() {
            return mLocalMedia;
        }

        /**
         * 设置已选择的本地资源
         * @param localMedia {@link List<LocalMedia>}
         * @return {@link MediaConfig}
         */
        public MediaConfig setLocalMedia(final List<LocalMedia> localMedia) {
            this.mLocalMedia = localMedia;
            return this;
        }

        /**
         * 获取拍照存储地址
         * @return 拍照存储地址
         */
        public String getCameraSavePath() {
            return mCameraSavePath;
        }

        /**
         * 设置拍照存储地址
         * @param cameraSavePath 拍照存储地址
         * @return {@link MediaConfig}
         */
        public MediaConfig setCameraSavePath(final String cameraSavePath) {
            this.mCameraSavePath = cameraSavePath;
            return this;
        }

        /**
         * 获取压缩图片存储地址
         * @return 压缩图片存储地址
         */
        public String getCompressSavePath() {
            return mCompressSavePath;
        }

        /**
         * 设置压缩图片存储地址
         * @param compressSavePath 压缩图片存储地址
         * @return {@link MediaConfig}
         */
        public MediaConfig setCompressSavePath(final String compressSavePath) {
            this.mCompressSavePath = compressSavePath;
            return this;
        }

        // =

        /**
         * 克隆新的配置信息
         * @return {@link MediaConfig}
         */
        public MediaConfig clone() {
            MediaConfig config = new MediaConfig();
            config.mMimeType            = mMimeType;
            config.mSelectionMode       = mSelectionMode;
            config.mIsCamera            = mIsCamera;
            config.mIsCrop              = mIsCrop;
            config.mIsCircleCrop        = mIsCircleCrop;
            config.mIsCompress          = mIsCompress;
            config.mMinimumCompressSize = mMinimumCompressSize;
            config.mWithAspectRatio     = mWithAspectRatio;
            config.mIsGif               = mIsGif;
            config.mImageSpanCount      = mImageSpanCount;
            config.mMinSelectNum        = mMinSelectNum;
            config.mMaxSelectNum        = mMaxSelectNum;
            config.mLocalMedia          = mLocalMedia;
            config.mCameraSavePath      = mCameraSavePath;
            config.mCompressSavePath    = mCompressSavePath;
            return config;
        }

        /**
         * 设置新的配置信息
         * @param config 新的配置信息
         * @return {@link MediaConfig}
         */
        public MediaConfig set(final MediaConfig config) {
            if (config != null) {
                mMimeType            = config.mMimeType;
                mSelectionMode       = config.mSelectionMode;
                mIsCamera            = config.mIsCamera;
                mIsCrop              = config.mIsCrop;
                mIsCircleCrop        = config.mIsCircleCrop;
                mIsCompress          = config.mIsCompress;
                mMinimumCompressSize = config.mMinimumCompressSize;
                mWithAspectRatio     = config.mWithAspectRatio;
                mIsGif               = config.mIsGif;
                mImageSpanCount      = config.mImageSpanCount;
                mMinSelectNum        = config.mMinSelectNum;
                mMaxSelectNum        = config.mMaxSelectNum;
                mLocalMedia          = config.mLocalMedia;
                mCameraSavePath      = config.mCameraSavePath;
                mCompressSavePath    = config.mCompressSavePath;
            }
            return this;
        }
    }
}