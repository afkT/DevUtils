package afkt.project.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import afkt.project.R;
import afkt.project.database.green.module.note.bean.Note;
import afkt.project.database.green.module.note.bean.NotePicture;
import afkt.project.database.green.module.note.bean.NoteType;
import dev.engine.image.DevImageEngine;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.DateUtils;

/**
 * detail: GreenDao 适配器
 * @author Ttt
 */
public class GreenDaoAdapter
        extends BaseQuickAdapter<Note, BaseViewHolder> {

    public GreenDaoAdapter() {
        super(R.layout.adapter_database);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            Note item
    ) {
        ViewHelper.get()
                .setText(helper.getView(R.id.vid_adb_title_tv), item.getText())
                .setText(helper.getView(R.id.vid_adb_content_tv), item.getComment())
                .setText(helper.getView(R.id.vid_adb_time_tv), DateUtils.formatDate(item.getDate(), "yyyy.MM.dd"))
                .setVisibility(item.getType() != NoteType.PICTURE, helper.getView(R.id.vid_adb_content_tv))
                .setVisibility(item.getType() != NoteType.TEXT, helper.getView(R.id.vid_adb_recy))
        ;
        RecyclerView vid_adb_recy = helper.getView(R.id.vid_adb_recy);
        if (ViewUtils.isVisibility(vid_adb_recy)) {
            vid_adb_recy.setAdapter(new ImageAdapter(item.getPictures()));
        }
    }

    class ImageAdapter
            extends BaseQuickAdapter<NotePicture, BaseViewHolder> {

        public ImageAdapter(@Nullable List<NotePicture> data) {
            super(R.layout.adapter_database_image, data);
        }

        @Override
        protected void convert(
                @NotNull BaseViewHolder helper,
                NotePicture item
        ) {
            DevImageEngine.getEngine().display(
                    helper.getView(R.id.vid_adbi_igview),
                    item.getPicture()
            );
        }
    }
}