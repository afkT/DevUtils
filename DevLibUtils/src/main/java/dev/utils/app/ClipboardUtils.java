package dev.utils.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 剪贴板相关工具类
 * @author Ttt
 */
public final class ClipboardUtils {

    private ClipboardUtils() {
    }

    // 日志 TAG
    private static final String TAG = ClipboardUtils.class.getSimpleName();

    /**
     * 复制文本到剪贴板
     * @param text
     */
    public static void copyText(final CharSequence text) {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 复制的数据
            ClipData clipData = ClipData.newPlainText("text", text);
            // 设置复制的数据
            clipManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "copyText");
        }
    }

    /**
     * 获取剪贴板的文本
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = clipManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).coerceToText(DevUtils.getContext());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getText");
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     * @param uri
     */
    public static void copyUri(final Uri uri) {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 复制的数据
            ClipData clipData = ClipData.newUri(DevUtils.getContext().getContentResolver(), "", uri);
            // 设置复制的数据
            clipManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "copyUri");
        }
    }

    /**
     * 获取剪贴板的uri
     * @return 剪贴板的uri
     */
    public static Uri getUri() {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = clipManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).getUri();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUri");
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     * @param intent 意图
     */
    public static void copyIntent(final Intent intent) {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            // 复制的数据
            ClipData clipData = ClipData.newIntent("intent", intent);
            // 设置复制的数据
            clipManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "copyIntent");
        }
    }

    /**
     * 获取剪贴板的意图
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        try {
            ClipboardManager clipManager = (ClipboardManager) DevUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = clipManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).getIntent();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIntent");
        }
        return null;
    }
}
