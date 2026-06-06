---
name: ui-dialogx-dialog
description: >-
  需要对话框、弹窗、菜单、加载等待、轻提示（Toast 替代）、应用内通知、底部弹窗、
  上下文菜单、全屏/自定义弹层或操作引导时，默认使用 DialogX
  （com.github.kongzue.DialogX，本工程已依赖）而非系统 AlertDialog/Toast/PopupMenu。
  SKILL 给组件选型、避坑事实与最小片段；组件静态工厂全表、链式 setter 全表、
  全局配置 DialogX.* 全表、TextInfo/InputInfo/ItemDivider、枚举与主题包坐标见 reference.md。
  在用户要求弹对话框、做加载框、提示框、底部菜单、确认/输入弹窗、引导蒙层或替换原生 Dialog 时使用。
---

# DialogX：对话框 / 菜单 / 提示统一方案

本工程**默认弹窗框架为 DialogX**（[GitHub](https://github.com/kongzue/DialogX)）。需要任何对话框、菜单、加载、提示、通知时，优先用 DialogX，不要用系统 `AlertDialog` / `Toast` / `PopupMenu` / `ProgressDialog`。

## 依赖与初始化

- 坐标已集中声明：`DEPS_ROOT=file/gradle/config_libs.gradle` 中 `dialogX`，由 `file/deps/deps_project.gradle` 以 `api deps_lib.common_widget.dialogX` 启用。**勿重复写 GAV**；改版本走 [gradle-central-deps](../gradle-central-deps/SKILL.md)。
- 当前仅引入**核心模块**（默认 Material 主题）。需 iOS/MIUI/Kongzue/MaterialYou 主题时，须额外引入主题包（坐标见 [reference.md § 主题包](reference.md#主题包与全局风格)）。
- `Application.onCreate` 一般无需手动 `DialogX.init(context)`（库会自动初始化）；全局风格/亮暗/默认文字样式在此处设置（见 [reference.md § 全局配置](reference.md#全局配置-dialogx)）。

## 组件选型（先选对组件）

| 需求 | 用哪个 | 阻断式 |
|------|--------|:------:|
| 标题+内容+1/2/3 按钮的提醒、确认 | `MessageDialog` | 是 |
| 上面的基础上要输入框 | `InputDialog` | 是 |
| 纵向列表式菜单（带标题/按钮） | `MessageMenu` | 是 |
| 加载等待（环形/进度），可无缝切成功/失败 | `WaitDialog` / `TipDialog`（单例同一个） | 是 |
| 底部弹出对话框 / 底部菜单（可单选多选、图标） | `BottomDialog` / `BottomMenu` | 是 |
| 类 Toast 的轻提示（可带图标/按钮，不阻断） | `PopTip` | 否 |
| 应用内通知条（顶部下拉） | `PopNotification` | 否 |
| 锚定某 View 的上下文菜单 | `PopMenu` | 否（半阻断）|
| 从底部弹出、定制自由度高于 BottomDialog | `FullScreenDialog` | 是 |
| 完全自定义布局+弹出方位（中/上/下/左/右） | `CustomDialog`（`ALIGN`） | 可选 |
| 蒙层操作引导、舞台光高亮某 View | `GuideDialog`（`STAGE_LIGHT_TYPE`） | 是 |

静态工厂方法**全量重载**见 [reference.md § 组件工厂](reference.md#组件静态工厂速查)。

## 核心事实（避坑）

1. **一行启动、无需 context**：`MessageDialog.show("标题","内容","确定");` 任意线程调用会自动切到 UI 线程。
2. **`show()` vs `build()`**：要在显示前改主题/亮暗/复杂配置，用 `build()` 链式配置后再 `.show()`；`build(IOSStyle.style())` 或 `.setStyle(...)` 单独指定样式。
3. **WaitDialog 与 TipDialog 是单例**：`WaitDialog.show("加载中…")` 后直接 `TipDialog.show("成功",WaitDialog.TYPE.SUCCESS,1500)` 会无缝过渡，**不要**先 dismiss 再 show。
4. **颜色一律传 `@ColorInt` int 值**，不要传 `R.color.x` 资源 id（会无效）：用 `Color.parseColor("#...")` 或 `getColor(R.color.x)`。
5. **按钮/菜单点击回调返回值**：`onClick(...)` `return false` 自动关闭对话框，`return true` **拦截关闭**（保持显示）。
6. **文字样式用 `TextInfo`**，输入框用 `InputInfo`，菜单分隔线用 `ItemDivider`（字段见 [reference.md § 数据类型](reference.md#数据类型-textinfo--inputinfo--itemdivider)）。
7. **自定义布局**用 `new OnBindView<T>(R.layout.xxx){ onBind(dialog, v){...} }`；ViewBinding 用 `OnBindingView<T, Binding>`。
8. **圆角/裁剪**：`.setRadius(px)`（BottomDialog/BottomMenu 只影响左上右上）；或全局 `DialogX.default*BackgroundRadius`。
9. **菜单图标**：`.setIconResIds(...)` 直传，某项无图标传 `0`；线性/面性图标随亮暗染色用 `.setAutoTintIconInLightOrDarkMode(true)` 或 `OnIconChangeCallBack(true)`；网络异步图标用 `MenuIconAdapter`。
10. **BottomMenu 单选/多选**：`setSingleSelection()`/`setSelection(i)` 单选、`setMultiSelection()`/`setSelection(int[])` 多选，配合 `OnMenuItemSelectListener` 实时拿选中项。

## 最小代码片段（速查）

**基础确认框**

```java
MessageDialog.show("提示", "确定删除？", "删除", "取消")
        .setOkButton((dialog, v) -> { /* ... */ return false; });
```

**加载 → 结果（单例无缝）**

```java
WaitDialog.show("提交中…");
// 完成后：
TipDialog.show("提交成功", WaitDialog.TYPE.SUCCESS, 1500);
```

**底部单选菜单（带图标）**

```java
BottomMenu.show("请选择", new String[]{"拍照", "相册"})
        .setIconResIds(R.mipmap.ic_camera, R.mipmap.ic_album)
        .setOnMenuItemClickListener((dialog, text, index) -> { /* ... */ return false; });
```

**轻提示（Toast 替代）**

```java
PopTip.show("已复制").iconSuccess().autoDismiss(2000);
```

**自定义布局对话框**

```java
CustomDialog.show(new OnBindView<CustomDialog>(R.layout.dialog_custom) {
    @Override public void onBind(CustomDialog dialog, View v) {
        // v.findViewById(...)
    }
}, CustomDialog.ALIGN.CENTER);
```

## 自检清单（写弹窗前）

- [ ] 先按「组件选型」表选对组件，未用系统 `AlertDialog/Toast/PopupMenu`？
- [ ] 需要显示前改主题/配置 → 用 `build()...show()` 而非直接 `show()`？
- [ ] 加载到结果是否复用 `WaitDialog`/`TipDialog` 单例无缝切换？
- [ ] 颜色都是 `@ColorInt` 值而非 `R.color` 资源 id？
- [ ] 点击回调 `return` 值符合「拦截/关闭」预期？
- [ ] 用到的主题（iOS/MIUI 等）对应主题包已在集中配置引入？
- [ ] 方法重载/全局开关有疑义时已查 [reference.md](reference.md)，必要时对照与依赖版本同 tag 的上游源码？
