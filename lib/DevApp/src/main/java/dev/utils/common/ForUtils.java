package dev.utils.common;

/**
 * detail: 循环工具类
 * @author Ttt
 */
public final class ForUtils {

    private ForUtils() {
    }

    // 日志 TAG
    private static final String TAG = ForUtils.class.getSimpleName();

    /**
     * detail: 回调方法
     * @author Ttt
     */
    public interface Callback<T> {

        void callback(
                int index,
                T value
        );
    }

    /**
     * 循环可变数组
     * @param callback 回调方法
     * @param args     参数
     * @param <T>      泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean forArgs(
            final Callback<T> callback,
            final T... args
    ) {
        return forArgs(callback, false, args);
    }

    /**
     * 循环可变数组
     * @param callback    回调方法
     * @param checkLength 是否检查长度
     * @param args        参数
     * @param <T>         泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean forArgs(
            final Callback<T> callback,
            final boolean checkLength,
            final T... args
    ) {
        if (callback != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (checkLength && len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                T value = args[i];
                callback.callback(i, value);
            }
            return true;
        }
        return false;
    }
}