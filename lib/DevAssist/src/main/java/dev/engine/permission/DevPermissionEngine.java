package dev.engine.permission;

/**
 * detail: Permission Engine
 * @author Ttt
 */
public final class DevPermissionEngine {

    private DevPermissionEngine() {
    }

    private static IPermissionEngine sEngine;

    /**
     * 获取 PermissionEngine
     * @return {@link IPermissionEngine}
     */
    public static IPermissionEngine getEngine() {
        return sEngine;
    }

    /**
     * 设置 PermissionEngine
     * @param engine {@link IPermissionEngine}
     */
    public static void setEngine(final IPermissionEngine engine) {
        DevPermissionEngine.sEngine = engine;
    }
}