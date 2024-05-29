package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityWallpaperBinding
import com.therouter.router.Route
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.storage.OnDevInsertListener
import dev.engine.storage.StorageItem
import dev.engine.storage.StorageResult
import dev.utils.app.WallpaperUtils
import dev.utils.common.FileUtils

/**
 * detail: 手机壁纸
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.WallpaperActivity_PATH)
class WallpaperActivity : BaseProjectActivity<ActivityWallpaperBinding, BaseProjectViewModel>(
    R.layout.activity_wallpaper, simple_Agile = {
        if (it is WallpaperActivity) {
            it.apply {
                val wallpaper = WallpaperUtils.getDrawable()

                binding.vidSaveBtn.setOnClickListener {
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
                    binding.vidIv.background = it
                }
            }
        }
    }
)