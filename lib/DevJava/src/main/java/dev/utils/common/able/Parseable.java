package dev.utils.common.able;

/**
 * detail: 通用 Parse 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Parse 接口定义存储类
 *     全部接口只定义一个方法 parse() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Parseable {

    private Parseable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Parse<T> {

        T parse();
    }

    // =======
    // = 有参 =
    // =======

    public interface ParseByParam<T, Param> {

        T parse(Param param);
    }

    public interface ParseByParam2<T, Param, Param2> {

        T parse(
                Param param,
                Param2 param2
        );
    }

    public interface ParseByParam3<T, Param, Param2, Param3> {

        T parse(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ParseByParamArgs<T, Param> {

        T parse(Param... args);
    }
}