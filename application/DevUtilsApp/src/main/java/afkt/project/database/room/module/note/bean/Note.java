package afkt.project.database.room.module.note.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * detail: Note 实体类
 * @author Ttt
 * <pre>
 *     可以不加 @ColumnInfo 默认为当前字段名, 如果不想创建该列可以增加 @Ignore 注解进行忽略
 * </pre>
 */
@Entity(
        tableName = "NoteTable"
//        primaryKeys = {"id"}, // 主键
//        indices = { // 索引
//                @Index(value = "id", unique = true)
//        }
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "text")
    public String text;

    public String comment;

    public Date date;

    public NoteType type; // Note 类型

    // ==========
    // = 构造函数 =
    // ==========

    @Ignore
    public Note() {
    }

    public Note(
            Long id,
            String text,
            String comment,
            Date date,
            NoteType type
    ) {
        this.id      = id;
        this.text    = text;
        this.comment = comment;
        this.date    = date;
        this.type    = type;
    }
}