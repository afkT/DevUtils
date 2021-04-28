package afkt.project.model.item

import afkt.project.model.bean.CommodityEvaluateBean
import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

/**
 * detail: 评价 Item
 * @author Ttt
 */
class EvaluateItem {

    // 商品评价等级
    @JvmField
    var evaluateLevel: Float

    // 商品评价内容
    @JvmField
    var evaluateContent: String

    // 存储对象
    var commodityEvaluateBean: CommodityEvaluateBean

    init {
        val text =
            ChineseUtils.randomWord(RandomUtils.getRandom(50)) + RandomUtils.getRandomLetters(
                RandomUtils.getRandom(10)
            )
        val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
        evaluateContent = randomText
        evaluateLevel = RandomUtils.getRandom(6).toFloat()
        commodityEvaluateBean = CommodityEvaluateBean.newCommodityEvaluateBean()
    }
}

/**
 * detail: Tab 模型类
 * @author Ttt
 */
class TabItem(
    // 标题
    val title: String,
    // 类型
    val type: Int
)