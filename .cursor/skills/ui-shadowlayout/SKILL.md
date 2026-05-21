---
name: ui-shadowlayout
description: >-
  在布局中需要阴影、圆角、纯色/渐变背景、描边、pressed/selected/ripple、虚线、子 View 按圆角裁剪，
  或想少写 shape/layer-list/selector drawable 时，优先使用 com.lihang.ShadowLayout（
  https://github.com/lihangleo2/ShadowLayout ）。流程与常见坑在 SKILL，R.styleable.ShadowLayout
  下全部 app:hl_*、代码 API 与 raw 源码见 reference.md。以 Maven/JitPack 坐标与上游
  GitHub README/raw 为准，不依赖工作区 shadowLibrary 模块路径。
---

# ShadowLayout：阴影 + Shape/Selector + 裁剪

## 权威来源（仅 Maven + 上游 GitHub）

**禁止**在工作区内查找或假设存在 `shadowLibrary/` 等上游子模块路径。能力说明、`hl_*` 属性全表、代码 API、版本 tag 与 raw 链接见 [reference.md](reference.md)。

新增依赖：JitPack 坐标 `com.github.lihangleo2:ShadowLayout:<version>`；本仓库集中坐标时 Read [gradle-central-deps/SKILL.md](../gradle-central-deps/SKILL.md)，勿重复 GAV。

## 何时用本 Skill（对齐 README 能力）

对照上游 README；属性枚举与实现细节以 [reference.md](reference.md) 中的上游 raw 为准（**仅通过上游 URL**，不读本地模块）。

在以下场景**优先用 `ShadowLayout` 包裹子 View**，减少 `drawable` 里的 shape / selector / layer-list：

- **阴影**：颜色、扩散、圆角、X/Y 偏移、单边或多边隐藏、是否完全关闭阴影。
- **圆角**：统一圆角，或四角独立（含阴影与内容区）。
- **背景**：纯色、`reference` 图片、与 **pressed / selected / ripple** 组合（类 selector）。
- **描边**：实线或虚线（dash）；可按状态切换描边色。
- **渐变**：线性渐变（起止色、可选中间色、角度）；设置后纯色背景属性失效。
- **不可点击态**：`clickable=false` 时的单独背景（配合 pressed 等）。
- **子 View 圆角裁剪**：如视频、图片、任意 View 需要不规则圆角外轮廓裁剪（README「剪裁各种难以搞定的圆角」）。

**不适用 / 慎用**：`hl_shapeMode="dashLine"` 时控件退化为**虚线线段**模式，初始化路径与阴影/shape 主流程不同，不能混用渐变等能力（见下文「虚线模式」）。

**与 DevWidget `round` 包**：若需求仅为无阴影的圆角矩形纯色+描边且工程已用 DevWidget，可按项目 [ui-devwidget-round](../ui-devwidget-round/SKILL.md) 取舍；本 Skill 覆盖**阴影、渐变、ripple、selector 态、裁剪**等更重场景。

## 核心事实（减少踩坑）

1. **类与父类**：`com.lihang.ShadowLayout` 继承 `FrameLayout`，子 View 写在标签内即可。
2. **命名空间**：`xmlns:app="http://schemas.android.com/apk/res-auto"`（以工程为准）。
3. **styleable 名**：`ShadowLayout`，XML 里属性前缀一般为 **`app:`**（全表见 [reference.md](reference.md#xml-属性全表declare-styleable-nameshadowlayout)）。
4. **库内拼写**：描边宽度属性为 **`app:hl_strokeWith`**（README 与源码均为 `With`，不是 `Width`）。
5. **默认背景**：未设置背景时内部逻辑偏白色底；**仅做裁剪、不要白底**时设 `app:hl_layoutBackground` 为透明色（README 示例用 `@color/transparent`）。
6. **子 View 裁剪**：`dispatchDraw` 内对子级做 `clipPath`（`@RequiresApi(LOLLIPOP)`）；逻辑在存在 **`getChildAt(0) != null`** 时按圆角裁剪，需 API 21+ 行为与设备 GPU 路径支持。
7. **RecyclerView 内点透**：README 建议裁剪容器上 `app:clickable="false"` 避免与 item 点击冲突（见属性表 `clickable`）。

## 虚线模式 `dashLine`（单独使用）

- 仅保留：`hl_shapeMode="dashLine"`、`hl_strokeColor`、`hl_stroke_dashWidth`、`hl_stroke_dashGap`（缺一会抛 `UnsupportedOperationException`）。
- **横向虚线**：View **宽 > 高**；**纵向虚线**：高 ≥ 宽（README：以长边为宽度、短边为线粗语义）。
- 此模式下勿再依赖阴影、渐变、圆角卡片等主流程 API（上游行为见 [reference.md](reference.md#虚线模式-dashline)）。

## 常用属性与 API 速查

- 属性全表：见 [reference.md § XML 属性全表](reference.md#xml-属性全表declare-styleable-nameshadowlayout)。
- `hl_shapeMode` 枚举：见 [reference.md § hl_shapeMode 枚举](reference.md#hl_shapemode-枚举)。
- 常用 setter：见 [reference.md § 常用代码 API](reference.md#常用代码-api)。

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

## 自检清单（写布局前）

- [ ] 是否需要 **阴影**？不需要则 `hl_shadowHidden="true"` 或只配 shape/裁剪。
- [ ] 描边宽度是否写成 **`hl_strokeWith`**？
- [ ] `hl_layoutBackground_true` / `hl_strokeColor_true` 是否与对应「常态」属性成对？
- [ ] 渐变是否误配了仍期望生效的 `hl_layoutBackground`？
- [ ] 裁剪/列表场景是否处理 **`clickable`** 与 **透明背景**？
- [ ] `dashLine` 是否单独使用且 dash 参数成对？
- [ ] 版本/属性有疑义时是否已对照 **与依赖版本同 tag** 的上游 raw（README / attrs / `ShadowLayout.java`），而非工作区路径？
