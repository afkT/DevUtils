package afkt.project.framework.mvvm;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tt.whorlviewlibrary.WhorlView;

import afkt.project.BR;
import afkt.project.R;
import afkt.project.base.app.BaseMVVMActivity;
import afkt.project.databinding.ActivityArticleMvvmBinding;
import afkt.project.ui.adapter.ArticleAdapter;
import dev.utils.DevFinal;
import dev.utils.app.ViewUtils;
import dev.utils.common.CollectionUtils;
import dev.widget.assist.ViewAssist;
import dev.widget.function.StateLayout;

/**
 * detail: 文章 MVVM Activity
 * @author Ttt
 */
public class ArticleMVVMActivity
        extends BaseMVVMActivity<ActivityArticleMvvmBinding, ArticleViewModel> {

    // 加载动画
    WhorlView whorlView;

    @Override
    public int baseContentId() {
        return R.layout.activity_article_mvvm;
    }

    @Override
    public void initOrder() {
        initViewModel();
        super.initOrder();
    }

    @Override
    public void initView() {
        super.initView();

        setSupportActionBar(binding.vidAamToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 设置点击事件
        binding.vidAamToolbar.setNavigationOnClickListener(v -> finish());

        // 获取标题
        String title = getIntent().getStringExtra(DevFinal.TITLE);
        // 设置标题
        binding.setTitle(title); // 或用下面设置
        binding.setVariable(BR.title, title); // 设置后, 会动态刷新

        // 初始化 View
        View view = binding.vidAamState.getView(ViewAssist.TYPE_ING);
        whorlView = ViewUtils.findViewById(view, R.id.vid_sli_load_view);
    }

    @Override
    public void initValue() {
        super.initValue();
        // 初始化布局管理器、适配器
        binding.vidAamRecy.setLayoutManager(new LinearLayoutManager(this));
        binding.vidAamRecy.setAdapter(new ArticleAdapter());
    }

    @Override
    public void initListener() {
        super.initListener();
        // 设置监听
        binding.vidAamState.setListener(new StateLayout.Listener() {
            @Override
            public void onRemove(
                    StateLayout layout,
                    int type,
                    boolean removeView
            ) {
            }

            @Override
            public void onNotFound(
                    StateLayout layout,
                    int type
            ) {
                // 切换 View 操作
                if (type == ViewAssist.TYPE_SUCCESS) {
                    ViewUtils.reverseVisibilitys(true, binding.vidAamRecy, binding.vidAamState);
                }
            }

            @Override
            public void onChange(
                    StateLayout layout,
                    int type,
                    int oldType,
                    View view
            ) {
                // 判断是否操作成功
                boolean success = (type == ViewAssist.TYPE_SUCCESS);
                // 切换 View 操作
                if (ViewUtils.reverseVisibilitys(success, binding.vidAamRecy, binding.vidAamState)) {
                    // 属于请求成功
                } else {
                    if (type == ViewAssist.TYPE_ING) {
                        if (!whorlView.isCircling()) {
                            whorlView.start();
                        }
                    } else {
                        whorlView.stop();
                    }
                }
            }
        });
    }

    @Override
    public void initOther() {
        super.initOther();
        // 表示请求中
        binding.vidAamState.showIng();
        // 开始请求
        viewModel.requestArticleLists().observe(this, articleBean -> {
            if (articleBean != null) {
                if (CollectionUtils.isEmpty(articleBean.data.datas)) { // 无数据
                    binding.vidAamState.showEmptyData();
                } else { // 请求成功
                    binding.vidAamState.showSuccess();
                    // 设置数据源
                    ((ArticleAdapter) binding.vidAamRecy.getAdapter())
                            .setNewInstance(articleBean.data.datas);
                }
            } else {
                binding.vidAamState.showFailed();
            }
        });
    }

    @Override
    public void initViewModel() {
        viewModel = getActivityViewModel(ArticleViewModel.class);
        getLifecycle().addObserver(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止动画
        whorlView.stop();
    }
}