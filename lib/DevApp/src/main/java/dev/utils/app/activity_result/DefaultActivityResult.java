package dev.utils.app.activity_result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.common.DevCommonUtils;

/**
 * detail: Activity Result 封装辅助类
 * @author Ttt
 * <pre>
 *     默认 Activity onActivityResult(int, int, Intent) 实现方式封装
 * </pre>
 */
public final class DefaultActivityResult {

    private DefaultActivityResult() {
    }

    // DefaultActivityResult 实例
    private static volatile DefaultActivityResult sInstance;

    /**
     * 获取 DefaultActivityResult 实例
     * @return {@link DefaultActivityResult}
     */
    public static DefaultActivityResult getInstance() {
        if (sInstance == null) {
            synchronized (DefaultActivityResult.class) {
                if (sInstance == null) {
                    sInstance = new DefaultActivityResult();
                }
            }
        }
        return sInstance;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * Activity 跳转回传
     * @param callback Activity 跳转回传回调
     * @return {@code true} success, {@code false} fail
     */
    public boolean startActivityForResult(final ResultCallback callback) {
        return DefaultActivityResult.ResultActivity.start(callback);
    }

    // ==========
    // = 跳转回传 =
    // ==========

    // 跳转回传回调 Map
    private static final Map<Integer, ResultCallback> sResultCallbackMaps = new HashMap<>();

    /**
     * detail: Activity 跳转回传回调
     * @author Ttt
     */
    public interface ResultCallback {

        /**
         * 跳转 Activity 操作
         * <pre>
         *     跳转失败, 必须返回 false 内部会根据返回值关闭 ResultActivity
         *     必须返回正确的值, 表示是否跳转成功
         * </pre>
         * @param activity {@link Activity}
         * @return {@code true} success, {@code false} fail
         */
        boolean onStartActivityForResult(Activity activity);

        /**
         * 回传处理
         * @param result     resultCode 是否等于 {@link Activity#RESULT_OK}
         * @param resultCode resultCode
         * @param intent     回传数据
         */
        void onActivityResult(
                boolean result,
                int resultCode,
                Intent intent
        );
    }

    // =============
    // = 内部封装逻辑 =
    // =============

    /**
     * detail: 回传结果处理 Activity
     * @author Ttt
     */
    public static class ResultActivity
            extends FragmentActivity {

        // 日志 TAG
        private static final String TAG = ResultActivity.class.getSimpleName();

        // 传参 UUID Key
        private static final String         EXTRA_UUID = DevFinal.STR.UUID;
        // 跳转回传回调
        private              ResultCallback mCallback;
        // 跳转回传回调
        private              Integer        mUUIDHash;

        /**
         * 跳转回传结果处理 Activity 内部方法
         * @param callback Activity 跳转回传回调
         * @return {@code true} success, {@code false} fail
         */
        protected static boolean start(final ResultCallback callback) {
            int     uuid   = -1;
            boolean result = false;
            if (callback != null) {
                uuid = DevCommonUtils.randomUUIDToHashCode();
                while (sResultCallbackMaps.containsKey(uuid)) {
                    uuid = DevCommonUtils.randomUUIDToHashCode();
                }
                sResultCallbackMaps.put(uuid, callback);
                try {
                    Intent intent = new Intent(DevUtils.getContext(), ResultActivity.class);
                    intent.putExtra(EXTRA_UUID, uuid);
                    result = AppUtils.startActivity(intent);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "start");
                }
            }
            if (!result && uuid != -1) {
                sResultCallbackMaps.remove(uuid);
            }
            return result;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            boolean result = false; // 跳转结果
            try {
                mUUIDHash = getIntent().getIntExtra(EXTRA_UUID, -1);
                mCallback = sResultCallbackMaps.get(mUUIDHash);
                result    = mCallback.onStartActivityForResult(this);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "onCreate");
            }
            if (!result) {
                if (mCallback != null) {
                    mCallback.onActivityResult(false, Activity.RESULT_CANCELED, null);
                }
                finish();
            }
        }

        @Override
        protected void onActivityResult(
                int requestCode,
                int resultCode,
                Intent intent
        ) {
            super.onActivityResult(requestCode, resultCode, intent);
            if (mCallback != null) {
                mCallback.onActivityResult(
                        resultCode == Activity.RESULT_OK,
                        resultCode, intent
                );
            }
            finish();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // 移除操作
            sResultCallbackMaps.remove(mUUIDHash);
        }
    }
}