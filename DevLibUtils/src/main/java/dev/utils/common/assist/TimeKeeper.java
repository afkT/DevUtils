package dev.utils.common.assist;

/**
 * detail: 堵塞时间处理
 * Created by Ttt
 */
public class TimeKeeper {

    /**
     * 设置等待一段时间后, 通知方法 (异步)
     * @param keepTimeMillis 堵塞时间
     * @param endCallback
     * @return
     */
    public void waitForEndAsyn(final long keepTimeMillis, final OnEndCallback endCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                waitForEnd(keepTimeMillis, endCallback);
            }
        }).start();
    }

    /**
     * 设置等待一段时间后, 通知方法 (同步)
     * @param keepTimeMillis 堵塞时间
     * @param endCallback
     * @return
     */
    public void waitForEnd(final long keepTimeMillis, final OnEndCallback endCallback) {
        if (keepTimeMillis >= 0l) {
            // 开始堵塞时间
            long startTime = System.currentTimeMillis();
            try {
                // 进行堵塞
                Thread.sleep(keepTimeMillis);
                // 触发回调
                if (endCallback != null) {
                    endCallback.onEnd(startTime, keepTimeMillis, System.currentTimeMillis(), false);
                }
            } catch (Exception e) {
                // 触发回调
                if (endCallback != null) {
                    endCallback.onEnd(startTime, keepTimeMillis, System.currentTimeMillis(), true);
                }
            }
        }
    }

    /**
     * detail: 结束通知回调
     * Created by Ttt
     */
    public interface OnEndCallback {

        /**
         * 结束触发通知方法
         * @param startTimeMillis 开始堵塞时间
         * @param keepTimeMillis  堵塞时间
         * @param endTimeMillis   结束时间
         * @param isError         是否异常
         */
        void onEnd(long startTimeMillis, long keepTimeMillis, long endTimeMillis, boolean isError);
    }
}
