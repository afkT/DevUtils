package afkt.project.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.util.ViewAssistUtils;
import dev.base.widget.BaseImageView;
import dev.other.GlideUtils;
import dev.widget.assist.ViewAssist;

/**
 * detail: ViewAssist RecyclerView 适配器
 * @author Ttt
 */
public class RecyclerLoadingAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RecyclerLoadingAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_recycler_loading, data);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            String url
    ) {
        BaseImageView vid_arl_igview = helper.getView(R.id.vid_arl_igview);
        FrameLayout   vid_arl_frame  = helper.getView(R.id.vid_arl_frame);
        ViewAssist    viewAssist     = ViewAssist.wrap(vid_arl_frame);
        ViewAssistUtils.registerRecyclerLoading(viewAssist, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(vid_arl_igview, viewAssist, url);
            }
        });
        loadImage(vid_arl_igview, viewAssist, url);
    }

    private void loadImage(
            BaseImageView imageView,
            ViewAssist viewAssist,
            String url
    ) {
        viewAssist.showIng();
        GlideUtils.with().displayImageToDrawable(url, imageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(
                    @Nullable GlideException e,
                    Object model,
                    Target<Drawable> target,
                    boolean isFirstResource
            ) {
                viewAssist.showFailed();
                return false;
            }

            @Override
            public boolean onResourceReady(
                    Drawable resource,
                    Object model,
                    Target<Drawable> target,
                    DataSource dataSource,
                    boolean isFirstResource
            ) {
                viewAssist.showSuccess();
                return false;
            }
        });
    }
}