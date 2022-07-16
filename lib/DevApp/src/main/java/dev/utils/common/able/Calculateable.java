package dev.utils.common.able;

/**
 * detail: 通用 Calculate 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Calculate 接口定义存储类
 *     全部接口只定义一个方法 calculate() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Calculateable {

    private Calculateable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Calculate<T> {

        T calculate();
    }

    // =======
    // = 有参 =
    // =======

    public interface CalculateByParam<T, Param> {

        T calculate(Param param);
    }

    public interface CalculateByParam2<T, Param, Param2> {

        T calculate(
                Param param,
                Param2 param2
        );
    }

    public interface CalculateByParam3<T, Param, Param2, Param3> {

        T calculate(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface CalculateByParamArgs<T, Param> {

        T calculate(Param... args);
    }
}