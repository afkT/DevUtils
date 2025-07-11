package afkt.project.feature.ui_effect.multi_select

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.app.AppViewModel
import afkt.project.model.data.bean.CommodityItem
import afkt.project.model.data.bean.CommodityItem.Companion.newCommodityItem
import afkt.project.model.data.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ui_effect.multi_select.MultiSelectAdapter.OnSelectListener
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.therouter.router.Route
import dev.expand.engine.log.log_dTag
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.toast.ToastTintUtils
import dev.widget.decoration.linear.FirstLinearColorItemDecoration

/**
 * detail: 多选辅助类 MultiSelectAssist
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.MultiSelectActivity_PATH)
class MultiSelectActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is MultiSelectActivity) {
            it.apply {
                // 增加 Toolbar 按钮
                addToolbarButton()

                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)
                    .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))

                val lists = mutableListOf<CommodityItem>()
                for (i in 0..14) lists.add(newCommodityItem())

                // 初始化布局管理器、适配器
                adapter = MultiSelectAdapter(lists)
                    .setSelectListener(object : OnSelectListener {
                        override fun onClickSelect(
                            position: Int,
                            now: Boolean
                        ) {
                            val item = adapter.getDataItem(position)
                            TAG.log_dTag(
                                message = "新状态: %s, 商品名: %s",
                                args = arrayOf(now, item?.commodityName)
                            )
                        }
                    })
                adapter.bindAdapter(binding.vidRv)

                QuickHelper.get(binding.vidRv)
                    .removeAllItemDecoration()
                    .addItemDecoration(
                        FirstLinearColorItemDecoration(
                            true, ResourceUtils.getDimension(R.dimen.dp_10)
                        )
                    )
            }
        }
    }
) {

    // 适配器
    lateinit var adapter: MultiSelectAdapter

    // =============
    // = 增加按钮处理 =
    // =============

    // 编辑按钮
    private var editView: AppCompatTextView? = null

    // 取消编辑按钮
    private var cancelView: AppCompatTextView? = null

    // 确定按钮
    private var confirmView: AppCompatTextView? = null

    // 全选按钮
    private var allSelectView: AppCompatTextView? = null

    // 非全选按钮
    private var unAllSelectView: AppCompatTextView? = null

    // 反选按钮
    private var inverseSelectView: AppCompatTextView? = null

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
     * @return [AppCompatTextView]
     */
    private fun createTextView(
        text: String,
        onClickListener: View.OnClickListener
    ): AppCompatTextView {
        return QuickHelper.get(AppCompatTextView(this))
            .setVisibilitys(false) // 默认隐藏
            .setText(text)
            .setBold()
            .setTextColors(ResourceUtils.getColor(R.color.white))
            .setTextSizeBySp(13.0F)
            .setPaddingLeft(30)
            .setPaddingRight(30)
            .setOnClick(onClickListener)
            .getView()
    }
}