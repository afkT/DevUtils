package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineImageCompressBinding
import android.graphics.BitmapFactory
import android.view.View
import dev.engine.compress.listener.OnCompressListener
import dev.engine.core.compress.CompressConfig
import dev.engine.extensions.compress.compress_list
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.image.ImageUtils
import dev.utils.common.FileUtils
import java.io.File

/**
 * detail: Image Compress Engine 图片压缩
 * @author Ttt
 */
class ImageCompressFragment :
    AppFragment<FragmentDevEngineImageCompressBinding, ImageCompressViewModel>(
        R.layout.fragment_dev_engine_image_compress, BR.viewModel
    )

/**
 * detail: Image Compress Engine 图片压缩 ViewModel
 * @author Ttt
 */
class ImageCompressViewModel : AppViewModel() {

    val clickCompress = View.OnClickListener { view ->
        val context = view.context
        // 通过资源生成待压缩源文件
        val bitmap = BitmapFactory.decodeResource(view.resources, R.mipmap.icon_launcher)
        val srcFile = File(context.cacheDir, "dev_engine_compress_src.jpg")
        ImageUtils.saveBitmapToSDCardJPEG(bitmap, srcFile.absolutePath)
        // 压缩输出目录
        val targetDir = File(context.cacheDir, "dev_engine_compress_out")
        FileUtils.createFolder(targetDir.absolutePath)

        val result = compress_list(
            lists = listOf(srcFile),
            config = CompressConfig(100, targetDir.absolutePath),
            compressListener = object : OnCompressListener {
                override fun onStart(
                    index: Int,
                    count: Int
                ) {
                }

                override fun onSuccess(
                    file: File?,
                    index: Int,
                    count: Int
                ) {
                }

                override fun onError(
                    error: Throwable?,
                    index: Int,
                    count: Int
                ) {
                    toast_showShort(text = "压缩失败: ${error?.message}")
                }

                override fun onComplete(
                    lists: MutableList<File>?,
                    maps: MutableMap<Int, File>?,
                    count: Int
                ) {
                    toast_showShort(text = "压缩完成, 共 ${lists?.size ?: 0} 张")
                }
            }
        )
        if (!result) toast_showShort(text = "压缩启动失败")
    }
}