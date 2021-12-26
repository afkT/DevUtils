package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityWallpaperBinding
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.WallpaperUtils
import dev.utils.common.FileUtils

/**
 * detail: 悬浮窗管理辅助类 ( 无需权限依赖 Activity )
 * @author Ttt
 */
@Route(path = RouterPath.FloatingWindowManager2Activity_PATH)
class FloatingWindowManager2Activity : BaseActivity<ActivityWallpaperBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_wallpaper

    override fun initValue() {
        super.initValue()

        val wallpaper = WallpaperUtils.getDrawable()

        binding.vidAwSaveBtn.setOnClickListener {
            if (wallpaper == null) {
                showToast(false, "获取壁纸失败")
                return@setOnClickListener
            }
            DevEngine.getStorage()?.insertImageToExternal(
                StorageItem.createExternalItem(
                    "${System.currentTimeMillis()}.jpg"
                ),
                DevSource.create(
                    wallpaper
                ),
                object : OnDevInsertListener {
                    override fun onResult(
                        result: StorageResult,
                        params: StorageItem?,
                        source: DevSource?
                    ) {
                        showToast(
                            result.isSuccess(),
                            "保存成功\n${FileUtils.getAbsolutePath(result.getFile())}",
                            "保存失败"
                        )
                    }
                }
            )
        }
        wallpaper?.let {
            binding.vidAwIgview.background = it
        }
    }
}