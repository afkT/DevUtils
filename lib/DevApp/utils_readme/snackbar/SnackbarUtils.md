# Snackbar 工具类

#### 使用演示类 [SnackbarUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/snackbar/SnackbarUse.java) 介绍了配置参数及使用

#### 项目类结构

* Snackbar 工具类（[SnackbarUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/SnackbarUtils.java)）：Snackbar 二次封装工具类

* Snackbar Style 样式抽象类（SnackbarUtils.Style）：主要决定 Snackbar 显示效果样式, 可继承该抽象类, 重写需要的方法, 并通过 setStyle 设置

* Snackbar Style 样式构建类（SnackbarUtils.StyleBuilder）：该类继承 SnackbarUtils.Style, 并且增加 set 属性方法, 并且可通过 Style 创建 StyleBuilder 并引用其样式配置

#### 框架亮点

* 支持通过 view/window/fragment/activity 构建 Snackbar 且可通过链式调用

* 支持自动区分资源 stringId, 支持可变参数传参, 自动格式化显示文本内容

* 使用 WeakReference 防止内存溢出, 并且支持 Snackbar 释放处理

* 支持 addView, above, bellow, 在指定的 view 上/下 方位置显示, 以及显示区域自动计算处理

* 支持手动关闭 Snackbar, 以及 Snackbar 事件监听处理等

* 支持自定义样式, 可配置样式属性比较全面, 高度自由可配置

* 支持样式构建引用, 在统一的样式上构建使用, 并快捷修改全局样式

## API 文档

| 方法 | 注释 |
| :- | :- |
| with | 获取 SnackbarUtils 对象 |
| getStyle | 获取样式 |
| setStyle | 设置样式 |
| getSnackbar | 获取 Snackbar |
| getSnackbarView | 获取 Snackbar View |
| getTextView | 获取 Snackbar TextView(snackbar_text) |
| getActionButton | 获取 Snackbar Action Button(snackbar_action) |
| addView | 向 Snackbar 布局中添加 View (Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示) |
| setCallback | 设置 Snackbar 展示完成 及 隐藏完成 的监听 |
| setAction | 设置 Action 按钮文字内容及点击监听 |
| dismiss | 关闭 Snackbar |
| showShort | 显示 Short Snackbar |
| showLong | 显示 Long Snackbar |
| showIndefinite | 显示 Indefinite Snackbar (无限时, 一直显示) |
| setSnackbarStyle | 设置 Snackbar 样式配置 |
| getShadowMargin | 获取阴影边距 |
| setShadowMargin | 设置阴影边距 |
| isAutoCalc | 判断是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示) |
| setAutoCalc | 设置是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示) |
| above | 设置 Snackbar 显示在指定 View 的上方 |
| bellow | 设置 Snackbar 显示在指定 View 的下方 |


### 调用方法

#### 获取 SnackbarUtils

> SnackbarUtils.with(view/window/fragment/activity);

#### 显示 Snackbar

> SnackbarUtils.with(view).showShort/showLong/showIndefinite(R.string.xxx、String);

#### 关闭 Snackbar

> SnackbarUtils.with(view).dismiss(isSetNull); // 是否销毁 Snackbar

#### 设置 Action Button

> SnackbarUtils.with(view).setAction(clickListener, R.string.xxx、String);

#### 设置样式

> SnackbarUtils.with(view).setStyle(style);



### 使用方法
```java
// ==============================================
// = 只能通过以下四种方式 获取 SnackbarUtils 对象 =
// ==============================================

SnackbarUtils.with(view);

SnackbarUtils.with(window);

SnackbarUtils.with(fragment);

SnackbarUtils.with(activity);


// ===============
// = 获取相关方法 =
// ===============

// = 获取 View =

// 获取 Snackbar 底层 View
View snackbarView = SnackbarUtils.with(view).getSnackbarView();

// 获取 Snackbar TextView(snackbar_text) - 左侧 文本TextView
TextView textView = SnackbarUtils.with(view).getTextView();

// 获取 Snackbar Action Button(snackbar_action) - 右侧 Button
Button actionButton = SnackbarUtils.with(view).getActionButton();

// =

// 获取 Snackbar 对象
Snackbar snackbar = SnackbarUtils.with(view).getSnackbar();

// 获取 View 阴影边距大小 - View 自带阴影
int shadowMargin = SnackbarUtils.with(view).getShadowMargin();

// 获取 是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
boolean autoCalc = SnackbarUtils.with(view).isAutoCalc(); // 只有调用 above / bellow 该属性才有意义

// 获取 Snackbar 显示效果样式配置信息
SnackbarUtils.StyleBuilder styleBuilder = SnackbarUtils.with(view).getStyle();


// ===============
// = 设置相关方法 =
// ===============

// 设置 View 阴影边距大小
SnackbarUtils.with(view).setShadowMargin(2);

// 设置是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
SnackbarUtils.with(view).setAutoCalc(true); // 只有调用 above / bellow 该属性才有意义

// 设置 Snackbar 显示效果样式
SnackbarUtils snackbarUtils = SnackbarUtils.with(view).setStyle(style);

// = 快捷设置指定样式效果的 Snackbar =

// 设置 Snackbar 显示效果为 当前 SnackbarUtils 对象使用的样式
Snackbar snackbar1 = SnackbarUtils.with(view).setSnackbarStyle(snackbar);

// 设置 Snackbar 显示效果为 自定义样式效果
Snackbar snackbar2 = SnackbarUtils.with(view).setSnackbarStyle(snackbar, style);


// = 设置 Action Button 文案等 =

// 设置 Snackbar Action Button(snackbar_action) 文案
SnackbarUtils.with(view).setAction(R.string.app_name);

// 设置 Snackbar Action Button(snackbar_action) 文案 - 支持格式化字符串
SnackbarUtils.with(view).setAction(R.string.app_name, "1", 2);

// 设置 Snackbar Action Button(snackbar_action) 文案
SnackbarUtils.with(view).setAction("撤销");

// 设置 Snackbar Action Button(snackbar_action) 文案 - 支持格式化字符串
SnackbarUtils.with(view).setAction("撤销 %s", "3");

// 设置 Snackbar Action Button(snackbar_action) 文案以及点击事件
SnackbarUtils.with(view).setAction(clickListener, R.string.app_name);

// 设置 Snackbar Action Button(snackbar_action) 文案以及点击事件
SnackbarUtils.with(view).setAction(clickListener, "撤销");


// = 设置 事件相关 =

// 设置 Snackbar 展示完成 及 隐藏完成 的监听
SnackbarUtils.with(view).setCallback(new Snackbar.Callback() {
    @Override
    public void onShown(Snackbar sb) {
        super.onShown(sb);
        // Snackbar 显示
    }

    @Override
    public void onDismissed(Snackbar transientBottomBar, int event) {
        super.onDismissed(transientBottomBar, event);
        // Snackbar 关闭
    }
});

// ===========
// = 操作方法 =
// ===========

// = 关闭 =

// 关闭显示 Snackbar
SnackbarUtils.with(view).dismiss();

// 关闭显示 Snackbar, 但不销毁 Snackbar
SnackbarUtils.with(view).dismiss(false);

// = 显示 - 支持 String、R.string.xx 以及格式化字符串 =

// 显示 Short Snackbar
SnackbarUtils.with(view).showShort("已收藏该消息!");

// 显示 Long Snackbar
SnackbarUtils.with(view).showLong("已收藏该消息!");

// 显示 Indefinite Snackbar (无限时, 一直显示)
SnackbarUtils.with(view).showIndefinite("已收藏该消息!");

// = 显示区域 =

// 设置是否自动计算边距 (如: 显示在 View 下面, 但是下方距离不够, 自动设置为在 View 上方显示)
// setAutoCalc 只有调用 above / bellow 该属性才有意义

// 设置 Snackbar 显示在指定 View 的上方, 并且向上边距 20
SnackbarUtils.with(view).above(targetView, 20);

// 设置 Snackbar 显示在指定 View 的下方, 并且向下边距 5
SnackbarUtils.with(view).bellow(targetView, 5);

// 向 Snackbar 布局中添加 View (Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示)
SnackbarUtils.with(view).addView(newTextView, 0);

// 向 Snackbar 布局中添加 View (Google 不建议, 复杂的布局应该使用 DialogFragment 进行展示)
SnackbarUtils.with(view).addView(viewId, 1);

// = 结合使用 =

// 只有调用了 showXxx, 才会进行设置样式, 并且显示
SnackbarUtils.with(view)
        .addView(viewId, 0)
        .setStyle(new NightStyle())
        .setAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, "撤销")
        .bellow(targetView, 0)
        .setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
            }
        }).setAutoCalc(true)
        .showShort("已收藏该消息!");

// =

// 通过已有样式创建 StyleBuilder 并修改样式效果使用
SnackbarUtils.StyleBuilder styleBuilder1 = new SnackbarUtils.StyleBuilder(style);
styleBuilder1.setActionColor(Color.RED);
SnackbarUtils.with(view).setStyle(styleBuilder1).showShort("已收藏该消息!");

// 修改默认样式中的部分展示效果
SnackbarUtils snackbarUtils1 = SnackbarUtils.with(view);
SnackbarUtils.StyleBuilder styleBuilder2 = snackbarUtils1.getStyle();
styleBuilder2.setActionColor(Color.BLACK);
snackbarUtils1.setStyle(styleBuilder2);
```


#### 自定义样式
```java
/**
 * detail: 自定义样式 - 可参照下方实现方法, 进行配置
 * @author Ttt
 */
class NightStyle extends SnackbarUtils.Style {
    @Override
    public int getTextColor() {
        return Color.WHITE;
    }

    @Override
    public float getRootAlpha() {
        return 0.5f;
    }
}
```


```java
/**
 * detail: 样式相关
 * @author Ttt
 */
SnackbarUtils.Style style = new SnackbarUtils.Style() {

    // ============
    // = RootView =
    // ============

    /**
     * RootView 的重心
     * @return
     */
    public int getRootGravity() {
        return 0;
    }

    /**
     * RootView 背景圆角大小
     * @return
     */
    public float getRootCornerRadius() {
        return 0f;
    }

    /**
     * RootView 背景着色颜色
     * @return
     */
    public int getRootBackgroundTintColor() {
        return 0;
    }

    /**
     * RootView 背景图片
     * @return
     */
    public Drawable getRootBackground() {
        return null;
    }

    /**
     * RootView margin 边距 - new int[] { left, top, right, bottom }
     * @return
     */
    public int[] getRootMargin() {
        return null;
    }

    /**
     * RootView 透明度
     * @return
     */
    public float getRootAlpha() {
        return 1.0f;
    }

    // = snackbar_text TextView 相关 =

    /**
     * TextView 的重心
     * @return
     */
    public int getTextGravity() {
        return 0;
    }

    /**
     * TextView 文本颜色
     * @return
     */
    public int getTextColor() {
        return 0;
    }

    /**
     * TextView 字体大小
     * @return
     */
    public float getTextSize() {
        return 0f;
    }

    /**
     * TextView 最大行数
     * @return
     */
    public int getTextMaxLines() {
        return 0;
    }

    /**
     * TextView Ellipsize 效果
     * @return
     */
    public TextUtils.TruncateAt getTextEllipsize() {
        return null;
    }

    /**
     * TextView 字体样式
     * @return
     */
    public Typeface getTextTypeface() {
        return null;
    }

    /**
     * TextView padding 边距 - new int[] { left, top, right, bottom }
     * @return
     */
    public int[] getTextPadding() {
        return null;
    }

    // = snackbar_action Button 相关 =

    /**
     * Action Button 的重心
     * @return
     */
    public int getActionGravity() {
        return 0;
    }

    /**
     * Action Button 文本颜色
     * @return
     */
    public int getActionColor() {
        return 0;
    }

    /**
     * Action Button 字体大小
     * @return
     */
    public float getActionSize() {
        return 0f;
    }

    /**
     * Action Button padding 边距 - new int[] { left, top, right, bottom }
     * @return
     */
    public int[] getActionPadding() {
        return null;
    }

    /**
     * Action Button 背景圆角大小
     * @return
     */
    public float getActionCornerRadius() {
        return 0f;
    }

    /**
     * Action Button 背景着色颜色
     * @return
     */
    public int getActionBackgroundTintColor() {
        return 0;
    }

    /**
     * Action Button 背景图片
     * @return
     */
    public Drawable getActionBackground() {
        return null; 
    }
};
```