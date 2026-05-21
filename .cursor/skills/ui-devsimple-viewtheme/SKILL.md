---
name: ui-devsimple-viewtheme
description: >-
  为布局 XML 中的控件默认补全 DevSimple（lib/DevSimple）的 ViewTheme 样式引用：
  按控件继承链匹配 ViewTheme.&lt;类名&gt;，ImageView/AppCompatImageView 使用
  ViewTheme.ImageView.FIT_XY，无匹配则用 ViewTheme.View。工作区内优先 Read
  lib/DevSimple；根样式清单见同目录 reference.md。在编写或批量整理 layout XML、
  引入 ViewTheme、迁移旧布局、或用户提到 DevSimple/ViewTheme 时使用。
---

# DevSimple 布局 XML 默认 ViewTheme 样式

## 模块路径（本地优先）

| 符号 | 当前值 |
|------|--------|
| `DEVSIMPLE_ROOT` | `lib/DevSimple` |
| `DEVSIMPLE_VALUES` | `{DEVSIMPLE_ROOT}/src/main/res/values` |

存在 `{DEVSIMPLE_ROOT}` 时 **Read 本地 XML**，勿用 WebFetch 代替。根样式全表、继承链、资源文件索引与 grep 维护步骤 → [reference.md](reference.md)。

## 何时应用

- 新建或编辑 `res/layout/**/*.xml` 中的控件，需要统一默认宽高与主题习惯时。
- 用户要求为 View 补上 DevSimple / `ViewTheme` 的 `style` 时。

## 前置假设

- 模块已依赖 DevSimple，R 中可解析 `@style/ViewTheme.*`。
- 样式名是否存在：以 [reference.md §根样式表](reference.md#根样式表控件向可直接作默认-style) 为准；若存疑或久未同步，在 `{DEVSIMPLE_VALUES}` **grep** `name="ViewTheme.` 后更新 reference（见 reference §扫描与更新）。

## 核心规则（按顺序执行）

1. **已有 `style`/`android:theme`**：不要覆盖用户显式配置；除非用户明确要求替换。
2. **解析控件「语义类名」**：
   - XML 为完整类名时取**简单类名**（如 `RecyclerView`）。
   - AppCompat / Material：沿继承向平台 Widget 回退，取**第一个在 reference 根样式表中出现的简单类名**。  
     例：`AppCompatEditText` → `EditText`；`MaterialButton` → `Button`。
3. **ImageView 特例**：`ImageView` / `AppCompatImageView` 一律  
   `style="@style/ViewTheme.ImageView.FIT_XY"`（不用 `ViewTheme.ImageView` 作默认）。
4. **匹配根样式**：若 reference 根样式表存在 `ViewTheme.<语义类名>`，则  
   `style="@style/ViewTheme.<语义类名>"`（ImageView 已按上一步处理）。
5. **ViewPager2**：无 `ViewTheme.ViewPager2`；使用 `style="@style/ViewTheme.ViewPager"`。
6. **无匹配**：`style="@style/ViewTheme.View"`。
7. **写法**：`style` 放在标签属性前列（与项目现有 XML 风格一致）。

继承链与常见标签对照 → [reference.md §继承链速查](reference.md#继承链速查xml-标签--语义类名)。需要 Match / Gravity / 子样式时 → [reference.md §子样式命名规律](reference.md#子样式命名规律按需拼接)。

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

## 执行清单

- [ ] 已 Read [reference.md](reference.md) 根样式表（或已 grep `{DEVSIMPLE_VALUES}` 核对）。
- [ ] 未覆盖用户已有 `style`（除非明确要求）。
- [ ] ImageView / ViewPager2 特例已套用。
- [ ] DevSimple 有新增 `ViewTheme` 时，按 reference §扫描与更新 同步 reference，勿只改 SKILL。
