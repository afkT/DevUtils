

## DevEnvironment

DevEnvironment 是一个 Android 环境配置切换库，运用 Java 注解、APT、反射等原理实现一键切换环境的工具库


### 该库解决什么问题

* App 在开发、测试、预生产、线上等阶段需要切换不同环境配置

* 同一个 App 中的不同模块，在同一阶段需要配置不同的环境

* 某些功能只能在指定环境下使用

* 需支持通过后台数据动态设置配置信息

* 环境配置在代码中写死，导致每次修改环境之后代码管理工具 ( Git、SVN ) 都会提示代码改动，一旦疏忽就提交了，这对于代码管理是不严谨的


### DevEnvironment 库亮点

* 使用简单、且无需重新打包即可一键切换环境

* 支持模块化配置与切换环境

* 支持通过后台数据动态设置配置信息

* 支持添加切换环境配置回调

* 使用 Java 注解编译时生成 DevEnvironment 类，内部实现 `切换` `保存` `读取` 环境的逻辑代码


### 为什么不用 Gradle

|     比较内容     |   DevEnvironment   | Gradle  Application Id 不同 | Gradle Application Id 相同 |
|:------------:|:------------------:|:-------------------------:|:------------------------:|
|   运行时切换环境    | :heavy_check_mark: |            :x:            |           :x:            |
|    切换环境回调    | :heavy_check_mark: |            :x:            |           :x:            |
|    切换环境逻辑    |        自动生成        |          需要自己实现           |          需要自己实现          |
|  N 套环境打包数量   |        1 个         |            N 个            |           N 个            |
|   多套环境同时安装   | :heavy_check_mark: |    :heavy_check_mark:     |           :x:            |
| 支付等 SDK 包名校验 | :heavy_check_mark: |            :x:            |    :heavy_check_mark:    |
|   多模块环境配置    | :heavy_check_mark: |    :heavy_check_mark:     |    :heavy_check_mark:    |
|   测试环境不泄露    | :heavy_check_mark: |    :heavy_check_mark:     |    :heavy_check_mark:    |
|      ……      |         ——         |            ——             |            ——            |


### 最新版本

| module  |     DevEnvironment      | DevEnvironmentCompiler  | DevEnvironmentCompilerRelease |
|:-------:|:-----------------------:|:-----------------------:|:-----------------------------:|
| version | [![][maven_svg]][maven] | [![][maven_svg]][maven] |    [![][maven_svg]][maven]    |

### Gradle

```groovy
dependencies {
    // Java
    // DevEnvironment - Android 环境配置切换库
    implementation 'io.github.afkt:DevEnvironment:1.1.8'
    debugAnnotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.8'
    releaseAnnotationProcessor 'io.github.afkt:DevEnvironmentCompilerRelease:1.1.8'
    // 如果需要 Release 包，支持通过后台数据动态设置配置信息 则使用 debug compiler lib
    // annotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.8'

    // Kotlin
    // DevEnvironment - Android 环境配置切换库
    implementation 'io.github.afkt:DevEnvironment:1.1.8'
    kaptDebug 'io.github.afkt:DevEnvironmentCompiler:1.1.8'
    kaptRelease 'io.github.afkt:DevEnvironmentCompilerRelease:1.1.8'
    // 如果需要 Release 包，支持通过后台数据动态设置配置信息 则使用 debug compiler lib
    // kapt 'io.github.afkt:DevEnvironmentCompiler:1.1.8'
}
```


### 构建 DevEnvironment 类

> 点击菜单栏中的 “Build” -> “Rebuild Project”，等待编译完成

正常会在 `/build/generated/ap_generated_sources/debug/out/dev/environment` 内创建 DevEnvironment.java

或者在 `/build/generated/source/kapt/debug/dev/environment` 内创建 DevEnvironment.java


--------

### 示例项目

直接运行、查看使用示例项目代码！！！

[DevEnvironmentUse][DevEnvironmentUse]！！！
[DevEnvironmentUse][DevEnvironmentUse]！！！
[DevEnvironmentUse][DevEnvironmentUse]！！！


### API

* **环境配置工具类 DevEnvironment.java**【自动生成】

| 方法                                | 注释                                           |
|:----------------------------------|:---------------------------------------------|
| isRelease                         | 是否使用 releaseAnnotationProcessor 构建           |
| getModuleList                     | 获取全部 ModuleBean 配置列表                         |
| addOnEnvironmentChangeListener    | 添加模块环境改变触发事件                                 |
| removeOnEnvironmentChangeListener | 移除模块环境改变触发事件                                 |
| clearOnEnvironmentChangeListener  | 清空模块环境改变触发事件                                 |
| reset                             | 重置操作                                         |
| getIMModule                       | 获取 IM [ Module ] Bean                        |
| getIMReleaseEnvironment           | 获取 IM [ Module ] Release Environment Bean    |
| getIMEnvironment                  | 获取 IM [ Module ] Selected Environment Bean   |
| getIMEnvironmentValue             | 获取 IM [ Module ] Selected Environment Value  |
| setIMEnvironment                  | 设置 IM [ Module ] Selected Environment Bean   |
| resetIM                           | 重置 IM [ Module ] Selected Environment Bean   |
| isIMAnnotation                    | 是否 IM [ Module ] Annotation Environment Bean |

```java
/**
 * 每个 @Module 都会生成七个方法, 以 IM Module 为例
 * getIMModule()
 * getIMReleaseEnvironment()
 * getIMEnvironment()
 * getIMEnvironmentValue()
 * setIMEnvironment()
 * resetIM()
 * isIMAnnotation()
 * 其中 IM 就是 class Name【Module】
 */
@Module(alias = "IM 模块")
private class IM {
}
```


### 生成 DevEnvironment.java 配置类示例

```java
/**
 * detail: Environment 配置
 * @author Ttt
 */
private final class EnvironmentConfig {

    @Module(alias = "IM 模块")
    private class IM {

        @Environment(value = "https://im.release.com", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://im.debug.com", alias = "测试环境")
        private String debug;
    }

    @Module(alias = "服务器请求地址")
    private class Service {

        @Environment(value = "https://release.com", isRelease = true, alias = "线上环境")
        private String release;

        @Environment(value = "https://debug.com", alias = "测试环境")
        private String debug;

        @Environment(value = "https://pre_release.com", alias = "预发布环境")
        private String pre_release;

        @Environment(value = "https://development.com", alias = "开发环境")
        private String development;
    }
}
```

### @Module

被 `@Module` 修饰的类或接口表示一个模块，每个 `@Module` 有 n ( n > 0 ) 个被 `@Environment` 修饰的属性，表示该模块中有 n 种环境配置

### @Environment

被 `@Environment` 修饰的属性表示一个环境，必须指定 **value** 属性值，此外还有两个可选属性：**isRelease** 和 **alias**

`value`：该环境配置值

`isRelease`：是一个 Boolean 型的属性，默认为 false，当值为 true 时，它就是所在 `@Module` 的默认环境

> 每个 `@Module` **必须有且只有一个** `isRelease` 值为 true 的 `@Environment`


### 项目类结构 - [包目录][包目录]

* Module 类（[@Module][@Module]）：模块 ( 注解标记类 )

* Environment 类（[@Environment][@Environment]）：环境配置 ( 注解标记类 )

* 模块环境改变接口（[OnEnvironmentChangeListener][OnEnvironmentChangeListener]）：模块环境发生变化时触发

> 每个 `@Module`、`@Environment` 注解，都会生成对应的 **ModuleBean**、**EnvironmentBean** 实体类

* @Module 映射实体类（[ModuleBean][ModuleBean]）：@Module ( 注解标记类 ) 映射实体类

* @Environment 映射实体类（[EnvironmentBean][EnvironmentBean]）：@Environment ( 注解标记类 ) 映射实体类


--------


### DevEnvironmentCompiler、DevEnvironmentCompilerRelease 编译区别

* **DevEnvironmentCompiler** 属于 Debug ( 打包 / 编译 ) 注解处理器，使用该方式编译生成的 DevEnvironment.java 类，允许设置选中的环境 ( `setXXEnvironment` 通过该方法设置，只有使用该注解编译才会实现该方法代码 )

    1. `getXXModule` 获取对应 Module 映射实体类 ModuleBean

    2. `getXXReleaseEnvironment` 获取对应 Module isRelease 值为 true 的 Environment 映射实体类 EnvironmentBean

    3. `getXXEnvironment` 获取对应 Module 选中的 Environment ( 默认选中 isRelease 值为 true 的 `@Environment` )

    4. `getXXEnvironmentValue` 获取对应 Module 选中的 Environment Value

    5. `setXXEnvironment` 设置对应 Module 选中的 Environment

    6. `resetXX` 用于删除对应 Module 选中的 Environment Config File

    7. `isXXAnnotation` 用于判断对应 Module 选中的 Environment 是否属于注解环境配置 ( **是否通过 @Environment 注解** )

* **DevEnvironmentCompilerRelease** 属于 Release ( 打包 / 编译 ) 注解处理器，使用该方式编译生成的 DevEnvironment.java 类，**每个 Module 只会生成一个常量 Environment，并且无法进行修改设置**

    1. `getXXModule` 获取对应 Module 映射实体类 ModuleBean

    2. `getXXReleaseEnvironment` 获取对应 Module isRelease 值为 true 的 Environment 映射实体类 EnvironmentBean

    3. `getXXEnvironment` 内部调用 `getXXReleaseEnvironment`

    4. `getXXEnvironmentValue` 内部调用 `getXXEnvironment` 获取 Value

    5. `setXXEnvironment` 内部不实现代码，直接返回 false ( 表示不支持设置 )

    6. `resetXX` 内部不实现代码，直接返回 false

    7. `isXXAnnotation` 内部不实现代码，直接返回 true

> **DevEnvironmentCompilerRelease** 编译生成的 DevEnvironment.java 类，全部属于 final 无法进行修改、设置，且部分方法内部不进行代码实现
>
> 而 **DevEnvironmentCompiler** 编译生成的 DevEnvironment.java 类，允许修改选中的 Environment 并且支持可视化切换、代码方式切换


--------


### 切换环境方式

> 注：使用 **DevEnvironmentCompilerRelease** 注解编译生成代码，不支持环境配置切换

1. 通过代码方式设置 setXXEnvironment

```java
// 如果准备设置环境等于当前选中的环境，则会返回 false
EnvironmentBean custom = new EnvironmentBean(
        "自定义配置", "https://custom.com", 
                "动态自定义", DevEnvironment.getServiceModule()
);
boolean result = DevEnvironment.setServiceEnvironment(context, custom);
```

2. 通过可视化界面方式切换

```java
// 显示右上角重启按钮
boolean result = DevEnvironmentUtils.start(context, new RestartCallback() {
    @Override
    public void onRestart() {
        ActivityUtils.getManager().exitApplication();
    }
});
// 不显示右上角重启按钮
boolean result = DevEnvironmentUtils.start(context);
```


### 环境切换监听事件

```java
// 添加环境改变监听事件
DevEnvironment.addOnEnvironmentChangeListener(new OnEnvironmentChangeListener() {

    /**
     * 模块环境发生变化时触发
     * @param module         环境发生变化的模块
     * @param oldEnvironment 该模块的旧环境
     * @param newEnvironment 该模块的最新环境
     */
    @Override
    public void onEnvironmentChanged(
            ModuleBean module,
            EnvironmentBean oldEnvironment,
            EnvironmentBean newEnvironment
    ) {
    }
});
// 移除环境改变监听事件
DevEnvironment.removeOnEnvironmentChangeListener(listener);
// 移除全部环境改变监听事件
DevEnvironment.clearOnEnvironmentChangeListener();
```


### 获取 Module

```java
// 有多少个 @Module 修饰，则会生成多少个 getXXModule 方法
ModuleBean serviceModule = DevEnvironment.getServiceModule();
ModuleBean imModule = DevEnvironment.getIMModule();
```


### 获取 Module Environment

```java
/**
 * getXXReleaseEnvironment 该方法返回 isRelease 值为 true 的 Environment ( 必须有且只有一个 )
 * 
 * 而 getXXEnvironment 则是获取当前 Module 选中的 Environment，可通过 setXXEnvironment 进行修改
 * 
 * 只有通过 DevEnvironmentCompiler 编译生成的 DevEnvironment.java 类，允许修改选中的 Environment 并且支持可视化切换、代码方式切换
 */

EnvironmentBean serviceReleaseEnvironment = DevEnvironment.getServiceReleaseEnvironment();
EnvironmentBean serviceEnvironment = DevEnvironment.getServiceEnvironment(context);

EnvironmentBean imReleaseEnvironment = DevEnvironment.getIMReleaseEnvironment();
EnvironmentBean imEnvironment = DevEnvironment.getIMEnvironment(context);
```


### 实现原理

同 Butterknife、Greendao 等第三方库，通过编译时注解 ( APT 技术 ) 实现，具体可参考该库实现代码





[maven_svg]: https://img.shields.io/badge/Maven-1.1.8-brightgreen.svg
[maven]: https://search.maven.org/search?q=io.github.afkt
[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment
[@Module]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment/annotation/Module.java
[@Environment]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment/annotation/Environment.java
[OnEnvironmentChangeListener]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment/listener/OnEnvironmentChangeListener.java
[ModuleBean]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment/bean/ModuleBean.java
[EnvironmentBean]: https://github.com/afkT/DevUtils/blob/master/lib/Environment/DevEnvironmentBase/src/main/java/dev/environment/bean/EnvironmentBean.java
[DevEnvironmentUse]: https://github.com/afkT/DevUtils/blob/master/application/DevEnvironmentUse/src/main/java/afkt/environment/use