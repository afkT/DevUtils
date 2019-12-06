package afkt.project.ui.activity;

import android.Manifest;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import butterknife.BindView;
import butterknife.OnClick;
import dev.utils.app.FlashlightUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.SizeUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.camera1.CameraAssist;
import dev.utils.app.camera1.CameraUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.widget.ScanShapeView;

/**
 * detail: 二维码扫描解析
 * @author Ttt
 */
public class QRCodeScanActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_ass_surface)
    SurfaceView vid_ass_surface;
    @BindView(R.id.vid_ass_scanview)
    ScanShapeView vid_ass_scanview;
    @BindView(R.id.vid_ass_flashlight_igview)
    ImageView vid_ass_flashlight_igview;
    // 获取类型 ( 默认正方形 )
    ScanShapeView.Shape mScanShape = ScanShapeView.Shape.Square;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_shape;
    }

    @Override
    protected void onDestroy() {
        // 销毁处理
        vid_ass_scanview.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 关闭手电筒
        setFlashlight(false);
    }

    @Override
    public void initValues() {
        super.initValues();
        // 初始化 Camera
        initCamera();
        // 刷新扫描类型
        refShape();
    }

    @OnClick({R.id.vid_ass_flashlight_igview, R.id.vid_ass_square_igview,
        R.id.vid_ass_hexagon_igview, R.id.vid_ass_annulus_igview})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_ass_flashlight_igview:
                if (!FlashlightUtils.isFlashlightEnable()) {
                    showToast(false, "暂不支持开启手电筒");
                    return;
                }
                // 设置开关
                setFlashlight(!ViewUtils.isSelected(vid_ass_flashlight_igview));
                break;
            case R.id.vid_ass_square_igview:
                mScanShape = ScanShapeView.Shape.Square;
                refShape();
                break;
            case R.id.vid_ass_hexagon_igview:
                mScanShape = ScanShapeView.Shape.Hexagon;
                refShape();
                break;
            case R.id.vid_ass_annulus_igview:
                mScanShape = ScanShapeView.Shape.Annulus;
                refShape();
                break;
        }
    }

    /**
     * 刷新类型处理
     */
    private void refShape(){
        // 设置扫描 View 类型
        vid_ass_scanview.setShapeType(mScanShape);

        boolean isExecute = false;
        if (isExecute) {
            // ============
            // = 处理方法 =
            // ============

            // 销毁方法
            vid_ass_scanview.destroy();
            // 启动动画
            vid_ass_scanview.startAnim();
            // 停止动画
            vid_ass_scanview.stopAnim();
            // 动画是否运行中
            vid_ass_scanview.isAnimRunning();

            // ========
            // = 共用 =
            // ========

            // 设置扫描 View 类型
            vid_ass_scanview.setShapeType(mScanShape);
            // 获取扫描 View 类型
            vid_ass_scanview.getShapeType();
            // 设置是否绘制背景
            vid_ass_scanview.setDrawBackground(true);
            // 设置背景颜色 - ( 黑色 百分之 40 透明度 ) #66000000
            vid_ass_scanview.setBGColor(Color.argb(102, 0, 0, 0));
            // 设置是否自动启动动画
            vid_ass_scanview.setAutoAnim(false);
            // 是否需要绘制动画 ( 效果 )
            vid_ass_scanview.setDrawAnim(false);
            // 设置拐角效果
            vid_ass_scanview.setCornerEffect(new ScanShapeView.CornerEffect(10));
            // 设置扫描区域大小 ( 扫描 View) 无关阴影背景以及整个 View 宽高
            vid_ass_scanview.setRegion(700);
            vid_ass_scanview.setRegion(700, 700);
            vid_ass_scanview.setRegion(new Rect(0, 0, 700, 700));
            // 获取扫描区域 距离 整个 View 的左 / 右边距 距离
            vid_ass_scanview.getRegionLeft();
            // 获取扫描区域 距离 整个 View 的上 / 下边距 距离
            vid_ass_scanview.getRegionTop();
            // 获取扫描区域位置信息
            vid_ass_scanview.getRegion(); // 获取扫描区域位置信息
            vid_ass_scanview.getRegion(100f, 200f); // 获取纠偏 ( 偏差 ) 位置后的扫描区域
            vid_ass_scanview.getRegionParent(); // 获取扫描区域在 View 中的位置
            vid_ass_scanview.getRegionWidth();
            vid_ass_scanview.getRegionHeight();
            // 获取边框边距
            vid_ass_scanview.getBorderMargin();
            // 设置扫描区域绘制边框边距
            vid_ass_scanview.setBorderMargin(0);
            // 设置扫描区域边框颜色
            vid_ass_scanview.setBorderColor(Color.WHITE);
            // 设置扫描区域边框宽度
            vid_ass_scanview.setBorderWidth(SizeUtils.dipConvertPxf(2));
            // 是否绘制边框
            vid_ass_scanview.setDrawBorder(true);

            // ==================
            // = 正方形特殊配置 =
            // ==================

            // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
            vid_ass_scanview.setBorderToSquare(0);
            // 设置四个角落与边框共存时, 对应边框宽度
            vid_ass_scanview.setBorderWidthToSquare(SizeUtils.dipConvertPxf(1));
            // 设置每个角的点距离 ( 长度 )
            vid_ass_scanview.setTriAngleLength(SizeUtils.dipConvertPxf(20));
            // 设置特殊处理 ( 正方形边框 ) - 当 描边类型为 2 , 并且存在圆角时, 设置距离尺寸过大会出现边框圆角 + 四个角落圆角有部分透出背景情况
            vid_ass_scanview.setSpecialToSquare(false); // 出现的时候则设置 true, 小尺寸 (setBorderWidthToSquare, setBorderWidth) 则不会出现
            // 设置正方形扫描动画速度 ( 毫秒 )
            vid_ass_scanview.setLineDurationToSquare(10l);
            // 设置正方形扫描线条 Bitmap
            vid_ass_scanview.setBitmapToSquare(ResourceUtils.getBitmap(R.drawable.line_scan));
            // 设置正方形线条动画 ( 着色 ) -> 如果不使用自己的 Bitmap(setBitmapToSquare), 则可以使用默认内置的图片, 进行着色达到想要的颜色
            vid_ass_scanview.setLineColorToSquare(Color.WHITE);
            // 设置正方形扫描线条向上 ( 下 ) 边距
            vid_ass_scanview.setLineMarginTopToSquare(0);
            // 设置正方形扫描线条向左 ( 右 ) 边距
            vid_ass_scanview.setLineMarginLeftToSquare(0);

            // ==================
            // = 六边形特殊配置 =
            // ==================

            // 设置六边形线条动画 - 线条宽度
            vid_ass_scanview.setLineWidthToHexagon(4f);
            // 置六边形线条动画 - 线条边距
            vid_ass_scanview.setLineMarginToHexagon(20f);
            // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
            vid_ass_scanview.setLineAnimDirection(true);
            // 设置六边形线条动画颜色
            vid_ass_scanview.setLineColorToHexagon(Color.WHITE);

            // ================
            // = 环形特殊配置 =
            // ================

            // 设置环形扫描线条 Bitmap
            vid_ass_scanview.setBitmapToAnnulus(ResourceUtils.getBitmap(R.drawable.line_scan));
            // 设置环形线条动画 ( 着色 )
            vid_ass_scanview.setLineColorToAnnulus(Color.WHITE);
            // 设置环形扫描线条速度
            vid_ass_scanview.setLineOffsetSpeedToAnnulus(4);
            // 设置环形对应的环是否绘制 0 - 外环, 1 - 中间环, 2 - 外环
            vid_ass_scanview.setAnnulusDraws(false, true, true);
            // 设置环形对应的环绘制颜色 0 - 外环, 1 - 中间环, 2 - 外环
            vid_ass_scanview.setAnnulusColors(Color.BLUE, Color.RED, Color.WHITE);
            // 设置环形对应的环绘制长度 0 - 外环, 1 - 中间环, 2 - 外环
            vid_ass_scanview.setAnnulusLengths(20, 30, 85);
            // 设置环形对应的环绘制宽度 0 - 外环, 1 - 中间环, 2 - 外环
            vid_ass_scanview.setAnnulusWidths(SizeUtils.dipConvertPx(3), SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7));
            // 设置环形对应的环绘制边距 0 - 外环, 1 - 中间环, 2 - 外环
            vid_ass_scanview.setAnnulusMargins(SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7), SizeUtils.dipConvertPx(7));
        }

        // 设置是否需要阴影背景
        vid_ass_scanview.setDrawBackground(true);

        // 判断类型
        switch (mScanShape){
            case Square: // 正方形
                // 天蓝色
                int squareColor = Color.argb(255, 0, 128, 255);
                // 设置扫描线条颜色
                vid_ass_scanview.setLineColorToSquare(squareColor);
                // 边框颜色
                vid_ass_scanview.setBorderColor(squareColor);
                // 设置圆角
                vid_ass_scanview.setCornerEffect(new ScanShapeView.CornerEffect(10));
//                // 不需要圆角
//                vid_ass_scanview.setCornerEffect(null);
//                // 设置 正方形描边 ( 边框 ), 类型 0 = 单独四个角落, 1 = 单独边框, 2 = 全部
//                vid_ass_scanview.setBorderToSquare(2);
                break;
            case Hexagon: // 六边形
                // 白色
                int hexagonColor = Color.WHITE;
                // 边框颜色
                vid_ass_scanview.setBorderColor(hexagonColor);
                // 设置六边形线条动画颜色
                vid_ass_scanview.setLineColorToHexagon(hexagonColor);
//                // 设置六边形线条动画方向 true = 从左到右, false = 从右到左
//                vid_ass_scanview.setLineAnimDirection(false);
                break;
            case Annulus: // 环形
                // 设置环形线条动画 ( 着色 )
                vid_ass_scanview.setLineColorToAnnulus(Color.RED);
                // 设置是否需要阴影背景
                vid_ass_scanview.setDrawBackground(false);
//                // 设置环形扫描线条速度
//                vid_ass_scanview.setLineOffsetSpeedToAnnulus(6f);
                break;
        }
        // 重新绘制
        vid_ass_scanview.postInvalidate();
    }

    // ==============
    // = 摄像头预览 =
    // ==============

    // 摄像头辅助类
    CameraAssist cameraAssist = new CameraAssist();

    /**
     * 初始化 Camera
     */
    private void initCamera() {
        // 添加回调
        vid_ass_surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 检查权限
                checkPermission();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                try {
                    if (cameraAssist != null){
                        cameraAssist.stopPreview();
                    }
                } catch (Exception e){
                    DevLogger.eTag(mTag, e, "surfaceDestroyed");
                }
                // 关闭手电筒
                setFlashlight(false);
            }
        });
    }

    /**
     * 检查摄像头权限
     */
    private void checkPermission(){
        // 摄像头权限
        String cameraPermission = Manifest.permission.CAMERA;
        // 判断是否允许权限
        if (PermissionUtils.isGranted(cameraPermission)){
            try {
                // 打开摄像头
                Camera camera = CameraUtils.open();
                camera.setDisplayOrientation(90); // 设置竖屏显示
                cameraAssist.setCamera(camera);
                // 获取预览大小
                final Camera.Size size = cameraAssist.getCameraResolution();
                // 设置预览大小, 需要这样设置, 开闪光灯才不会闪烁
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                // 开始预览
                cameraAssist.openDriver(vid_ass_surface.getHolder()).startPreview();
                // 默认开启自动对焦, 设置不需要自动对焦
                cameraAssist.setAutoFocus(false);
            } catch (Exception e){
                DevLogger.eTag(mTag, e, "checkPermission - startPreview");
            }
        } else {
            ToastTintUtils.warning("需要摄像头权限预览");
            // 申请权限
            PermissionUtils.permission(cameraPermission).callBack(new PermissionUtils.PermissionCallBack() {
                @Override
                public void onGranted() {
                    // 刷新处理
                    checkPermission();
                }

                @Override
                public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                    // 再次申请权限
                    checkPermission();
                }
            }).request(this);
        }
    }

    // ==============
    // = 手电筒处理 =
    // ==============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    private void setFlashlight(boolean open) {
        if (open) {
            FlashlightUtils.getInstance().setFlashlightOn(cameraAssist.getCamera());
        } else {
            FlashlightUtils.getInstance().setFlashlightOff(cameraAssist.getCamera());
        }
        ViewUtils.setSelected(open, vid_ass_flashlight_igview);
    }
}