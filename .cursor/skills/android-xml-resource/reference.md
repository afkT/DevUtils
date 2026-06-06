# Android XML 资源命名 — 参考

与 [SKILL.md](SKILL.md) 配套：各资源类型的前缀全表、drawable 值编码族语法、values 命名细则、配置限定符顺序、示例与反例、完整校验清单。流程与重点（单文件 selector 范式）见 SKILL。

## layout 命名

文件名 = `<顶层组件类型>_<功能名>`，组件类型提到最前：

| 用途 | 前缀 | 类名示例 | 文件名示例 |
|------|------|----------|------------|
| Activity | `activity_` | `UserProfileActivity` | `activity_user_profile.xml` |
| Fragment | `fragment_` | `SignUpFragment` | `fragment_sign_up.xml` |
| Dialog / DialogFragment | `dialog_` | `ChangePasswordDialog` | `dialog_change_password.xml` |
| 列表/适配器 item | `item_` | — | `item_person.xml` |
| 自定义 View 布局 | `view_` | `ProfileAvatarView` | `view_profile_avatar.xml` |
| 被 include 的局部布局 | `partial_` / `include_` | — | `partial_stats_bar.xml` |

- 分模块时叠加模块域：`activity_main_home.xml`、`item_learning_word.xml`。
- 局部复用布局用 `partial_`/`include_`，不要用业务页面前缀。

## drawable 命名两族

### A. 图标 / 模块状态族

`[<module>_]<feature>_<role>[_<state|type>]`：

| 资产类型 | 前缀 | 示例 |
|----------|------|------|
| 图标 | `ic_` | `ic_star.png` |
| 按钮背景 | `btn_` | `btn_send.xml` |
| 分割线 | `divider_` | `divider_horizontal.xml` |
| 通知 | `notification_` | `notification_bg.xml` |
| 模块图标选择器 | `<module>_..._selector` | `main_tab_main_selector.xml`、`me_tab_selector.xml` |

模块图标选择器内部用 `@mipmap/...` 或 `@drawable/...` 切换 checked/pressed/selected/default。

### B. 值编码族（通用背景 / 描边 / 文本色）

把颜色、圆角、描边、状态编码进文件名，便于复用与去重：

```
bg_<color1>[_<color2>][_<alphaPercent>][_r<radius>][_border[_<borderColor>]][_w<width>]_<suffix>
border_<strokeColor>[_<alphaPercent>]_r<radius>[_w<width>]
text_color_<color1>_<color2>[_..]_selector
```

| 段 | 规则 |
|----|------|
| 前缀 | `bg_` 纯色/状态背景；`border_` 以描边为主（填充常为透明/白）；`text_color_` 文本/图标 `ColorStateList` |
| 颜色 token | 6 位小写 hex 不带 `#`（`ffffff`、`0088ff`）；按真实色值取，短别名也写满 6 位。第 1 个=默认/normal，第 2 个=激活态 |
| alpha | `_<百分比>`，`_40` = 40% 不透明（≈ `#66......`） |
| 圆角 | `r<dp>`，小数用 `_`：`r5`=5dp、`r2_5`=2.5dp、`r50`=胶囊 |
| 描边 | `border` 表示有 `stroke`，可带描边色 `border_0088ff`；线宽 `w<dp>`（`w1`=1dp） |
| 后缀 | `selector`（多 state 同文件）、`shape`（单一 shape）、`gradient`（渐变） |

示例：

| 文件名 | 含义 |
|--------|------|
| `bg_ffffff_0088ff_r5_border_selector.xml` | 白底 / 激活态高亮 0088ff、圆角 5dp、有描边的状态背景 |
| `bg_ffffff_ebf5ff_r2_5_selector.xml` | 白底 / 激活 ebf5ff、圆角 2.5dp、无描边 |
| `bg_ffffff_r50_border_000000_40.xml` | 白底、圆角 50（胶囊）、描边 000000@40% 的单一 shape |
| `border_ededed_r10_w1.xml` | 透明填充、描边 ededed、圆角 10dp、线宽 1dp |
| `text_color_333333_ffffff_selector.xml` | 文本 `ColorStateList`：默认 333333、激活态 ffffff |

## values 命名

文件名复数化：`strings.xml`、`colors.xml`、`dimens.xml`、`styles.xml`、`attrs.xml`、`themes.xml`、`arrays.xml`、`ids.xml`。

### strings

- `name` 用 `snake_case`，按用途/页面加语义前缀：`title_*`、`label_*`、`btn_*`、`hint_*`、`msg_*`、`error_*`。
- 不要把同一文案复制成多个 key。

### colors

- 调色板条目：`color_<hex6>`，带透明度 `color_<hex6>_<alphaPercent>`：
  - `color_ffffff` = `#ffffff`，`color_0088ff` = `#0088ff`，`color_000000_40` = `#66000000`。
- 语义别名可叠加一层（`color_primary`、`color_text_secondary`、`white`、`transparent`）；引用语义别名而非散落 hex，新增以调色板 `color_<hex>` 为主。

### dimens

- 物理长度 `dp_<n>`、文字 `sp_<n>`；小数用 `_` 连接小数点后一位：`dp_12`、`dp_0_5`、`dp_12_5`、`sp_14`。
- 整数不写 `dp_12_0`；小数 **截断** 到一位（`12.5555` → `dp_12_5`，不四舍五入）。

### styles / themes

- `name` 用 PascalCase + 点分层级：`Widget.App.Button.Primary`、`Theme.App`、`TextAppearance.App.Title`。
- `theme` 用 `Theme.<App>[.<变体>]`；`style` 按「类型.作用域.角色」分层。

### ids（布局内 `@+id/`）

- `<控件类型>_<语义>`：`btn_submit`、`tv_title`、`et_username`、`rv_list`、`iv_avatar`、`cl_root`。
- 同一布局内 id 唯一且语义化；不要用 `view1`、`textView2` 这类自动名。

## 配置限定符顺序

限定符以 `-` 追加到目录基名，并按官方优先级从左到右排列（节选常用，左高右低）：

```
MCC/MNC → 语言_地区(zh-rCN) → 布局方向(ldrtl) → 最小宽度(sw600dp)
→ 可用宽/高(w/h) → 屏幕尺寸(small..xlarge) → 屏幕方向(land/port)
→ UI 模式(night) → 密度(hdpi/xhdpi/xxhdpi) → 版本(v21)
```

- 例：`drawable-night-xxhdpi`、`values-zh-rCN`、`layout-sw600dp-land`。
- 同一限定符类型只能出现一个值；目录不可嵌套。

## 状态资源单文件整合（细则）

- **背景 selector**：每个 `<item>` 内联 `<shape>`（`corners`/`solid`/`stroke`/`gradient`），不外链多个 shape 文件。
- **ColorStateList**：`res/color/` 下 `<selector>`，每个 `<item>` 指定 `android:color` + state，默认项无 state 放末尾。
- **图标 selector**：`<item android:drawable="@mipmap/..." android:state_xxx="true"/>`，默认项放末尾。
- **顺序**：具体/优先 state 在前（`state_checked`/`state_pressed`/`state_selected`/`state_focused`…），默认兜底项放最后。

范式 XML 见 [SKILL.md § 状态资源单文件整合](SKILL.md#状态资源单文件整合强约束--重点)。

## 模块化资源目录

若工程按模块拆分（如 `res-<module>/`）：

- 模块内文件名以模块/功能域起头：`main_tab_*`、`learning_word_*`、`test_questions_*`。
- 跨模块复用的通用背景/描边放公共 `res/`，用值编码族命名。
- 同名冲突由前缀规避，不要依赖目录隔离放任同名。

## 反例

- `Activity_Main.xml`、`btnSubmit.xml`、`ic-Star.png`（大写/连字符，编译失败）。
- `shape1.xml`、`bg.xml`、`selector.xml`（无语义、不可预测）。
- `bg_blue_normal.xml` + `bg_blue_pressed.xml` + `bg_blue_selector.xml`（应整合为一个 selector）。
- `dp_12_0`、`12dp`（dimen 应为 `dp_12`）。
- 在 `res/` 根直接放文件，或自造 `res/widgets/` 目录。

## 完整校验清单

- [ ] 文件名仅小写字母/数字/下划线，且有正确前缀。
- [ ] 资源放进预定义类型目录；限定符按官方顺序、以 `-` 追加。
- [ ] layout 用组件前缀；id 用「控件类型_语义」。
- [ ] drawable 选对命名族：图标/模块 vs 值编码（颜色/圆角/描边/状态）。
- [ ] 颜色 `color_<hex6>[_<alphaPercent>]`；尺寸 `dp_*`/`sp_*`；style/theme 用 PascalCase 点分。
- [ ] 多 state 资源 **单文件**、背景内联 shape、默认项放最后。
- [ ] 颜色 hex 取自真实色值（短别名也写满 6 位）。
- [ ] 模块资源带模块前缀，放对 `res-<module>/`。
- [ ] 新建前已确认无法用现成 View 能力 / ColorStateList 复用。
