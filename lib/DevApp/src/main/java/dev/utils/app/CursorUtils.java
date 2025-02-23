package dev.utils.app;

import android.database.Cursor;

import java.io.Closeable;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: Cursor 游标工具类
 * @author Ttt
 * <pre>
 *     部分方法为组合快捷使用, 内部并不进行 Cursor 判空, 在使用前自行 Cursor 校验
 * </pre>
 */
public final class CursorUtils {

    private CursorUtils() {
    }

    // 日志 TAG
    private static final String TAG = CursorUtils.class.getSimpleName();

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 关闭 IO
     * @param closeables Closeable[]
     */
    public static void closeIO(final Closeable... closeables) {
        CloseUtils.closeIO(closeables);
    }

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        CloseUtils.closeIOQuietly(closeables);
    }

    // ==========
    // = 通用方法 =
    // ==========

    /**
     * 对应游标是否存在数据
     * @param cursor Cursor
     * @return {@code true} yes, {@code false} no
     */
    public static boolean existsCount(final Cursor cursor) {
        return cursor != null && cursor.getCount() > 0;
    }

    /**
     * 获取对应列名的索引
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 给定列名从零开始的列索引, 如果列名不存在, 则返回 -1
     */
    public static int getColumnIndex(
            final Cursor cursor,
            final String columnName
    ) {
        if (columnName == null) return -1;
        return cursor.getColumnIndex(columnName);
    }

    /**
     * 获取对应列索引列名
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引列名
     */
    public static String getColumnName(
            final Cursor cursor,
            final int columnIndex
    ) {
        if (columnIndex >= 0) {
            try {
                return cursor.getColumnName(columnIndex);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getColumnName");
            }
        }
        return DevFinal.DEFAULT.ERROR_STRING;
    }

    /**
     * 获取所有列名
     * @param cursor Cursor
     * @return 返回所有列名
     */
    public static String[] getColumnNames(final Cursor cursor) {
        return cursor.getColumnNames();
    }

    /**
     * 获取所有列数量
     * @param cursor Cursor
     * @return 返回所有列数量
     */
    public static int getColumnCount(final Cursor cursor) {
        return cursor.getColumnCount();
    }

    /**
     * 判断对应列索引值是否为 null
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNull(
            final Cursor cursor,
            final int columnIndex
    ) {
        try {
            if (columnIndex >= 0) return cursor.isNull(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isNull");
        }
        return true;
    }

    /**
     * 判断对应列名的值是否为 null
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNullByName(
            final Cursor cursor,
            final String columnName
    ) {
        int index = getColumnIndex(cursor, columnName);
        return isNull(cursor, index);
    }

    /**
     * 获取对应列索引值类型
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值类型
     */
    public static int getType(
            final Cursor cursor,
            final int columnIndex
    ) {
        try {
            if (columnIndex >= 0) return cursor.getType(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getType");
        }
        return Cursor.FIELD_TYPE_NULL;
    }

    /**
     * 获取对应列名值类型
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列名值类型
     */
    public static int getTypeByName(
            final Cursor cursor,
            final String columnName
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getType(cursor, index);
    }

    // ============
    // = 获取值操作 =
    // ============

    // ===============
    // = columnIndex =
    // ===============

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static int getInt(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getInt(cursor, columnIndex, DevFinal.DEFAULT.ERROR_INT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static long getLong(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getLong(cursor, columnIndex, DevFinal.DEFAULT.ERROR_LONG);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static float getFloat(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getFloat(cursor, columnIndex, DevFinal.DEFAULT.ERROR_FLOAT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static double getDouble(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getDouble(cursor, columnIndex, DevFinal.DEFAULT.ERROR_DOUBLE);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static String getString(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getString(cursor, columnIndex, DevFinal.DEFAULT.ERROR_STRING);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static short getShort(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getShort(cursor, columnIndex, DevFinal.DEFAULT.ERROR_SHORT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static byte[] getBlob(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getBlob(cursor, columnIndex, null);
    }

    // =

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static int getInt(
            final Cursor cursor,
            final int columnIndex,
            final int defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getInt(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInt");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static long getLong(
            final Cursor cursor,
            final int columnIndex,
            final long defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getLong(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLong");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static float getFloat(
            final Cursor cursor,
            final int columnIndex,
            final float defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getFloat(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFloat");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static double getDouble(
            final Cursor cursor,
            final int columnIndex,
            final double defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getDouble(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDouble");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static String getString(
            final Cursor cursor,
            final int columnIndex,
            final String defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getString(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static short getShort(
            final Cursor cursor,
            final int columnIndex,
            final short defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getShort(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getShort");
        }
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static byte[] getBlob(
            final Cursor cursor,
            final int columnIndex,
            final byte[] defaultValue
    ) {
        try {
            if (columnIndex >= 0) return cursor.getBlob(columnIndex);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBlob");
        }
        return defaultValue;
    }

    // ==============
    // = columnName =
    // ==============

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static int getIntByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getIntByName(cursor, columnName, DevFinal.DEFAULT.ERROR_INT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static long getLongByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getLongByName(cursor, columnName, DevFinal.DEFAULT.ERROR_LONG);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static float getFloatByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getFloatByName(cursor, columnName, DevFinal.DEFAULT.ERROR_FLOAT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static double getDoubleByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getDoubleByName(cursor, columnName, DevFinal.DEFAULT.ERROR_DOUBLE);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static String getStringByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getStringByName(cursor, columnName, DevFinal.DEFAULT.ERROR_STRING);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static short getShortByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getShortByName(cursor, columnName, DevFinal.DEFAULT.ERROR_SHORT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static byte[] getBlobByName(
            final Cursor cursor,
            final String columnName
    ) {
        return getBlobByName(cursor, columnName, null);
    }

    // =

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static int getIntByName(
            final Cursor cursor,
            final String columnName,
            final int defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getInt(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static long getLongByName(
            final Cursor cursor,
            final String columnName,
            final long defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getLong(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static float getFloatByName(
            final Cursor cursor,
            final String columnName,
            final float defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getFloat(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static double getDoubleByName(
            final Cursor cursor,
            final String columnName,
            final double defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getDouble(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static String getStringByName(
            final Cursor cursor,
            final String columnName,
            final String defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getString(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static short getShortByName(
            final Cursor cursor,
            final String columnName,
            final short defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getShort(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static byte[] getBlobByName(
            final Cursor cursor,
            final String columnName,
            final byte[] defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getBlob(cursor, index, defaultValue);
    }

    // ======================
    // = 获取值操作 - 抛出异常 =
    // ======================

    // ===============
    // = columnIndex =
    // ===============

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static int getIntThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getIntThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_INT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static long getLongThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getLongThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_LONG);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static float getFloatThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getFloatThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_FLOAT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static double getDoubleThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getDoubleThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_DOUBLE);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static String getStringThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getStringThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_STRING);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static short getShortThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getShortThrows(cursor, columnIndex, DevFinal.DEFAULT.ERROR_SHORT);
    }

    /**
     * 获取对应列索引值
     * @param cursor      Cursor
     * @param columnIndex 对应列索引
     * @return 对应列索引值
     */
    public static byte[] getBlobThrows(
            final Cursor cursor,
            final int columnIndex
    ) {
        return getBlobThrows(cursor, columnIndex, null);
    }

    // =

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static int getIntThrows(
            final Cursor cursor,
            final int columnIndex,
            final int defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getInt(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static long getLongThrows(
            final Cursor cursor,
            final int columnIndex,
            final long defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getLong(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static float getFloatThrows(
            final Cursor cursor,
            final int columnIndex,
            final float defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getFloat(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static double getDoubleThrows(
            final Cursor cursor,
            final int columnIndex,
            final double defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getDouble(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static String getStringThrows(
            final Cursor cursor,
            final int columnIndex,
            final String defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getString(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static short getShortThrows(
            final Cursor cursor,
            final int columnIndex,
            final short defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getShort(columnIndex);
        return defaultValue;
    }

    /**
     * 获取对应列索引值
     * @param cursor       Cursor
     * @param columnIndex  对应列索引
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static byte[] getBlobThrows(
            final Cursor cursor,
            final int columnIndex,
            final byte[] defaultValue
    ) {
        if (columnIndex >= 0) return cursor.getBlob(columnIndex);
        return defaultValue;
    }

    // ==============
    // = columnName =
    // ==============

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static int getIntByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getIntByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_INT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static long getLongByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getLongByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_LONG);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static float getFloatByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getFloatByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_FLOAT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static double getDoubleByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getDoubleByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_DOUBLE);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static String getStringByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getStringByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_STRING);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static short getShortByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getShortByNameThrows(cursor, columnName, DevFinal.DEFAULT.ERROR_SHORT);
    }

    /**
     * 获取对应列名值
     * @param cursor     Cursor
     * @param columnName 目标列的名字
     * @return 对应列索引值
     */
    public static byte[] getBlobByNameThrows(
            final Cursor cursor,
            final String columnName
    ) {
        return getBlobByNameThrows(cursor, columnName, null);
    }

    // =

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static int getIntByNameThrows(
            final Cursor cursor,
            final String columnName,
            final int defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getIntThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static long getLongByNameThrows(
            final Cursor cursor,
            final String columnName,
            final long defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getLongThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static float getFloatByNameThrows(
            final Cursor cursor,
            final String columnName,
            final float defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getFloatThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static double getDoubleByNameThrows(
            final Cursor cursor,
            final String columnName,
            final double defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getDoubleThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static String getStringByNameThrows(
            final Cursor cursor,
            final String columnName,
            final String defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getStringThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static short getShortByNameThrows(
            final Cursor cursor,
            final String columnName,
            final short defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getShortThrows(cursor, index, defaultValue);
    }

    /**
     * 获取对应列名值
     * @param cursor       Cursor
     * @param columnName   目标列的名字
     * @param defaultValue 默认值
     * @return 对应列索引值
     */
    public static byte[] getBlobByNameThrows(
            final Cursor cursor,
            final String columnName,
            final byte[] defaultValue
    ) {
        int index = getColumnIndex(cursor, columnName);
        return getBlobThrows(cursor, index, defaultValue);
    }
}