package afkt.project.database.room.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "NoteTable"
//        primaryKeys = {"id"}, // 主键
//        indices = { // 索引
//                @Index(value = "id", unique = true)
//        }
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "text")
    private String text;

    @Ignore
    public Note() {
    }

    public Note(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}