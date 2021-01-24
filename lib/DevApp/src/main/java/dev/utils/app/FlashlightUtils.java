package dev.utils.app;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import dev.utils.LogPrintUtils;

/**
 * detail: 手电筒工具类
 * @author Ttt
 * <pre>
 *     在非 Camera 预览页面使用的话, 需要先调用 register(), 不使用的时候 unregister();
 * </pre>
 */
public final class FlashlightUtils {

    private FlashlightUtils() {
    }

    // 日志 TAG
    private final String TAG = FlashlightUtils.class.getSimpleName();

    // FlashlightUtils 实例
    private static volatile FlashlightUtils sInstance;

    /**
     * 获取 FlashlightUtils 实例
     * @return {@link FlashlightUtils}
     */
    public static FlashlightUtils getInstance() {
        if (sInstance == null) {
            synchronized (FlashlightUtils.class) {
                if (sInstance == null) {
                    sInstance = new FlashlightUtils();
                }
            }
        }
        return sInstance;
    }

    // Camera 对象
    private Camera mCamera;

    /**
     * 注册摄像头
     * @return {@code true} success, {@code false} fail
     */
    public boolean register() {
        try {
            mCamera = Camera.open(0);
        } catch (Throwable ignore) {
            return false;
        }
        if (mCamera == null) return false;
        try {
            mCamera.setPreviewTexture(new SurfaceTexture(0));
            mCamera.startPreview();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "register");
            return false;
        }
    }

    /**
     * 注销摄像头
     * @return {@code true} success, {@code false} fail
     */
    public boolean unregister() {
        if (mCamera == null) return false;
        try {
            mCamera.stopPreview();
            mCamera.release();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregister");
        }
        return false;
    }

    // =

    /**
     * 是否支持手机闪光灯
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFlashlightEnable() {
        PackageManager packageManager = AppUtils.getPackageManager();
        return (packageManager != null) && packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // =

    /**
     * 打开闪光灯
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOn() {
        return setFlashlightOn(mCamera);
    }

    /**
     * 打开闪光灯
     * @param camera {@link android.graphics.Camera}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOn(final Camera camera) {
        if (camera != null) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                if (parameters != null && !Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                    try {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setFlashlightOn");
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setFlashlightOn - getParameters");
            }
        }
        return false;
    }

    // =

    /**
     * 关闭闪光灯
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOff() {
        return setFlashlightOff(mCamera);
    }

    /**
     * 关闭闪光灯
     * @param camera {@link android.graphics.Camera}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOff(final Camera camera) {
        if (camera != null) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                if (parameters != null && Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                    try {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        return true;
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "setFlashlightOff");
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setFlashlightOff - getParameters");
            }
        }
        return false;
    }

    // =

    /**
     * 是否打开闪光灯
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlashlightOn() {
        return isFlashlightOn(mCamera);
    }

    /**
     * 是否打开闪光灯
     * @param camera {@link android.graphics.Camera}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlashlightOn(final Camera camera) {
        if (camera != null) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                return parameters != null && Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isFlashlightOn");
            }
        }
        return false;
    }
}