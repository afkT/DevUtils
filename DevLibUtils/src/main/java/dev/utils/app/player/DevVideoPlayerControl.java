package dev.utils.app.player;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import dev.utils.LogPrintUtils;

/**
 * detail: 视频播放控制器
 * @author Ttt
 */
public class DevVideoPlayerControl implements SurfaceHolder.Callback,
        DevMediaManager.MediaListener {

    // ============
    // = 外部回调 =
    // ============

    // 日志 TAG
    private final String TAG = DevVideoPlayerControl.class.getSimpleName();
    // 播放设置
    private DevMediaManager.MediaSet mMediaSet;

    // ========
    // = View =
    // ========

    // 播放载体SurfaceView
    private SurfaceView mSurfaceview;
    // 画面预览回调
    private SurfaceHolder mSurfaceHolder;
    // 判断是否自动播放
    private boolean autoPlay;

    /**
     * 初始化构造函数
     * @param surfaceview
     */
    public DevVideoPlayerControl(final SurfaceView surfaceview) {
        this(surfaceview, false);
    }

    /**
     * 初始化构造函数
     * @param surfaceview
     * @param autoPlay
     */
    public DevVideoPlayerControl(final SurfaceView surfaceview, final boolean autoPlay) {
        this.mSurfaceview = surfaceview;
        this.autoPlay = autoPlay;

        // = 初始化操作 =

        // 初始化 DevMediaManager 回调事件类
        DevMediaManager.getInstance().setMeidaListener(this);
    }

    // ================
    // = 内部快捷控制 =
    // ================

    /**
     * 重置操作
     */
    private void resetOperate() {
        // 移除旧的回调
        if (mSurfaceHolder != null) {
            mSurfaceHolder.removeCallback(this);
        }
        // 设置Holder
        mSurfaceHolder = mSurfaceview.getHolder();
        // 移除旧的回调
        if (mSurfaceHolder != null) {
            mSurfaceHolder.removeCallback(this);
        }
        // 添加回调
        mSurfaceHolder.addCallback(this);
    }

    // ====================
    // = Surface 回调事件 =
    // ====================

    /**
     * surface 改变通知
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogPrintUtils.dTag(TAG, "surfaceChanged -> format: " + format + ", width: " + width + ", height: " + height);
    }

    /**
     * surface 创建
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogPrintUtils.dTag(TAG, "surfaceCreated");
        try {
            // 开始播放
            DevMediaManager.getInstance().playPrepare(mMediaSet);
            // =
            LogPrintUtils.dTag(TAG, "setDisplay(surfaceHolder) - Success");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setDisplay(surfaceHolder) - Error");
        }
    }

    /**
     * surface 销毁
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogPrintUtils.dTag(TAG, "surfaceDestroyed");
    }

    // ========================
    // = MediaPlayer 回调事件 =
    // ========================

    /**
     * 准备完成回调
     */
    @Override
    public void onPrepared() {
        LogPrintUtils.dTag(TAG, "onPrepared");
        // =
        if (mSurfaceview != null) {
            // 如果等于null，或者不在显示中,则跳过
            if (mSurfaceHolder.getSurface() == null || !mSurfaceHolder.getSurface().isValid())
                return;

            try {
                MediaPlayer mPlayer = DevMediaManager.getInstance().getMediaPlayer();
                mPlayer.setDisplay(mSurfaceHolder);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "onPrepared");
            }
            // 判断是否自动播放
            if (autoPlay) {
                try { // 如果没有设置则直接播放
                    DevMediaManager.getInstance().getMediaPlayer().start();
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "onPrepared - start");
                }
            }
            // 触发回调
            if (mMediaListener != null) {
                mMediaListener.onPrepared();
            }
        }
    }

    /**
     * 播放完成/结束
     */
    @Override
    public void onCompletion() {
        LogPrintUtils.dTag(TAG, "onCompletion");
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onCompletion();
        }
    }

    /**
     * 缓存进度
     */
    @Override
    public void onBufferingUpdate(int percent) {
        LogPrintUtils.dTag(TAG, "onBufferingUpdate: " + percent);
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onBufferingUpdate(percent);
        }
    }

    /**
     * 滑动进度加载成功
     */
    @Override
    public void onSeekComplete() {
        LogPrintUtils.dTag(TAG, "onSeekComplete");
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onSeekComplete();
        }
    }

    /**
     * 异常回调
     */
    @Override
    public void onError(int what, int extra) {
        LogPrintUtils.dTag(TAG, "onError -> what: " + what + ", extra: " + extra);
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onError(what, extra);
        }
    }

    /**
     * 视频大小改变通知
     */
    @Override
    public void onVideoSizeChanged(int width, int height) {
        LogPrintUtils.dTag(TAG, "onVideoSizeChanged -> width: " + width + ", height: " + height);
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onVideoSizeChanged(width, height);
        }
    }

    // =

    // 播放事件监听
    private DevMediaManager.MediaListener mMediaListener;

    /**
     * 设置播放监听事件
     * @param mediaListener
     */
    public void setMediaListener(final DevMediaManager.MediaListener mediaListener) {
        this.mMediaListener = mediaListener;
    }

    // ================
    // = 播放快捷操作 =
    // ================

    /**
     * 暂停播放
     */
    public void pausePlayer() {
        DevMediaManager.getInstance().pause();
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        DevMediaManager.getInstance().stop();
    }

    /**
     * 开始播放
     * @param playUri 播放地址
     * @return
     */
    public void startPlayer(final String playUri) {
        startPlayer(playUri, false);
    }

    /**
     * 开始播放
     * @param playUri   播放地址
     * @param isLooping 是否循环播放
     */
    public void startPlayer(final String playUri, final boolean isLooping) {
        // 设置播放信息
        this.mMediaSet = new DevMediaManager.MediaSet() {
            @Override
            public boolean isLooping() {
                return isLooping;
            }

            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.setDataSource(playUri);
            }
        };
        // 重置操作
        resetOperate();
    }

    /**
     * 开始播放
     * @param mediaSet 播放设置
     */
    public void startPlayer(final DevMediaManager.MediaSet mediaSet) {
        // 设置播放信息
        this.mMediaSet = mediaSet;
        // 重置操作
        resetOperate();
    }

    /**
     * 获取显示的SurfaceView
     * @return
     */
    public SurfaceView getSurfaceview() {
        return mSurfaceview;
    }

    // =

    /**
     * 是否播放中
     * @return
     */
    public boolean isPlaying() {
        return DevMediaManager.getInstance().isPlaying();
    }

    /**
     * 是否播放中
     * @param uri 播放地址
     * @return
     */
    public boolean isPlaying(final String uri) {
        if (!TextUtils.isEmpty(uri)) { // 需要播放的地址,必须不等于null
            // 获取之前播放路径
            String playUri = DevMediaManager.getInstance().getPlayUri();
            // 如果不等于null,并且播放地址相同
            if (playUri != null && playUri.equals(uri)) {
                try {
                    return DevMediaManager.getInstance().isPlaying();
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "isPlaying - uri : " + uri);
                }
            }
        }
        return isPlaying();
    }

    /**
     * 判断是否自动播放
     * @return
     */
    public boolean isAutoPlay() {
        return autoPlay;
    }

    /**
     * 设置自动播放
     * @param autoPlay
     */
    public void setAutoPlay(final boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    /**
     * 获取当前播放的地址
     * @return
     */
    public String getPlayUri() {
        return DevMediaManager.getInstance().getPlayUri();
    }


    /**
     * 获取视频宽度
     * @return
     */
    public int getVideoWidth() {
        return DevMediaManager.getInstance().getVideoWidth();
    }

    /**
     * 获取视频高度
     * @return
     */
    public int getVideoHeight() {
        return DevMediaManager.getInstance().getVideoHeight();
    }

    /**
     * 获取当前播放时间
     * @return
     */
    public int getCurrentPosition() {
        return DevMediaManager.getInstance().getCurrentPosition();
    }

    /**
     * 获取资源总时间
     * @return
     */
    public int getDuration() {
        return DevMediaManager.getInstance().getDuration();
    }

    /**
     * 获取播放进度百分比
     * @return
     */
    public int getPlayPercent() {
        return DevMediaManager.getInstance().getPlayPercent();
    }
}
