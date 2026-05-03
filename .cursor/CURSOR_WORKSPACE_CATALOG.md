# DevUtils：Cursor 工作区目录说明（`.cursor`）

本文档路径：**`.cursor/CURSOR_WORKSPACE_CATALOG.md`**。用于汇总本仓库 **`.cursor/`** 下与 Cursor 相关的配置与约定，便于与 Agent 快速查阅；**不限于** `rules`、`skills`，后续若增加 `hooks`、`agents`、其它约定目录或单文件说明，应在本目录中 **增删对应章节与表格**。

**维护约定**：凡在本仓库对 `.cursor/` 做 **新增、删除、重命名** 等会影响「工作区行为或可发现性」的变更（含规则 `.mdc`、各 Skill 的 `SKILL.md`、以及未来其它纳入编目的路径），请同步更新 **本文件**（目录表、相对路径、摘要与文末清单）。同步义务由规则 [rules/cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc) 提醒 Agent 执行。

---

## 当前子树一览（编目范围）

| 相对路径 | 用途 |
|----------|------|
| `rules/` | Cursor Project Rules（`.mdc`） |
| `skills/` | 本仓库工程向 Agent Skills（各子目录 `SKILL.md`） |

> 若仓库新增 `.cursor` 下其它目录或顶层文件且希望团队可见，请在本表与后文增加小节。

---

## 一、`rules/`（Project Rules）

规则文件为 **`.mdc`**；YAML 中 `alwaysApply: true` 表示默认注入对话上下文。

| 文件 | alwaysApply | description（YAML） |
|------|:-------------:|---------------------|
| [chinese-simplified.mdc](rules/chinese-simplified.mdc) | 是 | Assistant replies in Simplified Chinese for this workspace. |
| [karpathy-guidelines.mdc](rules/karpathy-guidelines.mdc) | 是 | Behavioral guidelines to reduce common LLM coding mistakes… |
| [cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc) | 是 | 变更 `.cursor/` 编目内容后同步更新 `CURSOR_WORKSPACE_CATALOG.md`（含未来 hooks 等）。 |

### 1.1 `chinese-simplified.mdc`

- **作用**：本工作区助手默认使用**简体中文**回复。

### 1.2 `karpathy-guidelines.mdc`

- **作用**：编码与协作行为准则（与项目说明合并使用）。
- **要点摘要**：
  1. **先想后写**：明确假设；多解时列出；不清楚则提问。
  2. **极简**：只实现需求内功能；避免过度抽象与无效防御代码。
  3. **手术式修改**：只改必要处；风格与仓库一致；自己引入的无用引用要删，不擅自删历史死代码。
  4. **目标可验证**：把任务拆成可检查步骤（测试、通过标准等）。

### 1.3 `cursor-catalog-sync.mdc`

- **作用**：在改动 `.cursor/` 编目范围内文件或结构后，**更新本文件 `CURSOR_WORKSPACE_CATALOG.md`**，避免说明与仓库脱节。

---

## 二、`skills/`（工程向 Skills）

每个 Skill 为独立目录下的 **`SKILL.md`**，顶部 YAML 含 `name`、`description`（及可选字段）。Agent 在描述匹配用户任务时应 **Read** 该文件并按文执行。

| 目录 / 文件 | `name` | 触发场景（来自 description） |
|-------------|--------|------------------------------|
| [gradle-central-deps/SKILL.md](skills/gradle-central-deps/SKILL.md) | `gradle-central-deps` | 在本仓库新增/引用 Gradle 依赖；查 `file/gradle/config.gradle`、`config_libs.gradle`；改 `file/deps/deps_*.gradle`；AndroidX/CameraX/Kotlin/Jetpack/第三方 Maven。 |
| [android-dimen-dp-sp/SKILL.md](skills/android-dimen-dp-sp/SKILL.md) | `android-dimen-dp-sp` | 去掉魔法数、统一 dimen、把硬编码 dp/sp 改为 `@dimen/dp_*`、`@dimen/sp_*` / `R.dimen.*`。 |
| [shadowlayout-ui/SKILL.md](skills/shadowlayout-ui/SKILL.md) | `shadowlayout-ui` | 阴影、圆角、渐变、描边、pressed/selected/ripple、虚线、子 View 圆角裁剪；少写 shape/layer-list；使用 `com.lihang.ShadowLayout`。 |
| [devwidget-round-ui/SKILL.md](skills/devwidget-round-ui/SKILL.md) | `devwidget-round-ui` | 圆角矩形纯色背景、描边；优先 DevWidget `dev.widget.ui.round`；替代多余 drawable。 |
| [devsimple-viewtheme-xml/SKILL.md](skills/devsimple-viewtheme-xml/SKILL.md) | `devsimple-viewtheme-xml` | 布局 XML 默认补全 DevSimple `ViewTheme.*`；继承链匹配；ImageView 用 `ViewTheme.ImageView.FIT_XY`。 |

### 2.1 `gradle-central-deps`

- **核心**：依赖坐标集中在 `config.gradle`（`deps`）与 `config_libs.gradle`（`deps_lib`）；**禁止重复 GAV**；官方 AndroidX/Kotlin 等进 `deps`，第三方进 `deps_lib`；业务侧在 `file/deps/*.gradle` 按现有风格引用。

### 2.2 `android-dimen-dp-sp`

- **核心**：资源名 `dp_`/`sp_` + 整数或 `整数_一位小数数字`（截断非四舍五入）；默认假定 dimen 已存在；不擅自新建 `dimens.xml`（除非用户要求）。

### 2.3 `shadowlayout-ui`

- **核心**：`R.styleable.ShadowLayout` 下 `app:hl_*` 全表与 API；注意 **`hl_strokeWith` 拼写**；`dashLine` 模式单独使用；与 DevWidget round 的取舍说明。

### 2.4 `devwidget-round-ui`

- **核心**：`Round*Layout` / `RoundTextView` / `RoundImageView`（圆形图）与 `app:dev_*`；**勿与 `android:background` 混用** RoundDrawable 方案。

### 2.5 `devsimple-viewtheme-xml`

- **YAML 备注**：含 `disable-model-invocation: true`（由 Cursor 按产品行为处理是否自动唤起）。
- **核心**：按控件语义类名匹配 `ViewTheme.<Name>`；AppCompat/Material 回退到平台 Widget 名；`ViewPager2` → `ViewTheme.ViewPager`；无匹配 → `ViewTheme.View`；维护时应用 grep 更新 Skill 内「根样式表」。

---

## 三、与「用户级」Skills 的区别

本仓库 **`skills/`** 仅包含 **DevUtils 工程相关** 的 Gradle、尺寸、UI 库与 DevSimple 等说明。本机 `~/.cursor/skills-cursor/` 下另有通用 Skills（如 canvas、create-rule、shell 等），**不在本目录说明范围内**；若需文档化，应在个人笔记或对应仓库维护。

---

## 四、变更检查清单（维护者用）

- [ ] `.cursor/` 编目范围内有增删改后，已更新上文 **「当前子树一览」**、各 **表格** 与 **小节摘要**。
- [ ] 新增 Skill 已写清 `name`、`description` 与正文路径交叉引用（README 链接等）。
- [ ] 新增其它 `.cursor` 子目录或顶层说明文件时，已增加对应章节并写清用途与维护人约定。
- [ ] 第三节「与用户级 Skills 区别」如无变化可不动。

*与文件系统对齐时请以仓库内 `.cursor` 实际目录为准。*
