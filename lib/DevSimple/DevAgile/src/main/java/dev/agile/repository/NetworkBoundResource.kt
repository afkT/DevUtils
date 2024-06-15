package dev.agile.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dev.agile.app.AppExecutors

/**
 * @see https://github.com/android/architecture-components-samples
 * NetworkBoundResource.kt
 */
abstract class NetworkBoundResource<ResultType, RequestType>(
    private val appExecutors: AppExecutors
) {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected abstract fun loadFromDb(): LiveData<ResultType?>

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun saveFetchData(item: RequestType)

    @MainThread
    protected abstract fun onFetchFailed(
        error: Throwable?,
        items: RequestType?
    )

    // ==========
    // = 内部方法 =
    // ==========

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    init {
        appExecutors.diskIO().execute {
            val dbSource = loadFromDb()
            appExecutors.mainThread().execute {
                result.addSource(dbSource) { data ->
                    result.removeSource(dbSource)
                    if (shouldFetch(data)) {
                        setValue(Resource.loading(null))
                        fetchFromNetwork(dbSource)
                    } else {
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType?>) {
        val apiResponse = fetchService()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveFetchData(response.data)
                        val loaded = loadFromDb()
                        appExecutors.mainThread().execute {
                            result.addSource(loaded) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }

                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute {
                        onFetchFailed(response.error, response.data)
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.error(response.error, newData))
                        }
                    }
                }

                is ApiEmptyResponse -> {
                    appExecutors.diskIO().execute {
                        val loaded = loadFromDb()
                        appExecutors.mainThread().execute {
                            result.addSource(loaded) { newData ->
                                setValue(Resource.empty(newData))
                            }
                        }
                    }
                }
            }
        }
    }
}