package afkt.project.database.room.module.image;

import afkt.project.database.room.module.note.NoteDatabase;

/**
 * detail: 不同模块数据库实现, 演示类
 * @author Ttt
 * <pre>
 *     主要演示区分 note 模块包目录
 *     参考 {@link NoteDatabase}
 * </pre>
 */
public abstract class ImageDatabase { // extends AbsRoomDatabase

//    // ============
//    // = database =
//    // ============
//
//    // 日志 TAG
//    public static final String TAG = ImageDatabase.class.getSimpleName();
//
//    /**
//     * 创建数据库
//     * @param dbName 数据库名
//     * @return {@link RoomDatabase}
//     */
//    public static ImageDatabase database(final String dbName) {
//        return database(dbName, null);
//    }
//
//    /**
//     * 创建数据库
//     * @param dbName   数据库名
//     * @param password 数据库解密密码
//     * @return {@link RoomDatabase}
//     */
//    public static ImageDatabase database(final String dbName, final String password) {
//        if (TextUtils.isEmpty(dbName)) return null;
//
//        ImageDatabase database = Room.databaseBuilder(
//                DevUtils.getContext(), ImageDatabase.class, dbName
//        )
//                // 允许主线程访问数据库
//                .allowMainThreadQueries()
//                // 构建数据库
//                .build();
//        return database;
//    }
}