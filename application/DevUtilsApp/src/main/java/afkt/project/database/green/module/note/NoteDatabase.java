package afkt.project.database.green.module.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.greenrobot.greendao.database.Database;

import afkt.project.database.green.MigrationHelper;
import afkt.project.database.green.able.AbsGreenDatabase;
import dev.DevUtils;
import dev.utils.common.StringUtils;
import gen.greendao.DaoMaster;
import gen.greendao.DaoSession;
import gen.greendao.NoteDao;
import gen.greendao.NotePictureDao;

import static dev.kotlin.engine.log.LogKt.log_dTag;

/*
 * detail: Note 数据库
 * @author Ttt
 */
public final class NoteDatabase
        extends AbsGreenDatabase {

    public NoteDatabase(
            UpgradeHelper helper,
            Database database,
            DaoMaster daoMaster,
            DaoSession daoSession
    ) {
        this.mHelper     = helper;
        this.mDatabase   = database;
        this.mDaoMaster  = daoMaster;
        this.mDaoSession = daoSession;
    }

    // 日志 TAG
    public static final String TAG = NoteDatabase.class.getSimpleName();

    // DBHelper
    private final UpgradeHelper mHelper;
    // NoteDatabase
    private final Database      mDatabase;
    private final DaoMaster     mDaoMaster;
    private final DaoSession    mDaoSession;

    // ============
    // = database =
    // ============

    /**
     * 创建数据库
     * @param dbName 数据库名
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase database(final String dbName) {
        return database(dbName, null);
    }

    /**
     * 创建数据库
     * @param dbName   数据库名
     * @param password 数据库解密密码
     * @return {@link NoteDatabase}
     */
    public static NoteDatabase database(
            final String dbName,
            final String password
    ) {
        if (TextUtils.isEmpty(dbName)) return null;

        // Database
        Database database;
        // DBHelper
        UpgradeHelper helper = new UpgradeHelper(
                DevUtils.getContext(),
                AbsGreenDatabase.createDatabaseName(dbName, StringUtils.isNotEmpty(password))
        );
        if (TextUtils.isEmpty(password)) {
            // regular SQLite database
            database = helper.getWritableDb();
        } else {
            // encrypted SQLCipher database
            database = helper.getEncryptedWritableDb(password);
        }
        DaoMaster  daoMaster  = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        return new NoteDatabase(helper, database, daoMaster, daoSession);
    }

    // ====================
    // = AbsGreenDatabase =
    // ====================

    @Override
    public DaoMaster.OpenHelper getHelper() {
        return mHelper;
    }

    @Override
    public Database getDatabase() {
        return mDatabase;
    }

    @Override
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    @Override
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    // ============
    // = 数据库升级 =
    // ============

    /**
     * detail: DB 升级 Helper
     * <pre>
     *     注意:
     *     默认的 DaoMaster.DevOpenHelper 会在数据库升级时, 删除所有的表, 意味着这将导致数据的丢失
     *     所以, 在正式的项目中, 你还应该做一层封装, 来实现数据库的安全升级
     * </pre>
     */
    private static class UpgradeHelper
            extends DaoMaster.OpenHelper {

        public UpgradeHelper(
                Context context,
                String name
        ) {
            super(context, name);
        }

        public UpgradeHelper(
                Context context,
                String name,
                SQLiteDatabase.CursorFactory factory
        ) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase db,
                int oldVersion,
                int newVersion
        ) {
            log_dTag(
                    TAG, null,
                    "oldVersion: %s, newVersion: %s", oldVersion, newVersion
            );
            MigrationHelper.migrate(db, NoteDao.class, NotePictureDao.class);
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    public NoteDao getNoteDao() {
        return mDaoSession.getNoteDao();
    }

    public NotePictureDao getNotePictureDao() {
        return mDaoSession.getNotePictureDao();
    }
}