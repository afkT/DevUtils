package afkt.project.model.bean

import dev.utils.common.ChineseUtils
import dev.utils.common.RandomUtils

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
    var commodityItem: CommodityItem

    init {
        val text =
            ChineseUtils.randomWord(RandomUtils.getRandom(50)) + RandomUtils.getRandomLetters(
                RandomUtils.getRandom(10)
            )
        val randomText = RandomUtils.getRandom(text.toCharArray(), text.length)
        evaluateContent = randomText
        evaluateLevel = RandomUtils.getRandom(6).toFloat()
        commodityItem = CommodityItem.newCommodityItem()
    }
}

/**
 * detail: 商品 Item
 * @author Ttt
 */
class CommodityItem(
    // 商品名
    val commodityName: String? = null,
    // 商品图片
    val commodityPicture: String? = null,
    // 商品价格
    val commodityPrice: Double
) {

    companion object {
        /**
         * 创建商品评价实体类
         * @return [CommodityItem]
         */
        fun newCommodityItem(): CommodityItem {
            return CommodityItem(
                commodityName = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40)),
                commodityPicture = "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}",
                commodityPrice = RandomUtils.nextDoubleRange(15.1, 79.3)
            )
        }
    }
}