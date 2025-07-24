package afkt.project.features.ui_effect.recycler_view

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.AdapterItemStickyBinding
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.data.bean.AdapterBean
import android.view.View
import android.view.ViewGroup
import com.gavin.com.library.PowerfulStickyDecoration
import com.gavin.com.library.StickyDecoration
import com.gavin.com.library.listener.GroupListener
import com.gavin.com.library.listener.PowerGroupListener
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.log.log_dTag
import dev.mvvm.utils.size.AppSize
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils

/**
 * detail: RecyclerView 吸附效果
 * @author Ttt
 * RecyclerView 实现顶部吸附效果
 * @see https://github.com/Gavin-ZYX/StickyDecoration
 */
class ItemStickyFragment : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is ItemStickyFragment) {
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

/**
 * detail: Item 实体类
 * @author Ttt
 */
class ItemStickyBean(
    // 标题
    title: String,
    // 时间
    time: Long
) : AdapterBean(title, "") {

    // 时间格式化
    val timeFormat: String

    // 吸附标题
    val timeTile: String

    init {
        val format = DevFinal.TIME.yyyyMMdd_POINT
        // 进行格式化
        timeFormat = DateUtils.formatTime(time, format)
        // 获取当前时间
        val currentTime = DateUtils.getDateNow(format)
        // 设置标题
        timeTile = if (currentTime == timeFormat) {
            "今日"
        } else {
            DateUtils.formatTime(time, DevFinal.TIME.ZH_MMdd)
        }
    }
}

/**
 * detail: 吸附 Item 预览 View Adapter
 * @author Ttt
 */
class ItemStickyAdapter(data: List<ItemStickyBean>) :
    DevDataAdapter<ItemStickyBean, DevBaseViewBindingVH<AdapterItemStickyBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemStickyBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_sticky)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemStickyBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        holder.binding.vidTitleTv.text = item.title
        holder.binding.vidTimeTv.text = item.timeFormat
    }
}