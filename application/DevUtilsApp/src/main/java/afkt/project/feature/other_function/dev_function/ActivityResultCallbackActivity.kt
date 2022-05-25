package afkt.project.feature.other_function.dev_function

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityActivityResultCallbackBinding
import afkt.project.model.item.RouterPath
import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.media.MediaConfig
import dev.utils.app.AppUtils
import dev.utils.app.activity_result.DefaultActivityResult
import dev.utils.app.toast.ToastTintUtils

/**
 * detail: 跳转 Activity 回传 Callback
 * @author Ttt
 */
@Route(path = RouterPath.OTHER_FUNCTION.ActivityResultCallbackActivity_PATH)
class ActivityResultCallbackActivity : BaseActivity<ActivityActivityResultCallbackBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_activity_result_callback

    override fun initListener() {
        super.initListener()
        binding.vidSelectBtn.setOnClickListener {
            AppUtils.startActivityForResult(object : DefaultActivityResult.ResultCallback {
                override fun onStartActivityForResult(activity: Activity): Boolean {
                    // 初始化图片配置
                    val config = MediaConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false)
                        .setMimeType(MediaConfig.MimeType.ofImage())
                        .setCamera(true).setGif(false)
                    // 打开图片选择器
                    DevEngine.getMedia()?.openGallery(activity, config)
                    return true
                }

                override fun onActivityResult(
                    result: Boolean,
                    resultCode: Int,
                    intent: Intent?
                ) {
                    if (result && intent != null) {
                        val imgPath = DevEngine.getMedia()?.getSingleSelectorPath(intent, true)
                        // 提示
                        ToastTintUtils.success("选择了图片: $imgPath")
                    } else {
                        showToast(false, "非成功操作")
                    }
                }
            })
        }
    }
}