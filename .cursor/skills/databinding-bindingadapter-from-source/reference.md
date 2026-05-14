# BindingAdapter 生成：补充参考

## 判定速查

| 特征 | 建议 |
|------|------|
| 方法需要 Context 且非 View 自带 `getContext()` 够用 | 一般仍可做，避免要求 Activity 子类 |
| 需要 inflater / 新建整棵子树 | 不做 BindingAdapter，改在代码里 |
| 仅 `(View)` 或仅接收者，且为「事件」类（滚到底、清空、请求焦点一次） | `timestamp: Long?` + `shouldTrigger*` |
| 仅 `(View, boolean)` 且 bool 表「是否执行」 | 可用 `Boolean?`，`true` 执行；注意 LiveData 同值不刷新 → 重要多次触发改用时间戳 |
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

## 与 java-kotlin-method-normalize 的分工

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
