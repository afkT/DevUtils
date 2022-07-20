package afkt.project.database.room.module.note.bean;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * detail: Note 数据库实体类转换
 * @author Ttt
 */
public class NoteConverter {

    @TypeConverter
    public Date convertToEntityPropertyDate(long databaseValue) {
        return new Date(databaseValue);
    }

    @TypeConverter
    public long convertToDatabaseValueDate(Date entityProperty) {
        return entityProperty == null ? 0L : entityProperty.getTime();
    }

    @TypeConverter
    public NoteType convertToEntityPropertyNoteType(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @TypeConverter
    public String convertToDatabaseValueNoteType(NoteType entityProperty) {
        return entityProperty.name();
    }

//    @TypeConverter
//    public List<NotePicture> convertToEntityPropertyNotePicture(String databaseValue) {
//        return GsonUtils.fromJson(databaseValue, GsonUtils.getArrayType(NotePicture.class));
//    }
//
//    @TypeConverter
//    public String convertToDatabaseValueNotePicture(NotePicture entityProperty) {
//        return GsonUtils.toJson(entityProperty);
//    }
}