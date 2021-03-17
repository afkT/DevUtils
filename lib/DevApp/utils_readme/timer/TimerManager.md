# 定时器工具类

#### 使用演示类 [TimerActivity](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/afkt/project/ui/activity/TimerActivity.java) 介绍了配置参数及使用

```java
/**
 * 主要是为了控制整个项目的定时器, 防止定时器混乱、忘记关闭等情况, 以及减少初始化等操作代码
 */
```

#### 项目类结构

* 定时器管理类（[TimerManager](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/TimerManager.java)）：定时器管理类

* 定时器（[DevTimer.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/DevTimer.java)）：定时器封装类，配合定时器管理类使用全局控制


#### 框架亮点

* 控制整个项目定时器, 防止定时器混乱、忘记关闭等情况, 统一控制管理

* 内部自动添加定时器到集合中, 便于项目控制处理

* 支持关闭指定 tag timer、all timer, 获取指定 Timer

* 内部封装 Timer, 支持获取执行次数、是否无限循环、TAG 标记等通用功能

## API 文档

* **定时器 ->** [DevTimer.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/DevTimer.java)

| 方法 | 注释 |
| :- | :- |
| getTag | 获取 TAG |
| getUUID | 获取 UUID HashCode |
| getDelay | 获取延迟时间 ( 多少毫秒后开始执行 ) |
| getPeriod | 获取循环时间 ( 每隔多少毫秒执行一次 ) |
| isRunning | 判断是否运行中 |
| isMarkSweep | 是否标记清除 |
| getTriggerNumber | 获取已经触发的次数 |
| getTriggerLimit | 获取允许触发的上限次数 |
| isTriggerEnd | 是否触发结束 ( 到达最大次数 ) |
| isInfinite | 是否无限循环 |
| setHandler | 设置 UI Handler |
| setCallback | 设置回调事件 |
| start | 运行定时器 |
| stop | 关闭定时器 |
| setTag | setTag |
| setDelay | setDelay |
| setPeriod | setPeriod |
| getLimit | getLimit |
| setLimit | setLimit |
| build | build |
| callback | 触发回调方法 |


* **定时器管理类 ->** [TimerManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/timer/TimerManager.java)

| 方法 | 注释 |
| :- | :- |
| addContainsChecker | 添加包含校验 |
| getSize | 获取全部定时器总数 |
| recycle | 回收定时器资源 |
| getTimer | 获取对应 TAG 定时器 ( 优先获取符合的 ) |
| getTimers | 获取对应 TAG 定时器集合 |
| closeAll | 关闭全部定时器 |
| closeAllNotRunning | 关闭所有未运行的定时器 |
| closeAllInfinite | 关闭所有无限循环的定时器 |
| closeAllTag | 关闭所有对应 TAG 定时器 |
| closeAllUUID | 关闭所有对应 UUID 定时器 |


#### 使用方法
```java
DevTimer timer = new DevTimer.Builder(1500L)
        .setDelay(100L) // 延迟时间 ( 多少毫秒后开始执行 )
        .setPeriod(1500L) // 循环时间 ( 每隔多少毫秒执行一次 )
        .setTag(TAG) // 定时器 Tag
        .setLimit(19) // 触发次数上限 ( 负数为无限循环 )
        .build(); // 构建定时器
timer.setCallback(new DevTimer.Callback() {
    @Override
    public void callback(
            DevTimer timer,
            int number,
            boolean end,
            boolean infinite
    ) {
        DevLogEngine.getEngine().dTag(TAG, "是否 UI 线程: %s", HandlerUtils.isMainThread());
    }
});
// 设置了 Handler 则属于 UI 线程触发回调
timer.setHandler(mUiHandler);
// 运行定时器
timer.start();
// 关闭定时器
timer.stop();

int uuid = 0;
// 关闭所有对应 UUID 定时器
TimerManager.closeAllUUID(uuid);
// 关闭所有对应 TAG 定时器
TimerManager.closeAllTag(TAG);
// 关闭所有无限循环的定时器
TimerManager.closeAllInfinite();
// 关闭所有未运行的定时器
TimerManager.closeAllNotRunning();
// 关闭全部定时器
TimerManager.closeAll();
// 回收定时器资源
TimerManager.recycle();
// 获取全部定时器总数
TimerManager.getSize();
// 获取对应 UUID 定时器 ( 优先获取符合的 )
TimerManager.getTimer(uuid);
// 获取对应 TAG 定时器 ( 优先获取符合的 )
TimerManager.getTimer(TAG);
// 获取对应 UUID 定时器集合
TimerManager.getTimers(uuid);
// 获取对应 TAG 定时器集合
TimerManager.getTimers(TAG);
```