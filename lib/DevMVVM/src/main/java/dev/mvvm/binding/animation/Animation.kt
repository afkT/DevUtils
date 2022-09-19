package dev.mvvm.binding.view

import android.view.View
import android.view.animation.Animation
import androidx.databinding.BindingAdapter
import dev.utils.app.anim.ViewAnimationUtils

// =================================
// = View Animation BindingAdapter =
// =================================

@BindingAdapter(
    value = ["binding_invisibleViewByAlpha", "binding_anim_banClick", "binding_anim_listener"],
    requireAll = false
)
fun View.bindingInvisibleViewByAlpha(
    durationMillis: Long,
    isBanClick: Boolean,
    listener: Animation.AnimationListener?
) {
    if (durationMillis > 0L) {
        ViewAnimationUtils.invisibleViewByAlpha(
            this, durationMillis, isBanClick, listener
        )
    }
}