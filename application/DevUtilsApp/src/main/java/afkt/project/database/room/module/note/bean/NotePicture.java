package afkt.project.database.room.module.note.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.UUID;

/**
 * detail: Note 图片资源
 * @author Ttt
 */
@Entity(
        primaryKeys = {"id", "noteId"}
)
public class NotePicture {

    @NonNull
    public Long id;

    @NonNull
    public Long noteId; // 对应的 note id ( 外键 )

    public String picture;

    // ==========
    // = 构造函数 =
    // ==========

    @Ignore
    public NotePicture(
            @NonNull Long noteId,
            String picture
    ) {
        this.id      = (long) UUID.randomUUID().hashCode();
        this.noteId  = noteId;
        this.picture = picture;
    }

    public NotePicture(
            @NonNull Long id,
            @NonNull Long noteId,
            String picture
    ) {
        this.id      = id;
        this.noteId  = noteId;
        this.picture = picture;
    }
}