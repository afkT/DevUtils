package dev.capture.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import dev.callback.DevCallback
import dev.capture.UtilsCompiler
import dev.capture.adapter.AdapterDateModuleList
import dev.capture.base.BaseDevHttpActivity
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureListActivityBinding
import dev.capture.model.Dialogs.DataTypeDialog
import dev.capture.model.Dialogs.GroupTypeDialog
import dev.capture.model.Items
import dev.capture.model.Items.GroupType
import dev.utils.DevFinal
import dev.utils.app.BarUtils
import dev.utils.app.ClickUtils.ClickAssist
import dev.utils.app.ClickUtils.OnDebouncingClickListener
import dev.utils.app.DialogUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: DevHttpCapture 抓包数据列表
 * @author Ttt
 */
class DevHttpCaptureListActivity : BaseDevHttpActivity<DevHttpCaptureListActivityBinding>() {

    // 当前选中的 Module
    private var mModule: String = ""

    // 当前选中的日期
    private var mDate: String = ""

    // 拼接筛选选项
    private var mOptions: String = ""

    // 数据来源类型
    private var mDataType = Items.DataType.T_ALL

    // 分组类型
    private var mGroupType = GroupType.T_TIME

    // 首页适配器
    private val mAdapter = AdapterDateModuleList()

    // 查询回调
    private val mCallback = object : DevCallback<Boolean>() {
        override fun callback(
            isQuerying: Boolean,
            size: Int
        ) {
            if (!isFinishing) {
                if (isQuerying) {
                    if (size == 0) {
                        UtilsCompiler.toastIMPL().normal(
                            R.string.dev_http_capture_querying
                        )
                    }
                    return
                }
                // 如果和之前的选项不一样, 则清空历史多选数据
                if (getNewOptions() != mOptions) {
                    mOptions = getNewOptions()
                    mAdapter.setNotifyAdapter(false)
                        .clearSelectAll()
                }
                // 设置数据源
                mAdapter.setDataList(
                    UtilsCompiler.getDateData(
                        mModule, mDate, mDataType, mGroupType
                    )
                )
                // 判断是否存在数据
                ViewUtils.reverseVisibilitys(
                    mAdapter.isDataNotEmpty,
                    binding.vidRv,
                    binding.vidTipsInclude.vidTipsFl
                )
                UtilsCompiler.toastIMPL().success(
                    R.string.dev_http_capture_query_complete
                )
                // 重置刷新点击处理
                UtilsCompiler.resetRefreshClick()
            }
        }
    }

    override fun createBinding(): DevHttpCaptureListActivityBinding {
        return DevHttpCaptureListActivityBinding.inflate(
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
        UtilsCompiler.removeCallback(mCallback)
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
        // 获取模块名
        mModule = intent.getStringExtra(DevFinal.STR.MODULE) ?: ""
        // 获取时间
        mDate = intent.getStringExtra(DevFinal.STR.DATE) ?: ""

        // 设置点击事件
        binding.vidTitleInclude.vidBackIv.setOnClickListener { finishOperate() }
        // 设置标题
        binding.vidTitleInclude.vidTitleTv.text = "$mDate - $mModule"
        // 设置提示文案
        binding.vidTipsInclude.vidTipsTv.setText(R.string.dev_http_capture_query_no_data)
        // 绑定适配器
        mAdapter.bindAdapter(binding.vidRv)

        // 刷新选项 View 文本
        refreshOptionsText()
        // 设置新的拼接筛选选项
        mOptions = getNewOptions()

        // 初始化事件
        initListener()

        // ==========
        // = 数据获取 =
        // ==========

        queryData()
    }

    /**
     * 查询数据
     */
    private fun queryData() {
        UtilsCompiler.queryData(
            mCallback, false
        )
    }

    /**
     * 获取新的拼接筛选选项
     * @return 拼接筛选选项
     */
    private fun getNewOptions(): String {
        return mDataType.type + "-" + mGroupType.type
    }

    /**
     * 刷新选项 View 文本
     */
    private fun refreshOptionsText() {
        ViewHelper.get()
            .setText(mDataType.getTitle(), binding.vidTabInclude.vidDataTypeTv)
            .setText(mGroupType.getTitle(), binding.vidTabInclude.vidGroupTypeTv)
    }

    // ==========
    // = 事件处理 =
    // ==========

    // 筛选选项点击 ( 双击 ) 辅助类
    private val mOptionsClick = ClickAssist(300L)

    /**
     * 初始化事件
     */
    private fun initListener() {
        initDialogs()
        ViewHelper.get()
            .setOnClick(object : OnDebouncingClickListener(mOptionsClick) {
                override fun doClick(view: View) {
                    DialogUtils.closeDialog(mDataTypeDialog)
                    DialogUtils.showDialog(mDataTypeDialog)
                }
            }, binding.vidTabInclude.vidDataLl)
            .setOnClick(object : OnDebouncingClickListener(mOptionsClick) {
                override fun doClick(view: View) {
                    DialogUtils.closeDialog(mGroupTypeDialog)
                    DialogUtils.showDialog(mGroupTypeDialog)
                }
            }, binding.vidTabInclude.vidGroupLl)
            .setOnClick(object : OnDebouncingClickListener(UtilsCompiler.REFRESH_CLICK) {
                override fun doClick(view: View) {
                    UtilsCompiler.toastIMPL().normal(
                        R.string.dev_http_capture_querying
                    )
                    if (!UtilsCompiler.isQuerying()) {
                        UtilsCompiler.queryData(
                            mCallback, true
                        )
                    }
                }

                override fun doInvalidClick(view: View) {
                    UtilsCompiler.toastIMPL().normal(
                        R.string.dev_http_capture_querying
                    )
                }
            }, binding.vidRefreshFl)
    }

    // ==========
    // = 弹窗处理 =
    // ==========

    // 数据来源选项 Dialog
    private var mDataTypeDialog: DataTypeDialog? = null

    // 分组选项 Dialog
    private var mGroupTypeDialog: GroupTypeDialog? = null

    /**
     * 初始化 Dialog
     */
    private fun initDialogs() {
        mDataTypeDialog = DataTypeDialog(
            this, object : DevCallback<Items.DataType>() {
                override fun callback(value: Items.DataType) {
                    if (value !== mDataType) {
                        mDataType = value
                        // 刷新选项 View 文本
                        refreshOptionsText()
                        // 查询数据
                        queryData()
                    }
                }
            })
        mGroupTypeDialog = GroupTypeDialog(
            this, object : DevCallback<GroupType>() {
                override fun callback(value: GroupType) {
                    if (value !== mGroupType) {
                        mGroupType = value
                        // 刷新选项 View 文本
                        refreshOptionsText()
                        // 查询数据
                        queryData()
                    }
                }
            })
    }
}