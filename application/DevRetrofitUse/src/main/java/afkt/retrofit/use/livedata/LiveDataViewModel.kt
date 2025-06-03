package afkt.retrofit.use.livedata

import afkt.retrofit.use.base.BaseViewModel
import androidx.databinding.ObservableField

/**
 * detail: request_coroutines_simple_livedata.kt 使用方法 ViewModel
 * @author Ttt
 */
class LiveDataViewModel(
    private val repository: LiveDataRepository = LiveDataRepository()
) : BaseViewModel() {

    // request_coroutines_simple_livedata.kt 文案
    val tipsText = ObservableField(
        "DevRetrofit 库 request_coroutines_simple_livedata.kt 封装使用示例"
    )

    // ==============
    // = 电影详情信息 =
    // ==============

    // 获取电影详情信息
    val clickMovieDetail: () -> Unit = {
    }

    // ===========
    // = 书籍列表 =
    // ===========

    // 获取书籍列表
    val clickBookList: () -> Unit = {
    }
}