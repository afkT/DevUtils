package afkt.httpcapture.use

import afkt.httpcapture.use.base.BaseActivity
import afkt.httpcapture.use.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, AppViewModel>(
    R.layout.activity_main, simple_Agile = {
        if (it is MainActivity) {

        }
    }
)