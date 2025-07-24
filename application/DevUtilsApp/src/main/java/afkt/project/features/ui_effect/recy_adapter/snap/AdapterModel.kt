package afkt.project.features.ui_effect.recy_adapter.snap

import dev.utils.DevFinal
import dev.utils.common.ChineseUtils
import dev.utils.common.DateUtils
import dev.utils.common.RandomUtils

/**
 * detail: LinearSnapHelper、PagerSnapHelper Adapter Item 数据模型
 * @author Ttt
 */
class SnapItemModel(
    // 标题
    val title: String,
    // 副标题
    val subtitle: String,
    // 图片路径
    var imageUrl: String?,
    // 时间
    val timeFormat: String
) {

    companion object {

        /**
         * 创建 正方形 Item
         * @return [SnapItemModel]
         */
        private fun newSnapItem(): SnapItemModel {
            val time = System.currentTimeMillis() - RandomUtils.nextLongRange(
                DevFinal.TIME.MINUTE_MS,
                DevFinal.TIME.DAY_MS
            )
            return SnapItemModel(
                title = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                subtitle = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10)),
                imageUrl = String.format(
                    "https://picsum.photos/id/%s/500",
                    RandomUtils.getRandom(1, 50)
                ),
                timeFormat = DateUtils.formatTime(time, DevFinal.TIME.yyyyMMdd_POINT)
            )
        }

        /**
         * 创建 长方形 Item
         * @return [SnapItemModel]
         */
        private fun newSnapItemPager(): SnapItemModel {
            return newSnapItem().apply {
                imageUrl = String.format(
                    "https://picsum.photos/id/%s/1080/1920",
                    RandomUtils.getRandom(1, 50)
                )
            }
        }

        // =

        fun randomItemLists(max: Int = 10): MutableList<SnapItemModel> {
            val lists = mutableListOf<SnapItemModel>()
            for (i in 0..max) lists.add(newSnapItem())
            return lists
        }

        fun randomItemPagerLists(max: Int = 10): MutableList<SnapItemModel> {
            val lists = mutableListOf<SnapItemModel>()
            for (i in 0..max) lists.add(newSnapItemPager())
            return lists
        }
    }
}