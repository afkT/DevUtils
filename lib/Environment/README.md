

## DevEnvironment

DevEnvironment 是一个在 Android 环境配置切换库，运用 Java 注解、APT、反射等原理实现一键切换环境的工具库


### 该库解决什么问题

* App 在开发、测试、预生产、线上等阶段需要切换不同环境配置

* 同一个 App 中的不同模块，在同一阶段需要配置不同的环境

* 某些功能只能在指定环境下使用

* 支持通过后台数据动态设置配置信息

* 环境地址在代码中写死，导致每次修改环境之后代码管理工具 ( Git、SVN ) 都会提示代码改动，一旦疏忽就提交了，这对于代码管理是不严谨的


### DevEnvironment 库亮点

* 使用简单、且无需重新打包即可一键切换环境

* 支持模块化配置与切换环境

* 支持通过后台数据动态设置配置信息

* 支持添加切换环境配置回调

* 使用 Java 注解编译时生成 DevEnvironment 类，内部实现 `切换` `保存` `读取` 环境的逻辑代码


### 为什么不用 Gradle

| 比较内容 | DevEnvironment | Gradle  Application Id 不同 | Gradle Application Id 相同 |
|:-:|:--:|:--:|:--:|
| 运行时切换环境 | √ | × | × |
| 切换环境回调 | √ | × | × |
| 切换环境逻辑 | 自动生成 | 需要自己实现 | 需要自己实现 |
| N 套环境打包数量 | 1 个 | N 个 | N 个|
| 多套环境同时安装 | √ | √ | × |
| 支付等SDK包名校验 | √ | × | √ |
| 多模块环境配置 | √ | √ | √ |
| 测试环境不泄露 | √ | √ | √ |
| …… | —— | —— | —— |


### 版本信息

module | DevEnvironment | DevEnvironmentCompiler | DevEnvironmentCompilerRelease
:---:|:---:|:---:|:---:
version | [![Download](https://api.bintray.com/packages/afkt/maven/DevEnvironment/images/download.svg)](https://bintray.com/afkt/maven/DevEnvironment/_latestVersion) | [![Download](https://api.bintray.com/packages/afkt/maven/DevEnvironmentCompiler/images/download.svg)](https://bintray.com/afkt/maven/DevEnvironmentCompiler/_latestVersion) | [![Download](https://api.bintray.com/packages/afkt/maven/DevEnvironmentCompilerRelease/images/download.svg)](https://bintray.com/afkt/maven/DevEnvironmentCompilerRelease/_latestVersion)


### 使用方法

```groovy
dependencies {
    implementation 'com.afkt:DevEnvironment:$version'
    debugAnnotationProcessor 'com.afkt:DevEnvironmentCompiler:$version'
    releaseAnnotationProcessor 'com.afkt:DevEnvironmentCompilerRelease:$version'
    // 如果需要 Release 包，支持通过后台数据动态设置配置信息 则使用 debug compiler lib
    // annotationProcessor 'com.afkt:DevEnvironmentCompiler:$version'
}
```


### API

* **环境配置工具类 DevEnvironment.java**

| 方法 | 注释 |
| :- | :- |
| isRelease | 是否使用 releaseAnnotationProcessor 构建 |
| getModuleList | 获取全部 ModuleBean 配置列表 |
| addOnEnvironmentChangeListener | 添加模块环境改变触发事件 |
| removeOnEnvironmentChangeListener | 移除模块环境改变触发事件 |
| clearOnEnvironmentChangeListener | 清空模块环境改变触发事件 |
| getStorageDir | 获取环境配置存储路径 - path /data/data/package/cache/DevEnvironment |
| deleteStorageDir | 删除环境存储配置文件 |
| reset | 重置操作 |
| getIMModule | 获取 IM [ Module ] Bean |
| getIMReleaseEnvironment | 获取 IM [ Module ] Release Environment Bean |
| getIMEnvironment | 获取 IM [ Module ] Selected Environment Bean |
| getIMEnvironmentValue | 获取 IM [ Module ] Selected Environment Value |
| setIMEnvironment | 设置 IM [ Module ] Selected Environment Bean |

```java
// 底部五个方法中 IM 属于 Module Name 例:
@Module(alias = "IM 模块")
private class IM {
}
```


### 使用示例

```java
public final class Config {

    @Module(alias = "服务器请求地址")
    private class Service {

        @Environment(value = "https://www.wanandroid.com/", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://debug.com", alias = "测试环境")
        private String debug;

        @Environment(value = "https://pre_release.com", alias = "预发布环境")
        private String pre_release;

        @Environment(value = "https://development.com", alias = "开发环境")
        private String development;
    }

    @Module(alias = "开关")
    private class Switch {

        @Environment(value = "true", isRelease = true)
        private String open;

        @Environment(value = "false")
        private String close;
    }

    @Module(alias = "IM 模块")
    private class IM {

        @Environment(value = "https://im.release.com/", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://im.debug.com", alias = "测试环境")
        private String debug;
    }
}
```

* @Module

被 `@Module` 修饰的类或接口表示一个模块，每个 `@Module` 有 n ( n > 0 ) 个被 `@Environment` 修饰的属性，表示该模块中有 n 种环境配置

* @Environment

被 `@Environment` 修饰的属性表示一个环境，必须指定 `value` 的值，此外还有两个可选属性：`isRelease` 和 `alias`

`value`：该环境配置值

`isRelease`：是一个 Boolean 型的属性，默认为 false，当值为 true 时，它就是所在 `@Module` 的默认环境

> 每个 `@Module` 必须有且只有一个 `@Environment` 的 `isRelease` 值为 true