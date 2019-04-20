package dev.utils.app.camera1;

import android.hardware.Camera;

import dev.utils.LogPrintUtils;

/**
 * detail: 摄像头相关工具类
 * @author Ttt
 */
public final class CameraUtils {

    private CameraUtils() {
    }

    // 日志 TAG
    private static final String TAG = CameraUtils.class.getSimpleName();

    // ==================
    // = 摄像头快速处理 =
    // ==================

    /**
     * 判断是否支持反转摄像头(是否存在前置摄像头)
     * @return
     */
    public static boolean isSupportReverse() {
        try {
            // 默认是不支持
            int isSupportReverse = 0;
            // 判断是否支持前置,支持则使用前置
            if (checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
                isSupportReverse += 1;
                // =
                LogPrintUtils.dTag(TAG, "支持前置摄像头(手机屏幕)");
            }
            // 判断是否支持后置,是则使用后置
            if (checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK)) {
                isSupportReverse += 1;
                // =
                LogPrintUtils.dTag(TAG, "支持后置摄像头(手机背面)");
            }
            // 如果都支持才表示支持反转
            return isSupportReverse == 2;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isSupportReverse");
        }
        // 默认不支持反转摄像头
        return false;
    }

    /**
     * 检查是否有摄像头
     * @param facing 前置还是后置
     * @return
     */
    public static boolean checkCameraFacing(final int facing) {
        try {
            int cameraCount = Camera.getNumberOfCameras();
            Camera.CameraInfo info = new Camera.CameraInfo();
            for (int i = 0; i < cameraCount; i++) {
                Camera.getCameraInfo(i, info);
                if (facing == info.facing) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "checkCameraFacing");
        }
        return false;
    }

    /**
     * 判断是否使用前置摄像头
     * @param facing
     * @return
     */
    public static boolean isFrontCamera(final int facing) {
        return facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    /**
     * 判断是否使用后置摄像头
     * @param facing
     * @return
     */
    public static boolean isBackCamera(final int facing) {
        return facing == Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    /**
     * 判断使用的视像头
     * @param isFrontCamera 是否前置摄像头
     * @return
     */
    public static int isUseCameraFacing(final boolean isFrontCamera) {
        // 默认使用后置摄像头
        int cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
        try {
            // 支持的摄像头 - 前置, 后置
            boolean[] cFacingArys = new boolean[]{false, false};
            // 判断是否支持前置
            cFacingArys[0] = checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
            // 判断是否支持后置
            cFacingArys[1] = checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
            // 进行判断想要使用的是前置，还是后置
            if (isFrontCamera && cFacingArys[0]) { // 使用前置, 必须也支持前置
                // 表示使用前置摄像头
                cameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;
            } else {
                // 表示使用后置摄像头
                cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isUseCameraFacing");
        }
        return cameraFacing;
    }

    // =

    /**
     * 释放摄像头资源
     * @param camera 摄像头对象
     */
    public static void freeCameraResource(android.hardware.Camera camera) {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.lock();
                camera.release();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "freeCameraResource");
        } finally {
            camera = null;
        }
    }

    /**
     * 初始化摄像头
     * @param camera
     * @param isFrontCamera {@code true} 前置(屏幕面), {@code false} 后置(手机背面)
     * @return 使用的摄像头
     */
    public static Camera initCamera(Camera camera, final boolean isFrontCamera) {
        // 如果之前存在摄像头数据, 则释放资源
        if (camera != null) {
            freeCameraResource(camera);
        }
        try {
            // 进行判断想要使用的是前置，还是后置
            if (isFrontCamera && checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT)) { // 使用前置, 必须也支持前置
                // 初始化前置摄像头
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                // 初始化后置摄像头
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "initCamera");
            // 释放资源
            freeCameraResource(camera);
        }
        return camera;
    }

    /**
     * 打开摄像头
     * @param cameraId {@link Camera.CameraInfo} CAMERA_FACING_FRONT(前置), CAMERA_FACING_BACK(后置)
     * @return
     */
    public static Camera open(int cameraId) {
        // 判断支持的摄像头数量
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return null;
        }
        try {
            // 判断是否指定哪个摄像头
            boolean explicitRequest = cameraId >= 0;
            // 如果没指定, 则进行判断处理
            if (!explicitRequest) { // 默认使用 后置摄像头, 没有后置才用其他(前置)
                int index = 0;
                while (index < numCameras) {
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(index, cameraInfo);
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        break;
                    }
                    index++;
                }
                cameraId = index;
            }

            Camera camera;
            if (cameraId < numCameras) {
                camera = Camera.open(cameraId);
            } else {
                if (explicitRequest) {
                    camera = null;
                } else {
                    // 默认使用后置
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                }
            }
            return camera;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "open - cameraId: " + cameraId);
        }
        return null;
    }

    /**
     * 打开摄像头(默认后置摄像头)
     * @return
     */
    public static Camera open() {
        return open(-1); // Camera.CameraInfo.CAMERA_FACING_BACK
    }
}
