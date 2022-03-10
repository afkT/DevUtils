
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
它是由 Google 推出的一套提交消息规范标准，也是目前使用范围最广的规范。

**格式：**

# type(scope): subject

* type（必须）: commit 的类别，只允许使用下面几个标识；
* scope（可选）: 用于说明 commit 影响的范围，比如数据层、控制层、视图层、功能模块等等；
* subject（必须）: commit 的简短描述，推荐以动词开头，比如设置、更新、新增、移除、撤销等；

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

commit message 例子：

`style: 格式化代码`

`feat(DevEngine): 新增 completeInitialize 完整初始化方法，移除 getMMKVConfig、getMMKVByHolder 方法`


### 3 分支管理规范

代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。正确的英文拼写和语法可以让阅读者易于理解，避免歧义。

> 注意：即使纯拼音命名方式也要避免采用。但 `alibaba`、`taobao`、`youku`、`hangzhou` 等国际通用的名称，可视同英文。





