package dev.utils.app;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: DownloadManager 工具类
 * @author Ttt
 */
public final class DownloadUtils {

    private DownloadUtils() {
    }

    // 日志 TAG
    private static final String TAG = DownloadUtils.class.getSimpleName();

    // ============
    // = 游标拦截器 =
    // ============

    /**
     * detail: 游标拦截器 ( 用于数据自定义处理 )
     * @author Ttt
     */
    public interface OnCursorInterceptor {

        /**
         * 拦截器回调方法
         * @param cursor Cursor
         * @param map    单条下载信息存储集合
         */
        void intercept(
                Cursor cursor,
                Map<String, Object> map
        );
    }

    // =======================
    // = DownloadManager 方法 =
    // =======================

    /**
     * 加入队列进行下载
     * @param request 请求下载信息
     * @return 下载 ID
     */
    public static long enqueue(final DownloadManager.Request request) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null && request != null) {
            try {
                return downloadManager.enqueue(request);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "enqueue");
            }
        }
        return -1L;
    }

    /**
     * 移除下载
     * @param ids 待移除的下载 ID
     * @return 实际删除下载总数
     */
    public static int remove(final long... ids) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null && ids != null && ids.length != 0) {
            try {
                return downloadManager.remove(ids);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "remove");
            }
        }
        return -1;
    }

    /**
     * 查询下载信息
     * @param query 查询配置
     * @return 下载信息
     */
    public static Cursor query(final DownloadManager.Query query) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null && query != null) {
            try {
                return downloadManager.query(query);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "query");
            }
        }
        return null;
    }

    /**
     * 打开下载文件 ( 下载成功才能打开 )
     * <pre>
     *     通过 new FileInputStream(openDownloadedFile().getFileDescriptor()) 进行文件操作
     * </pre>
     * @param id 下载 ID
     * @return 下载文件 ParcelFileDescriptor
     */
    public static ParcelFileDescriptor openDownloadedFile(final long id) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null) {
            try {
                return downloadManager.openDownloadedFile(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "openDownloadedFile");
            }
        }
        return null;
    }

    /**
     * 获取下载文件 Uri
     * <pre>
     *     通过 {@link ResourceUtils#openInputStream(Uri)} 进行文件操作
     * </pre>
     * @param id 下载 ID
     * @return 下载成功则返回 Uri, 否则返回 null
     */
    public static Uri getUriForDownloadedFile(final long id) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null) {
            try {
                return downloadManager.getUriForDownloadedFile(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getUriForDownloadedFile");
            }
        }
        return null;
    }

    /**
     * 获取下载文件 MimeType 类型
     * @param id 下载 ID
     * @return 下载成功则返回 MimeType, 否则返回 null
     */
    public static String getMimeTypeForDownloadedFile(final long id) {
        DownloadManager downloadManager = AppUtils.getDownloadManager();
        if (downloadManager != null) {
            try {
                return downloadManager.getMimeTypeForDownloadedFile(id);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMimeTypeForDownloadedFile");
            }
        }
        return null;
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 通过下载 ID 创建 Query 查询配置对象
     * @param id 下载 ID
     * @return 查询配置对象
     */
    public static DownloadManager.Query createQueryById(final long id) {
        return new DownloadManager.Query().setFilterById(id);
    }

    /**
     * 通过下载状态创建 Query 查询配置对象
     * <pre>
     *     调用 {@link #query(DownloadManager.Query)} 可能会返回多条
     *     需要自行循环 Cursor 读取所需数据
     * </pre>
     * @param flags 下载状态
     * @return 查询配置对象
     */
    public static DownloadManager.Query createQueryByStatus(final int flags) {
        return new DownloadManager.Query().setFilterByStatus(flags);
    }

    // =

    /**
     * 移除下载结果 ( 不对比 ids 数量和成功数量 )
     * @param ids 待移除的下载 ID
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeResult(final long... ids) {
        if (ids != null && ids.length != 0) {
            int count = remove(ids);
            return count > 0;
        }
        return false;
    }

    /**
     * 移除下载结果 ( 对比 ids 数量和成功数量 )
     * @param ids 待移除的下载 ID
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeResultEqual(final long... ids) {
        if (ids != null && ids.length != 0) {
            int count = remove(ids);
            return count == ids.length;
        }
        return false;
    }

    // =======================
    // = 查询方法 - 通过下载 ID =
    // =======================

    /**
     * 查询下载地址 ( 通过下载 ID )
     * <pre>
     *     转换 Uri 通过 {@link UriUtils#ofUri(String)}
     * </pre>
     * @param id 下载 ID
     * @return 下载地址
     */
    public static String queryUriById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getStringByNameThrows(
                        cursor, DownloadManager.COLUMN_URI
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryUriById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }

    /**
     * 查询下载进度信息 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载进度信息[2], [0] = 当前进度, [1] = 总大小
     */
    public static long[] queryBytesById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                long progress = CursorUtils.getLongByNameThrows(
                        cursor, DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR
                );
                long total = CursorUtils.getLongByNameThrows(
                        cursor, DownloadManager.COLUMN_TOTAL_SIZE_BYTES
                );
                return new long[]{progress, total};
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryBytesById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return new long[]{-1L, -1L};
    }

    /**
     * 查询下载状态 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载状态
     */
    public static int queryStatusById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getIntByNameThrows(
                        cursor, DownloadManager.COLUMN_STATUS
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryStatusById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return -1;
    }

    /**
     * 查询下载失败原因 ( 通过下载 ID )
     * <pre>
     *     自行根据 DownloadManager.ERROR_XXX 进行判断失败原因
     * </pre>
     * @param id 下载 ID
     * @return 下载失败原因
     */
    public static int queryReasonById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getIntByNameThrows(
                        cursor, DownloadManager.COLUMN_REASON
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryReasonById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return -1;
    }

    /**
     * 查询下载文件类型 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载文件类型
     */
    public static String queryMediaTypeById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getStringByNameThrows(
                        cursor, DownloadManager.COLUMN_MEDIA_TYPE
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryMediaTypeById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }

    /**
     * 查询最近下载变更时间 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 最近下载变更时间
     */
    public static long queryLastModifiedById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getLongByNameThrows(
                        cursor, DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryLastModifiedById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return -1L;
    }

    /**
     * 查询下载标题 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载标题
     */
    public static String queryTitleById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getStringByNameThrows(
                        cursor, DownloadManager.COLUMN_TITLE
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryTitleById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }

    /**
     * 查询下载描述 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载描述
     */
    public static String queryDescriptionById(final long id) {
        Cursor cursor = query(createQueryById(id));
        if (cursor != null) {
            try {
                return CursorUtils.getStringByNameThrows(
                        cursor, DownloadManager.COLUMN_DESCRIPTION
                );
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryDescriptionById");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }

    // ====================
    // = 游标查询 - 单条数据 =
    // ====================

    /**
     * 查询单条下载信息 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 单条下载信息
     */
    public static Map<String, Object> queryCursorSingleById(final long id) {
        return queryCursorSingleById(id, null);
    }

    /**
     * 查询单条下载信息 ( 通过下载 ID )
     * @param id          下载 ID
     * @param interceptor 游标拦截器
     * @return 单条下载信息
     */
    public static Map<String, Object> queryCursorSingleById(
            final long id,
            final OnCursorInterceptor interceptor
    ) {
        Cursor cursor = query(createQueryById(id));
        return queryCursorSingle(cursor, interceptor);
    }

    /**
     * 查询单条下载信息
     * @param cursor Cursor
     * @return 单条下载信息
     */
    public static Map<String, Object> queryCursorSingle(final Cursor cursor) {
        return queryCursorSingle(cursor, null);
    }

    /**
     * 查询单条下载信息
     * @param cursor      Cursor
     * @param interceptor 游标拦截器
     * @return 单条下载信息
     */
    public static Map<String, Object> queryCursorSingle(
            final Cursor cursor,
            final OnCursorInterceptor interceptor
    ) {
        return _queryCursorSingle(cursor, interceptor, true);
    }

    /**
     * 查询单条下载信息 ( 内部方法 )
     * @param cursor      Cursor
     * @param interceptor 游标拦截器
     * @param closeCursor 是否关闭游标
     * @return 单条下载信息
     */
    public static Map<String, Object> _queryCursorSingle(
            final Cursor cursor,
            final OnCursorInterceptor interceptor,
            final boolean closeCursor
    ) {
        if (cursor != null) {
            try {
                // 下载 ID
                int id = CursorUtils.getIntByName(
                        cursor, DownloadManager.COLUMN_ID
                );
                // 下载地址
                String uriString = CursorUtils.getStringByName(
                        cursor, DownloadManager.COLUMN_URI
                );
                // 当前已经下载进度
                long progress = CursorUtils.getLongByName(
                        cursor, DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR
                );
                // 文件总大小
                long total = CursorUtils.getLongByName(
                        cursor, DownloadManager.COLUMN_TOTAL_SIZE_BYTES
                );
                // 下载状态
                int status = CursorUtils.getIntByName(
                        cursor, DownloadManager.COLUMN_STATUS
                );
                // 下载失败原因
                int reason = CursorUtils.getIntByName(
                        cursor, DownloadManager.COLUMN_REASON
                );
                // 下载文件类型
                String mediaType = CursorUtils.getStringByName(
                        cursor, DownloadManager.COLUMN_MEDIA_TYPE
                );
                // 最近下载变更时间
                long lastModified = CursorUtils.getLongByName(
                        cursor, DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP
                );
                // 下载标题
                String title = CursorUtils.getStringByName(
                        cursor, DownloadManager.COLUMN_TITLE
                );
                // 下载描述
                String description = CursorUtils.getStringByName(
                        cursor, DownloadManager.COLUMN_DESCRIPTION
                );
                // 存储对应信息
                Map<String, Object> map = new LinkedHashMap<>();
                map.put(DownloadManager.COLUMN_ID, id);
                map.put(DownloadManager.COLUMN_URI, uriString);
                map.put(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR, progress);
                map.put(DownloadManager.COLUMN_TOTAL_SIZE_BYTES, total);
                map.put(DownloadManager.COLUMN_STATUS, status);
                map.put(DownloadManager.COLUMN_REASON, reason);
                map.put(DownloadManager.COLUMN_MEDIA_TYPE, mediaType);
                map.put(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP, lastModified);
                map.put(DownloadManager.COLUMN_TITLE, title);
                map.put(DownloadManager.COLUMN_DESCRIPTION, description);
                // 游标拦截器 ( 用于数据自定义处理 )
                if (interceptor != null) {
                    try {
                        interceptor.intercept(cursor, map);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "_queryCursorSingle - intercept");
                    }
                }
                return map;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "_queryCursorSingle");
            } finally {
                if (closeCursor) {
                    CursorUtils.closeIOQuietly(cursor);
                }
            }
        }
        return null;
    }

    // ====================
    // = 游标查询 - 多条数据 =
    // ====================

    /**
     * 查询多条下载信息
     * @param query 查询配置
     * @return 多条下载信息
     */
    public static List<Map<String, Object>> queryCursorMultipleByQuery(
            final DownloadManager.Query query
    ) {
        return queryCursorMultipleByQuery(query, null);
    }

    /**
     * 查询多条下载信息
     * @param query       查询配置
     * @param interceptor 游标拦截器
     * @return 多条下载信息
     */
    public static List<Map<String, Object>> queryCursorMultipleByQuery(
            final DownloadManager.Query query,
            final OnCursorInterceptor interceptor
    ) {
        Cursor cursor = query(query);
        return queryCursorMultiple(cursor, interceptor);
    }

    /**
     * 查询多条下载信息
     * @param cursor Cursor
     * @return 多条下载信息
     */
    public static List<Map<String, Object>> queryCursorMultiple(final Cursor cursor) {
        return queryCursorMultiple(cursor, null);
    }

    /**
     * 查询多条下载信息
     * @param cursor      Cursor
     * @param interceptor 游标拦截器
     * @return 多条下载信息
     */
    public static List<Map<String, Object>> queryCursorMultiple(
            final Cursor cursor,
            final OnCursorInterceptor interceptor
    ) {
        if (cursor != null) {
            try {
                List<Map<String, Object>> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    // 读取当前索引游标数据
                    Map<String, Object> map = _queryCursorSingle(
                            cursor, interceptor, false
                    );
                    if (map != null) list.add(map);
                }
                if (list.isEmpty()) return null;
                return list;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryCursorMultiple");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }

    // ==========
    // = 状态方法 =
    // ==========

    /**
     * 是否有效下载状态 ( 判断非 -1 )
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isValidStatus(final int status) {
        return status != -1;
    }

    /**
     * 是否等待下载状态
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPendingStatus(final int status) {
        return DownloadManager.STATUS_PENDING == status;
    }

    /**
     * 是否下载中状态
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRunningStatus(final int status) {
        return DownloadManager.STATUS_RUNNING == status;
    }

    /**
     * 是否暂停下载状态
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPausedStatus(final int status) {
        return DownloadManager.STATUS_PAUSED == status;
    }

    /**
     * 是否下载成功状态
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSuccessfulStatus(final int status) {
        return DownloadManager.STATUS_SUCCESSFUL == status;
    }

    /**
     * 是否下载失败状态
     * @param status 当前状态值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFailedStatus(final int status) {
        return DownloadManager.STATUS_FAILED == status;
    }

    // =======================
    // = 状态方法 - 通过下载 ID =
    // =======================

    /**
     * 是否有效下载状态 ( 判断非 -1 )
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isValidStatusById(final long id) {
        return isValidStatus(queryStatusById(id));
    }

    /**
     * 是否等待下载状态
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPendingStatusById(final long id) {
        return isPendingStatus(queryStatusById(id));
    }

    /**
     * 是否下载中状态
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRunningStatusById(final long id) {
        return isRunningStatus(queryStatusById(id));
    }

    /**
     * 是否暂停下载状态
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPausedStatusById(final long id) {
        return isPausedStatus(queryStatusById(id));
    }

    /**
     * 是否下载成功状态
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSuccessfulStatusById(final long id) {
        return isSuccessfulStatus(queryStatusById(id));
    }

    /**
     * 是否下载失败状态
     * @param id 下载 ID
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFailedStatusById(final long id) {
        return isFailedStatus(queryStatusById(id));
    }
}