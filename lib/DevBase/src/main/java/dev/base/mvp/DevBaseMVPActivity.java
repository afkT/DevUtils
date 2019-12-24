package dev.base.mvp;

import android.os.Bundle;

import dev.base.activity.DevBaseActivity;

/**
 * detail: MVP Activity 基类
 * @author Ttt
 * <pre>
 *     需要自己实现 Contract( 契约类 ) 用来管理 View 与 Presenter 的交互
 * </pre>
 */
public abstract class DevBaseMVPActivity<P extends MVP.Presenter> extends DevBaseActivity implements MVP.IView {

    // MVP Presenter
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 创建 MVP 模式的 Presenter
        createPresenter();
        // 初始化操作
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消 MVP 各个模块间的关联
        if (mPresenter != null) mPresenter.detachView();
    }

    /**
     * 创建 MVP 模式的 Presenter
     */
    private void createPresenter() {
        // 初始化 Presenter
        mPresenter = presenter();
        // 绑定关联
        if (mPresenter != null) mPresenter.attachView(this);
    }

    // ====================
    // = 对外要求实现方法 =
    // ====================

    /**
     * 初始化创建 Presenter
     * @return {@link MVP.Presenter}
     */
    protected abstract P presenter();
}