package afkt.project.model.bean;

import dev.utils.common.ChineseUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: Item 实体类
 * @author Ttt
 */
public class ItemBean {

    // 标题
    public String title;
    // 副标题
    public String subtitle;
    // 内容
    public String content;
    // 图片路径
    public String imageUrl;
    // 时间
    public long   time;
    public String timeFormat;

    /**
     * 创建 Item 实体类 ( 正方形 )
     * @return {@link ItemBean}
     */
    public static ItemBean newItemBean() {
        ItemBean item = new ItemBean();
        item.title = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10));
        item.subtitle = ChineseUtils.randomWord(RandomUtils.getRandom(5, 10));
        item.content = ChineseUtils.randomWord(RandomUtils.getRandom(30, 60));
        item.imageUrl = String.format("https://picsum.photos/id/%s/500", RandomUtils.getRandom(1, 50));
        item.time = System.currentTimeMillis() - RandomUtils.nextLongRange(DateUtils.MINUTE, DateUtils.DAY);
        item.timeFormat = DateUtils.formatTime(item.time, "yyyy.MM.dd");
        return item;
    }

    /**
     * 创建 Item 实体类 ( 长方形 )
     * @return {@link ItemBean}
     */
    public static ItemBean newItemBeanPager() {
        ItemBean item = ItemBean.newItemBean();
        item.imageUrl = String.format("https://picsum.photos/id/%s/1080/1920", RandomUtils.getRandom(1, 50));
        return item;
    }
}