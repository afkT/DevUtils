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
     * detail: 循环消费者
     * @author Ttt
     */
    public interface Consumer<T> {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                T value
        );
    }

    // ==========
    // = 可变数组 =
    // ==========

    // ==========
    // = 对象类型 =
    // ==========

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean forArgs(
            final Consumer<T> action,
            final T... args
    ) {
        return forArgs(action, false, args);
    }

    /**
     * 循环可变数组
     * <pre>
     *     基础类型需要传入包装类型, 如 int 传入为 Integer 为泛型类型
     * </pre>
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @param <T>         泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean forArgs(
            final Consumer<T> action,
            final boolean checkLength,
            final T... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (checkLength && len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                T value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ==========
    // = 基础类型 =
    // ==========

    // =======
    // = Int =
    // =======

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface IntConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                int value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forInts(
            final IntConsumer action,
            final int... args
    ) {
        return forInts(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forInts(
            final IntConsumer action,
            final boolean checkLength,
            final int... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (checkLength && len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                int value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ==========
    // = Double =
    // ==========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface DoubleConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                double value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forDoubles(
            final DoubleConsumer action,
            final double... args
    ) {
        return forDoubles(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forDoubles(
            final DoubleConsumer action,
            final boolean checkLength,
            final double... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (checkLength && len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                double value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ===========
    // = Boolean =
    // ===========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface BooleanConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                boolean value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forBooleans(
            final BooleanConsumer action,
            final boolean... args
    ) {
        return forBooleans(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forBooleans(
            final BooleanConsumer action,
            final boolean checkLength,
            final boolean... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (checkLength && len == 0) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                boolean value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }
}