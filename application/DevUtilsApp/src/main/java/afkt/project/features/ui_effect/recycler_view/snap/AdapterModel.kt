package afkt.project.features.ui_effect.recycler_view.snap

import afkt.project.model.helper.RandomHelper
import dev.utils.DevFinal
import dev.utils.common.DateUtils

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
            return SnapItemModel(
                title = RandomHelper.randomWordRange(5, 10),
                subtitle = RandomHelper.randomWordRange(5, 10),
                imageUrl = RandomHelper.randomImage500(),
                timeFormat = DateUtils.formatTime(
                    RandomHelper.randomTimeDiff(),
                    DevFinal.TIME.yyyyMMdd_POINT
                )
            )
        }

        /**
         * 创建 长方形 Item
         * @return [SnapItemModel]
         */
        private fun newSnapItemPager(): SnapItemModel {
            return newSnapItem().apply {
                imageUrl = RandomHelper.randomImage1080x1920()
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