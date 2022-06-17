package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.databinding.ActivityMainContentVmImplBinding
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dev.base.expand.content.DevBaseContentMVVMActivity
import dev.base.expand.mvvm.MVVM
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.ColorUtils

/**
 * detail: Main Content Activity MVVM Activity
 * @author Ttt
 */
class MainBaseContentVMImplActivity :
    DevBaseContentMVVMActivity<ActivityMainContentVmImplBinding, MVVM.VMImpl>() {

    override fun baseLayoutId(): Int {
        return R.layout.activity_main_content_vm_impl
    }

    override fun baseLayoutView(): View? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.title = "MVVM VMImpl Content Title"

        // 随机设置背景色
        binding.vidInclude.color = ColorUtils.getRandomColor()

        contentAssist.addTitleView(
            QuickHelper.get(TextView(this))
                .setText("随机标题: " + ChineseUtils.randomWord(10))
                .setTextColors(ResourceUtils.getColor(R.color.red))
                .setBackgroundColor(ResourceUtils.getColor(R.color.pink))
                .setWidthHeight(
                    ViewUtils.MATCH_PARENT,
                    ResourceUtils.getDimensionInt(R.dimen.dp_50)
                )
                .setGravity(Gravity.CENTER)
                .setBold()
                .setOnClick { view ->
                    showToast(TextViewUtils.getText(view))
                }
                .getView()
        )

        // 悬浮 View
        contentAssist.addFloatView(
            QuickHelper.get(ImageView(this))
                .setWidthHeight(
                    ResourceUtils.getDimensionInt(R.dimen.dp_50),
                    ResourceUtils.getDimensionInt(R.dimen.dp_50)
                )
                .setImageResource(R.mipmap.icon_launcher_round)
                .getView()
        )

        // 悬浮 View 居下显示 ( 需要 addBodyView 后进行设置 LayoutParams 才有效 )
        contentAssist.floatFrame?.let {
            QuickHelper.get(ViewUtils.getChildAtLast<ImageView>(it))
                .setMargin(ResourceUtils.getDimensionInt(R.dimen.dp_20))
                .setLayoutGravity(Gravity.END or Gravity.BOTTOM)
                .setOnClick {
                    showToast("点击了悬浮 View")
                }
        }
    }

    override fun initViewModel() {
    }
}