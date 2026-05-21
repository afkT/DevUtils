---
name: binding-adapter-from-source
description: >-
  根据用户给出的 Java/Kotlin 工具类或 View 相关源码，设计并实现 androidx.databinding.BindingAdapter。
  工作区内优先 Read lib/DevSimple 下 bindingadapters；范例文件表见 reference.md。
  过滤不适合 XML 的 API；Long? 时间戳、Boolean? 三态、attribute 合并（参照 XYI）；成稿按 code-method-normalize。
  在用户要求生成 BindingAdapter、补全 DataBinding 属性、或评审 DevSimple bindingadapters 时使用。
---

# 从源码生成 DataBinding BindingAdapter

## 模块路径（本地优先，与 viewtheme 共用锚点）

| 符号 | 当前值（DevUtils） |
|------|-------------------|
| `DEVSIMPLE_ROOT` | `lib/DevSimple` |
| `BINDING_VIEW_DIR` | `{DEVSIMPLE_ROOT}/src/main/java/dev/simple/bindingadapters/view` |
| `BINDING_ATTR_DIR` | `{DEVSIMPLE_ROOT}/src/main/java/dev/simple/bindingadapters/view/attribute` |

存在 `{DEVSIMPLE_ROOT}` 时 **Read 本地 `*.kt`**，勿用 WebFetch 代替。生成前在 `{BINDING_VIEW_DIR}` 下列出并对照现有实现；**优先范例文件名、前缀惯例、判定表** → [reference.md](reference.md)。

**上游 fallback**（仅工作区无模块时）：https://github.com/afkT/DevUtils/tree/master/lib/DevSimple

## 必读搭配

生成或改写 **方法体与文档** 时，**同时 Read 并遵守**：

- [code-method-normalize/SKILL.md](../code-method-normalize/SKILL.md)（JavaDoc/KDoc、`<pre>`、首段规则、`@param`/`@return`、`boolean` 对举、异常内消化等）

本 Skill 负责 **「是否该做 BindingAdapter、属性如何设计、时间戳与合并参数」**；文档句式交给 **code-method-normalize**。

## 风格对齐（执行要点）

1. **Read** `{BINDING_VIEW_DIR}` 下与目标控件相关的 `*.kt`（至少一份同族文件 + [reference.md §优先范例](reference.md#优先范例文件按主题选读) 中对应行）。
2. 合并参数实体放在 `{BINDING_ATTR_DIR}`；`XYI` 等写法对照同目录 `XYI.kt`。
3. 文件头「不适合 BindingAdapter」的说明以 **`View.kt`** 为准（路径见 reference）。

---

## 1. 核心门槛：方法是否适合 BindingAdapter + XML

BindingAdapter 绑定在 **布局里的单个 View 节点**；只封装 **对该接收者 View（及合理子类型）有意义、且能在 XML 表达参数** 的操作。

**应跳过或不要求用户封装**（与 `View.kt` 文件头说明一致）的典型类别：

- 需要 **LayoutInflater / 非当前节点上下文** 的：`inflate` 等
- 返回 **Activity / Fragment / 任意非 View** 且无法在绑定中作为稳定入参的：`getActivity` 等
- 依赖 **任意子下标访问**、且语义不属于「当前节点自身配置」的：`getChildAt` 等（除非业务明确绑定的是容器且参数是 index，再按列表类控件单独设计）
- **ViewGroup 结构转换** 等与当前节点单次属性语义不符的：`convertViewGroup` 等

**适合封装的正向例子**：`removeAllViews`、`requestLayout`、`setText`、滚动到某位置、设置可见性、设置图片资源等——参数为基本类型、`String`、Drawable、`XYI` 等可在 Binding / LiveData 中传递的类型。

若某方法 **仅 `View`/`TextView`/… 一个参数**，除「纯状态同步」（如设置某个由 XML 已绑定的 bool）外，**重复触发**（同值不刷新）往往成问题 → 见第 2 节 **时间戳**。

---

## 2. 仅 View（接收者）型副作用：`Long?` 时间戳触发

当底层 API 形如 `FooUtils.action(view)` **仅有当前接收者、无业务参数**，又需要在 MVVM 里 **多次触发同一逻辑**（例如再次滑到底、再次清空）时：

- **唯一绑定入参** 使用 `timestamp: Long?`（或与其它参数组合时，时间戳作为触发列之一）
- 在适配器开头使用 **与项目一致的正时间戳判断**（见第 3 节）
- 在 KDoc `<pre>` 中写明：建议绑定 `System.currentTimeMillis()` 或 ViewModel 内递增时间戳；**优于** `LiveData<Boolean>` 固定 `true` 不二次刷新

**成对语义（示例）**：已有 `EditTextUtils.setText(view, text)` 对应「设文本」；可另增 **「清空文本」** 适配器，入参仅为 `timestamp: Long?`，内部在通过校验后调用 `EditTextUtils.setText(view, "")`（或项目等价 API）。文档中说明与 `binding_et_text` 等的关系，避免重复绑定冲突。

---

## 2.5 Boolean 三态开关（开启 / 关闭 / 不修改）

当底层工具类提供 **成对的 set / remove**（或语义等价的 enable / disable）API，且布局需要绑定 **可切换、可保持的 View 效果**（下划线、删除线、`paintFlags` 装饰、部分可见性样式等）时：

- 绑定入参使用 **`Boolean?`**（不用非空 `Boolean`，以便区分「未绑定」与「显式关闭」）。
- 适配器内统一约定：
  - **`null`** → `return`，**不修改** View（未提供 LiveData、或尚未赋值时保留 XML / 主题默认）。
  - **`true`** → 调用 **开启 / 设置** API（如 `TextViewUtils.setUnderlineText`）。
  - **`false`** → 调用 **关闭 / 移除** API（如 `TextViewUtils.removeUnderlineText`）。

与 **§2 时间戳** 的分工：

| 场景 | 选用 |
|------|------|
| 需要 **同步并保持** 开/关状态，随 DataBinding 刷新更新 UI | **`Boolean?` 三态**（本节） |
| **无状态**，每次都要 **再触发一次** 副作用（再次滚到底、再次清空） | **`Long?` 时间戳**（§2） |
| 工具类 **仅有 set、无 remove**，且 `false` 语义就是「不做事」 | 可 `Boolean?` 仅 `true` 时执行；若业务要在布局里 **关掉** 已有效果，应补 remove 工具方法后改本节三态 |

**禁止**：底层已有 `removeXxx` 时仍写 `if (flag != true) return`，导致布局绑 `false` 无法撤销效果。

**仓库参考实现**：`TextView.bindingTVUnderline`、`TextView.bindingTVStrikeThru`（见 `{BINDING_VIEW_DIR}/TextView.kt`，reference §优先范例）。

```kotlin
fun TextView.bindingTVUnderline(
    underline: Boolean?,
    antiAlias: Boolean?,
) {
    if (underline == null) return
    if (underline) {
        TextViewUtils.setUnderlineText(this, antiAlias ?: true)
    } else {
        TextViewUtils.removeUnderlineText(this)
    }
}
```

- 可选第二参数（如 `antiAlias`）仅在 **`true` 开启分支** 使用；`requireAll = false` 时缺省与工具类一致。
- KDoc `<pre>` 中写明三态与对应的 `set*` / `remove*` 工具方法名。

---

## 3. 时间戳判定：可复用扩展

逻辑统一为：**非 null 且大于 0** 才执行。

本仓库已有：

```kotlin
fun Long?.qualifiesScroll(): Boolean = this != null && this > 0L
```

**新代码**若语义不是滚动（避免命名误导），可在同一模块抽取例如：

```kotlin
/** 数据绑定用：时间戳非空且大于 0 时执行一次副作用。 */
fun Long?.qualifiesBindingAction(): Boolean = this != null && this > 0L
```

具体命名与存放文件随模块惯例；**禁止**用 `<=0` 或 `null` 仍执行（除非项目已有特例并在备注中写明）。

---

## 4. 多参数合并为实体类（减少绑定次数与属性数量）

**避免** 多个密切相关、应同事务触发的量拆成多个 `binding_*`，导致 **多次调用适配器**、LiveData 数量膨胀。

做法：

1. 在 `dev.simple.bindingadapters.view.attribute`（即 `{BINDING_ATTR_DIR}`，或用户指定包的 `attribute` 子包）新增 **Kotlin data class / open class**，字段覆盖原工具方法参数（含可选默认值）。
2. **单个** `@BindingAdapter("binding_…_single_snake_name")`，入参为该类型可空；`null` 时跳过。
3. 命名与 **XYI** 对齐习惯：简短后缀表意（如位移用 `XYI`）；补充 `equals`/`hashCode`（data class 自带）、`toString`、伴生工厂方法视需要。
4. 迁移示例：由 `binding_scroll_instant_rel_dx` + `binding_scroll_instant_rel_dy`（`requireAll = false`）改为 **`binding_scroll_instant_rel_xy`** + `XYI?`（见用户提供的旧/新对照）。

---

## 5. 布局属性与 Kotlin 命名

- XML：`app:binding_snake_case`；`@BindingAdapter` 的 `value` 与之一致。
- Kotlin 扩展：`fun View.bindingCamelCase(...)`，与文件内现有风格一致。
- `requireAll = false` 仅当 **确有独立缺省语义** 时使用；能合并为实体类则优先合并。
- 有返回值时：优先 `Boolean` 表示是否执行/成功，并满足 **code-method-normalize** 中 `boolean`/`Boolean` 的 `@return` 写法。

---

## 6. 生成工作流（Agent 执行顺序）

1. **Read** 用户给出的源文件，列出候选 `public`/`internal` 方法。
2. **过滤** 第 1 节不适配 XML 的 API。
3. 对每个保留方法：**映射接收者类型**（`View` / `TextView` / `ImageView` / `ViewGroup` / `RecyclerView` 等）、**参数拆分或合并**（第 4 节）、**是否需要时间戳**（第 2 节）或 **Boolean 三态开关**（第 2.5 节）。
4. 落文件：新适配器写入 `{BINDING_VIEW_DIR}/`（按控件分文件，与现有 `TextView.kt`、`ViewScroll.kt` 等并列）；attribute 类写入 `{BINDING_ATTR_DIR}/`。
5. **Read** `code-method-normalize` 并整理文档与返回类型。
6. 自检：`@BindingAdapter` 不与 `{BINDING_VIEW_DIR}` 内已有属性名冲突；`try/catch` 与日志风格参照 reference 中 **TextView** / **ImageViewLoadNative** 范例。

判定表、XML 示例、`binding_*` 前缀表见 [reference.md](reference.md)。

## 执行清单

- [ ] 已 Read 本地 `{BINDING_VIEW_DIR}` 或 reference §优先范例，而非仅凭记忆写适配器。
- [ ] 已对照 reference §DevSimple 前缀惯例，避免 `binding_*` 冲突。
- [ ] 新 attribute 类落在 `{BINDING_ATTR_DIR}`；成稿前已 Read `code-method-normalize`。
