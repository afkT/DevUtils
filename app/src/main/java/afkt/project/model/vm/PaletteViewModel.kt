package afkt.project.model.vm

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette

class PaletteViewModel : ViewModel(),
    DefaultLifecycleObserver {

    val itemPosition = MutableLiveData<Int>()

    val paletteColor = MutableLiveData<Palette>()

    fun postItemPosition(value: Int) {
        itemPosition.value = value
    }

    fun postPalette(value: Palette) {
        paletteColor.value = value
    }
}