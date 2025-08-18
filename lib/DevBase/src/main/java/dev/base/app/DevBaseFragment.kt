package dev.base.app

import android.view.View

/**
 * detail: Fragment 基类
 * @author Ttt
 */
abstract class DevBaseFragment : AbstractDevBaseFragment(),
    View.OnClickListener {
    override fun onClick(v: View) {}
}