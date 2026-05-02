---
name: devwidget-radius-ui
description: >-
  在 Android 布局或代码中，当模块已依赖 io.github.afkt:DevWidgetX（或工程 DevWidget 子模块），且需求与
  `.cursor/skills/devwidget-round-ui/SKILL.md` 中「优先 round 包」的场景一致，但相对 round 仅多出「将子 View /
  自身绘制裁在圆角轮廓内」、且不需要 ShadowLayout 的阴影/渐变/selector/虚线等能力时，优先使用
  `dev.widget.ui.radius` 包内控件，勿为裁剪单独引入 ShadowLayout。用于圆角容器内视频/图片/子布局需被裁切、
  替代「仅为裁剪而上 ShadowLayout」。
disable-model-invocation: false
---

# DevWidgetX 圆角裁剪（优先 radius 包）

## 与 `devwidget-round-ui` 的关系

先读 **`.cursor/skills/devwidget-round-ui/SKILL.md`**：其中的 **DevWidget 依赖校验**、**三层决策顺序（round → radius → ShadowLayout）**、**ShadowLayout 命中条件**、**`RoundImageView` 与背景系属性不可混用** 等规则 **仍然成立**。需要 **`app:hl_*`** 独占能力时改读 **`.cursor/skills/shadowlayout-ui/SKILL.md`**，勿用 radius 替代 ShadowLayout。

本 skill 只解决一件事：在 **已满足**「该用 DevWidget、且不是 ShadowLayout 那套复杂背景/阴影需求」的前提下，若相对 `dev.widget.ui.round` **仅多出**「**裁剪**子内容到圆角区域内」（源码在 `draw()` 里对 `Canvas` `clipPath`），则 **改用 `dev.widget.ui.radius` 包内对应 View**，**不要**仅为裁剪去依赖 `com.github.lihangleo2:ShadowLayout`。

## 何时用 radius、何时仍用 round / ShadowLayout

| 情况 | 选型 |
|------|------|
| 圆角 + 纯色底 + 描边，**子 View 可以画出圆角外** | `dev.widget.ui.round`（见 devwidget-round-ui） |
| 同上，但 **必须**把子 View / 视频等 **裁在圆角内**；**不要** `hl_shadow*`、线性渐变背景、双态 selector、虚线描边、ShadowLayout 绑定文案等 | **`dev.widget.ui.radius`**（本 skill） |
| 命中 devwidget-round-ui 里 **ShadowLayout 命中表**任一项（阴影、渐变、pressed 双态等）且模块已有 ShadowLayout | `.cursor/skills/shadowlayout-ui/SKILL.md` |

**不算 ShadowLayout 命中**：只要系统 `elevation` / Material 阴影即可 → round 或 radius + 系统阴影均可，不必为阴影上 ShadowLayout。

## `dev.widget.ui.radius` 清单

路径以 DevWidget 模块源码为起点：`src/main/java/dev/widget/ui/radius/`。

| 文件 | 类型 | 与 round 包对应关系 |
|------|------|---------------------|
| `RadiusLinearLayout.java` | `dev.widget.ui.radius.RadiusLinearLayout` | 对应 `RoundLinearLayout` |
| `RadiusConstraintLayout.java` | `dev.widget.ui.radius.RadiusConstraintLayout` | 对应 `RoundConstraintLayout` |
| `RadiusRelativeLayout.java` | `dev.widget.ui.radius.RadiusRelativeLayout` | 对应 `RoundRelativeLayout` |
| `RadiusLayout.java` | `dev.widget.ui.radius.RadiusLayout`（`FrameLayout`） | 对应 `RoundFrameLayout` |
| `RadiusTextView.java` | `dev.widget.ui.radius.RadiusTextView` | 对应 `RoundTextView` |
| `RadiusButton.java` | `dev.widget.ui.radius.RadiusButton` | 对应 `RoundButton` |
| `RadiusImageView.java` | `dev.widget.ui.radius.RadiusImageView` | 与 `RoundImageView` 不同 API，见 devwidget-round-ui 中 RoundImageView 说明；裁剪位图以 radius 实现为准 |
| `IRadiusMethod.java` | 接口 | 代码里改圆角、`dev_clearRadius` 等 |

**选用建议**：与 round 包相同，按布局结构选容器；需要 **FrameLayout** 语义时用 `RadiusLayout`。

## 属性与 round 的差异（实现层面必读）

`attrs.xml` 里 `declare-styleable name="DevWidget"` 中，**圆角半径** 与 **`app:dev_clearRadius`** 由 `RadiusAttrs` 读取，`radius` 容器 **会**按圆角路径裁剪。

**`radius` 容器不会在 `initAttrs` 里调用 `RoundDrawable.fromAttributeSet`**，因此 **`app:dev_backgroundColor`、`app:dev_borderColor`、`app:dev_borderWidth`、`app:dev_isRadiusAdjustBounds` 不会**像 `RoundLinearLayout` 等一样 **自动**变成圆角背景。若既要 **裁剪** 又要 **与 RoundDrawable 相同的纯色圆角底+描边**：

- 在 **代码** 中对该 `Radius*` 容器使用 `RoundDrawable.fromAttributeSet` + `RoundDrawable.setBackgroundKeepingPadding`（与 `RoundLinearLayout` 同源 API），或
- 采用项目内已有封装，

以 **`lib/DevWidget/.../attrs.xml`**、`RoundDrawable`、`Radius*` 源码为准。

## 注意事项

1. **`android:background` 与 RoundDrawable**：若在 radius 容器上自行设置 `RoundDrawable`，须遵守 round 包关于背景与 padding 的约定（见 `RoundLinearLayout` 类顶注释）。
2. **仍以 Gradle 为准**：未依赖 DevWidgetX / DevWidget 时，不要引用 `dev.widget.ui.radius`。
3. **与 ShadowLayout skill 分工**：仅「圆角内裁剪」**不是** ShadowLayout 的充分理由；只有同时需要 shadowlayout-ui skill 列出的能力时才引入 ShadowLayout。

## 与本仓库的关系

与 devwidget-round-ui 相同：开源实现一般在 **`lib/DevWidget/`**（或工程内等价模块路径）；发布坐标常见为 **`io.github.afkt:DevWidgetX`**。
