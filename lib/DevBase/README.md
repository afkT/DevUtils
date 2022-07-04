
## Gradle

```gradle
implementation 'io.github.afkt:DevBase:1.1.3'
```

## 目录结构

```
- dev.base                  | 根目录
   - able                   | 基类库接口相关
   - activity               | 核心 Base Activity 代码
   - adapter                | RecyclerView ViewBinding ViewHolder
   - expand                 | 基于 Base Activity、Fragment 扩展包
      - content             | Content Layout 基类
      - mvp                 | MVP 架构基类
      - viewbinding         | ViewBinding 基类
   - fragment               | 核心 Base Fragment 代码
   - utils                  | 依赖工具包
      - assist              | 功能辅助类 ( 抽取通用代码 )
```


## 项目类结构 - [包目录][包目录]

### 核心代码

* 核心 Base Activity（[activity][activity]）：整个库 Activity 基类都基于该模块代码

* 核心 Base Fragment（[fragment][fragment]）：整个库 Fragment 基类都基于该模块代码

### 其他代码

* 接口相关（[able][able]）：对外提供开放方法接口，用于基类可选配置及获取操作

* 库依赖工具包（[utils、assist][utils、assist]）：抽取通用代码工具类、封装相同逻辑代码辅助类

### 基于 Base Activity、Fragment 扩展包（[expand][expand]）

* Content Layout 基类（[content][content]）：通过内置 Layout 作为根布局，方便对全局进行增删 View 控制处理

* MVP 架构基类（[mvp][mvp]）：MVP Contract Lifecycle 架构基类

* ViewBinding 基类（[viewbinding][viewbinding]）：使用 ViewBinding 实现对 View 进行 bind 基类

## 设计思路

首先整个库 Activity、Fragment 最终实现都是继承 [AbstractDevBaseActivity][AbstractDevBaseActivity]、[AbstractDevBaseFragment][AbstractDevBaseFragment]

方便对核心代码设计理解及管理控制，并在此基础上实现三个扩展基类 MVP、ViewBinding、Content Layout

* **ViewBinding 基类**

> 使用 ViewBinding 代替频繁 findViewById，或替换 [Butter Knife][Butter Knife]
>
> **Butter Knife Attention**: This tool is now deprecated. Please switch to [view binding][view binding]

* **MVP 架构基类**

> 使用 MVP Contract 来进行管理，优化代码结构并使用 [Lifecycle][Lifecycle] 解决 MVP 内存泄漏问题

* **Content Layout 基类**

> **核心实现：内置 R.layout.base_content_view 作为 contentView 根布局进行显示**
>
> 并进行动态添加 title、body 等布局 View，以达到能够对全局进行 View 增删显隐控制处理，以及后续需求迭代、维护全局操作

各个扩展基类都有实现 MVP、ViewBinding 组合功能，如：`MVPViewBinding`、`ContentMVP`、`ContentViewBinding`、`ContentMVPViewBinding` 组合基类

## 其他

* 为什么没添加 MVVM 架构基类

> 因 MVVM 需要依赖较多库，可能部分项目并不使用 MVVM 作为基础架构，为此新增 [DevBaseMVVM][DevBaseMVVM] 库进行区分，减少库依赖数量，以及 MVVM 架构代码实现设计理解

架构只是一种思维方式，不管是 MVC、MVP 还是 MVVM，都只是一种思考问题、解决问题的思维

其目的是要解决编程过程中，模块内部高内聚、模块与模块之间低耦合、可维护性、易测试等问题

* 混淆

> -keep class 包名.databinding.** {*;}

因为 [ViewBindingUtils][ViewBindingUtils] 是通过反射进行初始化，防止方法 `bind`、`inflate` 被混淆，所以需要忽略自动生成的 ViewBinding 类





[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base
[activity]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/activity
[fragment]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/fragment
[able]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/able
[utils、assist]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/utils
[expand]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand
[content]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/content
[mvp]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/mvp
[viewbinding]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/expand/viewbinding
[AbstractDevBaseActivity]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/activity/AbstractDevBaseActivity.kt
[AbstractDevBaseFragment]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/fragment/AbstractDevBaseFragment.kt
[Butter Knife]: https://github.com/JakeWharton/butterknife
[view binding]: https://developer.android.com/topic/libraries/view-binding
[Lifecycle]: https://developer.android.com/topic/libraries/architecture/lifecycle
[DevBaseMVVM]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
[ViewBindingUtils]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/utils/ViewBindingUtils.kt