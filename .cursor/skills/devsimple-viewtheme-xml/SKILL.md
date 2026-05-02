---
name: devsimple-viewtheme-xml
description: >-
  在布局 XML 中按 DevSimple 的 ViewTheme 约定补全 style。先检查模块是否依赖 DevSimple
  （如 io.github.afkt:DevSimple 或 project 引入）；无依赖则不添加任何 ViewTheme style。
  用于编写或修改 layout、用户要求 DevSimple / ViewTheme 默认样式、或统一基础尺寸时。
---

# DevSimple 布局默认 ViewTheme 样式

## 前置条件（必做）

**仅当当前正在编辑的 Android 模块已依赖 DevSimple 时**，才执行本 skill 的「补 `style="@style/ViewTheme.…"`」逻辑。

1. 在该模块所属的 **`build.gradle` / `build.gradle.kts`**（及工程里通过 `apply from` 引入的 deps 脚本）中检索是否出现 DevSimple，例如：
   - Maven：`io.github.afkt:DevSimple`（版本可变，如 `1.0.6`）
   - 本地工程：`project(':DevSimple')`、`project(":lib:DevSimple")` 等与 DevSimple 模块相关的 `project(...)`
2. **未找到任何 DevSimple 依赖**：**不要**为布局中的 View 添加 `style="@style/ViewTheme.…"`，也无需引用本 skill 的后续规则；可正常写布局其它内容。
3. **已找到依赖**：再按下面章节选择样式并写入。

本 skill 可在 **任意仓库** 使用；DevSimple 既可能是 **Maven 依赖**，也可能是 **本仓库内的 `DevSimple` 子模块**（源码根目录下常见 `DevSimple/src/...`，模块根之前的父路径以工程为准）——**是否走本逻辑只以 Gradle 里是否存在上述依赖为准**，不以「仓库里是否检出 DevSimple 源码树」为准。

## `ViewTheme.*` 从哪来、如何核对

- **通过 `io.github.afkt:DevSimple:…` 依赖时**：`ViewTheme.*` 随 AAR 资源打包进应用，一般 **无法在宿主仓库里直接打开** `values` 文件；以 **DevSimple 发布版本文档或本 skill 所列基样式名为准**。若用户指定了版本且需要 100% 核对，可让用户打开依赖缓存中的 `res/values` 或对照该版本的源码仓库。
- **本仓库含 DevSimple 源码时**（例如 `DevSimple/src/main/res/values`，模块根目录名以工程为准）：可用 **Grep / Read** 在该目录下确认 `name="ViewTheme.…"` 是否存在，再写入布局。

## 默认写入规则（仅在有 DevSimple 依赖时）

1. **不要覆盖** 已有 `style`（除非用户明确要求替换）。
2. 在主题/AppCompat 已按项目要求配置的前提下，为需要统一尺寸的 View 增加 `style="@style/ViewTheme.…"`。
3. **解析用「类名」**：以布局标签对应类的 **继承链** 为准，从当前类向父类走到 `android.view.View`，取链上 **第一个** 能与下面「库中已有基样式」列表对齐的简单类名（最近、最具体）。
   - 例：`AppCompatEditText` → … → `EditText`：用 **`@style/ViewTheme.EditText`**，不用 `ViewTheme.TextView`。
   - 例：`AppCompatImageView` → … → `ImageView`：见下节。

## ImageView 特例

继承链上语义基类为 **`ImageView`**（含 `AppCompatImageView` 等）时，默认：

`style="@style/ViewTheme.ImageView.FIT_XY"`

**只有** ImageView 系使用 **`FIT_XY`**；其它控件不要用 `FIT_XY`。

## 非 ImageView

若命中基类为 `RecyclerView`、`FrameLayout` 等，使用：

`style="@style/ViewTheme.<简单类名>"`

（与 DevSimple 里根级 `ViewTheme.XXX` 命名一致。）

## 未找到 `ViewTheme.<类名>` 时

`style="@style/ViewTheme.View"`

## 库中已有基样式（`ViewTheme.<简单类名>`）

AppBarLayout、Button、ConstraintLayout、CoordinatorLayout、Divider、EditText、FrameLayout、GridLayout、HorizontalScrollView、ImageView、LinearLayout、MaterialCardView、NestedScrollView、ProgressBar、RecyclerView、RelativeLayout、ScrollView、SeekBar、SurfaceView、SwipeRefreshLayout、TabLayout、TextView、TextureView、Toolbar、ViewFlipper、ViewPager、ViewStub、ViewSwitcher、WebView。

**回退**：其它 `View` 子类 → `@style/ViewTheme.View`。（ImageView 系仍用 **`ViewTheme.ImageView.FIT_XY`**。）

## 执行步骤（给 Agent）

1. **Gradle**：确认当前 module 是否依赖 DevSimple；**无** → **停止**，不添加 ViewTheme `style`。
2. 确定标签类的继承链。
3. 自子向父匹配 **「库中已有基样式」**；`ImageView` → `ViewTheme.ImageView.FIT_XY`，否则 `ViewTheme.<类名>`，否则 `ViewTheme.View`。
4. **可选校验**：若仓库存在 `DevSimple/.../res/values`（从 DevSimple 模块源码根向下找 `src/main/res/values`），再 Grep 确认 `name="…"`；纯 Maven 依赖则跳过文件 Grep，直接按约定写入。
5. 写入 `style`，保持项目 XML 风格。

## 示例

- `AppCompatImageView` → `@style/ViewTheme.ImageView.FIT_XY`
- `RecyclerView` → `@style/ViewTheme.RecyclerView`
- `FrameLayout` → `@style/ViewTheme.FrameLayout`
- `AppCompatEditText` → `@style/ViewTheme.EditText`
- 自定义 `MyView` 无对应样式 → `@style/ViewTheme.View`

子样式（如 `ViewTheme.TextView.Gravity.CENTER`）仅在用户明确要求时使用；**本 skill 的默认**仅指上述基样式、ImageView 的 `FIT_XY`、或回退 `ViewTheme.View`。
