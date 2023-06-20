package dev.widget.assist;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.HashMap;
import java.util.Map;

import dev.utils.app.ViewUtils;
import dev.utils.app.anim.ViewAnimationUtils;
import dev.utils.common.ForUtils;

/**
 * detail: View 填充辅助类
 * @author Ttt
 * <pre>
 *     作用于 加载中、加载失败、无数据等视图或自定义视图 填充包裹 View
 *     <p></p>
 *     使用方式:
 *     // 首先创建 ViewAssist
 *     ViewAssist assist = ViewAssist.wrap(ViewGroup);
 *     assist.register(100, new Adapter()); // 注册 Type - Adapter
 *     assist.showType(100); // 显示对应 Type Adapter View
 * </pre>
 */
public final class ViewAssist {

    private ViewAssist() {
    }

    // 标记 TAG
    private Object    mTag;
    // 包裹 View
    private ViewGroup mWrapper;
    // 模型, 默认为隐藏 View 操作
    private Model     mModel = Model.GONE;

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(final ViewGroup wrapper) {
        return wrap(wrapper, null, null);
    }

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @param model   View 操作模式
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(
            final ViewGroup wrapper,
            final Model model
    ) {
        return wrap(wrapper, model, null);
    }

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @param tag     TAG
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(
            final ViewGroup wrapper,
            final Object tag
    ) {
        return wrap(wrapper, null, tag);
    }

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @param model   View 操作模式
     * @param tag     TAG
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(
            final ViewGroup wrapper,
            final Model model,
            final Object tag
    ) {
        if (wrapper == null) return null;
        ViewAssist assist = new ViewAssist();
        assist.mWrapper = wrapper;
        assist.mTag     = tag;
        if (model != null) {
            assist.mModel = model;
        }
        return assist;
    }

    // =============
    // = 对外公开方法 =
    // =============

    // Type: 操作中、失败、成功、空数据
    public static final int TYPE_ING        = 1;
    public static final int TYPE_FAILED     = 2;
    public static final int TYPE_SUCCESS    = 3;
    public static final int TYPE_EMPTY_DATA = 4;

    /**
     * 显示 Type Adapter View
     * @param type Type
     */
    public void showType(final int type) {
        innerShowType(type, true);
    }

    /**
     * 显示 Type Adapter View
     * @param type       Type
     * @param notFoundOP 是否处理未找到操作
     */
    public void showType(
            final int type,
            final boolean notFoundOP
    ) {
        innerShowType(type, notFoundOP);
    }

    // =

    public void showIng() {
        showType(TYPE_ING);
    }

    public void showFailed() {
        showType(TYPE_FAILED);
    }

    public void showSuccess() {
        showType(TYPE_SUCCESS);
    }

    public void showEmptyData() {
        showType(TYPE_EMPTY_DATA);
    }

    // =

    public void showIng(final boolean notFoundOP) {
        showType(TYPE_ING, notFoundOP);
    }

    public void showFailed(final boolean notFoundOP) {
        showType(TYPE_FAILED, notFoundOP);
    }

    public void showSuccess(final boolean notFoundOP) {
        showType(TYPE_SUCCESS, notFoundOP);
    }

    public void showEmptyData(final boolean notFoundOP) {
        showType(TYPE_EMPTY_DATA, notFoundOP);
    }

    // ============
    // = function =
    // ============

    /**
     * 获取包裹 View
     * @return {@link ViewGroup}
     */
    public ViewGroup getWrapper() {
        return mWrapper;
    }

    /**
     * 包裹 View 是否隐藏
     * @return {@code true} yes, {@code false} no
     */
    public boolean isGoneWrapper() {
        return ViewUtils.isVisibilityGone(mWrapper);
    }

    /**
     * 包裹 View 是否显示
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVisibleWrapper() {
        return ViewUtils.isVisibility(mWrapper);
    }

    /**
     * 隐藏包裹 View
     * @return {@link ViewAssist}
     */
    public ViewAssist goneWrapper() {
        ViewUtils.setVisibility(false, mWrapper);
        return this;
    }

    /**
     * 显示包裹 View
     * @return {@link ViewAssist}
     */
    public ViewAssist visibleWrapper() {
        ViewUtils.setVisibility(true, mWrapper);
        return this;
    }

    // =

    /**
     * 隐藏包裹 View 并设置渐隐动画
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@link ViewAssist}
     */
    public ViewAssist goneWrapper(
            final long durationMillis,
            final boolean isBanClick,
            final Animation.AnimationListener animationListener
    ) {
        ViewAnimationUtils.goneViewByAlpha(
                mWrapper, durationMillis, isBanClick, animationListener
        );
        return this;
    }

    /**
     * 显示包裹 View 并设置渐显动画
     * @param durationMillis    动画持续时间
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     * @return {@link ViewAssist}
     */
    public ViewAssist visibleWrapper(
            final long durationMillis,
            final boolean isBanClick,
            final Animation.AnimationListener animationListener
    ) {
        ViewAnimationUtils.visibleViewByAlpha(
                mWrapper, durationMillis, isBanClick, animationListener
        );
        return this;
    }

    // =

    /**
     * 重置处理
     * @return {@link ViewAssist}
     */
    public ViewAssist reset() {
        return innerReset();
    }

    /**
     * 注册 type
     * @param type    Type
     * @param adapter {@link Adapter}
     * @return {@link ViewAssist}
     */
    public ViewAssist register(
            final int type,
            final Adapter adapter
    ) {
        return innerRegister(type, adapter);
    }

    /**
     * 取消注册 type
     * @param type Type
     * @return {@link ViewAssist}
     */
    public ViewAssist unregister(final int type) {
        return innerUnregister(type, true);
    }

    /**
     * 取消注册 type
     * @param type   Type
     * @param remove 判断解绑的 type 正在显示是否删除
     * @return {@link ViewAssist}
     */
    public ViewAssist unregister(
            final int type,
            final boolean remove
    ) {
        return innerUnregister(type, remove);
    }

    // =

    public ViewAssist registerIng(final Adapter adapter) {
        return register(TYPE_ING, adapter);
    }

    public ViewAssist registerFailed(final Adapter adapter) {
        return register(TYPE_FAILED, adapter);
    }

    public ViewAssist registerSuccess(final Adapter adapter) {
        return register(TYPE_SUCCESS, adapter);
    }

    public ViewAssist registerEmptyData(final Adapter adapter) {
        return register(TYPE_EMPTY_DATA, adapter);
    }

    // =

    public ViewAssist unregisterIng() {
        return unregister(TYPE_ING);
    }

    public ViewAssist unregisterFailed() {
        return unregister(TYPE_FAILED);
    }

    public ViewAssist unregisterSuccess() {
        return unregister(TYPE_SUCCESS);
    }

    public ViewAssist unregisterEmptyData() {
        return unregister(TYPE_EMPTY_DATA);
    }

    // =

    public ViewAssist unregisterIng(final boolean remove) {
        return unregister(TYPE_ING, remove);
    }

    public ViewAssist unregisterFailed(final boolean remove) {
        return unregister(TYPE_FAILED, remove);
    }

    public ViewAssist unregisterSuccess(final boolean remove) {
        return unregister(TYPE_SUCCESS, remove);
    }

    public ViewAssist unregisterEmptyData(final boolean remove) {
        return unregister(TYPE_EMPTY_DATA, remove);
    }

    // =

    public boolean isTypeView(final int type) {
        return mCurrentType == type;
    }

    public boolean isTypeIng() {
        return isTypeView(TYPE_ING);
    }

    public boolean isTypeFailed() {
        return isTypeView(TYPE_FAILED);
    }

    public boolean isTypeSuccess() {
        return isTypeView(TYPE_SUCCESS);
    }

    public boolean isTypeEmptyData() {
        return isTypeView(TYPE_EMPTY_DATA);
    }

    // =

    public Adapter getAdapter(final int type) {
        return mMapAdapters.get(type);
    }

    public Adapter getAdapterByIng() {
        return getAdapter(TYPE_ING);
    }

    public Adapter getAdapterByFailed() {
        return getAdapter(TYPE_FAILED);
    }

    public Adapter getAdapterBySuccess() {
        return getAdapter(TYPE_SUCCESS);
    }

    public Adapter getAdapterByEmptyData() {
        return getAdapter(TYPE_EMPTY_DATA);
    }

    // =

    public View getView(final int type) {
        return innerGetView(type);
    }

    public View getViewByIng() {
        return getView(TYPE_ING);
    }

    public View getViewByFailed() {
        return getView(TYPE_FAILED);
    }

    public View getViewBySuccess() {
        return getView(TYPE_SUCCESS);
    }

    public View getViewByEmptyData() {
        return getView(TYPE_EMPTY_DATA);
    }

    // =

    public ViewAssist notifyDataSetChanged() {
        return innerNotifyDataSetChanged(mCurrentType);
    }

    public ViewAssist notifyDataSetChanged(final int type) {
        return innerNotifyDataSetChanged(type);
    }

    public ViewAssist notifyDataSetChangedByIng() {
        return notifyDataSetChanged(TYPE_ING);
    }

    public ViewAssist notifyDataSetChangedByFailed() {
        return notifyDataSetChanged(TYPE_FAILED);
    }

    public ViewAssist notifyDataSetChangedBySuccess() {
        return notifyDataSetChanged(TYPE_SUCCESS);
    }

    public ViewAssist notifyDataSetChangedByEmptyData() {
        return notifyDataSetChanged(TYPE_EMPTY_DATA);
    }

    // ===========
    // = get/set =
    // ===========

    public int getCurrentType() {
        return mCurrentType;
    }

    public View getCurrentView() {
        return mCurrentView;
    }

    public ViewAssist setListener(final Listener listener) {
        mListener = listener;
        return this;
    }

    public Object getTag() {
        return mTag;
    }

    public ViewAssist setTag(final Object tag) {
        mTag = tag;
        return this;
    }

    public Object getData(final String key) {
        return mMapDatas.get(key);
    }

    public ViewAssist setData(
            final String key,
            final Object data
    ) {
        mMapDatas.put(key, data);
        return this;
    }

    public boolean isGoneModel() {
        return mModel == Model.GONE;
    }

    public boolean isRemoveModel() {
        return mModel == Model.REMOVE;
    }

    /**
     * 更新 Model 为隐藏 View
     * @return {@link ViewAssist}
     */
    public ViewAssist changeModelGone() {
        return innerChangeModel(Model.GONE);
    }

    /**
     * 更新 Model 为移除 View
     * @return {@link ViewAssist}
     */
    public ViewAssist changeModelRemove() {
        return innerChangeModel(Model.REMOVE);
    }

    // ============
    // = 接口、枚举 =
    // ============

    /**
     * detail: View 操作模式
     * @author Ttt
     */
    public enum Model {

        // 隐藏 View 操作
        GONE,

        // 移除 View 操作
        REMOVE
    }

    /**
     * detail: View Adapter
     * @author Ttt
     */
    public interface Adapter {

        View onCreateView(
                ViewAssist assist,
                LayoutInflater inflater
        );

        void onBindView(
                ViewAssist assist,
                View view,
                int type
        );
    }

    /**
     * detail: ViewType 切换事件
     * @author Ttt
     */
    public interface Listener {

        void onRemove(
                ViewAssist assist,
                int type,
                boolean removeView
        );

        void onNotFound(
                ViewAssist assist,
                int type
        );

        void onChange(
                ViewAssist assist,
                int type,
                int oldType
        );
    }

    // ==========
    // = 内部方法 =
    // ==========

    private final Map<String, Object>   mMapDatas    = new HashMap<>();
    private final Map<Integer, Adapter> mMapAdapters = new HashMap<>();
    private final SparseArray<View>     mTypeViews   = new SparseArray<>(3);
    private       int                   mCurrentType = -1;
    private       View                  mCurrentView;
    private       Listener              mListener;

    /**
     * 获取 View 方法
     * @param type ViewType
     * @return 对应 Type View
     */
    private View innerGetView(final int type) {
        View view = mTypeViews.get(type);
        if (view != null) return view;
        Adapter adapter = mMapAdapters.get(type);
        if (mWrapper == null || adapter == null) return null;
        view = adapter.onCreateView(this, LayoutInflater.from(mWrapper.getContext()));
        mTypeViews.put(type, view);
        return view;
    }

    /**
     * 重置处理
     * @return {@link ViewAssist}
     */
    private ViewAssist innerReset() {
        ViewUtils.removeAllViews(mWrapper);
        mMapDatas.clear();
        mMapAdapters.clear();
        mTypeViews.clear();
        mCurrentType = -1;
        mCurrentView = null;
        return this;
    }

    /**
     * 更新操作模式
     * @param model View 操作模式
     * @return {@link ViewAssist}
     */
    private ViewAssist innerChangeModel(final Model model) {
        ViewUtils.removeAllViews(mWrapper);
        mTypeViews.clear();
        mCurrentType = -1;
        mCurrentView = null;
        mModel       = model;
        return this;
    }

    /**
     * 注册 type
     * @param type    Type
     * @param adapter {@link Adapter}
     * @return {@link ViewAssist}
     */
    private ViewAssist innerRegister(
            final int type,
            final Adapter adapter
    ) {
        if (adapter == null) return this;
        mMapAdapters.put(type, adapter);
        return this;
    }

    /**
     * 取消注册 type
     * @param type   Type
     * @param remove 判断解绑的 type 正在显示是否删除
     * @return {@link ViewAssist}
     */
    private ViewAssist innerUnregister(
            final int type,
            final boolean remove
    ) {
        mTypeViews.remove(type);
        mMapAdapters.remove(type);
        boolean removeView = false;
        if (remove && type == mCurrentType) {
            ViewUtils.removeSelfFromParent(mCurrentView);
            mCurrentType = -1;
            mCurrentView = null;
            removeView   = true;
        }
        if (mListener != null) {
            mListener.onRemove(this, type, removeView);
        }
        return this;
    }

    /**
     * 显示 Type Adapter View
     * @param type       Type
     * @param notFoundOP 是否处理未找到操作
     */
    private void innerShowType(
            final int type,
            final boolean notFoundOP
    ) {
        if (mWrapper == null) return;
        Adapter adapter = mMapAdapters.get(type);
        if (adapter != null) {
            View view = innerGetView(type);
            if (view != null) {
                // 先隐藏之前旧的 View
                ViewUtils.setVisibility(false, mCurrentView);
                // =
                int oldType = mCurrentType;
                mCurrentType = type;
                mCurrentView = view;
                mTypeViews.put(type, view);

                // 判断操作模式
                if (isGoneModel()) {
                    ForUtils.forSimpleArgs(value -> {
                        // 隐藏非对应 Type View
                        ViewUtils.setVisibility(value == view, value);
                    }, ViewUtils.getChilds(mWrapper));
                } else if (isRemoveModel()) {
                    // 删除所有 View
                    ViewUtils.removeAllViews(mWrapper);
                }
                // 添加 View
                if (mWrapper.indexOfChild(view) == -1) {
                    mWrapper.addView(view);
                }
                // 绑定 View
                adapter.onBindView(this, view, type);

                if (mListener != null) {
                    mListener.onChange(this, type, oldType);
                }
                return;
            }
        }

        // ================================
        // = 没有找到 Adapter、View 创建失败 =
        // ================================

        // 是否处理未找到操作 ( 主要用于动画扩展支持 )
        if (notFoundOP) {
            mCurrentType = -1;
            mCurrentView = null;

            // 判断操作模式
            if (isGoneModel()) {
                // 隐藏全部 View
                ViewUtils.setVisibilitys(false, ViewUtils.getChilds(mWrapper));
            } else if (isRemoveModel()) {
                // 删除所有 View
                ViewUtils.removeAllViews(mWrapper);
            } else { // 预留后续扩展
                ViewUtils.removeAllViews(mWrapper);
            }
        }

        if (mListener != null) {
            mListener.onNotFound(this, type);
        }
    }

    /**
     * 内部刷新 Type Adapter View
     * @param type Type
     * @return {@link ViewAssist}
     */
    private ViewAssist innerNotifyDataSetChanged(final int type) {
        if (mWrapper == null) return this;
        Adapter adapter = mMapAdapters.get(type);
        if (adapter == null) return this;
        View view = innerGetView(type);
        if (view == null) return this;
        try {
            adapter.onBindView(this, view, type);
        } catch (Exception ignored) {
        }
        return this;
    }
}