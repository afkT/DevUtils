package afkt.project.ui.fragment

import afkt.project.R
import afkt.project.base.app.BaseFragment
import afkt.project.databinding.FragmentPaletteBinding
import android.os.Bundle
import android.view.View
import androidx.palette.graphics.Palette

class PaletteFragment : BaseFragment<FragmentPaletteBinding>() {

    private var palette: Palette? = null

    override fun baseContentId(): Int = R.layout.fragment_palette

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}