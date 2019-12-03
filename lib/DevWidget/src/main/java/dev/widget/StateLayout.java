package dev.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.LogPrintUtils;
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
    private int mState = State.INIT.getValue();
    // 状态值改变接口
    private OnStateChanged mOnStateChanged;
    // 插入 View Map state - layoutId
    private Map<Integer, Integer> mViewMaps = new LinkedHashMap<>();
    // 全局配置
    private static GlobalBuilder builder;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化操作
        init();
    }

    // ============
    // = 状态枚举 =
    // ============

    /**
     * detail: 状态枚举类
     * @author Ttt
     */
    public enum State {

        // 初始化
        INIT(0),

        // 操作中
        ING(1),

        // 操作失败
        FAIL(2),

        // 操作成功
        SUCCESS(3),

        // 无数据
        NO_DATA(4);

        // 状态值
        int value;

        State(int value) {
            this.value = value;
        }

        /**
         * 获取状态值
         * @return 状态值
         */
        public int getValue() {
            return value;
        }

        /**
         * 获取状态枚举类
         * @param state 状态值
         * @return {@link State}
         */
        public static State getState(int state) {
            try {
                return State.values()[state];
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getState");
                return null;
            }
        }
    }

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
     * @return {@link State}
     */
    public int getState() {
        return mState;
    }

    /**
     * 获取当前状态枚举值
     * @return {@link State}
     */
    public State getStateEnum() {
        return State.getState(mState);
    }

    /**
     * 获取对应值状态枚举
     * @param state 状态值
     * @return {@link State}
     */
    public State getStateEnum(int state) {
        return State.getState(state);
    }

    /**
     * 设置状态值
     * @param state 状态
     * @return {@link StateLayout}
     */
    public StateLayout setState(int state) {
        this.mState = state;
        if (mOnStateChanged != null) {
            mOnStateChanged.OnChanged(this, mState, mType, mSize);
        }
        if (builder != null && builder.onStateChanged != null) {
            builder.onStateChanged.OnChanged(this, mState, mType, mSize);
        }
        return this;
    }

    /**
     * 设置状态值
     * @param state 状态
     * @param size  数据总数
     * @return {@link StateLayout}
     */
    public StateLayout setState(int state, int size) {
        this.mState = state;
        this.mSize = size;
        if (mOnStateChanged != null) {
            mOnStateChanged.OnChanged(this, mState, mType, mSize);
        }
        if (builder != null && builder.onStateChanged != null) {
            builder.onStateChanged.OnChanged(this, mState, mType, mSize);
        }
        return this;
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
        StateLayout.builder = builder;
    }

    /**
     * detail: 全局配置
     * @author Ttt
     */
    public static class GlobalBuilder {

        // 状态值改变接口
        private OnStateChanged onStateChanged;
        // 插入 View Map state - layoutId
        private Map<Integer, Integer> viewMaps = new LinkedHashMap<>();

        public GlobalBuilder() {
        }

        /**
         * 设置状态值改变接口
         * @param stateChanged {@link OnStateChanged}
         * @return {@link GlobalBuilder}
         */
        public GlobalBuilder setOnStateChanged(OnStateChanged stateChanged) {
            this.onStateChanged = stateChanged;
            return this;
        }

        /**
         * 插入 View Layout
         * @param state    状态值
         * @param resource layout Id
         * @return {@link GlobalBuilder}
         */
        public GlobalBuilder insert(int state, @LayoutRes int resource) {
            viewMaps.put(state, resource);
            return this;
        }

        /**
         * 插入 View Layout
         * @param state    状态值
         * @param resource layout Id
         * @return {@link GlobalBuilder}
         */
        public GlobalBuilder insert(State state, @LayoutRes int resource) {
            if (state != null) viewMaps.put(state.value, resource);
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
        mState = State.INIT.getValue();
        mOnStateChanged = null;
        mViewMaps = new HashMap<>();
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
    private void init (){
        // 防止为 null
        if (builder != null) {
            mViewMaps.putAll(builder.viewMaps);
        }

        // 循环插入 View
        Collection<Integer> layoutIds = mViewMaps.values();
        for (Integer layout : layoutIds) {
            View view = ViewUtils.inflate(this.getContext(), layout, this);
            if (view != null) {
                this.addView(view);
            }
        }

        // 默认触发初始化回调 ( 一开始 mType 非准确, 可通过获取 StateLayout 判断 Context 所属功能页面 )
        if (builder != null && builder.onStateChanged != null) {
            builder.onStateChanged.OnChanged(this, mState, mType, mSize);
        }
    }
}