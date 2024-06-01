package afkt.project.feature.ui_effect.recy_adapter.pager_snap

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.bean.ItemBean
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.utils.app.ListViewUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: PagerSnapHelper - 无限滑动
 * @author Ttt
 * PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 */
@Route(path = RouterPath.UI_EFFECT.PagerSnapMAXActivity_PATH)
class PagerSnapMAXActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is PagerSnapMAXActivity) {
            it.apply {
                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)

                val lists = mutableListOf<ItemBean>()
                for (i in 0..9) lists.add(ItemBean.newItemBeanPager())

                // 初始化布局管理器、适配器
                val adapter = PagerSnapMAXAdapter(lists)
                binding.vidRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) // VERTICAL
                adapter.bindAdapter(binding.vidRv)
                val helper = PagerSnapHelper()
                helper.attachToRecyclerView(binding.vidRv)
                val size = lists.size
                // 滑动到中间 ( 无滑动过程 )
                (binding.vidRv.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                    size * 100 - 1, 10
                )
                // 复位到中间
                ListViewUtils.smoothScrollToPosition(binding.vidRv, size * 100 + 1)

                binding.vidRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            val layoutManager = recyclerView.layoutManager
                            if (layoutManager is LinearLayoutManager) {
                                // 获取最后一个可见 view 的位置
                                val lastItemPosition = layoutManager.findLastVisibleItemPosition()
                                // 获取第一个可见 view 的位置
                                val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
                                // 获取居中索引
                                val currentPosition = (lastItemPosition + firstItemPosition) / 2
                                // 真实索引
                                val index = adapter.getRealIndex(currentPosition)
                                TAG.log_dTag(
                                    message = "%s - %s 当前显示索引: %s - %s",
                                    args = arrayOf(
                                        lastItemPosition,
                                        firstItemPosition,
                                        currentPosition,
                                        index
                                    )
                                )
                            }
                        }
                    }
                })
            }
        }
    }
)