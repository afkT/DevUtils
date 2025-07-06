package afkt.project.model.button

import afkt.project.R
import afkt.project.model.basic.IntentData
import android.os.Bundle
import dev.utils.DevFinal

// =================================
// = ButtonEnum 映射 Fragment Title =
// =================================

fun ButtonEnum.title(): Bundle? {
    return IntentData.with().setTitle(
        this.text
    ).insert()
}

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
        ButtonEnum.MODULE_DEV_ASSIST_ENGINE -> R.id.DevAssistEngineFragment
        else -> DevFinal.DEFAULT.ERROR_INT
    }
}