package dev.agile.repository

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val error: Throwable?
) {
    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(
            error: Throwable?,
            data: T?
        ): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> empty(data: T?): Resource<T> {
            return Resource(Status.EMPTY, data, null)
        }
    }
}