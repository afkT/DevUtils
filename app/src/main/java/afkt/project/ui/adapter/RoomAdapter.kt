package afkt.project.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import afkt.project.R;
import afkt.project.database.room.module.note.bean.Note;
import afkt.project.database.room.module.note.bean.NoteAndPicture;
import afkt.project.database.room.module.note.bean.NotePicture;
import afkt.project.database.room.module.note.bean.NoteType;
import dev.engine.image.DevImageEngine;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.DateUtils;

/**
 * detail: Room 适配器
 * @author Ttt
 */
public class RoomAdapter
        extends BaseQuickAdapter<NoteAndPicture, BaseViewHolder> {

    public RoomAdapter() {
        super(R.layout.adapter_database);
    }

    @Override
    protected void convert(
            BaseViewHolder helper,
            NoteAndPicture item
    ) {
        Note note = item.note;

        ViewHelper.get()
                .setText(helper.getView(R.id.vid_adb_title_tv), note.text)
                .setText(helper.getView(R.id.vid_adb_content_tv), note.comment)
                .setText(helper.getView(R.id.vid_adb_time_tv), DateUtils.formatDate(note.date, "yyyy.MM.dd"))
                .setVisibility(note.type != NoteType.PICTURE, helper.getView(R.id.vid_adb_content_tv))
                .setVisibility(note.type != NoteType.TEXT, helper.getView(R.id.vid_adb_recy))
        ;
        RecyclerView vid_adb_recy = helper.getView(R.id.vid_adb_recy);
        if (ViewUtils.isVisibility(vid_adb_recy)) {
            vid_adb_recy.setAdapter(new ImageAdapter(item.pictures));
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
                    item.picture
            );
        }
    }
}