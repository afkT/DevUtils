package dev.utils.common.able;

/**
 * detail: 通用 Filter 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Filter 接口定义存储类
 *     全部接口只定义一个方法 accept() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Filterable {

    private Filterable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Filter 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Filter {

        boolean accept();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Filter 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface FilterByParam<Param> {

        /**
         * 通过传入参数判断是否接受后续操作
         * @param param 泛型参数
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(Param param);
    }

    /**
     * detail: Filter 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface FilterByParam2<Param, Param2> {

        /**
         * 通过传入参数判断是否接受后续操作
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Filter 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface FilterByParam3<Param, Param2, Param3> {

        /**
         * 通过传入参数判断是否接受后续操作
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Filter 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface FilterByParamArgs<Param> {

        /**
         * 通过传入参数判断是否接受后续操作
         * @param args 泛型参数数组
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(Param... args);
    }
}