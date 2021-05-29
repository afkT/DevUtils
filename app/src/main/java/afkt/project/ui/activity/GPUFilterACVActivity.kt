package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.ActivityGpuFilterBinding
import afkt.project.model.bean.ACVFileBean
import afkt.project.ui.adapter.GPUFilterACVAdapter
import afkt.project.util.GPUFilterUtils.getFilterBitmap
import afkt.project.util.GPUFilterUtils.getGPUImageToneCurveFilter
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.AdapterView
import com.alibaba.android.arouter.facade.annotation.Route
import com.luck.picture.lib.config.PictureMimeType
import dev.engine.log.DevLogEngine
import dev.engine.media.DevMediaEngine
import dev.utils.app.HandlerUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.image.ImageUtils
import ktx.dev.engine.media.MediaConfig
import java.util.*

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
        binding.vidAgfGallery.adapter = GPUFilterACVAdapter(this, listACVFiles).also {
            gpuFilterACVAdapter = it
        }
        binding.vidAgfGallery.onItemSelectedListener =
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
        binding.vidAgfGallery.setSelection(0)
    }

    override fun initListener() {
        super.initListener()
        binding.vidAgfSelectBtn.setOnClickListener {
            // 初始化图片配置
            var config = MediaConfig()
                .setCompress(false).setMaxSelectNum(1).setCrop(false)
                .setMimeType(PictureMimeType.ofImage())
                .setCamera(true).setGif(false)
            // 打开图片选择器
            DevMediaEngine.getEngine().openGallery(mActivity, config)
        }
    }

    // ===========
    // = 图片回传 =
    // ===========

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // 判断是否属于图片选择
        if (resultCode == RESULT_OK && data != null) {
            // 获取图片地址
            val imgPath = DevMediaEngine.getEngine().getSingleSelectorPath(data, true)
            // 获取图片 Bitmap
            selectBitmap = ImageUtils.decodeFile(imgPath)
            // 设置图片滤镜
            setFilter()
        }
    }

    // ===========
    // = 滤镜处理 =
    // ===========

    /**
     * 设置滤镜效果
     */
    private fun setFilter() {
        try {
            if (selectBitmap == null) return
            // 获取选中的滤镜
            val position = binding.vidAgfGallery.selectedItemPosition
            // 获取滤镜文件实体类
            val acvFileBean = gpuFilterACVAdapter.getItem(position)
            // 设置滤镜效果
            val gpuFilter = getGPUImageToneCurveFilter(ResourceUtils.open(acvFileBean.acvPath))
            val bitmapFilter = getFilterBitmap(selectBitmap, gpuFilter)
            binding.vidAgfIgview.setImageBitmap(bitmapFilter)
        } catch (e: Exception) {
            DevLogEngine.getEngine().eTag(TAG, e, "setFilter")
        }
    }
}