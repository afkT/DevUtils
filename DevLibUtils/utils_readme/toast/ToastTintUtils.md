# Toast 美化工具类

#### 使用演示类 [ToastTintUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/com/dev/utils/toast/ToastTintUse.java) 介绍了配置参数及使用

> 1. 支持子线程弹出 Toast, 可通过开关配置
> 2. 内部解决 Android 7.1.1 崩溃问题
> 3. 但无处理 部分ROM 如魅族、小米、三星等关闭应用通知，无法显示 Toast 问题

#### 项目类结构

* Toast 工具类（[ToastTintUtils](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/src/main/java/dev/utils/app/toast/ToastTintUtils.java)）：Toast 美化工具类。


#### 框架亮点

* 支持不分主次线程都可以弹出 Toast, 并可通过开关控制

* 支持自动区分资源 stringId , 支持可变参数传参, 自动格式化 Toast 内容

* 支持自动适配, 根据不同 Android 版本, 生成不同的 Toast 对象

* 支持自定义样式：Toast（Gravity、背景、圆角、边距等）、TextView（样式、颜色、大小、Ellipsize等）

* 默认几种样式 Toast (normal、info、warning、success、error), 并且支持统一变更设置样式、以及自定义样式

* 原始 Toast 基础上封装美化, 并且可通过 Style 控制 Toast 效果, 实现全局配置 Style 效果 

## API 文档

| 方法 | 注释 |
| :- | :- |
| reset | 重置默认参数 |
| setToastFilter | 设置 Toast 过滤器 |
| setIsHandler | 设置是否使用 Handler 显示 Toast |
| setNullText | 设置 Text 为 null 的文本 |
| setUseConfig | 判断是否使用配置 |
| setGravity | 设置 Toast 显示在屏幕上的位置。 |
| setMargin | 设置边距 |
| getDefaultStyle | 获取默认样式 |
| getNormalStyle | 获取 Normal 样式 |
| getInfoStyle | 获取 Info 样式 |
| getWarningStyle | 获取 Warning 样式 |
| getErrorStyle | 获取 Error 样式 |
| getSuccessStyle | 获取 Success 样式 |
| setNormalStyle | 设置 Normal 样式 |
| setInfoStyle | 设置 Info 样式 |
| setWarningStyle | 设置 Warning 样式 |
| setErrorStyle | 设置 Error 样式 |
| setSuccessStyle | 设置 Success 样式 |
| getInfoDrawable | 获取 Info 样式 icon |
| getWarningDrawable | 获取 Warning 样式 icon |
| getErrorDrawable | 获取 Error 样式 icon |
| getSuccessDrawable | 获取 Success 样式 icon |
| normal | normal 样式 Toast |
| info | info 样式 Toast |
| warning | warning 样式 Toast |
| error | error 样式 Toast |
| success | success 样式 Toast |
| custom | custom Toast |


#### 配置相关

```java
// 获取默认样式
ToastTintUtils.getDefaultStyle();

// 获取 Normal 样式
ToastTintUtils.getNormalStyle();
// 设置 Normal 样式
ToastTintUtils.setNormalStyle(style);

// 获取 Error 样式
ToastTintUtils.getErrorStyle();
// 设置 Error 样式
ToastTintUtils.setErrorStyle(style);
// 获取 Error 样式 小图标
ToastTintUtils.getErrorDrawable();

// 获取 Warning 样式
ToastTintUtils.getWarningStyle();
// 设置 Warning 样式
ToastTintUtils.setWarningStyle(style);
// 获取 Warning 样式 小图标
ToastTintUtils.getWarningDrawable();

// 获取 Success 样式
ToastTintUtils.getSuccessStyle();
// 设置 Success 样式
ToastTintUtils.setSuccessStyle(style);
// 获取 Success 样式 小图标
ToastTintUtils.getSuccessDrawable();

// 获取 Info 样式
ToastTintUtils.getInfoStyle();
// 设置 Info 样式
ToastTintUtils.setInfoStyle(style);
// 获取 Info 样式 小图标
ToastTintUtils.getInfoDrawable();

// 是否使用配置 - 如 Gravity、HorizontalMargin、VerticalMargin
ToastTintUtils.setUseConfig(true);

// 设置 Gravity
ToastTintUtils.setGravity(Gravity.BOTTOM, 0, 0);

// 当 Toast 内容为 null 时, 显示的内容
ToastTintUtils.setNullText("text is null");

// 是否设置 Handler 显示 Toast - 默认 true, 支持子线程显示 Toast
ToastTintUtils.setIsHandler(true);

// 设置 HorizontalMargin、VerticalMargin 边距
ToastTintUtils.setMargin(0f, 0f);

// 配置 Toast 过滤, 判断是否显示 Toast、以及内容处理
// ToastTintUtils.setToastFilter(new ToastTintUtils.Filter() {});

// 恢复默认配置
ToastTintUtils.reset();
```


#### 使用方法
```java
// 显示 Success 样式 Toast
ToastTintUtils.success("Success Style Toast");

// 显示 Error 样式 Toast
ToastTintUtils.error("Error Style Toast");

// 显示 Info 样式 Toast
ToastTintUtils.info("Info Style Toast");

// 显示 Normal 样式 Toast
ToastTintUtils.normal("Normal Style Toast");

// 显示 Warning 样式 Toast
ToastTintUtils.warning("Warning Style Toast");

// 显示 Custom 样式 Toast
ToastTintUtils.custom(style, "Custom Style Toast");

// 显示 Custom 样式 Toast, 自定义小图标
ToastTintUtils.custom(new TempStyle(), "Custom Style Toast", iconDrawable);
```

# 预览

***Success 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/success.png)

***Error 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/error.png)

***Info 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/info.png)

***Normal 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/normal.png)

***Warning 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/warning.png)

***Custom 样式 Toast***

![](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/toast/toast_tint/custom.png)


#### 自定义样式
```java
/**
  * 自定义实现样式
  * {@link ToastTintUtils.SuccessStyle}
  * {@link ToastTintUtils.ErrorStyle}
  * {@link ToastTintUtils.InfoStyle}
  * {@link ToastTintUtils.WarningStyle}
  * {@link ToastTintUtils.NormalStyle}
  * {@link ToastTintUtils.DefaultStyle}
  */
private static class TempStyle implements ToastTintUtils.Style {

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
     * 背景着色颜色
     * @return
     */
    @Override
    public int getBackgroundTintColor() {
        return 0;
    }
    
    /**
     * 背景图片
     * @return
     */
    @Override
    public Drawable getBackground() {
        return null;
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
     * 获取图标着色颜色
     * @return
     */
    @Override
    public int getTintIconColor() {
        return Color.WHITE;
    }
    
    /**
     * 是否渲染图标 - getTintIconColor() 着色渲染
     * @return
     */
    @Override
    public boolean isTintIcon() {
        return false;
    }
}
```