package afkt.project.features.ui_effect

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectFlexboxBinding
import afkt.project.model.basic.AdapterModel
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.simple.app.base.asFragment
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: Flexbox LayoutManager
 * @author Ttt
 */
class FlexboxFragment : AppFragment<FragmentUiEffectFlexboxBinding, FlexboxViewModel>(
    R.layout.fragment_ui_effect_flexbox, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<FlexboxFragment> {
            binding.vidRv.apply {
                val layoutManager = FlexboxLayoutManager(context)
                layoutManager.flexDirection = FlexDirection.ROW
                layoutManager.justifyContent = JustifyContent.FLEX_START
                this.layoutManager = layoutManager
            }
            // 创建数据
            viewModel.randomData()
        }
    }
)

class FlexboxViewModel : AppViewModel() {

    val adapter = FlexboxAdapter()

    var clickRefresh = View.OnClickListener { view ->
        randomData()
    }

    // ==============
    // = 随机创建数据 =
    // ==============

    fun randomData() {
        val lists = mutableListOf<String>()
        for (i in 1..20) {
            val text = ChineseUtils.randomWord(RandomUtils.getRandom(8)) +
                    RandomUtils.getRandomLetters(RandomUtils.getRandom(8))
            val randomText = "$i." + RandomUtils.getRandom(
                text.toCharArray(), text.length
            )
            lists.add(randomText)
        }
        adapter.addAllAndClear(lists)
    }
}

class FlexboxAdapter() : AdapterModel<String>() {

    // Item Binding
    val itemBinding = ItemBinding.of<String>(
        BR.itemValue, R.layout.adapter_item_flexbox_text
    )
}