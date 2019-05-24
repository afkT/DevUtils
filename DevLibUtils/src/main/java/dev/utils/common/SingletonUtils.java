package dev.utils.common;

/**
 * detail: 单例工具类
 * @param <T> 泛型
 * @author Ttt
 */
public abstract class SingletonUtils<T> {

    private T mInstance;

    /**
     * 实现实例抽象方法
     * @return 实例
     */
    protected abstract T newInstance();

    /**
     * 获取实例方法
     * @return 实例
     */
    public final T getInstance() {
        if (mInstance == null) {
            synchronized (SingletonUtils.class) {
                if (mInstance == null) {
                    mInstance = newInstance();
                }
            }
        }
        return mInstance;
    }
}
