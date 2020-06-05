package dev.widget.assist;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

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
    private String    tag;
    // 包裹 View
    private ViewGroup wrapper;

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
    public static ViewAssist wrap(ViewGroup wrapper, String tag) {
        if (wrapper == null) return null;
        ViewAssist assist = new ViewAssist();
        assist.wrapper = wrapper;
        assist.tag = tag;
        return assist;
    }

    // =

    private Object                    data;
    private HashMap<String, Object>   mapDatas    = new HashMap<>();
    private HashMap<Integer, Adapter> mapAdapters = new HashMap<>();
    private SparseArray<View>         typeViews   = new SparseArray<>(3);
    private int                       currentType = -1;
    private View                      currentView;
    private Listener                  listener;

    public interface Adapter {

        View onCreateView(ViewAssist assist, LayoutInflater inflater);

        void onBindView(ViewAssist assist, View view, int type);
    }

    public interface Listener {

        void onRemove(ViewAssist assist, int type, boolean removeView);

        void onNotFound(ViewAssist assist, int type);

        void onChange(ViewAssist assist, int type, int oldType);
    }

    // ================
    // = 对外公开方法 =
    // ================

    // Type: 加载中、加载失败、空数据
    public static final int TYPE_LOADING     = 1;
    public static final int TYPE_LOAD_FAILED = 2;
    public static final int TYPE_EMPTY_DATA  = 3;

    public void showLoading() {
        showType(TYPE_LOADING);
    }

    public void showLoadFailed() {
        showType(TYPE_LOAD_FAILED);
    }

    public void showEmptyData() {
        showType(TYPE_EMPTY_DATA);
    }

    /**
     * 显示 Type Adapter View
     * @param type Type
     */
    public void showType(int type) {
        if (wrapper == null) return;
        Adapter adapter = mapAdapters.get(type);
        if (adapter != null) {
            View view = getView(type);
            if (view != null) {
                visible();
                int oldType = currentType;
                currentType = type;
                currentView = view;
                typeViews.put(type, view);
                // 添加 View
                if (wrapper.indexOfChild(view) == -1) {
                    wrapper.removeAllViews();
                    wrapper.addView(view);
                }
                adapter.onBindView(this, view, type);

                if (listener != null) {
                    listener.onChange(this, type, oldType);
                }
                return;
            }
        }
        //gone();
        currentType = type;
        currentView = null;
        wrapper.removeAllViews();
        if (listener != null) {
            listener.onNotFound(this, type);
        }
    }

    public ViewAssist notifyDataSetChanged() {
        showType(currentType);
        return this;
    }

    // ============
    // = function =
    // ============

    public ViewAssist gone() {
        if (wrapper != null) wrapper.setVisibility(View.GONE);
        return this;
    }

    public ViewAssist visible() {
        if (wrapper != null) wrapper.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 注册 type
     * @param type    Type
     * @param adapter {@link Adapter}
     * @return {@link ViewAssist}
     */
    public ViewAssist register(int type, Adapter adapter) {
        if (adapter == null) return this;
        mapAdapters.put(type, adapter);
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
    public ViewAssist unregister(int type, boolean remove) {
        typeViews.remove(type);
        mapAdapters.remove(type);
        boolean removeView = false;
        if (remove && type == currentType) {
            if (wrapper != null) wrapper.removeAllViews();
            currentType = -1;
            currentView = null;
            //gone();
            removeView = true;
        }
        if (listener != null) {
            listener.onRemove(this, type, removeView);
        }
        return this;
    }

    // =============
    // = get / set =
    // =============

    public ViewGroup getWrapper() {
        return wrapper;
    }

    public String getTag() {
        return tag;
    }

    public ViewAssist setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public <T> T getData() {
        try {
            return (T) data;
        } catch (Exception e) {
            LogPrintUtils.e(e);
        }
        return null;
    }

    public ViewAssist setData(Object data) {
        this.data = data;
        return this;
    }

    public <T> T getData(String key) {
        try {
            return (T) mapDatas.get(key);
        } catch (Exception e) {
            LogPrintUtils.e(e);
        }
        return null;
    }

    public ViewAssist setData(String key, Object data) {
        mapDatas.put(key, data);
        return this;
    }

    public <T extends Adapter> T getAdapter(int type) {
        return (T) mapAdapters.get(type);
    }

    public View getView(int type) {
        View view = typeViews.get(type);
        if (view != null) return view;
        Adapter adapter = mapAdapters.get(type);
        if (wrapper == null || adapter == null) return null;
        return adapter.onCreateView(this, LayoutInflater.from(wrapper.getContext()));
    }

    public int getCurrentType() {
        return currentType;
    }

    public View getCurrentView() {
        return currentView;
    }

    public ViewAssist setListener(Listener listener) {
        this.listener = listener;
        return this;
    }
}