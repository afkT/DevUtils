# Android 版本平台适配 — 参考

与 [SKILL.md](SKILL.md) 配套：URL 规律、链接扫描、Android 17 实例子页。其它版本 **N** 用同一套路替换路径中的数字。

## 路径规律

| 模式 | 含义 |
|------|------|
| `/about/versions/{N}` | 版本枢纽 |
| `/behavior-changes-all` | 运行于 N 的全部应用 |
| `/behavior-changes-{N}` | `targetSdkVersion >= N` |
| `/changes/{slug}` | 单主题行为变更详解 |
| `/features` | 新功能与 API 总览 |
| `/features/{slug}` | 单功能详解 |
| `/changes/non-sdk-{N}` | 非 SDK 接口限制（migration 常链入） |
| `/reference/compat-framework-changes` | 兼容框架 / toggles |
| `/migration` | 迁移与测试流程 |
| `/setup-sdk` | SDK、API level、`VERSION_CODES` |
| `/release-notes` | Beta / 最终版补充 |

`hl=zh-cn` 建议始终带上；去重链接时去掉 `?hl=*` 与锚点 `#...` 再比较路径。

## Android 17 扫描结果

自 **2026-05** 对版本首页、`behavior-changes-*`、`features`、`migration` 爬链整理（新 Beta 可能增页，执行 Skill 时 **重新跑扫描**）。

### 行为变更主站（步骤 1.1 / 1.2）

| 页面 | URL |
|------|-----|
| 所有应用 | https://developer.android.com/about/versions/17/behavior-changes-all?hl=zh-cn |
| target 17 | https://developer.android.com/about/versions/17/behavior-changes-17?hl=zh-cn |

**1.1 主题摘录（便于对照，以官网为准）**

- Core：应用内存上限（`ApplicationExitInfo` / MemoryLimiter）
- Privacy：SMS OTP（WebOTP / Retriever 等）
- Security：`usesCleartextTraffic` 弃用计划、隐式 URI 授权、Keystore 密钥数量、跨配置 loopback
- UX：旋转后 IME 可见性
- Human input：触摸板 pointer capture 相对事件
- Media：后台音频加固 → 见 `changes/bg-audio`
- Connectivity：蓝牙自主重配对

**1.2 主题摘录（target 17+）**

- Core：无锁 `MessageQueue` → `changes/messagequeue`；`static final` 反射不可改
- Accessibility：CJKV IME / `TextAttribute`
- Privacy：ECH、局域网权限、物理键盘密码显示、标准 SMS OTP
- Security：Activity Security / BAL、CT 默认开启、Safer DCL 扩展 native、CP2 PII/SQL
- Media：后台音频（更严）→ `changes/bg-audio`
- 大屏：方向/尺寸限制忽略 → `changes/ff-restrictions-ignored`
- Connectivity：`BluetoothSocket.read()` RFCOMM 返回 -1

### `changes/` 子页（步骤 1.3）

| slug | URL | 关联主条目 |
|------|-----|------------|
| `bg-audio` | https://developer.android.com/about/versions/17/changes/bg-audio?hl=zh-cn | 后台播放 / 焦点 / 音量 |
| `messagequeue` | https://developer.android.com/about/versions/17/changes/messagequeue?hl=zh-cn | MessageQueue 无锁实现 |
| `ff-restrictions-ignored` | https://developer.android.com/about/versions/17/changes/ff-restrictions-ignored?hl=zh-cn | 大屏方向与尺寸限制 |

### 行为相关其它（步骤 1.4）

| 页面 | URL |
|------|-----|
| 非 SDK 接口 | https://developer.android.com/about/versions/17/changes/non-sdk-17?hl=zh-cn |
| 兼容框架变更 | https://developer.android.com/about/versions/17/reference/compat-framework-changes?hl=zh-cn |

### `features/` 子页（步骤 2.2）

| slug | URL |
|------|-----|
| （总览） | https://developer.android.com/about/versions/17/features?hl=zh-cn |
| `contact-picker` | https://developer.android.com/about/versions/17/features/contact-picker?hl=zh-cn |

**features 总览常见块（实现前查 API Reference）**

- ProfilingManager 新 trigger（含 `TRIGGER_TYPE_ANOMALY`）
- JobDebugInfo / `getPendingJobReasonStats`
- AlarmManager `setExactAndAllowWhileIdle` + `OnAlarmListener`
- ECH、Contacts Picker、AAPM、PQC 签名
- Assistant 音量流、Handoff、Live Update 语义色、UWB DL-TDoA 等

### 枢纽与其它（步骤 2.3 / 测试）

| 页面 | URL |
|------|-----|
| 首页 | https://developer.android.com/about/versions/17?hl=zh-cn |
| 概览 | https://developer.android.com/about/versions/17/overview?hl=zh-cn |
| 迁移 | https://developer.android.com/about/versions/17/migration?hl=zh-cn |
| SDK | https://developer.android.com/about/versions/17/setup-sdk?hl=zh-cn |
| 发布说明 | https://developer.android.com/about/versions/17/release-notes?hl=zh-cn |
| 获取系统映像 | https://developer.android.com/about/versions/17/get?hl=zh-cn |
| 下载 | https://developer.android.com/about/versions/17/download?hl=zh-cn |

QPR 子路径（如 `qpr1/`）仅在用户明确做 QPR 适配时读取。

## API level 与 `VERSION_CODES`（Android 17）

**扫描日期**：2026-05。执行 Skill 时仍须重新读取 `setup-sdk` 与 API Reference；下表仅作仓库对照参考。

| 字段 | 值 |
|------|-----|
| API level | 37 |
| 文档用语 | Android 17 (API level 37) |
| 仓库对照方式 | 优先检索 `{DEVAPP_ROOT}=lib/DevApp` 下既有版本判断封装；Android 17 以 SDK 引入的常量为准 |

升级 `compileSdk` / `targetSdk` 时同步查 **setup-sdk** 与 AGP 兼容说明，不单改数字。

## 仓库落点发现（非固定类名锚点）

| 符号 | 当前值（DevUtils） | 用途 |
|------|-------------------|------|
| `DEVAPP_ROOT` | `lib/DevApp` | Android 平台工具类与版本判断封装优先落点 |

**扫描日期**：2026-05。以下类名只是当时的**扫描示例**，用于提示可检索的领域方向；重构或迁移后以工作区检索结果为准，**不要**在 SKILL 正文固定依赖这些类名。

| 区域 | 说明 |
|------|------|
| `IntentUtils.removeLaunchSecurityProtection` | Android 16+ Intent 启动侧加固（见 method-normalize 示例） |
| `JobSchedulerUtils` | 类 `<pre>` 链 16 行为变更与 17 features；`getPendingJobReasonStats` 等 |
| `ProcessUtils` | 类文档链 17 `behavior-changes-all` |
| `ScreenUtils` | `BAKLAVA` 大屏相关逻辑 |

新增封装时优先扩展现有 `*Utils`，避免重复版本判断工具。建议先用 **Glob / 内容检索** 定位相近落点：

| 查找目标 | 建议模式 |
|----------|----------|
| 版本判断封装与平台常量 | Glob `{DEVAPP_ROOT}/**/*Version*.*`、`{DEVAPP_ROOT}/**/*Utils.*` |
| 按领域关键词缩小范围 | Glob `{DEVAPP_ROOT}/**/*Job*.*`、`{DEVAPP_ROOT}/**/*Intent*.*` 等 |
| 确认 API / 常量是否已有封装 | 内容检索具体 API 名、权限名、行为变更关键词 |

Agent 在实现前应把 `Job`、`Process`、`Screen`、`Intent` 替换为当前适配项的领域词；若无相近文件，再按仓库包结构新增同领域工具类。

## 扫描命令模板

将 `17` 替换为目标 **N**（与 SKILL.md §4 相同）：

```bash
N=17
for u in \
  "https://developer.android.com/about/versions/${N}?hl=zh-cn" \
  "https://developer.android.com/about/versions/${N}/behavior-changes-all?hl=zh-cn" \
  "https://developer.android.com/about/versions/${N}/behavior-changes-${N}?hl=zh-cn" \
  "https://developer.android.com/about/versions/${N}/features?hl=zh-cn" \
  "https://developer.android.com/about/versions/${N}/migration?hl=zh-cn"; do
  curl -sL "$u" | grep -oE "https://developer\.android\.com/about/versions/${N}[^\"<> ]+" \
    | sed 's/?hl=zh-cn//;s/?hl=en//;s/#.*//'
done | sort -u
```
