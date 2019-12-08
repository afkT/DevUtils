package afkt.project.base.app;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 * <pre>
 *     MVVM 需要传入 ViewDataBinding
 * </pre>
 */
public abstract class BaseMVVMActivity<VDB extends ViewDataBinding> extends BaseActivity {

    protected VDB viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // MVVM 只需要调用此句绑定
//        viewDataBinding = DataBindingUtil.bind(mContentView);
//        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消 MVVM 绑定
        if (viewDataBinding != null) {
            viewDataBinding.unbind();
        }
    }
}