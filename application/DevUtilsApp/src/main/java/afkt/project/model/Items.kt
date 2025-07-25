package afkt.project.model

import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
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
    val name: String? = null,
    // 商品图片
    val picture: String? = null,
    // 商品价格
    price: Double
) {
    val priceText = price.toPriceString()?.toRMBSubZeroAndDot()

    companion object {
        /**
         * 创建商品评价实体类
         * @return [CommodityItem]
         */
        fun newCommodityItem(): CommodityItem {
            return CommodityItem(
                name = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40)),
                picture = "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}",
                price = RandomUtils.nextDoubleRange(15.1, 79.3)
            )
        }
    }
}

/**
 * detail: 适配器实体类
 * @author Ttt
 */
open class AdapterBean(
    // 标题
    val title: String,
    // 内容
    val content: String,
) {

    companion object {

        /**
         * 创建适配器实体类
         * @param position 索引
         * @return [AdapterBean]
         */
        private fun newAdapterBean(position: Int): AdapterBean {
            val number = RandomUtils.getRandom(10, 100) + (10 + position / 3) * 3
            val content = "${position + 1}." + ChineseUtils.randomWord(
                RandomUtils.getRandom(number)
            )
            return AdapterBean(
                title = ChineseUtils.randomWord(2),
                content = content
            )
        }

        /**
         * 获取适配器实体类集合
         * @param count 集合总数
         * @return 适配器实体类集合
         */
        fun newAdapterBeanList(count: Int): List<AdapterBean> {
            val lists = mutableListOf<AdapterBean>()
            for (i in 0 until count) {
                lists.add(newAdapterBean(i))
            }
            return lists
        }
    }
}