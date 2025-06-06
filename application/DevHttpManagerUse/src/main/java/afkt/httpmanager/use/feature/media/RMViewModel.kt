package afkt.httpmanager.use.feature.media

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.media.data.api.MediaAPI
import afkt.httpmanager.use.feature.media.data.api.MediaService
import afkt.httpmanager.use.feature.media.data.api.updateDebugEnvironment
import afkt.httpmanager.use.feature.media.data.api.updateReleaseEnvironment
import afkt.httpmanager.use.feature.media.data.model.MovieDetailBean
import afkt.httpmanager.use.feature.media.data.model.PhotoBean
import afkt.httpmanager.use.network.helper.ResponseHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.DevHttpManager
import dev.environment.DevEnvironment
import dev.http.manager.RetrofitOperation
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import okhttp3.HttpUrl.Companion.toHttpUrl

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
        // Toast 提示
        ResponseHelper.toastSuccess("开始请求，查看 logcat")
    }

    // 获取电影详情信息
    val clickMovieDetail: () -> Unit = {
        requestMovieDetail()
        // Toast 提示
        ResponseHelper.toastSuccess("开始请求，查看 logcat")
    }

    // 切换【有效】环境
    val clickReleaseBaseUrl: () -> Unit = {
        // 设置 Media 模块 release environment baseUrl
        MediaAPI.updateReleaseEnvironment()
        // Toast 提示
        ResponseHelper.toastSuccess("切换成功，再次请求查看 logcat")
        // 获取电影详情信息
        requestMovieDetail()
    }

    // 切换【无效】环境
    val clickErrorBaseUrl: () -> Unit = {
        // 设置 Media 模块 debug environment baseUrl
        MediaAPI.updateDebugEnvironment()
        // Toast 提示
        ResponseHelper.toastSuccess("切换成功，再次请求查看 logcat")
        // 获取电影详情信息
        requestMovieDetail()
    }

    /**
     * 更新环境，重新构建 Retrofit
     */
    private fun updateEnvironment() {
        // 方式一【内部自动】通知重新构建 Retrofit，也可以手动调用
        MediaAPI.operation().reset()
        // 方式二【监听】DevEnvironment 环境切换
        val mediaModule = DevEnvironment.getMediaModule()
        // 添加模块环境改变触发事件
        DevEnvironment.addOnEnvironmentChangeListener { module, oldEnvironment, newEnvironment ->
            module.hiIfNotNull {
                if (it.name == mediaModule.name) {
                    MediaAPI.operation().reset()
                }
            }
        }
        /**
         * 至于更新的 BaseUrl 和当前 Retrofit baseUrl 是否一致，以及是否需要重新构建 Retrofit
         * 可以自行在 [MediaAPI] 中新增方法获取 Retrofit baseUrl 进行校验
         */
    }

    // 重新构建 Retrofit 方式
    val clickRetrofitReset: () -> Unit = {
        // Toast 提示
        ResponseHelper.toastSuccess("查看 retrofitResetUse() 代码")
    }

    private fun retrofitResetUse() {
        // 方式一
        MediaAPI.operation().reset()
        // 方式一【指定链接】
        MediaAPI.operation().reset("https://development.github.com/".toHttpUrl())

        // 方式二【需要调用过 DevHttpManager.RM.putRetrofitBuilder() 进行存储】
        DevHttpManager.RM.reset(MediaAPI.NAME)
        // 方式二【指定链接】
        DevHttpManager.RM.reset(
            MediaAPI.NAME, "https://development.github.com/".toHttpUrl()
        )

        // 方式三【重新构建全部 Retrofit】
        DevHttpManager.RM.resetAll()
    }

    // Retrofit Manager 操作方法
    val clickRetrofitManager: () -> Unit = {
        // Toast 提示
        ResponseHelper.toastSuccess("查看 retrofitManagerUse() 代码")
    }

    private fun retrofitManagerUse() {
        // 方式一
        val operation: RetrofitOperation = MediaAPI.operation()
        // 具体方法
        operation.getRetrofit()
        operation.getRetrofit(true)
        operation.reset()
        operation.reset("https://development.github.com/".toHttpUrl())
        operation.resetAndCreate(MediaService::class.java)
        operation.resetAndCreate(
            MediaService::class.java, "https://development.github.com/".toHttpUrl()
        )
        operation.create(MediaService::class.java)

        // 方式二【需要调用过 DevHttpManager.RM.putRetrofitBuilder() 进行存储】
        DevHttpManager.RM.reset(MediaAPI.NAME)?.hiIfNotNull { operation ->
            // 操作方法如上
        }
        /**
         * 其他 API 方法点击 [DevHttpManager.RM] 进行查看
         */
        DevHttpManager.RM
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