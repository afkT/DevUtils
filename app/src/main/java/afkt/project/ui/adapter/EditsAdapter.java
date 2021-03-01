package afkt.project.ui.adapter;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;

import java.math.BigDecimal;
import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.CommodityEvaluateBean;
import afkt.project.model.item.EvaluateItem;
import afkt.project.util.ProjectUtils;
import dev.assist.EditTextWatcherAssist;
import dev.base.widget.BaseEditText;
import dev.engine.image.DevImageEngine;
import dev.utils.app.ViewUtils;
import dev.utils.common.BigDecimalUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Item EditText 输入监听 Adapter
 * @author Ttt
 */
public class EditsAdapter
        extends BaseQuickAdapter<EvaluateItem, BaseViewHolder> {

    // EditText 输入监听辅助类
    private final EditTextWatcherAssist<EvaluateItem> editTextWatcherAssist = new EditTextWatcherAssist();

    public EditsAdapter(@Nullable List<EvaluateItem> data) {
        super(R.layout.adapter_item_edits, data);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            EvaluateItem item
    ) {
        // 当前索引
        int position = helper.getLayoutPosition();
        // 判断是否显示边距
        ViewUtils.setVisibility(position == 0, helper.getView(R.id.vid_aie_line));

        CommodityEvaluateBean commodityEvaluateBean = item.commodityEvaluateBean;

        // ===========
        // = 商品信息 =
        // ===========

        // 商品名
        helper.setText(R.id.vid_aie_name_tv, commodityEvaluateBean.commodityName);
        // 商品价格
        helper.setText(R.id.vid_aie_price_tv,
                "￥" + BigDecimalUtils.operation(commodityEvaluateBean.commodityPrice)
                        .round(2, BigDecimal.ROUND_HALF_UP)
        );
        // 商品图片
        DevImageEngine.getEngine().display(
                helper.getView(R.id.vid_aie_pic_igview),
                commodityEvaluateBean.commodityPicture,
                ProjectUtils.getRoundConfig3()
        );

        // 评星等级
        RatingBar vid_aie_ratingbar = helper.getView(R.id.vid_aie_ratingbar);
        vid_aie_ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                item.evaluateLevel = ratingCount;
            }
        });
        // 设置评星等级
        vid_aie_ratingbar.setStar(item.evaluateLevel);

        // ===========
        // = 输入监听 =
        // ===========

        // 评价内容
        BaseEditText vid_aie_content_edit = helper.getView(R.id.vid_aie_content_edit);
        // 评价内容字数
        TextView vid_aie_number_tv = helper.getView(R.id.vid_aie_number_tv);
        // 计算已经输入的内容长度
        vid_aie_number_tv.setText(String.valueOf(120 - StringUtils.length(item.evaluateContent)));
        // 绑定监听事件
        editTextWatcherAssist.bindListener(item.evaluateContent, position, vid_aie_content_edit, new EditTextWatcherAssist.InputListener<EvaluateItem>() {
            @Override
            public void onTextChanged(
                    CharSequence charSequence,
                    EditText editText,
                    int pos,
                    EvaluateItem evaluateItem
            ) {
                try {
                    // 保存评价内容
                    getData().get(pos).evaluateContent = charSequence.toString();
                } catch (Exception e) {
                }
                try {
                    // 计算已经输入的内容长度
                    vid_aie_number_tv.setText(String.valueOf(120 - StringUtils.length(item.evaluateContent)));
                } catch (Exception e) {
                }
            }
        });
    }
}