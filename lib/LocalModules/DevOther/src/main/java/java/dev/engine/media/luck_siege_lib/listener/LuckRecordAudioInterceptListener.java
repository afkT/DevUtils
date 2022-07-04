package java.dev.engine.media.luck_siege_lib.listener;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionResultCallback;

import dev.utils.app.toast.ToastUtils;

/**
 * detail: PictureSelector 录音拦截器
 * @author luck
 */
public class LuckRecordAudioInterceptListener
        implements OnRecordAudioInterceptListener {

    // 拒绝权限提示文案
    protected final String mDeniedHint;

    // ==========
    // = 构造函数 =
    // ==========

    public LuckRecordAudioInterceptListener(final String deniedHint) {
        this.mDeniedHint = deniedHint;
    }

    // ==================================
    // = OnRecordAudioInterceptListener =
    // ==================================

    @Override
    public void onRecordAudio(
            Fragment fragment,
            int requestCode
    ) {
        String[] recordAudio = {Manifest.permission.RECORD_AUDIO};
        if (PermissionChecker.isCheckSelfPermission(fragment.getContext(), recordAudio)) {
            startRecordSoundAction(fragment, requestCode);
        } else {
            PermissionChecker.getInstance().requestPermissions(
                    fragment, new String[]{Manifest.permission.RECORD_AUDIO},
                    new PermissionResultCallback() {
                        @Override
                        public void onGranted() {
                            startRecordSoundAction(fragment, requestCode);
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.showShort(mDeniedHint);
                        }
                    }
            );
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
    protected void startRecordSoundAction(
            final Fragment fragment,
            final int requestCode
    ) {
        Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (recordAudioIntent.resolveActivity(fragment.requireActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(recordAudioIntent, requestCode);
        } else {
            ToastUtils.showShort("The system is missing a recording component");
        }
    }
}