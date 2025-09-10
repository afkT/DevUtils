package dev.engine.permission;

import android.app.Activity;
import android.content.Context;

import java.util.List;

/**
 * detail: Permission Engine 接口
 * @author Ttt
 */
public interface IPermissionEngine {

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
    }

    // =============
    // = 请求权限回调 =
    // =============

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

    // =============
    // = 对外公开方法 =
    // =============

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

    /**
     * 再次请求处理操作
     * <pre>
     *     如果存在拒绝了且不再询问则跳转到应用设置页面
     *     否则则再次请求拒绝的权限
     * </pre>
     * @param activity   {@link Activity}
     * @param callback   权限请求回调
     * @param deniedList 申请未通过的权限集合
     * @return 0 不符合要求无任何操作、1 再次请求操作、2  跳转到应用设置页面
     */
    int againRequest(
            Activity activity,
            Callback callback,
            List<String> deniedList
    );

    // =============
    // = 权限请求方法 =
    // =============

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

    /**
     * 请求权限
     * <pre>
     *     againRequest 参数为 true 则会调用 {@link #againRequest(Activity, Callback, List)}
     * </pre>
     * @param activity     {@link Activity}
     * @param permissions  待申请权限
     * @param callback     权限请求回调
     * @param againRequest 如果失败是否再次请求
     */
    void request(
            Activity activity,
            String[] permissions,
            Callback callback,
            boolean againRequest
    );
}