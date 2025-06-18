package afkt.project

import afkt.project.base.app.AppActivity
import afkt.project.base.app.AppViewModel
import afkt.project.databinding.MainActivityBinding

class MainActivity : AppActivity<MainActivityBinding, AppViewModel>(
    R.layout.main_activity
)