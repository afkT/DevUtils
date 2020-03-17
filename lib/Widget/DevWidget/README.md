
# About

> DevWidget 主要是常用 UI 效果、特殊功能需求展示的自定义 View UI 库，持续增加开发中常用的 View 效果、Adapter 模式设计复用样式等，提升开发效率，便于快捷引用快速开发

## Gradle

```java
implementation 'com.afkt:DevWidget:1.0.0'

// AndroidX
implementation 'com.afkt:DevWidgetX:1.0.0'
```

## README

[README - API](https://github.com/afkT/DevUtils/blob/master/lib/Widget/DevWidget/README_API.md)

[Change Log](https://github.com/afkT/DevUtils/blob/master/lib/Widget/DevWidget/CHANGELOG.md)


## Preview

| WrapView | LineTextView | SignView |
|:--:|:--:|:--:|
| ![WrapView](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/wrap_view.jpg) | ![LineTextView](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/line_text_view.jpg) | ![SignView](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/sign_view.jpg) |
| ScanShapeView - Square | ScanShapeView - Hexagon | ScanShapeView - Annulus |
| ![ScanShapeView - Square](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/scan_shape_view_square.gif) | ![ScanShapeView - Hexagon](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/scan_shape_view_hexagon.gif) | ![ScanShapeView - Annulus](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/scan_shape_view_annulus.gif) |
| LoadProgressBar |   |   |
| ![LoadProgressBar](https://raw.githubusercontent.com/afkT/DevUtils/master/lib/Widget/DevWidget/file/image/load_progress_bar.gif) |   |   |


## Attribute Config

- [LoadProgressBar](#LoadProgressBar) 自定义加载 ProgressBar 样式 View

- [ScanShapeView](#ScanShapeView) 自定义扫描 ( 二维码 / AR ) 效果形状 View


### <span id="LoadProgressBar">**`自定义加载 ProgressBar 样式 View - LoadProgressBar`**</span>

```java
// 内外圆环 + 数字 + 无扇形
view.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
         .setOuterRingWidth(SizeUtils.dipConvertPx(5)) // 内环宽度
         .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
         .setProgressColor(ResourceUtils.getColor(R.color.color_88)) // 进度颜色
         .setCanvasNumber(true); // 是否绘制数字

<dev.widget.ui.LoadProgressBar
   app:dev_canvasNumber="true"
   app:dev_outerRingColor="@color/khaki"
   app:dev_outerRingWidth="5.0dp"
   app:dev_progressColor="#888888"
   app:dev_progressStyle="rings" />


// 扇形 + 数字 + 无内外圆环
view.setProgressStyle(CustomProgressBar.ProgressStyle.FAN_SHAPED)
         .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)) // 进度颜色
         .setCanvasNumber(true); // 是否绘制数字

<dev.widget.ui.LoadProgressBar
   app:dev_canvasNumber="true"
   app:dev_progressColor="@color/sky_blue"
   app:dev_progressStyle="fan_shaped" />


// 扇形 + 数字 + 外圆环
view.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
         .setOuterRingWidth(SizeUtils.dipConvertPx(1)) // 内环宽度
         .setOuterRingColor(Color.RED) // 内环颜色
         .setProgressColor(ResourceUtils.getColor(R.color.mediumturquoise)) // 进度颜色
         .setNumberTextColor(Color.parseColor("#FB7D00")) // 字体颜色
         .setCanvasNumber(true); // 是否绘制数字

<dev.widget.ui.LoadProgressBar
   app:dev_canvasNumber="true"
   app:dev_numberTextColor="#FB7D00"
   app:dev_outerRingColor="@color/red"
   app:dev_outerRingWidth="1.0dp"
   app:dev_progressColor="@color/mediumturquoise"
   app:dev_progressStyle="arc_fan_shaped" />


// 单独字体
view.setProgressStyle(CustomProgressBar.ProgressStyle.NUMBER)
         .setNumberTextSize(20f) // 字体大小
         .setNumberTextColor(ResourceUtils.getColor(R.color.deeppink)); // 字体颜色

<dev.widget.ui.LoadProgressBar
   app:dev_numberTextColor="@color/deeppink"
   app:dev_numberTextSize="40sp"
   app:dev_progressStyle="number" />


// Attribute
app:dev_canvasNumber=""
app:dev_progressColor=""
app:dev_outerRingColor=""
app:dev_insideCircleWidth=""
app:dev_outerRingWidth=""
app:dev_numberTextSize=""
app:dev_numberTextColor=""
app:dev_progressStyle=""
```
