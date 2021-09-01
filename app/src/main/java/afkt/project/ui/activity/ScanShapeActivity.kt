package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityScanShapeBinding
import afkt.project.util.ProjectUtils.refShape
import android.Manifest
import android.view.SurfaceHolder
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.permission.IPermissionEngine
import dev.utils.app.ListenerUtils
import dev.utils.app.ViewUtils
import dev.utils.app.camera.camera1.CameraAssist
import dev.utils.app.camera.camera1.CameraUtils
import dev.utils.app.camera.camera1.FlashlightUtils
import dev.utils.app.permission.PermissionUtils
import dev.utils.app.toast.ToastTintUtils
import dev.widget.ui.ScanShapeView

/**
 * detail: 自定义扫描 View ( QRCode、AR )
 * @author Ttt
 */
@Route(path = RouterPath.ScanShapeActivity_PATH)
class ScanShapeActivity : BaseActivity<ActivityScanShapeBinding>() {

    override fun baseLayoutId(): Int = R.layout.activity_scan_shape

    override fun onDestroy() {
        // 销毁处理
        binding.vidAssScanview.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        // 开始动画
        binding.vidAssScanview.startAnim()
        // 添加回调
        binding.vidAssSurface.holder?.addCallback(mHolderCallback)
    }

    override fun onPause() {
        super.onPause()
        // 停止动画
        binding.vidAssScanview.stopAnim()
        try {// 停止预览
            cameraAssist.stopPreview()
        } catch (e: Exception) {
        }
    }

    override fun initValue() {
        super.initValue()
        // 设置扫描类型
        refShape(binding.vidAssScanview, ScanShapeView.Shape.Square)
    }

    override fun initListener() {
        super.initListener()
        ListenerUtils.setOnClicks(
            this,
            binding.vidAssFlashlightIgview,
            binding.vidAssSquareIgview,
            binding.vidAssHexagonIgview,
            binding.vidAssAnnulusIgview
        )
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.vid_ass_flashlight_igview -> {
                if (!FlashlightUtils.isFlashlightEnable()) {
                    showToast(false, "暂不支持开启手电筒")
                    return
                }
                // 设置开关
                setFlashlight(!ViewUtils.isSelected(binding.vidAssFlashlightIgview))
            }
            R.id.vid_ass_square_igview -> refShape(
                binding.vidAssScanview,
                ScanShapeView.Shape.Square
            )
            R.id.vid_ass_hexagon_igview -> refShape(
                binding.vidAssScanview,
                ScanShapeView.Shape.Hexagon
            )
            R.id.vid_ass_annulus_igview -> refShape(
                binding.vidAssScanview,
                ScanShapeView.Shape.Annulus
            )
        }
    }

    // ============
    // = 摄像头预览 =
    // ============

    // 摄像头辅助类
    val cameraAssist = CameraAssist()

    private val mHolderCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            // 检查权限
            checkPermission()
        }

        override fun surfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // 关闭手电筒
            setFlashlight(false)
            try {
                cameraAssist.stopPreview()
            } catch (e: Exception) {
                DevEngine.getLog()?.eTag(TAG, e, "surfaceDestroyed")
            }
        }
    }

    /**
     * 检查摄像头权限
     */
    private fun checkPermission() {
        // 摄像头权限
        val cameraPermission = Manifest.permission.CAMERA
        // 判断是否允许权限
        if (PermissionUtils.isGranted(cameraPermission)) {
            try {
                // 打开摄像头
                val camera = CameraUtils.open()
                camera.setDisplayOrientation(90) // 设置竖屏显示
                cameraAssist.camera = camera
                // 获取预览大小
                val size = cameraAssist.cameraResolution
                // 设置预览大小, 需要这样设置, 开闪光灯才不会闪烁
                val parameters = camera.parameters
                parameters.setPreviewSize(size.width, size.height)
                camera.parameters = parameters
                // 开始预览
                cameraAssist.openDriver(binding.vidAssSurface.holder).startPreview()
//                // 默认开启自动对焦, 设置不需要自动对焦
//                cameraAssist.setAutoFocus(false)
            } catch (e: Exception) {
                DevEngine.getLog()?.eTag(TAG, e, "checkPermission startPreview")
            }
        } else {
            // 申请权限
            DevEngine.getPermission()?.request(
                this, arrayOf(cameraPermission),
                object : IPermissionEngine.Callback {
                    override fun onGranted() {
                        // 刷新处理
                        checkPermission()
                    }

                    override fun onDenied(
                        grantedList: List<String>,
                        deniedList: List<String>,
                        notFoundList: List<String>
                    ) {
                        ToastTintUtils.warning("需要摄像头权限预览")
                    }
                }
            )
        }
    }

    // ============
    // = 手电筒处理 =
    // ============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    private fun setFlashlight(open: Boolean) {
        if (open) {
            cameraAssist.setFlashlightOn()
        } else {
            cameraAssist.setFlashlightOff()
        }
        ViewUtils.setSelected(open, binding.vidAssFlashlightIgview)
    }
}