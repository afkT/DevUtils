package dev.mvvm.binding.view

import androidx.constraintlayout.widget.Guideline
import androidx.databinding.BindingAdapter

// ===================================
// = ConstraintLayout BindingAdapter =
// ===================================

@BindingAdapter("binding_layout_constraintGuide_begin")
fun Guideline.bindingLayoutConstraintGuideBegin(margin: Int) {
    this.setGuidelineBegin(margin)
}