package afkt.project.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.CommodityEvaluateBean;
import afkt.project.model.item.EvaluateItem;
import afkt.project.ui.widget.BaseEditText;
import dev.assist.EditTextWatcherAssist;
import dev.other.GlideUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.common.BigDecimalUtils;
import dev.utils.common.DevCommonUtils;

/**
 * detail: Item EditText 输入监听 Adapter
 * @author Ttt
 */
public class EditsAdapter extends BaseQuickAdapter<EvaluateItem, BaseViewHolder> {

    // EditText 输入监听辅助类
    private EditTextWatcherAssist<EvaluateItem> editTextWatcherAssist = new EditTextWatcherAssist();
    // 圆角 RequestOptions
    private RequestOptions requestOptions;

    public EditsAdapter(@Nullable List<EvaluateItem> data) {
        super(R.layout.adapter_item_edits, data);
        // 获取默认 RequestOptions
        requestOptions = GlideUtils.defaultOptions();
        // 设置圆角, 使用 RoundedCorners 图片不会闪烁
        requestOptions.transform(new RoundedCorners((int) ResourceUtils.getDimension(R.dimen.un_radius)));
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateItem item) {
        // 当前索引
        int position = helper.getLayoutPosition();
        // 判断是否显示边距
        ViewUtils.setVisibility(position == 0, helper.getView(R.id.vid_aie_line));

        CommodityEvaluateBean commodityEvaluateBean = item.commodityEvaluateBean;

        // ============
        // = 商品信息 =
        // ============

        // 商品名
        helper.setText(R.id.vid_aie_name_tv, commodityEvaluateBean.commodityName);
        // 商品价格
        helper.setText(R.id.vid_aie_price_tv,
                "￥" + BigDecimalUtils.round(commodityEvaluateBean.commodityPrice, 2));
        // 商品图片
        GlideUtils.with().displayImage(commodityEvaluateBean.commodityPicture, helper.getView(R.id.vid_aie_pic_igview), requestOptions);

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

        // ============
        // = 输入监听 =
        // ============

        // 评价内容
        BaseEditText vid_aie_content_edit = helper.getView(R.id.vid_aie_content_edit);
        // 评价内容字数
        TextView vid_aie_number_tv = helper.getView(R.id.vid_aie_number_tv);
        // 计算已经输入的内容长度
        vid_aie_number_tv.setText(120 - DevCommonUtils.length(item.evaluateContent) + "");
        // 绑定监听事件
        editTextWatcherAssist.bindListener(item.evaluateContent, position, vid_aie_content_edit, new EditTextWatcherAssist.InputListener<EvaluateItem>() {
            @Override
            public void onTextChanged(CharSequence charSequence, EditText editText, int pos, EvaluateItem evaluateItem) {
                try {
                    // 保存评价内容
                    getData().get(pos).evaluateContent = charSequence.toString();
                } catch (Exception e) {
                }
                try {
                    // 计算已经输入的内容长度
                    vid_aie_number_tv.setText(120 - DevCommonUtils.length(item.evaluateContent) + "");
                } catch (Exception e) {
                }
            }
        });
    }
}
