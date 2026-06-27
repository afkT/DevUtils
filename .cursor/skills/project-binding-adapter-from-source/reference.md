# BindingAdapter 生成 — 参考

与 [SKILL.md](SKILL.md) 配套：模块路径、**优先范例文件表**、判定速查、XML 示例与 `binding_*` 前缀。流程与规则见 SKILL。

## 模块路径

| 符号 | 当前值 |
|------|--------|
| `DEVSIMPLE_ROOT` | `lib/DevSimple` |
| `BINDING_VIEW_DIR` | `{DEVSIMPLE_ROOT}/src/main/java/dev/simple/bindingadapters/view` |
| `BINDING_ATTR_DIR` | `{BINDING_VIEW_DIR}/attribute` |
| `BINDING_EXT` | `{DEVSIMPLE_ROOT}/src/main/java/dev/simple/bindingadapters/BindingAdaptersExt.kt` |

新增范例文件后，更新下表「主题」列即可，**勿**在 SKILL.md 中再堆完整路径列表。

## 优先范例文件（按主题选读）

生成前应 **Read** `{BINDING_VIEW_DIR}` 下与任务相关的文件；下表为常用对照入口（文件名相对于 `BINDING_VIEW_DIR`）。

| 文件 | 对照主题 |
|------|----------|
| `View.kt` | 不适合 BindingAdapter 的 API 边界说明；通用 View 适配器 |
| `ViewScroll.kt` | `Long?.qualifiesScroll()`；`XYI` 合并位移、`binding_scroll_*` |
| `ViewScrollDelayed.kt` | 延迟滚动触发 |
| `ViewScrollDelayAssist.kt` | 延迟二次执行辅助 |
| `TextView.kt` | `binding_tv_*`；**Boolean 三态** `bindingTVUnderline` / `bindingTVStrikeThru` |
| `EditText.kt` | `binding_et_*` |
| `ImageViewLoadEngine.kt` | `binding_image_*`（引擎加载） |
| `ImageViewLoadNative.kt` | `binding_image_native_*`；`try/catch` 与日志风格 |
| `ViewVisibility.kt` | 可见性相关属性 |
| `RecyclerView.kt` | `RecyclerView` 绑定 |

**attribute 目录**（`{BINDING_ATTR_DIR}`）：

| 文件 | 对照主题 |
|------|----------|
| `XYI.kt` | 开放类 / 伴生常量、`KEEP_SCROLL`、`equals`/`hashCode`、工厂方法 |
| `EtAttrs.kt`、`TVAttrs.kt`、`ShapeAttrs.kt` 等 | 各控件合并参数实体命名习惯 |

目录内另有 `ViewState*`、`ViewPadding.kt` 等，按控件语义选读，不必全表背入 SKILL。

## 判定速查

| 特征 | 建议 |
|------|------|
| 方法需要 Context 且非 View 自带 `getContext()` 够用 | 一般仍可做，避免要求 Activity 子类 |
| 需要 inflater / 新建整棵子树 | 不做 BindingAdapter，改在代码里 |
| 仅 `(View)` 或仅接收者，且为「事件」类（滚到底、清空、请求焦点一次） | `timestamp: Long?` + `qualifies*` |
| 有成对 **set / remove**，bool 表 **效果开/关状态**（下划线、删除线等） | **`Boolean?` 三态**：`null` 不改，`true` → `set*`，`false` → `remove*`；参照 `bindingTVUnderline` |
| 仅 `(View, boolean)` 且 bool 表「是否执行一次」、无 remove | `Boolean?` 仅 `true` 执行；同值不刷新 → 多次触发改时间戳 |
| 两个 Int（dx, dy）、xy、宽高对 | 合并为 `attribute` 下实体，单属性绑定 |
| 与滚动、延迟二次对齐相关 | 对照 `ViewScroll.kt` + `ViewScrollDelayed.kt` / `ViewScrollDelayAssist.kt` 是否需套一层 |

## XML 侧示例（时间戳）

```xml
app:binding_scroll_event_smooth_bottom="@{viewModel.scrollBottomTrigger}"
```

ViewModel 在需要滚到底时执行：`scrollBottomTrigger.set(System.currentTimeMillis())`（或 `set(n + 1)` 等保证为正且变化）。

## XML 侧示例（合并实体）

```xml
app:binding_scroll_instant_rel_xy="@{viewModel.relScroll}"
```

`relScroll` 为 `MutableLiveData<XYI>` 或单次命令 `XYI`；为 `null` 时不滚动。

## XML 侧示例（Boolean 三态开关）

```xml
app:binding_tv_underline="@{viewModel.showUnderline}"
app:binding_tv_strike_thru="@{viewModel.showStrikeThru}"
```

ViewModel 中 `showUnderline` / `showStrikeThru` 为 `Boolean` 或 `LiveData<Boolean>`：`true` 显示装饰线，`false` 移除；未绑定或表达式为 `null` 时不改当前 `paintFlags`。可选 `app:binding_tv_underline_anti_alias` 仅在开启下划线时生效。

适配器骨架（与 `TextView.kt` 一致）：

```kotlin
if (underline == null) return
if (underline) {
    TextViewUtils.setUnderlineText(this, antiAlias ?: true)
} else {
    TextViewUtils.removeUnderlineText(this)
}
```

## 与 code-method-normalize 的分工

- **本 Skill**：要不要 adapter、几个属性、是否 `XYI`、是否 `Long?`、是否拆文件。
- **normalize Skill**：首段无反引号/`[Type]`（Kotlin）、`<pre>` 里写布局属性名与对应工具方法、`@param` 不写 Kotlin 类型重复、`@return` `` `true` ``/`` `false` ``。

## DevSimple 前缀惯例（生成时遵循现有文件）

| 区域 | XML 前缀示例 |
|------|----------------|
| 通用 View 属性 | `binding_view_*` |
| 可见性 | `binding_visibility` / `binding_visibleOrGone` |
| TextView | `binding_tv_*` |
| EditText | `binding_et_*` |
| 图片（引擎） | `binding_image_*` |
| 图片（原生 API） | `binding_image_native_*` |
| 滚动 | `binding_scroll_*` |

新文件若属同一模块，应用 **最接近语义** 的前缀，避免与上表冲突。
新增前在 `{BINDING_VIEW_DIR}` 内检索 `binding_` 避免冲突。

## 维护本 reference

DevSimple 新增 `bindingadapters/view/*.kt` 时：

```bash
ls lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/*.kt
ls lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/attribute/*.kt
```

将新文件按上表格式补入 **§优先范例**；SKILL 正文保持流程，不恢复完整路径列表。
