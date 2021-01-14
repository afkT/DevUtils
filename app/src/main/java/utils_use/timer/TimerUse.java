package utils_use.timer;

import android.os.Handler;
import android.os.Message;

import dev.engine.log.DevLogEngine;
import dev.utils.app.assist.manager.TimerManager;

/**
 * detail: 定时器使用方法
 * @author Ttt
 */
public final class TimerUse {

    private TimerUse() {
    }

    // 日志 TAG
    private static final String                TAG    = TimerUse.class.getSimpleName();
    // 创建定时器
    private              TimerManager.AbsTimer absTimer;
    // 通知常量
    private static final int                   NOTIFY = 100;

    /**
     * 定时器使用方法
     */
    private void timerUse() {

//        // 创建定时器 ( 立即执行、无限循环、通知默认 what )
//        public static TimerManager.AbsTimer createTimer(Handler handler, long period) {
//            return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0L, period, -1);
//        }
//
//        // 创建定时器 ( 无限循环、通知默认 what )
//        public static TimerManager.AbsTimer createTimer(Handler handler, long delay, long period) {
//            return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, delay, period, -1);
//        }
//
//        // 创建定时器 ( 立即执行、通知默认 what )
//        public static TimerManager.AbsTimer createTimer(Handler handler, long period, int triggerLimit) {
//            return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0L, period, triggerLimit);
//        }
//
//        // 创建定时器 ( 立即执行、无限循环 )
//        public static TimerManager.AbsTimer createTimer(Handler handler, int what, long period) {
//            return createTimer(handler, what, 0L, period, -1);
//        }
//
//        // 创建定时器 ( 无限循环 )
//        public static TimerManager.AbsTimer createTimer(Handler handler, int what, long delay, long period) {
//            return createTimer(handler, what, delay, period, -1);
//        }
//
//        // 创建定时器 ( 立即执行 )
//        public static TimerManager.AbsTimer createTimer(Handler handler, int what, long period, int triggerLimit) {
//            return createTimer(handler, what, 0L, period, triggerLimit);
//        }
//
//        /**
//         * 创建定时器
//         * @param handler 通知的 Handler
//         * @param what 通知的 what
//         * @param delay 延迟时间 ( 多少毫秒后开始执行 )
//         * @param period 循环时间 ( 每隔多少秒执行一次 )
//         * @param triggerLimit 触发次数上限 ( -1 表示无限循环 )
//         * @return
//         */
//        public static TimerManager.AbsTimer createTimer(Handler handler, int what, long delay, long period, int triggerLimit) {
//            return new TimerManager.TimerTask(handler, what, delay, period, triggerLimit);
//        }

        // 初始化定时器任务
        absTimer = TimerManager.createTimer(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // 获取触发次数
                int number = absTimer.getTriggerNumber();
                // 触发次数
                if (number == 1) {
                    DevLogEngine.getEngine().dTag(TAG, "第一次触发, 0.5 秒延迟");
                } else {
                    DevLogEngine.getEngine().dTag(TAG, "每隔 2 秒触发一次, 触发次数: %s", number);
                }
            }
        }, NOTIFY, 500L, 2000L, -1);
        // 开始定时
        absTimer.startTimer();

//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                switch (msg.what) {
//                    case NOTIFY:
//                        // 获取触发次数
//                        DevLogEngine.getEngine().dTag(TAG, "已经触发次数: %s", absTimer.getTriggerNumber());
//                        DevLogEngine.getEngine().dTag(TAG, "触发上限次数: %s", absTimer.getTriggerLimit());
//                        // 判断是否触发结束
//                        if (absTimer.isTriggerEnd()) {
//                            DevLogEngine.getEngine().dTag(TAG, "触发结束");
//                        }
//                        break;
//                }
//            }
//        };
//
//        // 配置参数 - 意思是 一开始 0 秒直接触发第一次, 然后后面每隔 60 秒触发一次, 通过 Handler 通知 NOTIFY 常量 ( -1 表示无限次 )
//        absTimer.setTriggerLimit(-1).setTime(0, 60 * 1000).setNotifyWhat(NOTIFY);
//        // 配置参数 - 一秒钟后进行触发, 然后每隔 1 秒循环触发 ( 但是触发一次 TriggerLimit 限制了次数 ), 并通过设置的 Handler 通知 对应传入的 What
//        absTimer.setHandler(handler).setTriggerLimit(1).setTime(1000, 1000).setNotifyWhat(NOTIFY);
//        // 配置参数 - 3 秒钟后进行触发, 然后每隔 3 秒循环触发 ( 但是触发 10 次 TriggerLimit 限制了次数 ), 并通过设置的 Handler 通知 对应传入的 What, 并且开始定时器
//        absTimer.setHandler(handler).setTriggerLimit(10).setTime(3000, 3000).setNotifyWhat(NOTIFY).startTimer();
//        // 开始运行定时器
//        absTimer.startTimer();
//        // 关闭定时器
//        absTimer.closeTimer();
//        // 判断是否运行中
//        absTimer.isRunTimer();
//
//        int id = 0;
//        // 关闭所有符合对应的标记 id 的定时器任务
//        TimerManager.closeMark(id);
//        // 关闭所有符合对应的字符串标记的定时器任务
//        TimerManager.closeMark("mark");
//        // 关闭所有无限循环的任务
//        TimerManager.closeInfiniteTask();
//        // 关闭所有未运行的任务
//        TimerManager.closeNotRunTask();
//        // 关闭全部任务
//        TimerManager.closeAll();
//        // 回收定时器资源
//        TimerManager.gc();
//        // 获取全部任务总数
//        TimerManager.getTimerSize();
//        // 获取属于标记 id 的定时器任务 ( 优先获取符合的 )
//        TimerManager.getTimer(id);
//        // 获取属于对应字符串标记的定时器任务 ( 优先获取符合的 )
//        TimerManager.getTimer("mark");
//        // 获取属于标记 id 的定时器任务集合
//        TimerManager.getTimers(id);
//        // 获取属于对应字符串标记的定时器任务集合
//        TimerManager.getTimers("mark");
    }
}