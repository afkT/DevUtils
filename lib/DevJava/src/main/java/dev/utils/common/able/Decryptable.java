package dev.utils.common.able;

/**
 * detail: 通用 Decrypt 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Decrypt 接口定义存储类
 *     全部接口只定义一个方法 decrypt() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Decryptable {

    private Decryptable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Decrypt<T> {

        T decrypt();
    }

    // =======
    // = 有参 =
    // =======

    public interface DecryptByParam<T, Param> {

        T decrypt(Param param);
    }

    public interface DecryptByParam2<T, Param, Param2> {

        T decrypt(
                Param param,
                Param2 param2
        );
    }

    public interface DecryptByParam3<T, Param, Param2, Param3> {

        T decrypt(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface DecryptByParamArgs<T, Param> {

        T decrypt(Param... args);
    }
}