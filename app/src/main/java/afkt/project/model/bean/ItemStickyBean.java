package afkt.project.model.bean;

import dev.utils.common.DateUtils;

/**
 * detail: Item 实体类
 * @author Ttt
 */
public class ItemStickyBean
        extends AdapterBean {

    // 图片路径
    public String imageUrl;
    // 时间
    public long   time;
    // 时间格式化
    public String timeFormat;
    // 吸附标题
    public String timeTile;

    public ItemStickyBean(
            String title,
            long time
    ) {
        super.title = title;
        this.time = time;

        String format = "yyyy.MM.dd";
        // 进行格式化
        timeFormat = DateUtils.formatTime(time, format);
        // 获取当前时间
        String currentTime = DateUtils.getDateNow(format);
        // 设置标题
        if (currentTime.equals(timeFormat)) {
            timeTile = "今日";
        } else {
            timeTile = DateUtils.formatTime(time, DateUtils.MMdd2);
        }
    }
}