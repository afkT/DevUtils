---
name: java-kotlin-method-normalize
description: >-
  规范化新生成或改写的 Java/Kotlin 方法：Java 入参 final（抽象方法、接口默认方法、@Override 实现除外一律不加）；
  Javadoc/KDoc 首段无 Java 式 `{@}`；补充说明 Java 用 `<pre>` 块、Kotlin 用段落或 Markdown 代码块；有返回值且带参时补全 @param/@return；
  boolean 的 @return：Java 用 {@code true}/{@code false}，Kotlin 用反引号 `true`/`false` 分支说明（默认中文；success/fail、yes/no 等已对举表意时可保留英文）；
  交叉引用：Java 用 {@link Type#member(...)}，Kotlin 用 KDoc 方括号 [Type.member]；优先返回入参或有语义的对象替代无意义 void；
  异常在方法内捕获并安全返回、避免未处理崩溃。
  在用户要求规范化方法、统一工具类写法、整理 Javadoc/KDoc、或按 DevUtils 方法风格处理时使用。
disable-model-invocation: true
---

# Java / Kotlin：生成方法的规范化

对 **新生成或改写** 的方法按下列规则整理；**仅改与规范化相关的部分**，不扩大重构范围。注释与 `@return` 等 **按语言区分**：**Java 用 Javadoc**，**Kotlin 用 KDoc**（语法不同，勿混用）。

## 1. 语言差异：入参 `final`（仅 Java）

- **Java**：**普通**（非下列例外）方法形参必须加 **`final`**。
- **例外（一律不加 `final`）**：
  - **`abstract`** 抽象方法（含抽象类或接口中 **无方法体** 的抽象声明）的形参；
  - **`interface` 中的默认方法**（`default`）的形参；
  - 标注 **`@Override`** 的实现方法（覆盖父类 / 实现接口）的形参。
- **Kotlin**：**暂不**要求为参数加等价约束（按语言惯例即可）。

## 2. `@param` / `@return`（Java Javadoc 与 Kotlin KDoc）

- 当方法 **既有非 `void` / 非 `Unit` 的返回类型**，又 **至少有一个入参** 时，必须写 **`@param`**（每个入参一条）与 **`@return`**。
- **`void` / `Unit`**：若无返回语义，见下文第 6 节；若保留且无返回值可描述，可不写 `@return`（以项目既有风格为准时跟随项目）。
- **类型与成员在标签行中的写法**：见第 4 节「交叉引用」——Java 用 `{@link …}`，Kotlin 用 **`[…]`**，勿在 Kotlin 中写 `{@link …}`。

## 3. 返回类型为 `Boolean` / `boolean` 时的 `@return`（**Java 与 Kotlin 不同**）

### 3.1 Java（Javadoc）

优先使用下列形式（说明 **true / false** 各自含义）：

```text
@return {@code true} XXXX, {@code false} XXX
```

- **默认**：`XXXX`、`XXX` 为简短中文说明，与业务语义一致。
- **不必强制改成中文**：若已符合上述形式，且两侧说明本身即可读、能区分语义（例如 `@return {@code true} success, {@code false} fail`、`@return {@code true} yes, {@code false} no` 等常见英文对举），**保留即可**，无需仅为「统一中文」而改写。

### 3.2 Kotlin（KDoc）

**不要**使用 `{@code true}` / `{@code false}`（那是 Javadoc）。在 `@return` 中用 **Markdown 反引号** 标出字面量：

```text
@return `true` XXXX, `false` XXX
```

- 含义与 Java 侧相同：分别说明为真、为假时的语义；默认中文；已对举的英文对举可保留（与 3.1 一致）。

## 4. 交叉引用与内联代码（**Java 与 Kotlin 不同**）

### 4.1 Java： `{@link …}`、`{@code …}`

- 指向类、方法、字段时，使用 **`{@link 完全限定或简单名#成员(参数类型列表)}`**。
- 示例：引用工具方法 **`ListViewUtils.fullScroll(View, int)`** 时写：

```text
{@link ListViewUtils#fullScroll(View, int)}
```

- **`@param` / `@return` 行内**需要引用类型时，使用 `{@link Intent}` 等形式（与仓库既有 Javadoc 一致）。
- 方法注释首段规则见下文 5.1；首段之后可在 `<pre>` 内使用 `{@code …}`、`{@link …}`。

### 4.2 Kotlin：KDoc 方括号 **`[…]`**、反引号 **`` `…` ``**

- 指向声明时，使用 KDoc 的 **Markdown 链接语法**：**`[接收者.成员]`** 或 **`[包路径.类名.成员]`**（按可见性与项目习惯选择能否省略包名）。
- 示例：对应 Java 侧的 `ListViewUtils#fullScroll(View, int)`，Kotlin 侧可写：

```text
[ListViewUtils.fullScroll]
```

- 内联代码、关键字、字面量用 **反引号** `` `foo` ``，**不要**写 `{@code foo}`。
- **禁止**在 Kotlin 源文件的 KDoc 里使用 **`{@link …}`**（非 KDoc 语法）；生成注释时 **按文件语言二选一**。

## 5. 方法注释 vs 方法备注（核心）

### 5.1 方法注释（**首段**，摘要）

- **只写方法功能用途**：一句话、**尽可能简短**；可写版本/前提（如「Android 16+：……」）。
- **Java（Javadoc）**：首段为纯叙述，**禁止**出现 **`{@...}`**（含 `{@code …}`、`{@link …}` 等）。首段不夹带 Javadoc 内联标签。
- **Kotlin（KDoc）**：首段同样保持简短摘要；使用 **KDoc/Markdown**（如反引号、`**粗体**`），**不要**使用 Java 的 `{@…}` 标签族。

### 5.2 方法备注（首段之后的说明块）

- 需要补充的场景、风险、滥用说明、API 名、类型引用、文档链接提示等，**全部放在首段之后**。
- **Java**：使用 **`<pre> … </pre>`** 包裹，内部 **允许** `{@code startActivity}`、`{@link Intent}` 等标签与换行。
- **Kotlin**：在首段后用普通段落或 **缩进代码块**（KDoc 约定）补充细节；成员引用用 **`[Type.member]`**，代码片段用反引号或 fenced block，**不用** `<pre>` 嵌 `{@link}`。
- 分工：**首段 = 是什么/干什么**；**其后 = 细节、约束、引用、注意事项**。

### 5.3 参考结构（Java，与仓库示例一致）

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

要点：**首行无 `{@}`**；**`<pre>` 内** 再放 `{@code startActivity}` 等；**`@param` / `@return`** 可用 `{@link 类型}`。

### 5.4 参考片段（Kotlin：`Boolean` 的 `@return` 与 `[…]` 链接）

```kotlin
/**
 * 在视图已满足显示条件时跳过一轮无效布局请求。
 *
 * 与 [ListViewUtils.fullScroll] 等列表滚动工具配合时，可减少嵌套滚动时的抖动。
 *
 * @param view 目标视图
 * @return `true` 已提交优化请求, `false` 当前状态无需处理
 */
fun requestLayoutIfNeeded(view: View): Boolean
```

要点：**`@return`** 用 `` `true` `` / `` `false` `` 对举；正文里指向 Java/Kotlin 声明用 **`[ListViewUtils.fullScroll]`**，勿写 `{@link …}`。

## 6. 返回值与 `void`

- **非必要不写 `void`**：若方法本质是「处理入参并供链式/复用」，优先 **返回有意义的值**；即使「没有额外返回语义」，也可 **返回入参对象**（如上的 `return intent`）或项目约定的空值（如 `null`），便于调用方连续书写。
- **仅在**确实无返回值且不宜伪造返回语义时，再使用 `void`。

## 7. 异常与崩溃

- **不得**因工具方法未捕获异常而导致主进程 **未处理崩溃**。
- 可能抛出受检/运行时的逻辑：**在方法内 `try/catch`**，记录日志（若项目已有工具类则沿用），**返回安全默认值**（如 `null`、false、原入参、空集合等，与语义一致即可）。
- **避免**将「易在调用方忘记处理」的异常通过 **`throws`** 层层上抛至未捕获崩溃；宁可 **在方法边界消化并返回异常语义下的安全值**。

## 8. 执行清单（改写方法时自测）

- [ ] Java 形参均已 `final`（抽象方法、`interface default`、`@Override` 方法除外不加）。
- [ ] 有非 `void` / 非 `Unit` 返回值且有参：`@param` / `@return` 齐全。
- [ ] **`boolean` / `Boolean`**：`@return` **Java** 为 `{@code true}` / `{@code false}` 分支说明；**Kotlin** 为 `` `true` `` / `` `false` `` 分支说明（新写默认中文；已对举英文可保留）。
- [ ] **交叉引用**：**Java** 用 `{@link Type#member(…)}`；**Kotlin** 用 **`[Type.member]`**，未混用 `{@link …}`。
- [ ] Javadoc **首段**单行、无 `{@}`；Kotlin 首段无 Java 式 `{@}`；补充说明位置与格式按语言（Java `<pre>` + `{@link}`；Kotlin 段落 + `[…]` + 反引号）。
- [ ] 能用返回入参/有意义值替代的，避免无意义 `void`。
- [ ] 危险调用已 try/catch，不依赖未捕获异常传播。
