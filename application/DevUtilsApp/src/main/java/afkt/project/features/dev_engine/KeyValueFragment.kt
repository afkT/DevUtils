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
        toast_showShort(text = "putString: $result")
    }

    val clickGetString = View.OnClickListener {
        val value = KEY_STRING.kv_getString()
        toast_showShort(text = "getString: $value")
    }

    val clickPutInt = View.OnClickListener {
        val result = KEY_INT.kv_putInt(value = 99999)
        toast_showShort(text = "putInt: $result")
    }

    val clickGetInt = View.OnClickListener {
        val value = KEY_INT.kv_getInt()
        toast_showShort(text = "getInt: $value")
    }

    val clickContains = View.OnClickListener {
        val result = KEY_STRING.kv_contains()
        toast_showShort(text = "contains $KEY_STRING: $result")
    }

    val clickRemove = View.OnClickListener {
        KEY_STRING.kv_remove()
        toast_showShort(text = "remove $KEY_STRING 完成")
    }

    val clickClear = View.OnClickListener {
        kv_clear()
        toast_showShort(text = "clear 清空完成")
    }
}