package afkt.project.ui.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.ItemBean;
import afkt.project.util.ProjectUtils;
import dev.other.GlideUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
public class LinearSnapAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    RequestOptions roundOptions;

    public LinearSnapAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.adapter_linear_snap, data);

        roundOptions = ProjectUtils.getRoundOptions10();
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            ItemBean item
    ) {
        ViewHelper.get()
                .setText(helper.getView(R.id.vid_als_title_tv), item.title)
                .setText(helper.getView(R.id.vid_als_subtitle_tv), item.subtitle)
                .setText(helper.getView(R.id.vid_als_time_tv), item.timeFormat);
        GlideUtils.with().displayImage(item.imageUrl, helper.getView(R.id.vid_als_igview),
                roundOptions);
    }
}