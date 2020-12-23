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
import afkt.project.databinding.AdapterLinearSnapBinding;
import afkt.project.model.bean.ItemBean;
import afkt.project.util.ProjectUtils;
import dev.other.GlideUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
public class LinearSnapMAXAdapter extends RecyclerView.Adapter {

    Context        context;
    List<ItemBean> data;
    RequestOptions roundOptions;

    public LinearSnapMAXAdapter(
            Context context,
            @Nullable List<ItemBean> data
    ) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return new ItemViewHolder(
                ViewUtils.inflate(context, R.layout.adapter_linear_snap, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder,
            int position
    ) {
        int size = data.size();
        if (size != 0) {
            int      index    = position % size;
            ItemBean itemBean = data.get(index);

            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String posIndex = position + " - " + index;

            ViewHelper.get()
                    .setText(itemHolder.binding.vidAlsTitleTv, itemBean.title)
                    .setText(itemHolder.binding.vidAlsSubtitleTv, itemBean.subtitle)
                    .setText(itemHolder.binding.vidAlsTimeTv, itemBean.timeFormat)
                    .setText(itemHolder.binding.vidAlsIndexTv, posIndex);
            GlideUtils.with().displayImage(itemBean.imageUrl, itemHolder.binding.vidAlsIgview,
                    roundOptions);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        AdapterLinearSnapBinding binding;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterLinearSnapBinding.bind(itemView);
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