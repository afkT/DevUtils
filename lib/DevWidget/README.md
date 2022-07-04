
# About

> DevWidget 主要是常用 UI 效果、特殊功能需求展示的自定义 View UI 库，持续增加开发中常用的 View 效果、Adapter 模式设计复用样式等，提升开发效率，便于快捷引用快速开发

## Gradle

```gradle
// AndroidX
implementation 'io.github.afkt:DevWidgetX:1.1.9'
```

## README

- 效果可运行 DevUtils 项目点击 DevWidget UI 库查看

- 该库依赖 [DevApp](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md) 开发，需引用 DevApp 库

- [README - API](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README_API.md)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/CHANGELOG.md)


## Preview

| WrapView | LineTextView | SignView |
|:--:|:--:|:--:|
| ![][WrapView_jpg] | ![][LineTextView_jpg] | ![][SignView_jpg] |
| ScanShapeView - Square | ScanShapeView - Hexagon | ScanShapeView - Annulus |
| ![][ScanShapeView_Square_gif] | ![][ScanShapeView_Hexagon_gif] | ![][ScanShapeView_Annulus_gif] |
| LoadProgressBar | FlowLikeView | CornerLabelView |
| ![][LoadProgressBar_gif] | ![][FlowLikeView_gif] | ![][CornerLabelView_jpg] |
| ViewAssist | ViewAssist - Loading | ViewAssist - Loading |
| ![][ViewAssist_jpg] | ![][ViewAssist_Loading_gif] | ![][ViewAssist_Loading2_gif] |
| ViewAssist - Error | ViewAssist - Empty | ViewAssist - Custom |
| ![][ViewAssist_Error_gif] | ![][ViewAssist_Empty_gif] | ![][ViewAssist_Custom_gif] |


## Catalog

- [LoadProgressBar](#LoadProgressBar) 自定义加载 ProgressBar 样式 View

- [ScanShapeView](#ScanShapeView) 自定义扫描 ( 二维码 / AR ) 效果形状 View

- [FlowLikeView](#FlowLikeView) 自定义点赞效果 View


## Attribute Config

### <span id="LoadProgressBar">**`自定义加载 ProgressBar 样式 View`** [LoadProgressBar.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/LoadProgressBar.java)</span>

```java
// 内外圆环 + 数字 + 无扇形
view.setProgressStyle(LoadProgressBar.ProgressStyle.RINGS)
         .setOuterRingWidth(SizeUtils.dp2px(5)) // 内环宽度
         .setOuterRingColor(ResourceUtils.getColor(R.color.khaki)) // 内环颜色
         .setProgressColor(ResourceUtils.getColor(R.color.color_88)) // 进度颜色
         .setCanvasNumber(true); // 是否绘制数字

<dev.widget.ui.LoadProgressBar
   app:dev_canvasNumber="true"
   app:dev_outerRingColor="@color/khaki"
   app:dev_outerRingWidth="5.0dp"
   app:dev_progressColor="#888888"
   app:dev_progressStyle="rings"/>


// 扇形 + 数字 + 无内外圆环
view.setProgressStyle(CustomProgressBar.ProgressStyle.FAN_SHAPED)
         .setProgressColor(ResourceUtils.getColor(R.color.sky_blue)) // 进度颜色
         .setCanvasNumber(true); // 是否绘制数字

<dev.widget.ui.LoadProgressBar
   app:dev_canvasNumber="true"
   app:dev_progressColor="@color/sky_blue"
   app:dev_progressStyle="fanShaped"/>


// 扇形 + 数字 + 外圆环
view.setProgressStyle(LoadProgressBar.ProgressStyle.ARC_FAN_SHAPED)
         .setOuterRingWidth(SizeUtils.dp2px(1)) // 内环宽度
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
   app:dev_progressStyle="arcFanShaped"/>


// 单独字体
view.setProgressStyle(CustomProgressBar.ProgressStyle.NUMBER)
         .setNumberTextSize(20F) // 字体大小
         .setNumberTextColor(ResourceUtils.getColor(R.color.deeppink)); // 字体颜色

<dev.widget.ui.LoadProgressBar
   app:dev_numberTextColor="@color/deeppink"
   app:dev_numberTextSize="40.0sp"
   app:dev_progressStyle="number"/>


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


### <span id="ScanShapeView">**`自定义扫描 ( 二维码 / AR ) 效果形状 View`** [ScanShapeView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/ScanShapeView.java)</span>

```java
/**
 * 刷新类型处理
 * @param scanView  扫描 View
 * @param scanShape 扫描类型
 */
public static void refShape(ScanShapeView scanView, ScanShapeView.Shape scanShape) {
    // 设置扫描 View 类型
    scanView.setShapeType(scanShape);

    boolean isExecute = false;
    if (isExecute) {

        // 销毁方法
        scanView.destroy();
        // 启动动画
        scanView.startAnim();
        // 停止动画
        scanView.stopAnim();
        // 动画是否运行中
        scanView.isAnimRunning();

        // =======
        // = 共用 =
        // =======

        // 设置扫描 View 类型
        scanView.setShapeType(scanShape);
        // 获取扫描 View 类型
        scanView.getShapeType();
        // 设置是否绘制背景
        scanView.setDrawBackground(true);
        // 设置背景颜色 - ( 黑色 百分之 40 透明度 ) #66000000
        scanView.setBGColor(Color.argb(102, 0, 0, 0));
        // 设置是否自动启动动画
        scanView.setAutoAnim(false);
        // 是否需要绘制动画 ( 效果 )
        scanView.setDrawAnim(false);
        // 设置拐角效果
        scanView.setCornerEffect(new ScanShapeView.CornerEffect(10));
        // 设置扫描区域大小 ( 扫描 View) 无关阴影背景以及整个 View 宽高
        scanView.setRegion(700);
        scanView.setRegion(700, 700);
        scanView.setRegion(new Rect(0, 0, 700, 700));
        // 获取扫描区域 距离 整个 View 的左 / 右边距 距离
        scanView.getRegionLeft();
        // 获取扫描区域 距离 整个 View 的上 / 下边距 距离
        scanView.getRegionTop();
        // 获取扫描区域位置信息
        scanView.getRegion(); // 获取扫描区域位置信息
        scanView.getRegion(100F, 200F); // 获取纠偏 ( 偏差 ) 位置后的扫描区域
        scanView.getRegionParent(); // 获取扫描区域在 View 中的位置
        scanView.getRegionWidth();
        scanView.getRegionHeight();
        // 获取边框边距
        scanView.getBorderMargin();
        // 设置扫描区域绘制边框边距
        scanView.setBorderMargin(0);
        // 设置扫描区域边框颜色
        scanView.setBorderColor(Color.WHITE);
        // 设置扫描区域边框宽度
        scanView.setBorderWidth(SizeUtils.dp2pxf(2));
        // 是否绘制边框
        scanView.setDrawBorder(true);

        // ===============
        // = 正方形特殊配置 =
        // ===============

        // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
        scanView.setBorderToSquare(0);
        // 设置四个角落与边框共存时, 对应边框宽度
        scanView.setBorderWidthToSquare(SizeUtils.dp2pxf(1));
        // 设置每个角的点距离 ( 长度 )
        scanView.setTriAngleLength(SizeUtils.dp2pxf(20));
        // 设置特殊处理 ( 正方形边框 ) - 当 描边类型为 2 , 并且存在圆角时, 设置距离尺寸过大会出现边框圆角 + 四个角落圆角有部分透出背景情况
        scanView.setSpecialToSquare(false); // 出现的时候则设置 true, 小尺寸 (setBorderWidthToSquare, setBorderWidth) 则不会出现
        // 设置正方形扫描动画速度 ( 毫秒 )
        scanView.setLineDurationToSquare(10L);
        // 设置正方形扫描线条 Bitmap
        scanView.setBitmapToSquare(ResourceUtils.getBitmap(R.drawable.line_scan));
        // 设置正方形线条动画 ( 着色 ) -> 如果不使用自己的 Bitmap(setBitmapToSquare), 则可以使用默认内置的图片, 进行着色达到想要的颜色
        scanView.setLineColorToSquare(Color.WHITE);
        // 设置正方形扫描线条向上 ( 下 ) 边距
        scanView.setLineMarginTopToSquare(0);
        // 设置正方形扫描线条向左 ( 右 ) 边距
        scanView.setLineMarginLeftToSquare(0);

        // ===============
        // = 六边形特殊配置 =
        // ===============

        // 设置六边形线条动画 - 线条宽度
        scanView.setLineWidthToHexagon(4F);
        // 置六边形线条动画 - 线条边距
        scanView.setLineMarginToHexagon(20F);
        // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
        scanView.setLineAnimDirection(true);
        // 设置六边形线条动画颜色
        scanView.setLineColorToHexagon(Color.WHITE);

        // =============
        // = 环形特殊配置 =
        // =============

        // 设置环形扫描线条 Bitmap
        scanView.setBitmapToAnnulus(ResourceUtils.getBitmap(R.drawable.line_scan));
        // 设置环形线条动画 ( 着色 )
        scanView.setLineColorToAnnulus(Color.WHITE);
        // 设置环形扫描线条速度
        scanView.setLineOffsetSpeedToAnnulus(4);
        // 设置环形对应的环是否绘制 0 - 外环, 1 - 中间环, 2 - 外环
        scanView.setAnnulusDraws(false, true, true);
        // 设置环形对应的环绘制颜色 0 - 外环, 1 - 中间环, 2 - 外环
        scanView.setAnnulusColors(Color.BLUE, Color.RED, Color.WHITE);
        // 设置环形对应的环绘制长度 0 - 外环, 1 - 中间环, 2 - 外环
        scanView.setAnnulusLengths(20, 30, 85);
        // 设置环形对应的环绘制宽度 0 - 外环, 1 - 中间环, 2 - 外环
        scanView.setAnnulusWidths(SizeUtils.dp2px(3), SizeUtils.dp2px(7), SizeUtils.dp2px(7));
        // 设置环形对应的环绘制边距 0 - 外环, 1 - 中间环, 2 - 外环
        scanView.setAnnulusMargins(SizeUtils.dp2px(7), SizeUtils.dp2px(7), SizeUtils.dp2px(7));
    }

    // 设置是否需要阴影背景
    scanView.setDrawBackground(true);

    // 判断类型
    switch (scanShape) {
        case Square: // 正方形
            // 天蓝色
            int squareColor = Color.argb(255, 0, 128, 255);
            // 设置扫描线条颜色
            scanView.setLineColorToSquare(squareColor);
            // 边框颜色
            scanView.setBorderColor(squareColor);
            // 设置圆角
            scanView.setCornerEffect(new ScanShapeView.CornerEffect(10));
//            // 不需要圆角
//            scanView.setCornerEffect(null);
//            // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
//            scanView.setBorderToSquare(2);
            break;
        case Hexagon: // 六边形
            // 白色
            int hexagonColor = Color.WHITE;
            // 边框颜色
            scanView.setBorderColor(hexagonColor);
            // 设置六边形线条动画颜色
            scanView.setLineColorToHexagon(hexagonColor);
//            // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
//            scanView.setLineAnimDirection(false);
            break;
        case Annulus: // 环形
            // 设置环形线条动画 ( 着色 )
            scanView.setLineColorToAnnulus(Color.RED);
            // 设置是否需要阴影背景
            scanView.setDrawBackground(false);
//            // 设置环形扫描线条速度
//            scanView.setLineOffsetSpeedToAnnulus(6F);
            break;
    }
    // 重新绘制
    scanView.postInvalidate();
}
```

### <span id="FlowLikeView">**`自定义点赞效果 View`** [FlowLikeView.java](https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/src/main/java/dev/widget/ui/FlowLikeView.java)</span>

```java
app:dev_animDuration="2000"
app:dev_iconHeight="30.0dp"
app:dev_iconWidth="30.0dp"

// 设置动画时间
flowLikeView.setAnimDuration(2000);
// 设置图标宽度
flowLikeView.setIconWidth(SizeUtils.dp2px(30));
// 设置图标高度
flowLikeView.setIconHeight(SizeUtils.dp2px(30));

// 设置漂浮图标
List<Drawable> lists = new ArrayList<>();
lists.add(ResourceUtils.getDrawable(R.drawable.ic_live_brow_1));
lists.add(ResourceUtils.getDrawable(R.drawable.ic_live_brow_2));
lists.add(ResourceUtils.getDrawable(R.drawable.ic_live_brow_3));
lists.add(ResourceUtils.getDrawable(R.drawable.ic_live_brow_4));
lists.add(ResourceUtils.getDrawable(R.drawable.ic_live_brow_5));
flowLikeView.setDrawables(lists);

// 设置漂浮图标
flowLikeView.setDrawablesById(R.drawable.ic_live_brow_1, R.drawable.ic_live_brow_2,
    R.drawable.ic_live_brow_3, R.drawable.ic_live_brow_4, R.drawable.ic_live_brow_5);

// 点赞操作
flowLikeView.like();
```





[WrapView_jpg]: https://github.com/afkT/DevUtils/raw/master/art/wrap_view.jpg
[LineTextView_jpg]: https://github.com/afkT/DevUtils/raw/master/art/line_text_view.jpg
[SignView_jpg]: https://github.com/afkT/DevUtils/raw/master/art/sign_view.jpg
[ScanShapeView_Square_gif]: https://github.com/afkT/DevUtils/raw/master/art/scan_shape_view_square.gif
[ScanShapeView_Hexagon_gif]: https://github.com/afkT/DevUtils/raw/master/art/scan_shape_view_hexagon.gif
[ScanShapeView_Annulus_gif]: https://github.com/afkT/DevUtils/raw/master/art/scan_shape_view_annulus.gif
[LoadProgressBar_gif]: https://github.com/afkT/DevUtils/raw/master/art/load_progress_bar.gif
[FlowLikeView_gif]: https://github.com/afkT/DevUtils/raw/master/art/flow_like_view.gif
[CornerLabelView_jpg]: https://github.com/afkT/DevUtils/raw/master/art/corner_label_view.jpg
[ViewAssist_jpg]: https://github.com/afkT/DevUtils/raw/master/art/view_assist.jpg
[ViewAssist_Loading_gif]: https://github.com/afkT/DevUtils/raw/master/art/view_assist_loading.gif
[ViewAssist_Loading2_gif]: https://github.com/afkT/DevUtils/raw/master/art/view_assist_loading2.gif
[ViewAssist_Error_gif]: https://github.com/afkT/DevUtils/raw/master/art/view_assist_error.gif
[ViewAssist_Empty_gif]: https://github.com/afkT/DevUtils/raw/master/art/view_assist_empty.gif
[ViewAssist_Custom_gif]: https://github.com/afkT/DevUtils/raw/master/art/view_assist_custom.gif