package dev.base.expand.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import dev.base.expand.viewbinding.DevBaseViewBindingFragment;

/**
 * detail: MVP Fragment ViewBinding 基类
 * @author Ttt
 */
public abstract class DevBaseMVPViewBindingFragment<P extends MVP.Presenter, VB extends ViewBinding>
        extends DevBaseViewBindingFragment<VB> implements MVP.IView {

    // MVP Presenter
    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 创建 MVP 模式的 Presenter
        createPresenter();
        // 底层初始化操作
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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