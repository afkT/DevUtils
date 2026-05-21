# ShadowLayout — 参考

与 [SKILL.md](SKILL.md) 配套：权威来源、`hl_*` 属性全表、代码 API 与版本 tag 核对。流程、取舍与最小示例见 SKILL。

## 权威来源

本 Skill **不依赖工作区 `shadowLibrary/` 模块路径**；只使用 Maven/JitPack 坐标与上游 GitHub README/raw。

| 用途 | 链接 |
|------|------|
| 能力说明、示例、依赖写法 | [README.md](https://github.com/lihangleo2/ShadowLayout/blob/master/README.md)（raw: https://raw.githubusercontent.com/lihangleo2/ShadowLayout/master/README.md） |
| `declare-styleable` / `hl_*` 定义 | https://raw.githubusercontent.com/lihangleo2/ShadowLayout/master/shadowLibrary/src/main/res/values/attrs.xml |
| 运行时行为、setter、边界逻辑 | https://raw.githubusercontent.com/lihangleo2/ShadowLayout/master/shadowLibrary/src/main/java/com/lihang/ShadowLayout.java |

- **版本对齐**：模块已声明的 `com.github.lihangleo2:ShadowLayout:x.y.z` 与上游 Git tag `x.y.z` 应对齐；核对 attrs/API 时用 `https://raw.githubusercontent.com/lihangleo2/ShadowLayout/{tag}/shadowLibrary/...` 替换上表中的 `master`。
- **新增依赖**：JitPack 坐标 `com.github.lihangleo2:ShadowLayout:<version>`；本仓库集中坐标时 Read [../gradle-central-deps/SKILL.md](../gradle-central-deps/SKILL.md)。

## `hl_shapeMode` 枚举

| 枚举值 | 含义 |
|--------|------|
| `pressed` | 按下态切换 `hl_layoutBackground` / `hl_layoutBackground_true`（及描边、绑定文案等） |
| `selected` | 选中态，配合 `setSelected()` / `android:selected` |
| `ripple` | 水波纹；与渐变逻辑分支不同 |
| `dashLine` | **仅虚线**：只用 `hl_strokeColor`、`hl_stroke_dashWidth`、`hl_stroke_dashGap`，初始化早退，不要再配阴影/渐变等 |

## XML 属性全表（`declare-styleable name="ShadowLayout"`）

### 阴影

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:hl_shadowHidden` | boolean | `true` 关闭阴影（只要 shape/裁剪等） |
| `app:hl_shadowColor` | color | 阴影颜色；无 alpha 时库内会按约定补透明度 |
| `app:hl_shadowLimit` | dimension | 阴影扩散/大小 |
| `app:hl_shadowOffsetX` | dimension | 阴影 X 偏移 |
| `app:hl_shadowOffsetY` | dimension | 阴影 Y 偏移 |
| `app:hl_shadowSymmetry` | boolean | 内容区是否相对阴影对称；`false` 时内容区随阴影区域 |
| `app:hl_shadowHiddenLeft` | boolean | 隐藏左侧阴影 |
| `app:hl_shadowHiddenRight` | boolean | 隐藏右侧阴影 |
| `app:hl_shadowHiddenTop` | boolean | 隐藏上侧阴影 |
| `app:hl_shadowHiddenBottom` | boolean | 隐藏下侧阴影 |

### 圆角

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:hl_cornerRadius` | dimension | 统一圆角 |
| `app:hl_cornerRadius_leftTop` | dimension | 左上角；可覆盖统一值 |
| `app:hl_cornerRadius_rightTop` | dimension | 右上角 |
| `app:hl_cornerRadius_leftBottom` | dimension | 左下角 |
| `app:hl_cornerRadius_rightBottom` | dimension | 右下角 |

### Shape / 背景 / 描边

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:hl_shapeMode` | enum | `pressed` / `selected` / `ripple` / `dashLine` |
| `app:hl_layoutBackground` | reference\|color | 常态背景：颜色、drawable、图片等 |
| `app:hl_layoutBackground_true` | reference\|color | 另一态背景；须与 `hl_layoutBackground` 成对 |
| `app:hl_strokeWith` | dimension | 描边宽度（注意拼写 `With`，不是 `Width`） |
| `app:hl_strokeColor` | color | 常态描边色 |
| `app:hl_strokeColor_true` | color | 另一态描边色；需与 `hl_strokeColor` 搭配 |
| `app:hl_stroke_dashWidth` | dimension | 虚线段实部长度 |
| `app:hl_stroke_dashGap` | dimension | 虚线间隙 |

### 点击与不可点击背景

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:clickable` | boolean | 库自定义可点性；`false` 常用于裁剪容器避免点透冲突 |
| `app:hl_layoutBackground_clickFalse` | reference\|color | `clickable=false` 且 `hl_shapeMode="pressed"` 时展示的背景 |

### 渐变

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:hl_startColor` | color | 渐变起点色 |
| `app:hl_centerColor` | color | 中间色（可选） |
| `app:hl_endColor` | color | 终点色 |
| `app:hl_angle` | integer | 角度，默认 0；代码 `setGradientColor` 要求为 45 的倍数 |

### 绑定 TextView

| 属性 | 类型 | 作用 |
|------|------|------|
| `app:hl_bindTextView` | reference | 绑定目标 TextView 的 id |
| `app:hl_text` | string | 常态文案 |
| `app:hl_text_true` | string | 另一态文案 |
| `app:hl_textColor` | color | 常态文字色 |
| `app:hl_textColor_true` | color | 另一态文字色 |

## 虚线模式 `dashLine`

- 仅保留：`hl_shapeMode="dashLine"`、`hl_strokeColor`、`hl_stroke_dashWidth`、`hl_stroke_dashGap`（缺一会抛 `UnsupportedOperationException`）。
- 横向虚线：View **宽 > 高**；纵向虚线：高 >= 宽。
- 此模式下勿再依赖阴影、渐变、圆角卡片等主流程 API；上游 `ShadowLayout.java` 中 `isExceptionByDashLine()` 会拦部分调用。

## 常用代码 API

链式返回 `ShadowLayout` 的常用 setter：

- 阴影：`setShadowHidden(boolean hide)`、`setShadowColor`、`setShadowLimit`、`setShadowOffsetX`、`setShadowOffsetY`、`setShadowHiddenTop/Left/Right/Bottom`
- 圆角：`setCornerRadius`、`setSpecialCorner(leftTop, rightTop, leftBottom, rightBottom)`
- 背景与描边：`setLayoutBackground`、`setLayoutBackgroundTrue`、`setStrokeColor`、`setStrokeColorTrue`、`setStrokeWidth`
- 渐变：`setGradientColor(start, end)` / `setGradientColor(angle, start, end)` / `setGradientColor(angle, start, center, end)`（angle 须为 45 倍数）
- 点击：`setClickable(boolean)`（覆盖内会维护库内 `isClickable` 与背景切换）
- 选中：`setSelected(boolean)`（`selected` 模式下切换 true/false 背景）

**注意**：`setLayoutBackground` 在 `isClickable == false` 时直接 return，不更新背景；动态改色前确认可点性设计。

## 依赖（Maven / JitPack）

- 仓库首页：https://github.com/lihangleo2/ShadowLayout
- 坐标：`com.github.lihangleo2:ShadowLayout`
- AndroidX 示例（版本请以 README/JitPack 核对）：`implementation 'com.github.lihangleo2:ShadowLayout:3.4.x'`
- 非 AndroidX 历史版本见 README `3.3.3` 说明。

## 维护本 reference

升级 ShadowLayout 或发现属性不一致时：

1. 确认项目使用的 Maven/JitPack 版本。
2. 用同版本 Git tag 读取上游 README、`attrs.xml`、`ShadowLayout.java`。
3. 更新本文的属性表、API 表与依赖说明。
4. 不要引入工作区 `shadowLibrary/` 路径。
