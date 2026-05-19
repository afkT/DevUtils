Change Log
==========

Version 1.0.7 *(2026-05-17)*
----------------------------

* `[Add]` Data Binding 适配器体系大幅扩展：`bindingadapters` 下 View 布局参数（margin / padding、宽高、变换、可见性等，`binding_*` 命名）；TextView / EditText（样式、Html、Span、compound drawable、键盘开闭与延迟、光标与 TextWatcher 等）；EditText InputFilter（`EditTextInputFilter`、`EtInputFilterAttrs`：`ETIFSpec` / `EtInputFilterPresetSpec` 预设，`binding_et_input_filter_preset`、过滤器数组 set / append / merge / clear、按字节长度；单行 / 数值 / 邮箱 / 金额 / 手机号 / 验证码等，及标签、字母限定、百分比、端口等扩展预设与 `allowSpace`、`scale` 等配置）；ImageView 引擎加载（`ImageViewLoadEngine`）与原生 API（`ImageViewLoadNative`）；RecyclerView（LayoutManager、Adapter 通知、ItemDecoration、滚动监听）；View / RecyclerView / ListView 滚动与延迟滚动（含 `_ts` 时间戳同值二次触发、`XYI` 坐标）；单击 / 防抖 / 计数 / 长按 / 触摸及扩大点击区域；动画（透明度、平移、旋转、缩放、摇晃）与 `binding_view_shape`、状态列表 Selector；统一委托 DevApp `*Utils`

* `[Add]` 扩展 `qualifies` 与相等性：`QualifiesExt`、`QualifiesLiveDataExt`、`QualifiesObservableExt`、`QualifiesObservableFieldExt`、`QualifiesStateFlowExt`；`CompareValue` 与 `compareValueEquals`，供绑定侧 IfValueEquals、IfCompareValueEquals 等条件控制

* `[Add]` 新增多种布局与控件 styles 资源（含 ShadowLayout、SeekBar、WebView、ViewPager 等）及 padding / margin 维度样式

* `[Add]` RecyclerView 绑定适配器；Adapter 链路增加 LiveData 支持并实现智能线程更新

* `[Fix]` 修复并优化 WebView 配置与缓存相关逻辑（WebViewAssist）

* `[Fix]` 修复 EditText / TextView 等绑定适配器可空参数 NPE；margin / padding 绑定 `reset` 默认可清空

* `[Refactor]` 绑定适配器按职责拆分文件并重命名（`ViewProperty` → `View`、`EditTextView` → `EditText` 等）；`shouldTrigger*` 统一为 `qualifies*`；TriggerEffect 扩展迁至 `extensions.qualifies`

* `[Refactor]` ValueLiveData、BaseIntent、AppExecutors、NetworkBoundResource、弃用适配器与 ItemLifecycle 等可见性、命名与可扩展性调整

* `[Chore]` 依赖 DevAssist、DevBase 等库同步升级；`bindingadapters`、`qualifies` 扩展及 `shadow_layout_styles` 等代码格式化

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
