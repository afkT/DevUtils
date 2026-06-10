package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineRefreshBinding
import android.os.Bundle
import android.view.View
import dev.engine.core.refresh.RefreshItem
import dev.engine.extensions.refresh.*
import dev.engine.extensions.toast.toast_showShort
import dev.engine.refresh.IRefreshEngine

/**
 * detail: Refresh Engine 下拉刷新
 * @author Ttt
 */
class RefreshFragment : AppFragment<FragmentDevEngineRefreshBinding, RefreshViewModel>(
    R.layout.fragment_dev_engine_refresh, BR.viewModel
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(binding.vidRefresh)
    }
}

/**
 * detail: Refresh Engine 下拉刷新 ViewModel
 * @author Ttt
 */
class RefreshViewModel : AppViewModel() {

    // 模拟加载耗时
    private val mockDelay = 1500L

    // 刷新次数
    private var refreshCount = 0

    // Refresh View Item
    private var refreshItem: RefreshItem? = null

    /**
     * 初始化下拉刷新
     * @param view Refresh View ( SmartRefreshLayout )
     */
    fun initialize(view: View) {
        val item = RefreshItem.create(view)
        refreshItem = item
        // 开启下拉刷新、上拉加载更多
        item.refresh_setEnableRefresh(enabled = true)
        item.refresh_setEnableLoadMore(enabled = true)
        // 设置刷新、加载更多监听
        item.refresh_setOnRefreshLoadMoreListener(
            listener = object : IRefreshEngine.OnRefreshLoadMoreListener {
                override fun onRefresh(refreshView: View) {
                    refreshView.postDelayed({
                        refreshCount++
                        "刷新成功, 第 $refreshCount 次".toast_showShort()
                        item.refresh_finishRefresh()
                    }, mockDelay)
                }

                override fun onLoadMore(refreshView: View) {
                    refreshView.postDelayed({
                        "加载更多完成".toast_showShort()
                        item.refresh_finishLoadMore()
                    }, mockDelay)
                }
            }
        )
    }
}