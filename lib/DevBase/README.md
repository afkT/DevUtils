
## Gradle

```gradle
// DevBase - Base ( Activity、Fragment ) MVP、MVVM 基类库
implementation 'io.github.afkt:DevBase:1.2.1'
```

## 目录结构

```
- dev.base                  | 根目录
   - core                   | 核心实现代码
      - arch                | 架构封装
         - databinding      | ViewDataBinding 基类
         - mvp              | MVP 基类
         - mvvm             | MVVM 基类
      - interfaces          | 基类开放接口包
   - simple                 | 简化敏捷开发基类
      - contracts           | 专属契约层
         - binding          | Binding 相关代码
         - factory          | Simple 简化开发工厂代码
         - lifecycle        | 生命周期监听管理
         - viewmodel        | ViewModel 代码
      - extensions          | 扩展实现代码
      - mvvm                | MVVM 简化开发封装
   - utils                  | 依赖工具包
      - adapter             | RecyclerView Binding ViewHolder
      - assist              | 功能辅助类 ( 抽取通用代码 )
```


## 项目类结构 - [包目录][包目录]

### 核心代码

* 核心 Base Activity、Fragment（[core][core]）：整个库 Activity、Fragment 基类都基于该模块代码

### 其他代码

* 接口相关（[interfaces][interfaces]）：对外提供开放方法接口，用于基类可选配置及获取操作

* 库依赖工具包（[utils、assist][utils、assist]）：抽取通用代码工具类、封装相同逻辑代码辅助类

### MVP、MVVM 架构基类 - [arch][arch]

* MVP 架构基类（[mvp][mvp]）：MVP Contract Lifecycle 架构基类

* MVVM 架构基类（[mvvm][mvvm]）：MVVM ( ViewDataBinding + ViewModel ) 架构基类

### Simple 简化敏捷开发基类 - [simple][simple]

直接运行、查看使用示例项目代码 [DevUtilsApp][DevUtilsApp]

```kotlin
// 简化后效果
class XXXFragment : AppFragment<XXXBinding, XXXViewModel>(
    R.layout.xxx, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<XXXFragment> {
            viewModel.xxxx
            binding.xxx
        }
    }
)

class XXXActivity : AppActivity<XXXBinding, XXXViewModel>(
    R.layout.xxx, BR.viewModel
)
```

## 设计思路

首先整个库 Activity、Fragment 最终实现都是继承 [AbstractDevBaseActivity][AbstractDevBaseActivity]、[AbstractDevBaseFragment][AbstractDevBaseFragment]

方便对核心代码设计理解及管理控制，并在此基础上实现 MVP、MVVM 扩展基类

* **Content Layout 基类**

> **核心实现：内置 R.layout.base_content_view 作为 contentView 根布局进行显示**
>
> 并进行动态添加 title、body 等布局 View，以达到能够对全局进行 View 增删显隐控制处理，以及后续需求迭代、维护全局操作

* 混淆

> -keep class 包名.databinding.** {*;}

因为 [ViewBindingUtils][ViewBindingUtils] 是通过反射进行初始化，防止方法 `bind`、`inflate` 被混淆，所以需要忽略自动生成的 ViewBinding 类

## 其他

架构只是一种思维方式，不管是 MVC、MVP 还是 MVVM，都只是一种思考问题、解决问题的思维

其目的是要解决编程过程中，模块内部高内聚、模块与模块之间低耦合、可维护性、易测试等问题





[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base
[core]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core
[interfaces]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/interfaces
[utils、assist]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/utils
[arch]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/arch
[mvp]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/arch/mvp
[mvvm]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/arch/mvvm
[AbstractDevBaseActivity]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/AbstractDevBaseActivity.kt
[AbstractDevBaseFragment]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/core/AbstractDevBaseFragment.kt
[ViewBindingUtils]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/utils/ViewBindingUtils.kt
[simple]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/src/main/java/dev/base/simple
[DevUtilsApp]: https://github.com/afkT/DevUtils/blob/master/application/DevUtilsApp/src/main/java/afkt/project/app