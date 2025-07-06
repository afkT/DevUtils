package afkt.retrofit.use.app

import afkt.retrofit.use.base.BaseActivity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CancelAdapt

// =================
// = App Helper 类 =
// =================

// ================
// = BaseActivity =
// ================

/**
 * 通用 Enable edge to edge【适配 API 35+】
 */
fun BaseActivity<*, *>.commonEnableEdgeToEdge() {
    enableEdgeToEdge()
    // 给 view 设置 insets, 使得 view 不会被 system bars 遮挡
    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(
            systemBars.left, systemBars.top,
            systemBars.right, systemBars.bottom
        )
        insets
    }
}

// ============
// = AutoSize =
// ============

/**
 * 返回自动适配 Resources
 * @param resource Resources
 * @return AutoSize Resources
 */
fun BaseActivity<*, *>.autoResources(resource: Resources?): Resources? {
    if (resource == null) return resource
    if (this !is CancelAdapt) {
        // 解决 Android 14 系统软键盘无法弹出
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return resource
        }
        // 360 -> design_width_in_dp
        if (resource.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            AutoSizeCompat.autoConvertDensityBaseOnWidth(
                resource, 360.0f
            )
        } else {
            // 横屏
            AutoSizeCompat.autoConvertDensityBaseOnHeight(
                resource, 360.0f
            )
        }
    }
    return resource
}