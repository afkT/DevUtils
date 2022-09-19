package dev.mvvm.binding.widget

import androidx.databinding.BindingAdapter
import dev.utils.common.ColorUtils
import dev.widget.ui.LoadProgressBar

// ==================================
// = LoadProgressBar BindingAdapter =
// ==================================

@BindingAdapter(
    value = [
        "binding_dev_progress",
        "binding_dev_max",
        "binding_dev_progressColor",
        "binding_dev_outerRingColor"
    ],
    requireAll = false
)
fun LoadProgressBar.bindingProgressAndColor(
    progress: Double,
    max: Double,
    progressColor: Int = 0,
    outerRingColor: Int = 0
) {
    if (progressColor != 0) {
        setProgressColor(progressColor)
        if (outerRingColor != 0) {
            setOuterRingColor(outerRingColor)
        } else {
            setOuterRingColor(ColorUtils.setAlpha(progressColor, 0.4F))
        }
    }
    setProgress((progress * 10).toInt())
    setMax((max * 10).toInt())
}