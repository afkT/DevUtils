package afkt.project.ui.activity;

import android.Manifest;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityScanShapeBinding;
import afkt.project.util.ProjectUtils;
import dev.engine.log.DevLogEngine;
import dev.utils.app.FlashlightUtils;
import dev.utils.app.ListenerUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.camera1.CameraAssist;
import dev.utils.app.camera1.CameraUtils;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.widget.ui.ScanShapeView;

/**
 * detail: 自定义扫描 View ( QRCode、AR )
 * @author Ttt
 */
public class ScanShapeActivity
        extends BaseActivity<ActivityScanShapeBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_scan_shape;
    }

    @Override
    protected void onDestroy() {
        // 销毁处理
        binding.vidAssScanview.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // 开始动画
            binding.vidAssScanview.startAnim();
            // 添加回调
            binding.vidAssSurface.getHolder().addCallback(mHolderCallback);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            // 停止动画
            binding.vidAssScanview.stopAnim();
            // 停止预览
            cameraAssist.stopPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void initValue() {
        super.initValue();
        // 设置扫描类型
        ProjectUtils.refShape(binding.vidAssScanview, ScanShapeView.Shape.Square);
    }

    @Override
    public void initListener() {
        super.initListener();

        ListenerUtils.setOnClicks(this, binding.vidAssFlashlightIgview,
                binding.vidAssSquareIgview, binding.vidAssHexagonIgview, binding.vidAssAnnulusIgview);
    }

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
                setFlashlight(!ViewUtils.isSelected(binding.vidAssFlashlightIgview));
                break;
            case R.id.vid_ass_square_igview:
                ProjectUtils.refShape(binding.vidAssScanview, ScanShapeView.Shape.Square);
                break;
            case R.id.vid_ass_hexagon_igview:
                ProjectUtils.refShape(binding.vidAssScanview, ScanShapeView.Shape.Hexagon);
                break;
            case R.id.vid_ass_annulus_igview:
                ProjectUtils.refShape(binding.vidAssScanview, ScanShapeView.Shape.Annulus);
                break;
        }
    }

    // =============
    // = 摄像头预览 =
    // =============

    // 摄像头辅助类
    CameraAssist cameraAssist = new CameraAssist();

    private final SurfaceHolder.Callback mHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 检查权限
            checkPermission();
        }

        @Override
        public void surfaceChanged(
                SurfaceHolder holder,
                int format,
                int width,
                int height
        ) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 关闭手电筒
            setFlashlight(false);
            try {
                // 停止预览
                if (cameraAssist != null) {
                    cameraAssist.stopPreview();
                }
            } catch (Exception e) {
                DevLogEngine.getEngine().eTag(TAG, e, "surfaceDestroyed");
            }
        }
    };

    /**
     * 检查摄像头权限
     */
    private void checkPermission() {
        // 摄像头权限
        String cameraPermission = Manifest.permission.CAMERA;
        // 判断是否允许权限
        if (PermissionUtils.isGranted(cameraPermission)) {
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
                cameraAssist.openDriver(binding.vidAssSurface.getHolder()).startPreview();
//                // 默认开启自动对焦, 设置不需要自动对焦
//                cameraAssist.setAutoFocus(false);
            } catch (Exception e) {
                DevLogEngine.getEngine().eTag(TAG, e, "checkPermission startPreview");
            }
        } else {
            ToastTintUtils.warning("需要摄像头权限预览");
            // 申请权限
            PermissionUtils.permission(cameraPermission).callback(new PermissionUtils.PermissionCallback() {
                @Override
                public void onGranted() {
                    // 刷新处理
                    checkPermission();
                }

                @Override
                public void onDenied(
                        List<String> grantedList,
                        List<String> deniedList,
                        List<String> notFoundList
                ) {
                    // 再次申请权限
                    checkPermission();
                }
            }).request(this);
        }
    }

    // =============
    // = 手电筒处理 =
    // =============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    private void setFlashlight(boolean open) {
        if (open) {
            cameraAssist.setFlashlightOn();
        } else {
            cameraAssist.setFlashlightOff();
        }
        ViewUtils.setSelected(open, binding.vidAssFlashlightIgview);
    }
}