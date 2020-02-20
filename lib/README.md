# Lib

该目录主要存储 DevUtils project 全部 lib module，封装快捷使用的工具类及 API 方法调用，隐藏内部逻辑判断对外提供便捷辅助类、统一回调类、Bean、Event 以及 Engine 兼容框架等


## 目录结构

```
- lib                                      | 根目录
   - DevApp                                | Android 工具类库
   - DevAssist                             | 封装逻辑代码, 实现多个快捷功能辅助类、以及 Engine 兼容框架等
   - DevBase                               | Base ( Activity、Fragment )、MVP 基类等
   - DevJava                               | Java 工具类库 ( 不依赖 android api )
   - DevOther                              | 第三方库封装、以及部分特殊工具类等, 方便 copy 封装类使用
   - DevStandard                           | 项目规范统一检测、生成替换等
   - DevWidget                             | 部分自定义 View 功能、效果
   - Environment                           | 环境配置切换库
      - DevEnvironment                     | 环境切换可视化 UI 操作
      - DevEnvironmentBase                 | 注解类、实体类、监听事件等通用基础
      - DevEnvironmentCompiler             | Debug ( 打包 / 编译 ) 生成实现代码
      - DevEnvironmentCompilerRelease      | Release ( 打包 / 编译 ) 生成实现代码
```