# 定时器工具类

#### 使用演示类 [TimerUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/timer/TimerUse.java) 介绍了配置参数及使用

```java
/**
 * 主要是为了控制整个项目的定时器,防止定时器混乱,或者导致忘记关闭等情况,以及减少初始化等操作代码
 * 主要实现是 AbsTimer、TimerTask 这两个类,
 * AbsTimer -> 定时器抽象类,对外提供该类对象,以及内部方法,便于内部实现方法的隐藏,以及达到对定时器任务的控制处理
 * TimerTask -> 内部私有类,实现了具体的定时器操作,以及代码控制等,防止外部直接new,导致定时器混乱
 * =
 * 如果外部想要实现定时器,但是通过内部 ArrayList 控制,也可以通过 实现AbsTimer接口,内部的startTimer()、closeTimer() 进行了对AbsTimer的保存，标记等操作
 * 需要注意的是,实现start(close)Timer() 方法,必须保留 super.start(close)Timer(); -> 内部 ArrayList 进行了操作,而不对外开放(不需要主动调用)
 * =
 * startTimer() -> 主要进行添加到 ArrayList, 并且标记不需要回收
 * closeTimer() -> 不直接操作remove,防止出现ConcurrentModificationException 异常, 而是做一个标记,便于后续回收
 */
```

#### 项目类结构

* 定时器工具类（[TimerManager](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/assist/manager/TimerManager.java)）：定时器工具类

* TimerManager.AbsTimer：定时器抽象类,对外提供该类对象,以及内部方法,便于内部实现方法的隐藏,以及达到对定时器任务的控制处理

* TimerManager.TimerTask：内部私有类,实现了具体的定时器操作,以及代码控制等,防止外部直接new,导致定时器混乱


#### 框架亮点

* 控制整个项目定时器, 防止定时器混乱、忘记关闭等情况, 统一控制管理

* 内部自动添加定时器到集合中, 便于项目控制处理

* 支持关闭指定 mark timer、 all timer , 获取指定 Timer

* 内部封装 Timer, 支持获取执行次数、是否无限循环、通知what、通知Obj、mark标记等通用功能

## API 文档

| 方法 | 注释 |
| :- | :- |
| gc | 回收资源 |
| timerSize | 获取全部任务总数 |
| getTimer | 获取属于对应字符串标记的定时器任务(优先获取符合的) |
| closeAll | 关闭全部任务 |
| closeInfiniteTask | 关闭所有无限循环的任务 |
| closeMark | 关闭所有符合对应的字符串标记的定时器任务 |
| createTimer | 创建定时器 => 立即执行,无限循环,通知默认what |
| getMarkId | 获取标记id |
| getMarkStr | 获取标记字符串 |
| setMarkId | 设置标记id |
| setMarkStr | 设置标记字符串 |
| startTimer | 运行定时器 |
| closeTimer | 关闭定时器 |
| isRunTimer | 判断是否运行中 |
| getTriggerNumber | 获取已经触发的次数 |
| getTriggerLimit | 获取允许触发的上限次数 |
| isTriggerEnd | 是否触发结束(到达最大次数) |
| isInfinite | 是否无限循环 |
| setHandler | 设置通知的Handler |
| setNotifyWhat | 设置通知的What |
| setNotifyObject | 设置通知的Obj |
| setTime | 设置时间 |
| setTriggerLimit | 设置触发次数上限 |


#### 使用方法
```java
/** 创建定时器 => 立即执行,无限循环,通知默认what */
public static TimerManager.AbsTimer createTimer(Handler handler, long period) {
    return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, -1);
}

/** 创建定时器 => 无限循环,通知默认what */
public static TimerManager.AbsTimer createTimer(Handler handler, long delay, long period) {
    return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, delay, period, -1);
}

/** 创建定时器 => 立即执行,通知默认what */
public static TimerManager.AbsTimer createTimer(Handler handler, long period, int triggerLimit) {
    return createTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, triggerLimit);
}

/** 创建定时器 => 立即执行,无限循环 */
public static TimerManager.AbsTimer createTimer(Handler handler, int what, long period) {
    return createTimer(handler, what, 0l, period, -1);
}

/** 创建定时器 => 无限循环 */
public static TimerManager.AbsTimer createTimer(Handler handler, int what, long delay, long period) {
    return createTimer(handler, what, delay, period, -1);
}

/** 创建定时器 => 立即执行 */
public static TimerManager.AbsTimer createTimer(Handler handler, int what, long period, int triggerLimit) {
    return createTimer(handler, what, 0l, period, triggerLimit);
}

/**
 * 创建定时器
 * @param handler 通知的Handler
 * @param what 通知的what
 * @param delay 延迟时间 - 多少毫秒后开始执行
 * @param period 循环时间 - 每隔多少秒执行一次
 * @param triggerLimit 触发次数上限(-1,表示无限循环)
 * @return
 */
public static TimerManager.AbsTimer createTimer(Handler handler, int what, long delay, long period, int triggerLimit) {
    return new TimerManager.TimerTask(handler, what, delay, period, triggerLimit);
}

// 初始化定时器任务
absTimer = TimerManager.createTimer(new Handler() {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        // 获取触发次数
        int number = absTimer.getTriggerNumber();
        // 触发次数
        if (number == 1) {
            DevLogger.dTag(TAG, "第一次触发, 0.5秒延迟");
        } else {
            DevLogger.dTag(TAG, "每隔2秒触发一次, 触发次数: " + number);
        }
    }
}, NOTIFY, 500l, 2000l, -1);
// 开始定时
absTimer.startTimer();


Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case NOTIFY:
                // 获取触发次数
                DevLogger.dTag(TAG, "已经触发次数：" + absTimer.getTriggerNumber());
                DevLogger.dTag(TAG, "触发上限次数：" + absTimer.getTriggerLimit());
                // 判断是否触发结束
                if (absTimer.isTriggerEnd()) {
                    DevLogger.dTag(TAG, "触发结束");
                }
                break;
        }
    }
};

// 配置参数 - 意思是 一开始0秒直接触发第一次，然后后面每隔60秒触发一次，通过Handler通知 NOTIFY 常量 (-1表示无限次)
absTimer.setTriggerLimit(-1).setTime(0, 60 * 1000).setNotifyWhat(NOTIFY);

// 配置参数 - 一秒钟后进行触发,然后每隔1秒循环触发(但是触发一次 TriggerLimit 限制了次数), 并通过设置的Handler通知 对应传入的What
absTimer.setHandler(handler).setTriggerLimit(1).setTime(1000, 1000).setNotifyWhat(NOTIFY);

// 配置参数 - 3秒钟后进行触发,然后每隔3秒循环触发(但是触发10次 TriggerLimit 限制了次数), 并通过设置的Handler通知 对应传入的What,并且开始定时器
absTimer.setHandler(handler).setTriggerLimit(10).setTime(3000, 3000).setNotifyWhat(NOTIFY).startTimer();

// 开始运行定时器
absTimer.startTimer();

// 关闭定时器
absTimer.closeTimer();

// 判断是否运行中
absTimer.isRunTimer();

/** 关闭所有符合对应的标记id的定时器任务 */
TimerManager.closeMark(id);

/** 关闭所有符合对应的字符串标记的定时器任务 */
TimerManager.closeMark("mark");

/** 关闭所有无限循环的任务 */
TimerManager.closeInfiniteTask();

/** 关闭全部任务 */
TimerManager.closeAll();

/** 回收资源 - 回收需要回收的 */
TimerManager.gc();
```