package afkt.httpmanager.use.feature.progress

import afkt.httpmanager.use.base.BaseViewModel

/**
 * detail: Progress Manager ViewModel
 * @author Ttt
 */
class PMViewModel(
    private val repository: PMRepository = PMRepository()
) : BaseViewModel() {

    // ===========
    // = 点击事件 =
    // ===========

    // 获取摄影图片列表
    val clickPhotoList: () -> Unit = {
        requestPhotoList()
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 获取摄影图片列表
     */
    private fun requestPhotoList() {
    }
}