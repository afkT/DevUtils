
<h1 align="center">DevUtils</h1>


<p align="center">
	<a href="https://github.com/afkT">
		<img alt="Profile" src="https://img.shields.io/badge/GitHub-afkT-orange.svg" />
	</a>
	<a href="https://github.com/afkT/DevUtils/blob/master/LICENSE">
		<img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" />
	</a>
	<a href="https://search.maven.org/search?q=io.github.afkt">
		<img alt="Version" src="https://img.shields.io/badge/Maven-Dev-5776E0.svg" />
	</a>
	<a href="https://android-arsenal.com/api?level=21">
		<img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" />
	</a>
	<a href="https://search.maven.org/search?q=io.github.afkt">
		<img alt="Version" src="https://img.shields.io/badge/DevUtils-2.5.2-yellow.svg" />
	</a>
	<a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md">
		<img alt="Utils" src="https://img.shields.io/badge/Utils-300+-critical.svg" />
	</a>
</p>


<p align="center">
	🔥 ( 持续更新，目前含 300+ 工具类 )
	<br>
	DevUtils 是一个 Android 工具库，主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用。
	<br>
	该项目尽可能的便于开发人员，快捷、高效开发安全可靠的项目。
</p>


<p align="center">
	<b>
		<a href="https://github.com/afkT/DevUtils/blob/master/README/android_standard.md">Android 规范</a>
	</b>、
	<b>
		<a href="https://github.com/afkT/DevUtils/blob/master/README/java_standard.md">Java 规范</a>
	</b>、
	<b>
		<a href="https://github.com/afkT/DevUtils/blob/master/README/git_standard.md">Git 规范</a>
	</b>
</p>


![module][dev_module_img]


## Android 版本适配信息

- [ ] 适配 Android 16 ( ??? ) ???
- [x] 适配 Android 15 ( VanillaIceCream ) [DevApp v2.4.8+][DevApp v2.4.8+]
- [x] 适配 Android 14 ( UpsideDownCake ) [DevApp v2.4.4+][DevApp v2.4.4+]
- [x] 适配 Android 13 ( Tiramisu ) [DevApp v2.4.3+][DevApp v2.4.3+]
- [x] 适配 Android 11 ( R ) [DevApp v2.0.8+][DevApp v2.0.8+]
- [x] 适配 Android 10 ( Q ) [DevApp v1.8.6+][DevApp v1.8.6+]


## Gradle、DevApp、SDK、Kotlin 版本信息

| Gradle        | DevApp        | MinSdkVersion | CompileSdkVersion | Kotlin          |
|---------------|---------------|---------------|-------------------|-----------------|
| 4.1.1 - 4.2.1 | 2.1.0 - 2.2.8 | 14            | 30                | 1.4.10 - 1.4.31 |
| 7.0.2 - 7.3.0 | 2.2.9 - 2.4.2 | 14            | 30 - 32           | 1.5.20 - 1.7.10 |
| 8.0.2 - 8.2.1 | 2.4.3 - 2.4.4 | 14            | 33 - 34           | 1.7.10 - 1.9.22 |
| 8.4.0         | 2.4.5 - 2.4.6 | 19            | 34                | 1.9.23          |
| 8.7.3         | 2.4.7 - 2.4.8 | 21            | 35                | 2.1.0           |
| 8.11.1        | 2.4.9 - 2.5.0 | 23            | 36                | 2.2.10          |
| 8.13.2        | 2.5.1         | 23            | 36                | 2.3.0           |


## 项目目录结构（ 全部已迁移至 Maven Central ）

```
- lib                                            | 根目录
   - DevApp                                      | Android 工具类库
   - DevAssist                                   | 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
   - DevBase                                     | DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库
   - DevEngine                                   | 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
   - DevSimple                                   | 简单敏捷开发库
   - DevWidget                                   | 自定义 View UI 库
   - DevDeprecated                               | Dev 系列库弃用代码统一存储库
   - Environment                                 | Android 环境配置切换库
      - DevEnvironment                           | 环境切换可视化 UI 操作
      - DevEnvironmentBase                       | 注解类、实体类、监听事件等通用基础
      - DevEnvironmentCompiler                   | Debug ( 打包 / 编译 ) 生成实现代码
      - DevEnvironmentCompilerRelease            | Release ( 打包 / 编译 ) 生成实现代码
   - HttpCapture                                 | Android 抓包库
      - DevHttpCapture                           | OkHttp 抓包工具库
      - DevHttpCaptureCompiler                   | Debug ( 打包 / 编译 ) 实现代码 ( 可视化 UI 操作 )
      - DevHttpCaptureCompilerRelease            | Release ( 打包 / 编译 ) 实现代码
   - HttpRequest                                 | Android 网络请求库
      - DevRetrofit                              | Retrofit + Kotlin Coroutines 封装
      - DevHttpManager                           | OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
   - DevJava                                     | Java 工具类库 ( 不依赖 android api )
```


## API 文档

- **[DevApp - Android 工具类库][DevApp API]**
- [DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等][DevAssist API]
- [DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库][DevBase API]
- [DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用][DevEngine API]
- [DevSimple - 简单敏捷开发库][DevSimple API]
- [DevWidget - 自定义 View UI 库][DevWidget API]
- [DevRetrofit - Retrofit + Kotlin Coroutines 封装][DevRetrofit API]
- [DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )][DevHttpManager API]
- [DevHttpCapture - OkHttp 抓包工具库][DevHttpCapture API]
- [DevEnvironment - Android 环境配置切换库][DevEnvironment API]
- [DevDeprecated - Dev 系列库弃用代码统一存储库][DevDeprecated API]
- [DevJava - Java 工具类库 ( 不依赖 android api )][DevJava API]


## ChangeLog 更新日志

- **[DevApp - Android 工具类库][DevApp ChangeLog]**
- [DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等][DevAssist ChangeLog]
- [DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库][DevBase ChangeLog]
- [DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用][DevEngine ChangeLog]
- [DevSimple - 简单敏捷开发库][DevSimple ChangeLog]
- [DevWidget - 自定义 View UI 库][DevWidget ChangeLog]
- [DevRetrofit - Retrofit + Kotlin Coroutines 封装][DevRetrofit ChangeLog]
- [DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )][DevHttpManager ChangeLog]
- [DevHttpCapture - OkHttp 抓包工具库][DevHttpCapture ChangeLog]
- [DevEnvironment - Android 环境配置切换库][DevEnvironment ChangeLog]
- [DevDeprecated - Dev 系列库弃用代码统一存储库][DevDeprecated ChangeLog]
- [DevJava - Java 工具类库 ( 不依赖 android api )][DevJava ChangeLog]


## 其他

- **[DevComponent][DevComponent]** 【100% Kotlin 实现 Android 项目组件化示例代码】
  基于 Android JetPack + Kotlin + Coroutines + MVVM 架构（DataBinding、ViewModel、Lifecycle）
  等最新技术栈进行组件化基础搭建，使用 ARouter 方案实现组件化

- **[DevUtils-repo][DevUtils-repo]** 该项目是针对 [DevUtils][DevUtils]
  第三方库封装扩展、新技术 Demo 编写、大文件资源等迁移存储仓库，
  减少 `DevUtils` 仓库大小方便快速 clone，并让 `DevUtils` 项目**更加纯粹**只保留 Dev 系列开发库相关代码。

- **[JavaDoc API Generate][JavaDoc]** 该工具类 (DevUtils) API 文档，是通过 JavaDoc 项目读取 class 信息生成，
  并且进行代码、注释间距规范检测，生成效果示范 [DevApp API 文档][DevApp API]

- **[DevOther][DevOther]** 功能、工具类二次封装，直接 copy 使用


## Download 预览

[DevWidget - 自定义 View UI 库部分效果预览][DevWidget Preview]


## Dev 系列开发库全部 Lib Gradle

```gradle

// DevApp - Android 工具类库
implementation 'io.github.afkt:DevAppX:2.5.2'

// DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
implementation 'io.github.afkt:DevAssist:1.4.5'

// DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库
implementation 'io.github.afkt:DevBase:1.2.2'

// DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
implementation 'io.github.afkt:DevEngine:1.1.7'

// DevSimple - 简单敏捷开发库
implementation 'io.github.afkt:DevSimple:1.0.6'

// DevWidget - 自定义 View UI 库
implementation 'io.github.afkt:DevWidgetX:1.2.7'

// DevRetrofit - Retrofit + Kotlin Coroutines 封装
implementation 'io.github.afkt:DevRetrofit:1.0.9'

// DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
implementation 'io.github.afkt:DevHttpManager:1.1.0'

// DevHttpCapture - OkHttp 抓包工具库
implementation 'io.github.afkt:DevHttpCapture:1.2.1'

// DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )
debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.2.1'
releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.2.1'

// DevEnvironment - Android 环境配置切换库
implementation 'io.github.afkt:DevEnvironment:1.1.9'
debugAnnotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.9' // kaptDebug
releaseAnnotationProcessor 'io.github.afkt:DevEnvironmentCompilerRelease:1.1.9' // kaptRelease
//annotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.9' // kapt

// DevDeprecated - Dev 系列库弃用代码统一存储库
implementation 'io.github.afkt:DevDeprecated:1.0.2'

// DevJava - Java 工具类库 ( 不依赖 android api )
implementation 'io.github.afkt:DevJava:1.5.5'
```




<!-- === -->
<!-- 链接 -->
<!-- === -->

<!-- ======== -->
<!-- DevUtils -->
<!-- ======== -->

[DevUtils]: https://github.com/afkT/DevUtils
[DevApp API]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
[DevApp ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md
[DevAssist API]: https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
[DevAssist ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/CHANGELOG.md
[DevBase API]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
[DevBase ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/CHANGELOG.md
[DevEngine API]: https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
[DevEngine ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/CHANGELOG.md
[DevSimple API]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/README.md
[DevSimple ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/CHANGELOG.md
[DevWidget API]: https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
[DevWidget ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md
[DevWidget Preview]: https://github.com/afkT/DevUtils-repo/blob/main/local_dev/DevWidget_Preview.md
[DevRetrofit API]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/README.md
[DevRetrofit ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/CHANGELOG.md
[DevHttpManager API]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/README.md
[DevHttpManager ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/CHANGELOG.md
[DevHttpCapture API]: https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/README.md
[DevHttpCapture ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/CHANGELOG.md
[DevEnvironment API]: https://github.com/afkT/DevUtils/blob/master/lib/Environment
[DevEnvironment ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/CHANGELOG.md
[DevDeprecated API]: https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/README.md
[DevDeprecated ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/CHANGELOG.md
[DevJava API]: https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
[DevJava ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevJava/CHANGELOG.md

<!-- ============== -->
<!-- DevUtils Other -->
<!-- ============== -->

[JavaDoc]: https://github.com/afkT/JavaDoc
[DevComponent]: https://github.com/afkT/DevComponent
[DevUtils-repo]: https://github.com/afkT/DevUtils-repo
[DevOther]: https://github.com/afkT/DevUtils-repo/blob/main/local_dev/DevOther

<!-- ======= -->
<!-- 零散汇总 -->
<!-- ======= -->

[Project Details README]: https://github.com/afkT/DevUtils/blob/master/README_PROJECT.md
[dev_module_img]: https://github.com/afkT/DevUtils/raw/master/art/module.png

<!-- ====== -->
<!-- 版本信息 -->
<!-- ====== -->

[DevApp v2.4.8+]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-248-2025-03-21
[DevApp v2.4.4+]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-244-2024-01-18
[DevApp v2.4.3+]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-243-2023-07-01
[DevApp v2.0.8+]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-208-2020-10-29
[DevApp v1.8.6+]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-186-2019-12-25