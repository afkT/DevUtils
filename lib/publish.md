
## 发布顺序及依赖情况

```
- DevApp                                   | Android 工具类库
   - DevAssist                             | 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
      - DevEngine                          | 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
         - DevSimple                       | 简单敏捷开发库
   - DevBase                               | Base ( Activity、Fragment )、MVP、ViewBinding、ContentLayout 基类库
      - DevBaseMVVM                        | MVVM ( ViewDataBinding + ViewModel ) 基类库
   - DevHttpCapture                        | OkHttp 抓包工具库
      - DevHttpCaptureCompiler             | Debug ( 打包 / 编译 ) 实现代码
      - DevHttpCaptureCompilerRelease      | Release ( 打包 / 编译 ) 实现代码
   - DevHttpManager                        | OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
   - DevWidget                             | 自定义 View UI 库
   - DevDeprecated                         | Dev 系列库弃用代码统一存储库
- DevRetrofit                              | Retrofit + Kotlin Coroutines 封装
- DevEnvironmentBase                       | 注解类、实体类、监听事件等通用基础
   - DevEnvironment                        | 环境切换可视化 UI 操作
   - DevEnvironmentCompiler                | Debug ( 打包 / 编译 ) 生成实现代码
   - DevEnvironmentCompilerRelease         | Release ( 打包 / 编译 ) 生成实现代码
- DevJava                                  | Java 工具类库 ( 不依赖 android api )
```