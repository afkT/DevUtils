package dev.utils.common.able;

/**
 * detail: 通用 Encrypt 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Encrypt 接口定义存储类
 *     全部接口只定义一个方法 encrypt() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Encryptable {

    private Encryptable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Encrypt<T> {

        T encrypt();
    }

    // =======
    // = 有参 =
    // =======

    public interface EncryptByParam<T, Param> {

        T encrypt(Param param);
    }

    public interface EncryptByParam2<T, Param, Param2> {

        T encrypt(
                Param param,
                Param2 param2
        );
    }

    public interface EncryptByParam3<T, Param, Param2, Param3> {

        T encrypt(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface EncryptByParamArgs<T, Param> {

        T encrypt(Param... args);
    }
}