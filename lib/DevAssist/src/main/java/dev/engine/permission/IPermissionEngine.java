package dev.engine.permission;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * detail: Permission Engine 接口
 * @author Ttt
 * <pre>
 *     @see <a href="https://github.com/getActivity/XXPermissions"/>
 *     以 XXPermissions 接口设计为基准, 如无特殊需求建议直接使用 XXPermissions
 * </pre>
 */
public interface IPermissionEngine<Config extends IPermissionEngine.EngineConfig,
        Item extends IPermissionEngine.EngineItem> {

    /**
     * detail: Permission Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    /**
     * detail: Permission Item
     * @author Ttt
     */
    interface EngineItem {

        /**
         * 获取权限的名称
         */
        @NonNull
        String permissionName();
    }

    // =============
    // = 请求权限回调 =
    // =============

    /**
     * detail: 权限请求回调
     * @author Ttt
     */
    interface Callback<Item extends IPermissionEngine.EngineItem> {

        /**
         * 权限请求结果回调
         * @param grantedList 授予权限列表
         * @param deniedList  拒绝权限列表
         */
        void onResult(
                @NonNull List<Item> grantedList,
                @NonNull List<Item> deniedList
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ==========
    // = 权限请求 =
    // ==========

    /**
     * 请求权限
     * @param activity   {@link Activity}
     * @param permission 待申请权限
     * @param callback   权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Activity activity,
            @NonNull Item permission,
            @NonNull Callback<Item> callback
    );

    /**
     * 请求权限
     * @param activity    {@link Activity}
     * @param permissions 待申请权限
     * @param callback    权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Activity activity,
            @NonNull List<Item> permissions,
            @NonNull Callback<Item> callback
    );

    // =

    /**
     * 请求权限
     * @param fragment   {@link Fragment}
     * @param permission 待申请权限
     * @param callback   权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Fragment fragment,
            @NonNull Item permission,
            @NonNull Callback<Item> callback
    );

    /**
     * 请求权限
     * @param fragment    {@link Fragment}
     * @param permissions 待申请权限
     * @param callback    权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Fragment fragment,
            @NonNull List<Item> permissions,
            @NonNull Callback<Item> callback
    );

    // ===================
    // = 权限请求 ( 配置 ) =
    // ===================

    /**
     * 请求权限
     * @param config     {@link Config}
     * @param activity   {@link Activity}
     * @param permission 待申请权限
     * @param callback   权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Config config,
            @NonNull Activity activity,
            @NonNull Item permission,
            @NonNull Callback<Item> callback
    );

    /**
     * 请求权限
     * @param config      {@link Config}
     * @param activity    {@link Activity}
     * @param permissions 待申请权限
     * @param callback    权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Config config,
            @NonNull Activity activity,
            @NonNull List<Item> permissions,
            @NonNull Callback<Item> callback
    );

    // =

    /**
     * 请求权限
     * @param config     {@link Config}
     * @param fragment   {@link Fragment}
     * @param permission 待申请权限
     * @param callback   权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Config config,
            @NonNull Fragment fragment,
            @NonNull Item permission,
            @NonNull Callback<Item> callback
    );

    /**
     * 请求权限
     * @param config      {@link Config}
     * @param fragment    {@link Fragment}
     * @param permissions 待申请权限
     * @param callback    权限请求回调
     * @return {@code true} success, {@code false} fail
     */
    boolean request(
            @NonNull Config config,
            @NonNull Fragment fragment,
            @NonNull List<Item> permissions,
            @NonNull Callback<Item> callback
    );

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否授予了权限
     * @param context    {@link Context}
     * @param permission 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean isGrantedPermission(
            @NonNull Context context,
            @NonNull Item permission
    );

    /**
     * 判断是否授予了权限
     * @param context     {@link Context}
     * @param permissions 待判断权限列表
     * @return {@code true} yes, {@code false} no
     */
    boolean isGrantedPermissions(
            @NonNull Context context,
            @NonNull List<Item> permissions
    );

    // =

    /**
     * 获取已经授予的权限
     * @param context     {@link Context}
     * @param permissions 待判断权限列表
     * @return 已经授予的权限集合
     */
    List<Item> getGrantedPermissions(
            @NonNull Context context,
            @NonNull List<Item> permissions
    );

    /**
     * 获取已经拒绝的权限
     * @param context     {@link Context}
     * @param permissions 待判断权限列表
     * @return 已经拒绝的权限集合
     */
    List<Item> getDeniedPermissions(
            @NonNull Context context,
            @NonNull List<Item> permissions
    );

    // =

    /**
     * 获取拒绝权限询问勾选状态
     * @param activity   {@link Activity}
     * @param permission 待判断权限
     * @return {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
     */
    boolean isDoNotAskAgainPermission(
            @NonNull Activity activity,
            @NonNull Item permission
    );

    /**
     * 获取拒绝权限询问勾选状态
     * @param activity    {@link Activity}
     * @param permissions 待判断权限列表
     * @return {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
     */
    boolean isDoNotAskAgainPermissions(
            @NonNull Activity activity,
            @NonNull List<Item> permissions
    );

    // =

    /**
     * 判断两个权限是否相等
     * @param permission  待判断权限
     * @param permission2 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsPermission(
            @NonNull Item permission,
            @NonNull Item permission2
    );

    /**
     * 判断两个权限是否相等
     * @param permission     待判断权限
     * @param permissionName 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsPermission(
            @NonNull Item permission,
            @NonNull String permissionName
    );

    /**
     * 判断两个权限是否相等
     * @param permissionName1 待判断权限
     * @param permissionName2 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsPermission(
            @NonNull String permissionName1,
            @NonNull String permissionName2
    );

    // =

    /**
     * 判断权限列表中是否包含某个权限
     * @param permissions 待判断权限列表
     * @param permission  待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean containsPermission(
            @NonNull List<Item> permissions,
            @NonNull Item permission
    );

    /**
     * 判断权限列表中是否包含某个权限
     * @param permissions    待判断权限列表
     * @param permissionName 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean containsPermission(
            @NonNull List<Item> permissions,
            @NonNull String permissionName
    );
}