
[![GitHub](https://img.shields.io/badge/GitHub-afkT-blue.svg)](https://github.com/afkT)
[![GitHub license](https://img.shields.io/github/license/afkT/DevUtils.svg)](https://github.com/afkT/DevUtils/blob/master/LICENSE)
[![Bintray](https://img.shields.io/badge/DevUtils-1.9.2-brightgreen.svg)](https://bintray.com/afkt/maven/DevApp)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Utils](https://img.shields.io/badge/utils-160+-ff69b4.svg)](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

# About (持续更新，目前含160+工具类)

> DevUtils 是一个 Android 工具库，主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用
>
> 该项目尽可能的便于开发人员，快捷、高效开发安全可靠的项目，以及内置部分常用的资源文件，如 color.xml

![module](https://raw.githubusercontent.com/afkT/DevUtils/master/file/DevUtils.png)


## Documentation - [Lib](https://github.com/afkT/DevUtils/blob/master/lib)

### DevApp - Android 工具类库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

- [Use and Config](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/utils_readme/USE_CONFIG.md)

### DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md)

### DevJava - Java 工具类库 ( 不依赖 android api )

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md)

### DevEnvironment - Android 环境配置切换库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/Environment)

### DevWidget - 自定义 View UI 库

- [Preview README](https://github.com/afkT/DevUtils/blob/master/lib/Widget/DevWidget)

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/Widget/DevWidget/README_API.md)


## Other

> [DevOther](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/README.md) 第三方库封装、以及部分特殊工具类等，方便 copy 封装类使用
>
> [Widget](https://github.com/afkT/DevUtils/blob/master/lib/Widget) 部分自定义 View 功能、效果
>
> [DevBase](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base) Base ( Activity、Fragment )、MVP 基类等
>
> [DevStandard](https://github.com/afkT/DevUtils/blob/master/lib/DevStandard/src/main/java/dev/standard) 项目规范统一检测、生成替换等
>
> [JavaDoc API Generate](https://github.com/afkT/JavaDoc) 该工具类 (DevUtils) API 文档，是通过 JavaDoc 项目读取 class 信息生成，并且进行代码、注释间距规范检测，生成效果示范 [DevApp - API](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)


## Dev 系列全部 Lib Gradle

```java

// DevApp Android 工具类库 https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
implementation 'com.afkt:DevApp:1.9.2'
//implementation 'com.afkt:DevAppX:1.9.2' // AndroidX

// DevJava Java 工具类库 ( 不依赖 android api ) https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
implementation 'com.afkt:DevJava:1.1.1' // 用于纯 Java 开发，如果依赖了 DevApp 则不需要依赖 DevJava

// DevAssist 快捷功能辅助类库 https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
implementation 'com.afkt:DevAssist:1.0.3'

// DevEnvironment Android 环境配置切换库 https://github.com/afkT/DevUtils/blob/master/lib/Environment
implementation 'com.afkt:DevEnvironment:1.0.0'
debugAnnotationProcessor 'com.afkt:DevEnvironmentCompiler:1.0.0'
releaseAnnotationProcessor 'com.afkt:DevEnvironmentCompilerRelease:1.0.0'
//annotationProcessor 'com.afkt:DevEnvironmentCompiler:1.0.0'

// DevWidget 自定义 View UI 库 https://github.com/afkT/DevUtils/blob/master/lib/Widget/DevWidget
implementation 'com.afkt:DevWidget:1.0.0'
//implementation 'com.afkt:DevWidgetX:1.0.0' // AndroidX
```

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
    