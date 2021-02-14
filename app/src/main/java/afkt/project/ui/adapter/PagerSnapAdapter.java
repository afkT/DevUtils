package afkt.project.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.ItemBean;
import afkt.project.util.ProjectUtils;
import dev.engine.image.DevImageEngine;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: RecyclerView ViewPager 效果 Adapter
 * @author Ttt
 */
public class PagerSnapAdapter
        extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    public PagerSnapAdapter(@Nullable List<ItemBean> data) {
        super(R.layout.adapter_pager_snap, data);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            ItemBean item
    ) {
        ViewHelper.get()
                .setText(helper.getView(R.id.vid_ags_title_tv), item.title)
                .setText(helper.getView(R.id.vid_ags_subtitle_tv), item.subtitle)
                .setText(helper.getView(R.id.vid_ags_time_tv), item.timeFormat);
        DevImageEngine.getEngine().display(
                helper.getView(R.id.vid_ags_igview),
                item.imageUrl,
                ProjectUtils.getRoundConfig10()
        );
    }
}