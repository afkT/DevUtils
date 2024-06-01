package afkt.project.feature.ui_effect.recy_adapter.item_sticky

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.View
import android.view.ViewGroup
import com.gavin.com.library.PowerfulStickyDecoration
import com.gavin.com.library.StickyDecoration
import com.gavin.com.library.listener.GroupListener
import com.gavin.com.library.listener.PowerGroupListener
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.mvvm.utils.size.AppSize
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils
import java.util.*

/**
 * detail: RecyclerView 吸附效果
 * @author Ttt
 * RecyclerView 实现顶部吸附效果
 * @see https://github.com/Gavin-ZYX/StickyDecoration
 */
@Route(path = RouterPath.UI_EFFECT.ItemStickyActivity_PATH)
class ItemStickyActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is ItemStickyActivity) {
            it.apply {
                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)

                // ====================
                // = 使用自定义悬浮 View =
                // ====================

                val listener = object : PowerGroupListener {
                    override fun getGroupName(position: Int): String {
                        return itemStickyAdapter.getDataItem(position).timeTile
                    }

                    override fun getGroupView(position: Int): View? {
                        TAG.log_dTag(
                            message = position.toString()
                        )
                        val view = layoutInflater.inflate(R.layout.adapter_sticky_view, null, false)
                        TextViewUtils.setText(
                            view.findViewById(R.id.vid_title_tv),
                            getGroupName(position)
                        )
                        return view
                    }
                }

                val decoration1 = PowerfulStickyDecoration.Builder
                    .init(listener)
                    .setGroupHeight(AppSize.dp2px(50F))
//                    // 重置 span ( 注意 : 使用 GridLayoutManager 时必须调用 )
//                    .resetSpan(mRecyclerView, (GridLayoutManager) manager)
                    .build()

                // ===============
                // = 默认悬浮 View =
                // ===============

                val groupListener = GroupListener { position ->
                    itemStickyAdapter.getDataItem(position).timeTile
                }

                val decoration = StickyDecoration.Builder.init(groupListener)
                    .setGroupBackground(ResourceUtils.getColor(R.color.color_f7))
                    .setGroupTextColor(ResourceUtils.getColor(R.color.color_33))
                    .setGroupTextSize(AppSize.sp2px(15.0F))
                    .setTextSideMargin(AppSize.dp2px(10.0F))
                    .build()

                // 初始化布局管理器、适配器
                itemStickyAdapter = ItemStickyAdapter(list)
                binding.vidRv.addItemDecoration(decoration)
                itemStickyAdapter.bindAdapter(binding.vidRv)
            }
        }
    }
) {

    // 适配器
    lateinit var itemStickyAdapter: ItemStickyAdapter

    private val list: List<ItemStickyBean>
        get() {
            val lists = mutableListOf<ItemStickyBean>()
            var time = System.currentTimeMillis()
            for (i in 0..9) {
                val number = RandomUtils.getRandom(4, 10)
                time -= DevFinal.TIME.DAY_MS * number
                for (y in 0..number) {
                    lists.add(
                        ItemStickyBean(
                            ChineseUtils.randomWord(RandomUtils.getRandom(3, 12)),
                            time
                        )
                    )
                }
            }
            return lists
        }
}