package afkt.project.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import afkt.project.R;
import afkt.project.db.Note;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: GreenDao 适配器
 * @author Ttt
 */
public class GreenDaoAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {

    public GreenDaoAdapter() {
        super(R.layout.adapter_key_value);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note item) {
        ViewHelper.get().setVisibility(helper.getLayoutPosition() == 0, helper.getView(R.id.vid_akv_line))
                .setText(helper.getView(R.id.vid_akv_key_tv), item.getText())
                .setText(helper.getView(R.id.vid_akv_value_tv), item.getComment());
    }
}
