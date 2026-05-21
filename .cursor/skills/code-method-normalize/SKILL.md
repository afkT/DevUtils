---
name: code-method-normalize
description: >-
    规范化新生成或改写的 Java/Kotlin 方法：Java 用 JavaDoc、Kotlin 用 KDoc，两套规则不混用；
    Java 入参 final（抽象方法、接口默认方法、@Override 实现除外一律不加）；
    方法注释与备注同一套结构：首段/首行无内联代码与类型引用，补充说明用 <pre> 包裹；
    有返回值且带参时补全 @param/@return；Kotlin 的 @param 行不写 [类型]（签名已标明）；boolean 的 @return：Java 用 {@code true}/{@code false}，Kotlin 用 `true`/`false`；
    类型引用：Java {@link …}，Kotlin @return/<pre> 等用 […]；优先返回入参或有语义的对象替代无意义 void；异常在方法内捕获并安全返回。
    在用户要求规范化方法、统一工具类写法、整理 Javadoc/KDoc、或按 DevUtils 方法风格处理时使用。
---

# Java / Kotlin：生成方法的规范化

对 **新生成或改写** 的方法按下列规则整理；**仅改与规范化相关的部分**，不扩大重构范围。

## 0. 文档风格：JavaDoc vs KDoc（必须分语言）

| 语言 | 文档块语法 | 说明 |
|------|-------------|------|
| **Java** | `/** … */` **JavaDoc** | `{@link …}`、`{@code …}` 等 JavaDoc 内联标签。 |
| **Kotlin** | `/** … */` **KDoc** | 内联代码用 **Markdown 反引号** `` `…` ``；类型/符号引用用 **`[…]`**（见下文「类与符号引用」）。**禁止**在 Kotlin 里写 JavaDoc 式的 `{@link …}` / `{@code …}`。 |

同一文件内若同时存在 Java 与 Kotlin，**各自文件内**严格对应上表，不把 JavaDoc 标签抄进 KDoc。

## 1. 语言差异：入参 `final`（仅 Java）

- **Java**：**普通**（非下列例外）方法形参必须加 **`final`**。
- **例外（一律不加 `final`）**：
    - **`abstract`** 抽象方法（含抽象类或接口中 **无方法体** 的抽象声明）的形参；
    - **`interface` 中的默认方法**（`default`）的形参；
    - 标注 **`@Override`** 的实现方法（覆盖父类 / 实现接口）的形参。
- **Kotlin**：**暂不**要求为参数加等价约束（按语言惯例即可）。

## 2. `@param` / `@return`（JavaDoc 与 KDoc 均适用块标签）

- 当方法 **既有非 `void` / 非 `Unit` 的返回类型**，又 **至少有一个入参** 时，必须写 **`@param`**（每个入参一条）与 **`@return`**。
- **`void` / `Unit`**：若无返回语义，见下文第 6 节；若保留且无返回值可描述，可不写 `@return`（以项目既有风格为准时跟随项目）。

### 2.1 `@param` / `@return` 行里的类型标注（按语言）

- **Java（JavaDoc）**：`@param intent {@link Intent}`、`@return {@link Intent}` 等，用 **`{@link 完全限定或简单名}`**（与项目导入/可读性一致即可）。
- **Kotlin（KDoc）**：
    - **`@param`**：**不要**在参数名后再写 **`[类型]`**；形参类型已在方法签名中，重复标注冗余且易与「正文里对类型的语义说明」混淆。直接写参数语义说明即可。
        - **错误**：`@param timestamp [Long] 触发用时间戳，大于 0 时执行一次`
        - **正确**：`@param timestamp 触发用时间戳，大于 0 时执行一次`
    - **`@return`**（以及 `<pre>` 等允许符号链接处）：仍可用 **`[类型或符号名]`** 指向返回类型或相关 API，**不用** `{@link …}`。

## 3. 返回类型为 `Boolean` / `boolean` 时的 `@return`

两种语言都要求 **一句话里写清 true / false 各自含义**（可对举），但 **字面量标记方式不同**：

### 3.1 Java（JavaDoc）

```text
@return {@code true} XXXX, {@code false} XXX
```

- **默认**：`XXXX`、`XXX` 为简短中文说明，与业务语义一致。
- **不必强制改成中文**：若已符合上述形式，且两侧说明即可读、能区分语义（例如 `@return {@code true} success, {@code false} fail`、`@return {@code true} yes, {@code false} no` 等常见英文对举），**保留即可**。

### 3.2 Kotlin（KDoc）

```text
@return `true` XXXX, `false` XXX
```

- 使用 **Markdown 行内代码**（反引号）包裹 **`true`** / **`false`** 字面量，**不使用** `{@code true}`。
- 中英文对举规则与 Java 一节相同：新写默认中文；已对举且表意充分时可保留英文。

## 4. 类与符号引用（补充说明、正文叙述）

| 场景 | Java（JavaDoc） | Kotlin（KDoc） |
|------|-----------------|----------------|
| 指向类、成员、常量等 | **`{@link 包.类#成员}`** 等标准 JavaDoc | **`[ClassName]`** 或 **`[package.ClassName]`** 等 KDoc 符号链接语法 |
| 行内代码片段 | **`{@code foo()}`** | **`` `foo()` ``**（反引号） |

**注意**：首段摘要中仍遵守第 5 节「禁止直接引用」；`{@link …}` / `` `[Type]` `` / `` `code` `` 等应放在 **`<pre>` 块** 或 **`@return` 行**（Kotlin 的 **`@param` 行不写 `[类型]`**，见第 2.1 节）等允许引用的位置，见下。

## 5. 方法注释 vs 方法备注（Java / Kotlin 同一套结构）

**方法注释**（KDoc/JavaDoc 的 **首段摘要**，通常首行）与 **方法备注**（其后的补充说明）共用下列规则：

### 5.1 首段 / 首行：方法注释（摘要）

- **只写方法功能用途**：一句话、**尽可能简短**；可写版本/前提（如「Android 16+：……」）。
- **禁止在首段直接写「代码形态」的引用**，包括但不限于：
    - **Java**：首段内不出现 **`{@…}`**（含 `{@code …}`、`{@link …}` 等）。
    - **Kotlin**：首段内不出现 **反引号代码** `` `…` ``，也不出现 **方括号符号链接** `[…]`（避免首行变成「标识符列表」而非摘要）。
- 首段为 **纯叙述**：说明「是什么 / 干什么」，不夹带可点击或可拷贝的 API 片段。

### 5.2 方法备注（首段之后的说明块）

- 需要补充的场景、风险、滥用说明、API 名、**类/方法引用**、文档链接提示等，**全部放在首段之后**。
- **Java 与 Kotlin 均使用** **`<pre> … </pre>`** 包裹整块备注。
- **`<pre>` 内允许**：
    - **Java**：`{@code startActivity}`、`{@link Intent}` 等；
    - **Kotlin**：`` `startActivity` ``、`[Intent]`、`[IntentFilter]` 等 KDoc 写法。
- 与首段分工：**首段 = 是什么/干什么；`<pre>` = 细节、约束、引用、注意事项。**

### 5.3 参考结构

**Java：**

```java
/**
 * Android 16+：关闭系统对 Intent 重定向的启动侧加固
 * <pre>
 *     极少数合法嵌套 {@code startActivity} 场景
 *     滥用会增大安全风险，仅当确有需要且已评估后再调用；详见官方「Intent 重定向」说明。
 * </pre>
 * @param intent {@link Intent}
 * @return {@link Intent}
 */
@RequiresApi(Build.VERSION_CODES.BAKLAVA)
public static Intent removeLaunchSecurityProtection(final Intent intent) {
    if (intent != null) {
        intent.removeLaunchSecurityProtection();
    }
    return intent;
}
```

**Kotlin（对照 KDoc 写法）：**

```kotlin
/**
 * Android 16+：关闭系统对 Intent 重定向的启动侧加固
 * <pre>
 *     极少数合法嵌套 `startActivity` 场景
 *     滥用会增大安全风险，仅当确有需要且已评估后再调用；详见官方「Intent 重定向」说明。
 * </pre>
 * @param intent 待处理的 Intent
 * @return [Intent]
 */
@RequiresApi(Build.VERSION_CODES.BAKLAVA)
fun removeLaunchSecurityProtection(intent: Intent?): Intent? {
    intent?.removeLaunchSecurityProtection()
    return intent
}
```

要点：**首行无 `{@}`（Java）或无 `` `…` `` / `[…]`（Kotlin）**；**`<pre>` 内** 再放具体 API/类型引用；**`@param` / `@return`** 行按第 2、3 节区分 JavaDoc / KDoc（Kotlin 的 **`@param` 不写 `[类型]`**）。

## 6. 返回值与 `void` / `Unit`

- **非必要不写 `void` / `Unit`**：若方法本质是「处理入参并供链式/复用」，优先 **返回有意义的值**；即使「没有额外返回语义」，也可 **返回入参对象**（如上的 `return intent`）或项目约定的空值（如 `null`），便于调用方连续书写。
- **仅在**确实无返回值且不宜伪造返回语义时，再使用 `void` / `Unit`。

## 7. 异常与崩溃

- **不得**因工具方法未捕获异常而导致主进程 **未处理崩溃**。
- 可能抛出受检/运行时的逻辑：**在方法内 `try/catch`**，记录日志（若项目已有工具类则沿用），**返回安全默认值**（如 `null`、false、原入参、空集合等，与语义一致即可）。
- **避免**将「易在调用方忘记处理」的异常通过 **`throws`** 层层上抛至未捕获崩溃；宁可 **在方法边界消化并返回异常语义下的安全值**。

## 8. 执行清单（改写方法时自测）

- [ ] **Java → JavaDoc、Kotlin → KDoc**，未混用 `{@link}` / `` `code` `` 规则。
- [ ] Java 形参均已 `final`（抽象方法、`interface default`、`@Override` 方法除外不加）。
- [ ] 有非 `void` / 非 `Unit` 返回值且有参：`@param` / `@return` 齐全。
- [ ] **`boolean` / `Boolean`**：`@return` 分支说明齐全——**Java** `{@code true}` / `{@code false}`；**Kotlin** `` `true` `` / `` `false` ``。
- [ ] **首段**无禁止引用（Java 无 `{@}`；Kotlin 无首段 `` `…` `` / `[…]`）；补充说明在 **`<pre>`** 内。
- [ ] **类型引用**：Java 用 `{@link …}`；Kotlin 在 **`@return` 行与 `<pre>` 内** 等可用 `[…]`；**`@param` 行不写 `[类型]`**（见第 2.1 节）。
- [ ] 能用返回入参/有意义值替代的，避免无意义 `void` / 无返回语义的 `Unit`。
- [ ] 危险调用已 try/catch，不依赖未捕获异常传播。
