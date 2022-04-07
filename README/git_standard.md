
## 摘要

* [1 前言](#1-前言)
* [2 Commit Message 格式](#2-commit-message-格式)
* [3 分支管理规范](#3-分支管理规范)
* [4 分支简明流程](#4-分支简明流程)
* [5 Git 特别注意事项](#5-Git-特别注意事项)
* [总结](#总结)
* [附录](#附录)


### 1 前言

在团队协作开发时，每个人提交代码时都会写 commit message；

每个人都有自己的书写风格，可以说是五花八门，十分不利于阅读和维护；

一般来说，大厂都有一套的自己的提交规范，尤其是在一些大型开源项目中，commit message 都是十分一致的；

因此，我们需要制定统一标准，促使团队形成一致的代码提交风格、规范。


### 2 Commit Message 格式

可查看 [AngularJS][AngularJS] 在 GitHub 上的提交记录，
它是由 Google 推出的一套 Commit Message 规范标准，也是目前使用范围最广的规范。

## type(scope): subject

* type（必须）：commit 的类别，常用以下部分标识；
* scope（可选）：用于说明 commit 影响的范围，比如数据层、控制层、视图层、功能模块等等；
* subject（必须）：commit 的简短描述，推荐以动词开头，比如设置、更新、新增、移除、撤销等；

| 类别       | 描述                                                            |
| --------- | -------------------------------------------------------------- |
| feat      | 新功能（feature）                                                |
| fix       | 产生 diff 并自动修复此问题，适合于一次提交直接修复问题                  |
| to        | 只产生 diff 不自动修复此问题，适合于多次提交，最终修复问题提交时使用 fix   |
| docs      | 文档（documentation）                                            |
| style     | 格式（不影响代码运行的变动）                                         |
| refactor  | 重构（即不是新增功能，也不是修改 bug 的代码变动）                       |
| perf      | 优化相关，比如提升性能、体验                                         |
| test      | 增加测试                                                         |
| chore     | 构建过程或辅助工具的变动                                            |
| revert    | 回滚到上一个版本                                                   |
| merge     | 代码合并                                                         |
| sync      | 同步主线或分支的 bug                                               |
| build     | 构建信息、配置变动                                                 |

**commit message 例子：**

`style: 格式化代码`

`feat(DevEngine): 新增 completeInitialize 完整初始化方法，移除 getMMKVConfig、getMMKVByHolder 方法`


### 3 分支管理规范

#### 3.1 分支类型说明

* master 分支（主分支）稳定版本
* develop 分支（开发分支）最新版本
* release 分支（发布分支）发布新版本
* hotfix 分支（热修复分支）修复线上 bug
* feature 分支（特性分支）实现新特性

#### 3.2 角色与项目角色对应关系

* Owner（拥有者）Git 管理员
* Master（管理员）开发主管
* Developer（开发者）开发人员
* Reporter（报告者）测试人员
* Guest（观察者）其他人员


### 4 分支简明流程

1. 每开发一个新功能，创建一个 feature 分支，多人在此分支上开发；
2. 提测时，将 master 分支和需要提测的分支汇总到一个 release 分支，发布测试环境；
3. 发现 bug 时，在 feature 分支上 debug 后，再次回到 2；
4. 发布生产环境后，将 release 分支合并到 master 分支，删除 release 分支。

#### 4.1 创建新项目（ master 分支）

1. 开发主管提交代码初始版本到 master 分支，并推送至 Gitlab 系统；
2. 开发主管在 Gitlab 系统中设置 master 分支为 Protected 分支（保护分支）；
3. Protected 分支不允许 Developer 角色推送代码，但 Master 角色可以推送代码。

#### 4.2 进行项目开发（ develop 分支）

1. 开发主管在 master 分支上创建 develop 分支（开发分支），并推送至 Gitlab 系统；
2. master 分支与 develop 分支一样，有且仅有一个；
3. 对于非并行项目可以使用 develop 分支开发方式，对于多人并行开发项目，
   使用 feature 分支开发方式，但 develop 和 feature 开发方式不应同时使用。

#### 4.3 开发新特性（ feature 分支）

每个新需求或新的研究创建一个 feature 分支；

命名规范：

feature-分支创建日期-新特性关键字，例如：feature-20150508-满立减；

推荐使用 feature 分支，但 feature 分支的生命周期不能跨一次迭代。

#### 4.4 发布测试环境（ release 分支）

开发负责人需完成以下任务：

1. 确认要发布的 feature 分支上的功能是否开发完毕并提交；
2. 创建 release 分支（发布分支），将所有要发布的分支逐个合并到 release 分支，有如下情况：
    * feature 分支（可能有多个）
    * master 分支（期间可能有其他 release 版本更新到了 master）
3. 命名规则：release-分支创建日期-新特性和待发布版本号；
    * 例如：release-201505081712-买立减v1.0.0，版本可根据需要添加，作为发版里程碑标记
4. 删除本次发布的所有 feature 分支；
5. 发布到测试环境，通知测试。

#### 4.5 修复待发布版本中的 bug（ feature 分支）

如果发现 bug，开发人员在 feature 分支上修复测试人员提交给自己的 bug，修复完成后，由负责人再次创建 release 分支，发布测试环境。

#### 4.6 发布正式环境

开发负责人需完成以下任务：

1. 根据修复后的 release 分支再次将 master 合并，打包发布生产环境；
2. 确认发布成功，并线上验收通过后，将 release 分支合并到 master 分支；
3. 在 master 分支上创建标签，命名规则：tag-日期-新特性和版本号；
    * 例如：tag-201505081712-买立减v1.0.0，版本可根据需要添加，作为发版里程碑标记
4. 删除对应 release 分支。

#### 4.6 修复线上 bug（ hotfix 分支）

线上的不同版本出现了 bug 怎么办？开发负责人需完成以下任务：

1. 从 master 分支某个 tag 上创建一个 hotfix 分支（热修复分支），一般是最新的 tag 应该和当前生产环境对应；
    * 命名规则：hotfix-分支创建日期-bug名称和待发布版本号，例如：hotfix-201705081614-购物车点击没反应v1.0.1
2. 开发人员完成 bug 修复，提交 hotfix 分支到测试环境验收通过；
3. 再次发布正式环境流程；
4. 将 hotfix 分支合并到 master 分支；
5. 在 master 分支上创建标签，命名规则：tag-日期-新特性和版本号；
    * 例如：tag-201505081712-买立减v1.0.0，版本可根据需要添加，作为发版里程碑标记
6. 删除 hotfix 分支。


### 5 Git 特别注意事项

由于 git 分支是基于指针的概念，所以分支速度非常快，当多个分支时，实际指针指向的是同一个文件，
当文件被修改时，使用的是暂存区保存修改，此时并未提交到相应分支；

所以切换分支的时候，暂存区只有一个，所以切换分支之前，一定要将所有修改 commit 到当前分支，
否则会在其他分支看到修改的内容，引起误解。


### 总结

编码规范、流程规范在软件开发过程中是至关重要的，它可以使我们在开发过程中少走很多弯路；

Git commit 规范也是如此，确实也是很有必要的，几乎不花费额外精力和时间，但在之后查找问题的效率却很高；

作为一名程序员，我们更应注重代码和流程的规范性，永远不要在质量上将就。

**以上规范仅供参考，具体实践请依据团队具体情况自行调整。**


### 附录

#### 参考

[如何规范你的 Git commit？][如何规范你的 Git commit？]

[Git commit 代码提交规范][Git commit 代码提交规范]

[Git commit 规范指南][Git commit 规范指南]

[Git 分支设计规范][Git 分支设计规范]

[Git 分支管理规范][Git 分支管理规范]





[AngularJS]: https://github.com/angular/angular/commits/master
[如何规范你的 Git commit？]: https://www.jianshu.com/p/a1ea85217d07
[Git commit 代码提交规范]: https://segmentfault.com/a/1190000017205604
[Git commit 规范指南]: https://segmentfault.com/a/1190000009048911
[Git 分支设计规范]: https://zhuanlan.zhihu.com/p/131332462
[Git 分支管理规范]: https://www.cnblogs.com/imyalost/p/9301732.html