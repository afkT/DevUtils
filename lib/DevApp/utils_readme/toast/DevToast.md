# Toast 工具类

#### 使用演示类 [DevToastUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/toast/DevToastUse.java) 介绍了配置参数及使用

> 1. 支持子线程弹出 Toast, 可通过开关配置
> 2. 内部解决 Android 7.1.1 崩溃问题
> 3. 已处理 部分 ROM 如魅族、小米、三星等关闭应用通知, 无法显示 Toast 问题

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster)

* Toast 工具类（[DevToast](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster/DevToast.java)）：Toast 工具类(对外公开直接调用), 直接调用 IToastImpl 类方法

* Toast 接口（[IToast](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster/IToast.java)）：主要编写 Operate 操作接口、Style 样式接口、Filter 过滤接口

* Toast 接口实现方法（[IToastImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster/IToastImpl.java)）：实现 Toast.Operate 接口, 并且对对应的方法, 进行处理

* Toast 工厂模式（[ToastFactory](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster/ToastFactory.java)）：用于生成适配不同 Android 版本对应的 Toast, 以及解决无通知权限显示 Toast

* Toast 默认样式（[DefaultToastStyle](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/toast/toaster/DefaultToastStyle.java)）：该类实现 IToast.Style 用于配置自定义 Toast


#### 框架亮点

* 支持无通知权限 Toast 弹出

* 支持不分主次线程都可以弹出 Toast, 并可通过开关控制

* 支持自动区分资源 stringId, 支持可变参数传参, 自动格式化 Toast 内容

* 使用单例 Toast, 避免频繁弹出造成不良的用户体验

* 支持自动适配, 根据不同 Android 版本、是否有通知权限, 生成不同的 Toast 对象

* 支持自定义 View 扩展, 通过 setView, 重新自定义 Toast 布局

* 支持自定义样式：Toast（Gravity、背景、圆角、边距等）、TextView（样式、颜色、大小、Ellipsize等）

* 支持全局配置样式：可通过配置全局通用样式, 或单独 Toast 特殊样式, 实现整个应用统一替换/设置

## API 文档

| 方法 | 注释 |
| :- | :- |
| reset | 重置默认参数 |
| setIsHandler | 设置是否使用 Handler 显示 Toast |
| setNullText | 设置 Text 为 null 的文本 |
| setTextLength | 设置 Toast 文案长度转换 显示时间 |
| init | Application 初始化调用 (内部已调用) |
| style | 使用单次 Toast 样式配置 |
| defaultStyle | 使用默认 Toast 样式 |
| getToastStyle | 获取 Toast 样式配置 |
| initStyle | 初始化 Toast 样式配置 |
| initToastFilter | 初始化 Toast 过滤器 |
| setView | 设置 Toast 显示的 View |
| show | 显示 Toast |
| cancel | 取消当前显示的 Toast |


#### 配置相关

```java
// 初始化 Toast - DevUtils 内部已经调用
DevToast.init(application); // 必须调用

// 初始化 Toast 样式 - 全局通用
// DevToast.initStyle(new IToast.Style() {}); // 可以实现 IToast.Style 接口, 参照 DefaultToastStyle

// 当 Toast 内容为 null 时, 显示的内容
DevToast.setNullText("text is null");

// 是否设置 Handler 显示 Toast - 默认 true, 支持子线程显示 Toast
DevToast.setIsHandler(true);

// 设置文本长度限制, 超过设置的位数则 为 LENGTH_LONG
DevToast.setTextLength(15);

// 支持自定义 View - 可不配置, 默认使用系统 Toast View
DevToast.setView(view);
DevToast.setView(viewId);

// 配置 Toast 过滤, 判断是否显示 Toast、以及内容处理
// DevToast.initToastFilter(new IToast.Filter() {});

// 恢复默认配置
DevToast.reset();
```


#### 使用方法
```java
// 显示 Toast
DevToast.show(view);
DevToast.show(R.string.app_name);
DevToast.show("Toast"); // initStyle - Toast

// 使用特殊样式 - 默认统一全局样式, style 则为 这个 Toast 单独为这个样式
DevToast.style(new TempStyle()).show("tempStyle - Toast");

// 获取 当前全局使用的样式
DevToast.getToastStyle();

// 获取默认样式
DevToast.defaultStyle();

// 关闭正在显示的 Toast
DevToast.cancel();
```


#### 自定义样式
```java
/**
  * 自定义实现样式
  */
private static class TempStyle implements IToast.Style {

    /**
     * Toast 的重心
     * @return
     */
    @Override
    public int getGravity() {
        return 0;
    }
    
    /**
     * X轴偏移
     * @return
     */
    @Override
    public int getXOffset() {
        return 0;
    }
    
    /**
     * Y轴偏移
     * @return
     */
    @Override
    public int getYOffset() {
        return 0;
    }
    
    /**
     * 获取水平边距
     * @return
     */
    @Override
    public int getHorizontalMargin() {
        return 0;
    }
    
    /**
     * 获取垂直边距
     * @return
     */
    @Override
    public int getVerticalMargin() {
        return 0;
    }
    
    /**
     * Toast Z轴坐标阴影
     * @return
     */
    @Override
    public int getZ() {
        return 0;
    }
    
    /**
     * 圆角大小
     * @return
     */
    @Override
    public float getCornerRadius() {
        return 5f;
    }
    
    /**
     * 背景着色颜色
     * @return
     */
    @Override
    public int getBackgroundTintColor() {
        return 0xB2000000;
    }
    
    /**
     * 背景图片
     * @return
     */
    @Override
    public Drawable getBackground() {
        return null;
    }
    
    // = TextView 相关 =
    
    /**
     * 文本颜色
     * @return
     */
    @Override
    public int getTextColor() {
        return Color.WHITE;
    }
    
    /**
     * 字体大小
     * @return
     */
    @Override
    public float getTextSize() {
        return 16f;
    }
    
    /**
     * 最大行数
     * @return
     */
    @Override
    public int getMaxLines() {
        return 0;
    }
    
    /**
     * Ellipsize 效果
     * @return
     */
    @Override
    public TextUtils.TruncateAt getEllipsize() {
        return null;
    }
    
    /**
     * 字体样式
     * @return
     */
    @Override
    public Typeface getTypeface() {
        // return Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        return null;
    }
    
    /**
     * TextView padding 边距 - new int[] { left, top, right, bottom }
     * @return
     */
    @Override
    public int[] getPadding() {
        return new int[] { 25, 10, 25, 10 };
    }
}
```