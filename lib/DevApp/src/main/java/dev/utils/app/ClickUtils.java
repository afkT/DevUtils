package dev.utils.app;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import dev.utils.LogPrintUtils;

/**
 * detail: 点击 ( 双击 ) 工具类
 * @author Ttt
 */
public final class ClickUtils {

    private ClickUtils() {
    }

    // 日志 TAG
    private static final String TAG = ClickUtils.class.getSimpleName();

    // 通用间隔时间
    public static final  long                     INTERVAL_TIME       = 1000L;
    // 是否校验 viewId
    private static       boolean                  sCheckViewId        = true;
    // 双击间隔时间
    private static       long                     sGlobalIntervalTime = 1000L;
    // 全局共用的点击辅助类
    private static final ClickAssist              sGlobalClickAssist  = new ClickAssist();
    // 功能模块 ClickAssist Maps
    private static final Map<Object, ClickAssist> sClickAssistMaps    = new HashMap<>();

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view  待添加点击范围 View
     * @param range 点击范围
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addTouchArea(
            final View view,
            final int range
    ) {
        return addTouchArea(view, range, range, range, range);
    }

    /**
     * 增加控件的触摸范围, 最大范围只能是父布局所包含的的区域
     * @param view   待添加点击范围 View
     * @param top    top range
     * @param bottom bottom range
     * @param left   left range
     * @param right  right range
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addTouchArea(
            final View view,
            final int top,
            final int bottom,
            final int left,
            final int right
    ) {
        if (view != null) {
            try {
                final View parent = (View) view.getParent();
                parent.post(new Runnable() {
                    public void run() {
                        try {
                            Rect bounds = new Rect();
                            view.getHitRect(bounds);

                            // 设置范围
                            bounds.top -= top;
                            bounds.bottom += bottom;
                            bounds.left -= left;
                            bounds.right += right;

                            TouchDelegate touchDelegate = new TouchDelegate(bounds, view);
                            if (view.getParent() instanceof View) {
                                ((View) view.getParent()).setTouchDelegate(touchDelegate);
                            }
                        } catch (Exception e) {
                            LogPrintUtils.eTag(TAG, e, "addTouchArea - runnable");
                        }
                    }
                });
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addTouchArea");
            }
        }
        return false;
    }

    /**
     * 设置全局是否校验 viewId
     * @param checkViewId 是否校验 viewId
     */
    public static void setCheckViewId(final boolean checkViewId) {
        ClickUtils.sCheckViewId = checkViewId;
    }

    /**
     * 设置全局双击间隔时间
     * @param globalIntervalTime 全局双击间隔时间
     */
    public static void setGlobalIntervalTime(final long globalIntervalTime) {
        ClickUtils.sGlobalIntervalTime = globalIntervalTime;
    }

    // ====================
    // = 功能模块辅助类操作 =
    // ====================

    /**
     * 获取对应功能模块点击辅助类
     * @param object key by Object
     * @return {@link ClickAssist}
     */
    public static ClickAssist get(final Object object) {
        // 获取对应模块点击辅助类
        ClickAssist clickAssist = sClickAssistMaps.get(object);
        if (clickAssist != null) {
            return clickAssist;
        }
        clickAssist = new ClickAssist();
        sClickAssistMaps.put(object, clickAssist);
        return clickAssist;
    }

    /**
     * 移除对应功能模块点击辅助类
     * @param object key by Object
     */
    public static void remove(final Object object) {
        sClickAssistMaps.remove(object);
    }

    // ====================
    // = 全局点击辅助类操作 =
    // ====================

    /**
     * 判断是否双击 ( 无效点击, 短时间内多次点击 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFastDoubleClick() {
        return sGlobalClickAssist.isFastDoubleClick();
    }

    /**
     * 判断是否双击 ( 无效点击, 短时间内多次点击 )
     * @param tagId id
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFastDoubleClick(final int tagId) {
        return sGlobalClickAssist.isFastDoubleClick(tagId);
    }

    /**
     * 判断是否双击 ( 无效点击, 短时间内多次点击 )
     * @param tagId        id
     * @param intervalTime 双击时间间隔
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFastDoubleClick(
            final int tagId,
            final long intervalTime
    ) {
        return sGlobalClickAssist.isFastDoubleClick(tagId, intervalTime);
    }

    // =

    /**
     * 判断是否双击 ( 无效点击, 短时间内多次点击 )
     * @param object    key by Object
     * @param configKey 时间间隔配置 Key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFastDoubleClick(
            final Object object,
            final String configKey
    ) {
        return sGlobalClickAssist.isFastDoubleClick(object, configKey);
    }

    /**
     * 判断是否双击 ( 无效点击, 短时间内多次点击 )
     * @param object       key by Object
     * @param intervalTime 双击时间间隔
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFastDoubleClick(
            final Object object,
            final long intervalTime
    ) {
        return sGlobalClickAssist.isFastDoubleClick(object, intervalTime);
    }

    // =

    /**
     * 初始化配置信息
     * @param mapConfigs config Maps
     * @return {@link ClickAssist}
     */
    public static ClickAssist initConfig(final Map<String, Long> mapConfigs) {
        return sGlobalClickAssist.initConfig(mapConfigs);
    }

    /**
     * 添加配置信息
     * @param key   config Key
     * @param value config Value
     * @return {@link ClickAssist}
     */
    public static ClickAssist putConfig(
            final String key,
            final Long value
    ) {
        return sGlobalClickAssist.putConfig(key, value);
    }

    /**
     * 移除配置信息
     * @param key config Key
     * @return {@link ClickAssist}
     */
    public static ClickAssist removeConfig(final String key) {
        return sGlobalClickAssist.removeConfig(key);
    }

    /**
     * 获取配置时间
     * @param key config Key
     * @return 配置时间
     */
    public static Long getConfigTime(final String key) {
        return sGlobalClickAssist.getConfigTime(key);
    }

    // =

    /**
     * 移除点击记录
     * @param key tag Key
     * @return {@link ClickAssist}
     */
    public static ClickAssist removeRecord(final String key) {
        return sGlobalClickAssist.removeRecord(key);
    }

    /**
     * 清空全部点击记录
     * @return {@link ClickAssist}
     */
    public static ClickAssist clearRecord() {
        return sGlobalClickAssist.clearRecord();
    }

    // =

    /**
     * 设置默认点击时间间隔
     * @param intervalTime 双击时间间隔
     * @return {@link ClickAssist}
     */
    public static ClickAssist setIntervalTime(final long intervalTime) {
        return sGlobalClickAssist.setIntervalTime(intervalTime);
    }

    /**
     * 重置处理
     * @return {@link ClickAssist}
     */
    public static ClickAssist reset() {
        return sGlobalClickAssist.reset();
    }

    // =

    /**
     * detail: 点击 ( 双击 ) 辅助类
     * @author Ttt
     * <pre>
     *     ps: 该辅助类, 主要避免全局共用一个双击控制类, 容易出现冲突, 方便控制某个 Activity 或功能模块 双击处理
     *     使用 Key(Tag-Object) 获取指定的 {@link ClickAssist}, 能够实现不同 Activity 或功能模块 单独使用 {@link ClickAssist}
     *     并且可以进行销毁处理
     * </pre>
     */
    public static class ClickAssist {

        // 最后一次点击的标识 id
        private       int               mLastTagId     = -1;
        // 最后一次点击时间
        private       long              mLastClickTime = 0L;
        // 双击间隔时间
        private       long              mIntervalTime;
        // 配置数据
        private final Map<String, Long> mConfigMaps    = new HashMap<>();
        // 点击记录数据
        private final Map<String, Long> mRecordMaps    = new HashMap<>();

        public ClickAssist() {
            this(ClickUtils.sGlobalIntervalTime);
        }

        /**
         * 构造函数
         * @param intervalTime 双击间隔时间
         */
        public ClickAssist(final long intervalTime) {
            this.mIntervalTime = intervalTime;
        }

        // =

        /**
         * 判断是否双击 ( 无效点击, 短时间内多次点击 )
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFastDoubleClick() {
            return isFastDoubleClick(-1, mIntervalTime);
        }

        /**
         * 判断是否双击 ( 无效点击, 短时间内多次点击 )
         * @param tagId id
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFastDoubleClick(final int tagId) {
            return isFastDoubleClick(tagId, mIntervalTime);
        }

        /**
         * 判断是否双击 ( 无效点击, 短时间内多次点击 )
         * @param tagId        id
         * @param intervalTime 双击间隔时间
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFastDoubleClick(
                final int tagId,
                final long intervalTime
        ) {
            long curTime  = System.currentTimeMillis();
            long diffTime = curTime - mLastClickTime;
            // 判断时间是否超过
            if (mLastTagId == tagId && mLastClickTime > 0 && diffTime < intervalTime) {
                LogPrintUtils.dTag(TAG, "isFastDoubleClick 无效点击 tagId: %s, intervalTime: %s", tagId, intervalTime);
                return true;
            }
            LogPrintUtils.dTag(TAG, "isFastDoubleClick 有效点击 tagId: %s, intervalTime: %s", tagId, intervalTime);
            mLastTagId = tagId;
            mLastClickTime = curTime;
            return false;
        }

        // =

        /**
         * 判断是否双击 ( 无效点击, 短时间内多次点击 )
         * @param object    key by Object
         * @param configKey 时间间隔配置 Key
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFastDoubleClick(
                final Object object,
                final String configKey
        ) {
            return isFastDoubleClick(object, getConfigTime(configKey));
        }

        /**
         * 判断是否双击 ( 无效点击, 短时间内多次点击 )
         * @param object       key by Object
         * @param intervalTime 双击时间间隔
         * @return {@code true} yes, {@code false} no
         */
        public boolean isFastDoubleClick(
                final Object object,
                final long intervalTime
        ) {
            // 获取 TAG
            String tag = ((object != null) ? ("obj_" + object.hashCode()) : "obj_null");
            // 获取上次点击的时间
            Long lastTime = mRecordMaps.get(tag);
            if (lastTime == null) {
                lastTime = 0L;
            }
            long curTime  = System.currentTimeMillis();
            long diffTime = curTime - lastTime;
            // 判断时间是否超过
            if (lastTime > 0 && diffTime < intervalTime) {
                LogPrintUtils.dTag(TAG, "isFastDoubleClick 无效点击 object: %s, intervalTime: %s", object, intervalTime);
                return true;
            }
            LogPrintUtils.dTag(TAG, "isFastDoubleClick 有效点击 object: %s, intervalTime: %s", object, intervalTime);
            // 保存上次点击时间
            mRecordMaps.put(tag, curTime);
            return false;
        }

        // =

        /**
         * 初始化配置信息
         * @param mapConfigs config Maps
         * @return {@link ClickAssist}
         */
        public ClickAssist initConfig(final Map<String, Long> mapConfigs) {
            if (mapConfigs != null) {
                mConfigMaps.putAll(mapConfigs);
            }
            return this;
        }

        /**
         * 添加配置信息
         * @param key   config Key
         * @param value config Value
         * @return {@link ClickAssist}
         */
        public ClickAssist putConfig(
                final String key,
                final Long value
        ) {
            mConfigMaps.put(key, value);
            return this;
        }

        /**
         * 移除配置信息
         * @param key config Key
         * @return {@link ClickAssist}
         */
        public ClickAssist removeConfig(final String key) {
            mConfigMaps.remove(key);
            return this;
        }

        /**
         * 获取配置时间
         * @param key config Key
         * @return 配置时间
         */
        public Long getConfigTime(final String key) {
            // 获取配置时间
            Long configTime = mConfigMaps.get(key);
            // 判断是否为 null
            return (configTime != null) ? configTime : mIntervalTime;
        }

        // =

        /**
         * 移除点击记录
         * @param key tag Key
         * @return {@link ClickAssist}
         */
        public ClickAssist removeRecord(final String key) {
            mRecordMaps.remove(key);
            return this;
        }

        /**
         * 清空全部点击记录
         * @return {@link ClickAssist}
         */
        public ClickAssist clearRecord() {
            mRecordMaps.clear();
            return this;
        }

        // =

        /**
         * 设置默认点击时间间隔
         * @param intervalTime 双击时间间隔
         * @return {@link ClickAssist}
         */
        public ClickAssist setIntervalTime(final long intervalTime) {
            this.mIntervalTime = intervalTime;
            return this;
        }

        /**
         * 重置处理
         * @return {@link ClickAssist}
         */
        public ClickAssist reset() {
            // 重置最后一次点击的标识 id
            mLastTagId = -1;
            // 重置最后一次点击时间
            mLastClickTime = 0L;
            // 重置双击间隔时间
            mIntervalTime = ClickUtils.sGlobalIntervalTime;
            // 清空配置信息
            mConfigMaps.clear();
            // 清空点击记录
            mRecordMaps.clear();
            return this;
        }
    }

    // =======
    // = 快捷 =
    // =======

    // 空实现 View.OnClickListener
    public static final View.OnClickListener EMPTY_CLICK = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogPrintUtils.dTag(TAG, "EMPTY_CLICK viewId: %s", view.getId());
        }
    };

    /**
     * detail: 双击点击事件
     * @author Ttt
     */
    public static abstract class OnDebouncingClickListener
            implements View.OnClickListener {

        // 是否校验 viewId
        private final boolean     mCheckViewId;
        // 点击辅助类
        private final ClickAssist mClickAssist;

        // ===========
        // = 构造函数 =
        // ===========

        public OnDebouncingClickListener() {
            this(ClickUtils.sGlobalClickAssist, ClickUtils.sCheckViewId);
        }

        public OnDebouncingClickListener(boolean checkViewId) {
            this(ClickUtils.sGlobalClickAssist, checkViewId);
        }

        public OnDebouncingClickListener(ClickAssist clickAssist) {
            this(clickAssist, ClickUtils.sCheckViewId);
        }

        public OnDebouncingClickListener(
                ClickAssist clickAssist,
                boolean checkViewId
        ) {
            this.mClickAssist = clickAssist;
            this.mCheckViewId = checkViewId;
        }

        // ===========
        // = 内部方法 =
        // ===========

        @Override
        public final void onClick(View view) {
            if (mClickAssist.isFastDoubleClick(mCheckViewId ? view.getId() : -1)) {
                doInvalidClick(view);
            } else {
                doClick(view);
            }
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 有效点击 ( 单击 )
         * @param view {@link View}
         */
        public abstract void doClick(View view);

        /**
         * 无效点击 ( 双击 )
         * @param view {@link View}
         */
        public void doInvalidClick(View view) {
        }
    }

    /**
     * detail: 计数点击事件
     * @author Ttt
     */
    public static abstract class OnCountClickListener
            implements View.OnClickListener {

        // 点击辅助类
        private final ClickAssist   mClickAssist;
        // 总点击数
        private final AtomicInteger mCount              = new AtomicInteger();
        // 无效点击总次数
        private final AtomicInteger mInvalidCount       = new AtomicInteger();
        // 每个周期无效点击次数 ( 周期 ( 有效 - 无效 - 有效 ) )
        private final AtomicInteger mInvalidCycleNumber = new AtomicInteger();

        // ===========
        // = 构造函数 =
        // ===========

        public OnCountClickListener() {
            this(ClickUtils.sGlobalClickAssist);
        }

        public OnCountClickListener(ClickAssist clickAssist) {
            this.mClickAssist = clickAssist;
        }

        // ===========
        // = 内部方法 =
        // ===========

        @Override
        public final void onClick(View view) {
            // 累加总点击数
            mCount.incrementAndGet();

            if (mClickAssist.isFastDoubleClick(view.getId())) {
                // 累加无效点击总次数
                mInvalidCount.incrementAndGet();
                // 累加每个周期无效点击次数 ( 周期 ( 有效 - 无效 - 有效 ) )
                int invalidCycleNumber = mInvalidCycleNumber.incrementAndGet();

                doInvalidClick(view, this, invalidCycleNumber);
            } else {
                // 重置周期无效点击次数
                mInvalidCycleNumber.set(0);

                doClick(view, this);
            }
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 有效点击 ( 单击 )
         * @param view     {@link View}
         * @param listener {@link OnCountClickListener}
         */
        public abstract void doClick(
                View view,
                OnCountClickListener listener
        );

        /**
         * 无效点击 ( 双击 )
         * @param view               {@link View}
         * @param listener           {@link OnCountClickListener}
         * @param invalidCycleNumber 每个周期无效点击次数 ( 周期 ( 有效 - 无效 - 有效 ) )
         */
        public void doInvalidClick(
                View view,
                OnCountClickListener listener,
                int invalidCycleNumber
        ) {
        }

        // =

        /**
         * 获取总点击数
         * @return 总点击数
         */
        public final int getCount() {
            return mCount.get();
        }

        /**
         * 获取无效点击总次数
         * @return 无效点击总次数
         */
        public final int getInvalidCount() {
            return mInvalidCount.get();
        }

        /**
         * 获取每个周期无效点击次数
         * @return 每个周期无效点击次数
         */
        public final int getInvalidCycleNumber() {
            return mInvalidCycleNumber.get();
        }
    }

    /**
     * detail: 多次点击触发事件
     * @author Ttt
     * <pre>
     *     因为双击后才进入计数, 所以 {@link #getInvalidCycleNumber()} 得 + 1 才是准确的点击数
     * </pre>
     */
    public static abstract class OnMultiClickListener
            extends OnCountClickListener {

        // 多次点击次数
        private final int multiClickNumber;

        /**
         * 构造函数
         * @param number 多次点击次数
         */
        public OnMultiClickListener(final int number) {
            this(number, INTERVAL_TIME);
        }

        /**
         * 构造函数
         * @param number       多次点击次数
         * @param intervalTime 双击间隔时间
         */
        public OnMultiClickListener(
                final int number,
                final long intervalTime
        ) {
            super(new ClickAssist(intervalTime));
            this.multiClickNumber = number;
        }

        @Override
        public final void doClick(
                View view,
                OnCountClickListener listener
        ) {
        }

        @Override
        public void doInvalidClick(
                View view,
                OnCountClickListener listener,
                int invalidCycleNumber
        ) {
            if (invalidCycleNumber + 1 >= multiClickNumber) {
                doMultiClick(view, invalidCycleNumber + 1);
            }
        }

        // ===============
        // = 对外公开方法 =
        // ===============

        /**
         * 多次点击触发
         * @param view        {@link View}
         * @param clickNumber 多次点击次数
         */
        public abstract void doMultiClick(
                View view,
                int clickNumber
        );
    }
}