package afkt.project.database.room.able;

import androidx.room.RoomDatabase;

/**
 * detail: Room Database 抽象类
 * @author Ttt
 * <pre>
 *     dbName 最好为 功能模块名, 如果涉及需要区分用户则为 功能模块名 + 用户 id
 * </pre>
 */
public abstract class AbsRoomDatabase
        extends RoomDatabase {

    // 数据库名
    private static final String DATABASE_NAME = "room-db";

    /**
     * 拼接数据库名
     * @param dbName    数据库名
     * @param encrypted 数据库是否加密
     * @return 数据库名
     */
    public static final String createDatabaseName(
            final String dbName,
            final boolean encrypted
    ) {
        if (encrypted) return dbName + "-" + DATABASE_NAME + "-encrypted";
        return dbName + "-" + DATABASE_NAME;
    }

    // =============
    // = 创建数据库 =
    // =============

    /**
     * detail: 数据库创建接口
     * @author Ttt
     */
    public interface Create {

        /**
         * 获取数据库名
         * @param dbName   数据库名
         * @param password 数据库解密密码
         * @param clazz    {@link AbsRoomDatabase} 实现类
         * @return 数据库名
         */
        String getDatabaseName(
                String dbName,
                String password,
                Class clazz
        );

        /**
         * 创建数据库方法
         * @param dbName   数据库名
         * @param password 数据库解密密码
         * @param clazz    {@link AbsRoomDatabase} 实现类
         * @return {@link AbsRoomDatabase}
         */
        AbsRoomDatabase create(
                String dbName,
                String password,
                Class clazz
        );
    }
}