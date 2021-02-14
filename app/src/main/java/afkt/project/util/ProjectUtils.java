package afkt.project.util;

import android.graphics.Color;
import android.graphics.Rect;

import afkt.project.R;
import dev.base.DevVariable;
import dev.engine.image.GlideConfig;
import dev.utils.app.ResourceUtils;
import dev.utils.app.SizeUtils;
import dev.widget.ui.ScanShapeView;

/**
 * detail: 项目工具类
 * @author Ttt
 */
public final class ProjectUtils {

    private ProjectUtils() {
    }

    // ==============
    // = GlideUtils =
    // ==============

//    // 圆角 RequestOptions
//    private static RequestOptions sRoundOptions;
//
//    /**
//     * 获取圆角 RequestOptions
//     * @return 圆角 {@link RequestOptions}
//     */
//    public static RequestOptions getRoundConfig3() {
//        if (sRoundOptions == null) {
//            // 获取默认 RequestOptions
//            sRoundOptions = GlideUtils.defaultOptions();
//            // 设置圆角, 使用 RoundedCorners 图片不会闪烁
//            sRoundOptions.transform(new RoundedCorners(ResourceUtils.getDimensionInt(R.dimen.un_dp_3)));
//        }
//        return sRoundOptions;
//    }
//
//    /**
//     * 获取圆角 RequestOptions
//     * @return 圆角 {@link RequestOptions}
//     */
//    public static RequestOptions getRoundConfig10() {
//        // 获取默认 RequestOptions
//        RequestOptions roundOptions = GlideUtils.defaultOptions();
//        // 设置圆角, 使用 RoundedCorners 图片不会闪烁
//        return roundOptions.transform(new RoundedCorners(ResourceUtils.getDimensionInt(R.dimen.un_dp_10)));
//    }

    // ==================
    // = DevImageEngine =
    // ==================

    // GlideConfig 配置变量
    private static DevVariable<Integer, GlideConfig> sConfigVariable = new DevVariable<>();

    /**
     * 获取圆角 GlideConfig
     * @return 圆角 {@link GlideConfig}
     */
    public static GlideConfig getRoundConfig3() {
        return priGetRoundConfig(3);
    }

    /**
     * 获取圆角 GlideConfig
     * @return 圆角 {@link GlideConfig}
     */
    public static GlideConfig getRoundConfig10() {
        return priGetRoundConfig(10);
    }

    /**
     * 获取圆角 GlideConfig
     * @param roundDP 圆角 dp 值
     * @return {@link GlideConfig}
     */
    public static GlideConfig priGetRoundConfig(int roundDP) {
        GlideConfig config = sConfigVariable.getVariableValue(roundDP);
        if (config != null) return config;
        config = GlideConfig.create();
        config.setRoundedCornersRadius(SizeUtils.dipConvertPx(roundDP));
        config.setTransform(GlideConfig.TRANSFORM_ROUNDED_CORNERS);
        sConfigVariable.putVariable(roundDP, config);
        return config;
    }

    // =================
    // = ScanShapeView =
    // =================

    /**
     * 刷新类型处理
     * @param scanView  扫描 View
     * @param scanShape 扫描类型
     */
    public static void refShape(
            ScanShapeView scanView,
            ScanShapeView.Shape scanShape
    ) {
        // 设置扫描 View 类型
        scanView.setShapeType(scanShape);

        boolean isExecute = false;
        if (isExecute) {

            // ===========
            // = 处理方法 =
            // ===========

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
            scanView.getRegion(100f, 200f); // 获取纠偏 ( 偏差 ) 位置后的扫描区域
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
            scanView.setBorderWidth(SizeUtils.dipConvertPxf(2));
            // 是否绘制边框
            scanView.setDrawBorder(true);

            // =================
            // = 正方形特殊配置 =
            // =================

            // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
            scanView.setBorderToSquare(0);
            // 设置四个角落与边框共存时, 对应边框宽度
            scanView.setBorderWidthToSquare(SizeUtils.dipConvertPxf(1));
            // 设置每个角的点距离 ( 长度 )
            scanView.setTriAngleLength(SizeUtils.dipConvertPxf(20));
            // 设置特殊处理 ( 正方形边框 ) - 当 描边类型为 2 , 并且存在圆角时, 设置距离尺寸过大会出现边框圆角 + 四个角落圆角有部分透出背景情况
            scanView.setSpecialToSquare(false); // 出现的时候则设置 true, 小尺寸 (setBorderWidthToSquare, setBorderWidth) 则不会出现
            // 设置正方形扫描动画速度 ( 毫秒 )
            scanView.setLineDurationToSquare(10L);
            // 设置正方形扫描线条 Bitmap
            scanView.setBitmapToSquare(ResourceUtils.getBitmap(R.drawable.dev_scan_line));
            // 设置正方形线条动画 ( 着色 ) -> 如果不使用自己的 Bitmap(setBitmapToSquare), 则可以使用默认内置的图片, 进行着色达到想要的颜色
            scanView.setLineColorToSquare(Color.WHITE);
            // 设置正方形扫描线条向上 ( 下 ) 边距
            scanView.setLineMarginTopToSquare(0);
            // 设置正方形扫描线条向左 ( 右 ) 边距
            scanView.setLineMarginLeftToSquare(0);

            // =================
            // = 六边形特殊配置 =
            // =================

            // 设置六边形线条动画 - 线条宽度
            scanView.setLineWidthToHexagon(4f);
            // 置六边形线条动画 - 线条边距
            scanView.setLineMarginToHexagon(20f);
            // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
            scanView.setLineAnimDirection(true);
            // 设置六边形线条动画颜色
            scanView.setLineColorToHexagon(Color.WHITE);

            // ===============
            // = 环形特殊配置 =
            // ===============

            // 设置环形扫描线条 Bitmap
            scanView.setBitmapToAnnulus(ResourceUtils.getBitmap(R.drawable.dev_scan_line));
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
            scanView.setAnnulusWidths(SizeUtils.dipConvertPx(3), SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7));
            // 设置环形对应的环绘制边距 0 - 外环, 1 - 中间环, 2 - 外环
            scanView.setAnnulusMargins(SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7));
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
//                // 不需要圆角
//                scanView.setCornerEffect(null);
//                // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
//                scanView.setBorderToSquare(2);
                break;
            case Hexagon: // 六边形
                // 白色
                int hexagonColor = Color.WHITE;
                // 边框颜色
                scanView.setBorderColor(hexagonColor);
                // 设置六边形线条动画颜色
                scanView.setLineColorToHexagon(hexagonColor);
//                // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
//                scanView.setLineAnimDirection(false);
                break;
            case Annulus: // 环形
                // 设置环形线条动画 ( 着色 )
                scanView.setLineColorToAnnulus(Color.RED);
                // 设置是否需要阴影背景
                scanView.setDrawBackground(false);
//                // 设置环形扫描线条速度
//                scanView.setLineOffsetSpeedToAnnulus(6f);
                break;
        }
        // 重新绘制
        scanView.postInvalidate();
    }
}