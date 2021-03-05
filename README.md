
[![GitHub](https://img.shields.io/badge/GitHub-afkT-blue.svg)](https://github.com/afkT)
[![GitHub license](https://img.shields.io/github/license/afkT/DevUtils.svg)](https://github.com/afkT/DevUtils/blob/master/LICENSE)
[![Bintray](https://img.shields.io/badge/DevUtils-2.1.9-brightgreen.svg)](https://bintray.com/afkt/maven/DevAppX)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Utils](https://img.shields.io/badge/utils-160+-ff69b4.svg)](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

# About ( 持续更新，目前含 160+ 工具类 ) [Roadmap](https://github.com/afkT/DevUtils/projects/1)

> DevUtils 是一个 Android 工具库，主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用
>
> 该项目尽可能的便于开发人员，快捷、高效开发安全可靠的项目。

![module](https://github.com/afkT/DevUtils/raw/master/art/module.png)

## Documentation - [Lib](https://github.com/afkT/DevUtils/blob/master/lib)

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

### DevJava - Java 工具类库 ( 不依赖 android api )

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/CHANGELOG.md)

### DevWidget - 自定义 View UI 库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README_API.md)

- [Preview README](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md)

### DevEnvironment - Android 环境配置切换库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/Environment)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironment/CHANGELOG.md)

## Other

> [DevBase2](https://github.com/afkT/DevUtils/blob/master/lib/DevBase2/src/main/java/dev/base) Base 基础代码 ( 非基类库 )
>
> [DevOther](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/README.md) 第三方库封装、以及部分特殊工具类等，方便 copy 封装类使用 
>
> [JavaDoc API Generate](https://github.com/afkT/JavaDoc) 该工具类 (DevUtils) API 文档，是通过 JavaDoc 项目读取 class 信息生成，并且进行代码、注释间距规范检测，生成效果示范 [DevApp - API](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)


## Dev 系列全部 Lib Gradle

```java

// DevApp Android 工具类库
implementation 'com.afkt:DevAppX:2.1.9'

// DevAssist 快捷功能辅助类库
implementation 'com.afkt:DevAssist:1.1.0'

// DevBase Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库
implementation 'com.afkt:DevBase:1.0.4'

// DevBaseMVVM MVVM ( ViewDataBinding + ViewModel ) 基类库
implementation 'com.afkt:DevBaseMVVM:1.0.2'

// DevJava Java 工具类库 ( 不依赖 android api )
implementation 'com.afkt:DevJava:1.2.9' // 用于纯 Java 开发，如果依赖了 DevApp 则不需要依赖 DevJava

// DevWidget 自定义 View UI 库
implementation 'com.afkt:DevWidgetX:1.0.8'

// DevEnvironment Android 环境配置切换库
implementation 'com.afkt:DevEnvironment:1.0.2'
debugAnnotationProcessor 'com.afkt:DevEnvironmentCompiler:1.0.2' // kaptDebug
releaseAnnotationProcessor 'com.afkt:DevEnvironmentCompilerRelease:1.0.2' // kaptRelease
//annotationProcessor 'com.afkt:DevEnvironmentCompiler:1.0.2' // kapt
```

## APK Demo

| [下载 DevUtils APK-Demo](https://github.com/afkT/DevUtils/raw/master/art/apk/app.apk) | [下载 AppInfoKtx APK-Demo](https://github.com/afkT/DevUtils/raw/master/art/apk/AppInfoKtx.apk) |
|:--:|:--:|
| ![apk_qrcode](https://github.com/afkT/DevUtils/raw/master/art/apk/app_apk_qrcode.png) | ![apk_qrcode](https://github.com/afkT/DevUtils/raw/master/art/apk/appinfo_apk_qrcode.png) |

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
    