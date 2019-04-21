package dev.utils.app.camera1;

import android.graphics.Point;
import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import dev.utils.LogPrintUtils;
import dev.utils.app.ScreenUtils;

/**
 * detail: 摄像头 预览、输出大小 辅助类
 * @author Ttt
 * <pre>
 *      需要的权限:
 *      <uses-permission android:name="android.permission.CAMERA" /> 摄像头
 *      <uses-permission android:name="android.permission.VIBRATE" /> 震动
 *      <uses-permission android:name="android.permission.FLASHLIGHT" /> 手电筒
 *      正常只需要摄像头权限
 * </pre>
 */
public final class CameraSizeAssist {

    // 日志 TAG
    private final String TAG = CameraSizeAssist.class.getSimpleName();
    // 摄像头 Camera
    private Camera mCamera;
    // 默认最大的偏差
    private final double MAX_ASPECT_DISTORTION = 0.15;
    // 最小尺寸, 小于该尺寸则不处理
    private final int MIN_PREVIEW_PIXELS = 480 * 320;

    // ============
    // = 构造函数 =
    // ============

    public CameraSizeAssist(final Camera camera) {
        this.mCamera = camera;
    }

    /**
     * 获取摄像头
     * @return {@link android.hardware.Camera}
     */
    public Camera getCamera() {
        return mCamera;
    }

    // ================
    // = 预览大小相关 =
    // ================

    /**
     * 设置预览大小
     * @param previewSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPreviewSize(final Camera.Size previewSize) {
        return setPreviewSize(null, previewSize);
    }

    /**
     * 设置预览大小
     * @param parameters
     * @param previewSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPreviewSize(Camera.Parameters parameters, final Camera.Size previewSize) {
        if (mCamera != null && previewSize != null) {
            try {
                if (parameters == null) {
                    parameters = mCamera.getParameters();
                }
                // 设置预览大小
                parameters.setPreviewSize(previewSize.width, previewSize.height);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setPreviewSize");
            }
        }
        return parameters;
    }

    // =

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸
     * @return Camera 预览分辨率
     */
    public Camera.Size getPreviewSize() {
        return getPreviewSize(null, -1d);
    }

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸
     * @param point
     * @return Camera 预览分辨率
     */
    public Camera.Size getPreviewSize(final Point point) {
        return getPreviewSize(point, -1d);
    }

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸
     * @return Camera 预览分辨率
     * @distortion point
     */
    public Camera.Size getPreviewSize(final double distortion) {
        return getPreviewSize(null, distortion);
    }

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸(无任何操作, 单独把Camera显示到SurfaceView 预览尺寸)
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 预览分辨率
     */
    public Camera.Size getPreviewSize(final Point point, final double distortion) {
        if (mCamera == null) {
            LogPrintUtils.dTag(TAG, "camera is null");
            return null;
        }
        try {
            // 计算大小并返回
            return calcPreviewSize(point, distortion);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPreviewSize");
        }
        return null;
    }

    // ================
    // = 拍照大小相关 =
    // ================

    /**
     * 设置拍照图片大小
     * @param pictureSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPictureSize(final Camera.Size pictureSize) {
        return setPictureSize(null, pictureSize);
    }

    /**
     * 设置拍照图片大小
     * @param parameters
     * @param pictureSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPictureSize(Camera.Parameters parameters, final Camera.Size pictureSize) {
        if (mCamera != null && pictureSize != null) {
            try {
                if (parameters == null) {
                    parameters = mCamera.getParameters();
                }
                // 设置预览大小
                parameters.setPictureSize(pictureSize.width, pictureSize.height);
//                // 设置拍照输出格式
//                parameters.setPictureFormat(PixelFormat.JPEG);
//                // 照片质量
//                parameters.set("jpeg-quality", 70);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setPictureSize");
            }
        }
        return parameters;
    }

    // =

    /**
     * 根据手机支持的拍照分辨率计算
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize() {
        return getPictureSize(false, null, -1d);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param max
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(final boolean max) {
        return getPictureSize(max, null, -1d);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param point
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(final Point point) {
        return getPictureSize(false, point, -1d);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param distortion
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(final double distortion) {
        return getPictureSize(false, null, distortion);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param point
     * @param distortion
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(final Point point, final double distortion) {
        return getPictureSize(false, point, distortion);
    }

    /**
     * 根据手机支持的拍照分辨率计算，设置预览尺寸
     * @param max        是否使用最大的尺寸
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(final boolean max, final Point point, final double distortion) {
        if (mCamera == null) {
            LogPrintUtils.dTag(TAG, "camera is null");
            return null;
        }
        try {
            // 计算大小并返回
            return calcPictureSize(max, point, distortion);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPictureSize");
        }
        return null;
    }

    // ====================
    // = 视频录制大小相关 =
    // ====================

    /**
     * 根据手机支持的视频录制分辨率计算
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize() {
        return getVideoSize(false, null, -1d, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param max
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(final boolean max) {
        return getVideoSize(max, null, -1d, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param point
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(final Point point) {
        return getVideoSize(false, point, -1d, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param distortion
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(final double distortion) {
        return getVideoSize(false, null, distortion, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param point
     * @param distortion
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(final Point point, final double distortion) {
        return getVideoSize(false, point, distortion, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算，设置预览尺寸
     * @param max        是否使用最大的尺寸
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @param minAccord  是否存在最小使用最小
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(final boolean max, final Point point, final double distortion, final boolean minAccord) {
        if (mCamera == null) {
            LogPrintUtils.dTag(TAG, "camera is null");
            return null;
        }
        try {
            // 计算大小并返回
            return calcVideoSize(max, point, distortion, minAccord);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoSize");
        }
        return null;
    }

    // ============
    // = 预览大小 =
    // ============

    /**
     * 根据对应的尺寸, 计算相应最符合的大小
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸) point.x = > 宽, point.y => 高
     * @param distortion 偏差比例值
     * @return Camera 预览分辨率
     */
    private Camera.Size calcPreviewSize(Point point, double distortion) {
        // 判断是否为 null
        if (point == null) {
            point = ScreenUtils.getScreenWidthHeightToPoint();
        }
        // 如果误差为负数, 则使用默认值
        if (distortion < 0) {
            distortion = MAX_ASPECT_DISTORTION;
        }
        // 获取 Camera 参数
        Camera.Parameters params = mCamera.getParameters();
        // 获取手机支持的分辨率集合,并以宽度为基准降序排序
        List<Camera.Size> listPreviewSizes = params.getSupportedPreviewSizes();
        // 防止数据为null
        if (listPreviewSizes == null) {
            // 获取默认预览大小
            Camera.Size defaultSize = params.getPreviewSize();
            return defaultSize;
        }

        // 进行排序处理 -> 并以宽度 * 高度 为基准降序排序
        Collections.sort(listPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int leftPixels = lhs.height * lhs.width;
                int rightPixels = rhs.height * rhs.width;

                if (leftPixels > rightPixels) {
                    return -1;
                } else if (leftPixels == rightPixels) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        // = 打印信息 =
        if (LogPrintUtils.isPrintLog()) {
            StringBuilder builder = new StringBuilder();
            builder.append("预览支持尺寸: \r\n");
            // 打印信息
            for (Camera.Size previewSize : listPreviewSizes) {
                // 例: 1080x1920
                builder.append(previewSize.width).append("x").append(previewSize.height).append("\r\n");
            }
            // 打印尺寸信息
            LogPrintUtils.dTag(TAG, builder.toString());
        }

        // 判断是否竖屏 point.x = > 宽, point.y => 高
        boolean isPortrait = point.y > point.x;
        // 如果是竖屏, 则修改
        if (isPortrait) {
            int tempY = point.y;
            int tempX = point.x;
            // 进行转换
            point.x = tempY;
            point.y = tempX;
        }

        // 计算比例值 宽 / 高
        double screenAspectRatio = (double) point.x / (double) point.y;

        // 循环遍历判断
        Iterator<Camera.Size> iterator = listPreviewSizes.iterator();
        while (iterator.hasNext()) {
            // 获取预览大小
            Camera.Size previewSize = iterator.next();
            // 获取宽、高
            int realWidth = previewSize.width;
            int realHeight = previewSize.height;
            // 小于最小尺寸, 则不处理
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                iterator.remove();
                continue;
            }

            // 判断预选的尺寸是否竖屏
            boolean isCandidatePortrait = realWidth < realHeight;
            // 翻转宽、高
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;

            // 计算比例
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double calcDistortion = Math.abs(aspectRatio - screenAspectRatio);

            // 如果大于指定的尺寸比例差, 则跳过
            if (calcDistortion > distortion) {
                iterator.remove();
                continue;
            }

            // 如果相符, 则直接跳过
            if (maybeFlippedWidth == point.x && maybeFlippedHeight == point.y) {
                return previewSize;
            }
        }

        // 如果没有精确匹配，请使用最大预览大小。这对于旧设备来说不是一个好主意，因为需要额外的计算。我们很可能会在新的Android 4 + 设备上运行，那里的CPU功能更强大。
        if (!listPreviewSizes.isEmpty()) {
            // 获取最大的尺寸
            Camera.Size largestPreview = listPreviewSizes.get(0);
            return largestPreview;
        }

        // = 都不匹配, 则用默认分辨率 =
        // 获取默认预览大小
        Camera.Size defaultSize = params.getPreviewSize();
        return defaultSize;
    }

    // ============
    // = 拍照大小 =
    // ============

    /**
     * 根据对应的尺寸, 计算相应最符合的大小
     * @param max        是否使用最大的尺寸
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸) point.x = > 宽, point.y => 高
     * @param distortion 偏差比例值
     * @return Camera 拍照分辨率
     */
    private Camera.Size calcPictureSize(final boolean max, Point point, double distortion) {
        // 判断是否为 null
        if (point == null) {
            point = ScreenUtils.getScreenWidthHeightToPoint();
        }
        // 如果误差为负数, 则使用默认值
        if (distortion < 0) {
            distortion = MAX_ASPECT_DISTORTION;
        }
        // 获取 Camera 参数
        Camera.Parameters params = mCamera.getParameters();
        // 获取手机支持的分辨率集合,并以宽度为基准降序排序
        List<Camera.Size> listPictureSizes = params.getSupportedPictureSizes();
        // 防止数据为null
        if (listPictureSizes == null) {
            // 获取默认拍照大小
            Camera.Size defaultSize = params.getPictureSize();
            return defaultSize;
        }

        // 进行排序处理 -> 并以宽度 * 高度 为基准降序排序
        Collections.sort(listPictureSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int leftPixels = lhs.height * lhs.width;
                int rightPixels = rhs.height * rhs.width;

                if (leftPixels > rightPixels) {
                    return -1;
                } else if (leftPixels == rightPixels) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        // = 打印信息 =
        if (LogPrintUtils.isPrintLog()) {
            StringBuilder builder = new StringBuilder();
            builder.append("拍照支持尺寸: \r\n");
            // 打印信息
            for (Camera.Size pictureSize : listPictureSizes) {
                // 例: 1080x1920
                builder.append(pictureSize.width).append("x").append(pictureSize.height).append("\r\n");
            }
            // 打印尺寸信息
            LogPrintUtils.dTag(TAG, builder.toString());
        }

        // 判断是否拿最大支持的尺寸
        if (max) {
            if (!listPictureSizes.isEmpty()) {
                // 获取最大的尺寸
                Camera.Size largestPicture = listPictureSizes.get(0);
                return largestPicture;
            } else {
                // 获取默认拍照大小
                Camera.Size defaultSize = params.getPictureSize();
                return defaultSize;
            }
        }

        // 判断是否竖屏 point.x = > 宽, point.y => 高
        boolean isPortrait = point.y > point.x;
        // 如果是竖屏, 则修改
        if (isPortrait) {
            int tempY = point.y;
            int tempX = point.x;
            // 进行转换
            point.x = tempY;
            point.y = tempX;
        }

        // 计算比例值 宽 / 高
        double pictureAspectRatio = (double) point.x / (double) point.y;

        // 判断最大符合
        Camera.Size maxAccordSize = null;

        // 循环遍历判断
        Iterator<Camera.Size> iterator = listPictureSizes.iterator();
        while (iterator.hasNext()) {
            // 获取拍照大小
            Camera.Size pictureSize = iterator.next();
            // 获取宽、高
            int realWidth = pictureSize.width;
            int realHeight = pictureSize.height;
            // 小于最小尺寸, 则不处理
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                iterator.remove();
                continue;
            }

            // 判断预选的尺寸是否竖屏
            boolean isCandidatePortrait = realWidth < realHeight;
            // 翻转宽、高
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;

            // 计算比例
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double calcDistortion = Math.abs(aspectRatio - pictureAspectRatio);

            // 如果大于指定的尺寸比例差, 则跳过
            if (calcDistortion > distortion) {
                iterator.remove();
                continue;
            }

            // 如果相符, 则直接跳过
            if (maybeFlippedWidth == point.x && maybeFlippedHeight == point.y) {
                return pictureSize;
            }

            // 保存最大相符的尺寸
            if (maxAccordSize == null) {
                maxAccordSize = pictureSize;
            }
        }

        // 如果存在最相符的则返回
        if (maxAccordSize != null) {
            return maxAccordSize;
        }

        // 如果没有精确匹配，请使用最大尺寸大小
        if (!listPictureSizes.isEmpty()) {
            // 获取最大的尺寸
            Camera.Size largestPicture = listPictureSizes.get(0);
            return largestPicture;
        }

        // = 都不匹配, 则用默认分辨率 =
        // 获取默认拍照大小
        Camera.Size defaultSize = params.getPictureSize();
        return defaultSize;
    }

    // ================
    // = 视频录制尺寸 =
    // ================

    /**
     * 根据对应的尺寸, 计算相应最符合的大小
     * @param max        是否使用最大的尺寸
     * @param point      指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸) point.x = > 宽, point.y => 高
     * @param distortion 偏差比例值
     * @param minAccord  是否判断存在最小使用最小
     * @return Camera 视频分辨率
     */
    private Camera.Size calcVideoSize(final boolean max, Point point, double distortion, final boolean minAccord) {
        // 判断是否为 null
        if (point == null) {
            point = ScreenUtils.getScreenWidthHeightToPoint();
        }
        // 如果误差为负数, 则使用默认值
        if (distortion < 0) {
            distortion = MAX_ASPECT_DISTORTION;
        }
        // 获取 Camera 参数
        Camera.Parameters params = mCamera.getParameters();
        // 获取手机支持的分辨率集合,并以宽度为基准降序排序
        List<Camera.Size> listVideoSizes = params.getSupportedVideoSizes();
        // 防止数据为null
        if (listVideoSizes == null) {
            // 获取默认拍照大小
            Camera.Size defaultSize = params.getPreferredPreviewSizeForVideo();
            return defaultSize;
        }

        // 进行排序处理 -> 并以宽度 * 高度 为基准降序排序
        Collections.sort(listVideoSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int leftPixels = lhs.height * lhs.width;
                int rightPixels = rhs.height * rhs.width;

                if (leftPixels > rightPixels) {
                    return -1;
                } else if (leftPixels == rightPixels) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        // = 打印信息 =
        if (LogPrintUtils.isPrintLog()) {
            StringBuilder builder = new StringBuilder();
            builder.append("视频录制支持尺寸: \r\n");
            // 打印信息
            for (Camera.Size videoSize : listVideoSizes) {
                // 例: 1080x1920
                builder.append(videoSize.width).append("x").append(videoSize.height).append("\r\n");
            }
            // 打印尺寸信息
            LogPrintUtils.dTag(TAG, builder.toString());
        }

        // 判断是否拿最大支持的尺寸
        if (max) {
            if (!listVideoSizes.isEmpty()) {
                // 获取最大的尺寸
                Camera.Size largestVideo = listVideoSizes.get(0);
                return largestVideo;
            } else {
                // 获取默认视频大小
                Camera.Size defaultSize = params.getPreferredPreviewSizeForVideo();
                return defaultSize;
            }
        }

        // 判断是否竖屏 point.x = > 宽, point.y => 高
        boolean isPortrait = point.y > point.x;
        // 如果是竖屏, 则修改
        if (isPortrait) {
            int tempY = point.y;
            int tempX = point.x;
            // 进行转换
            point.x = tempY;
            point.y = tempX;
        }

        // 计算比例值 宽 / 高
        double videoAspectRatio = (double) point.x / (double) point.y;

        // 判断最大符合
        Camera.Size maxAccordSize = null;
        // 判断最小符合
        Camera.Size minAccordSize = null;

        // 循环遍历判断
        Iterator<Camera.Size> iterator = listVideoSizes.iterator();
        while (iterator.hasNext()) {
            // 获取视频大小
            Camera.Size videoSize = iterator.next();
            // 获取宽、高
            int realWidth = videoSize.width;
            int realHeight = videoSize.height;
            // 小于最小尺寸, 则不处理
            if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                iterator.remove();
                continue;
            }

            // 判断预选的尺寸是否竖屏
            boolean isCandidatePortrait = realWidth < realHeight;
            // 翻转宽、高
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;

            // 计算比例
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double calcDistortion = Math.abs(aspectRatio - videoAspectRatio);

            // 如果大于指定的尺寸比例差, 则跳过
            if (calcDistortion > distortion) {
                iterator.remove();
                continue;
            }

            // 如果相符, 则直接跳过
            if (maybeFlippedWidth == point.x && maybeFlippedHeight == point.y) {
                return videoSize;
            }

            // 保存最大相符的尺寸
            if (maxAccordSize == null) {
                maxAccordSize = videoSize;
            }
            // 保存最小符合的
            minAccordSize = videoSize;
        }

        if (minAccord && minAccordSize != null) {
            return minAccordSize;
        }

        // 如果存在最相符的则返回
        if (maxAccordSize != null) {
            return maxAccordSize;
        }

        // 如果没有精确匹配，请使用最大尺寸大小
        if (!listVideoSizes.isEmpty()) {
            // 获取最大的尺寸
            Camera.Size largestVideo = listVideoSizes.get(0);
            return largestVideo;
        }

        // = 都不匹配, 则用默认分辨率 =
        // 获取默认视频大小
        Camera.Size defaultSize = params.getPreferredPreviewSizeForVideo();
        return defaultSize;
    }
}
