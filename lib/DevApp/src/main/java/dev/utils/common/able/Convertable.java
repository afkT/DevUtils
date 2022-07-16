package dev.utils.common.able;

/**
 * detail: 通用 Convert 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Convert 接口定义存储类
 *     全部接口只定义一个方法 convert() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Convertable {

    private Convertable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Convert<T> {

        T convert();
    }

    // =======
    // = 有参 =
    // =======

    public interface ConvertByParam<T, Param> {

        T convert(Param param);
    }

    public interface ConvertByParam2<T, Param, Param2> {

        T convert(
                Param param,
                Param2 param2
        );
    }

    public interface ConvertByParam3<T, Param, Param2, Param3> {

        T convert(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ConvertByParamArgs<T, Param> {

        T convert(Param... args);
    }
}