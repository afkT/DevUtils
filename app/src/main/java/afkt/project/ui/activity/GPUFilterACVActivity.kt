package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityGpuFilterBinding
import afkt.project.model.bean.ACVFileBean
import afkt.project.ui.adapter.GPUFilterACVAdapter
import afkt.project.utils.GPUFilterUtils.getFilterBitmap
import afkt.project.utils.GPUFilterUtils.getGPUImageToneCurveFilter
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.engine.media.MediaConfig
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.UriUtils
import dev.utils.app.image.ImageUtils

/**
 * detail: GPU ACV 文件滤镜效果
 * @author Ttt
 */
@Route(path = RouterPath.GPUFilterACVActivity_PATH)
class GPUFilterACVActivity : BaseActivity<ActivityGpuFilterBinding>() {

    // 适配器
    private lateinit var gpuFilterACVAdapter: GPUFilterACVAdapter

    // ACV 文件集合
    private val listACVFiles: MutableList<ACVFileBean> = ArrayList()

    // 图片 Bitmap
    private var selectBitmap: Bitmap? = null

    companion object {
        // 滤镜线程
        private var filterThread: Runnable? = null
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

        // 初始化数据
        listACVFiles.add(ACVFileBean("August", "filter/August.acv"))
        listACVFiles.add(ACVFileBean("Darker", "filter/Darker.acv"))
        listACVFiles.add(ACVFileBean("Dream", "filter/Dream.acv"))
        listACVFiles.add(ACVFileBean("Fornature", "filter/Fornature.acv"))
        listACVFiles.add(ACVFileBean("Greens", "filter/Greens.acv"))
        listACVFiles.add(ACVFileBean("Miami", "filter/Miami.acv"))

        // 设置适配器
        binding.vidGallery.adapter = GPUFilterACVAdapter(this, listACVFiles).also {
            gpuFilterACVAdapter = it
        }
        binding.vidGallery.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    gpuFilterACVAdapter.setSelectPosition(position)
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
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // 判断是否属于图片选择
        if (resultCode == RESULT_OK && data != null) {
            // 获取图片地址
            val imgPath = DevEngine.getMedia()?.getSingleSelectorPath(data, true)
            // 获取图片 Bitmap
            selectBitmap = if (UriUtils.isUri(imgPath)) {
                ImageUtils.decodeStream(
                    ResourceUtils.openInputStream(
                        UriUtils.getUriForString(imgPath)
                    )
                )
            } else {
                ImageUtils.decodeFile(imgPath)
            }
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
            // 获取滤镜文件实体类
            val acvFileBean = gpuFilterACVAdapter.getItem(position)
            // 设置滤镜效果
            val gpuFilter = getGPUImageToneCurveFilter(ResourceUtils.open(acvFileBean.acvPath))
            val bitmapFilter = getFilterBitmap(this, selectBitmap, gpuFilter)
            binding.vidIv.setImageBitmap(bitmapFilter)
        } catch (e: Exception) {
            DevEngine.getLog()?.eTag(TAG, e, "setFilter")
        }
    }
}