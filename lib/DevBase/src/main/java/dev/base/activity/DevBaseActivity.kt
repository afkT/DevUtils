package dev.base.activity

import android.view.View

/**
 * detail: Activity 基类
 * @author Ttt
 */
abstract class DevBaseActivity : AbstractDevBaseActivity(),
    View.OnClickListener {
    override fun onClick(v: View) {}
}