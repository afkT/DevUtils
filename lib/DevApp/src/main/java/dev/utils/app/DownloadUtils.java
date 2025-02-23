package dev.utils.app;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

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
    public ParcelFileDescriptor openDownloadedFile(final long id) {
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
    public Uri getUriForDownloadedFile(final long id) {
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
    public String getMimeTypeForDownloadedFile(final long id) {
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

    // ==========
    // = 查询方法 =
    // ==========

    /**
     * 查询下载状态 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载状态
     */
    public static int queryStatusById(final long id) {
        return queryStatus(createQueryById(id));
    }

    /**
     * 查询下载状态
     * @param query 查询配置
     * @return 下载状态
     */
    public static int queryStatus(final DownloadManager.Query query) {
        Cursor cursor = query(query);
        if (cursor != null) {
            try {
                return CursorUtils.getIntByNameThrows(cursor, DownloadManager.COLUMN_STATUS);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryStatus");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return -1;
    }

    /**
     * 查询下载进度信息 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载进度信息[2], [0] = 当前进度, [1] = 总大小
     */
    public static long[] queryBytesById(final long id) {
        return queryBytes(createQueryById(id));
    }

    /**
     * 查询下载进度信息
     * @param query 查询配置
     * @return 下载进度信息[2], [0] = 当前进度, [1] = 总大小
     */
    public static long[] queryBytes(final DownloadManager.Query query) {
        Cursor cursor = query(query);
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
                LogPrintUtils.eTag(TAG, e, "queryBytes");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return new long[]{-1L, -1L};
    }

    /**
     * 查询下载状态信息 ( 通过下载 ID )
     * @param id 下载 ID
     * @return 下载状态信息 ( 例如失败原因 )
     */
    public static String queryReasonById(final long id) {
        return queryReason(createQueryById(id));
    }

    /**
     * 查询下载状态信息
     * @param query 查询配置
     * @return 下载状态信息 ( 例如失败原因 )
     */
    public static String queryReason(final DownloadManager.Query query) {
        Cursor cursor = query(query);
        if (cursor != null) {
            try {
                return CursorUtils.getStringByNameThrows(cursor, DownloadManager.COLUMN_REASON);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "queryReason");
            } finally {
                CursorUtils.closeIOQuietly(cursor);
            }
        }
        return null;
    }
}