package dev.widget.assist;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import dev.utils.LogPrintUtils;

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

    // 标记 Tag
    private Object    mTag;
    // 包裹 View
    private ViewGroup mWrapper;

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(ViewGroup wrapper) {
        return wrap(wrapper, null);
    }

    /**
     * 传入包裹 View
     * @param wrapper 包裹 View
     * @param tag     Tag
     * @return {@link ViewAssist}
     */
    public static ViewAssist wrap(
            ViewGroup wrapper,
            Object tag
    ) {
        if (wrapper == null) return null;
        ViewAssist assist = new ViewAssist();
        assist.mWrapper = wrapper;
        assist.mTag = tag;
        return assist;
    }

    // =

    private       Object                mData;
    private final Map<String, Object>   mMapDatas    = new HashMap<>();
    private final Map<Integer, Adapter> mMapAdapters = new HashMap<>();
    private final SparseArray<View>     mTypeViews   = new SparseArray<>(3);
    private       int                   mCurrentType = -1;
    private       View                  mCurrentView;
    private       Listener              mListener;

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

    // ===============
    // = 对外公开方法 =
    // ===============

    // Type: 操作中、失败、成功、空数据
    public static final int TYPE_ING        = 1;
    public static final int TYPE_FAILED     = 2;
    public static final int TYPE_SUCCESS    = 3;
    public static final int TYPE_EMPTY_DATA = 4;

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

    /**
     * 显示 Type Adapter View
     * @param type Type
     */
    public void showType(int type) {
        if (mWrapper == null) return;
        Adapter adapter = mMapAdapters.get(type);
        if (adapter != null) {
            View view = getView(type);
            if (view != null) {
                visible();
                int oldType = mCurrentType;
                mCurrentType = type;
                mCurrentView = view;
                mTypeViews.put(type, view);
                // 添加 View
                if (mWrapper.indexOfChild(view) == -1) {
                    mWrapper.removeAllViews();
                    mWrapper.addView(view);
                }
                adapter.onBindView(this, view, type);

                if (mListener != null) {
                    mListener.onChange(this, type, oldType);
                }
                return;
            }
        }
        //gone();
        mCurrentType = type;
        mCurrentView = null;
        mWrapper.removeAllViews();
        if (mListener != null) {
            mListener.onNotFound(this, type);
        }
    }

    public ViewAssist notifyDataSetChanged() {
        showType(mCurrentType);
        return this;
    }

    // ============
    // = function =
    // ============

    public ViewAssist gone() {
        if (mWrapper != null) mWrapper.setVisibility(View.GONE);
        return this;
    }

    public ViewAssist visible() {
        if (mWrapper != null) mWrapper.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 注册 type
     * @param type    Type
     * @param adapter {@link Adapter}
     * @return {@link ViewAssist}
     */
    public ViewAssist register(
            int type,
            Adapter adapter
    ) {
        if (adapter == null) return this;
        mMapAdapters.put(type, adapter);
        return this;
    }

    /**
     * 取消注册 type
     * @param type Type
     * @return {@link ViewAssist}
     */
    public ViewAssist unregister(int type) {
        return unregister(type, true);
    }

    /**
     * 取消注册 type
     * @param type   Type
     * @param remove 判断解绑的 type 正在显示是否删除
     * @return {@link ViewAssist}
     */
    public ViewAssist unregister(
            int type,
            boolean remove
    ) {
        mTypeViews.remove(type);
        mMapAdapters.remove(type);
        boolean removeView = false;
        if (remove && type == mCurrentType) {
            if (mWrapper != null) mWrapper.removeAllViews();
            mCurrentType = -1;
            mCurrentView = null;
            //gone();
            removeView = true;
        }
        if (mListener != null) {
            mListener.onRemove(this, type, removeView);
        }
        return this;
    }

    /**
     * 重置处理
     * @return {@link ViewAssist}
     */
    public ViewAssist reset() {
        //gone();
        if (mWrapper != null) mWrapper.removeAllViews();
        mData = null;
        mMapDatas.clear();
        mMapAdapters.clear();
        mTypeViews.clear();
        mCurrentType = -1;
        mCurrentView = null;
        return this;
    }

    // ===========
    // = get/set =
    // ===========

    public ViewGroup getWrapper() {
        return mWrapper;
    }

    public Object getTag() {
        return mTag;
    }

    public ViewAssist setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public <T> T getData() {
        try {
            return (T) mData;
        } catch (Exception e) {
            LogPrintUtils.e(e);
        }
        return null;
    }

    public ViewAssist setData(Object data) {
        this.mData = data;
        return this;
    }

    public <T> T getData(String key) {
        try {
            return (T) mMapDatas.get(key);
        } catch (Exception e) {
            LogPrintUtils.e(e);
        }
        return null;
    }

    public ViewAssist setData(
            String key,
            Object data
    ) {
        mMapDatas.put(key, data);
        return this;
    }

    public <T extends Adapter> T getAdapter(int type) {
        return (T) mMapAdapters.get(type);
    }

    public View getView(int type) {
        View view = mTypeViews.get(type);
        if (view != null) return view;
        Adapter adapter = mMapAdapters.get(type);
        if (mWrapper == null || adapter == null) return null;
        view = adapter.onCreateView(this, LayoutInflater.from(mWrapper.getContext()));
        mTypeViews.put(type, view);
        return view;
    }

    public int getCurrentType() {
        return mCurrentType;
    }

    public View getCurrentView() {
        return mCurrentView;
    }

    public ViewAssist setListener(Listener listener) {
        this.mListener = listener;
        return this;
    }
}