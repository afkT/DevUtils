package afkt.gtpush.ui.activity

import afkt.gtpush.R
import afkt.gtpush.base.BaseActivity
import afkt.gtpush.base.config.RouterPath
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