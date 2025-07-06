package afkt.project.feature.dev_sku

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.app.project.bindAdapter
import afkt.project.app.project.routerActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.data.button.ButtonList
import afkt.project.model.data.button.ButtonValue
import afkt.project.model.data.button.RouterPath
import com.therouter.router.Route
import dev.expand.engine.json.fromJson
import dev.utils.DevFinal
import dev.utils.app.ResourceUtils
import dev.utils.app.toast.ToastTintUtils
import dev.utils.common.CollectionUtils

/**
 * detail: DevSKU 商品 SKU 组合封装实现
 * @author Ttt
 */
@Route(path = RouterPath.DEV_LIBS.DevSKUActivity_PATH)
class DevSKUActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is DevSKUActivity) {
            it.apply {
                binding.vidRv.bindAdapter(ButtonList.moduleDevSKUButtonValues) { buttonValue ->
                    when (buttonValue.type) {
                        ButtonValue.BTN_SKU_DIALOG -> {
                            val skuFileName = "sku_test.json" // sku.json
                            val skuFileContent = ResourceUtils.readStringFromAssets(skuFileName)
                            val commoditySKU = skuFileContent.fromJson(
                                classOfT = CommoditySKU::class.java
                            )

                            // ==============
                            // = SKU Dialog =
                            // ==============

                            commoditySKU?.let { model ->
                                SKUDialog(it, object : SKUCallback {
                                    override fun callback(
                                        spec: Spec,
                                        number: Int,
                                        buyType: BuyType
                                    ) {
                                        ToastTintUtils.success(StringBuilder().apply {
                                            append("购买数量: ")
                                            append(number)
                                            append(DevFinal.SYMBOL.NEW_LINE)
                                            append("购买方式: ")
                                            append(buyType.desc)
                                            append(DevFinal.SYMBOL.NEW_LINE)
                                            append("规格名: ")
                                            append(spec.specName)
                                            append(DevFinal.SYMBOL.NEW_LINE)
                                            append("规格 id: ")
                                            append(spec.specId)
                                        }.toString())
                                    }
                                }).apply {
                                    // 显示 SKU Dialog
                                    assist.setDevDialog(this)
                                        .showDialog(BuyType.BUY, model, 1045)
                                }
                            }
                        }

                        else -> buttonValue.routerActivity()
                    }
                }
            }
        }
    }
)

// ==========
// = 数据模型 =
// ==========

// 映射 assets sku json 实体类
// sku.json 完整 SKU 模拟数据
// sku_test.json 删除部分规格信息方便测试无库存情况

data class CommoditySKU(
    val attributeList: MutableList<Attribute>,
    val specList: MutableList<Spec>
)

data class Spec(
    val specId: Int,
    val specName: String,
    val picUrl: String,
    val inventory: Int,
    val salePrice: Double,
    val attrValueIdList: List<Int>
)

data class Attribute(
    val id: Int,
    val attrName: String,
    val attrValueList: List<AttrValue>
)

data class AttrValue(
    val id: Int,
    val attrValue: String
)

// =================
// = DevSKU 转换处理 =
// =================

/**
 * detail: DevSKU [SKU] 封装类转换
 * @author Ttt
 * 如何使用 DevSKU:
 * 首先编写 Convert 类将服务器返回的信息转换为 DevSKU 封装类
 * [SKU.Model]      数据集基本模型
 * [SKU.Attr]       属性
 * [SKU.AttrValue]  属性值
 * 接着创建 [SKUData] 数据处理包装类
 * 通过 [SKUData.initialize] 进行初始化并通过 [SKUData.refreshStateData] 方法获取 Adapter 数据源
 * 每次选择、取消选择规格都需要重新调用 [SKUData.refreshStateData] 获取最新状态
 */
class SKUConvert {

    // SKU 数据处理包装 ( 对外公开快捷使用 )
    val skuData = SKUData<Spec>()

    // ===============================================
    // = 以下方法作用于将服务器返回的信息转换为 DevSKU 封装类 =
    // ===============================================

    /**
     * 转换数据
     * @param model 商品 SKU 模型
     * @param specId 默认选中规格数据
     */
    fun convert(
        model: CommoditySKU,
        specId: Int = Int.MIN_VALUE
    ) {
        // 清空 null 数据 ( 可不添加该操作, 视自身情况而定 )
        CollectionUtils.clearNull(model.specList)
        CollectionUtils.clearNull(model.attributeList)
        model.attributeList.forEach { attr ->
            CollectionUtils.clearNull(attr.attrValueList)
        }
        // 初始化操作
        init(model, specId)
    }

    /**
     * 初始化操作
     * @param model 商品 SKU 模型
     * @param specId 默认选中规格数据
     */
    private fun init(
        model: CommoditySKU,
        specId: Int
    ) {

        // =============
        // = 转换规格属性 =
        // =============

        val attrs = mutableListOf<SKU.Attr>()
        model.attributeList.forEach { attr ->
            val lists = mutableListOf<SKU.AttrValue>()
            attr.attrValueList.forEach { attrValue ->
                lists.add(
                    SKU.AttrValue(
                        attrValue.id, attrValue.attrValue,
                        SKU.State.OPTIONAL
                    )
                )
            }
            if (lists.isNotEmpty()) {
                attrs.add(SKU.Attr(attr.id, attr.attrName, lists))
            }
        }

        // =======================
        // = 转换 SKU 数据集基本模型 =
        // =======================

        val skuModels = mutableMapOf<List<Int>, SKU.Model<Spec>>()
        model.specList.forEach { spec ->
            spec.attrValueIdList.apply {
                skuModels[this] = SKU.Model(
                    spec.inventory,
                    spec.salePrice,
                    spec
                )
            }
        }

        // ============
        // = 初始化数据 =
        // ============

        skuData.initialize(attrs, skuModels)

        // =================
        // = 自动选中规格属性 =
        // =================

        if (specId != Int.MIN_VALUE) {
            model.specList.forEach { spec ->
                spec.attrValueIdList.apply {
                    if (spec.specId == specId) {
                        skuData.autoSelectAttr(this)
                    }
                }
            }
        }
    }
}