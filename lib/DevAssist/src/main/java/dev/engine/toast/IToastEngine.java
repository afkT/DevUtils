package dev.engine.toast;

import android.app.Application;

/**
 * detail: Toast Engine 接口
 * @author Ttt
 */
public interface IToastEngine<Config extends IToastEngine.EngineConfig,
        Item extends IToastEngine.EngineItem> {

    /**
     * detail: Toast Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Toast ( Data、Params ) Item
     * @author Ttt
     */
    class EngineItem {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param application {@link Application}
     */
    void initialize(Application application);

    /**
     * 判断 Toast 框架是否已经初始化
     * @return {@code true} yes, {@code false} no
     */
    boolean isInit();

    /**
     * 设置是否为调试模式
     * @param debug 是否为调试模式
     */
    void setDebugMode(boolean debug);

    /**
     * 设置 Toast Config
     * @param config Toast Config
     */
    void setConfig(Config config);

    /**
     * 获取 Toast Config
     * @return Toast Config
     */
    Config getConfig();

    // =============
    // = Toast 方法 =
    // =============

    /**
     * 取消 Toast 的显示
     */
    void cancel();

    /**
     * 延迟显示 Toast
     * @param id          字符串资源 ID
     * @param delayMillis 延迟的毫秒数
     */
    void delayedShow(
            int id,
            long delayMillis
    );

    /**
     * 延迟显示 Toast
     * @param object      要显示的对象
     * @param delayMillis 延迟的毫秒数
     */
    void delayedShow(
            Object object,
            long delayMillis
    );

    /**
     * 延迟显示 Toast
     * @param text        要显示的文本
     * @param delayMillis 延迟的毫秒数
     */
    void delayedShow(
            CharSequence text,
            long delayMillis
    );

    /**
     * debug 模式下显示 Toast
     * @param id 字符串资源 ID
     */
    void debugShow(int id);

    /**
     * debug 模式下显示 Toast
     * @param object 要显示的对象
     */
    void debugShow(Object object);

    /**
     * debug 模式下显示 Toast
     * @param text 要显示的文本
     */
    void debugShow(CharSequence text);

    /**
     * 显示一个短 Toast
     * @param id 字符串资源 ID
     */
    void showShort(int id);

    /**
     * 显示一个短 Toast
     * @param object 要显示的对象
     */
    void showShort(Object object);

    /**
     * 显示一个短 Toast
     * @param text 要显示的文本
     */
    void showShort(CharSequence text);

    /**
     * 显示一个长 Toast
     * @param id 字符串资源 ID
     */
    void showLong(int id);

    /**
     * 显示一个长 Toast
     * @param object 要显示的对象
     */
    void showLong(Object object);

    /**
     * 显示一个长 Toast
     * @param text 要显示的文本
     */
    void showLong(CharSequence text);

    /**
     * 显示 Toast
     * @param id 字符串资源 ID
     */
    void show(int id);

    /**
     * 显示 Toast
     * @param object 要显示的对象
     */
    void show(Object object);

    /**
     * 显示 Toast
     * @param text 要显示的文本
     */
    void show(CharSequence text);

    /**
     * 显示 Toast
     * @param params Toast 参数
     */
    void show(Item params);

    // ============
    // = Toast UI =
    // ============

    /**
     * 给当前 Toast 设置新的布局
     * @param id 布局资源 ID
     */
    void setView(int id);

    /**
     * 设置 Toast 的位置
     * @param gravity 重心
     */
    void setGravity(int gravity);

    /**
     * 设置 Toast 的位置
     * @param gravity 重心
     * @param xOffset X 轴偏移量
     * @param yOffset Y 轴偏移量
     */
    void setGravity(
            int gravity,
            int xOffset,
            int yOffset
    );

    /**
     * 设置 Toast 的位置
     * @param gravity          重心
     * @param xOffset          X 轴偏移量
     * @param yOffset          Y 轴偏移量
     * @param horizontalMargin 水平边距
     * @param verticalMargin   垂直边距
     */
    void setGravity(
            int gravity,
            int xOffset,
            int yOffset,
            float horizontalMargin,
            float verticalMargin
    );
}