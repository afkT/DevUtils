package dev.utils.common.able;

/**
 * detail: 通用 Modify 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Modify 接口定义存储类
 *     全部接口只定义一个方法 modify() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Modifyable {

    private Modifyable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Modify<T> {

        T modify();
    }

    // =======
    // = 有参 =
    // =======

    public interface ModifyByParam<T, Param> {

        T modify(Param param);
    }

    public interface ModifyByParam2<T, Param, Param2> {

        T modify(
                Param param,
                Param2 param2
        );
    }

    public interface ModifyByParam3<T, Param, Param2, Param3> {

        T modify(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ModifyByParamArgs<T, Param> {

        T modify(Param... args);
    }
}