# DevWidget Round — 参考

与 [SKILL.md](SKILL.md) 配套：本地模块路径、Round 类型清单、`DevWidget` 属性表与源码对照。流程、取舍与最小示例见 SKILL。

## 模块路径（本地优先）

| 符号 | 当前值（DevUtils） | 说明 |
|------|-------------------|------|
| `DEVWIDGET_ROOT` | `lib/DevWidget` | DevWidget 模块根目录 |

| 用途 | 本地路径（优先 Read） |
|------|----------------------|
| 模块说明、目录结构、`devwidgetuiround` API 索引 | `{DEVWIDGET_ROOT}/README.md` |
| `round` 包 View / `RoundDrawable` 实现 | `{DEVWIDGET_ROOT}/src/main/java/dev/widget/ui/round/` |
| `declare-styleable` **`DevWidget`**、`app:dev_*` 定义 | `{DEVWIDGET_ROOT}/src/main/res/values/attrs.xml` |

上游 fallback（仅工作区无 `{DEVWIDGET_ROOT}` 或需对照远程 tag 时）：https://github.com/afkT/DevUtils/tree/master/lib/DevWidget

## Round 包类型一览

| 类型 | 类名 | 典型用途 |
|------|------|----------|
| 布局容器 | `RoundFrameLayout` | 卡片、蒙层、任意子 View 容器 |
| 布局容器 | `RoundLinearLayout` | 横向/纵向圆角条 |
| 布局容器 | `RoundConstraintLayout` | Constraint 圆角容器 |
| 布局容器 | `RoundRelativeLayout` | Relative 圆角容器 |
| 文本 | `RoundTextView` | 标签、角标、带圆角纯色底的文字 |
| 按钮 | `RoundButton` | 圆角纯色底按钮 |
| 图片 | `RoundImageView` | 正圆裁剪图片 + 可选圆圈描边/底色 |
| 非 View | `RoundDrawable` | 代码里给任意 View 设置同款背景 |

完整类列表以 `{DEVWIDGET_ROOT}/src/main/java/dev/widget/ui/round/` 为准。

## XML 属性（`declare-styleable` 名：`DevWidget`）

属性定义以 `{DEVWIDGET_ROOT}/src/main/res/values/attrs.xml` 为准；XML 前缀一般为 `app:`。

### RoundDrawable 系

适用于 `RoundFrameLayout` / `RoundLinearLayout` / `RoundConstraintLayout` / `RoundTextView` 等。

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:dev_backgroundColor` | color | 填充色（纯色；可为状态色列表） |
| `app:dev_borderWidth` | dimension | 描边宽度；0 表示无边框 |
| `app:dev_borderColor` | color | 描边颜色 |
| `app:dev_radius` | dimension | 统一圆角半径 |
| `app:dev_radiusLeftTop` | dimension | 左上圆角 |
| `app:dev_radiusRightTop` | dimension | 右上圆角 |
| `app:dev_radiusLeftBottom` | dimension | 左下圆角 |
| `app:dev_radiusRightBottom` | dimension | 右下圆角 |
| `app:dev_isRadiusAdjustBounds` | boolean | 为 true 时，在 `onBoundsChange` 中把圆角设为短边一半 |

## 圆角逻辑摘要

对照 `{DEVWIDGET_ROOT}/src/main/java/dev/widget/ui/round/RoundDrawable.java` 中 `fromAttributeSet`：

- 任一独立角 `dev_radiusLeftTop` / `RightTop` / `LeftBottom` / `RightBottom` > 0：使用 `setCornerRadii`，关闭 radius adjust bounds 的自动行为。
- 否则使用 `dev_radius` 作为 `setCornerRadius`；`dev_radius > 0` 同样会关闭仅 adjust 的自动圆角逻辑。
- 描边：`setStrokeData(borderWidth, borderColor)`。

### 仅 `RoundImageView`

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:dev_borderWidth` | dimension | 圆圈描边宽度 |
| `app:dev_borderColor` | color | 描边颜色 |
| `app:dev_borderOverlay` | boolean | 边框是否与内容叠加 |
| `app:dev_circleBackgroundColor` | color | 图片背后圆形纯色填充 |

`RoundImageView` 固定为圆形裁剪，`ScaleType` 仅支持 `CENTER_CROP`，不用于圆角矩形图片。

## 代码侧 API

- `RoundDrawable.fromAttributeSet(context, attrs, defStyleAttr, defStyleRes)`：与 XML 相同属性集构造背景。
- `RoundDrawable.setBackgroundKeepingPadding(view, drawable)`：设置 background 并尽量保留 padding。
- 链式 API：`setBgData`、`setStrokeData` / `setStrokeColors`、`setRadiusAdjustBounds`、`getStrokeWidth` 等。

## 与 README / 源码的对应关系

- 目录与 API 索引：Read `{DEVWIDGET_ROOT}/README.md`（`dev.widget.ui.round`、`devwidgetuiround` 小节）。
- README 未单独列出的 `Round*Layout` 等：以 `{DEVWIDGET_ROOT}/src/main/java/dev/widget/ui/round/` 下现有类名为准。
- 属性与行为有争议时：Read `attrs.xml` 与 `RoundDrawable.java`，不要仅凭记忆或远程文档。

## 维护本 reference

DevWidget 新增 Round 类、属性或行为时：

1. Read `{DEVWIDGET_ROOT}/src/main/java/dev/widget/ui/round/` 与 `{DEVWIDGET_ROOT}/src/main/res/values/attrs.xml`。
2. 更新 Round 类型清单、属性表或 API 表。
3. SKILL.md 保持流程和关键坑，不恢复完整属性表。
