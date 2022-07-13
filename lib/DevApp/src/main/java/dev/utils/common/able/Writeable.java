package dev.utils.common.able;

/**
 * detail: 通用 Write 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Write 接口定义存储类
 *     全部接口只定义一个方法 write() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Operateable} 完全一致, 只有方法名、接口名不同
 * </pre>
 */
public final class Writeable {

    private Writeable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Write 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Write {

        boolean write();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Write 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface WriteByParam<Param> {

        /**
         * 通过传入参数执行操作
         * @param param 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean write(Param param);
    }

    /**
     * detail: Write 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface WriteByParam2<Param, Param2> {

        /**
         * 通过传入参数执行操作
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean write(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Write 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface WriteByParam3<Param, Param2, Param3> {

        /**
         * 通过传入参数执行操作
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return {@code true} success, {@code false} fail
         */
        boolean write(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Write 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface WriteByParamArgs<Param> {

        /**
         * 通过传入参数执行操作
         * @param args 泛型参数数组
         * @return {@code true} success, {@code false} fail
         */
        boolean write(Param... args);
    }
}