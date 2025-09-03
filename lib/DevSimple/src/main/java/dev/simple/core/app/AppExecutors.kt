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
    private val mainThread: MainExecutor
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): MainExecutor {
        return mainThread
    }

    interface MainExecutor : Executor {

        fun execute(
            delayMillis: Long,
            command: Runnable
        )
    }

    class MainThreadExecutor : MainExecutor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

        override fun execute(
            delayMillis: Long,
            command: Runnable
        ) {
            mainThreadHandler.postDelayed(command, delayMillis)
        }
    }

    // =

    companion object {

        fun instance(): AppExecutors {
            return Holder.holder
        }

        fun newExecutors(
            diskIO: Executor = Executors.newFixedThreadPool(5),
            networkIO: Executor = Executors.newFixedThreadPool(5),
            mainThread: MainExecutor = MainThreadExecutor()
        ): AppExecutors {
            return AppExecutors(diskIO, networkIO, mainThread)
        }
    }

    private object Holder {

        val holder = AppExecutors(
            Executors.newSingleThreadExecutor(),
            Executors.newFixedThreadPool(3),
            MainThreadExecutor()
        )
    }
}