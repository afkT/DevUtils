package dev.utils.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 广播相关工具类
 * @author Ttt
 */
public final class ReceiverUtils {

    private ReceiverUtils() {
    }

    // 日志 TAG
    private static final String TAG = ReceiverUtils.class.getSimpleName();

    // ============
    // = 应用内广播 =
    // ============

    /**
     * 获取 LocalBroadcastManager
     * @return {@link LocalBroadcastManager}
     */
    public static LocalBroadcastManager getLocalBroadcastManager() {
        return getLocalBroadcastManager(DevUtils.getContext());
    }

    /**
     * 获取 LocalBroadcastManager
     * @param context Context
     * @return {@link LocalBroadcastManager}
     */
    public static LocalBroadcastManager getLocalBroadcastManager(final Context context) {
        if (context == null) return null;
        try {
            return LocalBroadcastManager.getInstance(context);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLocalBroadcastManager");
        }
        return null;
    }

    // =

    /**
     * 注册广播监听 ( 应用内广播 )
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_registerReceiver(
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        return local_registerReceiver(
                getLocalBroadcastManager(),
                receiver, filter
        );
    }

    /**
     * 注册广播监听 ( 应用内广播 )
     * @param localBroadcastManager {@link LocalBroadcastManager}
     * @param receiver              {@link BroadcastReceiver}
     * @param filter                {@link IntentFilter}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_registerReceiver(
            final LocalBroadcastManager localBroadcastManager,
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        if (receiver == null) return false;
        if (localBroadcastManager == null) return false;
        try {
            localBroadcastManager.registerReceiver(receiver, filter);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "local_registerReceiver");
        }
        return false;
    }

    /**
     * 注销广播监听 ( 应用内广播 )
     * @param receiver {@link BroadcastReceiver}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_unregisterReceiver(
            final BroadcastReceiver receiver
    ) {
        return local_unregisterReceiver(getLocalBroadcastManager(), receiver);
    }

    /**
     * 注销广播监听 ( 应用内广播 )
     * @param localBroadcastManager {@link LocalBroadcastManager}
     * @param receiver              {@link BroadcastReceiver}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_unregisterReceiver(
            final LocalBroadcastManager localBroadcastManager,
            final BroadcastReceiver receiver
    ) {
        if (receiver == null) return false;
        if (localBroadcastManager == null) return false;
        try {
            localBroadcastManager.unregisterReceiver(receiver);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "local_unregisterReceiver");
        }
        return false;
    }

    /**
     * 发送广播 ( 应用内广播 )
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_sendBroadcast(
            final Intent intent
    ) {
        return local_sendBroadcast(getLocalBroadcastManager(), intent);
    }

    /**
     * 发送广播 ( 应用内广播 )
     * @param localBroadcastManager {@link LocalBroadcastManager}
     * @param intent                {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_sendBroadcast(
            final LocalBroadcastManager localBroadcastManager,
            final Intent intent
    ) {
        if (intent == null) return false;
        if (localBroadcastManager == null) return false;
        try {
            localBroadcastManager.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "local_sendBroadcast");
        }
        return false;
    }

    /**
     * 发送广播 ( 同步 ) ( 应用内广播 )
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_sendBroadcastSync(
            final Intent intent
    ) {
        return local_sendBroadcastSync(getLocalBroadcastManager(), intent);
    }

    /**
     * 发送广播 ( 同步 ) ( 应用内广播 )
     * @param localBroadcastManager {@link LocalBroadcastManager}
     * @param intent                {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean local_sendBroadcastSync(
            final LocalBroadcastManager localBroadcastManager,
            final Intent intent
    ) {
        if (intent == null) return false;
        if (localBroadcastManager == null) return false;
        try {
            localBroadcastManager.sendBroadcastSync(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "local_sendBroadcastSync");
        }
        return false;
    }

    // =======
    // = 广播 =
    // =======

    /**
     * 注册广播监听
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean registerReceiverBool(
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        return registerReceiverBool(DevUtils.getContext(), receiver, filter);
    }

    /**
     * 注册广播监听
     * @param context  {@link Context}
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean registerReceiverBool(
            final Context context,
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
            } else {
                context.registerReceiver(receiver, filter);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiverBool");
        }
        return false;
    }

    /**
     * 注册广播监听
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @param flags    Additional options for the receiver. For apps targeting
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean registerReceiverBool(
            final BroadcastReceiver receiver,
            final IntentFilter filter,
            final int flags
    ) {
        return registerReceiverBool(DevUtils.getContext(), receiver, filter, flags);
    }

    /**
     * 注册广播监听
     * @param context  {@link Context}
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @param flags    Additional options for the receiver. For apps targeting
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean registerReceiverBool(
            final Context context,
            final BroadcastReceiver receiver,
            final IntentFilter filter,
            final int flags
    ) {
        try {
            context.registerReceiver(receiver, filter, flags);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiverBool");
        }
        return false;
    }

    // =

    /**
     * 注册广播监听
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return 粘性 Intent
     */
    public static Intent registerReceiver(
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        return registerReceiver(DevUtils.getContext(), receiver, filter);
    }

    /**
     * 注册广播监听
     * @param context  {@link Context}
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return 粘性 Intent
     */
    public static Intent registerReceiver(
            final Context context,
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                return context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
            } else {
                return context.registerReceiver(receiver, filter);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
        return null;
    }

    /**
     * 注册广播监听
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @param flags    Additional options for the receiver. For apps targeting
     * @return 粘性 Intent
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Intent registerReceiver(
            final BroadcastReceiver receiver,
            final IntentFilter filter,
            final int flags
    ) {
        return registerReceiver(DevUtils.getContext(), receiver, filter, flags);
    }

    /**
     * 注册广播监听
     * @param context  {@link Context}
     * @param receiver {@link BroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @param flags    Additional options for the receiver. For apps targeting
     * @return 粘性 Intent
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Intent registerReceiver(
            final Context context,
            final BroadcastReceiver receiver,
            final IntentFilter filter,
            final int flags
    ) {
        try {
            return context.registerReceiver(receiver, filter, flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
        return null;
    }

    /**
     * 注销广播监听
     * @param receiver {@link BroadcastReceiver}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean unregisterReceiver(final BroadcastReceiver receiver) {
        return unregisterReceiver(DevUtils.getContext(), receiver);
    }

    /**
     * 注销广播监听
     * @param context  {@link Context}
     * @param receiver {@link BroadcastReceiver}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean unregisterReceiver(
            final Context context,
            final BroadcastReceiver receiver
    ) {
        if (context == null || receiver == null) return false;
        try {
            context.unregisterReceiver(receiver);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
        return false;
    }

    // ==========
    // = 发送广播 =
    // ==========

    /**
     * 发送广播 ( 无序 )
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(final Intent intent) {
        return sendBroadcast(DevUtils.getContext(), intent);
    }

    /**
     * 发送广播 ( 无序 )
     * @param context {@link Context}
     * @param intent  {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(
            final Context context,
            final Intent intent
    ) {
        if (context == null || intent == null) return false;
        try {
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendBroadcast");
        }
        return false;
    }

    /**
     * 发送广播 ( 无序 )
     * @param intent             {@link Intent}
     * @param receiverPermission 广播权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(
            final Intent intent,
            final String receiverPermission
    ) {
        return sendBroadcast(DevUtils.getContext(), intent, receiverPermission);
    }

    /**
     * 发送广播 ( 无序 )
     * @param context            {@link Context}
     * @param intent             {@link Intent}
     * @param receiverPermission 广播权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(
            final Context context,
            final Intent intent,
            final String receiverPermission
    ) {
        if (context == null || intent == null || receiverPermission == null) return false;
        try {
            context.sendBroadcast(intent, receiverPermission);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendBroadcast");
        }
        return false;
    }

    /**
     * 发送广播 ( 有序 )
     * @param intent             {@link Intent}
     * @param receiverPermission 广播权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendOrderedBroadcast(
            final Intent intent,
            final String receiverPermission
    ) {
        return sendOrderedBroadcast(DevUtils.getContext(), intent, receiverPermission);
    }

    /**
     * 发送广播 ( 有序 )
     * @param context            {@link Context}
     * @param intent             {@link Intent}
     * @param receiverPermission 广播权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendOrderedBroadcast(
            final Context context,
            final Intent intent,
            final String receiverPermission
    ) {
        if (context == null || intent == null || receiverPermission == null) return false;
        try {
            context.sendOrderedBroadcast(intent, receiverPermission);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendOrderedBroadcast");
        }
        return false;
    }
}