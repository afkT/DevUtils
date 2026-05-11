Change Log
==========

Version 1.0.7 *(2026-05-07)*
----------------------------

* `[Add]` TextView DataBinding：扩展 `@BindingAdapter`（下划线/删除线、行距、字号 sp、行数/ems、密码显示、Html 委托 `TextViewUtils` 等），见 `bindingadapters/view/TextView.kt`

* `[Add]` 新增多种布局与控件 styles 资源（含 ShadowLayout、SeekBar、WebView、ViewPager 等）及 padding/margin 维度样式

* `[Add]` RecyclerView 绑定适配器；Adapter 链路增加 LiveData 支持并实现智能线程更新

* `[Fix]` 修复并优化 WebView 配置与缓存相关逻辑（WebViewAssist）

* `[Refactor]` ValueLiveData、BaseIntent、AppExecutors、NetworkBoundResource、弃用适配器与 ItemLifecycle 等可见性、命名与可扩展性调整

* `[Chore]` 依赖 DevAssist、DevBase 等库同步升级

Version 1.0.6 *(2026-03-08)*
----------------------------

* `[Add]` 新增多个 styles xml 样式文件

* `[Update]` 更新 PayloadLiveData 为 ValueLiveData

Version 1.0.5 *(2026-01-19)*
----------------------------

* `[Add]` 新增 AdapterModel 通用模型

* `[Add]` 新增 PayloadLiveData、StateIntLiveData

Version 1.0.4 *(2025-10-12)*
----------------------------

* `[Merge]` 合并 DevMVVM、DevAgile 代码统一到 DevSimple 中并进行重构

* `[Refactor]` 重构 DevSimple 模块，重新调整包名目录结构，删除无用重复冗余类，简化代码提高可读性

Version 1.0.3 *(2025-03-21)*
----------------------------

* `[Chore]` 依赖 DevMVVM 库同步升级

Version 1.0.2 *(2024-12-11)*
----------------------------

* `[Chore]` 依赖 DevMVVM 库同步升级

Version 1.0.1 *(2024-06-04)*
----------------------------

* `[Config]` 更新 IUIController、UIThemeExt 默认配置

* `[Update]` 更新 SimpleActivityImpl、SimpleFragmentImpl 匿名函数处理以及相关联实用类变更

Version 1.0.0 *(2024-05-15)*
----------------------------

* Initial release
