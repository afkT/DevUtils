---
name: devwidget-round-ui
description: >-
  Android 布局或代码：圆角、纯色底、描边、少写 shape/layer-list。先查模块是否已依赖 Maven 坐标
  **io.github.afkt:DevWidgetX**（或工程 deps 中映射到该坐标的别名，如 deps.dev.dev_widget）。有依赖则 **先 round 后
  radius**：子内容可画出圆角外 → `dev.widget.ui.round` + `RoundDrawable`；必须把子 View/视频/位图裁在圆角内
  → `dev.widget.ui.radius`（`draw` 内 `clipPath`）。`Radius*` 不会在 init 里自动注入 RoundDrawable 背景，既要裁剪又要同色底+描边时在代码里
  `RoundDrawable.fromAttributeSet` + `setBackgroundKeepingPadding`。`RoundImageView` 与容器背景系属性表分离。无依赖则整份跳过。实现以依赖库内
  `declare-styleable` 与对应类为准。选型与 ShadowLayout 的衔接见 **.cursor/skills/shadowlayout-ui/SKILL.md**（其中 round/radius 以本文为准）。
disable-model-invocation: false
---

# DevWidgetX 圆角 UI（`round` / `radius`）

发布坐标：**`io.github.afkt:DevWidgetX`**。与 **ShadowLayout** 同层约定：**是否使用本文 API 只以 Gradle 里是否存在该 Maven 依赖（或等价别名）为准**，不根据本仓库是否含源码树判断。

## 前置条件（必做）

**仅当当前正在处理的 Android 模块已依赖 DevWidgetX 时**，才执行本 skill 中「使用 `dev.widget.ui.round` / `dev.widget.ui.radius`」及后续规则。

1. 在该模块的 **`build.gradle` / `build.gradle.kts`** 以及工程里 **`apply from` 的 deps 脚本**（如 `file/deps/*.gradle`、`file/gradle/config.gradle`、`file/gradle/config_libs.gradle`）中检索是否出现任一形式：
   - Maven：**`io.github.afkt:DevWidgetX`**（版本号可变）
   - 工程别名：如 **`deps.dev.dev_widget`** 等映射到上述坐标（以本仓库及目标工程的 deps 配置为准）
2. **未找到任何上述依赖**：**跳过**本 skill 后续全部规则；不要引用 `dev.widget.ui.round` / `radius`；圆角与背景按项目常规（`shape`、`MaterialShapeDrawable`、`CardView`、`clipToOutline` 等）。
3. **已找到依赖**：按下文 **`round` → `radius`** 选型；属性与行为以**已解析到 classpath 的 DevWidgetX 版本**内资源与类实现为准。

本 skill 可在 **任意仓库** 使用；**是否走 DevWidgetX 逻辑只以 Gradle 里是否存在该依赖为准**。

## `round` 与 `radius`（固定顺序）

| 顺序 | 需求 | 包 / 类 |
|------|------|---------|
| **1** | 圆角 + **纯色**填充 + 可选描边；子内容 **允许** 画在圆角轮廓 **外** | **`dev.widget.ui.round`** |
| **2** | 在 **1** 的前提下 **必须** 把子 View / Surface / 视频等 **裁进圆角**；且仍只要 DevWidgetX 这套「纯色底 + 圆角 + 描边」能力范围 | **`dev.widget.ui.radius`** |

**不要**把「需要裁剪」误判成必须换别的容器库：有 DevWidgetX 时 **2** 用 **`Radius*`**。仅需系统 **`elevation` / `translationZ`** 时，`Round*` 或 `Radius*` 均可叠加，不必为此换方案。

## `dev.widget.ui.round`（包 `dev.widget.ui.round`）

| 文件 | 类型 | 用途 |
|------|------|------|
| `RoundLinearLayout` | `dev.widget.ui.round.RoundLinearLayout` | 圆角线性容器 |
| `RoundConstraintLayout` | `dev.widget.ui.round.RoundConstraintLayout` | 圆角约束容器 |
| `RoundFrameLayout` | `dev.widget.ui.round.RoundFrameLayout` | 圆角帧布局 |
| `RoundRelativeLayout` | `dev.widget.ui.round.RoundRelativeLayout` | 圆角相对布局 |
| `RoundTextView` | `dev.widget.ui.round.RoundTextView` | 圆角文本 |
| `RoundButton` | `dev.widget.ui.round.RoundButton` | 圆角按钮 |
| `RoundImageView` | `dev.widget.ui.round.RoundImageView` | 圆角位图（**属性表与下述容器不同**，见下节） |
| `RoundDrawable` | `dev.widget.ui.round.RoundDrawable` | 非 View；代码里 `setBackground` / 组合 |

容器按布局结构选；纯代码拼背景用 **`RoundDrawable`**。

### `declare-styleable name="DevWidget"` — 与 RoundDrawable 背景系共用

`RoundLinearLayout` / `RoundFrameLayout` / `RoundRelativeLayout` / `RoundConstraintLayout` / `RoundTextView` / `RoundButton` 通过 **`RoundDrawable.fromAttributeSet`** 读下列属性并设为 **View 的 background**（纯色圆角矩形 + 可选描边，**非** XML 线性渐变那类）。

| 属性 | 说明 |
|------|------|
| `app:dev_backgroundColor` | 填充色 |
| `app:dev_borderColor` / `app:dev_borderWidth` | 描边 |
| `app:dev_radius` | 统一圆角；与四角互斥逻辑见 `fromAttributeSet` |
| `app:dev_radiusLeftTop` / `RightTop` / `LeftBottom` / `RightBottom` | 分角半径 |
| `app:dev_isRadiusAdjustBounds` | `true` 时趋向胶囊形（`min(w,h)/2`）；与显式半径组合时以实现为准 |

**`RoundImageView` 专用**（不要与上表混用）：`dev_borderWidth`、`dev_borderColor`、`dev_borderOverlay`、`dev_circleBackgroundColor` 等 — 以 **`RoundImageView.initAttrs`** 与类顶注释为准。

### 行为要点（round）

1. **`android:background`**：圆角底与描边走 **background**；XML 里写 `android:background` 易与 `dev_*` **冲突或不生效**，优先用 **`app:dev_*`**。
2. **子 View 不自动裁剪**：上述带 RoundDrawable 背景的容器 **只画背景**，**不** `clip` 子 View；要裁子内容 → 用 **`dev.widget.ui.radius`**（见下节）。**`RoundImageView`** 的位图圆角/裁剪语义 **以该类源码为准**，不要与容器混谈。
3. **`dev_clearRadius` 等**：在库 **`declare-styleable`** 中可能标给 **`Radius*`**；**round 容器不读** — 勿把 round 文档套到 `Radius*` 上。

## `dev.widget.ui.radius`（包 `dev.widget.ui.radius`）

在 **已确认依赖 DevWidgetX** 且需求相对 round **仅多出「按圆角路径裁剪子绘制」**（典型为 `draw` 里对 `Canvas` **`clipPath`**）时使用。

| 文件 | 类型 | 与 round 对应 |
|------|------|----------------|
| `RadiusLinearLayout` | `dev.widget.ui.radius.RadiusLinearLayout` | `RoundLinearLayout` |
| `RadiusConstraintLayout` | `dev.widget.ui.radius.RadiusConstraintLayout` | `RoundConstraintLayout` |
| `RadiusRelativeLayout` | `dev.widget.ui.radius.RadiusRelativeLayout` | `RoundRelativeLayout` |
| `RadiusLayout` | `dev.widget.ui.radius.RadiusLayout`（`FrameLayout`） | `RoundFrameLayout` |
| `RadiusTextView` | `dev.widget.ui.radius.RadiusTextView` | `RoundTextView` |
| `RadiusButton` | `dev.widget.ui.radius.RadiusButton` | `RoundButton` |
| `RadiusImageView` | `dev.widget.ui.radius.RadiusImageView` | 与 `RoundImageView` **API 不同**；裁剪以 radius 实现为准 |
| `IRadiusMethod` | 接口 | 改圆角、`dev_clearRadius` 等 |

需要 **FrameLayout** 语义时用 **`RadiusLayout`**。

### `Radius*` 与 round 的实现差异（易错）

`RadiusAttrs` 读 **圆角** 与 **`app:dev_clearRadius`**，容器 **会**按路径裁剪。

**`Radius*` 的 `initAttrs` 不会调用 `RoundDrawable.fromAttributeSet`**，因此 **`app:dev_backgroundColor`、`dev_borderColor`、`dev_borderWidth`、`dev_isRadiusAdjustBounds` 不会**像 `RoundLinearLayout` 那样 **自动**变成圆角背景。

既要 **裁剪** 又要 **与 RoundDrawable 相同的纯色圆角底 + 描边**时：在代码中对 `Radius*` 使用 **`RoundDrawable.fromAttributeSet`** + **`RoundDrawable.setBackgroundKeepingPadding`**（与 `RoundLinearLayout` 同源 API），或项目内已有封装。以**依赖库内** `declare-styleable`、**`RoundDrawable`** 与 **`Radius*`** 实现为准。

若在 `Radius*` 上手动设 **`RoundDrawable` 作 background**，须遵守 **`RoundLinearLayout` 类顶注释**里关于 background 与 padding 的约定。

## 与 ShadowLayout skill 的关系

三层 **round → radius → ShadowLayout** 的完整顺序与 **`hl_*` 独占能力**说明在 **`.cursor/skills/shadowlayout-ui/SKILL.md`**。本文只覆盖 **round / radius**；是否再用 ShadowLayout 以该 skill 为准，且 **DevWidgetX 是否生效**始终回到本文 **「前置条件」** 的 Gradle 校验。
