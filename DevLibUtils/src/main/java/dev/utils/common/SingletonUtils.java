package dev.utils.common;

/**
 * detail: 单例工具类
 * Created by Ttt
 */
public abstract class SingletonUtils<T> {

    private T instance;

    /**
     * 实现实例抽象方法
     *
     * @return
     */
    protected abstract T newInstance();

    /**
     * 获取实例方法
     *
     * @return
     */
    public final T getInstance() {
        if (instance == null) {
            synchronized (SingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
