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

    // ========
    // = MVVM =
    // ========

    // https://www.jianshu.com/p/53925ccb900e

    // http://blog.zhaiyifan.cn/2016/06/16/android-new-project-from-0-p7/

    // Data Binding 顾名思义 数据绑定
    // 是 Google 对 MVVM 在 Android 上的一种实现, 可以直接绑定数据到 xml 中, 并实现自动刷新
    // 现在最新的版本还支持双向绑定, 尽管使用场景不是那么多

    // 将数据分解到各个 view、在 UI 线程上更新数据、监控数据的变化, 实时更新, 这样一来
    // 你要展示的数据已经和展示它的布局紧紧绑定在了一起 我认为这才是 DataBinding 真正的魅力所在

    // ===========================
    // = MVVM 模式包含了三个部分 =
    // ===========================

    // Model 代表你的基本业务逻辑, 如数据获取、数据模型等, 即 DataModel 抽象数据源, ViewModel 从 Model 中读取或存储数据
    // View 显示内容, 当用户触发响应事件时, 通知 ViewModel 展示提供的数据
    // ViewModel 将前面两者联系在一起的对象 View + model = ViewModel

    // 它采用双向绑定 ( data-binding ) : View 的变动自动反映在 ViewModel 反之亦然, Angular 和 Ember 都采用这种模式
    // View 把用户事件都交给 ViewModel 处理, 不保留任何 UI 逻辑

    // 主要等同于, 写了个类  ActivityArticleMvvmBinding ( 自动生成 ), 内部初始化 View, 并且改变值等方法, 都会同步刷新, 调用 View 对应的方法
    // 并且 ViewModel 持有 Binding 类对象, 内部做逻辑处理等, 最后通过 Binding 类通知改变等

    // =======
    // = MVP =
    // =======

    // Model: 数据部分
    // View: 主要负责界面的显示及跟数据无关的逻辑, 比如设置控件的点击事件等
    // Presenter: 主要负责 View 与 Model 的交互

    // MVP 很好的解决了 View 层与 Model 层的分离, 使之交互都是通过 Presenter 层来做, 这样做得好处有以下几点:
    // 1 便于单元测试, 因为对于 Model 层或者 Presenter 来说, 都是一些接口, 便于编写测试用例
    // 2 维护性提高, 对于 View 层来说的改动不影响 Presenter 和 Model 层的改动

    // 最主要的好处就是以上两点, 坏处也有以下几点:
    // 1 代码量增加, 特别是需要新增加 View 与 Model, 及 Presenter 类
    // 2 View 与 Presenter 的交互的接口的粒度不好把握, 这个需要深入的理解业务才能好好解决

    // MVP 的核心是:
    // View 层不持有 Model 层对象任何引用, 当然参数里面和临时变量里可以有 Model 层对象, 只持有 Presenter 层对象引用, 任何需要更新或者操作数据的
    // 都间接通过 Presenter 对象去操作数据, 而 Model 层想要操作 View 层是无法实现的, 必须通过 Presenter 层

    // Presenter 层持有 View 层对象的引用, 除此之外不持有其他的 UI 控件等的引用, Model 层会把想要更新 View 的操作委托 Presenter 去操作,
    // 而 Presenter 层会把更新 View 操作交给 View 层对象去操作
}