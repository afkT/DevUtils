package afkt.project.model.bean;

import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 商品评价实体类
 * @author Ttt
 */
public class CommodityEvaluateBean {

    // 商品名
    public String commodityName;
    // 商品图片
    public String commodityPicture;
    // 商品价格
    public double commodityPrice;

    /**
     * 创建商品评价实体类
     * @return {@link CommodityEvaluateBean}
     */
    public static CommodityEvaluateBean newCommodityEvaluateBean() {
        CommodityEvaluateBean commodityEvaluateBean = new CommodityEvaluateBean();
        commodityEvaluateBean.commodityName = ChineseUtils.randomWord(RandomUtils.getRandom(5, 40));
        commodityEvaluateBean.commodityPicture = "https://picsum.photos/20" + RandomUtils.getRandom(0, 10);
        commodityEvaluateBean.commodityPrice = RandomUtils.nextDoubleRange(15.1f, 79.3f);
        return commodityEvaluateBean;
    }
}