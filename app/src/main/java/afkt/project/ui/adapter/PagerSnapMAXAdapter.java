package afkt.project.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import afkt.project.R;
import afkt.project.model.bean.ItemBean;
import afkt.project.util.ProjectUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import dev.base.widget.BaseImageView;
import dev.base.widget.BaseTextView;
import dev.other.GlideUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
public class PagerSnapMAXAdapter extends RecyclerView.Adapter {

    Context        context;
    List<ItemBean> data;
    RequestOptions roundOptions;

    public PagerSnapMAXAdapter(Context context, @Nullable List<ItemBean> data) {
        this.context = context;
        this.data = data;
        this.roundOptions = ProjectUtils.getRoundOptions10();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                ViewUtils.inflate(context, R.layout.adapter_pager_snap, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int size = data.size();
        if (size != 0) {
            int index = position % size;
            ItemBean itemBean = data.get(index);

            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String posIndex = position + " - " + index;

            ViewHelper.get()
                    .setText(itemHolder.vid_ags_title_tv, itemBean.title)
                    .setText(itemHolder.vid_ags_subtitle_tv, itemBean.subtitle)
                    .setText(itemHolder.vid_ags_time_tv, itemBean.timeFormat)
                    .setText(itemHolder.vid_ags_index_tv, posIndex);
            GlideUtils.with().displayImage(itemBean.imageUrl, itemHolder.vid_ags_igview,
                    roundOptions);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vid_ags_title_tv)
        BaseTextView  vid_ags_title_tv;
        @BindView(R.id.vid_ags_subtitle_tv)
        BaseTextView  vid_ags_subtitle_tv;
        @BindView(R.id.vid_ags_time_tv)
        BaseTextView  vid_ags_time_tv;
        @BindView(R.id.vid_ags_igview)
        BaseImageView vid_ags_igview;
        @BindView(R.id.vid_ags_index_tv)
        BaseTextView  vid_ags_index_tv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 获取真实索引
     * @param position 当前索引
     * @return Data 真实索引
     */
    public int getRealIndex(int position) {
        int size = data.size();
        return (size != 0) ? position % size : 0;
    }
}