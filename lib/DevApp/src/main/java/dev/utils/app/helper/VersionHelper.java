package dev.utils.app.helper;

import dev.utils.app.PathUtils;

/**
 * detail: Android 版本适配 Helper 类
 * @author Ttt
 * <pre>
 *     Android 版本适配 Helper 类, 方便快捷使用
 *     并简化需多工具类组合使用的功能
 *     关于路径建议及兼容建议查看 {@link PathUtils}
 *     <p></p>
 *     Android 10 更新内容
 *     @see <a href="https://developer.android.google.cn/about/versions/10"/>
 *     Android 11 更新内容
 *     @see <a href="https://developer.android.google.cn/about/versions/11"/>
 *     <p></p>
 *     Android Q 适配指南
 *     @see <a href="https://juejin.im/post/5ddd2417f265da060a5217ff"/>
 *     Android 11 最全适配实践指南
 *     @see <a href="https://mp.weixin.qq.com/s/ZrsO5VvURwW98PTHei0kFA"/>
 *     MANAGE_EXTERNAL_STORAGE
 *     @see <a href="https://developer.android.google.cn/preview/privacy/storage"/>
 * </pre>
 */
public final class VersionHelper {

    private VersionHelper() {
    }

    // 日志 TAG
    private static final String TAG = VersionHelper.class.getSimpleName();

}