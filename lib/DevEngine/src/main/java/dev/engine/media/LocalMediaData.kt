package dev.engine.media

import com.luck.picture.lib.entity.LocalMedia
import dev.utils.DevFinal
import dev.utils.common.StringUtils

/**
 * detail: Local Media Selector Data
 * @author Ttt
 */
class LocalMediaData : IMediaEngine.EngineData {

    private var mLocalMedia: LocalMedia? = null

    constructor()

    constructor(localMedia: LocalMedia?) {
        this.mLocalMedia = localMedia
    }

    // =

    fun getLocalMedia(): LocalMedia? {
        return mLocalMedia
    }

    fun setLocalMedia(localMedia: LocalMedia) {
        this.mLocalMedia = localMedia
    }

    /**
     * 获取本地资源路径
     * @param original 是否使用原图
     * @return 本地资源路径
     */
    fun getLocalMediaPath(original: Boolean): String? {
        mLocalMedia?.let {
            if (original) return it.path
            // 判断资源类型
            val mimeType = it.mimeType
            return if (StringUtils.isStartsWith(mimeType, DevFinal.STR.IMAGE)) { // 图片
                if (it.isCompressed) { // 是否压缩图片
                    it.compressPath
                } else if (it.isCut) { // 是否裁减图片
                    it.cutPath
                } else { // 获取原图
                    it.path
                }
            } else {
                it.path
            }
        }
        return null
    }
}