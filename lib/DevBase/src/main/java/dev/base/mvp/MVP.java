package dev.base.mvp;

/**
 * detail: MVP 类
 * @author Ttt
 */
public final class MVP {

    private MVP() {
    }

    /**
     * detail: MVP 模式的 Model ( 通常作为获取数据 )
     * @author Ttt
     */
    public interface IModel {
    }

    /**
     * detail: MVP 模式的 View ( 通过 Presenter 将数据传入到该层, 负责 View 的展示相关 )
     * @author Ttt
     */
    public interface IView {
    }

    /**
     * detail: MVP 模式 P 层接口类
     * @author Ttt
     */
    public interface IPresenter<V extends IView> {

        /**
         * 设置 View 层与 P 层 关联持有
         * @param view {@link IView}
         */
        void attachView(V view);

        /**
         * 销毁 View 与 P 层 关联关系
         */
        void detachView();
    }

    /**
     * detail: MVP 模式的指挥者 ( 连接 View 和 Model)
     * @author Ttt
     */
    public class Presenter<V extends IView, M extends IModel> implements IPresenter<V> {

        // IView
        protected V mView;
        // IModel
        protected M mModel;

        @Override
        public void attachView(V view) {
            mView = view;
        }

        @Override
        public void detachView() {
            mView = null;
        }
    }
}