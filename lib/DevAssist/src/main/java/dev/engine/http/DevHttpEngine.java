package dev.engine.http;

/**
 * detail: Http Engine
 * @author Ttt
 * <pre>
 *     Application: IHttpEngine.initEngine()
 *     use: IHttpEngine.xxx
 * </pre>
 */
public final class DevHttpEngine {

    private DevHttpEngine() {
    }

    // IHttpEngine
    private static IHttpEngine sHttpEngine;

    /**
     * 初始化 Engine
     * @param httpEngine {@link IHttpEngine}
     */
    public static void initEngine(IHttpEngine httpEngine) {
        DevHttpEngine.sHttpEngine = httpEngine;
    }

    // ===============
    // = IHttpEngine =
    // ===============

    // =============
    // = 获取 Call =
    // =============

    /**
     * 获取 Request Call Object
     * @param request  {@link IHttpEngine.Request}
     * @param callBack {@link IHttpEngine.RequestCallBack}
     * @return {@link IHttpEngine.Call}
     */
    public static IHttpEngine.Call newCall(IHttpEngine.Request request, IHttpEngine.RequestCallBack callBack) {
        if (sHttpEngine != null) {
            return sHttpEngine.newCall(request, callBack);
        }
        return null;
    }
}
