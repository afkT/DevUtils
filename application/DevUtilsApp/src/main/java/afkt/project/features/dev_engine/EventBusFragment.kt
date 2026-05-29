package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineEventBusBinding
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.core.LiveEvent
import dev.engine.extensions.eventbus.*
import dev.engine.extensions.toast.toast_showShort
import dev.utils.LogPrintUtils

/**
 * detail: EventBus Engine 事件总线
 * @author Ttt
 */
class EventBusFragment : AppFragment<FragmentDevEngineEventBusBinding, EventBusViewModel>(
    R.layout.fragment_dev_engine_event_bus, BR.viewModel
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(viewLifecycleOwner)
    }
}

/**
 * detail: EventBus Engine 事件总线 ViewModel
 * @author Ttt
 */
class EventBusViewModel : AppViewModel() {

    private companion object {

        const val EVENT_BUS_TAG = "EventBusEngine"
    }

    private var lifecycleOwner: LifecycleOwner? = null

    private val foreverObserver = Observer<MockOrderlyEvent> {
        printEvent("observeForever", it)
    }

    private val stickyForeverObserver = Observer<MockBroadcastEvent> {
        printEvent("observeStickyForever", it)
    }

    private var isObserveForever = false

    /**
     * 初始化 EventBus 监听
     * @param owner 生命周期持有者
     */
    fun initialize(owner: LifecycleOwner) {
        lifecycleOwner = owner
        eventbus_observe(
            type = MockPostEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observe", it)
            }
        )

        eventbus_observeSticky(
            type = MockDelayEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observeSticky", it)
            }
        )

        eventbus_observe(
            type = MockLifecycleDelayEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observe lifecycle", it)
            }
        )

        eventbus_observe(
            type = MockAcrossProcessEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observe across process", it)
            }
        )

        eventbus_observe(
            type = MockAcrossAppEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observe across app", it)
            }
        )

        eventbus_observe(
            type = MockBroadcastOptionsEvent::class.java,
            lifecycle = owner,
            observer = Observer {
                printEvent("observe broadcast options", it)
            }
        )

        if (isObserveForever) return
        isObserveForever = true

        eventbus_observeForever(
            type = MockOrderlyEvent::class.java,
            observer = foreverObserver
        )

        eventbus_observeStickyForever(
            type = MockBroadcastEvent::class.java,
            observer = stickyForeverObserver
        )
    }

    val clickEventBusPost = View.OnClickListener {
        eventbus_post(
            value = MockPostEvent(
                id = 1,
                name = "DevUtils",
                timeMillis = System.currentTimeMillis()
            )
        )
    }

    val clickEventBusPostDelay = View.OnClickListener {
        eventbus_postDelay(
            value = MockDelayEvent(
                taskId = "delay_task",
                delayMillis = 1000L,
                retry = true
            ),
            delay = 1000L
        )
    }

    val clickEventBusPostDelayLifecycle = View.OnClickListener {
        val owner = lifecycleOwner ?: return@OnClickListener
        eventbus_postDelay(
            lifecycle = owner,
            value = MockLifecycleDelayEvent(
                owner = "EventBusFragment",
                message = "lifecycle postDelay",
                delayMillis = 1000L
            ),
            delay = 1000L
        )
    }

    val clickEventBusPostOrderly = View.OnClickListener {
        eventbus_postOrderly(
            value = MockOrderlyEvent(
                orderNo = 10086L,
                status = "created",
                amount = 99.9
            )
        )
    }

    val clickEventBusPostAcrossProcess = View.OnClickListener {
        eventbus_postAcrossProcess(
            value = MockAcrossProcessEvent(
                processName = "main",
                pid = android.os.Process.myPid()
            )
        )
    }

    val clickEventBusPostAcrossApp = View.OnClickListener {
        eventbus_postAcrossApp(
            value = MockAcrossAppEvent(
                packageName = "afkt.project",
                versionName = "mock_1.0.0"
            )
        )
    }

    @Suppress("DEPRECATION")
    val clickEventBusBroadcast = View.OnClickListener {
        eventbus_broadcast(
            value = MockBroadcastEvent(
                title = "broadcast",
                content = "deprecated broadcast event"
            )
        )
    }

    val clickEventBusBroadcastOptions = View.OnClickListener {
        eventbus_broadcast(
            value = MockBroadcastOptionsEvent(
                title = "broadcast options",
                foreground = true,
                onlyInApp = true
            ),
            foreground = true,
            onlyInApp = true
        )
    }

    override fun onCleared() {
        eventbus_removeObserver(
            type = MockOrderlyEvent::class.java,
            observer = foreverObserver
        )
        eventbus_removeObserver(
            type = MockBroadcastEvent::class.java,
            observer = stickyForeverObserver
        )
        isObserveForever = false
        super.onCleared()
    }

    private fun printEvent(
        source: String,
        event: LiveEvent
    ) {
        val message = "$source: $event"
        LogPrintUtils.dTag(EVENT_BUS_TAG, message)
        toast_showShort(text = message)
    }
}

data class MockPostEvent(
    val id: Int,
    val name: String,
    val timeMillis: Long
) : LiveEvent

data class MockDelayEvent(
    val taskId: String,
    val delayMillis: Long,
    val retry: Boolean
) : LiveEvent

data class MockLifecycleDelayEvent(
    val owner: String,
    val message: String,
    val delayMillis: Long
) : LiveEvent

data class MockOrderlyEvent(
    val orderNo: Long,
    val status: String,
    val amount: Double
) : LiveEvent

data class MockAcrossProcessEvent(
    val processName: String,
    val pid: Int
) : LiveEvent

data class MockAcrossAppEvent(
    val packageName: String,
    val versionName: String
) : LiveEvent

data class MockBroadcastEvent(
    val title: String,
    val content: String
) : LiveEvent

data class MockBroadcastOptionsEvent(
    val title: String,
    val foreground: Boolean,
    val onlyInApp: Boolean
) : LiveEvent