package afkt.jpush.ui.activity

import afkt.jpush.R
import afkt.jpush.base.BaseActivity
import afkt.jpush.base.config.RouterPath
import androidx.viewbinding.ViewBinding
import dev.utils.app.HandlerUtils

class SplashActivity : BaseActivity<ViewBinding>() {

    override fun isViewBinding(): Boolean = false

    override fun baseLayoutId(): Int = R.layout.activity_splash

    override fun initOther() {
        super.initOther()
        HandlerUtils.postRunnable({
            if (isFinishing) return@postRunnable
            routerActivity(RouterPath.MainActivity_PATH)
            finish()
        }, 1200)
    }
}