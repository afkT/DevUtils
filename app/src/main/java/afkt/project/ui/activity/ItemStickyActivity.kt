package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.ItemStickyBean
import afkt.project.ui.adapter.ItemStickyAdapter
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.gavin.com.library.PowerfulStickyDecoration
import com.gavin.com.library.StickyDecoration
import com.gavin.com.library.listener.GroupListener
import com.gavin.com.library.listener.PowerGroupListener
import dev.engine.log.DevLogEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.SizeUtils
import dev.utils.app.TextViewUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils
import java.util.*

/**
 * detail: RecyclerView 吸附效果
 * @author Ttt
 * RecyclerView 实现顶部吸附效果
 * @see https://github.com/Gavin-ZYX/StickyDecoration
 */
@Route(path = RouterPath.ItemStickyActivity_PATH)
class ItemStickyActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // 适配器
    lateinit var itemStickyAdapter: ItemStickyAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun initValue() {
        super.initValue()

        val parent = binding.vidBvrRecy.parent as? ViewGroup
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)

        // ====================
        // = 使用自定义悬浮 View =
        // ====================

        val listener: PowerGroupListener = object : PowerGroupListener {
            override fun getGroupName(position: Int): String {
                return itemStickyAdapter.getDataItem(position).timeTile
            }

            override fun getGroupView(position: Int): View? {
                DevLogEngine.getEngine()?.dTag(TAG, position.toString())
                val view = layoutInflater.inflate(R.layout.adapter_sticky_view, null, false)
                TextViewUtils.setText(
                    view.findViewById(R.id.vid_asv_title_tv),
                    getGroupName(position)
                )
                return view
            }
        }

        val decoration1 = PowerfulStickyDecoration.Builder
            .init(listener)
            .setGroupHeight(SizeUtils.dipConvertPx(50.0f))
//            // 重置 span ( 注意 : 使用 GridLayoutManager 时必须调用 )
//            .resetSpan(mRecyclerView, (GridLayoutManager) manager)
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
            .setGroupTextSize(SizeUtils.spConvertPx(15.0f))
            .setTextSideMargin(SizeUtils.dipConvertPx(10.0f))
            .build()

        // 初始化布局管理器、适配器
        itemStickyAdapter = ItemStickyAdapter(list)
        binding.vidBvrRecy.adapter = itemStickyAdapter
        binding.vidBvrRecy.addItemDecoration(decoration)
    }

    private val list: List<ItemStickyBean>
        private get() {
            val lists: MutableList<ItemStickyBean> = ArrayList()
            var time = System.currentTimeMillis()
            for (i in 0..9) {
                val number = RandomUtils.getRandom(4, 10)
                time -= DateUtils.DAY * number
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