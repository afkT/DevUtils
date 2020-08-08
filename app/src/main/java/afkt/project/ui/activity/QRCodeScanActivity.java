package afkt.project.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.widget.BaseImageView;
import afkt.project.util.ProjectUtils;
import afkt.project.util.zxing.PreviewCallback;
import afkt.project.util.zxing.decode.DecodeConfig;
import afkt.project.util.zxing.decode.DecodeFormat;
import afkt.project.util.zxing.decode.DecodeResult;
import afkt.project.util.zxing.decode.DecodeThread;
import butterknife.BindView;
import butterknife.OnClick;
import dev.other.ZXingQRCodeUtils;
import dev.other.picture.PictureSelectorUtils;
import dev.utils.app.FlashlightUtils;
import dev.utils.app.HandlerUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.assist.BeepVibrateAssist;
import dev.utils.app.assist.InactivityTimerAssist;
import dev.utils.app.camera1.CameraAssist;
import dev.utils.app.camera1.CameraUtils;
import dev.utils.app.image.ImageUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.widget.ui.ScanShapeView;

/**
 * detail: 二维码扫描解析
 * @author Ttt
 */
public class QRCodeScanActivity extends BaseToolbarActivity implements DecodeResult {

    // = View =
    @BindView(R.id.vid_ass_surface)
    SurfaceView   vid_ass_surface;
    @BindView(R.id.vid_ass_scanview)
    ScanShapeView vid_ass_scanview;
    @BindView(R.id.vid_ass_flashlight_igview)
    BaseImageView vid_ass_flashlight_igview;
    // 无操作计时辅助类
    private InactivityTimerAssist mInactivityTimerAssist;
    // 扫描成功响声 + 震动
    private BeepVibrateAssist     mBeepVibrateAssist;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_shape;
    }

    @Override
    protected void onDestroy() {
        // 销毁处理
        vid_ass_scanview.destroy();
        // 结束计时
        mInactivityTimerAssist.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 清空重新初始化
        mCaptureHandler = null;
        // 开始计时
        mInactivityTimerAssist.onResume();
        // 开始动画
        vid_ass_scanview.startAnim();
        try {
            // 添加回调
            vid_ass_surface.getHolder().addCallback(mHolderCallBack);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCaptureHandler != null) {
            // 停止预览、解码
            mCaptureHandler.quitSynchronously();
            mCaptureHandler = null;
        }
        // 暂停计时
        mInactivityTimerAssist.onPause();
        // 停止动画
        vid_ass_scanview.stopAnim();
        try {
            // 停止预览
            cameraAssist.stopPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void initValue() {
        super.initValue();

        // ================
        // = 初始化辅助类 =
        // ================

        // 停留在该页面无操作计时辅助类
        mInactivityTimerAssist = new InactivityTimerAssist(this);
        // 设置扫描成功响声 + 震动
        mBeepVibrateAssist = new BeepVibrateAssist(this, R.raw.dev_beep);
        // 设置扫描类型
        ProjectUtils.refShape(vid_ass_scanview, ScanShapeView.Shape.Square);
        // 显示图片识别按钮
        ViewUtils.setVisibility(true, findViewById(R.id.vid_ass_image_igview));
    }

    @OnClick({R.id.vid_ass_flashlight_igview, R.id.vid_ass_square_igview,
            R.id.vid_ass_hexagon_igview, R.id.vid_ass_annulus_igview, R.id.vid_ass_image_igview})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.vid_ass_flashlight_igview:
                if (!FlashlightUtils.isFlashlightEnable()) {
                    showToast(false, "暂不支持开启手电筒");
                    return;
                }
                // 设置开关
                setFlashlight(!ViewUtils.isSelected(vid_ass_flashlight_igview));
                break;
            case R.id.vid_ass_square_igview:
                ProjectUtils.refShape(vid_ass_scanview, ScanShapeView.Shape.Square);
                break;
            case R.id.vid_ass_hexagon_igview:
                ProjectUtils.refShape(vid_ass_scanview, ScanShapeView.Shape.Hexagon);
                break;
            case R.id.vid_ass_annulus_igview:
                ProjectUtils.refShape(vid_ass_scanview, ScanShapeView.Shape.Annulus);
                break;
            case R.id.vid_ass_image_igview:
                // 初始化图片配置
                PictureSelectorUtils.PicConfig picConfig = new PictureSelectorUtils.PicConfig()
                        .setCompress(false).setMaxSelectNum(1).setCrop(false).setMimeType(PictureMimeType.ofImage())
                        .setCamera(true).setGif(false);
                // 打开图片选择器
                PictureSelectorUtils.openGallery(PictureSelector.create(this), picConfig);
                break;
        }
    }

    // ==============
    // = 摄像头预览 =
    // ==============

    // 摄像头辅助类
    CameraAssist cameraAssist = new CameraAssist();

    private SurfaceHolder.Callback mHolderCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 检查权限
            checkPermission();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 关闭手电筒
            setFlashlight(false);
            try {
                // 停止预览
                if (cameraAssist != null) {
                    cameraAssist.stopPreview();
                }
            } catch (Exception e) {
                DevLogger.eTag(mTag, e, "surfaceDestroyed");
            }
        }
    };

    /**
     * 检查摄像头权限
     */
    private void checkPermission() {
        // 摄像头权限
        String cameraPermission = Manifest.permission.CAMERA;
        // 判断是否允许权限
        if (PermissionUtils.isGranted(cameraPermission)) {
            try {
                // 打开摄像头
                Camera camera = CameraUtils.open();
                camera.setDisplayOrientation(90); // 设置竖屏显示
                cameraAssist.setCamera(camera);
                // 设置监听
                cameraAssist.setPreviewNotify(new CameraAssist.PreviewNotify() {
                    @Override
                    public void stopPreviewNotify() {
                        DevLogger.dTag(mTag, "停止预览通知");
                        // 通知处理
                        if (mPreviewCallback != null) {
                            mPreviewCallback.setHandler(null, 0);
                        }
                    }

                    @Override
                    public void startPreviewNotify() {
                        DevLogger.dTag(mTag, "开始预览通知");
                    }
                });
                // 获取预览大小
                final Camera.Size size = cameraAssist.getCameraResolution();
                // 设置预览大小, 需要这样设置, 开闪光灯才不会闪烁
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                // 打开摄像头
                cameraAssist.openDriver(vid_ass_surface.getHolder());
                // 初始化预览回调
                mPreviewCallback = new PreviewCallback(size);
                // 初始化获取 Handler
                if (mCaptureHandler == null) {
                    // 内部调用 startPreview() 开始预览方法
                    mCaptureHandler = new CaptureHandler(new DecodeConfig() {

                        // 是否出现异常
                        boolean isError = false;

                        @Override
                        public Handler getHandler() {
                            return mCaptureHandler;
                        }

                        @Override
                        public boolean isCropRect() {
                            return false;
                        }

                        @Override
                        public Rect getCropRect() {
                            if (mCropRect == null) {
                                // 获取扫描区域
                                RectF rectF = vid_ass_scanview.getRegion();
                                // 重设扫描区域
                                Rect rect = new Rect();
                                rect.left = (int) rectF.left;
                                rect.top = (int) rectF.top;
                                rect.right = (int) rectF.right;
                                rect.bottom = (int) rectF.bottom;
                                mCropRect = rect;
                            }
                            return mCropRect;
                        }

                        @Override
                        public Camera.Size getPreviewSize() {
                            if (cameraAssist != null) {
                                return cameraAssist.getPreviewSize();
                            }
                            return null;
                        }

                        @Override
                        public boolean isError() {
                            return isError;
                        }

                        @Override
                        public void setError(boolean isError, Exception e) {
                            this.isError = isError;
                            // 打印日志
                            DevLogger.eTag(mTag, e, "setError");
                        }
                    }, mDecodeMode, cameraAssist, mPreviewCallback, this);
                }
            } catch (Exception e) {
                DevLogger.eTag(mTag, e, "checkPermission - startPreview");
            }
        } else {
            ToastTintUtils.warning("需要摄像头权限预览");
            // 申请权限
            PermissionUtils.permission(cameraPermission).callBack(new PermissionUtils.PermissionCallBack() {
                @Override
                public void onGranted() {
                    // 刷新处理
                    checkPermission();
                }

                @Override
                public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                    // 再次申请权限
                    checkPermission();
                }
            }).request(this);
        }
    }

    // ==============
    // = 手电筒处理 =
    // ==============

    /**
     * 设置手电筒开关
     * @param open 是否打开
     */
    private void setFlashlight(boolean open) {
        if (open) {
            cameraAssist.setFlashlightOn();
        } else {
            cameraAssist.setFlashlightOff();
        }
        ViewUtils.setSelected(open, vid_ass_flashlight_igview);
    }

    // ================
    // = DecodeResult =
    // ================

    @Override
    public void handleDecode(Result result, Bundle bundle) {
        // Camera 解码结果

        // 提示解析成功声音
        mBeepVibrateAssist.playBeepSoundAndVibrate();
        // 打印结果
        DevLogger.dTag(mTag, "handleDecode - result: " + ZXingQRCodeUtils.getResultData(result));

//        // 回传
//        Intent resultIntent = new Intent();
//        bundle.putInt("width", (mCropRect != null) ? mCropRect.width() : ScreenUtils.getScreenWidth());
//        bundle.putInt("height", (mCropRect != null) ? mCropRect.height() : ScreenUtils.getScreenHeight());
//        bundle.putString(KeyConstants.Common.KEY_DATA, result.getText());
//        resultIntent.putExtras(bundle);
//        this.setResult(RESULT_OK, resultIntent);
//        this.finish();

        showToast(true, "二维码内容: " + ZXingQRCodeUtils.getResultData(result));

        // 以下代码只是为了解决停留在此页面可以一直扫码, 实际扫码成功应该回传
        if (mCaptureHandler != null) {
            // mCaptureHandler.restartPreviewAndDecode();

            // 延迟重置, 否则手机一直震动 ( 扫描成功, 重置后又解析成功连续触发 )
            HandlerUtils.postRunnable(new Runnable() {
                @Override
                public void run() {
                    if (mCaptureHandler != null) {
                        try {
                            mCaptureHandler.restartPreviewAndDecode();
                        } catch (Exception e) {
                        }
                    }
                }
            }, 1000);
        }
    }

    // ==================
    // = 二维码识别相关 =
    // ==================

    // 预览回调
    private PreviewCallback mPreviewCallback;
    // 数据解析 Handler
    private CaptureHandler  mCaptureHandler;
    // 扫描区域
    private Rect            mCropRect;
    // 解码类型
    private @DecodeFormat.DecodeMode
    int mDecodeMode = DecodeFormat.ALL;

    /**
     * detail: 捕获预览画面处理 Handler
     * @author Ttt
     */
    private static class CaptureHandler extends Handler {

        // 日志 TAG
        private static final String          TAG = CaptureHandler.class.getSimpleName();
        // 解码线程
        private final        DecodeThread    mDecodeThread;
        // 默认表示成功
        private              State           mState;
        // Camera 辅助类
        private              CameraAssist    mCameraAssist;
        // 数据回调
        private              PreviewCallback mPreviewCallback;
        // 解码结果回调
        private              DecodeResult    mDecodeResult;

        /**
         * 构造函数
         * @param decodeConfig    解析配置
         * @param decodeMode      解析类型
         * @param cameraAssist    Camera 辅助类
         * @param previewCallback 预览回调
         * @param decodeResult    解码结果回调
         */
        public CaptureHandler(DecodeConfig decodeConfig, @DecodeFormat.DecodeMode int decodeMode,
                              CameraAssist cameraAssist, PreviewCallback previewCallback, DecodeResult decodeResult) {
            this.mState = State.SUCCESS;
            // 初始化解码线程
            this.mDecodeThread = new DecodeThread(decodeConfig, decodeMode);
            this.mDecodeThread.start();
            // 初始化辅助类, 并开始预览
            this.mCameraAssist = cameraAssist;
            this.mCameraAssist.startPreview();
            this.mPreviewCallback = previewCallback;
            this.mDecodeResult = decodeResult;
            // 设置预览解码线程
            restartPreviewAndDecode();
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == R.id.restart_preview) {
                restartPreviewAndDecode();
            } else if (message.what == R.id.decode_succeeded) { // 解析成功
                DevLogger.dTag(TAG, "解析成功");
                mState = State.SUCCESS;
                Bundle bundle = message.getData();
                mDecodeResult.handleDecode((Result) message.obj, bundle);
            } else if (message.what == R.id.decode_failed) { // 解析失败 ( 解析不出来触发 )
                DevLogger.dTag(TAG, "解析失败");
                // 表示预览中
                mState = State.PREVIEW;
                // 设置预览解码线程
                requestPreviewFrame(mDecodeThread.getHandler(), R.id.decode);
            }
        }

        /**
         * detail: 内部枚举状态值
         * @author Ttt
         */
        private enum State {
            PREVIEW, SUCCESS, DONE
        }

        /**
         * 重新设置预览以及解码处理
         */
        private void restartPreviewAndDecode() {
            DevLogger.dTag(TAG, "restartPreviewAndDecode");
            if (mState == State.SUCCESS) {
                mState = State.PREVIEW;
                // 设置请求预览页面
                requestPreviewFrame(mDecodeThread.getHandler(), R.id.decode);
            }
        }

        /**
         * 设置预览帧数据监听
         * @param handler 解码 Handler ( DecodeHandler )
         * @param message 解码消息
         */
        private synchronized void requestPreviewFrame(Handler handler, int message) {
            DevLogger.dTag(TAG, "requestPreviewFrame");
            Camera theCamera = mCameraAssist.getCamera();
            // 不为 null 并且预览中才处理
            if (theCamera != null && mCameraAssist.isPreviewing()) {
                mPreviewCallback.setHandler(handler, message);
                theCamera.setOneShotPreviewCallback(mPreviewCallback);
            }
        }

        // ================
        // = 对外公开方法 =
        // ================

        /**
         * 同步退出解析处理
         */
        public void quitSynchronously() {
            DevLogger.dTag(TAG, "退出扫描");
            // 表示状态为默认
            mState = State.DONE;
            // 停止预览
            mCameraAssist.stopPreview();
            Message quit = Message.obtain(mDecodeThread.getHandler(), R.id.quit);
            quit.sendToTarget();
            try {
                // 进行处理解析数据
                mDecodeThread.join(200l);
            } catch (InterruptedException e) {
            }
            // 移除堵塞在队列的消息
            removeMessages(R.id.decode_succeeded);
            removeMessages(R.id.decode_failed);
        }
    }

    // ============
    // = 图片回传 =
    // ============

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 判断是否属于图片选择
        if (resultCode == Activity.RESULT_OK && data != null) {
            LocalMedia localMedia = PictureSelectorUtils.getSingleMedia(data);
            // 获取图片地址
            String imgPath = PictureSelectorUtils.getLocalMediaPath(localMedia, true);
            // 获取图片 Bitmap
            Bitmap selectBitmap = ImageUtils.decodeFile(imgPath);
            // 解析图片
            ZXingQRCodeUtils.decodeQRCode(selectBitmap, new ZXingQRCodeUtils.QRScanCallBack() {
                @Override
                public void onResult(boolean success, Result result, Exception e) {
                    HandlerUtils.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {
                                handleDecode(result, new Bundle());
                            } else {
                                showToast(false, "图片非二维码 / 识别失败");
                            }
                        }
                    });
                }
            });
        }
    }
}