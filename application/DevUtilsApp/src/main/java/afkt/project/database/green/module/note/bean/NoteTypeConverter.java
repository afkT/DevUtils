package afkt.project.database.green.module.note.bean;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * detail: Note 类型转换存储实现
 * @author Ttt
 */
public class NoteTypeConverter
        implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}