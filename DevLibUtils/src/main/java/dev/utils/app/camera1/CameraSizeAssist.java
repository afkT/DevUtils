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
 * Created by Ttt
 * <uses-permission android:name="android.permission.CAMERA" /> 摄像头
 * <uses-permission android:name="android.permission.VIBRATE" /> 震动
 * <uses-permission android:name="android.permission.FLASHLIGHT" /> 手电筒
 * 正常只需要摄像头权限
 */
public final class CameraSizeAssist {

    // 日志TAG
    private final String TAG = CameraSizeAssist.class.getSimpleName();
    // 摄像头 Camera
    private Camera mCamera;
    // 默认最大的偏差
    private final double MAX_ASPECT_DISTORTION = 0.15;
    // 最小尺寸, 小于该尺寸则不处理
    private final int MIN_PREVIEW_PIXELS = 480 * 320;

    // == 构造函数 ==

    public CameraSizeAssist(Camera camera) {
        this.mCamera = camera;
    }

    /**
     * 获取摄像头
     * @return {@link android.hardware.Camera}
     */
    public Camera getCamera() {
        return mCamera;
    }

    // ==================
    // == 预览大小相关 ==
    // ==================

    /**
     * 设置预览大小
     * @param previewSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPreviewSize(Camera.Size previewSize) {
        return setPreviewSize(null, previewSize);
    }

    /**
     * 设置预览大小
     * @param parameters
     * @param previewSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPreviewSize(Camera.Parameters parameters, Camera.Size previewSize) {
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
    public Camera.Size getPreviewSize(Point point) {
        return getPreviewSize(point, -1d);
    }

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸
     * @distortion point
     * @return Camera 预览分辨率
     */
    public Camera.Size getPreviewSize(double distortion) {
        return getPreviewSize(null, distortion);
    }

    /**
     * 根据手机支持的预览分辨率计算，设置预览尺寸(无任何操作, 单独把Camera显示到SurfaceView 预览尺寸)
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 预览分辨率
     */
    public Camera.Size getPreviewSize(Point point, double distortion) {
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

    // ==================
    // == 拍照大小相关 ==
    // ==================

    /**
     * 设置拍照图片大小
     * @param pictureSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPictureSize(Camera.Size pictureSize) {
        return setPictureSize(null, pictureSize);
    }

    /**
     * 设置拍照图片大小
     * @param parameters
     * @param pictureSize
     * @return {@link Camera.Parameters}
     */
    public Camera.Parameters setPictureSize(Camera.Parameters parameters, Camera.Size pictureSize) {
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
    public Camera.Size getPictureSize(boolean max) {
        return getPictureSize(max, null, -1d);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param point
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(Point point) {
        return getPictureSize(false, point, -1d);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param distortion
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(double distortion) {
        return getPictureSize(false, null, distortion);
    }

    /**
     * 根据手机支持的拍照分辨率计算
     * @param point
     * @param distortion
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(Point point, double distortion) {
        return getPictureSize(false, point, distortion);
    }

    /**
     * 根据手机支持的拍照分辨率计算，设置预览尺寸
     * @param max 是否使用最大的尺寸
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 拍照分辨率
     */
    public Camera.Size getPictureSize(boolean max, Point point, double distortion) {
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

    // ======================
    // == 视频录制大小相关 ==
    // ======================

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
    public Camera.Size getVideoSize(boolean max) {
        return getVideoSize(max, null, -1d, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param point
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(Point point) {
        return getVideoSize(false, point, -1d, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param distortion
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(double distortion) {
        return getVideoSize(false, null, distortion, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算
     * @param point
     * @param distortion
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(Point point, double distortion) {
        return getVideoSize(false, point, distortion, false);
    }

    /**
     * 根据手机支持的视频录制分辨率计算，设置预览尺寸
     * @param max 是否使用最大的尺寸
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @param minAccord 是否存在最小使用最小
     * @return Camera 视频分辨率
     */
    public Camera.Size getVideoSize(boolean max, Point point, double distortion, boolean minAccord) {
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

    // ==================
    // == 内部处理方法 ==
    // ==================

    // 暂时不使用
    /**
     * 根据对应的尺寸, 计算相应最符合的大小
     * @param lists 摄像头尺寸大小(预览、拍照、视频)
     * @param point point.x = > 宽, point.y => 高 (从指定的宽高, 开始往下(超过的不处理)选择最符合的尺寸)
     * @return Camera (预览、拍照、视频) 分辨率
     */
    private Camera.Size calcSize(List<Camera.Size> lists, Point point) {
        if (lists == null) {
            return null;
        }
        // 判断是否竖屏
        boolean isPortrait = false;
        // 尺寸大小
        Camera.Size mSize = null;
        try {
            // 获取屏幕宽、高
            int sWidth = (point != null) ? point.x : ScreenUtils.getScreenWidth();
            int sHeight = (point != null) ? point.y : ScreenUtils.getScreenHeight();
            // 如果高度大于宽度, 则表示属于竖屏
            isPortrait = sHeight > sWidth;
            // 进行排序处理 -> 并以宽度为基准降序排序
            Collections.sort(lists, new Comparator<Camera.Size>() {
                @Override
                public int compare(Camera.Size lhs, Camera.Size rhs) {
                    if (lhs.width > rhs.width) {
                        return -1;
                    } else if (lhs.width == rhs.width) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            // 遍历尺寸大小
            for (Camera.Size size : lists) {
                // 判断横竖屏
                if (isPortrait) { // 属于竖屏 => 高度 > 宽度
                    // 因为是竖屏, 所以判断需要倒着过来
                    if (sWidth == size.height && sHeight == size.width) {
                        // 保存符合比例的大小
                        mSize = size;
                        break;
                    }
                    // 计算合适的比例
                    if (size.width >= sHeight) {
                        mSize = size;
                    }
                } else { // 属于横屏 => 宽度 > 高度
                    // 因为是横屏, 所以判断需要正常
                    if (sWidth == size.width && sHeight == size.height) {
                        // 保存符合比例的大小
                        mSize = size;
                        break;
                    }
                    // 计算合适的比例
                    if (size.height >= sWidth) {
                        mSize = size;
                    }
                }
            }
            // 获取最合适的比例
            LogPrintUtils.dTag(TAG, "返回 calcSize -> 宽度: " + mSize.width + ", 高度: " + mSize.height + ", 是否竖屏: " + isPortrait);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "异常 calcSize - 是否竖屏: " + isPortrait);
        }
        return mSize;
    }

    // ============
    // = 预览大小 =
    // ============

    /**
     * 根据对应的尺寸, 计算相应最符合的大小
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 预览分辨率
     * hint: point.x = > 宽, point.y => 高
     */
    private Camera.Size calcPreviewSize(Point point, double distortion) {
        // 判断是否为null
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

        // ==== 打印信息 ====
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

        // ==

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
     * @param max 是否使用最大的尺寸
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @return Camera 拍照分辨率
     * hint: point.x = > 宽, point.y => 高
     */
    private Camera.Size calcPictureSize(boolean max, Point point, double distortion) {
        // 判断是否为null
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

        // ==== 打印信息 ====
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

        // ==

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
     * @param max 是否使用最大的尺寸
     * @param point 指定的尺寸(为null, 则使用屏幕尺寸) (从指定的宽高, 开始往下(超过的不处理) 选择最接近尺寸)
     * @param distortion 偏差比例值
     * @param minAccord 是否判断存在最小使用最小
     * @return Camera 视频分辨率
     * hint: point.x = > 宽, point.y => 高
     */
    private Camera.Size calcVideoSize(boolean max, Point point, double distortion, boolean minAccord) {
        // 判断是否为null
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

        // ==== 打印信息 ====
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

        // ==

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

//    /**
//     * 根据手机支持的视频分辨率，设置录制尺寸
//     * @param point point.x = > 宽, point.y => 高 (从指定的宽高, 开始往下(超过的不处理)选择最符合的尺寸)
//     * @param designated 指定的尺寸 - 可多个如 竖屏 => new Point(480, 640), new Point(360, 640), 横屏 => new Point(640, 480), new Point(640, 360)
//     * @return Camera 视频分辨率
//     */
//    public Camera.Size getVideoSize(Point point, Point... designated) {
//        if (mCamera == null) {
//            // 打印支持的尺寸
//            LogPrintUtils.dTag(TAG, "camera is null");
//            return null;
//        }
//        // 判断是否竖屏
//        boolean isPortrait = false;
//        // 视频尺寸大小
//        Camera.Size mVideoSize = null;
//        try {
//            // 获取 Camera 参数
//            Camera.Parameters params = mCamera.getParameters();
//            // 小数点处理, 只要后两位
//            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//            decimalFormat.setRoundingMode(RoundingMode.FLOOR);
//            // 获取屏幕宽、高
//            int sWidth = (point != null) ? point.x : ScreenUtils.getScreenWidth();
//            int sHeight = (point != null) ? point.y : ScreenUtils.getScreenHeight();
//            // 如果高度大于宽度, 则表示属于竖屏
//            isPortrait = sHeight > sWidth;
//            // 判断是否存在指定尺寸
//            boolean isDesignated = (designated != null && designated.length != 0);
//            // 打印准备计算的信息
//            LogPrintUtils.dTag(TAG, "getVideoSize - sWidth: " + sWidth + ", sHeight: " + sHeight + ", isPortrait: " + isPortrait + ", isDesignated(是否存在指定尺寸): " + isDesignated);
//            // 打印信息
//            if (isDesignated) {
//                for (int i = 0, len = designated.length; i < len; i++) {
//                    Point appoint = designated[i];
//                    if (appoint != null) {
//                        LogPrintUtils.dTag(TAG, "appoint.x: " + appoint.x + ", appoint.y: " + appoint.y);
//                    }
//                }
//            } else {
//                LogPrintUtils.dTag(TAG, "designated is null or leng == 0");
//            }
//            // 获取支持录制的视频尺寸
//            List<Camera.Size> listVideoSizes = params.getSupportedVideoSizes();
//            // 获取手机支持的分辨率集合, 并以宽度为基准降序排序
//            Collections.sort(listVideoSizes, new Comparator<Camera.Size>() {
//                @Override
//                public int compare(Camera.Size lhs, Camera.Size rhs) {
//                    if (lhs.width > rhs.width) {
//                        return -1;
//                    } else if (lhs.width == rhs.width) {
//                        return 0;
//                    } else {
//                        return 1;
//                    }
//                }
//            });
//            // 是否跳出循环
//            boolean isBreak = false;
//            // 默认是否支持固定的大小
//            Camera.Size fixedSize = null;
//            // 计算比例(竖屏 以高度为基准, 高:宽), (横屏 以宽度为基准, 宽:高)
//            float ratio = (isPortrait ? ((float) sHeight / (float) sWidth) : ((float) sWidth / (float) sHeight)) - 1;
//            // 转换保留两位小数点
//            ratio = Float.parseFloat(decimalFormat.format(ratio));
//            // 遍历预览大小
//            for (Camera.Size size : listVideoSizes) {
//                if (isBreak) {
//                    break;
//                }
//                // 打印支持的尺寸
//                LogPrintUtils.dTag(TAG, "VideoSizes - 宽度: " + size.width + ", 高度: " + size.height);
//                // 判断横竖屏
//                if (isPortrait) { // 属于竖屏 => 高度 > 宽度
//                    // 因为是竖屏, 所以判断需要倒着过来
//                    if (sWidth == size.height && sHeight == size.width) {
//                        // 保存符合比例的大小
//                        mVideoSize = size;
//                    }
//                    // 处理宽大于高的, 因为是使用竖屏, 参数判断都反着处理
//                    if (size.width > size.height) {
//                        // 获取比例
//                        float ratioCalc = ((float) size.width / (float) size.height) - 1;
//                        // 转换保留两位小数点
//                        ratioCalc = Float.parseFloat(decimalFormat.format(ratioCalc));
//                        // 判断符合规则的
//                        if (ratioCalc == ratio) {
//                            // 保存尺寸
//                            fixedSize = size;
//                            // 如果存在才处理
//                            if (isDesignated) {
//                                for (int i = 0, len = designated.length; i < len; i++) {
//                                    Point appoint = designated[i];
//                                    if (appoint != null) {
//                                        // 判断是否支持固定的大小
//                                        if (size.width == appoint.y && size.height == appoint.x) {
//                                            isBreak = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            // 最小支持到640
//                            if (size.width < 640) {
//                                break;
//                            }
//                            // 保存尺寸
//                            fixedSize = size;
//                            // 如果存在才处理
//                            if (isDesignated) {
//                                for (int i = 0, len = designated.length; i < len; i++) {
//                                    Point appoint = designated[i];
//                                    if (appoint != null) {
//                                        // 判断是否支持固定的大小
//                                        if (size.width == appoint.y && size.height == appoint.x) {
//                                            isBreak = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else { // 属于横屏 => 宽度 > 高度
//                    // 因为是横屏, 所以判断需要正常
//                    if (sWidth == size.width && sHeight == size.height) {
//                        // 保存符合比例的大小
//                        mVideoSize = size;
//                    }
//                    // 处理高大于宽的, 因为是使用横屏, 参数判断需要正常
//                    if (size.height > size.width) {
//                        // 获取比例
//                        float ratioCalc = ((float) size.height / (float) size.width) - 1;
//                        // 转换保留两位小数点
//                        ratioCalc = Float.parseFloat(decimalFormat.format(ratioCalc));
//                        // 判断符合规则的
//                        if (ratioCalc == ratio) {
//                            // 保存尺寸
//                            fixedSize = size;
//                            // 如果存在才处理
//                            if (isDesignated) {
//                                for (int i = 0, len = designated.length; i < len; i++) {
//                                    Point appoint = designated[i];
//                                    if (appoint != null) {
//                                        // 判断是否支持固定的大小
//                                        if (size.width == appoint.y && size.height == appoint.x) {
////                                            isBreak = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            // 最小支持到 640
//                            if (size.height < 640) {
//                                break;
//                            }
//                            // 保存尺寸
//                            fixedSize = size;
//                            // 如果存在才处理
//                            if (isDesignated) {
//                                for (int i = 0, len = designated.length; i < len; i++) {
//                                    Point appoint = designated[i];
//                                    if (appoint != null) {
//                                        // 判断是否支持固定的大小
//                                        if (size.width == appoint.y && size.height == appoint.x) {
////                                            isBreak = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            // 如果支持固定的大小, 则进行处理
//            if (fixedSize != null) {
//                // 保存固定支持的尺寸
//                mVideoSize = fixedSize;
//            }
//            // 获取最合适的比例
//            LogPrintUtils.dTag(TAG, "getVideoSize -> 宽度: " + mVideoSize.width + ", 高度: " + mVideoSize.height + ", 是否竖屏: " + isPortrait);
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "getVideoSize - 是否竖屏: " + isPortrait);
//        }
//        return mVideoSize;
//    }
}
