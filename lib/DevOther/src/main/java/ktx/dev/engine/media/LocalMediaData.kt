package ktx.dev.engine.media

import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import dev.engine.media.IMediaEngine

/**
 * detail: Local Media Selector Data
 * @author Ttt
 */
class LocalMediaData : IMediaEngine.MediaData {

    var localMedia: LocalMedia? = null

    constructor()

    constructor(localMedia: LocalMedia?) {
        this.localMedia = localMedia
    }

    /**
     * 获取本地资源路径
     * @param original 是否使用原图地址
     * @return 本地资源路径
     */
    fun getLocalMediaPath(original: Boolean): String? {
        localMedia?.let {
            if (original) return it.path
            // 判断资源类型
            val mimeType = it.mimeType
            return if (PictureMimeType.isHasImage(mimeType)) { // 图片
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