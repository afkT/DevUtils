
## Gradle

```gradle
// AndroidX
implementation 'io.github.afkt:DevWidgetX:1.1.9'
```

## 目录结构

```
- dev                  | 根目录
   - widget            | 自定义 View 根目录
      - adjust         | 自动调节高度 View
      - assist         | View 辅助类
      - custom         | 自定义 View
      - decoration     | RecyclerView ItemDecoration
         - grid        | Grid ItemDecoration
         - linear      | Linear ItemDecoration
      - function       | 需求功能 View
      - ui             | UI View
         - round       | 圆角相关 View
      - utils          | 工具类目录
```


## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md)

## README

- 效果可运行 DevUtils 项目点击 DevWidget UI 库查看

- 该库依赖 [DevApp](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md) 开发，需引用 DevApp 库

- [Preview README](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md)


## API


- dev                                                  | 根目录
   - [widget](#devwidget)                              | 自定义 View 根目录
      - [adjust](#devwidgetadjust)                     | 自动调节高度 View
      - [assist](#devwidgetassist)                     | View 辅助类
      - [custom](#devwidgetcustom)                     | 自定义 View
      - [decoration](#devwidgetdecoration)             | RecyclerView ItemDecoration
         - [grid](#devwidgetdecorationgrid)            | Grid ItemDecoration
         - [linear](#devwidgetdecorationlinear)        | Linear ItemDecoration
      - [function](#devwidgetfunction)                 | 需求功能 View
      - [ui](#devwidgetui)                             | UI View
         - [round](#devwidgetuiround)                  | 圆角相关 View
      - [utils](#devwidgetutils)                       | 工具类目录


## <span id="dev">**`dev`**</span>


## <span id="devwidget">**`dev.widget`**</span>


* **DevWidget ->** [DevWidget.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/DevWidget.java)

| 方法 | 注释 |
| :- | :- |
| getDevWidgetVersionCode | 获取 DevWidget 版本号 |
| getDevWidgetVersion | 获取 DevWidget 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |


## <span id="devwidgetadjust">**`dev.widget.adjust`**</span>


* **自动调节高度 GridView ->** [AdjustHeightGridView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/adjust/AdjustHeightGridView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |


* **自动调节高度 ListView ->** [AdjustHeightListView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/adjust/AdjustHeightListView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |


* **自动调节高度 RecyclerView ->** [AdjustHeightRecyclerView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/adjust/AdjustHeightRecyclerView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |


* **自动调节高度 WebView ->** [AdjustHeightWebView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/adjust/AdjustHeightWebView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |


## <span id="devwidgetassist">**`dev.widget.assist`**</span>


* **View 填充辅助类 ->** [ViewAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/assist/ViewAssist.java)

| 方法 | 注释 |
| :- | :- |
| wrap | 传入包裹 View |
| showIng | showIng |
| showFailed | showFailed |
| showSuccess | showSuccess |
| showEmptyData | showEmptyData |
| showType | 显示 Type Adapter View |
| notifyDataSetChanged | notifyDataSetChanged |
| gone | gone |
| visible | visible |
| register | 注册 type |
| unregister | 取消注册 type |
| reset | 重置处理 |
| getWrapper | getWrapper |
| getTag | getTag |
| setTag | setTag |
| getData | getData |
| setData | setData |
| getAdapter | getAdapter |
| getView | getView |
| getCurrentType | getCurrentType |
| getCurrentView | getCurrentView |
| setListener | setListener |


## <span id="devwidgetcustom">**`dev.widget.custom`**</span>


* **自定义 Gallery 滑动控制 ->** [CustomGallery.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomGallery.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onFling | onFling |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |


* **自定义 HorizontalScrollView 滑动监听、滑动控制 ->** [CustomHorizontalScrollView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomHorizontalScrollView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onScrollChanged | onScrollChanged |
| computeScrollDeltaToGetChildRectOnScreen | computeScrollDeltaToGetChildRectOnScreen |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| setScrollCallback | 设置滑动监听回调 |


* **自定义 NestedScrollView 滑动监听、滑动控制 ->** [CustomNestedScrollView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomNestedScrollView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onScrollChanged | onScrollChanged |
| computeScrollDeltaToGetChildRectOnScreen | computeScrollDeltaToGetChildRectOnScreen |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| setScrollCallback | 设置滑动监听回调 |


* **自定义 RecyclerView 滑动监听、滑动控制 ->** [CustomRecyclerView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomRecyclerView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onScrolled | onScrolled |
| onScrollStateChanged | onScrollStateChanged |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| setScrollCallback | 设置滑动监听回调 |
| getCustomScrollX | 获取距离左边距离 |
| getCustomScrollY | 获取距离顶部距离 |
| onScrollChanged | 滑动改变通知 |


* **自定义 ScrollView 滑动监听、滑动控制 ->** [CustomScrollView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomScrollView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onScrollChanged | onScrollChanged |
| computeScrollDeltaToGetChildRectOnScreen | computeScrollDeltaToGetChildRectOnScreen |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| setScrollCallback | 设置滑动监听回调 |


* **自定义 ViewPager 滑动监听、滑动控制 ->** [CustomViewPager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomViewPager.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| onPageScrolled | onPageScrolled |
| onPageScrollStateChanged | onPageScrollStateChanged |
| onSlideDirection | 滑动方向 |


* **自定义 WebView 滑动监听、滑动控制 ->** [CustomWebView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/custom/CustomWebView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onScrollChanged | onScrollChanged |
| onTouchEvent | onTouchEvent |
| onInterceptTouchEvent | onInterceptTouchEvent |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |
| setScrollCallback | 设置滑动监听回调 |


## <span id="devwidgetdecoration">**`dev.widget.decoration`**</span>


* **基础 RecyclerView Grid 分割线处理 ->** [BaseColorGridItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/BaseColorGridItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| isRowItemDecoration | 是否 Grid Row ItemDecoration |
| isColumnItemDecoration | 是否 Grid Column ItemDecoration |
| getSpanCount | 获取 Span 总数 ( Grid 列 ) |
| setSpanCount | 设置 Span 总数 ( Grid 列 ) |


* **RecyclerView 分割线绘制基类 ->** [BaseColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/BaseColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| isSingleDraw | 获取单条数据是否绘制分割线 |
| setSingleDraw | 设置单条数据是否绘制分割线 |
| getPaint | 获取分割线画笔 |
| isVertical | 判断分割线绘制方向是否为 VERTICAL |
| isHorizontal | 判断分割线绘制方向是否为 HORIZONTAL |
| setVertical | 设置分割线绘制方向为 VERTICAL |
| setHorizontal | 设置分割线绘制方向为 HORIZONTAL |
| getHeight | 获取分割线高度 |
| setHeight | 设置分割线高度 |
| getLeft | 获取分割线距左边距 |
| setLeft | 设置分割线距左边距 |
| getRight | 获取分割线距右边距 |
| setRight | 设置分割线距右边距 |
| setLeftRight | 设置分割线距左、右边距 |
| getOffset | 获取分割线偏差值 |
| setOffset | 设置分割线偏差值 |


## <span id="devwidgetdecorationgrid">**`dev.widget.decoration.grid`**</span>


* **RecyclerView Grid 列分割线处理 ( 每一列数据 ) ->** [GridColumnColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/grid/GridColumnColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| getItemOffsets | getItemOffsets |
| onDraw | onDraw |


* **RecyclerView Grid 行分割线处理 ( 每一行数据 ) ->** [GridRowColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/grid/GridRowColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| getItemOffsets | getItemOffsets |
| onDraw | onDraw |


## <span id="devwidgetdecorationlinear">**`dev.widget.decoration.linear`**</span>


* **RecyclerView Linear 分割线处理 ( 第一条数据 ) ->** [FirstLinearColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/linear/FirstLinearColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| getItemOffsets | getItemOffsets |
| onDraw | onDraw |


* **RecyclerView Linear 分割线处理 ( 最后一条数据 ) ->** [LastLinearColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/linear/LastLinearColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| getItemOffsets | getItemOffsets |
| onDraw | onDraw |


* **RecyclerView Linear 分割线处理 ( 每一条数据 ) ->** [LinearColorItemDecoration.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/decoration/linear/LinearColorItemDecoration.java)

| 方法 | 注释 |
| :- | :- |
| getItemOffsets | getItemOffsets |
| onDraw | onDraw |


## <span id="devwidgetfunction">**`dev.widget.function`**</span>


* **自定义 FrameLayout 设置最大、最小宽高 ->** [LimitLayout.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/function/LimitLayout.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |


* **TextView 换行监听 ->** [LineTextView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/function/LineTextView.java)

| 方法 | 注释 |
| :- | :- |
| onDraw | onDraw |
| isNewLine | 判断是否换行 |
| setNewLineCallback | 设置换行监听回调 |
| onNewLine | 换行触发 |


* **自定义 EditText 右边 Icon ->** [RightIconEditText.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/function/RightIconEditText.java)

| 方法 | 注释 |
| :- | :- |
| setCompoundDrawables | setCompoundDrawables |
| onTouchEvent | onTouchEvent |
| finalize | finalize |
| getRangeMultiple | 获取右边 Icon 触发范围倍数 |
| setRangeMultiple | 设置右边 Icon 触发范围倍数 |
| isDrawRightIcon | 是否绘制右边 Icon |
| setDrawRightIcon | 设置是否绘制右边 Icon |
| getRightClickListener | 获取右边 Icon 点击事件 |
| setRightClickListener | 设置右边 Icon 点击事件 |
| getRightIcon | 获取右边 Icon Drawable |
| setRightIcon | 设置右边 Icon Drawable |
| setTextWatcher | 设置输入监听回调 |


* **自定义签名 View ->** [SignView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/function/SignView.java)

| 方法 | 注释 |
| :- | :- |
| onDraw | onDraw |
| onTouchEvent | onTouchEvent |
| clearCanvas | 清空画布 |
| snapshotByView | 通过 View 绘制为 Bitmap |
| getPath | 获取绘制路径 |
| setPath | 设置绘制路径 |
| getPaint | 获取绘制画笔 |
| setPaint | 设置绘制画笔 |
| setDrawCallback | 设置绘制回调事件 |


* **状态布局 ->** [StateLayout.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/function/StateLayout.java)

| 方法 | 注释 |
| :- | :- |
| reset | 重置处理 |
| setGlobal | setGlobal |
| setListener | setListener |
| showIng | showIng |
| showFailed | showFailed |
| showSuccess | showSuccess |
| showEmptyData | showEmptyData |
| showType | showType |
| notifyDataSetChanged | notifyDataSetChanged |
| gone | gone |
| visible | visible |
| register | 注册 type |
| unregister | 取消注册 type |
| getAssistTag | getAssistTag |
| setAssistTag | setAssistTag |
| getData | getData |
| setData | setData |
| getView | getView |
| getCurrentType | getCurrentType |
| getCurrentView | getCurrentView |


## <span id="devwidgetui">**`dev.widget.ui`**</span>


* **自定义角标 View ->** [CornerLabelView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/CornerLabelView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onDraw | onDraw |
| getPainter1 | 获取 Text1 Painter |
| getPainter2 | 获取 Text2 Painter |
| setPaddingTop | 设置顶部边距 |
| setPaddingCenter | 设置 Text1、Text2 边距 |
| setPaddingBottom | 设置底部边距 |
| setFillColor | 设置背景填充颜色 |
| setFillShader | 设置背景填充渐变色 |
| left | 设置左边显示角标 |
| right | 设置右边显示角标 |
| top | 设置顶部显示角标 |
| bottom | 设置底部显示角标 |
| triangle | 设置角标三角形铺满样式 |
| setText1 | 设置文本 |
| setTextColor1 | 设置字体颜色 |
| setTextHeight1 | 设置字体高度 |
| setTextBold1 | 设置字体是否加粗 |
| setText2 | 设置文本 |
| setTextColor2 | 设置字体颜色 |
| setTextHeight2 | 设置字体高度 |
| setTextBold2 | 设置字体是否加粗 |
| refresh | 刷新重绘处理 |
| getPaint | 获取画笔 |
| getText | 获取文本 |
| getTextColor | 获取字体颜色 |
| getTextHeight | 获取字体高度 |
| isTextBold | 获取字体是否加粗 |


* **翻转卡片 View ->** [FlipCardView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/FlipCardView.java)

| 方法 | 注释 |
| :- | :- |
| isFront | 当前是否显示正面 Layout |
| getCurrentPosition | 获取当前显示的索引 |
| getAdapter | 获取数据源适配器 |
| setAdapter | 设置数据源适配器 |
| notifyDataSetChanged | Adapter 数据源变更时调用 |
| flip | 翻转操作 |
| setInOutAnimator | 设置进出动画 |
| setFlipDistance | 设置翻牌角度 |
| getItemCount | getItemCount |
| getItemView | getItemView |


* **自定义点赞效果 View ->** [FlowLikeView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/FlowLikeView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onSizeChanged | onSizeChanged |
| like | 点赞操作 |
| getDrawables | 获取 Icon 集合 |
| setDrawables | 设置 Icon 集合 |
| setDrawablesById | 设置 Icon 集合 |
| getIconWidth | 获取点赞 Icon 宽度 |
| setIconWidth | 设置点赞 Icon 宽度 |
| getIconHeight | 获取点赞 Icon 高度 |
| setIconHeight | 设置点赞 Icon 高度 |
| getAnimDuration | 获取点赞动画执行时间 |
| setAnimDuration | 设置点赞动画执行时间 |


* **自定义加载 ProgressBar 样式 View ->** [LoadProgressBar.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/LoadProgressBar.java)

| 方法 | 注释 |
| :- | :- |
| onDraw | onDraw |
| reset | 重置参数 |
| getMax | 获取最大值 |
| setMax | 设置最大值 |
| getProgress | 获取当前进度 |
| setProgress | 设置当前进度 |
| getProgressColor | 获取进度条颜色 |
| setProgressColor | 设置进度条颜色 |
| getOuterRingColor | 获取外环进度条颜色 |
| setOuterRingColor | 设置外环进度条颜色 |
| getInsideCircleWidth | 获取内环进度条宽度 |
| setInsideCircleWidth | 设置内环进度条宽度 |
| getOuterRingWidth | 获取外环进度条宽度 |
| setOuterRingWidth | 设置外环进度条宽度 |
| isCanvasNumber | 是否绘制数字 |
| setCanvasNumber | 设置是否绘制数字 |
| getNumberTextSize | 获取绘制的字体大小 |
| setNumberTextSize | 设置绘制的字体大小 |
| getNumberTextColor | 获取绘制的数字颜色 |
| setNumberTextColor | 设置绘制的数字颜色 |
| getProgressStyle | 获取进度条样式 |
| setProgressStyle | 设置进度条样式 |


* **自定义圆角 View ->** [RadiusLayout.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/RadiusLayout.java)

| 方法 | 注释 |
| :- | :- |
| onSizeChanged | onSizeChanged |
| draw | draw |
| onSaveInstanceState | onSaveInstanceState |
| onRestoreInstanceState | onRestoreInstanceState |
| setRadius | 设置圆角值 |
| setRadiusLeftTop | 设置左上圆角值 |
| setRadiusLeftBottom | 设置左下圆角值 |
| setRadiusRightTop | 设置右上圆角值 |
| setRadiusRightBottom | 设置右下圆角值 |
| setRadiusLeft | 设置左上、左下圆角值 |
| setRadiusRight | 设置右上、右下圆角值 |
| setRadiusTop | 设置左上、右上圆角值 |
| setRadiusBottom | 设置左下、右下圆角值 |
| getRadius | 获取圆角值 |
| getRadiusLeftTop | 获取左上圆角值 |
| getRadiusLeftBottom | 获取左下圆角值 |
| getRadiusRightTop | 获取右上圆角值 |
| getRadiusRightBottom | 获取右下圆角值 |


* **自动同比放大 ImageView ->** [ResizableImageView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/ResizableImageView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| getZoomHeight | 获取缩放后的高度 |
| getWHListener | 获取宽高监听事件 |
| setWHListener | 设置宽高监听事件 |


* **自定义扫描 ( 二维码 / AR ) 效果形状 View ->** [ScanShapeView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/ScanShapeView.java)

| 方法 | 注释 |
| :- | :- |
| onDraw | onDraw |
| destroy | 销毁处理 |
| getShapeType | 获取扫描形状类型 |
| setShapeType | 设置扫描形状类型 |
| getCornerRadius | 获取拐角角度大小 |
| setCornerEffect | 设置是否拐角圆角 ( 主要是控制绘制边框的线 ) |
| setRegion | 设置扫描区域大小 |
| getRegionLeft | 获取扫描绘制区域距离左 / 右边距 |
| getRegionTop | 获取扫描绘制区域距离上 / 下边距 |
| getRegionWidth | 获取扫描区域宽度 |
| getRegionHeight | 获取扫描区域高度 |
| getRegion | 获取扫描区域信息 |
| getRegionParent | 获取在父布局中实际的位置 |
| getBorderMargin | 获取边框边距 |
| setBorderMargin | 设置边框边距 |
| getBorderColor | 获取边框颜色 |
| setBorderColor | 设置边框颜色 |
| getBorderWidth | 获取边框宽度 |
| setBorderWidth | 设置边框宽度 |
| isDrawBorder | 是否绘制边框 |
| setDrawBorder | 设置是否绘制边框 |
| isDrawBackground | 是否绘制背景 |
| setDrawBackground | 设置是否绘制背景 |
| getBGColor | 获取绘制的背景颜色 |
| setBGColor | 设置绘制的背景颜色 |
| isDrawAnim | 是否绘制动画效果 |
| setDrawAnim | 设置是否绘制动画效果 |
| isAutoAnim | 是否自动播放动画 |
| setAutoAnim | 设置是否自动播放动画 |
| getBorderToSquare | 获取正方形描边 ( 边框 ) 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部 |
| setBorderToSquare | 设置正方形描边 ( 边框 ) 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部 |
| getBorderWidthToSquare | 获取正方形描边 ( 边框 ) 宽度 |
| setBorderWidthToSquare | 设置正方形描边 ( 边框 ) 宽度 |
| getTriAngleLength | 获取每个角的点距离 ( 长度 ) 正方形四个角落区域 |
| setTriAngleLength | 设置每个角的点距离 ( 长度 ) 正方形四个角落区域 |
| isSpecialToSquare | 是否特殊处理 ( 正方形边框 ) |
| setSpecialToSquare | 设置是否特殊处理 ( 正方形边框 ) |
| getLineDurationToSquare | 获取正方形扫描动画速度 ( 毫秒 ) |
| setLineDurationToSquare | 设置正方形扫描动画速度 ( 毫秒 ) |
| getBitmapToSquare | 获取正方形扫描线条 Bitmap |
| setBitmapToSquare | 设置正方形扫描线条 Bitmap |
| getLineMarginTopToSquare | 获取正方形扫描线条向上 ( 下 ) 边距 |
| setLineMarginTopToSquare | 设置正方形扫描线条向上 ( 下 ) 边距 |
| getLineMarginLeftToSquare | 获取正方形扫描线条向左 ( 右 ) 边距 |
| setLineMarginLeftToSquare | 设置正方形扫描线条向左 ( 右 ) 边距 |
| getLineColorToSquare | 获取正方形线条动画颜色 ( 着色 ) |
| setLineColorToSquare | 设置正方形线条动画 ( 着色 ) |
| getLineWidthToHexagon | 获取六边形线条动画 ( 线条宽度 ) |
| setLineWidthToHexagon | 设置六边形线条动画 ( 线条宽度 ) |
| getLineMarginToHexagon | 获取六边形线条动画 ( 线条边距 ) |
| setLineMarginToHexagon | 设置六边形线条动画 ( 线条边距 ) |
| isLineAnimDirection | 获取六边形线条动画方向 ( true = 从左到右, false = 从右到左 ) |
| setLineAnimDirection | 设置六边形线条动画方向 ( true = 从左到右, false = 从右到左 ) |
| getLineColorToHexagon | 获取六边形线条动画颜色 |
| setLineColorToHexagon | 设置六边形线条动画颜色 |
| getBitmapToAnnulus | 获取环形扫描线条 Bitmap |
| setBitmapToAnnulus | 设置环形扫描线条 Bitmap |
| getLineColorToAnnulus | 获取环形线条动画颜色 ( 着色 ) |
| setLineColorToAnnulus | 设置环形线条动画 ( 着色 ) |
| getLineOffsetSpeedToAnnulus | 获取环形扫描线条速度 |
| setLineOffsetSpeedToAnnulus | 设置环形扫描线条速度 |
| getAnnulusDraws | 获取环形对应的环是否绘制 |
| setAnnulusDraws | 设置环形对应的环是否绘制 |
| getAnnulusColors | 获取环形对应的环绘制颜色 |
| setAnnulusColors | 设置环形对应的环绘制颜色 |
| getAnnulusLengths | 获取环形对应的环绘制长度 |
| setAnnulusLengths | 设置环形对应的环绘制长度 |
| getAnnulusWidths | 获取环形对应的环绘制宽度 |
| setAnnulusWidths | 设置环形对应的环绘制宽度 |
| getAnnulusMargins | 获取环形对应的环绘制边距 |
| setAnnulusMargins | 设置环形对应的环绘制边距 |
| startAnim | 启动动画 |
| stopAnim | 停止动画 |
| isAnimRunning | 是否动画运行中 |
| getRadius | getRadius |


* **波浪 View ->** [WaveView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/WaveView.java)

| 方法 | 注释 |
| :- | :- |
| onSizeChanged | onSizeChanged |
| onDraw | onDraw |
| getAmplitudeRatio | 获取波浪垂直振幅比率 |
| setAmplitudeRatio | 设置波浪垂直振幅比率 |
| getWaterLevelRatio | 获取波浪水位比率 |
| setWaterLevelRatio | 设置波浪水位比率 |
| getWaveLengthRatio | 获取波浪波长比率 |
| setWaveLengthRatio | 设置波浪波长比率 |
| getWaveShiftRatio | 获取波浪水平偏移比率 |
| setWaveShiftRatio | 设置波浪水平偏移比率 |
| getBorderWidth | 获取边框宽度 |
| getBorderColor | 获取边框颜色 |
| setBorder | 设置边框宽度、颜色 |
| getBehindWaveColor | 获取波浪背景色 |
| getFrontWaveColor | 获取波浪前景色 |
| setWaveColor | 设置波浪颜色 |
| getShapeType | 获取波浪外形形状 |
| setShapeType | 设置波浪外形形状 |
| isShowWave | 是否进行波浪图形处理 |
| setShowWave | 设置是否进行波浪图形处理 |


* **换行 View ->** [WrapView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/WrapView.java)

| 方法 | 注释 |
| :- | :- |
| onMeasure | onMeasure |
| onLayout | onLayout |
| refreshDraw | 刷新绘制 ( 更新配置信息后, 必须调用 ) |
| getRowLine | 获取 View 换行行数 |
| getMaxLine | 获取最大行数 |
| setMaxLine | 设置最大行数 |
| getRowTopMargin | 获取每一行向上的边距 ( 行间隔 ) |
| setRowTopMargin | 设置每一行向上的边距 ( 行间隔 ) |
| getViewLeftMargin | 获取每个 View 之间的 Left 边距 |
| setViewLeftMargin | 设置每个 View 之间的 Left 边距 |
| setRowViewMargin | 设置 Row View 边距 |


## <span id="devwidgetuiround">**`dev.widget.ui.round`**</span>


* **圆角 Drawable ->** [RoundDrawable.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/round/RoundDrawable.java)

| 方法 | 注释 |
| :- | :- |
| onStateChange | onStateChange |
| isStateful | isStateful |
| onBoundsChange | onBoundsChange |
| getStrokeWidth | 获取描边粗细 |
| setStrokeColors | 设置描边粗细和颜色 |
| setStrokeData | 设置描边粗细和颜色 |
| setBgData | 设置按钮的背景色 ( 只支持纯色, 不支持 Bitmap 或 Drawable ) |
| setRadiusAdjustBounds | 设置圆角大小是否自适应为 View 的高度的一半 |
| fromAttributeSet | 通过 AttributeSet 构建 RoundDrawable |
| setBackgroundKeepingPadding | 设置背景 |


* **圆角图片 ->** [RoundImageView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/round/RoundImageView.java)

| 方法 | 注释 |
| :- | :- |
| setScaleType | setScaleType |
| setAdjustViewBounds | setAdjustViewBounds |
| onDraw | onDraw |
| onSizeChanged | onSizeChanged |
| setPadding | setPadding |
| setPaddingRelative | setPaddingRelative |
| setImageBitmap | setImageBitmap |
| setImageDrawable | setImageDrawable |
| setImageResource | setImageResource |
| setImageURI | setImageURI |
| getImageAlpha | getImageAlpha |
| setImageAlpha | setImageAlpha |
| getColorFilter | getColorFilter |
| setColorFilter | setColorFilter |
| onTouchEvent | onTouchEvent |
| getBorderWidth | 获取边框宽度 |
| setBorderWidth | 设置边框宽度 |
| getBorderColor | 获取边框颜色 |
| setBorderColor | 设置边框颜色 |
| getCircleBackgroundColor | 获取圆圈背景颜色 |
| setCircleBackgroundColor | 设置圆圈背景颜色 |
| isBorderOverlay | 是否叠加边框 |
| setBorderOverlay | 设置是否叠加边框 |
| isDisableCircularTransformation | 是否开启圆圈处理 |
| setDisableCircularTransformation | 设置是否开启圆圈处理 |


## <span id="devwidgetutils">**`dev.widget.utils`**</span>


* **RadiusLayout 圆角属性封装处理类 ->** [RadiusAttrs.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/utils/RadiusAttrs.java)

| 方法 | 注释 |
| :- | :- |
| onSizeChanged | View 宽高改变时调用 |
| getPath | 获取绘制路径 |
| setRadius | 设置圆角值 |
| setRadiusLeftTop | 设置左上圆角值 |
| setRadiusLeftBottom | 设置左下圆角值 |
| setRadiusRightTop | 设置右上圆角值 |
| setRadiusRightBottom | 设置右下圆角值 |
| setRadiusLeft | 设置左上、左下圆角值 |
| setRadiusRight | 设置右上、右下圆角值 |
| setRadiusTop | 设置左上、右上圆角值 |
| setRadiusBottom | 设置左下、右下圆角值 |
| getRadius | 获取圆角值 |
| getRadiusLeftTop | 获取左上圆角值 |
| getRadiusLeftBottom | 获取左下圆角值 |
| getRadiusRightTop | 获取右上圆角值 |
| getRadiusRightBottom | 获取右下圆角值 |
| onSaveInstanceState | onSaveInstanceState |
| onRestoreInstanceState | onRestoreInstanceState |


* **波浪 View Helper 类 ->** [WaveHelper.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/utils/WaveHelper.java)

| 方法 | 注释 |
| :- | :- |
| get | 获取 WaveHelper |
| start | 启动动画 |
| cancel | 关闭动画 |
| getAmplitudeRatio | 获取波浪垂直振幅比率 |
| setAmplitudeRatio | 设置波浪垂直振幅比率 |
| getWaterLevelRatio | 获取波浪水位比率 |
| setWaterLevelRatio | 设置波浪水位比率 |
| getWaveLengthRatio | 获取波浪波长比率 |
| setWaveLengthRatio | 设置波浪波长比率 |
| getWaveShiftRatio | 获取波浪水平偏移比率 |
| setWaveShiftRatio | 设置波浪水平偏移比率 |
| getBorderWidth | 获取边框宽度 |
| getBorderColor | 获取边框颜色 |
| setBorder | 设置边框宽度、颜色 |
| getBehindWaveColor | 获取波浪背景色 |
| getFrontWaveColor | 获取波浪前景色 |
| setWaveColor | 设置波浪颜色 |
| getShapeType | 获取波浪外形形状 |
| setShapeType | 设置波浪外形形状 |
| isShowWave | 是否进行波浪图形处理 |
| setShowWave | 设置是否进行波浪图形处理 |
| buildPropertyAnimation | 通过属性动画进行设置波浪 View 动画效果 |
| build | build |
| getWaveShiftRatioStart | getWaveShiftRatioStart |
| getWaveShiftRatioEnd | getWaveShiftRatioEnd |
| getWaveShiftRatioMillis | getWaveShiftRatioMillis |
| setWaveShiftRatioMillis | 设置波浪移动方向效果属性值 |
| getAmplitudeRatioStart | getAmplitudeRatioStart |
| getAmplitudeRatioEnd | getAmplitudeRatioEnd |
| getAmplitudeRatioMillis | getAmplitudeRatioMillis |
| setAmplitudeRatioMillis | 设置波浪大小 ( 上下波动 ) 效果属性值 |
| getWaterLevelRatioStart | getWaterLevelRatioStart |
| getWaterLevelRatioEnd | getWaterLevelRatioEnd |
| getWaterLevelRatioMillis | getWaterLevelRatioMillis |
| setWaterLevelRatioMillis | 设置水位高度属性值 |


* **DevWidget 属性封装处理类 ->** [WidgetAttrs.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/utils/WidgetAttrs.java)

| 方法 | 注释 |
| :- | :- |
| getMaxWidth | 获取 View 最大显示宽度 |
| setMaxWidth | 设置 View 最大显示宽度 |
| getMaxHeight | 获取 View 最大显示高度 |
| setMaxHeight | 设置 View 最大显示高度 |
| isSlide | 是否允许滑动 |
| setSlide | 设置是否允许滑动 |
| toggleSlide | 切换滑动控制状态 |