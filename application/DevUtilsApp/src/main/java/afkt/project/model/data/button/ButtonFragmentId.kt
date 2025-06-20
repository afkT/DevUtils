package afkt.project.model.data.button

import afkt.project.R
import dev.utils.DevFinal

// ==============================
// = ButtonEnum 映射 Fragment Id =
// ==============================

/**
 * Button 转换对应的 Fragment 页面 Id
 * @return FragmentId
 */
fun ButtonEnum.fragmentId(): Int {
    return when (this) {
        ButtonEnum.MODULE_LIB -> R.id.LibUtilsFragment
        else -> DevFinal.DEFAULT.ERROR_INT
    }
}