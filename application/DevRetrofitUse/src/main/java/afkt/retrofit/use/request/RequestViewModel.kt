package afkt.retrofit.use.request

import afkt.retrofit.use.base.BaseViewModel
import androidx.databinding.ObservableField

/**
 * detail: request_coroutines.kt 使用方法 ViewModel
 * @author Ttt
 */
class RequestViewModel(
    private val repository: RequestRepository = RequestRepository()
) : BaseViewModel() {

    // request_coroutines.kt 文案
    val tipsText = ObservableField(
        "DevRetrofit 库 request_coroutines.kt 封装使用示例"
    )
}