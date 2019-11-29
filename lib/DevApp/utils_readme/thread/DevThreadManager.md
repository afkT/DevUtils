# 线程工具类

#### 使用演示类 [ThreadUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/thread/ThreadUse.java) 介绍了配置参数及使用

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread)

* 线程池管理 - 开发类（[DevThreadManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadManager.java)）：内部封装 DevThreadPool 配置处理, 方便直接使用

* 线程池 - 开发类（[DevThreadPool.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadPool.java)）：具体线程池操作方法、线程处理等

## API 文档

* **线程池管理 - 开发类 ->** [DevThreadManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevThreadManager 实例 |
| initConfig | 初始化配置信息 |
| putConfig | 添加配置信息 |
| removeConfig | 移除配置信息 |


* **线程池 - 开发类 ->** [DevThreadPool.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/common/thread/DevThreadPool.java)

| 方法 | 注释 |
| :- | :- |
| execute | 加入到线程池任务队列 |
| shutdown | shutdown 会等待所有提交的任务执行完成, 不管是正在执行还是保存在任务队列中的已提交任务 |
| shutdownNow | shutdownNow会尝试中断正在执行的任务(其主要是中断一些指定方法如sleep方法), 并且停止执行等待队列中提交的任务 |
| isShutdown | 判断线程池是否已关闭 = isShutDown当调用shutdown()方法后返回为 true |
| isTerminated | 若关闭后所有任务都已完成, 则返回 true. |
| awaitTermination | 请求关闭、发生超时或者当前线程中断 |
| submit | 提交一个Callable任务用于执行 |
| invokeAll | 执行给定的任务 |
| invokeAny | 执行给定的任务 |
| schedule | 延迟执行Runnable命令 |
| scheduleWithFixedRate | 延迟并循环执行命令 |
| scheduleWithFixedDelay | 延迟并以固定休息时间循环执行命令 |

#### 使用方法
```java
Runnable runnable = new Runnable() {
    @Override
    public void run() {

    }
};

// = 优先判断 10个线程数, 的线程池是否存在, 不存在则创建, 存在则复用 =
DevThreadManager.getInstance(10).execute(runnable);

// 与上面 传入 int 是完全不同的线程池
DevThreadManager.getInstance("10").execute(runnable);

// 可以先增加配置
DevThreadManager.putConfig("QPQP", new DevThreadPool(DevThreadPool.DevThreadPoolType.CALC_CPU));
// 使用配置的信息
DevThreadManager.getInstance("QPQP").execute(runnable);


DevThreadManager.putConfig("QQQQQQ", 10);
// 使用配置的信息
DevThreadManager.getInstance("QQQQQQ").execute(runnable);
```