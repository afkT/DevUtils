package dev.utils.app;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.io.IOException;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 手电筒工具类
 * @author Ttt
 */
public final class FlashlightUtils {

    private FlashlightUtils() {
    }

    // 日志 TAG
    private final String TAG = FlashlightUtils.class.getSimpleName();
    // 单例对象
    private static final FlashlightUtils INSTANCE = new FlashlightUtils();

    /**
     * 获取 FlashlightUtils 实例
     * @return {@link FlashlightUtils}
     */
    public static FlashlightUtils getInstance() {
        return INSTANCE;
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
        } catch (Throwable t) {
            return false;
        }
        if (mCamera == null) return false;
        try {
            mCamera.setPreviewTexture(new SurfaceTexture(0));
            mCamera.startPreview();
            return true;
        } catch (IOException e) {
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

    /**
     * 打开闪光灯
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOn() {
        if (mCamera == null) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            return true;
        }
        return false;
    }

    /**
     * 关闭闪光灯
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlashlightOff() {
        if (mCamera == null) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        if (Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            return true;
        }
        return false;
    }

    /**
     * 是否打开闪光灯
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlashlightOn() {
        if (mCamera == null) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        return Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * 是否支持手机闪光灯
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlashlightEnable() {
        try {
            return AppUtils.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isFlashlightEnable");
        }
        return false;
    }

    // =

    /**
     * 打开闪光灯
     * @param camera {@link android.graphics.Camera}
     * @return {@code true} yes, {@code false} no
     */
    public boolean setFlashlightOn(final Camera camera) {
        if (camera != null) {
            try {
                Camera.Parameters parameter = camera.getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameter);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setFlashlightOn");
            }
        }
        return false;
    }

    /**
     * 关闭闪光灯
     * @param camera {@link android.graphics.Camera}
     * @return {@code true} yes, {@code false} no
     */
    public boolean setFlashlightOff(final Camera camera) {
        if (camera != null) {
            try {
                Camera.Parameters parameter = camera.getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameter);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setFlashlightOff");
            }
        }
        return false;
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
                return Camera.Parameters.FLASH_MODE_TORCH.equals(parameters.getFlashMode());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isFlashlightOn");
            }
        }
        return false;
    }
}