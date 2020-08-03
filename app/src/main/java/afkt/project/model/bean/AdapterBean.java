package afkt.project.model.bean;

import java.util.ArrayList;
import java.util.List;

import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 适配器实体类
 * @author Ttt
 */
public class AdapterBean {

    // 标题
    public String title;
    // 内容
    public String content;

    /**
     * 创建适配器实体类
     * @param position 索引
     * @return {@link AdapterBean}
     */
    private static AdapterBean newAdapterBean(int position) {
        AdapterBean adapterBean = new AdapterBean();
        adapterBean.title = ChineseUtils.randomWord(2);

        int number = RandomUtils.getRandom(10, 100) + (10 + position / 3) * 3;
        adapterBean.content = (position + 1) + "." + ChineseUtils.randomWord(RandomUtils.getRandom(number));
        return adapterBean;
    }

    /**
     * 获取适配器实体类集合
     * @param count 集合总数
     * @return 适配器实体类集合
     */
    public static List<AdapterBean> newAdapterBeanList(int count) {
        List<AdapterBean> lists = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            lists.add(newAdapterBean(i));
        }
        return lists;
    }
}