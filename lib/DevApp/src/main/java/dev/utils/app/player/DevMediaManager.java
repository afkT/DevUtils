package dev.utils.app.player;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import androidx.annotation.RawRes;

import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: MediaPlayer 统一管理类
 * @author Ttt
 */
public final class DevMediaManager
        implements OnBufferingUpdateListener,
        OnCompletionListener,
        OnPreparedListener,
        OnVideoSizeChangedListener,
        OnErrorListener,
        OnSeekCompleteListener {

    private DevMediaManager() {
    }

    // 日志 TAG
    private static String TAG = DevMediaManager.class.getSimpleName();

    // MediaPlayer 对象
    private                 MediaPlayer     mMediaPlayer;
    // DevMediaManager 实例
    private static volatile DevMediaManager sInstance;

    /**
     * 获取 DevMediaManager 实例
     * @return {@link DevMediaManager}
     */
    public static DevMediaManager getInstance() {
        if (sInstance == null) {
            synchronized (DevMediaManager.class) {
                if (sInstance == null) {
                    sInstance = new DevMediaManager();
                }
            }
        }
        return sInstance;
    }

    // ===============
    // = 内部处理方法 =
    // ===============

    /**
     * 创建 MediaPlayer
     */
    private void createMedia() {
        // 销毁 MediaPlayer
        destroyMedia();
        // 初始化 MediaPlayer
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        // 绑定事件
        bindListener();
        // 设置默认流类型
        setAudioStreamType(mStreamType);
    }

    /**
     * 销毁 MediaPlayer
     */
    private void destroyMedia() {
        try {
            // 表示非播放状态
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop(); // 停止播放
                }
                mMediaPlayer.release(); // 释放资源
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "destroyMedia");
        }
        // 重置为 null
        mMediaPlayer = null;
        // 清空播放信息
        clearMediaPlayerData();
    }

    /**
     * 绑定事件
     * @return {@code true} 绑定成功, {@code false} 绑定失败
     */
    private boolean bindListener() {
        if (mMediaPlayer != null) {
            // 播放结束回调
            mMediaPlayer.setOnBufferingUpdateListener(this);
            // 播放结束回调
            mMediaPlayer.setOnCompletionListener(this);
            // 预加载完成回调
            mMediaPlayer.setOnPreparedListener(this);
            // 视频宽高大小改变回调
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            // 错误回调
            mMediaPlayer.setOnErrorListener(this);
            // 滑动加载完成回调
            mMediaPlayer.setOnSeekCompleteListener(this);
            return true;
        }
        return false;
    }

    /**
     * 设置流类型
     * @param streamType Audio streamType
     * @return {@link DevMediaManager}
     */
    public DevMediaManager setAudioStreamType(final int streamType) {
        this.mStreamType = streamType;
        // 防止为 null
        if (mMediaPlayer != null) {
            try {
                // 播放流类型
                mMediaPlayer.setAudioStreamType(streamType);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAudioStreamType");
            }
        }
        return this;
    }

    // ===========
    // = 播放操作 =
    // ===========

    /**
     * 播放 Raw 资源
     * @param rawId 播放资源
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepareRaw(@RawRes final int rawId) {
        return playPrepareRaw(rawId, false);
    }

    /**
     * 播放 Raw 资源
     * @param rawId     播放资源
     * @param isLooping 是否循环播放
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepareRaw(
            @RawRes final int rawId,
            final boolean isLooping
    ) {
        try {
            mPlayRawId = rawId;
            mPlayUri = null;
            // 预播放
            return playPrepare(new MediaSet() {
                @Override
                public void setMediaConfig(MediaPlayer mediaPlayer)
                        throws Exception {
                    // 获取资源文件
                    AssetFileDescriptor afd = ResourceUtils.openRawResourceFd(rawId);
                    try {
                        // 设置播放路径
                        mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    } finally {
                        CloseUtils.closeIOQuietly(afd);
                    }
                }

                @Override
                public boolean isLooping() {
                    return isLooping;
                }
            });
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "playPrepareRaw");
            // 销毁资源
            destroyMedia();
        }
        return false;
    }

    // =

    /**
     * 播放 Assets 资源
     * @param playUri 播放地址
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepareAssets(final String playUri) {
        return playPrepareAssets(playUri, false);
    }

    /**
     * 播放 Assets 资源
     * @param playUri   播放地址
     * @param isLooping 是否循环播放
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepareAssets(
            final String playUri,
            final boolean isLooping
    ) {
        try {
            mPlayRawId = -1;
            if (playUri.startsWith("/")) {
                mPlayUri = playUri;
            } else {
                mPlayUri = "/" + playUri;
            }

            final String tempPlayUri = mPlayUri;
            // 预播放
            return playPrepare(new MediaSet() {
                @Override
                public void setMediaConfig(MediaPlayer mediaPlayer)
                        throws Exception {
                    // 获取资源文件
                    AssetFileDescriptor afd = ResourceUtils.openNonAssetFd("assets" + tempPlayUri);
                    try {
                        // 设置播放路径
                        mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    } finally {
                        CloseUtils.closeIOQuietly(afd);
                    }
                }

                @Override
                public boolean isLooping() {
                    return isLooping;
                }
            });
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "playPrepareAssets %s", playUri);
            // 销毁资源
            destroyMedia();
        }
        return false;
    }

    // ===========
    // = 播放路径 =
    // ===========

    /**
     * 预加载播放 (file-path or http/rtsp URL) http 资源、本地资源
     * @param playUri 播放地址
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepare(final String playUri) {
        return playPrepare(playUri, false);
    }

    /**
     * 预加载播放 (file-path or http/rtsp URL) http 资源、本地资源
     * @param playUri   播放地址
     * @param isLooping 是否循环播放
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepare(
            final String playUri,
            final boolean isLooping
    ) {
        try {
            mPlayRawId = -1;
            mPlayUri = playUri;
            // 预播放
            return playPrepare(new MediaSet() {
                @Override
                public void setMediaConfig(MediaPlayer mediaPlayer)
                        throws Exception {
                    mediaPlayer.setDataSource(playUri);
                }

                @Override
                public boolean isLooping() {
                    return isLooping;
                }
            });
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "playPrepare playUri: %s", playUri);
            // 销毁资源
            destroyMedia();
        }
        return false;
    }

    // ===============
    // = 最终调用方法 =
    // ===============

    /**
     * 预加载播放 ( 最终调用方法, 加载成功触发 onPrepared, 该方法内调用 mMediaPlayer.start() )
     * @param mediaSet 播放设置
     * @return {@code true} 执行成功, {@code false} 执行失败
     */
    public boolean playPrepare(final MediaSet mediaSet) {
        // 防止为 null
        if (mediaSet == null) {
            return false;
        }
        try {
            // 初始化 MediaPlayer
            createMedia();
            // 设置循环播放
            mMediaPlayer.setLooping(mediaSet.isLooping());
            // 设置播放音量
            if (mediaSet.getVolume() >= 0f) {
                mMediaPlayer.setVolume(mediaSet.getVolume(), mediaSet.getVolume());
            }
            // 设置播放路径
            mediaSet.setMediaConfig(mMediaPlayer);
            // 异步加载
            mMediaPlayer.prepareAsync();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "playPrepare");
            // 销毁资源
            destroyMedia();
        }
        return false;
    }

    // ===================
    // = MediaPlayer 操作 =
    // ===================

    /**
     * 是否播放中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 暂停操作
     */
    public void pause() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.pause();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "pause");
            }
        }
    }

    /**
     * 停止操作 ( 销毁 MediaPlayer )
     */
    public void stop() {
        // 销毁 MediaPlayer
        destroyMedia();
    }

    // ===========
    // = 快捷操作 =
    // ===========

    /**
     * 是否忽略错误类型
     * @param errorWhat onError 方法回调 what
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isIgnoreWhat(final int errorWhat) {
        // 是否忽略
        boolean ignore = false;
        switch (errorWhat) {
            case -38:
            case 1:
            case 100:
            case 700:
            case 701:
            case 800:
                ignore = true;
                break;
        }
        return ignore;
    }

    // ===========
    // = 回调事件 =
    // ===========

    /**
     * 播放出错回调
     * @param mp    {@link MediaPlayer}
     * @param what  异常 what
     * @param extra 异常 extra
     * @return {@code true} 处理异常, {@code false} 调用 OnCompletionListener
     */
    @Override
    public boolean onError(
            MediaPlayer mp,
            int what,
            int extra
    ) {
        LogPrintUtils.dTag(TAG, "onError what: %s, extra: %s", what, extra);
        // 触发回调
        if (mMediaListener != null) {
            return mMediaListener.onError(what, extra);
        }
        return false;
    }

    /**
     * 视频大小改变通知
     * @param mp     {@link MediaPlayer}
     * @param width  宽度
     * @param height 高度
     */
    @Override
    public void onVideoSizeChanged(
            MediaPlayer mp,
            int width,
            int height
    ) {
        LogPrintUtils.dTag(TAG, "onVideoSizeChanged - width: %s, height: %s", width, height);
        mVideoWidth = width;
        mVideoHeight = height;
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onVideoSizeChanged(width, height);
        }
    }

    /**
     * 使用 mMediaPlayer.prepareAsync() 异步播放准备成功回调
     * @param mp {@link MediaPlayer}
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        LogPrintUtils.dTag(TAG, "onPrepared");
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onPrepared();
        }
    }

    /**
     * 视频播放结束回调
     * @param mp {@link MediaPlayer}
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        LogPrintUtils.dTag(TAG, "onCompletion");
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onCompletion();
        }
    }

    /**
     * MediaPlayer 缓冲更新回调
     * @param mp      {@link MediaPlayer}
     * @param percent 缓冲百分比进度
     */
    @Override
    public void onBufferingUpdate(
            MediaPlayer mp,
            int percent
    ) {
        LogPrintUtils.dTag(TAG, "onBufferingUpdate - percent: %s", percent);
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onBufferingUpdate(percent);
        }
    }

    /**
     * 滑动加载完成回调
     * @param mp {@link MediaPlayer}
     */
    @Override
    public void onSeekComplete(MediaPlayer mp) {
        LogPrintUtils.dTag(TAG, "onSeekComplete");
        // 触发回调
        if (mMediaListener != null) {
            mMediaListener.onSeekComplete();
        }
    }

    // ===============
    // = 封装回调事件 =
    // ===============

    // MediaPlayer 回调事件
    private MediaListener mMediaListener;

    /**
     * detail: MediaPlayer 回调接口
     * @author Ttt
     */
    public interface MediaListener {

        /**
         * 使用 mMediaPlayer.prepareAsync() 异步播放准备成功回调
         */
        void onPrepared();

        /**
         * 视频播放结束回调
         */
        void onCompletion();

        /**
         * MediaPlayer 缓冲更新回调
         * @param percent 缓冲百分比进度
         */
        void onBufferingUpdate(int percent);

        /**
         * 滑动加载完成回调
         */
        void onSeekComplete();

        /**
         * 播放出错回调
         * @param what  异常 what
         * @param extra 异常 extra
         * @return {@code true} 处理异常, {@code false} 调用 OnCompletionListener
         */
        boolean onError(
                int what,
                int extra
        );

        /**
         * 视频大小改变通知
         * @param width  宽度
         * @param height 高度
         */
        void onVideoSizeChanged(
                int width,
                int height
        );
    }

    /**
     * 设置 MediaPlayer 回调事件
     * @param mediaListener {@link MediaListener} MediaPlayer 回调事件
     * @return {@link DevMediaManager}
     */
    public DevMediaManager setMediaListener(final MediaListener mediaListener) {
        this.mMediaListener = mediaListener;
        return this;
    }

    /**
     * detail: Media 播放设置
     * @author Ttt
     */
    public static abstract class MediaSet {

        /**
         * 是否循环播放 ( 默认不循环 )
         * @return {@code true} yes, {@code false} no
         */
        public boolean isLooping() {
            return false;
        }

        /**
         * 获取播放音量 ( 设置, 默认使用全局统一音量 )
         * @return 播放音量
         */
        public float getVolume() {
            return DevMediaManager.getInstance().getVolume();
        }

        /**
         * 设置播放配置
         * @param mediaPlayer {@link MediaPlayer}
         * @throws Exception 设置异常
         */
        public abstract void setMediaConfig(MediaPlayer mediaPlayer)
                throws Exception;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 判断 MediaPlayer 是否为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNullMediaPlayer() {
        return mMediaPlayer == null;
    }

    /**
     * 判断 MediaPlayer 是否不为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNotNullMediaPlayer() {
        return mMediaPlayer != null;
    }

    /**
     * 获取 MediaPlayer 对象
     * @return {@link MediaPlayer}
     */
    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * 设置 MediaPlayer 对象
     * @param mediaPlayer {@link MediaPlayer}
     * @return {@link DevMediaManager}
     */
    public DevMediaManager setMediaPlayer(final MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
        return this;
    }

    /**
     * 设置日志打印 TAG
     * @param tag 日志 TAG
     * @return {@link DevMediaManager}
     */
    public DevMediaManager setTAG(final String tag) {
        TAG = tag;
        return this;
    }

    /**
     * 获取播放音量
     * @return 播放音量
     */
    public float getVolume() {
        return mVolume;
    }

    /**
     * 设置播放音量
     * @param volume 播放音量
     * @return {@link DevMediaManager}
     */
    public DevMediaManager setVolume(final float volume) {
        this.mVolume = volume;
        return this;
    }

    // ===========
    // = 内部变量 =
    // ===========

    // 流类型
    private int    mStreamType  = AudioManager.STREAM_MUSIC;
    // 本地资源
    private int    mPlayRawId   = -1;
    // 播放路径 / 地址
    private String mPlayUri     = null;
    // 视频宽度
    private int    mVideoWidth  = 0;
    // 视频高度
    private int    mVideoHeight = 0;
    // 播放音量
    private float  mVolume      = -1f;

    /**
     * 清空播放信息
     */
    private void clearMediaPlayerData() {
        mPlayRawId = -1;
        mPlayUri = null;
        mVideoWidth = 0;
        mVideoHeight = 0;
    }

    /**
     * 获取播放资源 id
     * @return 播放资源 id
     */
    public int getPlayRawId() {
        return mPlayRawId;
    }

    /**
     * 获取播放地址
     * @return 播放地址
     */
    public String getPlayUri() {
        return mPlayUri;
    }

    /**
     * 获取视频宽度
     * @return 视频宽度
     */
    public int getVideoWidth() {
        return mVideoWidth;
    }

    /**
     * 获取视频高度
     * @return 视频高度
     */
    public int getVideoHeight() {
        return mVideoHeight;
    }

    /**
     * 获取播放时间
     * @return 播放时间
     */
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 获取资源总时间
     * @return 资源总时间
     */
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 获取播放进度百分比
     * @return 播放进度百分比
     */
    public int getPlayPercent() {
        try {
            return (getCurrentPosition() * 100) / getDuration();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPlayPercent");
        }
        return 0;
    }
}