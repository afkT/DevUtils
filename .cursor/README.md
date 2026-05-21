# DevUtils：Cursor 工作区说明（`.cursor`）

本目录汇总本仓库与 Cursor Agent 相关的 **Project Rules** 与 **工程向 Skills**，便于快速了解「能做什么、何时会用到」。具体步骤、路径与表格以各 `SKILL.md` / `reference.md` 为准。

维护 `.cursor/` 增删改后请同步本文件；格式与顺序见 [rules/cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc)。

---

## 当前子树一览

| 相对路径 | 用途 | 编目文件数（约） |
|----------|------|------------------|
| `rules/` | Project Rules（`.mdc`） | 5 |
| `skills/` | 工程向 Agent Skills（`SKILL.md`，部分含 `reference.md`） | 11 个 Skill + 4 份 reference |

### 目录树

```
.cursor/
├── README.md
├── rules/
│   ├── chinese-simplified.mdc
│   ├── cursor-catalog-sync.mdc
│   ├── karpathy-guidelines.mdc
│   ├── skill-naming-normalization.mdc
│   └── skill-reference-layout.mdc
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
    ├── ui-devsimple-viewtheme/
    │   ├── SKILL.md
    │   └── reference.md
    ├── ui-devwidget-round/SKILL.md
    └── ui-shadowlayout/SKILL.md
```

---

## 一、`rules/`（Project Rules）

| 文件 | alwaysApply | 说明 |
|------|:-------------:|------|
| [chinese-simplified.mdc](rules/chinese-simplified.mdc) | 是 | 助手默认使用简体中文回复。 |
| [cursor-catalog-sync.mdc](rules/cursor-catalog-sync.mdc) | 是 | 变更 `.cursor/` 编目后同步本 README。 |
| [karpathy-guidelines.mdc](rules/karpathy-guidelines.mdc) | 否 | 写码、评审、重构时按需采用的通用行为准则。 |
| [skill-naming-normalization.mdc](rules/skill-naming-normalization.mdc) | 是 | 创建或重命名 Skill 时的命名与前缀规范。 |
| [skill-reference-layout.mdc](rules/skill-reference-layout.mdc) | 否 | 编辑 `skills/**` 时 SKILL 与 reference 的分工约定。 |

---

## 二、`skills/`（工程向 Skills）

任务匹配时 Agent 应 **Read** 对应 [SKILL.md](skills/)；含 `reference.md` 的 Skill 在需要全表或扫描清单时再读 reference。

**自动唤起**：下表「自动唤起」为 **关** 的 Skill 在 YAML 中设置了 `disable-model-invocation: true`，需用户明确提到相关任务时再加载；**默认** 表示未设置该字段。

| 目录 | `name` | 自动唤起 | reference | 能做什么 |
|------|--------|:--------:|:---------:|----------|
| [android-dimen-dp-sp](skills/android-dimen-dp-sp/SKILL.md) | `android-dimen-dp-sp` | 默认 | — | 将硬编码 dp/sp 改为 `@dimen/dp_*`、`@dimen/sp_*` 与 `R.dimen.*`。 |
| [android-version-platform-adapt](skills/android-version-platform-adapt/SKILL.md) | `android-version-platform-adapt` | 关 | [有](skills/android-version-platform-adapt/reference.md) | 按官方版本文档做行为变更与新 API 适配、targetSdk 升级与工具封装。 |
| [binding-adapter-from-source](skills/binding-adapter-from-source/SKILL.md) | `binding-adapter-from-source` | 默认 | [有](skills/binding-adapter-from-source/reference.md) | 从 Java/Kotlin 源码设计 BindingAdapter 与布局自定义属性。 |
| [code-method-normalize](skills/code-method-normalize/SKILL.md) | `code-method-normalize` | 默认 | — | 统一 Java/Kotlin 方法注释与写法（JavaDoc/KDoc、final、安全返回等）。 |
| [gradle-central-deps](skills/gradle-central-deps/SKILL.md) | `gradle-central-deps` | 默认 | — | 在集中 Gradle 配置中新增或引用依赖坐标，写入 deps 清单。 |
| [gradle-third-party-version-upgrade](skills/gradle-third-party-version-upgrade/SKILL.md) | `gradle-third-party-version-upgrade` | 关 | [有](skills/gradle-third-party-version-upgrade/reference.md) | 查证并升级第三方库版本，对齐仓库与官方发布。 |
| [release-changelog-update](skills/release-changelog-update/SKILL.md) | `release-changelog-update` | 关 | — | 按既有版式用 git 提交说明更新 CHANGELOG / 发版记录。 |
| [ui-devsimple-viewtheme](skills/ui-devsimple-viewtheme/SKILL.md) | `ui-devsimple-viewtheme` | 默认 | [有](skills/ui-devsimple-viewtheme/reference.md) | 为布局控件补全 DevSimple `ViewTheme` 样式引用。 |
| [ui-devwidget-round](skills/ui-devwidget-round/SKILL.md) | `ui-devwidget-round` | 默认 | — | 用 DevWidget Round 做圆角、描边，减少 shape drawable。 |
| [ui-shadowlayout](skills/ui-shadowlayout/SKILL.md) | `ui-shadowlayout` | 默认 | — | 用 ShadowLayout 做阴影、圆角、背景与状态效果，少写 selector/shape。 |

---

## 三、与本机「用户级」Skills 的区别

本仓库 `skills/` 仅覆盖 **DevUtils 工程**相关能力（Gradle、尺寸、UI 库、DataBinding、平台适配等）。本机 `~/.cursor/skills-cursor/` 下的通用 Skills（如 create-rule、create-skill 等）不在此说明。

---

## 四、维护检查清单

- [ ] 子树一览、目录树、上表与仓库 `.cursor/` 实际内容一致。
- [ ] 表格行、目录树子项顺序与同目录文件系统顺序一致（默认字典序）。
- [ ] 新增或重命名 Skill 后，已更新 `name`、自动唤起、reference 列与链接。
- [ ] Skill 重命名后，已修正 `skills/**` 内互链（`../` 路径）。
- [ ] 说明列保持简短，细节以各 `SKILL.md` 为准。
