package dev.simple.app.base.interfaces

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * detail: Base Activity、Fragment 创建 Layout View
 * @author Ttt
 */
interface BindingLayoutView<T> {

    fun bind(value: T, inflater: LayoutInflater): View?
}

interface BindingActivityView : BindingLayoutView<AppCompatActivity>

interface BindingFragmentView : BindingLayoutView<Fragment>