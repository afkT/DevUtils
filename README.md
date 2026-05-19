
<h1 align="center">DevUtils</h1>


<div align="center">

[![GitHub Profile](https://img.shields.io/badge/GitHub-afkT-orange.svg?style=for-the-badge)](https://github.com/afkT)
[![GitHub License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)](https://github.com/afkT/DevUtils/blob/master/LICENSE)
[![Maven](https://img.shields.io/badge/Maven-Dev-5776E0.svg?style=for-the-badge)](https://search.maven.org/search?q=io.github.afkt)
[![Android API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=for-the-badge)](https://developer.android.com/about/versions)
[![DevUtils Version](https://img.shields.io/badge/DevUtils-2.5.3-yellow.svg?style=for-the-badge)](https://github.com/afkT/DevUtils/releases)
[![Utils](https://img.shields.io/badge/Utils-300+-critical.svg?style=for-the-badge)](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

</div>


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


## Dev 系列开发库全部 Lib Gradle [DevApp API 文档][DevApp API]、[Project Details README][Project Details README]

```gradle

// DevApp - Android 工具类库
implementation 'io.github.afkt:DevAppX:2.5.3'

// DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
implementation 'io.github.afkt:DevAssist:1.4.6'

// DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库
implementation 'io.github.afkt:DevBase:1.2.3'

// DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
implementation 'io.github.afkt:DevEngine:1.1.8'

// DevSimple - 简单敏捷开发库
implementation 'io.github.afkt:DevSimple:1.0.7'

// DevWidget - 自定义 View UI 库
implementation 'io.github.afkt:DevWidgetX:1.2.8'

// DevRetrofit - Retrofit + Kotlin Coroutines 封装
implementation 'io.github.afkt:DevRetrofit:1.1.0'

// DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
implementation 'io.github.afkt:DevHttpManager:1.1.1'

// DevHttpCapture - OkHttp 抓包工具库
implementation 'io.github.afkt:DevHttpCapture:1.2.2'

// DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )
debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.2.2'
releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.2.2'

// DevEnvironment - Android 环境配置切换库
implementation 'io.github.afkt:DevEnvironment:1.2.0'
debugAnnotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.2.0' // kaptDebug
releaseAnnotationProcessor 'io.github.afkt:DevEnvironmentCompilerRelease:1.2.0' // kaptRelease
//annotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.2.0' // kapt

// DevDeprecated - Dev 系列库弃用代码统一存储库
implementation 'io.github.afkt:DevDeprecated:1.0.3'

// DevJava - Java 工具类库 ( 不依赖 android api )
implementation 'io.github.afkt:DevJava:1.5.6'
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


<!-- === -->
<!-- 链接 -->
<!-- === -->

[DevApp API]: https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
[Project Details README]: https://github.com/afkT/DevUtils/blob/master/README_PROJECT.md
[dev_module_img]: https://github.com/afkT/DevUtils/raw/master/art/module.png