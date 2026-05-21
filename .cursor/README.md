# DevUtils：Cursor 工作区目录说明（`.cursor`）

本文档路径：**`.cursor/README.md`**（GitHub 浏览 `.cursor` 目录时默认展示）。用于汇总本仓库 **`.cursor/`** 下与 Cursor 相关的配置与约定，便于与 Agent 快速查阅；**不限于** `rules`、`skills`，后续若增加 `hooks`、`agents`、其它约定目录或单文件说明，应在本目录中 **增删对应章节与表格**。

**维护约定**：凡在本仓库对 `.cursor/` 做 **新增、删除、重命名** 等会影响「工作区行为或可发现性」的变更（含规则 `.mdc`、各 Skill 的 `SKILL.md`、伴随 `reference.md`、以及未来其它纳入编目的路径），请同步更新 **本 README**（目录表、相对路径、摘要与文末清单）。同步义务由规则 [rules/cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc) 提醒 Agent 执行；**条目顺序**与 **描述风格** 亦见该规则。

---

## 当前子树一览（编目范围）

| 相对路径 | 用途 | 编目文件数（约） |
|----------|------|------------------|
| `rules/` | Cursor Project Rules（`.mdc`） | 4 |
| `skills/` | 本仓库工程向 Agent Skills（各子目录 `SKILL.md`，部分含 `reference.md`） | 11 个 Skill + 3 份 reference |

> 若仓库新增 `.cursor` 下其它目录或顶层说明文件且希望团队可见，请在本表与后文增加小节。`.DS_Store` 等本地杂项不入编目。

### 目录树（与仓库对齐）

```
.cursor/
├── README.md
├── rules/
│   ├── chinese-simplified.mdc
│   ├── cursor-catalog-sync.mdc
│   ├── karpathy-guidelines.mdc
│   └── skill-naming-normalization.mdc
└── skills/
    ├── android-dimen-dp-sp/SKILL.md
    ├── android-version-platform-adapt/
    │   ├── SKILL.md
    │   └── reference.md
    ├── binding-adapter-from-source/
    │   ├── SKILL.md
    │   └── reference.md
    ├── code-method-normalize/SKILL.md
    ├── gradle-central-deps/SKILL.md
    ├── gradle-third-party-version-upgrade/
    │   ├── SKILL.md
    │   └── reference.md
    ├── release-changelog-update/SKILL.md
    ├── ui-devsimple-viewtheme/SKILL.md
    ├── ui-devwidget-round/SKILL.md
    └── ui-shadowlayout/SKILL.md
```

---

## 一、`rules/`（Project Rules）

规则文件为 **`.mdc`**；YAML 中 `alwaysApply: true` 表示默认注入对话上下文；`false` 表示 **按需** 由 Agent 在相关任务时 Read（仍可在 Cursor 规则列表中启用）。

| 文件 | alwaysApply | description（YAML） |
|------|:-------------:|---------------------|
| [chinese-simplified.mdc](rules/chinese-simplified.mdc) | 是 | 本工作区助手默认使用简体中文回复。 |
| [cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc) | 是 | 变更 `.cursor/` 编目内容后同步 README；条目顺序对齐文件系统；表格与列表描述凝练准确。 |
| [karpathy-guidelines.mdc](rules/karpathy-guidelines.mdc) | **否** | 减少常见 LLM 编码失误的行为准则；写码/评审/重构时按需 Read 合并。 |
| [skill-naming-normalization.mdc](rules/skill-naming-normalization.mdc) | 是 | 创建或重命名 Skill 前规范目录名、YAML `name`、领域前缀与项目级 `project-` 前缀。 |

### 1.1 `chinese-simplified.mdc`

- **作用**：本工作区助手默认使用**简体中文**回复。

### 1.2 `cursor-catalog-sync.mdc`

- **作用**：改动 `.cursor/` 编目范围后更新本 README；表格行、§ 编号与目录树子项顺序须与同目录文件系统顺序一致；说明列与分条列表保持简短准确。

### 1.3 `karpathy-guidelines.mdc`

- **作用**：编码与协作行为准则；**非**默认全量注入（`alwaysApply: false`），在编写、评审或重构代码时由 Agent **Read 后**与项目说明合并使用。
- **要点摘要**：
    1. **先想后写**：明确假设；多解时列出；不清楚则提问。
    2. **极简**：只实现需求内功能；避免过度抽象与无效防御代码。
    3. **手术式修改**：只改必要处；风格与仓库一致；自己引入的无用引用要删，不擅自删历史死代码。
    4. **目标可验证**：把任务拆成可检查步骤（测试、通过标准等）。

### 1.4 `skill-naming-normalization.mdc`

- **作用**：创建、重命名或评审 Skill 前规范命名；通用能力用领域前缀，项目专有能力统一用 `project-<领域>-<主题>`。

---

## 二、`skills/`（工程向 Skills）

每个 Skill 为独立目录下的 **`SKILL.md`**，顶部 YAML 含 `name`、`description`（及可选字段）。Agent 在描述匹配用户任务时应 **Read** 该文件并按文执行。

**`disable-model-invocation: true`**（下列 **3** 个 Skill 的 YAML 含此字段）：降低 Cursor 在未点名时自动唤起该 Skill 的概率；用户明确提到相关任务时仍应 Read 并执行。

| 目录 / 文件 | `name` | 自动唤起 | 伴随 reference | 触发场景（来自 description） |
|-------------|--------|:--------:|:--------------:|------------------------------|
| [android-dimen-dp-sp/SKILL.md](skills/android-dimen-dp-sp/SKILL.md) | `android-dimen-dp-sp` | 默认 | — | 将硬编码 dp/sp 改为 `@dimen/dp_*`、`@dimen/sp_*` 与 `R.dimen.*` 引用。 |
| [android-version-platform-adapt/SKILL.md](skills/android-version-platform-adapt/SKILL.md) | `android-version-platform-adapt` | **关** | [reference.md](skills/android-version-platform-adapt/reference.md) | 按 Android 官方版本文档做行为变更与新 API 适配；targetSdk 升级与工具封装。 |
| [binding-adapter-from-source/SKILL.md](skills/binding-adapter-from-source/SKILL.md) | `binding-adapter-from-source` | 默认 | [reference.md](skills/binding-adapter-from-source/reference.md) | 从 Java/Kotlin 源码设计 BindingAdapter 与布局 `app:binding_*` 属性。 |
| [code-method-normalize/SKILL.md](skills/code-method-normalize/SKILL.md) | `code-method-normalize` | 默认 | — | 规范化 Java/Kotlin 方法注释与写法（JavaDoc/KDoc、final、安全返回等）。 |
| [gradle-central-deps/SKILL.md](skills/gradle-central-deps/SKILL.md) | `gradle-central-deps` | 默认 | — | 新增或引用 Gradle 依赖时走集中坐标与 `deps_*.gradle` 流程。 |
| [gradle-third-party-version-upgrade/SKILL.md](skills/gradle-third-party-version-upgrade/SKILL.md) | `gradle-third-party-version-upgrade` | **关** | [reference.md](skills/gradle-third-party-version-upgrade/reference.md) | 升级 `config*.gradle` 中第三方 GAV；多源校验版本与坐标迁移。 |
| [release-changelog-update/SKILL.md](skills/release-changelog-update/SKILL.md) | `release-changelog-update` | **关** | — | 按库根 `CHANGELOG.md` 版式，用 git 完整提交说明更新发版记录。 |
| [ui-devsimple-viewtheme/SKILL.md](skills/ui-devsimple-viewtheme/SKILL.md) | `ui-devsimple-viewtheme` | 默认 | — | 布局 XML 按控件继承链补全 DevSimple `ViewTheme.*` 样式。 |
| [ui-devwidget-round/SKILL.md](skills/ui-devwidget-round/SKILL.md) | `ui-devwidget-round` | 默认 | — | 圆角纯色背景与描边优先 DevWidget Round 系列，减少 drawable。 |
| [ui-shadowlayout/SKILL.md](skills/ui-shadowlayout/SKILL.md) | `ui-shadowlayout` | 默认 | — | ShadowLayout 的 `hl_*` 与 API；以上游 GitHub/Maven 为准，不读工作区 shadowLibrary。 |

表中「自动唤起」**关** = YAML 含 `disable-model-invocation: true`；**默认** = 未设置该字段（由 Cursor 产品行为决定是否自动匹配）。

**曾用名（已废弃，勿再引用路径）**：`shadowlayout-ui` → `ui-shadowlayout`；`devwidget-round-ui` → `ui-devwidget-round`；`devsimple-viewtheme-xml` → `ui-devsimple-viewtheme`；`lib-changelog-update` → `release-changelog-update`；`java-kotlin-method-normalize` → `code-method-normalize`；`databinding-bindingadapter-from-source` → `binding-adapter-from-source`。

### 2.1 `android-dimen-dp-sp`

- **核心**：`dp_`/`sp_` 资源命名规则；默认假定 dimen 已存在；不擅自新建 `dimens.xml`（除非用户要求）。

### 2.2 `android-version-platform-adapt`

- **YAML**：`disable-model-invocation: true`。
- **核心**：以 `developer.android.com/about/versions/{N}` 为准扫描行为变更与新特性；输出适配摘要并落地 Manifest/Gradle/`lib/DevApp`。
- **reference**：[reference.md](skills/android-version-platform-adapt/reference.md) — 路径规律与已扫子页清单（执行时须重跑扫描）。
- **成稿前 Read** `code-method-normalize`。

### 2.3 `binding-adapter-from-source`

- **核心**：从工具类/View 源码推导 BindingAdapter；过滤不适合 XML 的 API；重复触发用 `Long?` 时间戳；效果开关用 `Boolean?` 三态；多参合并为 `attribute/` 类型。
- **reference**：[reference.md](skills/binding-adapter-from-source/reference.md) — 判定表与 XML 示例。
- **成稿前 Read** `code-method-normalize`。

### 2.4 `code-method-normalize`

- **核心**：Java 用 JavaDoc、Kotlin 用 KDoc；Java 形参默认 `final`（抽象/`@Override` 等例外）；注释结构、 `@param`/`@return` 与 boolean 对举规则；优先安全非 void 返回。
- **搭配**：`binding-adapter-from-source`、`android-version-platform-adapt` 成稿前须 Read 本 Skill。

### 2.5 `gradle-central-deps`

- **核心**：坐标集中在 `config.gradle` / `config_libs.gradle`；禁止重复 GAV；业务在 `file/deps/*.gradle` 按现有风格引用。
- **官方检索**：新增 AndroidX 前查 [AndroidX 版本总览（中文）](https://developer.android.com/jetpack/androidx/versions?hl=zh-cn)。

### 2.6 `gradle-third-party-version-upgrade`

- **YAML**：`disable-model-invocation: true`。
- **核心**：多源查证最新可解析版本；写回 `config*.gradle` 与 `versions.gradle`；补开源/文档链接。
- **reference**：[reference.md](skills/gradle-third-party-version-upgrade/reference.md) — Central/JitPack 等 URL 模板。

### 2.7 `release-changelog-update`

- **YAML**：`disable-model-invocation: true`。
- **核心**：以 CHANGELOG 顶栏上一版日期为窗，`git log` 用 `%B` 完整说明；合并重复主题；版本以发版配置或用户指定为准。

### 2.8 `ui-devsimple-viewtheme`

- **核心**：按控件语义类名匹配 `ViewTheme.<Name>`；`ViewPager2` → `ViewTheme.ViewPager`；无匹配 → `ViewTheme.View`。源码见 `lib/DevSimple/src/main/res/values/`。

### 2.9 `ui-devwidget-round`

- **核心**：`Round*Layout` / `RoundTextView` / `RoundImageView` 与 `app:dev_*`；勿与 `android:background` 混用 RoundDrawable。

### 2.10 `ui-shadowlayout`

- **核心**：`app:hl_*` 全表与 API（正文速查）；权威来源为 JitPack 坐标与上游 README/raw，**禁止**假设工作区存在 `shadowLibrary/`；注意 `hl_strokeWith` 拼写；与 `ui-devwidget-round` 取舍见该 Skill。

---

## 三、与「用户级」Skills 的区别

本仓库 **`skills/`** 仅包含 **DevUtils 工程相关** 的 Gradle、尺寸、UI 库、DevSimple、DataBinding 与 Android 平台适配等说明。本机 `~/.cursor/skills-cursor/` 下另有通用 Skills（如 canvas、create-rule、create-skill、shell 等），**不在本目录说明范围内**；若需文档化，应在个人笔记或对应仓库维护。

---

## 四、变更检查清单（维护者用）

- [ ] `.cursor/` 编目范围内有增删改后，已更新上文 **「当前子树一览」**、**目录树**、各 **表格** 与 **小节摘要**。
- [ ] 表格行、§2.x 编号、目录树子项顺序与同目录 **文件系统顺序** 一致（默认字典序）。
- [ ] 表格说明列与 § 分条列表 **简短准确**，未整段照搬 YAML `description`。
- [ ] 新增 Skill 已写清 `name`、`description`；若含 `disable-model-invocation` 或 `reference.md`，表格与对应 §2.x 已同步。
- [ ] Skill 重命名后，目录树、链接路径、`name` 列与 §2.x 标题一致；曾用名仅保留在「曾用名」一行或 `docs/` legacy 表。
- [ ] 规则 `.mdc` 的 `alwaysApply` 与 YAML `description` 与表格一致。
- [ ] 新增其它 `.cursor` 子目录或顶层说明文件时，已增加对应章节并写清用途与维护人约定。
- [ ] 第三节「与用户级 Skills 区别」如无变化可不动。

*与文件系统对齐时请以仓库内 `.cursor` 实际目录为准。*
