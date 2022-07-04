
## Gradle

```gradle
implementation 'io.github.afkt:DevBaseMVVM:1.1.1'
```

## 目录结构

```
- dev.base           | 根目录
   - able            | 基类库接口相关
   - adapter         | RecyclerView ViewDataBinding ViewHolder
   - expand          | 基于 Base Activity、Fragment 扩展包
      - content      | Content Layout MVVM 基类
      - mvvm         | MVVM 架构基类
      - viewdata     | ViewDataBinding 基类
      - viewmodel    | ViewModel 基类
   - utils           | 依赖工具包
      - assist       | 功能辅助类 ( 抽取通用代码 )
```


## 项目类结构 - [包目录][包目录]

### 核心代码

* 核心依赖库 [DevBase][DevBase]：整个库最终基类都基于该库 `DevBase` 代码

### 其他代码

* 接口相关（[able][able]）：对外提供开放方法接口，用于基类可选配置及获取操作

* 库依赖工具包（[utils、assist][utils、assist]）：抽取通用代码工具类、封装相同逻辑代码辅助类

### 基于 Base Activity、Fragment 扩展包（[expand][expand]）

* Content Layout MVVM 基类（[content][content]）：通过内置 Layout 作为根布局，方便对全局进行增删 View 控制处理

* MVVM 架构基类（[mvvm][mvvm]）：MVVM ( ViewDataBinding + ViewModel ) 架构基类

* ViewDataBinding 基类（[viewdata][viewdata]）：使用 ViewDataBinding 实现对 View 进行 bind、数据双向绑定基类

* ViewModel 基类（[viewmodel][viewmodel]）：使用 ViewModel 进行数据管理、交互基类

## Google

* [android / sunflower](https://github.com/android/sunflower)

* [ViewModel 概览](https://developer.android.com/topic/libraries/architecture/viewmodel)

* [LiveData 概览](https://developer.android.com/topic/libraries/architecture/livedata)

## 其他

* [MVPVM in Action, 谁告诉你 MVP 和 MVVM 是互斥的](http://blog.zhaiyifan.cn/2016/03/16/android-new-project-from-0-p3)

* [是让人耳目一新的 Jetpack MVVM 精讲啊！](https://juejin.cn/post/6844903976240939021)

* [DataBinding 最全使用说明](https://juejin.cn/post/6844903549223059463)

* [Android 官方 MVVM 框架实现组件化之整体结构](https://www.jianshu.com/p/c0988e7f31fd)

* [AndroidX Jetpack Practice](https://github.com/hi-dhl/AndroidX-Jetpack-Practice)

* [AndroidLibs / 框架](https://github.com/GuoYangGit/AndroidLibs/blob/master/框架)

* [深入浅出 MVVM 教程](https://www.jianshu.com/p/bcdb7c2a07eb)

* [深入浅出 MVVM 教程 Repository ( 数据仓库 ) ](https://juejin.cn/post/6844903505635835911)

* [关于 Android 架构，你是否还在生搬硬套？](https://juejin.cn/post/6942464122273398820)

* [引入 Jetpack 架构后，你的 App 会发生哪些变化？](https://juejin.cn/post/6955491901265051661)

* [一个整合了大量主流开源项目高度可配置化的 Android MVP 快速集成框架](https://github.com/JessYanCoding/MVPArms/blob/master/MVPArms.md)

## Other

* [GitHub MVVMHabit](https://github.com/goldze/MVVMHabit)

* [GitHub Jetpack-Mvvm](https://github.com/zskingking/Jetpack-Mvvm)

* [GitHub Jetpack-MVVM-Best-Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice)





[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base
[DevBase]: https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
[able]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/able
[utils、assist]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/utils
[expand]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/expand
[content]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/expand/content
[mvvm]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/expand/mvvm
[viewdata]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/expand/viewdata
[viewmodel]: https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/src/main/java/dev/base/expand/viewmodel