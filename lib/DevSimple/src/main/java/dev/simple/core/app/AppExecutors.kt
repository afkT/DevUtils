package dev.simple.core.app

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * detail: 整个应用程序的全局线程池
 * @author Google
 */
class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    // =

    companion object {

        fun newDefault(): AppExecutors {
            return AppExecutors(
                Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                MainThreadExecutor()
            )
        }

        fun instance(): AppExecutors {
            return Holder.holder
        }
    }

    private object Holder {
        val holder = newDefault()
    }
}