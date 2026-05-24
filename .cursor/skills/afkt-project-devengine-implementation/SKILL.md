---
name: afkt-project-devengine-implementation
description: >-
  新增或修改 DevUtils DevEngine 第三方框架解耦实现：读取 DevAssist Engine 接口、DevEngine core 实现、
  extensions 调用入口、默认初始化和 README，实现 JSON、Log、Image、Permission、Toast 等 Engine；
  在用户要求创建某个功能 Engine、新增 Engine 实现、替换第三方库或扩展 DevEngine 时使用。
---

# AFKT Project DevEngine Implementation

用于 DevUtils 私人工具库中新增或维护某类 Engine 实现。详情路径与同步点见 [reference.md](reference.md)。

## 路径锚点

| 符号 | 当前值 |
|------|--------|
| `DEVASSIST_ENGINE_ROOT` | `lib/DevAssist/src/main/java/dev/engine` |
| `DEVENGINE_ROOT` | `lib/DevEngine/src/main/java/dev/engine` |
| `DEVENGINE_CORE_ROOT` | `lib/DevEngine/src/main/java/dev/engine/core` |
| `DEVENGINE_EXT_ROOT` | `lib/DevEngine/src/main/java/dev/engine/extensions` |
| `DEVENGINE_README` | `lib/DevEngine/README.md` |
| `DEPS_ROOT` | `file/gradle` |

## 工作流

1. 明确目标 Engine 类型与第三方实现名，例如 JSON + Moshi、Log + Timber、Image + Glide。
2. 先读 `DEVASSIST_ENGINE_ROOT/<domain>` 下的 `DevXxxEngine`、`IXxxEngine`、Config、Item、Result、callback/listener 等接口，确认能力边界；不要让实现层扩展接口未定义的业务语义。
3. 读取 `DEVENGINE_CORE_ROOT/<domain>`、`DEVENGINE_EXT_ROOT/<domain>` 与 `DevEngine.kt` 中同领域已有实现，复用现有命名、注释、异常处理和默认初始化风格。
4. 如果缺少第三方依赖，先 Read `../gradle-central-deps/SKILL.md`，再更新集中依赖配置和目标模块依赖；不要直接硬编码重复 GAV。
5. 在 `core/<domain>` 新增或修改具体实现：文件名优先使用 `engine_<lib>.kt`，实现类命名为 `<Lib>EngineImpl`；仅在确有共享价值时新增 `Config` 或 `Utils`。
6. 在 `extensions/<domain>` 同步 Kotlin 调用入口，使外部仍通过 `DevEngine.getXxx()`、`DevXxxEngine` 或既有 extension 风格调用。
7. 同步 `DevEngine.kt`：补 import、`default<Lib>EngineImpl()`、`new<Lib>EngineImpl()`，并按需要接入 `completeInitialize` / `initializeDefaultEngines` 默认初始化。
8. 同步 `DEVENGINE_README` 的目录结构、依赖实现信息、对应 Engine 小节和实现链接；只更新目标小节，不重排整份 README。
9. 新增或改写方法注释时 Read `../code-method-normalize/SKILL.md`，保持 Kotlin KDoc / JavaDoc 规则一致。

## 例子：创建 JSON Engine

当用户要求创建 JSON Engine 或新增 JSON 第三方实现时，同时检查并按需更新：

- `DEVASSIST_ENGINE_ROOT/json`
- `DEVENGINE_CORE_ROOT/json`
- `DEVENGINE_EXT_ROOT/json`
- `DEVENGINE_ROOT/DevEngine.kt`
- `DEVENGINE_README`
- 需要依赖时再更新 `DEPS_ROOT` 相关文件

## 自检清单

- [ ] 已确认 DevAssist 接口能力边界，没有绕过接口直接暴露第三方库。
- [ ] core 实现、extensions 入口、`DevEngine.kt` 默认/创建方法命名一致。
- [ ] 默认初始化选择清晰；多个实现并存时没有无意替换现有默认实现。
- [ ] 第三方依赖走集中依赖规则，README 实现列表和链接已同步。
- [ ] 新增方法注释符合 `code-method-normalize`。
