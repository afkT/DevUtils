---
name: devwidget-round-ui
description: >-
  在需要圆角矩形纯色背景、描边边框时，优先用 DevWidget（io.github.afkt:DevWidgetX）的
  dev.widget.ui.round 包替代 shape / layer-list drawable。列出 Round 系列 View、
  R.styleable.DevWidget 中相关 app:dev_* 属性及行为。用于布局 XML、圆角卡片、背景、
  头像圆圈、减少 drawable 文件。
---

# DevWidget `round` 包：圆角 + 纯色 + 描边

## 何时用本 Skill

- 需求是：**圆角**、**纯色背景**、**可选描边**，打算写 `shape.xml` / `layer-list` / selector drawable。
- **默认前提**：工程已依赖 **DevWidget**（及 README 要求的 **DevApp**）。坐标与说明见仓库 [lib/DevWidget/README.md](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md)。
- **范围**：仅使用包路径 `dev.widget.ui.round` 下的实现；不引导使用 `dev.widget.ui.radius` 等其他目录。

## 核心原则

1. **用 Round 系列控件 + `app:dev_*`**，少建 drawable 文件。
2. **禁止与 `android:background` 混用（RoundDrawable 方案）**：圆角背景占用了 View 的 `background`。在 XML 里写 `android:background` **不会按预期生效**；背景色、边框请用下文 `app:dev_backgroundColor`、`app:dev_borderWidth`、`app:dev_borderColor`。
3. **RoundDrawable 只支持纯色填充**（`ColorStateList`），不支持 Bitmap/复杂 Drawable 作背景。
4. 命名空间：`xmlns:app="http://schemas.android.com/apk/res-auto"`（以工程为准）。

## Round 包类型一览（命中速查）

| 类型 | 类名 | 典型用途 |
| :--- | :--- | :--- |
| 布局容器 | `RoundFrameLayout` | 卡片、蒙层、任意子 View 容器 |
| 布局容器 | `RoundLinearLayout` | 横向/纵向圆角条 |
| 布局容器 | `RoundConstraintLayout` | Constraint 圆角容器 |
| 文本 | `RoundTextView` | 标签、角标、带圆角纯色底的文字 |
| 图片 | `RoundImageView` | **正圆**裁剪图片 + 可选圆圈描边/底色（实现基于 CircleImageView，**不是**圆角矩形图） |
| 非 View | `RoundDrawable` | 代码里给任意 View 设置同款背景：`fromAttributeSet` + `setBackgroundKeepingPadding` |

**选控件**：与原生同名布局一致即可（要 Constraint 用 `RoundConstraintLayout`，要文字用 `RoundTextView`…）。

**RoundImageView 与其它 Round* 的差异**：仅负责**圆形**头像式展示（`CENTER_CROP`）；若要圆角矩形图片请不在本 Skill 范围内另选方案。

## XML 属性（`declare-styleable` 名：`DevWidget`）

所有属性在资源中声明为 **`DevWidget`** 分组，XML 前缀一般为 **`app:`**。

### A. RoundDrawable 系（`RoundFrameLayout` / `RoundLinearLayout` / `RoundConstraintLayout` / `RoundTextView`）

| 属性 | 类型 | 作用                                                                                           |
| :--- | :--- |:---------------------------------------------------------------------------------------------|
| `app:dev_backgroundColor` | color | 填充色（纯色；可为状态色列表由 Drawable 处理）                                                                 |
| `app:dev_borderWidth` | dimension | 描边宽度；0 表示无边框                                                                                 |
| `app:dev_borderColor` | color | 描边颜色                                                                                         |
| `app:dev_radius` | dimension | 统一圆角半径；与四角属性互斥逻辑见下                                                                           |
| `app:dev_radiusLeftTop` | dimension | 左上圆角                                                                                         |
| `app:dev_radiusRightTop` | dimension | 右上圆角                                                                                         |
| `app:dev_radiusLeftBottom` | dimension | 左下圆角                                                                                         |
| `app:dev_radiusRightBottom` | dimension | 右下圆角                                                                                         |
| `app:dev_isRadiusAdjustBounds` | boolean | 为 true 时，在 `onBoundsChange` 中把圆角设为 **短边的一半**（胶囊/全圆角 View）；与显式 `dev_radius` 或任一角大于 0 的组合以源码为准 |

**圆角逻辑摘要**（`RoundDrawable.fromAttributeSet`）：

- 若 **任一角** `dev_radiusLeftTop` / `RightTop` / `LeftBottom` / `RightBottom` **> 0**：使用 `setCornerRadii` 四角独立圆角，并 **关闭** radius adjust bounds 的自动行为。
- 否则使用 **`dev_radius`** 作为 `setCornerRadius`；若 **`dev_radius` > 0** 同样会关闭与「仅 adjust」相关的自动圆角逻辑。
- 描边：`setStrokeData(borderWidth, borderColor)`。

### B. 仅 `RoundImageView`（圆形图 + 边框）

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:dev_borderWidth` | dimension | 圆圈描边宽度 |
| `app:dev_borderColor` | color | 描边颜色 |
| `app:dev_borderOverlay` | boolean | 边框是否与内容叠加（内缩 vs 覆盖） |
| `app:dev_circleBackgroundColor` | color | 图片背后**圆形**纯色填充（透明则可不画） |

**注意**：`RoundImageView` **不使用** `dev_radius` / `dev_backgroundColor` 等矩形圆角背景属性做圆角矩形；固定为圆形裁剪，`ScaleType` 仅支持 `CENTER_CROP`，不支持 `adjustViewBounds`。

## 代码侧（可选）

- `RoundDrawable.fromAttributeSet(context, attrs, defStyleAttr, defStyleRes)`：与 XML 相同属性集构造背景。
- `RoundDrawable.setBackgroundKeepingPadding(view, drawable)`：设置 background 并尽量保留 padding。
- 链式 API：`setBgData`、`setStrokeData` / `setStrokeColors`、`setRadiusAdjustBounds`、`getStrokeWidth` 等（见 `RoundDrawable` 源码）。

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

## 与 README 的对应关系

- 官方目录说明：`dev.widget.ui.round` 为圆角相关 View（见 [DevWidget README - 目录结构](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md)）。
- API 列表锚点：`devwidgetuiround` 小节中的 `RoundDrawable`、`RoundImageView` 等（README 中 **未单独列出** 各 `Round*Layout`，以模块源码 `lib/DevWidget/src/main/java/dev/widget/ui/round/` 为准）。

## 自检清单（生成布局前）

- [ ] 是否应用了 **DevWidget** 依赖且未用 `android:background` 覆盖圆角背景？
- [ ] 若是 **矩形**圆角容器：是否选了 `Round*Layout` / `RoundTextView` 之一？
- [ ] 若是 **圆形头像**：是否使用 `RoundImageView` 且接受 `CENTER_CROP`？
- [ ] 描边是否为 `app:dev_borderWidth` + `app:dev_borderColor` 组合？
