
## 摘要

* [1 前言](#1-前言)
* [2 Commit Message 格式](#2-commit-message-格式)
* [3 分支管理规范](#3-分支管理规范)


### 1 前言

在团队协作开发时，每个人提交代码时都会写 commit message。

每个人都有自己的书写风格，可以说是五花八门，十分不利于阅读和维护。

一般来说，大厂都有一套的自己的提交规范，尤其是在一些大型开源项目中，commit message 都是十分一致的。

因此，我们需要制定统一标准，促使团队形成一致的代码提交风格、规范。


### 2 Commit Message 格式

可查看 [AngularJS](https://github.com/angular/angular/commits/master) 在 GitHub 上的提交规范，
它是由 Google 推出的一套 Commit Message 规范标准，也是目前使用范围最广的规范。

## type(scope): subject

* type（必须）：commit 的类别，常用以下部分标识；
* scope（可选）：用于说明 commit 影响的范围，比如数据层、控制层、视图层、功能模块等等；
* subject（必须）：commit 的简短描述，推荐以动词开头，比如设置、更新、新增、移除、撤销等；

| 类型       | 描述                                                            |
| --------- | -------------------------------------------------------------- |
| feat      | 新功能 ( feature )                                              |
| fix       | 产生 diff 并自动修复此问题，适合于一次提交直接修复问题                  |
| to        | 只产生 diff 不自动修复此问题，适合于多次提交，最终修复问题提交时使用 fix   |
| docs      | 文档 ( documentation )                                          |
| style     | 格式 ( 不影响代码运行的变动 )                                       |
| refactor  | 重构 ( 即不是新增功能，也不是修改 bug 的代码变动 )                     |
| perf      | 优化相关，比如提升性能、体验                                         |
| test      | 增加测试                                                         |
| chore     | 构建过程或辅助工具的变动                                            |
| revert    | 回滚到上一个版本                                                   |
| merge     | 代码合并                                                         |
| sync      | 同步主线或分支的 bug                                               |

**commit message 例子：**

`style: 格式化代码`

`feat(DevEngine): 新增 completeInitialize 完整初始化方法，移除 getMMKVConfig、getMMKVByHolder 方法`


### 3 分支管理规范

#### 3.1 分支类型说明

* master 分支 ( 主分支 ) 稳定版本
* develop 分支 ( 开发分支 ) 最新版本
* release 分支 ( 发布分支 ) 发布新版本
* hotfix 分支 ( 热修复分支 ) 修复线上 bug
* feature 分支 ( 特性分支 ) 实现新特性

#### 3.2 角色与项目角色对应关系

* Owner ( 拥有者 ) Git 管理员
* Master ( 管理员 ) 开发主管
* Developer ( 开发者 ) 开发人员
* Reporter ( 报告者 ) 测试人员
* Guest ( 观察者 ) 其他人员

#### 3.3 分支简明使用流程

1. 每开发一个新功能, 创建一个 feature 分支, 多人在此分支上开发；
2. 提测时, 将 master 分支和需要提测的分支汇总到一个 release 分支, 发布测试环境；
3. 发现 bug 时, 在 feature 分支上 debug 后, 再次回到 2；
4. 发布生产环境后, 将 release 分支合并到 master 分支, 删除 release 分支；

#### 3.4 创建新项目 ( master 分支 )

1. 开发主管提交代码初始版本到 master 分支, 并推送至 Gitlab 系统
2. 开发主管在 Gitlab 系统中设置 master 分支为 Protected 分支 ( 保护分支 )
3. Protected 分支不允许 Developer 角色推送代码, 但 Master 角色可以推送代码



