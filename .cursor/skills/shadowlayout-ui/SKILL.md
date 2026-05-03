---
name: shadowlayout-ui
description: >-
  在布局中需要阴影、圆角、纯色/渐变背景、描边、pressed/selected/ripple、虚线、子 View 按圆角裁剪，
  或想少写 shape/layer-list/selector drawable 时，优先使用 com.lihang.ShadowLayout（
  https://github.com/lihangleo2/ShadowLayout ）。列出 R.styleable.ShadowLayout 下全部 app:hl_* 与行为、
  常见坑与代码 API。默认工程已依赖该库（如 JitPack com.github.lihangleo2:ShadowLayout）。
---

# ShadowLayout：阴影 + Shape/Selector + 裁剪

## 何时用本 Skill（对齐 README 能力）

参考上游 [README.md](https://github.com/lihangleo2/ShadowLayout/blob/master/README.md) 与本地对照过的 `shadowLibrary/.../attrs.xml`、`ShadowLayout.java`。

在以下场景**优先用 `ShadowLayout` 包裹子 View**，减少 `drawable` 里的 shape / selector / layer-list：

- **阴影**：颜色、扩散、圆角、X/Y 偏移、单边或多边隐藏、是否完全关闭阴影。
- **圆角**：统一圆角，或四角独立（含阴影与内容区）。
- **背景**：纯色、`reference` 图片、与 **pressed / selected / ripple** 组合（类 selector）。
- **描边**：实线或虚线（dash）；可按状态切换描边色。
- **渐变**：线性渐变（起止色、可选中间色、角度）；设置后纯色背景属性失效。
- **不可点击态**：`clickable=false` 时的单独背景（配合 pressed 等）。
- **子 View 圆角裁剪**：如视频、图片、任意 View 需要不规则圆角外轮廓裁剪（README「剪裁各种难以搞定的圆角」）。

**不适用 / 慎用**：`hl_shapeMode="dashLine"` 时控件退化为**虚线线段**模式，初始化路径与阴影/shape 主流程不同，不能混用渐变等能力（见下文「虚线模式」）。

**与 DevWidget `round` 包**：若需求仅为无阴影的圆角矩形纯色+描边且工程已用 DevWidget，可按项目 [devwidget-round-ui](../devwidget-round-ui/SKILL.md) 取舍；本 Skill 覆盖**阴影、渐变、ripple、selector 态、裁剪**等更重场景。

## 核心事实（减少踩坑）

1. **类与父类**：`com.lihang.ShadowLayout` 继承 `FrameLayout`，子 View 写在标签内即可。
2. **命名空间**：`xmlns:app="http://schemas.android.com/apk/res-auto"`（以工程为准）。
3. **styleable 名**：`ShadowLayout`，XML 里属性前缀一般为 **`app:`**（如 `app:hl_cornerRadius`）。
4. **库内拼写**：描边宽度属性为 **`app:hl_strokeWith`**（README 与源码均为 `With`，不是 `Width`）。
5. **默认背景**：未设置背景时内部逻辑偏白色底；**仅做裁剪、不要白底**时设 `app:hl_layoutBackground` 为透明色（README 示例用 `@color/transparent`）。
6. **子 View 裁剪**：`dispatchDraw` 内对子级做 `clipPath`（`@RequiresApi(LOLLIPOP)`）；逻辑在存在 **`getChildAt(0) != null`** 时按圆角裁剪，需 API 21+ 行为与设备 GPU 路径支持。
7. **RecyclerView 内点透**：README 建议裁剪容器上 `app:clickable="false"` 避免与 item 点击冲突（见属性表 `clickable`）。

## `hl_shapeMode` 枚举（attrs.xml）

| 枚举值 | 含义 |
| :--- | :--- |
| `pressed` | 按下态切换 `hl_layoutBackground` / `hl_layoutBackground_true`（及描边、绑定文案等） |
| `selected` | 选中态，配合 `setSelected()` / `android:selected` |
| `ripple` | 水波纹；与渐变逻辑分支不同（ripple 分支内处理） |
| `dashLine` | **仅虚线**：只用 `hl_strokeColor`、`hl_stroke_dashWidth`、`hl_stroke_dashGap`，初始化早退，**不要**再配阴影/渐变等 |

## XML 属性全表（`declare-styleable name="ShadowLayout"`）

### 阴影

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:hl_shadowHidden` | boolean | `true` 关闭阴影（只要 shape/裁剪等） |
| `app:hl_shadowColor` | color | 阴影颜色；无 alpha 时库内会按约定补透明度（见 README） |
| `app:hl_shadowLimit` | dimension | 阴影扩散/大小 |
| `app:hl_shadowOffsetX` | dimension | 阴影 X 偏移 |
| `app:hl_shadowOffsetY` | dimension | 阴影 Y 偏移 |
| `app:hl_shadowSymmetry` | boolean | 内容区是否相对阴影对称；`false` 时内容区随阴影区域（README 配图 isSym） |
| `app:hl_shadowHiddenLeft` | boolean | 为 `true` 隐藏左侧阴影（Right/Top/Bottom 同理） |
| `app:hl_shadowHiddenRight` | boolean | 隐藏右侧阴影 |
| `app:hl_shadowHiddenTop` | boolean | 隐藏上侧阴影 |
| `app:hl_shadowHiddenBottom` | boolean | 隐藏下侧阴影 |

### 圆角（阴影圆角 + shape 圆角 + 裁剪路径共用）

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:hl_cornerRadius` | dimension | 统一圆角 |
| `app:hl_cornerRadius_leftTop` | dimension | 左上；与其它三角组合可覆盖统一值 |
| `app:hl_cornerRadius_rightTop` | dimension | 右上 |
| `app:hl_cornerRadius_leftBottom` | dimension | 左下 |
| `app:hl_cornerRadius_rightBottom` | dimension | 右下 |

### Shape / 背景 / 描边

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:hl_shapeMode` | enum | 见上表 `pressed` / `selected` / `ripple` / `dashLine` |
| `app:hl_layoutBackground` | reference\|color | 「false/常态」背景：颜色、drawable、图片等 |
| `app:hl_layoutBackground_true` | reference\|color | 「true/另一态」背景；**必须与** `hl_layoutBackground` **成对**（attrs 注释：否则报错） |
| `app:hl_strokeWith` | dimension | 描边宽度（**注意拼写 With**） |
| `app:hl_strokeColor` | color | 常态描边色 |
| `app:hl_strokeColor_true` | color | 另一态描边色；需与 `hl_strokeColor` 搭配 |
| `app:hl_stroke_dashWidth` | dimension | 虚线段实部长度 |
| `app:hl_stroke_dashGap` | dimension | 虚线间隙 |

### 点击与不可点击背景

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:clickable` | boolean | 库自定义可点性（README：避免系统 `setOnClickListener` 擅自改 clickable）；`false` 常用于裁剪容器 |
| `app:hl_layoutBackground_clickFalse` | reference\|color | `clickable=false` 且 `hl_shapeMode="pressed"` 时展示的背景 |

### 渐变（设置后以渐变为主）

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:hl_startColor` | color | 渐变起点色 |
| `app:hl_centerColor` | color | 中间色（可选） |
| `app:hl_endColor` | color | 终点色 |
| `app:hl_angle` | integer | 角度，默认 0；代码 `setGradientColor` 要求为 **45 的倍数** |

### 绑定 TextView（文案与颜色随状态）

| 属性 | 类型 | 作用 |
| :--- | :--- | :--- |
| `app:hl_bindTextView` | reference | 绑定目标 TextView 的 id（如 `@+id/xxx`） |
| `app:hl_text` | string | 常态文案 |
| `app:hl_text_true` | string | 另一态文案 |
| `app:hl_textColor` | color | 常态文字色 |
| `app:hl_textColor_true` | color | 另一态文字色 |

## 虚线模式 `dashLine`（单独使用）

- 仅保留：`hl_shapeMode="dashLine"`、`hl_strokeColor`、`hl_stroke_dashWidth`、`hl_stroke_dashGap`（缺一会抛 `UnsupportedOperationException`）。
- **横向虚线**：View **宽 > 高**；**纵向虚线**：高 ≥ 宽（README：以长边为宽度、短边为线粗语义）。
- 此模式下勿再依赖阴影、渐变、圆角卡片等主流程 API（源码 `isExceptionByDashLine()` 会拦部分调用）。

## 常用代码 API（与 README 方法表一致处已核对）

链式返回 `ShadowLayout` 的常用 setter：

- 阴影：`setShadowHidden(boolean hide)`、`setShadowColor`、`setShadowLimit`、`setShadowOffsetX`、`setShadowOffsetY`、`setShadowHiddenTop/Left/Right/Bottom`
- 圆角：`setCornerRadius`、`setSpecialCorner(leftTop, rightTop, leftBottom, rightBottom)`
- 背景与描边：`setLayoutBackground`、`setLayoutBackgroundTrue`、`setStrokeColor`、`setStrokeColorTrue`、`setStrokeWidth`
- 渐变：`setGradientColor(start, end)` / `setGradientColor(angle, start, end)` / `setGradientColor(angle, start, center, end)`（angle 须为 45 倍数）
- 点击：`setClickable(boolean)`（覆盖内会维护库内 `isClickable` 与背景切换）
- 选中：`setSelected(boolean)`（`selected` 模式下切换 true/false 背景）

**注意**：`setLayoutBackground` 在 **`isClickable == false`** 时直接 return，不更新背景（源码行为）；动态改色前确认可点性设计。

## XML 最小片段（速查）

**圆角 + 阴影 + 子 View**

```xml
<com.lihang.ShadowLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:hl_cornerRadius="10dp"
    app:hl_shadowColor="#2a000000"
    app:hl_shadowLimit="5dp">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="36dp"
        android:text="内容" />
</com.lihang.ShadowLayout>
```

**仅裁剪 + 透明底（README 视频圆角）**

```xml
<com.lihang.ShadowLayout
    android:layout_width="match_parent"
    android:layout_height="200dp"
    app:hl_cornerRadius_leftTop="12dp"
    app:hl_cornerRadius_rightTop="12dp"
    app:hl_layoutBackground="@android:color/transparent"
    app:clickable="false">
    <!-- 子 View：SurfaceView / TextureView / ImageView 等 -->
</com.lihang.ShadowLayout>
```

## 依赖（README）

- 仓库：`https://github.com/lihangleo2/ShadowLayout`
- 典型坐标：`implementation 'com.github.lihangleo2:ShadowLayout:3.4.5'`（AndroidX）；老非 AndroidX 见 README 3.3.3 说明。
- 仓库 `shadowLibrary` 模块 attrs 路径：`shadowLibrary/src/main/res/values/attrs.xml`。

## 自检清单（写布局前）

- [ ] 是否需要 **阴影**？不需要则 `hl_shadowHidden="true"` 或只配 shape/裁剪。
- [ ] 描边宽度是否写成 **`hl_strokeWith`**？
- [ ] `hl_layoutBackground_true` / `hl_strokeColor_true` 是否与对应「常态」属性成对？
- [ ] 渐变是否误配了仍期望生效的 `hl_layoutBackground`？
- [ ] 裁剪/列表场景是否处理 **`clickable`** 与 **透明背景**？
- [ ] `dashLine` 是否单独使用且 dash 参数成对？
