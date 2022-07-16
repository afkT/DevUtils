package dev.utils.common.able;

/**
 * detail: 通用 Flow 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Flow 接口定义存储类
 *     全部接口只定义一个方法 flow() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Flowable {

    private Flowable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Flow<T> {

        T flow();
    }

    // =======
    // = 有参 =
    // =======

    public interface FlowByParam<T, Param> {

        T flow(Param param);
    }

    public interface FlowByParam2<T, Param, Param2> {

        T flow(
                Param param,
                Param2 param2
        );
    }

    public interface FlowByParam3<T, Param, Param2, Param3> {

        T flow(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface FlowByParamArgs<T, Param> {

        T flow(Param... args);
    }
}