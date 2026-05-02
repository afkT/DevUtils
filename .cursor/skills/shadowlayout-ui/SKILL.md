---
name: shadowlayout-ui
description: >-
  在 Android 布局或代码中，当模块已依赖 com.github.lihangleo2:ShadowLayout（万能阴影布局）时，
  按需求在「裁剪圆角内区域 / 渐变色 / 按压变色」与 DevWidget round 之间选型；未依赖 ShadowLayout
  则跳过本 skill。用于圆角容器、边框、纯色或渐变背景、减少手写 layer-list/shape XML、与
  DevWidget 圆角 skill 的协同决策。
disable-model-invocation: false
---

# ShadowLayout（万能阴影布局）与圆角 / 背景 / 描边选型

官方仓库：<https://github.com/lihangleo2/ShadowLayout>

`ShadowLayout` 继承 **`FrameLayout`**（包名一般为 **`com.lihang.ShadowLayout`**），可在 XML 或代码中作为容器，通过库自定义属性配置阴影、圆角、描边、纯色/渐变背景、按压态等，从而减少手写 `shape` / `layer-list` drawable。子 View 的摆放规则与 `FrameLayout` 一致。

## 前置条件（必做）

**仅当当前正在处理的 Android 模块已依赖 ShadowLayout 时**，才执行本 skill 中「使用 `com.lihang.ShadowLayout`」及后续选型逻辑。

1. 在该模块的 **`build.gradle` / `build.gradle.kts`** 以及工程里 **`apply from` 的 deps 脚本**（如 `file/deps/*.gradle`、`file/gradle/config_libs.gradle`）中检索是否出现任一形式：
   - Maven：`com.github.lihangleo2:ShadowLayout`（版本号可变）
   - 工程别名：如 `deps.lib.shadowLayout`、`shadowLayout` 等映射到上述坐标（以本仓库 `config_libs.gradle` 为准）
2. **未找到任何上述依赖**：**跳过**本 skill 后续全部规则；不要假设存在 `com.lihang.ShadowLayout` 或 `app:hl_*` 等属性；圆角/描边/渐变按项目其它约定处理（如手写 drawable、`MaterialShapeDrawable` 等）。
3. **已找到依赖**：再进入下方「与 DevWidget round 的选型」。

本 skill 可在 **任意仓库** 使用；**是否走 ShadowLayout 逻辑只以 Gradle 里是否存在该依赖为准**。

## 与 DevWidget round 的选型（必读顺序）

在已确认 **存在 ShadowLayout 依赖** 的前提下，按下面顺序判断；涉及 DevWidget 时 **必须先阅读并遵循** 项目技能：`.cursor/skills/devwidget-round-ui/SKILL.md`。

### 1. 何时优先用 ShadowLayout

若需求中包含 **至少一项**，且模块已有 ShadowLayout 依赖，则 **优先** 使用 `com.lihang.ShadowLayout`（在布局中写全类名），而不是仅用 DevWidget 的「纯 RoundDrawable 背景系」容器：

- **裁剪**：需要子 View / 内容严格限制在圆角轮廓内（圆角裁切、防内容画出圆角外等 ShadowLayout 能力覆盖的场景）。
- **渐变色**：背景为线性/角度渐变等，希望少写或不用渐变 drawable XML。
- **点击按压变色**：需要按下/抬起等状态切换背景或描边，希望由控件属性统一处理而非手写 selector drawable。

具体属性名以库内 `attrs` 与 [ShadowLayout README](https://github.com/lihangleo2/ShadowLayout) 为准；本仓库示例中可见 `app:hl_startColor`、`app:hl_endColor`、`app:hl_angle` 等 **`hl_*`** 前缀属性（与版本一致即可）。

### 2. 何时优先用 DevWidget round

若 **不需要** 上列三项（无强裁剪需求、无渐变、无按压态变色），仅需要圆角 + 纯色背景 + 描边等，且模块 **已依赖 DevWidgetX / DevWidget**，则 **不要** 为省事套一层 ShadowLayout；应 **优先** 按 `.cursor/skills/devwidget-round-ui/SKILL.md` 使用 `dev.widget.ui.round` 下控件与 `RoundDrawable`。

### 3. 想用 DevWidget round 但模块没有 DevWidget 依赖时

若按上一条本应走 **DevWidget round**，但校验后发现 **当前模块未依赖 DevWidget**（无法使用 `dev.widget.ui.round`），则在 **已依赖 ShadowLayout** 的前提下，**改用 ShadowLayout** 实现圆角、背景色、描边、渐变等（以库支持的能力为上限），而不是虚构 DevWidget API。

## 决策小结（给 Agent 的快速表）

| 有 ShadowLayout 依赖？ | 需要裁剪 / 渐变 / 按压变色？ | 有 DevWidget 依赖？ | 行动 |
|------------------------|-----------------------------|---------------------|------|
| 否 | — | — | 跳过本 skill；不用 ShadowLayout |
| 是 | 是 | — | 用 `com.lihang.ShadowLayout` |
| 是 | 否 | 是 | 用 devwidget-round-ui skill |
| 是 | 否 | 否 | 用 ShadowLayout 兜底 |

## 注意事项

1. **`FrameLayout` 语义**：多子 View 时默认重叠，需用 `layout_gravity` 或单层子容器（如再包 `LinearLayout`）组织结构。
2. **与 DevSimple / ViewTheme**：若布局还需统一基础 style，仍按 `.cursor/skills/devsimple-viewtheme-xml/SKILL.md` 在 **有 DevSimple 依赖** 时处理；与本 skill 正交。
3. **属性与版本**：不同 ShadowLayout 版本属性可能有增减；以依赖版本下的 `attrs` 与官方文档为准，勿硬编码过时的属性名。
