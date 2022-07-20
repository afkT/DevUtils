package afkt.project.feature.framework.mvp

import afkt.project.R
import afkt.project.base.app.BaseMVPActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.framework.ArticleAdapter
import afkt.project.model.bean.ArticleBean
import afkt.project.model.item.RouterPath
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tt.whorlviewlibrary.WhorlView
import dev.utils.app.ViewUtils
import dev.utils.common.CollectionUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

/**
 * detail: 文章 MVP Activity
 * @author Ttt
 */
@Route(path = RouterPath.FRAMEWORK.ArticleMVPActivity_PATH)
class ArticleMVPActivity : BaseMVPActivity<ArticleMVP.Presenter, BaseViewRecyclerviewBinding>(),
    ArticleMVP.View {

    // 加载动画
    var loadView: WhorlView? = null

    // 适配器
    var adapter = ArticleAdapter()

    override fun createPresenter(): ArticleMVP.Presenter {
        return ArticleMVP.Presenter(this, this)
    }

    override fun baseLayoutId(): Int {
        return R.layout.base_view_recyclerview
    }

    override fun initView() {
        super.initView()
        // 初始化 View
        val view = stateLayout.getView(ViewAssist.TYPE_ING)
        loadView = ViewUtils.findViewById(view, R.id.vid_whv)
    }

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        adapter.bindAdapter(binding.vidRv)
    }

    override fun initListener() {
        super.initListener()
        // 设置监听
        stateLayout.setListener(object : StateLayout.Listener {
            override fun onRemove(
                layout: StateLayout,
                type: Int,
                removeView: Boolean
            ) {
            }

            override fun onNotFound(
                layout: StateLayout,
                type: Int
            ) {
                // 切换 View 操作
                if (type == ViewAssist.TYPE_SUCCESS) {
                    ViewUtils.reverseVisibilitys(
                        true,
                        contentAssist.contentLinear,
                        contentAssist.stateLinear
                    )
                }
            }

            override fun onChange(
                layout: StateLayout,
                type: Int,
                oldType: Int,
                view: View
            ) {
                // 判断是否操作成功
                val success = (type == ViewAssist.TYPE_SUCCESS)
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(
                        success,
                        contentAssist.contentLinear,
                        contentAssist.stateLinear
                    )
                ) {
                    // 属于请求成功
                } else {
                    if (type == ViewAssist.TYPE_ING) {
                        loadView?.apply {
                            if (!isCircling) start()
                        }
                    } else {
                        loadView?.stop()
                    }
                }
            }
        })
    }

    override fun initOther() {
        super.initOther()
        // 表示请求中
        stateLayout.showIng()
        // 获取文章列表
        presenter.articleLists()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止动画
        loadView?.stop()
    }

    // ===================
    // = ArticleMVP.View =
    // ===================

    override fun onArticleListResponse(
        succeed: Boolean,
        articleBean: ArticleBean?
    ) {
        articleBean?.data?.apply {
            if (CollectionUtils.isEmpty(datas)) { // 无数据
                stateLayout.showEmptyData()
            } else { // 请求成功
                stateLayout.showSuccess()
                // 设置数据源
                adapter.setDataList(datas)
            }
            return
        }
        // 请求失败
        stateLayout.showFailed()
    }
}