package java.dev.engine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelectionPreviewModel;
import com.luck.picture.lib.basic.PictureSelectionSystemModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.manager.PictureCacheManager;

import java.util.ArrayList;
import java.util.List;

import dev.engine.media.IMediaEngine;
import dev.utils.LogPrintUtils;
import dev.utils.app.UriUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: PictureSelector Media Selector Engine 实现
 * @author Ttt
 * <pre>
 *     功能配置文档
 *     @see <a href="https://github.com/LuckSiege/PictureSelector"/>
 *     @see <a href="https://github.com/LuckSiege/PictureSelector/wiki"/>
 * </pre>
 */
public class PictureSelectorEngineImpl
        implements IMediaEngine<MediaConfig, MediaData> {

    // 日志 TAG
    private final String      TAG              = PictureSelectorEngineImpl.class.getSimpleName();
    // 全局请求跳转回传 code
    private final int         PIC_REQUEST_CODE = 159857;
    // 全局配置信息
    private final MediaConfig PIC_CONFIG       = new MediaConfig();

    // ==========
    // = 配置方法 =
    // ==========

    @Override
    public MediaConfig getConfig() {
        return PIC_CONFIG;
    }

    @Override
    public void setConfig(MediaConfig config) {
        PIC_CONFIG.set(config);
    }

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public boolean openCamera(Activity activity) {
        return openCamera(activity, PIC_CONFIG);
    }

    @Override
    public boolean openCamera(
            Activity activity,
            MediaConfig config
    ) {
        return startCameraModel(
                createPictureSelector(activity), config
        );
    }

    @Override
    public boolean openCamera(Fragment fragment) {
        return openCamera(fragment, PIC_CONFIG);
    }

    @Override
    public boolean openCamera(
            Fragment fragment,
            MediaConfig config
    ) {
        return startCameraModel(
                createPictureSelector(fragment), config
        );
    }

    // =

    @Override
    public boolean openGallery(Activity activity) {
        return openGallery(activity, PIC_CONFIG);
    }

    @Override
    public boolean openGallery(
            Activity activity,
            MediaConfig config
    ) {
        return startGalleryModel(
                createPictureSelector(activity), config
        );
    }

    @Override
    public boolean openGallery(Fragment fragment) {
        return openGallery(fragment, PIC_CONFIG);
    }

    @Override
    public boolean openGallery(
            Fragment fragment,
            MediaConfig config
    ) {
        return startGalleryModel(
                createPictureSelector(fragment), config
        );
    }

    // =

    @Override
    public boolean openPreview(Activity activity) {
        return openPreview(activity, PIC_CONFIG);
    }

    @Override
    public boolean openPreview(
            Activity activity,
            MediaConfig config
    ) {
        return startPreviewModel(
                createPictureSelector(activity), config
        );
    }

    @Override
    public boolean openPreview(Fragment fragment) {
        return openPreview(fragment, PIC_CONFIG);
    }

    @Override
    public boolean openPreview(
            Fragment fragment,
            MediaConfig config
    ) {
        return startPreviewModel(
                createPictureSelector(fragment), config
        );
    }

    // ==========
    // = 其他方法 =
    // ==========

    @Override
    public void deleteCacheDirFile(
            Context context,
            int type
    ) {
        try {
            PictureCacheManager.deleteCacheDirFile(context, type);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteCacheDirFile");
        }
    }

    @Override
    public void deleteAllCacheDirFile(Context context) {
        try {
            PictureCacheManager.deleteAllCacheDirFile(context);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteAllCacheDirFile");
        }
    }

    @Override
    public boolean isMediaSelectorResult(
            int requestCode,
            int resultCode
    ) {
        return requestCode == PIC_REQUEST_CODE && resultCode == Activity.RESULT_OK;
    }

    // =

    @Override
    public List<MediaData> getSelectors(Intent intent) {
        List<LocalMedia> result = PictureSelector.obtainSelectorList(intent);
        List<MediaData>  lists  = new ArrayList<>();
        if (result != null) {
            for (LocalMedia libData : result) {
                if (libData != null) {
                    lists.add(createMediaData(libData));
                }
            }
        }
        return lists;
    }

    @Override
    public List<Uri> getSelectorUris(
            Intent intent,
            boolean original
    ) {
        List<MediaData> result = getSelectors(intent);
        List<Uri>       lists  = new ArrayList<>();
        if (result != null) {
            for (MediaData media : result) {
                if (media != null) {
                    Uri uri;
                    if (original) {
                        uri = media.getOriginalUri();
                    } else {
                        uri = media.getAvailableUri();
                    }
                    if (uri != null) {
                        lists.add(uri);
                    }
                }
            }
        }
        return lists;
    }

    @Override
    public MediaData getSingleSelector(Intent intent) {
        List<MediaData> lists = getSelectors(intent);
        if (lists != null && lists.size() > 0) return lists.get(0);
        return null;
    }

    @Override
    public Uri getSingleSelectorUri(
            Intent intent,
            boolean original
    ) {
        List<Uri> lists = getSelectorUris(intent, original);
        if (lists != null && lists.size() > 0) return lists.get(0);
        return null;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 创建图片选择器对象
     * @param object {@link Activity}、{@link Fragment}
     * @return {@link PictureSelector}
     */
    private PictureSelector createPictureSelector(final Object object) {
        if (object instanceof Activity) {
            return PictureSelector.create((Activity) object);
        } else if (object instanceof Fragment) {
            return PictureSelector.create((Fragment) object);
        }
        return null;
    }

    // ==========
    // = 转换对象 =
    // ==========

    /**
     * 转换 List
     * @param lists {@link MediaData} list
     * @return {@link LocalMedia} list
     */
    private ArrayList<LocalMedia> convertList(final List<MediaData> lists) {
        ArrayList<LocalMedia> medias = new ArrayList<>();
        if (lists != null) {
            for (MediaData media : lists) {
                if (media != null) {
                    Object libData = media.getLibOriginalData();
                    if (libData instanceof LocalMedia) {
                        medias.add((LocalMedia) libData);
                    } else {
                        LocalMedia localMedia = new LocalMedia();
                        Uri        uri        = media.getOriginalUri();
                        if (uri != null) {
                            localMedia.setPath(uri.toString());
                            localMedia.setOriginalPath(uri.toString());
                            medias.add(localMedia);
                        }
                    }
                }
            }
        }
        return medias;
    }

    /**
     * 创建 MediaData 并填充数据
     * @param libData 第三方库选择数据实体类
     * @return MediaData
     */
    private MediaData createMediaData(final LocalMedia libData) {
        MediaData media = new MediaData();
        media.setLibOriginalData(libData);

        // ============
        // = 初始化数据 =
        // ============

        // 资源路径 Uri
        media.setOriginalUri(UriUtils.getUriForPath(libData.getOriginalPath()))
                .setSandboxUri(UriUtils.getUriForPath(libData.getSandboxPath()))
                .setCompressUri(UriUtils.getUriForPath(libData.getCompressPath()))
                .setThumbnailUri(UriUtils.getUriForPath(libData.getVideoThumbnailPath()))
                .setWatermarkUri(UriUtils.getUriForPath(libData.getWatermarkPath()))
                .setCropUri(UriUtils.getUriForPath(libData.getCutPath()));

        // 资源信息
        media.setMimeType(libData.getMimeType())
                .setDuration(libData.getDuration())
                .setWidth(libData.getWidth())
                .setHeight(libData.getHeight());

        // 资源裁剪信息
        media.setCropImageWidth(libData.getCropImageWidth())
                .setCropImageHeight(libData.getCropImageHeight())
                .setCropOffsetX(libData.getCropOffsetX())
                .setCropOffsetY(libData.getCropOffsetY())
                .setCropAspectRatio(libData.getCropResultAspectRatio());

        // 状态信息
        media.setCropState(libData.isCut())
                .setCompressState(libData.isCompressed());

        return media;
    }

    // ==========================
    // = PictureSelection Model =
    // ==========================

    /**
     * 跳转 Camera PictureSelection Model
     * @param pictureSelector {@link PictureSelector}
     * @param config          {@link MediaConfig}
     * @return {@code true} success, {@code false} fail
     */
    private boolean startCameraModel(
            final PictureSelector pictureSelector,
            final MediaConfig config
    ) {
        if (pictureSelector != null && config != null) {
            try {
                Object libConfig = config.getLibCustomConfig();
                if (libConfig instanceof PictureSelectionCameraModel) {
                    ((PictureSelectionCameraModel) libConfig).forResultActivity(
                            PIC_REQUEST_CODE
                    );
                    return true;
                }
                throw new Exception("MediaConfig libCustomConfig is null");
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startCameraModel");
            }
        }
        return false;
    }

    /**
     * 跳转 Gallery PictureSelection Model
     * @param pictureSelector {@link PictureSelector}
     * @param config          {@link MediaConfig}
     * @return {@code true} success, {@code false} fail
     */
    private boolean startGalleryModel(
            final PictureSelector pictureSelector,
            final MediaConfig config
    ) {
        if (pictureSelector != null && config != null) {
            try {
                Object libConfig = config.getLibCustomConfig();
                if (libConfig instanceof PictureSelectionModel) {
                    ((PictureSelectionModel) libConfig).forResult(
                            PIC_REQUEST_CODE
                    );
                    return true;
                }
                if (libConfig instanceof PictureSelectionSystemModel) {
                    ((PictureSelectionSystemModel) libConfig).forSystemResultActivity(
                            PIC_REQUEST_CODE
                    );
                    return true;
                }
                throw new Exception("MediaConfig libCustomConfig is null");
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startGalleryModel");
            }
        }
        return false;
    }

    /**
     * 跳转 Preview PictureSelection Model
     * @param pictureSelector {@link PictureSelector}
     * @param config          {@link MediaConfig}
     * @return {@code true} success, {@code false} fail
     */
    private boolean startPreviewModel(
            final PictureSelector pictureSelector,
            final MediaConfig config
    ) {
        if (pictureSelector != null && config != null) {
            try {
                Object libConfig = config.getLibCustomConfig();
                if (libConfig instanceof PictureSelectionPreviewModel) {
                    ((PictureSelectionPreviewModel) libConfig).startActivityPreview(
                            ConvertUtils.toInt(config.getCustomData(), 0),
                            false, convertList(config.getMediaDatas())
                    );
                    return true;
                }
                throw new Exception("MediaConfig libCustomConfig is null");
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startPreviewModel");
            }
        }
        return false;
    }
}