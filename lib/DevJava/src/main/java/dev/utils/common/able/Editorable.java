package dev.utils.common.able;

/**
 * detail: 通用 Editor 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Editor 接口定义存储类
 *     全部接口只定义一个方法 edit() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Editorable {

    private Editorable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Editor<T> {

        T edit();
    }

    // =======
    // = 有参 =
    // =======

    public interface EditorByParam<T, Param> {

        T edit(Param param);
    }

    public interface EditorByParam2<T, Param, Param2> {

        T edit(
                Param param,
                Param2 param2
        );
    }

    public interface EditorByParam3<T, Param, Param2, Param3> {

        T edit(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface EditorByParamArgs<T, Param> {

        T edit(Param... args);
    }
}