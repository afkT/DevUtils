package dev.utils.common.able;

/**
 * detail: 通用 Send 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Send 接口定义存储类
 *     全部接口只定义一个方法 post() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Sendable {

    private Sendable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Send<T> {

        T post();
    }

    // =======
    // = 有参 =
    // =======

    public interface SendByParam<T, Param> {

        T post(Param param);
    }

    public interface SendByParam2<T, Param, Param2> {

        T post(
                Param param,
                Param2 param2
        );
    }

    public interface SendByParam3<T, Param, Param2, Param3> {

        T post(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface SendByParamArgs<T, Param> {

        T post(Param... args);
    }
}