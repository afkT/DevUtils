package dev.widget.function;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.app.ViewUtils;

/**
 * detail: 状态布局
 * @author Ttt
 */
public class StateLayout extends FrameLayout {

    // 日志 TAG
    private static final String TAG = StateLayout.class.getSimpleName();
    // 数据总数
    private int mSize = 0;
    // 功能模块类型 ( 功能区分非状态 )
    private String mType = "";
    // 状态值
    private int mState = INIT;
    // 状态值改变接口
    private OnStateChanged mOnStateChanged;
    // View Map 校验
    private Map<Integer, View> mViewMaps = new LinkedHashMap<>();
    // 全局配置
    private static GlobalBuilder sBuilder;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化操作
        init();
    }

    // ============
    // = 状态枚举 =
    // ============

    // 初始化
    public static int INIT = 0;

    // 操作中
    public static int ING = 1;

    // 操作失败
    public static int FAIL = 2;

    // 操作成功
    public static int SUCCESS = 3;

    // 无数据
    public static int NO_DATA = 4;

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取数据总数
     * @return 数据总数
     */
    public int getSize() {
        return mSize;
    }

    /**
     * 设置数据总数
     * @param size 数据总数
     * @return {@link StateLayout}
     */
    public StateLayout setSize(int size) {
        this.mSize = size;
        return this;
    }

    // =

    /**
     * 获取功能模块类型
     * @return 功能模块类型
     */
    public String getType() {
        return mType;
    }

    /**
     * 设置功能模块类型
     * @param type 功能模块类型
     * @return {@link StateLayout}
     */
    public StateLayout setType(String type) {
        this.mType = type;
        return this;
    }

    // =

    /**
     * 获取当前状态值
     * @return 当前状态值
     */
    public int getState() {
        return mState;
    }

    /**
     * 设置状态值
     * @param state 状态值
     * @return {@link StateLayout}
     */
    public StateLayout setState(int state) {
        this.mState = state;
        // 切换 View
        toggleStateView(state);
        // 触发回调
        if (mOnStateChanged != null) {
            mOnStateChanged.OnChanged(this, mState, mType, mSize);
        }
        if (sBuilder != null && sBuilder.onStateChanged != null) {
            sBuilder.onStateChanged.OnChanged(this, mState, mType, mSize);
        }
        return this;
    }

    /**
     * 设置状态值
     * @param state 状态值
     * @param size  数据总数
     * @return {@link StateLayout}
     */
    public StateLayout setState(int state, int size) {
        this.mSize = size;
        return setState(state);
    }

    // =

    /**
     * 切换状态 View
     * @param state 状态值
     * @return {@link StateLayout}
     */
    public StateLayout toggleStateView(int state) {
        // 隐藏其他 View
        ViewUtils.setVisibilitys(false, ViewUtils.getChilds(this));
        // 显示当前状态 View
        ViewUtils.setVisibility(true, getView(state));
        return this;
    }

    // =

    /**
     * 获取对应状态 View
     * @param state 状态值
     * @return 对应状态 View
     */
    public View getView(int state) {
        return mViewMaps.get(state);
    }

    /**
     * 插入 View Layout
     * @param state    状态值
     * @param resource layout Id
     * @return {@link StateLayout}
     */
    public StateLayout insert(int state, @LayoutRes int resource) {
        return insertView(state, resource);
    }

    /**
     * 插入 View Layout
     * @param state      状态值
     * @param layoutView layout View
     * @return {@link StateLayout}
     */
    public StateLayout insert(int state, View layoutView) {
        return insertView(state, layoutView);
    }

    /**
     * 移除对应状态 View
     * @param state 状态值
     * @return {@link StateLayout}
     */
    public StateLayout remove(int state) {
        return removeView(state);
    }

    // =

    /**
     * 获取状态值改变接口
     * @return {@link OnStateChanged}
     */
    public OnStateChanged getStateChanged() {
        return mOnStateChanged;
    }

    /**
     * 设置状态值改变接口
     * @param stateChanged {@link OnStateChanged}
     * @return {@link StateLayout}
     */
    public StateLayout setOnStateChanged(OnStateChanged stateChanged) {
        this.mOnStateChanged = stateChanged;
        return this;
    }

    /**
     * detail: 状态值改变接口
     * @author Ttt
     */
    public interface OnStateChanged {

        /**
         * 状态值改变触发回调
         * @param stateLayout {@link StateLayout}
         * @param state       状态值
         * @param type        功能模块类型
         * @param size        数据总数
         */
        void OnChanged(StateLayout stateLayout, int state, String type, int size);
    }

    // ============
    // = 全局配置 =
    // ============

    /**
     * 设置全局配置
     * @param builder {@link GlobalBuilder}
     */
    public static void setBuilder(GlobalBuilder builder) {
        StateLayout.sBuilder = builder;
    }

    /**
     * detail: 全局配置
     * @author Ttt
     */
    public static class GlobalBuilder {

        // 状态值改变接口
        private OnStateChanged onStateChanged;
        // 插入 View Map state - layoutId
        private Map<Integer, Integer> layoutIdMaps = new LinkedHashMap<>();

        /**
         * 构造函数
         * @param stateChanged {@link OnStateChanged}
         */
        public GlobalBuilder(OnStateChanged stateChanged) {
            this.onStateChanged = stateChanged;
        }

        /**
         * 插入 View Layout
         * @param state    状态值
         * @param resource layout Id
         * @return {@link GlobalBuilder}
         */
        public GlobalBuilder insert(int state, @LayoutRes int resource) {
            layoutIdMaps.put(state, resource);
            return this;
        }

        /**
         * 移除对应状态 View
         * @param state 状态值
         * @return {@link GlobalBuilder}
         */
        public GlobalBuilder remove(int state) {
            layoutIdMaps.remove(state);
            return this;
        }
    }

    // ==================
    // = 初始化相关代码 =
    // ==================

    /**
     * 重置处理
     * @return {@link StateLayout}
     */
    public StateLayout reset() {
        mSize = 0;
        mType = "";
        mState = INIT;
        mOnStateChanged = null;
        mViewMaps = new LinkedHashMap<>();
        this.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        return this;
    }

    /**
     * 内部初始化方法
     */
    private void init() {
        // 插入 View Map state - layoutId
        Map<Integer, Integer> layoutIdMaps = new LinkedHashMap<>();
        // 防止为 null
        if (sBuilder != null) {
            layoutIdMaps.putAll(sBuilder.layoutIdMaps);
        }

        // 清空全部 View
        removeAllViews();

        Iterator<Map.Entry<Integer, Integer>> iterator = layoutIdMaps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            int state = entry.getKey();
            Integer layout = entry.getValue();
            insertView(state, layout);
        }

        // 默认触发初始化回调 ( 一开始 mType 非准确, 可通过获取 StateLayout 判断 Context 所属功能页面 )
        if (sBuilder != null && sBuilder.onStateChanged != null) {
            sBuilder.onStateChanged.OnChanged(this, mState, mType, mSize);
        }
    }

    /**
     * 插入 View
     * @param state  状态值
     * @param layout View Layout id
     * @return {@link StateLayout}
     */
    private StateLayout insertView(int state, int layout) {
        View view = ViewUtils.inflate(this.getContext(), layout);
        return insertView(state, view);
    }

    /**
     * 插入 View
     * @param state 状态值
     * @param view  View Layout
     * @return {@link StateLayout}
     */
    private StateLayout insertView(int state, View view) {
        if (view != null) {
            // 移除旧的 View
            removeView(state);
            // 默认隐藏 View
            ViewUtils.setVisibility(false, view);
            // 保存新的 View
            this.addView(view);
            // 保存 View
            mViewMaps.put(state, view);
        }
        return this;
    }

    /**
     * 移除 View
     * @param state 状态值
     * @return {@link StateLayout}
     */
    private StateLayout removeView(int state) {
        // 如果存在旧的 View, 则移除
        if (mViewMaps.containsKey(state)) {
            View view = mViewMaps.get(state);
            if (view != null) removeView(view);
            mViewMaps.remove(state);
        }
        return this;
    }
}