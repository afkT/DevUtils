package dev.utils.common.able;

/**
 * detail: 通用 Read 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Read 接口定义存储类
 *     全部接口只定义一个方法 read() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Getable} 完全一致, 只有方法名、接口名不同
 * </pre>
 */
public final class Readable {

    private Readable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Read 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Read<T> {

        T read();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Read 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface ReadByParam<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param 泛型参数
         * @return 泛型值
         */
        T read(Param param);
    }

    /**
     * detail: Read 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface ReadByParam2<T, Param, Param2> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return 泛型值
         */
        T read(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Read 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface ReadByParam3<T, Param, Param2, Param3> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return 泛型值
         */
        T read(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Read 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface ReadByParamArgs<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param args 泛型参数数组
         * @return 泛型值
         */
        T read(Param... args);
    }
}