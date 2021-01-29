package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.PathConfig
import afkt.project.databinding.ActivityWallpaperBinding
import dev.utils.app.WallpaperUtils
import dev.utils.app.image.ImageUtils

/**
 * detail: 手机壁纸
 * @author Ttt
 */
class WallpaperActivity : BaseActivity<ActivityWallpaperBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_wallpaper

    override fun initValue() {
        super.initValue()

        var wallpaper = WallpaperUtils.getDrawable()

        binding.vidAwSaveBtn.setOnClickListener {
            if (wallpaper == null) {
                showToast(false, "获取壁纸失败")
                return@setOnClickListener
            }
            val filePath = PathConfig.AEP_DOWN_IMAGE_PATH
            val fileName = "${System.currentTimeMillis()}.jpg"
            val bitmap = ImageUtils.drawableToBitmap(wallpaper)
            val result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName)
            showToast(result, "保存成功\n${filePath + fileName}", "保存失败")
        }
        wallpaper?.let {
            binding.vidAwIgview.background = it
        }
    }
}