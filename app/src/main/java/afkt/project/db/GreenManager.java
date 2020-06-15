package afkt.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 * detail: GreenDao 管理类
 * @author Ttt
 * <pre>
 *     Android ORM 框架 : GreenDao 使用详解 ( 进阶篇 )
 *     @see <a href="https://blog.csdn.net/speedystone/article/details/74193053"/>
 *     @see <a href="https://greenrobot.org/greendao/documentation/modelling-entities"/>
 * </pre>
 */
public final class GreenManager {

    private GreenManager() {
    }

    // 日志 TAG
    private static final String TAG = GreenManager.class.getSimpleName();

    private static UpgradeHelper helper;
    private static Database      db;
    private static DaoMaster     daoMaster;
    private static DaoSession    daoSession;

    /**
     * 初始化操作
     * @param context {@link Context}
     */
    public static void init(Context context) {
        // regular SQLite database
        helper = new UpgradeHelper(context, "notes-db");
        db = helper.getWritableDb();

//        // encrypted SQLCipher database
//        helper = new UpgradeHelper(context, "notes-db-encrypted");
//        db = helper.getEncryptedWritableDb("encryption-key");

        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * detail: DB 升级 Helper
     * <pre>
     *     注意:
     *     默认的 DaoMaster.DevOpenHelper 会在数据库升级时, 删除所有的表, 意味着这将导致数据的丢失
     *     所以, 在正式的项目中, 你还应该做一层封装, 来实现数据库的安全升级
     * </pre>
     */
    public static class UpgradeHelper extends DaoMaster.OpenHelper {

        public UpgradeHelper(Context context, String name) {
            super(context, name);
        }

        public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            super.onUpgrade(db, oldVersion, newVersion);
//            MigrationHelper
        }
    }

    // ============
    // = 对外公开 =
    // ============

    public static UpgradeHelper getHelper() {
        return helper;
    }

    public static Database getDatabase() {
        return db;
    }

    public static DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    // ========
    // = Dao =
    // ========

    public static NoteDao getNoteDao() {
        return daoSession.getNoteDao();
    }

    public static NotePictureDao getNotePictureDao() {
        return daoSession.getNotePictureDao();
    }
}