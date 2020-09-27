package afkt.project.database.green;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import afkt.project.database.green.able.GreenDatabase;
import afkt.project.database.green.module.note.NoteDatabase;
import dev.utils.app.logger.DevLogger;
import dev.utils.common.StringUtils;

/**
 * detail: GreenDao 管理类
 * @author Ttt
 */
public final class GreenManager {

    private GreenManager() {
    }

    // 日志 TAG
    private static final String TAG = GreenManager.class.getSimpleName();

    // DataBase 对象缓存
    private static final Map<String, GreenDatabase> sDatabaseMaps = new HashMap<>();

    // ============
    // = database =
    // ============

    /**
     * 获取 GreenDatabase 对象
     * @param dbName 数据库名
     * @param clazz  {@link GreenDatabase} 实现类
     * @return {@link GreenDatabase}
     */
    public static <T extends GreenDatabase> T database(final String dbName, final Class clazz) {
        return database(dbName, null, clazz);
    }

    /**
     * 获取 GreenDatabase 对象
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @param clazz    {@link GreenDatabase} 实现类
     * @return {@link GreenDatabase}
     */
    public static <T extends GreenDatabase> T database(final String dbName, final String password,
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
        GreenDatabase greenDatabase = sDatabaseMaps.get(databaseName);
        if (greenDatabase != null) {
            T db = null;
            try {
                db = (T) greenDatabase;
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
    private static final GreenDatabase.Create CREATE = new GreenDatabase.Create() {

        @Override
        public String getDatabaseName(String dbName, String password, Class clazz) {
            return GreenDatabase.createDatabaseName(dbName, StringUtils.isNotEmpty(password));
        }

        @Override
        public GreenDatabase create(String dbName, String password, Class clazz) {
            if (clazz == NoteDatabase.class) {
                return NoteDatabase.database(dbName, password);
            }
            return null;
        }
    };

    // ===============
    // = 快捷获取方法 =
    // ===============

    public static NoteDatabase getNoteDatabase() {
        return database(NoteDatabase.TAG, NoteDatabase.class);
    }
}