package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineKeyValueBinding
import android.view.View
import dev.engine.extensions.keyvalue.*
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: KeyValue Engine 键值对存储
 * @author Ttt
 */
class KeyValueFragment : AppFragment<FragmentDevEngineKeyValueBinding, KeyValueViewModel>(
    R.layout.fragment_dev_engine_key_value, BR.viewModel
)

/**
 * detail: KeyValue Engine 键值对存储 ViewModel
 * @author Ttt
 */
class KeyValueViewModel : AppViewModel() {

    private companion object {

        const val KEY_STRING = "kv_key_string"
        const val KEY_INT = "kv_key_int"
    }

    val clickPutString = View.OnClickListener {
        val result = KEY_STRING.kv_putString(value = "DevUtils")
        "putString: $result".toast_showShort()
    }

    val clickGetString = View.OnClickListener {
        val value = KEY_STRING.kv_getString()
        "getString: $value".toast_showShort()
    }

    val clickPutInt = View.OnClickListener {
        val result = KEY_INT.kv_putInt(value = 99999)
        "putInt: $result".toast_showShort()
    }

    val clickGetInt = View.OnClickListener {
        val value = KEY_INT.kv_getInt()
        "getInt: $value".toast_showShort()
    }

    val clickContains = View.OnClickListener {
        val result = KEY_STRING.kv_contains()
        "contains $KEY_STRING: $result".toast_showShort()
    }

    val clickRemove = View.OnClickListener {
        KEY_STRING.kv_remove()
        "remove $KEY_STRING 完成".toast_showShort()
    }

    val clickClear = View.OnClickListener {
        kv_clear()
        "clear 清空完成".toast_showShort()
    }
}