package dev.utils.common.able;

/**
 * detail: 通用 Decode 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Decode 接口定义存储类
 *     全部接口只定义一个方法 decode() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Decodeable {

    private Decodeable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Decode<T> {

        T decode();
    }

    // =======
    // = 有参 =
    // =======

    public interface DecodeByParam<T, Param> {

        T decode(Param param);
    }

    public interface DecodeByParam2<T, Param, Param2> {

        T decode(
                Param param,
                Param2 param2
        );
    }

    public interface DecodeByParam3<T, Param, Param2, Param3> {

        T decode(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface DecodeByParamArgs<T, Param> {

        T decode(Param... args);
    }
}