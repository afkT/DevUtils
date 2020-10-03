package afkt.project.database.room;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import afkt.project.database.room.able.AbsRoomDatabase;
import afkt.project.database.room.module.note.NoteDatabase;
import dev.utils.app.logger.DevLogger;
import dev.utils.common.StringUtils;

/**
 * detail: Room 数据库管理类
 * @author Ttt
 */
public final class RoomManager {

    private RoomManager() {
    }

    // 日志 TAG
    public static final String TAG = RoomManager.class.getSimpleName();

    // Database 对象缓存
    private static final Map<String, AbsRoomDatabase> sDatabaseMaps = new HashMap<>();

    // ============
    // = database =
    // ============

    /**
     * 获取 RoomDatabase 对象
     * @param dbName 数据库名
     * @param clazz  {@link AbsRoomDatabase} 实现类
     * @return {@link AbsRoomDatabase}
     */
    public static <T extends AbsRoomDatabase> T database(final String dbName, final Class clazz) {
        return database(dbName, null, clazz);
    }

    /**
     * 获取 RoomDatabase 对象
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @param clazz    {@link AbsRoomDatabase} 实现类
     * @return {@link AbsRoomDatabase}
     */
    public static <T extends AbsRoomDatabase> T database(final String dbName, final String password,
                                                         final Class clazz) {
        if (TextUtils.isEmpty(dbName)) return null;

        // 获取数据库名
        String databaseName = CREATE.getDatabaseName(dbName, password, clazz);

        if (sDatabaseMaps.get(databaseName) == null) {
            try {
                sDatabaseMaps.put(databaseName, CREATE.create(dbName, password, clazz));
            } catch (Exception e) {
                DevLogger.eTag(TAG, e, "database");
            }
        }
        AbsRoomDatabase roomDatabase = sDatabaseMaps.get(databaseName);
        if (roomDatabase != null) {
            T db = null;
            try {
                db = (T) roomDatabase;
            } catch (Exception e) {
                DevLogger.eTag(TAG, e, "database - convert T");
            }
            return db;
        }
        return null;
    }

    // ========
    // = 创建 =
    // ========

    // 数据库创建接口
    private static final AbsRoomDatabase.Create CREATE = new AbsRoomDatabase.Create() {

        @Override
        public String getDatabaseName(String dbName, String password, Class clazz) {
            return AbsRoomDatabase.createDatabaseName(dbName, StringUtils.isNotEmpty(password));
        }

        @Override
        public AbsRoomDatabase create(String dbName, String password, Class clazz) {
            if (clazz == NoteDatabase.class) {
                return NoteDatabase.database(dbName, password);
            }
            return null;
        }
    };

    // ============
    // = 快捷方法 =
    // ============

    /**
     * 获取 Note Database
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase getNoteDatabase() {
        return database(NoteDatabase.TAG, NoteDatabase.class);
    }
}