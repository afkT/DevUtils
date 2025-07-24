package afkt.project.features.ui_effect.recycler_view

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectItemStickyBinding
import afkt.project.model.basic.AdapterModel
import android.annotation.SuppressLint
import android.view.View
import com.gavin.com.library.PowerfulStickyDecoration
import com.gavin.com.library.StickyDecoration
import com.gavin.com.library.listener.GroupListener
import com.gavin.com.library.listener.PowerGroupListener
import dev.expand.engine.log.log_dTag
import dev.mvvm.utils.size.AppSize
import dev.simple.app.base.asFragment
import dev.utils.DevFinal
import dev.utils.app.TextViewUtils
import dev.utils.app.assist.ResourceColor
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils
import dev.utils.common.StringUtils
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: RecyclerView 吸附效果
 * @author Ttt
 */
class ItemStickyFragment : AppFragment<FragmentUiEffectItemStickyBinding, ItemStickyViewModel>(
    R.layout.fragment_ui_effect_item_sticky, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ItemStickyFragment> {

            // ====================
            // = 使用自定义悬浮 View =
            // ====================

            val listener = object : PowerGroupListener {
                override fun getGroupName(position: Int): String {
                    val title = viewModel.adapter.getOrNull(position)?.stickyTile
                    return StringUtils.checkValue(title)
                }

                @SuppressLint("InflateParams")
                override fun getGroupView(position: Int): View {
                    TAG.log_dTag(message = position.toString())

                    val view = layoutInflater.inflate(
                        R.layout.adapter_item_sticky_title, null
                    )
                    TextViewUtils.setText(
                        view.findViewById(R.id.vid_title_tv),
                        getGroupName(position)
                    )
                    return view
                }
            }

            val titleDecorationTemp = PowerfulStickyDecoration.Builder
                .init(listener)
                .setGroupHeight(AppSize.dp2px(50F))
                .build()

            // ===============
            // = 默认悬浮 View =
            // ===============

            val groupListener = GroupListener { position ->
                val title = viewModel.adapter.getOrNull(position)?.stickyTile
                StringUtils.checkValue(title)
            }

            val titleDecoration = StickyDecoration.Builder.init(groupListener)
                .setGroupBackground(ResourceColor.get().getColor(R.color.color_f7))
                .setGroupTextColor(ResourceColor.get().getColor(R.color.color_33))
                .setGroupTextSize(AppSize.sp2px(15.0F))
                .setTextSideMargin(AppSize.dp2px(10.0F))
                .build()
            binding.vidRv.addItemDecoration(titleDecoration)
        }
    }
)

class ItemStickyViewModel : AppViewModel() {

    val adapter = ItemStickyAdapter().apply {
        addAllAndClear(ItemStickyModel.randomList())
    }
}

class ItemStickyAdapter() : AdapterModel<ItemStickyModel>() {

    // Item Binding
    val itemBinding = ItemBinding.of<ItemStickyModel>(
        BR.itemValue, R.layout.adapter_item_sticky
    )
}

/**
 * detail: 吸附 Item 数据模型
 * @author Ttt
 */
class ItemStickyModel(
    // 标题
    val title: String,
    // 时间
    time: Long
) {

    // 吸附标题
    val stickyTile: String

    // 时间格式化
    val timeFormat: String

    init {
        val format = DevFinal.TIME.yyyyMMdd_POINT
        // 进行格式化
        timeFormat = DateUtils.formatTime(time, format)
        // 获取当前时间
        val currentTime = DateUtils.getDateNow(format)
        // 设置标题
        stickyTile = if (currentTime == timeFormat) {
            "今日"
        } else {
            DateUtils.formatTime(time, DevFinal.TIME.ZH_MMdd)
        }
    }

    companion object {

        fun randomList(): MutableList<ItemStickyModel> {
            val lists = mutableListOf<ItemStickyModel>()
            var currentTime = System.currentTimeMillis()
            for (i in 0..9) {
                val number = RandomUtils.getRandom(4, 10)
                currentTime -= DevFinal.TIME.DAY_MS * number
                for (y in 0..number) {
                    val title = ChineseUtils.randomWord(RandomUtils.getRandom(3, 12))
                    lists.add(ItemStickyModel(title, currentTime))
                }
            }
            return lists
        }
    }
}