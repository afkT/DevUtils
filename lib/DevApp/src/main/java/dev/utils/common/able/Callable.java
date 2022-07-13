package dev.utils.common.able;

/**
 * detail: 通用 Call 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Call 接口定义存储类
 *     全部接口只定义一个方法 callback() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Operateable} 完全一致, 只有方法名、接口名不同
 * </pre>
 */
public final class Callable {

    private Callable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Call 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Call {

        boolean callback();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Call 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CallByParam<Param> {

        /**
         * 通过传入参数触发回调
         * @param param 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean callback(Param param);
    }

    /**
     * detail: Call 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CallByParam2<Param, Param2> {

        /**
         * 通过传入参数触发回调
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean callback(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Call 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CallByParam3<Param, Param2, Param3> {

        /**
         * 通过传入参数触发回调
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean callback(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Call 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CallByParamArgs<Param> {

        /**
         * 通过传入参数触发回调
         * @param args 泛型参数数组
         * @return {@code true} success, {@code false} fail
         */
        boolean callback(Param... args);
    }
}