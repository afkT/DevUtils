---
name: shadowlayout-ui
description: >-
  在 Android 布局或代码中，当模块已依赖 com.github.lihangleo2:ShadowLayout（万能阴影布局）时，
  与 DevWidget 协同按 **round → radius → ShadowLayout** 三层判断：仅圆角+纯色+描边用 round；仅再多「裁子
  View」且无 hl_* 独占能力用 radius（见 devwidget-round-ui）；其余命中 hl_* 能力再用 ShadowLayout。
  勿把「仅裁剪」在有 DevWidget 时误判为 ShadowLayout。未依赖 ShadowLayout 则跳过本 skill。
disable-model-invocation: false
---

# ShadowLayout（万能阴影布局）与圆角 / 阴影 / 背景 / 描边选型

官方仓库：<https://github.com/lihangleo2/ShadowLayout>

`ShadowLayout` 继承 **`FrameLayout`**（包名一般为 **`com.lihang.ShadowLayout`**），可在 XML 或代码中作为容器，通过库自定义属性配置阴影、圆角、描边、纯色/渐变背景、按压态等，从而减少手写 `shape` / `layer-list` drawable。子 View 的摆放规则与 `FrameLayout` 一致。

## 自定义属性（`declare-styleable`：`ShadowLayout`）

以下说明摘自上游仓库 **`shadowLibrary/src/main/res/values/attrs.xml`** 中 XML 注释（与 [README 属性表](https://github.com/lihangleo2/ShadowLayout) 一致；README 中边框宽度行写作 `hl_strokeWith`，与 attrs 实际命名相同，**不是** `hl_strokeWidth`）。布局里前缀一般为 **`app:hl_*`**（`xmlns:app` 指向应用包名）。**本 skill 只列容器 `com.lihang.ShadowLayout` 用到的属性**；同库还有 `SmartLoadingView`、`ScrollViewIndicator`、`SwitchButton` 等独立 `declare-styleable`，需要时再查该文件。

### 阴影

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:hl_shadowHidden` | 为 **true** 时**隐藏**阴影；只要 shape/selector、不要阴影时用。 |
| `app:hl_shadowColor` | 阴影颜色。 |
| `app:hl_shadowLimit` | 阴影扩散范围（程度）。 |
| `app:hl_shadowSymmetry` | 控件区域是否相对阴影对称；默认对称；不对称时控件区域随阴影区域变化。 |
| `app:hl_shadowOffsetX` / `app:hl_shadowOffsetY` | 阴影 X/Y 偏移。 |
| `app:hl_shadowHiddenLeft` / `Right` / `Top` / `Bottom` | 隐藏对应边的阴影。 |

### 圆角（含阴影、shape、背景图、stroke 的圆角）

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:hl_cornerRadius` | 统一圆角尺寸。 |
| `app:hl_cornerRadius_leftTop` / `rightTop` / `leftBottom` / `rightBottom` | 单角圆角；**设置某角后会忽略** `hl_cornerRadius` 对该角逻辑（以库实现为准）。 |

### shape / selector / 边框 / 虚线

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:hl_shapeMode` | `pressed` / `selected` / `ripple` / `dashLine`：按压态、选中态、水波纹、**虚线模式**（可配合下方 stroke 与 dash 属性画虚线）。 |
| `app:hl_layoutBackground` | 「false / 默认」态背景：颜色、图片或 drawable。 |
| `app:hl_layoutBackground_true` | 「true」态背景；**须与** `hl_layoutBackground` **同用**，否则库会报错。 |
| `app:hl_strokeWith` | 描边宽度（attrs 内拼写即 `With`，勿改成 `Width`）。 |
| `app:hl_strokeColor` / `app:hl_strokeColor_true` | false/true 态描边色；`_true` 须与 `hl_strokeColor` 搭配。 |
| `app:hl_stroke_dashWidth` / `app:hl_stroke_dashGap` | 虚线实段长度、间隔。 |

### 渐变填充

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:hl_startColor` / `app:hl_centerColor` / `app:hl_endColor` | 渐变起、中、止色；中间色可选。 |
| `app:hl_angle` | 渐变角度，默认 0。**设渐变后**以渐变为主，`hl_layoutBackground` **无效**（attrs 注释）。 |

### 绑定 TextView（随状态改文案/颜色）

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:hl_bindTextView` | 要绑定的 TextView 的 id（reference）。 |
| `app:hl_text` / `app:hl_text_true` | false/true 态文案。 |
| `app:hl_textColor` / `app:hl_textColor_true` | false/true 态文字颜色。 |

### 可点击与不可点时的背景

| 属性 | 作用（attrs 注释摘要） |
|------|------------------------|
| `app:clickable` | 库自定义布尔：**非**系统 `android:clickable` 语义混用；避免 `setOnClickListener` 与系统 clickable 行为不一致问题。 |
| `app:hl_layoutBackground_clickFalse` | `clickable` 为 false 时展示的颜色或图片。 |

**版本提示**：本仓库 `config_libs.gradle` 中示例版本为 **3.4.x**；大版本升级请以依赖模块内 `attrs.xml` 与 [官方 README](https://github.com/lihangleo2/ShadowLayout) 为准。

## 前置条件（必做）

**仅当当前正在处理的 Android 模块已依赖 ShadowLayout 时**，才执行本 skill 中「使用 `com.lihang.ShadowLayout`」及后续选型逻辑。

1. 在该模块的 **`build.gradle` / `build.gradle.kts`** 以及工程里 **`apply from` 的 deps 脚本**（如 `file/deps/*.gradle`、`file/gradle/config_libs.gradle`）中检索是否出现任一形式：
   - Maven：`com.github.lihangleo2:ShadowLayout`（版本号可变）
   - 工程别名：如 `deps.lib.shadowLayout`、`shadowLayout` 等映射到上述坐标（以本仓库 `config_libs.gradle` 为准）
2. **未找到任何上述依赖**：**跳过**本 skill 后续全部规则；不要假设存在 `com.lihang.ShadowLayout` 或 `app:hl_*` 等属性；圆角/描边/渐变/阴影按项目其它约定处理（如手写 drawable、`MaterialShapeDrawable`、`elevation` / `translationZ` / `Outline` 等）。
3. **已找到依赖**：再进入下方「与 DevWidget round / radius 的选型」。

本 skill 可在 **任意仓库** 使用；**是否走 ShadowLayout 逻辑只以 Gradle 里是否存在该依赖为准**。

## 与 DevWidget round / radius 的选型（必读顺序）

在已确认 **存在 ShadowLayout 依赖** 的前提下，仍须 **先** 按 DevWidget 文档的 **`round` → `radius`** 两层判断，再决定是否需要 ShadowLayout，**避免**「工程里已有 ShadowLayout」就把 **仅裁剪** 误判为必须用 ShadowLayout。涉及 DevWidget 时须阅读 **`.cursor/skills/devwidget-round-ui/SKILL.md`**（**`round` ↔ `radius` 两层决策**、类清单、`Radius*` + `RoundDrawable` 组合方式）。其中 **不包含** ShadowLayout；何时必须 `hl_*` 以本文 §3 为准。

### 1. 何时优先用 DevWidget round

若 **仅** 需要圆角 + 纯色背景 + 描边（子内容 **可以** 画出圆角外也无妨），且模块 **已依赖 DevWidgetX / DevWidget**，则 **不要** 套 ShadowLayout；按 **devwidget-round-ui** 使用 `dev.widget.ui.round` 与 `RoundDrawable`。若只要 **系统** `elevation` / `translationZ`、**不要** `app:hl_shadow*`，仍属本层 + 系统阴影即可。

### 2. 何时优先用 DevWidget radius（夹在 round 与 ShadowLayout 之间）

若在第 **1** 条风格下 **还必须** 把子 View / 视频等 **裁在圆角轮廓内**，且 **不需要** 下文第 3 条中任一 **ShadowLayout 独占** 能力（`hl_shadow*` 软阴影、渐变、`hl_shapeMode` 按压/ripple/虚线、双态背景图、`hl_bindTextView` 等），且模块 **已依赖 DevWidget**，则 **优先** **`dev.widget.ui.radius`**（见 **devwidget-round-ui**），**不要**仅为裁剪选用 `com.lihang.ShadowLayout`。

**实现提示**：`Radius*` 与 round 的 XML 背景自动注入行为不同；既要裁剪又要与 `RoundDrawable` 相同的纯色底 + 描边时，见 **devwidget-round-ui** 中 **`Radius*` + `RoundDrawable`** 小节。

### 3. 何时优先用 ShadowLayout

若需求包含 **至少一项** ShadowLayout **`app:hl_*`** 独占能力，且模块已有 ShadowLayout 依赖，则 **优先** `com.lihang.ShadowLayout`（布局写全类名），**不要**用 round / radius 硬扛。典型包括：

- **可配置软阴影**（`hl_shadowColor`、`hl_shadowLimit`、偏移、单边隐藏等）；**不是**「只要系统 elevation」。
- **渐变背景**（`hl_startColor` / `hl_endColor` / `hl_angle` 等）。
- **按压 / 选中 / ripple / 虚线描边**（`hl_shapeMode` 等）及双态 `hl_layoutBackground` / `hl_strokeColor` 等。
- **图片背景 selector**、`hl_bindTextView` 等 round/radius **不具备** 的能力。
- **裁剪且与上列组合**（例如既要圆角裁切 **又要** `hl_shadow*`），或 **无 DevWidget**、无法使用 radius 时，须裁子内容则只能走 ShadowLayout（或项目其它方案）。

**「仅裁剪」不算本条**：仅裁在圆角内、无上述 `hl_*` 独占项、且有 DevWidget → **第 2 条 radius**，**不是**本条。

具体属性名与含义以本文档 **「自定义属性」** 小节及库内 `attrs` 为准。

### 4. 想用 DevWidget round / radius 但模块没有 DevWidget 依赖时

若按第 **1** 或 **2** 条本应走 **round / radius**，但 **当前模块未依赖 DevWidget**，则在 **已依赖 ShadowLayout** 的前提下 **改用 ShadowLayout**（或项目既定手写 drawable 等），不要虚构 `dev.widget.ui.round` / `radius` API。

## 决策小结（给 Agent 的快速表）

**判断顺序**：先能否用 **round** → 若要裁子内容且无 `hl_*` 独占、有 DevWidget 则用 **radius** → 否则再判断是否本条 ShadowLayout。

| 有 ShadowLayout 依赖？ | 需求（在已校验 DevWidget 的前提下） | 有 DevWidget 依赖？ | 行动 |
|------------------------|-------------------------------------|---------------------|------|
| 否 | — | — | 跳过本 skill；不用 ShadowLayout |
| 是 | **仅** 圆角 + 纯色 + 描边；子内容可不裁 | 是 | **devwidget-round-ui**（`round`）；不用 ShadowLayout |
| 是 | 同上 **且必须** 裁子内容；**无** `hl_shadow*` / 渐变 / 按压双态 / 虚线 / 绑定 TextView 等 | 是 | **devwidget-round-ui**（`radius`）；**不要**因有 ShadowLayout 依赖就选 ShadowLayout |
| 是 | 需要 **`hl_*` 独占能力**（含「裁剪 + 任一项独占」），或无 DevWidget 但必须裁内容 | — / 否 | **`com.lihang.ShadowLayout`**（见上文 §3） |
| 是 | 无裁剪、无渐变、无按压变色、**无** `hl_shadow*`；子内容可不裁 | 否 | ShadowLayout 兜底圆角/背景/描边 |

## 注意事项

1. **`FrameLayout` 语义**：多子 View 时默认重叠，需用 `layout_gravity` 或单层子容器（如再包 `LinearLayout`）组织结构。
2. **与 DevSimple / ViewTheme**：若布局还需统一基础 style，仍按 `.cursor/skills/devsimple-viewtheme-xml/SKILL.md` 在 **有 DevSimple 依赖** 时处理；与本 skill 正交。
3. **属性与版本**：不同 ShadowLayout 版本属性可能有增减；以依赖版本下的 `attrs` 与官方文档为准，勿硬编码过时的属性名。
