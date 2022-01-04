package afkt.project.framework.mvvm

import afkt.project.BR
import afkt.project.R
import afkt.project.base.app.BaseMVVMActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityArticleMvvmBinding
import afkt.project.model.bean.ArticleBean
import afkt.project.ui.adapter.ArticleAdapter
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.tt.whorlviewlibrary.WhorlView
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.common.CollectionUtils
import dev.widget.assist.ViewAssist
import dev.widget.function.StateLayout

/**
 * detail: 文章 MVVM Activity
 * @author Ttt
 */
@Route(path = RouterPath.ArticleMVVMActivity_PATH)
class ArticleMVVMActivity : BaseMVVMActivity<ActivityArticleMvvmBinding, ArticleViewModel>() {

    // 加载动画
    var loadView: WhorlView? = null

    // 适配器
    var adapter = ArticleAdapter()

    override fun baseContentId(): Int = R.layout.activity_article_mvvm

    override fun initOrder() {
        initViewModel()
        super.initOrder()
    }

    override fun initView() {
        super.initView()

        setSupportActionBar(binding.vidToolbar)
        supportActionBar?.apply {
            // 给左上角图标的左边加上一个返回的图标
            setDisplayHomeAsUpEnabled(true)
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            setDisplayShowTitleEnabled(false)
        }

        // 设置点击事件
        binding.vidToolbar.setNavigationOnClickListener { v: View? -> finish() }

        // 获取标题
        val title = intent.getStringExtra(DevFinal.STR.TITLE)
        // 设置标题
        binding.title = title // 或用下面设置
        binding.setVariable(BR.title, title) // 设置后, 会动态刷新

        // 初始化 View
        val view = binding.vidState.getView(ViewAssist.TYPE_ING)
        loadView = ViewUtils.findViewById(view, R.id.vid_load_view)
    }

    override fun initValue() {
        super.initValue()
        // 初始化布局管理器、适配器
        adapter.bindAdapter(binding.vidRecy)
    }

    override fun initListener() {
        super.initListener()
        // 设置监听
        binding.vidState.setListener(object : StateLayout.Listener {
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
                    ViewUtils.reverseVisibilitys(true, binding.vidRecy, binding.vidState)
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
                        binding.vidRecy,
                        binding.vidState
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
        binding.vidState.showIng()
        // 开始请求
        viewModel.requestArticleLists().observe(this, Observer { articleBean: ArticleBean? ->
            articleBean?.data?.apply {
                if (CollectionUtils.isEmpty(datas)) { // 无数据
                    binding.vidState.showEmptyData()
                } else { // 请求成功
                    binding.vidState.showSuccess()
                    // 设置数据源
                    adapter.setDataList(datas)
                }
                return@Observer
            }
            binding.vidState.showFailed()
        })
    }

    override fun initViewModel() {
        getActivityViewModel(ArticleViewModel::class.java)?.apply {
            viewModel = this
//            lifecycle.addObserver(viewModel)
            viewModel.lifecycle(this@ArticleMVVMActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止动画
        loadView?.stop()
    }
}