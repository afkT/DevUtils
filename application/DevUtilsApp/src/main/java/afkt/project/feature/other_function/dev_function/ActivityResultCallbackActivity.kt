package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.ActivityActivityResultCallbackBinding
import afkt.project.ui.createGalleryConfig
import android.app.Activity
import android.content.Intent
import com.therouter.router.Route
import dev.engine.DevEngine
import dev.expand.engine.image.display
import dev.mvvm.utils.toSource
import dev.utils.app.AppUtils
import dev.utils.app.activity_result.DefaultActivityResult

/**
 * detail: 跳转 Activity 回传 Callback
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.ActivityResultCallbackActivity_PATH)
class ActivityResultCallbackActivity :
    BaseProjectActivity<ActivityActivityResultCallbackBinding, BaseProjectViewModel>(
        R.layout.activity_activity_result_callback, simple_Agile = {
            if (it is ActivityResultCallbackActivity) {
                it.apply {
                    binding.vidSelectBtn.setOnClickListener {
                        AppUtils.startActivityForResult(object :
                            DefaultActivityResult.ResultCallback {
                            override fun onStartActivityForResult(activity: Activity): Boolean {
                                // 打开图片选择器
                                DevEngine.getMedia()
                                    ?.openGallery(activity, activity.createGalleryConfig())
                                return true
                            }

                            override fun onActivityResult(
                                result: Boolean,
                                resultCode: Int,
                                intent: Intent?
                            ) {
                                val imgUri =
                                    DevEngine.getMedia()?.getSingleSelectorUri(intent, false)
                                if (imgUri != null) {
                                    binding.vidIv.display(source = imgUri.toSource())
                                } else {
                                    Thread {
                                        Thread.sleep(100L)
                                        // 延迟 100 毫秒是防止 Activity 销毁对应的 Toast 显示在已销毁的 Activity
                                        // 导致实际预览效果 Toast 并没有显示
                                        showToast(false, "非成功操作")
                                    }.start()
                                }
                            }
                        })
                    }
                }
            }
        }
    )