Change Log
==========

Version 1.4.7 *(2026-06-11)*
----------------------------

* `[Add]` 新增下拉刷新上拉加载引擎 `IRefreshEngine`、`DevRefreshEngine`，扩展头部底部尺寸、拖拽动画、开关与状态查询等接口能力；`view()` 改为 `WeakReference<View>` 以降低内存泄漏风险

* `[Add]` 新增事件总线引擎 `IEventBusEngine`、`DevEventBusEngine`，支持 LiveEventBus 初始化配置、跨进程/应用广播；移除 sticky 事件接口

* `[Add]` 新增 WebView 引擎 `IWebEngine`、`DevWebEngine`，集成 AndroidX WebKit（升级至 1.17.0-alpha01），覆盖 postMessage、暗色模式、安全浏览、代理、PaymentRequest、WebAuthn、BFCache 等跨内核能力；扩展页面监听、Cookie 策略、状态保存与缓存模式可空配置

* `[Add]` 扩展 `IImageEngine`：暂停/恢复加载、预加载、缓存清理、低内存回调及多种 `ImageView` 显示与格式转换重载

* `[Add]` 新增 PopTip 引擎 `IPopTipEngine`、`DevPopTipEngine`：非阻断式文本提示，支持主题样式、自定义布局、状态图标、按钮文本/资源 ID、单例句柄与生命周期绑定（`LifecycleOwner`）

* `[Add]` 新增 PopNotification 引擎 `IPopNotificationEngine`、`DevPopNotificationEngine`：非阻断式通知条，支持单例/多例、覆盖动画与位移拦截器配置

* `[Add]` 新增路由引擎 `IRouterEngine`、`DevRouterEngine`：集成 TheRouter，提供页面导航、参数注入、AOP 拦截、异步 Intent/Fragment 创建及跨模块服务获取；接口参数由 `Object` 收紧为 `Context`、`Fragment`、`Uri` 等具体类型

* `[Refactor]` PopTip、PopNotification 配置项按功能分组并补充 DialogX 映射注释；路由引擎接口注释与泛型约束优化

Version 1.4.6 *(2026-05-20)*
----------------------------

* `[Refactor]` DevCallback、DevFunction、DevTimerAssist、EditTextSearchAssist 等回调与辅助类方法命名规范化

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.4.5 *(2026-03-08)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.4.4 *(2025-10-12)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

* `[Add]` Debug 编译辅助开发 Engine 模块

Version 1.4.3 *(2025-03-21)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

* `[Add]` DevToastEngine 吐司模块

Version 1.4.2 *(2024-12-11)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.4.1 *(2024-05-06)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.4.0 *(2024-01-18)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.3.9 *(2023-07-01)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.3.8 *(2022-09-18)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

* `[Add]` 新增 DevDataAdapter#onAttachedToRecyclerView 方法初始化处理

Version 1.3.7 *(2022-08-07)*
----------------------------

* `[Add]` DevTimerAssist 定时器辅助类

Version 1.3.6 *(2022-07-04)*
----------------------------

* `[Update]` 更新 IMediaEngine 接口，新增 openPreview 方法、更新 Path 返回为 Uri

Version 1.3.5 *(2022-06-02)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.3.4 *(2022-05-13)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

* `[Update]` 更新 DevPage 变量命名 DF_XXX 为 DEF_XXX

Version 1.3.3 *(2022-03-20)*
----------------------------

* `[Update]` 更新 PageAssist 同步使用 DevPage 默认页数信息

Version 1.3.2 *(2022-01-23)*
----------------------------

* `[Add]` DevVariableExt 变量操作基类扩展类

Version 1.3.1 *(2022-01-10)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.3.0 *(2021-12-30)*
----------------------------

* `[Chore]` 依赖 DevApp 库同步升级

Version 1.2.9 *(2021-12-20)*
----------------------------

* `[Add]` CommonState 通用状态类

* `[Add]` DevHistory 历史数据记录功类

* `[Add]` DevPage#getDefault 方法

* `[Add]` DevDataAdapter#initialize 方法

* `[Add]` DevDataAdapter 相关类默认初始化处理

Version 1.2.8 *(2021-11-26)*
----------------------------

* `[Add]` EditTextWatcherAssist#OtherListener 其他事件触发扩展抽象类

Version 1.2.7 *(2021-10-06)*
----------------------------

* `[Add]` DevDataAdapter#getRecyclerView、setRecyclerView、bindAdapter

Version 1.2.6 *(2021-09-20)*
----------------------------

* `[Refactor]` review code、代码格式化处理、方法名、参数名、变量名等规范排查

* `[Add]` DevDataList、DevDataListExt

* `[Add]` DevEngineAssist、DevBarCodeEngine 条形码模块

* `[Delete]` DevHttpEngine

Version 1.2.5 *(2021-06-28)*
----------------------------

* `[Update]` DevStorageEngine 支持 OnInsertListener 插入结果监听

* `[Add]` DevSource Bitmap、Drawable 字段

Version 1.2.4 *(2021-06-21)*
----------------------------

* `[Update]` DevStorageEngine 为 DevKeyValueEngine 用于键值对存储 Engine

* `[Add]` DevSource mInputStream 字段

* `[Add]` DevStorageEngine 用于多媒体资源存储 ( 高低版本适配 ) Engine

Version 1.2.2-3 *(2021-06-04)*
----------------------------

* `[Add]` DevAnalyticsEngine、DevPushEngine、DevShareEngine

Version 1.2.1 *(2021-05-19)*
----------------------------

* `[Update]` 修改 Function 为 DevFunction

Version 1.2.0 *(2021-05-09)*
----------------------------

* `[sync]` sync to Maven Central

Version 1.1.9 *(2021-03-31)*
----------------------------

* `[Add]` DevDataAdapterExt2

Version 1.1.8 *(2021-03-27)*
----------------------------

* `[Add]` DevDataAdapter#getActivity、setActivity、parentContext

* `[Add]` DevPage#getConfig、getConfigPage、getConfigPageSize

Version 1.1.7 *(2021-03-24)*
----------------------------

* `[Update]` DevDataAdapterExt

Version 1.1.6 *(2021-03-23)*
----------------------------

* `[Add]` DataManager#isLastPositionAndGreaterThanOrEqual、addLists 方法

* `[Add]` DevDataAdapter Context 构造函数

* `[Add]` DevDataAdapterExt

Version 1.1.5 *(2021-03-19)*
----------------------------

* `[Add]` DevCacheEngine

Version 1.1.4 *(2021-03-16)*
----------------------------

* `[Add]` DevStorageEngine

Version 1.1.3 *(2021-03-12)*
----------------------------

* `[Add]` DevCompressEngine、DevMediaEngine

Version 1.1.2 *(2021-03-10)*
----------------------------

* `[Update]` 修改 Engine Config 接口名

Version 1.1.1 *(2021-03-10)*
----------------------------

* `[Add]` DevPermissionEngine

* `[Update]` 修改 Engine Config 接口名

Version 1.1.0 *(2021-03-02)*
----------------------------

* `[Add]` Function Operation Utils

Version 1.0.9 *(2021-02-15)*
----------------------------

* `[Add]` DevDataAdapter ( DataManager RecyclerView Adapter )

* `[Add]` BitmapListener、DrawableListener

* `[Update]` DevSource、IImageEngine

Version 1.0.8 *(2021-02-08)*
----------------------------

* `[Update]` 重新调整包名、类名以及重新部分代码

Version 1.0.7 *(2020-12-28)*
----------------------------

* `[Add]` EditTextSearchAssist EditText 搜索辅助类

Version 1.0.6 *(2020-12-10)*
----------------------------

* `[Style]` 代码格式化处理 ( 间距美化调整等 )

* `[Update]` 修改 CallBack 相关代码为 Callback

Version 1.0.5 *(2020-11-15)*
----------------------------

* `[Refactor]` 使用 QAPlugs ( PMD、findbugs、checkstyle )、IDEA Analyze 进行代码质量分析、代码优化等

Version 1.0.4 *(2020-07-19)*
----------------------------

* `[Delete]` PageAssist 删除 PageNumReady 属性及相关方法

Version 1.0.3 *(2019-12-25)*
----------------------------

* `[Add]` IJSONEngine#isJSONObject、isJSONArray

* `[Add]` IMultiSelectEdit#getSelectSize、getDataCount

* `[Update]` RequestStatusAssist 为 RequestStateAssist 以及方法名 Status 修改为 State

Version 1.0.2 *(2019-09-19)*
----------------------------

* `[Update]` 修改 AbstractCallBack、AdapterDataAssist 部分方法返回值 ( 返回当前对象，方便链式调用 )

Version 1.0.1 *(2019-09-12)*
----------------------------

* `[Update]` DevJSONEngine#toJsonIndent 删除 param indent ( 缩进单位 )

Version 1.0.0 *(2019-09-03)*
----------------------------

* Initial release
