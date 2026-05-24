# AFKT Project DevEngine Implementation Reference

本文件记录 DevEngine 实现工作流的路径清单、命名契约和同步点。常规执行顺序以 [SKILL.md](SKILL.md) 为准。

## 目标路径

| 位置 | 用途 | 示例 |
|------|------|------|
| `lib/DevAssist/src/main/java/dev/engine/<domain>` | Engine 接口层、门面、Config、Item、Result、callback/listener | `json/DevJSONEngine.kt`、`json/IJSONEngine.kt` |
| `lib/DevEngine/src/main/java/dev/engine/core/<domain>` | 第三方库解耦实现、配置转换、工具适配 | `json/engine_gson.kt`、`json/engine_fastjson.kt` |
| `lib/DevEngine/src/main/java/dev/engine/extensions/<domain>` | Kotlin extension 调用入口 | `json/json.kt` |
| `lib/DevEngine/src/main/java/dev/engine/DevEngine.kt` | 默认初始化、创建实现、set/get/assist 统一入口 | `defaultGsonEngineImpl()`、`newGsonEngineImpl()` |
| `lib/DevEngine/README.md` | 目录结构、依赖实现信息、可选实现方案与链接 | `### JSON 映射` |
| `file/gradle/config*.gradle`、`file/deps/*.gradle` | 第三方依赖坐标与模块引用 | 通过 `gradle-central-deps` 维护 |

## 命名契约

| 类型 | 约定 | 示例 |
|------|------|------|
| 领域包 | 小写功能域名，DevAssist 与 DevEngine 保持一致 | `json`、`log`、`permission` |
| core 文件 | `engine_<lib>.kt` | `engine_gson.kt`、`engine_timber_logger.kt` |
| 实现类 | `<Lib>EngineImpl` | `GsonEngineImpl`、`TimberEngineImpl` |
| 默认初始化方法 | `default<Lib>EngineImpl()` | `defaultGsonEngineImpl()` |
| 创建方法 | `new<Lib>EngineImpl()` | `newGsonEngineImpl()` |
| Engine 门面 | `DevXxxEngine` | `DevJSONEngine` |
| Engine 接口 | `IXxxEngine` | `IJSONEngine` |

## 已有领域速查

| 领域 | DevAssist 包 | DevEngine core | extensions |
|------|--------------|----------------|------------|
| BarCode | `barcode` | `core/barcode` | `extensions/barcode` |
| Cache | `cache` | `core/cache` | `extensions/cache` |
| Compress | `compress` | `core/compress` | `extensions/compress` |
| Image | `image` | `core/image` | `extensions/image` |
| JSON | `json` | `core/json` | `extensions/json` |
| KeyValue | `keyvalue` | `core/keyvalue` | `extensions/keyvalue` |
| Log | `log` | `core/log` | `extensions/log` |
| Media | `media` | `core/media` | `extensions/media` |
| Permission | `permission` | `core/permission` | `extensions/permission` |
| Storage | `storage` | `core/storage` | `extensions/storage` |
| Toast | `toast` | `core/toast` | `extensions/toast` |

## `DevEngine.kt` 同步点

新增实现时按文件既有分区同步：

- imports：引入 `dev.engine.core.<domain>.<Lib>EngineImpl` 与必要 Config。
- `initializeDefaultEngines`：只有该实现应成为默认实现时才接入；多个实现并存时保留当前默认并可注释备选。
- 默认初始化方法区：新增 `default<Lib>EngineImpl()`，内部调用 `new<Lib>EngineImpl().apply { DevXxxEngine.setEngine(this) }`。
- Engine set/get/assist：新增领域时才补 `setXxxEngine`、`getXxx`、`getXxxAssist`；仅新增同领域实现时通常不改这几组。
- 内置 Engine 实现区：新增 `new<Lib>EngineImpl()`，返回具体实现。

## README 同步点

更新 `lib/DevEngine/README.md` 时优先保持原有版式：

- 目录结构：新增领域时同步 `core` 与 `extensions` 树。
- 依赖实现信息：新增领域时补一行 `**Xxx ...**`。
- 项目类结构：新增或修改对应 `### Xxx` 小节。
- 可选实现方案：新增第三方库链接与实现类链接。
- 不要重排整份 README，不要无关更新版本号。

## JSON Engine 示例落点

创建或扩展 JSON Engine 时通常涉及：

| 文件或目录 | 处理内容 |
|------------|----------|
| `lib/DevAssist/src/main/java/dev/engine/json` | 读取 `DevJSONEngine`、`IJSONEngine`，确认序列化、反序列化、类型转换等接口 |
| `lib/DevEngine/src/main/java/dev/engine/core/json` | 新增 `engine_<lib>.kt`，必要时新增配置或工具适配 |
| `lib/DevEngine/src/main/java/dev/engine/extensions/json` | 同步外部调用入口 |
| `lib/DevEngine/src/main/java/dev/engine/DevEngine.kt` | 新增 `default<Lib>EngineImpl()`、`new<Lib>EngineImpl()`，按需调整默认实现 |
| `lib/DevEngine/README.md` | 同步 JSON 映射小节的可选实现方案 |

## 维护检索建议

- 查接口：搜索 `interface I<Domain>Engine`、`object Dev<Domain>Engine`。
- 查默认实现：搜索 `default.*EngineImpl`、`new.*EngineImpl`。
- 查 README 小节：搜索 `### <Domain>` 或中文领域名。
- 查扩展入口：搜索 `fun .*DevEngine.get<Domain>`、`DevEngine.get<Domain>()` 或同目录既有 extension。
