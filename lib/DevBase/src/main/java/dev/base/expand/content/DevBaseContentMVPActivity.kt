package dev.base.expand.content;

import android.os.Bundle;

import dev.base.expand.mvp.MVP;

/**
 * detail: Content MVP Activity 基类
 * @author Ttt
 */
public abstract class DevBaseContentMVPActivity<P extends MVP.Presenter> extends DevBaseContentActivity implements MVP.IView {

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