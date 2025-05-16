package dev.agile.repository

sealed class ApiResponse<T> {

    companion object {

        fun <T> success(data: T): ApiSuccessResponse<T> {
            return ApiSuccessResponse(data)
        }

        fun <T> error(
            error: Throwable?,
            data: T?
        ): ApiErrorResponse<T> {
            return ApiErrorResponse(error, data)
        }

        fun <T> empty(): ApiEmptyResponse<T> {
            return ApiEmptyResponse()
        }
    }
}

data class ApiSuccessResponse<T>(
    val data: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(
    val error: Throwable?,
    val data: T?
) : ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()