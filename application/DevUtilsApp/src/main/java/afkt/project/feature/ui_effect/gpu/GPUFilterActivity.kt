package afkt.project.feature.ui_effect.gpu

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityGpuFilterBinding
import afkt.project.feature.ui_effect.gpu.GPUFilterUtils.getFilterBitmap
import afkt.project.feature.ui_effect.gpu.bean.FilterItem.Companion.createFilterForType
import afkt.project.model.item.RouterPath
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.media.MediaConfig
import dev.kotlin.engine.log.log_eTag
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ScreenUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.image.ImageUtils
import dev.utils.common.ScaleUtils

/**
 * detail: GPU 滤镜效果
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.GPUFilterActivity_PATH)
class GPUFilterActivity : BaseActivity<ActivityGpuFilterBinding>() {

    // 适配器
    lateinit var gpuFilterAdapter: GPUFilterAdapter

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

    companion object {
        // 滤镜线程
        var filterThread: Runnable? = null
    }

    override fun baseLayoutId(): Int = R.layout.activity_gpu_filter

    override fun onDestroy() {
        super.onDestroy()
        filterThread = null
    }

    override fun initValue() {
        super.initValue()

        // 设置滤镜线程
        filterThread = Runnable { setFilter() }

        // 设置适配器
        binding.vidGallery.adapter = GPUFilterAdapter(this).also {
            gpuFilterAdapter = it
        }
        binding.vidGallery.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    gpuFilterAdapter.setSelectPosition(position)
                    // 延迟一会进行滤镜
                    HandlerUtils.removeRunnable(filterThread)
                    HandlerUtils.postRunnable(filterThread, 500)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        // 默认选中第一个
        binding.vidGallery.setSelection(0)
    }

    override fun initListener() {
        super.initListener()
        binding.vidSelectBtn.setOnClickListener {
            // 初始化图片配置
            val config = MediaConfig()
                .setCompress(false).setMaxSelectNum(1).setCrop(false)
                .setMimeType(MediaConfig.MimeType.ofImage())
                .setCamera(true).setGif(false)
            // 打开图片选择器
            DevEngine.getMedia()?.openGallery(mActivity, config)
        }
    }

    // ==========
    // = 图片回传 =
    // ==========

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)
        // 判断是否属于图片选择
        if (resultCode == RESULT_OK && intent != null) {
            // 获取图片地址
            val imgUri = DevEngine.getMedia()?.getSingleSelectorUri(intent, true)
            // 获取图片 Bitmap
            selectBitmap = ImageUtils.decodeStream(ResourceUtils.openInputStream(imgUri))
            // 设置图片滤镜
            setFilter()
        }
    }

    // ==========
    // = 滤镜处理 =
    // ==========

    /**
     * 设置滤镜效果
     */
    private fun setFilter() {
        try {
            if (selectBitmap == null) return
            // 获取选中的滤镜
            val position = binding.vidGallery.selectedItemPosition
            // 获取滤镜 Item
            val filterItem = gpuFilterAdapter.getItem(position)
            // 设置滤镜效果
            val bitmapFilter = getFilterBitmap(
                this, selectBitmap, createFilterForType(filterItem.filterType)
            )
            bitmapFilter?.let {
                val wh = ScaleUtils.calcScaleToWidthI(
                    ScreenUtils.getScreenWidth().toDouble(),
                    it.width.toDouble(), it.height.toDouble()
                )
                QuickHelper.get(binding.vidIv)
                    .setWidthHeight(wh[0], wh[1])
                    .setImageBitmap(it)
            }
        } catch (e: Exception) {
            TAG.log_eTag(
                throwable = e,
                message = "setFilter"
            )
        }
    }
}