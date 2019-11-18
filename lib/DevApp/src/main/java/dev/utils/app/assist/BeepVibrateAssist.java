package dev.utils.app.assist;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;

import java.io.Closeable;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 播放「bee」的声音, 并且震动辅助类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.VIBRATE" />
 * </pre>
 */
public final class BeepVibrateAssist implements Closeable {

    // 日志 TAG
    private static final String TAG = BeepVibrateAssist.class.getSimpleName();
    // Activity
    private final Activity mActivity;
    // 播放资源对象
    private MediaPlayer mMediaPlayer = null;
    // 是否需要震动
    private boolean mIsVibrate = true;
    // 震动时间
    private long mVibrateDuration = 200L;

    /**
     * 构造函数
     * @param activity {@link Activity}
     */
    public BeepVibrateAssist(final Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 构造函数
     * @param activity {@link Activity}
     * @param rawId    R.raw.id
     */
    public BeepVibrateAssist(final Activity activity, @RawRes final int rawId) {
        this.mActivity = activity;
        this.mMediaPlayer = buildMediaPlayer(rawId);
    }

    /**
     * 构造函数
     * @param activity {@link Activity}
     * @param filePath 文件路径
     */
    public BeepVibrateAssist(final Activity activity, final String filePath) {
        this.mActivity = activity;
        this.mMediaPlayer = buildMediaPlayer(filePath);
    }

    // ================
    // = 内部判断方法 =
    // ================

    /**
     * 判断是否允许播放声音
     * <pre>
     *     RINGER_MODE_NORMAL( 普通 )、RINGER_MODE_SILENT( 静音 )、RINGER_MODE_VIBRATE( 震动 )
     * </pre>
     * @return {@code true} 允许, {@code false} 不允许
     */
    private boolean shouldBeep() {
        AudioManager audioManager = getAudioManager();
        // 只有属于普通模式才播放
        return (audioManager != null && audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL);
    }

    /**
     * 更新播放流处理
     */
    private synchronized void streamUpdate() {
        if (shouldBeep() && mMediaPlayer != null) {
            try {
                // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
                // so we now play on the music stream.
                mActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "streamUpdate");
            }
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 判断是否允许播放声音
     * @return {@code true} 允许, {@code false} 不允许
     */
    public boolean isPlayBeep() {
        return shouldBeep();
    }

    /**
     * 判断是否允许震动
     * @return {@code true} 允许, {@code false} 不允许
     */
    public boolean isVibrate() {
        return mIsVibrate;
    }

    /**
     * 设置是否允许震动
     * @param vibrate 是否允许震动
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setVibrate(final boolean vibrate) {
        return setVibrate(vibrate, 200L);
    }

    /**
     * 设置是否允许震动
     * @param vibrate         是否允许震动
     * @param vibrateDuration 震动时间 ( 毫秒 )
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setVibrate(final boolean vibrate, final long vibrateDuration) {
        this.mIsVibrate = vibrate;
        this.mVibrateDuration = vibrateDuration;
        return this;
    }

    /**
     * 设置播放资源对象
     * @param mediaPlayer {@link MediaPlayer}
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setMediaPlayer(final MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
        // 更新播放流处理
        streamUpdate();
        return this;
    }

    /**
     * 进行播放声音, 并且震动
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public synchronized boolean playBeepSoundAndVibrate() {
        // 判断是否允许播放
        if (shouldBeep() && mMediaPlayer != null) {
            // 判断是否允许震动
            if (mIsVibrate) {
                try {
                    getVibrator().vibrate(mVibrateDuration);
                } catch (Exception e) {
                }
            }
            try {
                // 播放
                mMediaPlayer.start();
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    /**
     * 关闭震动、提示声, 并释放资源
     */
    @Override
    public synchronized void close() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    // =========================
    // = 创建 MediaPlayer 处理 =
    // =========================

    /**
     * 创建 MediaPlayer 对象
     * @param rawId R.raw.id
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(@RawRes final int rawId) {
        return buildMediaPlayer(rawId, 0.1f);
    }

    /**
     * 创建 MediaPlayer 对象
     * @param rawId      R.raw.id
     * @param beepVolume 音量
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(@RawRes final int rawId, final float beepVolume) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onCompletion");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onError what: " + what + ", extra: " + extra);
                // 播放异常, 直接不处理
                return true;
            }
        });
        try {
            AssetFileDescriptor file = openRawResourceFd(rawId);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            } finally {
                if (file != null) {
                    try {
                        file.close();
                    } catch (Exception e) {
                    }
                }
            }
            mediaPlayer.setVolume(beepVolume, beepVolume);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "buildMediaPlayer");
            mediaPlayer.release();
            return null;
        }
    }

    // =

    /**
     * 创建 MediaPlayer 对象
     * @param filePath 文件路径
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final String filePath) {
        return buildMediaPlayer(filePath, 0.1f);
    }

    /**
     * 创建 MediaPlayer 对象
     * @param filePath   文件路径
     * @param beepVolume 音量
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final String filePath, final float beepVolume) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onCompletion");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onError what: " + what + ", extra: " + extra);
                // 播放异常, 直接不处理
                return true;
            }
        });
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.setVolume(beepVolume, beepVolume);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "buildMediaPlayer");
            mediaPlayer.release();
            return null;
        }
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ============
    // = AppUtils =
    // ============

    /**
     * 获取 AudioManager
     * @return {@link AudioManager}
     */
    private static AudioManager getAudioManager() {
        return getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 获取 Vibrator
     * @return {@link Vibrator}
     */
    private static Vibrator getVibrator() {
        return getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 获取 SystemService
     * @param name 服务名
     * @param <T>  泛型
     * @return SystemService Object
     */
    private static <T> T getSystemService(final String name) {
        if (isSpace(name)) return null;
        try {
            return (T) DevUtils.getContext().getSystemService(name);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSystemService");
        }
        return null;
    }

    // =================
    // = ResourceUtils =
    // =================

    /**
     * 获取对应资源 AssetFileDescriptor
     * @param id resource identifier
     * @return {@link AssetFileDescriptor}
     */
    private static AssetFileDescriptor openRawResourceFd(@RawRes final int id) {
        try {
            return DevUtils.getContext().getResources().openRawResourceFd(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openRawResourceFd");
        }
        return null;
    }

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}