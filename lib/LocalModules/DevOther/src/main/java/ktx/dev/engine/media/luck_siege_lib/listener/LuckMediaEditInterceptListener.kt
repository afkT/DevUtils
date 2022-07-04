package ktx.dev.engine.media.luck_siege_lib.listener

import android.net.Uri
import androidx.fragment.app.Fragment
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener
import com.luck.picture.lib.utils.DateUtils
import com.yalantis.ucrop.UCrop
import dev.utils.common.FileUtils
import java.io.File

/**
 * detail: PictureSelector 编辑拦截器
 * @author luck
 * 图片预览 - 左下角编辑点击触发
 */
open class LuckMediaEditInterceptListener(
    // 编辑保存路径
    protected val mOutputCropPath: String,
    // UCrop.Options
    protected val mOptions: UCrop.Options
) : OnMediaEditInterceptListener {

    // ==========
    // = 构造函数 =
    // ==========

    init {
        // 创建文件夹防止未创建导致无法写入
        FileUtils.createFolder(mOutputCropPath)
    }

    // ================================
    // = OnMediaEditInterceptListener =
    // ================================

    override fun onStartMediaEdit(
        fragment: Fragment,
        currentLocalMedia: LocalMedia,
        requestCode: Int
    ) {
        val currentEditPath = currentLocalMedia.availablePath
        val inputUri = if (PictureMimeType.isContent(currentEditPath)) {
            Uri.parse(currentEditPath)
        } else {
            Uri.fromFile(File(currentEditPath))
        }
        val destinationUri = Uri.fromFile(
            File(
                mOutputCropPath, DateUtils.getCreateFileName("CROP_") + ".jpeg"
            )
        )
        val uCrop = UCrop.of<Any>(inputUri, destinationUri)
        uCrop.withOptions(mOptions)
        uCrop.startEdit(fragment.requireContext(), fragment, requestCode)
    }
}