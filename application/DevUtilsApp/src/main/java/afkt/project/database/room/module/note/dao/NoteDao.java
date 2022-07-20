package afkt.project.database.room.module.note.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import afkt.project.database.room.module.note.bean.Note;
import afkt.project.database.room.module.note.bean.NoteAndPicture;
import afkt.project.database.room.module.note.bean.NotePicture;

/**
 * detail: Room DAO 访问数据库方法
 * @author Ttt
 */
@Dao
public interface NoteDao {

//    @Insert
//    long[] insertNotes(Note... notes);

    @Insert
    long insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Transaction
    @Query("SELECT * FROM NoteTable")
    List<NoteAndPicture> getNoteAndPictureLists();

    @Transaction
    @Query("SELECT * FROM NoteTable LIMIT :limit OFFSET :offset")
    List<NoteAndPicture> getNoteAndPictureLists(
            int limit,
            int offset
    );

    // =

    @Insert
    long[] insertNotePictures(List<NotePicture> lists);

    @Delete
    int deleteNotePictures(NotePicture... notePictures);
}