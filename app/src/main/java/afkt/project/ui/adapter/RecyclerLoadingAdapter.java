package afkt.project.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import afkt.project.R;
import afkt.project.util.ViewAssistUtils;
import dev.base.DevSource;
import dev.base.widget.BaseImageView;
import dev.engine.image.DevImageEngine;
import dev.engine.image.listener.DrawableListener;
import dev.widget.assist.ViewAssist;

/**
 * detail: ViewAssist RecyclerView 适配器
 * @author Ttt
 */
public class RecyclerLoadingAdapter
        extends BaseQuickAdapter<String, BaseViewHolder> {

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

        DevImageEngine.getEngine().display(
                imageView,
                url,
                new DrawableListener() {
                    @Override
                    public void onStart(DevSource source) {

                    }

                    @Override
                    public void onResponse(
                            DevSource source,
                            Drawable value
                    ) {
                        viewAssist.showSuccess();
                    }

                    @Override
                    public void onFailure(
                            DevSource source,
                            Throwable throwable
                    ) {
                        viewAssist.showFailed();
                    }
                }
        );
    }
}