---
name: devwidget-round-ui
description: >-
  在 Android 布局或代码中处理圆角、纯色背景、描边时，先校验模块是否依赖
  io.github.afkt:DevWidgetX（或工程 dev_widget 坐标 / DevWidget 子模块）；有依赖则优先使用
  dev.widget.ui.round 包内控件与 RoundDrawable，减少手写 shape/layer-list XML。
  三层顺序：仅圆角+纯色+描边用 round；在此基础上还须裁子 View 且无 hl_* 类能力时用 radius（见
  devwidget-radius-ui）；再之外且命中 ShadowLayout 能力表、模块有该依赖时按 shadowlayout-ui，勿用 round 硬扛。
  用于编写或修改 layout、用户提到圆角卡片、边框描边、纯色圆角背景、替代 shape drawable 时。
disable-model-invocation: false
---

# DevWidgetX 圆角 / 背景 / 描边（优先 round 包）

## 前置条件（必做）

**仅当当前正在处理的 Android 模块已依赖 DevWidgetX（DevWidget 库）时**，才执行本 skill 的「优先使用 `dev.widget.ui.round`」逻辑。

1. 在该模块的 **`build.gradle` / `build.gradle.kts`** 以及工程里 **`apply from` 的 deps 脚本**（如 `file/deps/*.gradle`、`file/gradle/config_libs.gradle`）中检索是否出现任一形式：
   - Maven：`io.github.afkt:DevWidgetX`（版本号可变）
   - 工程统一坐标：如 `deps.dev.dev_widget`（定义见 `file/gradle/config.gradle` 等）
   - 本地子模块：`project(':DevWidget')`、`project(":lib:DevWidget")` 等与 **DevWidget / DevWidgetX** 模块相关的 `project(...)`
2. **未找到任何上述依赖**：**跳过**本 skill 后续全部规则；不要假设存在 `dev.widget.ui.round` 类；圆角/描边/背景按常规方式处理（如 `shape` drawable、`MaterialShapeDrawable`、`CardView` 等，以项目既有风格为准）。
3. **已找到依赖**：需要「圆角 + **纯色**背景 + 描边」或希望少写 drawable XML，且 **应先按下方「三层决策」** 判断是 **round** 还是应改读 **radius** / **ShadowLayout**；本 skill 的「优先 round」仅对应 **第一层**。

本 skill 可在 **任意仓库** 使用；**是否走 round 逻辑只以 Gradle 里是否存在 DevWidgetX / DevWidget 依赖为准**，不以「仓库里是否自带 `DevWidget/src/...` 源码树」为唯一依据。

## 与 devwidget-radius-ui、shadowlayout-ui 的三层决策顺序（防误判）

按 **固定顺序** 判断，**不要**因模块同时依赖 ShadowLayout 就把「裁剪」误判为必须用 ShadowLayout。

| 顺序 | 需求摘要 | 选型 | 技能文件 |
|------|----------|------|----------|
| **1** | 圆角 + 纯色背景 + 描边；子内容 **可以** 画出圆角外 | **`dev.widget.ui.round`**（本 skill） | 本文 |
| **2** | 在 **1** 的基础上 **必须** 把子 View / 视频等 **裁在圆角内**；**不要** `hl_shadow*`、线性渐变、pressed 双态、虚线、`hl_bindTextView` 等 | **`dev.widget.ui.radius`** | `.cursor/skills/devwidget-radius-ui/SKILL.md` |
| **3** | **2** 仍不够，或需要 ShadowLayout **`app:hl_*`** 能力（可配置软阴影、渐变、双态 selector/ripple、虚线、绑定 TextView 等），且模块 **已依赖** ShadowLayout | **`com.lihang.ShadowLayout`** | `.cursor/skills/shadowlayout-ui/SKILL.md` |

**仅「圆角内裁剪」不是 ShadowLayout 的充分理由**：有 DevWidget 时走 **第 2 层 radius**，勿仅为裁剪引入或选用 ShadowLayout。第 3 层须 **同时** 满足「命中 `hl_*` 能力」与「Gradle 有 ShadowLayout 依赖」。

## `dev.widget.ui.round` 清单（提高检索命中率）

以下路径以 **DevWidget 模块源码根** 为起点：`DevWidget/src/main/java/dev/widget/ui/round/`（磁盘上模块根目录前是否还有其它父路径以工程为准，读到 `src/main/java/dev/widget/ui/round/` 即可）。

| 文件 | 类型 | 典型用途 |
|------|------|----------|
| `RoundLinearLayout.java` | `dev.widget.ui.round.RoundLinearLayout` | 圆角线性容器 |
| `RoundConstraintLayout.java` | `dev.widget.ui.round.RoundConstraintLayout` | 圆角约束容器 |
| `RoundFrameLayout.java` | `dev.widget.ui.round.RoundFrameLayout` | 圆角帧布局容器 |
| `RoundRelativeLayout.java` | `dev.widget.ui.round.RoundRelativeLayout` | 圆角相对布局容器 |
| `RoundTextView.java` | `dev.widget.ui.round.RoundTextView` | 圆角文字 |
| `RoundButton.java` | `dev.widget.ui.round.RoundButton` | 圆角按钮 |
| `RoundImageView.java` | `dev.widget.ui.round.RoundImageView` | 圆角图片（属性与上表容器类不同，见源码注释） |
| `RoundDrawable.java` | `dev.widget.ui.round.RoundDrawable` | **非 View**：圆角 `GradientDrawable` 子类，可在代码里 `setBackground` / 组合使用 |

**选用建议**：容器类（Linear / Frame / Relative / Constraint）按布局结构选；纯文本用 `RoundTextView`；需 `Button` 语义用 `RoundButton`；图片用 `RoundImageView`；仅在代码里拼背景时用 `RoundDrawable`。

## ShadowLayout 命中条件（round **没有**、勿用 round 替代）

`dev.widget.ui.round` 的 `RoundDrawable` 背景系：**纯色填充 + 圆角 + 描边**；**无** `ShadowLayout` 那套 `app:hl_*` 能力。

**先排除 radius**：若需求 **只是**「在圆角 + 纯色 + 描边前提下裁子内容」，见上文 **三层决策第 2 层** 与 `.cursor/skills/devwidget-radius-ui/SKILL.md`，**不要**把「裁剪」直接记为下表 ShadowLayout 命中。

下列 **在已排除「仅用 radius 即可」之后**，任一条为 **true** 且当前模块 **已依赖** `com.github.lihangleo2:ShadowLayout` 时，应 **改读并遵循** `.cursor/skills/shadowlayout-ui/SKILL.md`，用 `com.lihang.ShadowLayout`（或项目既定封装）实现，**不要**再用 round（或 radius）硬扛 ShadowLayout 独占能力：

| 命中项 | round / radius 为何不够 | ShadowLayout 侧（详见 shadowlayout-ui skill） |
|--------|-------------------------|-----------------------------------------------|
| **裁剪 + ShadowLayout 独占能力** | **仅** 裁剪 → **radius**（见 devwidget-radius-ui）。**若** 还需与本表后列组合（如裁视频 **且** 要 `hl_shadow*`），则 ShadowLayout | 圆角轮廓内裁剪与 `hl_*` 背景/阴影等可同容器配置 |
| **可配置阴影** | **无** `hl_shadowColor` / `hl_shadowLimit` / 偏移、单边隐藏等（系统 `elevation` 不算本列） | `app:hl_shadow*` 软阴影 |
| **渐变背景** | `RoundDrawable` 路径 **非** 线性渐变 XML 替代 | `hl_startColor` / `hl_endColor` / `hl_angle` 等 |
| **按压 / 选中 / ripple** | 无容器级 `pressed`/`selected`/`ripple` 与 `_true` 双态背景、描边 | `hl_shapeMode`、`hl_layoutBackground` / `_true`、`hl_strokeColor` / `_true` |
| **虚线描边** | 无 dash 模式 | `hl_shapeMode="dashLine"` + `hl_stroke_dashWidth` / `hl_stroke_dashGap` |
| **图片背景 selector** | 容器背景不做「双态切换整张图」那套 | `hl_layoutBackground` + `hl_layoutBackground_true` 等 |
| **绑定 TextView** | 无随状态改绑定 View 文案/颜色 | `hl_bindTextView`、`hl_text` / `hl_text_true` 等 |

**再次强调（与上文「先排除 radius」一致）**：仅「子内容裁在圆角内」、无本表其它列能力 → **`dev.widget.ui.radius`**（`.cursor/skills/devwidget-radius-ui/SKILL.md`），**不要**因工程已依赖 ShadowLayout 就改用 ShadowLayout。

**不算命中 ShadowLayout 的特例**：只要 **`elevation` / `translationZ` / Material 默认阴影** 即可、**不要** `hl_shadow*` 那套参数时，仍可用 round + 系统阴影，**不必**为此引入 ShadowLayout（与 shadowlayout-ui skill 表述一致）。

**未依赖 DevWidget 但依赖了 ShadowLayout**：圆角/背景等以 shadowlayout-ui 为准，勿虚构 `dev.widget.ui.round` API。

## 自定义属性（`declare-styleable`：`DevWidget`）

属性定义集中在 DevWidget 模块 **`src/main/res/values/attrs.xml`** 的 `<declare-styleable name="DevWidget">`。下面按 **本 skill 涉及的 round 包** 分类摘录 **format + attrs 内注释/实现语义**，便于检索而无需先打开源码。

### 与 `RoundDrawable` 背景系共用（`RoundLinearLayout` / `RoundFrameLayout` / `RoundRelativeLayout` / `RoundConstraintLayout` / `RoundTextView` / `RoundButton`）

这些 View 通过 `RoundDrawable.fromAttributeSet(...)` 读取下列属性并设为 **View 的 background**（纯色圆角矩形 + 可选描边；**非**渐变）。

| 属性 | format | 作用说明 |
|------|--------|----------|
| `app:dev_backgroundColor` | color | 填充色（背景）；支持 `ColorStateList` 资源时在代码路径中会按状态着色（见 `RoundDrawable`）。 |
| `app:dev_borderColor` | color | 描边颜色。 |
| `app:dev_borderWidth` | dimension | 描边粗细。 |
| `app:dev_radius` | dimension | 统一圆角半径；与四角属性互斥逻辑见下。 |
| `app:dev_radiusLeftTop` | dimension | 左上圆角；任一角 **> 0** 时走四角数组，`dev_radius` 不再单独作为统一圆角（见 `fromAttributeSet`）。 |
| `app:dev_radiusRightTop` | dimension | 右上圆角。 |
| `app:dev_radiusLeftBottom` | dimension | 左下圆角。 |
| `app:dev_radiusRightBottom` | dimension | 右下圆角。 |
| `app:dev_isRadiusAdjustBounds` | boolean | 为 **true** 时，在 `onBoundsChange` 中把圆角设为 **min(宽, 高)/2**（「胶囊」形）；与显式 `dev_radius` / 四角半径组合时，实现里在设置了正半径后会关闭自动调整（见 `RoundDrawable`）。 |

**与 `attrs.xml` 同文件、但不属于 round 背景系的项**：`dev_clearRadius` 等标注给 **`RadiusLayout` / `Radius*`**（`dev.widget.ui.radius` 包），**round 包布局不会读取**；不要用 round 文档去套 radius 控件。

### `RoundImageView` 专用（圆角/圆形位图 + 边框）

与上表 **不共用**「背景填充圆角」那套 `dev_backgroundColor` / `dev_radius*`；该类从 `DevWidget` styleable 只读取：

| 属性 | format | 作用说明 |
|------|--------|----------|
| `app:dev_borderWidth` | dimension | 外圈描边宽度。 |
| `app:dev_borderColor` | color | 描边颜色。 |
| `app:dev_borderOverlay` | boolean | 边框是否叠在图片外侧（CircleImageView 语义）。 |
| `app:dev_circleBackgroundColor` | color | 圆形/圆角区域外的底色（透明位图区域等）。 |

### 代码里使用 `RoundDrawable`

`RoundDrawable` 支持与 XML 相同语义的一组属性（见类顶 KDoc）：`dev_backgroundColor`、`dev_borderColor`、`dev_borderWidth`、`dev_isRadiusAdjustBounds`、`dev_radius` 与四角 `dev_radius*`，通过 **`RoundDrawable.fromAttributeSet`** 或各类 setter 配置。

**与源码的关系**：若文档与某版本实现不一致，以 **`lib/DevWidget/.../attrs.xml`** 与 **`RoundDrawable.fromAttributeSet` / `RoundImageView.initAttrs`** 为准；类顶 `/** ... <pre> ...` 为第二参考。

## 注意事项（必读）

1. **`android:background` 冲突**：圆角与填充/描边通过 **View 的 background** 实现；在 XML 里写 `android:background` **会不生效或被覆盖**，应使用上述 `app:dev_*` 属性配置背景与边框（与源码「注意事项」一致）。
2. **不裁剪子 View / 内容（针对 RoundDrawable 背景系）**：`RoundLinearLayout` / `RoundFrameLayout` / `RoundRelativeLayout` / `RoundConstraintLayout` / `RoundTextView` / `RoundButton` 等通过 **背景 Drawable** 画圆角与描边，**不会因此自动裁切子 View**；子 View 仍可能画在圆角轮廓外。需要 **裁切子 View** 且仍走 DevWidget 选型时，用 **`dev.widget.ui.radius`**（见 `.cursor/skills/devwidget-radius-ui/SKILL.md`）；其它方案（`clipToOutline`、`CardView` 等）以项目规范为准。**`RoundImageView`** 走的是图片圆角/圆形绘制逻辑，**是否裁剪位图以该类顶部注释与实现为准**，不要与上列容器的「不裁子 View」混为一谈。
3. **`RoundImageView`** 与「纯 RoundDrawable 铺背景」的控件 **属性集合不同**，**不要**混用属性表；以本节 **「RoundImageView 专用」** 表格与类顶注释为准。

## 与本仓库的关系

- 开源库源码根：`DevWidget/`（即 **`DevWidget/src/...`** 所在模块）；发布 Maven 一般为 **`io.github.afkt:DevWidgetX`**（与模块根目录下 `project.properties` 中 artifactId 一致）。
- 在 **本仓库** 内维护 DevWidget 源码时，修改行为以 **`DevWidget/`** 下实现与 `attrs` 为准。
