package afkt.project.database.room.dao;

import android.text.TextUtils;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.HashMap;
import java.util.Map;

import dev.DevUtils;
import dev.utils.app.logger.DevLogger;

/**
 * detail: Room 数据库管理类
 * @author Ttt
 */
public final class RoomManager {

    private RoomManager() {
    }

    // 日志 TAG
    public static final String TAG = RoomManager.class.getSimpleName();

    // 默认数据库名
    private static final String                    DATABASE_NAME = "room-db";
    // RoomDataBase 对象缓存
    private static final Map<String, RoomDatabase> sDatabaseMaps = new HashMap<>();

    // ============
    // = 通用方法 =
    // ============

    /**
     * 拼接数据库名
     * @param dbName 数据库名
     * @return 数据库名
     */
    private static String _createDatabaseName(final String dbName) {
        return dbName + "-" + DATABASE_NAME;
    }

    /**
     * 获取 RoomDataBase 对象
     * <pre>
     *     如果用户数据不清空那么 dbName 最好为 功能模块名 + 用户 id
     *     并且自行保存已有的 dbName 方便删除数据库
     *     最终 database name 为 {@link #_createDatabaseName(String)}
     * </pre>
     * @param dbName 数据库名
     * @param clazz  Dao class
     * @param <T>    泛型
     * @return {@link RoomDatabase}
     */
    private synchronized static <T extends RoomDatabase> T _database(final String dbName, final Class clazz) {
        if (TextUtils.isEmpty(dbName) || clazz == null) return null;
        // 获取数据库标记名
        String dbTagName = _createDatabaseName(dbName);
        // 存储 RoomDatabase 对象
        if (sDatabaseMaps.get(dbTagName) == null) {
            try {
                sDatabaseMaps.put(dbTagName, Room.databaseBuilder(
                        DevUtils.getContext(), clazz, dbTagName
                ).build());
            } catch (Exception e) {
                DevLogger.eTag(TAG, e, "database");
            }
        }
        RoomDatabase roomDatabase = sDatabaseMaps.get(dbTagName);
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

    // ============
    // = 快捷方法 =
    // ============

    /**
     * 获取 RoomDataBase 对象缓存 Map
     * @return RoomDataBase 对象缓存 Map
     */
    public static Map<String, RoomDatabase> getDatabaseMaps() {
        return sDatabaseMaps;
    }

    /**
     * 获取 Note Database
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase getNoteDatabase() {
        return getNoteDatabase("notes");
    }

    /**
     * 获取 Note Database
     * @param dbName 数据库名
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase getNoteDatabase(String dbName) {
        return _database(dbName, NoteDatabase.class);
    }
}