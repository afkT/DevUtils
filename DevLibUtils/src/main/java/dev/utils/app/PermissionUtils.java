package dev.utils.app;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.DevUtils;

/**
 * detail: 权限请求工具类
 * @author Ttt
 * <pre>
 *      参考:
 *      @see <a href="https://github.com/anthonycr/Grant"/>
 *      compile 'com.anthonycr.grant:permissions:1.0'
 *      <p></p>
 *      权限介绍
 *      @see <a href="https://www.cnblogs.com/mengdd/p/4892856.html"/>
 *      <p></p>
 *      第三方库:
 *      @see <a href="https://github.com/hotchemi/PermissionsDispatcher">PermissionsDispatcher</a>
 *      @see <a href="https://github.com/tbruyelle/RxPermissions">RxPermissions</a>
 *      @see <a href="https://github.com/anthonycr/Grant">Grant</a>
 *      <p></p>
 *      使用方法:
 *      第一种请求方式
 *      PermissionUtils.permission("").callBack(null).request();
 *      第二种请求方式 - 需要在 onRequestPermissionsResult 中通知调用
 *      PermissionUtils.permission("").callBack(null).request(Activity);
 *      <p></p>
 *      注意事项: 需要注意在onResume 中调用
 *      不管是第一种方式, 跳自定义的Activity, 还是第二种 系统内部跳转授权页面, 都会多次触发onResume
 *      @see <a href="https://www.aliyun.com/jiaocheng/8030.html"/>
 *      尽量避免在 onResume中调用
 *      com.anthonycr.grant:permissions:1.0 也是会触发onResume 只是 通过 Set<String> mPendingRequests 来控制请求过的权限
 *      拒绝后在onResume 方法内再次请求, 直接触发授权成功, 如果需要清空通过调用 notifyPermissionsChange 通知改变, 否则一直调用获取权限，拒绝过后，都会认为是请求通过
 * </pre>
 */
public final class PermissionUtils {

    // 全部权限
    private static final Set<String> mAllPermissions = new HashSet<>(1);
    // 申请的权限
    private List<String> mPermissions = new ArrayList<>();
    // 准备请求的权限
    private List<String> mPermissionsRequest = new ArrayList<>();
    // 申请通过的权限
    private List<String> mPermissionsGranted = new ArrayList<>();
    // 申请未通过的权限
    private List<String> mPermissionsDenied = new ArrayList<>();
    // 申请未通过的权限 - 永久拒绝
    private List<String> mPermissionsDeniedForever = new ArrayList<>();
    // 查询不到的权限
    private List<String> mPermissionsNotFound = new ArrayList<>();
    // 操作回调
    private PermissionCallBack mCallBack;
    // 回调方法
    private Looper mLooper = Looper.getMainLooper();
    // 判断是否请求过
    private boolean request = false;
    // Permission 请求Code
    public static final int P_REQUEST_CODE = 100;

    static {
        // 初始化权限数据
        initializePermissionsMap();
    }

    /**
     * 初始化遍历保存全部权限
     */
    private static synchronized void initializePermissionsMap() {
        Field[] fields = Manifest.permission.class.getFields();
        for (Field field : fields) {
            String name = null;
            try {
                name = (String) field.get("");
            } catch (IllegalAccessException e) {
            }
            mAllPermissions.add(name);
        }
    }

    // =

    /**
     * 构造函数
     * @param permissions
     */
    private PermissionUtils(final String... permissions) {
        mPermissions.clear();
        // 防止数据为null
        if (permissions != null && permissions.length != 0) {
            // 遍历全部需要申请的权限
            for (String permission : permissions) {
                mPermissions.add(permission);
            }
        }
    }

    // =

    /**
     * 判断是否授予了权限
     * @param permissions
     * @return
     */
    public static boolean isGranted(final String... permissions) {
        // 防止数据为null
        if (permissions != null && permissions.length != 0) {
            // 遍历全部需要申请的权限
            for (String permission : permissions) {
                if (!isGranted(DevUtils.getContext(), permission)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否授予了权限
     * @param context
     * @param permission
     * @return
     */
    private static boolean isGranted(final Context context, final String permission) {
        // SDK 版本小于 23 则表示直接通过 || 检查是否通过权限
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 是否拒绝了权限 - 拒绝过一次, 再次申请时, 弹出选择不再提醒并拒绝才会触发 true
     * @param activity
     * @param permission
     * @return
     */
    public static boolean shouldShowRequestPermissionRationale(final Activity activity, final String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    // ============
    // = 使用方法 =
    // ============

    /**
     * 申请权限初始化
     * @param permissions
     * @return
     */
    public static PermissionUtils permission(final String... permissions) {
        return new PermissionUtils(permissions);
    }

    /**
     * 设置回调方法
     * @param callBack
     */
    public PermissionUtils callBack(final PermissionCallBack callBack) {
        if (request) {
            return this;
        }
        this.mCallBack = callBack;
        return this;
    }

    /**
     * 权限判断处理
     * @return -1 已经请求过, 0 = 不处理, 1 = 需要请求
     */
    private int checkPermissions() {
        if (request) {
            return -1; // 已经申请过
        }
        request = true;
        // 如果 SDK 版本小于 23 则直接通过
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // 表示全部权限都通过
            mPermissionsGranted.addAll(mPermissions);
            // 处理请求回调
            requestCallback();
        } else {
            for (String permission : mPermissions) {
                // 首先判断是否存在
                if (mAllPermissions.contains(permission)) {
                    // 判断是否通过请求
                    if (isGranted(DevUtils.getContext(), permission)) {
                        mPermissionsGranted.add(permission); // 权限允许通过
                    } else {
                        mPermissionsRequest.add(permission); // 准备请求权限
                    }
                } else {
                    // 保存到没找到的权限集合
                    mPermissionsNotFound.add(permission);
                }
            }
            // 判断是否存在等待请求的权限
            if (mPermissionsRequest.isEmpty()) {
                // 处理请求回调
                requestCallback();
            } else { // 表示需要申请
                return 1;
            }
        }
        // 表示不需要申请
        return 0;
    }

    /**
     * 请求权限
     * <p></p>
     * 内部自动调用 PermissionUtils.isGranted, 并且进行判断处理
     * 无需调用以下代码判断
     * boolean isGranted = PermissionUtils.isGranted(Manifest.permission.xx);
     */
    public void request() {
        if (checkPermissions() == 1) {
            // 如果 SDK 版本大于 23 才请求
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                sInstance = this;
                // 自定义Activity
                PermissionUtils.PermissionActivity.start(DevUtils.getContext());
            }
        }
    }

    /**
     * 请求权限
     * @param activity {@link Fragment#getActivity()}
     */
    public void request(final Activity activity) {
        request(activity, P_REQUEST_CODE);
    }

    /**
     * 请求权限 - 需要在Activity 的 onRequestPermissionsResult 回调中 调用 PermissionUtils.onRequestPermissionsResult(this);
     * @param activity    {@link Fragment#getActivity()}
     * @param requestCode
     */
    public void request(final Activity activity, final int requestCode) {
        if (checkPermissions() == 1 && activity != null) {
            // 如果 SDK 版本大于 23 才请求
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                sInstance = this;
                // 请求权限
                String[] permissions = mPermissionsRequest.toArray(new String[mPermissionsRequest.size()]);
                // 请求权限
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }
        }
    }

    // ================
    // = 请求权限回调 =
    // ================

    public interface PermissionCallBack {
        /**
         * 授权通过权限
         * @param permissionUtils
         */
        void onGranted(PermissionUtils permissionUtils);

        /**
         * 授权未通过权限
         * @param permissionUtils
         */
        void onDenied(PermissionUtils permissionUtils);
    }

    // ================
    // = 内部Activity =
    // ================

    // 内部持有对象
    private static PermissionUtils sInstance;

    /**
     * detail: 请求权限 Activity
     * @author Ttt
     * <pre>
     *     实现Activity的透明效果
     *     @see <a href="https://blog.csdn.net/u014434080/article/details/52260407"/>
     * </pre>
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class PermissionActivity extends Activity {

        /**
         * 跳转 PermissionActivity 请求权限 内部方法
         * @param context
         */
        protected static void start(final Context context) {
            Intent starter = new Intent(context, PermissionActivity.class);
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(starter);
        }

        /**
         * PermissionActivity - onCreate 内部方法
         * @param savedInstanceState
         */
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 请求权限
            int size = sInstance.mPermissionsRequest.size();
            requestPermissions(sInstance.mPermissionsRequest.toArray(new String[size]), 1);
        }

        /**
         * 请求权限回调
         * @param requestCode
         * @param permissions
         * @param grantResults
         */
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            sInstance.onRequestPermissionsResultCommon(this); // 处理回调
            finish(); // 关闭当前页面
        }
    }

    // ================
    // = 内部处理方法 =
    // ================

    /**
     * 内部请求回调, 统一处理方法
     */
    private void requestCallback() {
        if (mCallBack != null) {
            // 判断是否允许全部权限
            boolean isGrantedAll = (mPermissions.size() == mPermissionsGranted.size());
            // 允许则触发回调
            if (isGrantedAll) {
                new Handler(mLooper).post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onGranted(PermissionUtils.this);
                    }
                });
            } else {
                new Handler(mLooper).post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onDenied(PermissionUtils.this);
                    }
                });
            }
        }
    }

    /**
     * 请求回调权限回调处理 - 通用
     * @param activity
     */
    private void onRequestPermissionsResultCommon(final Activity activity) {
        // 获取权限状态
        getPermissionsStatus(activity);
        // 判断请求结果
        requestCallback();
    }

    /**
     * 获取权限状态
     * @param activity
     */
    private void getPermissionsStatus(final Activity activity) {
        for (String permission : mPermissionsRequest) {
            // 判断是否通过请求
            if (isGranted(activity, permission)) {
                mPermissionsGranted.add(permission);
            } else {
                // 未授权
                mPermissionsDenied.add(permission);
                // 拒绝权限
                if (!shouldShowRequestPermissionRationale(activity, permission)) {
                    mPermissionsDeniedForever.add(permission);
                }
            }
        }
    }

    // =========================
    // = 通过传入Activity 方式 =
    // =========================

    /**
     * 请求权限回调 - 需要在 onRequestPermissionsResult 回调里面调用
     * @param activity
     */
    public static void onRequestPermissionsResult(final Activity activity) {
        if (activity != null && sInstance != null) {
            // 触发回调
            sInstance.onRequestPermissionsResultCommon(activity);
        }
    }
}