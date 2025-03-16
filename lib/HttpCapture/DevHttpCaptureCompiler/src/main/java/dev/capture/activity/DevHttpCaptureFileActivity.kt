package dev.capture.activity

import android.content.Intent
import android.os.Bundle
import dev.callback.DevCallback
import dev.capture.UtilsCompiler
import dev.capture.adapter.AdapterCaptureFile
import dev.capture.base.BaseDevHttpActivity
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureFileActivityBinding
import dev.capture.model.Items
import dev.utils.DevFinal
import dev.utils.app.BarUtils
import dev.utils.app.ClipboardUtils
import dev.utils.app.ResourceUtils

/**
 * detail: DevHttpCapture 抓包数据详情页
 * @author Ttt
 */
class DevHttpCaptureFileActivity : BaseDevHttpActivity<DevHttpCaptureFileActivityBinding>() {

    // 首页适配器
    private val mAdapter = AdapterCaptureFile()

    override fun createBinding(): DevHttpCaptureFileActivityBinding {
        return DevHttpCaptureFileActivityBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 设置状态栏颜色
        BarUtils.setStatusBarColor(
            this, ResourceUtils.getColor(R.color.dev_http_capture_include_title_bg_color)
        )
        // 初始化数据
        initValue(intent)
    }

    override fun finishOperate() {
        // 关闭当前页面
        finish()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化数据
     * @param intent Intent
     */
    private fun initValue(intent: Intent) {
        // 获取抓包文件
        val json = intent.getStringExtra(DevFinal.STR.JSON)

        // 设置点击事件
        binding.vidTitleInclude.vidBackIv.setOnClickListener { finishOperate() }
        // 设置标题
        binding.vidTitleInclude.vidTitleTv.setText(R.string.dev_http_capture_details_title)
        // 绑定适配器
        mAdapter.setCallback(object : DevCallback<Items.FileItem>() {
            override fun callback(item: Items.FileItem) {
                ClipboardUtils.copyText(item.value)
                UtilsCompiler.toastIMPL().success(
                    R.string.dev_http_capture_copy_success
                )
            }
        }).bindAdapter(binding.vidRv)
        // 设置数据源
        binding.vidRv.post {
            if (!isFinishing) {
                mAdapter.setDataList(UtilsCompiler.getFileData(json))
            }
        }
    }
}