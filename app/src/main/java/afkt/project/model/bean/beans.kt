package afkt.project.model.bean

import dev.utils.DevFinal
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils
import java.util.*

/**
 * detail: ACV 文件实体类
 * @author Ttt
 */
class ACVFileBean(
    // ACV 名
    val acvName: String,
    // 文件地址
    val acvPath: String
)

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
            var content = "${position + 1}." + ChineseUtils.randomWord(
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
            val lists: MutableList<AdapterBean> = ArrayList(count)
            for (i in 0 until count) {
                lists.add(newAdapterBean(i))
            }
            return lists
        }
    }
}

/**
 * detail: Item 实体类
 * @author Ttt
 */
class ItemStickyBean(
    // 标题
    title: String,
    // 时间
    time: Long
) : AdapterBean(title, "") {

    // 时间格式化
    val timeFormat: String

    // 吸附标题
    val timeTile: String

    init {
        val format = DevFinal.TIME.yyyyMMdd5
        // 进行格式化
        timeFormat = DateUtils.formatTime(time, format)
        // 获取当前时间
        val currentTime = DateUtils.getDateNow(format)
        // 设置标题
        timeTile = if (currentTime == timeFormat) {
            "今日"
        } else {
            DateUtils.formatTime(time, DevFinal.TIME.MMdd2)
        }
    }
}

/**
 * detail: Item 实体类
 * @author Ttt
 */
class ItemBean(
    // 标题
    val title: String,
    // 副标题
    val subtitle: String,
    // 内容
    val content: String,
    // 图片路径
    var imageUrl: String?,
    // 时间
    val timeFormat: String
) {

    companion object {

        /**
         * 创建 Item 实体类 ( 正方形 )
         * @return [ItemBean]
         */
        fun newItemBean(): ItemBean {
            var time = System.currentTimeMillis() - RandomUtils.nextLongRange(
                DateUtils.MINUTE,
                DateUtils.DAY
            )
            return ItemBean(
                title = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                subtitle = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                content = ChineseUtils.randomWord(RandomUtils.getRandom(30, 60)),
                imageUrl = String.format(
                    "https://picsum.photos/id/%s/500",
                    RandomUtils.getRandom(1, 50)
                ),
                timeFormat = DateUtils.formatTime(time, DevFinal.TIME.yyyyMMdd5)
            )
        }

        /**
         * 创建 Item 实体类 ( 长方形 )
         * @return [ItemBean]
         */
        fun newItemBeanPager(): ItemBean {
            return newItemBean().apply {
                imageUrl = String.format(
                    "https://picsum.photos/id/%s/1080/1920",
                    RandomUtils.getRandom(1, 50)
                )
            }
        }
    }
}

/**
 * detail: 商品评价实体类
 * @author Ttt
 */
class CommodityEvaluateBean(
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
         * @return [CommodityEvaluateBean]
         */
        fun newCommodityEvaluateBean(): CommodityEvaluateBean {
            return CommodityEvaluateBean(
                commodityName = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40)),
                commodityPicture = "https://picsum.photos/20${RandomUtils.getRandom(0, 10)}",
                commodityPrice = RandomUtils.nextDoubleRange(15.1, 79.3)
            )
        }
    }
}