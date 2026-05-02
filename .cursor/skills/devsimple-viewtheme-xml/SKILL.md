---
name: devsimple-viewtheme-xml
description: >-
  在布局 XML 中为控件默认补全 DevSimple 的 ViewTheme 样式（lib/DevSimple/src/main/res/values）。
  在编写或修改 layout、item、merge 中的 View、用户提到 ViewTheme / DevSimple 默认样式、
  或需要统一 wrap_content/match_parent 等基础尺寸时使用。
---

# DevSimple 布局默认 ViewTheme 样式

## 样式来源

以仓库内 **`lib/DevSimple/src/main/res/values`** 下已定义的 `ViewTheme.*` 为准；新增布局前应用 **Read / Grep** 确认对应 `style name` 是否存在，避免引用不存在的资源。

## 默认写入规则

1. **不要覆盖** 已有 `style`（除非用户明确要求替换）。
2. 在根元素已声明 DevSimple/AppCompat 主题的前提下，为需要统一尺寸的 View 增加：
   - `style="@style/ViewTheme.…"`
3. **解析用「类名」**：以布局标签的 **Java/Kotlin 类继承链** 为准，从 **当前标签对应类** 向父类走到 `android.view.View`，取继承链上 **第一个** 在 `values` 中存在 **同名基样式** 的简单类名（最近、最具体的匹配）。
   - 例：`AppCompatEditText` → `EditText` → `TextView` → …：若存在 `ViewTheme.EditText`，则用 **`@style/ViewTheme.EditText`**（以 `EditText` 为语义基准，不用 `ViewTheme.TextView`）。
   - 例：`AppCompatImageView` → `ImageView` → …：基样式落在 **`ImageView`** 上。

## ImageView 特例

凡继承链上 **最先命中** 的、带独立 `ViewTheme` 的图像控件基类为 **`ImageView`**（含 `AppCompatImageView` 等），默认使用：

`style="@style/ViewTheme.ImageView.FIT_XY"`

**只有** 这一条走 **`ViewTheme.ImageView.FIT_XY`**（`scaleType` 为 `fitXY`）；**不要**对其他控件类型使用 `FIT_XY`。

## 非 ImageView：基样式名

若命中基类为 `RecyclerView`、`FrameLayout` 等，且存在对应 **`ViewTheme.<简单类名>`**（与 `styles.xml` 及各 `*_styles.xml` 中 **首个根级** `ViewTheme.XXX` 一致），则使用：

`style="@style/ViewTheme.RecyclerView"`、`style="@style/ViewTheme.FrameLayout"` 等。

## 未找到对应 `ViewTheme.<类名>` 时

使用通用回退：

`style="@style/ViewTheme.View"`

（定义见 `lib/DevSimple/src/main/res/values/styles.xml`。）

## 继承链速查（写 XML 时常用）

从标签类名向上查找，直到匹配到下方 **「库中已有基样式」** 中的简单类名；**ImageView 分支** 按上一节改用 `ViewTheme.ImageView.FIT_XY`。

## 库中已有基样式（`ViewTheme.<简单类名>` 单行根样式）

在以下简单类名上存在与控件语义对应的基样式时，用 **`@style/ViewTheme.<简单类名>`**（ImageView 仍用 **`ViewTheme.ImageView.FIT_XY`**）：

AppBarLayout、Button、ConstraintLayout、CoordinatorLayout、Divider、EditText、FrameLayout、GridLayout、HorizontalScrollView、ImageView、LinearLayout、MaterialCardView、NestedScrollView、ProgressBar、RecyclerView、RelativeLayout、ScrollView、SeekBar、SurfaceView、SwipeRefreshLayout、TabLayout、TextView、TextureView、Toolbar、ViewFlipper、ViewPager、ViewStub、ViewSwitcher、WebView。

**回退**：任意其他 `View` 子类 → `@style/ViewTheme.View`。

## 执行步骤（给 Agent）

1. 确定布局标签的 **完整类名** 或 **简单类名**，写出向 `View` 的继承链（可查 AndroidX / SDK 源码或文档）。
2. 自子向父扫描：第一个在 **「库中已有基样式」** 列表中的类名 → 非 `ImageView` 则 `ViewTheme.<该类名>`；为 `ImageView` → `ViewTheme.ImageView.FIT_XY`。
3. `Grep` `lib/DevSimple/src/main/res/values` 中 `name="ViewTheme.<该类名>"` 或 `FIT_XY`；若无则 **`ViewTheme.View`**。
4. 写入 `style="…"`，并保持项目现有 XML 格式与属性顺序习惯。

## 示例

- `androidx.appcompat.widget.AppCompatImageView` → `@style/ViewTheme.ImageView.FIT_XY`
- `androidx.recyclerview.widget.RecyclerView` → `@style/ViewTheme.RecyclerView`
- `android.widget.FrameLayout` → `@style/ViewTheme.FrameLayout`
- `androidx.appcompat.widget.AppCompatEditText` → `@style/ViewTheme.EditText`
- 自定义 `com.example.MyView` 且无 `ViewTheme.MyView` → `@style/ViewTheme.View`

更细的子样式（如 `ViewTheme.TextView.Gravity.CENTER`）仅在用户明确要求时使用；**本 skill 的「默认」仅指上述基样式或 ImageView 的 FIT_XY / 回退 View**。
