package afkt.project.model.item;

import java.util.ArrayList;
import java.util.List;

import afkt.project.model.bean.CommodityEvaluateBean;
import dev.utils.common.ChineseUtils;
import dev.utils.common.RandomUtils;

/**
 * detail: 评价 Item
 * @author Ttt
 */
public class EvaluateItem {

    // 商品评价等级
    public float                 evaluateLevel;
    // 商品评价内容
    public String                evaluateContent;
    // 存储对象
    public CommodityEvaluateBean commodityEvaluateBean;
    // 选择的图片
    public List<String>          imageList = new ArrayList<>();

    public EvaluateItem() {
        // 随机字符串
        String text       = ChineseUtils.randomWord(RandomUtils.getRandom(50)) + RandomUtils.getRandomLetters(RandomUtils.getRandom(10));
        String randomText = RandomUtils.getRandom(text.toCharArray(), text.length());

        evaluateContent = randomText;
        evaluateLevel = RandomUtils.getRandom(6);
        commodityEvaluateBean = CommodityEvaluateBean.newCommodityEvaluateBean();
    }
}