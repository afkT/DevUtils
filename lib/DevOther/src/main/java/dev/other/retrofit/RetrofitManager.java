package dev.other.retrofit;

import java.util.HashMap;
import java.util.Map;

import dev.utils.LogPrintUtils;
import retrofit2.Retrofit;

/**
 * detail: Retrofit 管理类
 * @author Ttt
 * <pre>
 *     使用 Retrofit2 + RxJava3 + RxAndroid3, 预留多 Retrofit 处理
 *     也可以使用单 Retrofit 通过 OkHttp Interceptor 进行拦截处理 ( 例: 通过 Header 某个字段判断重新设置 baseUrl、Header )
 *     <p></p>
 *     Android : 手把手带你深入剖析 Retrofit 2.0 源码
 *     @see <a href="https://blog.csdn.net/carson_ho/article/details/73732115"/>
 *     这是一份很详细的 Retrofit 2.0 使用教程 ( 含实例讲解 )
 *     @see <a href="https://blog.csdn.net/carson_ho/article/details/73732076"/>
 *     封装 Retrofit2 + RxJava2 网络请求框架
 *     @see <a href="https://www.jianshu.com/p/2e8b400909b7"/>
 * </pre>
 */
public final class RetrofitManager {

    private RetrofitManager() {
    }

    // 日志 TAG
    private final String TAG = RetrofitManager.class.getSimpleName();

    // RetrofitManager 实例
    private static volatile RetrofitManager sInstance;

    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    // Retrofit Map ( 多 BaseUrl、Header、OkHttp 配置等考虑 )
    private final Map<String, Retrofit> mRetrofitMap = new HashMap<>();

    /**
     * 通过 tag 获取 Retrofit
     * @param tag Retrofit tag
     * @return {@link Retrofit}
     */
    public Retrofit get(final String tag) {
        return mRetrofitMap.get(tag);
    }

    /**
     * 通过 tag 保存 Retrofit
     * @param tag      Retrofit tag
     * @param retrofit {@link Retrofit}
     * @return {@link Retrofit}
     */
    public Retrofit put(
            final String tag,
            final Retrofit retrofit
    ) {
        if (tag != null && retrofit != null) {
            mRetrofitMap.put(tag, retrofit);
        }
        return retrofit;
    }

    /**
     * 通过 tag 移除 Retrofit
     * @param tag Retrofit tag
     * @return {@link Retrofit}
     */
    public Retrofit remove(final String tag) {
        return mRetrofitMap.remove(tag);
    }

    /**
     * 通过 tag 判断是否存在 Retrofit
     * @param tag Retrofit tag
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains(final String tag) {
        return mRetrofitMap.containsKey(tag);
    }

    /**
     * 获取 Retrofit Map
     * @return Retrofit Map
     */
    public Map<String, Retrofit> getRetrofitMap() {
        return mRetrofitMap;
    }

    // ===================
    // = 代理 API Service =
    // ===================

    /**
     * 创建 API Service Class
     * @param tag     Retrofit tag
     * @param service API Service
     * @param <T>     ApiServiceT.class
     * @return API Service Instance
     */
    public <T> T create(
            final String tag,
            final Class<T> service
    ) {
        Retrofit retrofit = mRetrofitMap.get(tag);
        if (retrofit != null) {
            try {
                return retrofit.create(service);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "create");
            }
        }
        return null;
    }
}