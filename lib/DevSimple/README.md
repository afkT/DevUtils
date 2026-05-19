
# DevSimple

DevSimple 是 DevUtils 体系中面向 **Data Binding / MVVM** 的简单敏捷开发库，在 [DevBase](https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md) 页面基类之上，提供大量 `app:binding_*` 布局绑定、**ViewTheme** 默认样式、Repository 模板与 LiveData / qualifies 等配套能力，与 [DevAssist](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md)（业务实体与 Engine）、[DevEngine](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md)（第三方框架解耦）分工协作：DevSimple 侧重 **View 层绑定与 XML 开发效率**。

## 核心功能亮点

* **BindingAdapter 体系**：450+ 适配方法、580+ `binding_*` 属性，覆盖 View 布局/可见性/背景/动画，TextView / EditText 全属性与 InputFilter 预设，ImageView 原生与 DevEngine 图片加载，RecyclerView 配置与滚动，单击防抖/计数/长按/触摸区域，以及延迟滚动（`_ts` 同值二次触发）

* **qualifies 条件绑定**：`qualifies` / `qualifiesTrue` / `qualifiesFalse` 及 LiveData、Observable、ObservableField、StateFlow 扩展，配合 `binding_*_IfNotEmpty`、`IfValueEquals`、`IfCompareValueEquals` 等控制 enabled / checked / selected / visible

* **ViewTheme 样式族**：30+ `values/*_styles.xml`，统一常见控件默认宽高、Gravity、Padding / Margin（含 ShadowLayout、WebView、SeekBar 等）

* **NetworkBoundResource**：网络 + 本地数据库 Bound 封装（`Resource`、`ApiResponse`、`AppExecutors` 调度）

* **ValueLiveData.smartUpdateValue**：主线程 `setValue` / 后台 `postValue` 智能选择；**AdapterModel** 列表模型与空态 `existData`

* **WebViewAssist**：WebSettings 链式封装、Cookie、缓存与全局 Builder

* **AppSize / AppAutoSize、AppImageConfig**：尺寸换算与 ImageConfig 键值缓存（对接 DevEngine Image Engine）

* **hiIf\*** 内联条件 DSL、价格与 DevSource 扩展（`toRMB`、`toPrice*`、`.toSource()`）

## Gradle

```gradle
// DevSimple - 简单敏捷开发库 ( Data Binding、ViewTheme、MVVM 配套 )
implementation 'io.github.afkt:DevSimple:1.0.7'
```

## 目录结构

```
- dev.simple                  | 根目录
   - bindingadapters          | Data Binding 适配器
      - attribute             | 多参数合并实体（Margin / Padding / Shape / Span 等）
      - view                  | View / TextView / EditText / RecyclerView 等绑定实现
   - core                     | 核心能力
      - adapter               | AdapterModel 列表模型
      - app                   | AppExecutors、BaseIntent
      - channel               | 渠道抽象 AppChannel
      - livedata              | ValueLiveData、AbsentLiveData、StateInt
   - extensions               | 扩展函数与工具
      - equality              | 相等性比较（Binding 条件用）
      - hi / hiif             | 内联条件分支 hiIf
      - image                 | ImageConfig 键值缓存
      - qualifies             | LiveData / Observable / StateFlow qualifies
      - size                  | AppSize / AppAutoSize 尺寸换算
   - features                 | 功能模块
      - repository            | NetworkBoundResource、Resource、ApiResponse
      - web                   | WebViewAssist
      - deprecated/adapter    | 旧版 DataBinding Adapter（兼容）
   - interfaces               | Binding 回调与 CompareValue
```

## API

- dev.simple                                                     | 根目录
    - [bindingadapters](#devsimplebindingadapters)                | Data Binding 适配器
        - [attribute](#devsimplebindingadaptersattribute)          | 多参数合并实体
        - [view](#devsimplebindingadaptersview)                    | View 绑定实现
    - [core](#devsimplecore)                                      | 核心能力
        - [adapter](#devsimplecoreadapter)                          | AdapterModel
        - [app](#devsimplecoreapp)                                  | AppExecutors、BaseIntent
        - [channel](#devsimplecorechannel)                          | AppChannel
        - [livedata](#devsimplecorelivedata)                        | LiveData 工具
    - [extensions](#devsimpleextensions)                          | 扩展函数
        - [equality](#devsimpleextensionsequality)                  | 相等性
        - [hi](#devsimpleextensionshi)                              | Hi 内联
        - [image](#devsimpleextensionsimage)                        | 图片配置
        - [qualifies](#devsimpleextensionsqualifies)                | qualifies 扩展
        - [size](#devsimpleextensionssize)                          | 尺寸换算
    - [features](#devsimplefeatures)                              | 功能模块
        - [repository](#devsimplefeaturesrepository)                | Repository
        - [web](#devsimplefeaturesweb)                              | WebViewAssist
        - [deprecated/adapter](#devsimplefeaturesdeprecatedadapter)   | 旧版 Adapter（兼容）
    - [interfaces](#devsimpleinterfaces)                          | 接口


## <span id="devsimple">**`dev.simple`**</span>


* **DevSimple ->** [DevSimple.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/DevSimple.kt)

| 方法 | 注释 |
| :- | :- |
| getDevSimpleVersionCode | 获取 DevSimple 版本号 |
| getDevSimpleVersion | 获取 DevSimple 版本 |
| setImageCreator | 设置 ImageConfig 创建器 |

## <span id="devsimplebindingadapters">**`dev.simple.bindingadapters`**</span>

## <span id="devsimplebindingadaptersview">**`dev.simple.bindingadapters.view`**</span>

* **EditTextInputFilter ->** [EditTextInputFilter.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/EditTextInputFilter.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingETInputFilterPreset | `binding_et_input_filter_preset`<br>`binding_et_input_filter_preset_max_length`<br>`binding_et_input_filter_preset_max_lines`<br>`binding_et_input_filter_preset_max_digits`<br>`binding_et_input_filter_preset_integer_digits`<br>`binding_et_input_filter_preset_decimal_digits`<br>`binding_et_input_filter_preset_min_value`<br>`binding_et_input_filter_preset_max_value`<br>`binding_et_input_filter_preset_max_byte_length`<br>`binding_et_input_filter_preset_allow_space`<br>`binding_et_input_filter_preset_allow_single_zero`<br>`binding_et_input_filter_preset_scale`<br>`binding_et_input_filter_preset_allow_percent_symbol` | 通过数据绑定按预设类型套用 InputFilter 组合（覆盖原有）。 |
| bindingETInputFilterPresetSpec | `binding_et_input_filter_preset_spec` | 通过数据绑定按封装参数套用 InputFilter 组合预设（覆盖原有）。 |
| bindingETInputFilters | `binding_et_input_filters` | 通过数据绑定覆盖设置 InputFilter 数组。 |
| bindingETInputFiltersAppend | `binding_et_input_filters_append` | 通过数据绑定在末尾追加 InputFilter。 |
| bindingETInputFiltersClearTs | `binding_et_input_filters_clear_ts` | 通过数据绑定以时间戳清空全部 InputFilter。 |
| bindingETInputFiltersMerge | `binding_et_input_filters_merge` | 通过数据绑定按运行时 Class 合并 InputFilter（同类型原位替换）。 |

* **EditTextKeyboard ->** [EditTextKeyboard.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/EditTextKeyboard.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingETCloseKeyboardTs | `binding_et_close_keyboard_ts` | 通过数据绑定以时间戳关闭软键盘。 |
| bindingETKeyboard | `binding_et_keyboard` | 通过数据绑定打开或关闭软键盘。 |
| bindingETOpenKeyboardDelay | `binding_et_open_keyboard_delay` | 通过数据绑定延时打开软键盘。 |
| bindingETOpenKeyboardTs | `binding_et_open_keyboard_ts` | 通过数据绑定以时间戳打开软键盘。 |

* **ImageView ->** [ImageView.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ImageView.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingIVAdjustViewBounds | `binding_iv_adjust_view_bounds` | 通过数据绑定设置是否保持 drawable 原始宽高比。 |
| bindingIVBackgroundResource | `binding_iv_background_resource` | 通过数据绑定按资源 ID 设置 View 背景（非前景 `src`）。 |
| bindingIVBitmap | `binding_iv_bitmap` | 通过数据绑定按位图设置前景图。 |
| bindingIVDrawable | `binding_iv_drawable` | 通过数据绑定按 Drawable 设置前景图。 |
| bindingIVImageLevel | `binding_iv_image_level` | 通过数据绑定设置前景 Level。 |
| bindingIVImageMatrix | `binding_iv_image_matrix` | 通过数据绑定设置前景 [Matrix]。 |
| bindingIVImageTintList | `binding_iv_image_tint_list` | 通过数据绑定设置前景着色颜色。 |
| bindingIVImageTintMode | `binding_iv_image_tint_mode` | 通过数据绑定设置前景着色模式。 |
| bindingIVMaxHeight | `binding_iv_max_height` | 通过数据绑定设置最大高度。 |
| bindingIVMaxWidth | `binding_iv_max_width` | 通过数据绑定设置最大宽度。 |
| bindingIVRemoveBitmapTs | `binding_iv_remove_bitmap_ts` | 通过数据绑定以时间戳移除前景位图。 |
| bindingIVRemoveDrawableTs | `binding_iv_remove_drawable_ts` | 通过数据绑定以时间戳移除前景 Drawable。 |
| bindingIVResource | `binding_iv_resource` | 通过数据绑定按资源 ID 设置前景图。 |
| bindingIVScaleType | `binding_iv_scale_type` | 通过数据绑定设置缩放类型。 |

* **ImageViewLoadNative ->** [ImageViewLoadNative.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ImageViewLoadNative.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingImageNativeBgColor | `binding_image_native_bg_color` | 通过数据绑定设置当前 ImageView 的背景色。 |
| bindingImageNativeBgResource | `binding_image_native_bg_resource` | 通过数据绑定按资源 ID 设置当前 ImageView 的背景。 |
| bindingImageNativeBitmap | `binding_image_native_bitmap` | 通过数据绑定按位图设置当前 ImageView 的前景图。 |
| bindingImageNativeDrawable | `binding_image_native_drawable` | 通过数据绑定按 Drawable 设置当前 ImageView 的前景图。 |
| bindingImageNativeResource | `binding_image_native_resource` | 通过数据绑定按资源 ID 设置当前 ImageView 的前景图。 |
| bindingImageNativeUri | `binding_image_native_uri` | 通过数据绑定按 Uri 设置当前 ImageView 的前景图。 |

* **RecyclerView ->** [RecyclerView.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/RecyclerView.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingRVAdapter | `binding_rv_adapter` | 通过数据绑定设置 Adapter。 |
| bindingRVAddOnScrollListener | `binding_rv_add_on_scroll_listener` | 通过数据绑定添加滚动监听。 |
| bindingRVAttachLinearSnapHelperTs | `binding_rv_attach_linear_snap_helper_ts` | 通过数据绑定附加 LinearSnapHelper。 |
| bindingRVAttachPagerSnapHelperTs | `binding_rv_attach_pager_snap_helper_ts` | 通过数据绑定附加 PagerSnapHelper。 |
| bindingRVClearOnScrollListenersTs | `binding_rv_clear_on_scroll_listeners_ts` | 通过数据绑定清空全部滚动监听。 |
| bindingRVHasFixedSize | `binding_rv_has_fixed_size` | 通过数据绑定设置 RecyclerView 的固定尺寸标记。 |
| bindingRVItemAnimatorRemove | `binding_rv_item_animator_remove` | 通过数据绑定移除 RecyclerView 的 item 动画器。 |
| bindingRVItemAnimatorRemoveTs | `binding_rv_item_animator_remove_ts` | 通过数据绑定以时间戳移除 RecyclerView 的 item 动画器。 |
| bindingRVItemDecorationAdd | `binding_rv_item_decoration_add` | 通过数据绑定添加 ItemDecoration。 |
| bindingRVItemDecorationRemove | `binding_rv_item_decoration_remove` | 通过数据绑定移除指定 ItemDecoration。 |
| bindingRVItemDecorationRemoveAt | `binding_rv_item_decoration_remove_at` | 通过数据绑定按下标移除 ItemDecoration。 |
| bindingRVItemViewCacheSize | `binding_rv_item_view_cache_size` | 通过数据绑定设置 RecyclerView 的 item 视图缓存数量。 |
| bindingRVLayoutManager | `binding_rv_layout_manager` | 通过数据绑定设置 LayoutManager。 |
| bindingRVNestedScrollingEnabled | `binding_rv_nested_scrolling_enabled` | 通过数据绑定设置嵌套滚动开关。 |
| bindingRVNotifyDataSetChangedTs | `binding_rv_notify_data_set_changed_ts` | 通过数据绑定触发 notifyDataSetChanged。 |
| bindingRVNotifyItemInserted | `binding_rv_notify_item_inserted` | 通过数据绑定触发 notifyItemInserted。 |
| bindingRVNotifyItemMoved | `binding_rv_notify_item_moved` | 通过数据绑定触发 notifyItemMoved。 |
| bindingRVNotifyItemRemoved | `binding_rv_notify_item_removed` | 通过数据绑定触发 notifyItemRemoved。 |
| bindingRVOrientation | `binding_rv_orientation` | 通过数据绑定设置列表方向。 |
| bindingRVRemoveAllItemDecorationTs | `binding_rv_remove_all_item_decoration_ts` | 通过数据绑定移除全部 ItemDecoration。 |
| bindingRVRemoveOnScrollListener | `binding_rv_remove_on_scroll_listener` | 通过数据绑定移除滚动监听。 |
| bindingRVSpanCount | `binding_rv_span_count` | 通过数据绑定设置网格或交错网格的 Span 列数。 |

* **TextView ->** [TextView.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/TextView.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingTVAllCaps | `binding_tv_all_caps` | 通过数据绑定设置是否全大写展示。 |
| bindingTVAntiAliasPaintFlagTs | `binding_tv_anti_alias_paint_flag_ts` | 通过数据绑定在时间戳有效时将画笔标志设为抗锯齿。 |
| bindingTVAutoLinkMask | `binding_tv_auto_link_mask` | 通过数据绑定设置自动链接掩码。 |
| bindingTVAutoSizePresetSizes | `binding_tv_auto_size_preset_sizes` | 通过数据绑定按预设字号表启用均匀自动调整。 |
| bindingTVAutoSizeTextTypeWithDefaults | `binding_tv_auto_size_text_type_with_defaults` | 通过数据绑定设置自动调整字号类型（默认配置）。 |
| bindingTVAutoSizeUniformConfig | `binding_tv_auto_size_uniform_config` | 通过数据绑定设置均匀自动调整字号参数。 |
| bindingTVBold | `binding_tv_bold` | 通过数据绑定设置是否粗体（默认字体样式）。 |
| bindingTVClearPaintFlagsTs | `binding_tv_clear_paint_flags_ts` | 通过数据绑定在时间戳有效时清空画笔标志。 |
| bindingTVCompoundBottom | `binding_tv_compound_bottom` | 通过数据绑定仅设置底部 compound drawable。 |
| bindingTVCompoundBottomRes | `binding_tv_compound_bottom_res` | 通过数据绑定按资源 ID 仅设置底部 compound drawable（固有尺寸）。 |
| bindingTVCompoundDrawablePadding | `binding_tv_compound_drawable_padding` | 通过数据绑定设置四周 drawable 与文字间距。 |
| bindingTVCompoundDrawables | `binding_tv_compound_drawables` | 通过数据绑定设置四周 compound drawable（须已 bounds 或接受系统行为）。 |
| bindingTVCompoundDrawablesIntrinsic | `binding_tv_compound_drawables_intrinsic` | 通过数据绑定按固有尺寸设置四周 compound drawable。 |
| bindingTVCompoundDrawablesIntrinsicRes | `binding_tv_compound_drawables_intrinsic_res` | 通过数据绑定按资源 ID 设置四周 compound drawable（固有尺寸）。 |
| bindingTVCompoundLeft | `binding_tv_compound_left` | 通过数据绑定仅设置左侧 compound drawable。 |
| bindingTVCompoundLeftRes | `binding_tv_compound_left_res` | 通过数据绑定按资源 ID 仅设置左侧 compound drawable（固有尺寸）。 |
| bindingTVCompoundRight | `binding_tv_compound_right` | 通过数据绑定仅设置右侧 compound drawable。 |
| bindingTVCompoundRightRes | `binding_tv_compound_right_res` | 通过数据绑定按资源 ID 仅设置右侧 compound drawable（固有尺寸）。 |
| bindingTVCompoundTop | `binding_tv_compound_top` | 通过数据绑定仅设置顶部 compound drawable。 |
| bindingTVCompoundTopRes | `binding_tv_compound_top_res` | 通过数据绑定按资源 ID 仅设置顶部 compound drawable（固有尺寸）。 |
| bindingTVEllipsize | `binding_tv_ellipsize` | 通过数据绑定设置省略号位置。 |
| bindingTVEms | `binding_tv_ems` | 通过数据绑定设置固定 ems 宽度。 |
| bindingTVGravity | `binding_tv_gravity` | 通过数据绑定设置文本对齐与重力。 |
| bindingTVHint | `binding_tv_hint` | 通过数据绑定设置提示文案。 |
| bindingTVHintColor | `binding_tv_hint_color` | 通过数据绑定按整型色值设置提示文字颜色。 |
| bindingTVHintColorState | `binding_tv_hint_color_state` | 通过数据绑定按状态列表设置提示文字颜色。 |
| bindingTVHtml | `binding_tv_html` | 通过数据绑定按 Html 解析结果设置正文。 |
| bindingTVImeOptions | `binding_tv_ime_options` | 通过数据绑定设置输入法选项。 |
| bindingTVIncludeFontPadding | `binding_tv_include_font_padding` | 通过数据绑定设置是否包含字体额外 padding。 |
| bindingTVInputType | `binding_tv_input_type` | 通过数据绑定设置 [android.text.InputType]。 |
| bindingTVLetterSpacing | `binding_tv_letter_spacing` | 通过数据绑定设置字间距。 |
| bindingTVLineSpacingExtra | `binding_tv_line_spacing_extra` | 通过数据绑定仅设置行间距额外值（倍数固定为 1）。 |
| bindingTVLineSpacingExtraMultiplier | `binding_tv_line_spacing_extra_multiplier` | 通过数据绑定设置行间距与倍数。 |
| bindingTVLines | `binding_tv_lines` | 通过数据绑定设置固定行数。 |
| bindingTVMaxEms | `binding_tv_max_ems` | 通过数据绑定设置最大 ems 宽度。 |
| bindingTVMaxLength | `binding_tv_max_length` | 通过数据绑定设置最大长度过滤。 |
| bindingTVMaxLengthAndText | `binding_tv_max_length_and_text` | 通过数据绑定同时设置最大长度与正文。 |
| bindingTVMaxLines | `binding_tv_max_lines` | 通过数据绑定设置最大行数。 |
| bindingTVMinEms | `binding_tv_min_ems` | 通过数据绑定设置最小 ems 宽度。 |
| bindingTVMinLines | `binding_tv_min_lines` | 通过数据绑定设置最小行数。 |
| bindingTVPaintFlags | `binding_tv_paint_flags` | 通过数据绑定设置画笔标志位。 |
| bindingTVPasswordVisible | `binding_tv_password_visible` | 通过数据绑定切换密码明文与密文显示（内置两种 [TransformationMethod]）。 |
| bindingTVStrikeThru | `binding_tv_strike_thru`<br>`binding_tv_strike_thru_anti_alias` | 通过数据绑定设置或移除删除线相关标志。 |
| bindingTVText | `binding_tv_text` | 通过数据绑定设置显示文本。 |
| bindingTVTextColor | `binding_tv_text_color` | 通过数据绑定按整型色值设置正文颜色。 |
| bindingTVTextColorState | `binding_tv_text_color_state` | 通过数据绑定按状态列表设置正文颜色。 |
| bindingTVTextScaleX | `binding_tv_text_scale_x` | 通过数据绑定设置横向字宽缩放。 |
| bindingTVTextSizeDp | `binding_tv_text_size_dp` | 通过数据绑定以 dp 为单位设置字号。 |
| bindingTVTextSizeIn | `binding_tv_text_size_in` | 通过数据绑定以英寸为单位设置字号。 |
| bindingTVTextSizePx | `binding_tv_text_size_px` | 通过数据绑定以 px 为单位设置字号。 |
| bindingTVTextSizeSp | `binding_tv_text_size_sp` | 通过数据绑定以 sp 为单位设置字号。 |
| bindingTVTextSizeUnitSize | `binding_tv_text_size_unit_size` | 通过数据绑定按单位与数值设置字号。 |
| bindingTVTransformationMethod | `binding_tv_transformation_method` | 通过数据绑定设置转换方法实例。 |
| bindingTVTypeface | `binding_tv_typeface`<br>`binding_tv_typeface_style` | 通过数据绑定设置 [Typeface]。 |
| bindingTVTypefaceBold | `binding_tv_typeface_bold` | 通过数据绑定在指定字体上切换粗体。 |
| bindingTVUnderline | `binding_tv_underline`<br>`binding_tv_underline_anti_alias` | 通过数据绑定设置或移除下划线相关标志。 |

* **TextViewSpan ->** [TextViewSpan.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/TextViewSpan.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingTVSpan | `binding_tv_span` | 通过数据绑定设置 [SpanUtils] 构建得到的富文本。 |
| bindingTVSpanBuilder | `binding_tv_span_builder` | 通过数据绑定设置 [SpannableStringBuilder]。 |
| bindingTVSpanChar | `binding_tv_span_char` | 通过数据绑定设置或清空富文本 [CharSequence]。 |
| bindingTVSpanLinkMovement | `binding_tv_span_link_movement` | 通过数据绑定为链接类 span 启用或清除 [LinkMovementMethod]。 |
| bindingTVSpanSegments | `binding_tv_span_segments` | 通过数据绑定按 [TvSpanSegment] 列表构建富文本。 |
| bindingTVSpanUtils | `binding_tv_span_utils` | 通过数据绑定执行 [SpanUtils.create] 并写入当前 [TextView]。 |
| bindingTVSpanUtilsRun | `binding_tv_span_utils_run` | 通过数据绑定应用 [SpanUtils] 或清空正文。 |
| bindingTVSpanUtilsTs | `binding_tv_span_utils`<br>`binding_tv_span_utils_ts` | 通过数据绑定在时间戳有效时再次执行 [SpanUtils.create]。 |

* **View ->** [View.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/View.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingViewAlpha | `binding_view_alpha` | 通过数据绑定设置透明度。 |
| bindingViewAnimation | `binding_view_animation` | 通过数据绑定设置或清除 View 动画。 |
| bindingViewBackground | `binding_view_background` | 通过数据绑定设置背景 Drawable。 |
| bindingViewBackgroundColor | `binding_view_background_color` | 通过数据绑定设置背景色。 |
| bindingViewBackgroundResource | `binding_view_background_resource` | 通过数据绑定设置背景资源 id。 |
| bindingViewBackgroundTintList | `binding_view_background_tint_list` | 通过数据绑定设置背景着色。 |
| bindingViewBackgroundTintMode | `binding_view_background_tint_mode` | 通过数据绑定设置背景着色模式。 |
| bindingViewBarMax | `binding_view_bar_max` | 通过数据绑定设置 ProgressBar 最大值。 |
| bindingViewBarProgress | `binding_view_bar_progress` | 通过数据绑定设置 ProgressBar 当前进度。 |
| bindingViewBarProgressState | `binding_view_bar_progress_state` | 通过数据绑定以单属性同时设置 ProgressBar 的最大值与当前进度。 |
| bindingViewBarValue | `binding_view_bar_progress_value`<br>`binding_view_bar_max_value` | 通过数据绑定同时设置 ProgressBar 的最大值与当前进度。 |
| bindingViewCancelAnimation | `binding_view_cancel_animation` | 通过数据绑定取消 View 上正在播放的动画。 |
| bindingViewCancelAnimationTs | `binding_view_cancel_animation_ts` | 通过数据绑定以时间戳触发 cancelAnimation。 |
| bindingViewClearAnimation | `binding_view_clear_animation` | 通过数据绑定显式清空 View 动画。 |
| bindingViewClearAnimationTs | `binding_view_clear_animation_ts` | 通过数据绑定以时间戳触发 clearAnimation。 |
| bindingViewClearFocus | `binding_view_clear_focus` | 通过数据绑定清除该 View 的焦点。 |
| bindingViewClearFocusTs | `binding_view_clear_focus_ts` | 通过数据绑定以时间戳触发 clearFocus。 |
| bindingViewClipChildren | `binding_view_clip_children` | 通过数据绑定设置 ViewGroup 是否裁剪子 View 绘制区域。 |
| bindingViewClipToOutline | `binding_view_clip_to_outline` | 通过数据绑定设置是否按 Outline 裁剪。 |
| bindingViewColorFilter | `binding_view_color_filter` | 通过数据绑定按颜色过滤背景 Drawable 并设回背景。 |
| bindingViewColorFilterDrawable | `binding_view_color_filter_drawable`<br>`binding_view_color_filter_drawable_color` | 通过数据绑定对指定 Drawable 着色并设为背景。 |
| bindingViewColorFilterDrawableObject | `binding_view_color_filter_drawable_object`<br>`binding_view_color_filter_drawable_filter` | 通过数据绑定对 Drawable 应用 ColorFilter 并设为背景。 |
| bindingViewColorFilterObject | `binding_view_color_filter_object` | 通过数据绑定使用 ColorFilter 处理当前背景。 |
| bindingViewDescendantFocusability | `binding_view_descendant_focusability` | 通过数据绑定设置子 View 焦点传递策略。 |
| bindingViewForeground | `binding_view_foreground` | 通过数据绑定设置前景 Drawable（API 23+）。 |
| bindingViewForegroundGravity | `binding_view_foreground_gravity` | 通过数据绑定设置前景重心（API 23+）。 |
| bindingViewForegroundTintList | `binding_view_foreground_tint_list` | 通过数据绑定设置前景着色（API 23+）。 |
| bindingViewForegroundTintMode | `binding_view_foreground_tint_mode` | 通过数据绑定设置前景着色模式（API 23+）。 |
| bindingViewHeight | `binding_view_height_px`<br>`binding_view_height_null_new_lp` | 通过数据绑定设置高度（像素），可选 LayoutParams 为 null 时是否新建。 |
| bindingViewHorizontalScrollBarEnabled | `binding_view_horizontal_scroll_bar_enabled` | 通过数据绑定设置是否绘制横向滚动条。 |
| bindingViewId | `binding_view_id` | 通过数据绑定设置 View id。 |
| bindingViewLayerType | `binding_view_layer_type`<br>`binding_view_layer_paint` | 通过数据绑定设置硬件分层类型与可选 Paint。 |
| bindingViewLayoutGravity | `binding_view_layout_gravity`<br>`binding_view_layout_gravity_reflection` | 通过数据绑定设置子项在父容器中的 layout gravity。 |
| bindingViewLayoutParams | `binding_view_layout_params` | 通过数据绑定替换 LayoutParams。 |
| bindingViewMinimumHeight | `binding_view_minimum_height` | 通过数据绑定设置最小高度。 |
| bindingViewMinimumWidth | `binding_view_minimum_width` | 通过数据绑定设置最小宽度。 |
| bindingViewNextFocusDownId | `binding_view_next_focus_down_id` | 通过数据绑定设置向下焦点导航目标 id。 |
| bindingViewNextFocusForwardId | `binding_view_next_focus_forward_id` | 通过数据绑定设置下一个前向焦点 id。 |
| bindingViewNextFocusLeftId | `binding_view_next_focus_left_id` | 通过数据绑定设置向左焦点导航目标 id。 |
| bindingViewNextFocusRightId | `binding_view_next_focus_right_id` | 通过数据绑定设置向右焦点导航目标 id。 |
| bindingViewNextFocusUpId | `binding_view_next_focus_up_id` | 通过数据绑定设置向上焦点导航目标 id。 |
| bindingViewOutlineProvider | `binding_view_outline_provider` | 通过数据绑定设置 OutlineProvider。 |
| bindingViewOutlineProviderClip | `binding_view_outline_provider_clip` | 通过数据绑定设置 OutlineProvider 并开启裁剪。 |
| bindingViewOverScrollMode | `binding_view_over_scroll_mode` | 通过数据绑定设置边缘过度滚动模式。 |
| bindingViewPivotX | `binding_view_pivot_x` | 通过数据绑定设置变换锚点 X。 |
| bindingViewPivotY | `binding_view_pivot_y` | 通过数据绑定设置变换锚点 Y。 |
| bindingViewProgressDrawable | `binding_view_progress_drawable` | 通过数据绑定设置 ProgressBar 的进度 Drawable（若不是 ProgressBar 则工具类内部忽略）。 |
| bindingViewRelativeAddRule | `binding_view_relative_add_rule_verb`<br>`binding_view_relative_add_rule_subject` | 通过数据绑定为 RelativeLayout 子 View 添加布局规则。 |
| bindingViewRelativeRemoveRule | `binding_view_relative_remove_rule_verb` | 通过数据绑定为 RelativeLayout 子 View 移除布局规则。 |
| bindingViewRemoveAllBackground | `binding_view_remove_all_background` | 通过数据绑定移除背景并清除 ImageView 的 src（与仅 removeBackground 区分）。 |
| bindingViewRemoveAllBackgroundTs | `binding_view_remove_all_background_ts` | 通过数据绑定以时间戳触发 removeAllBackground。 |
| bindingViewRemoveAllViews | `binding_view_remove_all_views` | 通过数据绑定移除 ViewGroup 的全部子 View。 |
| bindingViewRemoveAllViewsTs | `binding_view_remove_all_views_ts` | 通过数据绑定以时间戳触发移除 ViewGroup 的全部子 View。 |
| bindingViewRemoveBackground | `binding_view_remove_background` | 通过数据绑定显式移除背景 Drawable。 |
| bindingViewRemoveBackgroundTs | `binding_view_remove_background_ts` | 通过数据绑定以时间戳触发 removeBackground。 |
| bindingViewRemoveForeground | `binding_view_remove_foreground` | 通过数据绑定显式移除前景 Drawable（API 23+）。 |
| bindingViewRemoveForegroundTs | `binding_view_remove_foreground_ts` | 通过数据绑定以时间戳触发 removeForeground（API 23 以下忽略）。 |
| bindingViewRemoveSelfFromParent | `binding_view_remove_self_from_parent` | 通过数据绑定将自身从父容器中移除。 |
| bindingViewRemoveSelfFromParentTs | `binding_view_remove_self_from_parent_ts` | 通过数据绑定以时间戳触发将自身从父容器中移除。 |
| bindingViewRequestFocus | `binding_view_request_focus` | 通过数据绑定请求该 View 获取焦点。 |
| bindingViewRequestFocusTs | `binding_view_request_focus_ts` | 通过数据绑定以时间戳触发 requestFocus。 |
| bindingViewRequestLayout | `binding_view_request_layout` | 通过数据绑定请求对该 View 重新布局。 |
| bindingViewRequestLayoutParent | `binding_view_request_layout_parent`<br>`binding_view_request_layout_parent_all` | 通过数据绑定沿父链请求 layout。 |
| bindingViewRequestLayoutParentTs | `binding_view_request_layout_parent_ts`<br>`binding_view_request_layout_parent_ts_all` | 通过数据绑定以时间戳触发沿父链 requestLayout。 |
| bindingViewRequestLayoutTs | `binding_view_request_layout_ts` | 通过数据绑定以时间戳触发 requestLayout。 |
| bindingViewRotation | `binding_view_rotation` | 通过数据绑定设置 Z 轴旋转角。 |
| bindingViewRotationX | `binding_view_rotation_x` | 通过数据绑定设置绕 X 轴旋转角。 |
| bindingViewRotationY | `binding_view_rotation_y` | 通过数据绑定设置绕 Y 轴旋转角。 |
| bindingViewScaleX | `binding_view_scale_x` | 通过数据绑定设置水平缩放。 |
| bindingViewScaleY | `binding_view_scale_y` | 通过数据绑定设置垂直缩放。 |
| bindingViewScrollContainer | `binding_view_scroll_container` | 通过数据绑定设置是否为滚动容器。 |
| bindingViewStartAnimation | `binding_view_start_animation` | 通过数据绑定启动指定 Animation（内部会 startAnimation）。 |
| bindingViewStartAttachedAnimation | `binding_view_start_attached_animation` | 通过数据绑定对已设置的 Animation 调用 start（不替换 animation 对象）。 |
| bindingViewStartAttachedAnimationTs | `binding_view_start_attached_animation_ts` | 通过数据绑定以时间戳触发已附着 Animation 的 start。 |
| bindingViewTag | `binding_view_tag` | 通过数据绑定设置 tag。 |
| bindingViewTextAlignment | `binding_view_text_alignment` | 通过数据绑定设置文本对齐（View 层语义）。 |
| bindingViewTextDirection | `binding_view_text_direction` | 通过数据绑定设置文本方向。 |
| bindingViewTranslationX | `binding_view_translation_x` | 通过数据绑定设置 translationX。 |
| bindingViewTranslationY | `binding_view_translation_y` | 通过数据绑定设置 translationY。 |
| bindingViewVerticalScrollBarEnabled | `binding_view_vertical_scroll_bar_enabled` | 通过数据绑定设置是否绘制纵向滚动条。 |
| bindingViewVisibilityBool | `binding_view_visibility_bool` | 通过数据绑定切换 VISIBLE / GONE（与 ViewUtils.setVisibility(boolean, View) 一致）。 |
| bindingViewVisibilityInBool | `binding_view_visibility_in_bool` | 通过数据绑定切换 VISIBLE / INVISIBLE（与 ViewUtils.setVisibilityIN 一致）。 |
| bindingViewVisibilityInt | `binding_view_visibility_int` | 通过数据绑定设置可见性整型常量。 |
| bindingViewWeight | `binding_view_weight` | 通过数据绑定设置 LinearLayout 子项 weight。 |
| bindingViewWidth | `binding_view_width_px`<br>`binding_view_width_null_new_lp` | 通过数据绑定设置宽度（像素），可选 LayoutParams 为 null 时是否新建。 |
| bindingViewWidthHeight | `binding_view_width_height_w`<br>`binding_view_width_height_h`<br>`binding_view_width_height_null_new_lp` | 通过数据绑定设置宽高（像素），可选是否在 LayoutParams 为 null 时创建新实例。 |
| bindingViewWidthHeightDims | `binding_view_width_height_dims` | 通过数据绑定以单属性设置宽高（像素）及 LayoutParams 空时是否新建。 |
| bindingViewX | `binding_view_x` | 通过数据绑定设置左侧绘制坐标 X。 |
| bindingViewY | `binding_view_y` | 通过数据绑定设置顶部绘制坐标 Y。 |

* **ViewAnimation ->** [ViewAnimation.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewAnimation.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingViewAnim | `binding_view_anim` | 通过数据绑定启动或结束 [Animation]。 |
| bindingViewAnimAlphaFade | `binding_view_anim_alpha_fade` | 通过数据绑定播放任意起止透明度的 Alpha 渐变（不改 visibility）。 |
| bindingViewAnimAmplifyScale | `binding_view_anim_amplify_scale` | 通过数据绑定播放中心放大至 1 的缩放动画（可指定时长与监听器）。 |
| bindingViewAnimAmplifyScaleTs | `binding_view_anim_amplify_scale_ts`<br>`binding_view_anim_amplify_scale_ts_listener` | 通过数据绑定以默认时长播放中心放大至 1 的缩放动画。 |
| bindingViewAnimGoneAlpha | `binding_view_anim_gone_alpha` | 通过数据绑定将视图渐隐为 GONE（可指定时长、是否禁止点击与监听器）。 |
| bindingViewAnimGoneAlphaTs | `binding_view_anim_gone_alpha_ts`<br>`binding_view_anim_gone_alpha_ts_listener` | 通过数据绑定以默认时长将视图渐隐为 GONE。 |
| bindingViewAnimHiddenAlpha | `binding_view_anim_hidden_alpha` | 通过数据绑定播放 1.0→0.0 透明度渐变（可指定时长与监听器，不改 visibility）。 |
| bindingViewAnimHiddenAlphaTs | `binding_view_anim_hidden_alpha_ts`<br>`binding_view_anim_hidden_alpha_ts_listener` | 通过数据绑定播放 1.0→0.0 透明度渐变（不改 visibility）。 |
| bindingViewAnimInvisibleAlpha | `binding_view_anim_invisible_alpha` | 通过数据绑定将视图渐隐为 INVISIBLE（可指定时长、是否禁止点击与监听器）。 |
| bindingViewAnimInvisibleAlphaTs | `binding_view_anim_invisible_alpha_ts`<br>`binding_view_anim_invisible_alpha_ts_listener` | 通过数据绑定以默认时长将视图渐隐为 INVISIBLE。 |
| bindingViewAnimLessenScale | `binding_view_anim_lessen_scale` | 通过数据绑定播放中心缩小至 0 的缩放动画（可指定时长与监听器）。 |
| bindingViewAnimLessenScaleTs | `binding_view_anim_lessen_scale_ts`<br>`binding_view_anim_lessen_scale_ts_listener` | 通过数据绑定以默认时长播放中心缩小至 0 的缩放动画。 |
| bindingViewAnimRotate | `binding_view_anim_rotate` | 通过数据绑定按自定义角度与 pivot 旋转。 |
| bindingViewAnimRotateCenter | `binding_view_anim_rotate_center` | 通过数据绑定绕视图中心旋转（可指定时长与监听器）。 |
| bindingViewAnimRotateCenterTs | `binding_view_anim_rotate_center_ts`<br>`binding_view_anim_rotate_center_ts_listener` | 通过数据绑定以默认时长绕视图中心旋转一周（0°→359°）。 |
| bindingViewAnimScale | `binding_view_anim_scale` | 通过数据绑定以左上角为原点播放缩放动画。 |
| bindingViewAnimScaleCenter | `binding_view_anim_scale_center` | 通过数据绑定以视图中心为原点播放缩放动画。 |
| bindingViewAnimShake | `binding_view_anim_shake` | 通过数据绑定触发自定义 X 向摇晃。 |
| bindingViewAnimShakePreset | `binding_view_anim_shake_preset` | 通过数据绑定触发预设幅度（X 方向 ±10）的摇晃。 |
| bindingViewAnimShakeTs | `binding_view_anim_shake_ts`<br>`binding_view_anim_shake_ts_listener` | 通过数据绑定以工具类默认参数触发摇晃（幅度 10、周期 7、700ms）。 |
| bindingViewAnimShowAlpha | `binding_view_anim_show_alpha` | 通过数据绑定播放 0.0→1.0 透明度渐变（可指定时长与监听器，不改 visibility）。 |
| bindingViewAnimShowAlphaTs | `binding_view_anim_show_alpha_ts`<br>`binding_view_anim_show_alpha_ts_listener` | 通过数据绑定播放 0.0→1.0 透明度渐变（不改 visibility）。 |
| bindingViewAnimTranslate | `binding_view_anim_translate` | 通过数据绑定触发平移动画。 |
| bindingViewAnimVisibleAlpha | `binding_view_anim_visible_alpha` | 通过数据绑定将视图渐显为 VISIBLE（可指定时长、是否禁止点击与监听器）。 |
| bindingViewAnimVisibleAlphaTs | `binding_view_anim_visible_alpha_ts`<br>`binding_view_anim_visible_alpha_ts_listener` | 通过数据绑定以默认时长将视图渐显为 VISIBLE。 |

* **ViewListener ->** [ViewListener.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewListener.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingClick | `binding_click`<br>`binding_click_interval` | 通过数据绑定设置带防抖的点击回调 |
| bindingClickAssist | `binding_click`<br>`binding_click_assist` | 通过数据绑定设置带防抖辅助类的点击回调 |
| bindingClickCountListener | `binding_click_count_listener` | 通过数据绑定设置自定义计数点击监听 |
| bindingClickDebounce | `binding_click_debounce`<br>`binding_click_debounce_check_view_id` | 通过数据绑定设置防抖单击监听 |
| bindingClickDebounceListener | `binding_click_debounce_listener` | 通过数据绑定设置自定义防抖单击监听 |
| bindingClickEmpty | `binding_click_empty` | 通过数据绑定设置空点击监听以消费点击事件 |
| bindingClickEmptyTs | `binding_click_empty_ts` | 通过数据绑定以时间戳设置空点击监听 |
| bindingClickMultiListener | `binding_click_multi_listener` | 通过数据绑定设置自定义多次点击监听 |
| bindingLongClick | `binding_long_click` | 通过数据绑定设置长按监听 |
| bindingTouch | `binding_touch` | 通过数据绑定设置触摸监听 |
| bindingTouchAreaExpand | `binding_touch_area_expand` | 通过数据绑定按四边分别扩大可点击区域 |
| bindingTouchAreaExpandPx | `binding_touch_area_expand_px` | 通过数据绑定为四边统一扩大可点击区域 |

* **ViewMargin ->** [ViewMargin.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewMargin.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingMargin | `binding_margin` | 通过数据绑定为四边设置相同外边距 |
| bindingMarginAttr | `binding_marginAttr` | 通过数据绑定按四边距对象设置外边距 |
| bindingMarginBottom | `binding_marginBottom`<br>`binding_marginReset` | 通过数据绑定设置底部外边距 |
| bindingMarginLeft | `binding_marginLeft`<br>`binding_marginReset` | 通过数据绑定设置左侧外边距 |
| bindingMarginRight | `binding_marginRight`<br>`binding_marginReset` | 通过数据绑定设置右侧外边距 |
| bindingMarginTop | `binding_marginTop`<br>`binding_marginReset` | 通过数据绑定设置顶部外边距 |

* **ViewPadding ->** [ViewPadding.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewPadding.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingPadding | `binding_padding` | 通过数据绑定为四边设置相同内边距 |
| bindingPaddingAttr | `binding_paddingAttr` | 通过数据绑定按四边距对象设置内边距 |
| bindingPaddingBottom | `binding_paddingBottom`<br>`binding_paddingReset` | 通过数据绑定设置底部内边距 |
| bindingPaddingLeft | `binding_paddingLeft`<br>`binding_paddingReset` | 通过数据绑定设置左侧内边距 |
| bindingPaddingRight | `binding_paddingRight`<br>`binding_paddingReset` | 通过数据绑定设置右侧内边距 |
| bindingPaddingTop | `binding_paddingTop`<br>`binding_paddingReset` | 通过数据绑定设置顶部内边距 |

* **ViewScroll ->** [ViewScroll.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScroll.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingScrollEventInstantBottom | `binding_scroll_event_instant_bottom` | 数据绑定触发瞬时滚动到内容或列表底部。 |
| bindingScrollEventInstantTop | `binding_scroll_event_instant_top` | 数据绑定触发瞬时滚动到内容或列表顶部。 |
| bindingScrollEventSmoothBottom | `binding_scroll_event_smooth_bottom` | 数据绑定触发平滑滚动到内容或列表底部。 |
| bindingScrollEventSmoothTop | `binding_scroll_event_smooth_top` | 数据绑定触发平滑滚动到内容或列表顶部。 |
| bindingScrollFullDirection | `binding_scroll_full_direction` | 数据绑定按焦点方向整段滚动。 |
| bindingScrollInstantAbsXY | `binding_scroll_instant_abs_xy` | 数据绑定执行无动画绝对滚动。 |
| bindingScrollInstantAdapterIndex | `binding_scroll_instant_adapter_index` | 数据绑定无动画滚动到指定 adapter 或列表项索引。 |
| bindingScrollInstantRelDxDy | `binding_scroll_instant_rel_xy` | 数据绑定执行无动画相对滚动。 |
| bindingScrollRv | `binding_scroll_rv` | 数据绑定触发 RecyclerView 平滑滚动至指定项，或停止当前滚动。 |
| bindingScrollRvLinearIndexOffset | `binding_scroll_rv_linear_index`<br>`binding_scroll_rv_linear_offset` | 数据绑定将指定项滚入可视区并附加像素偏移。 |
| bindingScrollRvSnapEndAuto | `binding_scroll_rv_snap_end_auto` | 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。 |
| bindingScrollRvSnapEndIndex | `binding_scroll_rv_snap_end_index` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。 |
| bindingScrollRvSnapStartAuto | `binding_scroll_rv_snap_start_auto` | 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。 |
| bindingScrollRvSnapStartIndex | `binding_scroll_rv_snap_start_index` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。 |
| bindingScrollRvStop | `binding_scroll_rv_stop` | 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。 |
| bindingScrollSetScrollX | `binding_scroll_set_scroll_x` | 数据绑定设置内容水平滚动位置。 |
| bindingScrollSetScrollY | `binding_scroll_set_scroll_y` | 数据绑定设置内容垂直滚动位置。 |
| bindingScrollSmoothAbsXY | `binding_scroll_smooth_abs_xy` | 数据绑定执行平滑绝对滚动。 |
| bindingScrollSmoothAdapterIndex | `binding_scroll_smooth_adapter_index` | 数据绑定平滑滑动到指定 adapter 或列表项索引。 |
| bindingScrollSmoothRelDxDy | `binding_scroll_smooth_rel_xy` | 数据绑定执行平滑相对滚动。 |

* **ViewScrollDelayAssist ->** [ViewScrollDelayAssist.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScrollDelayAssist.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingScrollDelayAssistEventInstantBottom | `binding_scroll_delay_assist_event_instant_bottom`<br>`binding_scroll_delay_assist_event_instant_bottom_assist` | 数据绑定触发瞬时滚动到内容或列表底部。 |
| bindingScrollDelayAssistEventInstantTop | `binding_scroll_delay_assist_event_instant_top`<br>`binding_scroll_delay_assist_event_instant_top_assist` | 数据绑定触发瞬时滚动到内容或列表顶部。 |
| bindingScrollDelayAssistEventSmoothBottom | `binding_scroll_delay_assist_event_smooth_bottom`<br>`binding_scroll_delay_assist_event_smooth_bottom_assist` | 数据绑定触发平滑滚动到内容或列表底部。 |
| bindingScrollDelayAssistEventSmoothTop | `binding_scroll_delay_assist_event_smooth_top`<br>`binding_scroll_delay_assist_event_smooth_top_assist` | 数据绑定触发平滑滚动到内容或列表顶部。 |
| bindingScrollDelayAssistFullDirection | `binding_scroll_delay_assist_full_direction`<br>`binding_scroll_delay_assist_full_direction_assist` | 数据绑定按焦点方向整段滚动。 |
| bindingScrollDelayAssistInstantAbsXY | `binding_scroll_delay_assist_instant_abs_xy`<br>`binding_scroll_delay_assist_instant_abs_assist` | 数据绑定执行无动画绝对滚动。 |
| bindingScrollDelayAssistInstantAdapterIndex | `binding_scroll_delay_assist_instant_adapter_index`<br>`binding_scroll_delay_assist_instant_adapter_index_assist` | 数据绑定无动画滚动到指定 adapter 或列表项索引。 |
| bindingScrollDelayAssistInstantRelDxDy | `binding_scroll_delay_assist_instant_rel_xy`<br>`binding_scroll_delay_assist_instant_rel_assist` | 数据绑定执行无动画相对滚动。 |
| bindingScrollDelayAssistRvLinearIndexOffset | `binding_scroll_delay_assist_rv_linear_index`<br>`binding_scroll_delay_assist_rv_linear_offset`<br>`binding_scroll_delay_assist_rv_linear_assist` | 数据绑定将指定项滚入可视区并附加像素偏移。 |
| bindingScrollDelayAssistRvSnapEndAuto | `binding_scroll_delay_assist_rv_snap_end_auto`<br>`binding_scroll_delay_assist_rv_snap_end_auto_assist` | 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。 |
| bindingScrollDelayAssistRvSnapEndIndex | `binding_scroll_delay_assist_rv_snap_end_index`<br>`binding_scroll_delay_assist_rv_snap_end_index_assist` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。 |
| bindingScrollDelayAssistRvSnapStartAuto | `binding_scroll_delay_assist_rv_snap_start_auto`<br>`binding_scroll_delay_assist_rv_snap_start_auto_assist` | 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。 |
| bindingScrollDelayAssistRvSnapStartIndex | `binding_scroll_delay_assist_rv_snap_start_index`<br>`binding_scroll_delay_assist_rv_snap_start_index_assist` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。 |
| bindingScrollDelayAssistRvStop | `binding_scroll_delay_assist_rv_stop`<br>`binding_scroll_delay_assist_rv_stop_assist` | 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。 |
| bindingScrollDelayAssistSetScrollX | `binding_scroll_delay_assist_set_scroll_x`<br>`binding_scroll_delay_assist_set_scroll_x_assist` | 数据绑定设置内容水平滚动位置。 |
| bindingScrollDelayAssistSetScrollY | `binding_scroll_delay_assist_set_scroll_y`<br>`binding_scroll_delay_assist_set_scroll_y_assist` | 数据绑定设置内容垂直滚动位置。 |
| bindingScrollDelayAssistSmoothAbsXY | `binding_scroll_delay_assist_smooth_abs_xy`<br>`binding_scroll_delay_assist_smooth_abs_assist` | 数据绑定执行平滑绝对滚动。 |
| bindingScrollDelayAssistSmoothAdapterIndex | `binding_scroll_delay_assist_smooth_adapter_index`<br>`binding_scroll_delay_assist_smooth_adapter_index_assist` | 数据绑定平滑滑动到指定 adapter 或列表项索引。 |
| bindingScrollDelayAssistSmoothRelDxDy | `binding_scroll_delay_assist_smooth_rel_xy`<br>`binding_scroll_delay_assist_smooth_rel_assist` | 数据绑定执行平滑相对滚动。 |

* **ViewScrollDelayed ->** [ViewScrollDelayed.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewScrollDelayed.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingScrollDelayedEventInstantBottom | `binding_scroll_delayed_event_instant_bottom`<br>`binding_scroll_delayed_event_instant_bottom_delay_ms` | 数据绑定触发瞬时滚动到内容或列表底部。 |
| bindingScrollDelayedEventInstantTop | `binding_scroll_delayed_event_instant_top`<br>`binding_scroll_delayed_event_instant_top_delay_ms` | 数据绑定触发瞬时滚动到内容或列表顶部。 |
| bindingScrollDelayedEventSmoothBottom | `binding_scroll_delayed_event_smooth_bottom`<br>`binding_scroll_delayed_event_smooth_bottom_delay_ms` | 数据绑定触发平滑滚动到内容或列表底部。 |
| bindingScrollDelayedEventSmoothTop | `binding_scroll_delayed_event_smooth_top`<br>`binding_scroll_delayed_event_smooth_top_delay_ms` | 数据绑定触发平滑滚动到内容或列表顶部。 |
| bindingScrollDelayedFullDirection | `binding_scroll_delayed_full_direction`<br>`binding_scroll_delayed_full_direction_delay_ms` | 数据绑定按焦点方向整段滚动。 |
| bindingScrollDelayedInstantAbsXY | `binding_scroll_delayed_instant_abs_xy`<br>`binding_scroll_delayed_instant_abs_delay_ms` | 数据绑定执行无动画绝对滚动。 |
| bindingScrollDelayedInstantAdapterIndex | `binding_scroll_delayed_instant_adapter_index`<br>`binding_scroll_delayed_instant_adapter_index_delay_ms` | 数据绑定无动画滚动到指定 adapter 或列表项索引。 |
| bindingScrollDelayedInstantRelDxDy | `binding_scroll_delayed_instant_rel_xy`<br>`binding_scroll_delayed_instant_rel_delay_ms` | 数据绑定执行无动画相对滚动。 |
| bindingScrollDelayedRvLinearIndexOffset | `binding_scroll_delayed_rv_linear_index`<br>`binding_scroll_delayed_rv_linear_offset`<br>`binding_scroll_delayed_rv_linear_delay_ms` | 数据绑定将指定项滚入可视区并附加像素偏移。 |
| bindingScrollDelayedRvSnapEndAuto | `binding_scroll_delayed_rv_snap_end_auto`<br>`binding_scroll_delayed_rv_snap_end_auto_delay_ms` | 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。 |
| bindingScrollDelayedRvSnapEndIndex | `binding_scroll_delayed_rv_snap_end_index`<br>`binding_scroll_delayed_rv_snap_end_index_delay_ms` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。 |
| bindingScrollDelayedRvSnapStartAuto | `binding_scroll_delayed_rv_snap_start_auto`<br>`binding_scroll_delayed_rv_snap_start_auto_delay_ms` | 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。 |
| bindingScrollDelayedRvSnapStartIndex | `binding_scroll_delayed_rv_snap_start_index`<br>`binding_scroll_delayed_rv_snap_start_index_delay_ms` | 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。 |
| bindingScrollDelayedRvStop | `binding_scroll_delayed_rv_stop`<br>`binding_scroll_delayed_rv_stop_delay_ms` | 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。 |
| bindingScrollDelayedSetScrollX | `binding_scroll_delayed_set_scroll_x`<br>`binding_scroll_delayed_set_scroll_x_delay_ms` | 数据绑定设置内容水平滚动位置。 |
| bindingScrollDelayedSetScrollY | `binding_scroll_delayed_set_scroll_y`<br>`binding_scroll_delayed_set_scroll_y_delay_ms` | 数据绑定设置内容垂直滚动位置。 |
| bindingScrollDelayedSmoothAbsXY | `binding_scroll_delayed_smooth_abs_xy`<br>`binding_scroll_delayed_smooth_abs_delay_ms` | 数据绑定执行平滑绝对滚动。 |
| bindingScrollDelayedSmoothAdapterIndex | `binding_scroll_delayed_smooth_adapter_index`<br>`binding_scroll_delayed_smooth_adapter_index_delay_ms` | 数据绑定平滑滑动到指定 adapter 或列表项索引。 |
| bindingScrollDelayedSmoothRelDxDy | `binding_scroll_delayed_smooth_rel_xy`<br>`binding_scroll_delayed_smooth_rel_delay_ms` | 数据绑定执行平滑相对滚动。 |

* **ViewShape ->** [ViewShape.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewShape.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingViewShape | `binding_view_shape` | 通过数据绑定应用或移除 [ShapeUtils] 背景。 |
| bindingViewShapeAlpha | `binding_view_shape_alpha` | 通过数据绑定设置 Shape 透明度并应用为背景。 |
| bindingViewShapeColor | `binding_view_shape_color` | 通过数据绑定设置纯色填充并应用为新背景 Shape。 |
| bindingViewShapeCornerRadius | `binding_view_shape_corner_radius` | 通过数据绑定创建统一圆角 Shape 并设为背景。 |
| bindingViewShapeCornerRadiusBottom | `binding_view_shape_corner_radius_bottom` | 通过数据绑定设置底部两角圆角。 |
| bindingViewShapeCornerRadiusLeft | `binding_view_shape_corner_radius_left` | 通过数据绑定设置左侧两角圆角。 |
| bindingViewShapeCornerRadiusRight | `binding_view_shape_corner_radius_right` | 通过数据绑定设置右侧两角圆角。 |
| bindingViewShapeCornerRadiusTop | `binding_view_shape_corner_radius_top` | 通过数据绑定设置顶部两角圆角。 |
| bindingViewShapeCorners | `binding_view_shape_corners` | 通过数据绑定设置四角圆角并应用为新背景 Shape。 |
| bindingViewShapeGradient | `binding_view_shape_gradient` | 通过数据绑定按角度渐变创建 Shape 并设为背景。 |
| bindingViewShapeGradientOrient | `binding_view_shape_gradient_orient` | 通过数据绑定按 [GradientDrawable.Orientation] 渐变创建 Shape 并设为背景。 |
| bindingViewShapePadding | `binding_view_shape_padding` | 通过数据绑定设置 Shape drawable 内边距（API 29+）。 |
| bindingViewShapeRadiusColor | `binding_view_shape_radius_color` | 通过数据绑定创建圆角纯色 Shape 并设为背景。 |
| bindingViewShapeSize | `binding_view_shape_size` | 通过数据绑定设置 Shape drawable 固有尺寸。 |
| bindingViewShapeSpec | `binding_view_shape_spec` | 通过数据绑定按 [ViewShapeSpec] 声明式构建 Shape 并设为背景。 |
| bindingViewShapeStroke | `binding_view_shape_stroke` | 通过数据绑定设置描边并应用为新背景 Shape。 |
| bindingViewShapeUtils | `binding_view_shape_utils` | 通过数据绑定将已配置的 [ShapeUtils] 设为背景。 |
| bindingViewShapeUtilsTs | `binding_view_shape_utils`<br>`binding_view_shape_utils_ts` | 通过数据绑定在时间戳有效时再次执行 [ShapeUtils.setDrawable]。 |

* **ViewStateActivated ->** [ViewStateActivated.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateActivated.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingActivated | `binding_activated` | 通过数据绑定设置 Activated 激活状态。 |
| bindingActivatedIfCompareValueEquals | `binding_activated_IfCompareValueEquals`<br>`binding_activated_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制 Activated 状态。 |
| bindingActivatedIfNotEmpty | `binding_activated_IfNotEmpty` | 根据文本是否非空控制 Activated 状态。 |
| bindingActivatedIfNotNull | `binding_activated_IfNotNull` | 根据引用是否非 null 控制 Activated 状态。 |
| bindingActivatedIfStringEquals | `binding_activated_IfStringEquals`<br>`binding_activated_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制 Activated 状态。 |
| bindingActivatedIfStringEqualsIgnoreCase | `binding_activated_IfStringEqualsIgnoreCase`<br>`binding_activated_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制 Activated 状态。 |
| bindingActivatedIfValueEquals | `binding_activated_IfValueEquals`<br>`binding_activated_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制 Activated 状态。 |

* **ViewStateChecked ->** [ViewStateChecked.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateChecked.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingChecked | `binding_checked` | 通过数据绑定设置是否选中 checked。 |
| bindingCheckedIfCompareValueEquals | `binding_checked_IfCompareValueEquals`<br>`binding_checked_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制是否选中 checked。 |
| bindingCheckedIfNotEmpty | `binding_checked_IfNotEmpty` | 根据文本是否非空控制是否选中 checked。 |
| bindingCheckedIfNotNull | `binding_checked_IfNotNull` | 根据引用是否非 null 控制是否选中 checked。 |
| bindingCheckedIfStringEquals | `binding_checked_IfStringEquals`<br>`binding_checked_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制是否选中 checked。 |
| bindingCheckedIfStringEqualsIgnoreCase | `binding_checked_IfStringEqualsIgnoreCase`<br>`binding_checked_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制是否选中 checked。 |
| bindingCheckedIfValueEquals | `binding_checked_IfValueEquals`<br>`binding_checked_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制是否选中 checked。 |

* **ViewStateClickable ->** [ViewStateClickable.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateClickable.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingClickable | `binding_clickable` | 通过数据绑定设置是否可点击。 |
| bindingClickableIfCompareValueEquals | `binding_clickable_IfCompareValueEquals`<br>`binding_clickable_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制是否可点击。 |
| bindingClickableIfNotEmpty | `binding_clickable_IfNotEmpty` | 根据文本是否非空控制是否可点击。 |
| bindingClickableIfNotNull | `binding_clickable_IfNotNull` | 根据引用是否非 null 控制是否可点击。 |
| bindingClickableIfStringEquals | `binding_clickable_IfStringEquals`<br>`binding_clickable_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制是否可点击。 |
| bindingClickableIfStringEqualsIgnoreCase | `binding_clickable_IfStringEqualsIgnoreCase`<br>`binding_clickable_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制是否可点击。 |
| bindingClickableIfValueEquals | `binding_clickable_IfValueEquals`<br>`binding_clickable_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制是否可点击。 |

* **ViewStateEnabled ->** [ViewStateEnabled.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateEnabled.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingEnabled | `binding_enabled` | 通过数据绑定设置是否启用。 |
| bindingEnabledIfCompareValueEquals | `binding_enabled_IfCompareValueEquals`<br>`binding_enabled_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制是否启用。 |
| bindingEnabledIfNotEmpty | `binding_enabled_IfNotEmpty` | 根据文本是否非空控制是否启用。 |
| bindingEnabledIfNotNull | `binding_enabled_IfNotNull` | 根据引用是否非 null 控制是否启用。 |
| bindingEnabledIfStringEquals | `binding_enabled_IfStringEquals`<br>`binding_enabled_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制是否启用。 |
| bindingEnabledIfStringEqualsIgnoreCase | `binding_enabled_IfStringEqualsIgnoreCase`<br>`binding_enabled_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制是否启用。 |
| bindingEnabledIfValueEquals | `binding_enabled_IfValueEquals`<br>`binding_enabled_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制是否启用。 |

* **ViewStateFocusable ->** [ViewStateFocusable.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateFocusable.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingFocusable | `binding_focusable` | 通过数据绑定设置是否可聚焦。 |
| bindingFocusableIfCompareValueEquals | `binding_focusable_IfCompareValueEquals`<br>`binding_focusable_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制是否可聚焦。 |
| bindingFocusableIfNotEmpty | `binding_focusable_IfNotEmpty` | 根据文本是否非空控制是否可聚焦。 |
| bindingFocusableIfNotNull | `binding_focusable_IfNotNull` | 根据引用是否非 null 控制是否可聚焦。 |
| bindingFocusableIfStringEquals | `binding_focusable_IfStringEquals`<br>`binding_focusable_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制是否可聚焦。 |
| bindingFocusableIfStringEqualsIgnoreCase | `binding_focusable_IfStringEqualsIgnoreCase`<br>`binding_focusable_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制是否可聚焦。 |
| bindingFocusableIfValueEquals | `binding_focusable_IfValueEquals`<br>`binding_focusable_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制是否可聚焦。 |
| bindingFocusableInTouchMode | `binding_focusableInTouchMode` | 通过数据绑定设置触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfCompareValueEquals | `binding_focusableInTouchMode_IfCompareValueEquals`<br>`binding_focusableInTouchMode_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfNotEmpty | `binding_focusableInTouchMode_IfNotEmpty` | 根据文本是否非空控制触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfNotNull | `binding_focusableInTouchMode_IfNotNull` | 根据引用是否非 null 控制触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfStringEquals | `binding_focusableInTouchMode_IfStringEquals`<br>`binding_focusableInTouchMode_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfStringEqualsIgnoreCase | `binding_focusableInTouchMode_IfStringEqualsIgnoreCase`<br>`binding_focusableInTouchMode_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制触摸模式下是否可聚焦。 |
| bindingFocusableInTouchModeIfValueEquals | `binding_focusableInTouchMode_IfValueEquals`<br>`binding_focusableInTouchMode_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制触摸模式下是否可聚焦。 |

* **ViewStateList ->** [ViewStateList.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateList.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingTVStateList | `binding_tv_state_list` | 通过数据绑定设置正文 [ColorStateList] 或恢复主题默认正文色。 |
| bindingTVStateListColor2Res | `binding_tv_state_list_color_2_res` | 通过数据绑定按两态颜色资源创建正文 [ColorStateList]。 |
| bindingTVStateListColor2Str | `binding_tv_state_list_color_2_str` | 通过数据绑定按两态 `#` 颜色创建正文 [ColorStateList]。 |
| bindingTVStateListColor3Res | `binding_tv_state_list_color_3_res` | 通过数据绑定按三态颜色资源创建正文 [ColorStateList]。 |
| bindingTVStateListColor3Str | `binding_tv_state_list_color_3_str` | 通过数据绑定按三态 `#` 颜色创建正文 [ColorStateList]。 |
| bindingTVStateListColor5Res | `binding_tv_state_list_color_5_res` | 通过数据绑定按五态颜色资源创建正文 [ColorStateList]。 |
| bindingTVStateListColor5Str | `binding_tv_state_list_color_5_str` | 通过数据绑定按五态 `#` 颜色创建正文 [ColorStateList]。 |
| bindingTVStateListColorRes | `binding_tv_state_list_color_res` | 通过数据绑定从颜色资源加载 [ColorStateList] 并设为正文色。 |
| bindingTVStateListHintColor | `binding_tv_state_list_hint_color` | 通过数据绑定将 [ColorStateList] 设为提示文字颜色。 |
| bindingTVStateListHintColor2Res | `binding_tv_state_list_hint_color_2_res` | 通过数据绑定按两态颜色资源创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColor2Str | `binding_tv_state_list_hint_color_2_str` | 通过数据绑定按两态 `#` 颜色创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColor3Res | `binding_tv_state_list_hint_color_3_res` | 通过数据绑定按三态颜色资源创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColor3Str | `binding_tv_state_list_hint_color_3_str` | 通过数据绑定按三态 `#` 颜色创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColor5Res | `binding_tv_state_list_hint_color_5_res` | 通过数据绑定按五态颜色资源创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColor5Str | `binding_tv_state_list_hint_color_5_str` | 通过数据绑定按五态 `#` 颜色创建提示文字 [ColorStateList]。 |
| bindingTVStateListHintColorRes | `binding_tv_state_list_hint_color_res` | 通过数据绑定从颜色资源加载 [ColorStateList] 并设为提示文字色。 |
| bindingTVStateListTextColor | `binding_tv_state_list_text_color` | 通过数据绑定将 [ColorStateList] 设为正文颜色。 |
| bindingViewStateList | `binding_view_state_list` | 通过数据绑定设置背景 [Drawable] 或移除背景。 |
| bindingViewStateListBackground | `binding_view_state_list_background` | 通过数据绑定将 [StateListDrawable] 或其它 [Drawable] 设为背景。 |
| bindingViewStateListSelector2Drawable | `binding_view_state_list_selector_2_drawable` | 通过数据绑定按两态 [Drawable] 创建背景。 |
| bindingViewStateListSelector2Res | `binding_view_state_list_selector_2_res` | 通过数据绑定按两态 drawable 资源创建背景 [StateListDrawable]。 |
| bindingViewStateListSelector3Drawable | `binding_view_state_list_selector_3_drawable` | 通过数据绑定按三态 [Drawable] 创建背景。 |
| bindingViewStateListSelector3Res | `binding_view_state_list_selector_3_res` | 通过数据绑定按三态 drawable 资源创建背景。 |
| bindingViewStateListSelector5Drawable | `binding_view_state_list_selector_5_drawable` | 通过数据绑定按五态 [Drawable] 创建背景。 |
| bindingViewStateListSelector5Res | `binding_view_state_list_selector_5_res` | 通过数据绑定按五态 drawable 资源创建背景。 |

* **ViewStateLongClickable ->** [ViewStateLongClickable.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateLongClickable.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingLongClickable | `binding_longClickable` | 通过数据绑定设置是否可长按。 |
| bindingLongClickableIfCompareValueEquals | `binding_longClickable_IfCompareValueEquals`<br>`binding_longClickable_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制是否可长按。 |
| bindingLongClickableIfNotEmpty | `binding_longClickable_IfNotEmpty` | 根据文本是否非空控制是否可长按。 |
| bindingLongClickableIfNotNull | `binding_longClickable_IfNotNull` | 根据引用是否非 null 控制是否可长按。 |
| bindingLongClickableIfStringEquals | `binding_longClickable_IfStringEquals`<br>`binding_longClickable_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制是否可长按。 |
| bindingLongClickableIfStringEqualsIgnoreCase | `binding_longClickable_IfStringEqualsIgnoreCase`<br>`binding_longClickable_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制是否可长按。 |
| bindingLongClickableIfValueEquals | `binding_longClickable_IfValueEquals`<br>`binding_longClickable_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制是否可长按。 |

* **ViewStatePressed ->** [ViewStatePressed.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStatePressed.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingPressed | `binding_pressed` | 通过数据绑定设置 Pressed 按下状态（代码层模拟；触摸时系统亦会刷新）。 |
| bindingPressedIfCompareValueEquals | `binding_pressed_IfCompareValueEquals`<br>`binding_pressed_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制 Pressed 按下状态。 |
| bindingPressedIfNotEmpty | `binding_pressed_IfNotEmpty` | 根据文本是否非空控制 Pressed 状态。 |
| bindingPressedIfNotNull | `binding_pressed_IfNotNull` | 根据引用是否非 null 控制 Pressed 状态。 |
| bindingPressedIfStringEquals | `binding_pressed_IfStringEquals`<br>`binding_pressed_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制 Pressed 状态。 |
| bindingPressedIfStringEqualsIgnoreCase | `binding_pressed_IfStringEqualsIgnoreCase`<br>`binding_pressed_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制 Pressed 状态。 |
| bindingPressedIfValueEquals | `binding_pressed_IfValueEquals`<br>`binding_pressed_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制 Pressed 状态。 |

* **ViewStateSelected ->** [ViewStateSelected.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewStateSelected.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingSelected | `binding_selected` | 通过数据绑定设置选中状态。 |
| bindingSelectedIfCompareValueEquals | `binding_selected_IfCompareValueEquals`<br>`binding_selected_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制选中状态。 |
| bindingSelectedIfNotEmpty | `binding_selected_IfNotEmpty` | 根据文本是否非空控制选中状态。 |
| bindingSelectedIfNotNull | `binding_selected_IfNotNull` | 根据引用是否非 null 控制选中状态。 |
| bindingSelectedIfStringEquals | `binding_selected_IfStringEquals`<br>`binding_selected_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制选中状态。 |
| bindingSelectedIfStringEqualsIgnoreCase | `binding_selected_IfStringEqualsIgnoreCase`<br>`binding_selected_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制选中状态。 |
| bindingSelectedIfValueEquals | `binding_selected_IfValueEquals`<br>`binding_selected_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制选中状态。 |

* **ViewVisibility ->** [ViewVisibility.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/view/ViewVisibility.kt)

| 方法 | binding 属性 | 说明 |
| :- | :- | :- |
| bindingVisibility | `binding_visibility` | 通过数据绑定设置视图可见性数值 |
| bindingVisibleOrGone | `binding_visibleOrGone` | 通过数据绑定在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfCompareValueEquals | `binding_visibleOrGone_IfCompareValueEquals`<br>`binding_visibleOrGone_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfNotEmpty | `binding_visibleOrGone_IfNotEmpty` | 根据文本是否非空控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfNotNull | `binding_visibleOrGone_IfNotNull` | 根据引用是否非 null 控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfStringEquals | `binding_visibleOrGone_IfStringEquals`<br>`binding_visibleOrGone_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfStringEqualsIgnoreCase | `binding_visibleOrGone_IfStringEqualsIgnoreCase`<br>`binding_visibleOrGone_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrGoneIfValueEquals | `binding_visibleOrGone_IfValueEquals`<br>`binding_visibleOrGone_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制视图在显示与隐藏 gone 之间切换 |
| bindingVisibleOrInVisible | `binding_visibleOrInVisible` | 通过数据绑定在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfCompareValueEquals | `binding_visibleOrInVisible_IfCompareValueEquals`<br>`binding_visibleOrInVisible_IfCompareValueEquals_value2` | 根据双方比对值接口实现是否在接口语义下相等控制视图在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfNotEmpty | `binding_visibleOrInVisible_IfNotEmpty` | 根据文本是否非空控制视图在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfNotNull | `binding_visibleOrInVisible_IfNotNull` | 根据引用是否非 null 控制视图在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfStringEquals | `binding_visibleOrInVisible_IfStringEquals`<br>`binding_visibleOrInVisible_IfStringEquals_value2` | 根据两字符串是否相等（区分大小写）控制视图在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfStringEqualsIgnoreCase | `binding_visibleOrInVisible_IfStringEqualsIgnoreCase`<br>`binding_visibleOrInVisible_IfStringEqualsIgnoreCase_value2` | 根据两字符串是否相等（忽略大小写）控制视图在显示与不可见占位之间切换 |
| bindingVisibleOrInVisibleIfValueEquals | `binding_visibleOrInVisible_IfValueEquals`<br>`binding_visibleOrInVisible_IfValueEquals_value2` | 根据两对象在工具类语义下是否相等控制视图在显示与不可见占位之间切换 |


## <span id="devsimplebindingadaptersattribute">**`dev.simple.bindingadapters.attribute`**</span>

> 多参数 BindingAdapter 合并实体（Margin、Padding、Shape、Span、RecyclerView 通知参数等），供 `app:binding_*` 绑定使用。

* **BarProgressState ->** [BarProgressState.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/BarProgressState.kt)

| 类型 | 说明 |
| :- | :- |
| BarProgressState | 绑定参数实体 |

* **EtAttrs ->** [EtAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/EtAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| EtMaxLengthAndText | 绑定参数实体 |

* **EtInputFilterAttrs ->** [EtInputFilterAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/EtInputFilterAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| EtInputFilterPresetSpec | 绑定参数实体 |
| ETIFSpec | 绑定参数实体 |

* **EtKeyboardAttrs ->** [EtKeyboardAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/EtKeyboardAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| EtKeyboardOpenDelayAt | 绑定参数实体 |

* **Margins ->** [Margins.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/Margins.kt)

| 方法 | 注释 |
| :- | :- |
| clone | detail: 通用 Margin 类 |
| equals | detail: 通用 Margin 类 |
| hashCode | detail: 通用 Margin 类 |
| toString | detail: 通用 Margin 类 |

* **Paddings ->** [Paddings.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/Paddings.kt)

| 方法 | 注释 |
| :- | :- |
| clone | detail: 通用 Padding 类 |
| equals | detail: 通用 Padding 类 |
| hashCode | detail: 通用 Padding 类 |
| toString | detail: 通用 Padding 类 |

* **RVAttributes ->** [RVAttributes.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/RVAttributes.kt)

| 类型 | 说明 |
| :- | :- |
| RvAdapterNotifyItemAt | 绑定参数实体 |
| RvAdapterNotifyItemMovedAt | 绑定参数实体 |
| RvItemDecorationAddOp | 绑定参数实体 |

* **ShapeAttrs ->** [ShapeAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/ShapeAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| ViewShapeRadiusColor | 绑定参数实体 |
| ViewShapeCornerRadii | 绑定参数实体 |
| ViewShapeStroke | 绑定参数实体 |
| ViewShapeGradient | 绑定参数实体 |
| ViewShapeGradientOrient | 绑定参数实体 |
| ViewShapeSize | 绑定参数实体 |
| ViewShapeGradientExtra | 绑定参数实体 |
| ViewShapeSpec | 绑定参数实体 |

* **SpanAttrs ->** [SpanAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/SpanAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| TvSpanQuote | 绑定参数实体 |
| TvSpanLeadingMargin | 绑定参数实体 |
| TvSpanBullet | 绑定参数实体 |
| TvSpanFontSize | 绑定参数实体 |
| TvSpanLineHeight | 绑定参数实体 |
| TvSpanShadow | 绑定参数实体 |
| TvSpanSpace | 绑定参数实体 |
| TvSpanImage | 绑定参数实体 |
| TvSpanSegment | 绑定参数实体 |

* **StateListAttrs ->** [StateListAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/StateListAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| ViewStateListColor2Str | 绑定参数实体 |
| ViewStateListColor3Str | 绑定参数实体 |
| ViewStateListColor5Str | 绑定参数实体 |
| ViewStateListColor2Res | 绑定参数实体 |
| ViewStateListColor3Res | 绑定参数实体 |
| ViewStateListColor5Res | 绑定参数实体 |
| ViewStateListSelector2Res | 绑定参数实体 |
| ViewStateListSelector3Res | 绑定参数实体 |
| ViewStateListSelector5Res | 绑定参数实体 |
| ViewStateListSelector2Drawable | 绑定参数实体 |
| ViewStateListSelector3Drawable | 绑定参数实体 |
| ViewStateListSelector5Drawable | 绑定参数实体 |

* **TVAttrs ->** [TVAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/TVAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| TvLineSpacingExtraMultiplier | 绑定参数实体 |
| TvMaxLengthAndText | 绑定参数实体 |
| TvTextSizeUnit | 绑定参数实体 |
| TvCompoundDrawablesFour | 绑定参数实体 |
| TvCompoundDrawablesFourRes | 绑定参数实体 |
| TvAutoSizeUniformConfiguration | 绑定参数实体 |
| TvAutoSizePresetSizes | 绑定参数实体 |
| TvTypefaceBold | 绑定参数实体 |

* **ViewAnimAttrs ->** [ViewAnimAttrs.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/ViewAnimAttrs.kt)

| 类型 | 说明 |
| :- | :- |
| ViewAnimAlphaAt | 绑定参数实体 |
| ViewAnimTranslateAt | 绑定参数实体 |
| ViewAnimShakeAt | 绑定参数实体 |
| ViewAnimShakePresetAt | 绑定参数实体 |
| ViewAnimDurationAt | 绑定参数实体 |
| ViewAnimRotateAt | 绑定参数实体 |
| ViewAnimAlphaFadeAt | 绑定参数实体 |
| ViewAnimScaleCenterAt | 绑定参数实体 |
| ViewAnimScaleAt | 绑定参数实体 |

* **WidthHeightDims ->** [WidthHeightDims.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/WidthHeightDims.kt)

| 类型 | 说明 |
| :- | :- |
| WidthHeightDims | 绑定参数实体 |

* **XYI ->** [XYI.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/attribute/XYI.kt)

| 方法 | 注释 |
| :- | :- |
| ofXY | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |
| ofIndex | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |
| clone | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |
| equals | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |
| hashCode | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |
| toString | 与 View 绝对滚动 BindingAdapter 配合：表示该轴保持当前 [android.view.View.getScrollX] / |

* **BindingAdaptersExt ->** [BindingAdaptersExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/bindingadapters/BindingAdaptersExt.kt)

| 方法 | 注释 |
| :- | :- |
| qualifiesScroll | 判断数值型绑定是否应执行一次滚动类逻辑 |
| qualifiesBindingAction | 判断时间戳是否应触发一次通用副作用 |


## <span id="devsimplecore">**`dev.simple.core`**</span>


## <span id="devsimplecoreadapter">**`dev.simple.core.adapter`**</span>

* **AdapterModel ->** [AdapterModel.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/adapter/AdapterModel.kt)

| 方法 | 注释 |
| :- | :- |
| refreshExist | 【刷新】是否存在数据状态 |
| mutableList | 【获取】MutableList 数据源 |
| mutableSet | 【获取】MutableSet 数据源 |
| count | 获取数据总数 |
| isLength | 判断数据长度是否等于期望长度 |
| greaterThan | 判断数据长度是否大于指定长度 |
| greaterThanOrEqual | 判断数据长度长度是否大于等于指定长度 |
| lessThan | 判断数据长度长度是否小于指定长度 |
| lessThanOrEqual | 判断数据长度长度是否小于等于指定长度 |
| isEmpty | 是否不存在数据 |
| isNotEmpty | 是否存在数据 |
| singleOrNull | 只有一条数据时才进行返回 |
| firstOrNull | 获取第一条数据 |
| lastOrNull | 获取最后一条数据 |
| getOrNull | 获取对应索引数据 |
| random | 随机获取指定索引数据 |
| clear | 清空全部数据 |
| add | 添加一条数据 |
| addAndClear | 清空全部数据并重新添加一条数据 |
| addAll | 添加多条数据 |
| addAllAndClear | 清空全部数据并重新添加多条数据 |
| addFirst | 添加一条数据到第一条 |
| addLast | 添加一条数据到最后一条 |
| addAt | 添加一条数据到指定索引 |
| remove | 移除一条数据 |
| removeAt | 移除一条数据 |
| removeAll | 移除指定集合数据 |
| removeFirstOrNull | 移除第一条数据并返回 |
| removeLastOrNull | 移除最后一条数据并返回 |
| setAt | 修改指定索引数据 |
| forEach | 循环方法 |
| forEachIndexed | 循环方法 |
| forEachReturn | 循环方法 |
| forEachIndexedReturn | 循环方法 |


## <span id="devsimplecoreapp">**`dev.simple.core.app`**</span>

* **AppExecutors ->** [AppExecutors.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/app/AppExecutors.kt)

| 方法 | 注释 |
| :- | :- |
| diskIO | detail: 整个应用程序的全局线程池 |
| networkIO | detail: 整个应用程序的全局线程池 |
| mainThread | detail: 整个应用程序的全局线程池 |
| execute | detail: 整个应用程序的全局线程池 |
| newExecutors | detail: 整个应用程序的全局线程池 |
| instance | detail: 整个应用程序的全局线程池 |

* **BaseIntent ->** [BaseIntent.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/app/BaseIntent.kt)

| 方法 | 注释 |
| :- | :- |
| returnT | 返回 T 链式调用 |
| insert | 插入数据 |
| reader | 读取数据并存储 |
| getDataMaps | 获取存储数据 Map |
| containsKey | 是否存在 Key |
| containsValue | 是否存在 Value |
| isNullValue | 对应 Key 保存的 Value 是否为 null |
| put | 保存数据 |
| putAll | 保存集合数据 |
| remove | 移除数据 |
| removeAll | 移除集合数据 |
| get | 获取对应 Key 保存的 Value |
| clear | 清空数据 |
| clearNull | 清除 null 数据 |
| clearNullKey | 清除 null Key 数据 |
| clearNullValue | 清除 null Value 数据 |
| clearEmpty | 清除 empty 数据 |
| clearEmptyKey | 清除 empty Key 数据 |
| clearEmptyValue | 清除 empty Value 数据 |


## <span id="devsimplecorechannel">**`dev.simple.core.channel`**</span>

* **AppChannel ->** [AppChannel.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/channel/AppChannel.kt)

| 方法 | 注释 |
| :- | :- |
| isNotFoundChannel | 是否未找到渠道实现 |
| getChannel | 获取渠道名 |
| getChannelInfo | 获取指定 Key 渠道信息 ( 只读 ) |
| getExtraInfo | 获取指定 Key 渠道额外携带信息 ( 只读 ) |
| getVariable | 获取渠道变量操作基类 |
| opVariable | 操作渠道变量 |

* **AbstractChannelFlavors ->** [AbstractChannelFlavors.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/channel/AbstractChannelFlavors.kt)

| 方法 | 注释 |
| :- | :- |
| getChannel | 获取渠道名 |
| getChannelInfo | 获取指定 Key 渠道信息 ( 只读 ) |
| getExtraInfo | 获取指定 Key 渠道额外携带信息 ( 只读 ) |
| getVariable | 获取渠道变量操作基类 |
| opVariable | 操作渠道变量 |


## <span id="devsimplecorelivedata">**`dev.simple.core.livedata`**</span>

* **ValueLiveData ->** [ValueLiveData.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/ValueLiveData.kt)

| 方法 | 注释 |
| :- | :- |
| dataValue | 获取数据值 |
| setValue | 主线程直接设置值 |
| postValue | 子线程安全设置值 ( 自动切换到主线程 ) |
| smartUpdateValue | 智能线程判断 ( 自动选择 setValue、postValue ) |
| resetValue | 重置数据值 ( 主线程直接设置 null ) |
| postResetValue | 重置数据值 ( 子线程安全设置 null ) |
| smartResetValue | 重置数据值 ( 智能线程判断设置 null ) |
| shouldUpdateValue | 判断是否需要更新值 |
| isEqual | 判断是否相同值 |
| isMainThread | 当前线程是否主线程 |

* **AbsentLiveData ->** [AbsentLiveData.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/AbsentLiveData.kt)

| 方法 | 注释 |
| :- | :- |

* **AbsentMutableLiveData ->** [AbsentMutableLiveData.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/AbsentMutableLiveData.kt)

| 方法 | 注释 |
| :- | :- |

* **StateInt ->** [StateIntLiveData.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/core/livedata/StateIntLiveData.kt)

| 方法 | 注释 |
| :- | :- |
| isXXXState | 是否 XXX 状态 |
| setXXX | 设置为 XXX 状态 ( 主线程 ) |
| postXXX | 设置为 XXX 状态 ( 子线程 ) |
| smartUpdateXXX | 设置为 XXX 状态 ( 智能线程判断 ) |
| isNORMALState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setNORMAL | 设置为 XXX 状态 ( 智能线程判断 ) |
| postNORMAL | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateNORMAL | 设置为 XXX 状态 ( 智能线程判断 ) |
| isINGState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setING | 设置为 XXX 状态 ( 智能线程判断 ) |
| postING | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateING | 设置为 XXX 状态 ( 智能线程判断 ) |
| isSUCCESSState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setSUCCESS | 设置为 XXX 状态 ( 智能线程判断 ) |
| postSUCCESS | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateSUCCESS | 设置为 XXX 状态 ( 智能线程判断 ) |
| isFAILState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setFAIL | 设置为 XXX 状态 ( 智能线程判断 ) |
| postFAIL | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateFAIL | 设置为 XXX 状态 ( 智能线程判断 ) |
| isERRORState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setERROR | 设置为 XXX 状态 ( 智能线程判断 ) |
| postERROR | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateERROR | 设置为 XXX 状态 ( 智能线程判断 ) |
| isSTARTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setSTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| postSTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateSTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| isRESTARTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setRESTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| postRESTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateRESTART | 设置为 XXX 状态 ( 智能线程判断 ) |
| isENDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setEND | 设置为 XXX 状态 ( 智能线程判断 ) |
| postEND | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateEND | 设置为 XXX 状态 ( 智能线程判断 ) |
| isPAUSEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setPAUSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postPAUSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdatePAUSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isRESUMEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setRESUME | 设置为 XXX 状态 ( 智能线程判断 ) |
| postRESUME | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateRESUME | 设置为 XXX 状态 ( 智能线程判断 ) |
| isSTOPState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setSTOP | 设置为 XXX 状态 ( 智能线程判断 ) |
| postSTOP | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateSTOP | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCANCELState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCANCEL | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCANCEL | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCANCEL | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCREATEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCREATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCREATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCREATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDESTROYState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDESTROY | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDESTROY | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDESTROY | 设置为 XXX 状态 ( 智能线程判断 ) |
| isRECYCLEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setRECYCLE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postRECYCLE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateRECYCLE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isINITState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setINIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postINIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateINIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isENABLEDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setENABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| postENABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateENABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| isENABLINGState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setENABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| postENABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateENABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDISABLEDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDISABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDISABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDISABLED | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDISABLINGState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDISABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDISABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDISABLING | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCONNECTEDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCONNECTINGState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCONNECTING | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCONNECTING | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCONNECTING | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDISCONNECTEDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDISCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDISCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDISCONNECTED | 设置为 XXX 状态 ( 智能线程判断 ) |
| isSUSPENDEDState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setSUSPENDED | 设置为 XXX 状态 ( 智能线程判断 ) |
| postSUSPENDED | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateSUSPENDED | 设置为 XXX 状态 ( 智能线程判断 ) |
| isUNKNOWNState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setUNKNOWN | 设置为 XXX 状态 ( 智能线程判断 ) |
| postUNKNOWN | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateUNKNOWN | 设置为 XXX 状态 ( 智能线程判断 ) |
| isINSERTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setINSERT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postINSERT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateINSERT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDELETEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDELETE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDELETE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDELETE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isUPDATEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setUPDATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postUPDATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateUPDATE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isSELECTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setSELECT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postSELECT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateSELECT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isENCRYPTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setENCRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postENCRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateENCRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isDECRYPTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setDECRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postDECRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateDECRYPT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isRESETState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setRESET | 设置为 XXX 状态 ( 智能线程判断 ) |
| postRESET | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateRESET | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCLOSEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCLOSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCLOSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCLOSE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isOPENState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setOPEN | 设置为 XXX 状态 ( 智能线程判断 ) |
| postOPEN | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateOPEN | 设置为 XXX 状态 ( 智能线程判断 ) |
| isEXITState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setEXIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postEXIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateEXIT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isNEXTState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setNEXT | 设置为 XXX 状态 ( 智能线程判断 ) |
| postNEXT | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateNEXT | 设置为 XXX 状态 ( 智能线程判断 ) |
| isNONEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setNONE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postNONE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateNONE | 设置为 XXX 状态 ( 智能线程判断 ) |
| isFINISHState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setFINISH | 设置为 XXX 状态 ( 智能线程判断 ) |
| postFINISH | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateFINISH | 设置为 XXX 状态 ( 智能线程判断 ) |
| isWAITINGState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setWAITING | 设置为 XXX 状态 ( 智能线程判断 ) |
| postWAITING | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateWAITING | 设置为 XXX 状态 ( 智能线程判断 ) |
| isCOMPLETEState | 设置为 XXX 状态 ( 智能线程判断 ) |
| setCOMPLETE | 设置为 XXX 状态 ( 智能线程判断 ) |
| postCOMPLETE | 设置为 XXX 状态 ( 智能线程判断 ) |
| smartUpdateCOMPLETE | 设置为 XXX 状态 ( 智能线程判断 ) |


## <span id="devsimpleextensionshi">**`dev.simple.extensions.hi`**</span>

* **HiInlineOnly ->** [HiInlineOnly.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/HiInlineOnly.kt)

| 方法 | 注释 |
| :- | :- |
| - | 内联限定标记，无对外 API |

* **HiIf ->** [HiIf.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif/HiIf.kt)

| 方法 | 注释 |
| :- | :- |
| hiIfNotNull | - |
| hiIfNotNullAs | - |
| hiIfNotNullWith | - |
| hiIfMap | - |

* **HiIfArray ->** [HiIfArray.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif/HiIfArray.kt)

| 方法 | 注释 |
| :- | :- |
| hiIfNotNullOrEmpty | - |

* **HiIfBoolean ->** [HiIfBoolean.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif/HiIfBoolean.kt)

| 方法 | 注释 |
| :- | :- |
| hiIf | - |
| hiIfElse | - |
| hiIfAnd | - |
| hiIfOr | - |

* **HiIfCollections ->** [HiIfCollections.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif/HiIfCollections.kt)

| 方法 | 注释 |
| :- | :- |
| hiIfNotNullOrEmpty | - |
| addHiIfNotNull | - |
| addAllHiIfNotNull | - |
| removeHiIfNotNull | - |
| removeAllHiIfNotNull | - |
| hiIfAnd | - |
| hiIfOr | - |

* **HiIfString ->** [HiIfString.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/hi/hiif/HiIfString.kt)

| 方法 | 注释 |
| :- | :- |
| hiIfNotNullOrEmpty | - |

## <span id="devsimplefeaturesdeprecatedadapter">**`dev.simple.features.deprecated.adapter`**</span>

> 旧版 DataBinding 列表方案，新项优先 binding-collection-adapter + AdapterModel。

* **BaseDataAdapter ->** [BaseDataAdapter.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/BaseDataAdapter.kt)

| 方法 | 注释 |
| :- | :- |
| onCreateViewHolder | detail: 通用 DataBinding Data Adapter |
| onBindViewHolder | detail: 通用 DataBinding Data Adapter |

* **BaseDataAdapterExt ->** [BaseDataAdapterExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/BaseDataAdapterExt.kt)

| 方法 | 注释 |
| :- | :- |
| onCreateViewHolder | detail: 通用 DataBinding Data AdapterExt |
| onBindViewHolder | detail: 通用 DataBinding Data AdapterExt |

* **BaseDataAdapterExt2 ->** [BaseDataAdapterExt2.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/BaseDataAdapterExt2.kt)

| 方法 | 注释 |
| :- | :- |
| onCreateViewHolder | detail: 通用 DataBinding Data AdapterExt2 |
| onBindViewHolder | detail: 通用 DataBinding Data AdapterExt2 |

* **ItemBinding ->** [ItemBinding.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/item/ItemBinding.kt)

| 方法 | 注释 |
| :- | :- |

* **ItemLifecycle ->** [ItemLifecycle.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/deprecated/adapter/item/ItemLifecycle.kt)

| 方法 | 注释 |
| :- | :- |
| of | detail: Item Binding Lifecycle |
| getLifecycleOwner | detail: Item Binding Lifecycle |
| setLifecycleOwner | detail: Item Binding Lifecycle |
| getLifecycleImpl | detail: Item Binding Lifecycle |
| setLifecycleImpl | detail: Item Binding Lifecycle |
| isValidLifecycleOwner | LifecycleOwner 是否有效 |
| isInvalidLifecycleOwner | LifecycleOwner 是否失效 |
| tryGetLifecycleOwner | 尝试获取 LifecycleOwner |
| findLifecycleOwner | 从给定的 View 中获取 LifecycleOwner |



## <span id="devsimplefeatures">**`dev.simple.features`**</span>


## <span id="devsimplefeaturesrepository">**`dev.simple.features.repository`**</span>

* **NetworkBoundResource ->** [NetworkBoundResource.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/NetworkBoundResource.kt)

| 方法 | 注释 |
| :- | :- |
| asLiveData | NetworkBoundResource.kt |

* **NetworkBoundScopeResource ->** [NetworkBoundScopeResource.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/NetworkBoundScopeResource.kt)

| 方法 | 注释 |
| :- | :- |
| fetchService | - |
| fetchRandomUUID | - |
| shouldFetchByUUID | - |

* **Resource ->** [Resource.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/Resource.kt)

| 方法 | 注释 |
| :- | :- |

* **ApiResponse ->** [Response.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/Response.kt)

| 方法 | 注释 |
| :- | :- |

* **Status ->** [Status.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/repository/Status.kt)

| 方法 | 注释 |
| :- | :- |


## <span id="devsimplefeaturesweb">**`dev.simple.features.web`**</span>

* **WebViewAssist ->** [WebViewAssist.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/features/web/WebViewAssist.kt)

| 方法 | 注释 |
| :- | :- |
| setGlobalBuilder | 设置全局 WebView 常用配置构建类 |
| getGlobalBuilder | 获取全局 WebView 常用配置构建类 |
| setCookie | 将 Cookie 设置到 WebView |
| getCookie | 获取指定 Url 的 Cookie |
| removeCookie | 移除 Cookie |
| removeSessionCookie | 移除 Session Cookie |
| removeAllCookie | 移除所有的 Cookie |
| setWebView | 设置 WebView |
| getWebView | 获取 WebView |
| isWebViewNotEmpty | WebView 是否不为 null |
| setBuilder | 设置 WebView 常用配置构建类 |
| getBuilder | 获取 WebView 常用配置构建类 |
| apply | 应用 ( 设置 ) 配置 |
| loadUrl | 加载网页 |
| loadData | 加载 Html 代码 |
| loadDataWithBaseURL | 加载 Html 代码 |
| postUrl | 使用 POST 方法将带有 postData 的 url 加载到 WebView 中 |
| getSettings | 获取 WebView 配置 |
| getUserAgentString | 获取浏览器标识 UA |
| setUserAgentString | 设置浏览器标识 |
| addJavascriptInterface | 添加 JS 交互注入对象 |
| removeJavascriptInterface | 移除 JS 交互注入对象 |
| evaluateJavascript | 执行 JS 方法 |
| setWebViewClient | 设置处理各种通知和请求事件对象 |
| getWebViewClient | 获取处理各种通知和请求事件对象 |
| setWebChromeClient | 设置辅助 WebView 处理 Javascript 对话框、标题等对象 |
| getWebChromeClient | 获取辅助 WebView 处理 Javascript 对话框、标题等对象 |
| destroy | 销毁处理 |
| canGoBack | WebView 是否可以后退 |
| goBack | WebView 后退 |
| canGoForward | WebView 是否可以前进 |
| goForward | WebView 前进 |
| canGoBackOrForward | WebView 是否可以跳转到当前起始点相距的历史记录 |
| goBackOrForward | WebView 跳转到当前起始点相距的历史记录 |
| reload | 刷新页面 ( 当前页面的所有资源都会重新加载 ) |
| stopLoading | 停止加载 |
| clearCache | 清除资源缓存 |
| clearHistory | 清除当前 WebView 访问的历史记录 |
| clearFormData | 清除自动完成填充的表单数据 |
| getScale | 获取缩放比例 |
| getScrollY | 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 ) |
| getScrollX | 获取当前内容横向滚动距离 |
| getContentHeight | 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 ) |
| getScaleHeight | 获取缩放高度 |
| getHeight | 获取 WebView 控件高度 |
| pageDown | 将视图内容向下滚动一半页面大小 |
| pageUp | 将视图内容向上滚动一半页面大小 |
| handlerKeyDown | 处理按键 ( 是否回退 ) |
| setLayerTypeSoftware | 关闭 WebView 硬件加速功能 |
| setLayerType | 设置 WebView 硬件加速类型 |
| getUrl | 获取当前 Url |
| getOriginalUrl | 获取最初请求 Url |
| getHitTestResult | 获取长按事件类型 |
| setWebViewAssist | 设置 WebView 辅助类 |
| setOnApplyListener | 设置应用配置监听事件 |
| getApplyListener | 获取应用配置监听事件 |
| onApply | 应用配置通知方法 |
| clone | 克隆方法 ( 用于全局配置克隆操作 ) |
| reset | 重置方法 |
| isJavaScriptEnabled | 是否支持 JavaScript |
| setJavaScriptEnabled | 设置是否支持 JavaScript |
| getRenderPriority | 获取渲染优先级 |
| setRenderPriority | 设置渲染优先级 |
| isWideViewPortEnabled | 是否使用宽视图 |
| setWideViewPortEnabled | 设置是否使用宽视图 |
| isLoadWithOverviewMode | 是否按宽度缩小内容以适合屏幕 |
| setLoadWithOverviewMode | 设置是否按宽度缩小内容以适合屏幕 |
| getLayoutAlgorithm | 获取基础布局算法 |
| setLayoutAlgorithm | 设置基础布局算法 |
| isSupportZoom | 是否支持缩放 |
| setSupportZoom | 设置是否支持缩放 |
| isBuiltInZoomControls | 是否显示内置缩放工具 |
| setBuiltInZoomControls | 设置是否显示内置缩放工具 |
| isDisplayZoomControls | 是否显示缩放工具 |
| setDisplayZoomControls | 设置是否显示缩放工具 |
| getTextZoom | 获取文本缩放倍数 |
| setTextZoom | 设置文本缩放倍数 |
| getStandardFontFamily | 获取 WebView 字体 |
| setStandardFontFamily | 设置 WebView 字体 |
| getDefaultFontSize | 获取 WebView 字体大小 |
| setDefaultFontSize | 设置 WebView 字体大小 |
| getMinimumFontSize | 获取 WebView 支持最小字体大小 |
| setMinimumFontSize | 设置 WebView 支持最小字体大小 |
| getMixedContentMode | 获取混合内容模式 |
| setMixedContentMode | 设置混合内容模式 |
| isLoadsImagesAutomatically | 是否支持自动加载图片 |
| setLoadsImagesAutomatically | 设置是否支持自动加载图片 |
| isJavaScriptCanOpenWindowsAutomatically | 是否支持通过 JS 打开新窗口 |
| setJavaScriptCanOpenWindowsAutomatically | 设置是否支持通过 JS 打开新窗口 |
| getDefaultTextEncodingName | 获取编码格式 |
| setDefaultTextEncodingName | 设置编码格式 |
| isGeolocationEnabled | 是否允许网页执行定位操作 |
| setGeolocationEnabled | 设置是否允许网页执行定位操作 |
| isAllowFileAccess | 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 ) |
| setAllowFileAccess | 设置是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 ) |
| isAllowFileAccessFromFileURLs | 是否允许通过 file url 加载的 JS 代码读取其他的本地文件 |
| setAllowFileAccessFromFileURLs | 设置是否允许通过 file url 加载的 JS 代码读取其他的本地文件 |
| isAllowUniversalAccessFromFileURLs | 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 ) |
| setAllowUniversalAccessFromFileURLs | 设置是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 ) |
| isBlockNetworkLoads | 是否不从网络加载资源 |
| setBlockNetworkLoads | 设置是否不从网络加载资源 |
| isBlockNetworkImage | 是否不从网络加载图像资源 |
| setBlockNetworkImage | 设置是否不从网络加载图像资源 |
| isMediaPlaybackRequiresUserGesture | 是否需要用户手势来播放媒体 |
| setMediaPlaybackRequiresUserGesture | 设置是否需要用户手势来播放媒体 |
| getCacheMode | 获取 WebView 缓存模式 |
| setCacheMode | 设置 WebView 缓存模式 |
| isDomStorageEnabled | 是否支持 DOM Storage |
| setDomStorageEnabled | 设置是否支持 DOM Storage |
| isAppCacheEnabled | 是否开启 Application Caches 功能 |
| setAppCacheEnabled | 设置是否开启 Application Caches 功能 |
| getAppCachePath | 获取 Application Caches 地址 |
| setAppCachePath | 设置 Application Caches 地址 |
| getAppCacheMaxSize | 获取 Application Caches 大小 |
| setAppCacheMaxSize | 设置 Application Caches 大小 |
| isDatabaseEnabled | 是否支持数据库缓存 |
| setDatabaseEnabled | 设置是否支持数据库缓存 |
| getDatabasePath | 获取数据库缓存路径 |
| setDatabasePath | 设置数据库缓存路径 |


## <span id="devsimpleextensionssize">**`dev.simple.extensions.size`**</span>

* **AppSize ->** [AppSize.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/size/AppSize.kt)

| 方法 | 注释 |
| :- | :- |
| dp2px | dp 转 px |
| dp2pxf | dp 转 px ( float ) |
| px2dp | px 转 dp |
| px2dpf | px 转 dp ( float ) |
| sp2px | sp 转 px |
| sp2pxf | sp 转 px ( float ) |
| px2sp | px 转 sp |
| px2spf | px 转 sp ( float ) |
| getDimension | 获取 Dimension |
| getDimensionInt | 获取 Dimension |

* **AppAutoSize ->** [AppAutoSize.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/size/AppAutoSize.kt)

| 方法 | 注释 |
| :- | :- |
| dp2px | dp 转 px |
| dp2pxf | dp 转 px ( float ) |
| px2dp | px 转 dp |
| px2dpf | px 转 dp ( float ) |
| sp2px | sp 转 px |
| sp2pxf | sp 转 px ( float ) |
| px2sp | px 转 sp |
| px2spf | px 转 sp ( float ) |
| getDimension | 获取 Dimension |
| getDimensionInt | 获取 Dimension |


## <span id="devsimpleextensionsimage">**`dev.simple.extensions.image`**</span>

* **AppImageConfig ->** [AppImageConfig.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/image/AppImageConfig.kt)

| 方法 | 注释 |
| :- | :- |
| create | detail: App ImageConfig 缓存类 |
| setCreator | 设置 ImageConfig 创建器 |
| devVariableExt | 获取变量操作基类扩展类 |
| toImageConfig | 通过 Key 获取 ImageConfig |
| defaultValue | 获取 ImageConfig 默认值 |
| setDefaultValue | 设置 ImageConfig 默认值 |

* **AppAutoImageConfig ->** [AppAutoImageConfig.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/image/AppAutoImageConfig.kt)

| 方法 | 注释 |
| :- | :- |
| devVariableExt | 获取变量操作基类扩展类 |
| toImageConfig | 通过 Key 获取 ImageConfig |
| defaultValue | 获取 ImageConfig 默认值 |
| setDefaultValue | 设置 ImageConfig 默认值 |


## <span id="devsimpleextensions">**`dev.simple.extensions`**</span>

* **DevSource 扩展 ->** [DevSource.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/DevSource.kt)

| 方法 | 注释 |
| :- | :- |

* **Price 扩展 ->** [Price.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/Price.kt)

| 方法 | 注释 |
| :- | :- |


## <span id="devsimpleextensionsequality">**`dev.simple.extensions.equality`**</span>

* **EqualityExt ->** [EqualityExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/equality/EqualityExt.kt)

| 方法 | 注释 |
| :- | :- |


## <span id="devsimpleextensionsqualifies">**`dev.simple.extensions.qualifies`**</span>

* **QualifiesExt ->** [QualifiesExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies/QualifiesExt.kt)

| 方法 | 注释 |
| :- | :- |

* **QualifiesLiveDataExt ->** [QualifiesLiveDataExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies/QualifiesLiveDataExt.kt)

| 方法 | 注释 |
| :- | :- |

* **QualifiesObservableExt ->** [QualifiesObservableExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies/QualifiesObservableExt.kt)

| 方法 | 注释 |
| :- | :- |

* **QualifiesObservableFieldExt ->** [QualifiesObservableFieldExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies/QualifiesObservableFieldExt.kt)

| 方法 | 注释 |
| :- | :- |

* **QualifiesStateFlowExt ->** [QualifiesStateFlowExt.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/extensions/qualifies/QualifiesStateFlowExt.kt)

| 方法 | 注释 |
| :- | :- |


## <span id="devsimpleinterfaces">**`dev.simple.interfaces`**</span>

* **BindingClick ->** [BindingClick.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingClick.kt)

| 方法 | 注释 |
| :- | :- |
| onClick | detail: DataBinding 点击事件 |

* **BindingConsumer ->** [BindingConsumer.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingConsumer.kt)

| 方法 | 注释 |
| :- | :- |
| accept | 传入指定参数执行操作 |

* **BindingGet ->** [BindingGet.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/BindingGet.kt)

| 方法 | 注释 |
| :- | :- |
| get | 获取泛型值 |

* **CompareValue ->** [CompareValue.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/CompareValue.kt)

| 方法 | 注释 |
| :- | :- |
| compareValue | 获取实际比对值 |
| equalsCompareValue | 判断与另一比对值是否一致 |

* **FunctionCallback ->** [FunctionCallback.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/src/main/java/dev/simple/interfaces/FunctionCallback.kt)

| 方法 | 注释 |
| :- | :- |
| block | detail: 方法流程回调 |
| start | detail: 方法流程回调 |
| success | detail: 方法流程回调 |
| error | detail: 方法流程回调 |
| finish | detail: 方法流程回调 |


