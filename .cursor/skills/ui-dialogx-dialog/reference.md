# DialogX 速查全表（reference）

配套 [SKILL.md](SKILL.md)。流程与避坑在 SKILL，此处放**全量表**：组件工厂、链式 setter、全局配置、数据类型、枚举、主题包。版本以工程依赖 `com.github.kongzue.DialogX:DialogX:0.0.51.beta3` 为准；有疑义时对照同 tag 上游源码（见文末维护步骤）。

包名：`com.kongzue.dialogx`（组件在 `…dialogx.dialogs`，回调接口在 `…dialogx.interfaces`，工具类在 `…dialogx.util`）。

---

## 组件静态工厂速查

所有组件都同时提供 `show(...)`（构建并立即显示）与 `build(...)`（仅构建，需手动 `.show()`）。`build(DialogXStyle)` / `build(OnBindView)` 多数组件支持。下表列出**常用重载签名**。

### MessageDialog / InputDialog / MessageMenu（阻断式）

| 方法 | 说明 |
|------|------|
| `MessageDialog.show(title, message)` | 标题 + 内容 |
| `MessageDialog.show(title, message, okText)` | + 确定按钮 |
| `MessageDialog.show(title, message, okText, cancelText)` | + 取消 |
| `MessageDialog.show(title, message, okText, cancelText, otherText)` | 三按钮 |
| `InputDialog.show(title, message, okText[, cancelText[, otherText]])` | 同上 + 输入框 |
| `InputDialog.show(title, message, okText, cancelText, inputText)` | 预填输入内容 |
| `MessageMenu` | 与 MessageDialog 同 API，额外纵向菜单（`setMenuList`/`setOnMenuItemClickListener`） |

> 所有 `title/message/okText…` 均有 `int resId` 重载（如 `show(int titleResId, int messageResId)`）。

### WaitDialog / TipDialog（**单例**，互相无缝切换）

| 方法 | 说明 |
|------|------|
| `WaitDialog.show(message)` | 环形等待 |
| `WaitDialog.show(message, float progress)` | 带进度（0f~1f） |
| `WaitDialog.show(Activity, message[, progress])` | 指定 Activity |
| `TipDialog.show(message, TYPE)` | 结果提示（成功/警告/错误） |
| `TipDialog.show(message, TYPE, long duration)` | 自动消失时长(ms) |
| `TipDialog.show(Activity, message, TYPE[, duration])` | 指定 Activity |

`TYPE` = `WaitDialog.TYPE`（`SUCCESS / WARNING / ERROR`；`NONE / PROGRESSING` 已废弃）。

### BottomDialog / BottomMenu（底部）

| 方法 | 说明 |
|------|------|
| `BottomDialog.show(title, message)` | 底部对话框 |
| `BottomDialog.show([title,] OnBindView)` | 自定义布局 |
| `BottomMenu.show(String... menus)` / `show(CharSequence[])` / `show(List<CharSequence>)` | 菜单 |
| `BottomMenu.show(title, menuList[, OnMenuItemClickListener])` | 带标题 |
| `BottomMenu.show(title, message, menuList[, listener])` | 标题 + 副标题 |

### Pop 系列（非阻断 / 锚定）

| 方法 | 说明 |
|------|------|
| `PopTip.show(message)` / `tip(message)` | 轻提示；`tip(...)` 为 toast 风格语义重载 |
| `PopTip.show(message, buttonText)` / `show(iconResId, message[, buttonText])` | 带按钮/图标 |
| `PopNotification.show(title[, message])` | 通知条 |
| `PopNotification.show(iconResId, title, message[, buttonText])` | 带图标/按钮 |
| `PopMenu.show(CharSequence... menus)` / `show(List)` | 居中上下文菜单 |
| `PopMenu.show(View baseView, menus[, OnBindView])` | **锚定** baseView 弹出 |

### FullScreenDialog / CustomDialog / GuideDialog（高自由度）

| 方法 | 说明 |
|------|------|
| `FullScreenDialog.show(OnBindView)` | 全屏自定义布局 |
| `CustomDialog.show(OnBindView)` | 居中自定义 |
| `CustomDialog.show(OnBindView, ALIGN)` | 指定弹出方位（见枚举） |
| `GuideDialog.show(tipImageResId / Bitmap / Drawable[, ALIGN])` | 引导图 |
| `GuideDialog.show(View baseView, STAGE_LIGHT_TYPE[, tipImage, alignBaseViewGravity])` | 锚定 View + 舞台光 |

---

## 通用链式 setter 全表

下列方法多数组件通用（返回自身，可链式）。`T` 表示对应对话框类型。

### 文本与按钮

| 方法 | 说明 |
|------|------|
| `setTitle(CharSequence/resId)` / `setMessage(...)` | 标题 / 内容 |
| `setOkButton(text[, OnDialogButtonClickListener])` | 确定按钮（文本/回调可单设） |
| `setCancelButton(...)` / `setOtherButton(...)` | 取消 / 第三按钮；传 `null` 隐藏 |
| `setButton(...)`（PopTip/PopNotification） | 单一操作按钮 |
| `setInputText(...)` / `getInputText()`（InputDialog） | 输入内容；按钮回调用 `OnInputDialogButtonClickListener` 拿到 `inputStr` |
| `getButtonSelectResult()` | 返回 `BUTTON_SELECT_RESULT` 枚举，事后查用户点了哪个 |

### 样式

| 方法 | 说明 |
|------|------|
| `setStyle(DialogXStyle)` | 单独指定主题（如 `IOSStyle.style()`） |
| `setTheme(DialogX.THEME)` | 单独指定亮/暗/自动 |
| `setTitleTextInfo(TextInfo)` / `setMessageTextInfo(...)` | 标题/内容文字样式 |
| `setOkButtonTextInfo` / `setCancelTextInfo` / `setOtherButtonTextInfo` | 各按钮文字样式 |
| `setMenuTextInfo(TextInfo)` / `setMenuTitleTextInfo(...)`（菜单类） | 菜单文字样式 |
| `setBackgroundColor(@ColorInt int)` | 强制对话框背景色（int 值，非 resId） |
| `setMaskColor(@ColorInt int)` | 背景遮罩色 |
| `setRadius(float px)` | 圆角（BottomDialog/Menu 只影响上方两角） |

### 行为

| 方法 | 说明 |
|------|------|
| `setCancelable(boolean)` | 点外部/返回键是否可关 |
| `setAllowInterceptTouch(boolean)`（Bottom，仅 Material） | 是否允许下滑关闭（false 同时隐藏滑动提示条） |
| `setCustomView(OnBindView)` / `getCustomView()` | 插入/获取自定义布局 |
| `setOnBackPressedListener(OnBackPressedListener)` | 返回键回调 |
| `setOnBackgroundMaskClickListener(...)` | 点遮罩回调（`return true` 拦截） |
| `refreshUI()` / `dismiss()` / `hide()` / `hideWithExitAnim()` | 刷新 / 关闭 / 隐藏（可再 `show()` 恢复） |
| `isShow()` / `bringToFront()` / `setThisOrderIndex(int)` | 状态 / 层级 |

### 菜单专用（BottomMenu / MessageMenu / PopMenu）

| 方法 | 说明 |
|------|------|
| `setMenuList(list)` / `setMenus(String...)` | 设置菜单项 |
| `setIconResIds(int...)` | 菜单图标（无图标项传 `0`） |
| `setOnMenuItemClickListener(OnMenuItemClickListener / OnMenuItemSelectListener)` | 点击/选中回调 |
| `setOnIconChangeCallBack(OnIconChangeCallBack / MenuIconAdapter)` | 动态/异步图标 |
| `setAutoTintIconInLightOrDarkMode(boolean)` | 图标随亮暗染色 |
| `setSelection(int)` / `setSingleSelection()` | 单选模式 |
| `setSelection(int[])` / `setMultiSelection()` | 多选模式 |
| `getSelectionIndex()` / `getSelectionIndexArray()` / `getSelectText*()` | 取选中项 |
| `setItemDivider(ItemDivider)` | 菜单分隔线 |

### 生命周期

| 方法 | 说明 |
|------|------|
| `setDialogLifecycleCallback(DialogLifecycleCallback<T>)` | `onShow` / `onDismiss` |
| `onShow(DialogXRunnable<T>)` / `onDismiss(DialogXRunnable<T>)` | 简化写法 |
| `BottomDialogSlideEventLifecycleCallback`（可滑动 Bottom） | `onSlideClose` / `onSlideTouchEvent` |
| `getLifecycle()` | 获取 androidx `Lifecycle` |

### PopTip / PopNotification 自动消失

| 方法 | 说明 |
|------|------|
| `autoDismiss(long delay)` | 指定 ms 后消失 |
| `showShort()` / `showLong()` | 短/长时显示 |
| `noAutoDismiss()` | 常驻 |
| `iconSuccess()` / `iconWarning()` / `iconError()`（PopTip） | 预设状态图标 |

---

## 全局配置 `DialogX.*`

在 `Application.onCreate` 设置（静态字段，下次显示生效）。

| 字段 | 类型 | 默认 | 说明 |
|------|------|------|------|
| `globalStyle` | `DialogXStyle` | `MaterialStyle.style()` | 全局主题风格 |
| `globalTheme` | `THEME` | `LIGHT` | 全局亮/暗/自动 |
| `tipTheme` | `THEME` | null | 单独指定 Tip/Wait 亮暗 |
| `implIMPLMode` | `IMPL_MODE` | `VIEW` | 实现模式（VIEW/WINDOW/DIALOG_FRAGMENT/FLOATING_ACTIVITY） |
| `DEBUGMODE` | `boolean` | true | 日志开关 |
| `autoShowInputKeyboard` | `boolean` | true | InputDialog 自动弹键盘 |
| `onlyOnePopTip` | `boolean` | false | 同时仅一个 PopTip |
| `onlyOnePopNotification` | `boolean` | true | 同时仅一个 PopNotification |
| `cancelable` | `boolean` | true | 默认可点外部关闭（不影响 Tip/Wait） |
| `cancelableTipDialog` | `boolean` | false | Wait/Tip 是否可关 |
| `useHaptic` | `boolean` | true | 振动反馈 |
| `autoRunOnUIThread` | `boolean` | true | 自动切 UI 线程 |
| `enableImmersiveMode` | `boolean` | true | 沉浸式适配 |
| `cancelButtonText` | `String` | null | BottomDialog 默认取消文本 |
| `backgroundColor` / `tipBackgroundColor` | `Integer` (ColorInt) | null | 默认背景色 |
| `tipProgressColor` | `Integer` (ColorInt) | null | 覆盖 Tip/Wait 进度色 |
| `dialogMaxWidth/Height` `dialogMinWidth/Height` | `int` (px) | 0 | 尺寸约束 |
| `default*BackgroundRadius` | `int` (px) | -1 | 各组件默认圆角（MessageDialog/BottomDialog/FullScreenDialog/WaitAndTip/PopMenu/PopTip/PopNotification） |
| `bottomDialogNavbarColor` | `int` (ColorInt) | `TRANSPARENT` | BottomDialog 导航栏背景 |
| `touchSlideTriggerThreshold` | `int` (px) | dip(35) | 下滑关闭阈值 |
| `globalHoverWindow` | `boolean` | false | 全局悬浮窗（需 `SYSTEM_ALERT_WINDOW`） |
| `enterAnimDuration` / `exitAnimDuration` | `long` | -1 | 进/出动画时长 |
| 文字样式：`titleTextInfo` `messageTextInfo` `buttonTextInfo` `okButtonTextInfo` `tipTextInfo` `menuTitleInfo` `menuTextInfo` `popTextInfo` | `TextInfo` | null | 各部位默认文字样式 |
| `inputInfo` | `InputInfo` | null | 默认输入框样式 |
| 默认文本：`defaultMessageDialogTitleText` `defaultWaitDialogWaitingText` `defaultTipDialogSuccess/Error/WarningText` | `CharSequence` | null | 默认占位文本 |
| `dialogLifeCycleListener` | `DialogLifecycleCallback<BaseDialog>` | null | 全局生命周期 |
| `autoGC` | `boolean` | false | 销毁时自动回收 |

`DialogX.init(Context)`：手动初始化入口（一般无需调用）。

---

## 数据类型 TextInfo / InputInfo / ItemDivider

### `TextInfo`（文字样式）

| 字段 | 类型 | 默认 | 说明 |
|------|------|------|------|
| `fontSize` | int | -1 | 字号，-1 用默认 |
| `fontSizeUnit` | `FONT_SIZE_UNIT` | `DP` | DP/PX/SP |
| `gravity` | int | -1 | 对齐，如 `Gravity.CENTER` |
| `fontColor` | int (ColorInt) | 1 | 颜色，1 用默认；传 int 值非 resId |
| `bold` | boolean | false | 粗体 |
| `maxLines` | int | -1 | 最大行数 |
| `showEllipsis` | boolean | false | 省略号 |
| `typeface` | `Typeface` | null | 字体 |

### `InputInfo`（输入框）

| 字段 | 类型 | 默认 | 说明 |
|------|------|------|------|
| `MAX_LENGTH` | int | -1 | 最大长度 |
| `inputType` | int | 0 | `android.text.InputType` |
| `textInfo` | `TextInfo` | null | 文字样式 |
| `multipleLines` | boolean | false | 多行 |
| `selectAllText` | boolean | false | 默认全选 |
| `cursorColor` / `bottomLineColor` | Integer (ColorInt) | null | 光标/底线颜色；`setThemeColor(int)` 一次设两者 |
| `inputFilters` | `InputFilter[]` | null | `addInputFilter` / `removeInputFilter` |

### `ItemDivider`（菜单分隔线）

| 字段 | 类型 | 默认 | 说明 |
|------|------|------|------|
| `left` / `right` | dp | 0 | 左右边距 |
| `width` | px | 1 | 粗细 |
| `color` | ColorInt | `#DFE1E5,#3A3A3A` | 颜色，可按 `light(boolean)` 分亮暗 |

---

## 枚举速查

| 枚举 | 取值 |
|------|------|
| `DialogX.THEME` | `LIGHT` / `DARK` / `AUTO` |
| `DialogX.IMPL_MODE` | `VIEW` / `WINDOW` / `DIALOG_FRAGMENT` / `FLOATING_ACTIVITY` |
| `WaitDialog.TYPE`（即 TipDialog TYPE） | `SUCCESS` / `WARNING` / `ERROR`（`NONE` / `PROGRESSING` 已废弃） |
| `TextInfo.FONT_SIZE_UNIT` | `DP` / `PX` / `SP` |
| `BUTTON_SELECT_RESULT` | `NONE` / `BUTTON_OK` / `BUTTON_CANCEL` / `BUTTON_OTHER` |
| `SELECT_MODE` | `SINGLE` / `MULTIPLE`（菜单单/多选） |
| `CustomDialog.ALIGN` | `CENTER` / `TOP[_CENTER/_LEFT/_RIGHT]` / `BOTTOM[_…]` / `LEFT[_CENTER/_TOP/_BOTTOM]` / `RIGHT[_…]` |
| `GuideDialog.STAGE_LIGHT_TYPE` | `RECTANGLE` / `SQUARE_OUTSIDE` / `SQUARE_INSIDE` / `CIRCLE_OUTSIDE` / `CIRCLE_INSIDE` |

---

## 回调接口（`com.kongzue.dialogx.interfaces`）

| 接口 | 用途 |
|------|------|
| `OnDialogButtonClickListener<T>` | 通用按钮 `onClick(dialog, v)`，`return true` 拦截关闭 |
| `OnInputDialogButtonClickListener<T>` | InputDialog 按钮，回调含 `inputStr` |
| `OnMenuButtonClickListener<T>` | 菜单按钮 |
| `OnMenuItemClickListener<T>` | 菜单项点击 `onClick(dialog, text, index)` |
| `OnMenuItemSelectListener<T>` | 单选 `onOneItemSelect` / 多选 `onMultiItemSelect` |
| `OnIconChangeCallBack<T>` / `MenuIconAdapter<T>` | 菜单图标（同步/异步） |
| `OnBindView<T>` / `OnBindingView<T,B>` | 自定义布局（findViewById / ViewBinding） |
| `OnBackPressedListener` / `OnBackgroundMaskClickListener<T>` | 返回键 / 遮罩点击 |
| `DialogLifecycleCallback<T>` / `BottomDialogSlideEventLifecycleCallback<T>` | 生命周期 / 滑动事件 |
| `DialogXRunnable<T>` | `onShow/onDismiss` 简化回调 |
| `DialogXStyle` | 自定义主题接口 |

---

## 主题包与全局风格

核心模块仅含 `MaterialStyle`。其他主题需**额外引入主题包**，再 `DialogX.globalStyle = new XxxStyle()`（或 `XxxStyle.style()`）。引入走 [gradle-central-deps](../gradle-central-deps/SKILL.md)。

| 主题 | MavenCentral 坐标（group `com.kongzue.dialogx.style`） | JitPack 坐标（group `com.github.kongzue.DialogX`） | 风格类 |
|------|------|------|------|
| iOS | `DialogXIOSStyle` | `DialogXIOSStyle` | `IOSStyle` |
| Kongzue | `DialogXKongzueStyle` | `DialogXKongzueStyle` | `KongzueStyle` |
| MIUI | `DialogXMIUIStyle` | `DialogXMIUIStyle` | `MIUIStyle` |
| Material You | `DialogXMaterialYouStyle` | `DialogXMaterialYou` | `MaterialYouStyle` |

> 注意 MaterialYou 两源 artifact 名不同（Central 带 `Style` 后缀，JitPack 不带）。

`build()` + `setStyle(IOSStyle.style())` 可对**单个**对话框临时换主题，不影响全局。

---

## 内部 styleable（一般不直接用）

`DialogX/src/main/res/values/attrs.xml` 定义的是**库内部自定义 View** 的 styleable，业务侧通常不在布局里直接引用，列此仅供改源码/排查时参考：

| styleable | 关键 attr | 用途 |
|-----------|-----------|------|
| `DialogXMaxLayout` | `maxLayoutWidth/Height` `minLayoutWidth/Height` `lockWidth` `interceptTouch` `dialogXSafetyMode(flags: none/top/left/bottom/right)` | 对话框容器尺寸/安全区 |
| `RealtimeBlurView` | `realtimeBlurRadius` `realtimeDownsampleFactor` `realtimeOverlayColor` `realtimeRadius` `dialogxDarkMode` `dialogxOverlayColorNoAlpha` | 实时模糊背景 |
| `ProgressView` | `progressStrokeWidth` `progressStrokeColor` | 等待环 |
| `DialogXBaseRelativeLayout` | `baseFocusable` `autoSafeArea` `interceptBack` | 根布局 |

> 业务侧定制外观请优先用上面的链式 `setXxx` 与 `DialogX.*` 全局配置，而非改这些 styleable。

---

## 维护步骤（同步上游）

```bash
# 取与依赖同 tag 的源码（version 见 file/gradle/config_libs.gradle 的 dialogX）
v=0.0.51.beta3
api="https://api.github.com/repos/kongzue/DialogX/contents"
# 全局配置：
curl -s "$api/DialogX/src/main/java/com/kongzue/dialogx/DialogX.java?ref=$v"
# 某组件静态工厂/setter（以 MessageDialog 为例）：
curl -s "$api/DialogX/src/main/java/com/kongzue/dialogx/dialogs/MessageDialog.java?ref=$v"
```

升级版本或属性存疑时，对照上述源码核对本表，并按需更新；同时按 [cursor-catalog-sync.mdc](../../rules/cursor-catalog-sync.mdc) 维护 `.cursor/README.md`。
