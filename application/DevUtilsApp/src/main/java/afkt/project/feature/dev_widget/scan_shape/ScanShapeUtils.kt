package afkt.project.feature.dev_widget.scan_shape

import afkt.project.R
import android.graphics.Color
import android.graphics.Rect
import dev.kotlin.utils.size.AppSize
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.widget.ui.ScanShapeView

/**
 * detail: ScanShapeView 工具类
 * @author Ttt
 */
object ScanShapeUtils {

    /**
     * 刷新类型处理
     * @param scanView  扫描 View
     * @param scanShape 扫描类型
     */
    @JvmStatic
    fun refShape(
        scanView: ScanShapeView,
        scanShape: ScanShapeView.Shape
    ) {
        // 设置扫描 View 类型
        scanView.shapeType = scanShape
        // 设置扫描区域大小 ( 扫描 View) 无关阴影背景以及整个 View 宽高
        scanView.setRegion(ScreenUtils.getScreenWidth() * 0.66F)

        val isExecute = false
        if (isExecute) {

            // 销毁方法
            scanView.destroy()
            // 启动动画
            scanView.startAnim()
            // 停止动画
            scanView.stopAnim()
            // 动画是否运行中
            scanView.isAnimRunning

            // =======
            // = 共用 =
            // =======

            // 设置扫描 View 类型
            scanView.shapeType = scanShape
            // 获取扫描 View 类型
            scanView.shapeType
            // 设置是否绘制背景
            scanView.isDrawBackground = true
            // 设置背景颜色 - ( 黑色 百分之 40 透明度 ) #66000000
            scanView.bgColor = Color.argb(102, 0, 0, 0)
            // 设置是否自动启动动画
            scanView.isAutoAnim = false
            // 是否需要绘制动画 ( 效果 )
            scanView.isDrawAnim = false
            // 设置拐角效果
            scanView.setCornerEffect(ScanShapeView.CornerEffect(10.0F))
            // 设置扫描区域大小 ( 扫描 View) 无关阴影背景以及整个 View 宽高
            scanView.setRegion(500F)
            scanView.setRegion(500F, 500F)
            scanView.setRegion(Rect(0, 0, 500, 500))
            // 获取扫描区域 距离 整个 View 的左 / 右边距 距离
            scanView.regionLeft
            // 获取扫描区域 距离 整个 View 的上 / 下边距 距离
            scanView.regionTop
            // 获取扫描区域位置信息
            scanView.region // 获取扫描区域位置信息
            scanView.getRegion(100F, 200F) // 获取纠偏 ( 偏差 ) 位置后的扫描区域
            scanView.regionParent // 获取扫描区域在 View 中的位置
            scanView.regionWidth
            scanView.regionHeight
            // 获取边框边距
            scanView.borderMargin
            // 设置扫描区域绘制边框边距
            scanView.borderMargin = 0F
            // 设置扫描区域边框颜色
            scanView.borderColor = Color.WHITE
            // 设置扫描区域边框宽度
            scanView.borderWidth = AppSize.dp2pxf(2F)
            // 是否绘制边框
            scanView.isDrawBorder = true

            // ===============
            // = 正方形特殊配置 =
            // ===============

            // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
            scanView.borderToSquare = 0
            // 设置四个角落与边框共存时, 对应边框宽度
            scanView.borderWidthToSquare = AppSize.dp2pxf(1F)
            // 设置每个角的点距离 ( 长度 )
            scanView.triAngleLength = AppSize.dp2pxf(20F)
            // 设置特殊处理 ( 正方形边框 ) - 当 描边类型为 2 , 并且存在圆角时, 设置距离尺寸过大会出现边框圆角 + 四个角落圆角有部分透出背景情况
            scanView.isSpecialToSquare =
                false // 出现的时候则设置 true, 小尺寸 (setBorderWidthToSquare, setBorderWidth) 则不会出现
            // 设置正方形扫描动画速度 ( 毫秒 )
            scanView.lineDurationToSquare = 10L
            // 设置正方形扫描线条 Bitmap
            scanView.bitmapToSquare = ResourceUtils.getBitmap(R.drawable.dev_scan_line)
            // 设置正方形线条动画 ( 着色 ) -> 如果不使用自己的 Bitmap(setBitmapToSquare), 则可以使用默认内置的图片, 进行着色达到想要的颜色
            scanView.lineColorToSquare = Color.WHITE
            // 设置正方形扫描线条向上 ( 下 ) 边距
            scanView.lineMarginTopToSquare = 0F
            // 设置正方形扫描线条向左 ( 右 ) 边距
            scanView.lineMarginLeftToSquare = 0F

            // ===============
            // = 六边形特殊配置 =
            // ===============

            // 设置六边形线条动画 - 线条宽度
            scanView.lineWidthToHexagon = 4F
            // 置六边形线条动画 - 线条边距
            scanView.lineMarginToHexagon = 20F
            // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
            scanView.isLineAnimDirection = true
            // 设置六边形线条动画颜色
            scanView.lineColorToHexagon = Color.WHITE

            // =============
            // = 环形特殊配置 =
            // =============

            // 设置环形扫描线条 Bitmap
            scanView.bitmapToAnnulus = ResourceUtils.getBitmap(R.drawable.dev_scan_line)
            // 设置环形线条动画 ( 着色 )
            scanView.lineColorToAnnulus = Color.WHITE
            // 设置环形扫描线条速度
            scanView.lineOffsetSpeedToAnnulus = 4F
            // 设置环形对应的环是否绘制 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusDraws(false, true, true)
            // 设置环形对应的环绘制颜色 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusColors(Color.BLUE, Color.RED, Color.WHITE)
            // 设置环形对应的环绘制长度 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusLengths(20, 30, 85)
            // 设置环形对应的环绘制宽度 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusWidths(
                AppSize.dp2pxf(3F),
                AppSize.dp2pxf(7F),
                AppSize.dp2pxf(7F)
            )
            // 设置环形对应的环绘制边距 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusMargins(
                AppSize.dp2pxf(7F),
                AppSize.dp2pxf(7F),
                AppSize.dp2pxf(7F)
            )
        }

        // 设置是否需要阴影背景
        scanView.isDrawBackground = true
        // 判断类型
        when (scanShape) {
            // 正方形
            ScanShapeView.Shape.Square -> {
                // 天蓝色
                val squareColor = Color.argb(255, 0, 128, 255)
                // 设置扫描线条颜色
                scanView.lineColorToSquare = squareColor
                // 边框颜色
                scanView.borderColor = squareColor
                // 设置圆角
                scanView.setCornerEffect(ScanShapeView.CornerEffect(10.0F))
//                // 不需要圆角
//                scanView.setCornerEffect(null)
//                // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
//                scanView.borderToSquare = 2
            }
            // 六边形
            ScanShapeView.Shape.Hexagon -> {
                // 白色
                val hexagonColor = Color.WHITE
                // 边框颜色
                scanView.borderColor = hexagonColor
                // 设置六边形线条动画颜色
                scanView.lineColorToHexagon = hexagonColor
//                // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
//                scanView.isLineAnimDirection = false
            }
            // 环形
            ScanShapeView.Shape.Annulus -> {
                // 设置环形线条动画 ( 着色 )
                scanView.lineColorToAnnulus = Color.RED
                // 设置是否需要阴影背景
                scanView.isDrawBackground = false
//                // 设置环形扫描线条速度
//                scanView.lineOffsetSpeedToAnnulus = 6F
            }
        }
        // 重新绘制
        scanView.postInvalidate()
    }
}