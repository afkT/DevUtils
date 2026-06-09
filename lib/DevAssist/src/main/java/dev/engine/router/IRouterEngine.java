package dev.engine.router;

import android.content.Intent;
import android.os.Bundle;

/**
 * detail: Router Engine 接口
 * @author Ttt
 * <p></p>
 * 页面导航、参数注入、跨模块服务获取与路由拦截
 * <p></p>
 * 方法、参数对齐 com.therouter.TheRouter 与 com.therouter.router.Navigator
 * <pre>
 *     build 系列返回 Object Navigator 句柄
 *     句柄操作方法统一以 Object navigator 作为首参对该句柄进行操作
 *     为保持与第三方库解耦, 第三方专属类型 ( callback、interceptor、parser )
 *     统一以 Object 形式传递
 * </pre>
 */
public interface IRouterEngine<Config extends IRouterEngine.EngineConfig,
        Item extends IRouterEngine.EngineItem> {

    /**
     * detail: Router Config
     * @author Ttt
     */
    interface EngineConfig {

        // 是否为 Debug 环境 ( 映射 TheRouter.isDebug )
        Boolean debug();

        // 是否由框架自动初始化 ( 映射 theRouterUseAutoInit )
        Boolean autoInit();

        // 是否异步初始化 Autowired 注入表 ( 映射 TheRouter.init asyncInitRouterInject )
        Boolean asyncInitRouterInject();
    }

    /**
     * detail: Router ( Data、Params ) Item
     * @author Ttt
     */
    interface EngineItem {

        // 路由 Path / Url
        String path();

        // 跳转请求码
        int requestCode();

        // 是否 pending 等待路由表初始化
        boolean pending();

        // Intent Flags
        int flags();

        // 进入动画资源 id
        int animIn();

        // 退出动画资源 id
        int animOut();

        // 跳转 Context 对象
        Object context();

        // 跳转 Fragment 对象
        Object fragment();

        // Activity Options Bundle 对象
        Object optionsCompat();

        // Intent Data ( Uri ) 对象
        Object intentData();

        // Intent Identifier
        String intentIdentifier();

        // Intent ClipData 对象
        Object intentClipData();

        // 跳转结果回调
        OnNavigationCallback navigationCallback();

        // 路由参数 Bundle
        Bundle extras();

        // Object 类型参数 ( 映射 Navigator.withObject )
        java.util.Map<String, Object> objectParams();
    }

    /**
     * detail: 路由跳转结果回调
     * @author Ttt
     * <p></p>
     * 对齐 com.therouter.router.interceptor.NavigationCallback
     */
    interface OnNavigationCallback {

        /**
         * 找到路由
         * @param navigator Navigator 对象
         */
        void onFound(Object navigator);

        /**
         * 未找到路由
         * @param navigator   Navigator 对象
         * @param requestCode 请求码
         */
        void onLost(
                Object navigator,
                int requestCode
        );

        /**
         * 路由到达
         * @param navigator Navigator 对象
         */
        void onArrival(Object navigator);

        /**
         * Activity 创建完成
         * @param navigator Navigator 对象
         * @param activity  Activity 对象
         */
        void onActivityCreated(
                Object navigator,
                Object activity
        );
    }

    /**
     * detail: 路由日志输出
     * @author Ttt
     * <p></p>
     * 对齐 TheRouter.logCat
     */
    interface OnLogCallback {

        /**
         * 输出日志
         * @param tag 日志 tag
         * @param msg 日志内容
         */
        void log(
                String tag,
                String msg
        );
    }

    /**
     * detail: 路由参数填充
     * @author Ttt
     * <p></p>
     * 对齐 Navigator.fillParams
     */
    interface OnFillParamsListener {

        /**
         * 填充路由参数 Bundle
         * @param extras 参数 Bundle
         */
        void fill(Bundle extras);
    }

    /**
     * detail: Intent 创建回调
     * @author Ttt
     * <p></p>
     * 对齐 Navigator.createIntentWithCallback
     */
    interface OnIntentCallback {

        /**
         * Intent 创建完成
         * @param intent Intent
         */
        void onIntent(Intent intent);
    }

    /**
     * detail: Fragment 创建回调
     * @author Ttt
     * <p></p>
     * 对齐 Navigator.createFragmentWithCallback
     */
    interface OnFragmentCallback {

        /**
         * Fragment 创建完成
         * @param fragment Fragment 对象
         */
        void onFragment(Object fragment);
    }

    /**
     * detail: Url 参数拼接修正
     * @author Ttt
     * <p></p>
     * 对齐 Navigator.getUrlWithParams key-value fix 回调
     */
    interface OnUrlParamsFixListener {

        /**
         * 修正单个 key-value 拼接
         * @param key   参数 key
         * @param value 参数 value
         * @return 拼接后的片段
         */
        String fix(
                String key,
                String value
        );
    }

    /**
     * detail: 路由 AOP 拦截继续回调
     * @author Ttt
     * <p></p>
     * 对齐 RouterInterceptor.InterceptorCallback
     */
    interface OnRouterContinueCallback {

        /**
         * 继续路由
         * @param routeItem 路由项对象
         */
        void onContinue(Object routeItem);
    }

    /**
     * detail: 路由 AOP 拦截器
     * @author Ttt
     * <p></p>
     * 对齐 RouterInterceptor, 用于 setRouterInterceptor 解耦包装
     */
    interface OnRouterInterceptor {

        /**
         * 路由 AOP 处理
         * @param routeItem 路由项对象
         * @param callback  继续回调
         */
        void process(
                Object routeItem,
                OnRouterContinueCallback callback
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Router Engine Config
     * @return Router Config
     */
    Config getConfig();

    /**
     * 设置 Router Engine Config
     * @param config Router Config
     */
    void setConfig(Config config);

    /**
     * 初始化 Router Engine
     * @param config Router Config
     * @return {@code true} success, {@code false} fail
     */
    boolean initialize(Config config);

    /**
     * Router 是否已初始化
     * @return {@code true} yes, {@code false} no
     */
    boolean isInitialized();

    /**
     * 设置是否为 Debug 环境
     * @param debug 是否为 Debug 环境
     */
    void setDebug(boolean debug);

    /**
     * 设置日志输出回调
     * @param callback 日志输出回调
     */
    void setLogCallback(OnLogCallback callback);

    /**
     * 为 Autowired 注解的变量赋值
     * @param target 目标对象
     */
    void inject(Object target);

    /**
     * 判断 url 是否为 TheRouter 的路由 Path
     * @param url 路由 url
     * @return {@code true} yes, {@code false} no
     */
    boolean isRouterPath(String url);

    /**
     * 判断 url 是否为 TheRouter 的 Action
     * @param url 路由 url
     * @return {@code true} yes, {@code false} no
     */
    boolean isRouterAction(String url);

    /**
     * 获取跨模块依赖的服务
     * @param clazz  服务类型
     * @param params 构造参数
     * @param <T>    服务类型
     * @return 服务实例
     */
    <T> T getService(
            Class<T> clazz,
            Object... params
    );

    /**
     * 执行业务自定义 FlowTask
     * @param taskName 任务名
     */
    void runTask(String taskName);

    /**
     * 设置全局默认路由跳转结果回调
     * @param callback 跳转结果回调
     */
    void setDefaultNavigationCallback(OnNavigationCallback callback);

    /**
     * 新增 Path 修改器
     * @param handle Path 修改器对象
     */
    void addNavigatorPathFixHandle(Object handle);

    /**
     * 移除 Path 修改器
     * @param handle Path 修改器对象
     * @return {@code true} success, {@code false} fail
     */
    boolean removeNavigatorPathFixHandle(Object handle);

    /**
     * 新增 Path 替换拦截器
     * @param interceptor Path 替换拦截器对象
     */
    void addPathReplaceInterceptor(Object interceptor);

    /**
     * 移除 Path 替换拦截器
     * @param interceptor Path 替换拦截器对象
     * @return {@code true} success, {@code false} fail
     */
    boolean removePathReplaceInterceptor(Object interceptor);

    /**
     * 新增路由替换拦截器
     * @param interceptor 路由替换拦截器对象
     */
    void addRouterReplaceInterceptor(Object interceptor);

    /**
     * 移除路由替换拦截器
     * @param interceptor 路由替换拦截器对象
     * @return {@code true} success, {@code false} fail
     */
    boolean removeRouterReplaceInterceptor(Object interceptor);

    /**
     * 新增 Action 拦截器
     * @param action      Action
     * @param interceptor Action 拦截器对象
     */
    void addActionInterceptor(
            String action,
            Object interceptor
    );

    /**
     * 移除 Action 拦截器
     * @param action      Action
     * @param interceptor Action 拦截器对象
     */
    void removeActionInterceptor(
            String action,
            Object interceptor
    );

    /**
     * 移除指定 Action 的全部拦截器
     * @param action Action
     */
    void removeAllInterceptorForKey(String action);

    /**
     * 移除指定拦截器 ( 所有 Action 共用 )
     * @param interceptor Action 拦截器对象
     */
    void removeAllInterceptorForValue(Object interceptor);

    /**
     * 新增 Autowired 注解解析器
     * @param parser Autowired 解析器对象
     */
    void addAutowiredParser(Object parser);

    /**
     * 手动初始化 TheRouter
     * @param context Context 对象
     * @return {@code true} success, {@code false} fail
     */
    boolean init(Object context);

    /**
     * 手动初始化 TheRouter
     * @param context              Context 对象
     * @param asyncInitRouterInject 是否异步初始化 Autowired 注入表
     * @return {@code true} success, {@code false} fail
     */
    boolean init(
            Object context,
            boolean asyncInitRouterInject
    );

    /**
     * 设置路由 AOP 拦截器
     * @param interceptor 路由 AOP 拦截器对象 ( RouterInterceptor 或 OnRouterInterceptor )
     */
    void setRouterInterceptor(Object interceptor);

    /**
     * 恢复 pending 状态的 Navigator 跳转
     */
    void sendPendingNavigator();

    /**
     * 获取 Navigator 全局 Object 参数
     * @param key 参数 key
     * @return 参数值
     */
    Object optGlobalObject(String key);

    // =================
    // = 构建 Navigator =
    // =================

    /**
     * 通过 Path 构建 Navigator ( 不跳转 )
     * @param path 路由 Path / Url
     * @return Navigator 对象
     */
    Object build(String path);

    /**
     * 通过 Path 构建 Navigator ( 不跳转 )
     * @param item Router 参数
     * @return Navigator 对象
     */
    Object build(Item item);

    /**
     * 通过 Intent 构建 Navigator ( 不跳转 )
     * @param intent Intent
     * @return Navigator 对象
     */
    Object build(Intent intent);

    // ====================
    // = Navigator 句柄操作 =
    // ====================

    /**
     * 获取带参数的完整 url
     * @param navigator Navigator 对象
     * @return 完整 url
     */
    String getUrlWithParams(Object navigator);

    /**
     * 获取带参数的完整 url
     * @param navigator      Navigator 对象
     * @param paramsFixHandle NavigatorParamsFixHandle 对象
     * @return 完整 url
     */
    String getUrlWithParams(
            Object navigator,
            Object paramsFixHandle
    );

    /**
     * 获取带参数的完整 url
     * @param navigator Navigator 对象
     * @param listener  Url 参数拼接修正
     * @return 完整 url
     */
    String getUrlWithParams(
            Object navigator,
            OnUrlParamsFixListener listener
    );

    /**
     * 获取 Navigator 当前 url
     * @param navigator Navigator 对象
     * @return url
     */
    String getNavigatorUrl(Object navigator);

    /**
     * 获取 Navigator 原始 url
     * @param navigator Navigator 对象
     * @return 原始 url
     */
    String getOriginalUrl(Object navigator);

    /**
     * 获取 Navigator PathFix 后的原始 url
     * @param navigator Navigator 对象
     * @return PathFix 后的原始 url
     */
    String getPathFixOriginalUrl(Object navigator);

    /**
     * 获取 Navigator 简化 url ( 不含 query )
     * @param navigator Navigator 对象
     * @return 简化 url
     */
    String getSimpleUrl(Object navigator);

    /**
     * 获取 Navigator 参数 Bundle
     * @param navigator Navigator 对象
     * @return 参数 Bundle
     */
    Bundle getNavigatorExtras(Object navigator);

    /**
     * 获取 Navigator 关联 Intent
     * @param navigator Navigator 对象
     * @return Intent
     */
    Intent getNavigatorIntent(Object navigator);

    /**
     * 设置 pending 等待路由表初始化
     * @param navigator Navigator 对象
     * @return Navigator 对象
     */
    Object pending(Object navigator);

    /**
     * 设置 Int 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withInt(
            Object navigator,
            String key,
            int value
    );

    /**
     * 设置 Long 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withLong(
            Object navigator,
            String key,
            long value
    );

    /**
     * 设置 Double 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withDouble(
            Object navigator,
            String key,
            double value
    );

    /**
     * 设置 Float 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withFloat(
            Object navigator,
            String key,
            float value
    );

    /**
     * 设置 Char 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withChar(
            Object navigator,
            String key,
            char value
    );

    /**
     * 设置 Byte 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withByte(
            Object navigator,
            String key,
            byte value
    );

    /**
     * 设置 Boolean 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withBoolean(
            Object navigator,
            String key,
            boolean value
    );

    /**
     * 设置 String 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withString(
            Object navigator,
            String key,
            String value
    );

    /**
     * 设置 Serializable 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withSerializable(
            Object navigator,
            String key,
            java.io.Serializable value
    );

    /**
     * 设置 Parcelable 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withParcelable(
            Object navigator,
            String key,
            android.os.Parcelable value
    );

    /**
     * 设置 Object 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withObject(
            Object navigator,
            String key,
            Object value
    );

    /**
     * 设置 Bundle 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @param value     参数值
     * @return Navigator 对象
     */
    Object withBundle(
            Object navigator,
            String key,
            Bundle value
    );

    /**
     * 设置 Bundle 参数 ( 合并到 Navigator )
     * @param navigator Navigator 对象
     * @param value     参数 Bundle
     * @return Navigator 对象
     */
    Object with(
            Object navigator,
            Bundle value
    );

    /**
     * 批量填充 Navigator 参数 Bundle
     * @param navigator Navigator 对象
     * @param listener  参数填充监听
     * @return Navigator 对象
     */
    Object fillParams(
            Object navigator,
            OnFillParamsListener listener
    );

    /**
     * 设置 Intent Data
     * @param navigator Navigator 对象
     * @param uri       Uri 对象
     * @return Navigator 对象
     */
    Object setData(
            Object navigator,
            Object uri
    );

    /**
     * 设置 Intent Identifier
     * @param navigator  Navigator 对象
     * @param identifier Identifier
     * @return Navigator 对象
     */
    Object setIdentifier(
            Object navigator,
            String identifier
    );

    /**
     * 设置 Intent ClipData
     * @param navigator Navigator 对象
     * @param clipData  ClipData 对象
     * @return Navigator 对象
     */
    Object setClipData(
            Object navigator,
            Object clipData
    );

    /**
     * 获取 Navigator Object 参数
     * @param navigator Navigator 对象
     * @param key       参数 key
     * @return 参数值
     */
    Object optObject(
            Object navigator,
            String key
    );

    /**
     * 追加 Intent Flags
     * @param navigator Navigator 对象
     * @param flags     Intent Flags
     * @return Navigator 对象
     */
    Object addFlags(
            Object navigator,
            int flags
    );

    /**
     * 设置 Intent Flags
     * @param navigator Navigator 对象
     * @param flags     Intent Flags
     * @return Navigator 对象
     */
    Object withFlags(
            Object navigator,
            int flags
    );

    /**
     * 设置 Activity Options Bundle
     * @param navigator     Navigator 对象
     * @param optionsCompat Options Bundle
     * @return Navigator 对象
     */
    Object withOptionsCompat(
            Object navigator,
            Bundle optionsCompat
    );

    /**
     * 设置进入动画资源 id
     * @param navigator Navigator 对象
     * @param animResId 动画资源 id
     * @return Navigator 对象
     */
    Object withInAnimation(
            Object navigator,
            int animResId
    );

    /**
     * 设置退出动画资源 id
     * @param navigator Navigator 对象
     * @param animResId 动画资源 id
     * @return Navigator 对象
     */
    Object withOutAnimation(
            Object navigator,
            int animResId
    );

    /**
     * 创建 Intent
     * @param navigator Navigator 对象
     * @param context   Context 对象
     * @return Intent
     */
    Intent createIntent(
            Object navigator,
            Object context
    );

    /**
     * 异步创建 Intent
     * @param navigator Navigator 对象
     * @param context   Context 对象
     * @param callback  Intent 创建回调
     */
    void createIntentWithCallback(
            Object navigator,
            Object context,
            OnIntentCallback callback
    );

    /**
     * 创建 Fragment
     * @param navigator Navigator 对象
     * @param <T>       Fragment 类型
     * @return Fragment 实例
     */
    <T> T createFragment(Object navigator);

    /**
     * 异步创建 Fragment
     * @param navigator Navigator 对象
     * @param callback  Fragment 创建回调
     */
    void createFragmentWithCallback(
            Object navigator,
            OnFragmentCallback callback
    );

    /**
     * 执行路由跳转
     * @param navigator Navigator 对象
     * @param item      Router 参数 ( 可为 null, 用于 context / fragment / requestCode / callback )
     */
    void navigation(
            Object navigator,
            Item item
    );

    /**
     * 执行 Action
     * @param navigator Navigator 对象
     * @return {@code true} success, {@code false} fail
     */
    boolean action(Object navigator);

    /**
     * 执行 Action
     * @param navigator Navigator 对象
     * @param context   Context 对象
     * @return {@code true} success, {@code false} fail
     */
    boolean action(
            Object navigator,
            Object context
    );
}