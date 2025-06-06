package afkt.httpmanager.use.feature.media

import afkt.httpmanager.use.base.BaseViewModel
import afkt.httpmanager.use.feature.media.data.api.MediaAPI
import afkt.httpmanager.use.feature.media.data.model.MovieDetailBean
import afkt.httpmanager.use.feature.media.data.model.PhotoBean
import afkt.httpmanager.use.network.model.ServiceException
import dev.mvvm.utils.hi.hiif.hiIfNotNull
import dev.retrofit.launchExecuteRequest

/**
 * detail: Retrofit Manager Request Repository
 * @author Ttt
 */
class RMRepository {

    /**
     * 获取摄影图片列表
     */
    fun fetchPhotoList(
        viewModel: BaseViewModel,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (List<PhotoBean>) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            MediaAPI.api().getPhotoList()
        }, start = {
            startBlock.invoke()
        }, success = { result ->
            result.hiIfNotNull {
                if (it.isSuccess()) {
                    successBlock.invoke(it.requireData())
                } else {
                    throw ServiceException(it.getMessage())
                }
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {
            finishBlock.invoke()
        })
    }

    /**
     * 获取电影详情信息
     */
    fun fetchMovieDetail(
        viewModel: BaseViewModel,
        startBlock: () -> Unit = {},
        finishBlock: () -> Unit = {},
        errorBlock: (Throwable) -> Unit = {},
        successBlock: (MovieDetailBean) -> Unit
    ) {
        viewModel.launchExecuteRequest(block = {
            MediaAPI.api().getMovieDetail()
        }, start = {
            startBlock.invoke()
        }, success = { result ->
            result.hiIfNotNull {
                if (it.isSuccess()) {
                    successBlock.invoke(it.requireData())
                } else {
                    throw ServiceException(it.getMessage())
                }
            }
        }, error = {
            errorBlock.invoke(it)
        }, finish = {
            finishBlock.invoke()
        })
    }
}