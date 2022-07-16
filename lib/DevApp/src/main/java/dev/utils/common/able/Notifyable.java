package dev.utils.common.able;

/**
 * detail: 通用 Notify 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Notify 接口定义存储类
 *     全部接口只定义一个方法 notifyMethod() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Notifyable {

    private Notifyable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Notify<T> {

        T notifyMethod();
    }

    // =======
    // = 有参 =
    // =======

    public interface NotifyByParam<T, Param> {

        T notifyMethod(Param param);
    }

    public interface NotifyByParam2<T, Param, Param2> {

        T notifyMethod(
                Param param,
                Param2 param2
        );
    }

    public interface NotifyByParam3<T, Param, Param2, Param3> {

        T notifyMethod(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface NotifyByParamArgs<T, Param> {

        T notifyMethod(Param... args);
    }
}