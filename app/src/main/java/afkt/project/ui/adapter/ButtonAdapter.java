package afkt.project.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.model.item.ButtonValue;

/**
 * detail: Button 适配器
 * @author Ttt
 */
public class ButtonAdapter extends BaseQuickAdapter<ButtonValue, BaseViewHolder> {

    public ButtonAdapter(@Nullable List<ButtonValue> data) {
        super(R.layout.base_view_button, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ButtonValue item) {
        helper.setText(R.id.vid_bvb_btn, item.text)
                .addOnClickListener(R.id.vid_bvb_btn)
                .addOnLongClickListener(R.id.vid_bvb_btn);
    }
}
