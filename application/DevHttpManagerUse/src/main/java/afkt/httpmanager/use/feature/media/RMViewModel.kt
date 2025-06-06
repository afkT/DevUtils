package afkt.httpmanager.use.feature.media

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.media.data.model.MovieDetailBean
import afkt.httpmanager.use.feature.media.data.model.PhotoBean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * detail: Retrofit Manager ViewModel
 * @author Ttt
 */
class RMViewModel(
    private val repository: RMRepository = RMRepository()
) : BaseViewModel() {

    // 摄影图片列表
    private val _photoList = MutableLiveData<List<PhotoBean>>()
    val photoList: LiveData<List<PhotoBean>> = _photoList

    // 电影详情信息
    private val _movieDetail = MutableLiveData<MovieDetailBean>()
    val movieDetail: LiveData<MovieDetailBean> = _movieDetail

    // ===========
    // = 点击事件 =
    // ===========

    // 获取摄影图片列表
    val clickPhotoList: () -> Unit = {
        requestPhotoList()
    }

    // 获取电影详情信息
    val clickMovieDetail: () -> Unit = {
        requestMovieDetail()
    }

    // ==========
    // = 请求方法 =
    // ==========

    /**
     * 获取摄影图片列表
     */
    private fun requestPhotoList() {
        repository.fetchPhotoList(this) {
            _photoList.value = it
        }
    }

    /**
     * 获取电影详情信息
     */
    private fun requestMovieDetail() {
        repository.fetchMovieDetail(this) {
            _movieDetail.value = it
        }
    }
}