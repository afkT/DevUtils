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
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                T value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ==========
    // = Simple =
    // ==========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface ConsumerSimple<T> {

        /**
         * 循环消费方法
         * @param value 对应索引值
         */
        void accept(T value);
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @param <T>    泛型
     * @return {@code true} success, {@code false} fail
     */
    public static <T> boolean forSimpleArgs(
            final ConsumerSimple<T> action,
            final T... args
    ) {
        return forSimpleArgs(action, false, args);
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
    public static <T> boolean forSimpleArgs(
            final ConsumerSimple<T> action,
            final boolean checkLength,
            final T... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (T value : args) {
                action.accept(value);
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
            if (len == 0) return !checkLength;

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
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                double value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // =========
    // = Float =
    // =========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface FloatConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                float value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forFloats(
            final FloatConsumer action,
            final float... args
    ) {
        return forFloats(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forFloats(
            final FloatConsumer action,
            final boolean checkLength,
            final float... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                float value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ========
    // = Long =
    // ========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface LongConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                long value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forLongs(
            final LongConsumer action,
            final long... args
    ) {
        return forLongs(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forLongs(
            final LongConsumer action,
            final boolean checkLength,
            final long... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                long value = args[i];
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
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                boolean value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ========
    // = Byte =
    // ========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface ByteConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                byte value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forBytes(
            final ByteConsumer action,
            final byte... args
    ) {
        return forBytes(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forBytes(
            final ByteConsumer action,
            final boolean checkLength,
            final byte... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                byte value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // ========
    // = Char =
    // ========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface CharConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                char value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forChars(
            final CharConsumer action,
            final char... args
    ) {
        return forChars(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forChars(
            final CharConsumer action,
            final boolean checkLength,
            final char... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                char value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }

    // =========
    // = Short =
    // =========

    /**
     * detail: 循环消费者
     * @author Ttt
     */
    public interface ShortConsumer {

        /**
         * 循环消费方法
         * @param index 索引
         * @param value 对应索引值
         */
        void accept(
                int index,
                short value
        );
    }

    /**
     * 循环可变数组
     * @param action 循环消费对象
     * @param args   参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forShorts(
            final ShortConsumer action,
            final short... args
    ) {
        return forShorts(action, false, args);
    }

    /**
     * 循环可变数组
     * @param action      循环消费对象
     * @param checkLength 是否检查长度
     * @param args        参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean forShorts(
            final ShortConsumer action,
            final boolean checkLength,
            final short... args
    ) {
        if (action != null && args != null) {
            int len = args.length;
            // 是否需要判断长度
            if (len == 0) return !checkLength;

            for (int i = 0; i < len; i++) {
                short value = args[i];
                action.accept(i, value);
            }
            return true;
        }
        return false;
    }
}