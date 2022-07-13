package dev.utils.common.able;

/**
 * detail: 通用 Get 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Get 接口定义存储类
 *     全部接口只定义一个方法 get() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Getable {

    private Getable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Get 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Get<T> {

        T get();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Get 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface GetByParam<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param 泛型参数
         * @return 泛型值
         */
        T get(Param param);
    }

    /**
     * detail: Get 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface GetByParam2<T, Param, Param2> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return 泛型值
         */
        T get(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Get 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface GetByParam3<T, Param, Param2, Param3> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return 泛型值
         */
        T get(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Get 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface GetByParamArgs<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param args 泛型参数数组
         * @return 泛型值
         */
        T get(Param... args);
    }
}