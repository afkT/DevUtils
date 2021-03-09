package dev.engine.permission;

import android.app.Activity;
import android.content.Context;

import java.util.List;

/**
 * detail: Permission Engine 接口
 * @author Ttt
 */
public interface IPermissionEngine {

    // ===============
    // = 请求权限回调 =
    // ===============

    /**
     * detail: 权限请求回调
     * @author Ttt
     */
    interface Callback {

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

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 判断是否授予了权限
     * @param context     {@link Context}
     * @param permissions 待判断权限
     * @return {@code true} yes, {@code false} no
     */
    boolean isGranted(
            Context context,
            String... permissions
    );

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
    boolean shouldShowRequestPermissionRationale(
            Activity activity,
            String... permissions
    );

    /**
     * 获取拒绝权限询问状态集合
     * @param activity    {@link Activity}
     * @param shouldShow  {@code true} 没有勾选不再询问, {@code false} 勾选了不再询问
     * @param permissions 待判断权限
     * @return 拒绝权限询问状态集合
     */
    List<String> getDeniedPermissionStatus(
            Activity activity,
            boolean shouldShow,
            String... permissions
    );

    // ===============
    // = 权限请求方法 =
    // ===============

    /**
     * 请求权限
     * @param activity    {@link Activity}
     * @param permissions 待申请权限
     */
    void request(
            Activity activity,
            String[] permissions
    );

    /**
     * 请求权限
     * @param activity    {@link Activity}
     * @param permissions 待申请权限
     * @param callback    权限请求回调
     */
    void request(
            Activity activity,
            String[] permissions,
            Callback callback
    );
}