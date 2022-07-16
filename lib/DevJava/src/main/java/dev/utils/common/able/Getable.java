package dev.utils.common.able;

/**
 * detail: 通用 Get 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Get 接口定义存储类
 *     全部接口只定义一个方法 get() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     其他类全部都是 copy {@link Getable} 代码完全一致, 只有方法名、接口名不同
 *     不通用 {@link Getable} 接口是为了区分功能, 对常用的功能定义对应类
 *     如 Readable、Writeable 读写较为常用, 所以专门定义对应接口类
 *     <p></p>
 *     正常如 {@link Writeable} write() 方法需要返回写入结果, 可自行传入 <T> 泛型为 Boolean
 *     也能自行决定需要返回什么类型值
 * </pre>
 */
public final class Getable {

    private Getable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Get<T> {

        T get();
    }

    // =======
    // = 有参 =
    // =======

    public interface GetByParam<T, Param> {

        T get(Param param);
    }

    public interface GetByParam2<T, Param, Param2> {

        T get(
                Param param,
                Param2 param2
        );
    }

    public interface GetByParam3<T, Param, Param2, Param3> {

        T get(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface GetByParamArgs<T, Param> {

        T get(Param... args);
    }
}