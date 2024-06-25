# About

该目录主要存储 DevUtils project 全部 lib module，封装快捷使用的工具类及 API 方法调用，隐藏内部逻辑判断对外提供便捷辅助类、统一回调类、Bean、Event 以及
Engine 兼容框架等

## 目录结构

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


## Dev 系列开发库全部 Lib Gradle

```gradle

// DevApp - Android 工具类库
implementation 'io.github.afkt:DevAppX:2.4.6'

// DevAssist - 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
implementation 'io.github.afkt:DevAssist:1.4.1'

// DevBase - Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库
implementation 'io.github.afkt:DevBase:1.1.8'

// DevBaseMVVM - MVVM ( ViewDataBinding + ViewModel ) 基类库
implementation 'io.github.afkt:DevBaseMVVM:1.1.6'

// DevMVVM - DataBinding 工具类库
implementation 'io.github.afkt:DevMVVM:1.0.4'

// DevSimple - 简单敏捷开发库
implementation 'io.github.afkt:DevSimple:1.0.1'

// DevAgile - 简单敏捷开发库 ( 常用、零散代码 )
implementation 'io.github.afkt:DevAgile:1.0.0'

// DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
implementation 'io.github.afkt:DevEngine:1.1.3'

// DevHttpCapture - OkHttp 抓包工具库
implementation 'io.github.afkt:DevHttpCapture:1.1.7'

// DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )
debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.1.7'
releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.1.7'

// DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
implementation 'io.github.afkt:DevHttpManager:1.0.6'

// DevRetrofit - Retrofit + Kotlin Coroutines 封装
implementation 'io.github.afkt:DevRetrofit:1.0.5'

// DevWidget - 自定义 View UI 库
implementation 'io.github.afkt:DevWidgetX:1.2.3'

// DevEnvironment - Android 环境配置切换库
implementation 'io.github.afkt:DevEnvironment:1.1.5'
debugAnnotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.5' // kaptDebug
releaseAnnotationProcessor 'io.github.afkt:DevEnvironmentCompilerRelease:1.1.5' // kaptRelease
//annotationProcessor 'io.github.afkt:DevEnvironmentCompiler:1.1.5' // kapt

// DevJava - Java 工具类库 ( 不依赖 android api )
implementation 'io.github.afkt:DevJava:1.5.1' // 用于纯 Java 开发，如果依赖了 DevApp 则不需要依赖 DevJava
```