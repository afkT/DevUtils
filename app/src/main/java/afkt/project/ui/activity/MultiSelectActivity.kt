package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.model.bean.CommodityEvaluateBean.Companion.newCommodityEvaluateBean
import afkt.project.ui.adapter.MultiSelectAdapter
import afkt.project.ui.adapter.MultiSelectAdapter.OnSelectListener
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.base.widget.BaseTextView
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.helper.view.ViewHelper
import dev.utils.app.toast.ToastTintUtils
import java.util.*

/**
 * detail: 多选辅助类 MultiSelectAssist
 * @author Ttt
 */
@Route(path = RouterPath.MultiSelectActivity_PATH)
class MultiSelectActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // 适配器
    lateinit var adapter: MultiSelectAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 增加 Toolbar 按钮
        addToolbarButton()

        val parent = binding.vidBvrRecy.parent as? ViewGroup
        // 根布局处理
        ViewHelper.get().setPadding(0, parent)
            .setBackgroundColor(ResourceUtils.getColor(R.color.color_33), parent)
    }

    override fun initValue() {
        super.initValue()

        val lists: MutableList<CommodityEvaluateBean> = ArrayList()
        for (i in 0..14) lists.add(newCommodityEvaluateBean())

        // 初始化布局管理器、适配器
        adapter = MultiSelectAdapter(lists)
            .setSelectListener(object : OnSelectListener {
                override fun onClickSelect(
                    pos: Int,
                    now: Boolean
                ) {
                    val item = adapter.getDataItem(pos)
                    DevEngine.getLog()
                        .eTag(TAG, "新状态: %s, 商品名: %s", now, item?.commodityName)
                }
            })
        binding.vidBvrRecy.adapter = adapter
    }

    // =============
    // = 增加按钮处理 =
    // =============

    // 编辑按钮
    var editView: BaseTextView? = null

    // 取消编辑按钮
    var cancelView: BaseTextView? = null

    // 确定按钮
    var confirmView: BaseTextView? = null

    // 全选按钮
    var allSelectView: BaseTextView? = null

    // 非全选按钮
    var unAllSelectView: BaseTextView? = null

    // 反选按钮
    var inverseSelectView: BaseTextView? = null

    /**
     * 增加 Toolbar 按钮
     */
    private fun addToolbarButton() {
        toolbar?.addView(createTextView("编辑") {
            adapter.isEditState = true
            // 显示其他按钮、隐藏编辑按钮
            ViewUtils.toggleVisibilitys(
                arrayOf<View?>(
                    cancelView,
                    confirmView,
                    allSelectView,
                    unAllSelectView,
                    inverseSelectView
                ), editView
            )
        }.also { editView = it })

        toolbar?.addView(createTextView("取消") {
            adapter.setEditState(false).clearSelectAll()
            // 显示编辑按钮、隐藏其他按钮
            ViewUtils.toggleVisibilitys(
                editView,
                cancelView,
                confirmView,
                allSelectView,
                unAllSelectView,
                inverseSelectView
            )
        }.also { cancelView = it })

        toolbar?.addView(createTextView("确定") {
            val builder = StringBuilder()
            builder.append("是否全选: ").append(adapter.isSelectAll)
            builder.append("\n是否选中: ").append(adapter.isSelect)
            builder.append("\n选中数量: ").append(adapter.selectSize)
            builder.append("\n总数: ").append(adapter.dataCount)
            ToastTintUtils.normal(builder.toString())
        }.also { confirmView = it })

        toolbar?.addView(createTextView("全选") {
            adapter.selectAll()
        }.also {
            allSelectView = it
        })

        toolbar?.addView(createTextView("非全选") {
            adapter.clearSelectAll()
        }.also {
            unAllSelectView = it
        })

        toolbar?.addView(createTextView("反选") {
            adapter.inverseSelect()
        }.also {
            inverseSelectView = it
        })

        // 显示编辑按钮
        ViewUtils.setVisibility(true, editView)
    }

    /**
     * 创建 TextView
     * @param text            文案
     * @param onClickListener 点击事件
     * @return [BaseTextView]
     */
    private fun createTextView(
        text: String,
        onClickListener: View.OnClickListener
    ): BaseTextView {
        return QuickHelper.get(BaseTextView(this))
            .setVisibilitys(false) // 默认隐藏
            .setText(text)
            .setBold()
            .setTextColors(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(13.0f)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick(onClickListener)
            .getView()
    }
}