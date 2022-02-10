package dev.utils.app.assist.floating;

/**
 * detail: 悬浮窗触摸事件接口实现
 * @author Ttt
 */
public abstract class DevFloatingListener
        implements IFloatingListener {

    // 点击事件间隔时间
    private long mClickIntervalTime     = 200L;
    // 点击事件间隔时间
    private long mLongClickIntervalTime = 1200L;

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
    public void setClickIntervalTime(long time) {
        this.mClickIntervalTime = time;
    }

    // =

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
    public void setLongClickIntervalTime(long time) {
        this.mLongClickIntervalTime = time;
    }
}
