package afkt.project.database.green;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.kotlin.engine.log.LogKt.log_e;

/**
 * Createdby PedroOkawa and modified by MBH on 16/08/16.
 * <pre>
 *     https://stackoverflow.com/questions/13373170/greendao-schema-update-and-data-migration/30334668#30334668
 * </pre>
 */
public final class MigrationHelper {

    public static void migrate(
            SQLiteDatabase sqliteDatabase,
            Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        StandardDatabase db = new StandardDatabase(sqliteDatabase);
        generateNewTablesIfNotExists(db, daoClasses);
        generateTempTables(db, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    public static void migrate(
            StandardDatabase db,
            Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        generateNewTablesIfNotExists(db, daoClasses);
        generateTempTables(db, daoClasses);
        dropAllTables(db, true, daoClasses);
        createAllTables(db, false, daoClasses);
        restoreData(db, daoClasses);
    }

    private static void generateNewTablesIfNotExists(
            StandardDatabase db,
            Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        reflectMethod(db, "createTable", true, daoClasses);
    }

    private static void generateTempTables(
            StandardDatabase db,
            Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig     daoConfig                = new DaoConfig(db, daoClasses[i]);
            String        tableName                = daoConfig.tablename;
            String        tempTableName            = daoConfig.tablename.concat("_TEMP");
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("CREATE TEMP TABLE ").append(tempTableName);
            insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
            db.execSQL(insertTableStringBuilder.toString());
        }
    }

    private static void dropAllTables(
            StandardDatabase db,
            boolean ifExists,
            @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
    }

    private static void createAllTables(
            StandardDatabase db,
            boolean ifNotExists,
            @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    private static void reflectMethod(
            StandardDatabase db,
            String methodName,
            boolean isExists,
            @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        if (daoClasses.length < 1) {
            return;
        }
        try {
            for (Class<?> cls : daoClasses) {
                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, db, isExists);
            }
        } catch (Exception e) {
            log_e(null, e);
        }
    }

    private static void restoreData(
            StandardDatabase db,
            Class<? extends AbstractDao<?, ?>>... daoClasses
    ) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig     = new DaoConfig(db, daoClasses[i]);
            String    tableName     = daoConfig.tablename;
            String    tempTableName = daoConfig.tablename.concat("_TEMP");
            // get all columns from tempTable, take careful to use the columns list
            List<String>      columns    = getColumns(db, tempTableName);
            ArrayList<String> properties = new ArrayList<>(columns.size());
            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                if (columns.contains(columnName)) {
                    properties.add(columnName);
                }
            }
            if (properties.size() > 0) {
                final String columnSQL = TextUtils.join(",", properties);

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                db.execSQL(insertTableStringBuilder.toString());
            }
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private static List<String> getColumns(
            StandardDatabase db,
            String tableName
    ) {
        List<String> columns = null;
        Cursor       cursor  = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            log_e(null, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (null == columns) {
                columns = new ArrayList<>();
            }
        }
        return columns;
    }
}