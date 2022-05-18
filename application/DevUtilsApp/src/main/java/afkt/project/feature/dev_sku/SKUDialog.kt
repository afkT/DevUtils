package afkt.project.feature.dev_sku

import afkt.project.R
import afkt.project.databinding.SkuDialogSpecBinding
import afkt_replace.core.lib.utils.engine.image.IMAGE_ROUND_10
import afkt_replace.core.lib.utils.engine.image.display
import afkt_replace.core.lib.utils.engine.image.toImageConfig
import afkt_replace.core.lib.utils.toSource
import android.app.Dialog
import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import dev.assist.NumberControlAssist
import dev.base.number.INumberListener
import dev.utils.app.EditTextUtils
import dev.utils.app.ViewUtils
import dev.utils.app.toast.ToastUtils
import dev.utils.common.BigDecimalUtils
import dev.utils.common.ConvertUtils
import kotlin.math.max
import kotlin.math.min

/**
 * detail: 购买操作类型
 * @author Ttt
 */
enum class BuyType(
    val type: Int,
    val desc: String
) {

    CART(1, "加入购物车"),

    BUY(2, "立即购买"),

    ;

    /**
     * 是否加入购物车
     * @return `true` yes, `false` no
     */
    fun isCart(): Boolean {
        return this == CART
    }

    /**
     * 是否立即购买
     * @return `true` yes, `false` no
     */
    fun isBuy(): Boolean {
        return this == BUY
    }
}

/**
 * detail: 确定购买指定规格回调
 * @author Ttt
 */
interface SKUCallback {

    /**
     * 回调方法
     * @param spec 规格信息
     * @param number 购买数量
     * @param buyType 购买操作类型
     */
    fun callback(
        spec: Spec,
        number: Int,
        buyType: BuyType
    )
}

/**
 * detail: SKU Dialog
 * @author Ttt
 */
class SKUDialog(
    activity: FragmentActivity,
    private val callback: SKUCallback
) : Dialog(activity, R.style.DevDialogFullScreenTheme) {

    private val binding: SkuDialogSpecBinding by lazy {
        SkuDialogSpecBinding.inflate(layoutInflater)
    }

    // DevSKU [SKU] 封装类转换
    private val skuConvert = SKUConvert()

    // SKU 适配器
    private val adapter: SKUAdapter by lazy {
        SKUAdapter(skuConvert) {
            // 规格选择刷新
            refreshBySPEC()
        }
    }

    init {
        this.setContentView(binding.root)

        window?.let {
            val params = it.attributes
            params.dimAmount = 0.5F
            params.width = ViewUtils.MATCH_PARENT
            params.gravity = Gravity.BOTTOM
            it.attributes = params
        }

        // ==========
        // = 监听处理 =
        // ==========

        binding.vidCloseIv.setOnClickListener {
            dismiss()
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 显示 Dialog
     * @param buyType 购买操作类型
     * @param model 商品 SKU 模型
     * @param specId 默认选中规格数据
     */
    fun showDialog(
        buyType: BuyType,
        model: CommoditySKU,
        specId: Int = Int.MIN_VALUE
    ): SKUDialog {

        // 绑定购买数量功能事件
        bindNumberListener()

        // ==========
        // = 点击确定 =
        // ==========

        binding.vidConfirmTv.setOnClickListener {
            if (adapter.isAllSelectAttr()) {
                // 获取规格信息
                adapter.getModel()?.value?.let { spec ->
                    dismiss()
                    // 触发回调
                    callback.callback(spec, mNumberAssist.currentNumber, buyType)
                }
            } else {
                ToastUtils.showShort("请选择规格")
            }
        }

        // 绑定适配器
        adapter.bindAdapter(binding.vidRecy)
        // SKU 转换数据
        skuConvert.convert(model, specId)
        // 刷新数据源
        adapter.refreshStateData()
        // 规格选择刷新
        refreshBySPEC()
        // 显示弹窗
        show()
        return this
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 最小购买数量
    private val MIN_NUMBER = 1

    // 最大购买数量
    private val MAX_NUMBER = 99999

    // 购买数量辅助类
    private val mNumberAssist = NumberControlAssist(MIN_NUMBER, MAX_NUMBER)

    /**
     * 刷新规格价格
     */
    private fun refreshPrice() {
        binding.vidPriceTv.text = adapter.getModel()?.value?.let { spec ->
            val price = (mNumberAssist.currentNumber * spec.salePrice)
            BigDecimalUtils.round(price).toString()
        }
    }

    /**
     * 规格选择刷新
     */
    private fun refreshBySPEC() {
        adapter.getModel()?.value?.let { spec ->
            mNumberAssist
                // 设置最小购买数量
                .setMinNumber(MIN_NUMBER)
                // 设置最大购买数量
                .setMaxNumber(max(min(spec.inventory, MAX_NUMBER), MIN_NUMBER))
                // 设置当前购买数量
                .setCurrentNumber(MIN_NUMBER)

            // ============
            // = 更新 View =
            // ============

            // 设置规格价格
            refreshPrice()
            // 加载图片
            binding.vidPicIv.display(
                source = spec.picUrl.toSource(),
                config = IMAGE_ROUND_10.toImageConfig()
            )
            return
        }
        // 设置购买数量 ( 初始化设置最小购买数量 )
        mNumberAssist.currentNumber = MIN_NUMBER
    }

    /**
     * 绑定购买数量功能事件
     */
    private fun bindNumberListener() {
        mNumberAssist.apply {

            // =================
            // = 数量监听事件接口 =
            // =================

            numberListener = object : INumberListener {
                override fun onPrepareChanged(
                    isAdd: Boolean,
                    curNumber: Int,
                    afterNumber: Int
                ): Boolean {
                    return (afterNumber in minNumber..maxNumber)
                }

                override fun onNumberChanged(
                    isAdd: Boolean,
                    curNumber: Int
                ) {
                    // 小于等于最小购买数量显示灰色按钮
                    ViewUtils.setSelected(
                        curNumber <= minNumber,
                        binding.vidLeftIv
                    )
                    // 大于等于最大购买数量显示灰色
                    ViewUtils.setSelected(
                        curNumber >= maxNumber,
                        binding.vidRightIv
                    )
                    EditTextUtils.setText(
                        binding.vidNumberEt, curNumber.toString()
                    )
                }
            }

            // ==========
            // = 输入监听 =
            // ==========

            binding.vidNumberEt
                .addTextChangedListener(object : EditTextUtils.DevTextWatcher() {
                    override fun onTextChanged(
                        text: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val inputNumber = ConvertUtils.toInt(text)
                        // 数量相同则不处理
                        if (inputNumber == currentNumber) {
                            return
                        }
                        // 如果小于最小值则修改为最小值
                        if (isLessThanMinNumber(inputNumber)) {
                            currentNumber = MIN_NUMBER
                            return
                        }
                        // 如果大于最大值则修改为最大值
                        if (isGreaterThanMaxNumber(inputNumber)) {
                            currentNumber = MAX_NUMBER
                            return
                        }
                        currentNumber = inputNumber
                    }
                })

            // ==========
            // = 按钮处理 =
            // ==========

            binding.vidLeftIv.setOnClickListener {
                subtractionNumber()
            }
            binding.vidRightIv.setOnClickListener {
                addNumber()
            }
        }
        // 设置购买数量 ( 初始化设置最小购买数量 )
        mNumberAssist.currentNumber = MIN_NUMBER // 该代码可不加用于在计算前就更新底部 UI
    }
}