package dev.utils.common.able;

/**
 * detail: 通用 UnBinder 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 UnBinder 接口定义存储类
 *     全部接口只定义一个方法 unbind() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class UnBinderable {

    private UnBinderable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface UnBinder<T> {

        T unbind();
    }

    // =======
    // = 有参 =
    // =======

    public interface UnBinderByParam<T, Param> {

        T unbind(Param param);
    }

    public interface UnBinderByParam2<T, Param, Param2> {

        T unbind(
                Param param,
                Param2 param2
        );
    }

    public interface UnBinderByParam3<T, Param, Param2, Param3> {

        T unbind(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface UnBinderByParamArgs<T, Param> {

        T unbind(Param... args);
    }
}