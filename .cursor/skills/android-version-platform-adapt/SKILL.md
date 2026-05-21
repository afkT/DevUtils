---
name: android-version-platform-adapt
description: >-
  依据 Android 官方「版本」文档完成平台适配：按版本号 N 抓取行为变更（所有应用、
  targetSdk=N）、changes/features 子页与探索新功能/API；归纳影响项并落地到
  Manifest/Gradle/工具类封装。在用户要求 Android 版本适配、行为变更排查、
  targetSdk 升级、新 API 封装或对照 developer.android.com/about/versions 时使用。
disable-model-invocation: true
---

# Android 版本平台适配（官方文档驱动）

## 适用范围

- 为指定 **Android 版本号 N**（如 17、16）做 **兼容性测试清单**、**targetSdk 升级**、**新 API 工具封装**、**行为变更修复**。
- **唯一事实来源**：`https://developer.android.com/about/versions/` 下该版本页面及子页（优先 `?hl=zh-cn`）。
- **禁止**仅凭记忆或第三方转载编写适配结论；未读官方页面前不得断言某变更是否存在。

与仓库方法风格：生成或改写 **方法体与文档** 前，**Read** 并按 [code-method-normalize/SKILL.md](../code-method-normalize/SKILL.md) 执行。

## 版本参数

| 用户表述 | URL 段 `versions/{N}` | 行为变更（target）页 | 典型 API level |
|----------|-------------------------|----------------------|----------------|
| Android 17 | `17` | `behavior-changes-17` | 37 |
| Android 16 | `16` | `behavior-changes-16` | 36 |
| Android 15 | `15` | `behavior-changes-15` | 35 |

- **N** 与官网路径一致（Android 17 → `17`）。
- **API level**、**`VERSION_CODES` 常量名**以该版 [setup-sdk](https://developer.android.com/about/versions/17/setup-sdk) 与 [API 参考](https://developer.android.com/reference/android/os/Build.VERSION_CODES) 为准；写 `@RequiresApi` 时与项目 `VersionUtils` / 既有工具类对齐。

### URL 模板（将 `{N}` 换为版本号）

| 用途 | URL |
|------|-----|
| 版本首页 | `https://developer.android.com/about/versions/{N}?hl=zh-cn` |
| 行为变更 · 所有应用 | `https://developer.android.com/about/versions/{N}/behavior-changes-all?hl=zh-cn` |
| 行为变更 · targetSdk={N} | `https://developer.android.com/about/versions/{N}/behavior-changes-{N}?hl=zh-cn` |
| 探索新功能与 API | `https://developer.android.com/about/versions/{N}/features?hl=zh-cn` |
| 迁移指南 | `https://developer.android.com/about/versions/{N}/migration?hl=zh-cn` |
| SDK 设置 | `https://developer.android.com/about/versions/{N}/setup-sdk?hl=zh-cn` |
| 发布说明 | `https://developer.android.com/about/versions/{N}/release-notes?hl=zh-cn` |

**Android 17 固定示例**（与上表一致）：

- 首页：https://developer.android.com/about/versions/17?hl=zh-cn  
- 所有应用：https://developer.android.com/about/versions/17/behavior-changes-all?hl=zh-cn  
- target 17：https://developer.android.com/about/versions/17/behavior-changes-17?hl=zh-cn  
- 新功能：https://developer.android.com/about/versions/17/features?hl=zh-cn  

## 执行原则

1. **先读再写**：每个子步骤对应 URL 必须用 **WebFetch**（失败则 **WebSearch** + 重试）拉取正文后再归纳。
2. **扫描子目录**：主站导航不会列出全部深度页；在步骤 1、2 完成后，对 **版本首页、两条 behavior 页、features 页、migration 页** 提取指向 `/about/versions/{N}/` 的链接，去重后按 [reference.md](reference.md) 分类补读。
3. **区分受众**：
   - **所有应用**：设备跑在 N 上即可能受影响（与 `targetSdkVersion` 无关）。
   - **target N**：仅 `targetSdkVersion >= N` 时强制或默认生效。
4. **落地范围**：只改任务相关模块；工具类放 `lib/DevApp` 等既有包时，类级 `<pre>` 可链官方页（参照 `JobSchedulerUtils`）。
5. **API 引用**：新 API 以 [Android API reference](https://developer.android.com/reference) 为准；features 页仅作索引，实现前核对方法签名与 `@RequiresApi`。

## 工作流

### 0. 确认目标

- 用户给出的 **N**、是否仅兼容性、是否升级 **targetSdk** / **compileSdk**。
- 记录仓库当前 `targetSdkVersion`、`compileSdk`（`build.gradle` / `versions.gradle`）。

### 1. 查看应用的行为变更

按顺序执行；每步 **WebFetch** 对应 URL，摘录：变更名、影响条件、涉及 API/权限、缓解措施、是否需改代码。

#### 1.1 所有应用

- 阅读：`.../behavior-changes-all?hl=zh-cn`
- 标注每条为 **全量 / 条件（权限、厂商、前台等）**。

#### 1.2 以 Android {N} 为目标平台的应用

- 阅读：`.../behavior-changes-{N}?hl=zh-cn`
- 与 1.1 对比，避免重复记两条；target 专有条目标 **「仅 target≥N」**。

#### 1.3 行为变更深度子页（`changes/`）

- 对步骤 1.1–1.2 及 **migration** 正文中出现的  
  `/about/versions/{N}/changes/*` **逐页 WebFetch**。
- Android 17 已知子页见 [reference.md § Android 17 扫描结果](reference.md#android-17-扫描结果)；其它 N 以扫描为准。

#### 1.4 行为变更相关参考子页

- 阅读 migration 链接的辅助页，例如（存在则读）：
  - `.../changes/non-sdk-{N}`
  - `.../reference/compat-framework-changes`
- 用于测试开关、非 SDK 接口与兼容框架，不替代 1.1–1.2 主清单。

### 2. 探索新功能和 API

#### 2.1 功能总览页

- 阅读：`.../features?hl=zh-cn`
- 按官方章节（Core / Privacy / Security / Media / Connectivity / UX 等）列出 **新 API、新权限、新 Manifest 属性**。

#### 2.2 功能深度子页（`features/`）

- 扫描 features 与版本首页中的 `.../features/*`，**逐页 WebFetch**（如 Android 17 的 `features/contact-picker`）。

#### 2.3 交叉核对

- **release-notes**：beta/最终版补充项与已修复问题。
- **migration / setup-sdk**：构建、测试、兼容 toggles、模拟器/真机要求。
- 需要 API 差异表时，读 features 页给出的 **API diff report** 链接（若有）。

### 3. 产出与代码落地

#### 3.1 适配摘要（回复或 issue 均可）

```markdown
## Android {N} 适配摘要

### 行为变更 · 所有应用
| 变更 | 影响模块/场景 | 建议动作 | 官方锚点 |
|------|---------------|----------|----------|

### 行为变更 · targetSdk {N}+
| 变更 | 影响 | 建议动作 | 官方锚点 |
|------|------|----------|----------|

### 新功能 / API（可选实现）
| 能力 | API/权限 | 仓库落点建议 | 文档 |
|------|----------|--------------|------|

### 测试建议
- [ ] 在 API {level} 模拟器/真机跑核心流程
- [ ] 若升级 targetSdk：复测 1.2 条目 + Play 目标平台要求
```

#### 3.2 代码与文档

- 新增/修改 Java 或 Kotlin 方法前：**Read** `code-method-normalize`。
- `@RequiresApi` / `VersionUtils.is*()` 与 **API level** 一致；官方链接放在类或方法 `<pre>` 的 `@see <a href="...">`。
- 行为变更 **缓解**（如后台音频、MessageQueue 反射、SMS OTP）优先 **安全默认 + 可选 API**，与 DevUtils 工具类风格一致。

### 4. 扫描子目录（必做）

对 `{N}` 执行链接发现（Agent 可用 shell 或解析 WebFetch 正文）：

```bash
# 将 17 换成目标 N；输出去重后的 /about/versions/{N}/ 路径
for u in \
  "https://developer.android.com/about/versions/{N}?hl=zh-cn" \
  "https://developer.android.com/about/versions/{N}/behavior-changes-all?hl=zh-cn" \
  "https://developer.android.com/about/versions/{N}/behavior-changes-{N}?hl=zh-cn" \
  "https://developer.android.com/about/versions/{N}/features?hl=zh-cn" \
  "https://developer.android.com/about/versions/{N}/migration?hl=zh-cn"; do
  curl -sL "$u" | grep -oE 'https://developer\.android\.com/about/versions/{N}[^"<> ]+' | sed 's/?hl=zh-cn//;s/?hl=en//'
done | sort -u
```

- 将结果分为 **`behavior-changes-*`**、**`changes/*`**、**`features/*`**、**`reference/*`**、**其它（migration/setup-sdk/release-notes/...）**。
- **凡未在 1–2 步读过的 `changes/*` 与 `features/*` 路径，补读后再更新摘要。**

## 工作流清单（可复制）

```
- [ ] 0. 确认 Android N、API level、是否升 targetSdk
- [ ] 1.1 WebFetch behavior-changes-all
- [ ] 1.2 WebFetch behavior-changes-{N}
- [ ] 1.3 WebFetch 全部 changes/* 子页
- [ ] 1.4 WebFetch non-sdk / compat-framework（若 migration 指向）
- [ ] 2.1 WebFetch features
- [ ] 2.2 WebFetch 全部 features/* 子页
- [ ] 2.3 WebFetch release-notes、migration、setup-sdk（按需）
- [ ] 4. 扫描链接并补读遗漏子页
- [ ] 3.1 输出适配摘要表
- [ ] 3.2 代码：Read code-method-normalize → 实现/修复 → 链官方 URL
```

## 与其它 Skill 的分工

| 场景 | Skill |
|------|--------|
| 平台行为变更、新 API、targetSdk 适配 | 本 Skill |
| 方法 JavaDoc/KDoc、`final`、安全返回 | `code-method-normalize` |
| 新增 Maven 依赖、AndroidX 版本 | `gradle-central-deps` / `gradle-third-party-version-upgrade` |
| DataBinding `app:binding_*` | `binding-adapter-from-source` |

## 附加说明

- 页面以 **英文** 为主时，`?hl=zh-cn` 可能仍部分英文；以正文为准，**链接用官方路径**。
- 官方页「Note: 详见 release notes」→ 必须打开 **release-notes** 补全。
- 更多 Android 17 已扫描路径与 API 对照见 [reference.md](reference.md)。
