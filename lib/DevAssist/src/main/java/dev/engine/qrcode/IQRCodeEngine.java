package dev.engine.qrcode;

import android.app.Application;
import android.content.Context;

/**
 * detail: QRCode Engine 接口
 * @author Ttt
 */
public interface IQRCodeEngine<Config extends IQRCodeEngine.EngineConfig> {

    /**
     * detail: QRCode Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Storage Result
     * @author Ttt
     */
    class EngineResult {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param application {@link Application}
     * @param config      QRCode Config
     */
    void initialize(
            Application application,
            Config config
    );

    /**
     * 绑定
     * @param context {@link Context}
     * @param config  QRCode Config
     */
    void register(
            Context context,
            Config config
    );

    /**
     * 解绑
     * @param context {@link Context}
     * @param config  QRCode Config
     */
    void unregister(
            Context context,
            Config config
    );
}