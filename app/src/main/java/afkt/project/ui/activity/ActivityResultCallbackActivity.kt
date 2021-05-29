package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityActivityResultCallbackBinding
import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.luck.picture.lib.config.PictureMimeType
import dev.engine.media.DevMediaEngine
import dev.utils.app.ActivityUtils
import dev.utils.app.AppUtils
import dev.utils.app.toast.ToastTintUtils
import ktx.dev.engine.media.MediaConfig

/**
 * detail: 跳转 Activity 回传 Callback
 * @author Ttt
 */
@Route(path = RouterPath.ActivityResultCallbackActivity_PATH)
class ActivityResultCallbackActivity : BaseActivity<ActivityActivityResultCallbackBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_activity_result_callback

    override fun initListener() {
        super.initListener()
        binding.vidAarcSelectBtn.setOnClickListener {
            AppUtils.startActivityForResult(object : ActivityUtils.ResultCallback {
                override fun onStartActivityForResult(activity: Activity): Boolean {
                    // 初始化图片配置
                    var config = MediaConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false)
                        .setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false)
                    // 打开图片选择器
                    DevMediaEngine.getEngine().openGallery(activity, config)
                    return true
                }

                override fun onActivityResult(
                    result: Boolean,
                    resultCode: Int,
                    data: Intent
                ) {
                    if (result && data != null) {
                        val imgPath = DevMediaEngine.getEngine().getSingleSelectorPath(data, true)
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