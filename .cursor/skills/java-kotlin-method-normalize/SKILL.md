---
name: java-kotlin-method-normalize
description: >-
    规范化新生成或改写的 Java/Kotlin 方法：Java 入参 final（抽象方法、接口默认方法、@Override 实现除外一律不加）；
    Javadoc 首段无 {@}、补充说明用 pre 块包裹；有返回值且带参时补全 @param/@return；
    boolean 的 @return 用 {@code true}/{@code false} 分支说明（默认中文；success/fail、yes/no 等已对举表意时可保留英文）；优先返回入参或有语义的对象替代无意义 void；
    异常在方法内捕获并安全返回、避免未处理崩溃。
    在用户要求规范化方法、统一工具类写法、整理 Javadoc、或按 DevUtils 方法风格处理时使用。
disable-model-invocation: true
---

# Java / Kotlin：生成方法的规范化

对 **新生成或改写** 的方法按下列规则整理；**仅改与规范化相关的部分**，不扩大重构范围。

## 1. 语言差异：入参 `final`（仅 Java）

- **Java**：**普通**（非下列例外）方法形参必须加 **`final`**。
- **例外（一律不加 `final`）**：
    - **`abstract`** 抽象方法（含抽象类或接口中 **无方法体** 的抽象声明）的形参；
    - **`interface` 中的默认方法**（`default`）的形参；
    - 标注 **`@Override`** 的实现方法（覆盖父类 / 实现接口）的形参。
- **Kotlin**：**暂不**要求为参数加等价约束（按语言惯例即可）。

## 2. Javadoc：`@param` / `@return`

- 当方法 **既有非 `void` 的返回类型**，又 **至少有一个入参** 时，必须写 **`@param`**（每个入参一条）与 **`@return`**。
- **`void`**：若无返回语义，见下文第 5 节；若保留 `void` 且仅有入参、无返回值可描述，可不写 `@return`（以项目既有风格为准时跟随项目）。

## 3. 返回类型为 `boolean` 时的 `@return`

优先使用下列形式（说明 **true / false** 各自含义）：

```text
@return {@code true} XXXX, {@code false} XXX
```

- **默认**：`XXXX`、`XXX` 为简短中文说明，与业务语义一致。
- **不必强制改成中文**：若已符合上述形式，且两侧说明本身即可读、能区分语义（例如 `@return {@code true} success, {@code false} fail`、`@return {@code true} yes, {@code false} no` 等常见英文对举），**保留即可**，无需仅为「统一中文」而改写。

## 4. 方法注释 vs 方法备注（核心）

### 4.1 方法注释（Javadoc **首段**，摘要）

- **只写方法功能用途**：一句话、**尽可能简短**；可写版本/前提（如「Android 16+：……」）。
- **禁止**在首段中出现 **`{@...}`**（含 `{@code …}`、`{@link …}` 等）。首段为纯叙述，不夹带 Javadoc 内联标签引用。

### 4.2 方法备注（首段之后的说明块）

- 需要补充的场景、风险、滥用说明、API 名、类型引用、文档链接提示等，**全部放在首段之后**。
- 使用 **`<pre> … </pre>`** 包裹，内部 **允许** `{@code startActivity}`、`{@link Intent}`、`{@link IntentFilter}` 等标签与换行。
- 与首段分工：**首段 = 是什么/干什么；`<pre>` = 细节、约束、引用、注意事项。**

### 4.3 参考结构（与仓库示例一致）

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

## 5. 返回值与 `void`

- **非必要不写 `void`**：若方法本质是「处理入参并供链式/复用」，优先 **返回有意义的值**；即使「没有额外返回语义」，也可 **返回入参对象**（如上的 `return intent`）或项目约定的空值（如 `null`），便于调用方连续书写。
- **仅在**确实无返回值且不宜伪造返回语义时，再使用 `void`。

## 6. 异常与崩溃

- **不得**因工具方法未捕获异常而导致主进程 **未处理崩溃**。
- 可能抛出受检/运行时的逻辑：**在方法内 `try/catch`**，记录日志（若项目已有工具类则沿用），**返回安全默认值**（如 `null`、false、原入参、空集合等，与语义一致即可）。
- **避免**将「易在调用方忘记处理」的异常通过 **`throws`** 层层上抛至未捕获崩溃；宁可 **在方法边界消化并返回异常语义下的安全值**。

## 7. 执行清单（改写方法时自测）

- [ ] Java 形参均已 `final`（抽象方法、`interface default`、`@Override` 方法除外不加）。
- [ ] 有非 `void` 返回值且带参：`@param` / `@return` 齐全。
- [ ] `boolean`：`@return` 含 `{@code true}` / `{@code false}` 分支说明（新写默认中文；已有 success/fail、yes/no 等对举且表意充分时可保留）。
- [ ] Javadoc 首段单行、无 `{@}`；补充说明在 `<pre>` 内。
- [ ] 能用返回入参/有意义值替代的，避免无意义 `void`。
- [ ] 危险调用已 try/catch，不依赖未捕获异常传播。
