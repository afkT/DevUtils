package dev.utils.common.able;

/**
 * detail: 通用 Operate 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Operate 接口定义存储类
 *     全部接口只定义一个方法 execute() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Operateable {

    private Operateable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Operate<T> {

        T execute();
    }

    // =======
    // = 有参 =
    // =======

    public interface OperateByParam<T, Param> {

        T execute(Param param);
    }

    public interface OperateByParam2<T, Param, Param2> {

        T execute(
                Param param,
                Param2 param2
        );
    }

    public interface OperateByParam3<T, Param, Param2, Param3> {

        T execute(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface OperateByParamArgs<T, Param> {

        T execute(Param... args);
    }
}