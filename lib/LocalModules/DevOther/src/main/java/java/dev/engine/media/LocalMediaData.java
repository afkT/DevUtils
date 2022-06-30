package java.dev.engine.media;

import com.luck.picture.lib.entity.LocalMedia;

import dev.engine.media.IMediaEngine;
import dev.utils.DevFinal;
import dev.utils.common.StringUtils;

/**
 * detail: Local Media Selector Data
 * @author Ttt
 */
public class LocalMediaData
        extends IMediaEngine.EngineData {

    private LocalMedia mLocalMedia;

    public LocalMediaData() {
    }

    public LocalMediaData(final LocalMedia localMedia) {
        this.mLocalMedia = localMedia;
    }

    // =

    public LocalMedia getLocalMedia() {
        return mLocalMedia;
    }

    public void setLocalMedia(final LocalMedia localMedia) {
        this.mLocalMedia = localMedia;
    }

    /**
     * 获取本地资源路径
     * @param original 是否使用原图
     * @return 本地资源路径
     */
    public String getLocalMediaPath(final boolean original) {
        if (mLocalMedia != null) {
            if (original) return mLocalMedia.getPath();
            // 判断资源类型
            String mimeType = mLocalMedia.getMimeType();
            if (StringUtils.isStartsWith(mimeType, DevFinal.STR.IMAGE)) { // 图片
                if (mLocalMedia.isCompressed()) { // 是否压缩图片
                    return mLocalMedia.getCompressPath();
                } else if (mLocalMedia.isCut()) { // 是否裁减图片
                    return mLocalMedia.getCutPath();
                } else { // 获取原图
                    return mLocalMedia.getPath();
                }
            } else {
                return mLocalMedia.getPath();
            }
        }
        return null;
    }
}