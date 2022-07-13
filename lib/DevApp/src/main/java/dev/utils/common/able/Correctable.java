package dev.utils.common.able;

/**
 * detail: 通用 Correct 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Correct 接口定义存储类
 *     全部接口只定义一个方法 correct() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Operateable} 完全一致, 只有方法名、接口名不同
 * </pre>
 */
public final class Correctable {

    private Correctable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Correct 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Correct {

        boolean correct();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Correct 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CorrectByParam<Param> {

        /**
         * 通过传入参数校验
         * @param param 泛型参数
         * @return {@code true} yes, {@code false} no
         */
        boolean correct(Param param);
    }

    /**
     * detail: Correct 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CorrectByParam2<Param, Param2> {

        /**
         * 通过传入参数校验
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return {@code true} yes, {@code false} no
         */
        boolean correct(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Correct 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CorrectByParam3<Param, Param2, Param3> {

        /**
         * 通过传入参数校验
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return {@code true} yes, {@code false} no
         */
        boolean correct(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Correct 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface CorrectByParamArgs<Param> {

        /**
         * 通过传入参数校验
         * @param args 泛型参数数组
         * @return {@code true} yes, {@code false} no
         */
        boolean correct(Param... args);
    }
}