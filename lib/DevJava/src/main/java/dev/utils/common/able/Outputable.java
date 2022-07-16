package dev.utils.common.able;

/**
 * detail: 通用 Output 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Output 接口定义存储类
 *     全部接口只定义一个方法 output() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Outputable {

    private Outputable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Output<T> {

        T output();
    }

    // =======
    // = 有参 =
    // =======

    public interface OutputByParam<T, Param> {

        T output(Param param);
    }

    public interface OutputByParam2<T, Param, Param2> {

        T output(
                Param param,
                Param2 param2
        );
    }

    public interface OutputByParam3<T, Param, Param2, Param3> {

        T output(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface OutputByParamArgs<T, Param> {

        T output(Param... args);
    }
}