package dev.capture.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import dev.DevHttpCapture
import dev.callback.DevCallback
import dev.capture.UtilsCompiler
import dev.capture.adapter.AdapterMainModule
import dev.capture.base.BaseDevHttpActivity
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureMainActivityBinding
import dev.capture.model.Items.MainItem
import dev.utils.DevFinal
import dev.utils.app.BarUtils
import dev.utils.app.ClickUtils.OnDebouncingClickListener
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.StringUtils

/**
 * detail: DevHttpCapture 入口
 * @author Ttt
 */
class DevHttpCaptureMainActivity : BaseDevHttpActivity<DevHttpCaptureMainActivityBinding>() {

    // 当前选中的 Module
    private var mModule: String = ""

    // 首页适配器
    private val mAdapter = AdapterMainModule()

    // 查询回调
    private val mCallback = object : DevCallback<Boolean>() {
        override fun callback(
            isQuerying: Boolean,
            size: Int
        ) {
            if (!isFinishing) {
                if (isQuerying) {
                    if (size == 0) {
                        UtilsCompiler.toastImpl().normal(
                            R.string.dev_http_capture_querying
                        )
                    }
                    return
                }
                // 设置数据源
                mAdapter.setDataList(UtilsCompiler.getMainData(mModule))
                // 判断是否存在数据
                ViewUtils.reverseVisibilitys(
                    mAdapter.isDataNotEmpty,
                    binding.vidRv,
                    binding.vidTipsInclude.vidTipsFl
                )
                UtilsCompiler.toastImpl().success(
                    R.string.dev_http_capture_query_complete
                )
                // 重置刷新点击处理
                UtilsCompiler.resetRefreshClick()
            }
        }
    }

    override fun createBinding(): DevHttpCaptureMainActivityBinding {
        return DevHttpCaptureMainActivityBinding.inflate(
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
        // 移除回调
        UtilsCompiler.clearCallback()
        // 清空数据
        UtilsCompiler.clearData()
        // 关闭所有页面
        UtilsCompiler.finishAllActivity()
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化数据
     * @param intent Intent
     */
    private fun initValue(intent: Intent) {
        // 移除回调
        UtilsCompiler.clearCallback()
        // 清空数据
        UtilsCompiler.clearData()

        // 获取模块名
        mModule = intent.getStringExtra(DevFinal.STR.MODULE) ?: ""

        // 设置点击事件
        binding.vidTitleInclude.vidBackIv.setOnClickListener { finishOperate() }
        // 设置标题
        binding.vidTitleInclude.vidTitleTv.text = DevHttpCapture.TAG
        // 设置提示文案
        binding.vidTipsInclude.vidTipsTv.setText(R.string.dev_http_capture_query_no_data)
        // 绑定适配器
        mAdapter.bindAdapter(binding.vidRv)
        // 判断是否选择指定模块
        if (StringUtils.isNotEmpty(mModule)) {
            // 默认展开该模块
            mAdapter.multiSelectMap.select(
                mModule, MainItem(mModule, ArrayList())
            )
        }

        // ==========
        // = 数据获取 =
        // ==========

        UtilsCompiler.queryData(
            mCallback, false
        )

        // ==========
        // = 事件处理 =
        // ==========

        ViewHelper.get()
            .setOnClick(object : OnDebouncingClickListener(UtilsCompiler.REFRESH_CLICK) {
                override fun doClick(view: View) {
                    UtilsCompiler.toastImpl().normal(
                        R.string.dev_http_capture_querying
                    )
                    if (!UtilsCompiler.isQuerying()) {
                        UtilsCompiler.queryData(
                            mCallback, true
                        )
                    }
                }

                override fun doInvalidClick(view: View) {
                    UtilsCompiler.toastImpl().normal(
                        R.string.dev_http_capture_querying
                    )
                }
            }, binding.vidRefreshFl)
    }
}