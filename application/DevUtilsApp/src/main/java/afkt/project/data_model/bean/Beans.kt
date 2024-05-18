package afkt.project.data_model.bean

import dev.utils.DevFinal
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils

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
            val time = System.currentTimeMillis() - RandomUtils.nextLongRange(
                DevFinal.TIME.MINUTE_MS,
                DevFinal.TIME.DAY_MS
            )
            return ItemBean(
                title = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                subtitle = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                content = ChineseUtils.randomWord(RandomUtils.getRandom(30, 60)),
                imageUrl = String.format(
                    "https://picsum.photos/id/%s/500",
                    RandomUtils.getRandom(1, 50)
                ),
                timeFormat = DateUtils.formatTime(time, DevFinal.TIME.yyyyMMdd_POINT)
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