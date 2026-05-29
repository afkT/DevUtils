package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineBarCodeBinding
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.barcode.listener.BarCodeEncodeCallback
import dev.engine.core.barcode.BarCodeData
import dev.engine.core.barcode.BarCodeResult
import dev.engine.extensions.barcode.barcode_decodeBarCode
import dev.engine.extensions.barcode.barcode_encodeBarCode
import dev.engine.extensions.toast.toast_showShort
import java.lang.ref.WeakReference

/**
 * detail: BarCode Engine 条形码、二维码处理
 * @author Ttt
 */
class BarCodeFragment : AppFragment<FragmentDevEngineBarCodeBinding, BarCodeViewModel>(
    R.layout.fragment_dev_engine_bar_code, BR.viewModel
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(binding.vidIv)
    }
}

/**
 * detail: BarCode Engine 条形码、二维码处理 ViewModel
 * @author Ttt
 */
class BarCodeViewModel : AppViewModel() {

    private companion object {

        const val CONTENT = "https://github.com/afkT/DevUtils"

        // 二维码大小 ( px )
        const val SIZE = 400
    }

    // 二维码承载控件 ( 弱引用持有 )
    private var imageViewRef: WeakReference<ImageView>? = null

    // 最近一次生成的二维码
    private var lastBitmap: Bitmap? = null

    /**
     * 初始化二维码承载控件
     * @param imageView Image View
     */
    fun initialize(imageView: ImageView) {
        imageViewRef = WeakReference(imageView)
    }

    val clickEncode = View.OnClickListener {
        BarCodeData[CONTENT, SIZE].barcode_encodeBarCode(
            callback = BarCodeEncodeCallback { success, bitmap, error ->
                if (success && bitmap != null) {
                    lastBitmap = bitmap
                    imageViewRef?.get()?.setImageBitmap(bitmap)
                    toast_showShort(text = "二维码生成成功")
                } else {
                    toast_showShort(text = "二维码生成失败: ${error?.message}")
                }
            }
        )
    }

    val clickDecode = View.OnClickListener {
        val bitmap = lastBitmap
        if (bitmap == null) {
            toast_showShort(text = "请先生成二维码")
            return@OnClickListener
        }
        bitmap.barcode_decodeBarCode(
            callback = object : BarCodeDecodeCallback<BarCodeResult> {
                override fun onResult(
                    success: Boolean,
                    result: BarCodeResult?,
                    error: Throwable?
                ) {
                    toast_showShort(
                        text = if (success) {
                            "解析结果: ${result?.getResultData()}"
                        } else {
                            "二维码解析失败: ${error?.message}"
                        }
                    )
                }
            }
        )
    }
}