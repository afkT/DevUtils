package dev.engine.permission;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import dev.utils.app.permission.PermissionUtils;

/**
 * detail: DevUtils Permission Engine 实现
 * @author Ttt
 */
public class DevPermissionEngineImpl
        implements IPermissionEngine {

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public boolean isGranted(
            Context context,
            String... permissions
    ) {
        return PermissionUtils.isGranted(permissions);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(
            Activity activity,
            String... permissions
    ) {
        return PermissionUtils.shouldShowRequestPermissionRationale(
                activity, permissions
        );
    }

    @Override
    public List<String> getDeniedPermissionStatus(
            Activity activity,
            boolean shouldShow,
            String... permissions
    ) {
        return PermissionUtils.getDeniedPermissionStatus(
                activity, shouldShow, permissions
        );
    }

    @Override
    public int againRequest(
            Activity activity,
            Callback callback,
            List<String> deniedList
    ) {
        return PermissionUtils.againRequest(activity, new PermissionUtils.PermissionCallback() {
            @Override
            public void onGranted() {
                if (callback != null) {
                    callback.onGranted();
                }
            }

            @Override
            public void onDenied(
                    List<String> grantedList,
                    List<String> deniedList,
                    List<String> notFoundList
            ) {
                if (callback != null) {
                    callback.onDenied(grantedList, deniedList, notFoundList);
                }
            }
        }, deniedList);
    }

    // =============
    // = 权限请求方法 =
    // =============

    @Override
    public void request(
            Activity activity,
            String[] permissions
    ) {
        request(activity, permissions, null);
    }

    @Override
    public void request(
            Activity activity,
            String[] permissions,
            Callback callback
    ) {
        PermissionUtils.permission(permissions).callback(new PermissionUtils.PermissionCallback() {
            @Override
            public void onGranted() {
                if (callback != null) {
                    callback.onGranted();
                }
            }

            @Override
            public void onDenied(
                    List<String> grantedList,
                    List<String> deniedList,
                    List<String> notFoundList
            ) {
                if (callback != null) {
                    callback.onDenied(grantedList, deniedList, notFoundList);
                }
            }
        }).request(activity);
    }
}