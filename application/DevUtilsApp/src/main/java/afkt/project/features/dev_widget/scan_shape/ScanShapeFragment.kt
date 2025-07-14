package afkt.project.features.dev_widget.scan_shape

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevWidgetScanShapeBinding
import android.Manifest
import android.view.SurfaceHolder
import androidx.databinding.ObservableBoolean
import dev.engine.permission.IPermissionEngine
import dev.expand.engine.log.log_eTag
import dev.expand.engine.permission.permission_isGranted
import dev.expand.engine.permission.permission_request
import dev.expand.engine.toast.toast_showShort
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import dev.simple.app.base.asFragment
import dev.utils.app.FlashlightUtils
import dev.utils.app.camera.camera1.CameraAssist
import dev.utils.app.camera.camera1.CameraUtils
import dev.utils.app.toast.ToastTintUtils
import dev.widget.ui.ScanShapeView

/**
 * detail: 自定义扫描 View ( QRCode、AR )
 * @author Ttt
 */
class ScanShapeFragment : AppFragment<FragmentDevWidgetScanShapeBinding, ScanShapeViewModel>(
    R.layout.fragment_dev_widget_scan_shape, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ScanShapeFragment> {
            binding.setVariable(BR.scanShape, binding.vidSsv)
            // 设置扫描类型
            ScanShapeUtils.refreshShape(binding.vidSsv, ScanShapeView.Shape.Annulus)
        }
    }
) {
    override fun onDestroyView() {
        // 销毁处理
        binding.vidSsv.destroy()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        // 刷新手电筒状态
        viewModel.refreshFlashlightOn()
        // 开始动画
        binding.vidSsv.startAnim()
        // 添加回调
        binding.vidSurface.holder?.addCallback(mHolderCallback)
    }

    override fun onPause() {
        super.onPause()
        try {
            // 停止动画
            binding.vidSsv.stopAnim()
            // 停止预览
            viewModel.cameraAssist.stopPreview()
        } catch (_: Exception) {
        }
    }

    // ============
    // = 摄像头预览 =
    // ============

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
            try {
                // 关闭手电筒
                viewModel.closeFlashlight()
                // 停止预览
                viewModel.cameraAssist.stopPreview()
            } catch (e: Exception) {
                TAG.log_eTag(
                    throwable = e,
                    message = "surfaceDestroyed"
                )
            }
        }
    }

    /**
     * 检查摄像头权限
     */
    private fun checkPermission() {
        val cameraAssist = viewModel.cameraAssist
        // 摄像头权限
        val cameraPermission = Manifest.permission.CAMERA
        // 判断是否允许权限
        val isGranted = context?.permission_isGranted(
            permissions = arrayOf(cameraPermission)
        )
        if (isGranted == true) {
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
                cameraAssist.openDriver(binding.vidSurface.holder).startPreview()
//                // 默认开启自动对焦, 设置不需要自动对焦
//                cameraAssist.setAutoFocus(false)
            } catch (e: Exception) {
                TAG.log_eTag(
                    throwable = e,
                    message = "checkPermission startPreview"
                )
            }
        } else {
            activity?.permission_request(
                permissions = arrayOf(cameraPermission),
                callback = object : IPermissionEngine.Callback {
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
}

class ScanShapeViewModel : AppViewModel() {

    // 摄像头辅助类
    val cameraAssist = CameraAssist()

    // 手电动开关状态
    val flashlightStatus = ObservableBoolean(false)

    // ==========
    // = 点击事件 =
    // ==========

    val clickFlashlight: () -> Unit = {
        if (FlashlightUtils.isFlashlightEnable()) {
            toggleFlashlight()
        } else {
            toast_showShort(text = "暂不支持开启手电筒")
        }
    }

    val clickSquare: (ScanShapeView?) -> Unit = {
        it.hiIfNotNull { view ->
            ScanShapeUtils.refreshShape(
                view, ScanShapeView.Shape.Square
            )
        }
    }

    val clickHexagon: (ScanShapeView?) -> Unit = {
        it.hiIfNotNull { view ->
            ScanShapeUtils.refreshShape(
                view, ScanShapeView.Shape.Hexagon
            )
        }
    }

    val clickAnnulus: (ScanShapeView?) -> Unit = {
        it.hiIfNotNull { view ->
            ScanShapeUtils.refreshShape(
                view, ScanShapeView.Shape.Annulus
            )
        }
    }

    // ============
    // = 手电筒处理 =
    // ============

    /**
     * 切换手电筒开关
     */
    fun toggleFlashlight() {
        setFlashlight(!flashlightStatus.get())
    }

    /**
     * 打开手电筒
     */
    fun openFlashlight() {
        setFlashlight(true)
    }

    /**
     * 关闭手电筒
     */
    fun closeFlashlight() {
        setFlashlight(false)
    }

    /**
     * 刷新手电筒状态
     */
    fun refreshFlashlightOn() {
        flashlightStatus.set(cameraAssist.isFlashlightOn)
    }

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
        flashlightStatus.set(open)
    }
}