
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
        logConfig.isSortLog = true; // 美化日志, 边框包围
        DevLogger.init(logConfig);
        // 打开 lib 内部日志 - 线上环境, 不调用方法就行
        DevUtils.openLog();
        DevUtils.openDebug();
    }
}
```

# 配置与使用相关 - [目录](https://github.com/afkT/DevUtils/tree/master/app/src/main/java/com/dev/use)

## [DevLogger 日志工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/logger/DevLogger.md)

## [DevToast Toast工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/DevToast.md)

## [ToastTintUtils Toast美化工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/ToastTintUtils.md)

## [TimerManager 定时器工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/timer/TimerManager.md)

## [DevMediaManager 多媒体工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/media/DevMediaManager.md)

## [FileRecordUtils、AnalysisRecordUtils、DevLoggerUtils 日志、异常文件记录保存工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/record/FileRecord.md)

## [DevThreadManager - 线程工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/thread/DevThreadManager.md)

## [DevCache - 缓存工具类文档](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/cache/DevCache.md)

# == 使用相关 ==

## Wifi热点工具类 使用 - [WifiHotUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/wifi/WifiHotUtils.java)

> [WifiHotUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/wifi/WifiHotUse.java) 介绍了配置参数及使用

```java
/**
 * detail: Wifi热点使用方法
 * Created by Ttt
 */
class WifiHotUse {

    /**
     * Wifi热点使用方法
     */
    private void wifiHotUse() {

//        // 需要权限
//        <uses-permission android:name="android.permission.WRITE_SETTINGS" />
//        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
//        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
//        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
//        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
//        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

        final WifiHotUtils wifiHotUtils = new WifiHotUtils(DevUtils.getContext());

        // 有密码
        WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", "123456789");

        // 无密码
        wifiConfiguration = WifiHotUtils.createWifiConfigToAp("WifiHot_AP", null);

        // 开启热点(兼容8.0)  7.1 跳转到热点页面, 需手动开启(但是配置信息使用上面的 WifiConfig)
        wifiHotUtils.stratWifiAp(wifiConfiguration);

        // 关闭热点
        wifiHotUtils.closeWifiAp();

        // === 8.0 特殊处理 ===

        // 8.0 以后热点是针对应用开启, 并且必须强制使用随机生成的 WifiConfig 信息, 无法替换

        // 如果应用开启了热点, 然后后台清空内存, 对应的热点会关闭, 应用开启的热点是系统随机的，不影响系统设置中的热点配置信息

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wifiHotUtils.setOnWifiAPListener(new WifiHotUtils.onWifiAPListener() {
                @Override
                public void onStarted(WifiConfiguration wifiConfig) {
                    String ssid = wifiHotUtils.getApWifiSSID();
                    String pwd = wifiHotUtils.getApWifiPwd();
                }

                @Override
                public void onStopped() {

                }

                @Override
                public void onFailed(int reason) {

                }
            });
        }

        // 还有其他方法, 具体看 WifiHotUtils 类
    }
}
```


## ShapeUtils 工具类 使用 - [ShapeUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/ShapeUtils.java)

> [ShapeUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/shape/ShapeUse.java) 介绍了配置参数及使用

```java
/**
 * detail: ShapeUtils 使用方法
 * Created by Ttt
 */
class ShapeUse {

    private void shapeUse() {

        Button vid_btn1 = null;
//        // 默认就设置背景色
//        ShapeUtils.Builder builder = new ShapeUtils.Builder();
//        builder.setRadiusLeft(10f).setColor(R.color.black);
//        vid_btn1.setBackground(builder.build().getDrawable());
        // 设置点击效果
        GradientDrawable drawable1 = ShapeUtils.newBuilder(10f, R.color.black).setStroke(5, R.color.green).build().getDrawable();
        GradientDrawable drawable2 = ShapeUtils.newBuilder(10f, R.color.sky_blue).setStroke(5, R.color.grey).build().getDrawable();
        vid_btn1.setBackground(StateListUtils.newSelector(drawable2, drawable1)); // 设置点击 View 背景变色, 不用写 shape xml 文件
        vid_btn1.setTextColor(StateListUtils.createColorStateList(R.color.red, R.color.white)); // 设置点击字体变色

        // 设置渐变
        View vid_view1 = null;
//        int[] colors = new int[]{ Color.RED, Color.BLUE, Color.GREEN };

        int[] colors = new int[3];
        colors[0] = ContextCompat.getColor(DevUtils.getContext(), R.color.black);
        colors[1] = ContextCompat.getColor(DevUtils.getContext(), R.color.sky_blue);
        colors[2] = ContextCompat.getColor(DevUtils.getContext(), R.color.orange);

        // ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().setDrawable(vid_view1);

        GradientDrawable drawable = ShapeUtils.newBuilderToGradient(GradientDrawable.Orientation.BR_TL, colors).build().getDrawable();
//        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT); // 线性渐变，这是默认设置
//        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT); // 放射性渐变，以开始色为中心。
        drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT); // 扫描线式的渐变。
        vid_view1.setBackground(drawable);
    }
}
```


## SharedPreferences 工具类 使用 - [SharedUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/share/SharedUtils.java)

> [ShareUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/share/ShareUse.java) 介绍了配置参数及使用

```java
/**
 * detail: SharedPreferences 使用方法
 * Created by Ttt
 */
class ShareUse {

    private void shareUse() {
        // 具体实现方法 基于 PreferenceImpl 实现

        // 存在可调用的方法 IPreference

        // SharedUtils 二次分装 SPUtils, 直接调用

        // 在DevUtils.init 中初始化了, 实际可以不调用
        SharedUtils.init(DevUtils.getContext());

        SharedUtils.put("aa", "aa");
        SharedUtils.put("ac", 123);

//        // 默认值如下
//        switch (type) {
//            case INTEGER:
//                return preferences.getInt(key, -1);
//            case FLOAT:
//                return preferences.getFloat(key, -1f);
//            case BOOLEAN:
//                return preferences.getBoolean(key, false);
//            case LONG:
//                return preferences.getLong(key, -1L);
//            case STRING:
//                return preferences.getString(key, null);
//            case STRING_SET:
//                return preferences.getStringSet(key, null);
//            default: // 默认取出String类型的数据
//                return null;
//        }

        // 想要自定义 模式，名字等
        SPUtils.getPreference(DevUtils.getContext()).put("aa", 1);
        SPUtils.getPreference(DevUtils.getContext(), "xxx").put("aa", 1);
        SPUtils.getPreference(DevUtils.getContext(), "xxxxx", Context.MODE_PRIVATE).put("aa", 1);
    }
}
```


## 字体计算工具类 使用 - [TextViewUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/TextViewUtils.java)

> [TextCalcUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/use/text/TextCalcUse.java) 介绍了配置参数及使用

```java
/**
 * detail: 计算字体宽度、高度
 * Created by Ttt
 */
class TextCalcUse{

    // 日志TAG
    private static final String TAG = TextCalcUse.class.getSimpleName();

    /**
     * 计算字体宽度、高度
     */
    protected void textCalcUse() {
        LinearLayout vid_linear = null;
        // 打印信息
        for (int i = 0, len = vid_linear.getChildCount(); i < len; i++) {
            View view = vid_linear.getChildAt(i);
            if (view != null && view instanceof TextView) {
                printInfo((TextView) view);
            }
        }

//        // 计算第几位超过宽度(600)
//        int pos = TextViewUtils.calcTextWidth(vid_tv.getPaint(), "测试内容", 600);

        TextView tv = new TextView(DevUtils.getContext());
        // 获取字体高度
        TextViewUtils.getTextHeight(tv);
        // 获取字体大小
        TextViewUtils.reckonTextSize(90); // 获取字体高度为90的字体大小
    }

    // =

    /**
     * 打印信息
     * @param textView
     */
    private void printInfo(TextView textView) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n内容：" + textView.getText().toString());
        builder.append("\n高度：" + TextViewUtils.getTextHeight(textView));
        builder.append("\n偏移高度：" + TextViewUtils.getTextTopOffsetHeight(textView));
        builder.append("\n宽度：" + TextViewUtils.getTextWidth(textView));
        builder.append("\n字体大小：" + textView.getTextSize());
        builder.append("\n计算字体大小：" + TextViewUtils.reckonTextSize(TextViewUtils.getTextHeight(textView)));
        // 打印日志
        DevLogger.dTag(TAG, builder.toString());
    }
}
```