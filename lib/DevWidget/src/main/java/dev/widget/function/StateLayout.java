package dev.widget.function;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.utils.LogPrintUtils;
import dev.widget.assist.ViewAssist;

/**
 * detail: 状态布局
 * @author Ttt
 * <pre>
 *     使用 type-layoutId 进行注册, 封装 {@link dev.widget.assist.ViewAssist} ( type-Adapter )
 * </pre>
 */
public class StateLayout
        extends FrameLayout {

    // 日志 TAG
    private final  String     TAG = StateLayout.class.getSimpleName();
    // View 填充辅助类
    private        ViewAssist mAssist;
    // 类型改变接口
    private        Listener   mListener;
    // 全局配置
    private static Global     sGlobal;

    public StateLayout(Context context) {
        super(context);
        init();
    }

    public StateLayout(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    public StateLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StateLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // =

    public interface Listener {

        void onRemove(
                StateLayout layout,
                int type,
                boolean removeView
        );

        void onNotFound(
                StateLayout layout,
                int type
        );

        void onChange(
                StateLayout layout,
                int type,
                int oldType,
                View view
        );
    }

    // =================
    // = 初始化相关代码 =
    // =================

    /**
     * 重置处理
     * @return {@link StateLayout}
     */
    public StateLayout reset() {
        init(); // 重新初始化
        post(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
            }
        });
        return this;
    }

    /**
     * 内部初始化方法
     */
    private void init() {
        mAssist = ViewAssist.wrap(this);
        mAssist.setListener(new ViewAssist.Listener() {
            @Override
            public void onRemove(
                    ViewAssist assist,
                    int type,
                    boolean removeView
            ) {
                if (mListener != null) {
                    mListener.onRemove(StateLayout.this, type, removeView);
                }
                if (sGlobal != null && sGlobal.listener != null) {
                    sGlobal.listener.onRemove(StateLayout.this, type, removeView);
                }
            }

            @Override
            public void onNotFound(
                    ViewAssist assist,
                    int type
            ) {
                if (mListener != null) {
                    mListener.onNotFound(StateLayout.this, type);
                }
                if (sGlobal != null && sGlobal.listener != null) {
                    sGlobal.listener.onNotFound(StateLayout.this, type);
                }
            }

            @Override
            public void onChange(
                    ViewAssist assist,
                    int type,
                    int oldType
            ) {
                if (mListener != null) {
                    mListener.onChange(StateLayout.this, type, oldType, assist.getView(type));
                }
                if (sGlobal != null && sGlobal.listener != null) {
                    sGlobal.listener.onChange(StateLayout.this, type, oldType, assist.getView(type));
                }
            }
        });
        // 添加全局配置
        if (sGlobal != null) {
            Map<Integer, Integer>                 mapLayouts = new HashMap<>(sGlobal.mapLayouts);
            Iterator<Map.Entry<Integer, Integer>> iterator   = mapLayouts.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Integer> entry  = iterator.next();
                int                         type   = entry.getKey();
                Integer                     layout = entry.getValue();
                mAssist.register(type, new TypeAdapter(layout));
            }
        }
    }

    // =

    /**
     * detail: 全局配置
     * @author Ttt
     */
    public static class Global {

        // type-layout
        private final Map<Integer, Integer> mapLayouts = new HashMap<>();
        // 类型改变接口
        private final Listener              listener;

        public Global(Listener listener) {
            this.listener = listener;
        }

        /**
         * 注册 type
         * @param type   Type
         * @param layout Layout
         * @return {@link Global}
         */
        public Global register(
                int type,
                @LayoutRes int layout
        ) {
            mapLayouts.put(type, layout);
            return this;
        }

        /**
         * 取消注册 type
         * @param type Type
         * @return {@link Global}
         */
        public Global unregister(int type) {
            mapLayouts.remove(type);
            return this;
        }
    }

    private class TypeAdapter
            implements ViewAssist.Adapter {

        private final int resource;

        public TypeAdapter(int layout) {
            this.resource = layout;
        }

        @Override
        public View onCreateView(
                ViewAssist assist,
                LayoutInflater inflater
        ) {
            try {
                return inflater.inflate(resource, null);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e);
                return null;
            }
        }

        @Override
        public void onBindView(
                ViewAssist assist,
                View view,
                int type
        ) {
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    public static void setGlobal(Global global) {
        StateLayout.sGlobal = global;
    }

    public StateLayout setListener(Listener listener) {
        this.mListener = listener;
        return this;
    }

    public void showIng() {
        mAssist.showIng();
    }

    public void showFailed() {
        mAssist.showFailed();
    }

    public void showSuccess() {
        mAssist.showSuccess();
    }

    public void showEmptyData() {
        mAssist.showEmptyData();
    }

    public void showType(int type) {
        mAssist.showType(type);
    }

    public StateLayout notifyDataSetChanged() {
        mAssist.notifyDataSetChanged();
        return this;
    }

    public StateLayout gone() {
        setVisibility(View.GONE);
        return this;
    }

    public StateLayout visible() {
        setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 注册 type
     * @param type   Type
     * @param layout Layout
     * @return {@link StateLayout}
     */
    public StateLayout register(
            int type,
            @LayoutRes int layout
    ) {
        mAssist.register(type, new TypeAdapter(layout));
        return this;
    }

    /**
     * 取消注册 type
     * @param type Type
     * @return {@link StateLayout}
     */
    public StateLayout unregister(int type) {
        return unregister(type, true);
    }

    /**
     * 取消注册 type
     * @param type   Type
     * @param remove 判断解绑的 type 正在显示是否删除
     * @return {@link StateLayout}
     */
    public StateLayout unregister(
            int type,
            boolean remove
    ) {
        mAssist.unregister(type, remove);
        return this;
    }

    public Object getAssistTag() {
        return mAssist.getTag();
    }

    public StateLayout setAssistTag(Object tag) {
        mAssist.setTag(tag);
        return this;
    }

    public <T> T getData() {
        return mAssist.getData();
    }

    public StateLayout setData(Object data) {
        mAssist.setData(data);
        return this;
    }

    public <T> T getData(String key) {
        return mAssist.getData(key);
    }

    public StateLayout setData(
            String key,
            Object data
    ) {
        mAssist.setData(key, data);
        return this;
    }

    public View getView(int type) {
        return mAssist.getView(type);
    }

    public int getCurrentType() {
        return mAssist.getCurrentType();
    }

    public View getCurrentView() {
        return mAssist.getCurrentView();
    }
}