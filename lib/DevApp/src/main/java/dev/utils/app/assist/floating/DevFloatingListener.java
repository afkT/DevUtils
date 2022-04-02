package dev.utils.app.assist.floating;

/**
 * detail: 悬浮窗触摸事件接口实现
 * @author Ttt
 * <pre>
 *     // 点击间隔时间
 *     ViewConfiguration.HOVER_TAP_TIMEOUT
 *     // 长按间隔时间
 *     ViewConfiguration.DEFAULT_LONG_PRESS_TIMEOUT
 * </pre>
 */
public abstract class DevFloatingListener
        implements IFloatingListener {

    // 点击事件间隔时间
    private long mClickIntervalTime     = 150L;
    // 长按事件间隔时间
    private long mLongClickIntervalTime = 600L;

    /**
     * 获取点击事件间隔时间
     * @return 点击事件间隔时间
     */
    @Override
    public long getClickIntervalTime() {
        return mClickIntervalTime;
    }

    /**
     * 获取点击事件间隔时间
     * @param time 点击事件间隔时间
     */
    @Override
    public void setClickIntervalTime(final long time) {
        this.mClickIntervalTime = time;
    }

    /**
     * 获取长按事件间隔时间
     * @return 长按事件间隔时间
     */
    @Override
    public long getLongClickIntervalTime() {
        return mLongClickIntervalTime;
    }

    /**
     * 获取长按事件间隔时间
     * @param time 长按事件间隔时间
     */
    @Override
    public void setLongClickIntervalTime(final long time) {
        this.mLongClickIntervalTime = time;
    }
}