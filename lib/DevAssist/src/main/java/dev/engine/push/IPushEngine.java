package dev.engine.push;

import android.app.Application;
import android.content.Context;

/**
 * detail: Push Engine 接口
 * @author Ttt
 */
public interface IPushEngine<Config extends IPushEngine.EngineConfig,
        Item extends IPushEngine.EngineItem> {

    /**
     * detail: Push Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    /**
     * detail: Push ( Data、Params ) Item
     * @author Ttt
     */
    interface EngineItem {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param application {@link Application}
     * @param config      Push Config
     */
    void initialize(
            Application application,
            Config config
    );

    /**
     * 绑定
     * @param context {@link Context}
     * @param config  Push Config
     */
    void register(
            Context context,
            Config config
    );

    /**
     * 解绑
     * @param context {@link Context}
     * @param config  Push Config
     */
    void unregister(
            Context context,
            Config config
    );

    // =

    /**
     * 推送进程启动通知
     * @param context {@link Context}
     * @param pid     Push 进程 ID
     */
    void onReceiveServicePid(
            Context context,
            int pid
    );

    /**
     * 初始化 Client Id 成功通知
     * @param context  {@link Context}
     * @param clientId 唯一 ID 用于标识当前应用
     */
    void onReceiveClientId(
            Context context,
            String clientId
    );

    /**
     * 设备 ( 厂商 ) Token 通知
     * @param context     {@link Context}
     * @param deviceToken 设备 Token
     */
    void onReceiveDeviceToken(
            Context context,
            String deviceToken
    );

    /**
     * 在线状态变化通知
     * @param context {@link Context}
     * @param online  是否在线
     */
    void onReceiveOnlineState(
            Context context,
            boolean online
    );

    /**
     * 命令回执通知
     * @param context {@link Context}
     * @param message Push ( Data、Params ) Item
     */
    void onReceiveCommandResult(
            Context context,
            Item message
    );

    /**
     * 推送消息送达通知
     * @param context {@link Context}
     * @param message Push ( Data、Params ) Item
     */
    void onNotificationMessageArrived(
            Context context,
            Item message
    );

    /**
     * 推送消息点击通知
     * @param context {@link Context}
     * @param message Push ( Data、Params ) Item
     */
    void onNotificationMessageClicked(
            Context context,
            Item message
    );

    /**
     * 透传消息送达通知
     * @param context {@link Context}
     * @param message Push ( Data、Params ) Item
     */
    void onReceiveMessageData(
            Context context,
            Item message
    );

    // ===============
    // = 转换 Message =
    // ===============

    /**
     * 传入 Object 转换 Engine Message
     * @param message Message Object
     * @return Engine Message
     */
    Item convertMessage(Object message);
}