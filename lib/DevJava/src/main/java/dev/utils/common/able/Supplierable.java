package dev.utils.common.able;

/**
 * detail: 通用 Supplier 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Supplier 接口定义存储类
 *     全部接口只定义一个方法 getAs() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Supplierable {

    private Supplierable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Supplier<T> {

        T getAs();
    }

    // =======
    // = 有参 =
    // =======

    public interface SupplierByParam<T, Param> {

        T getAs(Param param);
    }

    public interface SupplierByParam2<T, Param, Param2> {

        T getAs(
                Param param,
                Param2 param2
        );
    }

    public interface SupplierByParam3<T, Param, Param2, Param3> {

        T getAs(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface SupplierByParamArgs<T, Param> {

        T getAs(Param... args);
    }
}