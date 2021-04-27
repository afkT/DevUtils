package afkt.project.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.ItemStickyBean;

/**
 * detail: 吸附 Item 预览 View Adapter
 * @author Ttt
 */
public class ItemStickyAdapter
        extends BaseQuickAdapter<ItemStickyBean, BaseViewHolder> {

    public ItemStickyAdapter(@Nullable List<ItemStickyBean> data) {
        super(R.layout.adapter_item_sticky, data);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            ItemStickyBean item
    ) {
        helper.setText(R.id.vid_ais_title_tv, item.title)
                .setText(R.id.vid_ais_time_tv, item.timeFormat);
    }
}