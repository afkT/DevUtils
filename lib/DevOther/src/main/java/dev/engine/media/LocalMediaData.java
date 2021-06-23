package dev.engine.media;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

/**
 * detail: Local Media Selector Data
 * @author Ttt
 */
public class LocalMediaData
        extends IMediaEngine.EngineData {

    private LocalMedia localMedia;

    public LocalMediaData() {
    }

    public LocalMediaData(final LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    // =

    public LocalMedia getLocalMedia() {
        return localMedia;
    }

    public void setLocalMedia(final LocalMedia localMedia) {
        this.localMedia = localMedia;
    }

    /**
     * 获取本地资源路径
     * @param original 是否使用原图地址
     * @return 本地资源路径
     */
    public String getLocalMediaPath(final boolean original) {
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
}