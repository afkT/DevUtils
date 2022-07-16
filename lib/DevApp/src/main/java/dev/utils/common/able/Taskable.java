package dev.utils.common.able;

/**
 * detail: 通用 Task 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Task 接口定义存储类
 *     全部接口只定义一个方法 result() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Taskable {

    private Taskable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Task<T> {

        T result();
    }

    // =======
    // = 有参 =
    // =======

    public interface TaskByParam<T, Param> {

        T result(Param param);
    }

    public interface TaskByParam2<T, Param, Param2> {

        T result(
                Param param,
                Param2 param2
        );
    }

    public interface TaskByParam3<T, Param, Param2, Param3> {

        T result(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface TaskByParamArgs<T, Param> {

        T result(Param... args);
    }
}