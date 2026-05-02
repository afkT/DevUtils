---
name: devwidget-round-ui
description: >-
  在 Android 布局或代码中处理圆角、纯色背景、描边时，先校验模块是否依赖
  io.github.afkt:DevWidgetX（或工程 dev_widget 坐标 / DevWidget 子模块）；有依赖则优先使用
  dev.widget.ui.round 包内控件与 RoundDrawable，减少手写 shape/layer-list XML。
  用于编写或修改 layout、用户提到圆角卡片、边框描边、纯色圆角背景、替代 shape drawable 时。
disable-model-invocation: false
---

# DevWidgetX 圆角 / 背景 / 描边（优先 round 包）

## 前置条件（必做）

**仅当当前正在处理的 Android 模块已依赖 DevWidgetX（DevWidget 库）时**，才执行本 skill 的「优先使用 `dev.widget.ui.round`」逻辑。

1. 在该模块的 **`build.gradle` / `build.gradle.kts`** 以及工程里 **`apply from` 的 deps 脚本**（如 `file/deps/*.gradle`）中检索是否出现任一形式：
   - Maven：`io.github.afkt:DevWidgetX`（版本号可变）
   - 工程统一坐标：如 `deps.dev.dev_widget`（定义见 `file/gradle/config.gradle` 等）
   - 本地子模块：`project(':DevWidget')`、`project(":lib:DevWidget")` 等与 **DevWidget / DevWidgetX** 模块相关的 `project(...)`
2. **未找到任何上述依赖**：**跳过**本 skill 后续全部规则；不要假设存在 `dev.widget.ui.round` 类；圆角/描边/背景按常规方式处理（如 `shape` drawable、`MaterialShapeDrawable`、`CardView` 等，以项目既有风格为准）。
3. **已找到依赖**：需要「圆角 + 背景色 + 描边」或希望少写 drawable XML 时，**优先**选用下面 **`lib/DevWidget/src/main/java/dev/widget/ui/round`** 中的类型（布局里写完整类名，见各文件顶部 KDoc）。

本 skill 可在 **任意仓库** 使用；**是否走 round 逻辑只以 Gradle 里是否存在 DevWidgetX / DevWidget 依赖为准**，不以「仓库里是否自带 `lib/DevWidget` 源码」为唯一依据。

## `dev.widget.ui.round` 清单（提高检索命中率）

以下路径均相对于仓库根目录：`lib/DevWidget/src/main/java/dev/widget/ui/round/`。

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

## XML 常用属性（与源码注释一致）

多数基于 `RoundDrawable` 的 **View** 在文件顶部注释中列出了 `app:` 属性，常见包括：

- `app:dev_backgroundColor`
- `app:dev_borderColor`
- `app:dev_borderWidth`
- `app:dev_isRadiusAdjustBounds`
- `app:dev_radius`
- `app:dev_radiusLeftTop` / `app:dev_radiusRightTop` / `app:dev_radiusLeftBottom` / `app:dev_radiusRightBottom`

`RoundImageView` 使用另一组属性（如 `app:dev_borderWidth`、`app:dev_borderColor`、`app:dev_borderOverlay`、`app:dev_circleBackgroundColor` 等），**以该文件顶部注释为准**。

**使用方式与边界条件**：编写或排查属性时，**直接打开对应类的文件最顶部注释**（`/** ... <pre> ...`），以库内说明为最高优先级。

## 注意事项（必读）

1. **`android:background` 冲突**：圆角与填充/描边通过 **View 的 background** 实现；在 XML 里写 `android:background` **会不生效或被覆盖**，应使用上述 `app:dev_*` 属性配置背景与边框（与源码「注意事项」一致）。
2. **不裁剪子 View / 内容（针对 RoundDrawable 背景系）**：`RoundLinearLayout` / `RoundFrameLayout` / `RoundRelativeLayout` / `RoundConstraintLayout` / `RoundTextView` / `RoundButton` 等通过 **背景 Drawable** 画圆角与描边，**不会因此自动裁切子 View**；子 View 仍可能画在圆角轮廓外。需要严格裁剪时请另行处理（如 `clipToOutline`、outline、`CardView` 等，以项目规范为准）。**`RoundImageView`** 走的是图片圆角/圆形绘制逻辑，**是否裁剪位图以该类顶部注释与实现为准**，不要与上列容器的「不裁子 View」混为一谈。
3. **`RoundImageView`** 与「纯 RoundDrawable 铺背景」的控件 **属性集合不同**，**不要**混用属性表；以该类顶部注释为准。

## 与本仓库的关系

- 开源库源码：`lib/DevWidget`；发布 Maven 一般为 **`io.github.afkt:DevWidgetX`**（与 `lib/DevWidget/project.properties` 中 artifactId 一致）。
- 在 **本仓库** 内维护 DevWidget 源码时，修改行为以 `lib/DevWidget` 下实现与 `attrs` 为准。
