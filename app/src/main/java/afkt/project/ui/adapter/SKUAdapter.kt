package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.SkuAdapterSpecBinding
import afkt.project.databinding.SkuAdapterSpecItemBinding
import afkt.project.ui.activity.SKUConvert
import afkt.project.ui.activity.Spec
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.sku.SKU
import dev.utils.app.ResourceUtils

/**
 * detail: SKU 适配器
 * @author Ttt
 */
class SKUAdapter(
    private val skuConvert: SKUConvert,
    val listener: View.OnClickListener?
) : DevDataAdapterExt<SKU.Attr, DevBaseViewBindingVH<SkuAdapterSpecBinding>>() {

    // 字体颜色
    private val color92ba37: Int = ResourceUtils.getColor(R.color.color_92ba37)
    private val color999999: Int = ResourceUtils.getColor(R.color.color_999999)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<SkuAdapterSpecBinding> {
        return newBindingViewHolder(parent, R.layout.sku_adapter_spec)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<SkuAdapterSpecBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        // 设置属性名
        holder.binding.vidNameTv.text = item.name
        // 添加属性值
        holder.binding.vidWv.apply {
            removeAllViews()
            // 循环创建子属性值并添加
            item.attrList.forEach { value ->
                addView(createSubAttr(item, value))
            }
        }
    }

    /**
     * 创建子属性 View
     * @param attr SKU.Attr
     * @param attrValue SKU.AttrValue
     */
    private fun createSubAttr(
        attr: SKU.Attr,
        attrValue: SKU.AttrValue
    ): LinearLayout {
        return SkuAdapterSpecItemBinding.inflate(
            LayoutInflater.from(context)
        ).apply {
            vidValueTv.text = attrValue.value
            if (isSelect(attr.id, attrValue)) {
                // 已选中
                vidValueTv.apply {
                    setTextColor(color92ba37)
                    setBackgroundResource(R.drawable.sku_shape_stroked_92ba37_corners6)
                    setOnClickListener {
                        unselect(attr.id)
                        // 刷新状态数据
                        refreshStateData()
                        // 触发点击
                        listener?.onClick(it)
                    }
                }
            } else {
                if (attrValue.isOptional()) {
                    // 可点击
                    vidValueTv.apply {
                        setTextColor(color999999)
                        setBackgroundResource(R.drawable.sku_shape_f6f6f6_corners6)
                        setOnClickListener {
                            select(attr.id, attrValue)
                            // 刷新状态数据
                            refreshStateData()
                            // 触发点击
                            listener?.onClick(it)
                        }
                    }
                } else if (attrValue.isNotOptional()) {
                    // 不可点击
                    vidValueTv.apply {
                        setTextColor(color999999)
                        setBackgroundResource(R.drawable.sku_shape_stroked_f6f6f6_corners6_dash)
                    }
                }
            }
        }.root
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ==================
    // = SKUData 二次封装 =
    // ==================

    /**
     * 以下方法可直接通过 [skuConvert.skuData] 获取
     * 为了美化代码以及代码控制, 统一通过 Adapter 进行获取
     */

    /**
     * 刷新状态数据
     */
    fun refreshStateData() {
        setDataList(skuConvert.skuData.refreshStateData())
    }

    /**
     * 获取选中属性对应的数据集基本模型
     * @return SKU.Model
     */
    fun getModel(): SKU.Model<Spec>? {
        return skuConvert.skuData.getModel()
    }

    /**
     * 是否全选每个属性 ( 每个属性的属性值集合都选中 )
     * @return `true` yes, `false` no
     */
    fun isAllSelectAttr(): Boolean {
        return skuConvert.skuData.isAllSelectAttr()
    }

    /**
     * 设置非选中操作
     * @param attrId SKU.Attr.id
     * @return SKUAdapter
     */
    fun unselect(attrId: Int): SKUAdapter {
        skuConvert.skuData.unselect(attrId)
        return this
    }

    /**
     * 设置选中操作
     * @param attrId SKU.Attr.id
     * @param attrValue SKU.AttrValue
     * @return SKUAdapter
     */
    fun select(
        attrId: Int,
        attrValue: SKU.AttrValue
    ): SKUAdapter {
        skuConvert.skuData.select(attrId, attrValue)
        return this
    }

    /**
     * 是否选中对应属性
     * @param attrId SKU.Attr.id
     * @param attrValue SKU.AttrValue
     * @return `true` yes, `false` no
     */
    fun isSelect(
        attrId: Int,
        attrValue: SKU.AttrValue
    ): Boolean {
        return skuConvert.skuData.isSelect(attrId, attrValue)
    }
}