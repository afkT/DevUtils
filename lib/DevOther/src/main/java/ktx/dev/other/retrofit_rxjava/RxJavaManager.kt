package ktx.dev.other.retrofit_rxjava

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

/**
 * detail: RxJava 相关管理类 ( 针对 Retrofit )
 * @author Ttt
 * ReactiveX / RxJava 文档中文版
 * @see https://github.com/mcxiaoke/RxDocs
 * Android RxJava : 这是一篇 清晰 & 易懂的 RxJava 入门教程
 * @see https://www.jianshu.com/p/a406b94f3188
 * How To Use RxJava
 * @see https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava
 * What's different in 3.0
 * @see https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-3.0
 * RxJava 的 Single、Completable 以及 Maybe
 * @see https://www.jianshu.com/p/45309538ad94
 * 给 Android 开发者的 RxJava 详解
 * @see http://gank.io/post/560e15be2dca930e00da1083
 * 这可能是最好的 RxJava 2.x 教程
 * @see https://www.jianshu.com/p/0cd258eecf60
 */
class RxJavaManager private constructor() {

    // 把每一个请求的 Disposable 对象都交给由统一的 CompositeDisposable 对象去管理
    private val mManagerMap: MutableMap<String, CompositeDisposable> = HashMap()

    companion object {
        // RxJavaManager 实例
        val instance: RxJavaManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxJavaManager()
        }
    }

    /**
     * 通过 tag 将请求添加到统一管理对象中
     * @param tag        tag
     * @param disposable Disposable 请求对象
     * 如果 tag 与 Activity、Fragment 相关则在 onDestroy 调用 remove
     * 如果是特殊非生命周期关联 tag 则需要请求完成调用 remove
     */
    fun add(
        tag: String,
        disposable: Disposable?
    ) {
        if (mManagerMap.containsKey(tag)) {
            mManagerMap[tag]?.add(disposable)
        } else {
            val cd = CompositeDisposable()
            cd.add(disposable)
            mManagerMap[tag] = cd
        }
    }

    /**
     * 通过 tag 移除请求
     * @param tag tag
     */
    fun remove(tag: String) {
        mManagerMap.remove(tag)?.clear()
    }

    /**
     * 通过 tag 判断是否存在 CompositeDisposable
     * @param tag tag
     * @return `true` yes, `false` no
     */
    fun contains(tag: String): Boolean {
        return mManagerMap.containsKey(tag)
    }

    /**
     * 获取 CompositeDisposable Map
     * @return CompositeDisposable Map
     */
    fun getManagerMap(): Map<String, CompositeDisposable> {
        return mManagerMap
    }

    // =

    /**
     * Flowable UI 线程
     * @param <T> 泛型
     * @return [FlowableTransformer]
     */
    fun <T> io_main(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
//                .doOnSubscribe { }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}