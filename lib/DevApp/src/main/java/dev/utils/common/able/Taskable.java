package dev.utils.common.able;

/**
 * detail: 通用 Task 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Task 接口定义存储类
 *     全部接口只定义一个方法 result() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Getable} 完全一致, 只有方法名、接口名不同
 *     <p></p>
 *     用于表示任务操作如 rename task、convert task 执行并返回结果值
 *     通用于各种 Task 操作又区分与 {@link Operateable} 返回操作 true、false 结果
 * </pre>
 */
public final class Taskable {

    private Taskable() {
    }

    // =======
    // = 无参 =
    // =======

    /**
     * detail: Task 接口 ( 最基础无参方法 )
     * @author Ttt
     */
    public interface Task<T> {

        T result();
    }

    // =======
    // = 有参 =
    // =======

    /**
     * detail: Task 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface TaskByParam<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param 泛型参数
         * @return 泛型值
         */
        T result(Param param);
    }

    /**
     * detail: Task 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface TaskByParam2<T, Param, Param2> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @return 泛型值
         */
        T result(
                Param param,
                Param2 param2
        );
    }

    /**
     * detail: Task 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface TaskByParam3<T, Param, Param2, Param3> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param param  泛型参数
         * @param param2 泛型参数
         * @param param3 泛型参数
         * @return 泛型值
         */
        T result(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    /**
     * detail: Task 接口 ( 通过传入参数 )
     * @author Ttt
     */
    public interface TaskByParamArgs<T, Param> {

        /**
         * 通过传入参数获取指定类型返回值
         * @param args 泛型参数数组
         * @return 泛型值
         */
        T result(Param... args);
    }
}