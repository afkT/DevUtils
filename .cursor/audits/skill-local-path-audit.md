# Cursor Skills 本地路径与仓库关联审计

本文档重新扫描 **`.cursor/skills/`** 下全部当前 Skill 文件，归纳其中的 **本地/仓库相对路径**、**目录契约**、**跨 Skill 链接**、**reference 分工** 与 **仍可能漂移的快照数据**。

**扫描范围**：`.cursor/skills/`  
**当前有效目录**：11 个 Skill 目录，6 份 `reference.md`。  
**忽略项**：`.DS_Store` 等本地杂项。  
**未发现**：绝对机器路径（如用户主目录或本机项目根路径）写入 Skill 正文。

---

## 1. 引用类型说明

| 类型 | 含义 | 现状判断 | 典型风险 |
|------|------|----------|----------|
| **A. 契约化仓库布局** | 用 `DEPS_ROOT`、`DEVSIMPLE_ROOT` 等符号承载仓库目录 | 推荐，当前多处已采用 | fork 或目录迁移时只需改契约表 |
| **B. 明确约定文件名** | `CHANGELOG.md`、`build.gradle`、`versions.gradle` 等按任务上下文定位的文件 | 可接受 | 多模块时需明确库根或版本源 |
| **C. 模块内相对锚点** | `*.kt`、`attribute/`、`ViewTheme.*` 等文件/样式名 | 适合放在 reference | 新增或重命名文件后表格漂移 |
| **D. Skill 互链** | `../other-skill/SKILL.md`、同目录 `reference.md` | 当前合理 | 重命名 Skill 时断链；由 `cursor-catalog-sync` 维护 |
| **E. 上游 raw URL** | GitHub raw 中的上游内部路径，如 `shadowLibrary/...` | 可接受，非工作区路径 | 需与 Maven/JitPack 版本 tag 对齐 |
| **F. 历史类名示例** | `JobSchedulerUtils`、`TextView.kt` 等 | 应仅作为 reference 示例 | 类迁移后不应阻塞执行 |

---

## 2. 按 Skill 汇总

图例：**中** = 有仓库契约或 reference 表需要维护；**轻** = 主要是约定文件名、外链或少量锚点；**无** = 基本不绑路径。当前无 **重** 级待改项。

| Skill `name` | 关联强度 | 本地/仓库路径与关联摘要 | 伴随 reference |
|--------------|:--------:|-------------------------|:--------------:|
| `android-dimen-dp-sp` | **轻** | 仅 Android 惯例 `values/dimens.xml`；明确不搜索仓库校验 dimen | — |
| `android-version-platform-adapt` | **轻** | `DEVAPP_ROOT=lib/DevApp`；历史类名仅在 reference 作扫描示例；官方 URL 为主要事实源 | 是 |
| `binding-adapter-from-source` | **中** | `DEVSIMPLE_ROOT`、`BINDING_VIEW_DIR`、`BINDING_ATTR_DIR`；范例文件名迁入 reference | 是 |
| `code-method-normalize` | **无** | 规则型方法风格 Skill，正文无仓库路径 | — |
| `gradle-central-deps` | **中** | `DEPS_ROOT=file/gradle`、`DEPS_MANIFEST=file/deps`；契约化良好 | — |
| `gradle-third-party-version-upgrade` | **中** | 已对齐 `DEPS_ROOT` / `DEPS_MANIFEST`；reference 仅保留 URL 与注释风格示例 | 是 |
| `release-changelog-update` | **轻** | `CHANGELOG.md`、库根 git `PATH`；不写死具体 `lib/**` | — |
| `ui-devsimple-viewtheme` | **中** | `DEVSIMPLE_ROOT`、`DEVSIMPLE_VALUES`；根样式全表已拆入 reference | 是 |
| `ui-devwidget-round` | **轻** | `DEVWIDGET_ROOT=lib/DevWidget`；类型清单、属性表和 API 已迁入 reference | 是 |
| `ui-shadowlayout` | **轻** | 无工作区路径，使用 Maven + 上游 raw；`hl_*` 属性全表和 API 已迁入 reference | 是 |

---

## 3. 重点审计结果

### 3.1 已契约化且状态较好

| Skill | 契约 | 说明 |
|-------|------|------|
| `gradle-central-deps` | `DEPS_ROOT`、`DEPS_MANIFEST` | 新增依赖路径唯一来源，结构清晰 |
| `binding-adapter-from-source` | `DEVSIMPLE_ROOT`、`BINDING_VIEW_DIR`、`BINDING_ATTR_DIR` | 硬编码多文件列表已移至 reference |
| `ui-devsimple-viewtheme` | `DEVSIMPLE_ROOT`、`DEVSIMPLE_VALUES` | 样式全表已移至 reference |
| `ui-devwidget-round` | `DEVWIDGET_ROOT` | 已本地优先，属性与 API 表已拆 reference |
| `android-version-platform-adapt` | `DEVAPP_ROOT` | 类名锚点弱化为扫描示例 |
| `gradle-third-party-version-upgrade` | `DEPS_ROOT`、`DEPS_MANIFEST` | 已与 `gradle-central-deps` 对齐 |

### 3.2 长表与快照数据已拆分

| Skill | 发现 | 建议 |
|-------|------|------|
| `binding-adapter-from-source` | 范例文件名、前缀表在 reference | 新增绑定适配器范例时只更新 reference 表 |
| `ui-devsimple-viewtheme` | `ViewTheme.*` 根样式表在 reference | DevSimple 新增样式后按 reference 的 grep 步骤更新 |
| `ui-devwidget-round` | `DevWidget` 属性表、Round 类型清单在 reference | DevWidget 新增属性/类时更新 reference |
| `ui-shadowlayout` | `hl_*` 属性全表、上游 raw/API 表在 reference | ShadowLayout 升级或属性变更时更新 reference |

### 3.3 仅保留为 reference 示例的类名或文件名

| Skill | 示例锚点 | 当前处理 |
|-------|----------|----------|
| `android-version-platform-adapt` | `JobSchedulerUtils`、`ProcessUtils`、`ScreenUtils` 等 | 已放在 reference 的扫描示例；SKILL 正文只要求按 `{DEVAPP_ROOT}` 检索 |
| `binding-adapter-from-source` | `TextView.kt`、`ImageViewLoadNative.kt`、`XYI.kt` 等 | 作为 reference 范例文件名；SKILL 正文使用 `{BINDING_VIEW_DIR}` / `{BINDING_ATTR_DIR}` |
| `ui-devsimple-viewtheme` | `ViewTheme.*` 全表 | 已放在 reference，并提供 grep 更新步骤 |

---

## 4. 跨 Skill 与 reference 链接图

```mermaid
flowchart LR
  subgraph gradle
    GCD[gradle-central-deps]
    GCV[gradle-third-party-version-upgrade]
    GCV --> GCD
    GCV -.-> GCVR[reference.md]
  end

  subgraph codegen
    BAS[binding-adapter-from-source]
    CMN[code-method-normalize]
    AVPA[android-version-platform-adapt]
    BAS --> CMN
    AVPA --> CMN
    BAS -.-> BASR[reference.md]
    AVPA -.-> AVPAR[reference.md]
  end

  subgraph ui
    UDV[ui-devsimple-viewtheme]
    UDR[ui-devwidget-round]
    USL[ui-shadowlayout]
    UDV -.-> UDVR[reference.md]
    UDR -.-> UDRR[reference.md]
    USL -.-> USLR[reference.md]
    USL --> UDR
    USL --> GCD
  end
```

---

## 5. 待改进建议

当前没有必须执行的结构性改造项；现有 `gradle-*` 命名保持不变即可，不再建议改为 `project-gradle-*`。

后续只需按变更场景维护：

| 场景 | 维护动作 |
|------|----------|
| 新增 / 删除 / 重命名 Skill 或 reference | 按 `.cursor/rules/cursor-catalog-sync.mdc` 同步 `.cursor/README.md` 与互链 |
| SKILL 内出现新的长表、扫描清单、属性全表 | 按 `.cursor/rules/skill-reference-layout.mdc` 拆入 `reference.md` |
| DevSimple / DevWidget / ShadowLayout 上游属性或源码变化 | 更新对应 `reference.md`，SKILL 仅保留流程和关键坑 |
| Gradle 依赖布局改变 | 更新两个 Gradle Skill 的 `DEPS_ROOT` / `DEPS_MANIFEST` 契约 |
| DevApp 平台工具类重构 | 更新 `android-version-platform-adapt/reference.md` 的扫描示例，不在 SKILL 正文硬编码类名 |

---

## 6. 建议落地顺序

暂无待落地改造。后续按 §5 的变更场景维护即可。

---

## 7. 相关规则与文档

| 文档 | 说明 |
|------|------|
| [../rules/cursor-catalog-sync.mdc](../rules/cursor-catalog-sync.mdc) | README 同步、Skill 互链、reference 列维护 |
| [../rules/skill-reference-layout.mdc](../rules/skill-reference-layout.mdc) | `SKILL.md` / `reference.md` 分工 |
| [../README.md](../README.md) | 当前 `.cursor` 编目总表 |
| [../../docs/skill-naming-convention.md](../../docs/skill-naming-convention.md) | Skill 命名与领域前缀 |
| [../../docs/skill-creation-spec.md](../../docs/skill-creation-spec.md) | Skill 创建与迁移规范 |

---

*审计依据：重新扫描 `.cursor/skills/**/*.md` 中的契约符号、`lib/`、`file/`、`src/main/`、`reference.md`、`../*/SKILL.md`、`ViewTheme.*`、`attrs.xml`、历史类名等模式，并结合当前目录实际子项人工归类。*
