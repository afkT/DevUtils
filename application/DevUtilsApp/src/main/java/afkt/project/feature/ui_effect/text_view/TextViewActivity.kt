package afkt.project.feature.ui_effect.text_view

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.ActivityTextviewBinding
import com.therouter.router.Route

/**
 * detail: 两个 TextView 显示效果
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.TextViewActivity_PATH)
class TextViewActivity : BaseProjectActivity<ActivityTextviewBinding, AppViewModel>(
    R.layout.activity_textview
)