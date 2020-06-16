package afkt.project.ui.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import afkt.project.R;
import afkt.project.db.Note;
import afkt.project.db.NotePicture;
import afkt.project.db.NoteType;
import dev.other.GlideUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.DateUtils;

/**
 * detail: GreenDao 适配器
 * @author Ttt
 */
public class GreenDaoAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {

    public GreenDaoAdapter() {
        super(R.layout.adapter_green_dao);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note item) {
        ViewHelper.get()
                .setText(helper.getView(R.id.vid_agd_title_tv), item.getText())
                .setText(helper.getView(R.id.vid_agd_content_tv), item.getComment())
                .setText(helper.getView(R.id.vid_agd_time_tv), DateUtils.formatDate(item.getDate(), "yyyy.MM.dd"))
                .setVisibility(item.getType() != NoteType.PICTURE, helper.getView(R.id.vid_agd_content_tv))
                .setVisibility(item.getType() != NoteType.TEXT, helper.getView(R.id.vid_agd_recy))
        ;
        RecyclerView vid_agd_recy = helper.getView(R.id.vid_agd_recy);
        if (ViewUtils.isVisibility(vid_agd_recy)) {
            vid_agd_recy.setAdapter(new ImageAdapter(item.getPictures()));
        }
    }

    class ImageAdapter extends BaseQuickAdapter<NotePicture, BaseViewHolder> {

        public ImageAdapter(@Nullable List<NotePicture> data) {
            super(R.layout.adapter_green_dao_image, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, NotePicture item) {
            GlideUtils.with().displayImage(item.getPicture(),
                    helper.getView(R.id.vid_agdi_igview));
        }
    }
}
