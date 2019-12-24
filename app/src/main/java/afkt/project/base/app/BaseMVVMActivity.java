package afkt.project.base.app;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

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

    // =======
    // = MVC =
    // =======

    // Android 中界面部分也采用了当前比较流行的 MVC 框架, 在 Android 中

    // 1 视图层 ( View ) : 一般采用 XML 文件进行界面的描述, 使用的时候可以非常方便的引入

    // 2 控制层 ( Controller ) : Android 的控制层的重任通常落在了众多的 Acitvity 的肩上, 这句话也就暗含了不要在 Acitivity 中写代码
    // 要通过 Activity 交割 Model 业务逻辑层处理, 这样做的另外一个原因是 Android 中的 Acitivity 的响应时间是 5s, 如果耗时的操作放在这里, 程序就很容易被回收掉

    // 3 模型层 (Model ) : 对数据库的操作、对网络等的操作都应该在 Model 里面处理, 当然对业务计算等操作也是必须放在的该层的
    // 在 Android SDK 中的数据绑定, 也都是采用了与 MVC 框架类似的方法来显示数据
    // 在控制层上将数据按照视图模型的要求 ( 也就是 Android SDK 中的 Adapter ) 封装就可以直接在视图模型上显示了
    // 从而实现了数据绑定, 比如显示 Cursor 中所有数据的 ListActivity, 其视图层就是一个 ListView
    // 将数据封装为 ListAdapter 并传递给 ListView, 数据就在 ListView 中显示

    // 正常 Activity 就起到了 Controller 的角色, 除非非常复杂的逻辑, 以及页面功能等, 才会单独分装 Control 类
}