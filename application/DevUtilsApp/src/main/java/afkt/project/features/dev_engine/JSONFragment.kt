package afkt.project.features.dev_engine

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentDevEngineJsonBinding
import android.view.View
import dev.engine.extensions.json.fromJson
import dev.engine.extensions.json.isJSON
import dev.engine.extensions.json.toJson
import dev.engine.extensions.json.toJsonIndent
import dev.engine.extensions.log.log_dTag
import dev.engine.extensions.toast.toast_showShort

/**
 * detail: JSON Engine 映射
 * @author Ttt
 */
class JSONFragment : AppFragment<FragmentDevEngineJsonBinding, JSONViewModel>(
    R.layout.fragment_dev_engine_json, BR.viewModel
)

/**
 * detail: JSON Engine 映射 ViewModel
 * @author Ttt
 */
class JSONViewModel : AppViewModel() {

    private companion object {

        const val TAG = "JSONEngine"

        const val MOCK_JSON = "{\"name\":\"DevUtils\",\"star\":99999}"
    }

    private val mockUser = JsonUser(name = "DevUtils", star = 99999)

    val clickToJson = View.OnClickListener {
        val json = mockUser.toJson()
        TAG.log_dTag(message = "toJson: $json")
        toast_showShort(text = "toJson: $json")
    }

    val clickToJsonIndent = View.OnClickListener {
        val json = mockUser.toJsonIndent()
        TAG.log_dTag(message = "toJsonIndent:\n$json")
        toast_showShort(text = "toJsonIndent 已打印, 请查看 Logcat")
    }

    val clickFromJson = View.OnClickListener {
        val user = MOCK_JSON.fromJson(classOfT = JsonUser::class.java)
        toast_showShort(text = "fromJson: $user")
    }

    val clickIsJSON = View.OnClickListener {
        val result = MOCK_JSON.isJSON()
        toast_showShort(text = "isJSON: $result")
    }
}

data class JsonUser(
    val name: String,
    val star: Int
)