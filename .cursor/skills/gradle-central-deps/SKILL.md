---
name: gradle-central-deps
description: >-
  在 Project 仓库中新增或引用 Gradle 依赖时，先查 `file/gradle/config.gradle` 与
  `file/gradle/config_libs.gradle` 是否已有坐标；按官方/非官方规则写入对应分组，并在
  `file/deps/deps_android.gradle` 或 `file/deps/deps_project.gradle` 中按现有风格引用。
  在用户要求添加 AndroidX、CameraX、Kotlin、Jetpack、第三方 Maven 依赖或修改 deps 清单时使用。
---

# Project 中心化依赖（Gradle）

根工程 `build.gradle` 已依次 `apply`：`file/gradle/config.gradle`（`ext.deps`）、`file/gradle/config_libs.gradle`（`ext.deps_lib`）。**任何新依赖必须先查是否已存在，禁止重复定义同一坐标。**

## 1. 查找是否已存在

按优先级全文检索（建议对 **artifact 名**、**group**、**已有 key** 各搜一次）：

| 文件 | 变量 | 用途 |
|------|------|------|
| `file/gradle/config.gradle` | `deps` | 构建插件、`dev`、**官方** `kotlin` / `androidx` 分组 |
| `file/gradle/config_libs.gradle` | `deps_lib` | **非 Android 官方**第三方库，按子分组组织 |

若已存在：跳到 **第 4 步**，只在本模块或 `file/deps/*.gradle` 中增加 `api` / `implementation` / `kapt` 引用，**不要**再写一条相同 GAV 的配置。

## 2. 不存在时：决定写入哪个文件、哪个分组

### 2.1 写入 `file/gradle/config.gradle`（`deps`）

在同时满足「官方」且语义属于下列范围时使用：

- **`kotlin` 分组**：`org.jetbrains.kotlin`、`org.jetbrains.kotlinx`、`org.jetbrains.dokka`；与 Kotlin/JetBrains 生态强相关、且现有 `kotlin` 分组里同类条目并列的 AndroidX KTX（若团队惯例放在 `kotlin` 而非纯 `androidx`，与文件内现有条目保持一致）。
- **`androidx` 分组**：`androidx.*` Jetpack/AndroidX 构件；`com.google.android.material:material` 等与官方文档并列的 Material 组件（本仓库当前放在 `androidx`）；`com.google.dagger:hilt-android*` 等本文件已有先例的官方栈。
- **`build` 分组**：Gradle 插件类坐标（与现有 `kotlin_gradle_plugin` 等并列）。
- **`dev` 分组**：仅 `io.github.afkt` Dev 系列，勿把外部库塞入。

**CameraX**（`androidx.camera:*`）属于 AndroidX 官方 → 新增键应放在 `config.gradle` 的 **`androidx`** 分组（除非仓库后续另有约定；以文件内同类 `androidx.*` 为准）。

### 2.2 写入 `file/gradle/config_libs.gradle`（`deps_lib`）

- 非 Google/JetBrains/AndroidX 官方发布、或 group 为第三方域名（如 `com.squareup`、`com.github.*`、`io.github.*` 等）→ 放入 **`deps_lib`**。
- 在现有子分组中选语义最接近的一个（如网络 `common`、图片 `image_widget`、路由 `router`）。**不要**把明显不属于该分组的库硬塞进去。
- 若确实没有合适分组：**在 `deps_lib` 顶层新增一个子 map 键**（命名风格与现有一致：小写 + 下划线，如 `camera_kit`），并在该块顶部用与全文件一致的 **区块注释**（`// ===...===` + 简短中文说明）。

### 2.3 与 `versions.gradle` 的关系

`config.gradle` / `config_libs.gradle` 首行均 `apply from: versions.gradle`。若版本需多处复用或与 Kotlin 版本对齐，可像 `kotlin-gradle-plugin` 那样使用 `${versions.xxx}`；否则可直接写死版本号（与现有条目风格一致）。

## 3. 新增条目时的注释与 key 命名

参考两文件中已有行：

- 可选：`// https://mvnrepository.com/artifact/...`
- 一行中文说明 + 官方文档或 GitHub 链接（与邻近条目同级）。
- **key**：全小写 + 下划线，语义清晰（如 `camerax_camera2`、`camerax_lifecycle`）；同一库多模块多个 key 时保持前缀一致。

## 4. 在业务侧引用（`file/deps`）

- **Android 库 / 示例里与 `deps` 相关的清单**：照 `file/deps/deps_android.gradle` —— `api deps.kotlin.<key>`、`api deps.androidx.<key>`，需要处理器时用 `kapt`（与模块现有写法一致）。
- **示例工程、第三方 lib 聚合清单**：照 `file/deps/deps_project.gradle` —— `api deps_lib.<子分组>.<key>`，并保留分组标题注释与简短中文/GitHub 说明。

具体模块若直接写依赖而不走 `deps`/`deps_lib`，仍应使用**同一字符串**（如 `api deps.androidx.camerax_core`），避免复制一份硬编码 GAV。

## 5. 自检清单

- [ ] 已在 `config.gradle` 与 `config_libs.gradle` 中确认无重复坐标。
- [ ] 官方/非官方归属与分组、注释风格与邻居一致。
- [ ] 若在 `deps` / `deps_lib` 新增 key，已在对应的 `file/deps/*.gradle` 或目标模块 `build.gradle` 中增加引用行。
- [ ] 需要注解处理器时同时声明 `kapt` 行（若该模块已使用 kapt）。
