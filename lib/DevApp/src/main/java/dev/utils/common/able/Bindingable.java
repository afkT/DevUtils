package dev.utils.common.able;

/**
 * detail: 通用 Binding 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Binding 接口定义存储类
 *     全部接口只定义一个方法 bind() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Bindingable {

    private Bindingable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Binding<T> {

        T bind();
    }

    // =======
    // = 有参 =
    // =======

    public interface BindingByParam<T, Param> {

        T bind(Param param);
    }

    public interface BindingByParam2<T, Param, Param2> {

        T bind(
                Param param,
                Param2 param2
        );
    }

    public interface BindingByParam3<T, Param, Param2, Param3> {

        T bind(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface BindingByParamArgs<T, Param> {

        T bind(Param... args);
    }
}