package afkt.project.feature.ui_effect.recy_adapter.item_sticky

import afkt.project.model.bean.AdapterBean
import dev.utils.DevFinal
import dev.utils.common.DateUtils

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
        val format = DevFinal.TIME.yyyyMMdd_POINT
        // 进行格式化
        timeFormat = DateUtils.formatTime(time, format)
        // 获取当前时间
        val currentTime = DateUtils.getDateNow(format)
        // 设置标题
        timeTile = if (currentTime == timeFormat) {
            "今日"
        } else {
            DateUtils.formatTime(time, DevFinal.TIME.ZH_MMdd)
        }
    }
}