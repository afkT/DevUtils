# DevSimple ViewTheme — 参考

与 [SKILL.md](SKILL.md) 配套：**根样式表**、继承链速查、资源文件索引与扫描维护。流程与匹配算法见 SKILL 正文。

## 模块路径

| 符号 | 当前值（DevUtils） |
|------|-------------------|
| `DEVSIMPLE_ROOT` | `lib/DevSimple` |
| `DEVSIMPLE_VALUES` | `{DEVSIMPLE_ROOT}/src/main/res/values` |

**上游 fallback**（仅工作区无 `{DEVSIMPLE_ROOT}` 时）：https://github.com/afkT/DevUtils/tree/master/lib/DevSimple

## 资源文件索引（`DEVSIMPLE_VALUES`）

| 文件 | 主要内容 |
|------|----------|
| `styles.xml` | `ViewTheme`、`ViewTheme.View` 及 View 子样式 |
| `text_view_styles.xml` | `ViewTheme.TextView` |
| `edit_text_styles.xml` | `ViewTheme.EditText` |
| `button_styles.xml` | `ViewTheme.Button` |
| `image_view_styles.xml` | `ViewTheme.ImageView`、`ViewTheme.ImageView.FIT_XY` 等 |
| `recycler_view_styles.xml` | `ViewTheme.RecyclerView` |
| `nested_scroll_view_styles.xml` | `ViewTheme.NestedScrollView` |
| `scroll_view_styles.xml` | `ViewTheme.ScrollView` |
| `horizontal_scroll_view_styles.xml` | `ViewTheme.HorizontalScrollView` |
| `view_pager_styles.xml` | `ViewTheme.ViewPager` |
| `web_view_styles.xml` | `ViewTheme.WebView` |
| `toolbar_styles.xml` | `ViewTheme.Toolbar` |
| `tab_layout_styles.xml` | `ViewTheme.TabLayout` |
| `progress_bar_styles.xml` | `ViewTheme.ProgressBar` |
| `seek_bar_styles.xml` | `ViewTheme.SeekBar` |
| `material_card_view_styles.xml` | `ViewTheme.MaterialCardView` |
| `constraint_layout_styles.xml` | `ViewTheme.ConstraintLayout` |
| `linear_layout_styles.xml` | `ViewTheme.LinearLayout` |
| `frame_layout_styles.xml` | `ViewTheme.FrameLayout` |
| `relative_layout_styles.xml` | `ViewTheme.RelativeLayout` |
| `grid_layout_styles.xml` | `ViewTheme.GridLayout` |
| `coordinator_layout_styles.xml` | `ViewTheme.CoordinatorLayout` |
| `app_bar_layout_styles.xml` | `ViewTheme.AppBarLayout` |
| `swipe_refresh_layout_styles.xml` | `ViewTheme.SwipeRefreshLayout` |
| `view_switcher_styles.xml` | `ViewTheme.ViewSwitcher` |
| `view_flipper_styles.xml` | `ViewTheme.ViewFlipper` |
| `view_stub_styles.xml` | `ViewTheme.ViewStub` |
| `surface_view_styles.xml` | `ViewTheme.SurfaceView` |
| `texture_view_styles.xml` | `ViewTheme.TextureView` |
| `shadow_layout_styles.xml` | `ViewTheme.ShadowLayout` |
| `divider_styles.xml` | `ViewTheme.Divider` 族 |
| `padding_margin_styles.xml` | `ViewTheme.Padding`、`ViewTheme.Margin` 族 |

## 根样式表（控件向，可直接作默认 `style`）

以下样式在 `{DEVSIMPLE_VALUES}` 中定义为 **`<Widget>` 根主题**（`ViewTheme.<Widget>`，不含更深子后缀）。ImageView 默认用 **`ViewTheme.ImageView.FIT_XY`**，见 SKILL。

| 样式名 | 典型 XML 标签 / 说明 |
|--------|---------------------|
| `ViewTheme.View` | `View`、无专门主题的控件兜底 |
| `ViewTheme.TextView` | `TextView`、`AppCompatTextView` |
| `ViewTheme.EditText` | `EditText`、`AppCompatEditText` |
| `ViewTheme.Button` | `Button`、`AppCompatButton`、`MaterialButton` |
| `ViewTheme.ImageView` | 仅作父样式；**默认不用** |
| `ViewTheme.ImageView.FIT_XY` | `ImageView`、`AppCompatImageView` **默认** |
| `ViewTheme.RecyclerView` | `RecyclerView` |
| `ViewTheme.NestedScrollView` | `NestedScrollView` |
| `ViewTheme.ScrollView` | `ScrollView` |
| `ViewTheme.HorizontalScrollView` | `HorizontalScrollView` |
| `ViewTheme.ViewPager` | `ViewPager`、`ViewPager2` |
| `ViewTheme.WebView` | `WebView` |
| `ViewTheme.Toolbar` | `Toolbar`、`androidx.appcompat.widget.Toolbar` |
| `ViewTheme.TabLayout` | `TabLayout` |
| `ViewTheme.ProgressBar` | `ProgressBar` |
| `ViewTheme.SeekBar` | `SeekBar` |
| `ViewTheme.MaterialCardView` | `MaterialCardView` |
| `ViewTheme.ConstraintLayout` | `ConstraintLayout` |
| `ViewTheme.LinearLayout` | `LinearLayout` |
| `ViewTheme.FrameLayout` | `FrameLayout` |
| `ViewTheme.RelativeLayout` | `RelativeLayout` |
| `ViewTheme.GridLayout` | `GridLayout` |
| `ViewTheme.CoordinatorLayout` | `CoordinatorLayout` |
| `ViewTheme.AppBarLayout` | `AppBarLayout` |
| `ViewTheme.SwipeRefreshLayout` | `SwipeRefreshLayout` |
| `ViewTheme.ViewSwitcher` | `ViewSwitcher` |
| `ViewTheme.ViewFlipper` | `ViewFlipper` |
| `ViewTheme.ViewStub` | `ViewStub` |
| `ViewTheme.SurfaceView` | `SurfaceView` |
| `ViewTheme.TextureView` | `TextureView` |
| `ViewTheme.ShadowLayout` | `com.lihang.ShadowLayout` |

> **快照说明**：上表与仓库 `{DEVSIMPLE_VALUES}` 对齐；DevSimple 增删样式后须按下文「扫描与更新」刷新本表。

## 继承链速查（XML 标签 → 语义类名）

从当前类沿父类向 `View` 查找，**命中上表「样式名」中间段（如 `RecyclerView`）即停止**，再套用 SKILL 中 ImageView / ViewPager2 特例。

| 常见 XML / 类 | 回退到（语义类名） |
|---------------|-------------------|
| AppCompatTextView | TextView |
| AppCompatEditText | EditText |
| AppCompatButton / MaterialButton | Button |
| AppCompatImageView | ImageView → **ViewTheme.ImageView.FIT_XY** |
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

## 非控件「工具向」根样式

一般不当作普通 Widget 的默认 `style`（未匹配控件仍用 `ViewTheme.View`）：

| 样式族 | 定义文件 | 用途 |
|--------|----------|------|
| `ViewTheme` | `styles.xml` | `wrap_content` 基类 |
| `ViewTheme.Divider.*` | `divider_styles.xml` | 分割线方向与 dp 变体 |
| `ViewTheme.Padding.*`、`ViewTheme.Margin.*` | `padding_margin_styles.xml` | 间距组合 |

分割线 `View` 按布局语义选用 `ViewTheme.Divider.Vertical` / `Horizontal` 及子样式，**不**用「无匹配 → View」规则替代。

## 子样式命名规律（按需拼接）

在根样式之上，各 `*_styles.xml` 普遍提供（不必背全表）：

- `ViewTheme.<Widget>.Match` | `.Match.Width` | `.Match.Height`
- `ViewTheme.<Widget>.Margin` / `.Padding`（空父样式，用于嵌套）
- `ViewTheme.<Widget>.Gravity.<…>` 与 `ViewTheme.<Widget>.Gravity.Layout.<…>`
- **ImageView**：`CENTER_CROP`、`FIT_CENTER`、`CENTER_INSIDE`、`FIT_START`、`FIT_END`、`MATRIX` 等 → `image_view_styles.xml`
- **EditText**：`Single`、`Match`、全套 `Gravity` → `edit_text_styles.xml`
- **ViewStub**：`ViewTheme.ViewStub.Zero` 等 → `view_stub_styles.xml`
- **View**：`ViewTheme.View.Space`、`ViewTheme.View.Space.Weight` → `styles.xml`

## 扫描与更新本 reference

DevSimple 新增或重命名 `ViewTheme` 后，在仓库根执行（将路径换为实际 `DEVSIMPLE_VALUES`）：

```bash
# 列出所有 ViewTheme 样式名（用于核对根表与子样式）
grep -rh 'name="ViewTheme\.' lib/DevSimple/src/main/res/values/*.xml | sed 's/.*name="//;s/".*//' | sort -u

# 仅两段的根样式（ViewTheme.<Widget>）
grep -rh 'name="ViewTheme\.[^"]*"' lib/DevSimple/src/main/res/values/*.xml \
  | sed 's/.*name="//;s/".*//' | awk -F. 'NF==2' | sort -u
```

Agent 维护时应：

1. **Read** 本地 `{DEVSIMPLE_VALUES}` 与上表 diff；
2. 更新本文件 **§根样式表**、必要时 **§继承链速查**；
3. **勿**仅凭记忆或 GitHub WebFetch 增删样式名。

*本 reference 根样式表与 `{DEVSIMPLE_VALUES}` 两段式 `ViewTheme.<Widget>` 定义对齐；子样式以各 `*_styles.xml` 为准。*
