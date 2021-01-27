package afkt.project.model.vm

import androidx.annotation.ColorInt
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaletteViewModel : ViewModel(),
    DefaultLifecycleObserver {

    val itemPosition = MutableLiveData<Int>()

    val paletteColor = MutableLiveData<PaletteColor>()

    fun postItemPosition(value: Int) {
        itemPosition.value = value
    }

    fun postPaletteColor(value: PaletteColor) {
        paletteColor.value = value
    }
}

class PaletteColor(
    @ColorInt val bgColor: Int,
    @ColorInt val titleColor: Int,
    @ColorInt val boldColor: Int,
)