package dev.utils.common.able;

/**
 * detail: 通用 Thread 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Thread 接口定义存储类
 *     全部接口只定义一个方法 execute() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Threadable {

    private Threadable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Thread<T> {

        T execute();
    }

    // =======
    // = 有参 =
    // =======

    public interface ThreadByParam<T, Param> {

        T execute(Param param);
    }

    public interface ThreadByParam2<T, Param, Param2> {

        T execute(
                Param param,
                Param2 param2
        );
    }

    public interface ThreadByParam3<T, Param, Param2, Param3> {

        T execute(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ThreadByParamArgs<T, Param> {

        T execute(Param... args);
    }
}