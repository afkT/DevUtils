---
name: devsimple-viewtheme-xml
description: >-
  为布局 XML 中的控件默认补全 DevSimple（lib/DevSimple）的 ViewTheme 样式引用：
  按控件继承链匹配 ViewTheme.&lt;类名&gt;，ImageView/AppCompatImageView 使用
  ViewTheme.ImageView.FIT_XY，无匹配则用 ViewTheme.View。在编写或批量整理
  layout XML、引入 ViewTheme、迁移旧布局、或用户提到 DevSimple/ViewTheme
  时使用。默认工程已依赖 DevSimple。
disable-model-invocation: true
---

# DevSimple 布局 XML 默认 ViewTheme 样式

源码参考：[DevUtils/lib/DevSimple](https://github.com/afkT/DevUtils/tree/master/lib/DevSimple)（本地优先查 `lib/DevSimple/src/main/res/values/`）。

## 何时应用

- 新建或编辑 `res/layout/**/*.xml` 中的控件，需要统一默认宽高与主题习惯时。
- 用户要求为 View 补上 DevSimple / `ViewTheme` 的 `style` 时。

## 前置假设

- 模块已依赖 DevSimple，R 中可解析 `@style/ViewTheme.*`。
- 若仓库内存在 `lib/DevSimple`，**以仓库内 XML 为准**核对样式名；上游变更时只同步该目录下的定义。

## 核心规则（按顺序执行）

1. **已有 `style`/`android:theme`**：不要覆盖用户显式配置；除非用户明确要求替换。
2. **解析控件“语义类名”**（用于匹配 `ViewTheme` 名称）：
   - XML 标签为完整类名（如 `androidx.recyclerview.widget.RecyclerView`）时，取**简单类名**（`RecyclerView`）。
   - 对 **AppCompat / Material 控件**：沿 Java/Kotlin 继承向**平台 Widget 类**回退，取**第一个在下方「根样式表」中出现的简单类名**作为基准。  
     例：`AppCompatEditText` → `EditText`（不用 `TextView`）；`AppCompatTextView` → `TextView`；`MaterialButton` → `Button`；`androidx.appcompat.widget.Toolbar` → `Toolbar`。
3. **ImageView 特例**（用户约定）：`ImageView` 与 `androidx.appcompat.widget.AppCompatImageView` 一律使用  
   `style="@style/ViewTheme.ImageView.FIT_XY"`  
   （不使用 `ViewTheme.ImageView` 作为默认。）
4. **匹配样式**：若存在 `ViewTheme.<语义类名>` 根样式（与下方清单一致），则  
   `style="@style/ViewTheme.<语义类名>"`  
   （**特例 ImageView 已在上一步处理**。）
5. **ViewPager2**：未单独提供 `ViewTheme.ViewPager2`；库内约定与 `ViewPager` 共用族名，使用  
   `style="@style/ViewTheme.ViewPager"`。
6. **无匹配**：使用 `style="@style/ViewTheme.View"`。
7. **写法**：`style` 放在标签属性前列（与项目现有 XML 风格一致即可）。

## 继承链速查（写 XML 时脑内回退顺序）

从当前类沿父类向 `View` 方向查找，**命中下表「根样式表」中的类名即停止**（取命中项，再套用 ImageView 特例）。

| 常见 XML / 类 | 回退到（语义类名） |
|---------------|-------------------|
| AppCompatTextView | TextView |
| AppCompatEditText | EditText |
| AppCompatButton / MaterialButton | Button |
| AppCompatImageView | ImageView → 使用 **ViewTheme.ImageView.FIT_XY** |
| AppCompatCheckBox / CheckBox | 无对应根样式 → **ViewTheme.View** |
| AppCompatRadioButton / RadioButton | 无 → **ViewTheme.View** |
| Switch / SwitchCompat / MaterialSwitch | 无 → **ViewTheme.View** |
| ViewPager2 | ViewPager |
| com.google.android.material.card.MaterialCardView | MaterialCardView |
| com.google.android.material.tabs.TabLayout | TabLayout |
| com.lihang.ShadowLayout | ShadowLayout |
| androidx.coordinatorlayout.widget.CoordinatorLayout | CoordinatorLayout |
| androidx.swiperefreshlayout.widget.SwipeRefreshLayout | SwipeRefreshLayout |
| androidx.recyclerview.widget.RecyclerView | RecyclerView |
| androidx.core.widget.NestedScrollView | NestedScrollView |

## 根样式表（DevSimple `values` 扫描结果）

以下均为 `lib/DevSimple/src/main/res/values/` 中定义的 **`ViewTheme.<Name>` 控件向根样式**（可直接 `@style/ViewTheme.<Name>`，ImageView 默认除外见上）。

| 样式名 | 典型 XML 标签 / 说明 |
|--------|---------------------|
| ViewTheme.View | `View`、无专门主题的控件兜底 |
| ViewTheme.TextView | `TextView`、`AppCompatTextView` |
| ViewTheme.EditText | `EditText`、`AppCompatEditText` |
| ViewTheme.Button | `Button`、`AppCompatButton`、`MaterialButton` |
| ViewTheme.ImageView | 仅作父样式；**默认不用**，默认用 **ViewTheme.ImageView.FIT_XY** |
| ViewTheme.ImageView.FIT_XY | `ImageView`、`AppCompatImageView` 默认 |
| ViewTheme.RecyclerView | `RecyclerView` |
| ViewTheme.NestedScrollView | `NestedScrollView` |
| ViewTheme.ScrollView | `ScrollView` |
| ViewTheme.HorizontalScrollView | `HorizontalScrollView` |
| ViewTheme.ViewPager | `ViewPager`、`ViewPager2` |
| ViewTheme.WebView | `WebView` |
| ViewTheme.Toolbar | `Toolbar`、`androidx.appcompat.widget.Toolbar` |
| ViewTheme.TabLayout | `TabLayout` |
| ViewTheme.ProgressBar | `ProgressBar` |
| ViewTheme.SeekBar | `SeekBar` |
| ViewTheme.MaterialCardView | `MaterialCardView` |
| ViewTheme.ConstraintLayout | `ConstraintLayout` |
| ViewTheme.LinearLayout | `LinearLayout` |
| ViewTheme.FrameLayout | `FrameLayout` |
| ViewTheme.RelativeLayout | `RelativeLayout` |
| ViewTheme.GridLayout | `GridLayout` |
| ViewTheme.CoordinatorLayout | `CoordinatorLayout` |
| ViewTheme.AppBarLayout | `AppBarLayout` |
| ViewTheme.SwipeRefreshLayout | `SwipeRefreshLayout` |
| ViewTheme.ViewSwitcher | `ViewSwitcher` |
| ViewTheme.ViewFlipper | `ViewFlipper` |
| ViewTheme.ViewStub | `ViewStub` |
| ViewTheme.SurfaceView | `SurfaceView` |
| ViewTheme.TextureView | `TextureView` |
| ViewTheme.ShadowLayout | `com.lihang.ShadowLayout` |

## 非控件「工具向」根样式（一般不当作控件默认 style）

下列存在，但通常用于 **theme 组合或分割线/间距**，不要当作普通 Widget 的默认 `style` 替代 `ViewTheme.View`：

- `ViewTheme`：`wrap_content` / `wrap_content` 基类。
- `ViewTheme.Divider` 及 `ViewTheme.Divider.Vertical` / `Horizontal` / `*.DP_*`（`divider_styles.xml`）。
- `ViewTheme.Padding`、`ViewTheme.Margin` 及大量 `*.TB_DP_*`、`*.LR_DP_*` 等（`padding_margin_styles.xml`）。

分割线 `View` 若需库内语义，按方向选用 `ViewTheme.Divider.Vertical` 或 `ViewTheme.Divider.Horizontal` 及子样式，**不属于**「未匹配则用 ViewTheme.View」的替代项，由布局语义决定。

## 子样式命名规律（需要 Match / Gravity / 尺寸时）

在根样式之上，各控件文件普遍提供（不必背全表，按需拼接）：

- `ViewTheme.<Widget>.Match` | `.Match.Width` | `.Match.Height`
- `ViewTheme.<Widget>.Margin` / `Padding`（空父样式，用于再嵌套子 style）
- `ViewTheme.<Widget>.Gravity.<CENTER|LEFT|…>` 与 `ViewTheme.<Widget>.Gravity.Layout.<…>`
- **ImageView**：`ViewTheme.ImageView.CENTER_CROP`、`FIT_CENTER`、`CENTER_INSIDE`、`FIT_START`、`FIT_END`、`MATRIX` 等（见 `image_view_styles.xml`）
- **EditText**：`Single`、`Match`、`Match.Width`、`Match.Height` 及整套 `Gravity` / `Gravity.Layout`（见 `edit_text_styles.xml`）
- **ViewStub**：另有 `ViewTheme.ViewStub.Zero` 等（见 `view_stub_styles.xml`）
- **View**（`styles.xml`）：`ViewTheme.View.Space`、`ViewTheme.View.Space.Weight` 等

## 示例

```xml
<androidx.core.widget.NestedScrollView
    style="@style/ViewTheme.NestedScrollView">

    <androidx.recyclerview.widget.RecyclerView
        style="@style/ViewTheme.RecyclerView" />

    <androidx.appcompat.widget.AppCompatImageView
        style="@style/ViewTheme.ImageView.FIT_XY"
        android:layout_width="match_parent"
        android:layout_height="120dp" />

    <androidx.appcompat.widget.AppCompatEditText
        style="@style/ViewTheme.EditText" />
</androidx.core.widget.NestedScrollView>
```

无库内主题的控件：

```xml
<androidx.appcompat.widget.SwitchCompat
    style="@style/ViewTheme.View"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

## 维护说明

新增控件主题时，DevSimple 通常在 `lib/DevSimple/src/main/res/values/` 增加 `*_styles.xml`；Agent 应 **grep** `name="ViewTheme.` 更新本 skill 的「根样式表」与速查表，避免幻觉引用。
