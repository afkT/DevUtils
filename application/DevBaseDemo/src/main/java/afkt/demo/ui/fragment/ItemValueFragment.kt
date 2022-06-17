package afkt.demo.ui.fragment

import afkt.demo.R
import afkt.demo.databinding.FragmentItemValueBinding
import android.os.Bundle
import android.view.View
import dev.base.expand.content.DevBaseContentViewBindingFragment
import dev.utils.DevFinal
import dev.utils.common.ColorUtils

/**
 * detail: Item Value Fragment
 * @author Ttt
 */
class ItemValueFragment : DevBaseContentViewBindingFragment<FragmentItemValueBinding>() {

    override fun baseLayoutId(): Int {
        return R.layout.fragment_item_value
    }

    override fun baseLayoutView(): View? {
        return null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val value = it.getInt(DevFinal.STR.VALUE)

            binding.vidTv.text = "$value"

            binding.root.setBackgroundColor(ColorUtils.getRandomColor())
        }
    }

    companion object {
        fun get(value: Int): DevBaseContentViewBindingFragment<FragmentItemValueBinding> {
            val fragment = ItemValueFragment()
            val bundle = Bundle()
            bundle.putInt(DevFinal.STR.VALUE, value)
            fragment.arguments = bundle
            return fragment
        }
    }
}