package ktx.dev.engine.media.luck_siege_lib.listener

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener
import com.luck.picture.lib.permissions.PermissionChecker
import com.luck.picture.lib.permissions.PermissionResultCallback
import dev.utils.app.toast.ToastUtils

/**
 * detail: PictureSelector 录音拦截器
 * @author luck
 */
open class LuckRecordAudioInterceptListener(
    // 拒绝权限提示文案
    protected val mDeniedHint: String
) : OnRecordAudioInterceptListener {

    // ==================================
    // = OnRecordAudioInterceptListener =
    // ==================================

    override fun onRecordAudio(
        fragment: Fragment,
        requestCode: Int
    ) {
        val recordAudio = arrayOf(Manifest.permission.RECORD_AUDIO)
        if (PermissionChecker.isCheckSelfPermission(fragment.context, recordAudio)) {
            startRecordSoundAction(fragment, requestCode)
        } else {
            PermissionChecker.getInstance().requestPermissions(
                fragment, arrayOf(Manifest.permission.RECORD_AUDIO),
                object : PermissionResultCallback {
                    override fun onGranted() {
                        startRecordSoundAction(fragment, requestCode)
                    }

                    override fun onDenied() {
                        ToastUtils.showShort(mDeniedHint)
                    }
                }
            )
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 启动录音意图
     * @param fragment    Fragment
     * @param requestCode 请求 code
     */
    protected fun startRecordSoundAction(
        fragment: Fragment,
        requestCode: Int
    ) {
        val recordAudioIntent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
        if (recordAudioIntent.resolveActivity(fragment.requireActivity().packageManager) != null) {
            fragment.startActivityForResult(recordAudioIntent, requestCode)
        } else {
            ToastUtils.showShort("The system is missing a recording component")
        }
    }
}