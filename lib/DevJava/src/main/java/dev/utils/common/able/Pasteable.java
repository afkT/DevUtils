package dev.utils.common.able;

/**
 * detail: 通用 Paste 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Paste 接口定义存储类
 *     全部接口只定义一个方法 paste() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Pasteable {

    private Pasteable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Paste<T> {

        T paste();
    }

    // =======
    // = 有参 =
    // =======

    public interface PasteByParam<T, Param> {

        T paste(Param param);
    }

    public interface PasteByParam2<T, Param, Param2> {

        T paste(
                Param param,
                Param2 param2
        );
    }

    public interface PasteByParam3<T, Param, Param2, Param3> {

        T paste(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface PasteByParamArgs<T, Param> {

        T paste(Param... args);
    }
}