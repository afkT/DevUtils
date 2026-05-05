---
name: gradle-third-party-version-upgrade
description: >-
  对 `file/gradle/config.gradle` 与 `file/gradle/config_*.gradle` 中定义的第三方库
  GAV 依赖做版本查证与升级；结合 Maven Central、Google Maven、Gradle Plugin Portal、
  JitPack 与 GitHub Releases/README 交叉校验「最新可用版本」；必要时修正 group/artifact、
  同步 `versions.gradle` 中集中版本号，并为缺失的开源地址补全注释。在用户要求升级依赖、
  检查过时库版本、对齐 config/deps 与中央仓库或 GitHub 发布时使用。
disable-model-invocation: true
---

# Gradle 第三方依赖版本升级（中心化配置）

## 适用范围

| 路径 | 是否纳入 |
|------|----------|
| `file/gradle/config.gradle`（`ext.deps`） | 是：含 `build` 插件、官方栈、Dev 系列等 |
| `file/gradle/config_libs.gradle`（`ext.deps_lib`） | 是：第三方库主战场 |
| 其它 `file/gradle/config_*.gradle` | **仅当**文件内定义 Maven/Gradle **坐标字符串**（`group:artifact:version` 或 `...:${versions.xxx}` 且 `versions` 在本仓库维护）时纳入；**纯路径映射**（如 `config_files.gradle` 的 `files` map）默认**不**做版本升级 |

与「新增依赖、分组归属」相关时，先读并按 [gradle-central-deps/SKILL.md](../gradle-central-deps/SKILL.md) 执行；本 Skill 专注**查证版本 + 写回配置**。

## 执行原则

1. **多源交叉校验**：单一页面（含 mvnrepository 聚合站）可能滞后或展示非最新；必须以 **构件可解析** 为准（Central / 官方仓库 / JitPack 构建成功 / 插件门户）。
2. **GitHub 补证**：若库开源于 GitHub/GitLab 等，用 **Releases / Tags** 与 **README**（安装说明、迁移、latest 徽章）核对「推荐版本」；与 Maven 上最新版不一致时，**以 README 与 Release 说明为准**（例如仅 Git Tag 发布、或 Maven 上已有更新的 `-android` 变体）。
3. **同步写回**：确认目标版本后，**同一轮**更新 `config.gradle` / `config_libs.gradle`（及受影响的 `config_*.gradle`）与 **`file/gradle/versions.gradle`** 中被引用的变量；同一库多行（如 `glide` + `glide_compiler`）保持版本一致。
4. **坐标变更**：若官方迁移 groupId/artifactId（或 JitPack 坐标规则变化），**同步修改字符串**，并保留/更新注释中的文档链接。
5. **注释与链接**：条目上方或行尾注释若**没有**可点击的**开源仓库或官方文档**链接，且已确认托管地址，则按邻近条目风格补上（例如 `// 库简述 https://github.com/org/repo`）；已有链接不重复堆砌。
6. **不臆测版本**：无法从仓库或源码托管方确认时，**不写死新版本**，在回复中说明阻塞原因与已查 URL。

## 版本信息查询顺序（建议）

对每条 `group:artifact:version`（或带 `${versions.*}` 的串）先解析 **group、artifact**；再按坐标类型选源（可并行查证）：

### A. 普通 Maven Central 构件（非 `com.github.*` 插件坐标等）

1. **Maven Central Search API**（优先，JSON）：  
   `https://search.maven.org/solrsearch/select?q=g:"GROUP"+AND+a:"ARTIFACT"&core=gav&rows=50&wt=json`  
   在 `response.docs[].v` 中取符合渠道策略的版本号（见下文「版本策略」）。
2. **仓库元数据**（辅助）：`https://repo1.maven.org/maven2/GROUP_PATH/ARTIFACT/maven-metadata.xml`（GROUP 中 `.` 换为 `/`）。
3. **AndroidX / Google 相关**：以 [AndroidX 发布说明](https://developer.android.com/jetpack/androidx/releases) 与 **Google Maven** 元数据为准，与 Central 交叉核对。

### B. JitPack / `com.github.*` / `com.github.USER:Repo:tag` 形式

1. **JitPack API**：`https://jitpack.io/api/builds/GROUP_PATH/ARTIFACT/latest`（具体路径以 JitPack 文档为准；`GROUP` 常为 `com.github.OWNER`）。确认 **status=ok** 的 tag/commit。
2. **GitHub**（若对应仓库存在）：`https://api.github.com/repos/OWNER/REPO/releases` 或 `.../releases/latest`；无 Release 时看 **Tags**；README 中的依赖片段优先于随意猜 tag。

### C. Gradle 插件（`config.gradle` 的 `build` 等）

- **Gradle Plugin Portal**：页面 `https://plugins.gradle.org/plugin/PLUGIN_ID` 或门户 Maven 坐标说明。  
- 若插件坐标在 Central，仍可用 Central Search。

### D. 次要参考（不可单独作为「最新」结论）

- mvnrepository.com、libraries.io 等：**仅作线索**，发现与 Central/GitHub 不一致时以 Central/GitHub/官方为准。

## 版本策略（与仓库现状对齐）

- **默认**：与文件中邻近条目一致，优先 **稳定版**（无 `alpha`/`beta`/`rc` 后缀），除非该依赖在项目中**已统一使用**预览通道（如已有 `3.0.0-alpha` 系列则单独评估，不擅自改频道）。
- **主版本升级**：若跨越 major，在回复中简要列出 **Release notes / 破坏性变更** 线索（README 章节或 Release 标题）；仍按用户要求或「全面升级」指令执行写回。
- **Kotlin / Android Gradle Plugin / 编译器**：与 `versions.gradle`、Android Studio 兼容矩阵一致；不单为「数字更新」而升到未验证组合。

## 写回位置规则

| 版本出现位置 | 操作 |
|--------------|------|
| `"..."` 内写死版本号 | 直接替换该字符串中的版本段 |
| `${versions.xxx}` | 改 **`file/gradle/versions.gradle`** 中对应 `xxx`，**不要**在 `config*.gradle` 里硬编码拆变量 |
| 同一库多模块 | 一并更新所有相同 `group:artifact` 前缀或文档要求成对的行 |

写回后保持 **Gradle Map 语法、缩进、逗号** 与文件内现有风格一致；不借机重排无关条目。

## 工作流清单（可复制）

```
- [ ] 列出用户指定或扫描到的 GAV / 变量引用
- [ ] 按「查询顺序」拉取 Central / JitPack / GitHub / 插件门户
- [ ] 交叉校验；记录最终采用版本与依据链接
- [ ] 更新 config.gradle / config_libs.gradle / config_*.gradle 与 versions.gradle（若涉及）
- [ ] 补全缺失的开源/文档注释链接
- [ ] 回复中汇总：库名、旧→新、依据 URL、若有 major 升级则注明风险
```

## 与 `gradle-central-deps` 的分工

| 场景 | 使用 |
|------|------|
| 升级已有 key 的版本号、修正坐标、补链接 | 本 Skill |
| 新库写入 `deps`/`deps_lib`、选分组、`file/deps/*.gradle` 引用 | `gradle-central-deps` |

## 附加说明

- **网络工具**：查证阶段应使用 **WebSearch / WebFetch**（或等价）访问上述 API 与 HTML 页面；注意 GitHub API **匿名速率限制**，失败时改用 Release 页 WebFetch。
- **验证**：若仓库内可运行 `./gradlew :模块:dependencies` 或 CI 任务，在重大升级后可建议用户本地做一次依赖解析验证（本 Skill不强制改 CI）。

更多 API 与示例见 [reference.md](reference.md)。
