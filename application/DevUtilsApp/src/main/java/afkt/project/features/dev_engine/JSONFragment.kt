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
        "toJson: $json".log_dTag(tag = TAG)
        "toJson: $json".toast_showShort()
    }

    val clickToJsonIndent = View.OnClickListener {
        val json = mockUser.toJsonIndent()
        "toJsonIndent:\n$json".log_dTag(tag = TAG)
        "toJsonIndent 已打印, 请查看 Logcat".toast_showShort()
    }

    val clickFromJson = View.OnClickListener {
        val user = MOCK_JSON.fromJson(classOfT = JsonUser::class.java)
        "fromJson: $user".toast_showShort()
    }

    val clickIsJSON = View.OnClickListener {
        val result = MOCK_JSON.isJSON()
        "isJSON: $result".toast_showShort()
    }
}

data class JsonUser(
    val name: String,
    val star: Int
)