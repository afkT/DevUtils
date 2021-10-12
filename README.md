
[![GitHub](https://img.shields.io/badge/GitHub-afkT-blue.svg)](https://github.com/afkT)
[![GitHub license](https://img.shields.io/github/license/afkT/DevUtils.svg)](https://github.com/afkT/DevUtils/blob/master/LICENSE)
[![MavenCentral](https://img.shields.io/badge/DevUtils-2.2.9-brightgreen.svg)](https://search.maven.org/search?q=io.github.afkt)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Utils](https://img.shields.io/badge/utils-200+-ff69b4.svg)](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

# About ( 持续更新，目前含 200+ 工具类 ) [Roadmap](https://github.com/afkT/DevUtils/projects/1)

> DevUtils 是一个 Android 工具库，主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用
>
> 该项目尽可能的便于开发人员，快捷、高效开发安全可靠的项目。

![module](https://github.com/afkT/DevUtils/raw/master/art/module.png)

## Documentation - [Lib](https://github.com/afkT/DevUtils/blob/master/lib) **（ 全部已迁移至 Maven Central ）**

### DevApp - Android 工具类库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

- [Use and Config](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/USE_CONFIG.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/CHANGELOG.md)

### DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/CHANGELOG.md)

### DevBase - Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库

- [README](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/CHANGELOG.md)

### DevBaseMVVM - MVVM ( ViewDataBinding + ViewModel ) 基类库

- [README](https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/CHANGELOG.md)

### DevEngine - 第三方框架解耦、一键替换第三方库

- [README](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/CHANGELOG.md)

### DevHttpCapture OKHttp 抓包工具库

- [README](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/CHANGELOG.md)

### DevHttpCaptureCompiler OKHttp 抓包工具库 ( 可视化功能 )

- [README](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/CHANGELOG.md)

### DevWidget - 自定义 View UI 库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README_API.md)

- [Preview README](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md)

### DevEnvironment - Android 环境配置切换库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/Environment)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironment/CHANGELOG.md)

### DevJava - Java 工具类库 ( 不依赖 android api )

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/CHANGELOG.md)

## Other

> [DevBase2](https://github.com/afkT/DevUtils/blob/master/lib/DevBase2/src/main/java/dev/base) Base 基础代码 ( 非基类库 )
>
> [DevOther](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/README.md) 第三方库封装、以及部分特殊工具类等，方便 copy 封装类使用 
>
> [JavaDoc API Generate](https://github.com/afkT/JavaDoc) 该工具类 (DevUtils) API 文档，是通过 JavaDoc 项目读取 class 信息生成，并且进行代码、注释间距规范检测，生成效果示范 [DevApp - API](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)
>
> [DevComponent](https://github.com/afkT/DevComponent) 【100% Kotlin 实现 Android 项目组件化示例代码】基于 Google JetPack AndroidX + Kotlin + Coroutines + MVVM 架构（DataBinding、ViewModel、Lifecycle）等最新技术栈进行组件化基础搭建，使用 ARouter 方案实现组件化


## Dev 系列全部 Lib Gradle

```java

// DevApp Android 工具类库
implementation 'io.github.afkt:DevAppX:2.2.9'

// DevAssist 快捷功能辅助类库
implementation 'io.github.afkt:DevAssist:1.2.7'

// DevBase Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库
implementation 'io.github.afkt:DevBase:1.0.7'

// DevBaseMVVM MVVM ( ViewDataBinding + ViewModel ) 基类库
implementation 'io.github.afkt:DevBaseMVVM:1.0.5'

// DevEngine 第三方框架解耦、一键替换第三方库
implementation 'io.github.afkt:DevEngine:1.0.0'

// DevHttpCapture OKHttp 抓包工具库
implementation 'io.github.afkt:DevHttpCapture:1.0.3'

// DevHttpCaptureCompiler OKHttp 抓包工具库 ( 可视化功能 )
debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.0.3'
releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.0.3'

// DevWidget 自定义 View UI 库
implementation 'io.github.afkt:DevWidgetX:1.1.1'

// DevEnvironment Android 环境配置切换库
implementation 'io.github.afkt:DevEnvironment:1.0.5'
debugAnnotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.0.5' // kaptDebug
releaseAnnotationProcessor 'io.github.afkt:DevEnvironmentCompilerRelease:1.0.5' // kaptRelease
//annotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.0.5' // kapt

// DevJava Java 工具类库 ( 不依赖 android api )
implementation 'io.github.afkt:DevJava:1.3.6' // 用于纯 Java 开发，如果依赖了 DevApp 则不需要依赖 DevJava
```

## [APK Demo](https://github.com/afkT/Resources/tree/main/APK)

## License

    Copyright 2018 afkT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    