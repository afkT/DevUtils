package afkt.base.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import afkt.base.fragment.DevBaseFragment;

/**
 * detail: MVP Fragment 基类
 * @author Ttt
 * <pre>
 *     需要自己实现 Contract( 契约类 ) 用来管理 View 与 Presenter 的交互
 * </pre>
 */
public abstract class DevBaseMVPFragment<P extends MVP.Presenter> extends DevBaseFragment implements MVP.IView {

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
    public void onDestroy() {
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