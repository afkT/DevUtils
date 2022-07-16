package afkt.project.database.green;

import static dev.kotlin.engine.log.LogKt.log_eTag;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import afkt.project.database.green.able.AbsGreenDatabase;
import afkt.project.database.green.module.image.ImageDatabase;
import afkt.project.database.green.module.note.NoteDatabase;
import dev.utils.common.StringUtils;

/**
 * detail: GreenDao 管理类
 * @author Ttt
 * <pre>
 *     官方文档
 *     @see <a href="https://greenrobot.org/greendao/documentation/modelling-entities"/>
 *     SQL 语句写到累了? 试试 GreenDAO
 *     @see <a href="https://www.jianshu.com/p/11bdd9d761e6"/>
 *     Android GreenDao 数据库
 *     @see <a href="https://www.jianshu.com/p/26c60d59e76d"/>
 *     Android ORM 框架 : GreenDao 使用详解 ( 进阶篇 )
 *     @see <a href="https://blog.csdn.net/speedystone/article/details/74193053"/>
 * </pre>
 */
public final class GreenManager {

    private GreenManager() {
    }

    // 日志 TAG
    private static final String TAG = GreenManager.class.getSimpleName();

    // Database 对象缓存
    private static final Map<String, AbsGreenDatabase> sDatabaseMaps = new HashMap<>();

    // ============
    // = database =
    // ============

    /**
     * 获取 GreenDatabase 对象
     * @param dbName 数据库名
     * @param clazz  {@link AbsGreenDatabase} 实现类
     * @return {@link AbsGreenDatabase}
     */
    public static <T extends AbsGreenDatabase> T database(
            final String dbName,
            final Class<?> clazz
    ) {
        return database(dbName, null, clazz);
    }

    /**
     * 获取 GreenDatabase 对象
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @param clazz    {@link AbsGreenDatabase} 实现类
     * @return {@link AbsGreenDatabase}
     */
    public static <T extends AbsGreenDatabase> T database(
            final String dbName,
            final String password,
            final Class<?> clazz
    ) {
        if (TextUtils.isEmpty(dbName)) return null;

        // 获取数据库名
        String databaseName = CREATE.getDatabaseName(dbName, password, clazz);

        if (sDatabaseMaps.get(databaseName) == null) {
            try {
                sDatabaseMaps.put(databaseName, CREATE.create(dbName, password, clazz));
            } catch (Exception e) {
                log_eTag(TAG, null, e, "database");
            }
        }
        AbsGreenDatabase greenDatabase = sDatabaseMaps.get(databaseName);
        if (greenDatabase != null) {
            T db = null;
            try {
                db = (T) greenDatabase;
            } catch (Exception e) {
                log_eTag(TAG, null, e, "database convert T");
            }
            return db;
        }
        return null;
    }

    // =======
    // = 创建 =
    // =======

    // 数据库创建接口
    private static final AbsGreenDatabase.Create CREATE = new AbsGreenDatabase.Create() {

        @Override
        public String getDatabaseName(
                String dbName,
                String password,
                Class<?> clazz
        ) {
            return AbsGreenDatabase.createDatabaseName(dbName, StringUtils.isNotEmpty(password));
        }

        @Override
        public AbsGreenDatabase create(
                String dbName,
                String password,
                Class<?> clazz
        ) {
            if (clazz == NoteDatabase.class) {
                return NoteDatabase.database(dbName, password);
            } else if (clazz == ImageDatabase.class) {
                return ImageDatabase.database(dbName, password);
            }
            return null;
        }
    };

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取 Note Database
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase getNoteDatabase() {
        return database(NoteDatabase.TAG, NoteDatabase.class);
    }
}