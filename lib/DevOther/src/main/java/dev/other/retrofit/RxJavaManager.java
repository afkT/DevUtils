package dev.other.retrofit;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * detail: RxJava 相关管理类 ( 针对 Retrofit )
 * @author Ttt
 * <pre>
 *     ReactiveX / RxJava 文档中文版
 *     @see <a href="https://github.com/mcxiaoke/RxDocs"/>
 *     Android RxJava : 这是一篇 清晰 & 易懂的 RxJava 入门教程
 *     @see <a href="https://www.jianshu.com/p/a406b94f3188"/>
 *     How To Use RxJava
 *     @see <a href="https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava"/>
 *     What's different in 3.0
 *     @see <a href="https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-3.0"/>
 *     RxJava 的 Single、Completable 以及 Maybe
 *     @see <a href="https://www.jianshu.com/p/45309538ad94"/>
 *     给 Android 开发者的 RxJava 详解
 *     @see <a href="http://gank.io/post/560e15be2dca930e00da1083"/>
 *     这可能是最好的 RxJava 2.x 教程
 *     @see <a href="https://www.jianshu.com/p/0cd258eecf60"/>
 * </pre>
 */
public final class RxJavaManager {

    private RxJavaManager() {
    }

    private static volatile RxJavaManager sInstance;

    public static RxJavaManager getInstance() {
        if (sInstance == null) {
            synchronized (RxJavaManager.class) {
                if (sInstance == null) {
                    sInstance = new RxJavaManager();
                }
            }
        }
        return sInstance;
    }

    // 把每一个请求的 Disposable 对象都交给由统一的 CompositeDisposable 对象去管理
    private final Map<String, CompositeDisposable> mManagerMap = new HashMap<>();

    /**
     * 通过 tag 将请求添加到统一管理对象中
     * <pre>
     *     如果 tag 与 Activity、Fragment 相关则在 onDestroy 调用 remove
     *     如果是特殊非生命周期关联 tag 则需要请求完成调用 remove
     * </pre>
     * @param tag        tag
     * @param disposable Disposable 请求对象
     */
    public void add(
            final String tag,
            final Disposable disposable
    ) {
        if (mManagerMap.containsKey(tag)) {
            mManagerMap.get(tag).add(disposable);
        } else {
            CompositeDisposable cd = new CompositeDisposable();
            cd.add(disposable);
            mManagerMap.put(tag, cd);
        }
    }

    /**
     * 通过 tag 移除请求
     * @param tag tag
     */
    public void remove(final String tag) {
        CompositeDisposable cd = mManagerMap.remove(tag);
        if (cd != null) cd.clear();
    }

    /**
     * 通过 tag 判断是否存在 CompositeDisposable
     * @param tag tag
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains(final String tag) {
        return mManagerMap.containsKey(tag);
    }

    /**
     * 获取 CompositeDisposable Map
     * @return CompositeDisposable Map
     */
    public Map<String, CompositeDisposable> getManagerMap() {
        return mManagerMap;
    }

    // =

    /**
     * Flowable UI 线程
     * @param <T> 泛型
     * @return {@link FlowableTransformer}
     */
    public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public @NonNull Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Consumer<Subscription>() {
//                            @Override
//                            public void accept(Subscription subscription) throws Exception {
//                            }
//                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}