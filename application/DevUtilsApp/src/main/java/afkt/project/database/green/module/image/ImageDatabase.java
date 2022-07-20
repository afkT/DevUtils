package afkt.project.database.green.module.image;

import org.greenrobot.greendao.database.Database;

import afkt.project.database.green.able.AbsGreenDatabase;
import afkt.project.database.green.module.note.NoteDatabase;
import gen.greendao.DaoMaster;
import gen.greendao.DaoSession;

/**
 * detail: 不同模块数据库实现, 演示类
 * @author Ttt
 * <pre>
 *     主要演示区分 note 模块包目录
 *     参考 {@link NoteDatabase}
 * </pre>
 */
public class ImageDatabase
        extends AbsGreenDatabase {

    /**
     * 创建数据库
     * @param dbName 数据库名
     * @return {@link ImageDatabase}
     */
    public static ImageDatabase database(final String dbName) {
        return database(dbName, null);
    }

    /**
     * 创建数据库
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @return {@link ImageDatabase}
     */
    public static ImageDatabase database(
            final String dbName,
            final String password
    ) {
        return null;
    }

    // ====================
    // = AbsGreenDatabase =
    // ====================

    @Override
    public DaoMaster.OpenHelper getHelper() {
        return null;
    }

    @Override
    public Database getDatabase() {
        return null;
    }

    @Override
    public DaoMaster getDaoMaster() {
        return null;
    }

    @Override
    public DaoSession getDaoSession() {
        return null;
    }
}