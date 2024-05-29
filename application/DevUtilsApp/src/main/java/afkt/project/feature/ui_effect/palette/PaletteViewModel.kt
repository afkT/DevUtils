package afkt.project.feature.ui_effect.palette

import afkt.project.base.project.BaseProjectViewModel
import androidx.lifecycle.MutableLiveData
import androidx.palette.graphics.Palette

class PaletteViewModel : BaseProjectViewModel() {

    val itemPosition = MutableLiveData<Int>()

    val paletteColor = MutableLiveData<Palette>()

    fun postItemPosition(value: Int) {
        itemPosition.value = value
    }

    fun postPalette(value: Palette) {
        paletteColor.value = value
    }
}