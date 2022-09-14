package dev.utils.common.able;

/**
 * detail: 通用 Consumer 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Consumer 接口定义存储类
 *     全部接口只定义一个方法 accept() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Consumerable {

    private Consumerable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Consumer<T> {

        T accept();
    }

    // =======
    // = 有参 =
    // =======

    public interface ConsumerByParam<T, Param> {

        T accept(Param param);
    }

    public interface ConsumerByParam2<T, Param, Param2> {

        T accept(
                Param param,
                Param2 param2
        );
    }

    public interface ConsumerByParam3<T, Param, Param2, Param3> {

        T accept(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ConsumerByParamArgs<T, Param> {

        T accept(Param... args);
    }
}