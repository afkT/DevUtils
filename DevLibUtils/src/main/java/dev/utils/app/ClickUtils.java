package dev.utils.app;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: 点击工具类
 * @author Ttt
 */
public final class ClickUtils {

    private ClickUtils() {
    }

    // 日志 TAG
    private static final String TAG = ClickUtils.class.getSimpleName();
    // 上一次点击的标识id = viewId 等
    private static int lastTagId = -1;
    // 上次点击时间
    private static long lastClickTime = 0l; // 局限性是, 全局统一事件，如果上次点击后，立刻点击其他就无法点
    // 默认间隔时间
    private static long DF_DIFF = 1000l; // 点击间隔1秒内
    // 配置数据
    private static final Map<String, Long> mapConfig = new HashMap<>();
    // 点击记录数据
    private static final Map<String, Long> mapRecords = new HashMap<>();

    // =

    /**
     * 判断两次点击的间隔 小于默认间隔时间(1秒), 则认为是多次无效点击
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DF_DIFF);
    }

    /**
     * 判断两次点击的间隔 小于默认间隔时间(1秒), 则认为是多次无效点击
     * @param tagId
     * @return
     */
    public static boolean isFastDoubleClick(final int tagId) {
        return isFastDoubleClick(tagId, DF_DIFF);
    }

    /**
     * 判断两次点击的间隔 小于间隔时间(diff), 则认为是多次无效点击
     * @param tagId
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(final int tagId, final long diff) {
        long cTime = System.currentTimeMillis();
        long dTime = cTime - lastClickTime;
        // 判断时间是否超过
        if (lastTagId == tagId && lastClickTime > 0 && dTime < diff) {
            LogPrintUtils.dTag(TAG, "isFastDoubleClick 无效点击 => tagId: " + tagId + ", diff: " + diff);
            return true;
        }
        LogPrintUtils.dTag(TAG, "isFastDoubleClick 有效点击 => tagId: " + tagId + ", diff: " + diff);
        lastTagId = tagId;
        lastClickTime = cTime;
        return false;
    }

    // =

    /**
     * 判断两次点击的间隔(根据默认Tag判断) 小于指定间隔时间, 则认为是多次无效点击
     * @param tag
     * @return
     */
    public static boolean isFastDoubleClick(final String tag) {
        // 获取配置时间
        Long config_time = mapConfig.get(tag);
        // 如果等于null, 则传入默认时间
        if (config_time == null) {
            return isFastDoubleClick(tag, DF_DIFF);
        }
        return isFastDoubleClick(tag, config_time);
    }

    /**
     * 判断两次点击的间隔 小于间隔时间(diff), 则认为是多次无效点击
     * @param tag
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(final String tag, final long diff) {
        // 获取上次点击的时间
        Long lastTime = mapRecords.get(tag);
        if (lastTime == null) {
            lastTime = 0l;
        }
        long cTime = System.currentTimeMillis();
        long dTime = cTime - lastTime;
        // 判断时间是否超过
        if (lastTime > 0 && dTime < diff) {
            LogPrintUtils.dTag(TAG, "isFastDoubleClick 无效点击 => tag: " + tag + ", diff: " + diff);
            return true;
        }
        LogPrintUtils.dTag(TAG, "isFastDoubleClick 有效点击 => tag: " + tag + ", diff: " + diff);
        // 保存上次点击时间
        mapRecords.put(tag, cTime);
        return false;
    }

    // =

    /**
     * 判断两次点击的间隔(根据默认Tag判断) 小于指定间隔时间, 则认为是多次无效点击
     * @param object
     * @return
     */
    public static boolean isFastDoubleClick(final Object object) {
        // 获取TAG
        String tag = ((object != null) ? ("obj_" + object.hashCode()) : "obj_null");
        // 获取配置时间
        Long config_time = mapConfig.get(tag);
        // 如果等于null, 则传入默认时间
        if (config_time == null) {
            return isFastDoubleClick(tag, DF_DIFF);
        }
        return isFastDoubleClick(tag, config_time);
    }

    /**
     * 判断两次点击的间隔 小于间隔时间(diff), 则认为是多次无效点击
     * @param object
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(final Object object, final long diff) {
        // 获取TAG
        String tag = ((object != null) ? ("obj_" + object.hashCode()) : "obj_null");
        // 获取上次点击的时间
        Long lastTime = mapRecords.get(tag);
        if (lastTime == null) {
            lastTime = 0l;
        }
        long cTime = System.currentTimeMillis();
        long dTime = cTime - lastTime;
        // 判断时间是否超过
        if (lastTime > 0 && dTime < diff) {
            LogPrintUtils.dTag(TAG, "isFastDoubleClick 无效点击 => obj: " + object + ", diff: " + diff);
            return true;
        }
        LogPrintUtils.dTag(TAG, "isFastDoubleClick 有效点击 => obj: " + object + ", diff: " + diff);
        // 保存上次点击时间
        mapRecords.put(tag, cTime);
        return false;
    }

    // =

    /**
     * 初始化配置信息
     * @param mapConfig
     */
    public static void initConfig(final Map<String, Object> mapConfig) {
        if (mapConfig != null) {
            mapConfig.putAll(mapConfig);
        }
    }

    /**
     * 添加配置信息
     * @param key
     * @param val
     */
    public static void putConfig(final String key, final Long val) {
        mapConfig.put(key, val);
    }

    /**
     * 移除配置信息
     * @param key
     */
    public static void removeConfig(final String key) {
        mapConfig.remove(key);
    }

    // =

    /**
     * 移除点击记录
     * @param key
     */
    public static void removeRecord(final String key) {
        mapRecords.remove(key);
    }

    /**
     * 清空点击记录(全部)
     */
    public static void clearRecord() {
        mapRecords.clear();
    }

    /**
     * 增加控件的触摸范围，最大范围只能是父布局所包含的的区域
     * @param view
     * @param range
     */
    public static void addTouchArea(final View view, final int range) {
        addTouchArea(view, range, range, range, range);
    }

    /**
     * 增加控件的触摸范围，最大范围只能是父布局所包含的的区域
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void addTouchArea(final View view, final int top, final int bottom, final int left, final int right) {
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
                            if (View.class.isInstance(view.getParent())) {
                                ((View) view.getParent()).setTouchDelegate(touchDelegate);
                            }
                        } catch (Exception e) {
                            LogPrintUtils.eTag(TAG, e, "addTouchArea - runnable");
                        }
                    }
                });
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "addTouchArea");
            }
        }
    }
}
