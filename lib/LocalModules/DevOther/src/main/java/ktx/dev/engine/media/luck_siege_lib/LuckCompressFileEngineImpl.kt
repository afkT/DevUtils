package ktx.dev.engine.media.luck_siege_lib

import android.content.Context
import android.net.Uri
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.utils.DateUtils
import dev.utils.app.PathUtils
import dev.utils.app.assist.ResourceAssist
import dev.utils.common.FileUtils
import top.zibin.luban.InputStreamProvider
import java.dev.other.LubanUtils
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 * detail: PictureSelector 相册压缩引擎
 * @author luck
 */
open class LuckCompressFileEngineImpl(
    // 图片大于多少才进行压缩 ( kb )
    protected val mMinimumCompressSize: Int
) : CompressFileEngine {

    // ======================
    // = CompressFileEngine =
    // ======================

    override fun onStartCompress(
        context: Context,
        source: ArrayList<Uri>,
        call: OnKeyValueResultCallbackListener
    ) {
        // 统一压缩存储路径 ( PictureSelector 库压缩存储 )
        val targetDir = PathUtils.getInternal().getAppCachePath(
            "Compress/PictureSelector"
        )
        FileUtils.createFolder(targetDir)
        // 压缩配置
        val compressConfig = LubanUtils.Config(
            mMinimumCompressSize, true, targetDir
        )
        // 转换待压缩数据
        val lists = toInputStreamList(context, source)
        LubanUtils.compress(lists, compressConfig, null, { filePath ->
            val indexOf = filePath.lastIndexOf(".")
            val postfix = if (indexOf != -1) filePath.substring(indexOf) else ".jpg"
            DateUtils.getCreateFileName("CMP_") + postfix
        }, object : LubanUtils.OnCompressListener {
            override fun onStart(
                index: Int,
                count: Int
            ) {
            }

            override fun onSuccess(
                file: File,
                index: Int,
                count: Int
            ) {
                val stream = lists[index]
                val srcPath = stream!!.path
                call.onCallback(srcPath, FileUtils.getAbsolutePath(file))
            }

            override fun onError(
                error: Throwable,
                index: Int,
                count: Int
            ) {
                val stream = lists[index]
                val srcPath = stream!!.path
                call.onCallback(srcPath, srcPath)
            }

            override fun onComplete(
                lists: List<File>,
                maps: Map<Int, File>,
                count: Int
            ) {
            }
        })
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 转换待压缩数据
     * @param context Context
     * @param sources 待转换数据
     * @return Uris
     */
    open protected fun toInputStreamList(
        context: Context?,
        sources: List<Uri>
    ): List<InputStreamProvider?> {
        val lists: MutableList<InputStreamProvider?> = ArrayList()
        for (uri in sources) {
            lists.add(object : InputStreamProvider {
                @Throws(IOException::class)
                override fun open(): InputStream {
                    return ResourceAssist.get(context).openInputStream(uri)
                }

                override fun getPath(): String {
                    return toUriPath(uri) ?: ""
                }
            })
        }
        return lists
    }

    /**
     * 获取 Uri 原始路径
     * @param uri Uri
     * @return Uri 原始路径
     */
    open protected fun toUriPath(uri: Uri?): String? {
        return uri?.let {
            val uriString = it.toString()
            if (PictureMimeType.isContent(uriString)) uriString else it.path
        }
    }
}