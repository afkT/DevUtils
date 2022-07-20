package afkt.project.database.room.module.note.bean;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * detail: {@link Note} 与 {@link NotePicture} 一对多关联关系类
 * @author Ttt
 */
public class NoteAndPicture {

    @Embedded
    public Note note;

    @Relation(
            parentColumn = "id",
            entityColumn = "noteId"
    )
    public List<NotePicture> pictures;
}