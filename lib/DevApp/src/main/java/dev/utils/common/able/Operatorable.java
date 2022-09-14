package dev.utils.common.able;

/**
 * detail: 通用 Operator 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Operator 接口定义存储类
 *     全部接口只定义一个方法 execute() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Operatorable {

    private Operatorable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Operator<T> {

        T execute();
    }

    // =======
    // = 有参 =
    // =======

    public interface OperatorByParam<T, Param> {

        T execute(Param param);
    }

    public interface OperatorByParam2<T, Param, Param2> {

        T execute(
                Param param,
                Param2 param2
        );
    }

    public interface OperatorByParam3<T, Param, Param2, Param3> {

        T execute(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface OperatorByParamArgs<T, Param> {

        T execute(Param... args);
    }
}