
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
		<img alt="Version" src="https://img.shields.io/badge/DevUtils-2.4.6-yellow.svg" />
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

![module](https://github.com/afkT/DevUtils/raw/master/art/module.png)


## Android 版本适配信息

- [ ] 适配 Android 15 ( VanillaIceCream ) ???
- [x] 适配 Android 14 ( UpsideDownCake ) [DevApp v2.4.4+](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-244-2024-01-18)
- [x] 适配 Android 13 ( Tiramisu ) [DevApp v2.4.3+](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-243-2023-07-01)
- [x] 适配 Android 11 ( R ) [DevApp v2.0.8+](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-208-2020-10-29)
- [x] 适配 Android 10 ( Q ) [DevApp v1.8.6+](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md#version-186-2019-12-25)


## 项目目录结构（ 全部已迁移至 Maven Central ）

```
- lib                                            | 根目录
   - DevApp                                      | Android 工具类库
   - DevAssist                                   | 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
   - DevBase                                     | Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库
   - DevBaseMVVM                                 | MVVM ( ViewDataBinding + ViewModel ) 基类库
   - DevEngine                                   | 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
   - DevHttpCapture                              | OkHttp 抓包工具库
   - DevHttpManager                              | OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
   - DevJava                                     | Java 工具类库 ( 不依赖 android api )
   - DevMVVM                                     | DataBinding 工具类库
   - DevRetrofit                                 | Retrofit + Kotlin Coroutines 封装
   - DevSimple                                   | 简单敏捷开发库集合
      - DevAgile                                 | 简单敏捷开发库 ( 常用、零散代码 )
      - DevSimple                                | 简单敏捷开发库
   - DevWidget                                   | 自定义 View UI 库
   - Environment                                 | Android 环境配置切换库
      - DevEnvironment                           | 环境切换可视化 UI 操作
      - DevEnvironmentBase                       | 注解类、实体类、监听事件等通用基础
      - DevEnvironmentCompiler                   | Debug ( 打包 / 编译 ) 生成实现代码
      - DevEnvironmentCompilerRelease            | Release ( 打包 / 编译 ) 生成实现代码
   - HttpCapture                                 | OkHttp 抓包工具库 ( 可视化功能 )
      - DevHttpCaptureCompiler                   | Debug ( 打包 / 编译 ) 实现代码
      - DevHttpCaptureCompilerRelease            | Release ( 打包 / 编译 ) 实现代码
   - LocalModules                                | 本地 Module lib ( 非发布库 )
      - DevBaseView                              | 通用基础 View 封装 ( 非基类库 )
      - DevOther                                 | 功能、工具类二次封装, 直接 copy 使用【 大部分迁移至 DevUtils-repo 】
      - DevSKU                                   | 商品 SKU 组合封装实现
```


## API 文档

- **[DevApp - Android 工具类库][DevApp API]**
- [DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等][DevAssist API]
- [DevBase - Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库][DevBase API]
- [DevBaseMVVM - MVVM ( ViewDataBinding + ViewModel ) 基类库][DevBaseMVVM API]
- [DevMVVM - DataBinding 工具类库][DevMVVM API]
- [DevAgile - 简单敏捷开发库 ( 常用、零散代码 )][DevAgile API]
- [DevSimple - 简单敏捷开发库][DevSimple API]
- [DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用][DevEngine API]
- [DevHttpCapture - OkHttp 抓包工具库][DevHttpCapture API]
- [DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )][DevHttpCaptureCompiler API]
- [DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )][DevHttpManager API]
- [DevRetrofit - Retrofit + Kotlin Coroutines 封装][DevRetrofit API]
- [DevWidget - 自定义 View UI 库][DevWidget API]
- [DevEnvironment - Android 环境配置切换库][DevEnvironment API]
- [DevJava - Java 工具类库 ( 不依赖 android api )][DevJava API]


## ChangeLog 更新日志

- **[DevApp - Android 工具类库][DevApp ChangeLog]**
- [DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等][DevAssist ChangeLog]
- [DevBase - Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库][DevBase ChangeLog]
- [DevBaseMVVM - MVVM ( ViewDataBinding + ViewModel ) 基类库][DevBaseMVVM ChangeLog]
- [DevMVVM - DataBinding 工具类库][DevMVVM ChangeLog]
- [DevAgile - 简单敏捷开发库 ( 常用、零散代码 )][DevAgile ChangeLog]
- [DevSimple - 简单敏捷开发库][DevSimple ChangeLog]
- [DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用][DevEngine ChangeLog]
- [DevHttpCapture - OkHttp 抓包工具库][DevHttpCapture ChangeLog]
- [DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )][DevHttpCaptureCompiler ChangeLog]
- [DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )][DevHttpManager ChangeLog]
- [DevRetrofit - Retrofit + Kotlin Coroutines 封装][DevRetrofit ChangeLog]
- [DevWidget - 自定义 View UI 库][DevWidget ChangeLog]
- [DevEnvironment - Android 环境配置切换库][DevEnvironment ChangeLog]
- [DevJava - Java 工具类库 ( 不依赖 android api )][DevJava ChangeLog]


## 其他






[DevApp API]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
[DevApp ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md
[DevAssist API]: https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
[DevAssist ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/CHANGELOG.md
[DevBase API]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
[DevBase ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/CHANGELOG.md
[DevBaseMVVM API]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
[DevBaseMVVM ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/CHANGELOG.md
[DevMVVM API]: https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md
[DevMVVM ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/CHANGELOG.md
[DevAgile API]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevAgile/README.md
[DevAgile ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevAgile/CHANGELOG.md
[DevSimple API]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md
[DevSimple ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/CHANGELOG.md
[DevEngine API]: https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
[DevEngine ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/CHANGELOG.md
[DevHttpCapture API]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
[DevHttpCapture ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/CHANGELOG.md
[DevHttpCaptureCompiler API]: https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/README.md
[DevHttpCaptureCompiler ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/CHANGELOG.md
[DevHttpManager API]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
[DevHttpManager ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/CHANGELOG.md
[DevRetrofit API]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
[DevRetrofit ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/CHANGELOG.md
[DevWidget API]: https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
[DevWidget ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md
[DevWidget Preview]: https://github.com/afkT/DevUtils-repo/blob/main/lib/DevWidget_Preview.md
[DevEnvironment API]: https://github.com/afkT/DevUtils/blob/master/lib/Environment
[DevEnvironment ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironment/CHANGELOG.md
[DevJava API]: https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
[DevJava ChangeLog]: https://github.com/afkT/DevUtils/blob/master/lib/DevJava/CHANGELOG.md