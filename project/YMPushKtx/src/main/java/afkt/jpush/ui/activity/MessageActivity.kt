package afkt.jpush.ui.activity

import afkt.jpush.R
import afkt.jpush.base.BaseActivity
import afkt.jpush.base.config.RouterPath
import afkt.jpush.databinding.ActivityMessageBinding
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.json.DevJSONEngine
import dev.utils.DevFinal

/**
 * detail: 推送消息 Activity
 * @author Ttt
 * 点击推送后根据, 推送消息跳转对应的路由页
 */
@Route(path = RouterPath.MessageActivity_PATH)
class MessageActivity : BaseActivity<ActivityMessageBinding>() {

    override fun isToolBar(): Boolean = true

    override fun baseLayoutId(): Int = R.layout.activity_message

    @JvmField
    @Autowired(name = DevFinal.DATA)
    var pushData: String? = null

    override fun initValue() {
        super.initValue()

        binding.vidAmMessageTv.text = DevJSONEngine.getEngine()?.toJsonIndent(
            pushData
        ) ?: "error"
    }
}