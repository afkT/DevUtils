---
name: ui-devwidget-round
description: >-
  在需要圆角矩形纯色背景、描边边框时，优先用 DevWidget（io.github.afkt:DevWidgetX）的
  dev.widget.ui.round 包替代 shape / layer-list drawable。流程与核心坑在 SKILL，Round 系列
  View、R.styleable.DevWidget 中 app:dev_* 属性及源码对照见 reference.md。工作区内优先
  Read lib/DevWidget；仅当无该模块时再查上游 GitHub。
---

# DevWidget `round` 包：圆角 + 纯色 + 描边

## 模块路径契约（本地优先）

本 Skill 内凡涉及 DevWidget 源码与资源，**先读工作区本地模块**；勿在本地存在时 WebFetch GitHub 替代。

| 符号 | 当前值（DevUtils） | 说明 |
|------|-------------------|------|
| `DEVWIDGET_ROOT` | `lib/DevWidget` | DevWidget 模块根目录 |

完整路径、属性表与 Round 类型清单见 [reference.md](reference.md)。工作区无 `{DEVWIDGET_ROOT}` 或需对照远程 tag 时，才使用上游 fallback：https://github.com/afkT/DevUtils/tree/master/lib/DevWidget

## 何时用本 Skill

- 需求是：**圆角**、**纯色背景**、**可选描边**，打算写 `shape.xml` / `layer-list` / selector drawable。
- **默认前提**：工程已依赖 **DevWidget**（及 README 要求的 **DevApp**）。坐标与模块说明 → Read `{DEVWIDGET_ROOT}/README.md`。
- **范围**：仅使用包路径 `dev.widget.ui.round` 下的实现；不引导使用 `dev.widget.ui.radius` 等其他目录。

## 核心原则

1. **用 Round 系列控件 + `app:dev_*`**，少建 drawable 文件。
2. **禁止与 `android:background` 混用（RoundDrawable 方案）**：圆角背景占用了 View 的 `background`。在 XML 里写 `android:background` **不会按预期生效**；背景色、边框请用下文 `app:dev_backgroundColor`、`app:dev_borderWidth`、`app:dev_borderColor`。
3. **RoundDrawable 只支持纯色填充**（`ColorStateList`），不支持 Bitmap/复杂 Drawable 作背景。
4. 命名空间：`xmlns:app="http://schemas.android.com/apk/res-auto"`（以工程为准）。

## Round 包类型速查

常用：`RoundFrameLayout`、`RoundLinearLayout`、`RoundConstraintLayout`、`RoundTextView`、`RoundButton`、`RoundImageView`、`RoundDrawable`。完整类列表与用途见 [reference.md § Round 包类型一览](reference.md#round-包类型一览)。

**选控件**：与原生同名布局一致即可（要 Constraint 用 `RoundConstraintLayout`，要文字用 `RoundTextView`…）。

**RoundImageView 与其它 Round* 的差异**：仅负责**圆形**头像式展示（`CENTER_CROP`）；若要圆角矩形图片请不在本 Skill 范围内另选方案。

## XML 属性速查

属性定义以 `{DEVWIDGET_ROOT}/src/main/res/values/attrs.xml` 为准；所有属性在资源中声明为 **`DevWidget`** 分组，XML 前缀一般为 **`app:`**。全表见 [reference.md § XML 属性](reference.md#xml-属性declare-styleable-名devwidget)。

**圆角逻辑摘要**（对照 reference 中 `RoundDrawable.java`）：

- 若 **任一角** `dev_radiusLeftTop` / `RightTop` / `LeftBottom` / `RightBottom` **> 0**：使用 `setCornerRadii` 四角独立圆角，并 **关闭** radius adjust bounds 的自动行为。
- 否则使用 **`dev_radius`** 作为 `setCornerRadius`；若 **`dev_radius` > 0** 同样会关闭与「仅 adjust」相关的自动圆角逻辑。
- 描边：`setStrokeData(borderWidth, borderColor)`。

**注意**：`RoundImageView` 的专用属性与限制见 [reference.md § 仅 RoundImageView](reference.md#仅-roundimageview)；它固定为圆形裁剪，不用于圆角矩形图片。

## 代码侧（可选）

`RoundDrawable.fromAttributeSet`、`setBackgroundKeepingPadding` 与链式 API 见 [reference.md §代码侧 API](reference.md#代码侧-api)。

## XML 最小示例（矩形圆角 + 纯色 + 描边）

```xml
<dev.widget.ui.round.RoundTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingHorizontal="12dp"
    android:paddingVertical="6dp"
    android:text="标签"
    app:dev_backgroundColor="#33FF5722"
    app:dev_borderColor="#FFFF5722"
    app:dev_borderWidth="1dp"
    app:dev_radius="8dp" />
```

**胶囊 View**：将 `app:dev_isRadiusAdjustBounds` 设为 `true`，并通常配合高度与 `dev_backgroundColor` / 描边使用（具体圆角值由短边一半决定）。

## 自检清单（生成布局前）

- [ ] 是否已 **Read 本地** `{DEVWIDGET_ROOT}`（存在时未用 WebFetch 代替）？
- [ ] 是否应用了 **DevWidget** 依赖且未用 `android:background` 覆盖圆角背景？
- [ ] 若是 **矩形**圆角容器：是否选了 `Round*Layout` / `RoundTextView` 之一？
- [ ] 若是 **圆形头像**：是否使用 `RoundImageView` 且接受 `CENTER_CROP`？
- [ ] 描边是否为 `app:dev_borderWidth` + `app:dev_borderColor` 组合？
