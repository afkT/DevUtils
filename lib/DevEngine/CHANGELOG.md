Change Log
==========

Version 1.1.9 *(2026-06-11)*
----------------------------

* `[Add]` 实现下拉刷新引擎 SmartRefreshLayout 适配：配置类、弱引用 `RefreshItem`、`release()` 及完整 Kotlin 扩展函数

* `[Add]` 实现事件总线 LiveEventBus 引擎：`EventBusConfig`、空安全校验与扩展函数；重构初始化/配置为接收者扩展

* `[Add]` 实现 WebView 系统引擎：默认性能配置、AndroidX WebKit 特性封装、Cookie 同步落盘；修复销毁与兼容性 API 调用的异常崩溃

* `[Add]` 扩展 Glide 图片引擎与 `image` 扩展函数；修复文件处理返回值及 `ImageConfig` 链式调用

* `[Add]` 实现 DialogX PopTip / PopNotification 引擎及扩展函数：单例管理、对齐常量、安全类型转换与 `LifecycleOwner` 生命周期绑定

* `[Add]` 实现 TheRouter 路由引擎及 `router` 扩展函数：拦截器、导航恢复、参数填充与异步回调

* `[Refactor]` 各引擎 `extensions` 扩展函数统一改为接收者（receiver）风格，配置/初始化方法改为 `Config`、`Application` 等类型扩展；补充 analytics、barcode、cache、web 等模块 KDoc

* `[Upgrade]` 重构模块依赖：DialogX、设备兼容、MVVM 绑定适配器等迁入 DevEngine；调整 Gradle 依赖分组

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.8 *(2026-05-20)*
----------------------------

* `[Add]` 集成 Timber 日志库，新增 Timber 日志引擎实现并扩展 DevEngine 日志能力

* `[Refactor]` 缓存、图片、键值对、媒体、存储等引擎及 BarCode、Glide、MMKV、SharedPreferences 等实现中成员可见性与方法开放度调整，便于继承扩展

* `[Upgrade]` 整理 DeviceCompat、Timber 等依赖声明顺序

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.7 *(2026-03-08)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

* `[Upgrade]` 升级第三方库以及对应 API 变动更新

Version 1.1.6 *(2025-10-12)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

* `[Refactor]` 重新调整包名目录结构，提高可读性

Version 1.1.5 *(2025-03-21)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.4 *(2024-12-11)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.3 *(2024-05-06)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.2 *(2024-01-18)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.1 *(2023-07-01)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.1.0 *(2022-09-18)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.9 *(2022-08-07)*
----------------------------

* `[Add]` 新增 engine kotlin 扩展函数实现代码

Version 1.0.8 *(2022-07-04)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

* `[Upgrade]` 升级第三方库以及对应 API 变动更新

Version 1.0.7 *(2022-06-02)*
----------------------------

* `[Chore]` 更新 Fastjson 为 Fastjson2 及其实现代码，并使用 fastjson2.android 版本

Version 1.0.6 *(2022-05-13)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.5 *(2022-03-20)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.4 *(2022-01-23)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.3 *(2022-01-10)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.2 *(2021-12-30)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.1 *(2021-12-20)*
----------------------------

* `[Chore]` 依赖 DevApp、DevAssist 库同步升级

Version 1.0.0 *(2021-09-20)*
----------------------------

* Initial release
