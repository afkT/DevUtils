package dev.utils.common.able;

/**
 * detail: 通用 Encode 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Encode 接口定义存储类
 *     全部接口只定义一个方法 encode() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Encodeable {

    private Encodeable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Encode<T> {

        T encode();
    }

    // =======
    // = 有参 =
    // =======

    public interface EncodeByParam<T, Param> {

        T encode(Param param);
    }

    public interface EncodeByParam2<T, Param, Param2> {

        T encode(
                Param param,
                Param2 param2
        );
    }

    public interface EncodeByParam3<T, Param, Param2, Param3> {

        T encode(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface EncodeByParamArgs<T, Param> {

        T encode(Param... args);
    }
}