
## Gradle

```java
implementation 'com.afkt:DevBase:1.0.0'
```

## 目录结构

```
- dev.base            | 根目录
   - able             | 基类库接口相关
   - activity         | 核心 Base Activity 代码
   - expand           | 基于 Base Activity、Fragment 扩展包
      - content       | 内置 XML Layout 为 Content View 基类封装
      - mvp           | MVP 基础架构封装
      - viewbinding   | ViewBinding 基类封装
   - fragment         | 核心 Base Fragment 代码
   - utils            | 基类依赖工具包
      - assist        | 功能辅助类 ( 抽取通用代码 )
```


## 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base)

### 核心代码

* 核心 Base Activity（[AbstractDevBaseActivity](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/activity)）：整个库 Activity 基类都基于该模块代码

* 核心 Base Fragment（[AbstractDevBaseFragment](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/fragment)）：整个库 Fragment 基类都基于该模块代码

### 其他代码

* 接口相关（[able](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/able)）：对外提供开放方法接口，服务基类用于可选配置及获取操作

* 库依赖工具包（[utils](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/utils)）：抽取通用代码工具类、封装相同逻辑代码辅助类

### 基于 Base Activity、Fragment 扩展包（[expand](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand)）

* 内置 XML Layout 基类（[content](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/content)）：通过内置 Layout 作为根布局，方便对全局进行增删 View 控制处理

* MVP 架构基类（[mvp](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/mvp)）：MVP Contract 架构封装基类

* ViewBinding 基类（[viewbinding](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/viewbinding)）：使用 ViewBinding 实现对 View 进行 bind 基类

## 设计思路



