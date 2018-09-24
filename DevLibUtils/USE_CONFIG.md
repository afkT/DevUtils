
## 初始化

```java
/**
 * detail: 全局Application
 * Created by Ttt
 */
public class BaseApplication extends Application{

    // 日志TAG
    private final String LOG_TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化工具类
        DevUtils.init(this.getApplicationContext());
        // == 初始化日志配置 ==
        // 设置默认Logger配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = LOG_TAG;
        DevLogger.init(logConfig);
        // 打开 lib 内部日志
        DevUtils.openLog();
        DevUtils.openDebug();
    }
}
```

# 配置使用相关 - [目录](https://github.com/afkT/DevUtils/tree/master/app/src/main/java/com/dev/use)


## 日志配置 - [DevLogger](https://github.com/afkT/DevUtils/tree/master/app/src/main/java/com/dev/use/logger)

> [LoggerUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/logger/LoggerUse.java) 介绍了配置参数及使用

```java
/**
 * detail: 日志使用方法
 * Created by Ttt
 */
class LoggerUse {

    private LoggerUse() {
    }

    /** 日志Tag */
    private final static String LOG_TAG = LoggerUse.class.getSimpleName();
    /** 日志文件夹路径 */
    public static final String LOG_SD_PATH = Config.SDP_PATH + File.separator + "Logger" + File.separator;

    // ================
    // ===== 配置 =====
    // ================

    /**
     * 日志配置相关
     */
    private void logConfig(){
        // == 在BaseApplication 中调用 ==
        // 初始化日志配置
        LogConfig lConfig = new LogConfig();
        // 堆栈方法总数(显示经过的方法)
        lConfig.methodCount = 3;
        // 堆栈方法索引偏移(0 = 最新经过调用的方法信息,偏移则往上推,如 1 = 倒数第二条经过调用的方法信息)
        lConfig.methodOffset = 0;
        // 是否输出全部方法(在特殊情况下，如想要打印全部经过的方法，但是不知道经过的总数)
        lConfig.isOutputMethodAll = false;
        // 显示日志线程信息(特殊情况，显示经过的线程信息,具体情况如上)
        lConfig.isDisplayThreadInfo = false;
        // 是否排序日志(格式化后)
        lConfig.isSortLog = false;
        // 日志级别
        lConfig.logLevel = LogLevel.DEBUG;
        // 设置Tag（特殊情况使用，不使用全部的Tag时,如单独输出在某个Tag下）
        lConfig.tag = "BaseLog";
        // 进行初始化配置 => 这样设置后, 默认全部日志都使用改配置, 特殊使用 DevLogger.other(config).d(xxx);
        DevLogger.init(lConfig);
//        // 进行初始化配置 - 必须调用 => 在DevUtils.init() 内部调用了
//        DevLoggerUtils.appInit(mContext);
    }

    // === 使用 ===

    /**
     * 日志使用方法
     */
    public static void loggerUse() {
        // 测试打印Log所用时间
        textTime();

        // try, catch 保存异常日志
        exLog();

        // 正常保存日志
        saveLog();

        // 使用日志操作
        tempLog();
    }
}
```


## 自定义Toast (Toasty) 配置 - [Toasty](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/toast/cus/Toasty.java)

> [ToastyUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/toasty/ToastyUse.java) 介绍了配置参数及使用

```java
/**
 * detail: Toasty 使用方法
 * Created by Ttt
 */
class ToastyUse {

    /**
     * 配置相关
     */
    private void config(){
        Toasty.Config config = Toasty.Config.getInstance();
        // 设置异常、错误颜色 - 红色
        config.setErrorColor(Color.parseColor("#D50000"));
        // 设置打印信息颜色 - 海洋蓝
        config.setInfoColor(Color.parseColor("#3F51B5"));
        // 设置成功颜色 - 绿色
        config.setSuccessColor(Color.parseColor("#388E3C"));
        // 设置警告颜色 - 橙色
        config.setWarningColor(Color.parseColor("#FFA900"));
        // ==
        // 设置字体样式
        config.setToastTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        // 设置字体大小 sp
        config.setTextSize(16);
        // 是否渲染图标
        config.setTintIcon(true);
        // 是否使用新的Toast
        config.setNewToast(true);
        // 应用配置
        config.apply();
    }

    /**
     * 使用
     */
    private void use(){
//        // 显示成功 Toast
//        Toasty.success(mActivity, "Success!", Toast.LENGTH_SHORT, true);
//        // 显示异常 Toast
//        Toasty.error(mActivity, "Error!", Toast.LENGTH_SHORT, true);
//        // 显示信息 Toast
//        Toasty.info(mActivity, "Info!", Toast.LENGTH_SHORT, true);
//        // 显示警告 Toast
//        Toasty.warning(mActivity, "Warning!", Toast.LENGTH_SHORT, true);
//        // 显示默认 Toast (灰色)
//        Toasty.normal(mActivity, "Normal!", Toast.LENGTH_SHORT);
//
//        // -- 使用自定义配置 --
//        Toasty.custom(mActivity, "自定义Toast", ToastyUtils.getDrawable(DevUtils.getContext(), R.mipmap.ic_launcher), Color.RED, Toast.LENGTH_LONG, true);
//
//        Toasty.custom(mActivity, "自定义Toast", ToastyUtils.getDrawable(DevUtils.getContext(), R.mipmap.ic_launcher), Color.RED, Toast.LENGTH_LONG, true, false);
//
//        // -- 或者获取 Toast View 进行自定义 --
//
//        // 引入View
//        final View toastLayout = ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(dev.utils.R.layout.dev_toast_layout, null);
//        // 初始化View
//        final ImageView toastIcon = (ImageView) toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_igview);
//        final TextView toastTextView = (TextView) toastLayout.findViewById(dev.utils.R.id.vid_dtl_toast_tv);
//
//        // 设置背景色
//        // ToastyUtils.setBackground(toastLayout, xxx);
//        // 设置字体色
//        // toastTextView.setTextColor(xxx);
//        // toastTextView.setTextColor(Toasty.Config.getInstance().getErrorColor());
//        // 是否显示图标
//        // ViewUtils.setVisibility(false, toastIcon);
//
//        // 可以通过 Toasty.Config.getInstance().getXxx 获取配置的参数
//
//        // 显示Toast
//        Toasty.showToasty(mActivity, toastLayout, duration, isNewToast);
    }
}
```


## 定时器工具类 使用 - [TimerManager](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/assist/manager/TimerManager.java)

> [TimerUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/timer/TimerUse.java) 介绍了配置参数及使用

```java
/**
 * detail: 定时器使用方法
 * Created by Ttt
 */
class TimerUse {

    /** 日志Tag */
    private final static String TAG = TimerUse.class.getSimpleName();
    // 创建定时器
    TimerManager.AbsTimer absTimer;
    /** 通知常量 */
    private final int NOTIFY = 100;

    /**
     * 定时器使用方法
     */
    private void timerUse(){

//        /** 创建定时器 => 立即执行,无限循环,通知默认what */
//        public static TimerManager.AbsTimer creTimer(Handler handler, long period) {
//            return creTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, -1);
//        }
//
//        /** 创建定时器 => 无限循环,通知默认what */
//        public static TimerManager.AbsTimer creTimer(Handler handler, long delay, long period) {
//            return creTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, delay, period, -1);
//        }
//
//        /** 创建定时器 => 立即执行,通知默认what */
//        public static TimerManager.AbsTimer creTimer(Handler handler, long period, int triggerLimit) {
//            return creTimer(handler, TimerManager.AbsTimer.TIMER_NOTIFY_WHAT, 0l, period, triggerLimit);
//        }
//
//        /** 创建定时器 => 立即执行,无限循环 */
//        public static TimerManager.AbsTimer creTimer(Handler handler, int what, long period) {
//            return creTimer(handler, what, 0l, period, -1);
//        }
//
//        /** 创建定时器 => 无限循环 */
//        public static TimerManager.AbsTimer creTimer(Handler handler, int what, long delay, long period) {
//            return creTimer(handler, what, delay, period, -1);
//        }
//
//        /** 创建定时器 => 立即执行 */
//        public static TimerManager.AbsTimer creTimer(Handler handler, int what, long period, int triggerLimit) {
//            return creTimer(handler, what, 0l, period, triggerLimit);
//        }
//
//        /**
//         * 创建定时器
//         * @param handler 通知的Handler
//         * @param what 通知的what
//         * @param delay 延迟时间 - 多少毫秒后开始执行
//         * @param period 循环时间 - 每隔多少秒执行一次
//         * @param triggerLimit 触发次数上限(-1,表示无限循环)
//         * @return
//         */
//        public static TimerManager.AbsTimer creTimer(Handler handler, int what, long delay, long period, int triggerLimit) {
//            return new TimerManager.TimerTask(handler, what, delay, period, triggerLimit);
//        }

        // 初始化定时器任务
        absTimer = TimerManager.creTimer(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // 获取触发次数
                int number = absTimer.getTriggerNumber();
                // 触发次数
                if (number == 1){
                    DevLogger.dTag(TAG, "第一次触发, 0.5秒延迟");
                } else {
                    DevLogger.dTag(TAG, "每隔2秒触发一次, 触发次数: " + number);
                }
            }
        }, NOTIFY, 500l, 2000l, -1);
        // 开始定时
        absTimer.startTimer();


//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                switch (msg.what){
//                    case NOTIFY:
//                        // 获取触发次数
//                        DevLogger.dTag(TAG, "已经触发次数：" + absTimer.getTriggerNumber());
//                        DevLogger.dTag(TAG, "触发上限次数：" + absTimer.getTriggerLimit());
//                        // 判断是否触发结束
//                        if (absTimer.isTriggerEnd()){
//                            DevLogger.dTag(TAG, "触发结束");
//                        }
//                        break;
//                }
//            }
//        };
//
//        // 配置参数 - 意思是 一开始0秒直接触发第一次，然后后面每隔60秒触发一次，通过Handler通知 NOTIFY 常量 (-1表示无限次)
//        absTimer.setTriggerLimit(-1).setTime(0, 60 * 1000).setNotifyWhat(NOTIFY);
//
//        // 配置参数 - 一秒钟后进行触发,然后每隔1秒循环触发(但是触发一次 TriggerLimit 限制了次数), 并通过设置的Handler通知 对应传入的What
//        absTimer.setHandler(handler).setTriggerLimit(1).setTime(1000, 1000).setNotifyWhat(NOTIFY);
//
//        // 配置参数 - 3秒钟后进行触发,然后每隔3秒循环触发(但是触发10次 TriggerLimit 限制了次数), 并通过设置的Handler通知 对应传入的What,并且开始定时器
//        absTimer.setHandler(handler).setTriggerLimit(10).setTime(3000, 3000).setNotifyWhat(NOTIFY).startTimer();
//
//        // 开始运行定时器
//        absTimer.startTimer();
//
//        // 关闭定时器
//        absTimer.closeTimer();
//
//        // 判断是否运行中
//        absTimer.isRunTimer();
//
//        /** 关闭所有符合对应的标记id的定时器任务 */
//        TimerManager.closeMark(id);
//
//        /** 关闭所有符合对应的字符串标记的定时器任务 */
//        TimerManager.closeMark("mark");
//
//        /** 关闭所有无限循环的任务 */
//        TimerManager.closeInfiniteTask();
//
//        /** 关闭全部任务 */
//        TimerManager.closeAll();
//
//        /** 回收资源 - 回收需要回收的 */
//        TimerManager.gc();
    }
}
```


## 多媒体工具类 使用 - [DevMediaManager](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/player/DevMediaManager.java)

> [MediaUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/media/MediaUse.java) 介绍了配置参数及使用

```java
/**
 * detail: 多媒体使用方法
 * Created by Ttt
 */
class MediaUse {

    /**
     * 多媒体使用方法
     */
    private void mediaUse(){
        // 默认事件监听
        DevMediaManager.getInstance().setMeidaListener(new DevMediaManager.MediaListener() {
            @Override
            public void onPrepared() {
                DevMediaManager.getInstance().getMediaPlayer().start();
            }

            @Override
            public void onCompletion() {
            }

            @Override
            public void onBufferingUpdate(int percent) {
            }

            @Override
            public void onSeekComplete() {
            }

            @Override
            public void onError(int what, int extra) {
            }

            @Override
            public void onVideoSizeChanged(int width, int height) {
            }
        });

        // =======

        // 播放音频
        DevMediaManager.getInstance().playPrepareRaw(R.raw.dev_beep);
        DevMediaManager.getInstance().playPrepareAssets("a.mp3");
        DevMediaManager.getInstance().playPrepare(SDCardUtils.getSDCardPath() + "/a.mp3");
        DevMediaManager.getInstance().playPrepare("http://xxx.mp3");
        DevMediaManager.getInstance().playPrepare(new DevMediaManager.MediaSet(){

            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息

        // =======

        SurfaceView surfaceView = null;
        // 播放视频
        DevVideoPlayerControl control = new DevVideoPlayerControl(surfaceView);
        control.startPlayer(SDCardUtils.getSDCardPath() + "/video_3.mp4");
        control.startPlayer("http://xxx.mp4");
        control.startPlayer(new DevMediaManager.MediaSet(){
            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息
    }
}
```