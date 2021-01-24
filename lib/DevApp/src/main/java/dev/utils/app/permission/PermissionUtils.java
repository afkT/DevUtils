package dev.utils.app.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.IntentUtils;
import dev.utils.app.info.AppInfoUtils;
import dev.utils.common.CollectionUtils;

/**
 * detail: 权限请求工具类
 * @author Ttt
 */
public final class PermissionUtils {

    // 日志 TAG
    private static final String TAG = PermissionUtils.class.getSimpleName();

    /**
     * 判断是否授予了权限
     * @param permissions 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isGranted(final String... permissions) {
        // 防止数据为 null
        if (permissions != null && permissions.length != 0) {
            // 遍历全部需要申请的权限
            for (String permission : permissions) {
                if (!isGranted(DevUtils.getContext(), permission)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 判断是否授予了权限
     * @param context    {@link Context}
     * @param permission 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isGranted(
            final Context context,
            final String permission
    ) {
        if (context == null || permission == null) return false;
        // SDK 版本小于 23 则表示直接通过 || 检查是否通过权限
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                PermissionChecker.PERMISSION_GRANTED == PermissionChecker.checkSelfPermission(context, permission);
    }

    /**
     * 获取拒绝权限询问勾选状态
     * <pre>
     *     拒绝过一次, 再次申请时, 弹出选择进行拒绝, 获取询问勾选状态
     *     true 表示没有勾选不再询问, 而 false 则表示勾选了不再询问
     * </pre>
     * @param activity    {@link Activity}
     * @param permissions 待判断权限
     * @return {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
     */
    public static boolean shouldShowRequestPermissionRationale(
            final Activity activity,
            final String... permissions
    ) {
        if (activity == null || permissions == null) return false;
        boolean shouldShowRequestPermissionRationale = false; // 表示勾选了不再询问
        for (String permission : permissions) {
            if (permission != null && !isGranted(activity, permission)) {
                shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
                if (!shouldShowRequestPermissionRationale) return false;
            }
        }
        return shouldShowRequestPermissionRationale;
    }

    /**
     * 获取拒绝权限询问状态集合
     * @param activity    {@link Activity}
     * @param shouldShow  {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
     * @param permissions 待判断权限
     * @return 拒绝权限询问状态集合
     */
    public static List<String> getDeniedPermissionStatus(
            final Activity activity,
            final boolean shouldShow,
            final String... permissions
    ) {
        if (activity == null || permissions == null) return Collections.EMPTY_LIST;
        Set<String> sets = new HashSet<>();
        for (String permission : permissions) {
            if (permission != null && !sets.contains(permission) && !isGranted(activity, permission)) {
                boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
                if (shouldShow == shouldShowRequestPermissionRationale) {
                    sets.add(permission);
                }
            }
        }
        return new ArrayList<>(sets);
    }

    /**
     * 是否存在 APK 安装权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean canRequestPackageInstalls() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                return AppUtils.getPackageManager().canRequestPackageInstalls();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "canRequestPackageInstalls");
            }
            return false;
        }
        return true;
    }

    /**
     * 获取全部权限
     * @return {@link Set} 全部权限
     */
    public static Set<String> getAllPermissionToSet() {
        Set<String> permissionSets = new HashSet<>();
        Field[]     fields         = Manifest.permission.class.getFields();
        for (Field field : fields) {
            try {
                String name = (String) field.get("");
                permissionSets.add(name);
            } catch (Exception e) {
            }
        }
        return permissionSets;
    }

    /**
     * 获取全部权限
     * @return {@link List} 全部权限
     */
    public static List<String> getAllPermissionToList() {
        return new ArrayList<>(getAllPermissionToSet());
    }

    // ================
    // = AppInfoUtils =
    // ================

    /**
     * 获取 APP 注册的权限
     * @return APP 注册的权限
     */
    public static List<String> getAppPermissionToList() {
        return AppInfoUtils.getAppPermissionToList();
    }

    /**
     * 获取 APP 注册的权限
     * @return APP 注册的权限
     */
    public static Set<String> getAppPermissionToSet() {
        return AppInfoUtils.getAppPermissionToSet();
    }

    /**
     * 获取 APP 注册的权限
     * @return APP 注册的权限数组
     */
    public static String[] getAppPermission() {
        return AppInfoUtils.getAppPermission();
    }

    /**
     * 获取 APP 注册的权限
     * @param packageName 应用包名
     * @return APP 注册的权限数组
     */
    public static String[] getAppPermission(final String packageName) {
        return AppInfoUtils.getAppPermission(packageName);
    }

    // ===========
    // = 权限申请 =
    // ===========

    // APP 注册的权限
    private static final Set<String>        sAppPermissionSets             = getAppPermissionToSet();
    // 申请未通过的权限 ( 永久拒绝 )
    private static final List<String>       sPermissionsDeniedForeverLists = new ArrayList<>();
    // 申请的权限 ( 传入的权限参数 )
    private final        Set<String>        mPermissionSets                = new HashSet<>();
    // 准备请求的权限
    private final        List<String>       mPermissionsRequestLists       = new ArrayList<>();
    // 申请通过的权限
    private final        List<String>       mPermissionsGrantedLists       = new ArrayList<>();
    // 申请未通过的权限
    private final        List<String>       mPermissionsDeniedLists        = new ArrayList<>();
    // 查询不到的权限 ( 包含未注册 )
    private final        List<String>       mPermissionsNotFoundLists      = new ArrayList<>();
    // 操作回调
    private              PermissionCallback mCallback;
    // 回调方法 Handler
    private final        Handler            mHandler                       = new Handler(Looper.getMainLooper());
    // 判断是否请求过
    private              boolean            mIsRequest                     = false;
    // 是否需要在 Activity 的 onRequestPermissionsResult 回调中, 调用 PermissionUtils.onRequestPermissionsResult(this);
    private              boolean            mIsRequestPermissionsResult    = false; // 默认使用内部 PermissionActivity
    // Permission 请求 Code
    public static final  int                P_REQUEST_CODE                 = 10101;

    /**
     * 构造函数
     * @param permissions 待申请权限
     */
    private PermissionUtils(final String... permissions) {
        mPermissionSets.clear();
        // 防止数据为 null
        if (permissions != null && permissions.length != 0) {
            // 遍历全部需要申请的权限
            for (String permission : permissions) {
                if (!TextUtils.isEmpty(permission)) {
                    mPermissionSets.add(permission);
                }
            }
        }
    }

    // ===========
    // = 使用方法 =
    // ===========

    /**
     * 申请权限初始化
     * @param permissions 待申请权限
     * @return {@link PermissionUtils}
     */
    public static PermissionUtils permission(final String... permissions) {
        return new PermissionUtils(permissions);
    }

    /**
     * 设置回调方法
     * @param callback {@link PermissionCallback}
     * @return {@link PermissionUtils}
     */
    public PermissionUtils callback(final PermissionCallback callback) {
        if (mIsRequest) return this;
        this.mCallback = callback;
        return this;
    }

    /**
     * 设置是否需要在 Activity 的 onRequestPermissionsResult 回调中, 调用 PermissionUtils.onRequestPermissionsResult(this);
     * @param requestPermissionsResult {@code true} yes, {@code false} no
     * @return {@link PermissionUtils}
     */
    public PermissionUtils setRequestPermissionsResult(final boolean requestPermissionsResult) {
        if (mIsRequest) return this;
        this.mIsRequestPermissionsResult = requestPermissionsResult;
        return this;
    }

    /**
     * 请求权限
     * @param activity {@link Activity}
     */
    public void request(final Activity activity) {
        request(activity, P_REQUEST_CODE);
    }

    /**
     * 请求权限
     * @param activity    {@link Activity}
     * @param requestCode 请求 code
     */
    public void request(
            final Activity activity,
            final int requestCode
    ) {
        if (checkPermissions(activity) == 1) {
            // 如果 SDK 版本大于 23 才请求
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                sInstance = this;
                // 请求权限
                String[] permissions = mPermissionsRequestLists.toArray(new String[mPermissionsRequestLists.size()]);
                // 判断请求方式
                if (this.mIsRequestPermissionsResult) {
                    // 请求权限
                    ActivityCompat.requestPermissions(activity, permissions, requestCode);
                } else {
                    // 自定义权限 Activity
                    PermissionActivity.start(activity);
                }
            }
        }
    }

    // ===============
    // = 请求权限回调 =
    // ===============

    /**
     * detail: 权限请求回调
     * @author Ttt
     */
    public interface PermissionCallback {

        /**
         * 授权通过权限回调
         */
        void onGranted();

        /**
         * 授权未通过权限回调
         * <pre>
         *     判断 deniedList 申请未通过的权限中拒绝状态
         *     可通过 {@link #getDeniedPermissionStatus(Activity, boolean, String...)} 进行获取
         *     第二个参数 shouldShow ( boolean )
         *     {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
         * </pre>
         * @param grantedList  申请通过的权限
         * @param deniedList   申请未通过的权限
         * @param notFoundList 查询不到的权限 ( 包含未注册 )
         */
        void onDenied(
                List<String> grantedList,
                List<String> deniedList,
                List<String> notFoundList
        );
    }

    // ================
    // = 内部 Activity =
    // ================

    // 内部持有对象
    private static PermissionUtils sInstance;

    /**
     * detail: 请求权限 Activity
     * @author Ttt
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class PermissionActivity
            extends Activity {

        /**
         * 跳转 PermissionActivity 请求权限 内部方法
         * @param context {@link Context}
         */
        protected static void start(final Context context) {
            Intent starter = new Intent(context, PermissionActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(starter);
        }

        /**
         * PermissionActivity ( onCreate 内部方法 )
         * @param savedInstanceState 关闭时存储数据
         */
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 请求权限
            int size = sInstance.mPermissionsRequestLists.size();
            requestPermissions(sInstance.mPermissionsRequestLists.toArray(new String[size]), 1);
        }

        /**
         * 请求权限回调
         * @param requestCode  请求 code
         * @param permissions  请求权限
         * @param grantResults 权限授权结果
         */
        @Override
        public void onRequestPermissionsResult(
                int requestCode,
                @NonNull String[] permissions,
                @NonNull int[] grantResults
        ) {
            sInstance.onRequestPermissionsResultCommon(this); // 处理回调
            finish(); // 关闭当前页面
        }
    }

    /**
     * 请求回调权限回调处理
     * @param activity {@link Activity}
     */
    private void onRequestPermissionsResultCommon(final Activity activity) {
        // 获取权限状态
        getPermissionsStatus(activity);
        // 判断请求结果
        requestCallback();
    }

    /**
     * 请求权限回调 ( 需要在 Activity 的 onRequestPermissionsResult 回调中, 调用 PermissionUtils.onRequestPermissionsResult(this); )
     * @param activity {@link Activity}
     */
    public static void onRequestPermissionsResult(final Activity activity) {
        if (activity != null && sInstance != null) { // 触发回调
            sInstance.onRequestPermissionsResultCommon(activity);
        }
    }

    /**
     * 刷新权限改变处理 ( 清空已拒绝的权限记录 )
     */
    public static void notifyPermissionsChange() {
        sPermissionsDeniedForeverLists.clear();
    }

    /**
     * 再次请求处理操作
     * <pre>
     *     如果存在拒绝了且不再询问则跳转到应用设置页面
     *     否则则再次请求拒绝的权限
     * </pre>
     * @param activity   {@link Activity}
     * @param callback   {@link PermissionCallback}
     * @param deniedList 申请未通过的权限集合
     * @return 0 不符合要求无任何操作、1 再次请求操作、2  跳转到应用设置页面
     */
    public static int againRequest(
            final Activity activity,
            final PermissionCallback callback,
            final List<String> deniedList
    ) {
        if (activity == null || CollectionUtils.isEmpty(deniedList)) return 0;
        // 获取拒绝的权限记录
        String[] deniedArys = deniedList.toArray(new String[deniedList.size()]);
        // 获取拒绝权限询问勾选状态 true 表示没有勾选不再询问, 而 false 则表示勾选了不再询问
        if (PermissionUtils.shouldShowRequestPermissionRationale(activity, deniedArys)) { // 再次请求
            PermissionUtils.permission(deniedArys).callback(callback).request(activity);
            return 1;
        } else { // 拒绝权限且不再询问, 跳转到应用设置页面
            AppUtils.startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent());
            return 2;
        }
    }

    // ===============
    // = 内部处理方法 =
    // ===============

    /**
     * 权限判断处理
     * @param activity {@link Activity}
     * @return -1 已经请求 ( 中 ) 过, 0 = 不处理 ( 通知回调 ), 1 = 需要请求
     */
    private int checkPermissions(final Activity activity) {
        if (activity == null) {
            // 处理请求回调
            requestCallback();
            // 不处理
            return 0;
        }
        if (mIsRequest) {
            return -1; // 已经请求 ( 中 ) 过
        }
        mIsRequest = true;
        // 如果 SDK 版本小于 23 则直接通过
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // 表示全部权限都通过
            mPermissionsGrantedLists.addAll(mPermissionSets);
            // 处理请求回调
            requestCallback();
        } else {
            for (String permission : mPermissionSets) {
                // 首先判断是否存在
                if (sAppPermissionSets.contains(permission)) {
                    // 判断是否通过请求
                    if (isGranted(activity, permission)) {
                        mPermissionsGrantedLists.add(permission); // 权限允许通过
                        // 如果原本已经永久拒绝现在通过, 则移除
                        if (sPermissionsDeniedForeverLists.contains(permission)) { // 移除永久拒绝的权限记录
                            CollectionUtils.remove(sPermissionsDeniedForeverLists, permission);
                        }
                    } else { // 判断是否已拒绝但可再次请求
                        if (!sPermissionsDeniedForeverLists.contains(permission)) { // 不存在, 则进行保存
                            mPermissionsRequestLists.add(permission); // 准备请求权限
                        }
                    }
                } else {
                    // 保存到没找到的权限集合
                    mPermissionsNotFoundLists.add(permission);
                }
            }
            // 判断是否存在等待请求的权限
            if (mPermissionsRequestLists.isEmpty()) {
                // 处理请求回调
                requestCallback();
            } else { // 表示需要申请
                return 1;
            }
        }
        return 0;
    }

    /**
     * 内部请求回调, 统一处理方法
     */
    private void requestCallback() {
        if (mCallback != null) {
            // 判断是否授权全部权限
            boolean isGrantedAll = (mPermissionSets.size() == mPermissionsGrantedLists.size());
            // 允许则触发回调
            if (isGrantedAll) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onGranted();
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onDenied(mPermissionsGrantedLists, mPermissionsDeniedLists, mPermissionsNotFoundLists);
                    }
                });
            }
        }
    }

    /**
     * 获取权限状态
     * @param activity {@link Activity}
     */
    private void getPermissionsStatus(final Activity activity) {
        for (String permission : mPermissionsRequestLists) {
            // 判断是否通过请求
            if (isGranted(activity, permission)) {
                mPermissionsGrantedLists.add(permission);
            } else {
                // 未授权
                mPermissionsDeniedLists.add(permission);
                // 拒绝权限并不再询问
                if (!shouldShowRequestPermissionRationale(activity, permission)) {
                    sPermissionsDeniedForeverLists.add(permission);
                }
            }
        }
    }
}