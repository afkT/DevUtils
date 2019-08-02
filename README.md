
[![Cocoapods](https://img.shields.io/badge/GitHub-afkT-blue.svg)](https://github.com/afkT)
[![GitHub license](https://img.shields.io/github/license/afkT/DevUtils.svg)](https://github.com/afkT/DevUtils/blob/master/LICENSE)
[![Cocoapods](https://img.shields.io/badge/DevUtils-1.7.6-brightgreen.svg)](https://github.com/afkT/DevUtils)
[![](https://jitpack.io/v/afkT/DevUtils.svg)](https://jitpack.io/#afkT/DevUtils)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Cocoapods](https://img.shields.io/badge/utils-100+-ff69b4.svg)](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/README.md)

# About (持续更新，目前含100+工具类)

> DevUtils 是一个 Android 工具库，主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用
>
> 该项目尽可能的便于开发人员，快捷、高效开发安全可靠的项目，以及内置部分常用的资源文件，如 color.xml、toast_layout.xml 等

![module](https://raw.githubusercontent.com/afkT/DevUtils/master/file/DevUtils.png)

## Gradle

Step 1. Add the JitPack repository to your build file
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency
```
dependencies {
	// 因为内含 res 文件, 使用 aar 方式调用
	implementation 'com.github.afkT:DevUtils:1.7.6@aar'
}
```

## Documentation

- [README - API](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/README.md)

- [Use and Config](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/USE_CONFIG.md)

- [JavaDoc - API](https://javadoc.jitpack.io/com/github/afkT/DevUtils/1.7.6/javadoc/)

- [检测代码规范、注释内容排版, API 文档生成](https://github.com/afkT/JavaDoc)


## Use

> 只需要在 Application 中调用 DevUtils.init() 进行初始化就行
>
> DevUtils.openLog() 是打开内部工具类 日志输出 [DevLogger](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/utils_readme/logger/DevLogger.md)，线上(release)版本则不调用此句

```java
/**
 * detail: 全局Application
 * @author Ttt
 */
public class BaseApplication extends Application {

    // 日志 TAG
    private final String LOG_TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化工具类
        DevUtils.init(this.getApplicationContext());
        // = 初始化日志配置 =
        // 设置默认Logger配置
        LogConfig logConfig = new LogConfig();
        logConfig.logLevel = LogLevel.DEBUG;
        logConfig.tag = LOG_TAG;
        logConfig.sortLog = true; // 美化日志, 边框包围
        DevLogger.init(logConfig);
        // 打开 lib 内部日志 - 线上环境, 不调用方法就行
        DevUtils.openLog();
        DevUtils.openDebug();
    }
}
```

## Other

> :smirk: [JavaDoc API Generate](https://github.com/afkT/JavaDoc) 该工具类(DevUtils) API 文档, 是通过 JavaDoc 项目读取 class 信息生成, 并且进行代码、注释间距规范检测, 生成效果示范 [DevUtils - API](https://github.com/afkT/DevUtils/blob/master/DevLibUtils/README.md)

> [DevQuickUtils](https://github.com/afkT/DevQuickUtils) 是一个 基于 [DevUtils](https://github.com/afkT/DevUtils) 二次封装的快捷开发实现库，封装多数逻辑判断代码，内含 Http、ImageLoader、Log 等兼容 Engine 框架，封装 Activity、Fragment、Bean 基类以及 ReqInfoAssist(请求信息辅助类)、PageInfoAssist(分页辅助类)、MultiSelectListAssist、MultiSelectMapAssist(多选辅助类) 等，便于开发人员，基于 [DevUtils](https://github.com/afkT/DevUtils)、[DevQuickUtils](https://github.com/afkT/DevQuickUtils) 快速熟练开发 Android 项目

> [DevQuickUtils - app module](https://github.com/afkT/DevQuickUtils/tree/master/app/src/main/java/demo/pro) 基于 ([DevUtils](https://github.com/afkT/DevUtils)、[DevQuickUtils](https://github.com/afkT/DevQuickUtils)) 库基础上实现常见功能、UI效果等，并持续增加比较火的框架使用、二次封装等，以及部分架构设计思路、使用等综合示范项目


## Thanks

> 感谢以下开源项目的作者，本项目中有些功能受你们项目灵感的启发，有些功能也用到你们的代码完成

- [GrenderG/Toasty](https://github.com/GrenderG/Toasty)
- [Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
- [litesuits/android-common](https://github.com/litesuits/android-common)
- [AbrahamCaiJin/CommonUtilLibrary](https://github.com/AbrahamCaiJin/CommonUtilLibrary)

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
    