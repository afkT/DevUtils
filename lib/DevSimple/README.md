
## DevSimple

DevSimple 是 DevUtils 系列中的 **简单敏捷开发库**，面向 **Data Binding + MVVM** 场景，在聚合多库能力之上提供 BindingAdapter、列表状态模型、仓库模式骨架、Kotlin 扩展与统一布局样式（`ViewTheme`），减少样板代码。


## 摘要

* [该库解决什么问题](#该库解决什么问题)
* [DevSimple 库亮点](#devsimple-库亮点)
* [Gradle](#gradle)
* [聚合依赖](#聚合依赖api-传递)
* [目录结构](#目录结构)
* [项目类结构](#项目类结构---包目录)
* [DevSimple API](#devsimple-api)
* [bindingadapters 常用属性](#bindingadapters-常用属性节选)
* [ViewTheme 布局样式](#viewtheme-布局样式)
* [使用示例](#使用示例)
* [示例项目](#示例项目)


### 该库解决什么问题

* 布局/XML 中大量调用 `ViewUtils`、`TextViewUtils`、`ImageUtils` 等工具类，代码冗长且难维护
* RecyclerView + DataBinding 列表需要重复的 Adapter、ViewHolder、刷新逻辑
* 本地数据库 + 网络请求的「先展示缓存、再拉网络、写回 DB」流程样板多
* LiveData 同值无法二次触发、需在主/子线程间智能 `setValue` / `postValue`
* 多渠道、多尺寸、图片 Engine 与 Binding 需要统一入口
* 布局 `layout_width` / `margin` / `gravity` 等重复书写


### DevSimple 库亮点

* **一站式聚合**：`api` 传递依赖 DevAssist、DevBase、DevEngine、DevWidget、DevRetrofit，并集成 [binding-collection-adapter](https://github.com/evant/binding-collection-adapter)
* **声明式 BindingAdapter**：`app:binding_*` 属性覆盖 View 布局参数、可见性、状态、点击防抖、形状、图片加载、RecyclerView、滚动等（委托 DevApp `*Utils`）
* **列表模型**：`AdapterModel` + `ObservableArrayList` + `existData` LiveData，配合 `itemBinding` / `items` 使用
* **仓库骨架**：`NetworkBoundResource` / `NetworkBoundScopeResource`（Architecture Components 风格）+ `Resource` / `Status`
* **LiveData 增强**：`ValueLiveData`、`StateIntLiveData`、`smartUpdateValue`、`qualifies*` 与时间戳 `*_ts` 二次触发
* **ViewTheme 样式族**：32+ 控件样式 XML，统一 Match / Gravity / Margin / Padding 命名


### 最新版本

| module  | DevSimple |
|:-------:|:---------:|
| version | [![][maven_svg]][maven] |

### Gradle

```groovy
dependencies {
    // DevSimple - 简单敏捷开发库 ( Data Binding + MVVM 敏捷层 )
    implementation 'io.github.afkt:DevSimple:1.0.6'
}
```

> 模块已开启 **viewBinding**、**dataBinding**；引用方工程若使用 Binding 布局，需同样开启 `dataBinding`（或 `viewBinding` 视场景而定）。


### 聚合依赖（api 传递）

DevSimple 发布时通过 `api` 暴露以下库（工程内为 `project` 依赖），业务侧通常只需依赖 DevSimple 即可使用其能力：

| 依赖 | 说明 |
|:-----|:-----|
| [DevAssist](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md) | 逻辑辅助、Engine 兼容 |
| [DevBase](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md) | Activity/Fragment MVP·MVVM 基类、`DevIntent` 等 |
| [DevEngine](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md) | 图片等 Engine 解耦；`binding_image_*` 走 `IImageEngine` |
| [DevWidget](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md) | 自定义 View（样式含 ShadowLayout 等） |
| [DevRetrofit](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/README.md) | 协程请求；`NetworkBoundScopeResource` 对接 `scopeExecuteRequest` |

另含 **binding-collection-adapter**（recyclerview、viewpager2）。

相关兄弟库文档（非 DevSimple 传递依赖，可按需单独引入）：

* [DevApp](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md) · [DevHttpCapture](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/README.md) · [DevEnvironment](https://github.com/afkT/DevUtils/blob/master/lib/Environment/README.md)


## 目录结构

```
- dev.simple                  | 根目录
   - bindingadapters          | Data Binding 适配器
      - attribute             | 多参数绑定数据类 ( Margins、ShapeAttrs 等 )
      - view                  | 各控件 @BindingAdapter 实现
   - core                     | 核心能力
      - app                   | AppExecutors、BaseIntent
      - adapter               | AdapterModel 列表模型
      - channel               | AppChannel 多渠道门面
      - livedata              | ValueLiveData、StateIntLiveData、AbsentLiveData
   - extensions               | Kotlin 扩展
      - hi / hiif             | hiIf* 链式判空
      - size                  | AppSize、AppAutoSize
      - image                 | AppImageConfig、AppAutoImageConfig
      - equality              | 相等性、CompareValue
      - qualifies             | 绑定触发条件 qualifies*
   - features                 | 功能模块
      - repository            | Resource、NetworkBoundResource
      - web                   | WebViewAssist
      - deprecated/adapter    | 旧版 DataBinding Adapter（弃用）
   - interfaces               | BindingGet、BindingConsumer、CompareValue 等
   - res/values               | ViewTheme.* 样式族
```


## 项目类结构 - [包目录][包目录]

### bindingadapters - [bindingadapters][bindingadapters]

* **属性数据类**（[attribute][attribute]）：`Margins`、`Paddings`、`WidthHeightDims`、`ShapeAttrs`、`SpanAttrs`、`StateListAttrs`、`XYI`（滚动坐标）等

* **控件适配器**（[view][binding_view]）：按职责拆分的 BindingAdapter 文件，统一 **`binding_` 前缀**（布局中使用 `app:binding_xxx="@{...}"`）

| 文件 | 能力概要 |
|:-----|:---------|
| [View.kt][View.kt] | id、clip、子 View、宽高、LayoutParams、focus、`*_ts` 时间戳触发 |
| [ViewMargin.kt][ViewMargin.kt] / [ViewPadding.kt][ViewPadding.kt] | margin / padding，支持 `binding_*Reset` 清空 |
| [ViewVisibility.kt][ViewVisibility.kt] | visibility、`IfNotEmpty` / `IfNotNull` / `IfValueEquals` / `IfCompareValueEquals` |
| [ViewState*.kt][View.kt] | enabled、selected、pressed、checked、clickable 等状态 |
| [ViewListener.kt][ViewListener.kt] | 单击、防抖、计数、长按、触摸、扩大点击区域 |
| [ViewAnimation.kt][ViewAnimation.kt] | 透明度、平移、旋转、缩放、摇晃 |
| [ViewShape.kt][ViewShape.kt] | 圆角、渐变、描边（`ShapeUtils`） |
| [ViewStateList.kt][ViewStateList.kt] | 文字色/背景 Selector |
| [ViewScroll.kt][ViewScroll.kt] | RecyclerView / ListView / ScrollView 滚动与 snap |
| [TextView.kt][TextView.kt] / [TextViewSpan.kt][TextViewSpan.kt] | 文本样式、Html、Span、compound drawable |
| [EditText.kt][EditText.kt] / [EditTextKeyboard.kt][EditTextKeyboard.kt] | 输入限制、TextWatcher、软键盘 |
| [ImageView.kt][ImageView.kt] | bitmap、drawable、scaleType、tint 等原生属性 |
| [ImageViewLoadEngine.kt][ImageViewLoadEngine.kt] | DevEngine 加载：`binding_image_url` 等 |
| [ImageViewLoadNative.kt][ImageViewLoadNative.kt] | 原生 set：`binding_image_native_*` |
| [RecyclerView.kt][RecyclerView.kt] | LayoutManager、Adapter 通知、Decoration、滚动监听 |

> 属性名由 `@BindingAdapter` 定义，**无** `attrs.xml`；完整列表以各源文件为准。子前缀：`binding_tv_`、`binding_et_`、`binding_iv_`、`binding_rv_`、`binding_image_`、`binding_view_` 等。

### core - [core][core]

* **AppExecutors**（[AppExecutors.kt][AppExecutors.kt]）：`diskIO` / `networkIO` / `mainThread` 全局线程池

* **BaseIntent**（[BaseIntent.kt][BaseIntent.kt]）：包装 `DevIntent` 的 `insert` / `reader` 链式传参

* **AdapterModel**（[AdapterModel.kt][AdapterModel.kt]）：`ObservableArrayList<T>` + `existData: LiveData<Boolean>`，增删改查与 `refreshExist()`

* **AppChannel**（[AppChannel.kt][AppChannel.kt]）：反射 `ChannelFlavorsImpl` 读取多渠道信息

* **LiveData**（[livedata][livedata]）：`ValueLiveData`（`smartUpdateValue`）、`StateIntLiveData`（业务状态枚举）、`AbsentLiveData`（恒 null 占位）

### extensions - [extensions][extensions]

* **hiIf**（[hiif][hiif]）：`hiIfNotNull`、`hiIfNotNullAs` 等，带 Kotlin contracts

* **size**（[AppSize.kt][AppSize.kt]）：dp / sp / px 换算

* **image**（[AppImageConfig.kt][AppImageConfig.kt]）：与 `DevSimple.setImageCreator` 配合的 `ImageConfig` 工厂

* **qualifies**（[qualifies][qualifies]）：绑定侧是否触发（配合 `*_ts`、集合非空等）

* **equality**（[EqualityExt.kt][EqualityExt.kt]）：`compareValueEquals(CompareValue)`

* **Price**、**DevSource**：价格格式化、`DevSource` 扩展

### features - [features][features]

* **repository**（[repository][repository]）：`Status`、`Resource<T>`、`ApiResponse`、`NetworkBoundResource`、`NetworkBoundScopeResource`

* **web**（[WebViewAssist.kt][WebViewAssist.kt]）：WebView 全局 Builder、Cookie、缓存、UA、生命周期等

* **deprecated/adapter**（[BaseDataAdapter.kt][BaseDataAdapter.kt]）：基于 `DevDataAdapter` 的旧列表方案，新代码推荐 **binding-collection-adapter + AdapterModel**

### interfaces - [interfaces][interfaces]

* [BindingGet][BindingGet] / [BindingConsumer][BindingConsumer] / [BindingClick][BindingClick]：DataBinding 事件契约

* [CompareValue][CompareValue]：`compareValue()`，用于 `*_IfCompareValueEquals` 条件显示


## DevSimple API

* **模块入口 ->** [DevSimple.kt][DevSimple.kt]

| 方法 | 注释 |
|:-----|:-----|
| getDevSimpleVersionCode | 获取 DevSimple 版本号 |
| getDevSimpleVersion | 获取 DevSimple 版本 |
| setImageCreator | 设置 `ImageConfig` 创建器（配合 `binding_image_*`） |

`BuildConfig` 中还写入 DevAssist、DevEngine、DevWidget、DevRetrofit 等聚合库版本号，便于运行时查询。


## bindingadapters 常用属性（节选）

> 完整定义见 [bindingadapters/view][binding_view] 各文件。

| 分组 | 代表属性 | 说明 |
|:-----|:---------|:-----|
| 可见性 | `binding_visibility`、`binding_visibleOrGone` | 显隐控制 |
| 条件显隐 | `binding_visibleOrGone_IfNotEmpty`、`binding_visibleOrGone_IfCompareValueEquals` | 配合 `CompareValue` |
| 外边距 | `binding_margin`、`binding_marginLeft`、`binding_marginReset` | 支持 reset 清空 |
| 图片引擎 | `binding_image_url`、`binding_image_engine`、`binding_image_config` | DevEngine 加载 |
| 图片原生 | `binding_image_native_uri`、`binding_image_native_bitmap` | 不经过 Engine |
| RecyclerView | `binding_rv_adapter`、`binding_rv_layout_manager`、`binding_rv_notify_data_set_changed_ts` | `*_ts` 同值二次触发 |
| 形状 | `binding_view_shape`、`binding_view_shape_radius_color` | `ShapeUtils` |
| 点击 | `binding_view_click`、`binding_view_click_debounce` | 防抖、计数等 |
| 滚动 | `binding_scroll_rv`、`binding_scroll_smooth_adapter_index` | 含延迟滚动 `*_ts` |

* **绑定触发辅助 ->** [BindingAdaptersExt.kt][BindingAdaptersExt.kt]

| 方法 | 注释 |
|:-----|:-----|
| qualifiesBindingAction | 判断 Long 时间戳是否应触发绑定 |
| qualifiesScroll | 判断滚动类绑定是否应触发 |


## core API（节选）

* **AppExecutors ->** [AppExecutors.kt][AppExecutors.kt]

| 方法 | 注释 |
|:-----|:-----|
| instance | 获取单例 |
| diskIO | 磁盘 IO 线程池 |
| networkIO | 网络 IO 线程池 |
| mainThread | 主线程 Handler（支持 postDelayed） |

* **AdapterModel ->** [AdapterModel.kt][AdapterModel.kt]

| 成员/方法 | 注释 |
|:----------|:-----|
| items | `ObservableArrayList<T>` 数据源 |
| existData | `LiveData<Boolean>` 是否存在数据 |
| refreshExist | 刷新 exist 状态（智能 set/post） |
| add / remove / clear / setNewData | 列表增删改（内部 refreshExist） |

* **ValueLiveData / StateIntLiveData ->** [ValueLiveData.kt][ValueLiveData.kt]、[StateIntLiveData.kt][StateIntLiveData.kt]

| 能力 | 注释 |
|:-----|:-----|
| smartUpdateValue | 相等性判断后 setValue 或 postValue |
| StateInt 枚举 | NORMAL / ING / SUCCESS / ERROR / EMPTY 等业务状态 |


## features.repository API（节选）

* **Resource ->** [Resource.kt][Resource.kt]

| 工厂方法 | 注释 |
|:---------|:-----|
| loading | 加载中 |
| success | 成功（含 data） |
| error | 失败 |
| empty | 空数据 |

* **NetworkBoundResource ->** [NetworkBoundResource.kt][NetworkBoundResource.kt]

| 方法（子类实现） | 注释 |
|:---------------|:-----|
| shouldFetch | 是否应从网络拉取 |
| loadFromDb | 从 DB 加载 LiveData |
| fetchService | 发起网络请求 LiveData |
| saveFetchData | 保存网络数据到 DB |
| onFetchFailed | 拉取失败回调 |
| asLiveData | 观察 `Resource<ResultType>` |

* **NetworkBoundScopeResource ->** [NetworkBoundScopeResource.kt][NetworkBoundScopeResource.kt]：在 `CoroutineScope` 中通过 DevRetrofit `scopeExecuteRequest` 拉取，子类实现 `fetchRequest()`。


## ViewTheme 布局样式

`res/values` 下提供以 **`ViewTheme`** 为根的样式族（约 32 个 xml），用于减少布局中重复的 `layout_width`、`gravity`、`margin` 等写法。

**命名模式：**

* 根：`ViewTheme.<Widget>`（如 `ViewTheme.TextView`）
* 铺满：`ViewTheme.<Widget>.Match` / `.Match.Width` / `.Match.Height`
* 方向：`ViewTheme.LinearLayout.Vertical` / `.Horizontal`
* 对齐：`ViewTheme.<Widget>.Gravity.CENTER`、`.Gravity.Layout.CENTER`
* 边距：`ViewTheme.<Widget>.Margin`、`.Padding`；具体 dp 见 [padding_margin_styles.xml][padding_margin_styles.xml]

**主要样式文件：**

| 文件 | 内容 |
|:-----|:-----|
| [styles.xml][styles.xml] | `ViewTheme`、`ViewTheme.View`、Gravity 系列 |
| [padding_margin_styles.xml][padding_margin_styles.xml] | Padding / Margin 预设 |
| [shadow_layout_styles.xml][shadow_layout_styles.xml] | ShadowLayout |
| [text_view_styles.xml][text_view_styles.xml] | TextView |
| [recycler_view_styles.xml][recycler_view_styles.xml] | RecyclerView |
| [image_view_styles.xml][image_view_styles.xml] | ImageView（含 FIT_XY、CENTER_CROP 等） |

布局示例：

```xml
<ImageView
    style="@style/ViewTheme.ImageView.FIT_XY"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_120" />
```


## 使用示例

### 1. 初始化图片 Binding（可选）

在 `Application` 或启动阶段设置 `ImageConfig` 创建器，供 `binding_image_url` 等使用：

```kotlin
DevSimple.setImageCreator { key, _ ->
    // 返回 ImageConfig，key 一般为 url
    ImageConfig.create().setUrl(key)
}
```

### 2. 布局中使用 BindingAdapter

```xml
<ImageView
    app:binding_image_url="@{itemValue.picture}" />

<TextView
    app:binding_visibleOrGone="@{viewModel.showTips}" />
```

### 3. binding-collection-adapter + AdapterModel

```kotlin
class ButtonAdapterModel : AdapterModel<ButtonEnum>() {
    val itemBinding = ItemBinding.of<ButtonEnum>(R.layout.item_button) { itemBinding, _, item ->
        itemBinding.set(BR.item, item)
            .bindExtra(BR.listener, BindingConsumer { /* click */ })
    }
}
```

```xml
<androidx.recyclerview.widget.RecyclerView
    app:itemBinding="@{viewModel.buttonAdapterModel.itemBinding}"
    app:items="@{viewModel.buttonAdapterModel.items}" />
```

### 4. NetworkBoundResource（模板）

```kotlin
class UserRepository(private val executors: AppExecutors) {
    fun loadUser(id: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, UserDto>(executors) {
            override fun shouldFetch(data: User?) = data == null || isStale(data)
            override fun loadFromDb() = userDao.getUser(id)
            override fun fetchService() = api.fetchUser(id)
            override fun saveFetchData(item: UserDto) { userDao.insert(item.toEntity()) }
            override fun onFetchFailed(error: Throwable?, items: UserDto?) { /* ... */ }
        }.asLiveData()
    }
}
```

协程 + Retrofit 场景可使用 `NetworkBoundScopeResource` 并实现 `fetchRequest()`。

### 5. WebViewAssist 全局配置

```kotlin
WebViewAssist.Builder()
    .setJavaScriptEnabled(true)
    // ...
    .build()
    .also { WebViewAssist.setGlobalBuilder(it) }
```

### 6. AppExecutors

```kotlin
AppExecutors.instance().networkIO().execute { /* 网络任务 */ }
AppExecutors.instance().mainThread().post { /* UI */ }
```


## 示例项目

直接运行、查看使用示例项目代码！！！

[DevUtilsApp][DevUtilsApp]！！！
[DevUtilsApp][DevUtilsApp]！！！
[DevUtilsApp][DevUtilsApp]！！！


## 事项

* DevSimple **不直接依赖** DevApp，BindingAdapter 内部通过 DevAssist / 传递依赖调用 `dev.utils.app.*Utils`；日志、权限等仍见 [DevApp](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md)

* Data Binding 混淆：若自定义 `@BindingAdapter`，注意 ProGuard 规则；模块内 [proguard-rules.pro](proguard-rules.pro) 可按工程需要补充

* 旧版 `features.deprecated.adapter` 仍可用，新列表推荐 **AdapterModel + binding-collection-adapter**

* [Change Log](CHANGELOG.md)

* 与 [DevBase](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md) Simple 敏捷基类配合：`AppActivity` / `AppFragment` 等


## 设计思路

DevSimple 将 **Data Binding 声明式 UI**、**MVVM 状态与列表模型**、**仓库层网络+DB 流程** 收敛到同一模块，并 `api` 聚合 DevBase / DevEngine / DevRetrofit 等，使业务工程只需依赖一个库即可获得敏捷开发所需的大部分能力。

BindingAdapter 统一 `binding_` 前缀并委托 DevApp 工具类，避免在 XML 与 Java/Kotlin 间重复书写 imperative 代码；`qualifies*` 与 `*_ts` 解决 LiveData / Binding 同值不刷新的常见问题；`ViewTheme` 则与 Binding 互补，规范布局尺寸与对齐写法。

架构上可与 [DevBase Simple 基类](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md#simple-简化敏捷开发基类---simple) 组合：`AppActivity<Binding, ViewModel>` + `AdapterModel` + `binding_*` 形成完整页面开发链路。




[maven_svg]: https://img.shields.io/badge/Maven-1.0.6-brightgreen.svg?style=for-the-badge
[maven]: https://search.maven.org/search?q=io.github.afkt
[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple
[bindingadapters]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters
[attribute]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute
[binding_view]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view
[View.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/View.kt
[ViewMargin.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewMargin.kt
[ViewPadding.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewPadding.kt
[ViewVisibility.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewVisibility.kt
[ViewListener.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewListener.kt
[ViewAnimation.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewAnimation.kt
[ViewShape.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewShape.kt
[ViewStateList.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateList.kt
[ViewScroll.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScroll.kt
[TextView.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/TextView.kt
[TextViewSpan.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/TextViewSpan.kt
[EditText.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/EditText.kt
[EditTextKeyboard.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/EditTextKeyboard.kt
[ImageView.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ImageView.kt
[ImageViewLoadEngine.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ImageViewLoadEngine.kt
[ImageViewLoadNative.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ImageViewLoadNative.kt
[RecyclerView.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/RecyclerView.kt
[core]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core
[AppExecutors.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/app/AppExecutors.kt
[BaseIntent.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/app/BaseIntent.kt
[AdapterModel.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/adapter/AdapterModel.kt
[AppChannel.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/channel/AppChannel.kt
[livedata]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata
[extensions]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions
[hiif]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif
[AppSize.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/size/AppSize.kt
[AppImageConfig.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/image/AppImageConfig.kt
[qualifies]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies
[EqualityExt.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/equality/EqualityExt.kt
[features]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features
[repository]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository
[WebViewAssist.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/web/WebViewAssist.kt
[BaseDataAdapter.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/BaseDataAdapter.kt
[interfaces]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces
[BindingGet]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingGet.kt
[BindingConsumer]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingConsumer.kt
[BindingClick]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingClick.kt
[CompareValue]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/CompareValue.kt
[DevSimple.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/DevSimple.kt
[BindingAdaptersExt.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/BindingAdaptersExt.kt
[ValueLiveData.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/ValueLiveData.kt
[StateIntLiveData.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/StateIntLiveData.kt
[Resource.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/Resource.kt
[NetworkBoundResource.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/NetworkBoundResource.kt
[NetworkBoundScopeResource.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/NetworkBoundScopeResource.kt
[padding_margin_styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/padding_margin_styles.xml
[styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/styles.xml
[shadow_layout_styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/shadow_layout_styles.xml
[text_view_styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/text_view_styles.xml
[recycler_view_styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/recycler_view_styles.xml
[image_view_styles.xml]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/res/values/image_view_styles.xml
[proguard-rules.pro]: https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/proguard-rules.pro
[DevUtilsApp]: https://github.com/afkT/DevUtils/blob/master/application/DevUtilsApp/src/main/java/afkt/project
