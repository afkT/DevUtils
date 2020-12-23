package dev.utils.common.assist;

/**
 * detail: 堵塞时间辅助类
 * @author Ttt
 */
public class TimeKeeper {

    /**
     * 设置等待一段时间后, 通知方法 ( 异步 )
     * @param keepTimeMillis 堵塞时间 ( 毫秒 )
     * @param callback       结束回调通知
     */
    public void waitForEndAsync(
            final long keepTimeMillis,
            final OnEndCallback callback
    ) {
        if (keepTimeMillis > 0L) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    waitForEnd(keepTimeMillis, callback);
                }
            }).start();
        }
    }

    /**
     * 设置等待一段时间后, 通知方法 ( 同步 )
     * @param keepTimeMillis 堵塞时间 ( 毫秒 )
     * @param callback       结束回调通知
     */
    public void waitForEnd(
            final long keepTimeMillis,
            final OnEndCallback callback
    ) {
        if (keepTimeMillis > 0L) {
            // 开始堵塞时间
            long startTime = System.currentTimeMillis();
            try {
                // 进行堵塞
                Thread.sleep(keepTimeMillis);
                // 触发回调
                if (callback != null) {
                    callback.onEnd(keepTimeMillis, startTime, System.currentTimeMillis(), false);
                }
            } catch (Exception e) {
                // 触发回调
                if (callback != null) {
                    callback.onEnd(keepTimeMillis, startTime, System.currentTimeMillis(), true);
                }
            }
        }
    }

    /**
     * detail: 结束通知回调
     * @author Ttt
     */
    public interface OnEndCallback {

        /**
         * 结束触发通知方法
         * @param keepTimeMillis  堵塞时间 ( 毫秒 )
         * @param startTimeMillis 开始堵塞时间 ( 毫秒 )
         * @param endTimeMillis   结束时间 ( 毫秒 )
         * @param isError         是否发生异常
         */
        void onEnd(
                long keepTimeMillis,
                long startTimeMillis,
                long endTimeMillis,
                boolean isError
        );
    }
}