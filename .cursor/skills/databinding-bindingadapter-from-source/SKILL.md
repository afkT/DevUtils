---
name: databinding-bindingadapter-from-source
description: >-
  根据用户给出的 Java/Kotlin 工具类或 View 相关源码，设计并实现 androidx.databinding.BindingAdapter（含布局属性名、扩展函数签名、与 XML 的对应关系）。
  过滤不适合在布局单节点上表达的 API；对「仅 View 入参」的副作用用 Long? 时间戳触发；效果类开关用 Boolean? 三态（null 不改、true 开启、false 关闭，成对 set/remove）；多参数合并为 attribute 包下数据类（参照 XYI）；生成后按 java-kotlin-method-normalize 整理 KDoc/JavaDoc。
  在用户要求从某类生成 BindingAdapter、补全 DataBinding 自定义属性、把工具方法暴露到 XML、或评审/改写 DevSimple bindingadapters 时使用。
disable-model-invocation: true
---

# 从源码生成 DataBinding BindingAdapter

## 必读搭配

生成或改写 **方法体与文档** 时，**同时 Read 并遵守**：

- [java-kotlin-method-normalize/SKILL.md](../java-kotlin-method-normalize/SKILL.md)（JavaDoc/KDoc、`<pre>`、首段规则、`@param`/`@return`、`boolean` 对举、异常内消化等）

本 Skill 负责 **「是否该做 BindingAdapter、属性如何设计、时间戳与合并参数」**；文档句式交给 **java-kotlin-method-normalize**。

## 仓库内参考实现（风格对齐）

生成前应 **Read** 下列文件之一或多份，保持命名、注释结构、时间戳与实体类用法一致：

- `lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/View.kt`（适合 XML 的 API 边界说明）
- `lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScroll.kt`（`Long?.qualifiesScroll()`、`XYI` 合并位移）
- `lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScrollDelayed.kt`、`ViewScrollDelayAssist.kt`（延迟二次执行）
- `lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/View.kt`、`TextView.kt`、`EditTextView.kt`、`ImageView.kt`、`ImageViewNative.kt`

实体类目录与范例：

- `lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/attribute/`（合并参数的数据类放此处）
- `attribute/XYI.kt`（开放类、伴生常量如 `KEEP_SCROLL`、`equals`/`hashCode`、工厂方法等写法）

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

**仓库参考实现**：`TextView.bindingTVUnderline`、`TextView.bindingTVStrikeThru`（`lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/TextView.kt`）。

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

1. 在 `dev.simple.bindingadapters.attribute`（或用户指定包的 `attribute` 子包）新增 **Kotlin data class / open class**，字段覆盖原工具方法参数（含可选默认值）。
2. **单个** `@BindingAdapter("binding_…_single_snake_name")`，入参为该类型可空；`null` 时跳过。
3. 命名与 **XYI** 对齐习惯：简短后缀表意（如位移用 `XYI`）；补充 `equals`/`hashCode`（data class 自带）、`toString`、伴生工厂方法视需要。
4. 迁移示例：由 `binding_scroll_instant_rel_dx` + `binding_scroll_instant_rel_dy`（`requireAll = false`）改为 **`binding_scroll_instant_rel_xy`** + `XYI?`（见用户提供的旧/新对照）。

---

## 5. 布局属性与 Kotlin 命名

- XML：`app:binding_snake_case`；`@BindingAdapter` 的 `value` 与之一致。
- Kotlin 扩展：`fun View.bindingCamelCase(...)`，与文件内现有风格一致。
- `requireAll = false` 仅当 **确有独立缺省语义** 时使用；能合并为实体类则优先合并。
- 有返回值时：优先 `Boolean` 表示是否执行/成功，并满足 **java-kotlin-method-normalize** 中 `boolean`/`Boolean` 的 `@return` 写法。

---

## 6. 生成工作流（Agent 执行顺序）

1. **Read** 用户给出的源文件，列出候选 `public`/`internal` 方法。
2. **过滤** 第 1 节不适配 XML 的 API。
3. 对每个保留方法：**映射接收者类型**（`View` / `TextView` / `ImageView` / `ViewGroup` / `RecyclerView` 等）、**参数拆分或合并**（第 4 节）、**是否需要时间戳**（第 2 节）或 **Boolean 三态开关**（第 2.5 节）。
4. 落文件：与现有 `bindingadapters/view` 分包一致；新增 attribute 类放 `attribute/`。
5. **Read** `java-kotlin-method-normalize` 并整理文档与返回类型。
6. 自检：`@BindingAdapter` 不与同模块已有属性名冲突；危险调用 `try/catch` 与项目日志工具一致（参照 `TextView.kt`、`ImageViewNative.kt`）。

更细的判定表与 XML 示例见 [reference.md](reference.md)。
